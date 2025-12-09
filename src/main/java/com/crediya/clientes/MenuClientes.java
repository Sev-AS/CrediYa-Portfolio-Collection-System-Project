package com.crediya.clientes;

import java.util.Scanner;



public class MenuClientes {
    private int opcion;
    Scanner consola = new Scanner(System.in);
    GestorClientes gestorClientes = new GestorClientes();

    public MenuClientes (){   // ← Constructor corregido
        gestorClientes.cargarClientes();
        do {

        System.out.println(
                """
        ▄▄▄▄▄▄▄ ▄▄                                   
        ███▀▀▀▀▀ ██ ▀▀               ██               
        ███      ██ ██  ▄█▀█▄ ████▄ ▀██▀▀ ▄█▀█▄ ▄█▀▀▀ 
        ███      ██ ██  ██▄█▀ ██ ██  ██   ██▄█▀ ▀███▄ 
        ▀███████ ██ ██▄ ▀█▄▄▄ ██ ██  ██   ▀█▄▄▄ ▄▄▄█▀ 

            -- 1 -- Inscribir un cliente       
            -- 2 -- ver lista de clientes      
            -- 3 -- Buscar cliente por nombre  
            -- 4 -- Eliminar un cliente        
            -- 5 -- Actualizar un cliente      
            -- 6 -- Salir de menú de cliente   
                """
       );

       opcion = consola.nextInt();

       switch (opcion) {
        case 1:
            MCagregarCliente();
            gestorClientes.guardarClientes();
            break;
        case 2:
            gestorClientes.listadetodoslosCliente();
            break;
        case 3:
            MCgetEspecifiCliente();
            break;
        case 4:
            MCdeleteEspecifiCliente();
            break;
        case 5:
            MCactualizarCliente();
            break;
        case 6:
            break;
       }

      } while (opcion != 6);
    }

    public void MCagregarCliente(){
        System.out.println("id");
        int id = consola.nextInt();
        consola.nextLine(); // LIMPIAR BUFFER

        System.out.println("Dame el nombre del cliente");
        String nombre = consola.nextLine();

        System.out.println("Dame el documento del cliente");
        int documento = consola.nextInt();
        consola.nextLine(); // LIMPIAR BUFFER

        System.out.println("Dame el correo del cliente");
        String correo = consola.nextLine();

        System.out.println("Dame el telefono del cliente");
        String telefono = consola.nextLine();

        Clientes nuevo = new Clientes(id, nombre, documento, correo, telefono);
        gestorClientes.agregarCliente(nuevo);
    }

    /* IMPORTANTE CAMBIAR, EL BUSSCADOR POR NOMBRE ESTA POR NOMBRE Y NO POR ID */
    public void MCgetEspecifiCliente(){
        consola.nextLine(); 
        System.out.println("Dame el nombre del cliente que buscas ");
        String MCnombre = consola.nextLine();

        gestorClientes.getEspecifiCliente(MCnombre);
    }


    public void MCdeleteEspecifiCliente(){
        consola.nextLine(); 
        System.out.println("Dame el nombre del cliente que quieres eliminar ");
        String MCnombre = consola.nextLine();

        gestorClientes.deleteEspecifiCliente(MCnombre);
    }


    public void MCactualizarCliente(){
        consola.nextLine(); 
        System.out.println("Dame el nombre del cliente que quieres actualizar");
        String actualizarCliente = consola.nextLine();

        System.out.println("Ahora dame los datos que quieres actualizar de esa persona");
        System.out.println("id");
        int id = consola.nextInt();

        consola.nextLine(); 
        System.out.println("Dame el nombre del cliente");
        String nombre = consola.nextLine();

        System.out.println("Dame el documento del cliente");
        int documento = consola.nextInt();

        consola.nextLine(); 
        System.out.println("Dame el correo del cliente");
        String correo = consola.nextLine();

        System.out.println("Dame el telefono del cliente");
        String telefono = consola.nextLine();

        Clientes actualizador = new Clientes(id, nombre, documento, correo, telefono);

        gestorClientes.actualizarCliente(actualizarCliente, actualizador);
    }

    public static void main(String[] args) {

        MenuClientes menuClientes = new MenuClientes();  // ← Correcto
        System.out.println(menuClientes);
    }
}
