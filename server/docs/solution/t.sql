

create table test_cart (
	sku_id int not null,
	sku_num int not null,
	sku_price decimal(7, 2) not null,
	primary key (sku_id)
);

select t.* from (
select test_cart.*, @r := (@minus := sku_num + @minus) - @minus_num n, if(@r <= 0, 0, if(sku_num - @r <= 0, sku_num, @r)) real_num from test_cart, 
(select @minus := 0) t0, (select @minus_num := sum(sku_num) div 4 from test_cart) t1 order by sku_price) t


select t.*, @group_sum := group_sum,
case @group_id := t.group_id
when 100 then
(
select sum(sku_price * real_num) price from (
select t.*, @r := (@minus := sku_num + @minus) - @group_sum n, if(@r <= 0, 0, if(sku_num - @r <= 0, sku_num, @r)) real_num 
from (select * from test_cart where group_id = @group_id) t, 
(select @minus := 0) t0 order by sku_price) t
) 
else -1 end D
from (select c.group_id, sum(c.sku_num) div 4 group_sum from test_cart c group by c.group_id) t




select t.*, @group_num := group_num,
case @group_id := t.group_id
when 100 then
(
select sum(sku_price * real_num) price from (
	select t.*, @r := (@minus := sku_num + @minus) - @group_num div 4 n, if(@r <= 0, 0, if(sku_num - @r <= 0, sku_num, @r)) real_num 
	from (select * from test_cart where group_id = @group_id) t, (select @minus := 0) t0 order by sku_price) t
) 
else -1 end D
from (select c.group_id, sum(c.sku_num) group_num from test_cart c group by c.group_id) t



-- 子查询不能套两层以上 ，上面代码有BUG，使用
select 'A', 100
union all
select 'B', 100



