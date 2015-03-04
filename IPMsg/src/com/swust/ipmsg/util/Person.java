package com.swust.ipmsg.util;

import java.io.Serializable;


public class Person implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userId;
	private String userName;
	private String hostName;
	private String ipAddress;// IP地址
	private String macAddress;
	private int icon = -1;
	public Person(){
		DeviceInfor deviceInfor = new DeviceInfor();
		String name = deviceInfor.getTeleType();
		userName = name;
		hostName = "Android";
		//因为局域网内每个人的IP总是不同的（IP冲突的情况不算，当然，冲突的时候通信是错误的）。
		userId = deviceInfor.getIpAddress();
		ipAddress = IPv4Util.intToIp(deviceInfor.getIpAddress());
		macAddress = deviceInfor.getMac();
		icon = 3;
	}
/*	public Person(Context context){
		DeviceInfor deviceInfor = new DeviceInfor();
		useName = deviceInfor.getTeleType();
		hostName = deviceInfor.getTeleType();
		//因为局域网内每个人的IP总是不同的（IP冲突的情况不算，当然，冲突的时候通信是错误的）。
		userId = deviceInfor.getIpAddress();
		ipAddress = IPv4Util.intToIp(deviceInfor.getIpAddress());
		macAddress = deviceInfor.getMac();
	}*/

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String useName) {
		this.userName = useName;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getMac() {
		return macAddress;
	}

	public void setMac(String mac) {
		this.macAddress = mac;
	}

	

	public int getIcon() {
		return Constant.mImageIds[icon % Constant.mImageIds.length];
	}
	public int getIconId(){
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	
	
}
