package com.ppx.terminal.mvc.tool;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ppx.terminal.common.jdbc.MyDaoSupport;

@Service
public class CellToolServiceImpl extends MyDaoSupport {
	
	public List<Map<String, Object>> listCell() {
		
		List<Map<String, Object>> list = getJdbcTemplate().queryForList("select * from ter_cell");
		
		return list;
	}
	
	
	
}
