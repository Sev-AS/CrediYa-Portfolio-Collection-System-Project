package com.crediya.prestamos;

import java.util.ArrayList;
import java.util.List;

import com.crediya.prestamos.Prestamos;


public class GestorPrestamos implements PrestamoRepository{


    private List<Prestamos> prestamos;


    @Override
    public Prestamos registrarPrestamo(Prestamos prestamo) {
        if (monto <= 0 || plazoMeses< 0) {
            throw new DatoInvalidoException (
                "El monto y el plazo deben ser mayores a 0.");
        }
    }

    @Override
    public List<Prestamos> listarPrestamos() {
        return new ArrayList<>(prestamos);
    }

    @Override
    public Prestamos obtenerPorId(int id) {
        throw new UnsupportedOperationException("Unimplemented method 'obtenerPorId'");
    }

    @Override
    public List<Prestamos> obtenerPorClienteId(int clienteId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerPorClienteId'");
    }

    @Override
    public void cambiarEstado(int id, String nuevoEstado) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cambiarEstado'");
    }

    @Override
    public void actualizarSaldo(int id, double nuevoSaldo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizarSaldo'");
    }

}
