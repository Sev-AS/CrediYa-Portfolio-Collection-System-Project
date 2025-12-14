package com.crediya.pagos;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;
import com.crediya.prestamos.PrestamoRepository;
import com.crediya.prestamos.Prestamos;
import com.crediya.reportes.ReportePagos;

public class MenuPagos {
    private final PagosRepository pagosRepository;
    private final PrestamoRepository prestamoRepository;
    private final Scanner consola;

    public MenuPagos(PagosRepository pagosRepository, PrestamoRepository prestamoRepository) {
        this.pagosRepository = pagosRepository;
        this.prestamoRepository = prestamoRepository;
        this.consola = new Scanner(System.in);
    }

    public void iniciarMenu() {
        int opcion = 0;
        do {
            try {
                System.out.println(
                        """

                                 --- GESTION DE PAGOS ---
                                1. Registrar Pago
                                2. Listar Pagos de un Prestamo
                                3. Generar Reporte de Pagos por Prestamo
                                4. Generar Reporte General de Pagos
                                5. Volver
                                """);
                System.out.print("Opcion: ");
                opcion = consola.nextInt();
                consola.nextLine();

                switch (opcion) {
                    case 1 -> registrarPago();
                    case 2 -> listarPagos();
                    case 3 -> generarReportePorPrestamo();
                    case 4 -> generarReporteGeneral();
                    case 5 -> System.out.println("Volviendo al menu principal...");
                    default -> System.out.println("Opcion no valida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Por favor, introduce un numero valido.");
                consola.nextLine();
                opcion = 0;
            }
        } while (opcion != 5);
    }

    private void registrarPago() {
        System.out.println("--- Registrar Pago ---");
        try {
            System.out.print("ID del Prestamo: ");
            int prestamoId = consola.nextInt();

            Prestamos prestamo = prestamoRepository.obtenerPorId(prestamoId);
            if (prestamo == null) {
                System.out.println("Error: No existe un prestamo con ID " + prestamoId);
                return;
            }

            System.out.println("Saldo Pendiente Actual: " + prestamo.getSaldoPendiente());
            if (prestamo.getSaldoPendiente() <= 0) {
                System.out.println("Este prestamo ya esta pagado por completo.");
                return;
            }

            System.out.print("Monto a Pagar: ");
            double monto = consola.nextDouble();
            consola.nextLine();

            if (monto <= 0) {
                System.out.println("El monto debe ser mayor a 0.");
                return;
            }

            if (monto > prestamo.getSaldoPendiente()) {
                System.out.println(
                        "Advertencia: El monto es mayor al saldo pendiente (" + prestamo.getSaldoPendiente() + ").");
                System.out.print("Desea continuar y registrar un saldo a favor? (S/N): ");
                String confirm = consola.nextLine();
                if (!confirm.equalsIgnoreCase("S")) {
                    return;
                }
            }

            String fecha = LocalDate.now().toString();

            Pagos pago = new Pagos(0, prestamoId, monto, fecha);
            Pagos pagoRegistrado = pagosRepository.registrar(pago);

            if (pagoRegistrado != null) {
                double nuevoSaldo = prestamo.getSaldoPendiente() - monto;
                if (Math.abs(nuevoSaldo) < 0.01)
                    nuevoSaldo = 0;

                prestamoRepository.actualizarSaldo(prestamoId, nuevoSaldo);
                System.out.println("Pago registrado exitosamente. Nuevo saldo pendiente: " + nuevoSaldo);

                if (nuevoSaldo <= 0) {
                    prestamoRepository.cambiarEstado(prestamoId, "Pagado");
                    System.out.println("El prestamo ha sido marcado como 'Pagado'.");
                }
            } else {
                System.out.println("Error al registrar el pago en la base de datos.");
            }

        } catch (InputMismatchException e) {
            System.out.println("Error: Valor invalido.");
            consola.nextLine();
        }
    }

    private void listarPagos() {
        System.out.println("--- Listar Pagos por Prestamo ---");
        try {
            System.out.print("ID del Prestamo: ");
            int prestamoId = consola.nextInt();
            consola.nextLine();

            var lista = pagosRepository.listarPorPrestamo(prestamoId);
            if (lista.isEmpty()) {
                System.out.println("No hay pagos registrados para este prestamo.");
            } else {
                lista.forEach(System.out::println);
            }
        } catch (InputMismatchException e) {
            System.out.println("Valor invalido.");
            consola.nextLine();
        }
    }

    private void generarReportePorPrestamo() {
        System.out.println("--- Generar Reporte de Pagos por Prestamo ---");
        try {
            System.out.print("ID del Prestamo: ");
            int prestamoId = consola.nextInt();
            consola.nextLine();

            Prestamos prestamo = prestamoRepository.obtenerPorId(prestamoId);
            if (prestamo == null) {
                System.out.println("Error: No existe un prestamo con ID " + prestamoId);
                return;
            }

            String ruta = ReportePagos.generarReportePorPrestamo(prestamoId, pagosRepository, prestamoRepository);
            if (ruta != null) {
                System.out.println("Reporte generado exitosamente.");
            } else {
                System.out.println("Error al generar el reporte.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Valor invalido.");
            consola.nextLine();
        }
    }

    private void generarReporteGeneral() {
        System.out.println("--- Generar Reporte General de Pagos ---");
        String ruta = ReportePagos.generarReporteGeneral(pagosRepository, prestamoRepository);
        if (ruta != null) {
            System.out.println("Reporte general generado exitosamente.");
        } else {
            System.out.println("Error al generar el reporte general.");
        }
    }
}
