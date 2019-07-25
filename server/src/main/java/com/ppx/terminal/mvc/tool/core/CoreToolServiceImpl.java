package com.ppx.terminal.mvc.tool.core;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ppx.terminal.common.controller.ControllerReturn;
import com.ppx.terminal.common.jdbc.MyDaoSupport;

@Service
public class CoreToolServiceImpl extends MyDaoSupport {
	
	public List<Store> listStore() {
		
		String sql = "select * from core_store";
		List<Store> list = getJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(Store.class));
		
		return list;
	}
	
	
	
	
}
