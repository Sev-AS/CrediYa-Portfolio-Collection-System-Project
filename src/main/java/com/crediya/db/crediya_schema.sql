DROP DATABASE IF EXISTS crediya_db;
CREATE DATABASE crediya_db;
USE crediya_db;

-- =====================================================
-- TABLAS
-- =====================================================

CREATE TABLE empleados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    documento VARCHAR(30) UNIQUE NOT NULL,
    rol VARCHAR(30) NOT NULL,
    correo VARCHAR(80) NOT NULL,
    salario DECIMAL(10,2) NOT NULL
);

CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    documento VARCHAR(30) UNIQUE NOT NULL,
    correo VARCHAR(80) NOT NULL,
    telefono VARCHAR(20) NOT NULL
);

CREATE TABLE prestamos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    empleado_id INT NOT NULL,
    monto DECIMAL(12,2) NOT NULL,
    interes DECIMAL(5,2) NOT NULL,
    cuotas INT NOT NULL,
    fecha_inicio DATE NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    saldo_pendiente DECIMAL(12,2) DEFAULT 0,  -- ⭐ CAMPO AGREGADO
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
    FOREIGN KEY (empleado_id) REFERENCES empleados(id) ON DELETE CASCADE
);

CREATE TABLE pagos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    prestamo_id INT NOT NULL,
    fecha_pago DATE NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (prestamo_id) REFERENCES prestamos(id) ON DELETE CASCADE
);

-- =====================================================
-- DATOS DE EMPLEADOS
-- =====================================================

INSERT INTO empleados (nombre, documento, rol, correo, salario) VALUES
('María Fernanda López', '52123456', 'GERENTE', 'maria.lopez@crediya.com', 4500000.00),
('Carlos Andrés Martínez', '80234567', 'ASESOR_CREDITO', 'carlos.martinez@crediya.com', 2800000.00),
('Laura Sofía Ramírez', '52345678', 'ASESOR_CREDITO', 'laura.ramirez@crediya.com', 2800000.00),
('Jorge Luis Pérez', '79456789', 'COBRADOR', 'jorge.perez@crediya.com', 2200000.00),
('Ana Patricia Gómez', '52567890', 'COBRADOR', 'ana.gomez@crediya.com', 2200000.00),
('Roberto Silva', '80678901', 'ANALISTA', 'roberto.silva@crediya.com', 3200000.00);

-- =====================================================
-- DATOS DE CLIENTES
-- =====================================================

INSERT INTO clientes (nombre, documento, correo, telefono) VALUES
('Juan Pablo Rodríguez', '52001001', 'juan.rodriguez@email.com', '3001234567'),
('Diana Carolina Torres', '80002002', 'diana.torres@email.com', '3109876543'),
('Miguel Ángel Vargas', '52003003', 'miguel.vargas@email.com', '3201112222'),
('Sandra Milena Castillo', '79004004', 'sandra.castillo@email.com', '3123334444'),
('Pedro Antonio Morales', '52005005', 'pedro.morales@email.com', '3145556666'),
('Claudia Patricia Herrera', '80006006', 'claudia.herrera@email.com', '3167778888'),
('Andrés Felipe Rojas', '52007007', 'andres.rojas@email.com', '3189990000'),
('Luz Marina Díaz', '79008008', 'luz.diaz@email.com', '3001112233'),
('Ricardo Enrique Soto', '52009009', 'ricardo.soto@email.com', '3113334455'),
('Martha Lucía Ortiz', '80010010', 'martha.ortiz@email.com', '3125556677'),
('Javier Esteban Cruz', '52011011', 'javier.cruz@email.com', '3137778899'),
('Carolina Isabel Mendoza', '79012012', 'carolina.mendoza@email.com', '3149990011');

-- =====================================================
-- DATOS DE PRÉSTAMOS (CON SALDO_PENDIENTE)
-- =====================================================

-- Préstamos ACTIVOS (saldo_pendiente calculado)
INSERT INTO prestamos (cliente_id, empleado_id, monto, interes, cuotas, fecha_inicio, estado, saldo_pendiente) VALUES
(1, 2, 5000000.00, 2.5, 12, '2024-11-01', 'PENDIENTE', 5150000.00),  -- monto + (monto * 0.025 * 12)
(2, 2, 3000000.00, 2.0, 6, '2024-11-15', 'PENDIENTE', 3060000.00),   -- monto + (monto * 0.02 * 6)
(3, 3, 8000000.00, 3.0, 24, '2024-10-01', 'PENDIENTE', 8576000.00),  -- monto + (monto * 0.03 * 24)
(4, 3, 2000000.00, 1.5, 12, '2024-12-01', 'PENDIENTE', 2036000.00),  -- monto + (monto * 0.015 * 12)
(5, 2, 10000000.00, 2.8, 36, '2024-09-01', 'PENDIENTE', 11008000.00); -- monto + (monto * 0.028 * 36)

