package com.crediya.clientes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClienteRepositoryArchivo implements ClienteRepository {

    private final String RUTA_ARCHIVO = "src/main/java/com/crediya/db/data_txt/clientes.txt";
    private List<Cliente> clientes;
    private int proximoId;

    public ClienteRepositoryArchivo() {
        this.clientes = new ArrayList<>();
        cargar();
        proximoId = calcularProximoId();
    }

    private int calcularProximoId() {
        return clientes.stream()
                .mapToInt(Cliente::getId)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public Cliente agregar(Cliente cliente) {
        cliente.setId(proximoId++);
        clientes.add(cliente);
        guardar();
        return cliente;
    }

    @Override
    public List<Cliente> listar() {
        return new ArrayList<>(clientes);
    }

    @Override
    public Cliente obtenerPorDocumento(int documento) {
        return clientes.stream()
                .filter(c -> c.getDocumento() == documento)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void actualizar(Cliente clienteActualizado) {
        Cliente clienteExistente = obtenerPorDocumento(clienteActualizado.getDocumento());
        if (clienteExistente != null) {
            clienteExistente.setNombre(clienteActualizado.getNombre());
            clienteExistente.setCorreo(clienteActualizado.getCorreo());
            clienteExistente.setTelefono(clienteActualizado.getTelefono());
            guardar();
        }
    }

    @Override
    public void eliminar(int documento) {
        if (clientes.removeIf(cliente -> cliente.getDocumento() == documento)) {
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
                if (partes.length == 5) {
                    int id = Integer.parseInt(partes[0]);
                    String nombre = partes[1];
                    int documento = Integer.parseInt(partes[2]);
                    String correo = partes[3];
                    String telefono = partes[4];
                    clientes.add(new Cliente(id, nombre, documento, correo, telefono));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al cargar el archivo de clientes: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error al parsear un numero en el archivo de clientes: " + e.getMessage());
        }
    }

    private void guardar() {
        try (PrintWriter writer = new PrintWriter(new File(RUTA_ARCHIVO))) {
            for (Cliente cliente : clientes) {
                writer.println(
                        cliente.getId() + ";" +
                                cliente.getNombre() + ";" +
                                cliente.getDocumento() + ";" +
                                cliente.getCorreo() + ";" +
                                cliente.getTelefono());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al guardar el archivo de clientes: " + e.getMessage());
        }
    }
}
