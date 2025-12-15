package com.crediya.empleados;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.crediya.common.DatabaseConnection;

public class EmpleadoRepositoryJdbc implements EmpleadoRepository {

    private final DatabaseConnection dbConnection;

    public EmpleadoRepositoryJdbc(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
        inicializarEsquema();
    }

    private void inicializarEsquema() {
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS empleados (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nombre VARCHAR(80) NOT NULL, " +
                    "documento VARCHAR(30) UNIQUE NOT NULL, " +
                    "rol VARCHAR(30) NOT NULL, " +
                    "correo VARCHAR(80) NOT NULL, " +
                    "salario DECIMAL(10,2) NOT NULL)";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.err.println("Error en la base de datos: " + e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        return dbConnection.getConnection();
    }

    @Override
    public Empleado agregar(Empleado empleado) {
        String sql = "INSERT INTO empleados(nombre, documento, rol, correo, salario) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, empleado.getNombre());
            pstmt.setString(2, String.valueOf(empleado.getDocumento()));
            pstmt.setString(3, empleado.getRol());
            pstmt.setString(4, empleado.getCorreo());
            pstmt.setBigDecimal(5, java.math.BigDecimal.valueOf(empleado.getSalario()));

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        empleado.setId(generatedKeys.getInt(1));
                    }
                }
            }
            return empleado;
        } catch (SQLException e) {
            System.err.println("Error al agregar empleado: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Empleado> listar() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleados";
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                empleados.add(mapRowToEmpleado(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar empleados: " + e.getMessage());
        }
        return empleados;
    }

    @Override
    public Empleado obtenerPorId(int id) {
        String sql = "SELECT * FROM empleados WHERE id = ?";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToEmpleado(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener empleado por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void actualizar(Empleado empleado) {
        String sql = "UPDATE empleados SET nombre = ?, documento = ?, rol = ?, correo = ?, salario = ? WHERE id = ?";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, empleado.getNombre());
            pstmt.setString(2, String.valueOf(empleado.getDocumento()));
            pstmt.setString(3, empleado.getRol());
            pstmt.setString(4, empleado.getCorreo());
            pstmt.setBigDecimal(5, java.math.BigDecimal.valueOf(empleado.getSalario()));
            pstmt.setInt(6, empleado.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar empleado: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM empleados WHERE id = ?";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar empleado: " + e.getMessage());
        }
    }

    private Empleado mapRowToEmpleado(ResultSet rs) throws SQLException {
        String docStr = rs.getString("documento");
        int documento;
        try {
            documento = Integer.parseInt(docStr);
        } catch (NumberFormatException e) {
            throw new SQLException("Documento no es un numero valido: " + docStr);
        }
        
        double salario = rs.getBigDecimal("salario").doubleValue();
        
        return new Empleado(
                rs.getInt("id"),
                rs.getString("nombre"),
                documento,
                rs.getString("rol"),
                rs.getString("correo"),
                salario);
    }
}