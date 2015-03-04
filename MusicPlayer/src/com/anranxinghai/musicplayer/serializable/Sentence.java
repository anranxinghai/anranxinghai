package com.anranxinghai.musicplayer.serializable;

import java.io.Serializable;

public class Sentence implements Serializable {
	
	private static final long serialVersionUID = 20140723L;
	private long startTime = 0;
	private long endTime = 0;
	private String content;
	private final static long DISAPPEAR_TIME = 1000L;
	
	public Sentence(String content,long startTime, long endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.content = content;
	}

	public Sentence(String content, long startTime) {
		this(content, startTime, 0);
	}
	
	public Sentence(String content) {
		this(content, 0);
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	/**
	 * 得到这一句的内容
	 * 
	 * @return 内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 检查某个时间是否包含在某句中间
	 * 
	 * @param time
	 *            时间
	 * @return 是否包含了
	 */
	public boolean isInTime(long time){
		return time >= startTime && time <= endTime;
	}
	
	/**
	 * 得到这个句子的时间长度,毫秒为单位
	 * 
	 * @return 长度
	 */
	public long getDuring(){
		return endTime - startTime;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{" + startTime + "(" + content + ")" + endTime + "}";
	}
	
	
	
	
}
