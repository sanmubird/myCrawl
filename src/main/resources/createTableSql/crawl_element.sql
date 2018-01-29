/*
Navicat MySQL Data Transfer

Source Server         : tale服务器
Source Server Version : 50721
Source Host           : 180.76.233.12:3306
Source Database       : mycrawls

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-01-29 10:56:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for crawl_element
-- ----------------------------
DROP TABLE IF EXISTS `crawl_element`;
CREATE TABLE `crawl_element` (
  `ELEMENTID` varchar(32) NOT NULL COMMENT '元素id',
  `PLANID` varchar(32) DEFAULT NULL COMMENT 'planid',
  `ELEMENTNAME` varchar(255) DEFAULT NULL COMMENT '元素名称',
  `ELEMENTCODE` varchar(255) DEFAULT NULL COMMENT '元素代码',
  `ELEMENTDESC` varchar(255) DEFAULT NULL COMMENT '元素描述',
  `TARGET` varchar(255) DEFAULT NULL COMMENT '筛选规则',
  `CREATETIME` datetime DEFAULT NULL COMMENT '创建时间',
  `ENABLE` tinyint(1) DEFAULT NULL COMMENT '是否启用',
  `LASTESTDAYS` int(11) DEFAULT NULL COMMENT '抓取最近几天的数据',
  `TESTURL` varchar(255) DEFAULT NULL COMMENT '测试抓取规则是否可用时,用的url地址,',
  PRIMARY KEY (`ELEMENTID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
