package com.crediya.pagos;

public class Pagos {
    private int id;
    private int prestamoId;
    private double monto;
    private String fecha;

    public Pagos(int id, int prestamoId, double monto, String fecha) {
        this.id = id;
        this.prestamoId = prestamoId;
        this.monto = monto;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(int prestamoId) {
        this.prestamoId = prestamoId;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "id=" + id +
                ", prestamoId=" + prestamoId +
                ", monto=" + monto +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
