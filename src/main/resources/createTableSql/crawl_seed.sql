/*
Navicat MySQL Data Transfer

Source Server         : tale服务器
Source Server Version : 50721
Source Host           : 180.76.233.12:3306
Source Database       : mycrawls

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-01-29 10:56:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for crawl_seed
-- ----------------------------
DROP TABLE IF EXISTS `crawl_seed`;
CREATE TABLE `crawl_seed` (
  `SEEDID` varchar(32) NOT NULL COMMENT '种子地址ID',
  `PLANID` varchar(32) DEFAULT NULL COMMENT '方案ID',
  `SEEDPATH` varchar(255) DEFAULT NULL COMMENT '种子地址',
  `SEEDPATHDESC` varchar(255) DEFAULT NULL COMMENT '种子路径描述',
  `CREATETIME` datetime DEFAULT NULL COMMENT '创建时间',
  `ENABLE` tinyint(1) DEFAULT '1' COMMENT '是否启用',
  PRIMARY KEY (`SEEDID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
