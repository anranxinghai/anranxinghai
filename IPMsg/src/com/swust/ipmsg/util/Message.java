package com.swust.ipmsg.util;

import java.io.Serializable;

import org.hamcrest.core.Is;

//消息类?
public class Message implements Serializable {// 可串行化（可序列化）
	private static final long serialVersionUID = 1L;//版本
	public String receivedTime = null;              //接收时间
	public String msg = null;                       //消息类
	private boolean isRead = false;
	private boolean isMine = false;
	// 两个构造方法。
	public Message() {
	};

	public Message(String receivedTime, String msg) {
		this.receivedTime = receivedTime;
		this.msg = msg;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public boolean isMine() {
		return isMine;
	}

	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}
	
	
}
