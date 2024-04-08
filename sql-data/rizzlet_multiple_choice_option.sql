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
-- Table structure for table `multiple_choice_option`
--

DROP TABLE IF EXISTS `multiple_choice_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `multiple_choice_option` (
  `id` bigint NOT NULL,
  `correct` bit(1) NOT NULL,
  `option_text` varchar(16) NOT NULL,
  `multiple_choice_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3omjm108umcc5f7xcwnua7lo1` (`multiple_choice_id`),
  CONSTRAINT `FK3omjm108umcc5f7xcwnua7lo1` FOREIGN KEY (`multiple_choice_id`) REFERENCES `multiple_choice_question` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `multiple_choice_option`
--

LOCK TABLES `multiple_choice_option` WRITE;
/*!40000 ALTER TABLE `multiple_choice_option` DISABLE KEYS */;
INSERT INTO `multiple_choice_option` VALUES (1,_binary '\0','Stavanger',1),(2,_binary '\0','Bergen',1),(3,_binary '','Trondheim',1),(4,_binary '\0','Oslo',1),(5,_binary '','1996',2),(6,_binary '\0','2001',2),(7,_binary '\0','1937',2),(8,_binary '\0','1982',2),(52,_binary '','6',52),(53,_binary '\0','12',52),(54,_binary '\0','8',52),(55,_binary '\0','16',52),(56,_binary '','Earth',55),(57,_binary '\0','Mars',55),(58,_binary '\0','Jupiter',55),(59,_binary '\0','Venus',55),(60,_binary '\0','Pluto',56),(61,_binary '\0','Mars',56),(62,_binary '','Mercury',56),(63,_binary '\0','Venus',56);
/*!40000 ALTER TABLE `multiple_choice_option` ENABLE KEYS */;
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
