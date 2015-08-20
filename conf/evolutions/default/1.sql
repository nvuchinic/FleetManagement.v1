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
  production_state          varchar(255))
;

create table driver (
  id                        integer primary key AUTOINCREMENT,
  first_name                varchar(255),
  last_name                 varchar(255),
  driver_name               varchar(255),
  phone_number              varchar(255) not null,
  adress                    varchar(255),
  description               varchar(255) not null,
  gender                    varchar(255) not null,
  dob                       timestamp,
  created                   timestamp,
  travel_order_id           integer,
  engaged                   integer(1),
  constraint uq_driver_travel_order_id unique (travel_order_id))
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

create table insurance (
  id                        integer primary key AUTOINCREMENT,
  contract_no               varchar(255),
  vehicle_id                integer,
  itype                     varchar(255),
  cost                      double,
  constraint uq_insurance_vehicle_id unique (vehicle_id))
;

create table maintenance (
  id                        integer primary key AUTOINCREMENT,
  vehicle_id                integer,
  service_type              varchar(255),
  m_date                    timestamp)
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

create table service (
  id                        integer primary key AUTOINCREMENT,
  stype                     varchar(255),
  description               varchar(255),
  maintenance_id            integer)
;

create table train (
  id                        integer primary key AUTOINCREMENT,
  size                      integer)
;

create table travel_order (
  id                        integer primary key AUTOINCREMENT,
  number_to                 varchar(255),
  driver_id                 integer,
  driver_name               varchar(255),
  vehicle_id                integer,
  vehicle_name              varchar(255),
  destination               varchar(255),
  start_date                timestamp,
  return_date               timestamp,
  constraint uq_travel_order_driver_id unique (driver_id),
  constraint uq_travel_order_vehicle_id unique (vehicle_id))
;

create table truckC (
  id                        integer primary key AUTOINCREMENT,
  size                      integer)
;

create table type (
  id                        integer primary key AUTOINCREMENT,
  name                      varchar(255),
  description_id            integer)
;

create table vehicle (
  id                        integer primary key AUTOINCREMENT,
  vid                       varchar(255),
  name                      varchar(255),
  owner_id                  integer,
  fleet_id                  integer,
  typev_id                  integer,
  travel_order_id           integer,
  prev_id                   integer,
  next_id                   integer,
  engaged                   integer(1),
  status                    varchar(255),
  is_registered             integer(1),
  is_insured                integer(1),
  is_asigned                integer(1),
  v_registration_id         integer,
  constraint uq_vehicle_travel_order_id unique (travel_order_id),
  constraint uq_vehicle_prev_id unique (prev_id),
  constraint uq_vehicle_next_id unique (next_id),
  constraint uq_vehicle_v_registration_id unique (v_registration_id))
;

create table vehicle_registration (
  id                        integer primary key AUTOINCREMENT,
  reg_no                    varchar(255),
  vehicle_id                integer,
  expiration_date           timestamp,
  constraint uq_vehicle_registration_vehicle_ unique (vehicle_id))
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

alter table driver add constraint fk_driver_travelOrder_1 foreign key (travel_order_id) references travel_order (id);
create index ix_driver_travelOrder_1 on driver (travel_order_id);
alter table insurance add constraint fk_insurance_vehicle_2 foreign key (vehicle_id) references vehicle (id);
create index ix_insurance_vehicle_2 on insurance (vehicle_id);
alter table maintenance add constraint fk_maintenance_vehicle_3 foreign key (vehicle_id) references vehicle (id);
create index ix_maintenance_vehicle_3 on maintenance (vehicle_id);
alter table service add constraint fk_service_maintenance_4 foreign key (maintenance_id) references maintenance (id);
create index ix_service_maintenance_4 on service (maintenance_id);
alter table travel_order add constraint fk_travel_order_driver_5 foreign key (driver_id) references driver (id);
create index ix_travel_order_driver_5 on travel_order (driver_id);
alter table travel_order add constraint fk_travel_order_vehicle_6 foreign key (vehicle_id) references vehicle (id);
create index ix_travel_order_vehicle_6 on travel_order (vehicle_id);
alter table type add constraint fk_type_description_7 foreign key (description_id) references description (id);
create index ix_type_description_7 on type (description_id);
alter table vehicle add constraint fk_vehicle_owner_8 foreign key (owner_id) references owner (id);
create index ix_vehicle_owner_8 on vehicle (owner_id);
alter table vehicle add constraint fk_vehicle_fleet_9 foreign key (fleet_id) references fleet (id);
create index ix_vehicle_fleet_9 on vehicle (fleet_id);
alter table vehicle add constraint fk_vehicle_typev_10 foreign key (typev_id) references type (id);
create index ix_vehicle_typev_10 on vehicle (typev_id);
alter table vehicle add constraint fk_vehicle_travelOrder_11 foreign key (travel_order_id) references travel_order (id);
create index ix_vehicle_travelOrder_11 on vehicle (travel_order_id);
alter table vehicle add constraint fk_vehicle_prev_12 foreign key (prev_id) references vehicle (id);
create index ix_vehicle_prev_12 on vehicle (prev_id);
alter table vehicle add constraint fk_vehicle_next_13 foreign key (next_id) references vehicle (id);
create index ix_vehicle_next_13 on vehicle (next_id);
alter table vehicle add constraint fk_vehicle_vRegistration_14 foreign key (v_registration_id) references vehicle_registration (id);
create index ix_vehicle_vRegistration_14 on vehicle (v_registration_id);
alter table vehicle_registration add constraint fk_vehicle_registration_vehic_15 foreign key (vehicle_id) references vehicle (id);
create index ix_vehicle_registration_vehic_15 on vehicle_registration (vehicle_id);



# --- !Downs

PRAGMA foreign_keys = OFF;

drop table admin;

drop table description;

drop table driver;

drop table employee;

drop table fleet;

drop table insurance;

drop table maintenance;

drop table manager;

drop table owner;

drop table reset_password;

drop table service;

drop table train;

drop table travel_order;

drop table truckC;

drop table type;

drop table vehicle;

drop table vehicle_registration;

drop table vendor;

PRAGMA foreign_keys = ON;

