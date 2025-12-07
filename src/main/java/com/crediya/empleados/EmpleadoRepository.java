package com.crediya.empleados;

import java.util.ArrayList;
import java.util.List;

public class EmpleadoRepository {

private List<Empleado> empleados = new ArrayList<>();

public void agregar(Empleado empleado){
    empleados.add(empleado);
}

public List<Empleado> listar(){
    return empleados;
}

}
