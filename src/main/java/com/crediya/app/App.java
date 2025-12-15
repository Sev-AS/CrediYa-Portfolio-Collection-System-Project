package com.crediya.app;

import java.util.InputMismatchException;
import java.util.Scanner;
import com.crediya.clientes.ClienteRepository;
import com.crediya.clientes.ClienteRepositoryArchivo;
import com.crediya.clientes.ClienteRepositoryJdbc;
import com.crediya.clientes.MenuClientes;
import com.crediya.common.DatabaseConnection;
import com.crediya.empleados.EmpleadoRepository;
import com.crediya.empleados.EmpleadoRepositoryArchivo;
import com.crediya.empleados.EmpleadoRepositoryJdbc;
import com.crediya.empleados.MenuEmpleados;
import com.crediya.pagos.MenuPagos;
import com.crediya.pagos.PagosRepository;
import com.crediya.pagos.PagosRepositoryArchivo;
import com.crediya.pagos.PagosRepositoryJdbc;
import com.crediya.prestamos.MenuPrestamos;
import com.crediya.prestamos.PrestamoRepository;
import com.crediya.prestamos.PrestamoRepositoryArchivo;
import com.crediya.prestamos.PrestamoRepositoryJdbc;
import com.crediya.reportes.MenuReportes;

public class App {
    private static final Scanner scanner = new Scanner(System.in);
    
    private static final boolean USAR_JDBC = true;
    
    public static void main(String[] args) {
        System.out.println("""
                                                              
 ▄▄▄▄▄▄▄                ▄▄     ▄▄▄   ▄▄▄      
███▀▀▀▀▀                ██ ▀▀  ███   ███      
███      ████▄ ▄█▀█▄ ▄████ ██  ▀███▄███▀ ▀▀█▄ 
███      ██ ▀▀ ██▄█▀ ██ ██ ██    ▀███▀  ▄█▀██ 
▀███████ ██    ▀█▄▄▄ ▀████ ██▄    ███   ▀█▄██ 
                                              
                                              
        """);
        
        ClienteRepository clienteRepository;
        EmpleadoRepository empleadoRepository;
        PrestamoRepository prestamoRepository;
        PagosRepository pagosRepository;
        DatabaseConnection dbConnection = null;
        
        if (USAR_JDBC) {
            try {
                dbConnection = new DatabaseConnection();
                clienteRepository = new ClienteRepositoryJdbc(dbConnection);
                empleadoRepository = new EmpleadoRepositoryJdbc(dbConnection);
                prestamoRepository = new PrestamoRepositoryJdbc(dbConnection);
                pagosRepository = new PagosRepositoryJdbc(dbConnection);
                System.out.println(" Modo de persistencia: Base de Datos (JDBC)");
            } catch (Exception e) {
                System.err.println("Error al conectar con la base de datos. Cambiando a modo archivo.");
                System.err.println("Error: " + e.getMessage());
                clienteRepository = new ClienteRepositoryArchivo();
                empleadoRepository = new EmpleadoRepositoryArchivo();
                prestamoRepository = new PrestamoRepositoryArchivo();
                pagosRepository = new PagosRepositoryArchivo();
                System.out.println(" Modo de persistencia: Archivos de texto");
            }
        } else {
            clienteRepository = new ClienteRepositoryArchivo();
            empleadoRepository = new EmpleadoRepositoryArchivo();
            prestamoRepository = new PrestamoRepositoryArchivo();
            pagosRepository = new PagosRepositoryArchivo();
            System.out.println(" Modo de persistencia: Archivos de texto");
        }
        
        MenuClientes menuClientes = new MenuClientes(clienteRepository, prestamoRepository);
        MenuEmpleados menuEmpleados = new MenuEmpleados(empleadoRepository);
        MenuPrestamos menuPrestamos = new MenuPrestamos(prestamoRepository, clienteRepository, empleadoRepository);
        MenuPagos menuPagos = new MenuPagos(pagosRepository, prestamoRepository);
        MenuReportes menuReportes = new MenuReportes(prestamoRepository, clienteRepository);
        
        int opcion = 0;
        do {
            try {
                System.out.println("\n"
                        
                                    + """                                                                                        
▄▄▄      ▄▄▄                     ▄▄▄▄▄▄▄                                         ▄▄ 
████▄  ▄████                     ███▀▀███▄       ▀▀              ▀▀              ██ 
███▀████▀███ ▄█▀█▄ ████▄ ██ ██   ███▄▄███▀ ████▄ ██  ████▄ ▄████ ██  ████▄  ▀▀█▄ ██ 
███  ▀▀  ███ ██▄█▀ ██ ██ ██ ██   ███▀▀▀▀   ██ ▀▀ ██  ██ ██ ██    ██  ██ ██ ▄█▀██ ██ 
███      ███ ▀█▄▄▄ ██ ██ ▀██▀█   ███       ██    ██▄ ██ ██ ▀████ ██▄ ████▀ ▀█▄██ ██ 
                                                                     ██             
                                                                     ▀▀             \n"""
                                                                             
                                                                             
                        + "1. Gestion de Clientes\n"
                        + "2. Gestion de Empleados\n"
                        + "3. Gestion de Prestamos\n"
                        + "4. Gestion de Pagos\n"
                        + "5. Reportes\n"
                        + "6. Salir\n");
                System.out.print("Seleccione una opcion: ");
                opcion = scanner.nextInt();
                scanner.nextLine();
                
                switch (opcion) {
                    case 1 -> menuClientes.iniciarMenu();
                    case 2 -> menuEmpleados.iniciarMenu();
                    case 3 -> menuPrestamos.iniciarMenu();
                    case 4 -> menuPagos.iniciarMenu();
                    case 5 -> menuReportes.iniciarMenu();
                    case 6 -> System.out.println("\nHasta luego!");
                    default -> System.out.println("Por favor seleccione una opcion del 1 al 6.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Introduzca un numero valido.");
                scanner.nextLine();
                opcion = 0;
            } catch (Exception e) {
                System.err.println("Error : " + e.getMessage());
                e.printStackTrace();
                opcion = 0;
            }
        } while (opcion != 6);
        
        scanner.close();
    }
}