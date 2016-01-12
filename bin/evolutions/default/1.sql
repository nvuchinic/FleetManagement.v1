# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table client (
  id                        bigint not null,
  c_name                    varchar(255),
  c_type                    varchar(255),
  address                   varchar(255),
  phone                     varchar(255),
  email                     varchar(255),
  constraint pk_client primary key (id))
;

create table currencyy (
  id                        bigint not null,
  name                      varchar(255),
  symbol                    varchar(255),
  description               varchar(255),
  constraint pk_currencyy primary key (id))
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
  first_name                varchar(255),
  last_name                 varchar(255),
  full_name                 varchar(255),
  dob                       date,
  address                   varchar(255),
  phone                     varchar(255),
  email                     varchar(255),
  is_driver                 boolean,
  is_engaged                boolean,
  travel_order_id           bigint,
  constraint uq_employee_travel_order_id unique (travel_order_id),
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
  vendor_id                 integer,
  driver_id                 bigint,
  bill_date                 date,
  fuel_amount               double,
  fuel_price                double,
  total_distance            double,
  total_distance_gps        double,
  vehicle_id                bigint,
  fuel_type_id              bigint,
  constraint pk_fuelBill primary key (id))
;

create table fuel_type (
  id                        bigint not null,
  ft_name                   varchar(255),
  constraint pk_fuel_type primary key (id))
;

create table insurance (
  id                        bigint not null,
  contract_no               varchar(255),
  expiration_date           date,
  vehicle_id                bigint,
  itype                     varchar(255),
  cost                      double,
  checked                   boolean,
  notification_id           bigint,
  vendor_id                 integer,
  constraint pk_insurance primary key (id))
;

create table issue (
  id                        bigint not null,
  vehicle_id                bigint,
  issue_date                date,
  summary                   varchar(255),
  description               varchar(255),
  odometer                  bigint,
  status                    varchar(255),
  reporting_employee_id     bigint,
  assigned_employee_id      bigint,
  resolve_date              date,
  constraint pk_issue primary key (id))
;

create table maintenance (
  id                        bigint not null,
  vehicle_id                bigint,
  service_type              varchar(255),
  m_date                    date,
  odometer                  integer,
  constraint pk_maintenance primary key (id))
;

create table measurement_unit (
  id                        bigint not null,
  name                      varchar(255),
  symbol                    varchar(255),
  description               varchar(255),
  constraint pk_measurement_unit primary key (id))
;

create table notification_settings (
  id                        bigint not null,
  name                      varchar(255),
  threshold                 integer,
  time_unit                 varchar(255),
  registration_notification_on boolean,
  inspection_notification_on boolean,
  insurance_notification_on boolean,
  constraint pk_notification_settings primary key (id))
;

create table owner (
  id                        bigint not null,
  name                      varchar(255),
  email                     varchar(255),
  constraint pk_owner primary key (id))
;

create table part (
  id                        bigint not null,
  name                      varchar(255),
  number                    bigint,
  part_category_id          bigint,
  description               varchar(255),
  cost                      double,
  manufacturer              varchar(255),
  quantity                  integer,
  vendor_id                 integer,
  m_unit_id                 bigint,
  currencyy_id              bigint,
  part_location_id          bigint,
  constraint pk_part primary key (id))
;

create table partCategory (
  id                        bigint not null,
  name                      varchar(255),
  description               varchar(255),
  constraint pk_partCategory primary key (id))
;

create table part_location (
  id                        bigint not null,
  name                      varchar(255),
  description               varchar(255),
  constraint pk_part_location primary key (id))
;

create table renewal_notification (
  id                        bigint not null,
  constraint pk_renewal_notification primary key (id))
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
  has_notification          boolean,
  constraint pk_service primary key (id))
;

create table service_notification (
  id                        bigint not null,
  service_for_sn_id         bigint,
  vehicle_id                bigint,
  miles_left_to_service     integer,
  next_service_milage       integer,
  next_service_date         date,
  constraint pk_service_notification primary key (id))
;

