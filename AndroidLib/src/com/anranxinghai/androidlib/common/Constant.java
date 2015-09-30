package com.anranxinghai.androidlib.common;

/**
 * @Description:常量类
 * @author anranxinghai
 */
public class Constant
{
    public static final String TAG = "QNConstant";
  
    // U盘目录
    public static final String U_PATH = "/mnt/usb_storage";
    // 外部SD卡目录
    public static final String EXTERNAL_SD_PATH = "/mnt/external_sd";
    // 内部SD卡目录
    public static final String INTERNAL_SD_PATH = "/mnt/sdcard";
    // 存储媒介类型
    public enum MemoryType
    {
        MEMORY_U, MEMORY_EXTERNAL_SD, MEMORY_INTERNAL_SD
    }
    
    
    public static final String UPLOAD_FILE = "web/uploadFile.action";
}
