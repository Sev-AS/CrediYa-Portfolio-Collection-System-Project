package com.crediya.clientes;

import java.util.List;

public interface ClienteRepository {
    Cliente agregar(Cliente cliente);
    List<Cliente> listar();
    Cliente obtenerPorDocumento(int documento);
    void actualizar(Cliente cliente);
    void eliminar(int documento);
}
