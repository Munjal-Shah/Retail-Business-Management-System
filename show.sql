drop package show;

create or replace package show as
	procedure show_employees(emp out sys_refcursor);
	procedure show_customers(cus out sys_refcursor);
	procedure show_products(pro out sys_refcursor);
	procedure show_purchases(pur out sys_refcursor);
	procedure show_suppliers(sup out sys_refcursor);
	procedure show_supply(supp out sys_refcursor);
	procedure show_logs(lgs out sys_refcursor);
end show;
/

create or replace package body show as
	procedure show_employees(emp out sys_refcursor) is 
	begin 
		open emp for
		select * from employees;
	end show_employees;
	
	procedure show_customers(cus out sys_refcursor) is 
	begin 
		open cus for
		select * from customers;
	end show_customers;
	
	procedure show_products(pro out sys_refcursor) is 
	begin 
		open pro for
		select * from products;
	end show_products;
	
	procedure show_purchases(pur out sys_refcursor) is 
	begin 
		open pur for
		select * from purchases;
	end show_purchases;
	
	procedure show_suppliers(sup out sys_refcursor) is 
	begin 
		open sup for
		select * from suppliers;
	end show_suppliers;
	
	procedure show_supply(supp out sys_refcursor) is 
	begin 
		open supp for
		select * from supply;
	end show_supply;
	
	procedure show_logs(lgs out sys_refcursor) is 
	begin 
		open lgs for
		select * from logs;
	end show_logs;
end show;
/