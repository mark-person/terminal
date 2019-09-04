package com.ppx.terminal.mvc.core.faq;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ppx.terminal.common.controller.ControllerReturn;
import com.ppx.terminal.common.jdbc.MyCriteria;
import com.ppx.terminal.common.jdbc.MyDaoSupport;
import com.ppx.terminal.common.page.Page;

@Service
public class FaqImpl extends MyDaoSupport {
	
	
	public String getDirection(String tableName, String code) {
		
		
		return "";
	}

	@Transactional
	public List<Faq> list(Page page, Faq pojo) {
		
		
		MyCriteria c = createCriteria("where")
			.addAnd("t.faq_title like ?", "%", pojo.getFaqTitle(), "%");
		
		StringBuilder cSql = new StringBuilder("select count(*) from core_faq t").append(c);
		StringBuilder qSql = new StringBuilder("select * from core_faq t").append(c);
		
		List<Faq> list = queryPage(Faq.class, page, cSql, qSql, c.getParaList());
		return list;
	}
	
	@Transactional
	public Map<String, Object> insert(Faq pojo) {
		insertEntity(pojo);
		
    	String itemSql = "insert into core_faq_descr_item(faq_id, faq_descr_index, item_content) values(?, ?, ?)";
    	for (int i = 0; i < pojo.getSub().size(); i++) {
			getJdbcTemplate().update(itemSql, pojo.getFaqId(), i, pojo.getSub().get(i));
		}
    	
    	String itemAnswerSql = "insert into core_faq_answer_item(faq_id, faq_answer_index, item_content) values(?, ?, ?)";
    	for (int i = 0; i < pojo.getAnswerSub().size(); i++) {
			getJdbcTemplate().update(itemAnswerSql, pojo.getFaqId(), i, pojo.getAnswerSub().get(i));
		}
		
        return ControllerReturn.of("faqId", super.getLastInsertId());
    }
	
	public Faq get(Integer id) {
		String sql = "select * from core_faq where faq_id = ?";
		Faq pojo = getJdbcTemplate().queryForObject(sql, BeanPropertyRowMapper.newInstance(Faq.class), id);  
		
		String itemSql = "select item_content from core_faq_descr_item where faq_id = ? order by faq_descr_index";
		List<String> subList = getJdbcTemplate().queryForList(itemSql, String.class, id);
		pojo.setSub(subList);
		
		String itemAnswerSql = "select item_content from core_faq_answer_item where faq_id = ? order by faq_answer_index";
		List<String> answerSubList = getJdbcTemplate().queryForList(itemAnswerSql, String.class, id);
		pojo.setAnswerSub(answerSubList);
		
        return pojo;
    }
    
	@Transactional
    public Map<String, Object> update(Faq pojo) {
    	updateEntity(pojo);
    	
    	String delSql = "delete from core_faq_descr_item where faq_id = ?";
    	String itemSql = "insert into core_faq_descr_item(faq_id, faq_descr_index, item_content) values(?, ?, ?)";
    	getJdbcTemplate().update(delSql, pojo.getFaqId());
    	for (int i = 0; i < pojo.getSub().size(); i++) {
			getJdbcTemplate().update(itemSql, pojo.getFaqId(), i, pojo.getSub().get(i));
		}
    	
    	String delAnswerSql = "delete from core_faq_answer_item where faq_id = ?";
    	String itemAnswerSql = "insert into core_faq_answer_item(faq_id, faq_answer_index, item_content) values(?, ?, ?)";
    	getJdbcTemplate().update(delAnswerSql, pojo.getFaqId());
    	for (int i = 0; i < pojo.getAnswerSub().size(); i++) {
			getJdbcTemplate().update(itemAnswerSql, pojo.getFaqId(), i, pojo.getAnswerSub().get(i));
		}
    	
        return ControllerReturn.of();
    }
    
    public Map<String, Object> delete(Integer id) {
        return ControllerReturn.of();
    }

}
