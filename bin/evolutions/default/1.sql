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

create table client (
  id                        bigint not null,
  c_name                    varchar(255),
  c_type                    varchar(255),
  address                   varchar(255),
  phone                     varchar(255),
  email                     varchar(255),
  constraint pk_client primary key (id))
;

create table description (
  id                        bigint not null,
  property_name             varchar(255),
  property_value            varchar(255),
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
  dob                       date,
  engagedd                  boolean,
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

create table fuelBill (
  id                        bigint not null,
  gas_station_name          varchar(255),
  driver_id                 bigint,
  bill_date                 date,
  fuel_amount               varchar(255),
  fuel_price                varchar(255),
  total_distance            varchar(255),
  total_distance_gps        varchar(255),
  vehicle_id                bigint,
  constraint pk_fuelBill primary key (id))
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

create table route (
  id                        bigint not null,
  start_point               varchar(255),
  end_point                 varchar(255),
  r_name                    varchar(255),
  constraint pk_route primary key (id))
;

create table service (
  id                        bigint not null,
  stype                     varchar(255),
  description               varchar(255),
  is_chosen                 boolean,
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

create table technicalInfo (
  id                        bigint not null,
  engine_serial_number      varchar(255),
  chassis_number            varchar(255),
  cylinder_volume           varchar(255),
  fuel_consumption          varchar(255),
  loading_limit             varchar(255),
  fuel_tank                 varchar(255),
  engine_power              varchar(255),
  torque                    varchar(255),
  num_of_cylinders          varchar(255),
  net_weight                varchar(255),
  loaded_weight             varchar(255),
  trunk_capacity            varchar(255),
  tires_id                  bigint,
  constraint uq_technicalInfo_tires_id unique (tires_id),
  constraint pk_technicalInfo primary key (id))
;

create table tires (
  id                        bigint not null,
  front_tire_size           varchar(255),
  rear_tire_size            varchar(255),
  front_tire_pressure       varchar(255),
  rear_tire_pressure        varchar(255),
  technical_info_id         bigint,
  constraint uq_tires_technical_info_id unique (technical_info_id),
  constraint pk_tires primary key (id))
;

create table train (
  id                        bigint not null,
  size                      integer,
  constraint pk_train primary key (id))
;

create table trainComposition (
  id                        bigint not null,
  num_of_vehicles           bigint,
  createdd                  date,
  constraint pk_trainComposition primary key (id))
;

create table travel_order (
  id                        bigint not null,
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
  id                        bigint not null,
  num_of_vehicles           bigint,
  createdd                  date,
  constraint pk_truckComposition primary key (id))
;

create table type (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_type primary key (id))
;

create table vehicle (
  id                        bigint not null,
  vid                       varchar(255),
  name                      varchar(255),
  owner_id                  bigint,
  fleet_id                  bigint,
  typev_id                  bigint,
  technical_info_id         bigint,
  travel_order_id           bigint,
  engagedd                  boolean,
  status                    varchar(255),
  is_registered             boolean,
  is_insured                boolean,
  is_asigned                boolean,
  is_linked                 boolean,
  v_registration_id         bigint,
  vehicle_warranty_id       bigint,
  truck_composition_id      bigint,
  train_composition_id      bigint,
  is_linkable               boolean,
  position                  integer,
  constraint uq_vehicle_technical_info_id unique (technical_info_id),
  constraint uq_vehicle_travel_order_id unique (travel_order_id),
  constraint uq_vehicle_v_registration_id unique (v_registration_id),
  constraint uq_vehicle_vehicle_warranty_id unique (vehicle_warranty_id),
  constraint pk_vehicle primary key (id))
;

create table vehicle_inspection (
  id                        bigint not null,
  inspect_date              date,
  vehicle_documentation     varchar(255),
  safety                    varchar(255),
  body                      varchar(255),
  tires_wheels              varchar(255),
  steering_suspension       varchar(255),
  brakes                    varchar(255),
  lightning_el_system       varchar(255),
  glass                     varchar(255),
  exhaust_system            varchar(255),
  emission                  varchar(255),
  obd                       varchar(255),
  fuel_system               varchar(255),
  add_notes                 varchar(255),
  vehicle_id                bigint,
  constraint pk_vehicle_inspection primary key (id))
;

create table vehicle_registration (
  id                        bigint not null,
  reg_no                    varchar(255),
  certificate_no            varchar(255),
  trailer_loading_limit     varchar(255),
  registration_holder_id    bigint,
  city                      varchar(255),
  registration_date         date,
  expiration_date           date,
  constraint pk_vehicle_registration primary key (id))
;

create table vehicleWarranty (
  id                        bigint not null,
  warranty_details          varchar(255),
  commencement_warranty_date date,
  expiry_warranty_date      date,
  warranty_km_limit         varchar(255),
  vehicle_card_number       varchar(255),
  type_of_card              varchar(255),
  card_issue_date           date,
  constraint pk_vehicleWarranty primary key (id))
;

create table vendor (
  id                        integer not null,
  name                      varchar(255),
  vendor_type               varchar(255),
  address                   varchar(255),
  city                      varchar(255),
  country                   varchar(255),
  phone                     varchar(255),
  email                     varchar(255),
  constraint pk_vendor primary key (id))
;

create table warehouseWorkOrder (
  id                        bigint not null,
  content                   varchar(255),
  location                  varchar(255),
  constraint pk_warehouseWorkOrder primary key (id))
;

create table work_order (
  id                        bigint not null,
  wo_number                 bigint,
  createdd                  date,
  driver_id                 bigint,
  driver_name               varchar(255),
  vehicle_id                bigint,
  vehicle_name              varchar(255),
  client_id                 bigint,
  description               varchar(255),
  status_wo                 varchar(255),
  constraint uq_work_order_vehicle_id unique (vehicle_id),
  constraint pk_work_order primary key (id))
;


create table VehicleDescription (
  descriptionId                  bigint not null,
  vehicleId                      bigint not null,
  constraint pk_VehicleDescription primary key (descriptionId, vehicleId))
;

create table mnts_services (
  maintenanceId                  bigint not null,
  serviceId                      bigint not null,
  constraint pk_mnts_services primary key (maintenanceId, serviceId))
;
create sequence admin_seq;

create sequence client_seq;

create sequence description_seq;

create sequence driver_seq;

create sequence employee_seq;

create sequence fleet_seq;

create sequence fuelBill_seq;

create sequence insurance_seq;

create sequence maintenance_seq;

create sequence manager_seq;

create sequence owner_seq;

create sequence reset_password_seq;

create sequence route_seq;

create sequence service_seq;

create sequence task_seq;

create sequence technicalInfo_seq;

create sequence tires_seq;

create sequence train_seq;

create sequence trainComposition_seq;

create sequence travel_order_seq;

create sequence truckComposition_seq;

create sequence type_seq;

create sequence vehicle_seq;

create sequence vehicle_inspection_seq;

create sequence vehicle_registration_seq;

create sequence vehicleWarranty_seq;

create sequence vendor_seq;

create sequence warehouseWorkOrder_seq;

create sequence work_order_seq;

alter table driver add constraint fk_driver_travelOrderr_1 foreign key (travel_orderr_id) references travel_order (id) on delete restrict on update restrict;
create index ix_driver_travelOrderr_1 on driver (travel_orderr_id);
alter table fuelBill add constraint fk_fuelBill_driver_2 foreign key (driver_id) references driver (id) on delete restrict on update restrict;
create index ix_fuelBill_driver_2 on fuelBill (driver_id);
alter table fuelBill add constraint fk_fuelBill_vehicle_3 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_fuelBill_vehicle_3 on fuelBill (vehicle_id);
alter table insurance add constraint fk_insurance_vehicle_4 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_insurance_vehicle_4 on insurance (vehicle_id);
alter table maintenance add constraint fk_maintenance_vehicle_5 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_maintenance_vehicle_5 on maintenance (vehicle_id);
alter table task add constraint fk_task_workOrder_6 foreign key (work_order_id) references work_order (id) on delete restrict on update restrict;
create index ix_task_workOrder_6 on task (work_order_id);
alter table technicalInfo add constraint fk_technicalInfo_tires_7 foreign key (tires_id) references tires (id) on delete restrict on update restrict;
create index ix_technicalInfo_tires_7 on technicalInfo (tires_id);
alter table tires add constraint fk_tires_technicalInfo_8 foreign key (technical_info_id) references technicalInfo (id) on delete restrict on update restrict;
create index ix_tires_technicalInfo_8 on tires (technical_info_id);
alter table travel_order add constraint fk_travel_order_driver_9 foreign key (driver_id) references driver (id) on delete restrict on update restrict;
create index ix_travel_order_driver_9 on travel_order (driver_id);
alter table travel_order add constraint fk_travel_order_vehicle_10 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_travel_order_vehicle_10 on travel_order (vehicle_id);
alter table travel_order add constraint fk_travel_order_route_11 foreign key (route_id) references route (id) on delete restrict on update restrict;
create index ix_travel_order_route_11 on travel_order (route_id);
alter table vehicle add constraint fk_vehicle_owner_12 foreign key (owner_id) references owner (id) on delete restrict on update restrict;
create index ix_vehicle_owner_12 on vehicle (owner_id);
alter table vehicle add constraint fk_vehicle_fleet_13 foreign key (fleet_id) references fleet (id) on delete restrict on update restrict;
create index ix_vehicle_fleet_13 on vehicle (fleet_id);
alter table vehicle add constraint fk_vehicle_typev_14 foreign key (typev_id) references type (id) on delete restrict on update restrict;
create index ix_vehicle_typev_14 on vehicle (typev_id);
alter table vehicle add constraint fk_vehicle_technicalInfo_15 foreign key (technical_info_id) references technicalInfo (id) on delete restrict on update restrict;
create index ix_vehicle_technicalInfo_15 on vehicle (technical_info_id);
alter table vehicle add constraint fk_vehicle_travelOrder_16 foreign key (travel_order_id) references travel_order (id) on delete restrict on update restrict;
create index ix_vehicle_travelOrder_16 on vehicle (travel_order_id);
alter table vehicle add constraint fk_vehicle_vRegistration_17 foreign key (v_registration_id) references vehicle_registration (id) on delete restrict on update restrict;
create index ix_vehicle_vRegistration_17 on vehicle (v_registration_id);
alter table vehicle add constraint fk_vehicle_vehicleWarranty_18 foreign key (vehicle_warranty_id) references vehicleWarranty (id) on delete restrict on update restrict;
create index ix_vehicle_vehicleWarranty_18 on vehicle (vehicle_warranty_id);
alter table vehicle add constraint fk_vehicle_truckComposition_19 foreign key (truck_composition_id) references truckComposition (id) on delete restrict on update restrict;
create index ix_vehicle_truckComposition_19 on vehicle (truck_composition_id);
alter table vehicle add constraint fk_vehicle_trainComposition_20 foreign key (train_composition_id) references trainComposition (id) on delete restrict on update restrict;
create index ix_vehicle_trainComposition_20 on vehicle (train_composition_id);
alter table vehicle_inspection add constraint fk_vehicle_inspection_vehicle_21 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_vehicle_inspection_vehicle_21 on vehicle_inspection (vehicle_id);
alter table vehicle_registration add constraint fk_vehicle_registration_regis_22 foreign key (registration_holder_id) references owner (id) on delete restrict on update restrict;
create index ix_vehicle_registration_regis_22 on vehicle_registration (registration_holder_id);
alter table work_order add constraint fk_work_order_driver_23 foreign key (driver_id) references driver (id) on delete restrict on update restrict;
create index ix_work_order_driver_23 on work_order (driver_id);
alter table work_order add constraint fk_work_order_vehicle_24 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_work_order_vehicle_24 on work_order (vehicle_id);
alter table work_order add constraint fk_work_order_client_25 foreign key (client_id) references client (id) on delete restrict on update restrict;
create index ix_work_order_client_25 on work_order (client_id);



alter table VehicleDescription add constraint fk_VehicleDescription_descrip_01 foreign key (descriptionId) references description (id) on delete restrict on update restrict;

alter table VehicleDescription add constraint fk_VehicleDescription_vehicle_02 foreign key (vehicleId) references vehicle (id) on delete restrict on update restrict;

alter table mnts_services add constraint fk_mnts_services_maintenance_01 foreign key (maintenanceId) references maintenance (id) on delete restrict on update restrict;

alter table mnts_services add constraint fk_mnts_services_service_02 foreign key (serviceId) references service (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists admin;

drop table if exists client;

drop table if exists description;

drop table if exists VehicleDescription;

drop table if exists driver;

drop table if exists employee;

drop table if exists fleet;

drop table if exists fuelBill;

drop table if exists insurance;

drop table if exists maintenance;

drop table if exists mnts_services;

drop table if exists manager;

drop table if exists owner;

drop table if exists reset_password;

drop table if exists route;

drop table if exists service;

drop table if exists task;

drop table if exists technicalInfo;

drop table if exists tires;

drop table if exists train;

drop table if exists trainComposition;

drop table if exists travel_order;

drop table if exists truckComposition;

drop table if exists type;

drop table if exists vehicle;

drop table if exists vehicle_inspection;

drop table if exists vehicle_registration;

drop table if exists vehicleWarranty;

drop table if exists vendor;

drop table if exists warehouseWorkOrder;

drop table if exists work_order;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists admin_seq;

drop sequence if exists client_seq;

drop sequence if exists description_seq;

drop sequence if exists driver_seq;

drop sequence if exists employee_seq;

drop sequence if exists fleet_seq;

drop sequence if exists fuelBill_seq;

drop sequence if exists insurance_seq;

drop sequence if exists maintenance_seq;

drop sequence if exists manager_seq;

drop sequence if exists owner_seq;

drop sequence if exists reset_password_seq;

drop sequence if exists route_seq;

drop sequence if exists service_seq;

drop sequence if exists task_seq;

drop sequence if exists technicalInfo_seq;

drop sequence if exists tires_seq;

drop sequence if exists train_seq;

drop sequence if exists trainComposition_seq;

drop sequence if exists travel_order_seq;

drop sequence if exists truckComposition_seq;

drop sequence if exists type_seq;

drop sequence if exists vehicle_seq;

drop sequence if exists vehicle_inspection_seq;

drop sequence if exists vehicle_registration_seq;

drop sequence if exists vehicleWarranty_seq;

drop sequence if exists vendor_seq;

drop sequence if exists warehouseWorkOrder_seq;

drop sequence if exists work_order_seq;

