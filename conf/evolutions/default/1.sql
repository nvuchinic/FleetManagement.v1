# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table vehicle (
  id                        integer not null,
  make                      varchar(255),
  model                     varchar(255),
  year                      varchar(255),
  constraint pk_vehicle primary key (id))
;

create sequence vehicle_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists vehicle;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists vehicle_seq;

