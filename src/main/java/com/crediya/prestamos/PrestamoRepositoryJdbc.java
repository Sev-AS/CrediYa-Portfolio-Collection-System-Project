package com.crediya.prestamos;

import java.sql.Connection;
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
                    "monto DECIMAL(12,2) NOT NULL, " +
                    "interes DECIMAL(5,2) NOT NULL, " +
                    "cuotas INT NOT NULL, " +
                    "fecha_inicio DATE NOT NULL, " +
                    "estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE', " +
                    "saldo_pendiente DECIMAL(12,2) DEFAULT 0, " +
                    "FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE, " +
                    "FOREIGN KEY (empleado_id) REFERENCES empleados(id) ON DELETE CASCADE)";

            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.err.println("Error al inicializar el esquema de prestamos: " + e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        return dbConnection.getConnection();
    }

    @Override
    public Prestamos agregar(Prestamos prestamo) {
        String sql = "INSERT INTO prestamos(cliente_id, empleado_id, monto, interes, cuotas, fecha_inicio, estado, saldo_pendiente) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, prestamo.getClienteId());
            pstmt.setInt(2, prestamo.getEmpleadoId());
            pstmt.setBigDecimal(3, java.math.BigDecimal.valueOf(prestamo.getMonto()));
            pstmt.setBigDecimal(4, java.math.BigDecimal.valueOf(prestamo.getInteresMensual() * 100.0));
            pstmt.setInt(5, prestamo.getCuotas());
            java.sql.Date fechaDate = java.sql.Date.valueOf(prestamo.getFechaInicio());
            pstmt.setDate(6, fechaDate);
            pstmt.setString(7, prestamo.getEstado());
            pstmt.setBigDecimal(8, java.math.BigDecimal.valueOf(prestamo.getSaldoPendiente()));

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        prestamo.setId(generatedKeys.getInt(1));
                    }
                }
            }

            return prestamo;
        } catch (SQLException e) {
            System.err.println("Error al agregar prestamo: " + e.getMessage());
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
            System.err.println("Error al listar prestamos: " + e.getMessage());
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
            System.err.println("Error al obtener prestamo por ID: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public List<Prestamos> obtenerPorClienteId(int clienteId) {
        List<Prestamos> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos WHERE cliente_id = ?";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, clienteId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    prestamos.add(mapRowToPrestamo(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener prestamos por cliente ID: " + e.getMessage());
        }
        return prestamos;
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
            System.err.println("Error al cambiar estado del prestamo: " + e.getMessage());
        }
    }

    @Override
    public void actualizarSaldo(int id, double nuevoSaldo) {
        String sql = "UPDATE prestamos SET saldo_pendiente = ? WHERE id = ?";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBigDecimal(1, java.math.BigDecimal.valueOf(nuevoSaldo));
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar saldo del prestamo: " + e.getMessage());
        }
    }

    private Prestamos mapRowToPrestamo(ResultSet rs) throws SQLException {
        double interesPorcentaje = rs.getBigDecimal("interes").doubleValue();
        double interesMensual = interesPorcentaje / 100.0; 
        
        java.sql.Date fechaDate = rs.getDate("fecha_inicio");
        String fechaInicio = (fechaDate != null) ? fechaDate.toString() : "";
        
        double saldoPendiente = 0.0;
        java.math.BigDecimal saldoBD = rs.getBigDecimal("saldo_pendiente");
        if (saldoBD != null) {
            saldoPendiente = saldoBD.doubleValue();
        }
        
        return new Prestamos(
                rs.getInt("id"),
                rs.getInt("cliente_id"),
                rs.getInt("empleado_id"),
                rs.getBigDecimal("monto").doubleValue(),
                interesMensual,
                rs.getInt("cuotas"),
                fechaInicio,
                rs.getString("estado"),
                saldoPendiente);
    }
}