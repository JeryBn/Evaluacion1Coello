# Modulo de Atencion Medica

Proyecto Spring Boot para la parte de Atencion Medica del Modulo 5 Historia Clinica.

Incluye una interfaz web con Thymeleaf en `http://localhost:8080`, ademas de la API REST.

## Requerimientos desarrollados

- RF-HC-08: Registrar consultas medicas.
- RF-HC-09: Registrar motivo de consulta.
- RF-HC-10: Registrar anamnesis.
- RF-HC-11: Registrar examen fisico.
- RF-HC-12: Registrar signos vitales.
- RF-HC-13: Registrar diagnosticos.
- RF-HC-14: Registrar tratamientos.
- RF-HC-15: Registrar evoluciones medicas.

## Base de datos

Todos los integrantes deben usar la misma base de datos:

```sql
CREATE DATABASE IF NOT EXISTS historia_clinica
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
```

La configuracion esta en:

```text
src/main/resources/application.properties
```

Por defecto usa:

```text
jdbc:mysql://localhost:3306/historia_clinica
usuario: root
password: vacio
```

Si su MySQL tiene otra clave, deben cambiar `spring.datasource.password`.

## Orden recomendado para el equipo

1. Crear un unico repositorio en GitHub para todo el equipo.
2. Subir primero la estructura base del proyecto Spring Boot.
3. Mantener una base comun llamada `historia_clinica`.
4. Integrar primero las entidades compartidas: Paciente e HistoriaClinica.
5. Integrar despues Antecedentes y Alergias.
6. Integrar finalmente Atencion Medica, porque usa `historiaClinicaId` para relacionar consultas con la historia clinica.
7. Probar que todos los endpoints funcionen con la misma base de datos.

## Endpoints principales

Crear una historia clinica base para pruebas:

```http
POST /api/atencion-medica/historias-clinicas
```

Registrar consulta medica:

```http
POST /api/atencion-medica/consultas
```

Listar consultas de una historia clinica:

```http
GET /api/atencion-medica/historias-clinicas/{historiaClinicaId}/consultas
```

Registrar signos vitales:

```http
POST /api/atencion-medica/consultas/{consultaId}/signos-vitales
```

Registrar diagnostico:

```http
POST /api/atencion-medica/consultas/{consultaId}/diagnosticos
```

Registrar tratamiento:

```http
POST /api/atencion-medica/consultas/{consultaId}/tratamientos
```

Registrar evolucion medica:

```http
POST /api/atencion-medica/consultas/{consultaId}/evoluciones
```

Cerrar consulta:

```http
PATCH /api/atencion-medica/consultas/{consultaId}/cerrar
```

Tambien existen endpoints `PUT` y `DELETE` para consultas, diagnosticos, tratamientos y evoluciones.

## Interfaz y Postman

La pantalla Thymeleaf permite registrar todo el flujo de Atencion Medica desde el navegador. Para las pruebas solicitadas por el profesor, importa `postman/Atencion-Medica.postman_collection.json` en Postman y ejecuta las solicitudes en orden.

## Ejemplo de consulta medica

```json
{
  "historiaClinicaId": 1,
  "codigoCita": "CIT-000125",
  "medicoId": 1,
  "nombreMedico": "Dr. Carlos Ramirez",
  "especialidad": "Medicina General",
  "motivoConsulta": "Dolor abdominal",
  "anamnesis": "Paciente refiere dolor abdominal desde hace 2 dias.",
  "examenFisico": "Abdomen blando, dolor leve a la palpacion.",
  "evaluacionClinica": "Cuadro compatible con dolor abdominal no complicado.",
  "observaciones": "Se indica control si presenta fiebre o aumento del dolor."
}
```

## Ejemplo de signos vitales

```json
{
  "pesoKg": 72,
  "tallaMetros": 1.70,
  "presionArterial": "120/80 mmHg",
  "frecuenciaCardiaca": 75,
  "frecuenciaRespiratoria": 18,
  "temperatura": 36.5,
  "saturacionOxigeno": 98
}
```
