

题目:买4免1,价低者免
表:
create table test_cart (
	sku_id int not null,
	sku_num int not null,
	sku_price decimal(7, 2) not null,
	primary key (sku_id)
);
问题 :一个SQL计算出总价




CREATE TABLE `test_p` (
  `item_id` int(11) NOT NULL,
  `policy_type` varchar(1) DEFAULT NULL,
  `policy_group` int(11) DEFAULT NULL,
  `policy_info` varchar(64) DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  `p` decimal(7,2) DEFAULT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk






select sum(num), policy_group, min(p), sum(num * p),
case policy_group 
when 100 then sum(num) div 3 * 10 + sum(num) mod 3 * min(p) -- (10元3件)
when 101 then sum(num * p) - sum(num) div 2 * max(p) -- (加一元多一件)
else -1 end D
from test_p group by policy_group






select @su:= 0;
select t.*, t.n - 2 from (
select test_p.*, @su:=num + @su n from test_p order by p) t where n > 2




select t.* from (
select test_p.*, @r := (@su := num + @su) - @di  n, if(@r <= 0, 0, if(num - @r <= 0, num, @r)) r from test_p, 
(select @su:=0) tt, (select @di := sum(num) div 4 from test_p) ttt order by p) t









N:3,Y:10(10元3件)
+1(加一元多一件)
B:4,F:1(买4免1)
D:0.5(5折)
D:0.9,2:0.8(单件9折，第二件8折)
D:0.9,2+:0.8(单件9折，第二件及以上8折)
1:0.9,2:0.8(第一件9折，任两件8折)
1:0.9,2+:0.8(第一件9折，任两件及以上8折)
E:10,-3(满10元立减3元)
* 价低者免，价低者折


