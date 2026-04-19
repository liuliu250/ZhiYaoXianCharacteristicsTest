-- MySQL dump 10.13  Distrib 9.6.0, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: survey_db
-- ------------------------------------------------------
-- Server version	9.6.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '1cfcaa79-3599-11f1-ae6a-a0ad9f5a3941:1-115';

--
-- Table structure for table `quests`
--

DROP TABLE IF EXISTS `quests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quests` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `priority` json DEFAULT NULL,
  `author` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_shown` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quests`
--

LOCK TABLES `quests` WRITE;
/*!40000 ALTER TABLE `quests` DISABLE KEYS */;
INSERT INTO `quests` VALUES (1,'test','1111',NULL,'liuliu250','2026-04-13 13:37:46',_binary '\0'),(2,'nice_job','2222',NULL,'Anclain','2026-04-13 13:42:52',_binary '\0'),(3,'Test','你知道的',NULL,'Mizuki_','2026-04-13 20:22:39',_binary '\0'),(5,'我的性别是女','那不然呢','[1.0, 0, 0, 0, 0]','liuliu250','2026-04-13 21:04:52',_binary ''),(6,'好','','[1.0, 1.0, -1.0, -1.0, 1.0]','liuliu250','2026-04-13 21:37:51',_binary '\0'),(7,'liuliu250','liuliu250','[1.0, 1.0, -1.0, -1.0, 1.0]','liuliu250','2026-04-13 21:43:00',_binary '\0'),(8,'liuliu2500','4111','null','liuliu250','2026-04-13 21:48:30',_binary '\0'),(9,'liuliu2500','4111','null','liuliu250','2026-04-13 21:51:40',_binary '\0'),(10,'liuliu2500','412','null','liuliu250','2026-04-13 21:53:44',_binary '\0'),(11,'liuliu33660000','412412','[-0.1, 0.02, 0.03, -1.0, -0.5]','Mizuki_','2026-04-13 21:56:44',_binary '\0'),(12,'@Mortis 草了那个武装大炮能发核弹的','哈哈昨天就射了一发','[1.0, 1.0, -1.0, -1.0, 1.0]','差点给我炸死','2026-04-13 21:57:43',_binary '\0'),(13,'比起王者荣耀，原神更好玩','再生父母','[0.05, 0.15, -0.05, 0.0, 0.1]','Mizuki_','2026-04-13 22:13:59',_binary ''),(14,'冰川日才没我录的多','哈哈昨天就射了一发','[-0.99, 0.99, 0.99, -0.99, 0.99]','@Anclain 还说不是gay','2026-04-13 23:47:24',_binary '\0'),(15,'ddos','','[-1.0, 1.0, -1.0, 1.0, 1.0]','ddos','2026-04-13 23:48:02',_binary '\0'),(16,'ddos','ddos','[-1.0, 1.0, 1.0, 1.0, -1.0]','ddos','2026-04-13 23:48:15',_binary '\0'),(17,'我会看木柜子乐队','母鸡卡是什么','[-0.3, 0.25, 0.05, 0.1, -0.1]','liuliu250','2026-04-14 20:37:15',_binary ''),(18,'我有充足的课外时间','mc时长1500h','[0.0, 0.05, 0.15, 0.0, 0.15]','liuliu250','2026-04-14 20:41:46',_binary ''),(19,'我很厌恶成绩比我好的人','“我要考过他！”','[0.0, -0.15, -0.3, -0.15, -0.05]','liuliu250','2026-04-14 20:44:27',_binary ''),(20,'我喜欢独处','思考者','[0.05, 0.05, 0.1, -0.25, -0.25]','liuliu250','2026-04-14 20:47:43',_binary ''),(21,'我以能熟练使用洛必达为豪','尤其是分子分母不趋于0的情况','[-0.1, 0.0, 0.1, 0.0, 0.1]','XHYender','2026-04-14 22:44:14',_binary ''),(22,'我能手证哥德巴赫猜想','1+1=2太简单辣','[0.0, 0.1, -0.1, -0.05, 0.1]','XHYender','2026-04-14 22:45:57',_binary '\0'),(23,'我可以吃紫米大寄巴','吃penis管哥','[0.4, 0.05, 0.0, 0.3, 0.0]','XHYender','2026-04-14 22:47:00',_binary ''),(24,'我可以把好汉的水管装进我的胸膛','《奔跑》唱起来','[0.2, 0.1, -0.1, 0.1, 0.1]','XHYender','2026-04-14 22:49:26',_binary ''),(25,'我热爱谈论政治','见证达人好阿','[-0.2, 0.5, -0.2, 0.15, 0.05]','liuliu250','2026-04-14 23:13:42',_binary ''),(26,'我有喜欢的男生，但表白被拒绝了','不是这个！','[0.6, 0.0, -0.15, 0.05, 0.15]','liuliu250','2026-04-14 23:42:15',_binary '');
/*!40000 ALTER TABLE `quests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_results`
--

DROP TABLE IF EXISTS `test_results`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_results` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dim1` double NOT NULL,
  `dim2` double NOT NULL,
  `dim3` double NOT NULL,
  `dim4` double NOT NULL,
  `dim5` double NOT NULL,
  `matched_profile` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `matched_score` double DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_results`
--

LOCK TABLES `test_results` WRITE;
/*!40000 ALTER TABLE `test_results` DISABLE KEYS */;
INSERT INTO `test_results` VALUES (1,1,1,1,1,1,'哎哟我',1000.114514,'2026-04-15 05:38:46'),(2,0,0,0,0,0,'鸡人',0,'2026-04-15 05:40:42'),(3,0,0,0,0,0,'鸡人',0,'2026-04-15 05:44:12'),(4,0,0,0,0,0,'鸡人',0,'2026-04-15 05:58:21'),(5,0.06896551724137931,-0.3846153846153847,0.16666666666666666,-0.13636363636363635,-0.047619047619047616,'鸡人',0.323865760060035,'2026-04-15 06:00:17'),(6,-0.7677586206896552,0.7219230769230768,0.1408333333333333,0.22772727272727275,0.17904761904761904,'水管',0.9880955165476151,'2026-04-15 06:01:04'),(7,-0.7677586206896552,0.7219230769230768,0.1408333333333333,0.22772727272727275,0.17904761904761904,'水管',0.9880955165476151,'2026-04-15 06:05:21'),(8,-0.8041379310344827,0.39499999999999996,0.33125,-0.07045454545454542,-0.07904761904761903,'kuigo',0.9689837394405552,'2026-04-15 06:14:42');
/*!40000 ALTER TABLE `test_results` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-19  9:01:36
