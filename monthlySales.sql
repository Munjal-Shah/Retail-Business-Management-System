drop package monthlySales;

create or replace package monthlySales as
type ref_cursor is ref cursor;
	function report_monthly_sales(p_id in products.pid%type)
		return ref_cursor;
end monthlySales;
/
 
create or replace package body monthlySales as
	function report_monthly_sales(p_id in products.pid%type)
		return ref_cursor is
		RS ref_cursor;
	begin
		open RS for
		select p.pname,to_char(s.ptime,'MON'), to_char(s.ptime,'yyyy'), sum(s.qty), sum(s.total_price), sum(s.total_price)/sum(s.qty) averageSale from products p, purchases s where s.pid = p_id and s.pid = p.pid group by to_char(s.ptime,'MON'),to_char(s.ptime,'yyyy'), p.pname order by p.pname;
		return RS;
	end;
end monthlySales;
/