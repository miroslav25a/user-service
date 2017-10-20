CREATE TABLE `user` (
  `id` bigint(100) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

insert into test.user (id, name, dob) values (1, 'john smith', '1981-08-21');