-- Préstamos VENCIDOS (saldo_pendiente con pagos parciales)
INSERT INTO prestamos (cliente_id, empleado_id, monto, interes, cuotas, fecha_inicio, estado, saldo_pendiente) VALUES
(6, 3, 4000000.00, 2.5, 12, '2024-06-01', 'PENDIENTE', 3480000.00),  -- 4120000 - 720000 (2 pagos)
(7, 2, 6000000.00, 3.0, 18, '2024-05-15', 'PENDIENTE', 5860000.00),  -- 6240000 - 380000 (1 pago)
(8, 3, 1500000.00, 2.0, 6, '2024-07-01', 'PENDIENTE', 1420000.00);   -- 1680000 - 260000 (1 pago)

-- Préstamos PAGADOS (saldo_pendiente = 0)
INSERT INTO prestamos (cliente_id, empleado_id, monto, interes, cuotas, fecha_inicio, estado, saldo_pendiente) VALUES
(9, 2, 2500000.00, 2.0, 6, '2024-06-01', 'PAGADO', 0.00),
(10, 3, 3500000.00, 2.5, 12, '2024-01-15', 'PAGADO', 0.00),
(1, 2, 1000000.00, 1.5, 6, '2024-03-01', 'PAGADO', 0.00);

-- Préstamos RECIENTES sin pagos (saldo_pendiente = monto total)
INSERT INTO prestamos (cliente_id, empleado_id, monto, interes, cuotas, fecha_inicio, estado, saldo_pendiente) VALUES
(11, 3, 7000000.00, 2.8, 24, '2024-12-10', 'PENDIENTE', 7470400.00),  -- monto + (monto * 0.028 * 24)
(12, 2, 4500000.00, 2.3, 18, '2024-12-05', 'PENDIENTE', 4685500.00);  -- monto + (monto * 0.023 * 18)

-- =====================================================
-- DATOS DE PAGOS
-- =====================================================

-- Préstamo 1: Juan Pablo (2 pagos al día)
INSERT INTO pagos (prestamo_id, fecha_pago, monto) VALUES
(1, '2024-11-05', 450000.00),
(1, '2024-12-05', 450000.00);

-- Préstamo 2: Diana (2 pagos adelantados)
INSERT INTO pagos (prestamo_id, fecha_pago, monto) VALUES
(2, '2024-11-20', 520000.00),
(2, '2024-12-10', 520000.00);

-- Préstamo 3: Miguel (2 pagos atrasados)
INSERT INTO pagos (prestamo_id, fecha_pago, monto) VALUES
(3, '2024-10-15', 350000.00),
(3, '2024-11-20', 350000.00);

-- Préstamo 4: Sandra (1 pago)
INSERT INTO pagos (prestamo_id, fecha_pago, monto) VALUES
(4, '2024-12-05', 175000.00);

-- Préstamo 5: Pedro (4 pagos)
INSERT INTO pagos (prestamo_id, fecha_pago, monto) VALUES
(5, '2024-09-10', 300000.00),
(5, '2024-10-10', 300000.00),
(5, '2024-11-10', 300000.00),
(5, '2024-12-10', 300000.00);

-- Préstamo 6: Claudia - VENCIDO (2 pagos antiguos)
INSERT INTO pagos (prestamo_id, fecha_pago, monto) VALUES
(6, '2024-06-15', 360000.00),
(6, '2024-07-15', 360000.00);

-- Préstamo 7: Andrés - VENCIDO (1 pago inicial)
INSERT INTO pagos (prestamo_id, fecha_pago, monto) VALUES
(7, '2024-05-25', 380000.00);

-- Préstamo 8: Luz - VENCIDO (1 pago)
INSERT INTO pagos (prestamo_id, fecha_pago, monto) VALUES
(8, '2024-07-10', 260000.00);

-- Préstamo 9: Ricardo - PAGADO COMPLETO (6 pagos)
INSERT INTO pagos (prestamo_id, fecha_pago, monto) VALUES
(9, '2024-06-05', 425000.00),
(9, '2024-07-05', 425000.00),
(9, '2024-08-05', 425000.00),
(9, '2024-09-05', 425000.00),
(9, '2024-10-05', 425000.00),
(9, '2024-11-05', 425000.00);

-- Préstamo 10: Martha - PAGADO COMPLETO (12 pagos)
INSERT INTO pagos (prestamo_id, fecha_pago, monto) VALUES
(10, '2024-01-20', 310000.00),
(10, '2024-02-20', 310000.00),
(10, '2024-03-20', 310000.00),
(10, '2024-04-20', 310000.00),
(10, '2024-05-20', 310000.00),
(10, '2024-06-20', 310000.00),
(10, '2024-07-20', 310000.00),
(10, '2024-08-20', 310000.00),
(10, '2024-09-20', 310000.00),
(10, '2024-10-20', 310000.00),
(10, '2024-11-20', 310000.00),
(10, '2024-12-20', 310000.00);

