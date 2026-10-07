-- Migracion aditiva: no elimina ni reemplaza las tablas de la primera entrega.
USE historia_clinica;
CREATE TABLE IF NOT EXISTS auditoria_registros (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(100) NOT NULL,
    fecha_hora TIMESTAMP(6) NOT NULL,
    operacion VARCHAR(20) NOT NULL,
    entidad VARCHAR(80) NOT NULL,
    registro_id BIGINT NOT NULL,
    INDEX idx_auditoria_fecha (fecha_hora)
) ENGINE=InnoDB;
-- registro_id es historico y polimorfico, sin FK: debe sobrevivir a la eliminacion.
