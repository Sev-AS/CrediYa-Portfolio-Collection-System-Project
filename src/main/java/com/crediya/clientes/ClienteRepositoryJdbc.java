package com.crediya.clientes;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.crediya.common.DatabaseConnection;

public class ClienteRepositoryJdbc implements ClienteRepository {

    private final DatabaseConnection dbConnection;

    public ClienteRepositoryJdbc(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
        inicializarEsquema();
    }

    private void inicializarEsquema() {
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS clientes (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nombre VARCHAR(80) NOT NULL, " +
                    "documento VARCHAR(30) UNIQUE NOT NULL, " +
                    "correo VARCHAR(80) NOT NULL, " +
                    "telefono VARCHAR(20) NOT NULL)";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.err.println("Error al inicializar el esquema de clientes: " + e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        return dbConnection.getConnection();
    }

    @Override
    public Cliente agregar(Cliente cliente) {
        String sql = "INSERT INTO clientes(nombre, documento, correo, telefono) VALUES(?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, String.valueOf(cliente.getDocumento()));
            pstmt.setString(3, cliente.getCorreo());
            pstmt.setString(4, cliente.getTelefono());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        cliente.setId(generatedKeys.getInt(1));
                    }
                }
            }
            return cliente;
        } catch (SQLException e) {
            System.err.println("Error al agregar cliente: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                clientes.add(mapRowToCliente(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        }
        return clientes;
    }

    @Override
    public Cliente obtenerPorDocumento(int documento) {
        String sql = "SELECT * FROM clientes WHERE documento = ?";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, String.valueOf(documento));
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToCliente(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener cliente por documento: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void actualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre = ?, correo = ?, telefono = ? WHERE documento = ?";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getCorreo());
            pstmt.setString(3, cliente.getTelefono());
            pstmt.setString(4, String.valueOf(cliente.getDocumento()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int documento) {
        String sql = "DELETE FROM clientes WHERE documento = ?";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, String.valueOf(documento));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
        }
    }

    private Cliente mapRowToCliente(ResultSet rs) throws SQLException {
        String docStr = rs.getString("documento");
        int documento;
        try {
            documento = Integer.parseInt(docStr);
        } catch (NumberFormatException e) {
            throw new SQLException("Documento no es un numero valido: " + docStr);
        }
        
        return new Cliente(
                rs.getInt("id"),
                rs.getString("nombre"),
                documento,
                rs.getString("correo"),
                rs.getString("telefono"));
    }
}
