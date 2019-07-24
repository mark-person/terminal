package com.ppx.terminal.mvc.tool;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ppx.terminal.common.jdbc.MyDaoSupport;

@Service
public class RandomToolServiceImpl extends MyDaoSupport {
	
	private final String RANDOM_CODE_INDEX = "RANDOM_CODE_INDEX";
	
	public int getRandomCodeIndex() {
		String sql = "select ifnull((select config_int from ter_config where config_name = ?), -1)";
		int randomCodeIndex = getJdbcTemplate().queryForObject(sql, Integer.class, RANDOM_CODE_INDEX);
		return randomCodeIndex;
	}
	
	public Map<String, Object>  getRandomCodeMsg() {
		String sql = "select count(*) c, max(code_index) max_index, min(code_index) min_index from ter_random_code";
		Map<String, Object> r = getJdbcTemplate().queryForMap(sql);
		return r;
	}
	
	public void truncateCode() {
		String truncateSql = "truncate table ter_random_code";
		getJdbcTemplate().update(truncateSql);
	}
	
	public void createCode() {
		String insertSql = "insert into ter_random_code(code_index, random_code) values(?, ?)";
		for (int i = 1; i <= 100; i++) {
			String randomCode = String.format("%08d", (int)(Math.random()*100000000));
			// 77662205
			System.out.println("xxxxxxxxxxxxxrandomCode:" + randomCode);
    		getJdbcTemplate().update(insertSql, i, randomCode);
		}
	}
	
	@Transactional
	public String getRandomCode() {
		String forUpdate = "select config_int from ter_config where config_name = ? for update";
		int randomCodeIndex = getJdbcTemplate().queryForObject(forUpdate, Integer.class, RANDOM_CODE_INDEX);
		
		String updateIndex = "update ter_config set config_int = config_int + 1 where config_name = ?";
		getJdbcTemplate().update(updateIndex, RANDOM_CODE_INDEX);
		
		String codeSql = "select random_code from ter_random_code where code_index = ?";
		return getJdbcTemplate().queryForObject(codeSql, String.class, randomCodeIndex);
	}
	
	
}
