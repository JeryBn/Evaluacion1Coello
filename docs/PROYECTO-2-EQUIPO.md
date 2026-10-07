# Proyecto 2: reparto y contrato de integracion

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
