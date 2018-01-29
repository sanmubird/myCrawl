/*
Navicat MySQL Data Transfer

Source Server         : tale服务器
Source Server Version : 50721
Source Host           : 180.76.233.12:3306
Source Database       : mycrawls

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-01-29 10:56:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for crawl_record
-- ----------------------------
DROP TABLE IF EXISTS `crawl_record`;
CREATE TABLE `crawl_record` (
  `RECORDID` varchar(32) NOT NULL,
  `PLANID` varchar(32) DEFAULT NULL,
  `USERID` varchar(32) DEFAULT NULL,
  `RESULTPATH` varchar(255) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `ISFINISHED` tinyint(1) DEFAULT NULL COMMENT '是否完成',
  PRIMARY KEY (`RECORDID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
