package com.crediya.pagos;

public class Pagos {
    //Atributos
    private int id;
    private int prestamo_id;
    private String fecha_pago;
    private double monto;

    //Constructor
    public Pagos (int id, int prestamo_id, String fecha_pago, double monto) {
        this.id = id;
        this.prestamo_id = prestamo_id;
        this.fecha_pago = fecha_pago;
        this.monto = monto;
    }

    //Setters
    public void setPagosId(int id){
        this.id = id;
    }
    public void setPagosPrestamo_id(int prestamo_id){
        this.prestamo_id = prestamo_id;
    }
    public void setPagosFecha_pago(String fecha_pago){
        this.fecha_pago = fecha_pago;
    }
    public void setPagosMonto(double monto){
        this.monto = monto;
    }
    //Getters
    public int getPagosId(){
        return this.id;
    }
    public int getPagosPrestamo_id(){
        return this.prestamo_id;
    }
    public String getPagosFecha_pago(){
        return this.fecha_pago;
    }
    public double getPagosMonto(){
        return this.monto;
    }
    //ToString
    @Override
    public String toString(){
        return "Pagos " +
        " id = " + id +
        " Prestamo_id " + prestamo_id + 
        " Fecha del Pago " + fecha_pago +
        " Monto " + monto;
    }
    /*
        public static void main(String[] args) {
        Pagos pago = new Pagos(0, 0, "2deMayo", 299.000);
        System.out.println(pago.toString());
    }
    */

}
