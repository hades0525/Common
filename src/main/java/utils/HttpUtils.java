package com.citycloud.ccuap.intellisense.utils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import lombok.extern.slf4j.Slf4j;

/** 
 * @ClassName: TFDataserverUtils 
 * @Description: TODO
 * @author: Hyman-->guonq@citycloud.com.cn
 * @date: 2019年11月28日 下午8:46:44 
 *  
 */
@Slf4j
public class HttpUtils {
	
	//token过期时间
	private final static  Duration timeout = Duration.ofMinutes(5);

	 public static String doPost(String url, MultipartFile file) { 
		 // 创建Httpclient对象 
	        CloseableHttpClient httpClient = HttpClients.createDefault(); 
	 
	       //设置请求超时时间
	       RequestConfig requestConfig = RequestConfig.custom()         
	             .setConnectTimeout(100000)
	             .setConnectionRequestTimeout(100000)
	 
	             .setSocketTimeout(100000)
	             .build();
	 
	        CloseableHttpResponse response = null; 
	        String resultString = ""; 
	        try { 
	            // 创建Http Post请求 
	            HttpPost httpPost = new HttpPost(url); 
	 
	            httpPost.setConfig(requestConfig);
	            // 创建请求内容 ，发送json数据需要设置contentType
	            
	            MultipartEntityBuilder builder = 
	            		MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
	            builder.setCharset(
	            		Charset.forName("UTF-8")).
	            addBinaryBody("file", file.getInputStream(), 
	            		ContentType.create(file.getContentType()), file.getOriginalFilename());
	            org.apache.http.HttpEntity entity = builder.build();
	            httpPost.setEntity(entity);
	            // 执行http请求 
	            response = httpClient.execute(httpPost); 
	            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
	                resultString = EntityUtils.toString(response.getEntity(), "utf-8"); 
	            }
	        } catch (Exception e) { 
	            e.printStackTrace(); 
	        } finally { 
	            try { 
	                response.close(); 
	            } catch (IOException e) { 
	                // TODO Auto-generated catch block 
	                e.printStackTrace(); 
	            } 
	        } 
	 
	        return resultString;
	    }

	
	
	public static JSONObject doPost(Map<String,Object> datas,Map<String,String> param){
		log.warn("#################上报接口数据参数#############" + datas);
		log.warn("#################上报接口,接口参数#############" + param);
		RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String privateKey = param.get("privateKey");
        headers.set("accessToken", create(privateKey));
        headers.set("publicKey", "TYSL-HQZZ");
        //设置类型
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);

        HttpEntity<Map<String,Object>> formEntity = 
        		new HttpEntity<Map<String,Object>>(datas, headers);
        String url = param.get("url");
        //将数据封装好后,推送出去处理
        ResponseEntity<JSONObject> forEntity = 
        		restTemplate.postForEntity(url, formEntity, JSONObject.class);
        log.warn("#################上报接口,返回参数#############" + forEntity.getBody());
        return forEntity.getBody();
	}
	
	

    //生成token
    public  static String create(String privateKey)
    {
        LocalDateTime localDateTime =  LocalDateTime.now().plusMinutes(timeout.toMinutes());
        Date date = Date.from( localDateTime.atZone( ZoneId.systemDefault()).toInstant());
        String token ="";
        try {
            token = JWT.create()
                    .withExpiresAt(date)
                    .sign(Algorithm.HMAC256(privateKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }


    /***
     * 校验token是否有效
     * @param token
     * @return
     */
    public  static boolean verify(String token,String privateKey)
    {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(privateKey))
                    .build();
            verifier.verify(token);
            return  true;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return  false;
        }
    }
    
}
