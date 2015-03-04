package com.swust.ipmsg;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.LinkedList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.swust.ipmsg.receiver.ConnectionReceiver;
import com.swust.ipmsg.util.IPMsg;

public class IPMsgApplication extends Application {
	private static LinkedList<Activity> activities = new LinkedList<Activity>();
	public static IPMsgApplication instance  = null;
	private static Context context = null;
	private static DatagramSocket datagramSocket= null;
	private ConnectionReceiver connectionReceiver = null;
	//之前这里是private，限制了IPMsgApplication建立，会报错
	public IPMsgApplication(){
	}
	
	
	static {
		try {
			datagramSocket = new DatagramSocket(IPMsg.IPMSG_DEFAULT_PORT);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		connectionReceiver = new ConnectionReceiver();
		registerReceiver(connectionReceiver, intentFilter);
		super.onCreate();
	}


	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}




	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}





	



	@Override
	public void unregisterReceiver(BroadcastReceiver receiver) {
		// TODO Auto-generated method stub

		if (connectionReceiver != null) {
			unregisterReceiver(connectionReceiver);
		}
		super.unregisterReceiver(receiver);
	}


	//单例模式中获取唯一的IPMsgApplication实例
	public static IPMsgApplication getInstance() {
		if (instance == null ) {
			instance = new IPMsgApplication();
		}
		return instance;
	}
	
	public static Context getContext(){
		return context;
	}
	
	
	
	public static void setDatagramSocket(DatagramSocket datagramSocket) {
		IPMsgApplication.datagramSocket = datagramSocket;
	}


	public static DatagramSocket getDatagramSocket() {
		return datagramSocket;
	}

	/**
	 * 添加Activity到容器中
	 */
	public void addActivity(Activity activity) {
		activities.add(activity);
		context = activities.get(0);
	}
	
	
	
	public static LinkedList<Activity> getActivities() {
		return activities;
	}
	
	


/*	public static Person getMyInformation() {
		return me;
	}*/


	/**
	 * 遍历所有Activity并finish
	 */
	public void exit() {
		for (Activity activity : activities) {
			activity.finish();
		}

		//PacketInternet.exit();
		
		if (datagramSocket!=null) {
			datagramSocket.close();
			datagramSocket = null;
		}
	}
}
