DROP DATABASE IF EXISTS chatApplication;
CREATE DATABASE IF NOT EXISTS chatApplication;
USE chatApplication;
CREATE TABLE users(
    userName varchar(30),
    password varchar(30),
    profileImage varchar(250)
);