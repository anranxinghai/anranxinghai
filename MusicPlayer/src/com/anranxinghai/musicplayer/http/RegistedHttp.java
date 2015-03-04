package com.anranxinghai.musicplayer.http;

import java.util.Map;

import com.anranxinghai.musicplayer.util.HttpUtil;

public class RegistedHttp {
	private String url = "http://10.132.12.165:8080/Server/RegistedServlet";
	//private String url = "http://192.168.0.103:8080/Server/RegistedServlet";
	
	public boolean isRegisted(Map<String,String> params){
		String result = HttpUtil.send(url,params);
		if ("registed".equals(result)) {
			return true;
		} else {
			return false;
		}
	}
}