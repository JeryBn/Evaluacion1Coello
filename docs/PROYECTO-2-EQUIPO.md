# Proyecto 2: reparto y contrato de integracion

## Estado final y reparto de exposicion (9 de octubre de 2026)

Este apartado reemplaza el reparto provisional que se conserva mas abajo como
antecedente de planificacion. Todo esta integrado en la aplicacion raiz.

| Expositor | Parte para defender | Evidencia |
| --- | --- | --- |
| Magaly Rosales | P1 HistoriaClinica-Atencion, relacion bidireccional, JSON sin recursion y CRUD relacionado | Commit propio `2355ca3`; PDF de Magaly |
| Michael | P3 usuarios/roles y P4 interfaz Thymeleaf; conexion con antecedentes/alergias | PDF de Michael; servicios, controladores y vistas de usuarios |
| Jery / Chory | P2 auditoria y P5 seguridad, integracion y Atencion Medica | PDF de Jery; aspecto de auditoria y configuracion de seguridad |

La implementacion restante se completo con asistencia de Codex en la copia de
Jery. El reparto de exposicion NO atribuye esos commits a Michael ni a Magaly.
Cada integrante debe estudiar, reproducir y explicar su parte. La guia pide
evidencia individual de entidades relacionadas: Michael puede demostrar
Usuario-Rol y Antecedente-HistoriaClinica; Jery ConsultaMedica-SignosVitales y
ConsultaMedica-Diagnostico; Magaly HistoriaClinica-Atencion.

Correcciones sobre el aporte de Magaly: inicializar la coleccion de atenciones,
conservar JsonManagedReference/JsonBackReference, completar edicion/eliminacion
acotadas a la historia y hacer DB_PORT configurable (3306/3307). Su commit y
autor se conservan; no se reemplazo su proyecto ni se borraron sus aportes.

Verificado: Maven package con 13 pruebas sin fallos; Newman con 47 solicitudes,
30 aserciones sin fallos. CSRF, permisos por rol, rechazo de usuario/rol inactivo,
proteccion del ultimo administrador, auditoria transaccional y CRUD relacionado
estan cubiertos. Newman ejecuta la coleccion de Postman; no equivale a afirmar
una ejecucion manual en la interfaz de Postman.

La base exportada es solo estructura, sin registros ni credenciales. Los datos
ficticios de demostracion permanecen exclusivamente en la base local.
El respaldo `antecedentes/` y sus cambios locales ajenos se preservan.

Continuacion de Evaluacion 01 en la misma aplicacion y base `historia_clinica`.
La portada menciona pedidos, pero el procedimiento pide continuar el proyecto
anterior: se conserva Historia Clinica. La guia tambien dice individual;
confirmar con el docente la entrega grupal y conservar evidencias por integrante.

## Responsabilidades (tres integrantes en total)

| Responsable | Preguntas | Entregable y criterio de aceptacion |
| --- | --- | --- |
| JeryBn / Atencion Medica | P1 de su modulo y P2 | Relaciones ConsultaMedica-SignosVitales y ConsultaMedica-Diagnostico; CRUD relacionado; auditoria automatica de registro, modificacion y eliminacion; tabla y consulta web/API. |
| Companera de Historia Clinica | P1 de su modulo, P3 y P4 | Usuario y Rol con relacion JPA; crear, listar, editar y activar/desactivar ambos; asignar rol; formularios Thymeleaf integrados; validar duplicados y entradas. Verificar tambien Paciente-HistoriaClinica. |
| Companera de Antecedentes | P1 de su modulo y P5 | Spring Security, login/logout, permisos en backend y menu, redireccion; denegar usuarios/roles inactivos; pruebas por rol. Verificar Antecedente-HistoriaClinica y Alergia-HistoriaClinica con CRUD relacionado. |

No crear otros proyectos Spring Boot ni modificar el respaldo `antecedentes/`.
Cada integrante debe demostrar al menos dos entidades relacionadas y su CRUD.
Usar ManyToMany solo donde corresponda; la guia no obliga a introducir relaciones artificiales.

