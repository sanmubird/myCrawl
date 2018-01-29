/*
Navicat MySQL Data Transfer

Source Server         : tale服务器
Source Server Version : 50721
Source Host           : 180.76.233.12:3306
Source Database       : mycrawls

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-01-29 10:56:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for crawl_regex
-- ----------------------------
DROP TABLE IF EXISTS `crawl_regex`;
CREATE TABLE `crawl_regex` (
  `REGEXID` varchar(32) NOT NULL,
  `PLANID` varchar(32) DEFAULT NULL,
  `REGEXPATH` varchar(255) DEFAULT NULL COMMENT '过滤规则',
  `REGEXPATHDESC` varchar(255) DEFAULT NULL COMMENT '过滤规则描述',
  `CREATETIME` datetime DEFAULT NULL COMMENT '创建时间',
  `ENABLE` tinyint(1) DEFAULT NULL COMMENT '是否启用',
  PRIMARY KEY (`REGEXID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
