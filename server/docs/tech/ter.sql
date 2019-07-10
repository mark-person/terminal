
create table ter_log_start (
	service_id 		varchar(32) not null,
	service_tag  	varchar(32) not null,
	start_time	 	timestamp not null default current_timestamp,
	primary key (service_id)
);

create table ter_log_heart (
  client_id 	varchar(10) not null,
  heart_time 	timestamp not null default current_timestamp,
  heart_count	int not null,
  service_id	varchar(32) not null,
  primary key (client_id, heart_time)
);

create table ter_log_login (
	client_id 		varchar(10) not null,
	login_time		timestamp not null default current_timestamp,
	login_status 	varchar(3) not null comment 'SUC:成功,E-M:消息错误,E-P:密码错误',
	service_id		varchar(32) not null,
	primary key(client_id, login_time)
);

create table ter_log_connect (
	connect_id 		int not null auto_increment,
	service_id		varchar(32) not null,
	connect_time	timestamp not null default current_timestamp,
	connect_status 	varchar(3) not null comment 'REG:注册,E-E:异常,E-I:无效',
	connect_msg		varchar(250),
	primary key(connect_id)
);

create table ter_log_command (
	command_id		 int not null auto_increment,
	service_id		 varchar(32) not null,
	command_time	 timestamp not null default current_timestamp,
	client_id 		 varchar(32) not null,
	command_content  varchar(64) not null,
	command_return   varchar(64),
	command_error	 varchar(250),
	primary key(command_id)
);

create table ter_client_login_service (
	client_id 		 varchar(32) not null,
	service_id 	 	 varchar(32) not null,
	login_time		 timestamp not null default current_timestamp,
	primary key(client_id)
);


create table ter_client (
	client_id		varchar(10) not null,
	client_password varchar(32) not null,
	primary key(client_id)
);
/* 每个终端不一样的密码，防止每多个终端使用相同的client_id登录 */
ALTER TABLE ter_client ADD UNIQUE INDEX idx_ter_client_password (client_password ASC) VISIBLE;

create table ter_cell (
	client_id	varchar(10) not null,
	cell_id		varchar(10) not null,
	cell_bit	varchar(16) not null,
	cell_code	varchar(8),
	primary key(client_id, cell_id)
);
alter table ter_cell add unique index idx_ter_cell_bit (cell_bit asc) visible,
	add unique index idx_ter_cell_code (cell_code asc) visible;

	
/**
 * USE_CODE_INDEX
 * select FLOOR(10000000 + RAND() * (100000000 - 10000000))
 */
create table ter_config (
	config_name  varchar(32),
	config_int   int not null,
	primary key(config_name)
);

create table ter_random_code (
	code_index 	int not null auto_increment,
	cell_code 	varchar(8) not null,
	primary key(code_index)
);
alter table ter_random_code add unique index idx_ter_random_cell_code (cell_code asc) visible;



	
	
	
	
	
	














