package com.crediya.clientes;

public class Cliente {
    private int id;
    private String nombre;
    private int documento;
    private String correo;
    private String telefono;

    public Cliente(int id, String nombre, int documento, String correo, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.documento = documento;
        this.correo = correo;
        this.telefono = telefono;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

 @Override
  public String toString() {
      return "Clientes " +"id='" + id + ", nombre='" + nombre +", documento='" + documento +", correo='" + correo +", telefono='" + telefono ;
  }
}

