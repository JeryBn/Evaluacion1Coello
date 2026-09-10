# Historia Clinica - Modulo 5

API REST y aplicacion web con Spring Boot, Thymeleaf, JPA e Hibernate para el laboratorio de Historia Clinica. El proyecto se organiza por capas (`Controller`, `Service` y `Repository`) y usa una unica base de datos compartida por el equipo.

## Modulos integrados

| Modulo | Responsable | Estado | Rutas principales |
| --- | --- | --- | --- |
| Pacientes e Historia Clinica | Equipo | Integrado | `/historias-clinicas`, `/api/pacientes`, `/api/historias-clinicas` |
| Atenciones iniciales | Equipo | Integrado | `/historias-clinicas/{id}/atenciones` |
| Atencion Medica (RF-HC-08 a RF-HC-15) | Coello | Integrado | `/atencion-medica`, `/api/atencion-medica` |
| Antecedentes y alergias | Equipo | Integrado | `/historias-clinicas/{id}/antecedentes`, `/api/historias-clinicas/{id}/antecedentes`, `/api/historias-clinicas/{id}/alergias` |

La integracion vigente usa una sola entidad compartida: una `ConsultaMedica` pertenece a una `HistoriaClinica` creada desde el modulo de Pacientes e Historia Clinica. No se deben crear historias clinicas desde el modulo de Atencion Medica.

## Organizacion del repositorio

- La aplicacion integrada y ejecutable se encuentra en la raiz del repositorio: `src/`, `pom.xml`, `database/` y `postman/`.
- La carpeta `antecedentes/` conserva el proyecto original entregado por el integrante responsable. Sus funcionalidades ya estan integradas en la aplicacion principal bajo `src/main/java/com/tecsup/historiaclinica/antecedentes/`; no debe iniciarse como una segunda aplicacion.
- La navegacion de Thymeleaf permite recorrer Inicio, Historias clinicas, Atenciones, Antecedentes y alergias, y Atencion medica desde una sola instancia de Spring Boot.

## Requerimientos implementados

### Pacientes, historia clinica y atenciones iniciales

- Registro y consulta de pacientes.
- Creacion de una historia clinica por paciente.
- Registro y listado de atenciones asociadas a una historia clinica.
- Vistas Thymeleaf para crear, consultar y listar historias clinicas.

### Atencion Medica

- **RF-HC-08:** registrar consulta medica.
- **RF-HC-09:** registrar motivo de consulta.
- **RF-HC-10:** registrar anamnesis.
- **RF-HC-11:** registrar examen fisico.
- **RF-HC-12:** registrar signos vitales.
- **RF-HC-13:** registrar diagnosticos.
- **RF-HC-14:** registrar tratamientos.
- **RF-HC-15:** registrar evoluciones medicas.

### Antecedentes y alergias

- Registro de antecedentes personales y familiares asociados a una historia clinica existente.
- Registro de alergias asociado a la misma historia clinica.
- Vistas Thymeleaf y endpoints REST integrados en la aplicacion principal.

## Base de datos

Todos los modulos usan la base `historia_clinica` en MySQL/MariaDB de XAMPP.

La exportacion completa y verificable de la estructura integrada se encuentra en [`database/historia_clinica.sql`](database/historia_clinica.sql). Incluye las tablas y relaciones de Pacientes, Historia Clinica, Atenciones, Antecedentes, Alergias y Atencion Medica.

```sql
CREATE DATABASE IF NOT EXISTS historia_clinica
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
```

Configuracion predeterminada en `src/main/resources/application.properties`:

```text
URL: jdbc:mysql://localhost:3306/historia_clinica
Usuario: root
Contrasena: vacia
```

Hibernate crea y actualiza las tablas. La relacion principal de la integracion es:

```text
paciente -> historia_clinica -> consultas_medicas
                                  -> signos_vitales
                                  -> diagnosticos
                                  -> tratamientos
                                  -> evoluciones_medicas
                         -> antecedente
                         -> alergia
```

La tabla `historias_clinicas` se conserva temporalmente sin datos por compatibilidad con pruebas anteriores; el flujo actual no la usa. No debe usarse para registros nuevos.

## Ejecutar el proyecto

1. Iniciar MySQL desde XAMPP.
2. Confirmar que existe la base `historia_clinica` en MySQL Workbench.
3. Desde la raiz del proyecto, ejecutar con Java 21:

```powershell
.\mvnw.cmd spring-boot:run
```

