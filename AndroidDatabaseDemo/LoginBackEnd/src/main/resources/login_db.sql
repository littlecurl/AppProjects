/*
 Navicat Premium Data Transfer

 Source Server         : 本地mysql
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : localhost:3306
 Source Schema         : login_db

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 12/11/2020 01:46:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for news_info
-- ----------------------------
DROP TABLE IF EXISTS `news_info`;
CREATE TABLE `news_info`  (
  `id` bigint(20) NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标题',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '图片',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of news_info
-- ----------------------------
INSERT INTO `news_info` VALUES (1, 'test title', 'content 111', 'http://i1.fuimg.com/702441/1d18b48fb075ce6f.jpg', '2020-06-17 23:42:11', '2020-06-18 00:45:19');
INSERT INTO `news_info` VALUES (2, 'test title2', 'content 222', 'http://i1.fuimg.com/702441/1d18b48fb075ce6f.jpg', '2020-06-17 23:42:20', '2020-06-18 00:45:23');
INSERT INTO `news_info` VALUES (3, 'title3', 'content 333', 'http://i1.fuimg.com/702441/1d18b48fb075ce6f.jpg', '2020-06-18 11:27:51', '2020-06-18 11:27:58');
INSERT INTO `news_info` VALUES (4, '标题4', 'content 444', 'http://i1.fuimg.com/702441/1d18b48fb075ce6f.jpg', '2020-06-18 11:28:33', '2020-06-18 11:28:40');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '姓名',
  `gender` tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '性别 1-男，2-女',
  `age` int(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '年龄',
  `telephone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '手机号',
  `register_mode` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '注册方式',
  `third_part_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '第三方id',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `KEY_UNIQUE_PHONE`(`telephone`) USING BTREE COMMENT '手机号唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (27, '1', 1, 1, '15733093033', 'byphone', '', '2020-11-09 23:04:05', '2020-11-09 23:04:05');
INSERT INTO `user_info` VALUES (28, '1', 1, 1, '15733091234', 'byphone', '', '2020-11-11 16:06:57', '2020-11-11 16:06:57');

-- ----------------------------
-- Table structure for user_password
-- ----------------------------
DROP TABLE IF EXISTS `user_password`;
CREATE TABLE `user_password`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `encrypt_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '加密后的密码',
  `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '关联用户id',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_password
-- ----------------------------
INSERT INTO `user_password` VALUES (27, '4QrcOUm6Wau+VuBX8g+IPg==', 27, '2020-11-09 23:04:05', '2020-11-09 23:04:05');
INSERT INTO `user_password` VALUES (28, '4QrcOUm6Wau+VuBX8g+IPg==', 28, '2020-11-11 16:06:57', '2020-11-11 16:06:57');

SET FOREIGN_KEY_CHECKS = 1;
