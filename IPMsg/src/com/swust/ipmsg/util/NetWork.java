package com.swust.ipmsg.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import com.swust.ipmsg.IPMsgApplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWork {
	
	private static Context context = IPMsgApplication.getContext();
	
	public static String localAddress = 
			IPv4Util.intToIp(new DeviceInfor(context).getIpAddress());
	
	public static String subAddress = 
			IPv4Util.intToIp(new DeviceInfor(context).getIpAddress()&0x00ffffff);
	
	public static String broadcastAddress = 
			IPv4Util.intToIp(new DeviceInfor(context).getIpAddress()|0xff000000);
	
	public NetWork(Context context){
		this.context = context;
	}
	
/*public NetWork(Context context){
		this.context = context;
	}*/
	
	/**
	 * 判断网络是否处于连接状态
	 * @param context
	 * @return true 如果处于连接状态，false 如果处于非连接状态
	 */
	public boolean isNetworkConnected(Context context) {  
	    if (context != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
	        if (mNetworkInfo != null) {  
	            return mNetworkInfo.isAvailable();  
	        }  
	    }  
	    return false;  
	}
	
	
	/**
	 * 判断WIFI是否处于连接状态
	 * @param context
	 * @return true 如果处于连接状态，false 如果处于非连接状态
	 */
	public static boolean isWifiConnected(Context context) {  
	    if (context != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mWiFiNetworkInfo = mConnectivityManager  
	                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);  
	        if (mWiFiNetworkInfo != null) {  
	            return mWiFiNetworkInfo.isAvailable();  
	        }
	    }  
	    return false;  
	}
	
	/**
	 * 判断移动网络是否处于连接状态
	 * @param context
	 * @return true 如果处于连接状态，false 如果处于非连接状态
	 */
	public boolean isMobileConnected(Context context) {  
	    if (context != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mMobileNetworkInfo = mConnectivityManager  
	                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);  
	        if (mMobileNetworkInfo != null) {  
	            return mMobileNetworkInfo.isAvailable();  
	        }  
	    }  
	    return false;  
	}
	
	
	public static int getConnectedType(Context context) {  
	    if (context != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
	        if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {  
	            return mNetworkInfo.getType();  
	        }  
	    }  
	    return -1;
	}
	
	
	
	/*// 检测网络连接状态,获得本机IP地址
		private class CheckNetConnectivity extends Thread {
			public void run() {
				try {// 判断并建立Wifi连接
					if (!wifiManager.isWifiEnabled()) {
						wifiManager.setWifiEnabled(true);
					}
					// 检查每一个网络接口上绑定的每一个IP ,如果不是环回地址并且10秒内可以连通。
					// 就获取这个IP地址，IP的String类型，IP的Byte类型
					for (Enumeration<NetworkInterface> en = NetworkInterface
							.getNetworkInterfaces(); en.hasMoreElements();) {
						NetworkInterface intf = en.nextElement();
						for (Enumeration<InetAddress> enumIpAddr = intf
								.getInetAddresses(); enumIpAddr.hasMoreElements();) {
							InetAddress inetAddress = enumIpAddr.nextElement();
							if (!inetAddress.isLoopbackAddress()) {
								if (inetAddress.isReachable(1000)) {
									localInetAddress = inetAddress;
									localIp = inetAddress.getHostAddress()
											.toString();
									localIpBytes = inetAddress.getAddress();
									System.arraycopy(localIpBytes, 0, regBuffer,
											44, 4);
								}
							}
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}
		*/
		
}
