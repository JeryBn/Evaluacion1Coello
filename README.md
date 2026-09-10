# Sistema de Historia Clinica - Modulo 5

Aplicacion web y API REST desarrollada para el laboratorio **Implementacion de Persistencia con Spring Boot e Hibernate**. El equipo construyo un unico sistema de Historia Clinica con Spring Boot, Thymeleaf, JPA, Hibernate y MySQL/MariaDB.

El proyecto aplica una arquitectura en capas:

```text
Controller -> Service -> Repository -> Base de datos
```

La aplicacion utiliza Inversion de Control (IoC), Inyeccion de Dependencias (DI), persistencia con JPA/Hibernate, endpoints REST y vistas web con Thymeleaf.

## Integracion del equipo

El trabajo de los tres integrantes se encuentra integrado en una sola aplicacion Spring Boot que se ejecuta desde la raiz del repositorio. Todos los modulos comparten la base de datos `historia_clinica` y reutilizan las entidades `Paciente` e `HistoriaClinica`.

| Parte del trabajo | Requerimientos | Integracion actual |
| --- | --- | --- |
| Pacientes, historia clinica y atenciones iniciales | RF-HC-01 a RF-HC-04 | Registro de pacientes, apertura, consulta y atenciones de la historia clinica. |
| Antecedentes y alergias | RF-HC-05 a RF-HC-07 | Registro y consulta de antecedentes personales, familiares y alergias. |
| Atencion medica | RF-HC-08 a RF-HC-15 | Consultas, signos vitales, diagnosticos, tratamientos y evoluciones. |

La carpeta `antecedentes/` conserva el proyecto original subido por el integrante responsable. Para ejecutar el sistema integrado se usa solamente el `pom.xml` de la raiz. La funcionalidad de antecedentes y alergias ya esta incorporada en `src/main/java/com/tecsup/historiaclinica/antecedentes/`.

## Requerimientos funcionales implementados

| Requerimiento | Implementacion |
| --- | --- |
| RF-HC-01 | Generacion de un numero unico de historia clinica por paciente. |
| RF-HC-02 | Consulta de la historia clinica de un paciente. |
| RF-HC-03 | Visualizacion de los datos basicos del paciente asociado a su historia. |
| RF-HC-04 | Registro y mantenimiento del historial de atenciones iniciales. |
| RF-HC-05 | Registro de antecedentes personales. |
| RF-HC-06 | Registro de antecedentes familiares. |
| RF-HC-07 | Registro de alergias del paciente. |
| RF-HC-08 | Registro de consulta medica asociada a una historia clinica. |
| RF-HC-09 | Registro del motivo de consulta. |
| RF-HC-10 | Registro de anamnesis. |
| RF-HC-11 | Registro de examen fisico y evaluacion clinica. |
| RF-HC-12 | Registro de signos vitales. |
| RF-HC-13 | Registro, consulta, actualizacion y eliminacion de diagnosticos. |
| RF-HC-14 | Registro, consulta, actualizacion y eliminacion de tratamientos. |
| RF-HC-15 | Registro, consulta, actualizacion y eliminacion de evoluciones medicas. |

## Arquitectura y relaciones

Cada funcionalidad se implementa con sus capas Controller, Service y Repository. Las relaciones principales persistidas por Hibernate son:

```text
Paciente (1) -------- (1) HistoriaClinica
                            |
                            +--- (N) Atencion
                            +--- (N) Antecedente
                            +--- (N) Alergia
                            +--- (N) ConsultaMedica
                                      |
                                      +--- (1) SignosVitales
                                      +--- (N) Diagnostico
                                      +--- (N) Tratamiento
                                      +--- (N) EvolucionMedica
```

La relacion `HistoriaClinica -> ConsultaMedica` conecta el modulo de Atencion Medica con el trabajo de Historia Clinica. Por eso, primero se debe crear el paciente y su historia antes de registrar una consulta medica, antecedente o alergia.

## Base de datos

La base compartida se llama `historia_clinica` y esta configurada para MySQL/MariaDB de XAMPP.

- Esquema SQL integrado: [database/historia_clinica.sql](database/historia_clinica.sql)
- Instrucciones de restauracion: [database/README.md](database/README.md)
- El archivo SQL contiene las tablas, claves primarias y claves foraneas del proyecto; no contiene datos personales ni registros clinicos de prueba.

Tablas incluidas en el esquema:

