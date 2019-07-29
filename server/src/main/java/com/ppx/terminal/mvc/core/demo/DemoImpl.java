package com.ppx.terminal.mvc.core.demo;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.ppx.terminal.common.controller.ControllerReturn;
import com.ppx.terminal.common.jdbc.MyCriteria;
import com.ppx.terminal.common.jdbc.MyDaoSupport;
import com.ppx.terminal.common.page.Page;

@Service
public class DemoImpl extends MyDaoSupport {

	public List<Demo> list(Page page, Demo pojo) {
		
		
		// 默认排序，后面加上需要从页面传过来的排序的，防止SQL注入
		// page.addDefaultOrderName("test_id").addPermitOrderName("test_price").addUnique("test_id");

		// 分开两条sql，mysql在count还会执行子查询, 总数返回0将不执行下一句
		
		//  mysql8支持order by和字查询count(*)优化，不支持left join优化
		MyCriteria c = createCriteria("where").addAnd("t.demo_name like ?", "%", pojo.getDemoName(), "%");
		
		 
		page.addDefaultOrderName("t.test_id").addPermitOrderName("t.demo_name").addUnique("t.demo_id");
		
		StringBuilder cSql = new StringBuilder("select count(*) from core_demo t").append(c);
		StringBuilder qSql = new StringBuilder("select * from core_demo t").append(c);
		
		List<Demo> list = queryPage(Demo.class, page, cSql, qSql, c.getParaList());
		return list;
	}
	
	public Map<String, Object> insert(Test pojo) {
        // 后面带不允许重名的字段（该字段需要建索引）
		int r = insertEntity(pojo, "demo_name");
        return ControllerReturn.exists(r, "测试名称");
    }
	
	public Test get(Integer id) {
		Test pojo = getJdbcTemplate().queryForObject("select * from test where demo_id = ?",
                BeanPropertyRowMapper.newInstance(Test.class), id);     
        return pojo;
    }
    
    public Map<String, Object> update(Demo bean) {
        // 后面带不允许重名的字段（该字段需要建索引）
        int r = updateEntity(bean, "demo_name");
        return ControllerReturn.exists(r, "测试名称已经存在");
    }
    
    public Map<String, Object> delete(Integer id) {
        getJdbcTemplate().update("delete from core_demo where demo_id = ?", id);
        return ControllerReturn.of();
    }

}
