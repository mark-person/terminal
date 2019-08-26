

create table e_marketing_activity (
	activity_id		int not null auto_increment,
	activity_name	varchar(64) not null,
	activity_begin	date not null,
	activity_end	date not null,
	activity_status	varchar(5) not null,
	primary key(activity_id)
) comment '营销活动';

create table e_marketing_program (
	program_id		int not null auto_increment,
	activity_id		int not null,
	program_type 	json not null,
	program_name	varchar(64) not null,
	program_prio	tinyint not null,
	primary key(program_id)
) comment '营销方案';

/**
 * 10元均价 10元3件 加1元多1件 买4免1 满10元立减3元
 */
create table e_marketing_program_type (
	program_type 		json not null,
	program_type_name 	varchar(64) not null,
	primary key(program_type)
) comment '营销方案类型';

create table e_program_target (
	program_id		int not null,
	target_id		int not null,
	primary key(program_id, target_id)
)





/**
 * >>>>>>>>>>>>>>>>>>>>>>
 * ->活动启动后生成冗余数据，过期和作废的清除
 * 日期处理，生成日期对应的program_id
 */
create table e_program_target_active (
	program_id		int not null,
	target_id		int not null,
	program_type 	json not null,
	program_prio	tinyint not null,
	primary key(program_id, target_id)
) comment '方案';

create table e_program_target_data (
	activity_date	date not null comment '活动日期--活动启动时生成,9.1~9.3生成3个',
	program_id		int not null,
	primary key(activity_date, program_id)
) comment '方案日期--活动日期决定';


create table e_target (
	target_id		int not null auto_increment,
	target_type 	tinyint not null,
	primary key(target_id)
) comment '目标';

/**
 * target_type:类目、专题、品牌、群组、商品、SKU
 */
create table e_target_sku (
	target_id	int not null,
	sku_id		int not null,
	primary key(target_id, sku_id)
) comment '目标对应SKU';







/* 
需求：找出购物车每个SKU的营销策略(买4免1等)
解决群组问题：类目、专题、品牌、群组、商品、SKU对应的营销策略(买4免1等)
解决日期问题：从2019.8.26~2019.8.27
解决冲突问题: 冲突取营销策略优化级最高的
*/
select s.sku_id, substring_index(group_concat(a.program_type order by a.program_prio desc) , ',', 1) program_type
from 
(
select 1 sku_id
union 
select 3 sku_id) cart
left join e_target_sku s on cart.sku_id = s.sku_id
left join e_program_target_active a on a.target_id = s.target_id 
	and program_id in (select program_id from e_program_target_data where activity_date = '2019-08-26')
group by s.sku_id


