```text
paciente
historia_clinica
atencion
antecedente
alergia
consultas_medicas
signos_vitales
diagnosticos
tratamientos
evoluciones_medicas
```

Configuracion predeterminada en `src/main/resources/application.properties`:

```text
Base de datos: historia_clinica
URL: jdbc:mysql://localhost:3306/historia_clinica
Usuario: root
Contrasena: vacia
Puerto web: 8080
```

Hibernate usa `spring.jpa.hibernate.ddl-auto=update`, por lo que puede crear o ajustar las tablas al iniciar la aplicacion. Para una revision del profesor, tambien se puede importar el esquema SQL desde MySQL Workbench.

## Requisitos para ejecutar

- Java 21.
- Maven 3.9 o superior.
- MySQL o MariaDB. En este proyecto se utilizo XAMPP.
- Una base llamada `historia_clinica`.

## Ejecucion local

1. Iniciar MySQL desde XAMPP.
2. Crear la base de datos si aun no existe:

```sql
CREATE DATABASE IF NOT EXISTS historia_clinica
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
```

3. Opcionalmente, importar `database/historia_clinica.sql` desde MySQL Workbench para revisar la estructura completa.
4. Abrir una terminal en la raiz del repositorio.
5. Ejecutar:

```powershell
./mvnw.cmd spring-boot:run
```

Si el puerto `8080` esta ocupado por otra aplicacion, ejecutar en un puerto libre:

```powershell
./mvnw.cmd spring-boot:run "-Dspring-boot.run.arguments=--server.port=8081"
```

