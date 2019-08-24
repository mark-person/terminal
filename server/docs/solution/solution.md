

# 问题，解决方案



#
* 问题(mysql):买4免1,价低者免
* 举例:

```sql
create table test_cart (
	sku_id int not null comment 'SKU_ID',
	sku_num int not null comment '数量',
	sku_price decimal(7, 2) not null comment '单价',
	primary key (sku_id)
) comment '购物车';
```
<table border=1>
<tr>
<td bgcolor=silver class='medium'>sku_id</td>
<td bgcolor=silver class='medium'>sku_num</td>
<td bgcolor=silver class='medium'>sku_price</td>
</tr>

<tr>
<td class='normal' valign='top'>1</td>
<td class='normal' valign='top'>3</td>
<td class='normal' valign='top'>3.50</td>
</tr>

<tr>
<td class='normal' valign='top'>2</td>
<td class='normal' valign='top'>4</td>
<td class='normal' valign='top'>4.00</td>
</tr>

<tr>
<td class='normal' valign='top'>3</td>
<td class='normal' valign='top'>1</td>
<td class='normal' valign='top'>2.20</td>
</tr>

<tr>
<td class='normal' valign='top'>4</td>
<td class='normal' valign='top'>1</td>
<td class='normal' valign='top'>5.00</td>
</tr>
</table>


* 
共9件，免去价格最低2件, sku_id=3的sku_num-1，sku=1的sku_num-1
总价=2*3.50+4*4.00+0*2.20+1*5.00=28

参考答案:
```sql
select sum(sku_price * real_num) total_price from (
select e_cart.*, @r := (@minus := sku_num + @minus) - @minus_num n, if(@r <= 0, 0, if(sku_num - @r <= 0, sku_num, @r)) real_num from e_cart, (select @minus := 0) t0, (select @minus_num := sum(sku_num) div 4 from e_cart) t1 order by sku_price) t

```


