# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table admin (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  surname                   varchar(255),
  email                     varchar(255),
  adress                    varchar(255),
  city                      varchar(255),
  is_admin                  tinyint(1) default 0,
  is_manager                tinyint(1) default 0,
  password                  varchar(255),
  constraint pk_admin primary key (id))
;

create table driver (
  id                        bigint auto_increment not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  driver_name               varchar(255),
  phone_number              varchar(255),
  adress                    varchar(255),
  description               varchar(255),
  travel_orderr_id          bigint,
  engagedd                  tinyint(1) default 0,
  constraint uq_driver_travel_orderr_id unique (travel_orderr_id),
  constraint pk_driver primary key (id))
;

create table employee (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  surname                   varchar(255),
  email                     varchar(255),
  adress                    varchar(255),
  city                      varchar(255),
  status                    varchar(255),
  profile_picture           varchar(255),
  constraint pk_employee primary key (id))
;

create table fleet (
  id                        bigint auto_increment not null,
  name                      varchar(255) not null,
  num_of_vehicles           bigint,
  createdd                  date,
  constraint pk_fleet primary key (id))
;

create table insurance (
  id                        bigint auto_increment not null,
  contract_no               varchar(255),
  vehicle_id                bigint,
  itype                     varchar(255),
  cost                      double,
  constraint uq_insurance_vehicle_id unique (vehicle_id),
  constraint pk_insurance primary key (id))
;

create table maintenance (
  id                        bigint auto_increment not null,
  vehicle_id                bigint,
  service_type              varchar(255),
  constraint pk_maintenance primary key (id))
;

create table manager (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  surname                   varchar(255),
  email                     varchar(255),
  adress                    varchar(255),
  city                      varchar(255),
  is_manager                tinyint(1) default 0,
  is_admin                  tinyint(1) default 0,
  password                  varchar(255),
  constraint pk_manager primary key (id))
;

create table owner (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  email                     varchar(255),
  constraint pk_owner primary key (id))
;

create table reset_password (
  id                        varchar(255) not null,
  user_email                varchar(255),
  constraint pk_reset_password primary key (id))
;

create table service (
  id                        bigint auto_increment not null,
  stype                     varchar(255),
  description               varchar(255),
  maintenance_id            bigint,
  constraint pk_service primary key (id))
;

create table train (
  id                        bigint auto_increment not null,
  size                      integer,
  constraint pk_train primary key (id))
;

create table travel_order (
  id                        bigint auto_increment not null,
  number_to                 varchar(255),
  driver_id                 bigint,
  driver_name               varchar(255),
  vehicle_id                bigint,
  vehicle_name              varchar(255),
  destination               varchar(255),
  constraint uq_travel_order_driver_id unique (driver_id),
  constraint uq_travel_order_vehicle_id unique (vehicle_id),
  constraint pk_travel_order primary key (id))
;

create table truckC (
  id                        bigint auto_increment not null,
  size                      integer,
  constraint pk_truckC primary key (id))
;

create table type (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  description               varchar(255),
  constraint pk_type primary key (id))
;

create table vehicle (
  id                        bigint auto_increment not null,
  vid                       varchar(255),
  name                      varchar(255),
  owner_id                  bigint,
  fleet_id                  bigint,
  typev_id                  bigint,
  travel_order_id           bigint,
  prev_id                   bigint,
  next_id                   bigint,
  engagedd                  tinyint(1) default 0,
  status                    varchar(255),
  is_registered             tinyint(1) default 0,
  is_insured                tinyint(1) default 0,
  is_asigned                tinyint(1) default 0,
  v_registration_id         bigint,
  constraint uq_vehicle_travel_order_id unique (travel_order_id),
  constraint uq_vehicle_prev_id unique (prev_id),
  constraint uq_vehicle_next_id unique (next_id),
  constraint uq_vehicle_v_registration_id unique (v_registration_id),
  constraint pk_vehicle primary key (id))
;

create table vehicle_registration (
  id                        bigint auto_increment not null,
  reg_no                    varchar(255),
  vehicle_id                bigint,
  constraint uq_vehicle_registration_vehicle_id unique (vehicle_id),
  constraint pk_vehicle_registration primary key (id))
;

create table vendor (
  id                        integer auto_increment not null,
  name                      varchar(255),
  address                   varchar(255),
  city                      varchar(255),
  country                   varchar(255),
  phone                     varchar(255),
  email                     varchar(255),
  constraint pk_vendor primary key (id))
;

alter table driver add constraint fk_driver_travelOrderr_1 foreign key (travel_orderr_id) references travel_order (id) on delete restrict on update restrict;
create index ix_driver_travelOrderr_1 on driver (travel_orderr_id);
alter table insurance add constraint fk_insurance_vehicle_2 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_insurance_vehicle_2 on insurance (vehicle_id);
alter table maintenance add constraint fk_maintenance_vehicle_3 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_maintenance_vehicle_3 on maintenance (vehicle_id);
alter table service add constraint fk_service_maintenance_4 foreign key (maintenance_id) references maintenance (id) on delete restrict on update restrict;
create index ix_service_maintenance_4 on service (maintenance_id);
alter table travel_order add constraint fk_travel_order_driver_5 foreign key (driver_id) references driver (id) on delete restrict on update restrict;
create index ix_travel_order_driver_5 on travel_order (driver_id);
alter table travel_order add constraint fk_travel_order_vehicle_6 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_travel_order_vehicle_6 on travel_order (vehicle_id);
alter table vehicle add constraint fk_vehicle_owner_7 foreign key (owner_id) references owner (id) on delete restrict on update restrict;
create index ix_vehicle_owner_7 on vehicle (owner_id);
alter table vehicle add constraint fk_vehicle_fleet_8 foreign key (fleet_id) references fleet (id) on delete restrict on update restrict;
create index ix_vehicle_fleet_8 on vehicle (fleet_id);
alter table vehicle add constraint fk_vehicle_typev_9 foreign key (typev_id) references type (id) on delete restrict on update restrict;
create index ix_vehicle_typev_9 on vehicle (typev_id);
alter table vehicle add constraint fk_vehicle_travelOrder_10 foreign key (travel_order_id) references travel_order (id) on delete restrict on update restrict;
create index ix_vehicle_travelOrder_10 on vehicle (travel_order_id);
alter table vehicle add constraint fk_vehicle_prev_11 foreign key (prev_id) references vehicle (id) on delete restrict on update restrict;
create index ix_vehicle_prev_11 on vehicle (prev_id);
alter table vehicle add constraint fk_vehicle_next_12 foreign key (next_id) references vehicle (id) on delete restrict on update restrict;
create index ix_vehicle_next_12 on vehicle (next_id);
alter table vehicle add constraint fk_vehicle_vRegistration_13 foreign key (v_registration_id) references vehicle_registration (id) on delete restrict on update restrict;
create index ix_vehicle_vRegistration_13 on vehicle (v_registration_id);
alter table vehicle_registration add constraint fk_vehicle_registration_vehicle_14 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_vehicle_registration_vehicle_14 on vehicle_registration (vehicle_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table admin;

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

SET FOREIGN_KEY_CHECKS=1;

