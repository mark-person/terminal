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
			getJdbcTemplate().update(insertSql, clientId, lockerNumber + bit, lockerNumber + bit);
		}
		
		if (column.isEmpty()) {
			getJdbcTemplate().update(insertSql, clientId, lockerNumber + "0101", lockerNumber + "0101");
		}
	}
	
	public void addColumn(String clientId, String lockerNumber) {
		List<String> row = listRowNumber(clientId, lockerNumber);
		List<String> column  = listColumnNumber(clientId, lockerNumber);
		
		String s = String.format("%02d", column.size() + 1);
		String insertSql = "insert into ter_cell(client_id, cell_id, cell_bit) values(?, ?, ?)";
		
		for (String r : row) {
			String bit = r + s;
			getJdbcTemplate().update(insertSql, clientId, lockerNumber + bit, lockerNumber + bit);
		}
		
		if (row.isEmpty()) {
			getJdbcTemplate().update(insertSql, clientId, lockerNumber + "0101", lockerNumber + "0101");
		}
	}
	
	
	public void delRow(String clientId, String lockerNumber) {
		List<String> row = listRowNumber(clientId, lockerNumber);
		String lastRow = String.format("%02d", row.size());
		
		String delSql = "delete from ter_cell where client_id = ? and cell_id like ? '%'";
		getJdbcTemplate().update(delSql, clientId, (lockerNumber + lastRow));
	}
	
	public void delColumn(String clientId, String lockerNumber) {
		List<String> column = listColumnNumber(clientId, lockerNumber);
		String lastColumn = String.format("%02d", column.size());
		
		String delSql = "delete from ter_cell where client_id = ? and cell_id like ?";
		getJdbcTemplate().update(delSql, clientId, (lockerNumber + "%" + lastColumn));
	}
	
	public void delCell(String clientId, String cellId) {
		String delSql = "delete from ter_cell where client_id = ? and cell_id = ?";
		getJdbcTemplate().update(delSql, clientId, cellId);
	}
	
	public void addCell(String clientId, String cellId) {
		String insertSql = "insert into ter_cell(client_id, cell_id, cell_bit) values(?, ?, ?)";
		getJdbcTemplate().update(insertSql, clientId, cellId, cellId);
	}
	
	public int editCell(String clientId, String cellId, String openCode) {
		String existsSql = "select count(*) from ter_cell where client_id = ? and cell_bit = ? and cell_id != ?";
		int c = getJdbcTemplate().queryForObject(existsSql, Integer.class, clientId, openCode, cellId);
		if (c > 0) {
			return -1;
		}
		
		String lockSql = "update ter_cell set cell_set_status = ?, cell_bit = ? where client_id = ? and cell_id = ?";
		return getJdbcTemplate().update(lockSql, "EDIT", openCode, clientId, cellId);
	}
	
	public void initCell(String clientId, String cellId) {
		String lockSql = "update ter_cell set cell_set_status = ? where client_id = ? and cell_id = ?";
		getJdbcTemplate().update(lockSql, "INIT", clientId, cellId);
	}
	
	public int lockCell(String clientId, String cellId) {
		String lockSql = "update ter_cell set cell_set_status = ? where client_id = ? and cell_id = ?";
		return getJdbcTemplate().update(lockSql, "LOCK", clientId, cellId);
	}
	
	public int unLockCell(String clientId, String cellId) {
		String lockSql = "update ter_cell set cell_set_status = ? where client_id = ? and cell_id = ?";
		return getJdbcTemplate().update(lockSql, "EDIT", clientId, cellId);
	}
	
	
}
