create database bank;
use bank;

crate user 'spring'@'%' IDENTIFIED BY 'spring_password';
grant all access to 'spring' on 'bank'