package com.crediya.prestamos;

import java.util.List;

public interface PrestamoRepository {
    Prestamos registrarPrestamo(Prestamos prestamo);

    List<Prestamos> listarPrestamos();

    Prestamos obtenerPorId(int id);
    
    List<Prestamos> obtenerPorClienteId(int clienteId);

    void cambiarEstado(int id, String nuevoEstado);

    void actualizarSaldo(int id, double nuevoSaldo);
}