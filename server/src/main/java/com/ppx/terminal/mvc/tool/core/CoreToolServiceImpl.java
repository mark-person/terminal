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
		String sql = "insert into core_store(store_no, store_name, store_address, store_lng, store_lat) "
				+ "values(?, ?, ?, ?, ?)";
		getJdbcTemplate().update(sql, s.getStoreNo(), s.getStoreName(), s.getStoreAddress(), s.getStoreLng(), s.getStoreLat());
	}
	
	public void updateStore(Store s) {
		String sql = "update core_store set store_no = ?, store_name = ?, store_address = ?, store_lng = ?, store_lat = ?"
				+ " where store_no = ?";
		getJdbcTemplate().update(sql, s.getStoreNo(), s.getStoreName(), s.getStoreAddress(), s.getStoreLng(), s.getStoreLat(), s.getInitStoreNo());
	}
	
	public void deleteStore(String storeNo) {
		String sql = "delete from core_store where store_no = ?";
		getJdbcTemplate().update(sql, storeNo);
	}
	
	
	
	
	
	
	public List<Locker> listLocker() {
		String sql = "select * from core_locker";
		List<Locker> list = getJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(Locker.class));
		return list;
	}
	
	public void insertStore(Locker s) {
		String sql = "insert into core_locker(locker_no, locker_desc) "
				+ "values(?, ?)";
		getJdbcTemplate().update(sql, s.getLockerNo(), s.getLockerDesc());
	}
	
	public void updateStore(Locker s) {
		String sql = "update core_locker set locker_no = ?, locker_desc = ?"
				+ " where locker_no = ?";
		getJdbcTemplate().update(sql, s.getLockerNo(), s.getLockerDesc(), s.getInitLockerNo());
	}
	
	public void deleteLocker(String lockerNo) {
		String sql = "delete from core_locker where locker_no = ?";
		getJdbcTemplate().update(sql, lockerNo);
	}
}
