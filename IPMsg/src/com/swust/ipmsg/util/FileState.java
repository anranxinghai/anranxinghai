package com.swust.ipmsg.util;
//文件状态，相当于一个结构体。
public class FileState {
	public long fileSize = 0;//文件大小
	public long currentSize = 0;//当前文件大小
	public String fileName = null;//文件名称
	public int percent = 0;
	
	public FileState(){
		//constructor
	}
	public FileState(long fileSize,long currentSize,String fileName){
		this.fileSize = fileSize;//文件大小
		this.currentSize = currentSize;//当前的文件大小
		this.fileName = fileName;//文件名
		//constructor
	}
}
