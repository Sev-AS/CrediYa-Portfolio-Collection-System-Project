package com.crediya.clientes;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuClientes {
    private final ClienteRepository clienteRepository;
    private final Scanner consola;

    public MenuClientes(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
        this.consola = new Scanner(System.in);
    }

    public void iniciarMenu() {
        int opcion = 0;
        do {
            try {
                System.out.println(
                        """

                                --- GESTION DE CLIENTES ---
                                    1. Inscribir un cliente
                                    2. Ver lista de clientes
                                    3. Buscar cliente por documento
                                    4. Eliminar un cliente
                                    5. Actualizar un cliente
                                    6. Salir de menu de cliente
                                        """);

                opcion = consola.nextInt();
                consola.nextLine();

                switch (opcion) {
                    case 1 -> mcAgregarCliente();
                    case 2 -> mcListarClientes();
                    case 3 -> mcBuscarCliente();
                    case 4 -> mcEliminarCliente();
                    case 5 -> mcActualizarCliente();
                    case 6 -> System.out.println("Saliendo del menu de clientes...");
                    default -> System.out.println("Opcion no valida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Por favor, introduce un numero valido.");
                consola.nextLine();
                opcion = 0;
            }
        } while (opcion != 6);
    }

    private void mcAgregarCliente() {
        System.out.println("--- Inscribir Nuevo Cliente ---");
        Cliente nuevoCliente = solicitarDatosCliente(0);
        Cliente clienteGuardado = clienteRepository.agregar(nuevoCliente);
        System.out.println("Cliente inscrito exitosamente con ID: " + clienteGuardado.getId());
        System.out.println(clienteGuardado);
    }

    private void mcListarClientes() {
        System.out.println("--- Lista de Clientes ---");
        List<Cliente> clientes = clienteRepository.listar();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            clientes.forEach(System.out::println);
        }
    }

    private void mcBuscarCliente() {
        System.out.println("--- Buscar Cliente por Documento ---");
        System.out.print("Dame el documento del cliente a buscar: ");
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
            System.out.println("Error: Documento invalido. Debe ser un numero.");
            consola.nextLine();
        }
    }

    private void mcEliminarCliente() {
        System.out.println("--- Eliminar Cliente ---");
        System.out.print("Dame el documento del cliente a eliminar: ");
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
            System.out.println("Error: Documento invalido. Debe ser un numero.");
            consola.nextLine();
        }
    }

    private void mcActualizarCliente() {
        System.out.println("--- Actualizar Cliente ---");
        System.out.print("Dame el documento del cliente a actualizar: ");
        try {
            int documento = consola.nextInt();
            consola.nextLine();

            Cliente clienteExistente = clienteRepository.obtenerPorDocumento(documento);
            if (clienteExistente == null) {
                System.out.println("No se encontro un cliente con el documento: " + documento);
                return;
            }

            System.out.println("Introduce los nuevos datos para el cliente con documento: " + documento);
            Cliente datosNuevos = solicitarDatosCliente(documento);
            datosNuevos.setId(clienteExistente.getId());

            clienteRepository.actualizar(datosNuevos);
            System.out.println("Cliente con documento " + documento + " ha sido actualizado.");
        } catch (InputMismatchException e) {
            System.out.println("Error: Documento invalido. Debe ser un numero.");
            consola.nextLine();
        }
    }

    private Cliente solicitarDatosCliente(int documento) {
        System.out.print("Nombre: ");
        String nombre = consola.nextLine();

        int doc = documento;
        if (doc == 0) {
            while (doc == 0) {
                try {
                    System.out.print("Documento: ");
                    doc = consola.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Documento invalido. Introduce un numero.");
                } finally {
                    consola.nextLine();
                }
            }
        }

        System.out.print("Correo: ");
        String correo = consola.nextLine();

        System.out.print("Telefono: ");
        String telefono = consola.nextLine();

        return new Cliente(0, nombre, doc, correo, telefono);
    }
}
