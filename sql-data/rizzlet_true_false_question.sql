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
-- Table structure for table `true_false_question`
--

DROP TABLE IF EXISTS `true_false_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `true_false_question` (
  `id` bigint NOT NULL,
  `text` varchar(64) NOT NULL,
  `type` tinyint DEFAULT NULL,
  `quiz_id` bigint NOT NULL,
  `is_true` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_gi377ki4e3dmuwayukr49jj6v` (`quiz_id`),
  CONSTRAINT `FK_gi377ki4e3dmuwayukr49jj6v` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `true_false_question`
--

LOCK TABLES `true_false_question` WRITE;
/*!40000 ALTER TABLE `true_false_question` DISABLE KEYS */;
INSERT INTO `true_false_question` VALUES (3,'T in NTNU stands for Trondheim',0,1,_binary '\0'),(53,'Water is composed of two hydrogen atoms and one oxygen atom.',0,2,_binary '');
/*!40000 ALTER TABLE `true_false_question` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed
