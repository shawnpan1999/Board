DROP TABLE IF EXISTS `login_ticket`;
CREATE TABLE `login_ticket` (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `user_id` int(11) NOT NULL,
                                `ticket` varchar(45) COLLATE utf8_bin NOT NULL,
                                `expired` datetime NOT NULL,
                                `status` int(11) DEFAULT '0',
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `ticket_UNIQUE` (`ticket`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `login_ticket` */

insert  into `login_ticket`(`id`,`user_id`,`ticket`,`expired`,`status`) values (1,1,'TICKET1','2020-03-08 23:21:14',2),(2,2,'TICKET2','2020-03-09 04:21:14',2),(3,3,'TICKET3','2020-03-09 09:21:14',2),(4,4,'TICKET4','2020-03-09 14:21:14',2),(5,5,'TICKET5','2020-03-09 19:21:14',2),(6,6,'TICKET6','2020-03-10 00:21:14',2),(7,7,'TICKET7','2020-03-10 05:21:14',2),(8,8,'TICKET8','2020-03-10 10:21:14',2),(9,9,'TICKET9','2020-03-10 15:21:14',2),(10,10,'TICKET10','2020-03-10 20:21:15',2),(11,11,'TICKET11','2020-03-11 01:21:15',2),(12,12,'ccccca1b4b8d417480d68a1b438641bd','2020-03-09 23:48:40',0),(13,13,'b2f0926564a841118ba5d7bce0fba734','2020-03-10 10:21:41',0),(14,13,'417ec8e23c9f489fb4b38f9f7793f9a6','2020-03-10 10:21:54',0),(15,13,'a3e3cbadebfa4cf0b6c797c21a75e8a3','2020-03-10 10:53:45',0),(16,13,'07c1670ffef04e1fb2497f2608e7dbd7','2020-03-10 10:53:48',0),(17,13,'40b2b39539d64988a138088a9fc0a3b0','2020-03-10 10:59:16',0),(18,14,'068c572bfa514aa48ef070223e5e45ef','2020-03-10 10:59:22',1),(19,13,'e5a47ce2f9004f75b0d72e75c3eb9989','2020-03-10 10:59:26',0),(20,13,'de148f0f2dd444299132f7609fee84e8','2020-03-10 11:25:35',0),(21,13,'0c01555a5ec346688980e6a919505611','2020-03-10 11:29:15',0),(22,12,'48c920236a9842d0a2d37bb2b1a5148c','2020-03-10 11:29:21',0),(23,13,'5d42ad4c59154f61a7405a35dc5dab38','2020-03-10 11:29:38',0),(24,12,'ff7c19d967ba43008d7b271bacd43d0b','2020-03-10 11:32:20',0),(25,12,'2a960a66b1ae4a5fadbe885d46fe943a','2020-03-10 11:33:14',0),(26,12,'00cdba764edc40ffbac339fcba0906bd','2020-03-10 11:35:12',1),(27,13,'f27c8913bd0d45659d5d4d7ae3700718','2020-03-10 11:35:27',0),(28,13,'2d0b7b4d0fff4ffabea5621ee9896c85','2020-03-10 17:47:08',0),(29,13,'72c539ae293c4b509c75d87334232baa','2020-03-10 17:56:39',1),(30,13,'d8945d239adf48dabb5e31f0384e5d97','2020-03-10 17:57:23',1),(31,13,'a204a619f988446e8c64933e725c2c29','2020-03-10 18:17:52',0),(32,13,'c975cafbf3b54d2fb398e5fc05488220','2020-03-10 20:48:23',0),(33,13,'1691758fc2654b4c8d3c98d8a9fff60d','2020-03-10 22:41:23',0),(34,13,'9d71e63eba5e41359c9beac7486b3de7','2020-03-10 23:04:40',0),(35,13,'d7e8228374604e9db3eb23aa5e90aae7','2020-03-11 14:05:50',0),(36,13,'80408e46a7814227a8eac27879f82453','2020-03-11 14:16:19',0),(37,13,'c01c56cb818f4b8791174a7f632eb569','2020-03-11 14:41:55',0),(38,13,'3420376de04d476481c34b3fb59a0c6f','2020-03-11 14:51:44',0),(39,13,'69fbcc379a4e404fa61b1d71745da432','2020-03-11 14:55:44',0),(40,13,'6f54d1281cb6480ca4e7f260a4e51a88','2020-03-11 14:57:35',0),(41,13,'fdf1ddf520874ee5990ea7c50a3fa8d9','2020-03-11 15:01:24',1),(42,13,'ea4c606249184b9385c2ab50bb2cdf99','2020-03-11 16:13:05',1),(43,13,'dd95edd04c4346b99aa0d002859e6031','2020-03-11 16:13:11',1),(44,13,'d290429c85ee423aa5c5e821643868f2','2020-03-11 16:13:53',1),(45,13,'3204b523391e42a9990bf94e403d649b','2020-03-11 16:14:26',0),(46,13,'ea311f55e3af420385d89a3da26fe0e1','2020-03-11 16:16:33',0),(47,13,'abc9068cc42d40e195621267997ce44c','2020-03-11 16:17:34',1),(48,13,'0a051cd6f6ce4303b5adedf9b5741cce','2020-03-11 16:19:24',1),(49,13,'90adfec1dced47ac8587b8bbd7613ca2','2020-03-11 16:22:00',1),(50,13,'fcedaab8b7464cbbaaf5c499c9d10d59','2020-03-11 16:35:58',1),(51,13,'3ba0c307200445e7a505722d172461d3','2020-03-11 16:37:21',0),(52,13,'0bd9e39e84ec4338b75bf0872adcd48b','2020-03-11 21:45:03',1),(53,14,'89f6e51a46f8460fb9e9cdcdd961cdd1','2020-03-11 21:45:12',0),(54,13,'8e7fee53d4c8462e841f95849ca5fceb','2020-03-11 21:51:16',1),(55,15,'a4c1ed603f2a495fa4a138ccbcdb181e','2020-03-11 23:24:54',1),(56,13,'ac3cfc962c84482ba61dd001fd522c03','2020-03-11 23:27:04',0),(57,13,'79e5bd910cf5428092408c500ef6848d','2020-03-12 08:19:32',0),(58,13,'edbe820bcdbc437b820f07f8b3068bb5','2020-03-12 10:33:14',1);

/*Table structure for table `blog` */

DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
                           `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                           `author_id` int(11) NOT NULL,
                           `created_date` datetime DEFAULT NULL,
                           `text` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
                           `like_count` int NOT NULL DEFAULT 0,
                           `comment_count` int NOT NULL DEFAULT 0,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `blog` */

insert  into `blog`(`id`,`author_id`,`created_date`,`text`) values (9,14,'2020-03-10 15:01:34','第一条留言~'),(10,13,'2020-03-10 16:19:29','test'),(13,13,'2020-03-11 10:38:16','第三条留言！');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                        `name` varchar(64) NOT NULL DEFAULT '',
                        `password` varchar(128) NOT NULL DEFAULT '',
                        `salt` varchar(32) NOT NULL DEFAULT '',
                        `head_url` varchar(256) NOT NULL DEFAULT '',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`password`,`salt`,`head_url`) values (13,'admin','9D1813AAFF5C190E62A163558E74AA8A','c343d','http://www.psw.life/imgCloud/img/avatar.jpg'),(14,'admin2','3C81E070AB852334A04E70BCC8561E5A','65f5d','http://psw.life/imgCloud/img/defaultHead.png');

DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
                           `id` INT NOT NULL AUTO_INCREMENT,
                           `msg_type` INT(1) NOT NULL,
                           `from_id` INT NULL,
                           `to_id` INT NULL,
                           `content` TEXT NULL,
                           `created_date` DATETIME NULL,
                           `has_read` INT NULL,
                           `conversation_id` VARCHAR(45) NOT NULL,
                           PRIMARY KEY (`id`),
                           INDEX `conversation_index` (`conversation_id` ASC),
                           INDEX `created_date` (`created_date` ASC)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;