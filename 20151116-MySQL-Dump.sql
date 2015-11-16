CREATE DATABASE  IF NOT EXISTS `mensa` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `mensa`;
-- MySQL dump 10.13  Distrib 5.5.46, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: mensa
-- ------------------------------------------------------
-- Server version	5.5.46-0ubuntu0.14.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Buildings`
--

DROP TABLE IF EXISTS `Buildings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Buildings` (
  `buildingId` int(10) unsigned NOT NULL,
  `seq` int(10) unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `address` varchar(511) NOT NULL,
  `timeOpenFrom` time NOT NULL,
  `timeOpenTill` time NOT NULL,
  PRIMARY KEY (`buildingId`),
  UNIQUE KEY `ID_Building_IND` (`buildingId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Buildings`
--

LOCK TABLES `Buildings` WRITE;
/*!40000 ALTER TABLE `Buildings` DISABLE KEYS */;
INSERT INTO `Buildings` VALUES (4,144,'Mensa Bingen','Berlinstraße 109, 55411 Bingen','10:00:10','15:30:00'),(8,150,'Café Bingen Rochusberg','Rochusallee 4, 55411 Bingen','11:00:00','14:00:00');
/*!40000 ALTER TABLE `Buildings` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `Buildings_BINS` BEFORE INSERT ON `Buildings` FOR EACH ROW
BEGIN
    SET NEW.seq = getNextSeq("sync");
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `Buildings_BUPD` BEFORE UPDATE ON `Buildings` FOR EACH ROW
BEGIN
    SET NEW.seq = getNextSeq("sync");
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `Buildings_BDEL` BEFORE DELETE ON `Buildings` FOR EACH ROW
BEGIN
    INSERT INTO _deletes(`seq`,`tableName`,`deleteSeqNumber`) 
    VALUES(getNextSeq("sync"), "Building", OLD.seq);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `Dates`
--

DROP TABLE IF EXISTS `Dates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Dates` (
  `dateId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `seq` int(10) unsigned NOT NULL DEFAULT '0',
  `date` date NOT NULL,
  PRIMARY KEY (`dateId`),
  UNIQUE KEY `ID_Dates_IND` (`dateId`),
  UNIQUE KEY `date_UNIQUE` (`date`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Dates`
--

LOCK TABLES `Dates` WRITE;
/*!40000 ALTER TABLE `Dates` DISABLE KEYS */;
INSERT INTO `Dates` VALUES (20,286,'2015-11-16'),(21,299,'2015-11-17'),(22,312,'2015-11-18'),(23,324,'2015-11-19'),(24,336,'2015-11-20'),(25,348,'2015-11-23'),(26,360,'2015-11-24'),(27,372,'2015-11-25'),(28,383,'2015-11-26'),(29,396,'2015-11-27');
/*!40000 ALTER TABLE `Dates` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `Dates_BINS` BEFORE INSERT ON `Dates` FOR EACH ROW
BEGIN
    SET NEW.seq = getNextSeq("sync");
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `Dates_BUPD` BEFORE UPDATE ON `Dates` FOR EACH ROW
BEGIN
    SET NEW.seq = getNextSeq("sync");
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `Dates_BDEL` BEFORE DELETE ON `Dates` FOR EACH ROW
BEGIN
    INSERT INTO _deletes(`seq`,`tableName`,`deleteSeqNumber`) 
    VALUES(getNextSeq("sync"), "Date", OLD.seq);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `Dishes`
--

DROP TABLE IF EXISTS `Dishes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Dishes` (
  `dishId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `seq` int(10) unsigned NOT NULL DEFAULT '0',
  `title` varchar(255) NOT NULL,
  `ingredients` varchar(45) NOT NULL,
  `priceStd` decimal(4,2) NOT NULL,
  `priceNonStd` decimal(4,2) NOT NULL,
  `type` tinyint(4) NOT NULL,
  `fk_buildingId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`dishId`),
  UNIQUE KEY `ID_Dishes_IND` (`dishId`),
  KEY `FKofferedIn_IND` (`fk_buildingId`),
  CONSTRAINT `FKofferedIn_FK` FOREIGN KEY (`fk_buildingId`) REFERENCES `Buildings` (`buildingId`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Dishes`
--

LOCK TABLES `Dishes` WRITE;
/*!40000 ALTER TABLE `Dishes` DISABLE KEYS */;
INSERT INTO `Dishes` VALUES (48,429,'Pulled Pork,verfeinert mit BBQ-Sauce im Burgerbrötchen und Coleslaw','Se,2,S,Sf,Gl,La,Sl,So',2.80,5.15,0,4),(49,430,'Farfalle mit Tomatenwürfeln und sahniger Blattspinatsauce','Gl,La',1.70,3.15,1,4),(50,365,'Fränkische Kartoffelsuppe','3,La,Sl',0.25,0.45,1,4),(51,431,'Weißer Bohneneintopf','Sl',0.40,0.75,1,4),(52,432,'Farfalle','Gl',0.50,0.90,2,4),(53,310,'Pommes frites','',0.60,1.10,2,4),(54,300,'Schwäbischer Rahmbraten in deftiger Sauce','S,Sf,La',1.65,1.65,0,4),(55,302,'Allgäuer Käsespätzle mit Röstzwiebeln','Ei,Gl,La,Sl',1.95,1.95,1,4),(56,304,'Lauchcremesuppe','1,La,So',0.25,0.25,1,4),(57,306,'Chinesischer Gemüseeintopf','Sl',0.40,0.40,2,4),(58,308,'Spätzle','Ei,Gl',0.50,0.50,1,4),(59,313,'Paniertes Schweineschnitzel Schweineschnitzel \"Strindberg\"','S,Sf,Ei,Gl,Sl',1.80,1.80,0,4),(60,315,'Gebratene Möhrenschnitzel mit Spinatsauce','Se,Ei,Gl,La,Sl,So',1.60,1.60,1,4),(61,377,'Gemüsebrühe','Sl',0.25,0.45,2,4),(62,403,'Hausgemachter Linseneintopf','Sf,Sl',0.40,0.75,2,4),(63,345,'Kartoffeln','',0.50,0.90,2,4),(64,325,'Putengeschnetzeltes \"Züricher Art\"','1,La,G,So',1.60,1.60,0,4),(65,327,'Vegetarische Frühlingsrolle mit Sweet Chili Sauce','Ei,Gl,Sl',1.60,1.60,1,4),(66,329,'Champignonsuppe','La',0.25,0.25,1,4),(67,331,'Pichelsteiner Eintopf','Sl',0.40,0.40,2,4),(68,357,'Reis','',0.50,0.90,2,4),(69,337,'Schlemmerfilet \"Italiano\" mit Tomatensauce','Fi,Gl,La',1.75,1.75,0,4),(70,339,'Pfälzer Dampfnudel mit Dessertsauce \"Vanillegeschmack\" mit heißen Kirschen','Gl,La',1.90,1.90,1,4),(71,353,'Broccolirahmsuppe','1,La,Sl,So',0.25,0.45,1,4),(72,343,'Berliner Erbseneintopf','Sl',0.40,0.40,2,4),(73,407,'Paniertes Hähnchenschnitzel mit Champignonsahnesauce','Sf,Gl,La,G',1.85,3.40,0,4),(74,408,'Rigatoni \"gorgonzola e noce\" mit Gorgonzola-Walnuss-Sauce und Rucola','Gl,La,Nu,Sl',2.20,4.05,1,4),(75,409,'Kartoffel-Möhren-Kichererbsen-Eintopf','Sl',0.40,0.75,2,4),(76,410,'Schmetterlingsnudeln mit Schinken mit Tomatensauce','2,3,S,Gl,8',1.95,3.60,0,4),(77,411,'2 Kartoffel-Käsetaschen mit Schnittlauchsauce','La',1.70,3.15,1,4),(78,412,'\"Minestrone\", italienischer Gemüseeintopf','Sl',0.40,0.75,2,4),(79,413,'Kartoffeln oder Farfalle','Gl',0.50,0.90,2,4),(80,414,'\"Kötbullar\", schwedische Hackfleischklopse und Preiselbeeren mit Rahmsauce','R,S,3,Sf,Ei,La',1.75,3.20,0,4),(81,415,'Asiatische Reispfanne, mit Gemüse, Pilzen und Sojakeimlingen mit Schmand','La,Sl,So',1.55,2.85,1,4),(82,416,'Badischer Kartoffeleintopf','La,Sl',0.40,0.75,1,4),(83,417,'Sauer eingelegter Rinderbraten','R,Sf,Nu,Sl',1.90,3.50,0,4),(84,418,'Karibische Linsen mit buntem Gemüse und Ananas','Nu,Sl',1.45,2.65,2,4),(85,419,'Blumenkohlcremesuppe','La,Sl',0.25,0.45,1,4),(86,420,'Schnippelbohneneintopf','Sl',0.40,0.75,2,4),(87,421,'mit Rotkohl','3,9',0.50,0.90,2,4),(88,422,'Kloß oder Pommes frites','Ei,La',0.60,1.10,1,4),(89,423,'Piccata vom Seelachs mit Kräuterrahmsauce','1,2,Fi,Ei,Gl,La,Sl,So',2.20,4.05,0,4),(90,399,'Broccolisouffle mit Kerbel-Karottenschaum','Ei,Gl,La,Sl,So',0.00,0.00,1,4),(91,424,'Tomatensuppe','',0.25,0.45,2,4),(92,435,'Farfalle mit Tomatenwürfeln und sahniger Blattspinatsauce','Gl,La',1.70,3.15,1,8);
/*!40000 ALTER TABLE `Dishes` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `Dishes_BINS` BEFORE INSERT ON `Dishes` FOR EACH ROW
BEGIN
    SET NEW.seq = getNextSeq("sync");
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `Dishes_BUPD` BEFORE UPDATE ON `Dishes` FOR EACH ROW
BEGIN
    SET NEW.seq = getNextSeq("sync");
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `Dishes_BDEL` BEFORE DELETE ON `Dishes` FOR EACH ROW
BEGIN
    INSERT INTO _deletes(`seq`,`tableName`,`deleteSeqNumber`) 
    VALUES(getNextSeq("sync"), "Dish", OLD.seq);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `Photos`
--

DROP TABLE IF EXISTS `Photos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Photos` (
  `photoId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `seq` int(10) unsigned DEFAULT NULL COMMENT 'Filled by trigger before INSERT or UPDATE',
  `dateUpload` date NOT NULL,
  `complains` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `fk_dishId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`photoId`),
  UNIQUE KEY `ID_Photos_IND` (`photoId`),
  KEY `FKtakenPhotos_IND` (`fk_dishId`),
  CONSTRAINT `FKtakenPhotos_FK` FOREIGN KEY (`fk_dishId`) REFERENCES `Dishes` (`dishId`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Photos`
--

LOCK TABLES `Photos` WRITE;
/*!40000 ALTER TABLE `Photos` DISABLE KEYS */;
/*!40000 ALTER TABLE `Photos` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `Photos_BINS` BEFORE INSERT ON `Photos` FOR EACH ROW
BEGIN
    SET NEW.seq = getNextSeq("sync");
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `Photos_BUPD` BEFORE UPDATE ON `Photos` FOR EACH ROW
BEGIN
    SET NEW.seq = getNextSeq("sync");
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `Photos_BDEL` BEFORE DELETE ON `Photos` FOR EACH ROW
BEGIN
    INSERT INTO _deletes(`seq`,`tableName`,`deleteSeqNumber`) 
    VALUES(getNextSeq("sync"), "Photo", OLD.seq);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `Ratings`
--

DROP TABLE IF EXISTS `Ratings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Ratings` (
  `ratingId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `seq` int(10) unsigned DEFAULT NULL,
  `date` date NOT NULL,
  `value` tinyint(3) unsigned NOT NULL,
  `fk_dishId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ratingId`),
  UNIQUE KEY `ID_Ratings_IND` (`ratingId`),
  KEY `FKrated_IND` (`fk_dishId`),
  CONSTRAINT `FKrated_FK` FOREIGN KEY (`fk_dishId`) REFERENCES `Dishes` (`dishId`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Ratings`
--

LOCK TABLES `Ratings` WRITE;
/*!40000 ALTER TABLE `Ratings` DISABLE KEYS */;
/*!40000 ALTER TABLE `Ratings` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `Ratings_BINS` BEFORE INSERT ON `Ratings` FOR EACH ROW
BEGIN
    SET NEW.seq = getNextSeq("sync");
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `Ratings_BUPD` BEFORE UPDATE ON `Ratings` FOR EACH ROW
BEGIN
    SET NEW.seq = getNextSeq("sync");
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `Ratings_BDEL` BEFORE DELETE ON `Ratings` FOR EACH ROW
BEGIN
    INSERT INTO _deletes(`seq`,`tableName`,`deleteSeqNumber`) 
    VALUES(getNextSeq("sync"), "Rating", OLD.seq);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `_deletes`
--

DROP TABLE IF EXISTS `_deletes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_deletes` (
  `seq` int(10) unsigned NOT NULL,
  `tableName` varchar(45) NOT NULL,
  `deleteSeqNumber` int(10) unsigned NOT NULL COMMENT 'Stores DELETE operations on all tables.\nThis is required for keep deletes in sync. Alternative is to have a delete flag on all tables.',
  PRIMARY KEY (`seq`),
  UNIQUE KEY `deleteSeqNumber_UNIQUE` (`deleteSeqNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `_deletes`
--

LOCK TABLES `_deletes` WRITE;
/*!40000 ALTER TABLE `_deletes` DISABLE KEYS */;
INSERT INTO `_deletes` VALUES (83,'Building',80),(84,'Building',21),(90,'Dish',89),(103,'Building',102),(107,'Building',106),(135,'Rating',133),(136,'Rating',126),(137,'Rating',122),(138,'Rating',130),(152,'Building',104),(161,'Dish',156),(162,'Dish',157),(163,'Dish',158),(164,'Dish',159),(165,'Dish',160),(168,'Dish',166),(169,'Dish',167),(235,'Dish',143),(236,'Dish',39),(237,'Dish',40),(238,'Dish',77),(239,'Dish',85),(240,'Dish',86),(241,'Dish',141),(242,'Dish',140),(243,'Dish',180),(244,'Dish',170),(245,'Dish',171),(246,'Dish',173),(247,'Dish',175),(248,'Dish',177),(249,'Dish',195),(250,'Dish',196),(251,'Dish',197),(252,'Dish',198),(253,'Dish',199),(254,'Dish',200),(255,'Dish',202),(256,'Dish',204),(257,'Dish',206),(258,'Dish',232),(259,'Dish',212),(260,'Dish',214),(261,'Dish',216),(262,'Dish',218),(263,'Dish',220),(264,'Dish',224),(265,'Dish',226),(266,'Dish',228),(267,'Dish',230),(268,'Date',13),(269,'Date',14),(270,'Date',15),(271,'Date',41),(272,'Date',42),(273,'Date',91),(274,'Date',92),(275,'Date',93),(276,'Date',94),(277,'Date',95),(278,'Date',96),(279,'Date',97),(280,'Date',98),(281,'Date',99),(282,'Date',100),(283,'Date',193),(284,'Date',211),(285,'Date',223),(425,'Building',146),(426,'Building',148),(427,'Building',149),(428,'Building',151);
/*!40000 ALTER TABLE `_deletes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `_sequence`
--

DROP TABLE IF EXISTS `_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_sequence` (
  `seq_name` varchar(63) NOT NULL,
  `seq_val` int(10) unsigned NOT NULL,
  PRIMARY KEY (`seq_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `_sequence`
--

LOCK TABLES `_sequence` WRITE;
/*!40000 ALTER TABLE `_sequence` DISABLE KEYS */;
INSERT INTO `_sequence` VALUES ('sync',435);
/*!40000 ALTER TABLE `_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `offeredAt`
--

DROP TABLE IF EXISTS `offeredAt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `offeredAt` (
  `fk_dateId` int(10) unsigned NOT NULL,
  `fk_dishId` int(10) unsigned NOT NULL,
  `seq` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`fk_dishId`,`fk_dateId`),
  UNIQUE KEY `ID_offeredAt_IND` (`fk_dishId`,`fk_dateId`),
  KEY `FKoff_Dat_IND` (`fk_dateId`),
  CONSTRAINT `FKoff_Dis` FOREIGN KEY (`fk_dishId`) REFERENCES `Dishes` (`dishId`) ON DELETE CASCADE,
  CONSTRAINT `FKoff_Dat_FK` FOREIGN KEY (`fk_dateId`) REFERENCES `Dates` (`dateId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offeredAt`
--

LOCK TABLES `offeredAt` WRITE;
/*!40000 ALTER TABLE `offeredAt` DISABLE KEYS */;
INSERT INTO `offeredAt` VALUES (20,48,288),(20,49,290),(20,50,292),(26,50,366),(20,51,294),(20,52,296),(20,53,298),(21,53,311),(22,53,323),(23,53,335),(24,53,347),(25,53,359),(26,53,371),(27,53,382),(29,53,406),(21,54,301),(21,55,303),(21,56,305),(21,57,307),(21,58,309),(22,59,314),(22,60,316),(22,61,318),(27,61,378),(22,62,320),(29,62,404),(22,63,322),(24,63,346),(27,63,381),(29,63,405),(23,64,326),(23,65,328),(23,66,330),(23,67,332),(23,68,334),(25,68,358),(24,69,338),(24,70,340),(24,71,342),(25,71,354),(24,72,344),(25,73,350),(25,74,352),(25,75,356),(26,76,362),(26,77,364),(26,78,368),(26,79,370),(27,80,374),(27,81,376),(27,82,380),(28,83,385),(28,84,387),(28,85,389),(28,86,391),(28,87,393),(28,88,395),(29,89,398),(29,90,400),(29,91,402),(20,92,434);
/*!40000 ALTER TABLE `offeredAt` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `offeredAt_BINS` BEFORE INSERT ON `offeredAt` FOR EACH ROW
BEGIN
    SET NEW.seq = getNextSeq("sync");
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `offeredAt_BUPD` BEFORE UPDATE ON `offeredAt` FOR EACH ROW
BEGIN
    SET NEW.seq = getNextSeq("sync");
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `offeredAt_BDEL` BEFORE DELETE ON `offeredAt` FOR EACH ROW
BEGIN
    INSERT INTO _deletes(`seq`,`tableName`,`deleteSeqNumber`) 
    VALUES(getNextSeq("sync"), "OfferedAt", OLD.seq);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Dumping routines for database 'mensa'
--
/*!50003 DROP FUNCTION IF EXISTS `getNextSeq` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getNextSeq`(sSeqName VARCHAR (63)) RETURNS int(10) unsigned
BEGIN
    DECLARE nLast_val INT UNSIGNED;
	SET nLast_val = (SELECT seq_val 
					 FROM _sequence 
					 WHERE seq_name = sSeqName);

    IF nLast_val IS NULL THEN
        SET nLast_val = 1;
        INSERT INTO _sequence(seq_name, seq_val) 
		VALUES (sSeqName, nLast_Val);
    ELSE
        SET nLast_val = nLast_val + 1;
		UPDATE _sequence SET seq_val = nLast_val
        WHERE seq_name = sSeqName;
    END IF;

RETURN nLast_val;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-16 15:54:37
