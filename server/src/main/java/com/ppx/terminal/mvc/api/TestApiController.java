/**
 * 
 */
package com.ppx.terminal.mvc.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
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
	public String test() {
		
		
		//HttpHeaders headers = new HttpHeaders();
		
		
		RestTemplate rest = new RestTemplate();
		MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
		
		paramMap.add("accessKey", "007");
		paramMap.add("timestamp", "2019-07-16 11:38:01");
		paramMap.add("cellCode", "15785369");
		paramMap.add("a", "2");
		paramMap.add("a", "1");
		paramMap.add("a", "3");
		
		String sign = getSign(paramMap);
		paramMap.add("sign", sign);
		
		ResponseEntity<JsonNode> jsonObject = rest.postForEntity("http://localhost:9001/api/cell/getCellBit", 
				paramMap, JsonNode.class);
		
		System.out.println("return value:" + jsonObject.getBody().get("code").textValue());
		
		
		
		
		return "test";
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
