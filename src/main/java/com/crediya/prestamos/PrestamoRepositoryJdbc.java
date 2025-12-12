package com.crediya.prestamos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.crediya.common.DatabaseConnection;

public class PrestamoRepositoryJdbc implements PrestamoRepository {

    private final DatabaseConnection dbConnection;

    public PrestamoRepositoryJdbc(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
        inicializarEsquema();
    }

    private void inicializarEsquema() {
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS prestamos (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "cliente_id INT NOT NULL, " +
                    "empleado_id INT NOT NULL, " +
                    "monto DOUBLE NOT NULL, " +
                    "interes_mensual DOUBLE NOT NULL, " +
                    "cuotas INT NOT NULL, " +
                    "fecha_inicio VARCHAR(255), " +
                    "estado VARCHAR(255))";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.err.println("Error al inicializar el esquema de préstamos: " + e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        return dbConnection.getConnection();
    }

    @Override
    public Prestamos agregar(Prestamos prestamo) {
        String sql = "INSERT INTO prestamos(cliente_id, empleado_id, monto, interes_mensual, cuotas, fecha_inicio, estado) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, prestamo.getClienteId());
            pstmt.setInt(2, prestamo.getEmpleadoId());
            pstmt.setDouble(3, prestamo.getMonto());
            pstmt.setDouble(4, prestamo.getInteresMensual());
            pstmt.setInt(5, prestamo.getCuotas());
            pstmt.setString(6, prestamo.getFechaInicio());
            pstmt.setString(7, prestamo.getEstado());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        prestamo.setId(generatedKeys.getInt(1));
                    }
                }
            }
            // Los valores calculados (montoTotal, cuotaMensual) ya están en el objeto
            // prestamo
            return prestamo;
        } catch (SQLException e) {
            System.err.println("Error al agregar préstamo: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Prestamos> listar() {
        List<Prestamos> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos";
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                prestamos.add(mapRowToPrestamo(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar préstamos: " + e.getMessage());
        }
        return prestamos;
    }

    @Override
    public Prestamos obtenerPorId(int id) {
        String sql = "SELECT * FROM prestamos WHERE id = ?";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToPrestamo(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener préstamo por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void cambiarEstado(int id, String nuevoEstado) {
        String sql = "UPDATE prestamos SET estado = ? WHERE id = ?";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nuevoEstado);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al cambiar estado del préstamo: " + e.getMessage());
        }
    }

    private Prestamos mapRowToPrestamo(ResultSet rs) throws SQLException {
        return new Prestamos(
                rs.getInt("id"),
                rs.getInt("cliente_id"),
                rs.getInt("empleado_id"),
                rs.getDouble("monto"),
                rs.getDouble("interes_mensual"),
                rs.getInt("cuotas"),
                rs.getString("fecha_inicio"),
                rs.getString("estado"));
    }
}
