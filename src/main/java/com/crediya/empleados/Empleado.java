package com.crediya.empleados;

import com.crediya.common.Persona;

public class Empleado extends Persona {
    private String rol;
    private double salario;

    public Empleado(int id, String nombre, int documento, String rol, String correo, double salario) {
        super(id, nombre, documento, correo);
        this.rol = rol;
        this.salario = salario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public String getTipoPersona() {
        return "Empleado - " + rol;
    }

    @Override
    public String toString() {
        return "Empleado [id=" + getId() + ", nombre=" + getNombre() + ", documento=" + getDocumento() + ", rol=" + rol + ", correo="
                + getCorreo() + ", salario=" + salario + "]";
    }
}