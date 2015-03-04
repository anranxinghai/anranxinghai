package com.swust.ipmsg.activity;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.swust.ipmsg.IPMsgApplication;
import com.swust.ipmsg.R;


public class GroupActivity extends ExpandableListActivity implements
		OnGestureListener, OnTouchListener {

	GestureDetector detector;
	ImageButton button2;

	private int verticalMinDistance = 20;
	private int minVelocity = 0;

	// 声明adapter
	private ExpandableListAdapter mAdapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		IPMsgApplication.getInstance().addActivity(this);
		// 取消标题   
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		/*// 进行全屏                              
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
				WindowManager.LayoutParams.FLAG_FULLSCREEN); */
		
		setContentView(R.layout.activity_group);
		System.out.println("OnCreate succeed!");
		detector = new GestureDetector((OnGestureListener) this);
		LinearLayout viewSnsLayout = (LinearLayout) findViewById(R.id.viewSnsLayout);
		viewSnsLayout.setOnTouchListener(this);
		viewSnsLayout.setLongClickable(true);
		this.button2 = (ImageButton) this.findViewById(R.id.flip8);

		// 设这标题
		setTitle("群聊");

		// 实例化adapter
		mAdapter = (ExpandableListAdapter) new MyExpandableListAdapter();

		// 为列表设置adapter
		setListAdapter(mAdapter);

		// 为list注册context菜单
		registerForContextMenu(this.getExpandableListView());

		// 按钮背景变色
		//ImageButton button11 = (ImageButton) findViewById(R.id.flip8);
		//StateListDrawable drwBackgroundSend = (StateListDrawable) button11
		//		.getBackground();
		//PorterDuffColorFilter tintGreen = new PorterDuffColorFilter(Color.argb(
		//		200, 0, 253, 0), PorterDuff.Mode.SRC_ATOP);
		//drwBackgroundSend.setColorFilter(tintGreen);
		
		ImageButton QunToSi = null;
		QunToSi = (ImageButton)findViewById(R.id.flip7);
		QunToSi.setOnClickListener(new QTSListener());	

		ImageButton qunliao = (ImageButton) findViewById(R.id.flip8);
		qunliao.setOnClickListener(new ChatListener());
	}
	
	class ChatListener implements OnClickListener {
		// 按应用按钮的响应事件
		
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(GroupActivity.this,
					ChatActivity.class);
			GroupActivity.this.startActivity(intent);
		}
	}
	
	class QTSListener implements OnClickListener{

    	//按应用按钮的响应事件
    	public void onClick(View v) {
    		 Intent intent= new Intent();
             intent.setClass(GroupActivity.this, MainActivity.class);
             GroupActivity.this.startActivity(intent);
    	}
    }

	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// //定义了当可折叠列表里的子元素(child)发生点击事件时调用的回调方法的接口
		// parent:发生点击动作的ExpandableListView;
		// v:在expandable list/ListView中被点击的视图(View);
		// groupPosition:包含被点击子元素的组(group)在ExpandableListView中的位置(索引);
		// childPosition被点击子元素(child)在组(group)中的位置;
		// id被点击子元素(child)的行ID(索引)

		Toast.makeText(this,
				" 组元素索引: " + groupPosition + " 子元素索引: " + childPosition,
				Toast.LENGTH_SHORT).show();

		return super.onChildClick(parent, v, groupPosition, childPosition, id);// //当点击事件被处理时返回
	}

	public void onGroupExpand(int groupPosition) {
		Toast.makeText(this, " 组元素索引: " + groupPosition, Toast.LENGTH_SHORT)
				.show();

		super.onGroupExpand(groupPosition);// 每当展开当前可伸缩列表中的某个组时，就调用该方法
											// groupPosition 组位置，也就是展开的那个组的位置
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// Called when the context menu for this view is being built.

		// menu:The context menu that is being built
		// v:The view for which the context menu is being built
		// menuInfo:Extra information about the item for which the context menu
		// should be shown. This information will vary depending on the class of
		// v.

		// 长按类表中某元素时出现
		menu.setHeaderTitle("上下文菜单");
		menu.add(0, 0, 0, "单击我");
	}

	// 单击上下文菜单后的逻辑
	public boolean onContextItemSelected(MenuItem item) {
		ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item
				.getMenuInfo();// 得到菜单信息传给info

		String title = ((TextView) info.targetView).getText().toString();

		int type = ExpandableListView
				.getPackedPositionType(info.packedPosition);// 将得到的信息位置传给type
		if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
			// 选择父列表元素

			int groupPos = ExpandableListView
					.getPackedPositionGroup(info.packedPosition);// 得到父列表索引位置
			int childPos = ExpandableListView
					.getPackedPositionChild(info.packedPosition);// 得到子列表索引位置
			Toast.makeText(this,
					title + " 组元素索引: " + groupPos + " 子元素索引: " + childPos,
					Toast.LENGTH_SHORT).show();
			return true;
		} else if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
			// 选择子列表元素

			int groupPos = ExpandableListView
					.getPackedPositionGroup(info.packedPosition);
			Toast.makeText(this, title + " 组元素索引: " + groupPos,
					Toast.LENGTH_SHORT).show();
			return true;
		}
		return false;
	}

	// 自定义Adapter
	public class MyExpandableListAdapter extends BaseExpandableListAdapter {

		// 父列表数据
		private String[] groups = { "群组成员"};

		// 子列表数据
		private String[][] children = { {"大噶","北高峰" ,
				"恩格斯", "阿什顿" , "卡夫"} };

		public Object getChild(int groupPosition, int childPosition) {
			// 获取指定组中的指定子元素数据。

			// 参数：groupPosition——组位置（该组内部含有子元素）
			// childPosition——子元素位置（相对于其它子元素）

			return children[groupPosition][childPosition];// 返回指定子元素数据
		}

		public long getChildId(int groupPosition, int childPosition) {
			// 参数：groupPosition——组位置（该组内部含有子元素）
			// childPosition——子元素位置（相对于其它子元素）

			return childPosition;// 子元素关联ID
		}

		public int getChildrenCount(int groupPosition) {
			// 获取指定组中的子元素个数
			// 参数：groupPosition——组位置（决定返回哪个组的子元素个数）

			return children[groupPosition].length;// 返回值:指定组的子元素个数
		}

		// 取子列表中的某一项的 View
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// 获取一个视图对象，显示指定组中的指定子元素数据

			// 参数：groupPosition——组位置（该组内部含有子元素）；
			// childPosition——子元素位置（决定返回哪个视图）；
			// isLastChild——子元素是否处于组中的最后一个 ；
			// convertView——重用已有的视图(View)对象;
			// parent:返回的视图(View)对象始终依附于的视图组。

			// 注意：在使用前应该检查一下这个视图对象是否非空并且这个对象的类型是否合适。由此引伸出，如果该对象不能被转换并显示正确的数据，这个方法就会调用getChildView(int,
			// int, boolean, View, ViewGroup)来创建一个视图(View)对象。

			TextView textView = getGenericView();
			textView.setText(getChild(groupPosition, childPosition).toString());
			textView.setTextColor(Color.BLUE);// 改变子列表Text颜色

			return textView;// 指定位置上的子元素返回的视图对象
		}

		public Object getGroup(int groupPosition) {
			// 获取指定组中的数据
			// 参数:groupPosition——组位置

			return groups[groupPosition];
		}

		public int getGroupCount() {
			// 获取组的个数

			return groups.length;// 返回值：组的个数
		}

		public long getGroupId(int groupPosition) {
			// 获取指定组的ID，这个组ID必须是唯一的
			// 参数:groupPosition--组位置

			return groupPosition;// 返回组相关ID
		}

		// 取父列表中的某一项的 View
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// 获取显示指定组的视图对象。这个方法仅返回关于组的视图对象，要想获取子元素的视图对象，就需要调用getChildView(int,
			// int, boolean, View, ViewGroup)。

			// 参数:groupPosition--组位置（决定返回哪个视图）
			// isExpanded--该组是展开状态还是伸缩状态
			// convertView 重用已有的视图对象
			// parent——返回的视图对象始终依附于的视图组。

			TextView textView = getGenericView();// 返回指定组的视图对象
			textView.setText(getGroup(groupPosition).toString());
			textView.setTextColor(Color.RED);// 改变父列表Text颜色
			return textView;
		}

		public boolean hasStableIds() {
			// 组和子元素是否持有稳定的ID,也就是底层数据的改变不会影响到它们

			return true;// 返回一个Boolean类型的值，如果为TRUE，意味着相同的ID永远引用相同的对象
		}

		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// 是否选中指定位置上的子元素

			// 参数:groupPosition--组位置（该组内部含有这个子元素）
			// childPosition 子元素位置

			return true;// 返回值:是否选中子元素
		}

		// 获取某一项的 View 的逻辑
		private TextView getGenericView() {
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT, 48);
			// AbsListView：基类用于实现虚拟化细目一览表。
			// AbsListView的延长布局参数提供一个地方给视图类型

			// FILL_PARENT，即填满（和父容器一样大小）、WRAP_CONTENT，即包裹住组件就好

			TextView textView = new TextView(GroupActivity.this);
			textView.setLayoutParams(lp);// 安排子类的排版
			textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);// 字体动态改变
			textView.setPadding(32, 0, 0, 0);// setPadding (int left, int top,
												// int right, int
												// bottom（即：设置text与其容器（比如图片）之间的间隔）
			textView.setTextSize(20);// 改变字体大小
			return textView;
		}
	}

	public boolean onTouch(View v, MotionEvent event) {
		return this.detector.onTouchEvent(event);
	}

	
	public boolean onDown(MotionEvent e) {
		return false;
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (e2.getX() - e1.getX() > verticalMinDistance
				&& Math.abs(velocityX) > minVelocity) {
			System.out.println("change");
			// MainActivity
			Intent intent = new Intent(GroupActivity.this,
					MainActivity.class);
			startActivity(intent);
			// Toast.makeText(this, "ChangeActivity",
			// Toast.LENGTH_SHORT).show();
		}
		return false;
	}

	public void onLongPress(MotionEvent e) {
		
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	public void onShowPress(MotionEvent e) {
	}

	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}
}