

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

create table core_demo (
  demo_id 		int not null auto_increment comment 'DEMO_ID,',
  demo_name 	varchar(32) not null,
  demo_date 	date,
  demo_type		tinyint comment 'DEDO_TYPE:T1,T2,T3',
  demo_int		int,
  demo_num	 	decimal(7,2),
  created		timestamp not null default current_timestamp,
  primary key (demo_id)
);
/** 如果是invisible，这样优化器就会忽略这个索引，但是索引依然存在于引擎内部 */
ALTER TABLE core_demo ADD INDEX idx_demo_name (demo_name ASC) VISIBLE;



















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
















