package com.ppx.terminal.mvc.core.dict;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.ppx.terminal.common.jdbc.MyDaoSupport;

@Service
public class DictImpl extends MyDaoSupport {
	
	private static Map<String, String> COLUMN_MAP_TABLE = new ConcurrentHashMap<String, String>();

	public Map<String, String> list(String[] columnName) {
		Map<String, String> returnMap = new HashMap<String, String>();
		
		boolean useTableNameCache = true;
		List<String> queryTableName = new ArrayList<String>();
		for (String cName : columnName) {
			if (!COLUMN_MAP_TABLE.containsKey(cName)) {
				useTableNameCache = false;
				break;
			}
			else {
				queryTableName.add(COLUMN_MAP_TABLE.get(cName));
			}
		}
		
		// 通过columnName缓存,  tableName
		if (useTableNameCache) {
			NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(getJdbcTemplate());
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("tableNameList", queryTableName);
			paramMap.put("cloumnNameList", Arrays.asList(columnName));
			String columnTableSql = "select TABLE_NAME, COLUMN_NAME, COLUMN_COMMENT from information_schema.columns where table_schema = database()" +
					" and table_name in (:tableNameList) and column_name in (:cloumnNameList)";
			List<Map<String, Object>> list = nameTemplate.queryForList(columnTableSql, paramMap);
			for (Map<String, Object> map : list) {
				returnMap.put((String)map.get("COLUMN_NAME"), (String)map.get("COLUMN_COMMENT"));
			}
			return returnMap;
		}
		else {
			NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(getJdbcTemplate());
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("cloumnNameList", Arrays.asList(columnName));
			String columnTableSql = "select TABLE_NAME, COLUMN_NAME, COLUMN_COMMENT from information_schema.columns where table_schema = database()" +
					" and column_name in (:cloumnNameList)";
			List<Map<String, Object>> list = nameTemplate.queryForList(columnTableSql, paramMap);
			
			if (list.size() != columnName.length) {
				Set<String> columnNameSet = new HashSet<String>();
				for (Map<String, Object> map : list) {
					columnNameSet.add((String)map.get("COLUMN_NAME"));
				}
				for (String cName : columnName) {
					if (!columnNameSet.contains(cName)) {
						returnMap.put("notExists", cName);
						return returnMap;
					}
				}
			}
			
			for (Map<String, Object> map : list) {
				COLUMN_MAP_TABLE.put((String)map.get("COLUMN_NAME"), (String)map.get("TABLE_NAME"));
				returnMap.put((String)map.get("COLUMN_NAME"), (String)map.get("COLUMN_COMMENT"));
			}
			return returnMap;
		}
	}
	

}
