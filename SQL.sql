第一步：创建数据库，如netease
create database netease

第二步：使用数据库
use netease

第三步：创建用户与密码，如netease netease@2017
create user netease@localhost identified by 'netease@2017';

第四步：授权用户
grant select,update,insert on netease.* to netease@localhost identified by 'netease@2017';

第五步：创建表
#用户表
CREATE TABLE `netease_userinfo` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL COMMENT '用户名',
  `password` varchar(45) NOT NULL COMMENT '密码',
  `createTime` date NOT NULL COMMENT '创建时间',
  `roleId` int(11) DEFAULT NULL COMMENT '用户角色',
  PRIMARY KEY (`username`),
  UNIQUE KEY `userid_UNIQUE` (`userId`),
  UNIQUE KEY `username_UNIQUE` (`username`)
); 

#角色表
CREATE TABLE `netease_role` (
  `roleId` int(11) NOT NULL COMMENT '角色id',
  `rolename` varchar(20) DEFAULT NULL COMMENT '角色名字',
  PRIMARY KEY (`roleId`)
);

#商品信息表
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
);

#购物车信息表
CREATE TABLE `netease_shopcart` (
  `userid` int(11) NOT NULL COMMENT '用户id',
  `goodsid` int(11) NOT NULL COMMENT '商品id',
  `purchasedAmount` int(11) DEFAULT NULL COMMENT '商品数量',
  PRIMARY KEY (`userid`,`goodsid`)
);

#订单信息表
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
);

#商品图片表
CREATE TABLE `netease_goodsimages` (
  `goodsId` int(11) NOT NULL COMMENT '商品id',
  `imgSrc` varchar(100) NOT NULL COMMENT '图片路径',
  PRIMARY KEY (`goodsId`,`imgSrc`)
);

第六步：添加表数据
INSERT INTO `netease_userinfo` VALUES (1,'buyer','37254660e226ea65ce6f1efd54233424','2017-02-13',1),(2,'seller','981c57a5cfb0f868e064904b8745766f','2017-02-13',2);
INSERT INTO `netease_role` VALUES (1,'买家'),(2,'卖家');



