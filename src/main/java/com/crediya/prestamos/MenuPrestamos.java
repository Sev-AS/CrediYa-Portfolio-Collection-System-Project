package com.crediya.prestamos;

import com.crediya.clientes.ClienteRepository;
import com.crediya.empleados.EmpleadoRepository;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuPrestamos {
    private final PrestamoRepository prestamoRepository;
    private final ClienteRepository clienteRepository;
    private final EmpleadoRepository empleadoRepository;
    private final Scanner consola;

    public MenuPrestamos(PrestamoRepository prestamoRepository, ClienteRepository clienteRepository,
            EmpleadoRepository empleadoRepository) {
        this.prestamoRepository = prestamoRepository;
        this.clienteRepository = clienteRepository;
        this.empleadoRepository = empleadoRepository;
        this.consola = new Scanner(System.in);
    }

    public void iniciarMenu() {
        int opcion = 0;
        do {
            try {
                System.out.println(
                        """

                                ▄▄▄▄▄▄▄
                                ███▀▀███▄                    ██
                                ███▄▄███▀ ████▄ ▄█▀█▄ ▄█▀▀▀ ▀██▀▀ ▀▀█▄ ███▄███▄ ▄███▄
                                ███▀▀▀▀   ██ ▀▀ ██▄█▀ ▀███▄  ██  ▄█▀██ ██ ██ ██ ██ ██
                                ███       ██    ▀█▄▄▄ ▄▄▄█▀  ██  ▀█▄██ ██ ██ ██ ▀███▀

                                1. Registrar Nuevo Prestamo
                                2. Ver Lista de Prestamos
                                3. Cambiar Estado de un Prestamo
                                4. Salir
                                """);
                System.out.print("Opcion: ");
                opcion = consola.nextInt();
                consola.nextLine();

                switch (opcion) {
                    case 1 -> mpAgregarPrestamo();
                    case 2 -> mpListarPrestamos();
                    case 3 -> mpCambiarEstado();
                    case 4 -> System.out.println("Saliendo del menu de prestamos...");
                    default -> System.out.println("Opcion no valida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Introduzca un numero valido.");
                consola.nextLine();
                opcion = 0;
            }
        } while (opcion != 4);
    }

    private void mpAgregarPrestamo() {
        System.out.println("Registrar Nuevo Prestamo");
        try {
            System.out.print("Documento del Cliente: ");
            int documentoCliente = consola.nextInt();

            var cliente = clienteRepository.obtenerPorDocumento(documentoCliente);
            if (cliente == null) {
                System.out.println("El cliente con documento " + documentoCliente + " no existe.");
                return;
            }
            int clienteId = cliente.getId();

            System.out.print("ID del Empleado: ");
            int empleadoId = consola.nextInt();

            if (empleadoRepository.obtenerPorId(empleadoId) == null) {
                System.out.println("El empleado con ID " + empleadoId + " no existe.");
                return;
            }

            System.out.print("Monto del prestamo: ");
            double monto = consola.nextDouble();
            if (monto <= 0) {
                System.out.println("El monto debe ser mayor a 0.");
                return;
            }

            System.out.print("Tasa de Interes Mensual En Formato Decimal: ");
            double interes = consola.nextDouble();
            if (interes < 0) {
                System.out.println("No puede ser negativo.");
                return;
            }
            if (interes > 1) {
                System.out.println("El interes parece estar en formato porcentaje.");
                System.out.print("Desea continuar? (S/N): ");
                String confirm = consola.next();
                if (!confirm.equalsIgnoreCase("S")) {
                    return;
                }
            }

            System.out.print("Numero de cuotas: ");
            int cuotas = consola.nextInt();
            if (cuotas <= 0) {
                System.out.println("El numero de cuotas debe ser mayor a 0.");
                return;
            }
            consola.nextLine();

            System.out.print("Fecha de Inicio En Formato YYYY-MM-DD: ");
            String fecha = consola.nextLine();

            System.out.print("Estado Inicial del prestamo (Activo, Pagado): ");
            String estado = consola.nextLine();

            Prestamos nuevo = new Prestamos(0, clienteId, empleadoId, monto, interes, cuotas, fecha, estado);
            Prestamos prestamoGuardado = prestamoRepository.agregar(nuevo);

            if (prestamoGuardado != null) {
                System.out.println("Prestamo registrado exitosamente con ID: " + prestamoGuardado.getId());
                System.out.println("Detalles del prestamo:");
                System.out.println(prestamoGuardado);
            } else {
                System.out.println("Error al guardar el prestamo.");
            }

        } catch (InputMismatchException e) {
            System.out.println("Uno de los valores numericos es invalido.");
            consola.nextLine();
        }
    }

    private void mpListarPrestamos() {
        System.out.println("Lista de Prestamos");
        List<Prestamos> prestamos = prestamoRepository.listar();
        if (prestamos.isEmpty()) {
            System.out.println("No hay prestamos registrados.");
        } else {
            prestamos.forEach(System.out::println);
        }
    }

    private void mpCambiarEstado() {
        System.out.println("Cambiar Estado de Prestamo");
        try {
            System.out.print("ID del prestamo a modificar: ");
            int id = consola.nextInt();
            consola.nextLine();

            Prestamos prestamo = prestamoRepository.obtenerPorId(id);
            if (prestamo == null) {
                System.out.println("No se encontro un prestamo con el ID: " + id);
                return;
            }

            System.out.print("Introduce el nuevo estado: ");
            String nuevoEstado = consola.nextLine();

            prestamoRepository.cambiarEstado(id, nuevoEstado);
            System.out.println("El estado del prestamo " + id + " ha sido actualizado a '" + nuevoEstado + "'.");

        } catch (InputMismatchException e) {
            System.out.println("Ingrese un numero entero positivob valido.");
            consola.nextLine();
        }
    }
}