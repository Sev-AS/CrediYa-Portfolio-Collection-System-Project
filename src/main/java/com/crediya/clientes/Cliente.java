package com.crediya.clientes;

import com.crediya.common.Persona;

public class Cliente extends Persona {
    private String telefono;

    public Cliente(int id, String nombre, int documento, String correo, String telefono) {
        super(id, nombre, documento, correo);
        this.telefono = telefono;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String getTipoPersona() {
        return "Cliente";
    }

    @Override
    public String toString() {
        return "Cliente [id=" + getId() + ", nombre=" + getNombre() + ", documento=" + getDocumento() + ", correo=" + getCorreo()
                + ", telefono=" + telefono + "]";
    }
}

