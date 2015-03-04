package com.channelsoft.android.aboutus.activitys;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.channelsoft.android.aboutus.R;
import com.channelsoft.android.aboutus.utils.HttpUtils;

/**
 * 熟识商机第二页Fragment
 * 
 * @author Harvey
 * 
 */
public class Page2Fragment extends Fragment
{
    private TextView feature4;
    private TextView feature5;
    private TextView feature6;
    String[]         features = new String[6];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_smarket_2, container, false);

        feature4 = (TextView) view.findViewById(R.id.feature4);
        feature5 = (TextView) view.findViewById(R.id.feature5);
        feature6 = (TextView) view.findViewById(R.id.feature6);
        feature4.setText("预订管理 比肩行业领导企业的客户接待和关怀系统，提高客户满意度，提升企业品牌。同时，客户的预订行为可以被记录并予以分析，为精准营销带来依据。");
        feature5.setText("经营管控 青牛商机可以对预订、营销活动的过程、结果做全面的统计分析，帮助店长和老板根据经营分析结果，快速调整改善营销服务过程。");
        feature6.setText("异地多店管理 异地帐号登录终端，查看业务情况，同时管理多家店铺，轻松快捷。");
        SpannableStringBuilder builder = new SpannableStringBuilder(feature4.getText().toString());
        ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.smarket_orange));
        builder.setSpan(redSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new RelativeSizeSpan(1.5f), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        feature4.setText(builder);

        SpannableStringBuilder builder2 = new SpannableStringBuilder(feature5.getText().toString());
        ForegroundColorSpan redSpan2 = new ForegroundColorSpan(getResources().getColor(R.color.smarket_blue));
        builder2.setSpan(redSpan2, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder2.setSpan(new RelativeSizeSpan(1.5f), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        feature5.setText(builder2);

        SpannableStringBuilder builder3 = new SpannableStringBuilder(feature6.getText().toString());
        ForegroundColorSpan redSpan3 = new ForegroundColorSpan(getResources().getColor(R.color.smarket_red));
        builder3.setSpan(new RelativeSizeSpan(1.5f), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder3.setSpan(redSpan3, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        feature6.setText(builder3);
        if (checkNetworkAvailable(getActivity()))
        {
            new GetFeatures().execute();
        }
        return view;

    }

    private class GetFeatures extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... params)
        {
            // Simulates a background job.
            try
            {
                Thread.sleep(200);
                String url = "http://192.168.1.102:8080/smarket/sshDemo/doGet.action";
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("version", "2");
                params2.put("type", "second");
                String result = HttpUtils.submitPostData(url, params2, "utf-8");
                Log.v("result", result);
                if (result != null && !result.equals(""))
                {
                    Log.v("result", "结果：" + result);
                }
                JSONObject dataJson = null;
                try
                {
                    dataJson = new JSONObject(result);
                    result = dataJson.getString("data");
                    Log.v("result", "结果：" + result);
                    features = result.split("&&&");
                }
                catch (JSONException e1)
                {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            catch (InterruptedException e)
            {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            SpannableStringBuilder builder = new SpannableStringBuilder(features[3]);
            ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.smarket_orange));
            builder.setSpan(redSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new RelativeSizeSpan(1.5f), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            feature4.setText(builder);

            SpannableStringBuilder builder2 = new SpannableStringBuilder(features[4]);
            ForegroundColorSpan redSpan2 = new ForegroundColorSpan(getResources().getColor(R.color.smarket_blue));
            builder2.setSpan(redSpan2, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder2.setSpan(new RelativeSizeSpan(1.5f), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            feature5.setText(builder2);

            SpannableStringBuilder builder3 = new SpannableStringBuilder(features[5]);
            ForegroundColorSpan redSpan3 = new ForegroundColorSpan(getResources().getColor(R.color.smarket_red));
            builder3.setSpan(new RelativeSizeSpan(1.5f), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder3.setSpan(redSpan3, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            feature6.setText(builder3);
            super.onPostExecute(result);
        }
    }

    public static boolean checkNetworkAvailable(Context context)
    {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null)
        {
            return false;
        }
        else
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
            {
                for (int i = 0; i < info.length; i++)
                {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        NetworkInfo netWorkInfo = info[i];
                        if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                        {
                            return true;
                        }
                        else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
