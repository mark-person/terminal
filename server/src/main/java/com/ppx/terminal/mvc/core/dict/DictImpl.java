package com.ppx.terminal.mvc.core.dict;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.util.Strings;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ppx.terminal.common.jdbc.MyDaoSupport;

@Service
public class DictImpl extends MyDaoSupport {
	
	private static Map<String, String> COLUMN_MAP_TABLE = new ConcurrentHashMap<String, String>();

	public Map<String, Object> list(String[] code) {
		Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
		
		boolean useTableNameCache = true;
		List<String> queryTableName = new ArrayList<String>();
		for (String cName : code) {
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
			paramMap.put("cloumnNameList", Arrays.asList(code));
			String columnTableSql = "select TABLE_NAME, COLUMN_NAME, substring_index(COLUMN_COMMENT, '-- ', 1) COLUMN_COMMENT from information_schema.columns where table_schema = database()" +
					" and table_name in (:tableNameList) and column_name in (:cloumnNameList)";
			List<Map<String, Object>> list = nameTemplate.queryForList(columnTableSql, paramMap);
			for (Map<String, Object> map : list) {
				setDictJson((String)map.get("COLUMN_NAME"), (String)map.get("COLUMN_COMMENT"), returnMap);
			}
			return returnMap;
		}
		else {
			NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(getJdbcTemplate());
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("cloumnNameList", Arrays.asList(code));
			String columnTableSql = "select TABLE_NAME, COLUMN_NAME, substring_index(COLUMN_COMMENT, '-- ', 1) COLUMN_COMMENT from information_schema.columns where table_schema = database()" +
					" and column_name in (:cloumnNameList)";
			List<Map<String, Object>> list = nameTemplate.queryForList(columnTableSql, paramMap);
			
			if (list.size() != code.length) {
				Set<String> columnNameSet = new HashSet<String>();
				for (Map<String, Object> map : list) {
					columnNameSet.add((String)map.get("COLUMN_NAME"));
				}
				for (String cName : code) {
					if (!columnNameSet.contains(cName)) {
						returnMap.put(cName, "notExist");
						return returnMap;
					}
				}
			}
			
			for (Map<String, Object> map : list) {
				COLUMN_MAP_TABLE.put((String)map.get("COLUMN_NAME"), (String)map.get("TABLE_NAME"));
				setDictJson((String)map.get("COLUMN_NAME"), (String)map.get("COLUMN_COMMENT"), returnMap);
			}
			return returnMap;
		}
	}
	
	private void setDictJson(String columnName, String comment, Map<String, Object> returnMap) {
		if (Strings.isEmpty(comment)) {
			returnMap.put(columnName, "comment is empty");
			return;
		}
		comment = comment.trim();
		String[] item = comment.split("\\|");
		if (item.length < 2) {
			returnMap.put(columnName, "lack of |");
			return;
		}
		
		try {
			JsonNode jn = new ObjectMapper().readTree(item[1]);
			returnMap.put(columnName, jn);
		} catch (IOException e) {
			returnMap.put(columnName, "json error:" + e.getMessage());
			return;
		}
		
		// disabled功能
		if (item.length == 3) {
			String disabled = item[2];
			returnMap.put(columnName + "_disabled", disabled);
		}
		
	}
	

}
