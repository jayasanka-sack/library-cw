# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table author (
  id                            integer auto_increment not null,
  name                          varchar(255),
  constraint pk_author primary key (id)
);

create table author_books (
  author_id                     integer not null,
  books_id                      integer not null,
  constraint pk_author_books primary key (author_id,books_id)
);

create table books (
  id                            integer auto_increment not null,
  name                          varchar(255),
  reader                        integer,
  constraint pk_books primary key (id)
);

create table dvds (
  id                            integer auto_increment not null,
  name                          varchar(255),
  reader                        integer,
  constraint pk_dvds primary key (id)
);

create table reader (
  id                            integer auto_increment not null,
  name                          varchar(255),
  constraint pk_reader primary key (id)
);

alter table author_books add constraint fk_author_books_author foreign key (author_id) references author (id) on delete restrict on update restrict;
create index ix_author_books_author on author_books (author_id);

alter table author_books add constraint fk_author_books_books foreign key (books_id) references books (id) on delete restrict on update restrict;
create index ix_author_books_books on author_books (books_id);

alter table books add constraint fk_books_reader foreign key (reader) references reader (id) on delete restrict on update restrict;
create index ix_books_reader on books (reader);

alter table dvds add constraint fk_dvds_reader foreign key (reader) references reader (id) on delete restrict on update restrict;
create index ix_dvds_reader on dvds (reader);


# --- !Downs

alter table author_books drop foreign key fk_author_books_author;
drop index ix_author_books_author on author_books;

alter table author_books drop foreign key fk_author_books_books;
drop index ix_author_books_books on author_books;

alter table books drop foreign key fk_books_reader;
drop index ix_books_reader on books;

alter table dvds drop foreign key fk_dvds_reader;
drop index ix_dvds_reader on dvds;

drop table if exists author;

drop table if exists author_books;

drop table if exists books;

drop table if exists dvds;

drop table if exists reader;

