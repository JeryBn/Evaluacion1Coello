# Base de datos

`historia_clinica.sql` es una exportacion de la base de datos integrada usada por la aplicacion.

Para restaurarla en MySQL o MariaDB:

```sql
SOURCE database/historia_clinica.sql;
```

La aplicacion tambien puede crear y actualizar las tablas al iniciar, mediante Hibernate y la configuracion de `src/main/resources/application.properties`.

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
