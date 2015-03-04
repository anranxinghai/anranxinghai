package com.swust.ipmsg.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.swust.ipmsg.IPMsgApplication;
import com.swust.ipmsg.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class DeviceInfor {
	private Context context;

	private String imei = null;
	private String imsi = null;
	private String teleType = null; // 手机型号
	private String teleNum = null; // 手机号码，有的可得，有的不可得
	private String[] cpuInfor = null;
	private String mac = null;
	private int ipAddress;

	public DeviceInfor() {
		context = IPMsgApplication.getContext();
		TelephonyManager mTm = null;
		try {
			mTm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);

			 imei = mTm.getDeviceId();
			imsi = mTm.getSubscriberId();
			teleNum = mTm.getLine1Number(); // 手机号码，有的可得，有的不可得
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("DeviceInfor", "context.getSystemService failed!");
		}

		teleType = android.os.Build.MODEL; // 手机型号
	
		cpuInfor = getCpuInfo();
		mac = getMacAddress();
		ipAddress = getIPAddress();
	}

	/*
	 * public DeviceInfor(Context context) { this.context = context;
	 * TelephonyManager mTm = null; try { mTm = (TelephonyManager) context
	 * .getSystemService(Context.TELEPHONY_SERVICE); } catch (Exception e) {
	 * e.printStackTrace(); Log.e("DeviceInfor",
	 * "context.getSystemService failed!"); }
	 * 
	 * imei = mTm.getDeviceId(); imsi = mTm.getSubscriberId(); teleType =
	 * android.os.Build.MODEL; // 手机型号 teleNum = mTm.getLine1Number(); //
	 * 手机号码，有的可得，有的不可得 cpuInfor = getCpuInfo(); mac = getMacAddress(); ipAddress
	 * = getIPAddress(); }
	 */
	private final String TAG = "DeviceInfor";

	public String getAllApp() {
		String result = "";
		List<PackageInfo> packages = context.getPackageManager()
				.getInstalledPackages(0);
		for (PackageInfo i : packages) {
			if ((i.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
				result += i.applicationInfo.loadLabel(
						context.getPackageManager()).toString()
						+ ",";
			}
		}
		return result.substring(0, result.length() - 1);
	}

	public String[] getCpuInfo() {
		String str1 = "/proc/cpuinfo";
		String str2 = "";
		String[] cpuInfo = { "", "" };// 1-cpu型号 //2-cpu频率
		String[] arrayOfString;
		try {
			FileReader fr = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			for (int i = 2; i < arrayOfString.length; i++) {
				cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
			}
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			cpuInfo[1] += arrayOfString[2];
			localBufferedReader.close();
		} catch (IOException e) {
		}
		Log.i(TAG, "cpuinfo:" + cpuInfo[0] + " " + cpuInfo[1]);
		return cpuInfo;
	}

	private String getMacAddress() {
		String mac = "";
			WifiManager wifiManager = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			mac = wifiInfo.getMacAddress();
			//在没有wifi的情况下，返回的mac的值为null
			if (mac != null) {
				mac = mac.replaceAll(":", "-");
			}else {
				Toast.makeText(context, "Wifi连接失败，请确认Wifi处于开启状态", Toast.LENGTH_LONG).show();
				mac = "00-00-00-00-00-00";
			}
			Log.i(TAG, "MAC:" + mac);
		
		return mac;
	}

	private Dialog buildDialog(Context context) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle(R.string.alert_dialog_seting_wifi_title);
		builder.setMessage(R.string.alert_dialog_msg);
		builder.setPositiveButton(R.string.alert_dialog_ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(
								Settings.ACTION_ACCESSIBILITY_SETTINGS);
						//context.startActivity(intent);
					}
				});
		builder.setNegativeButton(R.string.alert_dialog_cancle,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				});
		return builder.create();
	}

	private int getIPAddress() {
		int ipAddress = 0;
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		ipAddress = wifiInfo.getIpAddress();
		if (ipAddress == 0 ) {
			ipAddress = 2130706433;
		}
		Log.i(TAG, "ipAddress:" + ipAddress);
		return ipAddress;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getMtype() {
		return teleType;
	}

	public void setMtype(String teleType) {
		this.teleType = teleType;
	}

	public String getNumer() {
		return teleNum;
	}

	public void setNumer(String teleNum) {
		this.teleNum = teleNum;
	}

	public String[] getCpuInfor() {
		return cpuInfor;
	}

	public void setCpuInforStrings(String[] cpuInfor) {
		this.cpuInfor = cpuInfor;
	}

	public String getMacInfor() {
		return mac;
	}

	public void setMacInfor(String mac) {
		this.mac = mac;
	}

	public String getTAG() {
		return TAG;
	}

	public String getTeleType() {
		return teleType;
	}

	public void setTeleType(String teleType) {
		this.teleType = teleType;
	}

	public String getTeleNum() {
		return teleNum;
	}

	public void setTeleNum(String teleNum) {
		this.teleNum = teleNum;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public int getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(int ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void setCpuInfor(String[] cpuInfor) {
		this.cpuInfor = cpuInfor;
	}

}
