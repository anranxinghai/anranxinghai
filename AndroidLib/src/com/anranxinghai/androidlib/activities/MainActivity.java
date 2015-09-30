package com.anranxinghai.androidlib.activities;

import java.util.Random;

import com.anranxinghai.androidlib.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	/**
	 * 随机生成字符串（一般用户数据库中id）
	 * @param length 要生成的字符串的长度
	 * @return
	 */
	public static String randomString(int length){
		Random random = null;
		char[] numbersAndLetters = null;
		if (length < 1) {
			return null;
		}
		if (random == null) {
			random = new Random();
			numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" +
	                   "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
		}
		char[] chars = new char[length];
		for (int i = 0; i < chars.length; i++) {
			chars[i] = numbersAndLetters[random.nextInt(71)];
		}
		
		return new String(chars);
	}
}
