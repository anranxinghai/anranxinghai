package com.channelsoft.android.aboutus.runnable;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;

import com.channelsoft.android.aboutus.constant.Constant;
import com.channelsoft.android.aboutus.utils.HttpUtils;

public class ImageDownloadRunnable implements Runnable {

	String[] imageFiles = null;
	Context context = null;
	
	
	public ImageDownloadRunnable(String[] imageFiles,Context context) {
		super();
		this.imageFiles = imageFiles;
		this.context = context;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < imageFiles.length; i++) {
			HttpUtils.downloadFile(imageFiles[i], Constant.SDCARD + "smarket/advertisement_" + i + ".png");
		}
		
		
		ArrayList<String> mImageStrings = new ArrayList<String>();
		Intent intent = new Intent();
		// 在intent中加入事件。
		intent.setAction(Constant.IMAGE_GET_AD_SUCCESS);
		context.sendBroadcast(intent);
	}

}
