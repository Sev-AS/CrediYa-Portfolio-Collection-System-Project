package com.crediya.empleados;

public class Empleado {
        private int id;
    private String nombre;
    private int documento;
    private String rol;
    private String correo;
    private double salario;

    public Empleado(int id, String nombre, int documento, String rol, String correo, double salario) {
        this.id = id;
        this.nombre = nombre;
        this.documento = documento;
        this.rol = rol;
        this.correo = correo;
        this.salario = salario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public double getSalario() {
        return salario;
    }

    @Override
    public String toString() {
        return "Empleado [id=" + id + ", nombre=" + nombre + ", documento=" + documento + ", rol=" + rol + ", correo="
                + correo + ", salario=" + salario + "]";
    }

}