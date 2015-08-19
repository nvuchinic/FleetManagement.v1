# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table admin (
  id                        integer primary key AUTOINCREMENT,
  name                      varchar(255),
  surname                   varchar(255),
  email                     varchar(255),
  adress                    varchar(255),
  city                      varchar(255),
  is_admin                  integer(1),
  is_manager                integer(1),
  password                  varchar(255))
;

create table description (
  id                        integer primary key AUTOINCREMENT,
  typev_id                  integer,
  chassis                   varchar(255),
  engine_number             varchar(255),
  c_cm                      varchar(255),
  vehicle_brand             varchar(255),
  model                     varchar(255),
  color                     varchar(255),
  shape                     varchar(255),
  fuel                      varchar(255),
  tankage                   varchar(255),
  current_mileage           varchar(255),
  production_date           varchar(255),
  production_state          varchar(255),
  constraint uq_description_typev_id unique (typev_id))
;

create table driver (
  id                        integer primary key AUTOINCREMENT,
  name                      varchar(255),
  surname                   varchar(255),
  phone_number              varchar(255) not null,
  adress                    varchar(255),
  description               varchar(255) not null,
  gender                    varchar(255) not null,
  dob                       timestamp,
  created                   timestamp)
;

create table employee (
  id                        integer primary key AUTOINCREMENT,
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
  profile_picture           varchar(255))
;

create table fleet (
  id                        integer primary key AUTOINCREMENT,
  name                      varchar(255) not null,
  arrival                   timestamp,
  departure                 timestamp,
  pickup_place              varchar(255),
  return_place              varchar(255),
  num_of_vehicles           integer)
;

create table manager (
  id                        integer primary key AUTOINCREMENT,
  name                      varchar(255),
  surname                   varchar(255),
  email                     varchar(255),
  adress                    varchar(255),
  city                      varchar(255),
  is_manager                integer(1),
  is_admin                  integer(1),
  password                  varchar(255))
;

create table owner (
  id                        integer primary key AUTOINCREMENT,
  name                      varchar(255),
  email                     varchar(255))
;

create table reset_password (
  id                        varchar(255) primary key,
  user_email                varchar(255),
  date                      timestamp)
;

create table train (
  id                        integer primary key AUTOINCREMENT,
  num_of_wagons             integer)
;

create table truck (
  id                        integer primary key AUTOINCREMENT,
  make                      varchar(255),
  model                     varchar(255),
  year                      varchar(255),
  num_of_containers         integer,
  status                    varchar(255))
;

create table type (
  id                        integer primary key AUTOINCREMENT,
  name                      varchar(255),
  description_id            integer,
  constraint uq_type_description_id unique (description_id))
;

create table vehicle (
  id                        integer primary key AUTOINCREMENT,
  vid                       varchar(255),
  owner_id                  integer,
  fleet_id                  integer,
  typev_id                  integer)
;

create table vendor (
  id                        integer primary key AUTOINCREMENT,
  name                      varchar(255),
  address                   varchar(255),
  city                      varchar(255),
  country                   varchar(255),
  phone                     varchar(255),
  email                     varchar(255))
;

alter table description add constraint fk_description_typev_1 foreign key (typev_id) references type (id);
create index ix_description_typev_1 on description (typev_id);
alter table type add constraint fk_type_description_2 foreign key (description_id) references description (id);
create index ix_type_description_2 on type (description_id);
alter table vehicle add constraint fk_vehicle_owner_3 foreign key (owner_id) references owner (id);
create index ix_vehicle_owner_3 on vehicle (owner_id);
alter table vehicle add constraint fk_vehicle_fleet_4 foreign key (fleet_id) references fleet (id);
create index ix_vehicle_fleet_4 on vehicle (fleet_id);
alter table vehicle add constraint fk_vehicle_typev_5 foreign key (typev_id) references type (id);
create index ix_vehicle_typev_5 on vehicle (typev_id);



# --- !Downs

PRAGMA foreign_keys = OFF;

drop table admin;

drop table description;

drop table driver;

drop table employee;

drop table fleet;

drop table manager;

drop table owner;

drop table reset_password;

drop table train;

drop table truck;

drop table type;

drop table vehicle;

drop table vendor;

PRAGMA foreign_keys = ON;

