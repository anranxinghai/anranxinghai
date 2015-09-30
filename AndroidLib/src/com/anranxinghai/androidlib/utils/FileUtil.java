package com.anranxinghai.androidlib.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import com.anranxinghai.androidlib.common.Constant;

/**
 * 文件操作工具类
 * 
 * @author anranxinghai
 *
 */
@SuppressLint("NewApi")
public class FileUtil {
	private static final String TAG = "FileUtil";

	/**
	 * 删除文件
	 * 
	 * @param filename
	 *            文件名
	 */
	public static boolean deleteFile(String filename) {
		boolean result = false;
		try {
			File file = new File(filename);
			if (file.exists()) {
				result = file.delete();
			}
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 
	 * 删除目录和所有文件
	 * 
	 * @param path
	 * @return
	 */
	public static boolean deleteDir(String path) {
		boolean success = true;
		File file = new File(path);
		if (file.exists()) {
			File[] list = file.listFiles();
			if (list != null) {
				int len = list.length;
				for (int i = 0; i < len; ++i) {
					if (list[i].isDirectory()) {
						deleteDir(list[i].getPath());
					} else {
						boolean ret = list[i].delete();
						if (!ret) {
							success = false;
						}
					}
				}
			}
		} else {
			success = false;
		}
		if (success) {
			file.delete();
		}
		return success;
	}

	/**
	 * 
	 * 确保目录存在
	 * 
	 * @param path
	 */
	public static void ifFolderNotExistsCreate(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	/**
	 * 
	 * 目录是否存在
	 */
	public static boolean ifFolderExists(String path) {
		File file = new File(path);
		return file.exists();
	}

	/**
	 * 
	 * 拷贝文件夹，（递归）
	 * 
	 * @param source
	 * @param target
	 * @return 是否复制成功
	 */
	public static boolean copyFolder(String source, String target) {
		File sourceFile = new File(source);
		File targetFile = new File(target);
		if (!sourceFile.exists()) {
			return false;
		}
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		File[] file = sourceFile.listFiles();
		FileInputStream fis = null;
		FileOutputStream fos = null;
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				try {
					fis = new FileInputStream(file[i]);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				try {
					File f = new File(target + "/" + file[i].getName());
					fos = new FileOutputStream(f);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				int bufferLength;
				byte[] b = new byte[1024 * 5];
				try {
					while ((bufferLength = fis.read(b)) != -1) {
						fos.write(b, 0, bufferLength);
					}
					fis.close();
					fos.flush();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else
				copyFolder(source + "/" + file[i].getName(), target + "/" + file[i].getName());
		}
		return true;
	}

	/**
	 * 
	 * 拷贝文件
	 * 
	 * @param fromFile
	 * @param toFile
	 * @param reWrite
	 * @throws IOException
	 */
	public static boolean copyFile(File fromFile, File toFile, Boolean reWrite) throws IOException {
		if (!fromFile.exists()) {
			return false;
		}
		if (!fromFile.isFile()) {
			return false;
		}
		if (!fromFile.canRead()) {
			return false;
		}
		if (!toFile.getParentFile().exists()) {
			return false;
		}
		if (toFile.exists() && reWrite) {
			toFile.delete();
		}
		FileInputStream fisfrom = new FileInputStream(fromFile);
		FileOutputStream fosto = new FileOutputStream(toFile);
		byte bt[] = new byte[1024];
		int c;
		while ((c = fisfrom.read(bt)) > 0) {
			fosto.write(bt, 0, c);
		}
		fisfrom.close();
		fosto.close();
		return true;
	}

	/**
	 * 
	 * 获取文件夹大小
	 * 
	 * @param file
	 *            File实例
	 * @return size 单位为字节
	 * 
	 * @throws Exception
	 */
	public static long getFolderSize(File file) throws Exception {
		long size = 0;
		File[] fileList = file.listFiles();
		if (null == fileList) {
			return size;
		}
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isDirectory()) {
				size = size + getFolderSize(fileList[i]);
			} else {
				size = size + fileList[i].length();
			}
		}
		return size;
	}

	/**
	 * 返回存储媒介的可用空间。单位：字节
	 * 
	 * @param memoryType
	 * @return 返回可用字节数。返回-1时表示获取失败（例如U盘不存在）
	 */
	public static long getAvailaSize(Constant.MemoryType memoryType) {
		StatFs statfs = null;
		try {
			switch (memoryType) {
			case MEMORY_U:
				if (usbStorageExist()) {
					statfs = new StatFs(Constant.U_PATH);
				} else {
					return -1;
				}
				break;
			case MEMORY_EXTERNAL_SD:
				if (externalSDExist()) {
					statfs = new StatFs(Constant.EXTERNAL_SD_PATH);
				} else {
					return -1;
				}
				break;
			case MEMORY_INTERNAL_SD:
				statfs = new StatFs(Constant.INTERNAL_SD_PATH);
				break;
			default:
				return 0;
			}
			long blockSize = statfs.getBlockSizeLong();
			long availableBlocks = statfs.getAvailableBlocksLong();
			return availableBlocks * blockSize;
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * 
	 * 判断U盘是否可用
	 * 
	 * 
	 * 
	 * @return
	 */
	public static boolean usbStorageExist() {
		File uPan = new File(Constant.U_PATH);
		return (null != uPan.listFiles());
	}

	/**
	 * 
	 * 判断外部SD卡是否可用
	 * 
	 * 
	 * 
	 * @return
	 */
	public static boolean externalSDExist() {
		File uPan = new File(Constant.EXTERNAL_SD_PATH);
		return (null != uPan.listFiles());
	}

	public static void writeFile(File textFile, String content) throws IOException {
		try {
			if (!textFile.exists()) {
				createFile(textFile);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(textFile));
		writer.write(content);
		writer.close();
	}

	public static boolean createFile(File file) throws IOException {
		if (file.isDirectory()) {
			Log.d(TAG, "文件是目录");
			file.mkdirs();
		} else if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
			Log.d(TAG, "文件不是目录,并且其父文件夹不存在：" + file.getParentFile());
		}
		file.createNewFile();
		return true;
	}

	public static String readFile(String filename) throws IOException {
		if (!isSDCardExist()) {
			return "";
		}
		File file = new File(filename);
		return readFile(file);
	}

	/**
	 * 读取文本文件内容
	 * 
	 * @param textFile
	 *            文件名
	 * @return
	 * @throws IOException
	 */
	public static String readFile(File textFile) throws IOException {
		try {
			if (!textFile.exists()) {
				createFile(textFile);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(new FileReader(textFile));
		StringBuilder builder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			builder.append(line);
			builder.append("\n");
		}
		reader.close();
		return builder.toString();
	}

	/**
	 * 判断SDCard是否存在
	 * 
	 * @return
	 */
	public static boolean isSDCardExist() {
		String state = Environment.getExternalStorageState();
		return !state.equals(Environment.MEDIA_MOUNTED) ? false : true;
	}

	public static void writeFile(String filename, String content) throws IOException {
		if (!isSDCardExist()) {
			return;
		}
		File file = new File(filename);
		writeFile(file, content);
	}

	public static boolean haveFile(String filePath) {
		Log.d(TAG, "filename:" + filePath);
		File file = new File(filePath);
		if (file.exists()) {
			Log.d(TAG, "判断是否已经下载了新版本安装包 true");
			return true;
		} else {
			Log.d(TAG, "判断是否已经下载了新版本安装包 false");
			return false;
		}
	}

	public static File createSDDir(String dirname) {
		File file = new File(dirname);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	// 上传图片
	public static String uploadFile(Context context, File uploadFile) {
		String imgPath = "";
		/*
		 * String jessionId = Constant.getJessionId(context); String appnodeurl
		 * = Constant.getAppNodeUrl(context); if (jessionId == null ||
		 * "".equals(jessionId)) { return imgPath; }
		 */
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		DataOutputStream ds = null;
		try {
			URL url = new URL(/*
							 * appnodeurl + UPLOAD_FILE + ";jsessionid=" +
							 * jessionId
							 */Constant.UPLOAD_FILE);
			Log.d(TAG, "--------------" + url);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			/* 允许Input、Output，不使用Cache */
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			/* 设置传送的method=POST */
			con.setRequestMethod("POST");
			/* setRequestProperty */
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "GBK");
			con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			/* 设置DataOutputStream */
			ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; " + "name=\"img\";filename=\"" + uploadFile.getName() + "\"" + end);
			ds.writeBytes(end);
			/* 取得文件的FileInputStream */
			FileInputStream fStream = new FileInputStream(uploadFile);
			/* 设置每次写入1024bytes */
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
			/* 从文件读取数据至缓冲区 */
			while ((length = fStream.read(buffer)) != -1) {
				/* 将资料写入DataOutputStream中 */
				ds.write(buffer, 0, length);
			}
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			/* close streams */
			fStream.close();
			ds.flush();
			/* 取得Response内容 */
			int code = con.getResponseCode();
			String value = "";
			if (code == 200) {
				InputStream is = con.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "GBK"));
				int ch;
				StringBuffer b = new StringBuffer();
				while ((ch = reader.read()) != -1) {
					b.append((char) ch);
				}
				value = b.toString();
			}
			try {
				Log.d(TAG, "------value--------" + value);
				JSONObject jsonObject = new JSONObject(value);
				if ("00".equals(jsonObject.getString("returnCode"))) {
					imgPath = jsonObject.getString("imgPath");
				} else if ("-1".equals(jsonObject.getString("returnCode"))) {
					Log.d(TAG, "" + jsonObject.getString("returnMsg"));
				} else {
					Log.d(TAG, "上传图片异常");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			ds.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imgPath;
	}

	/**
	 * 通过url加载图片，可以用ImageLoader
	 * 
	 * @param url
	 * @return
	 */
	public static Drawable loadImageFromUrl(String url) {
		Log.d("TAG", "HttpUtil loadImageFromUrl start,imgUrl:" + url);
		URL mUrl;
		InputStream inputStream = null;
		Drawable drawable = null;
		try {
			mUrl = new URL(url);
			inputStream = (InputStream) mUrl.getContent();
			drawable = Drawable.createFromStream(inputStream, "src");
		} catch (MalformedURLException e1) {
			Log.d("TAG", "HttpUtil loadImageFromUrl MalformedURLException", e1);
			drawable = null;
		} catch (IOException e) {
			Log.d("TAG", "HttpUtil loadImageFromUrl IOException", e);
			drawable = null;
		} catch (Exception e) {
			Log.d("TAG", "HttpUtil loadImageFromUrl Exception", e);
			drawable = null;
		}
		return drawable;
	}
}
