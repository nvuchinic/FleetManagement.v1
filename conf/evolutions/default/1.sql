# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table admin (
  id                        bigint not null,
  name                      varchar(255),
  surname                   varchar(255),
  email                     varchar(255),
  adress                    varchar(255),
  city                      varchar(255),
  is_admin                  boolean,
  is_manager                boolean,
  password                  varchar(255),
  constraint pk_admin primary key (id))
;

create table employee (
  id                        bigint not null,
  name                      varchar(255),
  surname                   varchar(255),
  email                     varchar(255),
  adress                    varchar(255),
  city                      varchar(255),
  dob                       timestamp,
  gender                    varchar(255) not null,
  created                   timestamp,
  updated                   timestamp,
  status                    varchar(255) not null,
  profile_picture           varchar(255),
  constraint pk_employee primary key (id))
;

create table manager (
  id                        bigint not null,
  name                      varchar(255),
  surname                   varchar(255),
  email                     varchar(255),
  adress                    varchar(255),
  city                      varchar(255),
  is_manager                boolean,
  is_admin                  boolean,
  password                  varchar(255),
  constraint pk_manager primary key (id))
;

create table reset_password (
  id                        varchar(255) not null,
  user_email                varchar(255),
  date                      timestamp,
  constraint pk_reset_password primary key (id))
;

create table train (
  id                        integer not null,
  license_no                varchar(255),
  longitude                 bigint,
  latitude                  bigint,
  num_of_wagons             integer,
  constraint pk_train primary key (id))
;

create table truck (
  id                        integer not null,
  license_no                varchar(255),
  longitude                 bigint,
  latitude                  bigint,
  make                      varchar(255),
  model                     varchar(255),
  year                      varchar(255),
  num_of_containers         integer,
  status                    varchar(255),
  constraint pk_truck primary key (id))
;

<<<<<<< HEAD
create table vendor (
  id                        integer not null,
  name                      varchar(255),
  address                   varchar(255),
  city                      varchar(255),
  country                   varchar(255),
  phone                     varchar(255),
  email                     varchar(255),
  constraint pk_vendor primary key (id))
;
=======
create sequence admin_seq;

create sequence employee_seq;

create sequence manager_seq;

create sequence reset_password_seq;
>>>>>>> 970d256092e4d5af5c3895cc6590c37d99a54226

create sequence train_seq;

create sequence truck_seq;

create sequence vendor_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists admin;

drop table if exists employee;

drop table if exists manager;

drop table if exists reset_password;

drop table if exists train;

drop table if exists truck;

drop table if exists vendor;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists admin_seq;

drop sequence if exists employee_seq;

drop sequence if exists manager_seq;

drop sequence if exists reset_password_seq;

drop sequence if exists train_seq;

drop sequence if exists truck_seq;

drop sequence if exists vendor_seq;

