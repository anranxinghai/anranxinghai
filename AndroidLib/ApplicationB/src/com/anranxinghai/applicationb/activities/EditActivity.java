package com.anranxinghai.applicationb.activities;

import java.util.HashMap;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.anranxinghai.applicationb.R;
import com.anranxinghai.applicationb.activities.BActivity.UserInfoAdapter;
import com.anranxinghai.applicationb.constant.Constants;

public class EditActivity extends Activity implements OnClickListener{

	
	private EditText userNameText;
	private EditText userPasswordText;
	private Button saveButton;
	private HashMap<String, Object> userInfo = null;
	private int position = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);

		userInfo = (HashMap<String, Object>)getIntent().getSerializableExtra("userInfo");
		position = getIntent().getIntExtra("position", 0);
		initView();
		
	}
	
	
	private void initView() {
		// TODO Auto-generated method stub
		userNameText = (EditText)findViewById(R.id.text_user_name);
		userNameText.setText((String)userInfo.get("userName"));
		userPasswordText = (EditText)findViewById(R.id.text_user_password);
		userPasswordText.setText((String)userInfo.get("userPassword"));
		saveButton = (Button)findViewById(R.id.button_user_save);
		saveButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
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
		switch (v.getId()) {
		case R.id.button_user_save:
			String userName = userNameText.getText().toString();
			String userPassword = userPasswordText.getText().toString();
			Uri uri = new Uri.Builder().scheme(ContentResolver.SCHEME_CONTENT).authority(Constants.AUTOHORITY).appendPath("user").build();
			ContentValues contentValues = new ContentValues();
			contentValues.put("user_name", userName);
			contentValues.put("user_password", userPassword);
			getContentResolver().update(uri, contentValues, "_id = ?", new String[]{userInfo.get("userId").toString()});
			Intent intent = new Intent();
			HashMap<String, Object> userInfo = new HashMap<String, Object>();
			userInfo.put("userId", this.userInfo.get("userId"));
			userInfo.put("userName", userName);
			userInfo.put("userPassword", userPassword);
			intent.putExtra("userInfo", userInfo);
			intent.putExtra("position", position);
			setResult(Activity.RESULT_OK, intent); 
			finish();
			break;

		default:
			break;
		}
	}
}
