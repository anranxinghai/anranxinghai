package com.channelsoft.android.aboutus.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.channelsoft.android.aboutus.R;
import com.channelsoft.android.aboutus.runnable.FeedbackCommitRunnable;
import com.channelsoft.android.aboutus.ui.QNToast;

/**
 * 反馈建议界面
 * 
 * @author Lihw
 * 
 * @date 2014.7.16
 */
public class FeedBackActivity extends Activity implements OnClickListener {
	private ImageButton adviceCommitButton; // 提交按钮
	private ImageButton backButton; // 返回按钮
	private EditText adviceEditText; // 建议输入框
	private EditText telNumberEditText; // 电话输入框
	private QNToast qnToast = new QNToast();
	private Handler messageHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				qnToast.makeText(FeedBackActivity.this, "提交成功，请等待开发人员与您联络！",
						Toast.LENGTH_SHORT).show();
				finish();
				break;
			case 0:
				qnToast.makeText(FeedBackActivity.this, "提交失败，请重新提交！",
						Toast.LENGTH_SHORT).show();

				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		backButton = (ImageButton) findViewById(R.id.advice_back);
		backButton.setOnClickListener(this);
		adviceCommitButton = (ImageButton) findViewById(R.id.advice_commit);
		adviceEditText = (EditText) findViewById(R.id.et_advice);
		telNumberEditText = (EditText) findViewById(R.id.et_telNumber);
		backButton.setOnClickListener(this);
		adviceCommitButton.setOnClickListener(this);
		adviceEditText.setOnClickListener(this);
		telNumberEditText.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.advice_back:
			intent.setClass(this, AboutActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.advice_commit:
			conmmitFeedback(adviceEditText.getText().toString(),
					telNumberEditText.getText().toString());
			break;
		default:
			break;
		}
	}

	/**
	 * 提交反馈建议数据方法
	 * 
	 * @param content
	 * @param contactTel
	 */
	public void conmmitFeedback(String content, String contactTel) {
		new Thread(new FeedbackCommitRunnable(content, contactTel,
				messageHandler)).start();
	}
}
