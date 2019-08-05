package com.ppx.terminal.mvc.core.tool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import com.ppx.terminal.common.jdbc.MyDaoSupport;

@Service
public class DbToolImpl extends MyDaoSupport {
	
	public List<Map<String, Object>> listTable() {
		String sql = "select TABLE_NAME value, concat(TABLE_COMMENT, '(', TABLE_NAME, ')') text "
				+ "from information_schema.tables where TABLE_NAME in ('core_demo', 'core_db_test', 'core_demo_sub')";
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
		
		String commentSql = "select COLUMN_NAME, trim(substring_index(COLUMN_COMMENT, '--', 1)) COLUMN_COMMENT from information_schema.COLUMNS where TABLE_NAME = ?" +
				" and COLUMN_NAME in ('" + colVal.replaceAll(",", "','") + "')";
		List<Map<String, Object>> commentList = getJdbcTemplate().queryForList(commentSql, tableName);
		
		
		Map<String, String> leftColMap = new HashMap<String, String>();
		String leftTable = "";
		
		for (Map<String, Object> map : commentList) {
			String name = (String)map.get("COLUMN_NAME");
			String comment = (String)map.get("COLUMN_COMMENT");
			if (comment.contains(";")) {
				comment = comment.split(";")[1];
			}
			if (comment.startsWith("select")) {
				comment = comment.replaceAll("\\s{1,}", " ");
				String[] itemArray = comment.split(" ");
				if (itemArray.length < 4) continue;
				
				String leftTableName = itemArray[4];
				String leftId = itemArray[1].replace(",", "");
				String leftName = itemArray[2].replace(",", "");
				
				leftColMap.put(name, "concat(" + tableName + "." + name + ",':'," + leftTableName + "." + leftName + ") " + name);
				leftTable += " left join " + leftTableName + " on " + tableName + "." + name + " = " + leftTableName + "." + leftId;
			}
		}
		
		
		for (String key : leftColMap.keySet()) {
			colVal = colVal.replace(key, leftColMap.get(key));
		}
		
		
		String tempSql = "select " + colVal + " from " + tableName + leftTable;		
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(tempSql);
		
		for (Map<String, Object> map : list) {
			map.keySet().forEach(k -> {
				if (map.get(k) == null) {
					map.put(k, "");
				}
			});
		}
		return list;
	}
	
	
	
	
	
	
	
	// 
	public Map<String, Object> listSqlData(String tableName, String columnName, String queryVal) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String commentSql = "select COLUMN_NAME, trim(substring_index(COLUMN_COMMENT, '--', 1)) COLUMN_COMMENT"
				+ " from information_schema.COLUMNS where TABLE_NAME = ? and COLUMN_NAME = ?";
		Map<String, Object> commentMap = getJdbcTemplate().queryForMap(commentSql, tableName, columnName);
		String sql = ((String)commentMap.get("COLUMN_COMMENT")).split(";")[1];
		
		Map<String, String> sqlMsgMap = getSqlMsg(sql);
		String sqlTableName = sqlMsgMap.get("tableName");
		String idColumn = sqlMsgMap.get("idColumn");
		String nameColumn= sqlMsgMap.get("nameColumn");
		String sqlCommentSql = "select trim(substring_index(COLUMN_COMMENT, '--', 1)) COLUMN_COMMENT"
				+ " from information_schema.COLUMNS where TABLE_NAME = ? and (COLUMN_NAME = ? or COLUMN_NAME = ?) order by ORDINAL_POSITION";
		List<String> commList = getJdbcTemplate().queryForList(sqlCommentSql, String.class, sqlTableName, idColumn, nameColumn);
		returnMap.put("title", commList);
		
		if (!Strings.isEmpty(queryVal)) {
			if (sql.contains("where")) {
				sql += " and " + nameColumn + " like ?";
			}
			else {
				sql += " where " + nameColumn + " like ?";
			}
		}
		
		
		List<Map<String, Object>> list = null;
		if (Strings.isEmpty(queryVal)) {
			list = getJdbcTemplate().queryForList(sql);
		}
		else {
			list = getJdbcTemplate().queryForList(sql, "%" + queryVal + "%");
		}
		
		
		for (Map<String, Object> map : list) {
			map.keySet().forEach(k -> {
				if (map.get(k) == null) {
					map.put(k, "");
				}
			});
		}
		returnMap.put("list", list);
		
		return returnMap;
		
	}
	
	
	private Map<String, String> getSqlMsg(String sql) {
		Map<String, String> returnMap = new HashMap<String, String>(3);
		sql = sql.replaceAll("\\s{1,}", " ").toLowerCase();
		String[] itemArray = sql.split(" ");
		if (itemArray.length < 5) {
			return returnMap;
		}
		String tableName = "";
		String idColumn = itemArray[1].replace(",", "");
		String nameColumn= itemArray[2].replace(",", "");
		for (String item : itemArray) {
			if ("from".equals(tableName)) {
				tableName = item;
			}
			if (item.equals("from")) {
				tableName = "from";
			}
		}
		returnMap.put("tableName", tableName);
		returnMap.put("idColumn", idColumn);
		returnMap.put("nameColumn", nameColumn);
		return returnMap;
	}
	
	
	
	
	
	
	
	
	
}
