package com.channelsoft.android.aboutus.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import android.util.Log;

public class HttpUtils {
	/*
     * Function  :   发送Post请求到服务器
     * Param     :   params请求体内容，encode编码格式
     * Author    :   博客园-依旧淡然
     */
    public static String submitPostData(String urlStr, Map<String, String> params, String encode) {
    	
        byte[] data = getRequestData(params, encode).toString().getBytes(); //获得请求体
        try {
        	URL url = new URL(urlStr);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(3000);	//设置连接超时时间
            httpURLConnection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
            httpURLConnection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
            httpURLConnection.setRequestMethod("POST");	//设置以Post方式提交数据
            httpURLConnection.setUseCaches(false);               //使用Post方式不能使用缓存
            //设置请求体的类型是文本类型
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置请求体的长度
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
            //获得输出流，向服务器写入数据
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data);
            
            int response = httpURLConnection.getResponseCode();            //获得服务器的响应码
            Log.v("responseCode", response+"");
            if(response == HttpURLConnection.HTTP_OK) {
                InputStream inptStream = httpURLConnection.getInputStream();
                return dealResponseResult(inptStream);                     //处理服务器的响应结果
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    /*
      * Function  :   封装请求体信息
      * Param     :   params请求体内容，encode编码格式
      * Author    :   博客园-依旧淡然
      */
     public static StringBuffer getRequestData(Map<String, String> params, String encode) {
         StringBuffer stringBuffer = new StringBuffer();        //存储封装好的请求体信息
         try {
            for(Map.Entry<String, String> entry : params.entrySet()) {
                 stringBuffer.append(entry.getKey())
                             .append("=")
                             .append(URLEncoder.encode(entry.getValue(), encode))
                             .append("&");
             }
             stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
         } catch (Exception e) {
             e.printStackTrace();
         }
         return stringBuffer;
     }
     
     /*
      * Function  :   处理服务器的响应结果（将输入流转化成字符串）
      * Param     :   inputStream服务器的响应输入流
      * Author    :   博客园-依旧淡然
      */
     public static String dealResponseResult(InputStream inputStream) {
         String resultData = null;      //存储处理结果
         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
         byte[] data = new byte[1024];
         int len = 0;
         try {
             while((len = inputStream.read(data)) != -1) {
                 byteArrayOutputStream.write(data, 0, len);
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
         resultData = new String(byteArrayOutputStream.toByteArray());    
         return resultData;
     }
     
     /**
      * 上传文件
      * @param filePath
      * @param url
      * @return 服务器返回数据
      * @throws ClientProtocolException
      * @throws IOException
      * @throws JSONException
      */
     public static String uploadFile(String filePath, String url) throws ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		// 设置通信协议版本
		httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

		HttpPost httppost = new HttpPost(url);
		File file = new File(filePath);

		MultipartEntity mpEntity = new MultipartEntity();
		ContentBody cbFile = new FileBody(file);
		mpEntity.addPart("picFile", cbFile); 

		httppost.setEntity(mpEntity);
		
		Log.v("upload", "executing request " + httppost.getRequestLine());

		HttpResponse response = httpclient.execute(httppost);
		HttpEntity resEntity = response.getEntity();

		String json = "";
		if (resEntity != null) {
			json = EntityUtils.toString(resEntity, "utf-8");
		}
		if (resEntity != null) {
			resEntity.consumeContent();
		}
		httpclient.getConnectionManager().shutdown();
		return json;
	}
    
    /**
     * 下载文件
     * @param sUrl
     * @param path
     */
    public static String downloadFile(String sUrl, String path) {
         InputStream input = null;
         OutputStream output = null;
         HttpURLConnection connection = null;
         try {
             URL url = new URL(sUrl);
             connection = (HttpURLConnection) url.openConnection();
             connection.connect();

             // expect HTTP 200 OK, so we don't mistakenly save error report
             // instead of the file
             if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                 return "Server returned HTTP " + connection.getResponseCode()
                         + " " + connection.getResponseMessage();
             }

             // this will be useful to display download percentage
             // might be -1: server did not report the length
             int fileLength = connection.getContentLength();

             // download the file
             input = connection.getInputStream();
             output = new FileOutputStream(path);

             byte data[] = new byte[4096];
             long total = 0;
             int count;
             while ((count = input.read(data)) != -1) {
                 // allow canceling with back button
                 output.write(data, 0, count);
             }
             Log.d("smarket",path + " download...");
         } catch (Exception e) {
             return e.toString();
         } finally {
             try {
                 if (output != null)
                     output.close();
                 if (input != null)
                     input.close();
             } catch (IOException ignored) {
             }

             if (connection != null)
                 connection.disconnect();
         }
         Log.v("download", sUrl + " to " + path + " finished");
         return null;
     }
}
