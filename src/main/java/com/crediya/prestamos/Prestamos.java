package com.crediya.prestamos;

public class Prestamos {
    //Atributos
    private int id;
    private int cliente_id;
    private int empleado_id;
    private double monto;
    private double interes;
    private int cuotas;
    private String fechaInicio;
    private String estado; 

    //Contructor
    public Prestamos (int id, int cliente_id, int empleado_id, double monto, double interes, int cuotas, String fechaInicio, String estado){
        this.id = id;
        this.cliente_id = cliente_id;
        this.empleado_id = empleado_id;
        this.monto = monto;
        this.interes = interes;
        this.cuotas = cuotas;
        this.fechaInicio = fechaInicio;
        this.estado = estado;
    }

    //Setters

    public void setP_id (int id){
        this.id = id;        
    }
    public void setP_cliente_id (int cliente_id){
        this.cliente_id = cliente_id;
    }
    public void setP_empleado_id (int empleado_id){
        this.empleado_id = empleado_id;
    }
    public void setP_monto (double monto){
        this.monto = monto;
    }
    public void setP_interes (double interes){
        this.interes = interes;
    }
    public void setP_cuotas (int cuotas){
        this.cuotas = cuotas;
    }
    public void setP_fechaInicio (String fechaInicio){
        this.fechaInicio = fechaInicio;
    }
    public void setP_estado (String estado){
        this.estado = estado;
    }

    //Getters

    public int getP_id () {
        return this.id;
    }
    public int getP_cliente_id () {
        return this.cliente_id;
    }
    public int getP_empleado_id () {
        return this.empleado_id;
    }
    public double getP_monto () {
        return this.monto;
    }
    public double getP_interes () {
        return this.interes;
    }
    public int getP_cuotas () {
        return this.cuotas;
    }
    public String getP_fechaInicio () {
        return this.fechaInicio;
    }
    public String getP_estado () {
        return this.estado;
    }

    @Override
    public String toString (){
        return "Prestamo " + "id = " + id + " cliente_id = "+ cliente_id + " empleado_id = " + empleado_id + " monto = " + monto + " interes = " + interes + " cuotas = " + cuotas + " fechaInicio = " + fechaInicio + " estado = " + estado;
    }
    /*
        public static void main(String[] args) {
        Prestamos p = new Prestamos(1, 1, 1, 2000, 0.8, 4, "2-Mayo", "Debe");

        System.out.println(p.toString());
        System.out.println(p.getP_fechaInicio());
    }
    */

}
