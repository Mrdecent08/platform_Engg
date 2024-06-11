-- MySQL dump 10.13  Distrib 5.7.31, for Linux (x86_64)
--
-- Host: localhost    Database: MWCdevsecops
-- ------------------------------------------------------
-- Server version	5.7.31-0ubuntu0.16.04.1

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
-- Table structure for table `Functestoptimization`
--

DROP TABLE IF EXISTS `Functestoptimization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Functestoptimization` (
  `testid` int(10) NOT NULL,
  `projectid` int(20) DEFAULT NULL,
  `pname` varchar(40) DEFAULT NULL,
  `totaltestcases` int(10) DEFAULT NULL,
  `passedtestcases` int(10) DEFAULT NULL,
  `failedtestcases` int(10) DEFAULT NULL,
  `totaldefects` int(10) DEFAULT NULL,
  `automatedtestcases` int(10) DEFAULT NULL,
  `sprint` varchar(10) DEFAULT NULL,
  `unimpactedtestcases` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`testid`),
  KEY `projectid` (`projectid`),
  CONSTRAINT `Functestoptimization_ibfk_1` FOREIGN KEY (`projectid`) REFERENCES `Project` (`projectid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Functestoptimization`
--

LOCK TABLES `Functestoptimization` WRITE;
/*!40000 ALTER TABLE `Functestoptimization` DISABLE KEYS */;
INSERT INTO `Functestoptimization` VALUES (1,9,'SNMP_Java',45,13,32,3,4,'sprint1','41'),(2,8,'CloudSim_Java',11,2,2,2,7,'sprint1','4'),(3,11,'Odl_Oxygen_Java',460,2,3,10,15,'sprint1','445'),(4,9,'SNMP_Java',46,30,16,4,5,'sprint2','41'),(5,9,'SNMP_Java',46,42,4,4,5,'sprint3','41'),(6,9,'SNMP_Java',48,42,6,4,5,'sprint4','43'),(7,9,'SNMP_Java',48,44,4,5,6,'sprint5','42'),(8,9,'SNMP_Java',52,50,2,2,7,'sprint6','45'),(9,8,'CloudSim_Java',11,2,2,3,6,'sprint2','5'),(10,8,'CloudSim_Java',8,2,2,3,4,'sprint3','4'),(11,8,'CloudSim_Java',7,2,2,2,3,'sprint4','4'),(12,8,'CloudSim_Java',7,2,2,2,3,'sprint5','4'),(13,8,'CloudSim_Java',4,3,1,1,2,'sprint6','2'),(14,11,'Odl_Oxygen_Java',462,8,18,10,13,'sprint2','449'),(15,11,'Odl_Oxygen_Java',462,25,7,9,10,'sprint3','452'),(16,11,'Odl_Oxygen_Java',469,18,14,5,9,'sprint4','460');
/*!40000 ALTER TABLE `Functestoptimization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Project`
--

DROP TABLE IF EXISTS `Project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Project` (
  `projectid` int(10) NOT NULL,
  `projectname` varchar(40) DEFAULT NULL,
  `owner` varchar(40) DEFAULT NULL,
  `releasedate` date DEFAULT NULL,
  `prereleasedate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastsucessfulbuild` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CIARating` int(1) DEFAULT NULL,
  PRIMARY KEY (`projectid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Project`
--

LOCK TABLES `Project` WRITE;
/*!40000 ALTER TABLE `Project` DISABLE KEYS */;
INSERT INTO `Project` VALUES (8,'CloudSim_Java','root','2020-08-08','2020-01-06 17:14:09','2020-01-06 17:14:09',3),(9,'SNMP_Java','root','2020-08-11','2020-01-06 17:14:28','2020-01-06 17:14:28',4),(11,'Odl_Oxygen_Java','root','2020-07-19','2020-01-23 14:12:45','2020-01-23 14:12:45',4);
/*!40000 ALTER TABLE `Project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Projectlivestatus`
--

DROP TABLE IF EXISTS `Projectlivestatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Projectlivestatus` (
  `id` int(11) NOT NULL,
  `projectname` varchar(50) DEFAULT NULL,
  `sprint` varchar(10) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `sprintrelease` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Projectlivestatus`
--

LOCK TABLES `Projectlivestatus` WRITE;
/*!40000 ALTER TABLE `Projectlivestatus` DISABLE KEYS */;
INSERT INTO `Projectlivestatus` VALUES (1,'CloudSim_Java','sprint1','deployed','Q1'),(2,'CloudSim_Java','sprint2','failed','Q1'),(3,'CloudSim_Java','sprint3','deployed','Q1'),(4,'CloudSim_Java','sprint4','deployed','Q1'),(7,'SNMP_Java','sprint1','failed','Q1'),(8,'SNMP_Java','sprint2','deployed','Q1'),(9,'SNMP_Java','sprint3','deployed','Q1'),(10,'SNMP_Java','sprint4','deployed','Q1'),(11,'SNMP_Java','sprint5','failed','Q1'),(12,'SNMP_Java','sprint6','deployed','Q1'),(13,'Odl_Oxygen_Java','sprint1','deployed','Q1'),(14,'Odl_Oxygen_Java','sprint2','deployed','Q1'),(15,'Odl_Oxygen_Java','sprint3','failed','Q1'),(16,'Odl_Oxygen_Java','sprint4','deployed','Q1'),(17,'Odl_Oxygen_Java','sprint5','deployed','Q1'),(18,'Odl_Oxygen_Java','sprint6','deployed','Q1');
/*!40000 ALTER TABLE `Projectlivestatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `issues`
--

DROP TABLE IF EXISTS `issues`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `issues` (
  `issue_id` int(6) NOT NULL,
  `vulnerability` varchar(200) NOT NULL,
  `Date` date NOT NULL,
  `status` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`issue_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `issues`
--

LOCK TABLES `issues` WRITE;
/*!40000 ALTER TABLE `issues` DISABLE KEYS */;
INSERT INTO `issues` VALUES (1,'StreamCorruptedException and NullPointerException in OpenDaylight odl-mdsal-xsql','2020-01-21','fixed'),(2,'Password change does not result in Karaf clearing cache, allowing old password to still be used','2020-01-12','fixed'),(3,'Denial of Service in OpenDaylight odl-mdsal-xsql','2020-01-05','fixed'),(4,'Denial of Service in adding flows in Open-Daylight(Sending multiple API requests)','2020-12-27','fixed');
/*!40000 ALTER TABLE `issues` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vulnerabilities`
--

DROP TABLE IF EXISTS `vulnerabilities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vulnerabilities` (
  `id` int(5) NOT NULL,
  `type` varchar(50) DEFAULT NULL,
  `project` varchar(25) DEFAULT NULL,
  `sprint` varchar(15) DEFAULT NULL,
  `count` int(10) DEFAULT NULL,
  `severity` varchar(15) DEFAULT NULL,
  `sprintnumber` varchar(10) DEFAULT NULL,
  `assetsaffected` int(5) DEFAULT NULL,
  `vulnerabilitydate` date DEFAULT NULL,
  `datefixed` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vulnerabilities`
--

LOCK TABLES `vulnerabilities` WRITE;
/*!40000 ALTER TABLE `vulnerabilities` DISABLE KEYS */;
INSERT INTO `vulnerabilities` VALUES (1,'InjectionFlaws','SNMP_Java','backlog',10,'CRITICAL','sprint1',1,'2019-12-01',NULL),(2,'AuthenticationProblems','Odl_Oxygen_Java','backlog',6,'CRITICAL','sprint1',1,'2019-12-01',NULL),(3,'Misconfiguration','CloudSim_Java','backlog',54,'MAJOR','sprint1',1,'2019-12-01',NULL),(4,'BrokenAccessControl','SNMP_Java','backlog',9,'MAJOR','sprint2',1,'2019-12-15',NULL),(5,'XMLExternalEntities','Odl_Oxygen_Java','backlog',12,'MAJOR','sprint2',1,'2019-12-15',NULL),(6,'Cross-SiteScripting','CloudSim_Java','backlog',97,'MINOR','sprint2',1,'2019-12-15',NULL),(7,'InsecureDeserialization','SNMP_Java','backlog',59,'MINOR','sprint3',2,'2019-12-30',NULL),(8,'Cross-SiteScripting','Odl_Oxygen_Java','backlog',5,'MINOR','sprint3',1,'2019-12-30',NULL),(9,'InjectionFlaws','CloudSim_Java','backlog',95,'CRITICAL','sprint3',1,'2019-12-30',NULL),(10,'Misconfiguration','SNMP_Java','backlog',3,'MAJOR','sprint4',1,'2020-01-15',NULL),(11,'BrokenAccessControl','Odl_Oxygen_Java','backlog',8,'MAJOR','sprint4',1,'2020-01-15',NULL),(12,'InsufficientLogging','SNMP_Java','backlog',3,'CRITICAL','sprint5',1,'2020-01-29',NULL),(13,'DataExposure','CloudSim_Java','current',93,'MINOR','sprint4',1,'2020-01-15',NULL),(14,'Misconfiguration','SNMP_Java','current',0,'MAJOR','sprint6',1,'2020-01-31',NULL),(15,'Misconfiguration','Odl_Oxygen_Java','backlog',3,'MAJOR','sprint5',2,'2020-01-29',NULL),(16,'BrokenAccessControl','Odl_Oxygen_Java','current',0,'MAJOR','sprint6',2,'2020-01-31',NULL),(17,'InjectionFlaws','SNMP_Java','backlog',8,'CRITICAL','sprint2',1,'2019-12-15',NULL),(18,'AuthenticationProblems','SNMP_Java','backlog',7,'CRITICAL','sprint3',1,'2019-12-30',NULL),(19,'AuthenticationProblems','SNMP_Java','backlog',5,'CRITICAL','sprint4',1,'2020-01-15',NULL),(20,'InsufficientLogging','SNMP_Java','current',2,'CRITICAL','sprint6',1,'2020-01-31',NULL),(21,'InsufficientLogging','Odl_Oxygen_Java','current',0,'CRITICAL','sprint6',1,'2020-01-31',NULL),(22,'AuthenticationProblems','Odl_Oxygen_Java','backlog',2,'CRITICAL','sprint5',1,'2020-01-29',NULL),(23,'InjectionFlaws','Odl_Oxygen_Java','backlog',2,'CRITICAL','sprint4',1,'2020-01-15',NULL),(24,'InjectionFlaws','Odl_Oxygen_Java','backlog',3,'CRITICAL','sprint3',2,'2019-12-30',NULL),(25,'InsufficientLogging','Odl_Oxygen_Java','backlog',4,'CRITICAL','sprint2',2,'2019-12-15',NULL),(26,'InsufficientLogging','CloudSim_Java','backlog',99,'CRITICAL','sprint2',1,'2019-12-15',NULL),(27,'InjectionFlaws','CloudSim_Java','current',24,'CRITICAL','sprint4',1,'2020-01-15',NULL),(28,'AuthenticationProblems','CloudSim_Java','backlog',102,'CRITICAL','sprint1',1,'2019-12-01',NULL),(29,'XMLExternalEntities','SNMP_Java','backlog',12,'MAJOR','sprint1',1,'2019-12-01',NULL),(30,'XMLExternalEntities','SNMP_Java','backlog',6,'MAJOR','sprint3',1,'2019-12-30',NULL),(31,'BrokenAccessControl','SNMP_Java','backlog',2,'MAJOR','sprint5',1,'2020-01-29',NULL),(32,'DataExposure','SNMP_Java','current',0,'MINOR','sprint6',1,'2020-01-31',NULL),(33,'XMLExternalEntities','CloudSim_Java','backlog',52,'MAJOR','sprint2',1,'2019-12-15',NULL),(34,'Misconfiguration','CloudSim_Java','backlog',48,'MAJOR','sprint3',1,'2019-12-30',NULL),(35,'BrokenAccessControl','CloudSim_Java','current',20,'MAJOR','sprint4',1,'2020-01-15',NULL),(36,'BrokenAccessControl','Odl_Oxygen_Java','backlog',14,'MAJOR','sprint1',1,'2019-12-01',NULL),(37,'XMLExternalEntities','Odl_Oxygen_Java','backlog',11,'MAJOR','sprint3',1,'2019-12-30',NULL),(38,'knownvulnerability','Odl_Oxygen_Java','backlog',10,'MINOR','sprint1',1,'2019-12-01',NULL),(39,'InsecureDeserialization','Odl_Oxygen_Java','backlog',7,'MINOR','sprint2',1,'2019-12-15',NULL),(40,'Cross-SiteScripting','Odl_Oxygen_Java','backlog',4,'MINOR','sprint4',1,'2020-01-15',NULL),(41,'knownvulnerability','Odl_Oxygen_Java','backlog',2,'MINOR','sprint5',1,'2020-01-29',NULL),(42,'DataExposure','Odl_Oxygen_Java','current',1,'MINOR','sprint6',2,'2020-01-31',NULL),(43,'InsecureDeserialization','SNMP_Java','backlog',57,'MINOR','sprint5',2,'2020-01-29',NULL),(44,'Cross-SiteScripting','SNMP_Java','backlog',57,'MINOR','sprint4',1,'2020-01-15',NULL),(45,'Cross-SiteScripting','SNMP_Java','backlog',61,'MINOR','sprint2',1,'2019-12-15',NULL),(46,'knownvulnerability','SNMP_Java','backlog',62,'MINOR','sprint1',1,'2019-12-01',NULL),(47,'InsecureDeserialization','CloudSim_Java','backlog',98,'MINOR','sprint1',1,'2019-12-01',NULL),(48,'InsecureDeserialization','CloudSim_Java','backlog',94,'MINOR','sprint3',1,'2019-12-30',NULL),(49,'AuthenticationProblems','CloudSim_Java','current',22,'CRITICAL','sprint4',2,'2020-01-15',NULL),(50,'InsufficientLogging','CloudSim_Java','current',19,'CRITICAL','sprint4',1,'2020-01-15',NULL),(51,'Misconfiguration','CloudSim_Java','current',27,'MAJOR','sprint4',1,'2020-01-15',NULL),(52,'XMLExternalEntities','CloudSim_Java','current',17,'MAJOR','sprint4',1,'2020-01-15',NULL);
/*!40000 ALTER TABLE `vulnerabilities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-05 21:21:01
