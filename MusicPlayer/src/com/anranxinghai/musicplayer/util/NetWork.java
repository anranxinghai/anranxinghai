package com.anranxinghai.musicplayer.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.util.Log;
import android.widget.Toast;

import com.anranxinghai.musicplayer.MusicPlayerApplication;

public class NetWork {

	public static boolean isWiFiConnected(){
		Context context = MusicPlayerApplication.getContext();
		ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    State wifi = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
	    if (wifi == State.CONNECTED||wifi==State.CONNECTING) {
	    	// unconnect network
	    	Log.d("wifi", "unconnected");
	    	Toast.makeText(context, "WIFI连接失败，请连接WIFI", Toast.LENGTH_SHORT).show();
	    	System.out.println("WIFI连接失败！");
	    	return false;
	     }else {
	    	 return true;
	     }
	}
}
