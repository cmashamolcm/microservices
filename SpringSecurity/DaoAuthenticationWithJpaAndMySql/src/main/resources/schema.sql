  drop database if exists db_1;
  create database db_1;
  use db_1;
  drop table if exists jpa_users;
  drop table if exists jpa_authorities;

  create table jpa_users(
      username varchar(50) not null primary key,
      password varchar(50) not null,
      enabled BOOL not null);

  create table jpa_authorities (
      rolenumber int not null primary key AUTO_INCREMENT,
      username varchar(50) not null,
      authority varchar(50) not null,
      constraint fk_authorities_users foreign key(username) references jpa_users(username));