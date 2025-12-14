package com.crediya.prestamos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PrestamoRepositoryEnMemoria implements PrestamoRepository {

    private final List<Prestamos> prestamos = new ArrayList<>();
    private final AtomicInteger proximoId = new AtomicInteger(1);

    @Override
    public Prestamos agregar(Prestamos prestamo) {
        prestamo.setId(proximoId.getAndIncrement());
        prestamos.add(prestamo);
        return prestamo;
    }

    @Override
    public List<Prestamos> listar() {
        return new ArrayList<>(prestamos);
    }

    @Override
    public Prestamos obtenerPorId(int id) {
        return prestamos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void cambiarEstado(int id, String nuevoEstado) {
        Prestamos prestamo = obtenerPorId(id);
        if (prestamo != null) {
            prestamo.setEstado(nuevoEstado);
        }
    }

    @Override
    public void actualizarSaldo(int id, double nuevoSaldo) {
        Prestamos prestamo = obtenerPorId(id);
        if (prestamo != null) {
            prestamo.setSaldoPendiente(nuevoSaldo);
        }
    }

}
