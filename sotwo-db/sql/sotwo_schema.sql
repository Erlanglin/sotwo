drop database if exists sotwo;
drop user if exists 'root'@'47.106.122.29';
create database sotwo;
use sotwo;
create user 'root'@'47.106.122.29' identified by '123456';
grant all privileges on root.* to 'root'@'47.106.122.29';
flush privileges;