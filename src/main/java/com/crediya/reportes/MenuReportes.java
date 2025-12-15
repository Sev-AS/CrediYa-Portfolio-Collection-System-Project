package com.crediya.reportes;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import com.crediya.clientes.Cliente;
import com.crediya.clientes.ClienteRepository;
import com.crediya.prestamos.PrestamoRepository;
import com.crediya.prestamos.Prestamos;

public class MenuReportes {
    private final PrestamoRepository prestamoRepository;
    private final ClienteRepository clienteRepository;
    private final Scanner consola;

    public MenuReportes(PrestamoRepository prestamoRepository, ClienteRepository clienteRepository) {
        this.prestamoRepository = prestamoRepository;
        this.clienteRepository = clienteRepository;
        this.consola = new Scanner(System.in);
    }

    public void iniciarMenu() {
        int opcion = 0;
        do {
            try {
                System.out.println(
                        """
                                                    
▄▄▄▄▄▄▄                                             
███▀▀███▄                          ██               
███▄▄███▀ ▄█▀█▄ ████▄ ▄███▄ ████▄ ▀██▀▀ ▄█▀█▄ ▄█▀▀▀ 
███▀▀██▄  ██▄█▀ ██ ██ ██ ██ ██ ▀▀  ██   ██▄█▀ ▀███▄ 
███  ▀███ ▀█▄▄▄ ████▀ ▀███▀ ██     ██   ▀█▄▄▄ ▄▄▄█▀ 
                ██                                  
                ▀▀                                  
                                1. Listar Prestamos Activos
                                2. Listar Prestamos Vencidos
                                3. Listar Clientes Morosos
                                4. Salir de menu de reportes
                                   """);

                opcion = consola.nextInt();
                consola.nextLine();

                switch (opcion) {
                    case 1 -> mrListarPrestamosActivos();
                    case 2 -> mrListarPrestamosVencidos();
                    case 3 -> mrListarClientesMorosos();
                    case 4 -> System.out.println("Saliendo del menu de reportes...");
                    default -> System.out.println("Opcion no valida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Por favor, introduzca un numero valido.");
                consola.nextLine();
                opcion = 0;
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
                opcion = 0;
            }
        } while (opcion != 4);
    }

    private void mrListarPrestamosActivos() {
        System.out.println("Prestamos Activos");
        try {
            List<Prestamos> todosLosPrestamos = prestamoRepository.listar();
            List<Prestamos> prestamosActivos = ReporteManager.filtrarPrestamosActivos(todosLosPrestamos);
            
            if (prestamosActivos.isEmpty()) {
                System.out.println("No hay prestamos activos registrados.");
            } else {
                System.out.println("Total de prestamos activos: " + prestamosActivos.size());
                prestamosActivos.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener prestamos activos: " + e.getMessage());
        }
    }

    private void mrListarPrestamosVencidos() {
        System.out.println("Prestamos Vencidos");
        try {
            List<Prestamos> todosLosPrestamos = prestamoRepository.listar();
            List<Prestamos> prestamosVencidos = ReporteManager.filtrarPrestamosVencidos(todosLosPrestamos);
            
            if (prestamosVencidos.isEmpty()) {
                System.out.println("No hay prestamos vencidos registrados.");
            } else {
                System.out.println("Total de prestamos vencidos: " + prestamosVencidos.size());
                prestamosVencidos.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener prestamos vencidos: " + e.getMessage());
        }
    }

    private void mrListarClientesMorosos() {
        System.out.println("Clientes Morosos");
        try {
            List<Cliente> todosLosClientes = clienteRepository.listar();
            List<Prestamos> todosLosPrestamos = prestamoRepository.listar();
            List<Cliente> clientesMorosos = ReporteManager.filtrarClientesMorosos(todosLosClientes, todosLosPrestamos);
            
            if (clientesMorosos.isEmpty()) {
                System.out.println("No hay clientes morosos registrados.");
            } else {
                System.out.println("Total de clientes morosos: " + clientesMorosos.size());
                clientesMorosos.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener clientes morosos: " + e.getMessage());
        }
    }
}