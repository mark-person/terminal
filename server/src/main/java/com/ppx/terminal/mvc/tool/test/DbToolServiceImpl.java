package com.ppx.terminal.mvc.tool.test;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ppx.terminal.common.jdbc.MyDaoSupport;

@Service
public class DbToolServiceImpl extends MyDaoSupport {
	
	
	
	public List<String> listColumn(String tableName) {
		System.out.println("999999999tableName:" + tableName);
		String sql = "select COLUMN_NAME from information_schema.COLUMNS where table_name = ?";
		List<String> list = getJdbcTemplate().queryForList(sql, String.class, tableName);
		return list;
	}
	
	
	
	
}
