package com.anranxinghai.musicplayer.runnable;

import java.util.Map;

import com.anranxinghai.musicplayer.http.RegisterHttp;

import android.R.bool;

public class RegisterThread extends SecurityThread {

	
	private Map<String, String> params;
	private boolean isRegisterSuccess = false;
	
	
	
	public boolean isRegisterSuccess() {
		return isRegisterSuccess;
	}



	public RegisterThread(Map<String, String> param) {
		this.params = param;
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub

		if (!stopRequested) {
			RegisterHttp registerHttp = new RegisterHttp();
			isRegisterSuccess = registerHttp.postSuccess(params);
		}
	}

}
