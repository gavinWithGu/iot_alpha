/*
Navicat MySQL Data Transfer

Source Server         : 10.44.55.218
Source Server Version : 50173
Source Host           : 10.44.55.218:3306
Source Database       : iot

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2017-07-25 16:20:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin_info`
-- ----------------------------
DROP TABLE IF EXISTS `admin_info`;
CREATE TABLE `admin_info` (
  `_id` int(11) NOT NULL,
  `_mobile_phone` varchar(11) DEFAULT NULL,
  `_email` varchar(30) DEFAULT NULL,
  `_password` varchar(50) DEFAULT NULL,
  `_created_time` datetime DEFAULT NULL,
  `_login_account` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='超级管理员表';

-- ----------------------------
-- Records of admin_info
-- ----------------------------

-- ----------------------------
-- Table structure for `api_log`
-- ----------------------------
DROP TABLE IF EXISTS `api_log`;
CREATE TABLE `api_log` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `_created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `_beacon_info_id` int(11) DEFAULT NULL,
  `_beacon_id` varchar(32) DEFAULT NULL,
  `_call_user_mobile` varchar(20) DEFAULT NULL,
  `_call_mobile_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`_id`),
  KEY `FK_Reference_1` (`_beacon_info_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of api_log
-- ----------------------------

-- ----------------------------
-- Table structure for `beacon_category`
-- ----------------------------
DROP TABLE IF EXISTS `beacon_category`;
CREATE TABLE `beacon_category` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `_name` varchar(30) DEFAULT NULL,
  `_parent_id` int(11) DEFAULT NULL,
  `_level` tinyint(4) DEFAULT NULL,
  `_contents` varchar(100) DEFAULT NULL,
  `_h5_url` varchar(100) DEFAULT NULL,
  `_created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `_h5_tmpl_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`_id`),
  KEY `FK_Reference_5` (`_parent_id`) USING BTREE,
  KEY `fk_h5_tmpl_id` (`_h5_tmpl_id`) USING BTREE,
  CONSTRAINT `beacon_category_ibfk_1` FOREIGN KEY (`_h5_tmpl_id`) REFERENCES `h5_tmpl` (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of beacon_category
-- ----------------------------

-- ----------------------------
-- Table structure for `beacon_devices`
-- ----------------------------
DROP TABLE IF EXISTS `beacon_devices`;
CREATE TABLE `beacon_devices` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `_beacon_id` varchar(64) DEFAULT NULL,
  `_content` varchar(2000) DEFAULT NULL COMMENT '说明1，说明2，说明3......\n            json array格式存储',
  `_h5_url` varchar(100) DEFAULT NULL,
  `_status` tinyint(4) DEFAULT NULL COMMENT '1. 在线 2.离线',
  `_geo_location` varchar(100) DEFAULT NULL,
  `_floor` int(11) DEFAULT NULL,
  `_type_id` int(11) DEFAULT NULL,
  `_pic_url` varchar(1000) DEFAULT NULL COMMENT '图片1，图片2，图片3......\n            json array格式存储',
  `_created_time` timestamp NULL DEFAULT NULL COMMENT '创建日期',
  `_h5_tmpl_id` int(11) DEFAULT NULL,
  `_owner_id` int(11) DEFAULT NULL COMMENT 'beacon拥有者的id',
  `_rssi_at_transmitting` varchar(10) DEFAULT NULL COMMENT '信号强弱',
  `_address_detail` varchar(100) DEFAULT NULL COMMENT '位置字符说明',
  `_application_type` tinyint(4) DEFAULT NULL COMMENT 'beacon类型',
  `_beacon_mac_address` varchar(32) NOT NULL,
  PRIMARY KEY (`_id`),
  KEY `FK_Reference_4` (`_type_id`) USING BTREE,
  KEY `fk_beacon_h5_tmpl_id` (`_h5_tmpl_id`) USING BTREE,
  KEY `_beacon_id` (`_beacon_id`) USING BTREE,
  CONSTRAINT `beacon_devices_ibfk_1` FOREIGN KEY (`_h5_tmpl_id`) REFERENCES `h5_tmpl` (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of beacon_devices
-- ----------------------------
INSERT INTO `beacon_devices` VALUES ('14', '71b9d5d6-15c2-11e6-8071-00163e07', null, null, '2', null, null, null, null, '2017-02-17 16:58:34', null, '4', '-70', null, '1', '11223344');
INSERT INTO `beacon_devices` VALUES ('15', '71b9d5d6-15c2-11e6-8071-00163e08', null, null, '2', null, null, null, null, '2017-02-17 17:04:03', null, '4', '-80', null, '1', '71b9d5d6-15c2-11e6-8071-00163e08');
INSERT INTO `beacon_devices` VALUES ('16', '71b9d5d6-15c2-11e6-8071-00163e09', null, null, '2', null, null, null, null, '2017-02-17 17:04:17', null, '4', '-50', null, '1', '11223355');
INSERT INTO `beacon_devices` VALUES ('17', '123', null, null, '2', null, null, null, null, '2017-06-05 15:39:36', null, '4', '123', null, null, '123');

-- ----------------------------
-- Table structure for `beacon_frames`
-- ----------------------------
DROP TABLE IF EXISTS `beacon_frames`;
CREATE TABLE `beacon_frames` (
  `_source_mac_address` varchar(32) NOT NULL,
  `_frame_id` varchar(32) NOT NULL,
  `_time_received` varchar(30) NOT NULL,
  `_rssi_at_receiving` varchar(10) NOT NULL,
  `_payload` varchar(100) NOT NULL,
  `_ap_mac_address` varchar(32) NOT NULL,
  `_cloud_received_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY `idx_mac_frame` (`_source_mac_address`,`_frame_id`,`_ap_mac_address`) USING BTREE,
  KEY `idx_time_received` (`_time_received`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of beacon_frames
-- ----------------------------
INSERT INTO `beacon_frames` VALUES ('71b9d5d6-15c2-11e6-8071-00163e07', '223344', '1493400508782', '-70', '30.000|26.396|99', '112233445566', '2017-07-25 12:21:11');
INSERT INTO `beacon_frames` VALUES ('71b9d5d6-15c2-11e6-8071-00163e07', '223344', '1493400508782', '-70', '30.000|26.396|99', '112233445566', '2017-07-25 12:21:12');
INSERT INTO `beacon_frames` VALUES ('71b9d5d6-15c2-11e6-8071-00163e07', '223344', '1493400508782', '-70', '30.000|26.396|99', '112233445566', '2017-07-25 12:21:12');
INSERT INTO `beacon_frames` VALUES ('71b9d5d6-15c2-11e6-8071-00163e07', '223344', '1493400508782', '-70', '30.000|26.396|99', '112233445566', '2017-07-25 12:21:12');

-- ----------------------------
-- Table structure for `beacon_owners`
-- ----------------------------
DROP TABLE IF EXISTS `beacon_owners`;
CREATE TABLE `beacon_owners` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `_name` varchar(20) DEFAULT NULL,
  `_created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `_code` char(6) DEFAULT NULL,
  `_email` varchar(50) DEFAULT NULL,
  `_privilege_id` int(11) DEFAULT NULL,
  `_description` varchar(100) DEFAULT NULL,
  `_phone` varchar(20) DEFAULT NULL,
  `_contact_name` varchar(30) DEFAULT NULL,
  `_status` tinyint(1) DEFAULT NULL COMMENT ' 0-启用；1-停用；2-注销',
  `_address` varchar(200) DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`_id`),
  KEY `FK_Reference_3` (`_privilege_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='beacon 拥有者表';

-- ----------------------------
-- Records of beacon_owners
-- ----------------------------
INSERT INTO `beacon_owners` VALUES ('4', '湖滨街道', '2017-06-05 16:28:41', '001', '13000000000@qq.com', null, '提供湖滨一条街停车服务', '13000000000', 'gavin', '0', '湖滨一条街湖滨一条街湖滨一条街湖滨一条街湖滨一条街');
INSERT INTO `beacon_owners` VALUES ('5', '广益街道', '2017-02-17 16:59:29', null, '13000000001@qq.com', null, '提供广益哥伦布附近停车位', '13000000001', 'gavin1', '0', '广益哥伦布附近');
INSERT INTO `beacon_owners` VALUES ('6', 'b', '2017-06-05 16:28:13', 'b21', 'b@b.com', null, 'b21', '13000000004', 'b', '0', null);

-- ----------------------------
-- Table structure for `beacon_ownership`
-- ----------------------------
DROP TABLE IF EXISTS `beacon_ownership`;
CREATE TABLE `beacon_ownership` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `beacon_id` int(11) DEFAULT NULL,
  `owner_id` int(11) DEFAULT NULL,
  `rssi` double DEFAULT NULL COMMENT '信号强度',
  PRIMARY KEY (`_id`),
  KEY `FK_Reference_6` (`beacon_id`) USING BTREE,
  KEY `FK_Reference_7` (`owner_id`) USING BTREE,
  CONSTRAINT `beacon_ownership_ibfk_1` FOREIGN KEY (`beacon_id`) REFERENCES `beacon_devices` (`_id`),
  CONSTRAINT `beacon_ownership_ibfk_2` FOREIGN KEY (`owner_id`) REFERENCES `beacon_owners` (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of beacon_ownership
-- ----------------------------

-- ----------------------------
-- Table structure for `execute_info`
-- ----------------------------
DROP TABLE IF EXISTS `execute_info`;
CREATE TABLE `execute_info` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `_created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `_status` tinyint(2) DEFAULT '0' COMMENT '状态 0：未发送；1：已发送；2：发送失败',
  `_result` varchar(200) DEFAULT NULL,
  `_notify_record_id` int(11) DEFAULT NULL COMMENT '关联的通知id',
  PRIMARY KEY (`_id`),
  KEY `fk_notify_id` (`_notify_record_id`) USING BTREE,
  CONSTRAINT `execute_info_ibfk_1` FOREIGN KEY (`_notify_record_id`) REFERENCES `notify_record` (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of execute_info
-- ----------------------------

-- ----------------------------
-- Table structure for `generator`
-- ----------------------------
DROP TABLE IF EXISTS `generator`;
CREATE TABLE `generator` (
  `_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of generator
-- ----------------------------

-- ----------------------------
-- Table structure for `h5_tmpl`
-- ----------------------------
DROP TABLE IF EXISTS `h5_tmpl`;
CREATE TABLE `h5_tmpl` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `_title` varchar(50) NOT NULL,
  `_desc` varchar(100) DEFAULT NULL,
  `_thumbnail` varchar(100) DEFAULT NULL,
  `_h5_url` varchar(100) DEFAULT NULL,
  `_created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `_status` tinyint(1) DEFAULT NULL,
  `_word_count` int(11) DEFAULT NULL COMMENT '文本数量',
  `_pic_count` int(11) DEFAULT NULL COMMENT '图片数量',
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of h5_tmpl
-- ----------------------------

-- ----------------------------
-- Table structure for `notify_record`
-- ----------------------------
DROP TABLE IF EXISTS `notify_record`;
CREATE TABLE `notify_record` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `_content` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '发送内容',
  `_title` varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '标题(发送邮件时需要)',
  `_created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `_address` varchar(300) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '发送地址:短信为手机号，邮件为邮箱名',
  `_status` tinyint(2) DEFAULT '0' COMMENT '状态 0：未发送；1：已发送；2：发送失败',
  `_type` int(11) NOT NULL DEFAULT '1' COMMENT '发送类型：1.华为push',
  `_send_time` timestamp NULL DEFAULT NULL COMMENT '发送时间',
  `_expire_time` timestamp NULL DEFAULT NULL COMMENT '过期时间',
  `_open_type` tinyint(2) DEFAULT '1' COMMENT '打开方式 1：url；2：app',
  `_execute_times` tinyint(1) NOT NULL DEFAULT '0' COMMENT '执行次数，默认为最大3次',
  `_open_type_content` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of notify_record
-- ----------------------------

-- ----------------------------
-- Table structure for `parking_lot`
-- ----------------------------
DROP TABLE IF EXISTS `parking_lot`;
CREATE TABLE `parking_lot` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `_name` varchar(50) NOT NULL COMMENT '停车场名称',
  `_merchant_id` int(11) NOT NULL COMMENT '所属商户',
  `_beacon_id` int(11) DEFAULT NULL COMMENT 'beacon id外键',
  `_address` varchar(100) NOT NULL COMMENT '地址',
  `_geocode` varchar(100) NOT NULL COMMENT '经纬度坐标',
  `_capacity` int(11) DEFAULT NULL COMMENT '容量',
  `_available_day_start` tinyint(1) NOT NULL COMMENT '开放日起始天',
  `_available_day_end` tinyint(1) NOT NULL COMMENT '开放日截止天',
  `_toll_day_start` tinyint(1) DEFAULT NULL COMMENT '收费日起始天',
  `_toll_day_end` tinyint(1) DEFAULT NULL COMMENT '收费日截止天',
  `_price` double DEFAULT NULL COMMENT '价格',
  `_price_unit` tinyint(4) DEFAULT NULL COMMENT '价格单位',
  `_created_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `_status` tinyint(1) DEFAULT '1' COMMENT '3.删除',
  `_available_time_end` varchar(6) NOT NULL COMMENT '开放日起始时分',
  `_available_time_start` varchar(6) NOT NULL COMMENT '开放日截止时分',
  `_toll_time_start` varchar(6) NOT NULL COMMENT '收费日起始时分',
  `_toll_time_end` varchar(6) NOT NULL COMMENT '收费日截止时分',
  PRIMARY KEY (`_id`),
  KEY `fk_merchant_id` (`_merchant_id`) USING BTREE,
  KEY `kf_beacon_device_id` (`_beacon_id`) USING BTREE,
  CONSTRAINT `parking_lot_ibfk_1` FOREIGN KEY (`_beacon_id`) REFERENCES `beacon_devices` (`_id`),
  CONSTRAINT `parking_lot_ibfk_2` FOREIGN KEY (`_merchant_id`) REFERENCES `beacon_owners` (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of parking_lot
-- ----------------------------
INSERT INTO `parking_lot` VALUES ('5', '湖滨商业街', '4', '14', '湖滨商业街', '120.284087,31.541942', '50', '1', '7', '6', '7', '5', '2', '2017-02-17 17:01:47', '1', '22:00', '08:00', '08:01', '18:00');
INSERT INTO `parking_lot` VALUES ('6', 'bbb', '4', '17', 'asdf', '120.305456,31.570037', '3', '1', '1', '1', '1', '2', '2', '2017-06-05 16:29:32', '3', '17:00', '15:00', '15:00', '17:00');
INSERT INTO `parking_lot` VALUES ('7', 'bbc', '4', '16', 'bbc', '116.401548,39.919068', '22', '1', '1', '1', '1', '22', '1', '2017-06-05 16:34:43', '1', '17:00', '15:00', '15:00', '17:00');

-- ----------------------------
-- Table structure for `parking_space`
-- ----------------------------
DROP TABLE IF EXISTS `parking_space`;
CREATE TABLE `parking_space` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `_parking_lot_id` int(11) NOT NULL COMMENT '所属停车场',
  `_beacon_id` int(11) NOT NULL COMMENT '所属beacon',
  `_internal_id` varchar(30) DEFAULT NULL COMMENT '停车位内部编号',
  `_created_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `_status` tinyint(1) DEFAULT '1' COMMENT '3.删除',
  PRIMARY KEY (`_id`),
  KEY `fk_parking_lot_id` (`_parking_lot_id`) USING BTREE,
  KEY `fk_beacon_id` (`_beacon_id`) USING BTREE,
  CONSTRAINT `parking_space_ibfk_1` FOREIGN KEY (`_beacon_id`) REFERENCES `beacon_devices` (`_id`),
  CONSTRAINT `parking_space_ibfk_2` FOREIGN KEY (`_parking_lot_id`) REFERENCES `parking_lot` (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of parking_space
-- ----------------------------
INSERT INTO `parking_space` VALUES ('8', '5', '15', '1号车位', '2017-02-17 17:04:49', '0');
INSERT INTO `parking_space` VALUES ('9', '5', '16', '2号车位', '2017-02-17 17:05:02', '0');

-- ----------------------------
-- Table structure for `parking_transactions`
-- ----------------------------
DROP TABLE IF EXISTS `parking_transactions`;
CREATE TABLE `parking_transactions` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `_transaction_id` varchar(60) NOT NULL COMMENT '事务唯一性标识(全局唯一)',
  `_beacon_id` varchar(32) NOT NULL,
  `_car_parking_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '进入车位时间',
  `_car_leaving_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '离开车位时间',
  PRIMARY KEY (`_id`),
  KEY `fk_beacon_id_device` (`_beacon_id`) USING BTREE,
  CONSTRAINT `parking_transactions_ibfk_1` FOREIGN KEY (`_beacon_id`) REFERENCES `beacon_devices` (`_beacon_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of parking_transactions
-- ----------------------------

-- ----------------------------
-- Table structure for `privilege_info`
-- ----------------------------
DROP TABLE IF EXISTS `privilege_info`;
CREATE TABLE `privilege_info` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `_name` varchar(10) DEFAULT NULL,
  `_beacon_uplimit` int(11) DEFAULT NULL,
  `_call_api_uplimit` int(11) DEFAULT NULL,
  `_code` char(6) DEFAULT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of privilege_info
-- ----------------------------

-- ----------------------------
-- Table structure for `sensor_data`
-- ----------------------------
DROP TABLE IF EXISTS `sensor_data`;
CREATE TABLE `sensor_data` (
  `_source_mac_address` varchar(32) NOT NULL,
  `_frame_id` varchar(32) NOT NULL,
  `_time_received` varchar(30) NOT NULL,
  `_sensor_type` varchar(20) NOT NULL,
  `_sensor_value` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sensor_data
-- ----------------------------
INSERT INTO `sensor_data` VALUES ('71b9d5d6-15c2-11e6-8071-00163e07', '223344', '1493400508782', 'humidity', '30.000');
INSERT INTO `sensor_data` VALUES ('71b9d5d6-15c2-11e6-8071-00163e07', '223344', '1493400508782', 'tempurate', '26.396');
INSERT INTO `sensor_data` VALUES ('71b9d5d6-15c2-11e6-8071-00163e07', '223344', '1493400508782', 'battery', '99');

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `_menu_name` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `_isleaf` tinyint(4) DEFAULT NULL COMMENT '是否叶子节点 1-是；0-否',
  `_menu_icon` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `_menu_url` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `_parent_menu` int(11) DEFAULT NULL,
  `_menu_order` int(11) DEFAULT NULL,
  `_created_time` datetime DEFAULT NULL,
  PRIMARY KEY (`_id`),
  KEY `FK_Reference_1` (`_parent_menu`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6003 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='记录系统的菜单信息';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('100', '系统管理', '0', 'icon-filter', null, null, '1', '2016-11-28 23:49:14');
INSERT INTO `sys_menu` VALUES ('200', '商户信息', '0', 'icon-filter', null, null, '2', '2016-11-28 23:49:14');
INSERT INTO `sys_menu` VALUES ('300', 'beacon管理', '0', 'icon-filter', null, null, '3', '2016-11-28 23:49:14');
INSERT INTO `sys_menu` VALUES ('400', '商户管理', '0', 'icon-filter', null, null, '4', '2016-11-28 23:49:14');
INSERT INTO `sys_menu` VALUES ('500', '商户权限管理', '0', 'icon-filter', null, null, '5', '2016-11-28 23:49:14');
INSERT INTO `sys_menu` VALUES ('600', '停车管理', '0', 'icon-filter', null, null, '7', '2016-11-28 23:49:14');
INSERT INTO `sys_menu` VALUES ('1001', '角色管理', '1', 'icon-filter', 'role.jsp', '100', '1', '2016-11-28 23:49:14');
INSERT INTO `sys_menu` VALUES ('1002', '配置项管理', '1', 'icon-filter', 'config.jsp', '100', '2', '2016-11-28 23:49:14');
INSERT INTO `sys_menu` VALUES ('1004', 'beacon分类管理', '1', 'icon-filter', 'beaconCategory.jsp', '100', '4', '2016-11-28 23:49:14');
INSERT INTO `sys_menu` VALUES ('1005', 'h5模板管理', '1', 'icon-filter', 'h5Tmpl.jsp', '100', '5', '2016-11-28 23:49:14');
INSERT INTO `sys_menu` VALUES ('1006', '消息管理', '1', 'icon-filter', 'pushService.jsp', '100', '6', '2016-11-28 23:49:14');
INSERT INTO `sys_menu` VALUES ('2001', '商户信息', '1', 'icon-filter', 'merchantInfo.jsp', '200', '1', '2016-11-28 23:49:14');
INSERT INTO `sys_menu` VALUES ('3001', 'beacon设备管理', '1', 'icon-filter', 'beacon.jsp', '300', '1', '2016-11-28 23:49:14');
INSERT INTO `sys_menu` VALUES ('3002', 'beacon所有权管理 ', '1', 'icon-filter', 'beaconOwnership.jsp', '300', '2', '2016-11-28 23:49:14');
INSERT INTO `sys_menu` VALUES ('4001', '商户管理', '1', 'icon-filter', 'merchant.jsp', '400', '1', '2016-11-28 23:49:14');
INSERT INTO `sys_menu` VALUES ('5001', '商户权限管理', '1', 'icon-filter', 'merchantPrivilege.jsp', '500', '1', '2016-11-28 23:49:14');
INSERT INTO `sys_menu` VALUES ('6001', '停车场管理', '1', 'icon-filter', 'parkingLot.jsp', '600', '1', '2016-11-28 23:49:14');
INSERT INTO `sys_menu` VALUES ('6002', '车位管理', '1', 'icon-filter', 'parkingSpace.jsp', '600', '2', '2016-11-28 23:49:14');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `_role_name` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `_role_code` varchar(8) COLLATE utf8_bin DEFAULT NULL,
  `_defaulted` tinyint(4) DEFAULT NULL COMMENT '0-否；1-是；',
  `_created_time` datetime DEFAULT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10009 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='记录系统的角色信息';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('10001', '超级管理员', '10001', '1', '2015-10-12 17:21:32');
INSERT INTO `sys_role` VALUES ('10007', '商户管理员角色', '10002', '1', '2016-08-17 10:50:11');
INSERT INTO `sys_role` VALUES ('10008', '运营管理员角色', '10003', '1', '2016-12-06 17:37:46');

-- ----------------------------
-- Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `_role_id` int(11) DEFAULT NULL,
  `_menu_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=556 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('497', '10001', '300');
INSERT INTO `sys_role_menu` VALUES ('498', '10001', '400');
INSERT INTO `sys_role_menu` VALUES ('500', '10001', '1001');
INSERT INTO `sys_role_menu` VALUES ('501', '10001', '1002');
INSERT INTO `sys_role_menu` VALUES ('518', '10001', '2001');
INSERT INTO `sys_role_menu` VALUES ('520', '10001', '3002');
INSERT INTO `sys_role_menu` VALUES ('521', '10001', '4001');
INSERT INTO `sys_role_menu` VALUES ('522', '10001', '5001');
INSERT INTO `sys_role_menu` VALUES ('537', '10001', '1004');
INSERT INTO `sys_role_menu` VALUES ('538', '10008', '100');
INSERT INTO `sys_role_menu` VALUES ('539', '10008', '300');
INSERT INTO `sys_role_menu` VALUES ('540', '10008', '1004');
INSERT INTO `sys_role_menu` VALUES ('541', '10008', '3002');
INSERT INTO `sys_role_menu` VALUES ('542', '10008', '400');
INSERT INTO `sys_role_menu` VALUES ('543', '10008', '4001');
INSERT INTO `sys_role_menu` VALUES ('544', '10007', '300');
INSERT INTO `sys_role_menu` VALUES ('545', '10007', '200');
INSERT INTO `sys_role_menu` VALUES ('546', '10007', '2001');
INSERT INTO `sys_role_menu` VALUES ('547', '10007', '3001');
INSERT INTO `sys_role_menu` VALUES ('548', '10008', '1005');
INSERT INTO `sys_role_menu` VALUES ('549', '10008', '1006');
INSERT INTO `sys_role_menu` VALUES ('550', '10008', '600');
INSERT INTO `sys_role_menu` VALUES ('551', '10008', '6001');
INSERT INTO `sys_role_menu` VALUES ('552', '10008', '6002');
INSERT INTO `sys_role_menu` VALUES ('553', '10007', '600');
INSERT INTO `sys_role_menu` VALUES ('554', '10007', '6001');
INSERT INTO `sys_role_menu` VALUES ('555', '10007', '6002');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `_login_name` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `_password` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `_work_number` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `_name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `_email` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `_mobile_phone` varchar(11) COLLATE utf8_bin DEFAULT NULL,
  `_status` int(11) DEFAULT NULL COMMENT '0-启用；1-停用；2-注销',
  `_defaulted` tinyint(4) DEFAULT NULL COMMENT '0-否；1-是；',
  `_created_time` datetime DEFAULT NULL,
  `_deleted_time` datetime DEFAULT NULL,
  `_role_id` int(11) DEFAULT NULL COMMENT '所属角色',
  `_beacon_owner_id` int(11) DEFAULT NULL COMMENT '所属beacon拥有者',
  PRIMARY KEY (`_id`),
  KEY `fk_beacon_owner_id` (`_beacon_owner_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20085 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='记录系统管理员信息';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('20013', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '00001', 'admin', 'admin@sina.com', '13000000001', '0', '0', '2016-12-06 17:10:01', null, '10001', null);
INSERT INTO `sys_user` VALUES ('20078', 'gavin', 'e10adc3949ba59abbe56e057f20f883e', null, 'gavin', 'gavin@sina.com', '13000000022', '0', '0', '2016-12-06 17:10:01', null, '10001', null);
INSERT INTO `sys_user` VALUES ('20082', 'a1', 'e10adc3949ba59abbe56e057f20f883e', null, 'a1', null, null, '0', null, '2017-02-17 16:57:54', null, '10007', '4');
INSERT INTO `sys_user` VALUES ('20083', 'a2', 'e10adc3949ba59abbe56e057f20f883e', null, 'a2', null, null, '0', null, '2017-02-17 16:59:29', null, '10007', '5');
INSERT INTO `sys_user` VALUES ('20084', 'b', 'e10adc3949ba59abbe56e057f20f883e', null, 'b', null, null, '0', null, '2017-06-05 16:28:07', null, '10007', '6');

-- ----------------------------
-- Table structure for `unique_ap_beacon_frames`
-- ----------------------------
DROP TABLE IF EXISTS `unique_ap_beacon_frames`;
CREATE TABLE `unique_ap_beacon_frames` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `_source_mac_address` varchar(32) NOT NULL,
  `_ap_mac_address` varchar(32) NOT NULL,
  `_frame_id` varchar(32) NOT NULL,
  `_time_received` varchar(30) NOT NULL,
  `_rssi_at_receiving` varchar(10) NOT NULL,
  `_payload` varchar(100) NOT NULL,
  PRIMARY KEY (`_id`),
  KEY `idx_time_received` (`_time_received`) USING BTREE,
  KEY `idx_source_ap_frame` (`_source_mac_address`,`_ap_mac_address`,`_frame_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=174 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of unique_ap_beacon_frames
-- ----------------------------
INSERT INTO `unique_ap_beacon_frames` VALUES ('173', '71b9d5d6-15c2-11e6-8071-00163e07', '112233445566', '223344', '1493400508782', '-70', '30.000|26.396|99');
