package com.crediya.reportes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.crediya.pagos.Pagos;
import com.crediya.pagos.PagosRepository;
import com.crediya.prestamos.PrestamoRepository;
import com.crediya.prestamos.Prestamos;

public class ReportePagos {

    private static final String RUTA_REPORTES = "src/main/java/com/crediya/db/reportes/";

    public static String generarReportePorPrestamo(int prestamoId, PagosRepository pagosRepository,
            PrestamoRepository prestamoRepository) {
        List<Pagos> pagos = pagosRepository.listarPorPrestamo(prestamoId);
        Prestamos prestamo = prestamoRepository.obtenerPorId(prestamoId);

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String nombreArchivo = "reporte_pagos_prestamo_" + prestamoId + "_" + timestamp + ".txt";
        String rutaCompleta = RUTA_REPORTES + nombreArchivo;

        File directorio = new File(RUTA_REPORTES);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        try (PrintWriter writer = new PrintWriter(new File(rutaCompleta))) {
            writer.println("=".repeat(70));
            writer.println("REPORTE DE PAGOS POR PRESTAMO");
            writer.println("=".repeat(70));
            writer.println("Fecha de generacion: "
                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            writer.println();

            if (prestamo != null) {
                writer.println("INFORMACION DEL PRESTAMO:");
                writer.println("-".repeat(70));
                writer.println("ID Prestamo: " + prestamo.getId());
                writer.println("ID Cliente: " + prestamo.getClienteId());
                writer.println("ID Empleado: " + prestamo.getEmpleadoId());
                writer.println("Monto: " + String.format("%.2f", prestamo.getMonto()));
                writer.println("Interes Mensual: " + String.format("%.4f", prestamo.getInteresMensual()));
                writer.println("Cuotas: " + prestamo.getCuotas() + " cuotas");
                writer.println("Estado: " + prestamo.getEstado());
                writer.println("Saldo Pendiente: " + String.format("%.2f", prestamo.getSaldoPendiente()));
                writer.println();
            }

            writer.println("DETALLE DE PAGOS:");
            writer.println("-".repeat(70));
            writer.println(String.format("%-10s %-20s %-20s", "ID Pago", "Monto", "Fecha"));
            writer.println("-".repeat(70));

            if (pagos.isEmpty()) {
                writer.println("No hay pagos registrados para este prestamo.");
            } else {
                double totalPagado = 0;
                for (Pagos pago : pagos) {
                    writer.println(String.format("%-10d %-20.2f %-20s",
                            pago.getId(),
                            pago.getMonto(),
                            pago.getFecha()));
                    totalPagado += pago.getMonto();
                }
                writer.println("-".repeat(70));
                writer.println(String.format("TOTAL PAGADO: %.2f", totalPagado));
            }

            writer.println("=".repeat(70));
            System.out.println("Reporte generado exitosamente en: " + rutaCompleta);
            return rutaCompleta;

        } catch (FileNotFoundException e) {
            System.err.println("Error al generar el reporte: " + e.getMessage());
            return null;
        }
    }

    public static String generarReporteGeneral(PagosRepository pagosRepository, PrestamoRepository prestamoRepository) {
        List<Prestamos> prestamos = prestamoRepository.listarPrestamos();

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String nombreArchivo = "reporte_pagos_general_" + timestamp + ".txt";
        String rutaCompleta = RUTA_REPORTES + nombreArchivo;

        File directorio = new File(RUTA_REPORTES);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        try (PrintWriter writer = new PrintWriter(new File(rutaCompleta))) {
            writer.println("=".repeat(70));
            writer.println("REPORTE GENERAL DE PAGOS");
            writer.println("=".repeat(70));
            writer.println("Fecha de generacion: "
                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            writer.println();

            double totalGeneralPagado = 0;
            int totalPrestamosConPagos = 0;

            for (Prestamos prestamo : prestamos) {
                List<Pagos> pagos = pagosRepository.listarPorPrestamo(prestamo.getId());

                if (!pagos.isEmpty()) {
                    totalPrestamosConPagos++;
                    writer.println("PRESTAMO ID: " + prestamo.getId());
                    writer.println("-".repeat(70));
                    writer.println("Cliente ID: " + prestamo.getClienteId());
                    writer.println("Monto Prestamo: " + String.format("%.2f", prestamo.getMonto()));
                    writer.println("Saldo Pendiente: " + String.format("%.2f", prestamo.getSaldoPendiente()));
                    writer.println();
                    writer.println(String.format("%-10s %-20s %-20s", "ID Pago", "Monto", "Fecha"));
                    writer.println("-".repeat(70));

                    double totalPagadoPrestamo = 0;
                    for (Pagos pago : pagos) {
                        writer.println(String.format("%-10d %-20.2f %-20s",
                                pago.getId(),
                                pago.getMonto(),
                                pago.getFecha()));
                        totalPagadoPrestamo += pago.getMonto();
                    }

                    writer.println("-".repeat(70));
                    writer.println(String.format("Subtotal Prestamo %d: %.2f", prestamo.getId(), totalPagadoPrestamo));
                    writer.println();
                    totalGeneralPagado += totalPagadoPrestamo;
                }
            }

            writer.println("=".repeat(70));
            writer.println("RESUMEN GENERAL:");
            writer.println("Total de prestamos con pagos: " + totalPrestamosConPagos);
            writer.println(String.format("TOTAL GENERAL PAGADO: %.2f", totalGeneralPagado));
            writer.println("=".repeat(70));

            System.out.println("Reporte general generado exitosamente en: " + rutaCompleta);
            return rutaCompleta;

        } catch (FileNotFoundException e) {
            System.err.println("Error al generar el reporte general: " + e.getMessage());
            return null;
        }
    }
}