4. Abrir [http://localhost:8080](http://localhost:8080).

Desde el inicio se puede acceder a:

- **Historias clinicas y atenciones iniciales:** crear paciente, abrir su historia clinica y registrar una atencion inicial.
- **Atencion medica:** usar el ID de una historia clinica ya creada para registrar la consulta, signos vitales, diagnosticos, tratamientos y evoluciones.

Para registrar un paciente desde la interfaz, abrir **Historias clinicas y atenciones iniciales**, seleccionar **Crear nueva historia clinica** y usar el enlace **Registrar nuevo paciente**. Al guardar, el paciente aparecera en el selector para crear su historia clinica.

## Flujo de prueba integrado

1. Crear un paciente: `POST /api/pacientes`.
2. Crear la historia del paciente: `POST /api/historias-clinicas/paciente/{pacienteId}`.
3. Registrar una consulta: `POST /api/atencion-medica/consultas`, enviando el `historiaClinicaId` recibido.
4. Registrar signos vitales, diagnosticos, tratamientos y evoluciones con el `consultaId` recibido.
5. Consultar las atenciones medicas de una historia: `GET /api/atencion-medica/historias-clinicas/{historiaClinicaId}/consultas`.

La coleccion de Postman para la parte de Atencion Medica se encuentra en `postman/Atencion-Medica.postman_collection.json`. Antes de ejecutar sus solicitudes, crear primero el paciente y la historia con los endpoints del modulo compartido.

## Endpoints principales

| Metodo | Ruta | Uso |
| --- | --- | --- |
| `POST` | `/api/pacientes` | Crear paciente |
| `POST` | `/api/historias-clinicas/paciente/{pacienteId}` | Crear historia clinica |
| `GET` | `/api/historias-clinicas` | Listar historias clinicas |
| `POST` | `/api/atencion-medica/consultas` | Registrar consulta medica |
| `GET` | `/api/atencion-medica/historias-clinicas/{id}/consultas` | Listar consultas de una historia |
| `POST` | `/api/atencion-medica/consultas/{id}/signos-vitales` | Registrar signos vitales |
| `POST` | `/api/atencion-medica/consultas/{id}/diagnosticos` | Registrar diagnostico |
| `POST` | `/api/atencion-medica/consultas/{id}/tratamientos` | Registrar tratamiento |
| `POST` | `/api/atencion-medica/consultas/{id}/evoluciones` | Registrar evolucion |
| `PATCH` | `/api/atencion-medica/consultas/{id}/cerrar` | Cerrar consulta |
| `GET` | `/api/historias-clinicas/{id}/antecedentes` | Listar antecedentes de una historia |
| `POST` | `/api/historias-clinicas/{id}/antecedentes` | Registrar antecedente personal o familiar |
| `GET` | `/api/historias-clinicas/{id}/alergias` | Listar alergias de una historia |
| `POST` | `/api/historias-clinicas/{id}/alergias` | Registrar alergia |

## Rutas web Thymeleaf

| Ruta | Uso |
| --- | --- |
| `/` | Inicio y acceso a los modulos |
| `/historias-clinicas` | Listar historias y crear pacientes o historias clinicas |
| `/historias-clinicas/paciente/{pacienteId}` | Ver detalle de una historia clinica |
| `/historias-clinicas/{id}/atenciones` | Registrar y consultar atenciones iniciales |
| `/historias-clinicas/{id}/antecedentes` | Registrar y consultar antecedentes y alergias |
| `/atencion-medica` | Registrar consultas medicas y sus componentes clinicos |

## Reglas de integracion del equipo

1. Antes de empezar, actualizar la rama principal con `git pull origin main`.
2. Cada integrante trabaja en una rama con nombre descriptivo, por ejemplo `feature/antecedentes`.
3. No duplicar entidades compartidas como `Paciente` o `HistoriaClinica`; reutilizar sus clases y relaciones existentes.
4. Mantener cada funcionalidad en sus capas Controller, Service y Repository.
5. Probar que el proyecto inicia y que el nuevo modulo funciona con `historia_clinica` antes de subirlo.
6. Actualizar este README en el mismo cambio cuando se agregue, modifique o integre un modulo, una ruta, una relacion o una instruccion de ejecucion.
7. Antes de mezclar cambios, revisar nombres de rutas, entidades, tablas y repositorios para evitar duplicados.

La persona que realice la integracion final revisara los cambios antes de incorporarlos a `main`, ordenara paquetes o nombres duplicados cuando sea necesario y dejara este documento actualizado.
