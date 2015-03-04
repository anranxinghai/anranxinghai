package com.channelsoft.android.aboutus.activitys;

import java.util.ArrayList;
import java.util.List;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.channelsoft.android.aboutus.R;

/**
 * 熟识商机界面，主要向用户展示商机功能特性
 * 
 * @author Lihw
 * 
 * @date 2014.07.17
 */
public class SmarketActivity extends FragmentActivity implements OnClickListener
{
    private ViewPager       pager;
    private List<Fragment>  list;
    private FragmentManager manager;
    private PagerTitleStrip pagerTitleStrip;
    private MyAdapter       adapter;
    private ImageButton     backButton;
    private ImageButton     pageUpButton;
    private ImageButton     pageDownButton;

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
        setContentView(R.layout.activity_smarket);
        backButton = (ImageButton) findViewById(R.id.smarket_back);
        pageDownButton = (ImageButton) findViewById(R.id.page_down);
        pageUpButton = (ImageButton) findViewById(R.id.page_up);

        pager = (ViewPager) this.findViewById(R.id.pager);
        pagerTitleStrip = (PagerTitleStrip) this.findViewById(R.id.pagertitle);
        pagerTitleStrip.setTextSpacing(0);// 设置标题间的距离
        pagerTitleStrip.setVisibility(View.GONE);
        list = new ArrayList<Fragment>();
        list.add(new Page1Fragment());// add Fragment
        list.add(new Page2Fragment());
        manager = getSupportFragmentManager();// 因为使用的是V4的补丁包，必须的继承FragmentActivity
        // 才能实例化manager对象
        adapter = new MyAdapter(manager);
        pager.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        backButton.setOnClickListener(this);
        pageDownButton.setOnClickListener(this);
        pageUpButton.setOnClickListener(this);
        pager.setOnPageChangeListener(new OnPageChangeListener()
        {

            @Override
            public void onPageSelected(int position)
            {
                // TODO Auto-generated method stub
                if (position == 0)
                {
                    pageUpButton.setVisibility(View.INVISIBLE);
                    pageDownButton.setVisibility(View.VISIBLE);
                }
                else if (position == 1)
                {
                    pageUpButton.setVisibility(View.VISIBLE);
                    pageDownButton.setVisibility(View.INVISIBLE);
                }
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
    }

    // 使用FragmentStatePagerAdapter能缓存更多的上一个页面
    // 继承getCount,getItem方法
    public class MyAdapter extends FragmentPagerAdapter
    {

        public MyAdapter(FragmentManager fm)
        {
            super(fm);
            // TODO Auto-generated constructor stub
        }

        @Override
        public int getCount()
        {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Fragment getItem(int arg0)
        {
            // TODO Auto-generated method stub
            return list.get(arg0);
        }

    }

    /**
     * 处理点击事件
     */
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        switch (v.getId())
        {
            case R.id.page_down:
                pager.setCurrentItem(pager.getCurrentItem() + 1);
                break;
            case R.id.page_up:
                pager.setCurrentItem(pager.getCurrentItem() - 1);
                break;
            case R.id.smarket_back:
                finish();
                break;
            default:
                break;
        }
    }

}
