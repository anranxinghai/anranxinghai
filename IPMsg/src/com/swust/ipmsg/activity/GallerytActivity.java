package com.swust.ipmsg.activity;

import java.util.HashMap;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.swust.ipmsg.IPMsgApplication;
import com.swust.ipmsg.R;
import com.swust.ipmsg.service.PacketInternet;
import com.swust.ipmsg.util.Constant;
import com.swust.ipmsg.util.IPMsg;
import com.swust.ipmsg.util.Packet;

public class GallerytActivity extends Activity implements
		AdapterView.OnItemClickListener,

		ViewSwitcher.ViewFactory {
//	private OnUpdateMyInformaiton onUpdateMyInformaiton = null;
	
	private final int MY_INFORMATION = 1;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		IPMsgApplication.getInstance().addActivity(this);
		// /窗体无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// 进行全屏
		/*
		 * this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 */

		/*OnUpdateMyInformaiton onUpdateMyInformaiton = (OnUpdateMyInformaiton) getIntent().getSerializableExtra("onUpdateMyInformaiton");
		setOnUpdateMyInformaiton(onUpdateMyInformaiton);*/
		setContentView(R.layout.activity_gallery);

		mSwitcher = (ImageSwitcher) findViewById(R.id.switcher);
		mSwitcher.setFactory(this); // //在使用一个mSwitcher之前，调用setFactory方法，要不setImageResource这个方法会报空指针异常。

		mSwitcher.setOnClickListener(new ChListener());// 监听事件

		mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_in));// setInAnimation：设置View进入屏幕时候使用的动画
											// ；AnimationUtils是
											// Android中动画模板工具；loadAnimation载入anim
											// Xml来完成混合型动画
		mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_out));// setOutAnimation: 设置View退出屏幕时候使用的动画

		// 一个Activity里如果直接用findViewById()的话,对应的是setConentView()的那个layout里的组件
		Gallery g = (Gallery) findViewById(R.id.gallery);
		g.setAdapter(new ImageAdapter(this, mThumbIds.length)); // 为Gallery装载图片
		// g.setOnItemClickListener(this);// 响应事件//
		// g.setOnItemSelectedListener(this);//setOnItemSelectedListener：是监听下拉框中的选择操作事件

		g.setSelection(100);// /////setSelection就是将输入的光标在mDefaultKeySsb遇到到第一个位置0.Selection.setSelection主要作用就是定位光标的位置
	}

	class ChListener implements OnClickListener {
		// 按应用按钮的响应事件
		@Override
		public void onClick(View v) {
		}
	}

	public void onItemSelected(AdapterView parent, View v, int position, long id) {
		// 参数的意义：AdapterView,发生Select时的AdapterView。如列表,或者网格(Grid),或者下拉框(Spinner)等都算作AdapterView
		// parent 发生选中事件的 AbsListView
		// View view,具体Select的View对象
		// position:选中的位置,从0开始,即 视图在一览中的位置（索引）。
		// id:选中View的id。即id 被点击条目的行 ID。这个是根据Adapter类型的getId(int
		// position)得来的。常用情景下的ListView,GridView一般都是NO_ID

		mSwitcher.setImageResource(mThumbIds[position % mImageIds.length]); // setImageResource与xml中的src的属性相匹配
		// 设置图片给imageView对象
	}

	public void onNothingSelected(AdapterView parent) {
		// 当视图中的处于选中状态的条目全部消失时执行的回调函数。 启动触控功能或适配器为空都可能导致选中条目消失。参数 parent
		// 没有任何选中条目的 AdapterView。
	}

	public View makeView() {
		Log.v("TAG", "makeView()");// ////
		ImageView i = new ImageView(this); // 产生ImageView对象
		i.setBackgroundColor(0xFF000000); // 设置背景颜色
		i.setScaleType(ImageView.ScaleType.FIT_CENTER);// scaleType是控制图片如何resized/moved来匹对ImageView的size（FIT_CENTER：把图片按比例扩大/缩小到View的宽度，居中显示）
		// 重新设置图片的宽高

		i.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT)); // setLayoutParams�������趨����
		// 表示这个子控件的父布局是一个ImageSwitcher,设置在表中的行的布局参数。
		// 重新设置Layout的宽高

		return i;// 返回imageView对象
	}

	private ImageSwitcher mSwitcher;

	public class ImageAdapter extends BaseAdapter {
		private int mGalleryItemBackground;
		private HashMap<Integer, View> mViewMaps;
		private int mCount;
		private LayoutInflater mInflater;

		public ImageAdapter(Context c, int count) {
			this.mCount = count;
			mViewMaps = new HashMap<Integer, View>(count);
			mInflater = LayoutInflater.from(GallerytActivity.this);
			// 见RES /值/ attrs.xml的的定义的<declare-styleable>
			// Gallery1.

			TypedArray a = obtainStyledAttributes(R.styleable.gallery); // obtainStyledAttributes
																		// 作用就是从我们自己定义的styleable.xml读取所需信息

			mGalleryItemBackground = a.getResourceId(
					R.styleable.gallery_android_galleryItemBackground, 0);
			a.recycle();
		}

		@Override
		public int getCount() {
			// 通常AdapterView会在调用getCount
			// 方法之后调用getView方法来获取根据Adapter数据生成的子View进行绘制，但是如果getCount返回0或负数，那么Adapter
			// 的getView方法将不被调用；
			// 用来获得相应的记录数——得到元素的个数——返回我们后台一共有多少数据
			// return mImageIds.length;
			return Integer.MAX_VALUE;// 如果返回为0那么说明没查询到记录
		}

		@Override
		public Object getItem(int position) {
			// 一般是继承抽象方法，或者某个类实现了接口后自动生成的。只是IDE的提示这里可能需要添加一些具体代码，可忽略。
			// 自动生成方法占位符，注释处表示此处只是一个占位符，可以添加自己的代码实现
			
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 返回一个加载了数据的view,以便于前面控件的调用

			Log.v("TAG", "getView() position=" + position + " convertView="
					+ convertView);
			View viewGroup = mViewMaps.get(position % mCount);
			ImageView imageView = null;
			TextView textView = null;
			int imagePposition = position; 
			if (viewGroup == null) {
				viewGroup = mInflater.inflate(R.layout.gallery_item, null); // inflate就相当于将一个xml中定义的布局找出来——即如果你的Activity里如果用到别的layout,比如对话框上的layout,你还要设置对话框上的layout里的组件(像图片ImageView,文字TextView)上的内容,你就必须用inflate()先将对话框上的layout找出来,然后再用这个layout对象去找到它上面的组件.
				imageView = (ImageView) viewGroup
						.findViewById(R.id.item_gallery_image);
				textView = (TextView) viewGroup
						.findViewById(R.id.item_gallery_text);
				imageView.setBackgroundResource(mGalleryItemBackground); // 设置Gallery背景图

				mViewMaps.put(position % mCount, viewGroup);

				imageView.setImageResource(mImageIds[position
						% mImageIds.length]); // setImageResource();实现图片在ImageSwitcher中的切换。与xml中的background属性相匹配
				imageView.setTag(position % mCount);
				imageView.setOnClickListener(new OnClickListener() {
					// 发送用户更新广播
					
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Toast.makeText(GallerytActivity.this, "您选择了第" + v.getTag() + "张图片",
								Toast.LENGTH_SHORT).show();
						int icon = (Integer) v.getTag();
						PacketInternet.getMyInformation().setIcon(icon);
						Intent intent = new Intent();
						//intent.putExtra("myIcon", icon);  
		                setResult(MY_INFORMATION, intent);
						finish();
					}
				});
				textView.setText(titles[position % mImageIds.length]);
			}
			return viewGroup;
		}
	}

	private String[] titles = { "图片1", "图片2", "图片3", "图片4", "图片5", "图片6",
			"图片7", "图片8", "图片9", "图片10", "图片11", "图片12", "图片13", "图片14",
			"图片15", "图片16", "图片17", "图片18", "图片19", "图片20", "图片21", "图片22",
			"图片23", "图片24", "图片25", "图片26", "图片27", "图片28", "图片29", "图片30",
			"图片31", "图片32", "图片33", "图片34", "图片35", "图片36", "图片37", "图片38",
			"图片39", "图片40" };

	private Integer[] mThumbIds = Constant.mThumbIds;

	private Integer[] mImageIds = Constant.mImageIds;

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// parent相当于点击图片的适配器的一个指针，可以通过parent来获得它里面装着的一切东西（The AdapterView where
		// the click happened. //指代当前的AdapterView ）
		// view是你点的item的view的句柄，就是你可以用这个view，来获得它里面的控件的id后操作控件（The view within
		// the AdapterView that was clicked (this will be a view provided by the
		// adapter) ）
		// position是你点击的图片在其适配器里的位置（生成listview时，适配器一个一个的做item，然后把他们按顺序排好队，在放到listview里，意思就是这图片是第position号做好的）
		// id是你点击的图片在其listview里的第几行的位置，大部分时候position和id的值是一样的（你点击的对应项目的id ）

		mSwitcher.setImageResource(mThumbIds[position % mImageIds.length]);
	}
	
	
	
	/*public interface OnUpdateMyInformaiton{
		public void onUpdateMyInformaiton();
	}

	public OnUpdateMyInformaiton getOnUpdateMyInformaiton() {
		return onUpdateMyInformaiton;
	}

	public void setOnUpdateMyInformaiton(OnUpdateMyInformaiton onUpdateMyInformaiton) {
		this.onUpdateMyInformaiton = onUpdateMyInformaiton;
	}
	*/
	
}