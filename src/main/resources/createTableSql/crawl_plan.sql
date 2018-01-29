/*
Navicat MySQL Data Transfer

Source Server         : tale服务器
Source Server Version : 50721
Source Host           : 180.76.233.12:3306
Source Database       : mycrawls

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-01-29 10:56:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for crawl_plan
-- ----------------------------
DROP TABLE IF EXISTS `crawl_plan`;
CREATE TABLE `crawl_plan` (
  `PLANID` varchar(32) NOT NULL COMMENT '计划ID',
  `USERID` varchar(32) NOT NULL,
  `PLANNAME` varchar(255) DEFAULT NULL COMMENT '方案名称',
  `PLANDESC` varchar(255) DEFAULT NULL COMMENT '方案描述',
  `CREATETIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CRAWLMEMORYPATH` varchar(255) DEFAULT NULL,
  `THREADNUMBER` int(11) DEFAULT NULL COMMENT '线程数',
  `DEPTNUMBER` int(11) DEFAULT NULL COMMENT '深入层数',
  `CONNECTTIMEOUT` int(11) DEFAULT NULL COMMENT '连接中断时间',
  `TOPN` int(11) DEFAULT NULL COMMENT '设置每次迭代中爬取数量的上限',
  `ISRESUMABLE` tinyint(1) DEFAULT NULL COMMENT '是否禁止重复抓取(0: 会重复抓取)',
  `ISAUTOPARSE` tinyint(1) DEFAULT NULL COMMENT '是否自动解析(1:自动解析 按照过滤规则筛选符合条件的url地址)',
  `ENABLE` tinyint(1) DEFAULT NULL COMMENT '是否启用',
  `MAXCONTENTLENGTH` int(11) DEFAULT NULL,
  PRIMARY KEY (`PLANID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
