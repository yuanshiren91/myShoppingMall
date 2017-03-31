# myShoppingMall
myShoppingMall是一个简单的java web项目，基于SSM框架，实现了购物网站基本功能。

-------------------
在这个项目里主要实现了以下技术：
* spring-webmvc
* mybatis
* mysql
* bootstrap
* jquery
## 运行环境
jdk1.7

tomcat7.0

## 快速运行
第一步：根据database文件配置Mysql数据库，或直接导入netease.sql文件

第二步：根据配置好的数据库信息修改/src/main/resources目录下的jdbc.properties文件

第三步：在本地磁盘中创建一个文件夹作为项目中图片存储文件夹，并修改/src/main/resources目录下config.properties文件，修改file.path项（如：d:\\netease）

第四步：使用eclipse导入maven项目，在eclipse中启动tomcat运行项目，或打包成war包并部署到tomcat下运行。

第五步：本地通过浏览器访问http://localhost:8080/myShoppingMall/index
