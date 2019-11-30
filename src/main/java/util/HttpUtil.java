package com.citycloud.ccuap.ybhw.util;

import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author cci
 */
public class HttpUtil {
	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	public static String doGet(String requestUrl) throws ResponseException {
		return doGet(requestUrl, null, null, "UTF-8");
	}

	public static String doGet(String requestUrl, Map<String, String> headers) throws ResponseException {
		return doGet(requestUrl, headers, null, "UTF-8");
	}

	public static String doGet(String requestUrl, Map<String, String> headers, Map<String, String> params)
			throws ResponseException {
		return doGet(requestUrl, headers, params, "UTF-8");
	}
	
	public static String doGet(String requestUrl, Map<String, String> headers, Map<String, String> params,
			String chatSet) throws ResponseException {
		StringBuilder remote = getURL(requestUrl, params, chatSet);
		logger.debug("remote url = {}", remote.toString());
		
		return process(remote.toString(), headers, new HttpGet(), null, chatSet);
	}
	
	public static String doDelete(String requestUrl) throws ResponseException {
		return doDelete(requestUrl, null, null, "UTF-8");
	}

	public static String doDelete(String requestUrl, Map<String, String> params)
			throws ResponseException {
		return doDelete(requestUrl, null, params, "UTF-8");
	}

	public static String doDelete(String requestUrl, Map<String, String> headers, Map<String, String> params) throws ResponseException {
		return doDelete(requestUrl, headers, params, "UTF-8");
	}

	public static String doDelete(String requestUrl, Map<String, String> headers, Map<String, String> params,
			String chatSet) throws ResponseException {
		StringBuilder remote = getURL(requestUrl, params, chatSet);
		logger.debug("remote url = {}", remote.toString());
		return process(remote.toString(), headers, new HttpDelete(), null, chatSet);
	}
	
	/**
	 * 拼装get请求地址
	 * 
	 * @param requestUrl
	 * @param params
	 * @param chatSet
	 * @return
	 */
	private static StringBuilder getURL(String requestUrl, Map<String, String> params,
			String chatSet) {
		StringBuilder url = new StringBuilder(requestUrl);
		if (params != null) {
			StringBuilder p = new StringBuilder();
			if (!requestUrl.contains("?")) {
				p.append("?");
			} else {
				p.append("&");
			}
			for (Map.Entry<String, String> entry : params.entrySet()) {
				try {
					p.append(entry.getKey());
					p.append("=");
					p.append(URLEncoder.encode(entry.getValue(), chatSet));
					p.append("&");
				} catch (UnsupportedEncodingException e) {
					logger.warn(e.getMessage());
				}
			}
			url.append(p.substring(0, p.length() - 1));
		}
		return url;
	}

	public static String doPost(String requestUrl) {
		return doPost(requestUrl, null, null, "UTF-8");
	}

	public static String doPost(String requestUrl, Map<String, String> headers) {
		return doPost(requestUrl, headers, null, "UTF-8");
	}

	public static String doPost(String requestUrl, Map<String, String> headers, Map<String, String> params) {
		return doPost(requestUrl, headers, params, "UTF-8");
	}

	public static String doPost(String requestUrl, Map<String, String> headers, Map<String, String> params, String chatSet)
			throws ResponseException {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (params != null) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		try {
			return process(requestUrl, headers, new HttpPost(), new UrlEncodedFormEntity(nvps, "UTF-8"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String doPost(String requestUrl, String json) throws ResponseException {
		return process(requestUrl, null, new HttpPost(), new StringEntity(json, ContentType.APPLICATION_JSON), "UTF-8");
	}
	
	public static String doPut(String requestUrl) {
		return doPut(requestUrl, null, null, "UTF-8");
	}

	public static String doPut(String requestUrl, Map<String, String> headers) {
		return doPut(requestUrl, headers, null, "UTF-8");
	}

	public static String doPut(String requestUrl, Map<String, String> headers, Map<String, String> params) {
		return doPut(requestUrl, headers, params, "UTF-8");
	}

	public static String doPut(String requestUrl, Map<String, String> headers, Map<String, String> params, String chatSet)
			throws ResponseException {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (params != null) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		try {
			return process(requestUrl, headers, new HttpPut(), new UrlEncodedFormEntity(nvps, "UTF-8"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String doPut(String requestUrl, String json) throws ResponseException {
		return process(requestUrl, null, new HttpPut(), new StringEntity(json, ContentType.APPLICATION_JSON), "UTF-8");
	}

	/**
	 * 
	 * @param headers
	 * @param requestUrl
	 * @param requestBase
	 * @param requestEntity
	 * @param chatSet
	 * @return 响应字符串
	 * @throws ResponseException
	 */
	public static String process(String requestUrl, Map<String, String> headers, HttpRequestBase requestBase,
			HttpEntity requestEntity, String chatSet) throws ResponseException {
		StringBuilder respBody = new StringBuilder();
		CloseableHttpClient httpclient = HttpClients.createDefault();
        URL url;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e1) {
            throw new ResponseException(e1);
        }
		String hostName = url.getHost();
		int port = url.getPort();
		String protocol = url.getProtocol();

		HttpHost targetHost = new HttpHost(hostName, port, protocol);
		
		if (headers!=null) {
			for (Map.Entry<String, String> header : headers.entrySet()) {
				requestBase.setHeader(header.getKey(), header.getValue());
			}
		}
		
		BufferedReader reader = null;
		try {
			requestBase.setURI(new URI(requestUrl));
			if (requestBase instanceof HttpEntityEnclosingRequestBase) {
				HttpEntityEnclosingRequestBase entityRequestBase = (HttpEntityEnclosingRequestBase) requestBase;
				entityRequestBase.setEntity(requestEntity);
			}
			HttpResponse response = httpclient.execute(targetHost, requestBase);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() >=400 ) {
				HttpEntity entity = response.getEntity();
				reader = new BufferedReader(new InputStreamReader(entity.getContent(), chatSet));
				String line = reader.readLine();
				while (line != null) {
					respBody.append(line);
					line = reader.readLine();
				}
				throw new ResponseException("响应代码:" + statusLine.getStatusCode() + ",错误描述："
						+ respBody);
			} else {
				HttpEntity entity = response.getEntity();
				reader = new BufferedReader(new InputStreamReader(entity.getContent(), chatSet));
				String line = reader.readLine();
				while (line != null) {
					respBody.append(line);
					line = reader.readLine();
				}
				EntityUtils.consume(entity);
			}

		} catch (Exception e) {
			throw new ResponseException(e);
		} finally {
			requestBase.releaseConnection();
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }
		logger.debug("response={}", respBody.toString());
		return respBody.toString();
	}
}
