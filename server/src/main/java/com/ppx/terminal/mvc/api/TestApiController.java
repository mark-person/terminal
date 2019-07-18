/**
 * 
 */
package com.ppx.terminal.mvc.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.ppx.terminal.common.api.ApiClientUtils;
import com.ppx.terminal.common.api.ApiReturnBody;

/**
 * @author mark
 * @date 2019年7月12日
 */

@Controller
@RequestMapping("/testApi")
public class TestApiController {

	@RequestMapping("/test") @ResponseBody
	public Map<String, String> test3() {
		MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
		paramMap.add("cellCode", "15785369");
		
		ApiReturnBody r = ApiClientUtils.call("apiV1/cell/getCellBit", paramMap);
		
		String returnMsg = "" + r.toString();
		
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("r", returnMsg);
		return returnMap;
	}
	
	
	
	
	
	
	@RequestMapping("/testtest") @ResponseBody
	public Map<String, String> test() {
		
		RestTemplate rest = new RestTemplate();
		MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		paramMap.add("accessKey", "007");
		paramMap.add("timestamp", sdf.format(new Date()));
		paramMap.add("cellCode", "15785369");
		paramMap.add("a", "2");
		paramMap.add("a", "1");
		paramMap.add("a", "3");
		paramMap.add("para", "中国");
		
		String sign = ApiClientUtils.getSign(paramMap);
		paramMap.add("sign", sign);
		
		// 报文:accessKey=007&timestamp=2019-07-16+11%3A38%3A01&cellCode=15785369&a=2&a=1&a=3&para=%E4%B8%AD%E5%9B%BD&sign=HhYehulocThgsnRm1sS1kbm8LOE%3D
		ResponseEntity<JsonNode> jsonObject = rest.postForEntity("http://localhost:9001/api/cell/getCellBit", 
				paramMap, JsonNode.class);
		
		String returnMsg = "";
		
		JsonNode bodyNode = jsonObject.getBody();
		
		System.out.println("xxxxxxxxxxxxxxx:" + bodyNode);
		if (bodyNode == null) {
			returnMsg = "body is null";
		}
		else if (bodyNode.get("code") == null) {
			returnMsg = "code is null";
		}
		else {
			returnMsg = "return value:"
					+ bodyNode.get("code").intValue() + "|"
					+ (bodyNode.get("msg") == null ? "" : bodyNode.get("msg").asText());
		}
		
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("r", returnMsg);
		return returnMap;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/testtest2") @ResponseBody
	public Map<String, String> test2() {
		
		RestTemplate rest = new RestTemplate();
		MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		paramMap.add("accessKey", "007");
		paramMap.add("timestamp", sdf.format(new Date()));
		paramMap.add("cellCode", "15785369");
		paramMap.add("a", "2");
		paramMap.add("a", "1");
		paramMap.add("a", "3");
		paramMap.add("para", "中国");
		
		String sign = ApiClientUtils.getSign(paramMap);
		paramMap.add("sign", sign);
		
		ResponseEntity<Map> jsonObject = rest.postForEntity("http://localhost:9001/api/cell/getCellBit", 
				paramMap, Map.class);
		Map<String, Object> apiReturn = jsonObject.getBody();
		
		ApiReturnBody r = new ApiReturnBody(apiReturn);
		System.out.println("000000:" + r.getCode());
		System.out.println("111111:" + r.getMsg());
		System.out.println("222222:" + r.get("cellCode"));

		String returnMsg = "";
		
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("r", returnMsg);
		return returnMap;
	}
}
