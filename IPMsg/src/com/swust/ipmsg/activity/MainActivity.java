package com.swust.ipmsg.activity;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import com.swust.ipmsg.IPMsgApplication;
import com.swust.ipmsg.R;
import com.swust.ipmsg.service.IPmsgService;
import com.swust.ipmsg.service.PacketInternet;
import com.swust.ipmsg.util.Constant;
import com.swust.ipmsg.util.IPMsg;
import com.swust.ipmsg.util.Packet;
import com.swust.ipmsg.util.Person;

public class MainActivity extends Activity implements OnTouchListener,
		OnGestureListener, OnClickListener {

	private TextView nickname;
	private ImageButton headIcon = null;
	// private EditText signature;
	private AutoCompleteTextView search;
	private ImageButton bt_1;
	private ImageButton searchButton = null;
	private ImageButton siToQun = null;
	private LinearLayout viewSnsLayout = null;
	private GestureDetector detector;
	public static final String SSS = "com.swust.ipmsg.ss";
	private String[] groups = null;
	// 声明adapter
	private BaseExpandableListAdapter friendListAdapter;
	private Person me = null;
	// 子列表数据
	private ArrayList<Map<Integer, Person>> personList = PacketInternet
			.getFriendList();
	private Map<Integer, Person> personMap = PacketInternet.getFriendMap();
	private ArrayList<Integer> personKeys = PacketInternet.getFriendKeys();
	private OperatesBroadcastReceiver operatesBroadcastReceiver = null;
	private IntentFilter bFilter = null;
	private ExpandableListView friendExpandableListView = null;
	private String[] searchPersons = null;
	private final int MY_INFORMATION = 1;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 取消标题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		/*
		 * // 进行全屏
		 * this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 */

		registerBroadcastReceiver();
		setContentView(R.layout.activity_main);
		searchPersons = new String[personMap.size()];
		
		// 实例化适配器，指定显示格式及数据源
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, searchPersons);
		IPMsgApplication.getInstance().addActivity(this);
		initView();
		// signature.requestFocusFromTouch();
		searchButton.setOnClickListener(this);
		// search.requestFocusFromTouch();

		System.out.println("onCreate Succeed!");

		detector = new GestureDetector(this);

		viewSnsLayout.setOnTouchListener(this);
		viewSnsLayout.setLongClickable(true);
		initView();

		// 指定自动完成控件的适配器。。/* 设置search的Adapter */
		search.setAdapter(adapter);

		// 设这标题
		setTitle("私聊群组成员");

		// 为列表设置adapter
		friendExpandableListView.setAdapter(friendListAdapter);

		// 为list注册context菜单
		// registerForContextMenu(this.getExpandableListView());

		headIcon.setOnClickListener(this);

		nickname.setText(me.getUserName());
		nickname.setOnClickListener(new SignListView());

		// 按钮背景变色
		// Button button11 = (Button) findViewById(R.id.button1);
		// StateListDrawable drwBackgroundSend = (StateListDrawable) button11
		// .getBackground();
		// PorterDuffColorFilter tintGreen = new
		// PorterDuffColorFilter(Color.argb(
		// 200, 0, 253, 0), PorterDuff.Mode.SRC_ATOP);
		// drwBackgroundSend.setColorFilter(tintGreen);
		siToQun.setOnClickListener(this);

	}

	private void initView() {

		// signature = (EditText) findViewById(R.id.text_signature);
		friendExpandableListView = (ExpandableListView) findViewById(R.id.friendList);
		siToQun = (ImageButton) findViewById(R.id.button_group);
		viewSnsLayout = (LinearLayout) findViewById(R.id.viewSnsLayout);
		me = PacketInternet.getMyInformation();
		groups = getResources().getStringArray(R.array.groupIndicatorLabeles);
		search = (AutoCompleteTextView) findViewById(R.id.text_search);
		searchButton = (ImageButton) findViewById(R.id.button_searche);
		nickname = (TextView) findViewById(R.id.text_nickname);
		headIcon = (ImageButton) findViewById(R.id.head_icon);
		headIcon.setImageDrawable(getResources().getDrawable(me.getIcon()));
		bt_1 = (ImageButton) this.findViewById(R.id.head_icon);
		friendListAdapter = (BaseExpandableListAdapter) new MainActivityExpandableListAdapter(
				this);
	}

	class SignListView implements OnClickListener {
		// 个性签名
		@Override
		public void onClick(View v) {
			InputMethodManager imm = (InputMethodManager) MainActivity.this
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			// signature.setCursorVisible(false);
			search.setCursorVisible(false);
			// imm.hideSoftInputFromWindow(signature.getWindowToken(),
			// InputMethodManager.HIDE_IMPLICIT_ONLY);
			imm.hideSoftInputFromWindow(search.getWindowToken(),
					InputMethodManager.RESULT_UNCHANGED_SHOWN);
			showCustomDialog();
		}

		public void showCustomDialog() {
			AlertDialog.Builder builder;
			AlertDialog alertdialog;
			Context mContext = MainActivity.this;

			// 下面俩种方法都可以
			// //LayoutInflater inflater = getLayoutInflater();
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.custom_dialog, null);
			TextView text = (TextView) layout.findViewById(R.id.text);
			text.setText("每一天、珍惜");
			ImageView image = (ImageView) layout.findViewById(R.id.image);
			image.setImageResource(R.drawable.sign);
			builder = new AlertDialog.Builder(mContext);
			builder.setView(layout);
			alertdialog = builder.create();
			alertdialog.show();
		}
	}

	/*
	 * @Override public boolean onChildClick(ExpandableListView parent, View v,
	 * int groupPosition, int childPosition, long id) { //
	 * //定义了当可折叠列表里的子元素(child)发生点击事件时调用的回调方法的接口 //
	 * parent:发生点击动作的ExpandableListView; // v:在expandable
	 * list/ListView中被点击的视图(View); //
	 * groupPosition:包含被点击子元素的组(group)在ExpandableListView中的位置(索引); //
	 * childPosition被点击子元素(child)在组(group)中的位置; // id被点击子元素(child)的行ID(索引)
	 * 
	 * Toast.makeText(this, " 组元素索引: " + groupPosition + " 子元素索引: " +
	 * childPosition, Toast.LENGTH_SHORT).show();
	 * 
	 * return super.onChildClick(parent, v, groupPosition, childPosition, id);//
	 * //当点击事件被处理时返回 }
	 */
	/*
	 * @Override public void onGroupExpand(int groupPosition) {
	 * Toast.makeText(this, " 组元素索引: " + groupPosition, Toast.LENGTH_SHORT)
	 * .show();
	 * 
	 * InputMethodManager imm = (InputMethodManager) this
	 * .getSystemService(Context.INPUT_METHOD_SERVICE); //
	 * signature.setCursorVisible(false); search.setCursorVisible(false); //
	 * imm.hideSoftInputFromWindow(signature.getWindowToken(), //
	 * InputMethodManager.HIDE_IMPLICIT_ONLY);
	 * imm.hideSoftInputFromWindow(search.getWindowToken(),
	 * InputMethodManager.RESULT_UNCHANGED_SHOWN);
	 * super.onGroupExpand(groupPosition);// 每当展开当前可伸缩列表中的某个组时，就调用该方法 //
	 * groupPosition 组位置，也就是展开的那个组的位置 }
	 */

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case MY_INFORMATION:
			Intent intent = new Intent();
			// 在intent中加入事件。
			intent.setAction(Constant.updateMyInformationAction);
			MainActivity.this.sendBroadcast(intent);
			break;

		default:
			break;
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// Called when the context menu for this view is being built.

		// menu:The context menu that is being built
		// v:The view for which the context menu is being built
		// menuInfo:Extra information about the item for which the context menu
		// should be shown. This information will vary depending on the class of
		// v.

		// 长按类表中某元素时出现
		menu.setHeaderTitle("聊     天");
		menu.add(0, 0, 0, "进入聊天界面");

		// Handler sleepHandler = new Handler();//表示4秒钟后执行menu.clear();
		// Message msg = new Message();
		// msg.what = 1;
		// sleepHandler .sendMessageDelayed(msg , 4000);

		// menu.clear();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(operatesBroadcastReceiver);
	}

	// 单击上下文菜单后的逻辑
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item
				.getMenuInfo();// 得到菜单信息传给info

		// String title = ((TextView) info.targetView).getText().toString();
		String title = "点击测试 ";

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
	public class MainActivityExpandableListAdapter extends
			BaseExpandableListAdapter implements OnLongClickListener {

		private Context context = null;

		public MainActivityExpandableListAdapter(Context context) {
			this.context = context;
		}

		// 获取与孩子在给定的组相关的数据,得到数组Child中元素的数据

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// 获取指定组中的指定子元素数据。

			// 参数：groupPosition——组位置（该组内部含有子元素）
			// childPosition——子元素位置（相对于其它子元素）

			return personList.get(groupPosition).get(
					personKeys.get(childPosition));// 返回指定子元素数据
		}

		// 获取在给定的组的儿童的ID，就是Child中元素的ID
		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// 参数：groupPosition——组位置（该组内部含有子元素）
			// childPosition——子元素位置（相对于其它子元素）

			return personKeys.get(childPosition);// 子元素关联ID
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// 获取指定组中的子元素个数
			// 参数：groupPosition——组位置（决定返回哪个组的子元素个数
			int childrenCount = 0;
			if (groupPosition < personList.size())
				childrenCount = personList.get(groupPosition).size();
			return childrenCount;// 返回值:指定组的子元素个数
		}

		// 获取一个视图显示在给定的组 的儿童的数据，就是存放Child—— 取子列表中的某一项的 View
		@Override
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
			/*
			 * TextView textView = getGenericView();
			 * textView.setText(getChild(groupPosition,
			 * childPosition).toString());
			 * textView.setTextColor(Color.BLACK);//改变子列表Text颜色
			 * textView.setOnClickListener(new ChatListener());
			 * 
			 * return textView;//指定位置上的子元素返回的视图对象
			 * 
			 * LinearLayout ll = new LinearLayout(MainActivity.this);
			 * ll.setOrientation(0);// 定义为纵向排列 ImageView logo = new
			 * ImageView(MainActivity.this);
			 * logo.setImageResource(images[groupPosition]);// 添加图片
			 * ll.addView(logo); TextView textView = getGenericView();//
			 * 调用定义的getTextView()方法 textView.setText(getChild(groupPosition,
			 * childPosition).toString());// 添加数据
			 * textView.setOnClickListener(new ChatListener());
			 * textView.setTextColor(Color.BLACK);// 改变子列表Text颜色
			 * ll.addView(textView); return ll;
			 */
			View view = null;
			if (groupPosition < personList.size()) {// 如果groupPosition的序号能从children列表中获得一个children对象
				Person person = personList.get(groupPosition).get(
						personKeys.get(childPosition));// 获得当前用户实例
				view = getLayoutInflater().inflate(R.layout.person_item_layout,
						null);// 生成List用户条目布局对象
				view.setOnLongClickListener(this);// 添加长按事件
				view.setOnClickListener(MainActivity.this);
				view.setTag(person);// 添加tag标记以便在长按事件和点击事件中根据该标记进行相关处理
				view.setPadding(30, 0, 0, 0);// 设置左边填充空白距离
				ImageView headIconView = (ImageView) view
						.findViewById(R.id.person_head_icon);// 头像
				TextView nickeNameView = (TextView) view
						.findViewById(R.id.person_nickename);// 昵称
				TextView personIPAddress = (TextView) view
						.findViewById(R.id.person_ip_address);// 登录IP
				TextView msgCountView = (TextView) view
						.findViewById(R.id.person_msg_count);// 未读信息计数
				// TextView ipaddressView =
				// (TextView)view.findViewById(R.id.person_ipaddress);//IP地址
				headIconView.setImageResource(person.getIcon());
				nickeNameView.setText(person.getUserName());
				personIPAddress.setText(person.getIpAddress().toString());

				// el 表达式：{%1$d}
				String msgCountStr = getString(R.string.init_msg_count);
				// 根据用户id从service层获得该用户的消息数?
				msgCountView
						.setText(String.format(msgCountStr, PacketInternet
								.getMessagesCountById(person.getUserId())));
				// ipaddressView.setText(person.ipAddress);
			}
			return view;
		}

		// 获取与给定的组相关的数据，得到数组Group中元素的数据
		@Override
		public Object getGroup(int groupPosition) {
			// 获取指定组中的数据
			// 参数:groupPosition——组位置
			return personList.get(groupPosition);
		}

		// 获取的群体数量，得到Group里元素的个数
		@Override
		public int getGroupCount() {
			return groups.length;// 返回值：组的个数
		}

		// 获取组在给定的位置编号，即Group中元素的ID
		@Override
		public long getGroupId(int groupPosition) {
			// 获取指定组的ID，这个组ID必须是唯一的
			// 参数:groupPosition--组位置

			return groupPosition;// 返回组相关ID
		}

		// //获取一个视图显示给定组，存放Group—— 取父列表中的某一项的 View
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// 获取显示指定组的视图对象。这个方法仅返回关于组的视图对象，要想获取子元素的视图对象，就需要调用getChildView(int,
			// int, boolean, View, ViewGroup)。

			// 参数:groupPosition--组位置（决定返回哪个视图）
			// isExpanded--该组是展开状态还是伸缩状态
			// convertView 重用已有的视图对象
			// parent——返回的视图对象始终依附于的视图组。

			/*
			 * TextView textView = getGenericView();//
			 * 调用定义的getTextView()方法,返回指定组的视图对象
			 * textView.setText(getGroup(groupPosition).toString());
			 * textView.setTextColor(Color.RED);// 改变父列表Text颜色 return textView;
			 */

			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT, 60);
			TextView textView = new TextView(context);
			textView.setLayoutParams(lp);
			textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
			textView.setPadding(50, 0, 0, 0);
			int childrenCount = 0;
			if (groupPosition < personList.size()) {// 如果groupPosition序号能从children列表中获得children对象，则获得该children对象中的用户数量
				childrenCount = personList.get(groupPosition).size();
			}
			textView.setText(groups[groupPosition] + "(" + childrenCount + ")");
			return textView;
		}

		// 表示孩子是否和组ID是跨基础数据的更改稳定
		@Override
		public boolean hasStableIds() {
			// 组和子元素是否持有稳定的ID,也就是底层数据的改变不会影响到它们

			return true;// 返回一个Boolean类型的值，如果为TRUE，意味着相同的ID永远引用相同的对象
		}

		// 孩子在指定的位置是可选的，即：Child中的元素是可点击的
		@Override
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

			TextView textView = new TextView(MainActivity.this);
			textView.setLayoutParams(lp);// 安排子类的排版
			textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);// 字体动态改变
			textView.setPadding(32, 0, 0, 0);// setPadding (int left, int top,
												// int right, int
												// bottom（即：设置text与其容器（比如图片）之间的间隔）
			textView.setTextSize(20);// 改变字体大小
			return textView;
		}

		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
			return false;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		System.out.println("other ontouch!");
		InputMethodManager imm = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		search.setCursorVisible(false);
		imm.hideSoftInputFromWindow(search.getWindowToken(),
				InputMethodManager.RESULT_UNCHANGED_SHOWN);
		v.setFocusable(true);
		v.setFocusableInTouchMode(true);
		v.requestFocus();
		return this.detector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	private int verticalMinDistance = 20;
	private int minVelocity = 0;

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (e1.getX() - e2.getX() > verticalMinDistance
				&& Math.abs(velocityX) > minVelocity) {
			System.out.println("otherproject");
			// GroupActivity
			Intent intent = new Intent();
			intent.setAction(SSS);
			startActivity(intent);
			Toast.makeText(this, "GroupActivity", Toast.LENGTH_SHORT).show();
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// 响应每个菜单项(通过菜单项的ID)
		case R.id.action_exit:
			Intent intent = new Intent(MainActivity.this, IPmsgService.class);
			stopService(intent);
			IPMsgApplication.getInstance().exit();
			break;
		case 2:
			// do something here
			break;
		case 3:
			// do something here
			break;
		case 4:
			// do something here
			break;
		default:
			// 对没有处理的事件，交给父类来处理
			return super.onOptionsItemSelected(item);
		}
		// 返回true表示处理完菜单项的事件，不需要将该事件继续传播下去了
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		InputMethodManager imm = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		// signature.setCursorVisible(false);
		search.setCursorVisible(false);
		// imm.hideSoftInputFromWindow(signature.getWindowToken(),
		// InputMethodManager.HIDE_IMPLICIT_ONLY);
		imm.hideSoftInputFromWindow(search.getWindowToken(),
				InputMethodManager.RESULT_UNCHANGED_SHOWN);
		return super.onTouchEvent(event);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		/*
		 * switch(view.getId()){ case R.id.myinfo_panel://弹出系统设置窗口
		 * showSettingDialog(); break; case R.id.person_item_layout://转到发信息页?
		 * person = (Person)view.getTag();//用户列表的childView被点击时
		 * openChartPage(person); break; case
		 * R.id.long_send_msg://长按列表的childView时在弹出的窗口中点击"发信息"按钮? person =
		 * (Person)view.getTag(); openChartPage(person);
		 * if(null!=dialog)dialog.dismiss(); break; case R.id.long_send_file:
		 * Intent intent = new Intent(this,MyFileManager.class);
		 * intent.putExtra("selectType", Constant.SELECT_FILES);
		 * startActivityForResult(intent, 0); dialog.dismiss(); break; case
		 * R.id.long_click_call: person = (Person)view.getTag();
		 * AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 * builder.setTitle(me.personNickeName); String title =
		 * String.format(getString(R.string.talk_with), person.personNickeName);
		 * builder.setMessage(title); builder.setIcon(me.personHeadIconId);
		 * builder.setNegativeButton(getString(R.string.close), new
		 * DialogInterface.OnClickListener() {
		 * 
		 * @Override public void onClick(DialogInterface cdialog, int which) {
		 * cdialog.dismiss(); } }); final AlertDialog callDialog =
		 * builder.show(); callDialog.setOnDismissListener(new
		 * DialogInterface.OnDismissListener() {
		 * 
		 * @Override public void onDismiss(DialogInterface arg0) {
		 * mService.stopTalk(person.personId); } });
		 * mService.startTalk(person.personId); break; case
		 * R.id.long_click_cancel: dialog.dismiss(); break;
		 */

		Intent intent = new Intent();

		switch (v.getId()) {
		case R.id.text_search:
			search.setFocusable(true);
			break;
		case R.id.button_searche:
			// do something about search
			break;
		case R.id.person_item_layout:
			// 打开发短信页面
			Person person = (Person) v.getTag();// 用户列表的childView被点击时
			openChatPage(person);
			break;
		case R.id.head_icon:

			intent.setClass(MainActivity.this, GallerytActivity.class);
			startActivityForResult(intent, MY_INFORMATION);
			break;
		case R.id.button_group:
			intent.setClass(MainActivity.this, GroupActivity.class);
			MainActivity.this.startActivity(intent);
			break;
		default:
			break;
		}

	}

	private class OperatesBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (action == null || action.equals(""))
				return;
			if (action.equals(Constant.personHasChangedAction)) {
				personList = PacketInternet.getFriendList();
				personMap = PacketInternet.getFriendMap();
				personKeys = PacketInternet.getFriendKeys();
				if (null == friendListAdapter) {
					friendListAdapter = new MainActivityExpandableListAdapter(
							MainActivity.this);
					friendExpandableListView.setAdapter(friendListAdapter);
					friendExpandableListView.expandGroup(0);
					friendExpandableListView.setGroupIndicator(getResources()
							.getDrawable(R.drawable.head_icon));
				}
/*				int i = 0;
				for (Integer personId : personKeys) {
					searchPersons[i] = personMap.get(personId).getUserName();
					i++;
				}*/
				friendListAdapter.notifyDataSetChanged();
			} else if (action.equals(Constant.updateMyInformationAction)) {
				me = PacketInternet.getMyInformation();
				nickname.setText(me.getUserName());
				headIcon.setImageResource(me.getIcon());
				Packet packet = new Packet();
				packet.setCommandNO(IPMsg.IPMSG_NEW_BR_ABSENCE);
				PacketInternet.broadcastMessage(packet);

			} else if (intent.getAction().equals(Constant.hasMsgUpdatedAction)) {
				friendListAdapter.notifyDataSetChanged();
			}
		}

	}

	private void registerBroadcastReceiver() {
		operatesBroadcastReceiver = new OperatesBroadcastReceiver();
		bFilter = new IntentFilter();
		bFilter.addAction(Constant.updateMyInformationAction);
		bFilter.addAction(Constant.personHasChangedAction);
		bFilter.addAction(Constant.hasMsgUpdatedAction);
		bFilter.addAction(Constant.receivedSendFileRequestAction);
		bFilter.addAction(Constant.remoteUserRefuseReceiveFileAction);
		bFilter.addAction(Constant.dataReceiveErrorAction);
		bFilter.addAction(Constant.dataSendErrorAction);
		bFilter.addAction(Constant.fileReceiveStateUpdateAction);
		bFilter.addAction(Constant.fileSendStateUpdateAction);
		bFilter.addAction(Constant.receivedTalkRequestAction);
		bFilter.addAction(Constant.remoteUserClosedTalkAction);
		registerReceiver(operatesBroadcastReceiver, bFilter);
	}

	private void openChatPage(Person person) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, ChatActivity.class);
		intent.putExtra("person", person);
		intent.putExtra("me", me);
		// intent.setAction(Constant.hasMsgUpdatedAction);
		intent.putExtra("userId", person.getUserId());
		// MainActivity.this.sendBroadcast(intent);
		startActivity(intent);
	}

}
