package com.swust.ipmsg.test;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String strings = "sdgdf.dfhgfh..sdfgh..args.dgh.";
		System.out.println("begin:");
		for (String string : strings.split("\\.")) {
			System.out.println(string);
		}
	}

}
