package com.swust.ipmsg.receiver;

import java.net.DatagramSocket;
import java.net.SocketException;

import com.swust.ipmsg.IPMsgApplication;
import com.swust.ipmsg.service.IPmsgService;
import com.swust.ipmsg.service.PacketInternet;
import com.swust.ipmsg.util.IPMsg;
import com.swust.ipmsg.util.Packet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class ConnectionReceiver extends BroadcastReceiver {
		   private final String TAG = "Internet";
		@Override
		   public void onReceive(Context context, Intent intent) {
		    ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		    intent.setClass(context, IPmsgService.class);
	    	
		    if (!wifiNetInfo.isConnected()) {
		    	// unconnect network
		    	Log.d(TAG, "unconnected");
		    	Toast.makeText(context, "WIFI连接失败，请连接WIFI", Toast.LENGTH_SHORT).show();
		    	context.stopService(intent);
		    	System.out.println("WIFI连接失败！");
		     }else {
				 // connect network
		    	 try {
					IPMsgApplication.setDatagramSocket(new DatagramSocket(IPMsg.IPMSG_DEFAULT_PORT));
				} catch (SocketException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	 Log.d(TAG, "connected");
		    	 Packet packet = new Packet(context);
		    	 packet.setCommandNO(IPMsg.IPMSG_NEW_BR_ENTRY);
		    	 context.startService(intent);
		    	 try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	 PacketInternet.broadcastMessage(packet);
		    	 Toast.makeText(context, "WIFI连接成功", Toast.LENGTH_SHORT).show();
		    	 System.out.println("WIFI连接成功！");
		     }
		   }
}
