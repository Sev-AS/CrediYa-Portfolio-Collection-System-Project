package com.crediya.empleados;

import java.util.Scanner;
import java.util.List;

public class MenuEmpleados {
    private int opcion;
    Scanner consola = new Scanner(System.in);
    EmpleadoRepositoryJdbc empleadoRepository = new EmpleadoRepositoryJdbc();

    public MenuEmpleados() {
        IniciarMenu();
    }

    private void IniciarMenu() {
        do {
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
                                            -- 2 -- ver lista de empleados
                                            -- 3 -- Buscar empleado por nombre
                                            -- 4 -- Eliminar un empleado
                                            -- 5 -- Actualizar un empleado
                                            -- 6 -- Salir de menú de empleado
                                                """);

            opcion = consola.nextInt();

            switch (opcion) {
                case 1:
                    MEagregarEmpleado();
                    break;
                case 2:
                    MElistarEmpleados();
                    break;
                case 3:
                    MEbuscarEmpleado();
                    break;
                case 4:
                    MEeliminarEmpleado();
                    break;
                case 5:
                    MEactualizarEmpleado();
                    break;
                case 6:
                    break;
            }

        } while (opcion != 6);
    }

    public void MEagregarEmpleado() {
        System.out.println("Dame el nombre del empleado");
        consola.nextLine(); // LIMPIAR BUFFER
        String nombre = consola.nextLine();

        System.out.println("Dame el documento del empleado");
        int documento = consola.nextInt();
        consola.nextLine(); // LIMPIAR BUFFER

        System.out.println("Dame el rol del empleado");
        String rol = consola.nextLine();

        System.out.println("Dame el correo del empleado");
        String correo = consola.nextLine();

        System.out.println("Dame el salario del empleado");
        double salario = consola.nextDouble();

        Empleado nuevo = new Empleado(0, nombre, documento, rol, correo, salario);
        empleadoRepository.guardar(nuevo);
    }

    public void MElistarEmpleados() {
        List<Empleado> empleados = empleadoRepository.listar();
        for (Empleado e : empleados) {
            System.out.println(e);
        }
    }

    public void MEbuscarEmpleado() {
        System.out.println("Opción no disponible por el momento.");
    }

    public void MEeliminarEmpleado() {
        System.out.println("Opción no disponible por el momento.");
    }

    public void MEactualizarEmpleado() {
        System.out.println("Opción no disponible por el momento.");
    }

    public static void main(String[] args) {
        MenuEmpleados menuEmpleados = new MenuEmpleados();
        System.out.println(menuEmpleados);
    }
}
