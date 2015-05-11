package com.swust.ipmsg.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;

import com.swust.ipmsg.receiver.ConnectionReceiver;
import com.swust.ipmsg.runnable.OffLine;
import com.swust.ipmsg.runnable.OnLine;
import com.swust.ipmsg.runnable.Receive;
import com.swust.ipmsg.runnable.SecurityThread;
import com.swust.ipmsg.util.IPMsg;
import com.swust.ipmsg.util.NetWork;
import com.swust.ipmsg.util.Packet;
import com.swust.ipmsg.util.Person;

@SuppressLint("ShowToast")
public class IPmsgService extends Service {

	
	private Person me;
	private Packet packet;

	private List<Map<Integer, Person>> friendList = new ArrayList<Map<Integer, Person>>();
	private Thread onLine = null;
	private Thread onLineSustaine = null;
	private Thread offLine = null;
	private Thread onLineListener = null;

	private SecurityThread receive = null;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		System.out.println("onCreate!");

						
	}
	
	
	


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		/*me = (Person) intent.getSerializableExtra("me");
		long packet_number = Calendar.getInstance().getTime().getTime();
		String msgString = "3.1.8" + ":" + packet_number + ":"
				+ me.getUseName() + ":" + me.getUseName() + ":"
				+ IPMsg.IPMSG_NEW_BR_ENTRY + ":" + me.getUseName();*/
		packet = new Packet(getApplicationContext());
		packet.setCommandNO(IPMsg.IPMSG_NEW_BR_ENTRY);
		
		onLine = new Thread(new OnLine(packet));
		offLine =new Thread(new OffLine(packet));
		receive = new Receive(this);
		if (NetWork.isWifiConnected(this)) {
			System.out.println("服务中WIFI连接成功！");
			onLine.start();
			receive.start();
			/*
			onLineSustaine = new OnLineSustaine(packet);
			onLineSustaine.start();*/
		} else {
			System.out.println("服务中WIFI连接失败！");
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
	
		if (!friendList.isEmpty()) {
			friendList.clear();
			friendList = null;
		}
		if (receive != null) {
			receive.stopRequest();
			receive = null;
		}
		System.out.println("onDestroy中可以释放一些资源!");
		PacketInternet.exit();
		super.onDestroy();
	}

	/*public void run() {
		System.out.println("Service is running!");
	}*/

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
/*
	@Override
	public void onRebind(Intent intent) {
		// TODO Auto-generated method stub
		super.onRebind(intent);
		System.out.println("onRebind");
	}*/





	public static Object getMessagesCountById(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

}