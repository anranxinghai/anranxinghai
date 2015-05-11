package com.swust.ipmsg.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.swust.ipmsg.R;
import com.swust.ipmsg.adapter.ReceiveSendFileListAdapter;
import com.swust.ipmsg.service.PacketInternet;
import com.swust.ipmsg.util.Constant;
import com.swust.ipmsg.util.FileState;
import com.swust.ipmsg.util.IPMsg;
import com.swust.ipmsg.util.Message;
import com.swust.ipmsg.util.Packet;
import com.swust.ipmsg.util.Person;

public class ChatActivity extends Activity implements OnClickListener{
	private Person person = null;
	private Person me = null;
	private EditText msgContentText = null;
	private Button sendMsg = null;
	private Button sendFile = null;
	private LinearLayout chartMsgPanel = null;
//	private MainService mService = null;
	private Intent mMainServiceIntent = null;
//	private MyBroadcastRecv broadcastRecv = null;
	private IntentFilter bFilter = null;
	private ScrollView chartMsgScroll = null;
	private boolean isPaused = false;//判断本身是否可见
	private boolean isRemoteUserClosed = false; //是否远程用户已经关闭了通话。 
	private ArrayList<FileState> receivedFileNames = null;//接收到的对方传过来的文件名
	private ArrayList<FileState> beSendFileNames = null;//发送到对方的文件名信息
	private ReceiveSendFileListAdapter receiveFileListAdapter = new ReceiveSendFileListAdapter(this);
	private ReceiveSendFileListAdapter sendFileListAdapter = new ReceiveSendFileListAdapter(this);
	private ChatMessageBroadcastReceiver broadcastRecv = null;
	private Map<Integer, List<Message>> chatMessages = null;// 所有用户信息容器
	//当发送或收到消息后把屏幕滚到最后一行
	private final Handler mHandler = new Handler();
    private Runnable scrollRunnable= new Runnable() {
	    @Override
	    public void run() {
            int offset = chartMsgPanel.getMeasuredHeight() - chartMsgScroll.getHeight();//判断高度 
            if (offset > 0) {
	            chartMsgScroll.scrollBy(0, 100);//每次滚100个单位
	        }
	    }
    };
    
	
/*	*//**
	 * MainService服务与当前Activity的绑定连接器
	 *//*
	private ServiceConnection sConnection = new ServiceConnection(){
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mService = ((MainService.ServiceBinder)service).getService();
			showMsg(person.getUserId());//如果服务连接成功则获取该用户的消息并显示
			System.out.println("Service connected to activity...");
		}
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mService = null;
			System.out.println("Service disconnected to activity...");
		}
	};*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题   
				this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
				
				/*// 进行全屏   
				this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
						WindowManager.LayoutParams.FLAG_FULLSCREEN); */
		setContentView(R.layout.activity_chat);
		Intent intent = getIntent();
		chatMessages = PacketInternet.getChatMessages();
		person = (Person)intent.getExtras().getSerializable("person");
		me = (Person)intent.getExtras().getSerializable("me");
		initView();
		
		//当前Activity与后台MainService进行绑定
