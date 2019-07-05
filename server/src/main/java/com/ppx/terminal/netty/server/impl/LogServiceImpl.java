package com.ppx.terminal.netty.server.impl;

import java.util.Date;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ppx.terminal.common.jdbc.MyDaoSupport;
import com.ppx.terminal.common.util.NettyUtils;

@Service
public class LogServiceImpl extends MyDaoSupport {

	
	
	@Transactional
	public void insertLogHeart(String clientId, int heartCount) {
		String insertSql = "insert into ter_log_heart(client_id, heart_count, service_id) values(?, ?, ?)";
		getJdbcTemplate().update(insertSql, clientId, heartCount, NettyUtils.getServiceId());
			
		int STORE_RECORD_NUM = 20;
		
		String minSql = "select min(heart_time) from ter_log_heart where (select count(*) from ter_log_heart where client_id = ?) > ?"
				+ " and client_id = ?";
		Date minDate = getJdbcTemplate().queryForObject(minSql, Date.class, clientId, STORE_RECORD_NUM, clientId);
		
		String deleteSql = "delete from ter_log_heart where client_id = ? and heart_time = ?";
		getJdbcTemplate().update(deleteSql, clientId, minDate);
		
	}
	
	@Transactional
	public void insertLogLogin(String clientId, String loginStatus) {
		String duplicateSql = "insert into ter_client_login_service(client_id, service_id) values(?, ?) ON DUPLICATE KEY UPDATE service_id = ?, login_time = now()";
		getJdbcTemplate().update(duplicateSql, clientId, NettyUtils.getServiceId(), NettyUtils.getServiceId());
		
		String insertSql = "insert into ter_log_login(client_id, login_status, service_id) values(?, ?, ?)";
		getJdbcTemplate().update(insertSql, clientId, loginStatus, NettyUtils.getServiceId());
	}
	
	@Transactional
	public void insertLogConnect(String connectStatus, String connectMsg) {
		if (Strings.isNotEmpty(connectMsg) && connectMsg.length() > 250) {
			connectMsg = connectMsg.substring(250);
		}
		String insertSql = "insert into ter_log_connect(service_id, connect_status, connect_msg) values(?, ?, ?)";
		getJdbcTemplate().update(insertSql, NettyUtils.getServiceId(), connectStatus, connectMsg);
	}
	
	@Transactional
	public void insertLogStart() {
		String duplicateSql = "insert into ter_log_start(service_id, service_tag) values(?, ?) ON DUPLICATE KEY UPDATE service_tag = ?, start_time = now()";
		getJdbcTemplate().update(duplicateSql, NettyUtils.getServiceId(), NettyUtils.getServiceTag(), NettyUtils.getServiceTag());
	}
	
	
}
