package com.crediya.common;

public abstract class Persona {
    protected int id;
    protected String nombre;
    protected int documento;
    protected String correo;
    
    public Persona(int id, String nombre, int documento, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.documento = documento;
        this.correo = correo;
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
    
    public abstract String getTipoPersona();
}

