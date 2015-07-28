# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

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
  constraint pk_truck primary key (id))
;

create sequence train_seq;

create sequence truck_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists train;

drop table if exists truck;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists train_seq;

drop sequence if exists truck_seq;

