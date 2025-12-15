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
                                               1. Inscribir un empleado
                                               2. Ver lista de empleados
                                               3. Buscar empleado por ID
                                               4. Eliminar un empleado
                                               5. Actualizar un empleado
                                               6. Salir de menu de empleado
                                                   """);

                opcion = consola.nextInt();
                consola.nextLine();

                switch (opcion) {
                    case 1 -> meAgregarEmpleado();
                    case 2 -> meListarEmpleados();
                    case 3 -> meBuscarEmpleado();
                    case 4 -> meEliminarEmpleado();
                    case 5 -> meActualizarEmpleado();
                    case 6 -> System.out.println("Saliendo del menu de empleados...");
                    default -> System.out.println("Opcion no valida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Introduzca un numero valido.");
                consola.nextLine();
                opcion = 0;
            }
        } while (opcion != 6);
    }

    private void meAgregarEmpleado() {
        System.out.println("Inscribir Nuevo Empleado");
        try {
            Empleado nuevoEmpleado = solicitarDatosEmpleado();
            
            Empleado empleadoGuardado = empleadoRepository.agregar(nuevoEmpleado);
            if (empleadoGuardado != null) {
                System.out.println("Empleado inscrito exitosamente con ID: " + empleadoGuardado.getId());
                System.out.println(empleadoGuardado);
            } else {
                System.out.println("Error: No se pudo guardar el empleado.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error de validacion: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al agregar empleado: " + e.getMessage());
        }
    }

    private void meListarEmpleados() {
        System.out.println("Lista de Empleados");
        List<Empleado> empleados = empleadoRepository.listar();
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
        } else {
            empleados.forEach(System.out::println);
        }
    }

    private void meBuscarEmpleado() {
        System.out.println("Buscar Empleado por ID");
        System.out.print("Ingrese el ID del empleado a buscar: ");
        try {
            int id = consola.nextInt();
            consola.nextLine();
            Empleado empleado = empleadoRepository.obtenerPorId(id);
            if (empleado != null) {
                System.out.println("Empleado encontrado:");
                System.out.println(empleado);
            } else {
                System.out.println("No se encontro un empleado con el ID: " + id);
            }
        } catch (InputMismatchException e) {
            System.out.println("Ingrese un numero entero positivo.");
            consola.nextLine();
        }
    }

    private void meEliminarEmpleado() {
        System.out.println("Eliminar Empleado");
        System.out.print("Ingrese el ID del empleado a eliminar: ");
        try {
            int id = consola.nextInt();
            consola.nextLine();
            if (empleadoRepository.obtenerPorId(id) != null) {
                empleadoRepository.eliminar(id);
                System.out.println("Empleado con ID " + id + " ha sido eliminado.");
            } else {
                System.out.println("No se encontro un empleado con el ID: " + id);
            }
        } catch (InputMismatchException e) {
            System.out.println("Ingrese un numero entero positivo.");
            consola.nextLine();
        }
    }

    private void meActualizarEmpleado() {
        System.out.println("Actualizar Empleado");
        System.out.print("Ingrese el ID del empleado a actualizar: ");
        try {
            int id = consola.nextInt();
            consola.nextLine();

            if (empleadoRepository.obtenerPorId(id) == null) {
                System.out.println("No se encontro un empleado con el ID: " + id);
                return;
            }

            System.out.println("Introduzca los nuevos datos para el empleado con ID: " + id);
            Empleado datosNuevos = solicitarDatosEmpleado();
            datosNuevos.setId(id);

            empleadoRepository.actualizar(datosNuevos);
            System.out.println("Empleado con ID " + id + " ha sido actualizado.");
        } catch (InputMismatchException e) {
            System.out.println("Ingrese un numero entero positivo.");
            consola.nextLine();
        }
    }

    private Empleado solicitarDatosEmpleado() {
        System.out.print("Nombre: ");
        String nombre = consola.nextLine().trim();
        if (nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }

        int documento = 0;
        while (documento <= 0) {
            try {
                System.out.print("Documento: ");
                documento = consola.nextInt();
                if (documento <= 0) {
                    System.out.println("El documento debe ser un numero positivo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Introduzca un numero.");
            } finally {
                consola.nextLine();
            }
        }

        System.out.print("Rol: ");
        String rol = consola.nextLine().trim();

        System.out.print("Correo: ");
        String correo = consola.nextLine().trim();

        double salario = -1.0;
        while (salario < 0) {
            try {
                System.out.print("Salario: ");
                salario = consola.nextDouble();
                if (salario < 0) {
                    System.out.println("El salario debe ser un numero positivo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Introduzca un numero.");
            } finally {
                consola.nextLine();
            }
        }

        return new Empleado(0, nombre, documento, rol, correo, salario);
    }
}