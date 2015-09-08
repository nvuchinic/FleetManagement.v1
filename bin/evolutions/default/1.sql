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
<<<<<<< HEAD
  id                        bigint not null,
  property_name             varchar(255),
  property_value            varchar(255),
=======
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
>>>>>>> b5bca42be31e75039f78202f50c9d14f7001a174
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
  is_chosen                 tinyint(1) default 0,
  constraint pk_service primary key (id))
;

create table task (
  id                        bigint not null,
  name                      varchar(255),
  date_time                 date,
  description               varchar(255),
  work_order_id             bigint,
  constraint pk_task primary key (id))
;

create table train (
  id                        bigint auto_increment not null,
  size                      integer,
  constraint pk_train primary key (id))
;

create table trainComposition (
  id                        bigint auto_increment not null,
  num_of_vehicles           bigint,
  createdd                  date,
  constraint pk_trainComposition primary key (id))
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
  num_of_vehicles           bigint,
  createdd                  date,
  constraint pk_truckComposition primary key (id))
;

create table type (
  id                        bigint auto_increment not null,
  name                      varchar(255),
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
  train_composition_id      bigint,
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

<<<<<<< HEAD

create table VehicleDescription (
  descriptionId                  bigint not null,
  vehicleId                      bigint not null,
  constraint pk_VehicleDescription primary key (descriptionId, vehicleId))
;
create sequence admin_seq;

create sequence client_seq;

create sequence description_seq;

create sequence driver_seq;

create sequence employee_seq;

create sequence fleet_seq;

create sequence insurance_seq;

create sequence maintenance_seq;

create sequence manager_seq;

create sequence owner_seq;

create sequence reset_password_seq;

create sequence route_seq;

create sequence service_seq;

create sequence task_seq;

create sequence train_seq;

create sequence trainComposition_seq;

create sequence travel_order_seq;

create sequence truckComposition_seq;

create sequence type_seq;

create sequence vehicle_seq;

create sequence vehicle_registration_seq;

create sequence vendor_seq;

create sequence work_order_seq;

=======
>>>>>>> b5bca42be31e75039f78202f50c9d14f7001a174
alter table driver add constraint fk_driver_travelOrderr_1 foreign key (travel_orderr_id) references travel_order (id) on delete restrict on update restrict;
create index ix_driver_travelOrderr_1 on driver (travel_orderr_id);
alter table insurance add constraint fk_insurance_vehicle_2 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_insurance_vehicle_2 on insurance (vehicle_id);
alter table maintenance add constraint fk_maintenance_vehicle_3 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_maintenance_vehicle_3 on maintenance (vehicle_id);
alter table service add constraint fk_service_maintenance_4 foreign key (maintenance_id) references maintenance (id) on delete restrict on update restrict;
create index ix_service_maintenance_4 on service (maintenance_id);
alter table task add constraint fk_task_workOrder_5 foreign key (work_order_id) references work_order (id) on delete restrict on update restrict;
create index ix_task_workOrder_5 on task (work_order_id);
alter table travel_order add constraint fk_travel_order_driver_6 foreign key (driver_id) references driver (id) on delete restrict on update restrict;
create index ix_travel_order_driver_6 on travel_order (driver_id);
alter table travel_order add constraint fk_travel_order_vehicle_7 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_travel_order_vehicle_7 on travel_order (vehicle_id);
alter table travel_order add constraint fk_travel_order_route_8 foreign key (route_id) references route (id) on delete restrict on update restrict;
create index ix_travel_order_route_8 on travel_order (route_id);
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
alter table vehicle add constraint fk_vehicle_trainComposition_15 foreign key (train_composition_id) references trainComposition (id) on delete restrict on update restrict;
create index ix_vehicle_trainComposition_15 on vehicle (train_composition_id);
alter table vehicle_registration add constraint fk_vehicle_registration_vehicle_16 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_vehicle_registration_vehicle_16 on vehicle_registration (vehicle_id);
alter table work_order add constraint fk_work_order_driver_17 foreign key (driver_id) references driver (id) on delete restrict on update restrict;
create index ix_work_order_driver_17 on work_order (driver_id);
alter table work_order add constraint fk_work_order_vehicle_18 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_work_order_vehicle_18 on work_order (vehicle_id);
alter table work_order add constraint fk_work_order_client_19 foreign key (client_id) references client (id) on delete restrict on update restrict;
create index ix_work_order_client_19 on work_order (client_id);



alter table VehicleDescription add constraint fk_VehicleDescription_descrip_01 foreign key (descriptionId) references description (id) on delete restrict on update restrict;

alter table VehicleDescription add constraint fk_VehicleDescription_vehicle_02 foreign key (vehicleId) references vehicle (id) on delete restrict on update restrict;

# --- !Downs

<<<<<<< HEAD
SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists admin;

drop table if exists client;

drop table if exists description;

drop table if exists VehicleDescription;

drop table if exists driver;

drop table if exists employee;

drop table if exists fleet;

drop table if exists insurance;

drop table if exists maintenance;

drop table if exists manager;

drop table if exists owner;

drop table if exists reset_password;

drop table if exists route;

drop table if exists service;

drop table if exists task;

drop table if exists train;

drop table if exists trainComposition;

drop table if exists travel_order;

drop table if exists truckComposition;

drop table if exists type;

drop table if exists vehicle;

drop table if exists vehicle_registration;

drop table if exists vendor;

drop table if exists work_order;
=======
SET FOREIGN_KEY_CHECKS=0;
>>>>>>> b5bca42be31e75039f78202f50c9d14f7001a174

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

<<<<<<< HEAD
drop sequence if exists task_seq;

drop sequence if exists train_seq;
=======
drop table trainComposition;
>>>>>>> b5bca42be31e75039f78202f50c9d14f7001a174

drop table travel_order;

drop table truckComposition;

drop table type;

drop table vehicle;

drop table vehicle_registration;

drop table vendor;

drop table work_order;

SET FOREIGN_KEY_CHECKS=1;

