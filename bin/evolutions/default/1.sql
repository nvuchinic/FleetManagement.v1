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

create table data (
  id                        bigint not null,
  timestamp                 timestamp,
  name                      varchar(255),
  constraint pk_data primary key (id))
;

create table driver (
  id                        bigint not null,
  name                      varchar(255),
  surname                   varchar(255),
  phone_number              varchar(255) not null,
  adress                    varchar(255),
  description               varchar(255) not null,
  gender                    varchar(255) not null,
  dob                       timestamp,
  created                   timestamp,
  constraint pk_driver primary key (id))
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

create table fleet (
  id                        bigint not null,
  name                      varchar(255),
  num_of_vehicles           bigint,
  constraint pk_fleet primary key (id))
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
  id                        bigint not null,
  num_of_wagons             integer,
  constraint pk_train primary key (id))
;

create table truck (
  id                        bigint not null,
  make                      varchar(255),
  model                     varchar(255),
  year                      varchar(255),
  num_of_containers         integer,
  status                    varchar(255),
  constraint pk_truck primary key (id))
;

create table type (
  id                        bigint not null,
  name                      varchar(255),
  description               varchar(255),
  constraint pk_type primary key (id))
;

create table vehicle (
  id                        bigint not null,
  vid                       varchar(255),
  owner_id                  bigint,
  description               varchar(255),
  data_id                   bigint,
  fleet_id                  bigint,
  type_id                   bigint,
  constraint uq_vehicle_data_id unique (data_id),
  constraint pk_vehicle primary key (id))
;

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

create sequence admin_seq;

create sequence data_seq;

create sequence driver_seq;

create sequence employee_seq;

create sequence fleet_seq;

create sequence manager_seq;

create sequence reset_password_seq;

create sequence train_seq;

create sequence truck_seq;

create sequence type_seq;

create sequence vehicle_seq;

create sequence vendor_seq;

alter table vehicle add constraint fk_vehicle_owner_1 foreign key (owner_id) references driver (id) on delete restrict on update restrict;
create index ix_vehicle_owner_1 on vehicle (owner_id);
alter table vehicle add constraint fk_vehicle_data_2 foreign key (data_id) references data (id) on delete restrict on update restrict;
create index ix_vehicle_data_2 on vehicle (data_id);
alter table vehicle add constraint fk_vehicle_fleet_3 foreign key (fleet_id) references fleet (id) on delete restrict on update restrict;
create index ix_vehicle_fleet_3 on vehicle (fleet_id);
alter table vehicle add constraint fk_vehicle_type_4 foreign key (type_id) references type (id) on delete restrict on update restrict;
create index ix_vehicle_type_4 on vehicle (type_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists admin;

drop table if exists data;

drop table if exists driver;

drop table if exists employee;

drop table if exists fleet;

drop table if exists manager;

drop table if exists reset_password;

drop table if exists train;

drop table if exists truck;

drop table if exists type;

drop table if exists vehicle;

drop table if exists vendor;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists admin_seq;

drop sequence if exists data_seq;

drop sequence if exists driver_seq;

drop sequence if exists employee_seq;

drop sequence if exists fleet_seq;

drop sequence if exists manager_seq;

drop sequence if exists reset_password_seq;

drop sequence if exists train_seq;

drop sequence if exists truck_seq;

drop sequence if exists type_seq;

drop sequence if exists vehicle_seq;

drop sequence if exists vendor_seq;

