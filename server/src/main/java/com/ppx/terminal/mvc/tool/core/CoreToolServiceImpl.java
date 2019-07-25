package com.ppx.terminal.mvc.tool.core;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.ppx.terminal.common.jdbc.MyDaoSupport;

@Service
public class CoreToolServiceImpl extends MyDaoSupport {
	
	public List<Store> listStore() {
		
		String sql = "select * from core_store";
		List<Store> list = getJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(Store.class));
		
		return list;
	}
	
	public void insertStore(Store s) {
		String sql = "insert into core_store(store_no, store_name, store_address) "
				+ "values(?, ?, ?)";
		getJdbcTemplate().update(sql, s.getStoreNo(), s.getStoreName(), s.getStoreAddress());
	}
	
	public void updateStore(Store s) {
		String sql = "update core_store set store_no = ?, store_name = ?, store_address = ?"
				+ " where store_no = ?";
		getJdbcTemplate().update(sql, s.getStoreNo(), s.getStoreName(), s.getStoreAddress(), s.getInitStoreNo());
	}
	
}
