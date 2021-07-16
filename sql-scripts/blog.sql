create database blog;
use blog;

create table blog (
    id int auto_increment primary key,
    title varchar(50) not null,
    text text not null,
    date date not null,
);

create table user (
    username varchar(75) not null primary key,
    password text not null,
    id text not null,
    last datetime not null,
);

create user 'golang'@'%' identified by 'golang_password';
grant all access to 'golang' on 'blog';