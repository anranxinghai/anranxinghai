package com.swust.ipmsg.util;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.swust.ipmsg.R;


public class Constant {

	// 存放键值对，使用泛型，定义了返回<String, Integer>此类型Map对象的方法，此方法的作用就是将键和值匹配起来,便于引用图片。
	public static Map<String, Integer> exts = new HashMap<String, Integer>();
	static {// 静态语句快
	// 静态语句块在加载该类时执行且只执行一次，多个静态语句块按照出现的次序运行。
		exts.put("doc", R.drawable.doc);
		exts.put("docx", R.drawable.doc);
		exts.put("xls", R.drawable.xls);
		exts.put("xlsx", R.drawable.xls);
		exts.put("ppt", R.drawable.ppt);
		exts.put("pptx", R.drawable.ppt);
		exts.put("jpg", R.drawable.image);
		exts.put("jpeg", R.drawable.image);
		exts.put("gif", R.drawable.image);
		exts.put("png", R.drawable.image);
		exts.put("ico", R.drawable.image);
		exts.put("apk", R.drawable.apk);
		exts.put("jar", R.drawable.jar);
		exts.put("rar", R.drawable.rar);
		exts.put("zip", R.drawable.rar);
		exts.put("mp3", R.drawable.music);
		exts.put("wma", R.drawable.music);
		exts.put("aac", R.drawable.music);
		exts.put("ac3", R.drawable.music);
		exts.put("ogg", R.drawable.music);
		exts.put("flac", R.drawable.music);
		exts.put("midi", R.drawable.music);
		exts.put("pcm", R.drawable.music);
		exts.put("wav", R.drawable.music);
		exts.put("amr", R.drawable.music);
		exts.put("m4a", R.drawable.music);
		exts.put("ape", R.drawable.music);
		exts.put("mid", R.drawable.music);
		exts.put("mka", R.drawable.music);
		exts.put("svx", R.drawable.music);
		exts.put("snd", R.drawable.music);
		exts.put("vqf", R.drawable.music);
		exts.put("aif", R.drawable.music);
		exts.put("voc", R.drawable.music);
		exts.put("cda", R.drawable.music);
		exts.put("mpc", R.drawable.music);
		exts.put("mpeg", R.drawable.video);
		exts.put("mpg", R.drawable.video);
		exts.put("dat", R.drawable.video);
		exts.put("ra", R.drawable.video);
		exts.put("rm", R.drawable.video);
		exts.put("rmvb", R.drawable.video);
		exts.put("mp4", R.drawable.video);
		exts.put("flv", R.drawable.video);
		exts.put("mov", R.drawable.video);
		exts.put("qt", R.drawable.video);
		exts.put("asf", R.drawable.video);
		exts.put("wmv", R.drawable.video);
		exts.put("avi", R.drawable.video);
		exts.put("3gp", R.drawable.video);
		exts.put("mkv", R.drawable.video);
		exts.put("f4v", R.drawable.video);
		exts.put("m4v", R.drawable.video);
		exts.put("m4p", R.drawable.video);
		exts.put("m2v", R.drawable.video);
		exts.put("dat", R.drawable.video);
		exts.put("xvid", R.drawable.video);
		exts.put("divx", R.drawable.video);
		exts.put("vob", R.drawable.video);
		exts.put("mpv", R.drawable.video);
		exts.put("mpeg4", R.drawable.video);
		exts.put("mpe", R.drawable.video);
		exts.put("mlv", R.drawable.video);
		exts.put("ogm", R.drawable.video);
		exts.put("m2ts", R.drawable.video);
		exts.put("mts", R.drawable.video);
		exts.put("ask", R.drawable.video);
		exts.put("trp", R.drawable.video);
		exts.put("tp", R.drawable.video);
		exts.put("ts", R.drawable.video);
	}

	public static Integer[] mImageIds = { R.drawable.head_01, R.drawable.head_02,
			R.drawable.head_03, R.drawable.head_04, R.drawable.head_05,
			R.drawable.head_06, R.drawable.head_07, R.drawable.head_08,
			R.drawable.head_09, R.drawable.head_10, R.drawable.head_11,
			R.drawable.head_12, R.drawable.head_13, R.drawable.head_14,
			R.drawable.head_15, R.drawable.head_16, R.drawable.head_17,
			R.drawable.head_18, R.drawable.head_19, R.drawable.head_20,
			R.drawable.head_21, R.drawable.head_22, R.drawable.head_23,
			R.drawable.head_24, R.drawable.head_25, R.drawable.head_26,
			R.drawable.head_27, R.drawable.head_28, R.drawable.head_29,
			R.drawable.head_30, R.drawable.head_31, R.drawable.head_32,
			R.drawable.head_33, R.drawable.head_34, R.drawable.head_35,
			R.drawable.head_36, R.drawable.head_37, R.drawable.head_38,
			R.drawable.head_39, R.drawable.head_40 };
	
