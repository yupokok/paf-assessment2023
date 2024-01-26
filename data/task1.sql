-- Write your Task 1 answers in this file
create database bedandbreakfast;

use bedandbreakfast;

create table users (
   email varchar(128) not null,
   name varchar(128) not null,

   primary key(email)
);

create table bookings (
   booking_id char(8) not null, -- ObjectID
   listing_id varchar(20),
   duration int not null,
   email varchar(128) not null,

   primary key(booking_id),
   foreign key(email) references users(email)
);

create table reviews (
   id int auto_increment,
   date timestamp,
   listing_id varchar(20),
   reviewer_name varchar(64) not null,
   comments text,

   primary key(id)
);

grant all privileges on bedandbreakfast.* to fred@'%';

flush privileges;