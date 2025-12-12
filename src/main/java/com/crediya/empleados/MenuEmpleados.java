package com.crediya.empleados;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuEmpleados {
    private final EmpleadoRepository empleadoRepository;
    private final Scanner consola;

    public MenuEmpleados(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
        this.consola = new Scanner(System.in);
    }

    public void iniciarMenu() {
        int opcion = 0;
        do {
            try {
                System.out.println(
                        """

                                 ▄▄▄▄▄▄▄                ▄▄                ▄▄
                                ███▀▀▀▀▀                ██                ██
                                ███▄▄    ███▄███▄ ████▄ ██ ▄█▀█▄  ▀▀█▄ ▄████ ▄███▄
                                ███      ██ ██ ██ ██ ██ ██ ██▄█▀ ▄█▀██ ██ ██ ██ ██
                                ▀███████ ██ ██ ██ ████▀ ██ ▀█▄▄▄ ▀█▄██ ▀████ ▀███▀
                                                  ██
                                                  ▀▀
                                                -- 1 -- Inscribir un empleado
                                                -- 2 -- Ver lista de empleados
                                                -- 3 -- Buscar empleado por ID
                                                -- 4 -- Eliminar un empleado
                                                -- 5 -- Actualizar un empleado
                                                -- 6 -- Salir de menú de empleado
                                                    """);

                opcion = consola.nextInt();
                consola.nextLine(); // Limpiar buffer

                switch (opcion) {
                    case 1 -> meAgregarEmpleado();
                    case 2 -> meListarEmpleados();
                    case 3 -> meBuscarEmpleado();
                    case 4 -> meEliminarEmpleado();
                    case 5 -> meActualizarEmpleado();
                    case 6 -> System.out.println("Saliendo del menú de empleados...");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Por favor, introduce un número válido.");
                consola.nextLine(); // Limpiar el buffer de entrada
                opcion = 0; // Resetear opción para continuar el bucle
            }
        } while (opcion != 6);
    }

    private void meAgregarEmpleado() {
        System.out.println("--- Inscribir Nuevo Empleado ---");
        Empleado nuevoEmpleado = solicitarDatosEmpleado();
        Empleado empleadoGuardado = empleadoRepository.agregar(nuevoEmpleado);
        System.out.println("Empleado inscrito exitosamente con ID: " + empleadoGuardado.getId());
        System.out.println(empleadoGuardado);
    }

    private void meListarEmpleados() {
        System.out.println("--- Lista de Empleados ---");
        List<Empleado> empleados = empleadoRepository.listar();
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
        } else {
            empleados.forEach(System.out::println);
        }
    }

    private void meBuscarEmpleado() {
        System.out.println("--- Buscar Empleado por ID ---");
        System.out.print("Dame el ID del empleado a buscar: ");
        try {
            int id = consola.nextInt();
            consola.nextLine(); // Limpiar buffer
            Empleado empleado = empleadoRepository.obtenerPorId(id);
            if (empleado != null) {
                System.out.println("Empleado encontrado:");
                System.out.println(empleado);
            } else {
                System.out.println("No se encontró un empleado con el ID: " + id);
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: ID inválido. Debe ser un número.");
            consola.nextLine();
        }
    }

    private void meEliminarEmpleado() {
        System.out.println("--- Eliminar Empleado ---");
        System.out.print("Dame el ID del empleado a eliminar: ");
        try {
            int id = consola.nextInt();
            consola.nextLine(); // Limpiar buffer
            if (empleadoRepository.obtenerPorId(id) != null) {
                empleadoRepository.eliminar(id);
                System.out.println("Empleado con ID " + id + " ha sido eliminado.");
            } else {
                System.out.println("No se encontró un empleado con el ID: " + id);
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: ID inválido. Debe ser un número.");
            consola.nextLine();
        }
    }

    private void meActualizarEmpleado() {
        System.out.println("--- Actualizar Empleado ---");
        System.out.print("Dame el ID del empleado a actualizar: ");
        try {
            int id = consola.nextInt();
            consola.nextLine(); // Limpiar buffer

            if (empleadoRepository.obtenerPorId(id) == null) {
                System.out.println("No se encontró un empleado con el ID: " + id);
                return;
            }

            System.out.println("Introduce los nuevos datos para el empleado con ID: " + id);
            Empleado datosNuevos = solicitarDatosEmpleado();
            datosNuevos.setId(id); // Asegurarse de que el ID es el correcto

            empleadoRepository.actualizar(datosNuevos);
            System.out.println("Empleado con ID " + id + " ha sido actualizado.");
        } catch (InputMismatchException e) {
            System.out.println("Error: ID inválido. Debe ser un número.");
            consola.nextLine();
        }
    }

    private Empleado solicitarDatosEmpleado() {
        System.out.print("Nombre: ");
        String nombre = consola.nextLine();

        int documento = 0;
        while (documento == 0) {
            try {
                System.out.print("Documento: ");
                documento = consola.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Documento inválido. Introduce un número.");
            } finally {
                consola.nextLine(); // Limpiar siempre el buffer
            }
        }

        System.out.print("Rol: ");
        String rol = consola.nextLine();

        System.out.print("Correo: ");
        String correo = consola.nextLine();

        double salario = 0.0;
        while (salario == 0.0) {
            try {
                System.out.print("Salario: ");
                salario = consola.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Salario inválido. Introduce un número.");
            } finally {
                consola.nextLine(); // Limpiar siempre el buffer
            }
        }

        return new Empleado(0, nombre, documento, rol, correo, salario);
    }


    public static void main(String[] args) {
        // Inyección de dependencia: Se crea la implementación del repositorio
        // y se pasa al menú.
        EmpleadoRepository repository = new EmpleadoRepositoryArchivo();
        MenuEmpleados menu = new MenuEmpleados(repository);
        menu.iniciarMenu();
    }
}
