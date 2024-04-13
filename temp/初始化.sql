-- MySQL dump 10.13  Distrib 8.3.0, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: log_manager_server_alert
-- ------------------------------------------------------
-- Server version	8.3.0

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

--
-- Current Database: `log_manager_server_alert`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `log_manager_server_alert` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `log_manager_server_alert`;

--
-- Table structure for table `alert_history`
--

DROP TABLE IF EXISTS `alert_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alert_history` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '所属应用名称',
  `app_group` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '所属应用分组',
  `level` tinyint NOT NULL COMMENT '告警级别',
  `rule_id` bigint NOT NULL COMMENT '规则id',
  `rule_name` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '规则名称',
  `rule_description` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '规则描述',
  `alert_description` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '告警信息描述',
  `alert_receiver` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '告警接收人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `creator_id` bigint DEFAULT NULL COMMENT '创建人',
  `updater_id` bigint DEFAULT NULL COMMENT '最后更新人',
  `delete_flag` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除(0:否 1:是)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alert_history`
--

LOCK TABLES `alert_history` WRITE;
/*!40000 ALTER TABLE `alert_history` DISABLE KEYS */;
INSERT INTO `alert_history` VALUES (1,'log-manager-system-admin','dev',1,3,'正则匹配固定窗口计数测试','测试一下正则匹配固定窗口计数功能','满足匹配条件[hhhh \\d+ test],统计条件[{\"cycle\":60,\"threadhold\":1,\"threshold\":5}],最后匹配关键词','admin','2024-03-21 06:22:34','2024-03-21 06:22:34',NULL,NULL,0),(2,'log-manager-system-admin','dev',1,3,'正则匹配固定窗口计数测试','测试一下正则匹配固定窗口计数功能','满足匹配条件[hhhh \\d+ test],统计条件[{\"cycle\":60,\"threadhold\":1,\"threshold\":5}],最后匹配关键词','admin','2024-03-21 06:22:37','2024-03-21 06:22:37',NULL,NULL,0),(3,'log-manager-system-admin','dev',1,3,'正则匹配固定窗口计数测试','测试一下正则匹配固定窗口计数功能','满足匹配条件[hhhh \\d+ test],统计条件[[固定窗口计数]最近60秒内,关键字命中次数超过5次]','admin','2024-03-21 06:28:06','2024-03-21 06:28:06',NULL,NULL,0),(4,'log-manager-system-admin','dev',3,4,'固定窗口百分比测试','123','满足匹配条件[connection timeout],统计条件[[固定窗口百分比统计]最近3秒内,关键字命中百分比达到0.2]','admin','2024-03-21 06:28:09','2024-03-21 06:28:09',NULL,NULL,0),(5,'log-manager-system-admin','dev',3,4,'固定窗口百分比测试','123','满足匹配条件[connection timeout],统计条件[[固定窗口百分比统计]最近3秒内,关键字命中百分比达到0.2]','admin','2024-03-21 06:28:13','2024-03-21 06:28:13',NULL,NULL,0),(6,'log-manager-system-admin','dev',1,3,'正则匹配固定窗口计数测试','测试一下正则匹配固定窗口计数功能','满足匹配条件[hhhh \\d+ test],统计条件[[固定窗口计数]最近60秒内,关键字命中次数超过5次]','admin','2024-03-21 06:28:16','2024-03-21 06:28:16',NULL,NULL,0),(7,'log-manager-system-admin','dev',3,4,'固定窗口百分比测试','123','满足匹配条件[connection timeout],统计条件[[固定窗口百分比统计]最近3秒内,关键字命中百分比达到0.2]','admin','2024-03-21 06:28:20','2024-03-21 06:28:20',NULL,NULL,0),(8,'log-manager-system-admin','dev',3,4,'固定窗口百分比测试','123','满足匹配条件[connection timeout],统计条件[[固定窗口百分比统计]最近3秒内,关键字命中百分比达到0.2]','admin','2024-03-21 06:28:23','2024-03-21 06:28:23',NULL,NULL,0),(9,'log-manager-system-admin','dev',3,4,'固定窗口百分比测试','123','满足匹配关键字[connection timeout],统计条件[[固定窗口百分比统计]最近3秒内,关键字命中百分比达到0.2]','admin','2024-03-23 03:28:57','2024-03-23 03:28:57',NULL,NULL,0),(10,'log-manager-system-admin','dev',1,3,'正则匹配固定窗口计数测试','测试一下正则匹配固定窗口计数功能','满足匹配关键字[hhhh \\d+ test],统计条件[[固定窗口计数]最近60秒内,关键字命中次数超过5次]','admin','2024-03-23 03:29:01','2024-03-23 03:29:01',NULL,NULL,0),(11,'log-manager-system-admin','dev',1,3,'正则匹配固定窗口计数测试','测试一下正则匹配固定窗口计数功能','满足匹配关键字[hhhh \\d+ test],统计条件[[固定窗口计数]最近60秒内,关键字命中次数超过5次]','admin','2024-03-23 03:29:59','2024-03-23 03:29:59',NULL,NULL,0),(12,'log-manager-system-admin','dev',1,3,'正则匹配固定窗口计数测试','测试一下正则匹配固定窗口计数功能','满足匹配关键字[hhhh \\d+ test],统计条件[[固定窗口计数]最近60秒内,关键字命中次数超过5次]','admin','2024-03-23 03:30:03','2024-03-23 03:30:03',NULL,NULL,0),(13,'log-manager-system-admin','dev',1,3,'正则匹配固定窗口计数测试','测试一下正则匹配固定窗口计数功能','满足匹配关键字[hhhh \\d+ test],统计条件[[固定窗口计数]最近60秒内,关键字命中次数超过5次]','admin','2024-03-23 03:30:06','2024-03-23 03:30:06',NULL,NULL,0),(14,'log-manager-system-admin','dev',3,4,'固定窗口百分比测试','123','满足匹配关键字[connection timeout],统计条件[[固定窗口百分比统计]最近3秒内,关键字命中百分比达到0.2]','admin','2024-03-23 03:30:09','2024-03-23 03:30:09',NULL,NULL,0),(15,'log-manager-system-admin','dev',1,3,'正则匹配固定窗口计数测试','测试一下正则匹配固定窗口计数功能','满足匹配关键字[hhhh \\d+ test],统计条件[[固定窗口计数]最近60秒内,关键字命中次数超过5次]','admin','2024-03-23 03:35:13','2024-03-23 03:35:13',NULL,NULL,0),(16,'log-manager-system-admin','dev',1,3,'正则匹配固定窗口计数测试','测试一下正则匹配固定窗口计数功能','满足匹配关键字[hhhh \\d+ test],统计条件[[固定窗口计数]最近60秒内,关键字命中次数超过5次]','admin','2024-03-23 03:35:16','2024-03-23 03:35:16',NULL,NULL,0),(17,'log-manager-system-admin','dev',3,4,'固定窗口百分比测试','123','满足匹配关键字[connection timeout],统计条件[[固定窗口百分比统计]最近3秒内,关键字命中百分比达到0.2]','admin','2024-03-23 03:35:19','2024-03-23 03:35:19',NULL,NULL,0);
/*!40000 ALTER TABLE `alert_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alert_rule`
--

DROP TABLE IF EXISTS `alert_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alert_rule` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属应用名称',
  `app_group` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属应用分组',
  `name` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '规则名称',
  `enabled` tinyint DEFAULT NULL COMMENT '是否启用',
  `level` tinyint DEFAULT NULL COMMENT '报警级别',
  `description` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '应用描述',
  `match_condition` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '匹配条件',
  `statistic_type` tinyint DEFAULT NULL COMMENT '统计策略 1立即报警 2固定窗口计数 3:滑动窗口计数 4:滑动窗口百分比',
  `alert_condition` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '报警条件,包括时间、阈值等',
  `alert_receiver` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '报警接收人',
  `notification_types` json DEFAULT NULL COMMENT '通知方式(可多选)',
  `mute_revert_time` timestamp NULL DEFAULT NULL COMMENT '静默恢复时间',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `creator_id` bigint DEFAULT NULL COMMENT '创建人',
  `updater_id` bigint DEFAULT NULL COMMENT '最后更新人',
  `delete_flag` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除(0:否 1:是)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alert_rule`
