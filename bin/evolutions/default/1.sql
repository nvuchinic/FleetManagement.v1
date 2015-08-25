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

create table description (
  id                        bigint not null,
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
  id                        bigint not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  driver_name               varchar(255),
  phone_number              varchar(255),
  adress                    varchar(255),
  description               varchar(255),
  travel_orderr_id          bigint,
<<<<<<< HEAD
  engagedd                  boolean,
=======
  dob                       date,
  engagedd                  tinyint(1) default 0,
>>>>>>> b65feaf653e525eaf6a1204d2dae559d4355533f
  constraint uq_driver_travel_orderr_id unique (travel_orderr_id),
  constraint pk_driver primary key (id))
;

create table employee (
  id                        bigint not null,
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
  id                        bigint not null,
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
  id                        bigint not null,
  contract_no               varchar(255),
  createdd                  date,
  vehicle_id                bigint,
  itype                     varchar(255),
  cost                      double,
  constraint uq_insurance_vehicle_id unique (vehicle_id),
  constraint pk_insurance primary key (id))
;

create table maintenance (
  id                        bigint not null,
  vehicle_id                bigint,
  service_type              varchar(255),
  m_date                    date,
  constraint pk_maintenance primary key (id))
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

create table owner (
  id                        bigint not null,
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
  id                        bigint not null,
  stype                     varchar(255),
  description               varchar(255),
  maintenance_id            bigint,
  constraint pk_service primary key (id))
;

create table train (
  id                        bigint not null,
  size                      integer,
  constraint pk_train primary key (id))
;

create table travel_order (
  id                        bigint not null,
  number_to                 bigint,
  name                      varchar(255),
  reason                    varchar(255),
  date                      date,
  driver_id                 bigint,
  vehicle_id                bigint,
  destination               varchar(255),
  start_date                date,
  return_date               date,
  constraint uq_travel_order_driver_id unique (driver_id),
  constraint uq_travel_order_vehicle_id unique (vehicle_id),
  constraint pk_travel_order primary key (id))
;

create table truckC (
  id                        bigint not null,
  size                      integer,
  constraint pk_truckC primary key (id))
;

create table type (
  id                        bigint not null,
  name                      varchar(255),
  description_id            bigint,
  constraint pk_type primary key (id))
;

create table vehicle (
  id                        bigint not null,
  vid                       varchar(255),
  name                      varchar(255),
  owner_id                  bigint,
  fleet_id                  bigint,
  typev_id                  bigint,
  travel_order_id           bigint,
  prev_id                   bigint,
  next_id                   bigint,
  engagedd                  boolean,
  status                    varchar(255),
  is_registered             boolean,
  is_insured                boolean,
  is_asigned                boolean,
  v_registration_id         bigint,
  constraint uq_vehicle_travel_order_id unique (travel_order_id),
  constraint uq_vehicle_prev_id unique (prev_id),
  constraint uq_vehicle_next_id unique (next_id),
  constraint uq_vehicle_v_registration_id unique (v_registration_id),
  constraint pk_vehicle primary key (id))
;

create table vehicle_registration (
  id                        bigint not null,
  reg_no                    varchar(255),
  vehicle_id                bigint,
<<<<<<< HEAD
  constraint uq_vehicle_registration_vehicle_ unique (vehicle_id),
=======
  expiration_date           date,
  constraint uq_vehicle_registration_vehicle_id unique (vehicle_id),
>>>>>>> b65feaf653e525eaf6a1204d2dae559d4355533f
  constraint pk_vehicle_registration primary key (id))
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

create table work_order (
  id                        bigint not null,
  wo_number                 bigint,
  wo_date                   timestamp,
  driver_id                 bigint,
  driver_name               varchar(255),
  vehicle_id                bigint,
  vehicle_name              varchar(255),
  description               varchar(255),
  status_wo                 varchar(255),
  constraint uq_work_order_driver_id unique (driver_id),
  constraint uq_work_order_vehicle_id unique (vehicle_id),
  constraint pk_work_order primary key (id))
;

create sequence admin_seq;

create sequence description_seq;

create sequence driver_seq;

create sequence employee_seq;

create sequence fleet_seq;

create sequence insurance_seq;

create sequence maintenance_seq;

create sequence manager_seq;

create sequence owner_seq;

create sequence reset_password_seq;

create sequence service_seq;

create sequence train_seq;

create sequence travel_order_seq;

