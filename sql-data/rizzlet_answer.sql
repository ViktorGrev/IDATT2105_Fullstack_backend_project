-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 134.209.196.166    Database: rizzlet
-- ------------------------------------------------------
-- Server version	8.0.36-0ubuntu0.23.10.1

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
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `answer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `answer` varchar(255) NOT NULL,
  `question_id` bigint NOT NULL,
  `result_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpk5onpllka8irsmxcf0i5varr` (`result_id`),
  CONSTRAINT `FKpk5onpllka8irsmxcf0i5varr` FOREIGN KEY (`result_id`) REFERENCES `quiz_result` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answer`
--

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
INSERT INTO `answer` VALUES (1,'2',4,1),(2,'[3]',1,1),(3,'[6]',2,1),(4,'true',3,1),(5,'3',4,2),(6,'[3]',1,2),(7,'[5]',2,2),(8,'false',3,2),(9,'2',57,3),(10,'[58]',55,3),(11,'[61]',56,3);
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;