--

LOCK TABLES `alert_rule` WRITE;
/*!40000 ALTER TABLE `alert_rule` DISABLE KEYS */;
INSERT INTO `alert_rule` VALUES (1,'log-manager-user-center','dev','',1,NULL,NULL,NULL,NULL,'null',NULL,NULL,NULL,'2024-03-11 10:00:53','2024-03-11 10:23:10',1,1,1),(3,'log-manager-system-admin','dev','正则匹配固定窗口计数测试',1,1,'测试一下正则匹配固定窗口计数功能','hhhh \\d+ test',2,'{\"cycle\":60,\"threadhold\":1,\"threshold\":5}','admin','[2, 1]',NULL,'2024-03-11 10:19:01','2024-03-23 03:29:42',1,1,0),(4,'log-manager-system-admin','dev','固定窗口百分比测试',1,3,'123','connection timeout',4,'{\"cycle\":3,\"threshold\":0.2,\"denominatorExpression\":\"connection\"}','admin','[1]',NULL,'2024-03-14 04:23:25','2024-03-23 03:29:51',1,1,0);
/*!40000 ALTER TABLE `alert_rule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `log_manager_server_user`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `log_manager_server_user` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `log_manager_server_user`;

--
-- Table structure for table `sys_app`
--

DROP TABLE IF EXISTS `sys_app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_app` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用名称',
  `description` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '应用描述',
  `groups` json DEFAULT NULL COMMENT '应用分组',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `creator_id` bigint DEFAULT NULL COMMENT '创建人',
  `updater_id` bigint DEFAULT NULL COMMENT '最后更新人',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `delete_flag` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除(0:否 1:是)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_app`
--

LOCK TABLES `sys_app` WRITE;
/*!40000 ALTER TABLE `sys_app` DISABLE KEYS */;
INSERT INTO `sys_app` VALUES (1,'log-manager-query-center','日志查询中心','[\"dev\"]','2024-03-02 04:40:25','2024-03-02 04:40:25',1,1,1,0),(2,'log-manager-user-center','迁移至system-admin','[\"dev\"]','2024-03-02 04:40:25','2024-03-02 04:40:25',1,1,0,0),(3,'log-manager-system-admin','日志后台系统管理中心','[\"dev\"]','2024-03-02 04:40:25','2024-03-02 04:40:25',1,1,1,0),(4,'hello-elk','测试12345678','[\"demo\"]','2024-03-02 04:40:25','2024-03-02 04:40:25',1,1,0,0),(5,'%{[app',NULL,'[\"%{[group]}\"]','2024-03-02 04:40:25','2024-03-02 11:14:11',1,1,1,1),(6,'%{[app',NULL,'[\"%{[group]}\"]','2024-03-03 04:44:51','2024-03-03 04:45:33',1,1,1,1),(7,'log-manager-server-alert',NULL,'[\"dev\"]','2024-03-11 08:05:12','2024-03-11 08:05:12',1,1,1,0),(8,'%{[app',NULL,'[\"%{[group]}\"]','2024-03-11 08:05:12','2024-03-11 08:05:12',1,1,0,0),(9,'log-manager-server-messaging',NULL,'[\"dev\"]','2024-03-21 05:57:44','2024-03-21 05:57:44',1,1,1,0);
/*!40000 ALTER TABLE `sys_app` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_station_message`
--

DROP TABLE IF EXISTS `sys_station_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_station_message` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户账号',
  `message_type` tinyint NOT NULL COMMENT '1:通知 2:消息 3:代办',
  `title` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `content` varchar(4096) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息内容',
  `read_flag` tinyint NOT NULL DEFAULT '0' COMMENT '是否已读',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `creator_id` bigint DEFAULT NULL COMMENT '创建人',
  `updater_id` bigint DEFAULT NULL COMMENT '最后更新人',
  `delete_flag` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除(0:否 1:是)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户站内信';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_station_message`
--

LOCK TABLES `sys_station_message` WRITE;
/*!40000 ALTER TABLE `sys_station_message` DISABLE KEYS */;
INSERT INTO `sys_station_message` VALUES (1,'admin',1,'日志中心告警-提示-log-manager-system-admin-dev','命中告警规则:测试一下正则匹配固定窗口计数功能\n告警信息:满足匹配关键字[hhhh \\d+ test],统计条件[[固定窗口计数]最近60秒内,关键字命中次数超过5次]\n告警接收人:admin\n',0,'2024-03-23 03:35:16','2024-03-23 03:35:16',NULL,NULL,0),(2,'admin',1,'日志中心告警-提示-log-manager-system-admin-dev','命中告警规则:测试一下正则匹配固定窗口计数功能\n告警信息:满足匹配关键字[hhhh \\d+ test],统计条件[[固定窗口计数]最近60秒内,关键字命中次数超过5次]\n告警接收人:admin\n',0,'2024-03-23 03:35:19','2024-03-23 03:35:19',NULL,NULL,0),(3,'admin',1,'日志中心告警-重要-log-manager-system-admin-dev','命中告警规则:123\n告警信息:满足匹配关键字[connection timeout],统计条件[[固定窗口百分比统计]最近3秒内,关键字命中百分比达到0.2]\n告警接收人:admin\n',0,'2024-03-23 03:35:19','2024-03-23 03:35:19',NULL,NULL,0);
/*!40000 ALTER TABLE `sys_station_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码Base64',
  `nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
  `avatar_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像url',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `creator_id` bigint DEFAULT NULL COMMENT '创建人',
  `updater_id` bigint DEFAULT NULL COMMENT '最后更新人',
  `delete_flag` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除(0:否 1:是)',
  `phone` char(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `email` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'admin','68d668b06529dd396cd157431525198a','张三',NULL,'2024-02-27 05:16:20','2024-02-27 05:16:20',NULL,1,0,'13345678901','2633247964@qq.com'),(2,'test','68d668b06529dd396cd157431525198a','test',NULL,'2024-02-29 03:19:48','2024-02-29 03:19:48',1,1,0,NULL,NULL),(3,'hhh','68d668b06529dd396cd157431525198a','hhh',NULL,'2024-03-03 04:49:08','2024-03-03 04:49:08',1,1,0,NULL,NULL),(4,'hfadldf','68d668b06529dd396cd157431525198a','hfadldf',NULL,'2024-03-04 06:05:32','2024-03-06 06:09:02',1,1,1,NULL,NULL),(5,'qqqqq','68d668b06529dd396cd157431525198a','qqqqq',NULL,'2024-03-06 06:09:27','2024-03-06 07:20:04',1,1,1,NULL,NULL),(6,'123','68d668b06529dd396cd157431525198a','123',NULL,'2024-03-06 07:20:33','2024-03-06 07:20:33',1,1,0,NULL,NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_app_role`
--

DROP TABLE IF EXISTS `sys_user_app_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_app_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `app_id` bigint NOT NULL COMMENT '应用id',
  `role_id` bigint NOT NULL COMMENT '角色id',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `creator_id` bigint DEFAULT NULL COMMENT '创建人',
  `updater_id` bigint DEFAULT NULL COMMENT '最后更新人',
  `delete_flag` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除(0:否 1:是)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_app_role`
--

LOCK TABLES `sys_user_app_role` WRITE;
/*!40000 ALTER TABLE `sys_user_app_role` DISABLE KEYS */;
INSERT INTO `sys_user_app_role` VALUES (1,1,-1,1,'2024-02-28 10:10:14','2024-02-28 10:10:14',NULL,NULL,1),(2,1,-1,2,'2024-02-28 10:26:52','2024-02-28 10:26:52',NULL,NULL,0),(3,2,-1,2,'2024-02-29 03:19:48','2024-02-29 03:19:48',1,1,0),(4,2,-1,1,'2024-02-29 03:19:48','2024-02-29 03:19:48',1,1,1),(5,2,-1,1,'2024-02-29 03:50:25','2024-02-29 03:50:25',1,1,1),(6,1,-1,1,'2024-02-29 03:51:03','2024-02-29 03:51:03',1,1,1),(7,1,-1,1,'2024-02-29 03:54:35','2024-02-29 03:54:35',2,2,0),(8,1,1,11,'2024-03-02 09:51:31','2024-03-02 09:51:31',1,1,1),(9,1,2,11,'2024-03-02 09:51:31','2024-03-02 09:51:31',1,1,1),(10,1,3,11,'2024-03-02 09:51:31','2024-03-02 09:51:31',1,1,1),(11,1,4,11,'2024-03-02 09:51:31','2024-03-02 09:51:31',1,1,1),(12,1,5,11,'2024-03-02 09:51:31','2024-03-02 09:51:31',1,1,0),(13,2,1,11,'2024-03-02 11:05:51','2024-03-02 11:05:51',1,1,1),(14,2,1,11,'2024-03-02 11:10:51','2024-03-02 11:10:51',1,1,1),(15,1,1,11,'2024-03-03 04:44:51','2024-03-03 04:44:51',1,1,1),(16,1,2,11,'2024-03-03 04:44:51','2024-03-03 04:44:51',1,1,1),(17,1,3,11,'2024-03-03 04:44:51','2024-03-03 04:44:51',1,1,1),(18,1,4,11,'2024-03-03 04:44:51','2024-03-03 04:44:51',1,1,1),(19,1,6,11,'2024-03-03 04:44:51','2024-03-03 04:44:51',1,1,0),(20,1,1,12,'2024-03-03 04:44:51','2024-03-03 04:44:51',1,1,1),(21,1,2,12,'2024-03-03 04:44:51','2024-03-03 04:44:51',1,1,1),(22,1,3,12,'2024-03-03 04:44:51','2024-03-03 04:44:51',1,1,1),(23,1,4,12,'2024-03-03 04:44:51','2024-03-03 04:44:51',1,1,1),(24,1,6,12,'2024-03-03 04:44:51','2024-03-03 04:44:51',1,1,0),(25,2,4,12,'2024-03-03 04:45:44','2024-03-03 04:45:44',1,1,0),(26,2,4,11,'2024-03-03 04:45:44','2024-03-03 04:45:44',1,1,0),(27,3,-1,2,'2024-03-03 04:49:08','2024-03-03 04:49:08',1,1,0),(28,3,4,12,'2024-03-03 04:49:52','2024-03-03 04:49:52',1,1,0),(29,4,-1,2,'2024-03-04 06:05:32','2024-03-04 06:05:32',1,1,1),(30,4,-1,1,'2024-03-04 06:06:36','2024-03-04 06:06:36',1,1,1),(31,4,-1,1,'2024-03-05 02:03:35','2024-03-05 02:03:35',1,1,1),(32,5,-1,2,'2024-03-06 06:09:27','2024-03-06 06:09:27',1,1,1),(33,5,-1,1,'2024-03-06 06:09:29','2024-03-06 06:09:29',1,1,1),(34,5,1,12,'2024-03-06 06:09:40','2024-03-06 06:09:40',1,1,1),(35,6,-1,2,'2024-03-06 07:20:33','2024-03-06 07:20:33',1,1,0),(36,6,-1,1,'2024-03-06 07:20:45','2024-03-06 07:20:45',1,1,1),(37,6,4,12,'2024-03-06 07:21:06','2024-03-06 07:21:06',1,1,0),(38,1,1,11,'2024-03-11 08:05:12','2024-03-11 08:05:12',1,1,1),(39,1,2,11,'2024-03-11 08:05:12','2024-03-11 08:05:12',1,1,1),(40,1,3,11,'2024-03-11 08:05:12','2024-03-11 08:05:12',1,1,1),(41,1,4,11,'2024-03-11 08:05:12','2024-03-11 08:05:12',1,1,1),(42,1,7,11,'2024-03-11 08:05:12','2024-03-11 08:05:12',1,1,1),(43,1,8,11,'2024-03-11 08:05:12','2024-03-11 08:05:12',1,1,1),(44,1,1,12,'2024-03-11 08:05:12','2024-03-11 08:05:12',1,1,1),(45,1,2,12,'2024-03-11 08:05:12','2024-03-11 08:05:12',1,1,1),(46,1,3,12,'2024-03-11 08:05:12','2024-03-11 08:05:12',1,1,1),(47,1,4,12,'2024-03-11 08:05:12','2024-03-11 08:05:12',1,1,1),(48,1,7,12,'2024-03-11 08:05:12','2024-03-11 08:05:12',1,1,1),(49,1,8,12,'2024-03-11 08:05:12','2024-03-11 08:05:12',1,1,1),(50,1,1,11,'2024-03-21 05:12:02','2024-03-21 05:12:02',1,1,1),(51,1,2,11,'2024-03-21 05:12:02','2024-03-21 05:12:02',1,1,1),(52,1,3,11,'2024-03-21 05:12:02','2024-03-21 05:12:02',1,1,1),(53,1,4,11,'2024-03-21 05:12:02','2024-03-21 05:12:02',1,1,1),(54,1,7,11,'2024-03-21 05:12:02','2024-03-21 05:12:02',1,1,1),(55,1,8,11,'2024-03-21 05:12:02','2024-03-21 05:12:02',1,1,1),(56,1,1,12,'2024-03-21 05:12:02','2024-03-21 05:12:02',1,1,1),(57,1,2,12,'2024-03-21 05:12:02','2024-03-21 05:12:02',1,1,1),(58,1,3,12,'2024-03-21 05:12:02','2024-03-21 05:12:02',1,1,1),(59,1,4,12,'2024-03-21 05:12:02','2024-03-21 05:12:02',1,1,1),(60,1,7,12,'2024-03-21 05:12:02','2024-03-21 05:12:02',1,1,1),(61,1,8,12,'2024-03-21 05:12:02','2024-03-21 05:12:02',1,1,1),(62,1,1,11,'2024-03-21 05:50:27','2024-03-21 05:50:27',1,1,1),(63,1,2,11,'2024-03-21 05:50:27','2024-03-21 05:50:27',1,1,1),(64,1,3,11,'2024-03-21 05:50:27','2024-03-21 05:50:27',1,1,1),(65,1,4,11,'2024-03-21 05:50:27','2024-03-21 05:50:27',1,1,1),(66,1,7,11,'2024-03-21 05:50:27','2024-03-21 05:50:27',1,1,1),(67,1,8,11,'2024-03-21 05:50:27','2024-03-21 05:50:27',1,1,1),(68,1,1,12,'2024-03-21 05:50:27','2024-03-21 05:50:27',1,1,1),(69,1,2,12,'2024-03-21 05:50:27','2024-03-21 05:50:27',1,1,1),(70,1,3,12,'2024-03-21 05:50:27','2024-03-21 05:50:27',1,1,1),(71,1,4,12,'2024-03-21 05:50:27','2024-03-21 05:50:27',1,1,1),(72,1,7,12,'2024-03-21 05:50:27','2024-03-21 05:50:27',1,1,1),(73,1,8,12,'2024-03-21 05:50:27','2024-03-21 05:50:27',1,1,1),(74,1,1,11,'2024-03-21 05:50:41','2024-03-21 05:50:41',1,1,1),(75,1,2,11,'2024-03-21 05:50:41','2024-03-21 05:50:41',1,1,1),(76,1,3,11,'2024-03-21 05:50:41','2024-03-21 05:50:41',1,1,1),(77,1,4,11,'2024-03-21 05:50:41','2024-03-21 05:50:41',1,1,1),(78,1,7,11,'2024-03-21 05:50:41','2024-03-21 05:50:41',1,1,1),(79,1,8,11,'2024-03-21 05:50:41','2024-03-21 05:50:41',1,1,1),(80,1,1,12,'2024-03-21 05:50:41','2024-03-21 05:50:41',1,1,1),(81,1,2,12,'2024-03-21 05:50:41','2024-03-21 05:50:41',1,1,1),(82,1,3,12,'2024-03-21 05:50:41','2024-03-21 05:50:41',1,1,1),(83,1,4,12,'2024-03-21 05:50:41','2024-03-21 05:50:41',1,1,1),(84,1,7,12,'2024-03-21 05:50:41','2024-03-21 05:50:41',1,1,1),(85,1,8,12,'2024-03-21 05:50:41','2024-03-21 05:50:41',1,1,1),(86,1,1,11,'2024-03-21 05:57:44','2024-03-21 05:57:44',1,1,0),(87,1,2,11,'2024-03-21 05:57:44','2024-03-21 05:57:44',1,1,0),(88,1,3,11,'2024-03-21 05:57:44','2024-03-21 05:57:44',1,1,0),(89,1,4,11,'2024-03-21 05:57:44','2024-03-21 05:57:44',1,1,0),(90,1,7,11,'2024-03-21 05:57:44','2024-03-21 05:57:44',1,1,0),(91,1,8,11,'2024-03-21 05:57:44','2024-03-21 05:57:44',1,1,0),(92,1,9,11,'2024-03-21 05:57:44','2024-03-21 05:57:44',1,1,0),(93,1,1,12,'2024-03-21 05:57:44','2024-03-21 05:57:44',1,1,0),(94,1,2,12,'2024-03-21 05:57:44','2024-03-21 05:57:44',1,1,0),(95,1,3,12,'2024-03-21 05:57:44','2024-03-21 05:57:44',1,1,0),(96,1,4,12,'2024-03-21 05:57:44','2024-03-21 05:57:44',1,1,0),(97,1,7,12,'2024-03-21 05:57:44','2024-03-21 05:57:44',1,1,0),(98,1,8,12,'2024-03-21 05:57:44','2024-03-21 05:57:44',1,1,0),(99,1,9,12,'2024-03-21 05:57:44','2024-03-21 05:57:44',1,1,0);
/*!40000 ALTER TABLE `sys_user_app_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-13 13:43:54