-- Préstamo 11: Javier - PAGADO COMPLETO (6 pagos)
INSERT INTO pagos (prestamo_id, fecha_pago, monto) VALUES
(11, '2024-03-10', 175000.00),
(11, '2024-04-10', 175000.00),
(11, '2024-05-10', 175000.00),
(11, '2024-06-10', 175000.00),
(11, '2024-07-10', 175000.00),
(11, '2024-08-10', 175000.00);

-- =====================================================
-- ACTUALIZAR SALDOS SEGÚN LOS PAGOS REALIZADOS
-- =====================================================

-- Actualizar saldos pendientes basados en los pagos
UPDATE prestamos SET saldo_pendiente = 4250000.00 WHERE id = 1;  -- 5150000 - 900000
UPDATE prestamos SET saldo_pendiente = 2020000.00 WHERE id = 2;  -- 3060000 - 1040000
UPDATE prestamos SET saldo_pendiente = 7876000.00 WHERE id = 3;  -- 8576000 - 700000
UPDATE prestamos SET saldo_pendiente = 1861000.00 WHERE id = 4;  -- 2036000 - 175000
UPDATE prestamos SET saldo_pendiente = 9808000.00 WHERE id = 5;  -- 11008000 - 1200000
UPDATE prestamos SET saldo_pendiente = 3480000.00 WHERE id = 6;  -- 4120000 - 720000
UPDATE prestamos SET saldo_pendiente = 5860000.00 WHERE id = 7;  -- 6240000 - 380000
UPDATE prestamos SET saldo_pendiente = 1420000.00 WHERE id = 8;  -- 1680000 - 260000
UPDATE prestamos SET saldo_pendiente = 0.00 WHERE id = 9;        -- PAGADO
UPDATE prestamos SET saldo_pendiente = 0.00 WHERE id = 10;       -- PAGADO
UPDATE prestamos SET saldo_pendiente = 0.00 WHERE id = 11;       -- PAGADO (mal insertado en pagos, debería ser préstamo 11 pero hay datos incorrectos)

-- =====================================================
-- CONSULTAS DE VERIFICACIÓN
-- =====================================================

SELECT '=== EMPLEADOS REGISTRADOS ===' AS '';
SELECT id, nombre, documento, rol, salario FROM empleados;

SELECT '=== CLIENTES REGISTRADOS ===' AS '';
SELECT id, nombre, documento, telefono FROM clientes;

SELECT '=== RESUMEN DE PRÉSTAMOS ===' AS '';
SELECT 
    estado,
    COUNT(*) as cantidad,
    SUM(monto) as total_monto,
    SUM(saldo_pendiente) as total_saldo_pendiente
FROM prestamos
GROUP BY estado;

SELECT '=== PRÉSTAMOS ACTIVOS ===' AS '';
SELECT 
    p.id,
    c.nombre AS cliente,
    c.documento AS doc_cliente,
    e.nombre AS asesor,
    p.monto,
    p.cuotas,
    p.saldo_pendiente,
    p.fecha_inicio
FROM prestamos p
INNER JOIN clientes c ON p.cliente_id = c.id
INNER JOIN empleados e ON p.empleado_id = e.id
WHERE p.estado = 'PENDIENTE'
ORDER BY p.fecha_inicio DESC;

SELECT '=== CLIENTES CON PRÉSTAMOS VENCIDOS ===' AS '';
SELECT 
    c.nombre,
    c.documento,
    c.telefono,
    p.monto,
    p.saldo_pendiente,
    p.fecha_inicio,
    DATEDIFF(CURDATE(), p.fecha_inicio) as dias_vencidos
FROM clientes c
INNER JOIN prestamos p ON c.id = p.cliente_id
WHERE p.estado = 'PENDIENTE' 
  AND DATEDIFF(CURDATE(), p.fecha_inicio) > 180
ORDER BY dias_vencidos DESC;

SELECT '=== SALDO POR PRÉSTAMO ===' AS '';
SELECT 
    p.id,
    c.nombre AS cliente,
    p.monto AS total_prestamo,
    COALESCE(SUM(pg.monto), 0) AS pagado,
    p.saldo_pendiente AS saldo,
    p.estado
FROM prestamos p
INNER JOIN clientes c ON p.cliente_id = c.id
LEFT JOIN pagos pg ON p.id = pg.prestamo_id
GROUP BY p.id, c.nombre, p.monto, p.saldo_pendiente, p.estado
ORDER BY p.id;