package com.crediya.prestamos;

public class Prestamos {
    private int id;
    private int clienteId;
    private int empleadoId;
    private double monto;
    private double interesMensual;
    private int cuotas;
    private String fechaInicio;
    private String estado;
    private double saldoPendiente;

    private double montoTotal;
    private double cuotaMensual;

    public Prestamos(int id, int clienteId, int empleadoId, double monto, double interesMensual, int cuotas,
            String fechaInicio, String estado) {
        this.id = id;
        this.clienteId = clienteId;
        this.empleadoId = empleadoId;
        this.monto = monto;
        this.interesMensual = interesMensual;
        this.cuotas = cuotas;
        this.fechaInicio = fechaInicio;
        this.estado = estado;

        calcularValoresPrestamo();
        this.saldoPendiente = this.montoTotal;
    }

    public Prestamos(int id, int clienteId, int empleadoId, double monto, double interesMensual, int cuotas,
            String fechaInicio, String estado, double saldoPendiente) {
        this(id, clienteId, empleadoId, monto, interesMensual, cuotas, fechaInicio, estado);
        this.saldoPendiente = saldoPendiente;
    }

    public void calcularValoresPrestamo() {
        if (this.monto <= 0 || this.cuotas <= 0 || this.interesMensual < 0) {
            throw new IllegalArgumentException(
                "El monto y las cuotas deben ser mayores a 0, y el interes no puede ser negativo.");
        }
        
        double interesTotal = this.monto * this.interesMensual * this.cuotas;
        
        this.montoTotal = this.monto + interesTotal;
        
        this.cuotaMensual = this.montoTotal / this.cuotas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getInteresMensual() {
        return interesMensual;
    }

    public void setInteresMensual(double interesMensual) {
        this.interesMensual = interesMensual;
    }

    public int getCuotas() {
        return cuotas;
    }

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public double getCuotaMensual() {
        return cuotaMensual;
    }

    public double getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(double saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    @Override
    public String toString() {
        return "Prestamo{"
                + "id=" + id + ", "
                + "clienteId=" + clienteId + ", "
                + "empleadoId=" + empleadoId + ", "
                + "monto=" + monto + ", "
                + "interesMensual=" + interesMensual + ", "
                + "cuotas=" + cuotas + ", "
                + "fechaInicio='" + fechaInicio + "'" + ", "
                + "estado='" + estado + "'" + ", "
                + "montoTotal=" + montoTotal + ", "
                + "cuotaMensual=" + cuotaMensual + ", "
                + "saldoPendiente=" + saldoPendiente + 
                '}';
    }
}