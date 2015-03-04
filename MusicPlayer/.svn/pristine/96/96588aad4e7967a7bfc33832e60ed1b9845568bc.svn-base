package com.anranxinghai.musicplayer.runnable;

import java.util.Map;

import android.os.Handler;
import android.os.Message;

import com.anranxinghai.musicplayer.constant.Constant;
import com.anranxinghai.musicplayer.http.FileTransHttp;
import com.anranxinghai.musicplayer.http.RegisterHttp;

public class FileTransThread extends SecurityThread {
	private Map<String, String> params;
	private boolean isDownloaded = false;
	private Handler handler;
	
	
	public FileTransThread(Map<String, String> params,Handler handler) {
		this.params = params;
		this.handler = handler;
	}

	public FileTransThread(Map<String, String> params) {
		this.params = params;
		handler = null;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub

		if (!stopRequested) {
			FileTransHttp fileTransHttp = new FileTransHttp(params);
			isDownloaded = fileTransHttp.downloadFile();
		}
		if (handler != null) {
			Message message = null;
			// isRegisted = true;
			if (isDownloaded) {
				message = handler.obtainMessage(Constant.DOWNLOAD_SUCCESS);

			} else {
				message = handler.obtainMessage(Constant.DOWNLOAD_FAILED);
			}
			handler.sendMessage(message);
		}
		
	}
}
