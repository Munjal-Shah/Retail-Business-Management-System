drop package operations;

create or replace package operations as

	function add_purchase(p_id in char, e_id in char, c_id in char, pur_qty in number)
		return number;
		
	procedure add_product(p_id products.pid%type, p_name products.pname%type, q_oh products.qoh%type, q_oh_threshold products.qoh_threshold%type, o_price products.original_price%type, dis_rate products.discnt_rate%type, s_id suppliers.sid%type);
	
end operations;
/

create or replace package body operations as

	function add_purchase(p_id in char, e_id in char, c_id in char, pur_qty in number) 
		return number is q_oh number; discount_cat number; discount number(3,2); price number(6,2); total_pr number(6,2);
	begin
		Select qoh into q_oh from products where pid = p_id;
		if q_oh >= pur_qty then
			dbms_output.put_line('Looks Good');
			Select discnt_rate into discount from products where pid = p_id;
			Select original_price into price from products where pid = p_id;
			total_pr := price*(1-discount)*pur_qty;
			q_oh := q_oh - pur_qty;
			update products set qoh = q_oh where pid = p_id;
			insert into purchases values(pur#_seq.nextval, e_id, p_id, c_id, pur_qty, sysdate, total_pr);
			select qoh into q_oh from products where pid = p_id;
		else	
			q_oh := -123;
			dbms_output.put_line('Insufficient quantity in stock. ');
		end if;
		return q_oh;
	end;
	
	
	procedure add_product(p_id products.pid%type, p_name products.pname%type, q_oh products.qoh%type, q_oh_threshold products.qoh_threshold%type, o_price products.original_price%type, dis_rate products.discnt_rate%type, s_id suppliers.sid%type) is
	begin
		insert into products(pid, pname, qoh, qoh_threshold, original_price, discnt_rate) values(p_id, p_name, q_oh, q_oh_threshold, o_price, dis_rate);
		insert into supply values(sup#_seq.nextval, p_id, s_id, sysdate, q_oh);
		commit;
	end;
	
end operations;
/