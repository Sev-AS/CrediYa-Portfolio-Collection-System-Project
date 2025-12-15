package com.crediya.pagos;

import java.util.List;

public interface PagosRepository {
    Pagos registrar(Pagos pago);

    List<Pagos> listarPorPrestamo(int prestamoId);
    
    Pagos registrarPagoConActualizacionSaldo(Pagos pago, double nuevoSaldo, int prestamoId);
}