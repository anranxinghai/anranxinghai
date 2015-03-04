package com.channelsoft.android.aboutus.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.channelsoft.android.aboutus.R;
/**
 * 联系我们界面
 * @author Lihw
 *
 * @date 2014.7.16
 */
public class ConnectionActivity extends Activity implements OnClickListener{
    
    private ImageButton backButton;//返回按钮
    private TextView telNumberTv;//电话号码
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connection);
		backButton = (ImageButton)findViewById(R.id.contact_back);
		telNumberTv = (TextView)findViewById(R.id.tel_number);
		telNumberTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG ); 
		backButton.setOnClickListener(this);
		telNumberTv.setOnClickListener(this);
		
	}
	/**
	 * 点击事件
	 */
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        switch (v.getId())
        {
            case R.id.contact_back:
                finish();
                break;
            case R.id.tel_number:
                Intent intent=new Intent(Intent.ACTION_DIAL); 
                intent.setData(Uri.parse("tel:400 818 1218"));
                startActivity(intent);
                break;
            default:
                break;
        }
    }

	
}
