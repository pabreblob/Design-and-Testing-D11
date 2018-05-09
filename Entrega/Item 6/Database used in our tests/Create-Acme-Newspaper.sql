start transaction;
create database `Acme-Newspaper`;

use `Acme-Newspaper`;

create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';
create user 'acme-manager'@'%' identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

grant select, insert, update, delete 
	on `Acme-Newspaper`.* to 'acme-user'@'%';
grant select, insert, update, delete, create, drop, references, index, alter, 
        create temporary tables, lock tables, create view, create routine, 
        alter routine, execute, trigger, show view
    on `Acme-Newspaper`.* to 'acme-manager'@'%';
-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: Acme-Newspaper
-- ------------------------------------------------------
-- Server version	5.5.29

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
-- Table structure for table `actor_folder`
--

DROP TABLE IF EXISTS `actor_folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_folder` (
  `Actor_id` int(11) NOT NULL,
  `folders_id` int(11) NOT NULL,
  UNIQUE KEY `UK_dp573h40udupcm5m1kgn2bc2r` (`folders_id`),
  CONSTRAINT `FK_dp573h40udupcm5m1kgn2bc2r` FOREIGN KEY (`folders_id`) REFERENCES `folder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_folder`
--

LOCK TABLES `actor_folder` WRITE;
/*!40000 ALTER TABLE `actor_folder` DISABLE KEYS */;
INSERT INTO `actor_folder` VALUES (164,234),(164,235),(164,236),(164,237),(164,238),(165,239),(165,240),(165,241),(165,242),(165,243),(165,244),(166,245),(166,246),(166,247),(166,248),(166,249),(166,250),(167,251),(167,252),(167,253),(167,254),(167,255),(168,256),(168,257),(168,258),(168,259),(168,260),(169,261),(169,262),(169,263),(169,264),(169,265),(170,266),(170,267),(170,268),(170,269),(170,270),(171,271),(171,272),(171,273),(171,274),(171,275),(172,276),(172,277),(172,278),(172,279),(172,280),(173,281),(173,282),(173,283),(173,284),(173,285),(174,286),(174,287),(174,288),(174,289),(174,290);
/*!40000 ALTER TABLE `actor_folder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gfgqmtp2f9i5wsojt33xm0uth` (`userAccount_id`),
  CONSTRAINT `FK_gfgqmtp2f9i5wsojt33xm0uth` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (164,0,'adminaddress','admin@gmail.com','admin','681331066','admsur',153);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `advertisement`
--

DROP TABLE IF EXISTS `advertisement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advertisement` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `bannerUrl` varchar(255) DEFAULT NULL,
  `brandName` varchar(255) DEFAULT NULL,
  `cvv` int(11) NOT NULL,
  `expMonth` int(11) NOT NULL,
  `expYear` int(11) NOT NULL,
  `holderName` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `marked` bit(1) NOT NULL,
  `pageUrl` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `newspaper_id` int(11) NOT NULL,
  `owner_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_2a9jqcvexg35eohaebb71i4xu` (`newspaper_id`),
  KEY `FK_hbjyxyjp7txk0kdrsxfbuo7af` (`owner_id`),
  CONSTRAINT `FK_hbjyxyjp7txk0kdrsxfbuo7af` FOREIGN KEY (`owner_id`) REFERENCES `agent` (`id`),
  CONSTRAINT `FK_2a9jqcvexg35eohaebb71i4xu` FOREIGN KEY (`newspaper_id`) REFERENCES `newspaper` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advertisement`
--

LOCK TABLES `advertisement` WRITE;
/*!40000 ALTER TABLE `advertisement` DISABLE KEYS */;
INSERT INTO `advertisement` VALUES (220,0,'https://www.racefoxx.com/media/image/product/2242/md/yamaha-r1-carbonfibre-engine-cover-clutch-glossy~3.jpg','brand1',134,11,18,'agent1','4024007154986433','\0','https://www.google.com','advertisement1',188,173),(221,0,'https://i0.wp.com/www.baddecisionsvinyl.us/wp-content/uploads/2018/03/90.jpg','brand1',134,11,18,'agent1','4024007154986433','','https://www.google.com','viagra',191,173),(222,0,'https://www.coches.com/fotos_historicas/honda/Civic/high_b04018aad396f2110939e61555d59fe4.jpg','brand1',134,11,18,'agent1','4024007154986433','\0','https://www.google.com','advertisement3',194,173),(223,0,'https://wordlesstech.com/wp-content/uploads/2012/08/Creative-Advertisements-by-Asile-1.jpg','brand1',134,11,18,'agent1','4024007154986433','\0','https://www.google.com','advertisement4',193,173),(224,0,'http://creativecriminals.com/pictures/post/2470/christmasadvertisements17.jpg','brand1',134,11,18,'agent1','4024007154986433','\0','https://www.google.com','advertisement5',190,173),(225,0,'http://creativecriminals.com/pictures/post/2470/christmasadvertisements24.jpg','brand1',134,11,18,'agent1','4024007154986433','\0','https://www.google.com','advertisement6',189,173),(226,0,'https://2static2.fjcdn.com/thumbnails/comments/Thekarts+used+roll+picturethekarts+rolled+image+sweet+jesus+_a17399b5c8103dd30b64ad5f7f637e74.png','brand1',127,11,18,'agent2','4024007154986433','\0','https://www.google.com','advertisement7',188,174);
/*!40000 ALTER TABLE `advertisement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `agent`
--

DROP TABLE IF EXISTS `agent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `agent` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5cg6nedtnilfs6spfq209syse` (`userAccount_id`),
  CONSTRAINT `FK_5cg6nedtnilfs6spfq209syse` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agent`
--

LOCK TABLES `agent` WRITE;
/*!40000 ALTER TABLE `agent` DISABLE KEYS */;
INSERT INTO `agent` VALUES (173,0,'agent1address','agent1@gmail.com','agent1','689378076','agent1sur',162),(174,0,'agent2address','agent2@gmail.com','agent2','689378092','agent2sur',163);
/*!40000 ALTER TABLE `agent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` longtext,
  `finalMode` bit(1) NOT NULL,
  `marked` bit(1) NOT NULL,
  `moment` datetime DEFAULT NULL,
  `summary` longtext,
  `title` varchar(255) DEFAULT NULL,
  `creator_id` int(11) NOT NULL,
  `newspaper_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_4kq8usc8b80hxifqgtl3kfwq4` (`marked`),
  KEY `UK_s748l8rcgwjfy7m2pny5q9ff4` (`creator_id`),
  KEY `UK_pftm848lf5hu8sd0vghfs605l` (`newspaper_id`),
  KEY `UK_1kimup8uio20cy3iit3bpfqvb` (`finalMode`),
  CONSTRAINT `FK_pftm848lf5hu8sd0vghfs605l` FOREIGN KEY (`newspaper_id`) REFERENCES `newspaper` (`id`),
  CONSTRAINT `FK_s748l8rcgwjfy7m2pny5q9ff4` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (199,0,'This is the body of the article','','\0','2017-02-24 23:00:00','This is the summary of the article','article1',165,188),(200,0,'This is the body of the article','','\0','2017-02-24 23:00:00','This is the summary of the article','article2',165,188),(201,0,'This is the body of the article','','\0','2017-02-24 23:00:00','This is the summary of the article','article3',165,188),(202,0,'This is the body of the article','','\0','2017-02-24 23:00:00','This is the summary of the article','article4',165,188),(203,0,'This is the body of the article','','\0','2017-02-24 23:00:00','This is the summary of the article','article5',165,188),(204,0,'This is the body of the article','','\0','2017-02-24 23:00:00','This is the summary of the article','article6',166,188),(205,0,'This is the body of the article','','\0','2017-02-24 23:00:00','This is the summary of the article','article7',166,188),(206,0,'This is the body of the article','','\0','2017-02-23 23:00:00','This is the summary of the article','article8',166,189),(207,0,'This is the body of the article','','\0','2017-02-22 23:00:00','This is the summary of the article','article9',165,190),(208,0,'This is the body of the article','','\0','2017-02-21 23:00:00','This is the summary of the article','article10',166,191),(209,0,'This is the body of the article','','\0','2017-02-20 23:00:00','This is the summary of the article','article11',165,193),(210,0,'sex ','','','2017-02-20 23:00:00','This is the summary of the article','article12',167,194),(211,0,'This is the body of the article','\0','\0',NULL,'This is the summary of the article','article13',167,187),(212,0,'This is the body of the article','\0','\0',NULL,'This is the summary of the article','article14',167,195);
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_pictureurls`
--

DROP TABLE IF EXISTS `article_pictureurls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article_pictureurls` (
  `Article_id` int(11) NOT NULL,
  `pictureUrls` varchar(255) DEFAULT NULL,
  KEY `FK_ss3kw69vpgtib45uv6scfcxbd` (`Article_id`),
  CONSTRAINT `FK_ss3kw69vpgtib45uv6scfcxbd` FOREIGN KEY (`Article_id`) REFERENCES `article` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_pictureurls`
--

LOCK TABLES `article_pictureurls` WRITE;
/*!40000 ALTER TABLE `article_pictureurls` DISABLE KEYS */;
INSERT INTO `article_pictureurls` VALUES (199,'https://upload.wikimedia.org/wikipedia/commons/thumb/8/80/Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg/250px-Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg');
/*!40000 ALTER TABLE `article_pictureurls` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chirp`
--

DROP TABLE IF EXISTS `chirp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chirp` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` longtext,
  `marked` bit(1) NOT NULL,
  `moment` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `creator_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_b7jkym7bhws5bb135mdt6kblo` (`marked`),
  KEY `UK_raeiqpusi8b6g4cltekeo03x2` (`creator_id`),
  CONSTRAINT `FK_raeiqpusi8b6g4cltekeo03x2` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chirp`
--

LOCK TABLES `chirp` WRITE;
/*!40000 ALTER TABLE `chirp` DISABLE KEYS */;
INSERT INTO `chirp` VALUES (179,0,'desc1','\0','2017-03-20 16:12:00','title1',165),(180,0,'viagra','','2017-04-20 16:12:00','title2',165),(181,0,'desc3','\0','2017-03-10 16:12:00','title3',165),(182,0,'desc4','\0','2017-03-10 16:42:00','title4',165),(183,0,'desc5','\0','2017-03-22 16:12:00','title5',165),(184,0,'desc6','\0','2017-08-20 16:12:00','title6',165),(185,0,'desc7','\0','2017-06-20 16:12:00','title7',166),(186,0,'desc8','\0','2018-03-20 16:12:00','title8',167);
/*!40000 ALTER TABLE `chirp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pwmktpkay2yx7v00mrwmuscl8` (`userAccount_id`),
  CONSTRAINT `FK_pwmktpkay2yx7v00mrwmuscl8` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (169,0,'customer1address','customer1@gmail.com','customer1','689361076','customer1sur',158),(170,0,'customer2address','customer2@gmail.com','customer2','689361077','customer2sur',159),(171,0,'customer3address','customer3@gmail.com','customer3','689362076','customer3sur',160),(172,0,'customer4address','customer4@gmail.com','customer4','689352076','customer4sur',161);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_volume`
--

DROP TABLE IF EXISTS `customer_volume`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_volume` (
  `customers_id` int(11) NOT NULL,
  `volumes_id` int(11) NOT NULL,
  KEY `FK_tdm5fmudk7sxy35fv4d5j2o1b` (`volumes_id`),
  KEY `FK_axjbpfbfplt3tx0aewm4p5tyv` (`customers_id`),
  CONSTRAINT `FK_axjbpfbfplt3tx0aewm4p5tyv` FOREIGN KEY (`customers_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `FK_tdm5fmudk7sxy35fv4d5j2o1b` FOREIGN KEY (`volumes_id`) REFERENCES `volume` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_volume`
--

LOCK TABLES `customer_volume` WRITE;
/*!40000 ALTER TABLE `customer_volume` DISABLE KEYS */;
INSERT INTO `customer_volume` VALUES (169,227),(169,228),(169,229),(169,230),(169,231),(169,232),(170,227);
/*!40000 ALTER TABLE `customer_volume` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `folder`
--

DROP TABLE IF EXISTS `folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `folder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_e6lcmpm09goh6x4n16fbq5uka` (`parent_id`),
  CONSTRAINT `FK_e6lcmpm09goh6x4n16fbq5uka` FOREIGN KEY (`parent_id`) REFERENCES `folder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `folder`
--

LOCK TABLES `folder` WRITE;
/*!40000 ALTER TABLE `folder` DISABLE KEYS */;
INSERT INTO `folder` VALUES (234,0,'In box',NULL),(235,0,'Out box',NULL),(236,0,'Notification box',NULL),(237,0,'Trash box',NULL),(238,0,'Spam box',NULL),(239,0,'In box',NULL),(240,0,'Out box',NULL),(241,0,'Notification box',NULL),(242,0,'Trash box',NULL),(243,0,'Spam box',NULL),(244,0,'custom',NULL),(245,0,'In box',NULL),(246,0,'Out box',NULL),(247,0,'Notification box',NULL),(248,0,'Trash box',NULL),(249,0,'Spam box',NULL),(250,0,'custom',245),(251,0,'In box',NULL),(252,0,'Out box',NULL),(253,0,'Notification box',NULL),(254,0,'Trash box',NULL),(255,0,'Spam box',NULL),(256,0,'In box',NULL),(257,0,'Out box',NULL),(258,0,'Notification box',NULL),(259,0,'Trash box',NULL),(260,0,'Spam box',NULL),(261,0,'In box',NULL),(262,0,'Out box',NULL),(263,0,'Notification box',NULL),(264,0,'Trash box',NULL),(265,0,'Spam box',NULL),(266,0,'In box',NULL),(267,0,'Out box',NULL),(268,0,'Notification box',NULL),(269,0,'Trash box',NULL),(270,0,'Spam box',NULL),(271,0,'In box',NULL),(272,0,'Out box',NULL),(273,0,'Notification box',NULL),(274,0,'Trash box',NULL),(275,0,'Spam box',NULL),(276,0,'In box',NULL),(277,0,'Out box',NULL),(278,0,'Notification box',NULL),(279,0,'Trash box',NULL),(280,0,'Spam box',NULL),(281,0,'In box',NULL),(282,0,'Out box',NULL),(283,0,'Notification box',NULL),(284,0,'Trash box',NULL),(285,0,'Spam box',NULL),(286,0,'In box',NULL),(287,0,'Out box',NULL),(288,0,'Notification box',NULL),(289,0,'Trash box',NULL),(290,0,'Spam box',NULL);
/*!40000 ALTER TABLE `folder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `followup`
--

DROP TABLE IF EXISTS `followup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `followup` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` longtext,
  `marked` bit(1) NOT NULL,
  `moment` datetime DEFAULT NULL,
  `summary` longtext,
  `title` varchar(255) DEFAULT NULL,
  `article_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_au589xcdh1v655391yr0ov4km` (`marked`),
  KEY `UK_aer0q20rslre6418yqlfwmeek` (`article_id`),
  CONSTRAINT `FK_aer0q20rslre6418yqlfwmeek` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `followup`
--

LOCK TABLES `followup` WRITE;
/*!40000 ALTER TABLE `followup` DISABLE KEYS */;
INSERT INTO `followup` VALUES (213,0,'This is the body of the followup','\0','2017-05-27 23:00:00','This is the summary of the followup','followUp2',199),(214,0,'This is the body of the followup','\0','2017-03-29 23:00:00','This is the summary of the followup','followUp3',199),(215,0,'This is the body of the followup','\0','2017-02-28 23:00:00','This is the summary of the followup','followUp4',199),(216,0,'This is the body of the followup','\0','2017-04-27 23:00:00','This is the summary of the followup','followUp5',199),(217,0,'This is the body of the followup','\0','2017-03-27 23:00:00','This is the summary of the followup','followUp6',199),(218,0,'This is the body of the followup','\0','2017-02-27 23:10:00','This is the summary of the followup','followUp6',199),(219,0,'This is the body of the followup','','2017-02-27 23:10:00','This is the summary of the followup','viagra',200);
/*!40000 ALTER TABLE `followup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `followup_pictureurls`
--

DROP TABLE IF EXISTS `followup_pictureurls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `followup_pictureurls` (
  `FollowUp_id` int(11) NOT NULL,
  `pictureUrls` varchar(255) DEFAULT NULL,
  KEY `FK_fo9xpiowwmo0v1ssj1oh1v6rr` (`FollowUp_id`),
  CONSTRAINT `FK_fo9xpiowwmo0v1ssj1oh1v6rr` FOREIGN KEY (`FollowUp_id`) REFERENCES `followup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `followup_pictureurls`
--

LOCK TABLES `followup_pictureurls` WRITE;
/*!40000 ALTER TABLE `followup_pictureurls` DISABLE KEYS */;
INSERT INTO `followup_pictureurls` VALUES (213,'https://upload.wikimedia.org/wikipedia/commons/thumb/8/80/Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg/250px-Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg'),(214,'https://upload.wikimedia.org/wikipedia/commons/thumb/8/80/Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg/250px-Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg'),(215,'https://upload.wikimedia.org/wikipedia/commons/thumb/8/80/Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg/250px-Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg'),(216,'https://upload.wikimedia.org/wikipedia/commons/thumb/8/80/Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg/250px-Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg'),(217,'https://upload.wikimedia.org/wikipedia/commons/thumb/8/80/Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg/250px-Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg'),(218,'https://upload.wikimedia.org/wikipedia/commons/thumb/8/80/Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg/250px-Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg'),(219,'https://upload.wikimedia.org/wikipedia/commons/thumb/8/80/Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg/250px-Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg'),(219,'https://static.boredpanda.com/blog/wp-content/uuuploads/landscape-photography/landscape-photography-15.jpg');
/*!40000 ALTER TABLE `followup_pictureurls` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('DomainEntity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` longtext,
  `moment` datetime DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `folder_id` int(11) NOT NULL,
  `sender_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7t1ls63lqb52igs4ms20cf94t` (`folder_id`),
  CONSTRAINT `FK_7t1ls63lqb52igs4ms20cf94t` FOREIGN KEY (`folder_id`) REFERENCES `folder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (291,0,'bodymessage1','2017-02-24 23:00:00',1,'message1',235,164),(292,0,'bodymessage1','2017-02-24 23:00:00',1,'message1',239,164),(293,0,'bodymessage2','2017-02-24 23:02:00',1,'message2',235,164),(294,0,'bodymessage2','2017-02-24 23:02:00',1,'message2',239,164),(295,0,'bodymessage3','2017-02-24 23:03:00',1,'message3',235,164),(296,0,'bodymessage3','2017-02-24 23:03:00',1,'message3',239,164),(297,0,'bodymessage4','2017-02-24 23:04:00',1,'message4',235,164),(298,0,'bodymessage4','2017-02-24 23:04:00',1,'message4',239,164),(299,0,'bodymessage5','2017-02-24 23:05:00',1,'message5',235,164),(300,0,'bodymessage5','2017-02-24 23:05:00',1,'message5',239,164),(301,0,'bodymessage6','2017-02-24 23:06:00',1,'message6',235,164),(302,0,'bodymessage6','2017-02-24 23:06:00',1,'message6',239,164),(303,0,'viagra','2017-02-24 23:07:00',1,'message7',246,166),(304,0,'viagra','2017-02-24 23:07:00',1,'message7',255,166);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_actor`
--

DROP TABLE IF EXISTS `message_actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_actor` (
  `Message_id` int(11) NOT NULL,
  `recipients_id` int(11) NOT NULL,
  KEY `FK_2340xdahcha0b5cyr6bxhq6ji` (`Message_id`),
  CONSTRAINT `FK_2340xdahcha0b5cyr6bxhq6ji` FOREIGN KEY (`Message_id`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_actor`
--

LOCK TABLES `message_actor` WRITE;
/*!40000 ALTER TABLE `message_actor` DISABLE KEYS */;
INSERT INTO `message_actor` VALUES (291,165),(292,165),(293,165),(294,165),(295,165),(296,165),(297,165),(298,165),(299,165),(300,165),(301,165),(302,165),(303,167),(304,167);
/*!40000 ALTER TABLE `message_actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `newspaper`
--

DROP TABLE IF EXISTS `newspaper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `newspaper` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` longtext,
  `free` bit(1) NOT NULL,
  `marked` bit(1) NOT NULL,
  `pictureUrl` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `publicationDate` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `creator_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_v2y7dxoqcqmpdhq0uscs773q` (`publicationDate`),
  KEY `UK_3hrwdnoln1lunfhkqt25u89q1` (`creator_id`),
  KEY `UK_qcgbyshj8yvku35tsidg0ex7` (`marked`),
  CONSTRAINT `FK_3hrwdnoln1lunfhkqt25u89q1` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `newspaper`
--

LOCK TABLES `newspaper` WRITE;
/*!40000 ALTER TABLE `newspaper` DISABLE KEYS */;
INSERT INTO `newspaper` VALUES (187,0,'This is newspaper1','','\0','https://upload.wikimedia.org/wikipedia/commons/thumb/8/80/Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg/250px-Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg',0,NULL,'newspaper1',165),(188,0,'This is newspaper2','','\0',NULL,0,'2017-02-24 23:00:00','newspaper2',165),(189,0,'This is newspaper3','','\0','https://upload.wikimedia.org/wikipedia/commons/thumb/8/80/Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg/250px-Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg',0,'2017-02-23 23:00:00','newspaper3',165),(190,0,'This is newspaper4','','\0','https://upload.wikimedia.org/wikipedia/commons/thumb/8/80/Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg/250px-Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg',0,'2017-02-22 23:00:00','newspaper4',165),(191,0,'This is newspaper5','','\0','https://upload.wikimedia.org/wikipedia/commons/thumb/8/80/Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg/250px-Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg',0,'2017-02-21 23:00:00','newspaper5',165),(192,0,'This is newspaper6','\0','\0','https://upload.wikimedia.org/wikipedia/commons/thumb/8/80/Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg/250px-Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg',26,NULL,'newspaper6',165),(193,0,'This is newspaper7','\0','\0','https://upload.wikimedia.org/wikipedia/commons/thumb/8/80/Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg/250px-Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg',20,'2017-02-20 23:00:00','newspaper7',166),(194,0,'Sex','\0','','https://upload.wikimedia.org/wikipedia/commons/thumb/8/80/Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg/250px-Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg',20,'2017-02-20 23:00:00','newspaper8',167),(195,0,'This is newspaper9','','\0','https://upload.wikimedia.org/wikipedia/commons/thumb/8/80/Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg/250px-Central_park_manhattan_2_New_York_photo_D_Ramey_Logan.jpg',0,'2017-02-20 23:00:00','newspaper9',165);
/*!40000 ALTER TABLE `newspaper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscription`
--

DROP TABLE IF EXISTS `subscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subscription` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `brandName` varchar(255) DEFAULT NULL,
  `cvv` int(11) NOT NULL,
  `expMonth` int(11) NOT NULL,
  `expYear` int(11) NOT NULL,
  `holderName` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `volume` bit(1) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `newspaper_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_2i5w4btb7x3r6d2jsd213aqct` (`customer_id`),
  KEY `FK_b3d3q413vlktogdjnnus3ep9e` (`newspaper_id`),
  CONSTRAINT `FK_b3d3q413vlktogdjnnus3ep9e` FOREIGN KEY (`newspaper_id`) REFERENCES `newspaper` (`id`),
  CONSTRAINT `FK_2i5w4btb7x3r6d2jsd213aqct` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscription`
--

LOCK TABLES `subscription` WRITE;
/*!40000 ALTER TABLE `subscription` DISABLE KEYS */;
INSERT INTO `subscription` VALUES (196,0,'brand1',156,10,18,'customer1','5521054892890001','\0',169,193),(197,0,'brand1',156,10,18,'customer1','5521054892890001','\0',169,194),(198,0,'brand1',136,10,20,'customer2','4916524284571696','\0',170,194);
/*!40000 ALTER TABLE `subscription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tabooword`
--

DROP TABLE IF EXISTS `tabooword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tabooword` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `word` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2rdxb3r8yoobpv3vxdh2e4ftp` (`word`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tabooword`
--

LOCK TABLES `tabooword` WRITE;
/*!40000 ALTER TABLE `tabooword` DISABLE KEYS */;
INSERT INTO `tabooword` VALUES (175,0,'viagra'),(176,0,'sex'),(177,0,'sexo'),(178,0,'cialis');
/*!40000 ALTER TABLE `tabooword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_o6s94d43co03sx067ili5760c` (`userAccount_id`),
  CONSTRAINT `FK_o6s94d43co03sx067ili5760c` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (165,0,'user1address','user1@gmail.com','user1','681331076','user1sur',154),(166,0,'user2address','user2@gmail.com','user2','681331076','user2sur',155),(167,0,NULL,'use3@gmail.com','user3',NULL,'user3sur',156),(168,0,'user4address','user4@gmail.com','user4','681361076','user4sur',157);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_user`
--

DROP TABLE IF EXISTS `user_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_user` (
  `followers_id` int(11) NOT NULL,
  `following_id` int(11) NOT NULL,
  KEY `FK_c1h4hh6d78lf7t6jkqn3yoi4l` (`following_id`),
  KEY `FK_ipxcfus1p41qgn9xbfhg2aa0r` (`followers_id`),
  CONSTRAINT `FK_ipxcfus1p41qgn9xbfhg2aa0r` FOREIGN KEY (`followers_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_c1h4hh6d78lf7t6jkqn3yoi4l` FOREIGN KEY (`following_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_user`
--

LOCK TABLES `user_user` WRITE;
/*!40000 ALTER TABLE `user_user` DISABLE KEYS */;
INSERT INTO `user_user` VALUES (165,166),(166,165),(167,165);
/*!40000 ALTER TABLE `user_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount`
--

DROP TABLE IF EXISTS `useraccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_csivo9yqa08nrbkog71ycilh5` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount`
--

LOCK TABLES `useraccount` WRITE;
/*!40000 ALTER TABLE `useraccount` DISABLE KEYS */;
INSERT INTO `useraccount` VALUES (153,0,'21232f297a57a5a743894a0e4a801fc3','admin'),(154,0,'24c9e15e52afc47c225b757e7bee1f9d','user1'),(155,0,'7e58d63b60197ceb55a1c487989a3720','user2'),(156,0,'92877af70a45fd6a2ed7fe81e1236b78','user3'),(157,0,'3f02ebe3d7929b091e3d8ccfde2f3bc6','user4'),(158,0,'ffbc4675f864e0e9aab8bdf7a0437010','customer1'),(159,0,'5ce4d191fd14ac85a1469fb8c29b7a7b','customer2'),(160,0,'033f7f6121501ae98285ad77f216d5e7','customer3'),(161,0,'55feb130be438e686ad6a80d12dd8f44','customer4'),(162,0,'83a87fd756ab57199c0bb6d5e11168cb','agent1'),(163,0,'b1a4a6b01cc297d4677c4ca6656e14d7','agent2');
/*!40000 ALTER TABLE `useraccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount_authorities`
--

DROP TABLE IF EXISTS `useraccount_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount_authorities` (
  `UserAccount_id` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_b63ua47r0u1m7ccc9lte2ui4r` (`UserAccount_id`),
  CONSTRAINT `FK_b63ua47r0u1m7ccc9lte2ui4r` FOREIGN KEY (`UserAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount_authorities`
--

LOCK TABLES `useraccount_authorities` WRITE;
/*!40000 ALTER TABLE `useraccount_authorities` DISABLE KEYS */;
INSERT INTO `useraccount_authorities` VALUES (153,'ADMIN'),(154,'USER'),(155,'USER'),(156,'USER'),(157,'USER'),(158,'CUSTOMER'),(159,'CUSTOMER'),(160,'CUSTOMER'),(161,'CUSTOMER'),(162,'AGENT'),(163,'AGENT');
/*!40000 ALTER TABLE `useraccount_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `volume`
--

DROP TABLE IF EXISTS `volume`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `volume` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` longtext,
  `price` double NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `year` varchar(255) DEFAULT NULL,
  `creator_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_hn1yt13d3dygf87w2u7c46mho` (`creator_id`),
  CONSTRAINT `FK_hn1yt13d3dygf87w2u7c46mho` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `volume`
--

LOCK TABLES `volume` WRITE;
/*!40000 ALTER TABLE `volume` DISABLE KEYS */;
INSERT INTO `volume` VALUES (227,0,'voldesc1',0,'volume1','2018',165),(228,0,'voldesc2',0,'volume2','2018',165),(229,0,'voldesc3',20,'volume3','2018',167),(230,0,'voldesc4',0,'volume4','2018',166),(231,0,'voldesc5',0,'volume5','2018',165),(232,0,'voldesc6',0,'volume6','2018',165),(233,0,'voldesc7',0,'volume7','2018',165);
/*!40000 ALTER TABLE `volume` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `volume_newspaper`
--

DROP TABLE IF EXISTS `volume_newspaper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `volume_newspaper` (
  `volumes_id` int(11) NOT NULL,
  `newspapers_id` int(11) NOT NULL,
  KEY `FK_55de0xvt5cb2u4p2xkeofporj` (`newspapers_id`),
  KEY `FK_m83owyms7s3qmk2o1lssxkv8v` (`volumes_id`),
  CONSTRAINT `FK_m83owyms7s3qmk2o1lssxkv8v` FOREIGN KEY (`volumes_id`) REFERENCES `volume` (`id`),
  CONSTRAINT `FK_55de0xvt5cb2u4p2xkeofporj` FOREIGN KEY (`newspapers_id`) REFERENCES `newspaper` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `volume_newspaper`
--

LOCK TABLES `volume_newspaper` WRITE;
/*!40000 ALTER TABLE `volume_newspaper` DISABLE KEYS */;
INSERT INTO `volume_newspaper` VALUES (227,188),(227,189),(227,190),(227,191),(228,188),(229,194),(230,193),(231,188),(232,188),(233,188);
/*!40000 ALTER TABLE `volume_newspaper` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-02 17:45:52
commit;