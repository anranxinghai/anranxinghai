package com.swust.ipmsg.runnable;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.text.format.DateFormat;
import android.util.Log;

import com.swust.ipmsg.IPMsgApplication;
import com.swust.ipmsg.R;
import com.swust.ipmsg.service.PacketInternet;
import com.swust.ipmsg.util.Constant;
import com.swust.ipmsg.util.IPMsg;
import com.swust.ipmsg.util.IPv4Util;
import com.swust.ipmsg.util.Message;
import com.swust.ipmsg.util.Packet;
import com.swust.ipmsg.util.Person;
import com.swust.ipmsg.activity.GallerytActivity;
public class Receive extends SecurityThread{

	private DatagramSocket recieveSocket = null;
	private DatagramPacket datagramPacket = null;
	private Context context = null;
	private ArrayList<Map<Integer, Person>> friendList = null;
	private Map<Integer, Person> friendMap = null;// 当前在线用户

	private ArrayList<Integer> friendKeys = null;
	private Map<Integer, List<Message>> chatMessages = null;// 所有用户信息容器

	public Receive(Context context) {
		this.context = context;
		recieveSocket = IPMsgApplication.getDatagramSocket();

		friendList = PacketInternet.getFriendList();
		friendMap = PacketInternet.getFriendMap();
		friendKeys = PacketInternet.getFriendKeys();
		chatMessages = PacketInternet.getChatMessages();
	}

	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			System.out.println("recieve start!");
			recieveSocket = IPMsgApplication.getDatagramSocket();
			System.out.println("socket successed!");
			// recieveSocket.joinGroup(InetAddress.getByName(PacketInternet.MULTICAST_ADDRESS));
			// System.out.println("join group successed!");
			waitForResponse();
			System.out.println("recieve run successed!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Thread run error!");
		}
	}

	
	@Override
	public void stopRequest() {
		if (recieveSocket!=null) {
			recieveSocket.close();
			recieveSocket = null;
			
		}
		
		super.stopRequest();
	}
	
	public void waitForResponse() throws IOException {
		// 接收消息事件

		try {

			while (!stopRequested) {
				byte[] b = new byte[PacketInternet.BUFFER_LENGTH];
				datagramPacket = new DatagramPacket(b, b.length);
				recieveSocket.receive(datagramPacket);
				System.out.println("Recieve Succeed!");
				String information = new String(datagramPacket.getData(), "gb2312");
				information = information.trim();
				System.out.println(information);
				Log.d("Receive......", "mReceive......");

				if (datagramPacket.getPort() == IPMsg.IPMSG_DEFAULT_PORT) {
					Packet packet = new Packet(information);
					information = packet.getAdditionalSection();
					Thread packetAnalisyThread = new Thread(new PacketAnalysis(packet));
					packetAnalisyThread.start();
					System.out.println("\nReturn Succeed!");
					// MessageActivity.myHandler.sendMessage(msg);
				}
				Thread.sleep(300);
			}
		} catch (InterruptedException x) {
			Thread.currentThread().interrupt(); // re-assert interrupt
			// stopRequest();
		}

	}

	public class PacketAnalysis implements Runnable {

		private Packet packet = null;

		public PacketAnalysis(Packet packet) {
			this.packet = packet;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			int cmdType = packet.getCommandNO();
			switch (cmdType) {
			case IPMsg.IPMSG_BR_ENTRY:
			case IPMsg.IPMSG_NEW_BR_ENTRY:

				/*
				 * Integer i = 123;
				 * 
				 * friendKeys.add(i); friendMap.put(i, person);
				 */
				Person person = new Person();
				String ipString = datagramPacket.getAddress().toString()
						.substring(1);
				person.setUserId(IPv4Util.ipToInt(ipString));
				person.setIpAddress(ipString);
				person.setHostName(packet.getSenderType());
				person.setIcon(packet.getIcon());
				person.setMac(packet.getMac());
				person.setUserName(packet.getSenderName());
				Integer integer = person.getUserId();
				if (!friendKeys.contains(integer)) {
					friendKeys.add(integer);
				}
				friendMap.put(integer, person);
				// friendList.add(friendMap);
				packet = new Packet();
				packet.setCommandNO(IPMsg.IPMSG_ANSENTRY);
				PacketInternet.sendMessage(packet, person.getIpAddress());
				sendPersonHasChangedBroadcast();
				break;
			case IPMsg.IPMSG_ANSENTRY:
			case IPMsg.IPMSG_NEW_ANSENTRY:
				person = new Person();
				ipString = datagramPacket.getAddress().toString().substring(1);
				person.setUserId(IPv4Util.ipToInt(ipString));
				person.setIpAddress(ipString);
				person.setHostName(packet.getSenderType());
				person.setIcon(packet.getIcon());
				person.setMac(packet.getMac());
				person.setUserName(packet.getSenderName());
				integer = person.getUserId();
				if (!friendKeys.contains(integer)) {
					friendKeys.add(integer);
				}
				friendMap.put(integer, person);
				// friendList.add(friendMap);
				sendPersonHasChangedBroadcast();
				break;
			case IPMsg.IPMSG_NEW_BR_ABSENCE:
				person = new Person();
				ipString = datagramPacket.getAddress().toString().substring(1);
				person.setUserId(IPv4Util.ipToInt(ipString));
				person.setIpAddress(ipString);
				person.setHostName(packet.getSenderType());
				person.setIcon(packet.getIcon());
				person.setMac(packet.getMac());
				person.setUserName(packet.getSenderName());
				integer = person.getUserId();
				if (!friendKeys.contains(integer)) {
					friendKeys.add(integer);
				}
				friendMap.put(integer, person);
				// friendList.add(friendMap);
				sendPersonHasChangedBroadcast();
				break;
			case IPMsg.IPMSG_NEW_SENDMSG:
			case IPMsg.IPMSG_SENDMSG:
				String packetNO = new Long(packet.getPacketNO()).toString();
				person = new Person();
				Message message = new Message(getCurrentTime(),
						packet.getAdditionalSection());
				message.setMine(false);
				ipString = datagramPacket.getAddress().toString().substring(1);
				person.setUserId(IPv4Util.ipToInt(ipString));
				person.setIpAddress(ipString);
				person.setHostName(packet.getSenderType());
				person.setIcon(R.drawable.head_02);
				person.setMac("");
				person.setUserName(packet.getSenderName());
				int userId = person.getUserId();
				List<Message> messages = null;
				if (chatMessages.containsKey(userId)) {
					messages = chatMessages.get(userId);
				} else {
					messages = new ArrayList<Message>();
				}

				messages.add(message);
				chatMessages.put(userId, messages);

				Intent intent = new Intent();
				intent.setAction(Constant.hasMsgUpdatedAction);
				intent.putExtra("userId", userId);
				intent.putExtra("msgCount", messages.size());
				context.sendBroadcast(intent);
				packet = new Packet();
				packet.setCommandNO(IPMsg.IPMSG_RECVMSG);
				packet.setAdditionalSection(packetNO);
				PacketInternet.sendMessage(packet, person.getIpAddress());
				MediaPlayer mediaPlayer = MediaPlayer
						.create(context,
								R.raw.message);
				mediaPlayer.start();
				break;
			case IPMsg.IPMSG_BR_EXIT:
			case IPMsg.IPMSG_NEW_BR_EXIT:
				person = new Person();
				ipString = datagramPacket.getAddress().toString().substring(1);
				person.setUserId(IPv4Util.ipToInt(ipString));
				person.setIpAddress(datagramPacket.getAddress().toString());
				person.setHostName(packet.getSenderType());
				person.setIcon(R.drawable.head_02);
				person.setMac("");
				person.setUserName(packet.getSenderName());
				integer = person.getUserId();
				friendKeys.remove(integer);
				friendMap.remove(integer);
				sendPersonHasChangedBroadcast();
				break;
			default:
				break;
			}

		}
		
		private String getCurrentTime() {
			// TODO Auto-generated method stub
			String dateString = DateFormat.format("yyyy-MM-dd hh:mm:ss",
					new Date()).toString();
			return dateString;
		}

	}

	// 发送用户更新广播
	private void sendPersonHasChangedBroadcast() {
		Intent intent = new Intent();
		// 在intent中加入事件。
		intent.setAction(Constant.personHasChangedAction);
		context.sendBroadcast(intent);
	}

	
/*	public OnUpdateMyInformaiton onUpdateMyInformaiton = new OnUpdateMyInformaiton() {
		
		private void sendUpdateMyInformationBroadcast() {
			Intent intent = new Intent();
			// 在intent中加入事件。
			intent.setAction(Constant.updateMyInformationAction);

			Packet packet= new Packet();
			packet.setCommandNO(IPMsg.IPMSG_CHANICON);
			PacketInternet.broadcastMessage(packet);
			context.sendBroadcast(intent);
		}
		
		@Override
		public void onUpdateMyInformaiton() {
			// TODO Auto-generated method stub
			sendUpdateMyInformationBroadcast();
		}
	};*/
	
}