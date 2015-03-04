package com.anranxinghai.musicplayer;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.LinkedList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;

public class MusicPlayerApplication extends Application {
	private static LinkedList<Activity> activities = new LinkedList<Activity>();
	public static MusicPlayerApplication instance  = null;
	private static Context context = null;
	private static MediaPlayer mediaPlayer = null;
	//之前这里是private，限制了IPMsgApplication建立，会报错
	public MusicPlayerApplication(){
	}
	
	
	static {
			mediaPlayer = new MediaPlayer();
	}
	
	

	public static MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}



	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
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







	//单例模式中获取唯一的IPMsgApplication实例
	public static MusicPlayerApplication getInstance() {
		if (instance == null ) {
			instance = new MusicPlayerApplication();
		}
		return instance;
	}
	
	public static Context getContext(){
		return context;
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
	}
}
