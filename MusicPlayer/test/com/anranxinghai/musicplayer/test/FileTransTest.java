package com.anranxinghai.musicplayer.test;

import java.util.HashMap;
import java.util.Map;

import com.anranxinghai.musicplayer.runnable.FileTransThread;

import junit.framework.TestCase;

public class FileTransTest extends TestCase{

	public void testTransFile(){
		String songName = "我们都一样";
		Map<String, String> params = new HashMap<String, String>();
		params.put("musicName", songName);
		new FileTransThread(params).start();
	}
}
