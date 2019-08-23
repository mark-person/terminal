

create table test_cart (
	sku_id int not null,
	sku_num int not null,
	sku_price decimal(7, 2) not null,
	primary key (sku_id)
);

select t.* from (
select test_cart.*, @r := (@minus := sku_num + @minus) - @minus_num n, if(@r <= 0, 0, if(sku_num - @r <= 0, sku_num, @r)) r from test_cart, 
(select @minus := 0) t_minus, (select @minus_num := sum(sku_num) div 4 from test_cart) t_minus_num order by sku_price) t



