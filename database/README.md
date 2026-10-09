# Base de datos

`esquema-proyecto2.sql` es la exportacion actual de las 13 tablas de la aplicacion,
incluyendo usuarios, roles y auditoria. Solo contiene estructura: no incluye
pacientes, registros clinicos, usuarios ni hashes de contrasenas.

Para instalarla en una base NUEVA de MySQL o MariaDB:

```sql
CREATE DATABASE IF NOT EXISTS historia_clinica CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE historia_clinica;
SOURCE database/esquema-proyecto2.sql;
```

La aplicacion tambien puede crear y actualizar las tablas al iniciar, mediante Hibernate y la configuracion de `src/main/resources/application.properties`.

En Workbench abrir el archivo SQL, seleccionar el esquema y ejecutarlo solo
si esta vacio. En una base existente, realizar respaldo y usar la actualizacion
de Hibernate; no importar nuevamente las instrucciones CREATE TABLE.
El archivo anterior `historia_clinica.sql` se conserva como evidencia historica
y contiene DROP TABLE: NO ejecutarlo sobre una base con datos.
`proyecto2-auditoria.sql` es el script incremental de la primera etapa de auditoria.
Configurar DB_PORT segun su instalacion (3306 por defecto, 3307 si corresponde).
El perfil demo y sus variables de claves se explican en el README principal.

Tablas del flujo integrado:

```text
paciente -> historia_clinica -> atencion
                              -> antecedente
                              -> alergia
                              -> consultas_medicas -> signos_vitales
                                                   -> diagnosticos
                                                   -> tratamientos
                                                   -> evoluciones_medicas
```
