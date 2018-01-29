/*
Navicat MySQL Data Transfer

Source Server         : tale服务器
Source Server Version : 50721
Source Host           : 180.76.233.12:3306
Source Database       : mycrawls

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-01-29 10:56:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for crawl_result
-- ----------------------------
DROP TABLE IF EXISTS `crawl_result`;
CREATE TABLE `crawl_result` (
  `RESULTID` varchar(32) NOT NULL,
  `ELEMENTID` varchar(32) DEFAULT NULL COMMENT '元素id',
  `RECORDID` varchar(255) DEFAULT NULL COMMENT '设计的原因:为了处理有多个任务同同时爬取的情况',
  `CONTENT` varchar(1500) DEFAULT NULL COMMENT '抓取内容',
  `URL` varchar(255) DEFAULT NULL COMMENT '抓取的url路径',
  `CREATETIME` datetime DEFAULT NULL COMMENT '创建时间',
  `ELEMENTCODE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`RESULTID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