6. Abrir la aplicacion en [http://localhost:8080](http://localhost:8080) o, si se uso el puerto alternativo, [http://localhost:8081](http://localhost:8081).

## Navegacion web con Thymeleaf

La interfaz incluye accesos de Inicio, Historias clinicas, Atencion medica, Atras y Adelante para recorrer los modulos desde una sola aplicacion.

| Ruta web | Funcion |
| --- | --- |
| `/` | Pagina de inicio y acceso a los modulos. |
| `/historias-clinicas` | Listado de historias; desde aqui se crean pacientes e historias. |
| `/historias-clinicas/nueva` | Seleccion de paciente y apertura de historia clinica. |
| `/historias-clinicas/pacientes/nuevo` | Registro de un nuevo paciente. |
| `/historias-clinicas/paciente/{pacienteId}` | Detalle de la historia y datos del paciente. |
| `/historias-clinicas/{historiaId}/atenciones` | Registro y listado de atenciones iniciales. |
| `/historias-clinicas/{historiaId}/antecedentes` | Registro y listado de antecedentes personales, familiares y alergias. |
| `/atencion-medica` | Registro de consultas medicas y componentes clinicos. |

Flujo recomendado para demostrar la aplicacion:

```text
Inicio
  -> Historias clinicas
  -> Registrar paciente
  -> Crear historia clinica
  -> Ver detalle
  -> Atenciones / Antecedentes y alergias / Atencion medica
```

## API REST

### Pacientes e historia clinica

| Metodo | Endpoint | Descripcion |
| --- | --- | --- |
| `POST` | `/api/pacientes` | Crear paciente. |
| `GET` | `/api/pacientes` | Listar pacientes. |
| `GET` | `/api/pacientes/{id}` | Consultar paciente por ID. |
| `POST` | `/api/historias-clinicas/paciente/{pacienteId}` | Crear historia clinica para un paciente. |
| `GET` | `/api/historias-clinicas` | Listar historias clinicas. |
| `GET` | `/api/historias-clinicas/{id}` | Consultar historia clinica por ID. |
| `GET` | `/api/historias-clinicas/paciente/{pacienteId}` | Consultar historia por paciente. |
| `POST` | `/api/historias-clinicas/{historiaId}/atenciones` | Registrar atencion inicial. |
| `GET` | `/api/historias-clinicas/{historiaId}/atenciones` | Listar atenciones iniciales. |

### Antecedentes y alergias

| Metodo | Endpoint | Descripcion |
| --- | --- | --- |
| `GET` | `/api/historias-clinicas/{historiaId}/antecedentes` | Listar antecedentes de una historia. |
| `POST` | `/api/historias-clinicas/{historiaId}/antecedentes` | Registrar antecedente personal o familiar. |
| `DELETE` | `/api/historias-clinicas/{historiaId}/antecedentes/{id}` | Eliminar antecedente. |
| `GET` | `/api/historias-clinicas/{historiaId}/alergias` | Listar alergias de una historia. |
| `POST` | `/api/historias-clinicas/{historiaId}/alergias` | Registrar alergia. |
| `DELETE` | `/api/historias-clinicas/{historiaId}/alergias/{id}` | Eliminar alergia. |

### Atencion medica

| Metodo | Endpoint | Descripcion |
| --- | --- | --- |
| `POST` | `/api/atencion-medica/consultas` | Registrar consulta medica. |
| `GET` | `/api/atencion-medica/historias-clinicas/{historiaId}/consultas` | Listar consultas de una historia. |
| `PUT` | `/api/atencion-medica/consultas/{consultaId}` | Actualizar consulta. |
| `PATCH` | `/api/atencion-medica/consultas/{consultaId}/cerrar` | Cerrar consulta. |
| `DELETE` | `/api/atencion-medica/consultas/{consultaId}` | Eliminar consulta. |
| `GET` / `POST` | `/api/atencion-medica/consultas/{consultaId}/signos-vitales` | Consultar o registrar signos vitales. |
| `GET` / `POST` | `/api/atencion-medica/consultas/{consultaId}/diagnosticos` | Consultar o registrar diagnosticos. |
| `GET` / `POST` | `/api/atencion-medica/consultas/{consultaId}/tratamientos` | Consultar o registrar tratamientos. |
| `GET` / `POST` | `/api/atencion-medica/consultas/{consultaId}/evoluciones` | Consultar o registrar evoluciones medicas. |
| `PUT` / `DELETE` | `/api/atencion-medica/diagnosticos/{diagnosticoId}` | Actualizar o eliminar diagnostico. |
| `PUT` / `DELETE` | `/api/atencion-medica/signos-vitales/{signosVitalesId}` | Actualizar o eliminar signos vitales. |
| `PUT` / `DELETE` | `/api/atencion-medica/tratamientos/{tratamientoId}` | Actualizar o eliminar tratamiento. |
| `PUT` / `DELETE` | `/api/atencion-medica/evoluciones/{evolucionId}` | Actualizar o eliminar evolucion medica. |

## Pruebas con Postman

La coleccion de Postman de Atencion Medica esta disponible en [postman/Atencion-Medica.postman_collection.json](postman/Atencion-Medica.postman_collection.json).

Orden recomendado de prueba:

1. Crear un paciente con `POST /api/pacientes`.
2. Crear la historia con `POST /api/historias-clinicas/paciente/{pacienteId}`.
3. Registrar antecedentes, alergias o atenciones iniciales con el ID de la historia.
4. Registrar una consulta medica enviando `historiaClinicaId`.
5. Usar el `consultaId` recibido para registrar signos vitales, diagnosticos, tratamientos y evoluciones.
6. Consultar, actualizar y eliminar los recursos para comprobar las operaciones CRUD.

## Estructura del repositorio

```text
.
|-- src/
|   |-- main/java/
|   |   |-- com/tecsup/historiaclinica/                # Pacientes, historia y antecedentes
|   |   `-- com/coello/historiaclinica/atencionmedica/ # Atencion medica
|   `-- main/resources/
|       |-- templates/                                 # Vistas Thymeleaf
|       `-- static/                                    # CSS y JavaScript
|-- database/                                          # Esquema SQL e instrucciones
|-- postman/                                           # Coleccion de pruebas
|-- antecedentes/                                      # Entrega original preservada
|-- pom.xml                                            # Aplicacion integrada
`-- README.md
```

## Reglas para futuras integraciones

1. Actualizar `main` antes de iniciar cambios: `git pull origin main`.
2. Trabajar en una rama propia y crear commits con mensajes claros.
3. No duplicar `Paciente`, `HistoriaClinica`, sus tablas ni sus repositorios.
4. Mantener el patron Controller -> Service -> Repository.
5. Verificar las rutas REST, las vistas Thymeleaf y la base `historia_clinica` antes de subir cambios.
6. Actualizar este README cuando se integre un modulo, requisito, endpoint, tabla o instruccion de ejecucion.

## Evidencias de entrega

Para la presentacion se deben mostrar:

- Enlace del repositorio GitHub.
- Base de datos `historia_clinica` y sus tablas relacionadas.
- Pruebas realizadas en Postman.
- Ejecucion de la interfaz Thymeleaf y el flujo integrado entre los modulos.
