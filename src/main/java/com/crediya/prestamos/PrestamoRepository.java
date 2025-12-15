package com.crediya.prestamos;

import java.util.List;

public interface PrestamoRepository {
    Prestamos agregar(Prestamos prestamo);

    List<Prestamos> listar();

    Prestamos obtenerPorId(int id);
    
    List<Prestamos> obtenerPorClienteId(int clienteId);

    void cambiarEstado(int id, String nuevoEstado);

    void actualizarSaldo(int id, double nuevoSaldo);
}