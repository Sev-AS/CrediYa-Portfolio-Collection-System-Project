package com.crediya.reportes;

import java.util.List;
import java.util.stream.Collectors;
import com.crediya.clientes.Cliente;
import com.crediya.prestamos.Prestamos;


public class ReporteManager {

    public static List<Prestamos> filtrarPrestamosActivos(List<Prestamos> prestamos) {
        return prestamos.stream()
                .filter(p -> p.getEstado().equalsIgnoreCase("Pendiente"))
                .collect(Collectors.toList());
    }

    public static List<Prestamos> filtrarPrestamosVencidos(List<Prestamos> prestamos) {
        return prestamos.stream()
                .filter(p -> p.getEstado().equalsIgnoreCase("Vencido"))
                .filter(p -> p.getSaldoPendiente() > 0)
                .collect(Collectors.toList());
    }

    public static List<Cliente> filtrarClientesMorosos(List<Cliente> clientes, List<Prestamos> prestamos) {
        // Obtener IDs de clientes con préstamos vencidos
        List<Integer> clientesConPrestamosVencidos = prestamos.stream()
                .filter(p -> p.getEstado().equalsIgnoreCase("Vencido"))
                .filter(p -> p.getSaldoPendiente() > 0)
                .map(Prestamos::getClienteId)
                .distinct()
                .collect(Collectors.toList());

        // Filtrar clientes que están en la lista de morosos
        return clientes.stream()
                .filter(c -> clientesConPrestamosVencidos.contains(c.getId()))
                .collect(Collectors.toList());
    }

    private ReporteManager() {
        throw new UnsupportedOperationException("Clase utilitaria - no debe ser instanciada");
    }
}