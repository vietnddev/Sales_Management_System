-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: flowiee
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accountEntity`
--

DROP TABLE IF EXISTS `accountEntity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `accountEntity` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(50) NOT NULL,
  `Password` varchar(500) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Gender` int(11) DEFAULT NULL,
  `Phone` varchar(50) DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL,
  `Avatar` varchar(250) DEFAULT NULL,
  `notes` varchar(500) DEFAULT NULL,
  `Status` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accountEntity`
--

LOCK TABLES `accountEntity` WRITE;
/*!40000 ALTER TABLE `accountEntity` DISABLE KEYS */;
INSERT INTO `accountEntity` VALUES (1,'admin','$2a$10$e5.zPCDMNKIuPloVNlVje.o3NG/nuTTngNtSoZV9/K3vIX2AQD3KW','Quản trị hệ thống',0,'0706820684','nguyenducviet0684@gmail.com',NULL,NULL,0),(20,'koma','$2a$10$Mj8VbmTnaImIvJYj0orxlugBaZ9IjO1Lml6MDXoCPvRTS4N3pxk52','Koma Nguyen',0,'0706820684','nguyenducviet0684@gmail.com',NULL,NULL,0);
/*!40000 ALTER TABLE `accountEntity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `category` (
  `CategoryID` int(11) NOT NULL AUTO_INCREMENT,
  `Code` varchar(50) DEFAULT NULL,
  `Type` varchar(100) NOT NULL,
  `Name` varchar(250) NOT NULL,
  `Sort` int(11) DEFAULT NULL,
  `Note` varchar(250) DEFAULT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`CategoryID`)
) ENGINE=InnoDB AUTO_INCREMENT=10031 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'colorProduct','0','Loại màu sắc',2,'2',1),(3,'colorProduct','1','Xanh hoa nhí',1,'',1),(4,'typeProduct','0','Loại sản phẩm',0,'',0),(7,'sizeProduct','0','Loại kích cỡ',3,'3',1),(8,'vocabulary','0','Loại từ vựng',8,'8',1),(9,'grammar','0','Loại ngữ pháp',9,'9',1),(1034,'sizeProduct','1','L',3,'',1),(1057,'sizeProduct','1','M',2,'',1),(1061,'typeProduct','1','Váy',4,'',1),(1063,'colorProduct','1','Đen',2,'',1),(1074,'','1','Order',0,'',0),(1075,'','1','Order',0,'',0),(1076,'','1','Chờ xác nhận',1,'',1),(1077,'','1','Đã xác nhận',2,'',1),(1078,'','1','Đang giao hàng',3,'',1),(1079,'','1','Đã giao hàng',4,'',1),(1080,'sizeProduct','1','XL',4,'',1),(1081,'','1','Đã hoàn thành',5,'',1),(1082,'channel','1','Kênh mua hàng',5,'5',1),(1083,'','1','Channel',0,'',0),(1084,'','1','Channel',0,'',0),(1085,'','1','Shopee',1,'https://shopee.vn/flowiee.official?categoryId=100017&itemId=19823520129',2),(1086,'','1','Facebook',2,'https://www.facebook.com/flowiee.official/',2),(1087,'','1','Instagram',3,'https://www.instagram.com/flowiee.official/?fbclid=IwAR0rCA9bRd0X7H3fNuh1fppKkyGszx8IEqJzufBi5Xv8rARAGAtAIZuMOnc',2),(1088,'','1','Hotline',4,'0386 621 821',2),(1089,'','1','Khác',6,'',2),(1090,'typeProduct','1','Thực phẩm',5,'',1),(1091,'notification','1','Loại thông báo',6,'6',1),(1092,'','1','Thông báo hệ thống',0,'',1),(1093,'typeProduct','1','Phụ kiện',6,'',1),(1094,'','1','Zalo',5,'',2),(1102,'colorProduct','1','Trắng',0,'',1),(1103,'typeProduct','1','Thịt heo các loại',0,'1121212',1),(1104,'typeProduct','1','Kem các loại',0,'',1),(1105,'typeProduct','1','Mì ăn liền',0,'',1),(1106,'typeProduct','1','Nước mắm',0,'',1),(1110,'typeProduct','1','Giấy vệ sinh',0,'',1),(1111,'sizeProduct','1','S',1,'',1),(1112,'','1','Trường học',2,'',1),(1113,'','1','Giới thiệu',1,'',1),(1114,'','1','Xe cộ',5,'',1),(1115,'','1','Động vật',4,'',1),(1116,'','1','Thực vật',3,'',1),(1117,'','1','Địa lý',6,'',1),(1118,'','1','Lịch sử',7,'',1),(1119,'','1','Nhà cửa',8,'',1),(1120,'wave-rsx','1','Theo dõi thay nhớt xe',10,'10',1),(1121,'','1','[Lần x] HEAD VISACOOP 5 - 24/09/2022',9,'28202 Km </br>\r\nA22/18-A22/19 Quốc lộ 50, Xã Bình Hưng, Huyện Bình Chánh, Thành phố Hồ Chí Minh',1),(1122,'','1','Số khung',1,'',1),(1123,'','1','Số máy',2,'',1),(1124,'','1','[Lần x] HEAD VISACOOP 5 - xx/xx/2022',8,'xxxxx Km\r\nA22/18-A22/19 Quốc lộ 50, Xã Bình Hưng, Huyện Bình Chánh, Thành phố Hồ Chí Minh',1),(1125,'','1','Mẫu câu giới thiệu',1,'',1),(1126,'','1','Bảng chữ cái - Nguyên âm',0,'',1),(1127,'','1','Bảng chữ cái - Phụ âm',0,'',1),(1128,'','1','hello',0,'',1),(1129,'','1','[Lần x] HEAD HUỲNH THÀNH x - 20/11/2022',10,'28202 Km <br>\r\nHuyện Mỏ Cày Nam, Tỉnh Bến Tre',1),(1130,'fieldtype','1','Loại trường của loại tài liệu',11,'11',1),(1132,'','1','text',1,'',1),(1133,'','1','number',2,'',1),(10014,'colorProduct','1','Màu tương cà',1,'',1),(10018,'cc','1','cò con',3,'',1),(10020,'caheo3','1','Sông',32,'3212313213',0),(10021,'Loại từ vựng','1','tobe',1,'',1),(10023,'animals','0','Loại phụ kiện',10,'Test',1),(10024,'animals','1','Con mèo',0,'cat2',1),(10025,'animals','1','Con heo',2,'',1),(10027,'sizeProduct','1','XXX',4,'',1),(10028,'typeProduct','1','Linh kiện máy tính',1,'',1),(10029,'colorProduct','1','Xám',1,'',1),(10030,'typeLatop','0','Loại laptop',1,'',1);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `comment` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDProduct` int(11) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `Phone` varchar(20) DEFAULT NULL,
  `Created` datetime NOT NULL,
  `Describes` varchar(10000) NOT NULL,
  `Status` int(11) DEFAULT NULL,
  `Code` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,1,'Nguyễn Đức Việt','vietnd@fsivietnam.com.vn','706820684','2022-12-12 00:00:00','Chưa có bình luận',1,0),(2,1,'Nguyễn Đức Việt','vietnd@fsivietnam.com.vn','706820684','2022-12-12 00:00:00','Chưa có bình luận',1,1),(3,1,'Quản trị hệ thống','nguyenducviet0684@gmail.com','0706820684','2022-10-27 20:18:02','',1,1),(4,1,'Quản trị hệ thống','nguyenducviet0684@gmail.com','0706820684','2022-10-27 20:18:07','1212',1,1),(5,1,'Quản trị hệ thống','nguyenducviet0684@gmail.com','0706820684','2022-10-27 20:18:11','',1,1),(6,1,'Cmt gốc 2','vietnd@fsivietnam.com.vn','706820684','2022-12-12 00:00:00','Cmt gốc 2',1,0),(7,1,'rp Cmt gốc 2','vietnd@fsivietnam.com.vn','706820684','2022-12-12 00:00:00','rp Cmt gốc 2',1,6),(8,1,'Quản trị hệ thống','null','null','2023-01-02 17:39:41','',1,1),(9,1,'Quản trị hệ thống','null','null','2023-01-02 17:39:46','',1,1);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `customer` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Password` varchar(50) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Phone` varchar(20) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Address` varchar(500) NOT NULL,
  `Status` tinyint(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'Flowiee@123','Nguyễn Đức Việt','070682068112','nguyenducviet0684@gmail.com','Tòa nhà Conic Riverside, Đường Số 1, KDC Conic, Quận 8, Thành phố Hồ Chí Minh',0),(2,'Flowiee@123','Xuân An','0706820682','Flowiee@','Tòa nhà Conic Riverside, Đường Số 1, KDC Conic, Quận 8, Thành phố Hồ Chí Minh',1),(3,'Flowiee@123','Kiều Linh','0706820680','Flowiee@','Tòa nhà Conic Riverside, Đường Số 1, KDC Conic, Quận 8, Thành phố Hồ Chí Minh',1),(4,'Flowiee@123','Nam','0706820683','Flowiee@','Tòa nhà Conic Riverside, Đường Số 1, KDC Conic, Quận 8, Thành phố Hồ Chí Minh',1),(5,'Flowiee@123','Koma','0706820684','Flowiee@','Tòa nhà Conic Riverside, Đường Số 1, KDC Conic, Quận 8, Thành phố Hồ Chí Minh',1);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detailorder`
--

DROP TABLE IF EXISTS `detailorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `detailorder` (
  `DetailOrderID` int(11) NOT NULL AUTO_INCREMENT,
  `IDOrders` int(11) NOT NULL,
  `IDProduct` int(11) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `UnitPrice` float NOT NULL,
  `Quantity` int(11) NOT NULL,
  `TotalMoney` float NOT NULL,
  `Note` varchar(500) DEFAULT NULL,
  `Status` tinyint(1) NOT NULL,
  PRIMARY KEY (`DetailOrderID`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detailorder`
--

LOCK TABLES `detailorder` WRITE;
/*!40000 ALTER TABLE `detailorder` DISABLE KEYS */;
INSERT INTO `detailorder` VALUES (1,1,4,'Thùng 24 ly mì Modern lẩu Thái tôm 65g',175000,1,175000,'',1),(2,1,3,'Thùng 30 gói mì Hảo Hảo tôm chua cay 75g',108000,1,108000,'',1),(3,1,2,'FLOWIEE - Áo khoát voan xuyên thấu Hanbok',330000,1,330000,'',1),(4,1,1,'FLOWIEE - Áo khoát voan xuyên thấu Hanbok',330000,1,330000,'',1),(5,2,1,'FLOWIEE - Áo khoát voan xuyên thấu Hanbok',999999,2,2000000,'',1),(6,2,2,'FLOWIEE - Áo khoát voan xuyên thấu Hanbok',330000,1,330000,'',1),(7,2,3,'Thùng 30 gói mì Hảo Hảo tôm chua cay 75g',108000,1,108000,'',1),(8,2,4,'Thùng 24 ly mì Modern lẩu Thái tôm 65g',175000,1,175000,'',1),(9,3,1,'FLOWIEE - Áo khoát voan xuyên thấu Hanbok',0,2,0,'',1),(10,4,1,'FLOWIEE - Áo khoát voan xuyên thấu Hanbok',909999,2,1820000,'Khuyến mãi 9 %',1),(11,5,1,'FLOWIEE - Áo khoát voan xuyên thấu Hanbok',909999,3,2730000,'Khuyến mãi 9 %',1),(12,5,2,'FLOWIEE - Áo khoát voan xuyên thấu Hanbok',320100,1,320100,'Khuyến mãi 3 %',1),(13,5,3,'Thùng 30 gói mì Hảo Hảo tôm chua cay 75g',105840,1,105840,'Khuyến mãi 2 %',1),(14,5,4,'Thùng 24 ly mì Modern lẩu Thái tôm 65g',148750,1,148750,'Khuyến mãi 15 %',1),(15,6,1,'FLOWIEE - Áo khoát voan xuyên thấu Hanbok',909999,1,909999,'Khuyến mãi 9 %',1),(16,7,1,'FLOWIEE - Áo khoát voan xuyên thấu Hanbok',909999,1,909999,'Khuyến mãi 9 %',1),(17,8,1,'FLOWIEE - Áo khoát voan xuyên thấu Hanbok',909999,1,909999,'Khuyến mãi 9 %',1),(18,8,3,'Thùng 30 gói mì Hảo Hảo tôm chua cay 75g',105840,1,105840,'Khuyến mãi 2 %',1),(19,8,4,'Thùng 24 ly mì Modern lẩu Thái tôm 65g',148750,1,148750,'Khuyến mãi 15 %',1),(20,9,1,'FLOWIEE - Áo khoát voan xuyên thấu Hanbok',909999,1,909999,'Khuyến mãi 9 %',1),(21,10,1,'FLOWIEE - Áo khoát voan xuyên thấu Hanbok',909999,1,909999,'Khuyến mãi 9 %',1),(22,1,1,'FLOWIEE - Áo khoát voan xuyên thấu Hanbok',909999,1,909999,'Khuyến mãi 9 %',1),(23,1,2,'FLOWIEE - Áo khoát voan xuyên thấu Hanbok 123',320100,1,320100,'Khuyến mãi 3 %',1),(24,2,2,'FLOWIEE - Áo khoát voan xuyên thấu Hanbok 123',320100,1,320100,'Khuyến mãi 3 %',1),(25,3,2,'FLOWIEE - Áo khoát voan xuyên thấu Hanbok 123',320100,1,320100,'Khuyến mãi 3 %',1),(26,4,4,'Thùng 24 ly mì Modern lẩu Thái tôm 65g',148750,1,148750,'Khuyến mãi 15 %',1),(27,5,2,'FLOWIEE - Áo khoát voan xuyên thấu Hanbok 123',320100,1,320100,'Khuyến mãi 3 %',1),(28,6,5,'123st',1,1,1,'Khuyến mãi 0 %',1),(29,7,1,'FLOWIEE - Áo khoát voan xuyên thấu Hanbok',909999,1,909999,'Khuyến mãi 9 %',1),(30,8,1,'FLOWIEE - Áo khoát voan xuyên thấu Hanbok',909999,1,909999,'Khuyến mãi 9 %',1),(31,8,2,'FLOWIEE - Áo khoát voan xuyên thấu Hanbok 123',320100,1,320100,'Khuyến mãi 3 %',1),(32,9,2,'FLOWIEE - Áo khoát voan xuyên thấu Hanbok 123',320100,1,320100,'Khuyến mãi 3 %',1);
/*!40000 ALTER TABLE `detailorder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `docdata`
--

DROP TABLE IF EXISTS `docdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `docdata` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDDocField` int(11) NOT NULL,
  `Value` varchar(5000) DEFAULT NULL,
  `IDDoc` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=160 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `docdata`
--

LOCK TABLES `docdata` WRITE;
/*!40000 ALTER TABLE `docdata` DISABLE KEYS */;
INSERT INTO `docdata` VALUES (1,1,'',1),(2,2,'',1),(3,1,'',2),(4,2,'',2),(5,3,'',1),(6,4,'',1),(7,5,'',1),(8,1,'',4),(9,2,'',4),(10,1,'1',5),(11,2,'2',5),(12,1,'123',6),(13,2,'',6),(14,1,'',7),(15,2,'',7),(16,1,'',8),(17,2,'',8),(18,1,'',9),(19,2,'',9),(20,1,'',10),(21,2,'',10),(22,1,'123',11),(23,2,'456',11),(24,1,'34',12),(25,2,'345',12),(26,3,'f3 12',13),(27,4,'f4 3 22',13),(28,5,'f5 52',13),(29,6,'122',7),(30,6,'',10),(31,6,'789',11),(32,6,'9',12),(33,1,'11',14),(34,2,'22',14),(35,6,'33',14),(36,1,'4444444444',16),(37,2,'12222222222222',16),(38,6,'1',16),(39,1,'',17),(40,2,'',17),(41,6,'',17),(42,1,'',18),(43,2,'',18),(44,6,'',18),(45,3,'',19),(46,4,'',19),(47,5,'',19),(48,1,'',2),(49,2,'',2),(50,6,'',2),(51,1,'',3),(52,2,'',3),(53,6,'',3),(54,1,'',4),(55,2,'',4),(56,6,'',4),(57,1,'3',5),(58,2,'4',5),(59,6,'55',5),(60,1,'',6),(61,2,'',6),(62,6,'',6),(63,1,'',7),(64,2,'',7),(65,6,'',7),(66,1,'',8),(67,2,'',8),(68,6,'',8),(69,1,'',10),(70,2,'',10),(71,6,'',10),(72,1,'',2),(73,2,'',2),(74,6,'',2),(75,1,'',3),(76,2,'',3),(77,6,'',3),(78,1,'',2),(79,2,'',2),(80,6,'',2),(81,1,'',3),(82,2,'',3),(83,6,'',3),(84,1,'',4),(85,2,'',4),(86,6,'',4),(87,1,'',5),(88,2,'',5),(89,6,'',5),(90,1,'',10),(91,2,'',10),(92,6,'',10),(93,1,'',11),(94,2,'',11),(95,6,'',11),(96,1,'678',12),(97,2,'987',12),(98,6,'',12),(99,3,'',14),(100,4,'',14),(101,5,'',14),(102,1,'1',16),(103,2,'kkkkkkkkkkk',16),(104,6,'1',16),(105,1,'',18),(106,2,'',18),(107,6,'',18),(108,1,'',19),(109,2,'',19),(110,6,'',19),(111,1,'',21),(112,2,'',21),(113,6,'',21),(114,1,'',23),(115,2,'',23),(116,6,'',23),(117,1,'',24),(118,2,'',24),(119,6,'',24),(120,1,'',26),(121,2,'',26),(122,6,'',26),(123,1,'',28),(124,2,'',28),(125,6,'',28),(126,7,'',4),(127,7,'',5),(128,7,'',21),(129,7,'',23),(130,7,'',28),(131,8,'',4),(132,8,'',5),(133,8,'',21),(134,8,'',23),(135,8,'',28),(136,1,'',29),(137,2,'',29),(138,6,'',29),(139,1,'',30),(140,2,'',30),(141,6,'',30),(142,3,'',31),(143,4,'',31),(144,5,'',31),(145,1,'',2),(146,2,'',2),(147,6,'',2),(148,1,'',3),(149,2,'',3),(150,6,'',3),(151,3,'',4),(152,4,'',4),(153,5,'',4),(154,1,'',5),(155,2,'',5),(156,6,'',5),(157,1,'',6),(158,2,'',6),(159,6,'',6);
/*!40000 ALTER TABLE `docdata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `docfield`
--

DROP TABLE IF EXISTS `docfield`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `docfield` (
  `DocFieldID` int(11) NOT NULL AUTO_INCREMENT,
  `IDDocType` int(11) NOT NULL,
  `Type` varchar(50) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Required` tinyint(1) NOT NULL,
  `Sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`DocFieldID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `docfield`
--

LOCK TABLES `docfield` WRITE;
/*!40000 ALTER TABLE `docfield` DISABLE KEYS */;
INSERT INTO `docfield` VALUES (1,1,'Text','Loại văn bản',0,1),(2,1,'Text','Ngày phát hành',1,2),(3,2,'Text','F3',0,3),(4,2,'Text','F4',0,4),(5,2,'Text','F5',0,5),(6,1,'Text','Trích yếu',0,3),(9,5,'number','F1',0,1),(10,5,'textarea','F2',0,2);
/*!40000 ALTER TABLE `docfield` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `docshare`
--

DROP TABLE IF EXISTS `docshare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `docshare` (
  `DocShareID` int(11) NOT NULL AUTO_INCREMENT,
  `IDUser` int(11) NOT NULL,
  `StorageID` int(11) NOT NULL,
  PRIMARY KEY (`DocShareID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `docshare`
--

LOCK TABLES `docshare` WRITE;
/*!40000 ALTER TABLE `docshare` DISABLE KEYS */;
INSERT INTO `docshare` VALUES (10,1,21),(11,4,21),(12,1,16),(13,4,16),(14,5,16);
/*!40000 ALTER TABLE `docshare` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctype`
--

DROP TABLE IF EXISTS `doctype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `doctype` (
  `DocTypeID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  `Describes` varchar(500) DEFAULT NULL,
  `FileCount` int(11) DEFAULT NULL,
  `SizeSum` int(11) DEFAULT NULL,
  `Sort` int(11) DEFAULT NULL,
  `Status` tinyint(1) NOT NULL,
  PRIMARY KEY (`DocTypeID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctype`
--

LOCK TABLES `doctype` WRITE;
/*!40000 ALTER TABLE `doctype` DISABLE KEYS */;
INSERT INTO `doctype` VALUES (1,'Văn bản hành chính','',0,0,1,1),(2,'Hóa đơn','',0,0,2,1),(4,'Chứng từ abc','',0,0,3,1),(5,'MSI','',0,0,0,1),(6,'Thư từ','',0,0,4,1);
/*!40000 ALTER TABLE `doctype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `image` (
  `FileID` int(11) NOT NULL AUTO_INCREMENT,
  `productVariantID` int(11) NOT NULL,
  `Note` varchar(150) DEFAULT NULL,
  `Status` tinyint(1) NOT NULL,
  `Type` varchar(100) NOT NULL,
  `URL` varchar(200) NOT NULL,
  `Sort` int(11) NOT NULL,
  `IsMain` tinyint(1) DEFAULT NULL,
  `FileName` varchar(100) DEFAULT NULL,
  `Extension` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`FileID`),
  KEY `fk_files_productvariant` (`productVariantID`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES (1,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230310_062212_661A0062.JPG',0,0,'6_20230310_062212_661A0062.JPG','JPG'),(2,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230310_062212_661A0060.JPG',0,0,'6_20230310_062212_661A0060.JPG','JPG'),(3,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230310_062212_661A0058.JPG',0,0,'6_20230310_062212_661A0058.JPG','JPG'),(4,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230310_062212_661A0063.JPG',0,0,'6_20230310_062212_661A0063.JPG','JPG'),(5,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230310_062212_661A0059.JPG',0,0,'6_20230310_062212_661A0059.JPG','JPG'),(6,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230310_062212_661A0061.JPG',0,0,'6_20230310_062212_661A0061.JPG','JPG'),(7,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230310_062213_661A0065.JPG',0,0,'6_20230310_062213_661A0065.JPG','JPG'),(8,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230310_062213_661A0068.JPG',0,0,'6_20230310_062213_661A0068.JPG','JPG'),(9,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230310_062213_661A0066.JPG',0,0,'6_20230310_062213_661A0066.JPG','JPG'),(10,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230310_062213_661A0069.JPG',0,0,'6_20230310_062213_661A0069.JPG','JPG'),(11,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230310_062213_661A0064.JPG',0,0,'6_20230310_062213_661A0064.JPG','JPG'),(12,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230310_062213_661A0067.JPG',0,0,'6_20230310_062213_661A0067.JPG','JPG'),(13,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230310_062213_661A0073.JPG',0,0,'6_20230310_062213_661A0073.JPG','JPG'),(14,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230310_062213_661A0070.JPG',0,0,'6_20230310_062213_661A0070.JPG','JPG'),(15,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230310_062213_661A0071.JPG',0,0,'6_20230310_062213_661A0071.JPG','JPG'),(16,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230310_062213_661A0074.JPG',0,0,'6_20230310_062213_661A0074.JPG','JPG'),(17,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230310_062213_661A0072.JPG',0,0,'6_20230310_062213_661A0072.JPG','JPG'),(18,9,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\9_20230310_064359_DSC_3611.jpg',0,0,'9_20230310_064359_DSC_3611.jpg','jpg'),(19,9,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\9_20230310_064359_DSC_3624.jpg',0,0,'9_20230310_064359_DSC_3624.jpg','jpg'),(20,10,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\10_20230310_065201_0a1a1251a0656c3b3574.jpg',0,0,'10_20230310_065201_0a1a1251a0656c3b3574.jpg','jpg'),(21,1,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\1_20230310_065317_7b5a723bc50f0951501e.jpg',0,0,'1_20230310_065317_7b5a723bc50f0951501e.jpg','jpg'),(22,1,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\1_20230310_065317_6b43279c98a854f60db9.jpg',0,0,'1_20230310_065317_6b43279c98a854f60db9.jpg','jpg'),(23,1,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\1_20230310_065317_7b969e1f202bec75b53a.jpg',0,0,'1_20230310_065317_7b969e1f202bec75b53a.jpg','jpg'),(24,1,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\1_20230310_065317_7dbb014cb7787b262269.jpg',0,0,'1_20230310_065317_7dbb014cb7787b262269.jpg','jpg'),(25,11,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\11_20230311_033049_cb8b29b8998c55d20c9d (1).jpg',0,0,'11_20230311_033049_cb8b29b8998c55d20c9d (1).jpg','jpg'),(26,11,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\11_20230311_033049_cb8b29b8998c55d20c9d.jpg',0,0,'11_20230311_033049_cb8b29b8998c55d20c9d.jpg','jpg'),(27,10,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\10_20230311_033150_1fbaad0f1b3bd7658e2a.jpg',0,0,'10_20230311_033150_1fbaad0f1b3bd7658e2a.jpg','jpg'),(28,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230311_040624_4fb007ea55c3b96d5037219c7f79ec1f_0.jpeg',0,0,'6_20230311_040624_4fb007ea55c3b96d5037219c7f79ec1f_0.jpeg','jpeg'),(29,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230311_040624_b035049f97a358b529f96c939149ed24_1.jpeg',0,0,'6_20230311_040624_b035049f97a358b529f96c939149ed24_1.jpeg','jpeg'),(30,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230311_040624_63aa5d6d48efd77dad800e6e8e6459b9_3.jpeg',0,0,'6_20230311_040624_63aa5d6d48efd77dad800e6e8e6459b9_3.jpeg','jpeg'),(31,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230311_040624_9b845d94dbd7f4248d9d99c96473f4c7_2.jpeg',0,0,'6_20230311_040624_9b845d94dbd7f4248d9d99c96473f4c7_2.jpeg','jpeg'),(32,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230311_040624_4520b275e693457d22529d0e59c9a156_4.jpeg',0,0,'6_20230311_040624_4520b275e693457d22529d0e59c9a156_4.jpeg','jpeg'),(33,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230311_040624_7cb9c1f19988e209072ea256a6d14607_6.jpeg',0,0,'6_20230311_040624_7cb9c1f19988e209072ea256a6d14607_6.jpeg','jpeg'),(34,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230311_040624_6591c76a5193bcc254d0de7afd1f43c8_7.jpeg',0,0,'6_20230311_040624_6591c76a5193bcc254d0de7afd1f43c8_7.jpeg','jpeg'),(35,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230311_040624_a268f7e383b82745993b89741b2ed713_11.jpeg',0,0,'6_20230311_040624_a268f7e383b82745993b89741b2ed713_11.jpeg','jpeg'),(36,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230311_040624_62479a9b463bc8fdb34b5eea21e3869c_5.jpeg',0,0,'6_20230311_040624_62479a9b463bc8fdb34b5eea21e3869c_5.jpeg','jpeg'),(37,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230311_040624_a98cc9726c274d152e289e87546f8e36_9.jpeg',0,0,'6_20230311_040624_a98cc9726c274d152e289e87546f8e36_9.jpeg','jpeg'),(38,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230311_040624_3060253ea5a025557d4f2684a019b931_12.jpeg',0,0,'6_20230311_040624_3060253ea5a025557d4f2684a019b931_12.jpeg','jpeg'),(39,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230311_040624_e346aff50021773be71883f93f6a476b_10.jpeg',0,0,'6_20230311_040624_e346aff50021773be71883f93f6a476b_10.jpeg','jpeg'),(40,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230311_040624_ed9087899cd50eb283dd9fd3c1094d80_8.jpeg',0,0,'6_20230311_040624_ed9087899cd50eb283dd9fd3c1094d80_8.jpeg','jpeg'),(41,9,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\9_20230313_114346_1e233b8c89b845e61ca9.jpg',0,0,'9_20230313_114346_1e233b8c89b845e61ca9.jpg','jpg'),(42,9,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\9_20230313_115718_3a6b801ef7e902b75bf8.jpg',0,0,'9_20230313_115718_3a6b801ef7e902b75bf8.jpg','jpg'),(61,6,'',0,'image/jpeg','E:\\FLOWIEE\\SpringBoot\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\6_20230313_011543_DSC_3627.jpg',0,0,'6_20230313_011543_DSC_3627.jpg','jpg'),(62,12,'',0,'image/jpeg','D:\\VietND\\Project\\Flowiee WebApp\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\12_20230402_102807_ronaldo.jpg',0,0,'12_20230402_102807_ronaldo.jpg','jpg'),(63,12,'',0,'image/jpeg','D:\\VietND\\Project\\Flowiee WebApp\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\12_20230402_102807_messi.jpg',0,0,'12_20230402_102807_messi.jpg','jpg'),(64,12,'',0,'image/jpeg','D:\\VietND\\Project\\Flowiee WebApp\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\12_20230402_102932_background.jpg',0,0,'12_20230402_102932_background.jpg','jpg'),(65,12,'',0,'image/jpeg','D:\\VietND\\Project\\Flowiee WebApp\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\12_20230403_123210_iphone-14-pro-max-041222-103227.jpg',0,0,'12_20230403_123210_iphone-14-pro-max-041222-103227.jpg','jpg'),(66,12,'',0,'image/jpeg','D:\\VietND\\Project\\Flowiee WebApp\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\12_20230403_123210_iphone-14-pro-max-041222-103252.jpg',0,0,'12_20230403_123210_iphone-14-pro-max-041222-103252.jpg','jpg'),(67,12,'',0,'image/jpeg','D:\\VietND\\Project\\Flowiee WebApp\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\12_20230403_123210_iphone-14-pro-max-041222-103242.jpg',0,0,'12_20230403_123210_iphone-14-pro-max-041222-103242.jpg','jpg'),(68,12,'',0,'image/jpeg','D:\\VietND\\Project\\Flowiee WebApp\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\12_20230403_123210_iphone-14-pro-max-041222-103232.jpg',0,0,'12_20230403_123210_iphone-14-pro-max-041222-103232.jpg','jpg'),(69,12,'',0,'image/jpeg','D:\\VietND\\Project\\Flowiee WebApp\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\12_20230403_123210_iphone-14-pro-max-041222-103221.jpg',0,0,'12_20230403_123210_iphone-14-pro-max-041222-103221.jpg','jpg'),(70,12,'',0,'image/jpeg','D:\\VietND\\Project\\Flowiee WebApp\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\12_20230403_123210_iphone-14-pro-max-041222-103229.jpg',0,0,'12_20230403_123210_iphone-14-pro-max-041222-103229.jpg','jpg'),(71,12,'',0,'image/jpeg','D:\\VietND\\Project\\Flowiee WebApp\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\12_20230403_123210_iphone-14-pro-max-041222-103256.jpg',0,0,'12_20230403_123210_iphone-14-pro-max-041222-103256.jpg','jpg'),(72,12,'',0,'image/webp','D:\\VietND\\Project\\Flowiee WebApp\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\12_20230403_123210_xiaomi-redmi-note-12-den-thumb-600x600.webp',0,0,'12_20230403_123210_xiaomi-redmi-note-12-den-thumb-600x600.webp','webp'),(73,12,'',0,'image/webp','D:\\VietND\\Project\\Flowiee WebApp\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\12_20230403_123240_xiaomi-redmi-note-12-den-thumb-600x600.webp',0,0,'12_20230403_123240_xiaomi-redmi-note-12-den-thumb-600x600.webp','webp'),(74,12,'',0,'image/jpeg','D:\\VietND\\Project\\Flowiee WebApp\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\12_20230403_123240_iphone-14-pro-max-041222-103256.jpg',0,0,'12_20230403_123240_iphone-14-pro-max-041222-103256.jpg','jpg'),(75,12,'',0,'image/jpeg','D:\\VietND\\Project\\Flowiee WebApp\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\12_20230403_123240_iphone-14-pro-max-041222-103242.jpg',0,0,'12_20230403_123240_iphone-14-pro-max-041222-103242.jpg','jpg'),(76,12,'',0,'image/jpeg','D:\\VietND\\Project\\Flowiee WebApp\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\12_20230403_123240_iphone-14-pro-max-041222-103252.jpg',0,0,'12_20230403_123240_iphone-14-pro-max-041222-103252.jpg','jpg'),(77,12,'',0,'image/jpeg','D:\\VietND\\Project\\Flowiee WebApp\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\12_20230403_123409_iphone-14-pro-max-041222-103227.jpg',0,0,'12_20230403_123409_iphone-14-pro-max-041222-103227.jpg','jpg'),(78,13,'',0,'image/jpeg','D:\\VietND\\Project\\Flowiee WebApp\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\13_20230404_100540_iphone-14-pro-max-041222-103221.jpg',0,0,'13_20230404_100540_iphone-14-pro-max-041222-103221.jpg','jpg'),(79,13,'',0,'image/jpeg','D:\\VietND\\Project\\Flowiee WebApp\\FlowieeOfficial\\src\\main\\resources\\static\\uploads\\products\\13_20230404_100704_iphone-14-pro-max-041222-103252.jpg',0,0,'13_20230404_100704_iphone-14-pro-max-041222-103252.jpg','jpg');
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `learning`
--

DROP TABLE IF EXISTS `learning`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `learning` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Type` varchar(100) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Pronounce` varchar(50) DEFAULT NULL,
  `Translate` varchar(100) NOT NULL,
  `Note` varchar(500) DEFAULT NULL,
  `Created` datetime NOT NULL,
  `Status` tinyint(1) NOT NULL,
  `IsGrammar` tinyint(1) NOT NULL,
  `IsVocabulary` tinyint(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `learning`
--

LOCK TABLES `learning` WRITE;
/*!40000 ALTER TABLE `learning` DISABLE KEYS */;
INSERT INTO `learning` VALUES (1,'Bảng chữ cái - Nguyên âm','ㅏ','a','a','','2022-11-01 00:00:00',1,0,1),(2,'Bảng chữ cái - Nguyên âm','ㅑ','ya','ya','','2022-11-01 00:00:00',1,0,1),(3,'Bảng chữ cái - Nguyên âm','ㅓ','o','o','','2022-11-01 00:00:00',1,0,1),(4,'Bảng chữ cái - Nguyên âm','ㅕ','yo','yo','','2022-11-01 00:00:00',1,0,1),(5,'Bảng chữ cái - Nguyên âm','ㅗ','ô','ô','','2022-11-01 00:00:00',1,0,1),(6,'Bảng chữ cái - Nguyên âm','ㅛ','yô','yô','','2022-11-12 00:00:00',1,0,1),(7,'Bảng chữ cái - Nguyên âm','ㅜ','u','u','','2022-11-01 00:00:00',1,0,1),(8,'Bảng chữ cái - Nguyên âm','ㅠ','yu','yu','','2022-11-01 00:00:00',1,0,1),(9,'Bảng chữ cái - Nguyên âm','ㅡ','ư','ư','','2022-11-01 00:00:00',1,0,1),(10,'Bảng chữ cái - Nguyên âm','ㅣ','i','i','','2022-11-01 00:00:00',1,0,1),(11,'Bảng chữ cái - Nguyên âm','ㅐ','e','e','','2022-11-01 00:00:00',1,0,1),(12,'Bảng chữ cái - Nguyên âm','ㅒ','ye','ye','','2022-11-01 00:00:00',1,0,1),(13,'Bảng chữ cái - Nguyên âm','ㅔ','ê','ê','','2022-11-01 00:00:00',1,0,1),(14,'Bảng chữ cái - Nguyên âm','ㅖ','yê','yê','','2022-11-01 00:00:00',1,0,1),(15,'Bảng chữ cái - Nguyên âm','ㅘ','oa','oa','Chữ ô (ㅗ) ghép với chữ a (ㅏ)','2022-11-01 00:00:00',1,0,1),(16,'Bảng chữ cái - Nguyên âm','ㅙ','uê','uê','','2022-11-01 00:00:00',1,0,1),(17,'Bảng chữ cái - Nguyên âm','ㅚ','uê','uê','','2022-11-01 00:00:00',1,0,1),(18,'Bảng chữ cái - Nguyên âm','ㅝ','uơ','uơ','Chữ u (ㅜ) ghép với chữ o (ㅓ)','2022-11-01 00:00:00',1,0,1),(19,'Bảng chữ cái - Nguyên âm','ㅞ','uê','uê','','2022-11-01 00:00:00',1,0,1),(20,'Bảng chữ cái - Nguyên âm','ㅟ','uy','uy','Chữ u (ㅜ) ghép với chữ i (I)','2022-11-01 00:00:00',1,0,1),(21,'Bảng chữ cái - Nguyên âm','ㅢ','ui','ui','Chữ ư (ㅡ) ghép với chữ i (I)','2022-11-01 00:00:00',1,0,1),(22,'Bảng chữ cái - Phụ âm','ㄱ','[k] / [g]','c , g','','2022-11-01 00:00:00',1,0,1),(23,'Bảng chữ cái - Phụ âm','ㄴ','n','n','','2022-11-01 00:00:00',1,0,1),(24,'Bảng chữ cái - Phụ âm','ㄷ','[t] / [d]','t , d','','2022-11-01 00:00:00',1,0,1),(25,'Bảng chữ cái - Phụ âm','ㄹ','[r] / [l]','r , l','Nếu đứng đầu tiên của từ thì đọc là \"r\" <br>\r\nCòn lại đọc là \"l\"','2022-11-01 00:00:00',1,0,1),(26,'Bảng chữ cái - Phụ âm','ㅁ','m','m','','2022-11-01 00:00:00',1,0,1),(27,'Bảng chữ cái - Phụ âm','ㅂ','[b] / [p]','b , p','','2022-11-01 00:00:00',1,0,1),(28,'Bảng chữ cái - Phụ âm','ㅅ','[s] / [sh]','s , sh','','2022-11-01 00:00:00',1,0,1),(29,'Bảng chữ cái - Phụ âm','ㅇ','ngờ','ng','','2022-11-01 00:00:00',1,0,1),(30,'Bảng chữ cái - Phụ âm','ㅈ','jờ','ch (j)','Đọc nghiêng về chữ jờ','2022-11-01 00:00:00',1,0,1),(31,'Bảng chữ cái - Phụ âm','ㅊ','chờ','ch','','2022-11-02 00:00:00',1,0,1),(32,'Bảng chữ cái - Phụ âm','ㅋ','khờ','kh','','2022-11-01 00:00:00',1,0,1),(33,'Bảng chữ cái - Phụ âm','ㅌ','thờ','th','','2022-11-01 00:00:00',1,0,1),(34,'Bảng chữ cái - Phụ âm','ㅍ','phờ','ph','','2022-11-01 00:00:00',1,0,1),(35,'Bảng chữ cái - Phụ âm','ㅎ','hờ','h','','2022-11-01 00:00:00',1,0,1),(36,'Bảng chữ cái - Phụ âm','ㄲ','k','k (căng)','','2022-11-01 00:00:00',1,0,1),(37,'Bảng chữ cái - Phụ âm','ㄸ','t','t (căng)','','2022-11-01 00:00:00',1,0,1),(38,'Bảng chữ cái - Phụ âm','ㅃ','p','p (căng)','','2022-11-01 00:00:00',1,0,1),(39,'Bảng chữ cái - Phụ âm','ㅆ','s','s (căng)','','2022-11-01 00:00:00',1,0,1),(40,'Bảng chữ cái - Phụ âm','ㅉ','ch','ch (căng)','','2022-11-01 00:00:00',1,0,1),(43,'hello','1','1','1','','1111-11-11 00:00:00',1,1,0),(44,'Mẫu câu giới thiệu','2','2','2','2','2222-02-22 00:00:00',1,1,0),(45,'Giới thiệu','1212','1212','1212','1212','2022-12-01 00:00:00',1,0,1);
/*!40000 ALTER TABLE `learning` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `notification` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDSender` int(11) DEFAULT NULL,
  `IDReceiver` int(11) NOT NULL,
  `Type` int(11) NOT NULL,
  `Message` varchar(500) NOT NULL,
  `IsReaded` tinyint(1) DEFAULT NULL,
  `Created` datetime NOT NULL,
  `IsSendMail` tinyint(1) NOT NULL,
  `IDOrders` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3635410 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES (1,1,0,1,'22093030',1,'2022-09-30 17:08:09',1,30),(3635379,1,4,1,'2209302626',0,'2022-09-30 13:10:08',1,26),(3635380,3,2,1,'2209302727',0,'2022-09-30 16:06:58',1,27),(3635381,0,5,1,'2209302828',0,'2022-09-30 16:10:52',1,28),(3635382,0,2,1,'2209302929',0,'2022-09-30 16:36:13',1,29),(3635384,0,0,1,'22093031',1,'2022-09-30 17:26:08',1,31),(3635385,0,0,1,'22100132',1,'2022-10-01 16:47:30',1,32),(3635386,1,0,1,'22100135',1,'2022-10-01 17:18:48',1,35),(3635387,1,0,1,'22100236',0,'2022-10-02 14:20:46',1,36),(3635388,1,0,1,'22100437',0,'2022-10-04 20:31:31',1,37),(3635389,1,0,1,'22100638',0,'2022-10-06 12:32:58',1,38),(3635390,1,0,1,'22100739',1,'2022-10-07 12:28:14',1,39),(3635391,1,0,1,'2210091',0,'2022-10-09 14:30:08',1,1),(3635392,1,0,1,'2210102',0,'2022-10-10 15:50:00',1,2),(3635393,1,0,1,'2210103',0,'2022-10-10 16:09:44',1,3),(3635394,1,0,1,'2210104',1,'2022-10-10 16:12:33',1,4),(3635395,1,0,1,'2210105',1,'2022-10-10 16:15:43',1,5),(3635396,1,0,1,'2211046',1,'2022-11-04 13:47:21',1,6),(3635397,1,0,1,'2301017',0,'2023-01-01 23:06:37',1,7),(3635398,1,0,1,'2301218',0,'2023-01-21 19:09:10',1,8),(3635399,1,0,1,'2301219',0,'2023-01-21 19:11:24',1,9),(3635400,1,0,1,'23012110',0,'2023-01-21 19:13:27',1,10),(3635401,1,0,1,'2301211',0,'2023-01-21 20:03:54',1,1),(3635402,1,0,1,'2301212',0,'2023-01-21 20:08:41',1,2),(3635403,1,0,1,'2301213',0,'2023-01-21 20:41:01',1,3),(3635404,1,0,1,'2301244',0,'2023-01-24 22:32:36',1,4),(3635405,1,0,1,'2301245',0,'2023-01-24 22:32:54',1,5),(3635406,1,0,1,'2301246',0,'2023-01-24 22:33:06',1,6),(3635407,1,0,1,'2301247',0,'2023-01-24 22:33:19',1,7),(3635408,1,0,1,'2301248',0,'2023-01-24 22:39:12',1,8),(3635409,1,0,1,'2301249',0,'2023-01-24 22:51:22',1,9);
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `orders` (
  `OrdersID` int(11) NOT NULL AUTO_INCREMENT,
  `Code` varchar(20) NOT NULL,
  `IDCustomer` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Phone` varchar(50) NOT NULL,
  `Email` varchar(50) DEFAULT NULL,
  `Address` varchar(500) DEFAULT NULL,
  `Note` varchar(500) DEFAULT NULL,
  `Date` datetime NOT NULL,
  `TotalMoney` float DEFAULT NULL,
  `Sales` varchar(50) DEFAULT NULL,
  `Channel` varchar(50) DEFAULT NULL,
  `Status` varchar(50) NOT NULL,
  PRIMARY KEY (`OrdersID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'2301211',5,'Koma','0706820684','Flowiee@','Tòa nhà Conic Riverside, Đường Số 1, KDC Conic, Quận 8, Thành phố Hồ Chí Minh','','2023-01-21 20:03:54',1230100,'Quản trị hệ thống','Shopee','1'),(2,'2301212',5,'Koma','0706820684','Flowiee@','Tòa nhà Conic Riverside, Đường Số 1, KDC Conic, Quận 8, Thành phố Hồ Chí Minh','','2023-01-21 20:08:41',320100,'Quản trị hệ thống','Shopee','1'),(3,'2301213',5,'Koma','0706820684','Flowiee@','Tòa nhà Conic Riverside, Đường Số 1, KDC Conic, Quận 8, Thành phố Hồ Chí Minh','','2023-01-21 20:41:01',320100,'Quản trị hệ thống','Hotline','3'),(4,'2301244',5,'Koma','0706820684','Flowiee@','Tòa nhà Conic Riverside, Đường Số 1, KDC Conic, Quận 8, Thành phố Hồ Chí Minh','','2023-01-24 22:32:36',148750,'Quản trị hệ thống','Zalo','1'),(5,'2301245',5,'Koma','0706820684','Flowiee@','Tòa nhà Conic Riverside, Đường Số 1, KDC Conic, Quận 8, Thành phố Hồ Chí Minh','','2023-01-24 22:32:54',320100,'Quản trị hệ thống','Instagram','1'),(6,'2301246',5,'Koma','0706820684','Flowiee@','Tòa nhà Conic Riverside, Đường Số 1, KDC Conic, Quận 8, Thành phố Hồ Chí Minh','','2023-01-24 22:33:06',1,'Quản trị hệ thống','Facebook','1'),(7,'2301247',5,'Koma','0706820684','Flowiee@','Tòa nhà Conic Riverside, Đường Số 1, KDC Conic, Quận 8, Thành phố Hồ Chí Minh','','2023-01-24 22:33:19',909999,'Quản trị hệ thống','Facebook','1'),(8,'2301248',5,'Koma','0706820684','Flowiee@','Tòa nhà Conic Riverside, Đường Số 1, KDC Conic, Quận 8, Thành phố Hồ Chí Minh','','2023-05-24 22:39:12',1230100,'Quản trị hệ thống','Facebook','2'),(9,'2301249',5,'Koma','0706820684','Flowiee@','Tòa nhà Conic Riverside, Đường Số 1, KDC Conic, Quận 8, Thành phố Hồ Chí Minh','','2023-01-24 22:51:22',320100,'Quản trị hệ thống','Zalo','1');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `positions`
--

DROP TABLE IF EXISTS `positions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `positions` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Code` varchar(20) DEFAULT NULL,
  `Name` varchar(250) NOT NULL,
  `Describes` varchar(250) DEFAULT NULL,
  `Created` date DEFAULT NULL,
  `UserCount` int(11) DEFAULT NULL,
  `Status` tinyint(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `positions`
--

LOCK TABLES `positions` WRITE;
/*!40000 ALTER TABLE `positions` DISABLE KEYS */;
/*!40000 ALTER TABLE `positions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `price_history`
--

DROP TABLE IF EXISTS `price_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `price_history` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ProductID` int(11) NOT NULL,
  `Price` float NOT NULL,
  `CreatedAt` datetime NOT NULL,
  `UpdatedAt` datetime NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `price_history`
--

LOCK TABLES `price_history` WRITE;
/*!40000 ALTER TABLE `price_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `price_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `product` (
  `ProductID` int(11) NOT NULL AUTO_INCREMENT,
  `Code` varchar(50) DEFAULT NULL,
  `Type` varchar(100) NOT NULL,
  `Name` varchar(200) NOT NULL,
  `Color` varchar(20) NOT NULL,
  `Price` float NOT NULL,
  `Size` varchar(50) NOT NULL,
  `Date` date DEFAULT NULL,
  `Storage` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `Describes` longtext,
  `Status` tinyint(1) NOT NULL,
  `Promotion` int(11) DEFAULT NULL,
  PRIMARY KEY (`ProductID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'dfds','Áo khoác','FLOWIEE - Áo khoát voan xuyên thấu Hanbok','Xanh hoa nhí',999999,'M','2023-02-25',101,23,'<p>123</p>',1,9),(2,'gft','Áo khoác','FLOWIEE - Áo khoát voan xuyên thấu Hanbok 123','Hồng nhạt',330000,'S','2023-02-24',102,14,'<p>\\</p>',1,3),(3,'123','Mì ăn liền','Thùng 30 gói mì Hảo Hảo tôm chua cay 75g','Trắng',108000,'L','2023-02-27',1009,54,'<h3 style=\"margin: 20px 0px 15px; padding: 0px; font-variant-numeric: normal; font-variant-east-asian: normal; font-weight: bold; font-stretch: normal; font-size: 20px; line-height: 28px; font-family: Arial, Helvetica, sans-serif; color: rgb(51, 51, 51); outline: none;\"><span style=\"color: rgba(0, 0, 0, 0.8); font-family: &quot;Helvetica Neue&quot;, Helvetica, Arial, 文泉驛正黑, &quot;WenQuanYi Zen Hei&quot;, &quot;Hiragino Sans GB&quot;, &quot;儷黑 Pro&quot;, &quot;LiHei Pro&quot;, &quot;Heiti TC&quot;, 微軟正黑體, &quot;Microsoft JhengHei UI&quot;, &quot;Microsoft JhengHei&quot;, sans-serif; font-size: 14px; font-weight: 400; white-space: pre-wrap;\">Đầm hai dây thắt nơ, dáng xoè rộng 2 tầng mỏng nhẹ. Có lớp lót\r\n\r\n- Chất liệu: sợi tổng hợp\r\n- Màu sắc: trắng\r\n- Size: S/M\r\n\r\nSỐ ĐO CƠ THỂ\r\nVòng ngực (cm) x Vòng eo (cm) x Vòng mông (cm)\r\nSize S : 79-82 x 61-64 x 89-92\r\nSize M : 83-86 x 65-68 x 93-96\r\n*Lưu ý: đây là số đo cơ thể mang tính chất tham khảo để bạn có thể nắm được mình phù hợp với sản phẩm có kích thước gì (S-M-L)\r\n*Để xem số đo về sản phẩm, bạn vui lòng nhấn vào “Bảng Quy Đổi Kích Cỡ” trong phần chọn mua nhé.\r\n\r\n---\r\n\r\n- CHÍNH SÁCH ĐỔI HÀNG\r\n- Chỉ áp dụng hình thức trả hàng trong TH sản phẩm bị lỗi về chất liệu hoặc lỗi từ phía nhà cung cấp. Sản phẩm chưa qua sử dụng và còn nguyên tem mác.\r\n- Khách vui lòng kiểm tra hàng ngay sau khi nhận hàng.\r\n- Sản phẩm đổi hàng, khách vui lòng đổi sang sản phẩm bằng tiền hoặc hơn so với hóa đơn.</span><br></h3>',1,2),(4,'','Thịt heo các loại','Thùng 24 ly mì Modern lẩu Thái tôm 65g','Đỏ',175000,'M','2022-10-08',7,378,'456',1,15),(5,'123','Sửa tươi','123st','Đỏ',1,'S','2023-01-21',0,2,NULL,1,0),(6,'newproduct','Rau, củ, trái cây','Nguyễn Đức Việt','Đỏ',123,'S','2023-02-24',123,123,NULL,1,1),(8,' ','Áo','Đầm trắng 2 tầng dáng xòe','Trắng',1,'S','2023-02-01',1,1,'',1,1),(9,'2','Áo','2','Xanh',2,'S','2023-02-21',2,2,'2',1,2),(13,'1212121212','Váy','Nguyễn Đức Việt','Xanh',1,'S','2023-02-21',1,1,'vv',1,1),(14,' ','Nước mắm','Đầm xanh lá 2 tầng','Màu tương cà',52,'XL','2023-02-01',52,52,'',1,52),(15,'oxy','Kem các loại','Sữa rửa mặt Oxy','Trắng',490000,'S','2023-02-24',1,1,NULL,1,0);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_attributes`
--

DROP TABLE IF EXISTS `product_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `product_attributes` (
  `ProductAttributeID` int(11) NOT NULL AUTO_INCREMENT,
  `ProductVariantID` int(11) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Value` varchar(255) DEFAULT NULL,
  `Status` tinyint(1) NOT NULL,
  `Sort` int(11) NOT NULL,
  PRIMARY KEY (`ProductAttributeID`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_attributes`
--

LOCK TABLES `product_attributes` WRITE;
/*!40000 ALTER TABLE `product_attributes` DISABLE KEYS */;
INSERT INTO `product_attributes` VALUES (1,5,'Chất liệu','Vải xịn',1,0),(2,5,'Họa tiết','Bông hoa',1,0),(3,5,'Nguồn gốc','Canada',1,0),(4,5,'Nguồn gốc','Canada',1,0),(5,5,'Nguồn gốc','Canada',1,0),(6,5,'Nguồn gốc','Canada',1,0),(7,5,'Nguồn gốc','Canada',1,0),(8,5,'Nguồn gốc','Canada',1,0),(9,5,'Nguồn gốc','Canada',1,0),(10,1,'Độ dày','1mm',0,0),(11,6,'Bánh bèo','5k',0,0),(12,4,'Xịt tóc','trắng',0,0),(13,4,'Kem đánh răng','ps',0,0),(14,3,'Hoa nhỉ','xanh',0,0),(15,6,'Nguyễn Đức Việt','15',0,15),(16,6,'Quản trị hệ thống','1',0,7),(17,6,'FSI','123',0,456),(18,0,'CPU','Intel core i5',0,0),(23,0,'1','1',0,1),(24,12,'Màn hình','13.3\" Retina (2560 x 1600)',0,3),(26,0,'2','2',0,2),(32,12,'22','22',1,2),(33,12,'11','11',0,12),(34,13,'1','1',0,1),(35,13,'2','2',0,2);
/*!40000 ALTER TABLE `product_attributes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_variants`
--

DROP TABLE IF EXISTS `product_variants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `product_variants` (
  `ProductVariantID` int(11) NOT NULL AUTO_INCREMENT,
  `ProductID` int(11) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Value` varchar(255) DEFAULT NULL,
  `Status` tinyint(1) NOT NULL,
  `Code` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ProductVariantID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_variants`
--

LOCK TABLES `product_variants` WRITE;
/*!40000 ALTER TABLE `product_variants` DISABLE KEYS */;
INSERT INTO `product_variants` VALUES (1,2,'Color','Màu tương cà',0,''),(2,2,'Color','Đen',0,''),(3,2,'Color','Xanh hoa nhí',1,''),(4,2,'Color','Trắng',1,''),(5,0,'Color','1mm',0,NULL),(6,1,'Color','Xanh hoa nhí',1,''),(7,3,'Color','Đen',1,''),(8,3,'Color','Màu tương cà',1,''),(9,1,'Color','Màu tương cà',1,''),(10,4,'Color','Đen',1,''),(11,7,'Color','Đen',1,''),(12,8,'Color','Xám',1,''),(13,8,'Color','Xanh hoa nhí',1,'');
/*!40000 ALTER TABLE `product_variants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `products` (
  `ProductID` int(11) NOT NULL AUTO_INCREMENT,
  `CategoryID` int(11) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Description` text,
  `Status` tinyint(1) NOT NULL,
  PRIMARY KEY (`ProductID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,1,'Áo thun',NULL,1),(2,0,'Color','',0),(3,0,'Mì ăn liền',NULL,1),(4,0,'Thịt heo',NULL,1),(6,1110,'Giấy VS',NULL,1),(7,1093,'Laptop',NULL,1),(8,1061,'HP','<p>123</p>',0);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rep`
--

DROP TABLE IF EXISTS `rep`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `rep` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDCmt` int(11) NOT NULL,
  `Name` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rep`
--

LOCK TABLES `rep` WRITE;
/*!40000 ALTER TABLE `rep` DISABLE KEYS */;
INSERT INTO `rep` VALUES (1,1,'Rep cmt 1'),(2,6,'Rep cmt 6');
/*!40000 ALTER TABLE `rep` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Type` int(11) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Describes` varchar(200) DEFAULT NULL,
  `Status` tinyint(1) NOT NULL,
  `Sort` int(11) DEFAULT NULL,
  `Code` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,0,'Tài khoản hệ thống','/admin/accountEntity',1,50,'MA'),(2,1,'Thêm tài khoản','- ok',1,51,'MAI'),(3,1,'Cập nhật tài khoản','',1,52,'MAU'),(4,1,'Xóa tài khoản','',1,53,'MAD'),(5,0,'Nhật ký hệ thống','/admin/log',1,40,'ML'),(6,0,'Quản lý sản phẩm','/admin/products',1,1,'MP'),(7,1,'Cập nhật sản phẩm','- ok',1,3,'MPU'),(8,1,'Thêm mới sản phẩm','- ok',1,2,'MPI'),(9,0,'Quản lý đơn hàng','/admin/orders',1,30,'MO'),(10,0,'Quản lý khách hàng','/admin/customers',1,20,'MCu'),(11,1,'Cập nhật đơn hàng','',1,31,'MOU'),(12,1,'Xóa đơn hàng','',1,32,'MOD'),(13,1,'Thêm khách hàng','',1,21,'MCuI'),(14,1,'Cập nhật thông tin khách hàng','',1,22,'MCuU'),(15,1,'Xóa khách hàng','',1,23,'MCuD'),(16,0,'Quản lý danh mục','/category-type',1,60,'MCa'),(17,0,'Quản lý cấu hình hệ thống','/admin/config',1,45,'MCo'),(18,0,'Quản lý nhóm quyền','/admin/role',1,65,'MR'),(19,1,'Thêm nhóm quyền','',1,66,'MRI'),(20,1,'Cập nhật nhóm quyền','',1,67,'MRU'),(21,1,'Xóa nhóm quyền','',1,68,'MRD'),(22,0,'Đơn hàng chờ xác nhận','',1,33,'MOUn'),(23,0,'Đơn hàng đang giao','',1,34,'MODe'),(24,0,'Đơn hàng đã hoàn thành','',1,35,'MOCo'),(25,0,'Đơn hàng đã hủy','',1,36,'MOCa'),(26,1,'Chi tiết đơn hàng','/admin/orders/detail-{ID}',1,30,'MOd'),(27,1,'Chi tiết sản phẩm','- ok',1,1,'MPd'),(28,0,'Quản lý Kho lưu trữ','/admin/storage',1,10,'MS');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storage`
--

DROP TABLE IF EXISTS `storage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `storage` (
  `StorageID` int(11) NOT NULL AUTO_INCREMENT,
  `IDParent` int(11) NOT NULL,
  `isFolder` tinyint(1) NOT NULL,
  `Name` varchar(500) NOT NULL,
  `StgName` varchar(500) DEFAULT NULL,
  `Describes` varchar(500) DEFAULT NULL,
  `Extension` varchar(10) DEFAULT NULL,
  `Size` int(11) DEFAULT NULL,
  `Author` varchar(50) NOT NULL,
  `Path` varchar(500) DEFAULT NULL,
  `IDDocType` int(11) DEFAULT NULL,
  PRIMARY KEY (`StorageID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storage`
--

LOCK TABLES `storage` WRITE;
/*!40000 ALTER TABLE `storage` DISABLE KEYS */;
INSERT INTO `storage` VALUES (1,0,0,'fsi',NULL,'',NULL,0,'1',NULL,0),(2,1,1,'VBHC','20230122112024-20230105035341-congvan-phoihop-TanPhu.pdf','',NULL,592249,'1','E:\\FLOWIEE\\Source\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\flowiee\\WEB-INF\\views\\admin\\assets\\storage\\20230122112024-20230105035341-congvan-phoihop-TanPhu.pdf',1),(4,1,1,'HĐ','20230122112131-20230102035615-Check nhé nhé (1).pdf','',NULL,7380,'1','E:\\FLOWIEE\\Source\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\flowiee\\WEB-INF\\views\\admin\\assets\\storage\\20230122112131-20230102035615-Check nhé nhé (1).pdf',2),(5,0,1,'1','20230211071930-NGUYEN DUC VIET - Java Developer.pdf','',NULL,407869,'1','E:\\FLOWIEE\\Source\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\flowiee\\WEB-INF\\views\\admin\\assets\\storage\\20230211071930-NGUYEN DUC VIET - Java Developer.pdf',1),(6,1,1,'2','20230124120306-20230118124312-20230102035615-Check nhé nhé (3) (4).pdf','',NULL,7380,'1','E:\\FLOWIEE\\Source\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\flowiee\\WEB-INF\\views\\admin\\assets\\storage\\20230124120306-20230118124312-20230102035615-Check nhé nhé (3) (4).pdf',5),(7,0,0,'2',NULL,'',NULL,0,'1',NULL,0),(8,7,0,'2.1',NULL,'',NULL,0,'1',NULL,0),(9,0,1,'at abc','20230124011839-20230102053832-19-2021-TT-BTTTT.PDF','',NULL,1703521,'1','E:\\FLOWIEE\\Source\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\flowiee\\WEB-INF\\views\\admin\\assets\\storage\\20230124011839-20230102053832-19-2021-TT-BTTTT.PDF',2),(10,7,1,'Thư từ','20230124021751-123.pdf','',NULL,145216,'1','E:\\FLOWIEE\\Source\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\flowiee\\WEB-INF\\views\\admin\\assets\\storage\\20230124021751-123.pdf',6);
/*!40000 ALTER TABLE `storage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `systemconfig`
--

DROP TABLE IF EXISTS `systemconfig`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `systemconfig` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  `Address` varchar(100) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Phone` varchar(20) NOT NULL,
  `Logo` varchar(200) DEFAULT NULL,
  `Favicon` varchar(200) DEFAULT NULL,
  `Description` text,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `systemconfig`
--

LOCK TABLES `systemconfig` WRITE;
/*!40000 ALTER TABLE `systemconfig` DISABLE KEYS */;
INSERT INTO `systemconfig` VALUES (1,'한국인: Hàn Quốc3','Quận 8, TP HCM?x3','nguyenducviet.vietnd@gmail.com?x3','07 0682 0684?x3','20012023-212931_logo_FLOWIEE - Đầm 2 dây dáng rộng màu đen - xanh có túi Florette.jpg','20012023-212928_favicon_d27b90776cd89ed38f3478ca7c5730fb.jpg','run:  23213123 ggggggggggggggggggg\r\n - FSI\r\n     + 1. Am hiểu hoạt động của công ty.rar\r\n     - Công đoàn\r\n         - Chien Binh FSI\r\n             + BẢNG ĐIỂM CHIẾN BINH FSI.xlsx - TEAM 4.pdf\r\n         - Covid 19\r\n             + DS ủng hộ Bắc  GIang chống dịch Covid.xlsx\r\n         - No change no game\r\n             + Ý tưởng SLD.docx\r\n         - Project x3\r\n             - Chạy bộ\r\n                 + BẢNG THEO DÕI THÀNH TÍCH TEAM 4 NGÀY 01-7.xlsx\r\n                 + BẢNG THEO DÕI THÀNH TÍCH TEAM 4 NGÀY 02-7.xlsx\r\n                 + BẢNG THEO DÕI THÀNH TÍCH TEAM 4 NGÀY 03-7.xlsx\r\n                 + BẢNG THEO DÕI THÀNH TÍCH TEAM 4 NGÀY 04-7.xlsx\r\n                 + BẢNG THEO DÕI THÀNH TÍCH TEAM 4 NGÀY 08-7.xlsx\r\n                 + BẢNG THEO DÕI THÀNH TÍCH TEAM 4 NGÀY 10-7.xlsx\r\n                 + BẢNG THEO DÕI THÀNH TÍCH TEAM 4 NGÀY 12-7.xlsx\r\n             + KẾ HOẠCH PROJECT X3.docx\r\n             - Review sách\r\n                 + blockchainHieuNTHM.docx\r\n                 + Huệ Lâm_Mình sinh ra không phải để buồn.docx\r\n                 + Mình sinh ra không phải để buồn.docx\r\n                 + Review _ Ngày xưa có một con bò_ Thảo HT_ Nhóm 4.pdf\r\n                 + ViệtNĐ_Đừng bao giờ đi ăn một mình.docx\r\n     - Dự án\r\n         - 2020\r\n             - Đại học Việt Đức - VGU\r\n                 + 01 QD phe duyet Du toan KHLCNT ĐỢT 1.docx\r\n                 + 07122021_Biên bản họp ký số.docx\r\n                 + 20221005_VGU_ICMS_SAPB1_UM_Endusers_v1.3.pdf\r\n                 - Check log sync ihrp 2-7-2021\r\n                     + listIHRP.txt\r\n                     + listUpdate.txt\r\n                     + serviceRecordAutoRunExecutors.txt\r\n                 - Data backup (Ransomeware)\r\n                     - Data backup\r\n                         - 1. Decision\r\n                             - 1. Decision_Binh Duong Provincial People\'s Committee\r\n                                 + 20120306_566_QD-UBND_Cho phep truong Dai hoc Viet Duc tru dong tai Binh Duong.pdf\r\n                                 + 20160825_2201_QĐ-UBND_Thuc hien De tai Nghien cuu xay dung bo giai phao nang cao An toan giao thong duong bo tinh Binh Duong.pdf\r\n                                 + 20170314_590_QĐ-UBND_Bo sung thanh vien Ban Dieu hanh De an Thanh pho Thong minh Binh Duong.pdf\r\n                                 + 20170316_31_BĐH TPTM-VP_Danh sach thanh vien to chuyen vien giup viec Ban dieu hanh trien khai De an thanh pho Thong minh Binh Duong.pdf\r\n                                 + 20180911_07_QCPH-CAT-ĐHBD_Quy che phoi hop thuc hien nhiem vu bao ve An ninh quoc gia.pdf\r\n                                 + Thumbs.db\r\n                             - 1. Decision_MOET\r\n                                 + 20080229_897_QD-BGDĐT_Bo nhiem GS. TS Wolf Rieck giu chuc Hieu truong Truong Dai hoc Viet Duc.pdf\r\n                                 + 20080418_2265_QD-BGDĐT_Thanh lap Ban quan ly Du an truong Dai hoc Viet Duc.pdf\r\n                                 + 20080513_2727_QD-BGDĐT_Phân công GS.TS Wolf Rieck Chu TK va TS Truong Quang Duoc chu Tk dc UQ, Dang Thi Bich hanh lam KTT.pdf\r\n');
/*!40000 ALTER TABLE `systemconfig` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `systemlog`
--

DROP TABLE IF EXISTS `systemlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `systemlog` (
  `LogID` int(11) NOT NULL AUTO_INCREMENT,
  `Action` varchar(1000) DEFAULT NULL,
  `Created` datetime DEFAULT NULL,
  `IP` varchar(20) NOT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `Module` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`LogID`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `systemlog`
--

LOCK TABLES `systemlog` WRITE;
/*!40000 ALTER TABLE `systemlog` DISABLE KEYS */;
INSERT INTO `systemlog` VALUES (1,'Đăng nhập hệ thống','2023-03-30 00:00:00','unknown','koma',NULL),(2,'Đăng nhập hệ thống','2023-03-30 22:51:58','0:0:0:0:0:0:0:1','koma',NULL),(3,'Đăng nhập hệ thống',NULL,'0:0:0:0:0:0:0:1','koma',NULL),(4,'Đăng nhập hệ thống','2023-03-30 22:53:40','0:0:0:0:0:0:0:1','koma',NULL),(5,'Login','2023-03-30 23:10:47','unknown','koma','System'),(6,'Login','2023-03-30 23:12:23','unknown','koma','System'),(7,'Login','2023-03-30 23:12:49','192.168.1.7','koma','System'),(8,'Login','2023-03-31 17:26:53','unknown','koma','System'),(9,'Login','2023-03-31 21:36:01','unknown','koma','System'),(10,'Login','2023-03-31 22:03:46','unknown','koma','System'),(11,'Login','2023-03-31 22:05:55','unknown','koma','System'),(12,'Login','2023-04-01 00:05:10','unknown','koma','System'),(13,'Xem log hệ thống','2023-04-01 00:05:10','127.0.0.1','koma','Hệ thống'),(14,'Xem log hệ thống','2023-04-01 00:05:20','127.0.0.1','koma','Hệ thống'),(15,'Xem log hệ thống','2023-04-01 00:05:23','127.0.0.1','koma','Hệ thống'),(16,'Xem log hệ thống','2023-04-01 00:05:25','0:0:0:0:0:0:0:1','koma','Hệ thống'),(17,'Xem log hệ thống','2023-04-01 00:07:56','0:0:0:0:0:0:0:1','koma','Hệ thống'),(18,'Xem log hệ thống','2023-04-01 00:08:01','0:0:0:0:0:0:0:1','koma','Hệ thống'),(19,'Xem log hệ thống','2023-04-01 00:08:02','0:0:0:0:0:0:0:1','koma','Hệ thống'),(20,'Xem log hệ thống','2023-04-01 00:08:04','0:0:0:0:0:0:0:1','koma','Hệ thống'),(21,'Xem log hệ thống','2023-04-01 00:08:14','0:0:0:0:0:0:0:1','koma','Hệ thống'),(22,'Xem log hệ thống','2023-04-01 00:08:31','0:0:0:0:0:0:0:1','koma','Hệ thống'),(23,'Xem log hệ thống','2023-04-01 00:08:33','0:0:0:0:0:0:0:1','koma','Hệ thống'),(24,'Xem log hệ thống','2023-04-01 00:12:56','0:0:0:0:0:0:0:1','koma','Hệ thống'),(25,'Xem log hệ thống','2023-04-01 00:13:09','0:0:0:0:0:0:0:1','koma','Hệ thống'),(26,'Xem log hệ thống','2023-04-01 00:13:13','0:0:0:0:0:0:0:1','koma','Hệ thống'),(27,'Xem log hệ thống','2023-04-01 00:13:58','0:0:0:0:0:0:0:1','koma','Hệ thống'),(28,'Xem log hệ thống','2023-04-01 00:20:42','0:0:0:0:0:0:0:1','koma','Hệ thống'),(29,'Xem log hệ thống','2023-04-01 00:21:00','0:0:0:0:0:0:0:1','koma','Hệ thống'),(30,'Xem log hệ thống','2023-04-01 00:22:49','0:0:0:0:0:0:0:1','koma','Hệ thống'),(31,'Xem log hệ thống','2023-04-01 00:22:53','0:0:0:0:0:0:0:1','koma','Hệ thống'),(32,'Xem log hệ thống','2023-04-01 00:30:21','0:0:0:0:0:0:0:1','koma','Hệ thống'),(33,'Xuất excel log hệ thống','2023-04-01 00:30:46','127.0.0.1','koma','Hệ thống'),(34,'Xuất excel log hệ thống','2023-04-01 00:30:47','127.0.0.1','koma','Hệ thống'),(35,'Xem log hệ thống','2023-04-01 00:30:59','0:0:0:0:0:0:0:1','koma','Hệ thống'),(36,'Login','2023-04-01 00:32:13','unknown','koma','System'),(37,'Xem log hệ thống','2023-04-01 00:32:20','192.168.1.3','koma','Hệ thống'),(38,'Xuất excel log hệ thống','2023-04-01 00:32:32','192.168.1.3','koma','Hệ thống'),(39,'Xem log hệ thống','2023-04-01 00:32:39','0:0:0:0:0:0:0:1','koma','Hệ thống'),(40,'Login','2023-04-01 09:14:12','unknown','koma','System'),(41,'Xem log hệ thống','2023-04-01 09:14:22','0:0:0:0:0:0:0:1','koma','Hệ thống'),(42,'Xem log hệ thống','2023-04-01 09:30:03','0:0:0:0:0:0:0:1','koma','Hệ thống'),(43,'Xem log hệ thống','2023-04-01 09:35:38','0:0:0:0:0:0:0:1','koma','Hệ thống'),(44,'Xem log hệ thống','2023-04-01 09:37:18','0:0:0:0:0:0:0:1','koma','Hệ thống'),(45,'Xem log hệ thống','2023-04-01 09:38:17','0:0:0:0:0:0:0:1','koma','Hệ thống'),(46,'Xem log hệ thống','2023-04-01 09:39:17','0:0:0:0:0:0:0:1','koma','Hệ thống'),(47,'Xem log hệ thống','2023-04-01 09:46:30','0:0:0:0:0:0:0:1','koma','Hệ thống'),(48,'Login','2023-04-01 10:57:24','unknown','koma','System'),(49,'Xem log hệ thống','2023-04-01 10:57:24','0:0:0:0:0:0:0:1','koma','Hệ thống'),(50,'Xem log hệ thống','2023-04-01 10:57:34','0:0:0:0:0:0:0:1','koma','Hệ thống'),(51,'Xem log hệ thống','2023-04-01 10:58:30','0:0:0:0:0:0:0:1','koma','Hệ thống'),(52,'Xem log hệ thống','2023-04-01 11:00:55','0:0:0:0:0:0:0:1','koma','Hệ thống'),(53,'Xem log hệ thống','2023-04-01 11:12:32','0:0:0:0:0:0:0:1','koma','Hệ thống'),(54,'Login','2023-04-01 22:03:16','unknown','koma','System'),(55,'Xem log hệ thống','2023-04-01 22:14:10','0:0:0:0:0:0:0:1','koma','Hệ thống'),(56,'Xem log hệ thống','2023-04-01 22:56:31','0:0:0:0:0:0:0:1','koma','Hệ thống'),(57,'Xem log hệ thống','2023-04-01 22:57:47','0:0:0:0:0:0:0:1','koma','Hệ thống'),(58,'Login','2023-04-02 08:27:58','unknown','admin','System'),(59,'Login','2023-04-02 08:57:13','0:0:0:0:0:0:0:1','admin','System'),(60,'Login','2023-04-02 08:57:27','0:0:0:0:0:0:0:1','admin','System'),(61,'Xem danh sách tài khoản hệ thống','2023-04-02 09:42:48','0:0:0:0:0:0:0:1','admin','Hệ thống'),(62,'Xem log hệ thống','2023-04-02 09:42:58','0:0:0:0:0:0:0:1','admin','Hệ thống'),(63,'Thêm mới tài khoản: AccountEntity(ID=25, username=koma4, password=$2a$10$cjo5qCgyZRGM8KBBPStHwe7wSCPZKdF.LNzp0XTC8KdvQ9vfSNyjO, name=Nguyen Duc Viet, gender=true, phone=0706820684, email=nguyenducviet0684@gmail.com, avatar=null, notes=null, status=true)','2023-04-02 09:45:02','0:0:0:0:0:0:0:1','admin','Hệ thống'),(64,'Xem danh sách tài khoản hệ thống','2023-04-02 09:45:02','0:0:0:0:0:0:0:1','admin','Hệ thống'),(65,'Xem log hệ thống','2023-04-02 09:45:05','0:0:0:0:0:0:0:1','admin','Hệ thống'),(66,'Xem danh sách tài khoản hệ thống','2023-04-02 09:46:07','0:0:0:0:0:0:0:1','admin','Hệ thống'),(67,'Xem danh sách tài khoản hệ thống','2023-04-02 09:46:08','0:0:0:0:0:0:0:1','admin','Hệ thống'),(68,'Xem danh sách tài khoản hệ thống','2023-04-02 09:46:09','0:0:0:0:0:0:0:1','admin','Hệ thống'),(69,'Xem log hệ thống','2023-04-02 09:46:09','0:0:0:0:0:0:0:1','admin','Hệ thống'),(70,'Xem danh sách tài khoản hệ thống','2023-04-02 09:46:30','0:0:0:0:0:0:0:1','admin','Hệ thống'),(71,'Xem danh sách tài khoản hệ thống','2023-04-02 09:46:36','0:0:0:0:0:0:0:1','admin','Hệ thống'),(72,'Xem log hệ thống','2023-04-02 09:46:39','0:0:0:0:0:0:0:1','admin','Hệ thống'),(73,'Xem log hệ thống','2023-04-02 09:47:49','0:0:0:0:0:0:0:1','admin','Hệ thống'),(74,'Cập nhật tài khoản hệ thống \nThông tin cũ: AccountEntity(ID=1, username=admin, password=$2a$10$e5.zPCDMNKIuPloVNlVje.o3NG/nuTTngNtSoZV9/K3vIX2AQD3KW, name=Quản trị hệ thống, gender=false, phone=0706820684, email=nguyenducviet0684@gmail.com, avatar=null, notes=null, status=true)\nThông tin mới: AccountEntity(ID=1, username=admin, password=$2a$10$e5.zPCDMNKIuPloVNlVje.o3NG/nuTTngNtSoZV9/K3vIX2AQD3KW, name=Quản trị hệ thống, gender=false, phone=0706820684, email=nguyenducviet0684@gmail.com, avatar=null, notes=null, status=true)','2023-04-02 09:50:09','0:0:0:0:0:0:0:1','admin','Hệ thống'),(75,'Xem log hệ thống','2023-04-02 09:50:13','0:0:0:0:0:0:0:1','admin','Hệ thống'),(76,'Xem log hệ thống','2023-04-02 09:51:28','0:0:0:0:0:0:0:1','admin','Hệ thống'),(77,'Xóa tài khoản koma4','2023-04-02 09:54:38','0:0:0:0:0:0:0:1','admin','Hệ thống'),(78,'Xem log hệ thống','2023-04-02 09:54:41','0:0:0:0:0:0:0:1','admin','Hệ thống'),(79,'Cập nhật tài khoản hệ thống. Thông tin cũ: AccountEntity(ID=1, username=admin, password=$2a$10$e5.zPCDMNKIuPloVNlVje.o3NG/nuTTngNtSoZV9/K3vIX2AQD3KW, name=Quản trị hệ thống, gender=false, phone=0706820684, email=nguyenducviet0684@gmail.com, avatar=null, notes=null, status=false). Thông tin mới: AccountEntity(ID=1, username=admin, password=$2a$10$e5.zPCDMNKIuPloVNlVje.o3NG/nuTTngNtSoZV9/K3vIX2AQD3KW, name=Quản trị hệ thống, gender=false, phone=0706820684, email=nguyenducviet0684@gmail.com, avatar=null, notes=null, status=false)','2023-04-02 09:56:16','0:0:0:0:0:0:0:1','admin','Hệ thống'),(80,'Đăng nhập','2023-04-02 13:30:28','unknown','admin','Hệ thống'),(81,'Đăng nhập','2023-04-02 22:59:50','unknown','admin','Hệ thống'),(82,'Đăng nhập','2023-04-03 00:22:19','unknown','admin','Hệ thống'),(83,'Đăng nhập','2023-04-03 00:24:27','unknown','admin','Hệ thống'),(84,'Đăng nhập','2023-04-03 00:34:53','0:0:0:0:0:0:0:1','admin','Hệ thống'),(85,'Đăng nhập','2023-04-04 10:04:50','unknown','admin','Hệ thống');
/*!40000 ALTER TABLE `systemlog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userrole`
--

DROP TABLE IF EXISTS `userrole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `userrole` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDUser` int(11) NOT NULL,
  `IDRole` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userrole`
--

LOCK TABLES `userrole` WRITE;
/*!40000 ALTER TABLE `userrole` DISABLE KEYS */;
INSERT INTO `userrole` VALUES (32,1,6),(33,1,27),(34,1,8),(35,1,7),(36,1,28),(37,1,10),(38,1,13),(39,1,14),(40,1,15),(41,1,9),(42,1,26),(43,1,11),(44,1,12),(45,1,22),(46,1,23),(47,1,24),(48,1,25),(49,1,5),(50,1,17),(51,1,1),(52,1,2),(53,1,3),(54,1,4),(55,1,16),(56,1,18),(57,1,19),(58,1,20),(59,1,21),(76,6,10),(109,4,6),(110,4,27),(111,4,8),(112,4,28),(113,4,15),(114,4,9),(115,4,22),(116,4,23),(117,4,24),(118,4,1);
/*!40000 ALTER TABLE `userrole` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-06 15:25:17
