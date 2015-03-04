package com.channelsoft.android.aboutus.activitys;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.channelsoft.android.aboutus.R;
//import com.channelsoft.android.aboutus.activitys.AboutActivity.PlaceholderFragment;
import com.channelsoft.android.aboutus.constant.Constant;
import com.channelsoft.android.aboutus.runnable.ImageDownloadRunnable;
import com.channelsoft.android.aboutus.runnable.ImageHttpGetRunnable;

/**
 * 此类是初始商机模块，以滑动图片的形式展现。
 * 
 * @author anranxinghai
 *
 */

public class AdDisplayActivity extends Activity implements OnClickListener,
		OnPageChangeListener {

	private ImageButton returnButton = null;
	private IntentFilter bFilter = null;

	private ImageHttpGetBroadcastReceiver broadcastRecv = null;
	/**
	 * ViewPager
	 */
	private ViewPager viewPager;

	/**
	 * 装提示点的ImageView数组
	 */
	private ImageView[] tips;

	/**
	 * 装ImageView数组
	 */
	private List<ImageView> mImageViews;

	/**
	 * 图片资源id
	 */
	private int[] imgIdArray;
	private ViewGroup group = null;
	private int                      currentItem;
    private ScheduledExecutorService scheduledExecutorService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		// 通过程序改变屏幕显示的方向，横屏
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addisplay);
		registerBroadcastReceiver();
		returnButton = (ImageButton) findViewById(R.id.return_back);
		returnButton.setOnClickListener(this);

		group = (ViewGroup) findViewById(R.id.viewGroup);
		viewPager = (ViewPager) findViewById(R.id.viewPager);

		new Thread(new ImageHttpGetRunnable("first", "1", this)).start();

		// 载入图片资源ID
		imgIdArray = new int[] { R.drawable.advertisement_1,
				R.drawable.advertisement_2, R.drawable.advertisement_3,
				R.drawable.advertisement_4, R.drawable.advertisement_5 };

		// 将点点加入到ViewGroup中
		tips = new ImageView[imgIdArray.length];
		for (int i = 0; i < tips.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(10, 10));
			tips[i] = imageView;
			if (i == 0) {
				tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}

			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
			layoutParams.leftMargin = 5;
			layoutParams.rightMargin = 5;
			group.addView(imageView, layoutParams);
		}

		// 将图片装载到数组中
		mImageViews = new ArrayList<ImageView>(imgIdArray.length);
		for (int i = 0; i < imgIdArray.length; i++) {
			ImageView imageView = new ImageView(this);
			mImageViews.add(imageView);
			imageView.setBackgroundResource(imgIdArray[i]);
		}
		
		Log.d("market", "默认加载图片！");

		// 设置Adapter
		viewPager.setAdapter(new AdvertisementViewPagerAdapter());
		// 设置监听，主要是设置点点的背景
		viewPager.setOnPageChangeListener(this);
		// 设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
		viewPager.setCurrentItem((mImageViews.size()) * 100);
		viewPager.setOnPageChangeListener(new OnPageChangeListener()
        {

            @Override
            public void onPageSelected(int position)
            {
                // TODO Auto-generated method stub
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0)
            {
                // TODO Auto-generated method stub

            }
        });

		/*
		 * if (savedInstanceState == null) {
		 * getFragmentManager().beginTransaction() .add(R.id.container, new
		 * PlaceholderFragment()).commit(); }
		 */
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (broadcastRecv!=null) {
			unregisterReceiver(broadcastRecv);
		}
		
	}

	class AdvertisementViewPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mImageViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mImageViews.get(position
					% mImageViews.size()));

		}

		/**
		 * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(
					mImageViews.get(position % mImageViews.size()), 0);
			return mImageViews.get(position % mImageViews.size());
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about, menu);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.return_back:
			intent.setClass(this, AboutActivity.class);
			startActivity(intent);
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		setImageBackground(arg0 % mImageViews.size());
	}

	/**
	 * 设置选中的tip的背景
	 * 
	 * @param selectItems
	 */
	private void setImageBackground(int selectItems) {
		// TODO Auto-generated method stub
		for (int i = 0; i < tips.length; i++) {
			if (i == selectItems) {
				tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	/*
	 * public static class PlaceholderFragment extends Fragment {
	 * 
	 * public PlaceholderFragment() { }
	 * 
	 * @Override public View onCreateView(LayoutInflater inflater, ViewGroup
	 * container, Bundle savedInstanceState) { View rootView =
	 * inflater.inflate(R.layout.fragment_about, container, false); return
	 * rootView; } }
	 */

	private class ImageHttpGetBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			group.removeAllViews();
			ArrayList<String> mImageStrings = intent
					.getStringArrayListExtra("mImageStrings");
			if (intent.getAction().equals(Constant.IMAGE_GET_AD_CANDOWNLOAD)) {
				
				
				
				String images[] = new String[mImageStrings.size()];
				for (int i = 0; i < images.length; i++) {
					images[i] =  mImageStrings.get(i);
				}
				ImageDownloadRunnable imageDownloadRunnable = new ImageDownloadRunnable(images, context);

				new Thread(imageDownloadRunnable).start();
			}else if (intent.getAction().equals(Constant.IMAGE_GET_AD_SUCCESS)) {
			

				this.getFileDir(Constant.SDCARD + "smarket/");
				
				// 载入图片资源ID

				BitmapFactory.Options option = new BitmapFactory.Options();
				option.inSampleSize = 1;
				
				
				
				
				// 将点点加入到ViewGroup中
				tips = new ImageView[paths.size()];
				for (int i = 0; i < tips.length; i++) {
					ImageView imageView = new ImageView(AdDisplayActivity.this);
					imageView.setLayoutParams(new LayoutParams(10, 10));
					tips[i] = imageView;
					if (i == 0) {
						tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
					} else {
						tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
					}

					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
							new ViewGroup.LayoutParams(
									LayoutParams.WRAP_CONTENT,
									LayoutParams.WRAP_CONTENT));
					layoutParams.leftMargin = 5;
					layoutParams.rightMargin = 5;
					group.addView(imageView, layoutParams);
				}

				// 将图片装载到数组中
				mImageViews = new ArrayList<ImageView>();
				for (int i = 0; i < paths.size(); i++) {
					Bitmap bm = BitmapFactory.decodeFile(paths.get(i), option);
					
					ImageView imageView = new ImageView(AdDisplayActivity.this);
					imageView.setImageBitmap(bm);
					mImageViews.add(imageView);
				}

				Log.d("Smarket", "服务器可用，从服务器下载图片。");
				
				// 设置Adapter
				viewPager.setAdapter(new AdvertisementViewPagerAdapter());
				// 设置监听，主要是设置点点的背景
				viewPager.setOnPageChangeListener(AdDisplayActivity.this);
				// 设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
				viewPager.setCurrentItem((mImageViews.size()) * 100);

			} else if (intent.getAction().equals(Constant.IMAGE_GET_FAILED)) {
				// 载入图片资源ID
				imgIdArray = new int[] { R.drawable.advertisement_1,
						R.drawable.advertisement_2, R.drawable.advertisement_3,
						R.drawable.advertisement_4, R.drawable.advertisement_5 };

				// 将点点加入到ViewGroup中
				tips = new ImageView[imgIdArray.length];
				for (int i = 0; i < tips.length; i++) {
					ImageView imageView = new ImageView(AdDisplayActivity.this);
					imageView.setLayoutParams(new LayoutParams(10, 10));
					tips[i] = imageView;
					if (i == 0) {
						tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
					} else {
						tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
					}

					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
							new ViewGroup.LayoutParams(
									LayoutParams.WRAP_CONTENT,
									LayoutParams.WRAP_CONTENT));
					layoutParams.leftMargin = 5;
					layoutParams.rightMargin = 5;
					group.addView(imageView, layoutParams);
				}

				// 将图片装载到数组中
				mImageViews = new ArrayList<ImageView>(imgIdArray.length);
				for (int i = 0; i < imgIdArray.length; i++) {
					ImageView imageView = new ImageView(AdDisplayActivity.this);
					mImageViews.add(imageView);
					imageView.setBackgroundResource(imgIdArray[i]);
				}
				Log.d("Smarket", "服务器不可用，使用默认图片。");
				// 设置Adapter
				viewPager.setAdapter(new AdvertisementViewPagerAdapter());
				// 设置监听，主要是设置点点的背景
				viewPager.setOnPageChangeListener(AdDisplayActivity.this);
				// 设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
				viewPager.setCurrentItem((mImageViews.size()) * 100);

				/*
				 * if (savedInstanceState == null) {
				 * getFragmentManager().beginTransaction() .add(R.id.container,
				 * new PlaceholderFragment()).commit(); }
				 */
			}
		}
		
		
		private List<String> items = null;//存放名称  
	     private List<String> paths = null;//存放路径  
	     private String rootPath = "/";  

		   
	     public void getFileDir(String filePath) {
	         try{  
	             items = new ArrayList<String>();  
	             paths = new ArrayList<String>();  
	             File f = new File(filePath);  
	             File[] files = f.listFiles();// 列出所有文件  
	            /* // 如果不是根目录,则列出返回根目录和上一目录选项  
	             if (!filePath.equals(rootPath)) {  
	                 items.add("返回根目录");  
	                 paths.add(rootPath);  
	                 items.add("返回上一层目录");  
	                 paths.add(f.getParent());  
	             }  */
	             // 将所有文件存入list中  
	             if(files != null){  
	                 int count = files.length;// 文件个数  
	                 for (int i = 0; i < count; i++) {  
	                     File file = files[i];  
	                     items.add(file.getName());  
	                     paths.add(file.getPath());  
	                 }  
	             }  
	         }catch(Exception ex){  
	             ex.printStackTrace();  
	         }  
	   
	     }  
	   

	}
	@Override
    protected void onStart()
    {
        // TODO Auto-generated method stub
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        // 每隔2秒钟切换一张图片
        scheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(), 2, 2, TimeUnit.SECONDS);
    }

    // 切换图片
    private class ViewPagerTask implements Runnable
    {

        @Override
        public void run()
        {
            // TODO Auto-generated method stub
            currentItem = (currentItem + 1) % mImageViews.size();
            // 更新界面
            // handler.sendEmptyMessage(0);
            handler.obtainMessage().sendToTarget();
        }

    }

    private Handler handler = new Handler()
                            {

                                @Override
                                public void handleMessage(Message msg)
                                {
                                    // TODO Auto-generated method stub
                                    // 设置当前页面
                                    viewPager.setCurrentItem(currentItem);
                                }
                            };
	// 广播接收器注册
	private void registerBroadcastReceiver() {
		broadcastRecv = new ImageHttpGetBroadcastReceiver();
		bFilter = new IntentFilter();
		bFilter.addAction(Constant.IMAGE_GET_AD_SUCCESS);
		bFilter.addAction(Constant.IMAGE_GET_FAILED);
		bFilter.addAction(Constant.IMAGE_GET_AD_CANDOWNLOAD);
		registerReceiver(broadcastRecv, bFilter);
	}
}
