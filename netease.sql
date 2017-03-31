-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: netease
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `netease_goodsimages`
--

DROP TABLE IF EXISTS `netease_goodsimages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `netease_goodsimages` (
  `goodsId` int(11) NOT NULL COMMENT '商品id',
  `imgSrc` varchar(100) NOT NULL COMMENT '图片路径',
  PRIMARY KEY (`goodsId`,`imgSrc`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品图片';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `netease_goodsimages`
--

LOCK TABLES `netease_goodsimages` WRITE;
/*!40000 ALTER TABLE `netease_goodsimages` DISABLE KEYS */;
/*!40000 ALTER TABLE `netease_goodsimages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `netease_goodsinfo`
--

DROP TABLE IF EXISTS `netease_goodsinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `netease_goodsinfo` (
  `goodsId` int(11) NOT NULL AUTO_INCREMENT,
  `goodsName` varchar(80) NOT NULL COMMENT '商品名称',
  `unitPrice` double DEFAULT '0' COMMENT '单价',
  `sellerId` int(11) NOT NULL COMMENT '卖家id',
  `description` varchar(1000) DEFAULT NULL COMMENT '详细描述',
  `abstractinfo` varchar(140) DEFAULT NULL COMMENT '摘要',
  `imgSrc` varchar(1000) DEFAULT NULL COMMENT '图片路径',
  `amount` int(11) NOT NULL COMMENT '数量',
  `isValid` varchar(2) DEFAULT '1' COMMENT '是否有效 ‘1’-有效 ‘0’-无效',
  PRIMARY KEY (`goodsId`),
  UNIQUE KEY `goodsid_UNIQUE` (`goodsId`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='			';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `netease_goodsinfo`
--

LOCK TABLES `netease_goodsinfo` WRITE;
/*!40000 ALTER TABLE `netease_goodsinfo` DISABLE KEYS */;
INSERT INTO `netease_goodsinfo` VALUES (29,'洗面奶',9.9,2,'洗面奶','洗面奶','http://haitao.nos.netease.com/ivnfb8a536_800_800.jpg',18,'1'),(31,'净水器',999,2,'【热水快至1分钟】碧然德即热水吧，过滤水再加热水，让你安心乐享饮用水！加热水快至1分钟，快的飞起来，喝咖啡喝茶都不是事儿！','【热水快至1分钟】碧然德即热水吧，过滤水再加热水，让你安心乐享饮用水！加热水快至1分钟，快的飞起来，喝咖啡喝茶都不是事儿！','http://haitao.nosdn3.127.net/imwskr4q10_800_800.jpg',13,'1'),(32,'膳魔师',239,2,'黑色的杯身还是第一见呢，磨砂的质感，给人一种低调内敛的感觉，但是内心依旧暖意十足；黑色不限于男生用，女生用起来很酷，也透露出成熟、优雅的感觉。','黑色的杯身还是第一见呢，磨砂的质感，给人一种低调内敛的感觉，但是内心依旧暖意十足；黑色不限于男生用，女生用起来很酷，也透露出成熟、优雅的感觉。','http://haitao.nosdn1.127.net/onlineihlef8n812988.jpg',3,'1'),(33,'Apple 苹果 iPhone 7',4999,2,'这，就是 iPhone 7。它带来了先进的新摄像头系统、更胜以往的性能和电池续航力、富有沉浸感的立体声扬声器、色彩更明亮丰富的 iPhone 显示屏，以及防溅抗水的特性。','这，就是 iPhone 7。它带来了先进的新摄像头系统、更胜以往的性能和电池续航力、富有沉浸感的立体声扬声器、色彩更明亮丰富的 iPhone 显示屏，以及防溅抗水的特性。','http://haitao.nos.netease.com/j0kwqdgo29_800_800.jpg',2,'1');
/*!40000 ALTER TABLE `netease_goodsinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `netease_orderinfo`
--

DROP TABLE IF EXISTS `netease_orderinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `netease_orderinfo` (
  `orderId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '用户id',
  `goodsId` int(11) NOT NULL COMMENT '商品id',
  `orderTime` date DEFAULT NULL COMMENT '订单时间',
  `purchasedAmount` int(11) DEFAULT NULL COMMENT '商品数量',
  `purchasedUnitPrice` double DEFAULT NULL COMMENT '购买时单价',
  `priceSum` double DEFAULT NULL COMMENT '总价',
  `isCompleted` varchar(2) DEFAULT NULL COMMENT '是否完成，0-未完成 1-完成',
  PRIMARY KEY (`orderId`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `netease_orderinfo`
--

LOCK TABLES `netease_orderinfo` WRITE;
/*!40000 ALTER TABLE `netease_orderinfo` DISABLE KEYS */;
INSERT INTO `netease_orderinfo` VALUES (72,1,29,'2017-03-30',2,9.9,19.8,'1'),(73,1,31,'2017-03-30',1,999,999,'1');
/*!40000 ALTER TABLE `netease_orderinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `netease_role`
--

DROP TABLE IF EXISTS `netease_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `netease_role` (
  `roleId` int(11) NOT NULL COMMENT '角色id',
  `rolename` varchar(20) DEFAULT NULL COMMENT '角色名字',
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `netease_role`
--

LOCK TABLES `netease_role` WRITE;
/*!40000 ALTER TABLE `netease_role` DISABLE KEYS */;
INSERT INTO `netease_role` VALUES (1,'买家'),(2,'卖家');
/*!40000 ALTER TABLE `netease_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `netease_shopcart`
--

DROP TABLE IF EXISTS `netease_shopcart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `netease_shopcart` (
  `userid` int(11) NOT NULL COMMENT '用户id',
  `goodsid` int(11) NOT NULL COMMENT '商品id',
  `purchasedAmount` int(11) DEFAULT NULL COMMENT '商品数量',
  PRIMARY KEY (`userid`,`goodsid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='购物车';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `netease_shopcart`
--

LOCK TABLES `netease_shopcart` WRITE;
/*!40000 ALTER TABLE `netease_shopcart` DISABLE KEYS */;
INSERT INTO `netease_shopcart` VALUES (1,32,1);
/*!40000 ALTER TABLE `netease_shopcart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `netease_userinfo`
--

DROP TABLE IF EXISTS `netease_userinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `netease_userinfo` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL COMMENT '用户名',
  `password` varchar(45) NOT NULL COMMENT '密码',
  `createTime` date NOT NULL COMMENT '创建时间',
  `roleId` int(11) DEFAULT NULL COMMENT '用户角色',
  PRIMARY KEY (`username`),
  UNIQUE KEY `userid_UNIQUE` (`userId`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `netease_userinfo`
--

LOCK TABLES `netease_userinfo` WRITE;
/*!40000 ALTER TABLE `netease_userinfo` DISABLE KEYS */;
INSERT INTO `netease_userinfo` VALUES (1,'buyer','37254660e226ea65ce6f1efd54233424','2017-02-13',1),(2,'seller','981c57a5cfb0f868e064904b8745766f','2017-02-13',2);
/*!40000 ALTER TABLE `netease_userinfo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-31  8:40:34
