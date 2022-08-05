/*
 Navicat MySQL Data Transfer

 Source Server         : RDS-MySql8.0
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : rm-7xvzddpb86a78d79zbo.mysql.rds.aliyuncs.com:3306
 Source Schema         : mybatis

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 05/08/2022 18:01:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名字',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `gender` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'Barry', 15, 'M');
INSERT INTO `user` VALUES (2, 'Ben', 17, 'M');

SET FOREIGN_KEY_CHECKS = 1;
