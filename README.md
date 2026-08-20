# Bootcamp Manager

## Descripción General
Bootcamp Manager es un sistema web académico desarrollado en Java para la gestión de estudiantes y sus inscripciones a cursos. Su propósito es didáctico, orientado a la enseñanza de patrones de arquitectura limpia, MVC y acceso a datos en aplicaciones empresariales modernas.

## Stack Tecnológico
- **Lenguaje:** Java 25 (LTS)
- **Servidor:** Apache Tomcat 11
- **Framework:** Jakarta EE 11 (Servlets, JSP)
- **Base de Datos:** MariaDB
- **Gestor de Dependencias:** Maven

## Análisis de la Estructura
- **controller:** Servlets que gestionan el flujo de la aplicación (EstudianteServlet, InscripcionServlet).
- **dao:** Acceso a datos y lógica de persistencia (EstudianteDAO, InscripcionDAO).
- **model:** DTOs que representan entidades de negocio (Estudiante, Inscripcion).
- **util:** Utilidades de infraestructura, como la conexión Singleton a la base de datos (DatabaseConnection).
- **resources:** Configuración externa de la aplicación (`db.properties`), separada del código fuente.
- **sql:** Script de creación de la base de datos y datos de prueba (`bootcamp_manager.sql`).

## Funcionalidades Descubiertas
### CRUD Completo de Estudiantes
- Listado, creación, edición y eliminación de estudiantes.
- El listado solo muestra estudiantes activos (`activo=1`).
- El formulario permite ingresar RUT, nombre, email y estado activo.
- Eliminar un estudiante elimina físicamente el registro, salvo que existan inscripciones asociadas (restricción de integridad referencial).

### Visualización de Inscripciones
- Desde el listado de estudiantes, se puede acceder a la vista de inscripciones de cada estudiante.
- Se muestra el curso y la fecha de inscripción, consultando la relación entre las tablas `estudiante`, `curso` e `inscripcion`.

### Funcionalidad Avanzada Detectada: Integridad Referencial y Restricción de Eliminación
- El sistema implementa protección a nivel de base de datos: si un estudiante tiene inscripciones asociadas, no puede ser eliminado (por la restricción FOREIGN KEY en la tabla `inscripcion`).
- El método `eliminar` en el DAO detecta esta restricción y el controlador muestra un mensaje al usuario en vez de fallar en silencio.
- Lo mismo aplica si se intenta guardar un RUT ya existente: se informa el conflicto en lugar de romper la operación.
- Esto garantiza la integridad de los datos y previene la pérdida accidental de información relacionada.

### Manejo de Errores
- Las vistas escapan los datos ingresados por el usuario (RUT, nombre, email) antes de mostrarlos, para evitar la inyección de HTML/JavaScript.
- Ante un error inesperado de base de datos, la aplicación redirige a una página de error genérica en vez de exponer detalles internos.

## Configuración Inicial
1. Ejecuta el script SQL `sql/bootcamp_manager.sql` para crear la base de datos y las tablas con datos de prueba.
2. Copia `src/main/resources/db.properties.example` a `src/main/resources/db.properties` y ajusta `db.url`, `db.user` y `db.password` según tu entorno de MariaDB (este archivo está en `.gitignore` y no se sube al repositorio).
3. Compila el proyecto con Maven y despliega el archivo WAR resultante en Apache Tomcat 11.

## Sprint Backlog - Tickets de Soporte

### Ticket #001 [Evolutivo]: "Políticas de Soft Delete"
**Descripción:** Implementar borrado lógico. Los registros no deben desaparecer de la BD.
**Requisitos:** Usar la columna activo, modificar el DELETE por un UPDATE y filtrar el listado principal.

### Ticket #002 [Mejora UX]: "Confirmación al Guardar"
**Descripción:** Al crear o editar un estudiante, el sistema redirige al listado sin ningún aviso de que la operación se realizó con éxito.
**Requisitos:** Mostrar un mensaje de confirmación tras guardar, reutilizando el mecanismo de mensajes ya usado para los errores.

### Ticket #003 [Validación]: "Formato de RUT"
**Descripción:** El campo RUT acepta cualquier texto sin validar su formato.
**Requisitos:** Validar el formato (y opcionalmente el dígito verificador) antes de guardar, informando al usuario si es inválido.

### Ticket #004 [Evolutivo]: "Estudiantes Inactivos"
**Descripción:** El listado principal solo muestra estudiantes activos; no hay forma de consultar los inactivos.
**Requisitos:** Agregar una vista o filtro para consultar los estudiantes con `activo = 0`.

### Ticket #005 [Mantenimiento Correctivo]: "Fallo en flujo de Actualización"
**Descripción:** Al intentar editar un alumno, el sistema no actualiza el registro correctamente: si el RUT no cambia, se informa un conflicto; si el RUT cambia, se crea un registro nuevo en vez de modificar el existente.
**Contexto:** Analizar la comunicación entre estudiante-form.jsp y el método doPost del Servlet.

### Ticket #006 [Evolutivo]: "Buscador de Alumnos por RUT"
**Descripción:** El cliente requiere filtrar la tabla de alumnos por su RUT.
**Requisitos:** Crear buscarPorRut() en el DAO y capturar el parámetro en el controlador.

### Ticket #007 [Funcionalidad Nueva]: "Login y Gestión de Sesión"
**Descripción:** La aplicación no valida ningún tipo de acceso: cualquiera puede entrar directo a `/estudiantes` sin autenticarse.
**Requisitos:** Implementar una pantalla de login, un servlet que valide credenciales y mantenga el estado con `HttpSession`, y proteger el acceso a las vistas de gestión para usuarios no autenticados.
