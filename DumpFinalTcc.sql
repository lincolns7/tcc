CREATE DATABASE  IF NOT EXISTS `medmatch` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `medmatch`;
-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: medmatch
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `candidatura`
--

DROP TABLE IF EXISTS `candidatura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidatura` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `plantao_id` int(11) NOT NULL,
  `medico_id` int(11) NOT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_candidatura_plantao` (`plantao_id`),
  KEY `fk_candidatura_medico` (`medico_id`),
  CONSTRAINT `fk_candidatura_medico` FOREIGN KEY (`medico_id`) REFERENCES `medico` (`id`),
  CONSTRAINT `fk_candidatura_plantao` FOREIGN KEY (`plantao_id`) REFERENCES `plantao` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidatura`
--

LOCK TABLES `candidatura` WRITE;
/*!40000 ALTER TABLE `candidatura` DISABLE KEYS */;
INSERT INTO `candidatura` VALUES (1,1,1,'ACEITA'),(2,1,6,'RECUSADA'),(3,2,2,'PENDENTE'),(4,3,3,'PENDENTE'),(5,4,4,'PENDENTE'),(6,5,5,'ACEITA'),(7,6,2,'PENDENTE'),(8,7,6,'ACEITA'),(9,8,3,'PENDENTE'),(10,10,4,'ACEITA'),(11,11,5,'PENDENTE'),(12,12,1,'PENDENTE'),(13,13,7,'PENDENTE'),(14,14,2,'RECUSADA'),(15,15,3,'ACEITA');
/*!40000 ALTER TABLE `candidatura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hospital`
--

DROP TABLE IF EXISTS `hospital`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hospital` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usuario_id` int(11) NOT NULL,
  `cnpj` varchar(20) NOT NULL,
  `nome_hospital` varchar(150) NOT NULL,
  `endereco` varchar(200) NOT NULL,
  `telefone` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cnpj` (`cnpj`),
  KEY `fk_hospital_usuario` (`usuario_id`),
  CONSTRAINT `fk_hospital_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hospital`
--

LOCK TABLES `hospital` WRITE;
/*!40000 ALTER TABLE `hospital` DISABLE KEYS */;
INSERT INTO `hospital` VALUES (1,1,'12.345.678/0001-90','Hospital Lincoln','Av. Higienópolis, 1200 - Londrina/PR','(43) 3333-1000'),(2,3,'23.456.789/0001-81','Hospital Vida','Av. Bandeirantes, 850 - Londrina/PR','(43) 3333-2000'),(3,4,'34.567.890/0001-72','Hospital Nossa Esperança','Rua Sergipe, 640 - Londrina/PR','(43) 3333-3000'),(4,5,'45.678.901/0001-63','Clínica Saúde','Av. JK, 1500 - Londrina/PR','(43) 3333-4000');
/*!40000 ALTER TABLE `hospital` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medico`
--

DROP TABLE IF EXISTS `medico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medico` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usuario_id` int(11) NOT NULL,
  `crm` varchar(20) NOT NULL,
  `telefone` varchar(20) NOT NULL,
  `especialidade` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `crm` (`crm`),
  KEY `fk_medico_usuario` (`usuario_id`),
  CONSTRAINT `fk_medico_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medico`
--

LOCK TABLES `medico` WRITE;
/*!40000 ALTER TABLE `medico` DISABLE KEYS */;
INSERT INTO `medico` VALUES (1,2,'CRM-PR 45678','(43) 99999-0001','Cardiologia'),(2,6,'CRM-PR 38124','(43) 99999-0002','Clínico Geral'),(3,7,'CRM-PR 41256','(43) 99999-0003','Pediatria'),(4,8,'CRM-PR 39547','(43) 99999-0004','Ortopedia'),(5,9,'CRM-PR 42781','(43) 99999-0005','Neurologia'),(6,10,'CRM-PR 36492','(43) 99999-0006','Cardiologia'),(7,11,'CRM-PR 43821','(43) 99999-0007','Dermatologia');
/*!40000 ALTER TABLE `medico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plantao`
--

DROP TABLE IF EXISTS `plantao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plantao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hospital_id` int(11) NOT NULL,
  `titulo` varchar(150) NOT NULL,
  `especialidade` varchar(100) NOT NULL,
  `data` date NOT NULL,
  `horario` time NOT NULL,
  `valor` decimal(10,2) NOT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_plantao_hospital` (`hospital_id`),
  CONSTRAINT `fk_plantao_hospital` FOREIGN KEY (`hospital_id`) REFERENCES `hospital` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plantao`
--

LOCK TABLES `plantao` WRITE;
/*!40000 ALTER TABLE `plantao` DISABLE KEYS */;
INSERT INTO `plantao` VALUES (1,1,'Plantão Cardiologia - Pronto Atendimento','Cardiologia','2026-09-05','07:00:00',950.00,'FECHADO'),(2,1,'Plantão Clínico - Emergência','Clínico Geral','2026-09-07','19:00:00',750.00,'ABERTO'),(3,1,'Plantão Pediatria','Pediatria','2026-09-10','07:00:00',820.00,'ABERTO'),(4,1,'Plantão Ortopedia - Pronto Socorro','Ortopedia','2026-09-12','19:00:00',900.00,'ABERTO'),(5,1,'Plantão Neurologia','Neurologia','2026-08-28','19:00:00',980.00,'FECHADO'),(6,2,'Plantão Clínico Geral - UTI','Clínico Geral','2026-09-03','07:00:00',850.00,'ABERTO'),(7,2,'Plantão Cardiologia - UTI','Cardiologia','2026-09-08','19:00:00',1050.00,'ABERTO'),(8,2,'Plantão Pediatria - Emergência','Pediatria','2026-09-15','07:00:00',880.00,'ABERTO'),(9,2,'Plantão Dermatologia','Dermatologia','2026-08-30','13:00:00',700.00,'FECHADO'),(10,3,'Plantão Ortopedia - Emergência','Ortopedia','2026-09-06','19:00:00',920.00,'ABERTO'),(11,3,'Plantão Neurologia','Neurologia','2026-09-11','07:00:00',1100.00,'ABERTO'),(12,3,'Plantão Cardiologia','Cardiologia','2026-09-18','19:00:00',1000.00,'ABERTO'),(13,4,'Plantão Dermatologia','Dermatologia','2026-09-04','08:00:00',780.00,'ABERTO'),(14,4,'Plantão Clínico Geral','Clínico Geral','2026-09-13','19:00:00',760.00,'ABERTO'),(15,4,'Plantão Pediatria','Pediatria','2026-09-20','07:00:00',830.00,'ABERTO');
/*!40000 ALTER TABLE `plantao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `email` varchar(150) NOT NULL,
  `senha` varchar(100) NOT NULL,
  `tipo` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Lincoln Hospital','lincoln.hospital@gmail.com','123456','HOSPITAL'),(2,'Lincoln Scherrer','scherrer.lincoln09@gmail.com','123456','MEDICO'),(3,'Mariana Costa','mariana.costa@hospitalvida.com','123456','HOSPITAL'),(4,'Carlos Eduardo Mendes','carlos.mendes@hospitalnossaesperanca.com','123456','HOSPITAL'),(5,'Fernanda Oliveira','fernanda.oliveira@clinicasaude.com','123456','HOSPITAL'),(6,'Gabriel Almeida','gabriel.almeida@email.com','123456','MEDICO'),(7,'Juliana Martins','juliana.martins@email.com','123456','MEDICO'),(8,'Rafael Souza','rafael.souza@email.com','123456','MEDICO'),(9,'Camila Ferreira','camila.ferreira@email.com','123456','MEDICO'),(10,'Bruno Rodrigues','bruno.rodrigues@email.com','123456','MEDICO'),(11,'Amanda Pereira','amanda.pereira@email.com','123456','MEDICO');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-08-13 15:19:34
