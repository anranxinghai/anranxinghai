package com.channelsoft.android.aboutus.http;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.channelsoft.android.aboutus.utils.HttpUtils;

public class FeedbackCommit
{
    String url = "http://192.168.1.101:8080/smarket/feedBacks-doSave.action";

    public void conmmitFeedback(Handler myHandler, String content, String contactTel)

    {
        Message message = new Message();
        Map<String, String> params = new HashMap<String, String>();
        params.put("content", content);
        params.put("contactTel", contactTel);
        String result = HttpUtils.submitPostData(url, params, "utf-8");
        Log.v("result", result);
        if (result != null && !result.equals(""))
        {
            Log.v("result", "结果：" + result);
        }
        else
        {
            message.what = 0;
        }
        JSONObject dataJson = null;
        try
        {
            dataJson = new JSONObject(result);
            result = dataJson.getString("data");
        }
        catch (JSONException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        if (result.equals("00"))
        {
            message.what = 1;
        }
        else
        {
            message.what = 0;
        }
        myHandler.sendMessage(message);

    }

}