create sequence truckC_seq;

create sequence type_seq;

create sequence vehicle_seq;

create sequence vehicle_registration_seq;

create sequence vendor_seq;

<<<<<<< HEAD
alter table driver add constraint fk_driver_travelOrderr_1 foreign key (travel_orderr_id) references travel_order (id) on delete restrict on update restrict;
create index ix_driver_travelOrderr_1 on driver (travel_orderr_id);
=======
create sequence work_order_seq;

alter table driver add constraint fk_driver_travelOrder_1 foreign key (travel_order_id) references travel_order (id) on delete restrict on update restrict;
create index ix_driver_travelOrder_1 on driver (travel_order_id);
>>>>>>> c1ec02872d50e07b31a0f8c211586af624f2e098
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
<<<<<<< HEAD
alter table type add constraint fk_type_description_7 foreign key (description_id) references description (id) on delete restrict on update restrict;
create index ix_type_description_7 on type (description_id);
alter table vehicle add constraint fk_vehicle_owner_8 foreign key (owner_id) references owner (id) on delete restrict on update restrict;
create index ix_vehicle_owner_8 on vehicle (owner_id);
alter table vehicle add constraint fk_vehicle_fleet_9 foreign key (fleet_id) references fleet (id) on delete restrict on update restrict;
create index ix_vehicle_fleet_9 on vehicle (fleet_id);
alter table vehicle add constraint fk_vehicle_typev_10 foreign key (typev_id) references type (id) on delete restrict on update restrict;
create index ix_vehicle_typev_10 on vehicle (typev_id);
alter table vehicle add constraint fk_vehicle_travelOrder_11 foreign key (travel_order_id) references travel_order (id) on delete restrict on update restrict;
create index ix_vehicle_travelOrder_11 on vehicle (travel_order_id);
alter table vehicle add constraint fk_vehicle_prev_12 foreign key (prev_id) references vehicle (id) on delete restrict on update restrict;
create index ix_vehicle_prev_12 on vehicle (prev_id);
alter table vehicle add constraint fk_vehicle_next_13 foreign key (next_id) references vehicle (id) on delete restrict on update restrict;
create index ix_vehicle_next_13 on vehicle (next_id);
alter table vehicle add constraint fk_vehicle_vRegistration_14 foreign key (v_registration_id) references vehicle_registration (id) on delete restrict on update restrict;
create index ix_vehicle_vRegistration_14 on vehicle (v_registration_id);
alter table vehicle_registration add constraint fk_vehicle_registration_vehic_15 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_vehicle_registration_vehic_15 on vehicle_registration (vehicle_id);
=======
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
alter table vehicle_registration add constraint fk_vehicle_registration_vehic_14 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_vehicle_registration_vehic_14 on vehicle_registration (vehicle_id);
alter table work_order add constraint fk_work_order_driver_15 foreign key (driver_id) references driver (id) on delete restrict on update restrict;
create index ix_work_order_driver_15 on work_order (driver_id);
alter table work_order add constraint fk_work_order_vehicle_16 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_work_order_vehicle_16 on work_order (vehicle_id);
>>>>>>> c1ec02872d50e07b31a0f8c211586af624f2e098



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists admin;

drop table if exists description;

drop table if exists driver;

drop table if exists employee;

drop table if exists fleet;

drop table if exists insurance;

drop table if exists maintenance;

drop table if exists manager;

drop table if exists owner;

drop table if exists reset_password;

drop table if exists service;

drop table if exists train;

drop table if exists travel_order;

drop table if exists truckC;

drop table if exists type;

drop table if exists vehicle;

drop table if exists vehicle_registration;

drop table if exists vendor;

drop table if exists work_order;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists admin_seq;

drop sequence if exists description_seq;

drop sequence if exists driver_seq;

drop sequence if exists employee_seq;

drop sequence if exists fleet_seq;

drop sequence if exists insurance_seq;

drop sequence if exists maintenance_seq;

drop sequence if exists manager_seq;

drop sequence if exists owner_seq;

drop sequence if exists reset_password_seq;

drop sequence if exists service_seq;

drop sequence if exists train_seq;

drop sequence if exists travel_order_seq;

drop sequence if exists truckC_seq;

drop sequence if exists type_seq;

drop sequence if exists vehicle_seq;

drop sequence if exists vehicle_registration_seq;

drop sequence if exists vendor_seq;

drop sequence if exists work_order_seq;