create table service_notification_settings (
  id                        bigint not null,
  service_id                bigint,
  meter_interval_size       integer,
  time_interval_size        integer,
  time_interval_unit        varchar(255),
  meter_interval_unit       varchar(255),
  meter_threshold_size      integer,
  meter_threshold_unit      varchar(255),
  time_threshold_size       integer,
  time_threshold_unit       varchar(255),
  constraint pk_service_notification_settings primary key (id))
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
  fuel_type_id              bigint,
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
  odometer                  integer,
  v_registration_id         bigint,
  vehicle_warranty_id       bigint,
  truck_composition_id      bigint,
  train_composition_id      bigint,
  is_linkable               boolean,
  position                  integer,
  vehicle_brand_id          bigint,
  vehicle_model_id          bigint,
  constraint uq_vehicle_technical_info_id unique (technical_info_id),
  constraint uq_vehicle_travel_order_id unique (travel_order_id),
  constraint uq_vehicle_v_registration_id unique (v_registration_id),
  constraint uq_vehicle_vehicle_warranty_id unique (vehicle_warranty_id),
  constraint pk_vehicle primary key (id))
;

create table vehicleBrand (
  id                        bigint not null,
  name                      varchar(255),
  typev_id                  bigint,
  constraint pk_vehicleBrand primary key (id))
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
  expiry_date               date,
  notification_id           bigint,
  checked                   boolean,
  constraint pk_vehicle_inspection primary key (id))
;

create table vehicleModel (
  id                        bigint not null,
  name                      varchar(255),
  vehicle_brand_id          bigint,
  constraint pk_vehicleModel primary key (id))
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
  checked                   boolean,
  vehicle_id                bigint,
  notification_id           bigint,
  constraint uq_vehicle_registration_vehicle_ unique (vehicle_id),
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

create table VehiclesServiceNotifications (
  serviceNotificationSettingsId  bigint not null,
  vehicleId                      bigint not null,
  constraint pk_VehiclesServiceNotifications primary key (serviceNotificationSettingsId, vehicleId))
;
create sequence client_seq;

create sequence currencyy_seq;

create sequence description_seq;

create sequence driver_seq;

create sequence employee_seq;

create sequence fleet_seq;

create sequence fuelBill_seq;

create sequence fuel_type_seq;

create sequence insurance_seq;

create sequence issue_seq;

create sequence maintenance_seq;

create sequence measurement_unit_seq;

create sequence notification_settings_seq;

create sequence owner_seq;

create sequence part_seq;

create sequence partCategory_seq;

create sequence part_location_seq;

create sequence renewal_notification_seq;

create sequence reset_password_seq;

create sequence route_seq;

create sequence service_seq;

create sequence service_notification_seq;

create sequence service_notification_settings_seq;

create sequence task_seq;

create sequence technicalInfo_seq;

create sequence tires_seq;

create sequence train_seq;

create sequence trainComposition_seq;

create sequence travel_order_seq;

create sequence truckComposition_seq;

create sequence type_seq;

create sequence vehicle_seq;

create sequence vehicleBrand_seq;

create sequence vehicle_inspection_seq;

create sequence vehicleModel_seq;

create sequence vehicle_registration_seq;

create sequence vehicleWarranty_seq;

create sequence vendor_seq;

create sequence warehouseWorkOrder_seq;

create sequence work_order_seq;

