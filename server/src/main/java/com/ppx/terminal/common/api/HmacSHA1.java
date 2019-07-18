/**
 * 
 */
package com.ppx.terminal.common.api;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;




/**
 * // O5fviq3DGCB5NrHcl/JP6+xxF6s=
 * // System.out.println("99999999:" + HmacSHA1.genHMAC("111", "2222"));
 * @author mark
 * @date 2019年7月15日
 */
public class HmacSHA1 {  
	
	  
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";  
    
    public static String genHMAC(String data, String key) {  
        byte[] result = null;  
        try {  
            // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称    
            SecretKeySpec signinKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);  
            // 生成一个指定 Mac 算法 的 Mac 对象    
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);  
            // 用给定密钥初始化 Mac 对象    
            mac.init(signinKey);  
            // 完成 Mac 操作     
            byte[] rawHmac = mac.doFinal(data.getBytes());  
            result = Base64.encodeBase64(rawHmac);  
  
        } catch (NoSuchAlgorithmException e) {  
            System.err.println(e.getMessage());  
        } catch (InvalidKeyException e) {  
            System.err.println(e.getMessage());  
        }  
        if (null != result) {  
            return new String(result);  
        } else {  
            return null;  
        }  
    } 
} 