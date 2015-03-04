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
 * 熟识商机第一页Fragment
 * 
 * @author Harvey
 * 
 */
public class Page1Fragment extends Fragment
{
    private TextView feature1;
    private TextView feature2;
    private TextView feature3;
    String[]         features = new String[6];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_smarket_1, container, false);

        feature1 = (TextView) view.findViewById(R.id.feature1);
        feature2 = (TextView) view.findViewById(R.id.feature2);
        feature3 = (TextView) view.findViewById(R.id.feature3);

        feature1.setText("会员管理 利用手机号码即可识别会员，通过来电商户即可掌握客户信息。通过青牛商机会员体系，企业可以为价值客户提供更高品质的营销和服务，提高客户的忠诚度和重复客流收入。");
        feature2.setText("精准营销 依据客户消费特点，结合数据分析，进行精确市场定位的智能营销，个性化定制各类客户的营销策略，锁定潜在客户人群，真正的低成本！高收益！");
        feature3.setText("云端数据 青牛商机采用云+端的管理模式，数据永不丢失，也不会因为营销人员的离职而带来客户信息的流失。");
        SpannableStringBuilder builder = new SpannableStringBuilder(feature1.getText().toString());
        ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.smarket_lightblue));
        builder.setSpan(redSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new RelativeSizeSpan(1.5f), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        feature1.setText(builder);

        SpannableStringBuilder builder2 = new SpannableStringBuilder(feature2.getText().toString());
        ForegroundColorSpan redSpan2 = new ForegroundColorSpan(getResources().getColor(R.color.smarket_green));
        builder2.setSpan(redSpan2, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder2.setSpan(new RelativeSizeSpan(1.5f), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        feature2.setText(builder2);

        SpannableStringBuilder builder3 = new SpannableStringBuilder(feature3.getText().toString());
        ForegroundColorSpan redSpan3 = new ForegroundColorSpan(getResources().getColor(R.color.smarket_black));
        builder3.setSpan(new RelativeSizeSpan(1.5f), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder3.setSpan(redSpan3, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        feature3.setText(builder3);
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
            SpannableStringBuilder builder = new SpannableStringBuilder(features[0]);
            ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.smarket_lightblue));
            builder.setSpan(redSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new RelativeSizeSpan(1.5f), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            feature1.setText(builder);

            SpannableStringBuilder builder2 = new SpannableStringBuilder(features[1]);
            ForegroundColorSpan redSpan2 = new ForegroundColorSpan(getResources().getColor(R.color.smarket_green));
            builder2.setSpan(redSpan2, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder2.setSpan(new RelativeSizeSpan(1.5f), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            feature2.setText(builder2);

            SpannableStringBuilder builder3 = new SpannableStringBuilder(features[2]);
            ForegroundColorSpan redSpan3 = new ForegroundColorSpan(getResources().getColor(R.color.smarket_black));
            builder3.setSpan(new RelativeSizeSpan(1.5f), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder3.setSpan(redSpan3, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            feature3.setText(builder3);
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
