drop database if exists sotwo;
drop user if exists 'sotwo'@'localhost';
create database sotwo;
use sotwo;
create user 'sotwo'@'localhost' identified by 'sotwo123456';
grant all privileges on sotwo.* to 'sotwo'@'localhost';
flush privileges;