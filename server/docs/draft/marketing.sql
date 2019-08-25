

create table marketing_activity (
	activity_id		int not null auto_increment,
	activity_name	varchar(64) not null,
	activity_begin	date not null,
	activity_end	date not null,
	activity_status	varchar(5) not null,
	primary key(activity_id)
) comment '营销活动';

create table marketing_program (
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
create table marketing_program_type (
	program_type 		json not null,
	program_type_name 	varchar(64) not null,
	primary key(program_type)
) comment '营销方案类型';

create table program_target (
	program_id		int not null,
	target_id		int not null,
	primary key(program_id, target_id)
)

/**
 * ->活动启动后生成冗余数据，过期和作废的清除
 */
create table program_target_active (
	program_id		int not null,
	target_id		int not null,
	program_type 	json not null,
	program_prio	tinyint not null,
	activity_begin 	date not null comment '--冗余活动开始时间',
	activity_end	date not null comment '--冗余活动结束时间',
	primary key(program_id, target_id)
)



create table e_target (
	target_id		int not null auto_increment,
	target_type 	tinyint not null
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














