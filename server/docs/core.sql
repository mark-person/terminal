

/* 
 * 有意义的主键_no varchar(5) not null
 * 业务主键用_id int not null auto_increment
 * 
 * 业务表状态用tinyint
 * 维表状态用varchar(5) not null,用有意思的英语大写
 * 
 * 
 * 
 * */

create table core_demo_main (
	main_id 	int not null auto_increment comment '主ID',
	main_name 	varchar(32) not null comment '主名',
	primary key (main_id)
) comment '主表';

create table core_demo_sub (
	 sub_id 		int not null auto_increment comment '规则ID',
	 main_id		int not null comment '主名;select main_id, main_name from core_demo_main',
	 sub_name		varchar(32) not null comment '规则名称',
	 primary key (sub_id)
) comment '从表';




create table core_demo (
  demo_id 		int not null auto_increment comment 'ID--其它说明',
  sub_id		int comment '规则名字;select sub_id, sub_name from core_demo_sub where main_id = ?;select main_id, main_name from core_demo_main',
  demo_name 	varchar(32) not null comment '标题',
  demo_date 	date comment '日期',
  demo_type		varchar(5) comment '类型;NEW:新的,OLD:旧的',
  demo_int		int comment '数量',
  demo_num	 	decimal(7,2) comment '金额',
  created		timestamp not null default current_timestamp,
  primary key (demo_id)
) comment '样例';
/** 如果是invisible，这样优化器就会忽略这个索引，但是索引依然存在于引擎内部 */
ALTER TABLE core_demo ADD INDEX idx_demo_name (demo_name ASC) VISIBLE;

create table core_db_test (
  test_id 		int not null auto_increment comment 'ID;其它说明',
  test_name 	varchar(32) not null comment '名称',
  test_date 	date comment '日期',
  primary key (test_id)
) comment 'DB样例';

















create table core_store (
	store_no 		varchar(5) not null,
	store_name  	varchar(32) not null,
	store_address 	varchar(250),
	store_lng		varchar(13) comment '经度，范围为-180~180',
	store_lat		varchar(13) comment '纬度，范围为-90~90',
	store_phone		varchar(32),
	primary key(store_no)
);



create table core_locker (
	locker_no	varchar(5) not null,
	locker_desc varchar(64),
	primary key(locker_no)
);
















