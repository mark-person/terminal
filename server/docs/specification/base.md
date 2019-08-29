

数据库字段备注如何写?


主名;select main_id, main_name from core_demo_main -- 其它说明
分类 {brain:'头脑风暴',tech:'技术',busi:'业务'}  -- 其它说明
规则名字;select sub_id, sub_name from core_demo_sub where main_id = ?;select main_id, main_name from core_demo_main -- 其它说明

类型;NEW:新的,OLD:旧的 -- 其它说明


* 测试用的表前缀test_
* 知识库用的表前缀repo_
* 统一返回错误码






