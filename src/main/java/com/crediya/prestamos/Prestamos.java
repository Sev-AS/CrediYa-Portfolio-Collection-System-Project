package com.crediya.prestamos;

public class Prestamos {
    //Examen
    private int id; //---
    private int clienteId; //---
    private double monto;//---
    private double interesMensual; //---
    private int plazoMeses;  //---
    private String estado;  //---
    //----------------------------------//
    private int empleadoId;
    private int cuotas;
    private String fechaInicio;
    private double saldoPendiente;

    private double montoTotal;
    private double cuotaMensual;

    public Prestamos(int id, int clienteId, int plazoMeses,int empleadoId, double monto, double interesMensual, int cuotas,
            String fechaInicio, String estado) {
        this.id = id;
        this.clienteId = clienteId;
        this.empleadoId = empleadoId;
        this.monto = monto;
        this.interesMensual = interesMensual;
        this.cuotas = cuotas;
        this.fechaInicio = fechaInicio;
        this.estado = estado;
        this.plazoMeses = plazoMeses;

        calcularValoresPrestamo();
        this.saldoPendiente = this.montoTotal;
    }

    public Prestamos(int id, int clienteId, int empleadoId, int plazoMeses, double monto, double interesMensual, int cuotas,
            String fechaInicio, String estado, double saldoPendiente) {
        this(id, clienteId, empleadoId, plazoMeses, monto, interesMensual, cuotas, fechaInicio, estado);
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

    // Examen
    
    public double calcularTotalAPagar(){
        if (this.monto <= 0 || this.cuotas <= 0 || this.interesMensual < 0) {
            throw new IllegalArgumentException(
                "El monto y las cuotas deben ser mayores a 0, y el interes no puede ser negativo.");
        }
        
        double interesTotal = this.monto * this.interesMensual * this.cuotas;
        
        this.montoTotal = this.monto + interesTotal;
        
        double totalAPagar = this.montoTotal * this.cuotas;

        return totalAPagar;
    }


    public int getPlazoMeses() {
        return plazoMeses;
    }

    public void setPlazoMeses(int plazoMeses) {
        this.plazoMeses = plazoMeses;
    }
//-------------------------------------------------//
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

    //Examen
    @Override
    public String toString() {
        return   "Prestamos [id=" + id + 
                 ", clienteId=" + clienteId +
                 ", monto=" + monto + 
                 ", interesMensual=" + interesMensual + 
                 ", plazoMeses=" + plazoMeses + 
                 ", estado=" + estado + 
                 ", empleadoId=" + empleadoId + 
                 ", cuotas=" + cuotas + 
                 ", fechaInicio=" + fechaInicio + 
                 ", saldoPendiente=" + saldoPendiente + 
                 ", montoTotal=" + montoTotal + 
                 ", cuotaMensual=" + cuotaMensual + 
                 ", calcularTotalAPagar()=" + calcularTotalAPagar() + "]";
    }
    //---------------------------------------------------------
    
}