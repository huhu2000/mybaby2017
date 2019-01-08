/*
Navicat MySQL Data Transfer

Source Server         : db_mblog
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : babylog

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-03-14 17:26:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bb_baby
-- ----------------------------
DROP TABLE IF EXISTS `bb_baby`;
CREATE TABLE `bb_baby` (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `brithday` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bb_baby
-- ----------------------------
INSERT INTO `bb_baby` VALUES ('1', '最代码牛哥', '2018-03-16');

-- ----------------------------
-- Table structure for bb_blog
-- ----------------------------
DROP TABLE IF EXISTS `bb_blog`;
CREATE TABLE `bb_blog` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日记表主键',
  `first` varchar(200) DEFAULT NULL COMMENT '宝宝的第一次',
  `language` varchar(200) DEFAULT NULL COMMENT '宝宝学会的语言',
  `cognitive` varchar(200) DEFAULT NULL COMMENT '宝宝的认知',
  `blog` text NOT NULL COMMENT '日记正文',
  `create_time` datetime NOT NULL COMMENT '创建记录时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新修改时间，默认为创建记录时间',
  `baby_id` tinyint(4) NOT NULL COMMENT '记录所属宝贝',
  `user_id` tinyint(4) NOT NULL COMMENT '记录者',
  PRIMARY KEY (`id`),
  KEY `bb_blog_bb_baby_id_fk` (`baby_id`),
  KEY `bb_blog_bb_user_id_fk` (`user_id`),
  CONSTRAINT `bb_blog_bb_baby_id_fk` FOREIGN KEY (`baby_id`) REFERENCES `bb_baby` (`id`),
  CONSTRAINT `bb_blog_bb_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `bb_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bb_blog
-- ----------------------------

-- ----------------------------
-- Table structure for bb_healthy
-- ----------------------------
DROP TABLE IF EXISTS `bb_healthy`;
CREATE TABLE `bb_healthy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `height` int(11) NOT NULL COMMENT '身高',
  `weight` float NOT NULL COMMENT '体重',
  `baby_id` tinyint(4) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `bb_healthy_bb_baby_id_fk` (`baby_id`),
  CONSTRAINT `bb_healthy_bb_baby_id_fk` FOREIGN KEY (`baby_id`) REFERENCES `bb_baby` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bb_healthy
-- ----------------------------

-- ----------------------------
-- Table structure for bb_user
-- ----------------------------
DROP TABLE IF EXISTS `bb_user`;
CREATE TABLE `bb_user` (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `password` varchar(32) NOT NULL,
  `gm` tinyint(4) NOT NULL COMMENT '简单管理权限，级别为1-5',
  `amilymembers` varchar(30) NOT NULL COMMENT '所属家庭成员类别 比如爸爸或妈妈',
  PRIMARY KEY (`id`),
  UNIQUE KEY `bb_user_name_uindex` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bb_user
-- ----------------------------
INSERT INTO `bb_user` VALUES ('1', 'admin', '21232f297a57a5a743894a0e4a801fc3', '5', 'baba');
