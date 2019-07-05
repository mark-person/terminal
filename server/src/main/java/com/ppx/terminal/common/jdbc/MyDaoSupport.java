package com.ppx.terminal.common.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * insert update queryPage等 操作实体对象
 * 
 * @author mark
 * @date 2018年10月28日
 */
public class MyDaoSupport extends JdbcDaoSupport {

	@Autowired
	protected void setMyJdbcTemplate(JdbcTemplate jdbcTemplate) {
		super.setJdbcTemplate(jdbcTemplate);
	}

	

}
