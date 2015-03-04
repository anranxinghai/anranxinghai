package com.swust.ipmsg.test;

import java.io.UnsupportedEncodingException;

public class CharsetTest {
	public static void main(String[] args) {
		String str = "中文" ;  
		byte[] jiema = null;
		try {
			jiema = str.getBytes("gb2312");
			for (int i = 0; i < jiema.length; i++) {
				System.out.println(jiema[i]);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //解码  
		try {
			String   bianma = new String(jiema,"UTF-8");
			System.out.println("转化后："+jiema);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//编码 如果上面的解码不对 可能出现问题  
	}
}
