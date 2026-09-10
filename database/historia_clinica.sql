
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
DROP TABLE IF EXISTS `paciente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paciente` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `apellidos` varchar(255) NOT NULL,
  `dni` varchar(255) NOT NULL,
  `nombres` varchar(255) NOT NULL,
  `sexo` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `historia_clinica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `historia_clinica` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `estado` varchar(255) DEFAULT NULL,
  `fecha_apertura` date NOT NULL,
  `numero_historia` varchar(255) NOT NULL,
  `paciente_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK5vxvjv2hg4e95m065i62j9fpp` (`numero_historia`),
  UNIQUE KEY `UKntilnitj68t50o4bhvxgwhda4` (`paciente_id`),
  CONSTRAINT `FKp54oxk24fs8u4i7ddoas4geqg` FOREIGN KEY (`paciente_id`) REFERENCES `paciente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `atencion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `atencion` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `motivo` varchar(255) NOT NULL,
  `observaciones` varchar(255) DEFAULT NULL,
  `historia_clinica_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrxktwu93s40e0a1oux0r911oe` (`historia_clinica_id`),
  CONSTRAINT `FKrxktwu93s40e0a1oux0r911oe` FOREIGN KEY (`historia_clinica_id`) REFERENCES `historia_clinica` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `antecedente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `antecedente` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha_registro` date DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `historia_clinica_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK49xxsecfpnxd2gaqkm4x3pyg6` (`historia_clinica_id`),
  CONSTRAINT `FK49xxsecfpnxd2gaqkm4x3pyg6` FOREIGN KEY (`historia_clinica_id`) REFERENCES `historia_clinica` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `alergia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alergia` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `alergia` varchar(255) DEFAULT NULL,
  `fecha_registro` date DEFAULT NULL,
  `observacion` varchar(255) DEFAULT NULL,
  `reaccion` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `historia_clinica_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsoomh98tedwuqfv88kxx9u1ld` (`historia_clinica_id`),
  CONSTRAINT `FKsoomh98tedwuqfv88kxx9u1ld` FOREIGN KEY (`historia_clinica_id`) REFERENCES `historia_clinica` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `consultas_medicas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `consultas_medicas` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `anamnesis` text NOT NULL,
  `codigo_cita` varchar(30) DEFAULT NULL,
  `especialidad` varchar(80) NOT NULL,
  `estado` enum('ABIERTA','CERRADA') NOT NULL,
  `evaluacion_clinica` text DEFAULT NULL,
  `examen_fisico` text NOT NULL,
  `fecha_atencion` datetime(6) NOT NULL,
  `medico_id` bigint(20) NOT NULL,
  `motivo_consulta` varchar(300) NOT NULL,
  `nombre_medico` varchar(100) NOT NULL,
  `observaciones` text DEFAULT NULL,
  `historia_clinica_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKoas3atiaky9sea0rax6hffe93` (`historia_clinica_id`),
  CONSTRAINT `FKoas3atiaky9sea0rax6hffe93` FOREIGN KEY (`historia_clinica_id`) REFERENCES `historia_clinica` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `signos_vitales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `signos_vitales` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fecha_registro` datetime(6) NOT NULL,
  `frecuencia_cardiaca` int(11) NOT NULL,
  `frecuencia_respiratoria` int(11) NOT NULL,
  `peso_kg` decimal(5,2) NOT NULL,
  `presion_arterial` varchar(20) NOT NULL,
  `saturacion_oxigeno` int(11) NOT NULL,
  `talla_metros` decimal(4,2) NOT NULL,
  `temperatura` decimal(4,1) NOT NULL,
  `consulta_medica_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKqfyre3slgsewh0ciuui2dq8h7` (`consulta_medica_id`),
  CONSTRAINT `FK35q8xoo9m9n8mhj4t8ct3pgxn` FOREIGN KEY (`consulta_medica_id`) REFERENCES `consultas_medicas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `diagnosticos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `diagnosticos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo_cie10` varchar(15) NOT NULL,
  `descripcion` varchar(180) NOT NULL,
  `observaciones` text DEFAULT NULL,
  `tipo_diagnostico` enum('PRINCIPAL','SECUNDARIO') NOT NULL,
  `consulta_medica_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp04n2ve1vupx9r49p9owtdgx1` (`consulta_medica_id`),
  CONSTRAINT `FKp04n2ve1vupx9r49p9owtdgx1` FOREIGN KEY (`consulta_medica_id`) REFERENCES `consultas_medicas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `tratamientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tratamientos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `duracion` varchar(80) DEFAULT NULL,
  `fecha_finalizacion` date DEFAULT NULL,
  `fecha_inicio` date DEFAULT NULL,
  `indicaciones` text DEFAULT NULL,
  `observaciones` text DEFAULT NULL,
  `recomendaciones` text DEFAULT NULL,
  `tratamiento_indicado` text NOT NULL,
  `consulta_medica_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrrg01iotklv6a0p5y6cfht7f0` (`consulta_medica_id`),
  CONSTRAINT `FKrrg01iotklv6a0p5y6cfht7f0` FOREIGN KEY (`consulta_medica_id`) REFERENCES `consultas_medicas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `evoluciones_medicas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evoluciones_medicas` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` text NOT NULL,
  `fecha_evolucion` datetime(6) NOT NULL,
  `medico_responsable` varchar(100) NOT NULL,
  `plan` text DEFAULT NULL,
  `consulta_medica_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1ulnpx908manvx3ems4b45g45` (`consulta_medica_id`),
  CONSTRAINT `FK1ulnpx908manvx3ems4b45g45` FOREIGN KEY (`consulta_medica_id`) REFERENCES `consultas_medicas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