alter table driver add constraint fk_driver_travelOrderr_1 foreign key (travel_orderr_id) references travel_order (id) on delete restrict on update restrict;
create index ix_driver_travelOrderr_1 on driver (travel_orderr_id);
alter table employee add constraint fk_employee_travelOrder_2 foreign key (travel_order_id) references travel_order (id) on delete restrict on update restrict;
create index ix_employee_travelOrder_2 on employee (travel_order_id);
alter table fuelBill add constraint fk_fuelBill_vendor_3 foreign key (vendor_id) references vendor (id) on delete restrict on update restrict;
create index ix_fuelBill_vendor_3 on fuelBill (vendor_id);
alter table fuelBill add constraint fk_fuelBill_driver_4 foreign key (driver_id) references employee (id) on delete restrict on update restrict;
create index ix_fuelBill_driver_4 on fuelBill (driver_id);
alter table fuelBill add constraint fk_fuelBill_vehicle_5 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_fuelBill_vehicle_5 on fuelBill (vehicle_id);
alter table fuelBill add constraint fk_fuelBill_fuelType_6 foreign key (fuel_type_id) references fuel_type (id) on delete restrict on update restrict;
create index ix_fuelBill_fuelType_6 on fuelBill (fuel_type_id);
alter table insurance add constraint fk_insurance_vehicle_7 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_insurance_vehicle_7 on insurance (vehicle_id);
alter table insurance add constraint fk_insurance_notification_8 foreign key (notification_id) references renewal_notification (id) on delete restrict on update restrict;
create index ix_insurance_notification_8 on insurance (notification_id);
alter table insurance add constraint fk_insurance_vendor_9 foreign key (vendor_id) references vendor (id) on delete restrict on update restrict;
create index ix_insurance_vendor_9 on insurance (vendor_id);
alter table issue add constraint fk_issue_vehicle_10 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_issue_vehicle_10 on issue (vehicle_id);
alter table issue add constraint fk_issue_reportingEmployee_11 foreign key (reporting_employee_id) references employee (id) on delete restrict on update restrict;
create index ix_issue_reportingEmployee_11 on issue (reporting_employee_id);
alter table issue add constraint fk_issue_assignedEmployee_12 foreign key (assigned_employee_id) references employee (id) on delete restrict on update restrict;
create index ix_issue_assignedEmployee_12 on issue (assigned_employee_id);
alter table maintenance add constraint fk_maintenance_vehicle_13 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_maintenance_vehicle_13 on maintenance (vehicle_id);
alter table part add constraint fk_part_partCategory_14 foreign key (part_category_id) references partCategory (id) on delete restrict on update restrict;
create index ix_part_partCategory_14 on part (part_category_id);
alter table part add constraint fk_part_vendor_15 foreign key (vendor_id) references vendor (id) on delete restrict on update restrict;
create index ix_part_vendor_15 on part (vendor_id);
alter table part add constraint fk_part_m_unit_16 foreign key (m_unit_id) references measurement_unit (id) on delete restrict on update restrict;
create index ix_part_m_unit_16 on part (m_unit_id);
alter table part add constraint fk_part_currencyy_17 foreign key (currencyy_id) references currencyy (id) on delete restrict on update restrict;
create index ix_part_currencyy_17 on part (currencyy_id);
alter table part add constraint fk_part_partLocation_18 foreign key (part_location_id) references part_location (id) on delete restrict on update restrict;
create index ix_part_partLocation_18 on part (part_location_id);
alter table service_notification add constraint fk_service_notification_servi_19 foreign key (service_for_sn_id) references service (id) on delete restrict on update restrict;
create index ix_service_notification_servi_19 on service_notification (service_for_sn_id);
alter table service_notification add constraint fk_service_notification_vehic_20 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_service_notification_vehic_20 on service_notification (vehicle_id);
alter table service_notification_settings add constraint fk_service_notification_setti_21 foreign key (service_id) references service (id) on delete restrict on update restrict;
create index ix_service_notification_setti_21 on service_notification_settings (service_id);
alter table task add constraint fk_task_workOrder_22 foreign key (work_order_id) references work_order (id) on delete restrict on update restrict;
create index ix_task_workOrder_22 on task (work_order_id);
alter table technicalInfo add constraint fk_technicalInfo_tires_23 foreign key (tires_id) references tires (id) on delete restrict on update restrict;
create index ix_technicalInfo_tires_23 on technicalInfo (tires_id);
alter table technicalInfo add constraint fk_technicalInfo_fuelType_24 foreign key (fuel_type_id) references fuel_type (id) on delete restrict on update restrict;
create index ix_technicalInfo_fuelType_24 on technicalInfo (fuel_type_id);
alter table tires add constraint fk_tires_technicalInfo_25 foreign key (technical_info_id) references technicalInfo (id) on delete restrict on update restrict;
create index ix_tires_technicalInfo_25 on tires (technical_info_id);
alter table travel_order add constraint fk_travel_order_driver_26 foreign key (driver_id) references employee (id) on delete restrict on update restrict;
create index ix_travel_order_driver_26 on travel_order (driver_id);
alter table travel_order add constraint fk_travel_order_vehicle_27 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_travel_order_vehicle_27 on travel_order (vehicle_id);
alter table travel_order add constraint fk_travel_order_route_28 foreign key (route_id) references route (id) on delete restrict on update restrict;
create index ix_travel_order_route_28 on travel_order (route_id);
alter table vehicle add constraint fk_vehicle_owner_29 foreign key (owner_id) references owner (id) on delete restrict on update restrict;
create index ix_vehicle_owner_29 on vehicle (owner_id);
alter table vehicle add constraint fk_vehicle_fleet_30 foreign key (fleet_id) references fleet (id) on delete restrict on update restrict;
create index ix_vehicle_fleet_30 on vehicle (fleet_id);
alter table vehicle add constraint fk_vehicle_typev_31 foreign key (typev_id) references type (id) on delete restrict on update restrict;
create index ix_vehicle_typev_31 on vehicle (typev_id);
alter table vehicle add constraint fk_vehicle_technicalInfo_32 foreign key (technical_info_id) references technicalInfo (id) on delete restrict on update restrict;
create index ix_vehicle_technicalInfo_32 on vehicle (technical_info_id);
alter table vehicle add constraint fk_vehicle_travelOrder_33 foreign key (travel_order_id) references travel_order (id) on delete restrict on update restrict;
create index ix_vehicle_travelOrder_33 on vehicle (travel_order_id);
alter table vehicle add constraint fk_vehicle_vRegistration_34 foreign key (v_registration_id) references vehicle_registration (id) on delete restrict on update restrict;
create index ix_vehicle_vRegistration_34 on vehicle (v_registration_id);
alter table vehicle add constraint fk_vehicle_vehicleWarranty_35 foreign key (vehicle_warranty_id) references vehicleWarranty (id) on delete restrict on update restrict;
create index ix_vehicle_vehicleWarranty_35 on vehicle (vehicle_warranty_id);
alter table vehicle add constraint fk_vehicle_truckComposition_36 foreign key (truck_composition_id) references truckComposition (id) on delete restrict on update restrict;
create index ix_vehicle_truckComposition_36 on vehicle (truck_composition_id);
alter table vehicle add constraint fk_vehicle_trainComposition_37 foreign key (train_composition_id) references trainComposition (id) on delete restrict on update restrict;
create index ix_vehicle_trainComposition_37 on vehicle (train_composition_id);
alter table vehicle add constraint fk_vehicle_vehicleBrand_38 foreign key (vehicle_brand_id) references vehicleBrand (id) on delete restrict on update restrict;
create index ix_vehicle_vehicleBrand_38 on vehicle (vehicle_brand_id);
alter table vehicle add constraint fk_vehicle_vehicleModel_39 foreign key (vehicle_model_id) references vehicleModel (id) on delete restrict on update restrict;
create index ix_vehicle_vehicleModel_39 on vehicle (vehicle_model_id);
alter table vehicleBrand add constraint fk_vehicleBrand_typev_40 foreign key (typev_id) references type (id) on delete restrict on update restrict;
create index ix_vehicleBrand_typev_40 on vehicleBrand (typev_id);
alter table vehicle_inspection add constraint fk_vehicle_inspection_vehicle_41 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_vehicle_inspection_vehicle_41 on vehicle_inspection (vehicle_id);
alter table vehicle_inspection add constraint fk_vehicle_inspection_notific_42 foreign key (notification_id) references renewal_notification (id) on delete restrict on update restrict;
create index ix_vehicle_inspection_notific_42 on vehicle_inspection (notification_id);
alter table vehicleModel add constraint fk_vehicleModel_vehicleBrand_43 foreign key (vehicle_brand_id) references vehicleBrand (id) on delete restrict on update restrict;
create index ix_vehicleModel_vehicleBrand_43 on vehicleModel (vehicle_brand_id);
alter table vehicle_registration add constraint fk_vehicle_registration_regis_44 foreign key (registration_holder_id) references owner (id) on delete restrict on update restrict;
create index ix_vehicle_registration_regis_44 on vehicle_registration (registration_holder_id);
alter table vehicle_registration add constraint fk_vehicle_registration_vehic_45 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_vehicle_registration_vehic_45 on vehicle_registration (vehicle_id);
alter table vehicle_registration add constraint fk_vehicle_registration_notif_46 foreign key (notification_id) references renewal_notification (id) on delete restrict on update restrict;
create index ix_vehicle_registration_notif_46 on vehicle_registration (notification_id);
alter table work_order add constraint fk_work_order_driver_47 foreign key (driver_id) references employee (id) on delete restrict on update restrict;
create index ix_work_order_driver_47 on work_order (driver_id);
alter table work_order add constraint fk_work_order_vehicle_48 foreign key (vehicle_id) references vehicle (id) on delete restrict on update restrict;
create index ix_work_order_vehicle_48 on work_order (vehicle_id);
alter table work_order add constraint fk_work_order_client_49 foreign key (client_id) references client (id) on delete restrict on update restrict;
create index ix_work_order_client_49 on work_order (client_id);



