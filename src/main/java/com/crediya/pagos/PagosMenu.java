package com.crediya.pagos;

public class PagosMenu {
    private int opcion;

    public PagosMenu(){
        
    }

    public void iniciarMenuClient(){
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


        } while (!opcion == 4);
    }
}
