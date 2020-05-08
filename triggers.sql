create or replace trigger purchase_insert
after insert on purchases
for each row
begin
	insert into logs values(log#_seq.nextval, user, 'insert',  'purchases', :new.pur#);
end;
/


create or replace trigger product_qoh_update
after update of qoh on products
for each row
begin
	insert into logs values(log#_seq.nextval, user, 'update', 'products', :new.pid);
end;
/


create or replace trigger purchase_visits_update
after update of visits_made on customers
for each row
begin
	insert into logs values(log#_seq.nextval, user, 'update', 'customers', :new.cid);
end;
/


create or replace trigger supply_insert
after insert on supply
for each row
begin
	insert into logs values(log#_seq.nextval, user, 'insert', 'supply', :new.sup#);
end;
/


create or replace trigger after_purchase
after insert on purchases
for each row
declare
	q_oh number(5);
	q_oh_threshold number(5);
	M number(5);
	quantity number(5);
	final_qoh number(5);
	s_id char(2);
	new_date date;
	visits number(4);
begin
	select qoh into q_oh from products where pid = :new.pid;
	select qoh_threshold into q_oh_threshold from products where pid = :new.pid;
	if q_oh < q_oh_threshold then
		M := q_oh_threshold - q_oh + 1;
		quantity := M + q_oh + 5;
		final_qoh := q_oh + quantity;
		select sid into s_id from supply where pid = :new.pid and rownum = 1 order by sid;
		insert into supply values(sup#_seq.nextval, :new.pid, s_id, sysdate, quantity);
		update products set qoh = final_qoh where pid = :new.pid;
		dbms_output.put_line('new qoh = ' || final_qoh);
	end if;
	
	select last_visit_date into new_date from customers where cid = :new.cid;
	select visits_made into visits from customers where cid = :new.cid;
	if new_date != sysdate then
		visits := visits + 1;
		update customers set last_visit_date = to_char(sysdate, 'DD-MON-YY') where cid = :new.cid;
		update customers set visits_made = visits where cid = :new.cid;
	end if;	
end;
/