alter table VehicleDescription add constraint fk_VehicleDescription_descrip_01 foreign key (descriptionId) references description (id) on delete restrict on update restrict;

alter table VehicleDescription add constraint fk_VehicleDescription_vehicle_02 foreign key (vehicleId) references vehicle (id) on delete restrict on update restrict;

alter table mnts_services add constraint fk_mnts_services_maintenance_01 foreign key (maintenanceId) references maintenance (id) on delete restrict on update restrict;

alter table mnts_services add constraint fk_mnts_services_service_02 foreign key (serviceId) references service (id) on delete restrict on update restrict;

alter table VehiclesServiceNotifications add constraint fk_VehiclesServiceNotificatio_01 foreign key (serviceNotificationSettingsId) references service_notification_settings (id) on delete restrict on update restrict;

alter table VehiclesServiceNotifications add constraint fk_VehiclesServiceNotificatio_02 foreign key (vehicleId) references vehicle (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists client;

drop table if exists currencyy;

drop table if exists description;

drop table if exists VehicleDescription;

drop table if exists driver;

drop table if exists employee;

drop table if exists fleet;

drop table if exists fuelBill;

drop table if exists fuel_type;

drop table if exists insurance;

drop table if exists issue;

drop table if exists maintenance;

drop table if exists mnts_services;

drop table if exists measurement_unit;

drop table if exists notification_settings;

drop table if exists owner;

drop table if exists part;

drop table if exists partCategory;

drop table if exists part_location;

drop table if exists renewal_notification;

drop table if exists reset_password;

drop table if exists route;

drop table if exists service;

drop table if exists service_notification;

drop table if exists service_notification_settings;

drop table if exists VehiclesServiceNotifications;

drop table if exists task;

drop table if exists technicalInfo;

drop table if exists tires;

drop table if exists train;

drop table if exists trainComposition;

drop table if exists travel_order;

drop table if exists truckComposition;

drop table if exists type;

drop table if exists vehicle;

drop table if exists vehicleBrand;

drop table if exists vehicle_inspection;

drop table if exists vehicleModel;

drop table if exists vehicle_registration;

drop table if exists vehicleWarranty;

drop table if exists vendor;

drop table if exists warehouseWorkOrder;

drop table if exists work_order;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists client_seq;

drop sequence if exists currencyy_seq;

drop sequence if exists description_seq;

drop sequence if exists driver_seq;

drop sequence if exists employee_seq;

drop sequence if exists fleet_seq;

drop sequence if exists fuelBill_seq;

drop sequence if exists fuel_type_seq;

drop sequence if exists insurance_seq;

drop sequence if exists issue_seq;

drop sequence if exists maintenance_seq;

drop sequence if exists measurement_unit_seq;

drop sequence if exists notification_settings_seq;

drop sequence if exists owner_seq;

drop sequence if exists part_seq;

drop sequence if exists partCategory_seq;

drop sequence if exists part_location_seq;

drop sequence if exists renewal_notification_seq;

drop sequence if exists reset_password_seq;

drop sequence if exists route_seq;

drop sequence if exists service_seq;

drop sequence if exists service_notification_seq;

drop sequence if exists service_notification_settings_seq;

drop sequence if exists task_seq;

drop sequence if exists technicalInfo_seq;

drop sequence if exists tires_seq;

drop sequence if exists train_seq;

drop sequence if exists trainComposition_seq;

drop sequence if exists travel_order_seq;

drop sequence if exists truckComposition_seq;

drop sequence if exists type_seq;

drop sequence if exists vehicle_seq;

drop sequence if exists vehicleBrand_seq;

drop sequence if exists vehicle_inspection_seq;

drop sequence if exists vehicleModel_seq;

drop sequence if exists vehicle_registration_seq;

drop sequence if exists vehicleWarranty_seq;

drop sequence if exists vendor_seq;

drop sequence if exists warehouseWorkOrder_seq;

drop sequence if exists work_order_seq;

