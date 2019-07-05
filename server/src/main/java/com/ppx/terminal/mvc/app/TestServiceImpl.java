package com.ppx.terminal.mvc.app;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ppx.terminal.common.jdbc.MyDaoSupport;

@Service
public class TestServiceImpl extends MyDaoSupport {
	
	public List<String> listServiceId() {
		String sql = "select service_id from ter_log_start";
		return getJdbcTemplate().queryForList(sql, String.class);
	}
	
	public String getServiceId(String clientId) {
		String sql = "select ifnull((select service_id from ter_client_login_service where client_id = '1'), '') service_id";
		return getJdbcTemplate().queryForObject(sql, String.class, clientId);
	}
	
	public List<Map<String, Object>> listHeart(String clientId) {
		String sql = "select client_id cid, service_id sid, date_format(heart_time, '%Y-%m-%d %H:%i:%s') t, heart_count c"
				+ " from ter_log_heart where client_id = ? order by heart_time desc";
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql, clientId);
		return list;
	}
	
	public List<Map<String, Object>> listLogin(String clientId) {
		String sql = "select client_id cid, service_id sid, date_format(login_time, '%Y-%m-%d %H:%i:%s') t, login_status s"
				+ " from ter_log_login where client_id = ? order by login_time desc limit 20";
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql, clientId);
		return list;
	}

	public List<Map<String, Object>> listConnect(String serviceId) {
		String sql = "select service_id sid, date_format(connect_time, '%Y-%m-%d %H:%i:%s') t, connect_status s, connect_msg msg"
				+ " from ter_log_connect where service_id = ? order by connect_id desc limit 20";
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql, serviceId);
		return list;
	}
	
	public List<Map<String, Object>> listCommand(String serviceId) {
		String sql = "select service_id sid, date_format(command_time, '%Y-%m-%d %H:%i:%s') t, client_id id, command_content command, ifnull(command_return, '') r, ifnull(command_error, '') e"
				+ " from ter_log_command where service_id = ? order by command_id desc limit 20";
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql, serviceId);
		return list;
	}
	
}
