# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table board (
  id                            integer auto_increment not null,
  name                          varchar(255),
  owner_user_id                 integer,
  status                        varchar(1),
  is_private                    boolean,
  constraint ck_board_status check ( status in ('A','D','V')),
  constraint pk_board primary key (id)
);

create table card (
  id                            integer auto_increment not null,
  name                          varchar(255),
  due_date                      timestamp,
  description                   varchar(512),
  parent_listt_id               integer,
  row                           integer not null,
  status                        varchar(1),
  constraint ck_card_status check ( status in ('A','D','V')),
  constraint pk_card primary key (id)
);

create table comment (
  id                            integer auto_increment not null,
  name                          varchar(255),
  text                          varchar(255),
  parent_card_id                integer,
  constraint pk_comment primary key (id)
);

create table listt (
  id                            integer auto_increment not null,
  name                          varchar(255),
  parent_board_id               integer,
  column                        integer not null,
  status                        varchar(1),
  constraint ck_listt_status check ( status in ('A','D','V')),
  constraint pk_listt primary key (id)
);

create table task (
  id                            integer auto_increment not null,
  name                          varchar(255),
  parent_card_id                integer,
  done                          boolean default false not null,
  constraint pk_task primary key (id)
);

create table team (
);

create table user (
  id                            integer auto_increment not null,
  name                          varchar(255),
  auth_token                    varchar(255),
  email_address                 varchar(256) not null,
  sha_password                  varbinary(64) not null,
  full_name                     varchar(256) not null,
  creation_date                 timestamp not null,
  constraint uq_user_email_address unique (email_address),
  constraint pk_user primary key (id)
);

alter table board add constraint fk_board_owner_user_id foreign key (owner_user_id) references user (id) on delete restrict on update restrict;
create index ix_board_owner_user_id on board (owner_user_id);

alter table card add constraint fk_card_parent_listt_id foreign key (parent_listt_id) references listt (id) on delete restrict on update restrict;
create index ix_card_parent_listt_id on card (parent_listt_id);

alter table comment add constraint fk_comment_parent_card_id foreign key (parent_card_id) references card (id) on delete restrict on update restrict;
create index ix_comment_parent_card_id on comment (parent_card_id);

alter table listt add constraint fk_listt_parent_board_id foreign key (parent_board_id) references board (id) on delete restrict on update restrict;
create index ix_listt_parent_board_id on listt (parent_board_id);

alter table task add constraint fk_task_parent_card_id foreign key (parent_card_id) references card (id) on delete restrict on update restrict;
create index ix_task_parent_card_id on task (parent_card_id);


# --- !Downs

alter table board drop constraint if exists fk_board_owner_user_id;
drop index if exists ix_board_owner_user_id;

alter table card drop constraint if exists fk_card_parent_listt_id;
drop index if exists ix_card_parent_listt_id;

alter table comment drop constraint if exists fk_comment_parent_card_id;
drop index if exists ix_comment_parent_card_id;

alter table listt drop constraint if exists fk_listt_parent_board_id;
drop index if exists ix_listt_parent_board_id;

alter table task drop constraint if exists fk_task_parent_card_id;
drop index if exists ix_task_parent_card_id;

drop table if exists board;

drop table if exists card;

drop table if exists comment;

drop table if exists listt;

drop table if exists task;

drop table if exists team;

drop table if exists user;

