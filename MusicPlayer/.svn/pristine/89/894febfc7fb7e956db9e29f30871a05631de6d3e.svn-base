package com.anranxinghai.musicplayer.runnable;

import java.util.Map;

import com.anranxinghai.musicplayer.http.RegistedHttp;
import com.anranxinghai.musicplayer.http.RegisterHttp;

import android.R.bool;
import android.os.Handler;
import android.os.Message;

public class RegistedThread extends SecurityThread {

	private static final int REGISTER_SUCCESS = 0;
	private static final int REGISTER_FAILED = 1;
	
	private Map<String, String> params;
	private boolean isRegisted = false;


	private Handler handler;
	
	
	public boolean isRegisted() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isRegisted;
	}



	public RegistedThread(Map<String, String> params,Handler handler) {
		this.params = params;
		this.handler = handler;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		if (!stopRequested) {
			RegistedHttp registedHttp = new RegistedHttp();
			isRegisted = registedHttp.isRegisted(params);
		}
		
		Message message = null;
		// isRegisted = true;
		if (isRegisted) {
			message = handler.obtainMessage(REGISTER_SUCCESS);

		} else {
			message = handler.obtainMessage(REGISTER_FAILED);
		}
		handler.sendMessageDelayed(message, 1000);
	}

}
