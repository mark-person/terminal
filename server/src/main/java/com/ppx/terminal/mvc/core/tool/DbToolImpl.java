package com.ppx.terminal.mvc.core.tool;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ppx.terminal.common.jdbc.MyDaoSupport;

@Service
public class DbToolImpl extends MyDaoSupport {
	
	public List<Map<String, Object>> listTable() {
		String sql = "select TABLE_NAME value, concat(TABLE_COMMENT, '(', TABLE_NAME, ')') text "
				+ "from information_schema.tables where TABLE_NAME in ('core_demo', 'core_db_test')";
		return getJdbcTemplate().queryForList(sql);
	}
	
	public List<Map<String, Object>> listColumn(String tableName) {
		// DATA_TYPE = 'date'
		// ID -- 其它说明
		String sql = "select COLUMN_NAME value, trim(substring_index(COLUMN_COMMENT, '--', 1)) comment, DATA_TYPE"
				+ " from information_schema.COLUMNS where TABLE_NAME = ? order by ORDINAL_POSITION";
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql, tableName);
		
		for (Map<String, Object> map : list) {
			String comment = (String)map.get("comment");
			String value = (String)map.get("value");
			String text = comment.split(";")[0] + "(" + value + ")";
			map.put("text", text);
		}
		
		
		return list;
	}
	
	public List<Map<String, Object>> listValue(String tableName, String colVal) {
		
		String tempSql = "select " + colVal + " from " + tableName;
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(tempSql);
		
		for (Map<String, Object> map : list) {
			map.keySet().forEach(k -> {
				if (map.get(k) == null) {
					//map.put(k, "<NULL>");
					map.put(k, "");
				}
			});
		}
		return list;
	}
	
	
}
