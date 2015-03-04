package com.swust.ipmsg.test;

import java.util.Calendar;

import junit.framework.TestCase;

import com.swust.ipmsg.service.PacketInternet;
import com.swust.ipmsg.util.IPMsg;
import com.swust.ipmsg.util.Packet;

public class PacketInternetTest extends TestCase{
	long packet_number = Calendar.getInstance().getTime().getTime();
	String msgString = 1 + ":" + packet_number + ":" + "anranxinghai" + ":"
			+ "Hisense" + ":" + IPMsg.IPMSG_NEW_BR_ENTRY + ":" + "anranxinghai";
	String Hisense = PacketInternet.getMyInformation().getIpAddress();
	Packet packet = new Packet(msgString);
	public void testSendOnLineMessage(){
		PacketInternet.sendMessage(packet,Hisense);
		System.out.println(packet.toString());
	}
	
/*	public void testSendOffLineMessage(){
		packet.setCommandNO(IPMsg.IPMSG_BR_EXIT);
		PacketInternet.sendMessage(packet,Hisense);
	}*/
}
