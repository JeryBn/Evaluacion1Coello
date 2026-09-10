# Historia Clinica - Modulo 5

API REST y aplicacion web con Spring Boot, Thymeleaf, JPA e Hibernate para el laboratorio de Historia Clinica. El proyecto se organiza por capas (`Controller`, `Service` y `Repository`) y usa una unica base de datos compartida por el equipo.

## Modulos integrados

| Modulo | Responsable | Estado | Rutas principales |
| --- | --- | --- | --- |
| Pacientes e Historia Clinica | Equipo | Integrado | `/historias-clinicas`, `/api/pacientes`, `/api/historias-clinicas` |
| Atenciones iniciales | Equipo | Integrado | `/historias-clinicas/{id}/atenciones` |
| Atencion Medica (RF-HC-08 a RF-HC-15) | Coello | Integrado | `/atencion-medica`, `/api/atencion-medica` |
| Antecedentes | Pendiente de integrar | Pendiente | Por definir al subir el modulo |

La integracion vigente usa una sola entidad compartida: una `ConsultaMedica` pertenece a una `HistoriaClinica` creada desde el modulo de Pacientes e Historia Clinica. No se deben crear historias clinicas desde el modulo de Atencion Medica.

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

## Base de datos

Todos los modulos usan la base `historia_clinica` en MySQL/MariaDB de XAMPP.

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

## Reglas de integracion del equipo

1. Antes de empezar, actualizar la rama principal con `git pull origin main`.
2. Cada integrante trabaja en una rama con nombre descriptivo, por ejemplo `feature/antecedentes`.
3. No duplicar entidades compartidas como `Paciente` o `HistoriaClinica`; reutilizar sus clases y relaciones existentes.
4. Mantener cada funcionalidad en sus capas Controller, Service y Repository.
5. Probar que el proyecto inicia y que el nuevo modulo funciona con `historia_clinica` antes de subirlo.
6. Actualizar este README en el mismo cambio cuando se agregue, modifique o integre un modulo, una ruta, una relacion o una instruccion de ejecucion.
7. Antes de mezclar cambios, revisar nombres de rutas, entidades, tablas y repositorios para evitar duplicados.

La persona que realice la integracion final revisara los cambios antes de incorporarlos a `main`, ordenara paquetes o nombres duplicados cuando sea necesario y dejara este documento actualizado.
