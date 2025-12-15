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
                    "fecha_pago DATE NOT NULL, " +
                    "monto DECIMAL(10,2) NOT NULL, " +
                    "FOREIGN KEY (prestamo_id) REFERENCES prestamos(id) ON DELETE CASCADE)";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.err.println("Error al inicializar el esquema de pagos: " + e.getMessage());
        }
    }

    @Override
    public Pagos registrar(Pagos pago) {
        String sql = "INSERT INTO pagos(prestamo_id, fecha_pago, monto) VALUES(?, ?, ?)";
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, pago.getPrestamoId());
            java.sql.Date fechaDate = java.sql.Date.valueOf(pago.getFecha());
            pstmt.setDate(2, fechaDate);
            pstmt.setBigDecimal(3, java.math.BigDecimal.valueOf(pago.getMonto()));

            return executeInsert(pstmt, pago);
        } catch (SQLException e) {
            System.err.println("Error al registrar pago: " + e.getMessage());
            return null;
        }
    }
    
    private Pagos executeInsert(PreparedStatement pstmt, Pagos pago) throws SQLException {
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pago.setId(generatedKeys.getInt(1));
                }
            }
        }
        return pago;
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
                    java.sql.Date fechaDate = rs.getDate("fecha_pago");
                    String fecha = (fechaDate != null) ? fechaDate.toString() : "";
                    
                    pagos.add(new Pagos(
                            rs.getInt("id"),
                            rs.getInt("prestamo_id"),
                            rs.getBigDecimal("monto").doubleValue(),
                            fecha));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar pagos: " + e.getMessage());
        }
        return pagos;
    }
    
    @Override
    public Pagos registrarPagoConActualizacionSaldo(Pagos pago, double nuevoSaldo, int prestamoId) {
        try (Connection conn = dbConnection.getConnection()) {
            conn.setAutoCommit(false);
            
            try {
                String sqlPago = "INSERT INTO pagos(prestamo_id, fecha_pago, monto) VALUES(?, ?, ?)";
                try (PreparedStatement pstmtPago = conn.prepareStatement(sqlPago, Statement.RETURN_GENERATED_KEYS)) {
                    pstmtPago.setInt(1, pago.getPrestamoId());
                    java.sql.Date fechaDate = java.sql.Date.valueOf(pago.getFecha());
                    pstmtPago.setDate(2, fechaDate);
                    pstmtPago.setBigDecimal(3, java.math.BigDecimal.valueOf(pago.getMonto()));
                    executeInsertInTransaction(pstmtPago, pago);
                }
                
                String sqlSaldo = "UPDATE prestamos SET saldo_pendiente = ? WHERE id = ?";
                try (PreparedStatement pstmtSaldo = conn.prepareStatement(sqlSaldo)) {
                    pstmtSaldo.setBigDecimal(1, java.math.BigDecimal.valueOf(nuevoSaldo));
                    pstmtSaldo.setInt(2, prestamoId);
                    
                    int affectedRows = pstmtSaldo.executeUpdate();
                    if (affectedRows == 0) {
                        throw new SQLException("No se pudo actualizar el saldo del prestamo.");
                    }
                }
                
                if (nuevoSaldo <= 0) {
                    String sqlEstado = "UPDATE prestamos SET estado = ? WHERE id = ?";
                    try (PreparedStatement pstmtEstado = conn.prepareStatement(sqlEstado)) {
                        pstmtEstado.setString(1, "Pagado");
                        pstmtEstado.setInt(2, prestamoId);
                        pstmtEstado.executeUpdate();
                    }
                }
                
                conn.commit();
                return pago;
                
            } catch (SQLException e) {
                conn.rollback();
                System.err.println("Transaccion revertida debido a error: " + e.getMessage());
                return null;
            } finally {
                conn.setAutoCommit(true);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al registrar pago con actualizacion de saldo: " + e.getMessage());
            return null;
        }
    }
    
    private void executeInsertInTransaction(PreparedStatement pstmt, Pagos pago) throws SQLException {
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("No se pudo registrar el pago.");
        }
        
        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                pago.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("No se pudo obtener el ID del pago registrado.");
            }
        }
    }
}