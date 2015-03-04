package com.swust.ipmsg.runnable;

import com.swust.ipmsg.service.PacketInternet;
import com.swust.ipmsg.util.IPMsg;
import com.swust.ipmsg.util.Packet;

public class OffLine extends SecurityThread implements Runnable {

	Packet packet = null;
	/*
	public OffLine (){
		
	}*/
	
	public OffLine (Packet packet){
		this.packet = packet;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		packet.setCommandNO(IPMsg.IPMSG_NEW_BR_EXIT);
		PacketInternet.broadcastMessage(packet);
		System.out.println("I'm onLine");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
