package com.channelsoft.android.aboutus.runnable;

import android.R.color;
import android.content.Context;
import android.util.Log;

import com.channelsoft.android.aboutus.constant.Constant;
import com.channelsoft.android.aboutus.http.FeedbackCommit;
import com.channelsoft.android.aboutus.http.ImageHttpGet;

public class ImageHttpGetRunnable implements Runnable{

	
	private String type = null;
	private String version = null;
	private Context context = null;
	
	
	public ImageHttpGetRunnable(String type, String version, Context context) {
		super();
		this.type = type;
		this.version = version;
		this.context = context;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		 // TODO Auto-generated method stub
		Log.d("smarket", "开始下载图片");
		ImageHttpGet imageHttpGet = new ImageHttpGet(context);
		imageHttpGet.getImage(type, version);
	}

}
