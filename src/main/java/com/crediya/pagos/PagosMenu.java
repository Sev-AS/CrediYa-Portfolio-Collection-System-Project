package com.crediya.pagos;

import java.util.Scanner;

public class PagosMenu {
    private int opcion;
    Scanner consola = new Scanner(System.in);
    PagosGestor pg = new PagosGestor();
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PagosMenu(){
        pg.cargarPagos();

        do { 
            System.out.println(
                """

            ▄▄▄▄▄▄▄                          
            ███▀▀███▄                        
            ███▄▄███▀ ▀▀█▄ ▄████ ▄███▄ ▄█▀▀▀ 
            ███▀▀▀▀  ▄█▀██ ██ ██ ██ ██ ▀███▄ 
            ███      ▀█▄██ ▀████ ▀███▀ ▄▄▄█▀ 
                            ██             
                            ▀▀▀          

            -- 1 -- Agregar pago       
            -- 2 -- Ver mis pagos     
            -- 3 -- Calcular mi Saldo
            -- 4 -- Salir         
                """
       );

        opcion = consola.nextInt();

        switch (opcion){
            case 1: MCAgregarPago(); pg.guardarPago();
            break;
            case 2: pg.listaDeTodosLosPagos();
            break;
        }

        } while(opcion != 4);
    }

    public void MCAgregarPago(){

        System.out.println("Dame el id");
        int id = consola.nextInt();

        System.out.println("Dame el id del prestamo");
        int prestamo_id = consola.nextInt();

        consola.nextLine();

        System.out.println("Fecha de pago");
        String fecha_pago = consola.nextLine();

        System.out.println("Monto");
        double monto = consola.nextDouble();

        Pagos pago = new Pagos(id, prestamo_id, fecha_pago, monto);
        pg.agregarPago(pago);
    }

    public static void main(String[] args) {
        PagosMenu pm = new PagosMenu();

        System.out.println(pm);
    }
}
