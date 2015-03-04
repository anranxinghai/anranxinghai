package com.swust.ipmsg.activity;

import com.swust.ipmsg.IPMsgApplication;
import com.swust.ipmsg.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MessageActivity extends Activity{

	
	TextView myTextView = null;
	
	public MessageActivity.MyHandler myHandler =  this.new MyHandler();// 定义Myhandler对象
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		IPMsgApplication.getInstance().addActivity(this);
		myTextView = (TextView) findViewById(R.id.mytext);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	
	
	public class MyHandler extends Handler {
		public MyHandler() {
		}

		public MyHandler(Looper L) {
			super(L);
		}

		public void handleMessage(Message msg) {
			Log.d("MyHandler", "handleMessage......");// 调试日志。//Log.d的输出颜色是蓝色的，仅输出debug调试的意思，但他会输出上层的信息，过滤起来可以通过DDMS的Logcat标签来选择
			super.handleMessage(msg);// super在java中代表当前对象的父类对象（与之相应，this代表当前对象），即调用父类中的同名方法
			// 此处跟新ＵＩ
			Bundle bundle = msg.getData();// getData:获得一组随意的基准关联说完这个事件
			String information = bundle.getString("information");// 返回关联文件给定关键,或设置空字符没有测绘类型存在为给定关键或一个空值明确地关联文件关键
			myTextView.setText(information);
		}
	}
	
	
}
