create table reader(
name varchar(100) not null, 
phone number not null,
gender varchar(1) not null,
username varchar(50) primary key,
password varchar(100) not null,
dob date not null,
age number,
email varchar(100) not null);

create table books(
name varchar(100) not null,
author varchar(100) not null,
isbn number primary key,
edition varchar(10),
publisher varchar(50),
genre varchar(50),
age number not null);

create table elibrary(
book_isbn number not null,
book_path clob,
book_cover blob);

create table log(
username varchar(50) not null,
book_isbn number not null,
session_begin timestamp not null,
session_end timestamp not null,
duration number);

create table ratingtable(
username varchar(50) not null,
book_isbn number not null,
rating number,
PRIMARY KEY (username,book_isbn));

alter table elibrary add constraint fk_1 foreign key(book_isbn) references books(isbn) on delete cascade;
alter table log add constraint fk_2 foreign key(username) references reader(username);

create or replace trigger age_calc
before insert
on reader 
for each row
Begin
	:new.age := floor(months_between(sysdate,:new.DOB)/12);
end;

create or replace trigger duration_calc
before insert
on log 
for each row
Begin
	:new.duration := (cast(:new.session_end as date)-cast(:new.session_begin as date))*24*60;
end;

-------------------------------------------------------------------------------------------------------------------------------
+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+
-------------------------------------------------------------------------------------------------------------------------------
select (session_end - session_begin) from log;
select extract(second from (session_end - session_begin)) from log;
select extract(minute from (session_end - session_begin)) from log;
select extract(hour from (session_end - session_begin)) from log;
select extract(day from (session_end - session_begin)) from log;
select (cast(session_end as date)-cast(session_begin as date))*24*60*60 from log;

select * 
from log 
where session_begin >= to_timestamp('14-04-2020 12:00:00', 'dd-mm-yyyy hh24:mi:ss')
  and session_end <= to_timestamp('14-04-2020 14:00:00', 'dd-mm-yyyy hh24:mi:ss');
  
  
  
  
  
  
  
  
  
  
  update books set rating =(select sum(rating)/count(rating) 
									from ratingtable 
									where book_isbn=1234567890  
									group by book_isbn)
	where isbn=1234567890;
	
	