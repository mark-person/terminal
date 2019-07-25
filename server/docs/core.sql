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




















create table core_store (
	store_no 		varchar(5) not null,
	store_name  	varchar(32) not null,
	store_address 	varchar(250),
	store_lng		decimal (13,10) comment '经度，范围为-180~180',
	store_lat		decimal (13,10) comment '纬度，范围为-90~90',
	store_phone		varchar(32),
	primary key(store_no)
);

create table core_locker (
	locker_no	varchar(5) not null,
	primary key(locker_no)
);
















