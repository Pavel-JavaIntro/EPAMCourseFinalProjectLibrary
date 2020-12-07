CREATE DATABASE  IF NOT EXISTS `new_library` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `new_library`;
-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: new_library
-- ------------------------------------------------------
-- Server version	8.0.21

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
-- Table structure for table `authors`
--

DROP TABLE IF EXISTS `authors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authors` (
  `id` int NOT NULL AUTO_INCREMENT,
  `surname` varchar(25) NOT NULL,
  `first_name` varchar(25) DEFAULT NULL,
  `second_name` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `alt` (`surname`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authors`
--

LOCK TABLES `authors` WRITE;
/*!40000 ALTER TABLE `authors` DISABLE KEYS */;
INSERT INTO `authors` VALUES (1,'Иванов','Иван','Иванович'),(2,'Шекспир','Уильям',NULL),(3,'Ильф','И.','А'),(4,'Петров','Евгений',NULL),(5,'Дюма','Александр',NULL),(6,'Касичев','Павел','null'),(7,'Радюк','Анастасия',NULL),(8,'Пушкин','Александр','Сергеевич'),(14,'Лермонтов','Михаил','Юрьевич'),(15,'Гете','Иоганн',NULL),(16,'Толстой','Лев','Николаевич'),(17,'Чехов','Антон','Павлович'),(18,'Маркс','Карл',NULL),(19,'Камю','Альбер',NULL),(20,'Сартр','Жан','Поль'),(21,'Грибоедов','Александр','Сергеевич'),(22,'Исаев','Алексей','Валерьевич'),(23,'Ломоносов','Михаил','Сергеевич'),(24,'Дирак','Поль',NULL),(25,'Гейнзенберг','Вернер',NULL),(26,'Гете','Иоганн',NULL),(27,'Кафка','Франц',NULL);
/*!40000 ALTER TABLE `authors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `id` int NOT NULL AUTO_INCREMENT,
  `edition_id` int NOT NULL,
  `reserved` tinyint DEFAULT '0',
  `location_id` int NOT NULL DEFAULT '3',
  `reader_id` int DEFAULT NULL,
  `due_date` date DEFAULT NULL,
  `standard_location_id` int NOT NULL DEFAULT '3',
  PRIMARY KEY (`id`),
  KEY `fk_edition_idx` (`edition_id`),
  KEY `fk_user_idx` (`reader_id`),
  KEY `fk_location_idx` (`location_id`),
  KEY `fk_standard_idx` (`standard_location_id`),
  CONSTRAINT `fk_edition` FOREIGN KEY (`edition_id`) REFERENCES `editions` (`id`),
  CONSTRAINT `fk_location` FOREIGN KEY (`location_id`) REFERENCES `locations` (`id`),
  CONSTRAINT `fk_standard` FOREIGN KEY (`standard_location_id`) REFERENCES `locations` (`id`),
  CONSTRAINT `fk_user` FOREIGN KEY (`reader_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1,1,3,2,1,NULL,3),(2,1,0,3,NULL,NULL,3),(3,2,3,2,1,'2020-12-06',3),(4,3,0,3,NULL,NULL,3),(5,4,0,3,NULL,NULL,3),(6,4,2,7,1,NULL,3),(7,5,0,3,NULL,NULL,3),(8,6,0,3,NULL,NULL,3),(9,7,0,3,NULL,NULL,3),(11,8,1,5,1,NULL,3),(12,8,0,3,NULL,NULL,3),(13,8,0,3,NULL,NULL,3),(14,9,0,3,NULL,NULL,3),(15,1,0,3,NULL,NULL,3),(16,1,0,3,NULL,NULL,3),(17,8,0,3,NULL,NULL,3),(23,25,3,2,1,NULL,3),(24,2,0,3,NULL,NULL,3),(25,26,2,5,1,NULL,3),(26,28,3,2,1,NULL,3),(27,30,3,2,1,NULL,3),(28,32,2,7,1,NULL,3),(29,33,3,2,1,NULL,3),(30,34,0,3,NULL,NULL,3),(31,35,0,3,NULL,NULL,3),(32,36,0,3,NULL,NULL,3),(33,37,0,3,NULL,NULL,3),(34,38,0,3,NULL,NULL,3),(35,39,3,2,1,NULL,3),(36,8,0,3,NULL,NULL,3),(37,25,0,3,NULL,NULL,3),(38,25,0,3,NULL,NULL,3),(39,25,0,3,NULL,NULL,3),(40,25,0,3,NULL,NULL,3),(41,25,0,3,NULL,NULL,3),(42,25,0,3,NULL,NULL,3),(43,8,0,3,NULL,NULL,3),(45,9,0,3,NULL,NULL,3),(46,2,0,6,NULL,NULL,6),(47,2,0,3,NULL,NULL,3),(48,40,0,3,NULL,NULL,3),(49,41,0,3,NULL,NULL,3),(50,42,0,3,NULL,NULL,3),(51,1,0,6,NULL,NULL,6),(52,43,0,3,NULL,NULL,3),(53,44,0,3,NULL,NULL,3),(54,45,0,3,NULL,NULL,3);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `edition_author`
--

DROP TABLE IF EXISTS `edition_author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `edition_author` (
  `edition_id` int NOT NULL,
  `author_id` int NOT NULL,
  KEY `fk_ed_idx` (`edition_id`),
  KEY `fk_author_idx` (`author_id`),
  CONSTRAINT `fk_author` FOREIGN KEY (`author_id`) REFERENCES `authors` (`id`),
  CONSTRAINT `fk_ed` FOREIGN KEY (`edition_id`) REFERENCES `editions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edition_author`
--

LOCK TABLES `edition_author` WRITE;
/*!40000 ALTER TABLE `edition_author` DISABLE KEYS */;
INSERT INTO `edition_author` VALUES (1,1),(2,1),(3,2),(4,5),(5,3),(5,4),(6,6),(7,6),(7,7),(8,8),(9,8),(25,14),(26,14),(28,15),(30,16),(32,17),(33,18),(34,19),(35,20),(36,21),(37,22),(38,23),(39,24),(39,25),(40,17),(41,26),(42,23),(43,15),(45,27);
/*!40000 ALTER TABLE `edition_author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `editions`
--

DROP TABLE IF EXISTS `editions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `editions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `standard_number` varchar(20) NOT NULL,
  `title` varchar(200) NOT NULL,
  `year` int DEFAULT '0',
  `genre_id` int NOT NULL,
  `deliveries` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `standard_number_UNIQUE` (`standard_number`),
  KEY `fk_genre_idx` (`genre_id`),
  CONSTRAINT `fk_genre` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `editions`
--

LOCK TABLES `editions` WRITE;
/*!40000 ALTER TABLE `editions` DISABLE KEYS */;
INSERT INTO `editions` VALUES (1,'978155545678345234','Механика',2002,2,2),(2,'978155545678345999','С++ для начинающих',2012,1,1),(3,'978155545678344321','Сонеты',1989,5,1),(4,'978155545678376543','Три мушкетера',2009,3,2),(5,'978155545678376000','Золотой теленок',2009,4,0),(6,'978155545678345560','Очень сложная книга',2017,1,0),(7,'978155555555555555','Очень сложная книга',2020,1,1),(8,'978155555555555550','Стихи',2019,5,3),(9,'978155555555555558','Сказки',2020,13,0),(25,'978155555555555556','Парус',1999,5,2),(26,'978155545678345239','Бородино',2012,3,0),(28,'978155555555555551','Фауст',2000,5,1),(30,'978155545678345777','Война и мир',1999,3,0),(32,'978155545678345288','Каштанка',1999,3,0),(33,'978155545678345236','Капитал',1978,3,3),(34,'978155545678345232','Чума',2000,3,0),(35,'978155555555555552','Тошнота',1999,3,2),(36,'978155555555555554','Горе от ума',2004,3,1),(37,'978155545678345237','От Дубно до Ростова',2016,3,1),(38,'978155545678345555','Избранное',2000,3,0),(39,'978155545678345111','Квантовая механика',2012,3,0),(40,'978155555555555557','Вишневый сад',1999,5,0),(41,'978155545678345222','Страдания юного Вертера',2020,5,0),(42,'978155555555555570','Опыты',2019,5,1),(43,'978155555555555333','Стихи',2016,5,1),(44,'978155545678345333','Проверка',0,5,0),(45,'978155555577755556','Замок',2020,16,0);
/*!40000 ALTER TABLE `editions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genres`
--

DROP TABLE IF EXISTS `genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genres` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `description_UNIQUE` (`description`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genres`
--

LOCK TABLES `genres` WRITE;
/*!40000 ALTER TABLE `genres` DISABLE KEYS */;
INSERT INTO `genres` VALUES (3,'adventures'),(9,'art'),(22,'astronomy'),(14,'bodybuilding'),(21,'folk history'),(4,'humor'),(2,'physics'),(5,'poetry'),(1,'programming'),(23,'theater'),(12,'военное дело'),(17,'иностранные языки'),(11,'математика'),(18,'палеонтология'),(20,'парапсихология'),(13,'сказки'),(15,'спорт'),(16,'странное запасное значение'),(10,'фантастика'),(19,'эзотерика');
/*!40000 ALTER TABLE `genres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `locations`
--

DROP TABLE IF EXISTS `locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `locations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(25) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `description_UNIQUE` (`description`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locations`
--

LOCK TABLES `locations` WRITE;
/*!40000 ALTER TABLE `locations` DISABLE KEYS */;
INSERT INTO `locations` VALUES (1,'decommissioned'),(4,'delivery desk'),(5,'delivery desk reserve'),(3,'depository'),(2,'on hand'),(6,'reading hall'),(7,'reading hall reserve');
/*!40000 ALTER TABLE `locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`description`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'admin'),(5,'guest'),(2,'librarian'),(4,'reader'),(3,'subscriber');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NOT NULL DEFAULT '4',
  `surname` varchar(25) NOT NULL,
  `name` varchar(25) NOT NULL,
  `address` varchar(80) NOT NULL,
  `phone` varchar(25) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `password` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_person` (`surname`,`name`,`email`,`address`),
  KEY `fk_role_idx` (`role_id`),
  KEY `surname` (`surname`) /*!80000 INVISIBLE */,
  KEY `phone` (`phone`),
  CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,1,'Касичев','Павел','Минск','222-333-1','pavkascool@gmail.com',92668751),(2,2,'Петров','Петр','Минск','556-333-0','xephjy@dsd.com',812757528),(3,4,'Кузнецов','Петр','Минск','556-999-0','a1@dsd.com',55555),(4,4,'Кузнецов','Игорь','Минск','999-999-0','a2@dsd.com',-934979389),(6,4,'Иванов','Михаил','г.Москва','666-555-44','pavkascool@gmail.com',-1451976326),(8,3,'Петров','Петр','г.Сан-Франциско','111-000-111','pavkascool@gmail.com',1897933501),(9,3,'Бурзумбеков','Бурзумбек','Бишкек','4444444444444','pavkascool@gmail.com',592488557),(10,4,'Сидоров','Сидор','г.Москва','11111111111','pavkascool@gmail.com',117065636),(11,4,'Жмуриков','Жмурик','Бишкек','222222222','pavkascool@gmail.com',178030683),(12,3,'Кузнецов','Петр','г.Витебск','66666666666','pavkascool@gmail.com',2011564243),(13,4,'Бульба','Тарас','Киев','0000000','pavkascool@gmail.com',-1120733235),(14,3,'Бумбараш','Иван','г.Кишенев','666666666','pavkascool@gmail.com',-445764646),(15,3,'Johnson','John','г.Сан-Франциско','001 00007777','pavkascool@gmail.com',843437987),(17,2,'Кузнецов','Игорь','г.Сан-Франциско','4443333333','pavkascool@gmail.com',1559435532),(18,2,'Бумбуков','Бумбук','г.Париж','90000099999','pavkascool@gmail.com',-463178488),(20,3,'Smith','John','г.Париж','888888888888888','pavkascool@gmail.com',-1002828420),(22,4,'Чапаев','Василий','г.Кишенев','666-555-44','pavkascool@gmail.com',-419364807),(23,3,'Чапаев','Василий','г.Париж','111-000-111','pavkascool@gmail.com',1770216477);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-07 19:20:17
