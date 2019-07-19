package com.ppx.terminal.mvc.tool;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ppx.terminal.common.jdbc.MyDaoSupport;

@Service
public class CellToolServiceImpl extends MyDaoSupport {
	
	public List<Map<String, Object>> listCell(String clientId, String lockerNumber) {
		
		String sql = "select * from ter_cell where client_id = ? and cell_id like ? '%'";
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql, clientId, lockerNumber);
		
		return list;
	}
	
	public List<String> listRowNumber(String clientId, String lockerNumber) {
		String sql = "select row_num from (select substr(cell_id, 2, 2) row_num from ter_cell where client_id = ? and cell_id like ? '%')"
				+ " a group by row_num order by row_num";
		List<String> list = getJdbcTemplate().queryForList(sql, String.class, clientId, lockerNumber);
		return list;
	}
	
	public List<String> listColumnNumber(String clientId, String lockerNumber) {
		String sql = "select column_num from (select substr(cell_id, 4, 2) column_num from ter_cell where client_id = ? and cell_id like ? '%')"
				+ " a group by column_num order by column_num";
		List<String> list = getJdbcTemplate().queryForList(sql, String.class, clientId, lockerNumber);
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void addRow(String clientId, String lockerNumber) {
		
		List<String> row = listRowNumber(clientId, lockerNumber);
		List<String> column  = listColumnNumber(clientId, lockerNumber);
		
		String s = String.format("%02d", row.size() + 1);
		String insertSql = "insert into ter_cell(client_id, cell_id, cell_bit) values(?, ?, ?)";
		
		for (String c : column) {
			String bit = s + c;
			getJdbcTemplate().update(insertSql, clientId, lockerNumber + bit, bit);
		}
		
	}
	
}
