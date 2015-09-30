package com.anranxinghai.androidlib.utils;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import android.app.Activity;
import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

/**
 * 常用工具类
 * @author anranxinghai
 *
 */
public class CommonUtil
{

	
	private final String TAG = "CommonUtil";
    /**
     * 根据传入的Map<String,String> 生成对应的XML
     * 
     * @param element
     *            头元素
     * @param xmlMap
     *            内容
     * 
     * @example createXml("GetSoftVersionInfo", new HashMap<String, String>() {
     *          {
     *          	put("terminalId", "000001");
     *          	put("localTime",String.valueOf(System.currentTimeMillis())); 
     *          }
     *          });
     */
    public static String createXml(String element, Map<String, String> xmlMap)
    {
        String result = null;
        Document localDocument = DocumentHelper.createDocument();
        Element localElement = localDocument.addElement(element);
        for (Map.Entry<String, String> entry : xmlMap.entrySet())
        {
            localElement.addElement(entry.getKey()).setText(entry.getValue());
        }
        try
        {
            OutputFormat localOutputFormat = new OutputFormat();
            localOutputFormat.setEncoding("utf-8");
            XMLWriter localXMLWriter = new XMLWriter(localOutputFormat);
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localXMLWriter.setOutputStream(localByteArrayOutputStream);
            localXMLWriter.write(localDocument);
            result = localByteArrayOutputStream.toString();
        } catch (Exception e)
        {
            e.printStackTrace();
            result = null;
        }

        Log.i("xml", result);

        return result;
    }

    /**
     * 将ASCII转成字符串
     * 
     * @param arrayList
     * @return
     */
    public static String asciiToString(byte[] arrayList)
    {
        StringBuffer sbu = new StringBuffer();
        for (int i = 0; i < arrayList.length; i++)
        {
            sbu.append((char) arrayList[i]);
        }
        return sbu.toString();
    }

    

    /**
     * 判断字串是否是手机号码
     * @param phone
     * @return
     */
    public static boolean isMobileNo(String phone)
    {
        Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    
  

}
