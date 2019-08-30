

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


分类{"brain":"头脑风暴","tech":"技术","busi":"业务"}  -- 其它说明

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













