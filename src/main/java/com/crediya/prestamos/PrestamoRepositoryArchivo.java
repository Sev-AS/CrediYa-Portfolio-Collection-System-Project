package com.crediya.prestamos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrestamoRepositoryArchivo implements PrestamoRepository {

    private final String RUTA_ARCHIVO = "src/main/java/com/crediya/db/data_txt/prestamos.txt";
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

    @Override
    public void actualizarSaldo(int id, double nuevoSaldo) {
        Prestamos prestamo = obtenerPorId(id);
        if (prestamo != null) {
            prestamo.setSaldoPendiente(nuevoSaldo);
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
                if (p.length == 8 || p.length == 9) {
                    Prestamos prestamo = new Prestamos(
                            Integer.parseInt(p[0]),
                            Integer.parseInt(p[1]),
                            Integer.parseInt(p[2]),
                            Double.parseDouble(p[3]),
                            Double.parseDouble(p[4]),
                            Integer.parseInt(p[5]),
                            p[6],
                            p[7]);

                    if (p.length == 9) {
                        prestamo.setSaldoPendiente(Double.parseDouble(p[8]));
                    }

                    prestamos.add(prestamo);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al cargar el archivo de prestamos: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error al parsear un numero en el archivo de prestamos: " + e.getMessage());
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
                                p.getEstado() + ";" +
                                p.getSaldoPendiente());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al guardar el archivo de prestamos: " + e.getMessage());
        }
    }
}
