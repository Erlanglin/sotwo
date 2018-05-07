drop database if exists sotwo;
drop user if exists 'root'@'localhost';
create database sotwo;
use sotwo;
create user 'root'@'localhost' identified by '123456';
grant all privileges on root.* to 'root'@'localhost';
flush privileges;