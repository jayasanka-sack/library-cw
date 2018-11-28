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
  books_isbn                    integer not null,
  constraint pk_author_books primary key (author_id,books_isbn)
);

create table books (
  isbn                          integer auto_increment not null,
  name                          varchar(255),
  pagecount                     integer not null,
  borrow_date                   datetime(6),
  reader                        integer,
  constraint pk_books primary key (isbn)
);

create table dvds (
  isbn                          integer auto_increment not null,
  name                          varchar(255),
  langages                      varchar(255),
  borrow_date                   datetime(6),
  reader                        integer,
  publisher                     integer,
  constraint pk_dvds primary key (isbn)
);

create table publisher (
  id                            integer auto_increment not null,
  name                          varchar(255),
  constraint pk_publisher primary key (id)
);

create table reader (
  id                            integer auto_increment not null,
  name                          varchar(255),
  mobile                        varchar(255),
  email                         varchar(255),
  constraint pk_reader primary key (id)
);

alter table author_books add constraint fk_author_books_author foreign key (author_id) references author (id) on delete restrict on update restrict;
create index ix_author_books_author on author_books (author_id);

alter table author_books add constraint fk_author_books_books foreign key (books_isbn) references books (isbn) on delete restrict on update restrict;
create index ix_author_books_books on author_books (books_isbn);

alter table books add constraint fk_books_reader foreign key (reader) references reader (id) on delete restrict on update restrict;
create index ix_books_reader on books (reader);

alter table dvds add constraint fk_dvds_reader foreign key (reader) references reader (id) on delete restrict on update restrict;
create index ix_dvds_reader on dvds (reader);

alter table dvds add constraint fk_dvds_publisher foreign key (publisher) references publisher (id) on delete restrict on update restrict;
create index ix_dvds_publisher on dvds (publisher);


# --- !Downs

alter table author_books drop foreign key fk_author_books_author;
drop index ix_author_books_author on author_books;

alter table author_books drop foreign key fk_author_books_books;
drop index ix_author_books_books on author_books;

alter table books drop foreign key fk_books_reader;
drop index ix_books_reader on books;

alter table dvds drop foreign key fk_dvds_reader;
drop index ix_dvds_reader on dvds;

alter table dvds drop foreign key fk_dvds_publisher;
drop index ix_dvds_publisher on dvds;

drop table if exists author;

drop table if exists author_books;

drop table if exists books;

drop table if exists dvds;

drop table if exists publisher;

drop table if exists reader;

