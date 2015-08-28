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

create table client (
  id                        bigint auto_increment not null,
  c_name                    varchar(255),
  c_type                    varchar(255),
  address                   varchar(255),
  phone                     varchar(255),
  email                     varchar(255),
  constraint pk_client primary key (id))
;

create table description (
  id                        bigint auto_increment not null,
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
  constraint pk_description primary key (id))
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
  dob                       date,
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
  arrival                   date,
  departure                 date,
  pickup_place              varchar(255),
  return_place              varchar(255),
  num_of_vehicles           bigint,
  createdd                  date,
  constraint pk_fleet primary key (id))
;

create table insurance (
  id                        bigint auto_increment not null,
  contract_no               varchar(255),
  createdd                  date,
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
  m_date                    date,
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

create table route (
  id                        bigint auto_increment not null,
  start_point               varchar(255),
  end_point                 varchar(255),
  r_name                    varchar(255),
  constraint pk_route primary key (id))
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
  number_to                 bigint,
  name                      varchar(255),
  reason                    varchar(255),
  date                      date,
  driver_id                 bigint,
  vehicle_id                bigint,
  vehicle_name              varchar(255),
  destination               varchar(255),
  start_date                date,
  return_date               date,
  route_id                  bigint,
  constraint uq_travel_order_driver_id unique (driver_id),
  constraint uq_travel_order_vehicle_id unique (vehicle_id),
  constraint pk_travel_order primary key (id))
;

create table truckComposition (
  id                        bigint auto_increment not null,
  createdd                  date,
  constraint pk_truckComposition primary key (id))
;

create table type (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  description_id            bigint,
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
  engagedd                  tinyint(1) default 0,
  status                    varchar(255),
  is_registered             tinyint(1) default 0,
  is_insured                tinyint(1) default 0,
  is_asigned                tinyint(1) default 0,
  is_linked                 tinyint(1) default 0,
  v_registration_id         bigint,
  truck_composition_id      bigint,
  is_linkable               tinyint(1) default 0,
  position                  integer,
  constraint uq_vehicle_travel_order_id unique (travel_order_id),
  constraint uq_vehicle_v_registration_id unique (v_registration_id),
  constraint pk_vehicle primary key (id))
;

create table vehicle_registration (
  id                        bigint auto_increment not null,
  reg_no                    varchar(255),
  vehicle_id                bigint,
  expiration_date           date,
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

create table work_order (
  id                        bigint auto_increment not null,
  wo_number                 bigint,
  createdd                  date,
  driver_id                 bigint,
  driver_name               varchar(255),
  vehicle_id                bigint,
  vehicle_name              varchar(255),
  client_id                 bigint,
  description               varchar(255),
  status_wo                 varchar(255),
  constraint uq_work_order_driver_id unique (driver_id),
  constraint uq_work_order_vehicle_id unique (vehicle_id),
  constraint pk_work_order primary key (id))
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
alter table travel_order add constraint fk_travel_order_route_7 foreign key (route_id) references route (id) on delete restrict on update restrict;
create index ix_travel_order_route_7 on travel_order (route_id);
alter table type add constraint fk_type_description_8 foreign key (description_id) references description (id) on delete restrict on update restrict;
create index ix_type_description_8 on type (description_id);
alter table vehicle add constraint fk_vehicle_owner_9 foreign key (owner_id) references owner (id) on delete restrict on update restrict;
create index ix_vehicle_owner_9 on vehicle (owner_id);
alter table vehicle add constraint fk_vehicle_fleet_10 foreign key (fleet_id) references fleet (id) on delete restrict on update restrict;
create index ix_vehicle_fleet_10 on vehicle (fleet_id);
alter table vehicle add constraint fk_vehicle_typev_11 foreign key (typev_id) references type (id) on delete restrict on update restrict;
create index ix_vehicle_typev_11 on vehicle (typev_id);
alter table vehicle add constraint fk_vehicle_travelOrder_12 foreign key (travel_order_id) references travel_order (id) on delete restrict on update restrict;
create index ix_vehicle_travelOrder_12 on vehicle (travel_order_id);
alter table vehicle add constraint fk_vehicle_vRegistration_13 foreign key (v_registration_id) references vehicle_registration (id) on delete restrict on update restrict;
create index ix_vehicle_vRegistration_13 on vehicle (v_registration_id);
alter table vehicle add constraint fk_vehicle_truckComposition_14 foreign key (truck_composition_id) references truckComposition (id) on delete restrict on update restrict;
create index ix_vehicle_truckComposition_14 on vehicle (truck_composition_id);
alter table vehicle_registration add constraint fk_vehicle_registration_vehicle_15 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_vehicle_registration_vehicle_15 on vehicle_registration (vehicle_id);
alter table work_order add constraint fk_work_order_driver_16 foreign key (driver_id) references driver (id) on delete restrict on update restrict;
create index ix_work_order_driver_16 on work_order (driver_id);
alter table work_order add constraint fk_work_order_vehicle_17 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_work_order_vehicle_17 on work_order (vehicle_id);
alter table work_order add constraint fk_work_order_client_18 foreign key (client_id) references client (id) on delete restrict on update restrict;
create index ix_work_order_client_18 on work_order (client_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table admin;

drop table client;

drop table description;

drop table driver;

drop table employee;

drop table fleet;

drop table insurance;

drop table maintenance;

drop table manager;

drop table owner;

drop table reset_password;

drop table route;

drop table service;

drop table train;

drop table travel_order;

drop table truckComposition;

drop table type;

drop table vehicle;

drop table vehicle_registration;

drop table vendor;

drop table work_order;

SET FOREIGN_KEY_CHECKS=1;

