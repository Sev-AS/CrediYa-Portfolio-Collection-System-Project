package com.crediya.reportes;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.crediya.clientes.Cliente;
import com.crediya.empleados.Empleado;
import com.crediya.pagos.Pagos;
import com.crediya.prestamos.Prestamos;

public class ReporteManager {

    public static List<Pagos> filtrarPagosPorRangoFecha(List<Pagos> pagos, String fechaInicio, String fechaFin) {
        return pagos.stream()
                .filter(p -> p.getFecha().compareTo(fechaInicio) >= 0 && p.getFecha().compareTo(fechaFin) <= 0)
                .collect(Collectors.toList());
    }

    public static List<Pagos> filtrarPagosPorMontoMinimo(List<Pagos> pagos, double montoMinimo) {
        return pagos.stream()
                .filter(p -> p.getMonto() >= montoMinimo)
                .collect(Collectors.toList());
    }

    public static double calcularTotalPagos(List<Pagos> pagos) {
        return pagos.stream()
                .mapToDouble(Pagos::getMonto)
                .sum();
    }

    public static Map<Integer, List<Pagos>> agruparPagosPorPrestamo(List<Pagos> pagos) {
        return pagos.stream()
                .collect(Collectors.groupingBy(Pagos::getPrestamoId));
    }

    public static Optional<Pagos> obtenerPagoMayor(List<Pagos> pagos) {
        return pagos.stream()
                .max(Comparator.comparingDouble(Pagos::getMonto));
    }

    public static List<Prestamos> filtrarPrestamosPorEstado(List<Prestamos> prestamos, String estado) {
        return prestamos.stream()
                .filter(p -> p.getEstado().equalsIgnoreCase(estado))
                .collect(Collectors.toList());
    }

    public static List<Prestamos> filtrarPrestamosActivos(List<Prestamos> prestamos) {
        return prestamos.stream()
                .filter(p -> p.getEstado().equalsIgnoreCase("Pendiente"))
                .collect(Collectors.toList());
    }

    public static List<Prestamos> filtrarPrestamosPorMontoMinimo(List<Prestamos> prestamos, double montoMinimo) {
        return prestamos.stream()
                .filter(p -> p.getMonto() >= montoMinimo)
                .collect(Collectors.toList());
    }

    public static List<Prestamos> filtrarPrestamosPorCliente(List<Prestamos> prestamos, int clienteId) {
        return prestamos.stream()
                .filter(p -> p.getClienteId() == clienteId)
                .collect(Collectors.toList());
    }

    public static double calcularPromedioSaldoPendiente(List<Prestamos> prestamos) {
        return prestamos.stream()
                .mapToDouble(Prestamos::getSaldoPendiente)
                .average()
                .orElse(0.0);
    }

    public static long contarPrestamosPorEstado(List<Prestamos> prestamos, String estado) {
        return prestamos.stream()
                .filter(p -> p.getEstado().equalsIgnoreCase(estado))
                .count();
    }

    public static List<Prestamos> obtenerPrestamosConSaldoPendiente(List<Prestamos> prestamos) {
        return prestamos.stream()
                .filter(p -> p.getSaldoPendiente() > 0)
                .collect(Collectors.toList());
    }

    public static List<Cliente> filtrarClientesPorNombre(List<Cliente> clientes, String nombreParcial) {
        return clientes.stream()
                .filter(c -> c.getNombre().toLowerCase().contains(nombreParcial.toLowerCase()))
                .collect(Collectors.toList());
    }

    public static List<Cliente> ordenarClientesPorNombre(List<Cliente> clientes) {
        return clientes.stream()
                .sorted(Comparator.comparing(Cliente::getNombre))
                .collect(Collectors.toList());
    }

    public static List<Empleado> filtrarEmpleadosPorRol(List<Empleado> empleados, String rol) {
        return empleados.stream()
                .filter(e -> e.getRol().equalsIgnoreCase(rol))
                .collect(Collectors.toList());
    }

    public static List<Empleado> filtrarEmpleadosPorSalarioMinimo(List<Empleado> empleados, double salarioMinimo) {
        return empleados.stream()
                .filter(e -> e.getSalario() >= salarioMinimo)
                .collect(Collectors.toList());
    }

    public static double calcularPromedioSalarios(List<Empleado> empleados) {
        return empleados.stream()
                .mapToDouble(Empleado::getSalario)
                .average()
                .orElse(0.0);
    }

    public static Optional<Empleado> obtenerEmpleadoConMayorSalario(List<Empleado> empleados) {
        return empleados.stream()
                .max(Comparator.comparingDouble(Empleado::getSalario));
    }

    public static Map<String, Long> contarPrestamosPorEstadoAgrupado(List<Prestamos> prestamos) {
        return prestamos.stream()
                .collect(Collectors.groupingBy(Prestamos::getEstado, Collectors.counting()));
    }

    public static Map<Integer, Double> calcularTotalPagadoPorPrestamo(List<Pagos> pagos) {
        return pagos.stream()
                .collect(Collectors.groupingBy(
                        Pagos::getPrestamoId,
                        Collectors.summingDouble(Pagos::getMonto)));
    }

    public static List<Prestamos> ordenarPrestamosPorMonto(List<Prestamos> prestamos) {
        return prestamos.stream()
                .sorted(Comparator.comparingDouble(Prestamos::getMonto).reversed())
                .collect(Collectors.toList());
    }

    public static List<Pagos> ordenarPagosPorFecha(List<Pagos> pagos) {
        return pagos.stream()
                .sorted(Comparator.comparing(Pagos::getFecha))
                .collect(Collectors.toList());
    }

    public static double calcularMontoTotalPrestamos(List<Prestamos> prestamos) {
        return prestamos.stream()
                .mapToDouble(Prestamos::getMonto)
                .sum();
    }

    public static List<Prestamos> filtrarPrestamosPorEmpleado(List<Prestamos> prestamos, int empleadoId) {
        return prestamos.stream()
                .filter(p -> p.getEmpleadoId() == empleadoId)
                .collect(Collectors.toList());
    }

    public static Optional<Prestamos> obtenerPrestamoConMayorSaldo(List<Prestamos> prestamos) {
        return prestamos.stream()
                .max(Comparator.comparingDouble(Prestamos::getSaldoPendiente));
    }

    public static List<Empleado> ordenarEmpleadosPorSalario(List<Empleado> empleados) {
        return empleados.stream()
                .sorted(Comparator.comparingDouble(Empleado::getSalario).reversed())
                .collect(Collectors.toList());
    }

    public static Map<String, List<Empleado>> agruparEmpleadosPorRol(List<Empleado> empleados) {
        return empleados.stream()
                .collect(Collectors.groupingBy(Empleado::getRol));
    }

    public static List<Pagos> filtrarPagosPorMontoRango(List<Pagos> pagos, double montoMin, double montoMax) {
        return pagos.stream()
                .filter(p -> p.getMonto() >= montoMin && p.getMonto() <= montoMax)
                .collect(Collectors.toList());
    }

    public static List<Prestamos> filtrarPrestamosPorCuotasMinimas(List<Prestamos> prestamos, int cuotasMinimas) {
        return prestamos.stream()
                .filter(p -> p.getCuotas() >= cuotasMinimas)
                .collect(Collectors.toList());
    }

    public static double calcularSaldoPendienteTotal(List<Prestamos> prestamos) {
        return prestamos.stream()
                .mapToDouble(Prestamos::getSaldoPendiente)
                .sum();
    }
}
