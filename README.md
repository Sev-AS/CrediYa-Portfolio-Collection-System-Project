## CREDIYA - Sistema de Gestión de Créditos

Sistema de gestión de préstamos desarrollado en Java 17 con persistencia dual (base de datos MySQL y archivos de texto). Orientado a instituciones financieras o proyectos académicos que necesitan administrar clientes, empleados, préstamos, pagos y reportes desde una aplicación de consola.

***

## Descripción general

CREDIYA permite gestionar el ciclo completo de un crédito: registro de clientes y empleados, creación de préstamos con cálculo automático de cuotas, registro de pagos con actualización de saldo y generación de reportes sobre el estado de la cartera.  
El sistema aplica principios de programación orientada a objetos, uso de colecciones de Java, persistencia con JDBC y programación funcional mediante Streams API.

***

## Características principales

- CRUD completo para clientes, empleados y préstamos.  
- Registro de pagos con actualización automática del saldo pendiente.  
- Cambio de estado del préstamo según el saldo (por ejemplo: Activo, Vencido, Pagado).  
- Persistencia dual configurable:
  - Modo base de datos MySQL.
  - Modo archivos de texto (formato tipo CSV con separador `;`).  
- Reportes de:
  - Préstamos activos.  
  - Préstamos vencidos.  
  - Clientes morosos.  
- Implementación de filtros y cálculos con Streams API y expresiones Lambda.  
- Cálculo automático de monto total y cuota mensual utilizando interés simple.

***

## Arquitectura del proyecto

Estructura de paquetes (ejemplo):

```text
com/crediya/
├── app/
│   └── App.java
├── clientes/
│   ├── Cliente.java
│   ├── ClienteRepository.java
│   ├── ClienteRepositoryJdbc.java
│   ├── ClienteRepositoryArchivo.java
│   └── MenuClientes.java
├── empleados/
│   ├── Empleado.java
│   ├── EmpleadoRepository.java
│   ├── EmpleadoRepositoryJdbc.java
│   ├── EmpleadoRepositoryArchivo.java
│   └── MenuEmpleados.java
├── prestamos/
│   ├── Prestamos.java
│   ├── PrestamoRepository.java
│   ├── PrestamoRepositoryJdbc.java
│   ├── PrestamoRepositoryArchivo.java
│   └── MenuPrestamos.java
├── pagos/
│   ├── Pagos.java
│   ├── PagosRepository.java
│   ├── PagosRepositoryJdbc.java
│   ├── PagosRepositoryArchivo.java
│   └── MenuPagos.java
├── reportes/
│   ├── MenuReportes.java
│   ├── ReporteManager.java
│   └── ReportePagos.java
└── common/
    ├── DatabaseConnection.java
    └── Persona.java
```

Patrones y principios utilizados:

- Repository: abstracción de la capa de persistencia.  
- Strategy: selección entre persistencia JDBC y archivos de texto.  
- Herencia mediante la clase base abstracta `Persona` para Cliente y Empleado.  
- Separación por capas: dominio, persistencia y presentación (menús por consola).  

***

## Requisitos

- Java JDK 17 o superior.  
- MySQL 8.0 o superior.  
- Maven (opcional) para la gestión de dependencias y construcción del proyecto.  
- IDE recomendado: IntelliJ IDEA, Eclipse, NetBeans o VS Code.

***

## Instalación y configuración

1. Clonar o descargar el repositorio del proyecto.  
2. Crear la base de datos ejecutando el script SQL proporcionado (por ejemplo `crediya_db_script.sql`) en MySQL.  
3. Configurar las credenciales de conexión en `DatabaseConnection.java`, por ejemplo:

   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/crediya_db";
   private static final String USER = "tu_usuario";
   private static final String PASSWORD = "tu_contraseña";
   ```

4. Seleccionar el modo de persistencia en `App.java`, modificando la constante:

   ```java
   // true = Base de datos (JDBC), false = Archivos de texto
   private static final boolean USAR_JDBC = true;
   ```

5. Compilar el proyecto (por ejemplo con Maven o desde el IDE).

***

## Ejecución

Desde línea de comandos (estructura orientativa):

```bash
# Compilar (si se usa Maven)
mvn clean compile

# Ejecutar
mvn exec:java -Dexec.mainClass="com.crediya.app.App"
```

O bien, desde el IDE:

- Importar el proyecto.  
- Configurar el JDK 17 o superior.  
- Ejecutar la clase `App` como aplicación Java.

***

## Uso básico

Flujo típico:

1. Registrar empleados desde el menú de empleados.  
2. Registrar clientes desde el menú de clientes.  
3. Crear préstamos:
   - Asociar cliente y empleado existentes.  
   - Ingresar monto, tasa de interés mensual en formato decimal (por ejemplo 0.05 equivale a 5%), número de cuotas y fecha de inicio.  
4. Registrar pagos sobre un préstamo:
   - El sistema actualiza el saldo pendiente.
   - Cuando el saldo llega a cero, el estado del préstamo pasa a Pagado.  
5. Generar reportes desde el menú de reportes para:
   - Listar préstamos activos.  
   - Listar préstamos vencidos.  
   - Listar clientes morosos.

***

## Persistencia de datos

Modo base de datos (JDBC):

- Almacenamiento en una base MySQL.  
- Uso de transacciones en operaciones críticas (por ejemplo registro de pagos y actualización de saldo).  
- Aprovecha integridad referencial y tipos de datos propios de la base de datos.

Modo archivos de texto:

- Almacenamiento en ficheros de texto con formato similar a CSV (separador `;`).  
- Pensado para entornos sin base de datos o contextos educativos.  
- Ficheros habituales: `clientes.txt`, `empleados.txt`, `prestamos.txt`, `pagos.txt`.

***

## Cálculo de cuotas (interés simple)

El sistema utiliza interés simple para el cálculo de las cuotas de los préstamos:

- Interés total = monto × tasa mensual × número de cuotas.  
- Monto total = monto + interés total.  
- Cuota mensual = monto total ÷ número de cuotas.
