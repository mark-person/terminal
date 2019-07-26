package com.ppx.terminal.mvc.tool.test;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ppx.terminal.common.jdbc.MyDaoSupport;

@Service
public class DbToolServiceImpl extends MyDaoSupport {
	
	public List<String> listColumn(String tableName) {
		String sql = "select COLUMN_NAME from information_schema.COLUMNS where table_name = ? order by ORDINAL_POSITION";
		List<String> list = getJdbcTemplate().queryForList(sql, String.class, tableName);
		return list;
	}
	
	public List<Map<String, Object>> listValue(String sql) {
		
		String tempSql = "select store_no, store_name from core_store";
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(tempSql);
		return list;
	}
	
	
}
