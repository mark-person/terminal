package com.ppx.terminal.mvc.core.tool;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ppx.terminal.common.jdbc.MyDaoSupport;

@Service
public class DbToolImpl extends MyDaoSupport {
	
	public List<Map<String, Object>> listTable() {
		String sql = "select TABLE_NAME value, concat(TABLE_COMMENT, '(', TABLE_NAME, ')') text "
				+ "from information_schema.tables where table_name in ('core_demo', 'core_db_test')";
		return getJdbcTemplate().queryForList(sql);
	}
	
	public List<String> listColumn(String tableName) {
		
		// select COLUMN_NAME, SUBSTRING_INDEX(COLUMN_COMMENT, ';', 1) from information_schema.COLUMNS where table_name = 'core_demo' order by ORDINAL_POSITION
		String sql = "select COLUMN_NAME from information_schema.COLUMNS where table_name = ? order by ORDINAL_POSITION";
		List<String> list = getJdbcTemplate().queryForList(sql, String.class, tableName);
		return list;
	}
	
	public List<Map<String, Object>> listValue(String tableName, String colVal) {
		
		String tempSql = "select " + colVal + " from " + tableName;
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(tempSql);
		for (Map<String, Object> map : list) {
			map.keySet().forEach(k -> {
				if (map.get(k) == null) {
					map.put(k, "<NULL>");
				}
			});
		}
		return list;
	}
	
	
}
