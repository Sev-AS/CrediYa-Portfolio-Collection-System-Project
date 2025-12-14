package com.crediya.pagos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PagosRepositoryArchivo implements PagosRepository {

    private final String RUTA_ARCHIVO = "src/main/java/com/crediya/db/data_txt/pagos.txt";
    private List<Pagos> pagos;
    private int proximoId;

    public PagosRepositoryArchivo() {
        this.pagos = new ArrayList<>();
        cargar();
        proximoId = calcularProximoId();
    }

    private int calcularProximoId() {
        return pagos.stream()
                .mapToInt(Pagos::getId)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public Pagos registrar(Pagos pago) {
        pago.setId(proximoId++);
        pagos.add(pago);
        guardar();
        return pago;
    }

    @Override
    public List<Pagos> listarPorPrestamo(int prestamoId) {
        return pagos.stream()
                .filter(p -> p.getPrestamoId() == prestamoId)
                .collect(Collectors.toList());
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
                if (partes.length == 4) {
                    int id = Integer.parseInt(partes[0]);
                    int prestamoId = Integer.parseInt(partes[1]);
                    double monto = Double.parseDouble(partes[2]);
                    String fecha = partes[3];
                    pagos.add(new Pagos(id, prestamoId, monto, fecha));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al cargar el archivo de pagos: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error al parsear un numero en el archivo de pagos: " + e.getMessage());
        }
    }

    private void guardar() {
        try (PrintWriter writer = new PrintWriter(new File(RUTA_ARCHIVO))) {
            for (Pagos pago : pagos) {
                writer.println(
                        pago.getId() + ";" +
                                pago.getPrestamoId() + ";" +
                                pago.getMonto() + ";" +
                                pago.getFecha());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al guardar el archivo de pagos: " + e.getMessage());
        }
    }
}