//        mMainServiceIntent = new Intent(this,MainService.class);
//        bindService(mMainServiceIntent, sConnection, BIND_AUTO_CREATE);
        registerBroadcastReceiver();
        
	}

	private void initView(){
		((ImageView)findViewById(R.id.my_head_icon)).setImageResource(person.getIcon());
		((TextView)findViewById(R.id.my_nickename)).setText(person.getUserName());
		msgContentText = (EditText)findViewById(R.id.text_msg_content);
		msgContentText.addTextChangedListener(new MsgTextWatcher());
		sendMsg = (Button)findViewById(R.id.button_send_msg);
		sendMsg.setOnClickListener(this);
		sendFile = (Button)findViewById(R.id.button_send_file);
		sendFile.setOnClickListener(this);
		chartMsgPanel = (LinearLayout)findViewById(R.id.chart_msg_panel);
		chartMsgScroll = (ScrollView)findViewById(R.id.chart_msg_scroll);
	}
	
	class MsgTextWatcher implements TextWatcher{

		public View view;
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			SpannableString ss = new SpannableString(s);
			String msg = s.toString();
			for (String emotionKey : Constant.emotions.keySet()) {
				if (msg.contains(emotionKey)) {
					Drawable d = getResources().getDrawable(Constant.emotions.get(emotionKey));
					d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
					ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
			         //用ImageSpan替换文本
			         ss.setSpan(span, 18, 19, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
				}
			}
			if ("android.widget.TextView".equals(view.getClass().getName())) {
				((TextView)view).setText(ss);
			} else if("android.widget.EditText".equals(view.getClass().getName()) ){
				((EditText)view).setText(ss);
			}
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
		
	}
	

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}

	@Override
	public void onClick(View vi) {
		switch(vi.getId()){
		case R.id.button_send_msg:
			String msg = msgContentText.getText().toString();
			/*try {
				msg = new String(msg.getBytes("unicode"), "gb2312");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			if(null==msg || msg.length()<=0){
				Toast.makeText(this, getString(R.string.content_is_empty), Toast.LENGTH_SHORT).show();
				return;
			}
			msgContentText.setText("");
			View view = getLayoutInflater().inflate(R.layout.send_msg_layout, null);
			ImageView sendHeadIcon = (ImageView)view.findViewById(R.id.send_head_icon);
			TextView smcView = (TextView)view.findViewById(R.id.send_msg_content);
			TextView smtView = (TextView)view.findViewById(R.id.send_msg_time);
			TextView nView = (TextView)view.findViewById(R.id.send_nickename);
			sendHeadIcon.setImageResource(me.getIcon());
			smcView.setText(msg);
			String currentTimeString = DateFormat.format("yyyy-MM-dd hh:mm:ss", new Date()).toString();
			smtView.setText(currentTimeString);
			List<Message> messages = null;
			Message message = new Message(currentTimeString, msg);
			message.setMine(true);
			message.setRead(true);
			int userId = person.getUserId();
			if (chatMessages.containsKey(userId)) {
				messages = chatMessages.get(userId);
			} else {
				messages = new ArrayList<Message>();
			}

			messages.add(message);
			chatMessages.put(userId, messages);
			
			nView.setText(me.getUserName());
			chartMsgPanel.addView(view);
			
			mHandler.post(scrollRunnable);
			Packet packet = new Packet(ChatActivity.this);
			packet.setCommandNO(IPMsg.IPMSG_NEW_SENDMSG);
			packet.setAdditionalSection(msg);
			PacketInternet.sendMessage(packet, person.getIpAddress());
			break;
		case R.id.button_send_file:
			Intent intent = new Intent(this,MyFileManager.class);
			intent.putExtra("selectType", Constant.SELECT_FILES);
			startActivityForResult(intent, Constant.FILE_RESULT_CODE);
			break;
		}
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.chart_menu, menu);
    	return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getOrder()){
    	case 1:
    		Intent intent = new Intent(this,MyFileManager.class);
			intent.putExtra("selectType", Constant.SELECT_FILES);
			startActivityForResult(intent, Constant.FILE_RESULT_CODE);
    		break;
    	case 2:
    		AlertDialog.Builder  builder = new AlertDialog.Builder(this);
			builder.setTitle(me.getUserName());
			String title = String.format(getString(R.string.talk_with), person.getUserName());
			builder.setMessage(title);
			builder.setIcon(me.getUserId());
			builder.setNegativeButton(getString(R.string.close), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface tdialog, int which) {
					tdialog.dismiss();
				}
			});
			final AlertDialog talkDialog = builder.show();
			talkDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
				@Override
				public void onDismiss(DialogInterface arg0) {
//					mService.stopTalk(person.getUserId());
				}
			});
//			mService.startTalk(person.getUserId());
    		break;
    	}
    	return super.onOptionsItemSelected(item);
    }
    

	boolean finishedSendFile = false;//记录当前这些文件是不是本次已经接收过了
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		/*if(resultCode == RESULT_OK){
			if(null!=data){
				int selectType = data.getExtras().getInt("selectType");
				if(selectType == Constant.SELECT_FILE_PATH){//如果收到的是文件夹选择模式，说明现在是要保存对方传过来的文件，则把当前选择的文件夹路径返回服务层
					String fileSavePath = data.getExtras().getString("fileSavePath");
					if(null!=fileSavePath){
//						mService.receiveFiles(fileSavePath);
//						finishedSendFile = true;//把本次接收状态置为true
					}else{
						Toast.makeText(this, getString(R.string.folder_can_not_write), Toast.LENGTH_SHORT).show();
					}
				}else if(selectType == Constant.SELECT_FILES){//如果收到的是文件选择模式，说明现在是要发送文件，则把当前选择的所有文件返回给服务层。
					@SuppressWarnings("unchecked")
					final ArrayList<FileName> files = (ArrayList<FileName>)data.getExtras().get("files");
//					mService.sendFiles(person.getUserId(), files);//把当前选择的所有文件返回给服务层
					
					//显示文件发送列表
//					beSendFileNames = mService.getBeSendFileNames();//从服务层获得所有需要接收的文件的文件名
					if(beSendFileNames.size()<=0)return;
					sendFileListAdapter.setResources(beSendFileNames);
					AlertDialog.Builder  builder = new AlertDialog.Builder(this);
					builder.setTitle(me.getUserName());
					builder.setMessage(R.string.start_to_send_file);
					builder.setIcon(me.getUserId());
					View vi = getLayoutInflater().inflate(R.layout.request_file_popupwindow_layout, null);
					builder.setView(vi);
					final AlertDialog fileListDialog = builder.show();
					fileListDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
						@Override
						public void onDismiss(DialogInterface arg0) {
							beSendFileNames.clear();
				        	files.clear();
						}
					});
					ListView lv = (ListView)vi.findViewById(R.id.receive_file_list);//需要接收的文件清单
					lv.setAdapter(sendFileListAdapter);
					Button btn_ok = (Button)vi.findViewById(R.id.receive_file_okbtn);
					btn_ok.setVisibility(View.GONE);
			        Button btn_cancle = (Button)vi.findViewById(R.id.receive_file_cancel);
			      //如果该按钮被点击则打开文件选择器，并设置成文件夹选择模式，选择一个用来接收对方文件的文件夹
			        btn_ok.setOnClickListener(new View.OnClickListener() {   
			            @Override  
			            public void onClick(View v) { 
			            	if(!finishedSendFile){//如果本次文件已经接收过了则不再打开文件夹选择器
				            	Intent intent = new Intent(ChatActivity.this,MyFileManager.class);
				    			intent.putExtra("selectType", Constant.SELECT_FILE_PATH);
				    			startActivityForResult(intent, 0);
			            	}
			            }   
				     });   
				    //如果该按钮被点击则向服务层发送用户拒绝接收文件的广播       
			        btn_cancle.setOnClickListener(new View.OnClickListener() {   
				        @Override  
				        public void onClick(View v) { 
				        	fileListDialog.dismiss();
				        }   
			        });
				}
			}
		}*/
	}
	

	
	@Override
	protected void onResume() {
		super.onResume();
		Intent intent = new Intent();
		intent.setAction(Constant.startChatAction);
		intent.putExtra("userId", person.getUserId());
		ChatActivity.this.sendBroadcast(intent);
		isPaused = false;
	}
	@Override
	protected void onPause() {
		super.onPause();
		isPaused = true;
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		/*unbindService(sConnection);
		unregisterReceiver(broadcastRecv);*/
	}
	
    //广播接收器
    private class ChatMessageBroadcastReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(Constant.hasMsgUpdatedAction)){
				showMsg(person.getUserId(),true);
			}
			else if(intent.getAction().equals(Constant.startChatAction)){
				showMsg(person.getUserId(),false);
			}else if(intent.getAction().equals(Constant.dataReceiveErrorAction) 
					|| intent.getAction().equals(Constant.dataSendErrorAction)){
				Toast.makeText(ChatActivity.this, intent.getExtras().getString("msg"), Toast.LENGTH_SHORT).show();
			}else if(intent.getAction().equals(Constant.fileSendStateUpdateAction)){//收到来自服务层的文件接收状态通知
				/*beSendFileNames = mService.getBeSendFileNames();//获得当前所有文件接收状态
				sendFileListAdapter.setResources(beSendFileNames);
				sendFileListAdapter.notifyDataSetChanged();//更新文件接收列表
*/			}else if(intent.getAction().equals(Constant.fileReceiveStateUpdateAction)){//收到来自服务层的文件接收状态通知
				/*receivedFileNames = mService.getReceivedFileNames();//获得当前所有文件接收状态
				receiveFileListAdapter.setResources(receivedFileNames);
				receiveFileListAdapter.notifyDataSetChanged();//更新文件接收列表
*/			}else if(intent.getAction().equals(Constant.receivedTalkRequestAction)){
				/*if(!isPaused){
					isRemoteUserClosed = false;
					final Person psn = (Person)intent.getExtras().get("person");
					String title = String.format(getString(R.string.talk_with), psn.getUserName());
					AlertDialog.Builder  builder = new AlertDialog.Builder(ChatActivity.this);
					builder.setTitle(psn.getUserName());
					builder.setMessage(title);
					builder.setIcon(psn.getIcon());
					View vi = getLayoutInflater().inflate(R.layout.request_talk_layout, null);
					builder.setView(vi);
					final AlertDialog revTalkDialog = builder.show();
					revTalkDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
						@Override
						public void onDismiss(DialogInterface arg0) {
//							mService.stopTalk(psn.getUserId());
						}
					});
					Button talkOkBtn = (Button)vi.findViewById(R.id.receive_talk_okbtn);
					talkOkBtn.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View okBtn) {
							if(!isRemoteUserClosed){//如果远程用户未关闭通话，则向对方发送同意接收通话指令
								//mService.acceptTalk(psn.getUserId());
								okBtn.setEnabled(false);
							}
						}
					});
					Button talkCancelBtn = (Button)vi.findViewById(R.id.receive_talk_cancel);
					talkCancelBtn.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View cancelBtn) {
							revTalkDialog.dismiss();
						}
					});
				}*/
			}else if(intent.getAction().equals(Constant.remoteUserRefuseReceiveFileAction)){
				//Toast.makeText(ChatActivity.this, getString(R.string.refuse_receive_file), Toast.LENGTH_SHORT).show();
			}else if(intent.getAction().equals(Constant.receivedSendFileRequestAction)){
				/*if(!isPaused){//如果自身处于可见状态则响应广播,弹出一个提示框是否要接收发过来的文件
					receivedFileNames = mService.getReceivedFileNames();//从服务层获得所有需要接收的文件的文件名
					receiveFileListAdapter.setResources(receivedFileNames);
					AlertDialog.Builder  builder = new AlertDialog.Builder(context);
					builder.setTitle(person.personNickeName);
					builder.setMessage(R.string.sending_file_to_you);
					builder.setIcon(person.personHeadIconId);
					View vi = getLayoutInflater().inflate(R.layout.request_file_popupwindow_layout, null);
					builder.setView(vi);
					final AlertDialog revFileDialog = builder.show();
					revFileDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
						@Override
						public void onDismiss(DialogInterface arg0) {
							receivedFileNames.clear();
				        	if(!finishedSendFile){//如果本次文件并未接收就关闭接收窗口，说明放弃本次接收，同时向远程发送一个拒绝接收的指令。
					        	Intent intent = new Intent();
								intent.setAction(Constant.refuseReceiveFileAction);
								sendBroadcast(intent);
				        	}
				        	finishedSendFile = false;//关闭文件接收对话框，本表示本次文件接收完成，把本次文件接收状态置为false
						}
					});
					ListView lv = (ListView)vi.findViewById(R.id.receive_file_list);//需要接收的文件清单
					lv.setAdapter(receiveFileListAdapter);
					Button btn_ok = (Button)vi.findViewById(R.id.receive_file_okbtn);
			        Button btn_cancle = (Button)vi.findViewById(R.id.receive_file_cancel);
			        
			        //如果该按钮被点击则打开文件选择器，并设置成文件夹选择模式，选择一个用来接收对方文件的文件夹
			        btn_ok.setOnClickListener(new View.OnClickListener() {   
			            @Override  
			            public void onClick(View v) { 
			            	if(!finishedSendFile){//如果本次文件已经接收过了则不再打开文件夹选择器
				            	Intent intent = new Intent(ChatActivity.this,MyFileManager.class);
				    			intent.putExtra("selectType", Constant.SELECT_FILE_PATH);
				    			startActivityForResult(intent, 0);
			            	}
			            }   
				     });   
			        
				    //如果该按钮被点击则向服务层发送用户拒绝接收文件的广播       
			        btn_cancle.setOnClickListener(new View.OnClickListener() {   
				        @Override  
				        public void onClick(View v) { 
				        	revFileDialog.dismiss();
				        }   
			        });
				}*/
			}
		}
		
		//根据用户id从service层获得该用户消息，并显示该消息
		private void showMsg(int userId,boolean isReading){
			List<Message> msgs = PacketInternet.getMessagesById(userId);
			if(null!=msgs){
				if (isReading) {
					View view = getLayoutInflater().inflate(R.layout.received_msg_layout, null);
					ImageView iView = (ImageView)view.findViewById(R.id.received_head_icon);
					TextView smcView = (TextView)view.findViewById(R.id.received_msg_content);
					TextView smtView = (TextView)view.findViewById(R.id.received_msg_time);
					TextView nView = (TextView)view.findViewById(R.id.received_nickename);
					iView.setImageResource(person.getIcon());
					Message msg = msgs.get(msgs.size()-1);
					msg.setRead(true);
					smcView.setText(msg.msg);
					smtView.setText(msg.receivedTime);
					nView.setText(person.getUserName());
					chartMsgPanel.addView(view);
					mHandler.post(scrollRunnable);
				}else {
					int i = 0;
					while(i<msgs.size()){
						Message msg = msgs.get(i);
						if (msg.isMine()) {
							View view = getLayoutInflater().inflate(R.layout.send_msg_layout, null);
							ImageView sendHeadIcon = (ImageView)view.findViewById(R.id.send_head_icon);
							TextView smcView = (TextView)view.findViewById(R.id.send_msg_content);
							TextView smtView = (TextView)view.findViewById(R.id.send_msg_time);
							TextView nView = (TextView)view.findViewById(R.id.send_nickename);
							sendHeadIcon.setImageResource(me.getIcon());
							smcView.setText((msg.msg));
							String currentTimeString = msg.receivedTime;
							smtView.setText(currentTimeString);
							nView.setText(me.getUserName());
							chartMsgPanel.addView(view);
							mHandler.post(scrollRunnable);
						}else {
							View view = getLayoutInflater().inflate(R.layout.received_msg_layout, null);
							ImageView iView = (ImageView)view.findViewById(R.id.received_head_icon);
							TextView smcView = (TextView)view.findViewById(R.id.received_msg_content);
							TextView smtView = (TextView)view.findViewById(R.id.received_msg_time);
							TextView nView = (TextView)view.findViewById(R.id.received_nickename);
							iView.setImageResource(person.getIcon());
							
							msg.setRead(true);
							smcView.setText(msg.msg);
							smtView.setText(msg.receivedTime);
							nView.setText(person.getUserName());
							chartMsgPanel.addView(view);
						}
						
						mHandler.post(scrollRunnable);
						i++;
					}
				}
				
			}
		}
    }
    
	//广播接收器注册
	private void registerBroadcastReceiver(){
        broadcastRecv = new ChatMessageBroadcastReceiver();
        bFilter = new IntentFilter();
        bFilter.addAction(Constant.hasMsgUpdatedAction);
        bFilter.addAction(Constant.startChatAction);
        bFilter.addAction(Constant.receivedSendFileRequestAction);
        bFilter.addAction(Constant.fileReceiveStateUpdateAction);
        bFilter.addAction(Constant.fileSendStateUpdateAction);
        bFilter.addAction(Constant.receivedTalkRequestAction);
        registerReceiver(broadcastRecv, bFilter);
	}	
}
