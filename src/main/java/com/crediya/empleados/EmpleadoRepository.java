package com.crediya.empleados;

import java.util.List;

public interface EmpleadoRepository {
    Empleado agregar(Empleado empleado);
    List<Empleado> listar();
    Empleado obtenerPorId(int id);
    void actualizar(Empleado empleado);
    void eliminar(int id);
}
