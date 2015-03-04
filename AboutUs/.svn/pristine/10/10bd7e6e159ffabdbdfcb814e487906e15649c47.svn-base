package com.channelsoft.android.aboutus.http;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;
import android.widget.Toast;

import com.channelsoft.android.aboutus.utils.HttpUtils;

public class AdvertisementGain {

	String url = "http://192.168.2.101:8080/smarket/get";

    public void gainAdvertisement(String type, String version)
    {


        Map<String, String> params = new HashMap<String, String>();
        params.put("type", type);
        params.put("version", version);
        String result = HttpUtils.submitPostData(url, params, "utf-8");
        Log.v("result", result);
        if (result != null && !result.equals(""))
        {
            Log.v("result", "结果：" + result);
        }
    }
}
