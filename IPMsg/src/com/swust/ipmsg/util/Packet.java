package com.swust.ipmsg.util;

import java.util.Calendar;

import com.swust.ipmsg.service.PacketInternet;


public class Packet {

	private String vision = null;
	private int packetNO = -1;
	private String senderName = null;
	private String senderType = null;
	private int commandNO = -1;
	private String additionalSection = null;
	
	private String group = null;
	private String firstAddtion = null;
	//在电脑端，这两个是分开的
	private String x = null;
	private String mac = null;
	
	private String ip = null;
	private int icon = -1;
	private String oneByte = null;
	private String lastAddtion = null;
	
	private static final String split = "\0";
	private static final String split2 = split + split;
	private static final String split3 = split + split + split;
	private static final String split5 = split + split + split + split + split;

	public Packet() {
		// TODO Auto-generated constructor stub
		DeviceInfor deviceInfor = new DeviceInfor();
		String telephoneName = deviceInfor.getTeleType();
		vision = "3.1.8";
		packetNO = (int) (Calendar.getInstance().getTime().getTime()%Integer.MAX_VALUE);
		senderName = telephoneName;
		senderType = "Android";
		commandNO = -1;
		additionalSection = telephoneName;
		
		firstAddtion = telephoneName;
		group = "Android";
		mac = deviceInfor.getMac();
		Person me = PacketInternet.getMyInformation();
		x = "2";
		ip = me.getIpAddress();
		icon = me.getIconId();
		oneByte = "10000001";
		lastAddtion = telephoneName;
		
		additionalSection = firstAddtion + split + group + split + mac + split5 + x + 
				split3 + icon;
		/*System.out.println(additionalSection);
		System.out.println("输出接收到的字符");
		byte [] b = additionalSection.getBytes();
		System.out.println(b);
		for (int i = 0; i < b.length; i++) {
			System.out.println(b[i]);
		}*/
		
	}

	public Packet(String string) {
		String[] strings = {"","","","","",""};
		// Splits this string using the supplied regularExpression. Equivalent
		// to split(regularExpression, 0). See split(CharSequence, int) for an
		// explanation of limit. See Pattern for regular expression syntax.
		System.out.print(string+"\n");
		
		char[] chars = string.toCharArray();
		
		int j = 0;
		for (int i = 0; i < chars.length;i++) {
			System.out.println(chars[i]);
			if (chars[i] == ':') {
				if (chars[i+1] == ':') {
					//strings[j] += chars[i] + chars[++i];如果是这样的话，它会把chars[i]和chars[i+1]的算数加法结果（58+58=116）作为字符串加上去
					strings[j]  += "" + chars[i] + chars[++i];
				}
				else {
					j++;
				}
			}
			else {
				strings[j] += chars[i];
			}
		}
		
		byte [] b = string.getBytes();
		System.out.println(b);
		for (int i = 0; i < b.length; i++) {
			System.out.println(b[i]);
		}
		vision = strings[0];
		packetNO = Integer.valueOf(strings[1]).intValue();
		senderName = strings[2];
		senderType = strings[3];
		commandNO = Integer.valueOf(strings[4]).intValue();
		additionalSection = strings[5];
		if (additionalSection.length() == 0 || additionalSection == null) {
			additionalSection = "";
		}else if(additionalSection.contains(split) ) {
			
			
			firstAddtion = "";
			group = "Android";
			mac = "";
			x = "";
			ip = "127.0.0.1";
			icon = -1;
			oneByte = "";
			lastAddtion = "";
			
			
			if (!senderType.equals("Android")) {
				String[] tem = null,tem1 = null,tem2 = null;
				tem = additionalSection.split(split5);
				tem1 = tem[0].split(split);
				firstAddtion = tem1[0];
				group = tem1[1];
				mac = tem1[2];
				
				tem2 = tem[1].split(split2);
				String[] tem3 = tem2[0].split(split); 
				x = tem3[0];
				ip = tem3[1];
				String[] tem4 = tem2[1].split(split);
				icon = Integer.valueOf(tem4[0]).intValue();
				oneByte = tem4[1];
				lastAddtion = tem2[2];
			}else{
				String[] tem = null;
				tem = additionalSection.split(split5);
				
				String[] tem1 = tem[0].split(split);
				
				byte[] b1 = tem[1].getBytes();
				for (int i = 0; i < b1.length; i++) {
					System.out.println(b1);
				}
				
				firstAddtion = tem1[0];
				group = tem1[1];
				mac = tem1[2];
			
				String[] tem2 = tem[1].split(split3); 
				x = tem2[0];
				icon = Integer.valueOf(tem2[1]).intValue();
			}
			
		}
	}

	public String getVision() {
		return vision;
	}

	public void setVision(String vision) {
		this.vision = vision;
	}

	public long getPacketNO() {
		return packetNO;
	}

	public void setPacketNO(int packetNO) {
		this.packetNO = packetNO;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	

	public String getSenderType() {
		return senderType;
	}

	public void setSenderType(String senderType) {
		this.senderType = senderType;
	}

	public int getCommandNO() {
		return commandNO;
	}

	public void setCommandNO(int commandNO) {
		this.commandNO = commandNO;
	}

	public String getAdditionalSection() {
		return additionalSection;
	}

	public void setAdditionalSection(String additionalSection) {
		this.additionalSection = additionalSection;
	}
	
	
	

	public String getGroup() {
		return group;
	}

/*	public void setGroup(String group) {
		this.group = group;
	}*/

	public String getFirstAddtion() {
		return firstAddtion;
	}

	/*public void setFirstAddtion(String firstAddtion) {
		this.firstAddtion = firstAddtion;
	}*/

	public String getMac() {
		return mac;
	}

	/*public void setMac(String mac) {
		this.mac = mac;
	}*/

	public String getIp() {
		return ip;
	}

	/*public void setIp(String ip) {
		this.ip = ip;
	}*/

	public int getIcon() {
		return icon;
	}

	/*public void setIcon(int icon) {
		this.icon = icon;
	}*/

/*	public String getOneByte() {
		return oneByte;
	}

	public void setOneByte(String oneByte) {
		this.oneByte = oneByte;
	}*/

	/*public String getLastAddtion() {
		return lastAddtion;
	}

	public void setLastAddtion(String lastAddtion) {
		this.lastAddtion = lastAddtion;
	}*/

	public byte[] getPacketBytes() {
		return toString().getBytes();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String packetString = vision + ":" + packetNO + ":" + senderName + ":"
				+ senderType + ":" + commandNO + ":" + additionalSection;
		return packetString;
	}

}
