package com.swust.ipmsg.runnable;

import android.media.MediaPlayer;

import com.swust.ipmsg.IPMsgApplication;
import com.swust.ipmsg.R;
import com.swust.ipmsg.service.PacketInternet;
import com.swust.ipmsg.util.Packet;

public class OnLine implements Runnable {

	Packet packet = null;
	/*
	public OnLine(){
	}
	*/
	public OnLine(Packet packet){
		this.packet = packet;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		PacketInternet.broadcastMessage(packet);
		MediaPlayer mediaPlayer = MediaPlayer.create(IPMsgApplication.getContext(),R.raw.online);
		//mediaPlayer.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("I'm onLine");
	}

}
