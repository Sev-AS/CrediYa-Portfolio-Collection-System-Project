package com.crediya.prestamos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrestamoRepositoryArchivo implements PrestamoRepository {

    private final String RUTA_ARCHIVO = "prestamos.txt";
    private List<Prestamos> prestamos;
    private int proximoId;

    public PrestamoRepositoryArchivo() {
        this.prestamos = new ArrayList<>();
        cargar();
        this.proximoId = calcularProximoId();
    }

    private int calcularProximoId() {
        return prestamos.stream()
                .mapToInt(Prestamos::getId)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public Prestamos agregar(Prestamos prestamo) {
        prestamo.setId(proximoId++);
        // Se recalculan los valores en caso de que se haya modificado algo antes de guardar
        prestamo.calcularValoresPrestamo(); 
        prestamos.add(prestamo);
        guardar();
        return prestamo;
    }

    @Override
    public List<Prestamos> listar() {
        return new ArrayList<>(prestamos);
    }

    @Override
    public Prestamos obtenerPorId(int id) {
        return prestamos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void cambiarEstado(int id, String nuevoEstado) {
        Prestamos prestamo = obtenerPorId(id);
        if (prestamo != null) {
            prestamo.setEstado(nuevoEstado);
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
                String[] p = linea.split(";");
                if (p.length == 8) {
                    Prestamos prestamo = new Prestamos(
                            Integer.parseInt(p[0]),
                            Integer.parseInt(p[1]),
                            Integer.parseInt(p[2]),
                            Double.parseDouble(p[3]),
                            Double.parseDouble(p[4]),
                            Integer.parseInt(p[5]),
                            p[6],
                            p[7]
                    );
                    prestamos.add(prestamo);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al cargar el archivo de préstamos: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error al parsear un número en el archivo de préstamos: " + e.getMessage());
        }
    }

    private void guardar() {
        try (PrintWriter writer = new PrintWriter(new File(RUTA_ARCHIVO))) {
            for (Prestamos p : prestamos) {
                writer.println(
                        p.getId() + ";" +
                        p.getClienteId() + ";" +
                        p.getEmpleadoId() + ";" +
                        p.getMonto() + ";" +
                        p.getInteresMensual() + ";" +
                        p.getCuotas() + ";" +
                        p.getFechaInicio() + ";" +
                        p.getEstado()
                );
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al guardar el archivo de préstamos: " + e.getMessage());
        }
    }
}
