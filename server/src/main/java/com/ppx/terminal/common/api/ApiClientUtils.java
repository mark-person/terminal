/**
 * 
 */
package com.ppx.terminal.common.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @author mark
 * @date 2019年7月12日
 */
public class ApiClientUtils {
	public static String API_SECRET_KEY = "";
	
	public static String API_URL = "";
	
	public static String getSign(MultiValueMap<String, String> paramMap) {
		StringBuffer sb = new StringBuffer();
		List<String> paraList = new ArrayList<String>();
		paraList.addAll(paramMap.keySet());
		Collections.sort(paraList);
		for (String name : paraList) {
			List<String> vList = paramMap.get(name);
			String v = StringUtils.collectionToDelimitedString(vList, "");
			sb.append(name).append(v);
		}
		return HmacSHA1.genHMAC(sb.toString(), ApiClientUtils.API_SECRET_KEY);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static ApiReturnBody call(String url, Map<String, List<String>> paraMap) {
		RestTemplate rest = new RestTemplate();
		MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		paramMap.add("accessKey", "007");
		paramMap.add("timestamp", sdf.format(new Date()));
		paramMap.putAll(paraMap);
		String sign = ApiClientUtils.getSign(paramMap);
		paramMap.add("sign", sign);
		
		
		
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> jsonObject = rest.postForEntity(ApiClientUtils.API_URL + url, paramMap, Map.class);
		@SuppressWarnings("unchecked")
		Map<String, Object> apiReturn = jsonObject.getBody();
		
		
		return new ApiReturnBody(apiReturn);
		
	}
	
}
