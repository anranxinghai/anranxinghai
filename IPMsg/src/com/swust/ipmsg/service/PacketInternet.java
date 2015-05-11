package com.swust.ipmsg.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.swust.ipmsg.IPMsgApplication;
import com.swust.ipmsg.util.IPMsg;
import com.swust.ipmsg.util.Message;
import com.swust.ipmsg.util.NetWork;
import com.swust.ipmsg.util.Packet;
import com.swust.ipmsg.util.Person;

public class PacketInternet {

	public static final int BUFFER_LENGTH = 1024;

	public final static String SUB_ADDRESS = NetWork.subAddress;
	public final static String BROADCAST_ADDRESS = NetWork.broadcastAddress;

	public static final String LOCALHOST_ADDRESS = NetWork.localAddress;// *某个机器的地址
	public static final String MULTICAST_ADDRESS = "226.135.12.4";// *作为群聊的多播组的地址

	private static Person me = null;

	static int port = IPMsg.IPMSG_DEFAULT_PORT;// 定义端口，用整型数据表（整数）示端口号
	public static DatagramSocket sendSocket = null;// 定义数据报类型的套接字
	static DatagramPacket datagramPacket = null;

	@SuppressLint("UseSparseArrays")
	private static Map<Integer, List<Message>> chatMessages = new HashMap<Integer, List<Message>>();// 所有用户信息容器
	// String hostName = null;

	// 还真心需要
	private static ArrayList<Map<Integer, Person>> friendList = new ArrayList<Map<Integer, Person>>();
	private static Map<Integer, Person> friendMap = new HashMap<Integer, Person>();// 当前在线用户

	private static ArrayList<Integer> friendKeys = new ArrayList<Integer>();

	@SuppressLint("UseSparseArrays")
	public PacketInternet() {

	}

	static {

		
		/*
		 * Integer integer = 54685; friendMap.put(integer, me);
		 * friendKeys.add(integer);
		 */
		friendList.add(friendMap);
		me = new Person(IPMsgApplication.getContext());
		/*
		 * friendKeys.add(integer); friendList.add(friendMap);
		 */

	}

	/*
	 * public PacketInternet (Person me){ this.me = me; }
	 */

	public static void sendMessage(Packet packet, String IP_ADDRESS) {
		try {
			sendSocket = IPMsgApplication.getDatagramSocket();
			// Log.v("SocketInit", "succeed!");
		} catch (Exception e) {
			e.printStackTrace();
			// Log.e("SocketInit", "failed");
		}

		String string = packet.toString();
		System.out.println("\nbroadcastMessage Run Succeed:" + string);
		/*
		 * try { string = new String(string.getBytes(),"utf-8"); } catch
		 * (UnsupportedEncodingException e2) { // TODO Auto-generated catch
		 * block e2.printStackTrace(); }
		 */
		byte[] b;
		try {
			//Windows 系统默认编码为"gb2312"，为了让windows端接收信息不乱吗，应该以"gb2312"格式编码
			b = string.getBytes("gb2312");
			// 接受的数据包长度为length个字节。 DatagramPacket对象中包括通过DatagramSocket传输的数据。
			datagramPacket = new DatagramPacket(b, b.length);// 创建一个DatagramPacket对象接收数据

		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			datagramPacket.setAddress(InetAddress.getByName(IP_ADDRESS));
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}// 集设定接口的网络逻辑地址的目标主机
			// 设置目标主机的IP地址。
		datagramPacket.setPort(port);// 设置这个数据包目标主机的端口号。

		new Thread() {
			
			Context context = IPMsgApplication.getContext();
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				if (NetWork.isWifiConnected(context)) {
					try {
						if (sendSocket == null) {
							sendSocket = new DatagramSocket(IPMsg.IPMSG_DEFAULT_PORT);
						}
						sendSocket.send(datagramPacket);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					Toast.makeText(context, "WIFI断开，请连接WIFI！", Toast.LENGTH_LONG).show();
				}
				
			}

		}.start();

		System.out.println("Send Succeed!");

		System.out.println("packet Succeed!");
		// socket.close();
	}

	/**
	 * 把协议包广播到整个群组中
	 * 
	 * @param packet
	 *            要发送的包
	 * @throws SocketException
	 */
	public static void broadcastMessage(Packet packet) {
		sendMessage(packet, BROADCAST_ADDRESS);
		sendMessage(packet, SUB_ADDRESS);
		sendMessage(packet, "192.168.1.105");
		// sendMessage(packet, MULTICAST_ADDRESS);
	}

	public static ArrayList<Map<Integer, Person>> getFriendList() {
		return friendList;
	}

	public static ArrayList<Integer> getFriendKeys() {

		return friendKeys;
	}

	public static Map<Integer, Person> getFriendMap() {
		return friendMap;
	}

	public static Person getMyInformation() {
		return me;
	}
	
	

	public static Map<Integer, List<Message>> getChatMessages() {
		return chatMessages;
	}


	// 根据用户id获得该用户的消息
	public static List<Message> getMessagesById(int personId) {
		return chatMessages.get(personId);
	}
	
	

	// 根据用户id获得该用户的消息数量
	public static int getMessagesCountById(int personId) {
		List<Message> msgs = chatMessages.get(personId);
		if (null != msgs) {
			int i = 0,count = 0;;
			while (i<msgs.size() ) {
				if (!msgs.get(i).isRead()) {
					count++;
				}
				i++;
			}
			return count;
		} else {
			return 0;
		}
	}
	
	public static void exit(){
		if (sendSocket != null) {
			sendSocket.close();
			sendSocket = null;
			datagramPacket = null;
		}
	}
}
