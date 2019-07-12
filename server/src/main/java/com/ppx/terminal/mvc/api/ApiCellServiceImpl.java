package com.ppx.terminal.mvc.api;

import org.springframework.stereotype.Service;

import com.ppx.terminal.common.jdbc.MyDaoSupport;

@Service
public class ApiCellServiceImpl extends MyDaoSupport {
	
	
	public String getCellBit(String cellCode) {
		String sql = "select ifnull((select cell_bit from ter_cell where cell_code = ?), '')";
		String cellBit = getJdbcTemplate().queryForObject(sql, String.class, cellCode);
		return cellBit;
	}
	
}
