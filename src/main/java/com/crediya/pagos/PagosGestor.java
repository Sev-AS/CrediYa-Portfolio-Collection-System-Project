package com.crediya.pagos;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PagosGestor {
    @SuppressWarnings("FieldMayBeFinal")
    //Atributos
    private List<Pagos> pagoslist;
    //Constructor PagosGestor
    public PagosGestor(){
        this.pagoslist = new ArrayList<>();
    }
    //Agregacion de pago
    public void agregarPago(Pagos pago){
        pagoslist.add(pago);
    }
    //Lista de todos los pagos
    public void listaDeTodosLosPagos(){
        if (pagoslist.isEmpty()){
            System.out.println("No hay registrado ningun pago");
        } else {
            for (Pagos p : pagoslist){
                System.out.println(p);
            }
        }
    }
    //Eliminar un pago en especifico
    public Pagos deletePago(int id){
        for (int i= 0; i < pagoslist.size(); i++){
            if (pagoslist.get(i).getPagosId() == id) {
                Pagos pagoEliminado = pagoslist.remove(i);
                System.out.println("Cliente eliminado");
                return pagoEliminado;
            }
        }
        return null;
    }

    //Actualizar un pago en especifico
    public boolean actualizarPago(int id, Pagos actualizadorPagos){
       for (Pagos p : pagoslist){
        if(id == p.getPagosId()){
            p.setPagosFecha_pago(actualizadorPagos.getPagosFecha_pago());
            p.setPagosMonto(actualizadorPagos.getPagosMonto());
            return true;
         }
       }
       System.out.println("No se encontro el id del pago que introdujo");
       return false;
    }
    //FALTARIA CALCULAR EL SALDO PENDIENTE
    public void calcularSaldo(){

    }

    //Persistencia mediante archivo txt
    public void cargarPagos(){
        pagoslist.clear();

        File archivo = new File("Pagos.txt");
        if(!archivo.exists()){
            System.out.println("No existe el archivo");
        }

        try (Scanner lectorArchivos = new Scanner(archivo)){
            while(lectorArchivos.hasNextLine()){
                String linea = lectorArchivos.nextLine();
                String[] partes = linea.split(";");

                if(partes.length == 4){
                    Pagos p = new Pagos(
                        Integer.parseInt(partes[0]),
                        Integer.parseInt(partes[1]),
                        partes[2],
                        Double.parseDouble(partes[3])
                    );
                    pagoslist.add(p);
                }
            }
            System.out.println("Pagos cargados correctamente");
        }catch (Exception e){
            System.out.println("No se ha podido leer el archivo" + e.getMessage());
        }
    }

    //Guardar los pagos
    public void guardarPago(){
        try(PrintWriter escritor = new PrintWriter("Pagos.txt")){
            for (Pagos p : pagoslist){
                escritor.println(
                    p.getPagosId() + ";" +
                    p.getPagosPrestamo_id() + ";" +
                    p.getPagosFecha_pago() + ";" +
                    p.getPagosMonto()
                );
            }
            System.out.println("Pagos registrados correctamente ");
        }
        catch (Exception e){
            System.out.println("Error al guardar los pagos" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        //Creacion de objetos
        PagosGestor gClientes = new PagosGestor();
        Pagos pago = new Pagos(1, 1, "2mayo", 2000);
        Pagos actualizadorPagos = new Pagos(2, 2, "3Mayo", 3000);
        //Uso de metodos
        gClientes.agregarPago(pago);
        //
        gClientes.listaDeTodosLosPagos();
        // gClientes.deletePago(1);
        gClientes.actualizarPago(1, actualizadorPagos);
        gClientes.listaDeTodosLosPagos();
        gClientes.guardarPago();
        gClientes.cargarPagos();
    }
}