drop table employees cascade constraints;
create table employees
(eid char(3) primary key,
ename varchar2(15),
telephone# char(12));


drop table customers cascade constraints;
create table customers
(cid char(4) primary key,
cname varchar2(15),
telephone# char(12),
visits_made number(4),
last_visit_date date);


drop table products cascade constraints;
create table products
(pid char(4) primary key,
pname varchar2(15),
qoh number(5),
qoh_threshold number(4),
original_price number(6,2),
discnt_rate number(3,2) check(discnt_rate between 0 and 0.8));


drop table purchases cascade constraints;
create table purchases
(pur# number(6) primary key,
eid char(3) references employees(eid),
pid char(4) references products(pid),
cid char(4) references customers(cid),
qty number(5),
ptime date,
total_price number(7,2));


drop table suppliers cascade constraints;
create table suppliers
(sid char(2) primary key,
sname varchar2(15) not null unique,
city varchar2(15),
telephone# char(12));


drop table supply cascade constraints;
create table supply
(sup# number(4) primary key,
pid char(4) references products(pid),
sid char(2) references suppliers(sid),
sdate date,
quantity number(5));


drop table logs cascade constraints;
create table logs
(log# number(5) primary key,
who varchar2(12) not null,
table_name varchar2(20) not null,
operation varchar2(10) not null,
key_value varchar2(6)); 






insert into employees values ('e01', 'Peter', '666-555-1234');
insert into employees values ('e02', 'David', '777-555-2341');
insert into employees values ('e03', 'Susan', '888-555-3412');
insert into employees values ('e04', 'Anne', '666-555-4123');
insert into employees values ('e05', 'Mike', '444-555-4231');

insert into customers values ('c001', 'Kathy', '666-555-4567', 3, '12-MAR-20');
insert into customers values ('c002', 'John', '888-555-7456', 1, '08-MAR-20');
insert into customers values ('c003', 'Chris', '666-555-6745', 3, '18-FEB-20');
insert into customers values ('c004', 'Mike', '999-555-5674', 1, '20-MAR-20');
insert into customers values ('c005', 'Mike', '777-555-4657', 2, '30-JAN-20');
insert into customers values ('c006', 'Connie', '777-555-7654', 2, '16-MAR-20');
insert into customers values ('c007', 'Katie', '888-555-6574', 1, '12-MAR-20');
insert into customers values ('c008', 'Joe', '666-555-5746', 1, '18-MAR-20');

insert into products values ('p001', 'stapler', 60, 20, 9.99, 0.1);
insert into products values ('p002', 'TV', 6, 5, 249, 0.15);
insert into products values ('p003', 'camera', 20, 5, 148, 0.2);
insert into products values ('p004', 'pencil', 100, 10, 0.99, 0.0);
insert into products values ('p005', 'chair', 10, 8, 12.98, 0.3);
insert into products values ('p006', 'lamp', 10, 6, 19.95, 0.1);
insert into products values ('p007', 'tablet', 50, 10, 149, 0.2);
insert into products values ('p008', 'computer', 5, 3, 499, 0.3);
insert into products values ('p009', 'powerbank', 20, 5, 49.95, 0.1);

insert into purchases values (100001, 'e01', 'p002', 'c001', 1, to_date('12-JAN-2020 10:34:30', 'DD-MON-YYYY HH24:MI:SS'), 211.65);
insert into purchases values (100002, 'e01', 'p003', 'c001', 1, to_date('20-FEB-2020 11:23:36', 'DD-MON-YYYY HH24:MI:SS'), 118.40);
insert into purchases values (100003, 'e02', 'p004', 'c002', 5, to_date('08-MAR-2020 09:30:50', 'DD-MON-YYYY HH24:MI:SS'), 4.95);
insert into purchases values (100004, 'e01', 'p005', 'c003', 2, to_date('23-FEB-2020 16:23:35', 'DD-MON-YYYY HH24:MI:SS'), 18.17);
insert into purchases values (100005, 'e04', 'p007', 'c004', 1, to_date('20-MAR-2020 13:38:55', 'DD-MON-YYYY HH24:MI:SS'), 119.20);
insert into purchases values (100006, 'e03', 'p008', 'c001', 1, to_date('12-MAR-2020 15:22:10', 'DD-MON-YYYY HH24:MI:SS'), 349.30);
insert into purchases values (100007, 'e03', 'p006', 'c003', 2, to_date('10-FEB-2020 17:12:20', 'DD-MON-YYYY HH24:MI:SS'), 35.91);
insert into purchases values (100008, 'e03', 'p006', 'c005', 1, to_date('16-JAN-2020 12:22:15', 'DD-MON-YYYY HH24:MI:SS'), 17.96);
insert into purchases values (100009, 'e03', 'p001', 'c007', 1, to_date('12-MAR-2020 14:44:23', 'DD-MON-YYYY HH24:MI:SS'), 8.99);
insert into purchases values (100010, 'e04', 'p002', 'c006', 1, to_date('20-JAN-2020 17:32:37', 'DD-MON-YYYY HH24:MI:SS'), 211.65);
insert into purchases values (100011, 'e02', 'p004', 'c006', 10, to_date('16-MAR-2020 16:54:40', 'DD-MON-YYYY HH24:MI:SS'), 9.90);
insert into purchases values (100012, 'e02', 'p008', 'c003', 2, to_date('18-FEB-2020 15:56:38', 'DD-MON-YYYY HH24:MI:SS'), 698.60);
insert into purchases values (100013, 'e04', 'p006', 'c005', 2, to_date('30-JAN-2020 10:38:25', 'DD-MON-YYYY HH24:MI:SS'), 35.91);
insert into purchases values (100014, 'e03', 'p009', 'c008', 3, to_date('18-MAR-2020 10:54:06', 'DD-MON-YYYY HH24:MI:SS'), 134.84);

insert into suppliers values ('s1', 'Walmart', 'Vestal', '607-798-1011');
insert into suppliers values ('s2', 'Wegmans', 'Johnson City', '607-729-7782');
insert into suppliers values ('s3', 'Target', 'Vestal', '607-729-6611');

insert into supply values (1000, 'p001', 's3', '20-JAN-20', 61);
insert into supply values (1001, 'p002', 's1', '02-JAN-20', 8);
insert into supply values (1002, 'p003', 's1', '14-FEB-20', 21);
insert into supply values (1003, 'p004', 's3', '05-MAR-20', 115);
insert into supply values (1004, 'p005', 's2', '06-JAN-20', 8);
insert into supply values (1005, 'p006', 's2', '10-JAN-20', 15);
insert into supply values (1006, 'p007', 's3', '15-MAR-20', 51);
insert into supply values (1007, 'p008', 's1', '12-MAR-20', 8);
insert into supply values (1008, 'p009', 's2', '14-MAR-20', 23);
insert into supply values (1009, 'p005', 's1', '23-JAN-20', 4);