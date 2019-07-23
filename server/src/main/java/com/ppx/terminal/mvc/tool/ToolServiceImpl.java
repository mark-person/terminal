package com.ppx.terminal.mvc.tool;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ppx.terminal.common.jdbc.MyDaoSupport;

@Service
public class ToolServiceImpl extends MyDaoSupport {
	
	public void createCode() {
		
		String truncateSql = "truncate table ter_random_code";
		getJdbcTemplate().update(truncateSql);
		
		String insertSql = "insert into ter_random_code(code_index, random_code) values(?, ?)";
		
		for (int i = 1; i <= 1000; i++) {
			String randomCode = String.format("%08d", (int)(Math.random()*100000000));
    		getJdbcTemplate().update(insertSql, i, randomCode);
		}
	}
	
	@Transactional
	public String getRandomCode() {
		String CURSOR_CODE_INDEX = "CURSOR_CODE_INDEX";
		
		String forUpdate = "select config_int from ter_config where config_name = ? for update";
		int useCodeIndex = getJdbcTemplate().queryForObject(forUpdate, Integer.class, CURSOR_CODE_INDEX);
		
		String updateIndex = "update ter_config set config_int = config_int + 1 where config_name = ?";
		getJdbcTemplate().update(updateIndex, CURSOR_CODE_INDEX);
		
		String codeSql = "select random_code from ter_random_code where code_index = ?";
		return getJdbcTemplate().queryForObject(codeSql, String.class, useCodeIndex);
	}
	
	
}
