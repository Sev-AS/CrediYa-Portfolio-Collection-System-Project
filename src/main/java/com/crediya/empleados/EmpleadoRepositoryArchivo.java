package com.crediya.empleados;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class EmpleadoRepositoryArchivo implements EmpleadoRepository {

    private final String RUTA_ARCHIVO = "src/main/java/com/crediya/db/data_txt/empleados.txt";
    private List<Empleado> empleados;
    private int proximoId;

    public EmpleadoRepositoryArchivo() {
        this.empleados = new ArrayList<>();
        cargar();
        proximoId = calcularProximoId();
    }

    private int calcularProximoId() {
        return empleados.stream()
                .mapToInt(Empleado::getId)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public Empleado agregar(Empleado empleado) {
        empleado.setId(proximoId++);
        empleados.add(empleado);
        guardar();
        return empleado;
    }

    @Override
    public List<Empleado> listar() {
        return new ArrayList<>(empleados);
    }

    @Override
    public Empleado obtenerPorId(int id) {
        return empleados.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void actualizar(Empleado empleadoActualizado) {
        Optional<Empleado> empleadoExistenteOpt = empleados.stream()
                .filter(e -> e.getId() == empleadoActualizado.getId())
                .findFirst();

        if (empleadoExistenteOpt.isPresent()) {
            Empleado empleadoExistente = empleadoExistenteOpt.get();
            empleadoExistente.setNombre(empleadoActualizado.getNombre());
            empleadoExistente.setDocumento(empleadoActualizado.getDocumento());
            empleadoExistente.setRol(empleadoActualizado.getRol());
            empleadoExistente.setCorreo(empleadoActualizado.getCorreo());
            empleadoExistente.setSalario(empleadoActualizado.getSalario());
            guardar();
        }
    }

    @Override
    public void eliminar(int id) {
        if (empleados.removeIf(empleado -> empleado.getId() == id)) {
            guardar();
        }
    }

    private void cargar() {
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) {
            return;
        }

        try (Scanner scanner = new Scanner(archivo)) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] partes = linea.split(";");
                if (partes.length == 6) {
                    int id = Integer.parseInt(partes[0]);
                    String nombre = partes[1];
                    int documento = Integer.parseInt(partes[2]);
                    String rol = partes[3];
                    String correo = partes[4];
                    double salario = Double.parseDouble(partes[5]);
                    empleados.add(new Empleado(id, nombre, documento, rol, correo, salario));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al cargar el archivo de empleados: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error al leer un numero en el archivo de empleados: " + e.getMessage());
        }
    }

    private void guardar() {
        try (PrintWriter writer = new PrintWriter(new File(RUTA_ARCHIVO))) {
            for (Empleado empleado : empleados) {
                writer.println(
                        empleado.getId() + ";" +
                                empleado.getNombre() + ";" +
                                empleado.getDocumento() + ";" +
                                empleado.getRol() + ";" +
                                empleado.getCorreo() + ";" +
                                empleado.getSalario());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al guardar el archivo de empleados: " + e.getMessage());
        }
    }
}