## Contrato para trabajar simultaneamente

- Paquete de usuarios: `com.tecsup.historiaclinica.usuarios`; tablas `usuarios`, `roles`.
- Usuario: id, username unico, passwordHash (nunca devolver), activo, rol.
- Rol: id, codigo unico e inmutable para roles del sistema, nombre, activo.
- Codigos: ADMINISTRADOR, MEDICO, RECEPCIONISTA. Un rol por usuario (`ManyToOne`).
- Seguridad publica autoridades `ROLE_ADMINISTRADOR`, `ROLE_MEDICO`, `ROLE_RECEPCIONISTA`.
- El principal autenticado debe devolver username en `getName()`. La auditoria toma
  `HttpServletRequest.getUserPrincipal()`, nunca un nombre enviado en JSON o cabecera.
- Rutas usuarios: `/admin/usuarios`, `/admin/roles`, `/api/admin/usuarios`, `/api/admin/roles`.
- Seguridad: `/login`, logout POST y pagina 403; BCrypt, CSRF en formularios y fetch;
  evitar desactivar CSRF globalmente para solucionar las pruebas.
- Auditoria: `/auditoria` y `/api/auditoria`, solo ADMINISTRADOR en la integracion final.
- ADMINISTRADOR: todos los modulos. MEDICO: pacientes, historias, antecedentes y atencion medica.
  RECEPCIONISTA: registro/consulta de pacientes; no datos clinicos, auditoria ni administracion.
  No crear citas: no es un modulo implementado; la tabla de la guia es un ejemplo.
- La responsable de usuarios implementa PasswordEncoder BCrypt local en su servicio;
  la responsable de seguridad reutiliza el hash y NO vuelve a cifrarlo al autenticar.
- La responsable de seguridad integra navegacion y permisos tras unir usuarios;
  agregar todos sus paquetes a EntityScan/EnableJpaRepositories si corresponde.

## Orden de trabajo y Git

1. Partir del ultimo main: `git pull --ff-only origin main`.
2. Crear ramas `feat/usuarios-roles` y `feat/seguridad-acceso`.
3. Usuarios acuerda primero entidades/repositorio con Seguridad; publicar ese avance.
4. Seguridad puede comenzar sus pruebas y configuracion con ese contrato, sin duplicar Usuario/Rol.
5. Integrar primero auditoria, despues usuarios/roles y al final seguridad/navegacion.
6. Cada avance tiene un commit con titulo y descripcion: entidades, servicio,
   controladores, vistas, pruebas y documentacion, segun el cambio real.
7. Crear PR hacia main, resolver conflictos conservando el trabajo ajeno y comprobar
   el sistema completo antes de integrar. No subir target, claves ni datos personales.

## Extension de la auditoria por las companeras

Anotar metodos publicos de servicios llamados desde otro bean con `@Auditar`.
Las altas/modificaciones devuelven la entidad JPA guardada; las bajas reciben el ID
como primer argumento y usan `idArgumento = 0`. Nunca anotar consultas de lectura.
No llamar el metodo auditado mediante `this`: el proxy AOP no intercepta autollamadas.
La operacion y su bitacora comparten transaccion: si falla una, se revierten ambas.
No auditar passwords, DNI, diagnosticos ni cuerpos completos: solo metadatos.
La baja en cascada registra la operacion principal, no un evento por cada hijo.

## Estado y evidencia

- Auditoria medica: implementada en este avance; revisar guia AUDITORIA.md.
- Usuarios/roles y sus vistas: pendientes de la companera de Historia Clinica.
- Seguridad y usuario autenticado real: pendientes de la companera de Antecedentes.
- La entrega completa NO esta terminada hasta integrar y probar los tres aportes.
- Cada integrante entrega capturas de entidades, tablas/FK, CRUD en la aplicacion
  y sus pruebas. Seguridad demuestra accesos permitidos y 403, incluso por URL directa.
