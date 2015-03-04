package com.channelsoft.android.aboutus.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.channelsoft.android.aboutus.constant.Constant;
import com.channelsoft.android.aboutus.utils.HttpUtils;
import com.sun.org.apache.bcel.internal.generic.IXOR;

public class ImageHttpGet
{

    Context context = null;
    String  url     = "http://192.168.1.101:8080/smarket/smarket/doGet.action";

    public ImageHttpGet(Context context)
    {
        this.context = context;
    }

    @SuppressWarnings("unchecked")
    public void getImage(String type, String version)
    {
        ArrayList<String> mImageStrings = new ArrayList<String>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("type", type);
        params.put("version", version);
        String result = HttpUtils.submitPostData(url, params, "utf-8");
        Log.d("Smarket", "获取图片之后");
        Log.d("Smarket", "Smarket"+result);
        if (result == null || "".equals(result))
        {
            Intent intent = new Intent();
            // 在intent中加入事件。
            intent.setAction(Constant.IMAGE_GET_FAILED);
            context.sendBroadcast(intent);
        }
        else if (type.equals("first"))
        {
            String adImages = null;
            JSONObject dataJson = null;
            try
            {
                dataJson = new JSONObject(result);
                adImages = dataJson.getString("data");

                String[] imageStrings = adImages.split(",");
                for (int i = 0; i < imageStrings.length; i++)
                {
                    mImageStrings.add(imageStrings[i]);
                    Log.d("五个图片URL", imageStrings[i]);
                }
                Intent intent = new Intent();
                // 在intent中加入事件。
                intent.setAction(Constant.IMAGE_GET_AD_CANDOWNLOAD);
                intent.putStringArrayListExtra("mImageStrings", mImageStrings);
                context.sendBroadcast(intent);
                Log.d("Smarket", "可以下载");
            }
            catch (JSONException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Log.v("result", "结果：" + result);
        }
        else if (type.equals("second"))
        {
            String adImages = null;
            JSONObject dataJson = null;
            try
            {
                dataJson = new JSONObject(result);
                adImages = dataJson.getString("data");

                String[] imageStrings = adImages.split(",");
                for (int i = 0; i<imageStrings.length; i++)
                {
                    mImageStrings.add(imageStrings[i]);
                    Log.d("五个图片URL", imageStrings[i]);
                }

                Intent intent = new Intent();
                // 在intent中加入事件。
                intent.setAction(Constant.IMAGE_GET_SMARKET_SUCCESS);
                intent.putStringArrayListExtra("mImageStrings", mImageStrings);
                context.sendBroadcast(intent);
            }
            catch (JSONException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Log.v("result", "结果：" + result);
        }

    }

}
