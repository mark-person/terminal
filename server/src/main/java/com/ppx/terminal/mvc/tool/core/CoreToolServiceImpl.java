package com.ppx.terminal.mvc.tool.core;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ppx.terminal.common.jdbc.MyDaoSupport;

@Service
public class CoreToolServiceImpl extends MyDaoSupport {
	
	public List<Map<String, Object>> listStore() {
		
		String sql = "select * from core_store";
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql);
		
		return list;
	}
	
	
	
	
}
