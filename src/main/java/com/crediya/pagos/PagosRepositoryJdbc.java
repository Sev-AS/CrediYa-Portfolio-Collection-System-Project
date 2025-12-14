package com.crediya.pagos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.crediya.common.DatabaseConnection;

public class PagosRepositoryJdbc implements PagosRepository {

    private final DatabaseConnection dbConnection;

    public PagosRepositoryJdbc(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
        inicializarEsquema();
    }

    private void inicializarEsquema() {
        try (Connection conn = dbConnection.getConnection();
                Statement stmt = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS pagos (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "prestamo_id INT NOT NULL, " +
                    "monto DOUBLE NOT NULL, " +
                    "fecha VARCHAR(255))";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.err.println("Error al inicializar el esquema de pagos: " + e.getMessage());
        }
    }

    @Override
    public Pagos registrar(Pagos pago) {
        String sql = "INSERT INTO pagos(prestamo_id, monto, fecha) VALUES(?, ?, ?)";
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, pago.getPrestamoId());
            pstmt.setDouble(2, pago.getMonto());
            pstmt.setString(3, pago.getFecha());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        pago.setId(generatedKeys.getInt(1));
                    }
                }
            }
            return pago;
        } catch (SQLException e) {
            System.err.println("Error al registrar pago: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Pagos> listarPorPrestamo(int prestamoId) {
        List<Pagos> pagos = new ArrayList<>();
        String sql = "SELECT * FROM pagos WHERE prestamo_id = ?";
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, prestamoId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    pagos.add(new Pagos(
                            rs.getInt("id"),
                            rs.getInt("prestamo_id"),
                            rs.getDouble("monto"),
                            rs.getString("fecha")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar pagos: " + e.getMessage());
        }
        return pagos;
    }
}
