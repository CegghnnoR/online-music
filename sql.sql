CREATE DATABASE `onlinemusic` CHARACTER SET utf8;

CREATE TABLE `user` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `username` varchar(20) NOT NULL,
    `password` VARCHAR(255) NOT NULL
);

CREATE TABLE `music` (
     `id` INT PRIMARY KEY AUTO_INCREMENT,
     `title` VARCHAR(50) NOT NULL,
     `singer` VARCHAR(30) NOT NULL,
     `time` VARCHAR(13) NOT NULL,
     `url` VARCHAR(1000) NOT NULL,
     `userid` int(11) NOT NULL
);

CREATE TABLE `lovemusic` (
     `id` INT PRIMARY KEY AUTO_INCREMENT,
     `user_id` INT(11) NOT NULL,
     `music_id` INT(11) NOT NULL
);