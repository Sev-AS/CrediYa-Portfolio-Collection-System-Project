package com.crediya.clientes;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import com.crediya.prestamos.Prestamos;
import com.crediya.prestamos.PrestamoRepository;

public class MenuClientes {
    private final ClienteRepository clienteRepository;
    private final PrestamoRepository prestamoRepository;
    private final Scanner consola;

    public MenuClientes(ClienteRepository clienteRepository, PrestamoRepository prestamoRepository) {
        this.clienteRepository = clienteRepository;
        this.prestamoRepository = prestamoRepository;
        this.consola = new Scanner(System.in);
    }

    public void iniciarMenu() {
        int opcion = 0;
        do {
            try {
                System.out.println(
                        """

                                                                              
                            ▄▄▄▄▄▄▄ ▄▄                                   
                            ███▀▀▀▀▀ ██ ▀▀               ██               
                            ███      ██ ██  ▄█▀█▄ ████▄ ▀██▀▀ ▄█▀█▄ ▄█▀▀▀ 
                            ███      ██ ██  ██▄█▀ ██ ██  ██   ██▄█▀ ▀███▄ 
                            ▀███████ ██ ██▄ ▀█▄▄▄ ██ ██  ██   ▀█▄▄▄ ▄▄▄█▀ 
                                              
                                              
                                    1. Inscribir un cliente
                                    2. Ver lista de clientes
                                    3. Buscar cliente por documento
                                    4. Eliminar un cliente
                                    5. Actualizar un cliente
                                    6. Consultar prestamos del cliente
                                    7. Salir de menu de cliente
""");

                opcion = consola.nextInt();
                consola.nextLine();

                switch (opcion) {
                    case 1 -> mcAgregarCliente();
                    case 2 -> mcListarClientes();
                    case 3 -> mcBuscarCliente();
                    case 4 -> mcEliminarCliente();
                    case 5 -> mcActualizarCliente();
                    case 6 -> mcConsultarPrestamos();
                    case 7 -> System.out.println("Saliendo del menu de clientes...");
                    default -> System.out.println("Opcion no valida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Introduzca un numero valido.");
                consola.nextLine();
                opcion = 0;
            }
        } while (opcion != 7);
    }

    private void mcAgregarCliente() {
        System.out.println("Inscribir Nuevo Cliente ");
        try {
            Cliente nuevoCliente = solicitarDatosCliente(0);
            
            // Validar que el documento no exista ya
            if (clienteRepository.obtenerPorDocumento(nuevoCliente.getDocumento()) != null) {
                System.out.println("Ya existe un cliente con ese documento.");
                return;
            }
            
            Cliente clienteGuardado = clienteRepository.agregar(nuevoCliente);
            if (clienteGuardado != null) {
                System.out.println("Cliente inscrito con ID: " + clienteGuardado.getId());
                System.out.println(clienteGuardado);
            } else {
                System.out.println("No se pudo guardar el cliente.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error : " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al agregar cliente: " + e.getMessage());
        }
    }

    private void mcListarClientes() {
        System.out.println("Lista de Clientes");
        List<Cliente> clientes = clienteRepository.listar();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            clientes.forEach(System.out::println);
        }
    }

    private void mcBuscarCliente() {
        System.out.println("Buscar Cliente por Documento");
        System.out.print("Ingrese el documento del cliente a buscar: ");
        try {
            int documento = consola.nextInt();
            consola.nextLine();
            Cliente cliente = clienteRepository.obtenerPorDocumento(documento);
            if (cliente != null) {
                System.out.println("Cliente encontrado:");
                System.out.println(cliente);
            } else {
                System.out.println("No se encontro un cliente con el documento: " + documento);
            }
        } catch (InputMismatchException e) {
            System.out.println("Documento invalido.");
            consola.nextLine();
        }
    }

    private void mcEliminarCliente() {
        System.out.println("Eliminar Cliente");
        System.out.print("Ingrese el documento del cliente a eliminar: ");
        try {
            int documento = consola.nextInt();
            consola.nextLine();
            if (clienteRepository.obtenerPorDocumento(documento) != null) {
                clienteRepository.eliminar(documento);
                System.out.println("Cliente con documento " + documento + " ha sido eliminado.");
            } else {
                System.out.println("No se encontro un cliente con el documento: " + documento);
            }
        } catch (InputMismatchException e) {
            System.out.println("Documento invalido.");
            consola.nextLine();
        }
    }

    private void mcActualizarCliente() {
        System.out.println("Actualizar Cliente");
        System.out.print("Dame el documento del cliente a actualizar: ");
        try {
            int documento = consola.nextInt();
            consola.nextLine();

            Cliente clienteExistente = clienteRepository.obtenerPorDocumento(documento);
            if (clienteExistente == null) {
                System.out.println("No se encontro un cliente con el documento: " + documento);
                return;
            }

            System.out.println("Introduzca los nuevos datos para el cliente con documento: " + documento);
            Cliente datosNuevos = solicitarDatosCliente(documento);
            datosNuevos.setId(clienteExistente.getId());

            clienteRepository.actualizar(datosNuevos);
            System.out.println("Cliente con documento " + documento + " ha sido actualizado.");
        } catch (InputMismatchException e) {
            System.out.println("Documento invalido.");
            consola.nextLine();
        }
    }

    private Cliente solicitarDatosCliente(int documento) {
        System.out.print("Nombre: ");
        String nombre = consola.nextLine().trim();
        if (nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }

        int doc = documento;
        if (doc == 0) {
            while (doc <= 0) {
                try {
                    System.out.print("Documento: ");
                    doc = consola.nextInt();
                    if (doc <= 0) {
                        System.out.println("El documento debe ser un numero positivo.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Introduce un numero entero positivo.");
                } finally {
                    consola.nextLine();
                }
            }
        }

        System.out.print("Correo: ");
        String correo = consola.nextLine().trim();

        System.out.print("Telefono: ");
        String telefono = consola.nextLine().trim();

        return new Cliente(0, nombre, doc, correo, telefono);
    }

    private void mcConsultarPrestamos() {
        System.out.println("Consultar Prestamos del Cliente");
        System.out.print("Dame el documento del cliente: ");
        try {
            int documento = consola.nextInt();
            consola.nextLine();
            
            Cliente cliente = clienteRepository.obtenerPorDocumento(documento);
            if (cliente == null) {
                System.out.println("No se encontro un cliente con el documento: " + documento);
                return;
            }
            
            List<Prestamos> prestamos = prestamoRepository.obtenerPorClienteId(cliente.getId());
            
            if (prestamos.isEmpty()) {
                System.out.println("El cliente " + cliente.getNombre() + " no tiene prestamos registrados.");
            } else {
                System.out.println("\nPrestamos del cliente " + cliente.getNombre() + ":");
                System.out.println("=".repeat(70));
                prestamos.forEach(System.out::println);
                System.out.println("=".repeat(70));
                System.out.println("Total de prestamos: " + prestamos.size());
            }
        } catch (InputMismatchException e) {
            System.out.println("Documento invalido.");
            consola.nextLine();
        }
    }
}