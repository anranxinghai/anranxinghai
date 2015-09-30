package com.anranxinghai.applicationa.utils;

import java.util.Random;

public class UserInfoUtils {
	

	private static Random random = null;
	private static char[] numbersAndLetters = null;
	
	
	public static String randomString(int length){
		
		if (length < 1) {
			return null;
		}
		if (random == null) {
			random = new Random();
			numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" +
	                   "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
		}
		char[] chars = new char[length];
		for (int i = 0; i < chars.length; i++) {
			chars[i] = numbersAndLetters[random.nextInt(71)];
		}
		
		return new String(chars);
	}
}
