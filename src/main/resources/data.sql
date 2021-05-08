CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(64) NOT NULL,
  `enabled` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
);
INSERT INTO `users` (`username`, `password`, `enabled`) VALUES ('om', '$2a$10$G7nFusgNsDTMkbSNBl9h7eSXbq01I6BiFXRh1RM7O0JaI2lP78ck.', '1');
INSERT INTO `users` (`username`, `password`, `enabled`) VALUES ('hari', '$2a$10$G7nFusgNsDTMkbSNBl9h7eSXbq01I6BiFXRh1RM7O0JaI2lP78ck.', '1');


# 
CREATE TABLE `roles` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`role_id`)
);
INSERT INTO `roles` (`name`) VALUES ('USER');
INSERT INTO `roles` (`name`) VALUES ('ADMIN');



CREATE TABLE `users_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  KEY `user_fk_idx` (`user_id`),
  KEY `role_fk_idx` (`role_id`),
  CONSTRAINT `role_fk` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`),
  CONSTRAINT `user_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);
INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES (1, 2);
INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES (2, 1);