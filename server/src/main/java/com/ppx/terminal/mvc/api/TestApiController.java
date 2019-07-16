/**
 * 
 */
package com.ppx.terminal.mvc.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.ppx.terminal.common.util.HmacSHA1;

/**
 * @author mark
 * @date 2019年7月12日
 */

@Controller
@RequestMapping("/testApi")
public class TestApiController {

	@RequestMapping("/test") @ResponseBody
	public Map<String, String> test() {
		
		RestTemplate rest = new RestTemplate();
		MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
		
		paramMap.add("accessKey", "007");
		paramMap.add("timestamp", "2019-07-16 14:16:01");
		paramMap.add("cellCode", "15785369");
		paramMap.add("a", "2");
		paramMap.add("a", "1");
		paramMap.add("a", "3");
		paramMap.add("para", "中国");
		
		String sign = getSign(paramMap);
		paramMap.add("sign", sign);
		
		// 报文:accessKey=007&timestamp=2019-07-16+11%3A38%3A01&cellCode=15785369&a=2&a=1&a=3&para=%E4%B8%AD%E5%9B%BD&sign=HhYehulocThgsnRm1sS1kbm8LOE%3D
		ResponseEntity<JsonNode> jsonObject = rest.postForEntity("http://localhost:9001/api/cell/getCellBit", 
				paramMap, JsonNode.class);
		
		JsonNode bodyNode = jsonObject.getBody();
		if (bodyNode == null) {
			System.out.println("body is null:");
		}
		else if (bodyNode.get("code") == null) {
			System.out.println("code is null");
		}
		else {
			System.out.println("return value:" + bodyNode.get("code").textValue());
		}
		
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("r", "OK");
		return returnMap;
	}

	
	private String getSign(MultiValueMap<String, String> paramMap) {
		StringBuffer sb = new StringBuffer();
		List<String> paraList = new ArrayList<String>();
		paraList.addAll(paramMap.keySet());
		Collections.sort(paraList);
		for (String name : paraList) {
			List<String> vList = paramMap.get(name);
			String v = StringUtils.collectionToDelimitedString(vList, "");
			sb.append(name).append(v);
		}
		return HmacSHA1.genHMAC(sb.toString(), "SIGN_KEY");
	}
}
