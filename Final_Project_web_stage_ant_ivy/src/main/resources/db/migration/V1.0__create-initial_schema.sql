/*drop table if exists order_books cascade;
drop table if exists order_table cascade;
drop table if exists user_table cascade;
drop table if exists book_table cascade;*/

create table user_table (
  user_id       bigserial not null primary key,
  first_name    varchar(255),
  last_name     varchar(255),
  user_email    varchar(255),
  user_login    varchar(255),
  user_password varchar(255),
  user_role     varchar(255),
  user_blocked  boolean);

create table book_table (
  book_id      bigserial not null  primary key,
  book_title   varchar(255),
  quantity     int4,
  version      int8 not null);

 create table order_table (
  order_id       bigserial not null primary key,
  book_returned  boolean,
  order_date     date,
  reading_place  varchar(255),
  returning_date date,
  user_id        int8,
  foreign key (user_id) references user_table (user_id));

create table order_books (
  order_books_id  bigserial not null primary key,
  order_id        int8 not null,
  book_id         int8 not null,
  foreign key (book_id) references book_table (book_id),
  foreign key (order_id) references order_table (order_id));



