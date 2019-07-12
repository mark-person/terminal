/**
 * 
 */
package com.ppx.terminal.mvc.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		ResponseEntity<JsonNode> jsonObject = rest.postForEntity("http://localhost:9001/api/cell/getCellBit?cellCode=15785369", 
				null, JsonNode.class);
		System.out.println("xxxxout:" + jsonObject.getBody().get("code").textValue());
		
		return "test";
	}

	
	
}
