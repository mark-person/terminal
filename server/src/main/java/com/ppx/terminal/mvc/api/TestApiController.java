/**
 * 
 */
package com.ppx.terminal.mvc.api;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author mark
 * @date 2019年7月12日
 */

@Controller
@RequestMapping("/testApi")
public class TestApiController {

	@RequestMapping("/test") @ResponseBody
	public String test() {
		
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		
		
		MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
		paramMap.add("cellCode", "15785369");
		
		
		// HttpEntity<String> formEntity = new HttpEntity<String>(paramMap, headers);
		
		ResponseEntity<JsonNode> jsonObject = rest.postForEntity("http://localhost:9001/api/cell/getCellBit", 
				paramMap, JsonNode.class);
		System.out.println("xxxxout:" + jsonObject.getBody().get("code").textValue());
		
		return "test";
	}

	
	
}
