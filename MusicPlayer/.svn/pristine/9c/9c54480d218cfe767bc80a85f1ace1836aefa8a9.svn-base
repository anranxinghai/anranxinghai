package com.anranxinghai.musicplayer.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

	
	public static String send(String url, Map <String,String> param){
//		urlString = urlString + "/servlet/SearchDB";
		HttpClient httpClient = new DefaultHttpClient();
		String result = "请求失败";
		HttpResponse httpResponse = null ;
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (String string : param.keySet()) {
			NameValuePair nameValuePair  = new BasicNameValuePair(string,param.get(string));
			list.add(nameValuePair);
		}
		
		
		HttpEntity httpEntity;
		try {
			httpEntity = new UrlEncodedFormEntity(list);
			HttpPost httpGetRequest = new HttpPost(url);
			httpGetRequest.setEntity(httpEntity);
			
			result = "请求失败";
				try {
					httpResponse = httpClient.execute(httpGetRequest);
				} catch (ClientProtocolException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return result;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return result;
				}
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				try {
					result  = EntityUtils.toString(httpResponse.getEntity());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				result = "请求失败";
			}
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println(result);
		result = result.substring(2,result.length());
		return result;
	}
}
