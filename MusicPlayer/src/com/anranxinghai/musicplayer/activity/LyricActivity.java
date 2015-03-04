package com.anranxinghai.musicplayer.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.anranxinghai.musicplayer.MusicPlayerApplication;
import com.anranxinghai.musicplayer.R;
import com.anranxinghai.musicplayer.activity.MusicPlayerActivity.MusicListAdapter;
import com.anranxinghai.musicplayer.constant.Constant;
import com.anranxinghai.musicplayer.serializable.Lyric;
import com.anranxinghai.musicplayer.serializable.PlayListItem;
import com.anranxinghai.musicplayer.ui.LyricView;

public class LyricActivity extends Activity implements OnCompletionListener,
		OnErrorListener, OnSeekBarChangeListener, Runnable {

	private LyricView lyricView;
	// private String path = "mnt/sdcard/qqmusic/song/那些年我们一起追过的女孩.mp3";
	// private String songPath = "/mnt/sdcard/qqmusic/song/那些年我们一起追过的女孩.mp3";
	// private String lyricPath = "/mnt/sdcard/Tencent/QQfile_recv/我们都一样.lrc";
	// private String lyricPath = "/mnt/sdcard/anranxinghai";
	public static Lyric lyric;

	private SeekBar playSeekBar;
	private ImageButton btnPlay;
	private TextView currentTime, totalTime, showName;
	private ArrayList<String> list;
	private ProgressDialog progressDialog;// 进度条对话框。
	private MusicListAdapter mla;// 适配器
	private MediaPlayer mediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lyric);
		lyricView = (LyricView) findViewById(R.id.text_lyric);

		mediaPlayer = MusicPlayerApplication.getInstance().getMediaPlayer();
		/*
		 * mp.reset(); try { File file = new File(songPath); FileInputStream fis
		 * = new FileInputStream(file); mp.setDataSource(fis.getFD());
		 * mp.prepare(); } catch (IllegalArgumentException e) {
		 * 
		 * e.printStackTrace(); } catch (IllegalStateException e) {
		 * 
		 * e.printStackTrace(); } catch (IOException e) {
		 * 
		 * e.printStackTrace(); } mp.start();
		 */
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String lyricUrl = bundle.getString("lyricUrl");
		list = Constant.list;
		PlayListItem playListItem = new PlayListItem(Constant.musicInfos[0].get(Constant.list.get(Constant.currentIndex)),
				list.get(Constant.currentIndex), 0L, true);
		lyric = new Lyric(new File(lyricUrl), playListItem);

		lyricView.setLyric(lyric);
		lyricView.setSentences(lyric.list);
		lyricView.setNotCurrentPaintColor(Color.BLUE);
		lyricView.setCurrentPaintColor(Color.RED);
		lyricView.setLrcTextSize(20);
		lyricView.setCurrentTypeface(Typeface.SERIF);
		lyricView.setBackgroundColor(Color.WHITE);
		lyricView.setTextHeight(40);
		initView();
		new Thread(new UIUpdateThread()).start();
		// new Thread(this).start();
		// Constant.es.shutdown();
		Constant.es.execute(this);
	}

	/*
	 * @Override public boolean onKeyDown(int keyCode, KeyEvent event) { if
	 * (keyCode == KeyEvent.KEYCODE_BACK ) { finish(); mp.stop(); mp.release();
	 * return false; } return false; }
	 */
	// 初始化控件
	private void initView() {
		btnPlay = (ImageButton) findViewById(R.id.btn_play_lyric);
		playSeekBar = (SeekBar) findViewById(R.id.seek_play_seek_bar_lyric);
		playSeekBar.setOnSeekBarChangeListener(this);
		currentTime = (TextView) findViewById(R.id.text_current_time_lyric);
		totalTime = (TextView) findViewById(R.id.text_total_time_lyric);
		showName = (TextView) findViewById(R.id.text_show_name_lyric);
	}

	private void playOrPause() {
		switch (Constant.currentState) {
		case Constant.IDLE:
			start();
			break;
		case Constant.PAUSE:
			mediaPlayer.pause();
			btnPlay.setImageResource(R.drawable.ic_media_play);
			Constant.currentState = Constant.START;
			break;
		case Constant.START:
			mediaPlayer.start();
			btnPlay.setImageResource(R.drawable.ic_media_pause);
			Constant.currentState = Constant.PAUSE;
			new Thread(new UIUpdateThread()).start();
		default:
			break;
		}
	}

	private void start() {

		if (list.size() > 0 && Constant.currentIndex < list.size()) {
			String songPath = list.get(Constant.currentIndex);
			mediaPlayer.reset();
			try {
				PlayListItem playListItem = new PlayListItem(Constant.musicInfos[0].get(Constant.list.get(Constant.currentIndex)),
						list.get(Constant.currentIndex), 0L, true);
				lyric = new Lyric(new File(Constant.HOME + Constant.musicInfos[0].get(Constant.list.get(Constant.currentIndex)) + ".lrc"),
						playListItem);

				lyricView.setLyric(lyric);

				lyricView.setSentences(lyric.list);
				onResume();
				new Thread(new UIUpdateThread()).start();
				mediaPlayer.setDataSource(songPath);
				mediaPlayer.prepare();
				mediaPlayer.start();
				initSeekBar();
				new Thread(this).start();
				String fileName = new File(songPath).getName();
				showName.setText(fileName);
				btnPlay.setImageResource(R.drawable.ic_media_pause);
				Constant.currentState = Constant.PAUSE;
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else {
			Toast.makeText(this, "播放列表为空", Toast.LENGTH_SHORT).show();
		}
	}

	private void initSeekBar() {
		// TODO Auto-generated method stub
		int time = mediaPlayer.getDuration();
		playSeekBar.setMax(time);
		playSeekBar.setProgress(0);
		totalTime.setText(toTime(time));
	}

	private String toTime(int time) {
		// TODO Auto-generated method stub
		int minute = time / 1000 / 60;
		int s = time / 1000 % 60;
		String mm = null;
		String ss = null;
		if (minute < 10) {
			mm = "0" + minute;
		} else {
			mm = minute + "";
		}
		if (s < 10) {
			ss = "0" + s;
		} else {
			ss = s + "";
		}

		return mm + ":" + ss;

	}

	private void previous() {
		if ((Constant.currentIndex - 1) >= 0) {
			Constant.currentIndex--;
			start();
		} else {
			Toast.makeText(this, "现在已经是第一首歌了", Toast.LENGTH_SHORT).show();
		}
	}

	private void next() {
		if ((Constant.currentIndex + 1) < list.size()) {
			Constant.currentIndex++;
			start();
		} else {
			Toast.makeText(this, "现在已经是最后首歌了", Toast.LENGTH_SHORT).show();
		}
	}

	// 播放按钮
	public void playOrPause(View v) {
		playOrPause();
	}

	// 上一首按钮
	public void playPrevious(View v) {
		previous();
	}

	// 下一首按钮
	public void playNext(View v) {
		next();
	}

	class UIUpdateThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			while (mediaPlayer.isPlaying()) {
				lyricView.updateIndex(mediaPlayer.getCurrentPosition());
				mHandler.post(mUpdateResults);
			}
		}

	}

	Handler mHandler = new Handler();
	Runnable mUpdateResults = new Runnable() {
		public void run() {
			lyricView.invalidate();
			/*
			 * int currentTime = mediaPlayer.getCurrentPosition(); if
			 * (currentTime<playSeekBar.getMax()) {
			 * playSeekBar.setProgress(currentTime); Message message =
			 * handler.obtainMessage
			 * (Constant.CURR_TIME_VALUE,toTime(currentTime));
			 * handler.sendMessage(message); try { Thread.sleep(500); } catch
			 * (InterruptedException e) { // TODO: handle exception
			 * e.printStackTrace(); } }else { Constant.flag = false; }
			 */
		}
	};

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		if (fromUser) {
			mediaPlayer.seekTo(progress);
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		mediaPlayer.reset();
		return false;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		if (list.size() > 0) {
			next();
		} else {
			Toast.makeText(this, "当前列表为空", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int currentTime = mediaPlayer.getCurrentPosition();
		if (currentTime < playSeekBar.getMax()) {
			playSeekBar.setProgress(currentTime);
			Message message = handler.obtainMessage(Constant.CURR_TIME_VALUE,
					toTime(currentTime));
			handler.sendMessage(message);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else {
			Constant.flag = false;
		}
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case Constant.CURR_TIME_VALUE:
				currentTime.setText(msg.obj.toString());
				break;

			default:
				break;
			}
		}

	};
}