	public static Integer[] mThumbIds = { R.drawable.head_01, R.drawable.head_02,
			R.drawable.head_03, R.drawable.head_04, R.drawable.head_05,
			R.drawable.head_06, R.drawable.head_07, R.drawable.head_08,
			R.drawable.head_09, R.drawable.head_10, R.drawable.head_11,
			R.drawable.head_12, R.drawable.head_13, R.drawable.head_14,
			R.drawable.head_15, R.drawable.head_16, R.drawable.head_17,
			R.drawable.head_18, R.drawable.head_19, R.drawable.head_20,
			R.drawable.head_21, R.drawable.head_22, R.drawable.head_23,
			R.drawable.head_24, R.drawable.head_25, R.drawable.head_26,
			R.drawable.head_27, R.drawable.head_28, R.drawable.head_29,
			R.drawable.head_30, R.drawable.head_31, R.drawable.head_32,
			R.drawable.head_33, R.drawable.head_34, R.drawable.head_35,
			R.drawable.head_36, R.drawable.head_37, R.drawable.head_38,
			R.drawable.head_39, R.drawable.head_40 };
	
	// 自定义Action
	public static final String updateMyInformationAction = "com.swust.ipmsg.updateMyInformation";
	public static final String personHasChangedAction = "com.swust.ipmsg.personHasChanged";
	public static final String hasMsgUpdatedAction = "com.swust.ipmsg.hasMsgUpdated";
	public static final String startChatAction = "com.swust.ipmsg.startChatAction";
	public static final String receivedSendFileRequestAction = "com.swust.ipmsg.receivedSendFileRequest";
	public static final String refuseReceiveFileAction = "com.swust.ipmsg.refuseReceiveFile";
	public static final String remoteUserRefuseReceiveFileAction = "com.swust.ipmsg.remoteUserRefuseReceiveFile";
	public static final String dataReceiveErrorAction = "com.swust.ipmsg.dataReceiveError";
	public static final String dataSendErrorAction = "com.swust.ipmsg.dataSendError";
	public static final String whoIsAliveAction = "com.swust.ipmsg.whoIsAlive";// 询问当前那个Activity是激活状态
	public static final String imAliveNow = "com.swust.ipmsg.imAliveNow";
	public static final String remoteUserUnAliveAction = "com.swust.ipmsg.remoteUserUnAlive";
	public static final String fileSendStateUpdateAction = "com.swust.ipmsg.fileSendStateUpdate";
	public static final String fileReceiveStateUpdateAction = "com.swust.ipmsg.fileReceiveStateUpdate";
	public static final String receivedTalkRequestAction = "com.swust.ipmsg.receivedTalkRequest";
	public static final String acceptTalkRequestAction = "com.swust.ipmsg.acceptTalkRequest";
	public static final String remoteUserClosedTalkAction = "com.swust.ipmsg.remoteUserClosedTalk";

	// 系统Action
	// System Action declare
	public static final String bootCompleted = "android.intent.action.BOOT_COMPLETED";//启动成功
	//public static final String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
	//是ConnectivityManager类的一个常量
	public static final String WIFIACTION = "android.net.conn.CONNECTIVITY_CHANGE";
	public static final String ETHACTION = "android.intent.action.ETH_STATE";

	// 生成唯一ID码
	public static int getMyId() {
		int id = (int) (Math.random() * 1000000);// 返回n >= 0.0 && n < 1.0.的double的数n，并乘以1000000
		return id;
	}

/*	// other 其它定义，另外消息长度为60个汉字，utf-8中定义一个汉字占3个字节，所以消息长度为180bytes
	// 文件长度为30个汉字，所以总长度为90个字节
	public static final int bufferSize = 256;
	public static final int msgLength = 180;
	public static final int fileNameLength = 90;
	public static final int readBufferSize = 4096;// 文件读写缓存
	//pagHead[0] = 'A',pagHead[1] = 'N',pagHead[2] = 'D'
	public static final byte[] pkgHead = "AND".getBytes();
	public static final int CMD80 = 80;
	public static final int CMD81 = 81;
	public static final int CMD82 = 82;
	public static final int CMD83 = 83;
	public static final int CMD_TYPE1 = 1;
	public static final int CMD_TYPE2 = 2;
	public static final int CMD_TYPE3 = 3;
	public static final int OPR_CMD1 = 1;
	public static final int OPR_CMD2 = 2;
	public static final int OPR_CMD3 = 3;
	public static final int OPR_CMD4 = 4;
	public static final int OPR_CMD5 = 5;
	public static final int OPR_CMD6 = 6;
	public static final int OPR_CMD10 = 10;
	public static final String MULTICAST_IP = "239.9.9.1";
	public static final int PORT = 5760;
	public static final int AUDIO_PORT = 5761;*/

	// int to ip转换
	public static String intToIp(int i) {// 将IP以字符串形式返回。
		String ip = ((i >> 24) & 0xFF) + "." + ((i >> 16) & 0xFF) + "."
				+ ((i >> 8) & 0xFF) + "." + (i & 0xFF);
		return ip;
	}

	// 其它定义
	public static final int FILE_RESULT_CODE = 1;
	public static final int SELECT_FILES = 1;// 是否要在文件选择器中显示文件
	public static final int SELECT_FILE_PATH = 2;// 文件选择器只显示文件夹
	// 文件选择状态保存
	
	// TreeMap：Create a natural order, empty tree map whose keys must be
	// mutually comparable and non-null.
	public static TreeMap<Integer, Boolean> fileSelectedState = new TreeMap<Integer, Boolean>();

	// 转换文件大小
	public static String formatFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");//十进制编码
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = fileS + "B";
			// fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

}
