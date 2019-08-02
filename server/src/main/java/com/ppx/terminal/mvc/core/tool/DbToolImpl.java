package com.ppx.terminal.mvc.core.tool;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ppx.terminal.common.jdbc.MyDaoSupport;

@Service
public class DbToolImpl extends MyDaoSupport {
	
	public List<String> listColumn(String tableName) {
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
