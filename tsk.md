Como desarrollador en la empresa CrediYa S.A.S., debes continuar con la implementación del sistema de cobros, agregando una nueva funcionalidad enfocada en la gestión de préstamos y el cálculo de intereses mensuales.


El objetivo es aplicar POO, herencia, colecciones, manejo de excepciones, y conexión a MySQL con JDBC, garantizando una lógica coherente con los pagos implementados previamente.


Debes realizar lo siguiente:

    1. Clase Prestamo
    Crea una clase Prestamo con los siguientes atributos:
    id (int)
    clienteId (int)
    monto (double)
    interesMensual (double)
    plazoMeses (int)
    estado (String) — valores posibles: "Activo", "Pagado", "Mora"
    Incluye:
    Constructores.
    Getters y setters correctamente encapsulados.
    Método calcularTotalAPagar() que retorne el monto total a pagar (monto + intereses).
    Sobrescribe toString() para mostrar la información del préstamo.

    Fórmula sugerida:
    total = monto + (monto * interesMensual/100 * plazoMeses)


    2. Gestión de préstamos en memoria
    Crea una clase GestorPrestamos que maneje una lista (ArrayList) de objetos Prestamo, con los siguientes métodos:
    registrarPrestamo(Prestamo p)
    Valida que el monto y el plazo sean mayores que 0.
    Si no, lanza una excepción personalizada DatoInvalidoException.
    listarPrestamos()
    Muestra en consola todos los préstamos con su total calculado.


     3. Persistencia con JDBC
    Agrega un método guardarPrestamoEnBD(Prestamo p) que:
    Inserte el préstamo en la tabla prestamos dentro de la base de datos crediya_db.
    Use la clase ConexionBD (Singleton) ya implementada.


Script base para la tabla:


CREATE TABLE prestamos (

  id INT AUTO_INCREMENT PRIMARY KEY,

  cliente_id INT,

  monto DECIMAL(10,2),

  interes_mensual DECIMAL(5,2),

  plazo_meses INT,

  estado VARCHAR(20),

  FOREIGN KEY (cliente_id) REFERENCES clientes(id)

);


    4. Búsqueda y filtrado con Stream API
    Implementa un método buscarPorEstado(String estado) que:
    Use Stream API y expresiones lambda para listar los préstamos cuyo estado coincida con el parámetro.


Ejemplo de ejecución esperada

=== REGISTRO DE PRÉSTAMOS ===

Préstamo registrado correctamente.


=== LISTADO DE PRÉSTAMOS ===

ID: 1 - Cliente: 5 - Monto: 500000.0 - Interés: 2.5% - Plazo: 6 meses - Total: 575000.0


=== PRÉSTAMOS EN ESTADO 'Activo' ===

Préstamo ID 1 - Estado: Activo