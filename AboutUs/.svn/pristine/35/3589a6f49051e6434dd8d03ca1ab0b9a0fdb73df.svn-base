package com.channelsoft.android.aboutus.activitys;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.channelsoft.android.aboutus.R;
import com.channelsoft.android.aboutus.db.QNSQLiteHelper;

/**
 * 此类时主界面类，进入“关于我们”模块后的第一个界面
 * 
 * @author anranxinghai
 * 
 */
public class AboutActivity extends Activity implements OnClickListener
{

    private ImageButton    firstSightButton = null;
    private ImageButton    familarButton    = null;
    private ImageButton    adviceButton     = null;
    private ImageButton    connectionButton = null;
    private QNSQLiteHelper myHelper;
    private SQLiteDatabase mysql ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        // 通过程序改变屏幕显示的方向，横屏
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        firstSightButton = (ImageButton) findViewById(R.id.button_first_sight);
        firstSightButton.setOnClickListener(this);

        familarButton = (ImageButton) findViewById(R.id.button_familar_smarket);
        familarButton.setOnClickListener(this);

        adviceButton = (ImageButton) findViewById(R.id.button_advice);
        adviceButton.setOnClickListener(this);

        connectionButton = (ImageButton) findViewById(R.id.button_connetion_us);
        connectionButton.setOnClickListener(this);

        myHelper = new QNSQLiteHelper(this);

        mysql = myHelper.getWritableDatabase();
    }

    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        switch (v.getId())
        {
            case R.id.button_first_sight:
                intent.setClass(this, AdDisplayActivity.class);
                startActivity(intent);
                break;

            case R.id.button_familar_smarket:
                intent.setClass(this, SmarketActivity.class);
                startActivity(intent);
                break;

            case R.id.button_advice:
                intent.setClass(this, FeedBackActivity.class);
                startActivity(intent);
                break;

            case R.id.button_connetion_us:
                intent.setClass(this, ConnectionActivity.class);
                startActivity(intent);
                break;

            default:
                break;
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
     * @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     * View rootView = inflater.inflate(R.layout.fragment_about, container, false); return rootView; } }
     */

}
