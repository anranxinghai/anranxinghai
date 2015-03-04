package com.swust.ipmsg.runnable;

import com.swust.ipmsg.service.PacketInternet;
import com.swust.ipmsg.util.Packet;

public class OnLineSustaine implements Runnable {

	Packet packet = null;
	
/*	public OnLineSustaine (){
		
	}*/
	
	public OnLineSustaine(Packet packet){
		this.packet = packet;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int i = 0;
		while (i < 4) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("I'm onLine");
			PacketInternet.broadcastMessage(packet);
			i++;
		}
	}

}
