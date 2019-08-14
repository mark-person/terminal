package com.ppx.terminal.mvc.core.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import com.ppx.terminal.common.controller.ControllerReturn;
import com.ppx.terminal.common.jdbc.MyDaoSupport;

@Service
public class DbToolImpl extends MyDaoSupport {
	
	public List<Map<String, Object>> listTable() {
		String sql = "select TABLE_NAME value, concat(TABLE_COMMENT, '(', TABLE_NAME, ')') text "
				+ "from information_schema.tables where TABLE_NAME in ('core_demo', 'core_db_test', 'core_demo_sub', 'core_demo_main')";
		return getJdbcTemplate().queryForList(sql);
	}
	
	public List<Map<String, Object>> listColumnMsg(String tableName) {
		// DATA_TYPE = 'date'
		// ID -- 其它说明
		// IS_NULLABLE:YES|NO COLUMN_KEY:PRI
		String sql = "select COLUMN_NAME value, trim(substring_index(COLUMN_COMMENT, '--', 1)) comment, DATA_TYPE, IS_NULLABLE, COLUMN_KEY"
				+ " from information_schema.COLUMNS where TABLE_NAME = ? order by ORDINAL_POSITION";
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql, tableName);
		
		
		for (Map<String, Object> map : list) {
			
			String comment = (String)map.get("comment");
			String value = (String)map.get("value");
			String isNull = (String)map.get("IS_NULLABLE");
			String key = (String)map.get("COLUMN_KEY");
			
			if ("NO".equals(isNull)) {
				isNull = "*";
			}
			else {
				isNull = "";
			}
			
			if ("PRI".equals(key)) {
				key = "PK";
			}
			else {
				key = "";
			}
			
			
			String text = comment.split(";")[0] + "(" + value + ")" + isNull + key;
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
				
				//leftColMap.put(name, "concat(" + tableName + "." + name + ",':'," + leftTableName + "." + leftName + ") " + name);
				leftColMap.put(name, leftTableName + "." + name + " " + name + "," +  leftTableName + "." + leftName + " " + name + "__desc");
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
	public Map<String, Object> listSingleData(String tableName, String columnName, String queryVal) {
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
		
		
		
		
		// 总数
		String countSql = "select count(*) from (" + sql +") t";
		int count = -1;
		if (Strings.isEmpty(queryVal)) {
			count = getJdbcTemplate().queryForObject(countSql, Integer.class);
		}
		else {
			count = getJdbcTemplate().queryForObject(countSql, Integer.class, "%" + queryVal + "%");
		}
		
		// 列表
		List<Map<String, Object>> list = null;
		sql = sql + " limit 10";
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
		
		returnMap.put("count", count);
		
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
	
	public Map<String, Object> update(Map<String, String[]> map) {
		String tableName = map.get("tableName")[0];
		
		Set<String> keySet = map.keySet();
		
		String pkKey = (String)(keySet.toArray()[0]);
		String pkVal = map.get(pkKey)[0];
		
		
		if (Strings.isEmpty(pkVal)) {
			// 新增 >>>>>>>>>>>>>>
			List<String> colList = new ArrayList<String>();
			List<String> questionList = new ArrayList<String>();
			List<Object> valList = new ArrayList<Object>();
			for (String key : keySet) {
				if (key.equals("tableName") || key.equals(pkKey)) {
					continue;
				}
				colList.add(key);
				questionList.add("?");
				valList.add(map.get(key)[0]);
			}
			String insertSql = "insert into " + tableName + "(" + Strings.join(colList, ',') + ") values(" + Strings.join(questionList, ',') + ")";
			getJdbcTemplate().update(insertSql, valList.toArray());
			return ControllerReturn.of(40000, "新增成功");
		}
		else {
			// 修改 >>>>>>>>>>>>>>
			List<Object> para = new ArrayList<Object>();
			String pkSql = "";
			Object pkObj = null;
			StringBuilder sqlSb = new StringBuilder();
			for (String key : keySet) {
				if ("".equals(pkSql)) {
					pkSql = key + " = ?";
					pkObj = map.get(key)[0];
					continue;
				}
				
				if (key.equals("tableName")) {
					continue;
				}
				if (map.get(key) != null) {
					sqlSb.append(key + " = " + "?,");
					para.add(map.get(key)[0]);
				}
			}
			para.add(pkObj);
			String updateSql = "update " + tableName + " set " + sqlSb.substring(0, sqlSb.length() - 1) + " where " + pkSql;
			getJdbcTemplate().update(updateSql, para.toArray());
			return ControllerReturn.of(40001, "修改成功");
		}
		
		
		
		
	}
	
	
	public Map<String, Object> listChainData(String tableName, String columnName) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String commentSql = "select COLUMN_NAME, trim(substring_index(COLUMN_COMMENT, '--', 1)) COLUMN_COMMENT"
				+ " from information_schema.COLUMNS where TABLE_NAME = ? and COLUMN_NAME = ?";
		Map<String, Object> commentMap = getJdbcTemplate().queryForMap(commentSql, tableName, columnName);
		String sql = ((String)commentMap.get("COLUMN_COMMENT")).split(";")[2];
		
		List<Map<String, Object>> dataList = getJdbcTemplate().queryForList(sql);
		
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : dataList) {
			Map<String, Object> m = new HashMap<String, Object>();
			String vName = (String)map.keySet().toArray()[0];
			String tName = (String)map.keySet().toArray()[1];
			m.put("v", map.get(vName));
			m.put("t", map.get(tName));
			returnList.add(m);
		}
		returnMap.put("list", returnList);
		
		return returnMap;
	}
	
	public Map<String, Object> listChainSecondData(String tableName, String columnName, Integer parentId) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String commentSql = "select COLUMN_NAME, trim(substring_index(COLUMN_COMMENT, '--', 1)) COLUMN_COMMENT"
				+ " from information_schema.COLUMNS where TABLE_NAME = ? and COLUMN_NAME = ?";
		Map<String, Object> commentMap = getJdbcTemplate().queryForMap(commentSql, tableName, columnName);
		String sql = ((String)commentMap.get("COLUMN_COMMENT")).split(";")[1];
		
		List<Map<String, Object>> dataList = getJdbcTemplate().queryForList(sql, parentId);
		
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : dataList) {
			Map<String, Object> m = new HashMap<String, Object>();
			String vName = (String)map.keySet().toArray()[0];
			String tName = (String)map.keySet().toArray()[1];
			m.put("v", map.get(vName));
			m.put("t", map.get(tName));
			returnList.add(m);
		}
		returnMap.put("list", returnList);
		
		return returnMap;
	}
	
	
	
	
	
}
