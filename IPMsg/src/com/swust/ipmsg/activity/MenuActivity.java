package com.swust.ipmsg.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import com.swust.ipmsg.IPMsgApplication;
import com.swust.ipmsg.R;
import com.swust.ipmsg.service.IPmsgService;
import com.swust.ipmsg.service.PacketInternet;
import com.swust.ipmsg.util.IPMsg;
import com.swust.ipmsg.util.Packet;

public class MenuActivity extends Activity {
	/** Called when the activity is first created. */

	long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {

			if ((System.currentTimeMillis() - exitTime) > 2000) // System.currentTimeMillis()无论何时调用，肯定大于2000
			{
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {

				setContentView(R.layout.activity_end);

				new CountDownTimer(500, 1000) {
					@Override
					public void onTick(long millisUntilFinished) {
					}

					@Override
					public void onFinish() {
						/*
						 * Intent intent = new Intent(); LinkedList<Activity>
						 * activitys = IPMsgApplication.getActivities();
						 * intent.setClass(MenuActivity.this,
						 * WelcomeActivity.class); startActivity(intent);
						 */

						int VERSION = Build.VERSION.SDK_INT;
						if (VERSION >= 5) {
							MenuActivity.this.overridePendingTransition(
									R.anim.alpha_in, R.anim.alpha_out);
						}
						
					}
				}.start();
				mediaPlayer.stop();
				mediaPlayer = null;
				IPMsgApplication.getInstance().exit();
				
				// android.os.Process.killProcess(android.os.Process.myPid());
			}

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	// 添加控件
	private ImageButton enter = null;
	private ImageButton exit = null;
	private ImageButton settings = null;

	protected final MediaPlayer MediaPlayer = null;
	MediaPlayer mediaPlayer = MediaPlayer;

	// Activity的OnCreate方法
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);// 调用父类的OnCreate方法
		IPMsgApplication.getInstance().addActivity(this);
		// 自动跳转Activity——在onCreate里设置个Timer，然后建立Intent指向你要调用Activity。 设置Timer
		// 10妙后执行startActivity就行了
		/*
		 * final Intent it = new Intent(this,OtherProjectActivity.class); Timer
		 * timer = new Timer(); TimerTask task = new TimerTask(){ public void
		 * run(){ startActivity(it); } }; timer.schedule(task, 1000*10);
		 */

		// 取消标题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

	/*	// 进行全屏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

		setContentView(R.layout.activity_menu);// 加载布局main.xml文件

		// 播放音乐
		mediaPlayer = android.media.MediaPlayer.create(this, R.raw.mm);
		mediaPlayer.setLooping(true);// setLooping()——设置是否循环播放

		// 用 findViewById(R.id.XXX);方法获得控件的ID。
		enter = (ImageButton) findViewById(R.id.enter);
		exit = (ImageButton) findViewById(R.id.exit);
		settings = (ImageButton) findViewById(R.id.settings);

		// View v = findViewById(R.id.button1);//找到你要设透明背景的layout 的id
		// v.getBackground().setAlpha(0);//0~255透明度值
		// View v1 = findViewById(R.id.button2);//找到你要设透明背景的layout 的id
		// v1.getBackground().setAlpha(1);//0~255透明度值

		// 设置监听器
		enter.setOnClickListener(new MenuListener());
		// application.setBackgroundColor(Color.TRANSPARENT);
		exit.setOnClickListener(new MenuListener());
		// exit.setBackgroundColor(Color.TRANSPARENT);
		settings.setOnClickListener(new MenuListener());
	}

	class MenuListener implements OnClickListener {
		// 按应用按钮响应跳转事件

		public void onClick(View v) {

			Intent intent = new Intent();
			/*intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
	                | Intent.FLAG_ACTIVITY_SINGLE_TOP
	                | Intent.FLAG_ACTIVITY_CLEAR_TOP);*/
			switch (v.getId()) {
			case R.id.enter:
				Packet packet= new Packet(MenuActivity.this);
				packet.setCommandNO(IPMsg.IPMSG_NEW_BR_ENTRY);
				//PacketInternet.broadcastMessage(packet);
				intent.setClass(MenuActivity.this, MainActivity.class);
				MenuActivity.this.startActivity(intent);
				break;
			case R.id.exit:
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						MenuActivity.this);// 创建一个AlertDialog.Builder对象在当前弹出新的界面
				dialog.setTitle("退出飞鸽？")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setMessage("请选择：")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										mediaPlayer.stop();
										Intent intent = new Intent(
												MenuActivity.this,
												IPmsgService.class);
										stopService(intent);
										IPMsgApplication.getInstance().exit();
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// 按取消按钮的响应事件
										dialog.cancel();// 取消弹出框
										// progressDialog =
										// ProgressDialog.show(ProjectActivity.this,
										// true);
										// progressDialog.setCancelable(true);//当点击按钮返回的时候Dialog消失
									}
								}).create().show();// 展示弹出框
				break;
			case R.id.settings:
				if (mediaPlayer.isPlaying()) {
					dialog = new AlertDialog.Builder(MenuActivity.this);// 创建一个AlertDialog.Builder对象在当前弹出新的界面
					dialog.setTitle("设置附加功能")
							.setIcon(android.R.drawable.ic_dialog_info)
							.setMessage("请选择：")
							.setPositiveButton("关闭音乐",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {

											// 关闭音乐的代码
											// mediaPlayer.release();
											mediaPlayer.stop();
											// mediaPlayer.setvolume();//设置音量
										}
									})
							.setNegativeButton("文件传输",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {

											// 开启文件传输的代码

										}
									})
							.setNeutralButton("图片传输",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {

											// 开启图片传输的代码

										}
									}).create().show();
				} else {
					dialog = new AlertDialog.Builder(MenuActivity.this);// 创建一个AlertDialog.Builder对象在当前弹出新的界面
					dialog.setTitle("设置附加功能")
							.setIcon(android.R.drawable.ic_dialog_info)
							.setMessage("请选择：")
							.setPositiveButton("开启音乐",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {

											// 开启音乐的代码
											// mediaPlayer.release();
											mediaPlayer = android.media.MediaPlayer
													.create(MenuActivity.this,
															R.raw.mm);
											mediaPlayer.setLooping(true);// setLooping()——设置是否循环播放
											mediaPlayer.start();
											// mediaPlayer.setvolume();//设置音量
										}
									})
							.setNegativeButton("文件传输",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {

											// 开启文件传输的代码

										}
									})
							.setNeutralButton("图片传输",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {

											// 开启图片传输的代码

										}
									}).create().show();
					
				}
				break;
			default:
				break;
			}
		}
	}

}
