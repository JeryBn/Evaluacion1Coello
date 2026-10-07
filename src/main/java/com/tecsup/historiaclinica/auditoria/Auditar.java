package com.tecsup.historiaclinica.auditoria;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditar {
    Class<?> entidad();
    Operacion operacion();
    int idArgumento() default -1;
    // Para altas que actualizan un registro existente (signos por consulta).
    String buscarExistentePor() default "";
}
