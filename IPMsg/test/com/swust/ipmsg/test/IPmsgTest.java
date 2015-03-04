package com.swust.ipmsg.test;

import com.swust.ipmsg.util.Packet;


public class IPmsgTest{

	public static void main(String[] args) {
		String string = "1:100:shirouzu::sdfsd:jupiter:32:Hello::ese";
		Packet packet = new Packet(string);
		System.out.println("版本:"+packet.getVision());
		System.out.println("包编号:"+packet.getPacketNO());
		System.out.println("发送者名字:"+packet.getSenderName());
		System.out.println("发送主机:"+packet.getSenderType());
		System.out.println("命令编号:"+packet.getCommandNO());
		System.out.println("附加信息区域:"+packet.getAdditionalSection());
	}
		
}
