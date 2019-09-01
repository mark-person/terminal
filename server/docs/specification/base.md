

数据库字段备注如何写?


主名;select main_id, main_name from core_demo_main -- 其它说明
分类 {brain:'头脑风暴',tech:'技术',busi:'业务'}  -- 其它说明
规则名字;select sub_id, sub_name from core_demo_sub where main_id = ?;select main_id, main_name from core_demo_main -- 其它说明

类型;NEW:新的,OLD:旧的 -- 其它说明


* 测试用的表前缀test_
* 知识库用的表前缀repo_
* 统一返回错误码

* 服务器tomcat CPU高或慢 怎么处理
* 数据库mysql CPU高或慢 怎么处理

-- nosql 替代 sql
-- 字段 可以使用->或->>
select @sex:=JSON_OBJECT('1','男', '0','女', '2','中性人', 'hidden', '2');
select test_id, JSON_UNQUOTE(JSON_EXTRACT(@sex, concat('$.', sex))) sex from test;



怎么设计数据字典？
例如
性别,1:男 0:女
状态,0:失败 1:成功

父类|select * from xxx|  -- 其它说明

分类|{"brain":"头脑风暴","tech":"技术","busi":"业务"}|["brain"] -- 其它说明

{"dict":"分类","brain":"头脑风暴","tech":"技术","busi":"业务"}



DELIMITER $$ 
create function myselect12() returns varchar(10)
begin
declare c varchar(10); 

-- IF @a is null THEN 

select @a:=concat('abc', @a) into c;
-- END IF;

 return @a;
end;
$$

select @a:='';

select myselect12(), myselect12(),myselect12()





DELIMITER $$ 
create function myselect17(v varchar(10), vv varchar(10)) returns varchar(10)
begin
declare c varchar(10); 

IF v is null THEN
select concat('abc', vv) into v;
END IF;

 return v;
end;
$$
-- 保存只运行一次
select @a:=myselect17(@a, 'xxxs') , @a:=myselect17(@a, 'xxxs') 



性别;{"1":"男","0":"女"} -- 其它说明





 select json_extract(substring_index(column_comment, '|', 1), '$.tech')
 from information_schema.columns where table_name = 'core_faq' and column_name = 'faq_category';
 
 
 >>>>>>>>>>改成传字段值，用临时变量v
 
DELIMITER $$ 
create function getDict7(v json, tableName varchar(64), columnName varchar(64)) returns json
begin
declare c json; 

IF v is null then
 select substring_index(column_comment, '|', 1)
 from information_schema.columns where table_name = tableName and column_name = columnName into v;
end if;

return v;
end;
$$
 
select json_extract(getDict7(@v, 'core_faq', 'faq_category'), '$.tech'),




 
DELIMITER $$ 
create function getDict12(v json, tableName varchar(64), columnName varchar(64), columnValue varchar(64)) returns json
begin
declare c json; 

if v is null then
	update test set TEST_VALUE = TEST_VALUE + 1   where test_id = 1;

	select substring_index(column_comment, '|', 1)
	from information_schema.columns where table_name = tableName and column_name = columnName into v;
end if;

return json_extract(v, concat('$.', columnValue));
end;
$$
 
select getDict12(@v, 'core_faq', 'faq_category', 'tech') a, getDict12(@v, 'core_faq', 'faq_category', 'brain') b




>>>最新v1
select core_faq.*, getD1(@d, faq_category) cat_descr
from core_faq,
 (select @d:= getDict5('core_faq', 'faq_category')) d
 
 DELIMITER $$ 
create function getD1(j varchar(256), columnValue varchar(64)) returns varchar(64)
begin
return  json_extract(j, concat('$.', columnValue));
end;
$$

DELIMITER $$ 
create function getDict5( tableName varchar(64), columnName varchar(64)) returns json
begin
declare c json; 


	select  substring_index(column_comment, '|', 1)
	from information_schema.columns where table_name = tableName and column_name = columnName into c;


return  c; -- json_extract(v, concat('$.', columnValue));
end;
$$









