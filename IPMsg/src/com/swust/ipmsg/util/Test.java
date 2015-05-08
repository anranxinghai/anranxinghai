package com.swust.ipmsg.util;

public class Test {
	int inttype = -1;
	String stringType = "";
	static String staticTypeString = "" ;
	public Test(int i){
		this.inttype = i;
	}
	public Test(String string){
		this.stringType = string;
	}
	public Test(int i,String string){
		this(i);  
		this.stringType =string;
	}
	
	
	public static void print(Test test){
		//以下注释掉的调用会出错（在静态方法中不能调用非静态方法），如果要调用，利用未注释的部分
		/*inttype = 1;
		print(statictypeString);*/
		test.inttype = 5;
		staticTypeString = "static";
		test.print(staticTypeString);
	}
	
	public void print(String string){
		
	}
}
