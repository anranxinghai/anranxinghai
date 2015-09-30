package com.anranxinghai.androidlib.databasecolumn;

import android.net.Uri;
import android.provider.BaseColumns;

public class UserInfoColumn implements BaseColumns{
	
	public static final String AUTOHORITY = "com.anranxinghai.androidlib.providers.userinfoprovider";
	
	public static final int CONTENT_URI_PATTERN_QUERY = 167556;
    public static final int CONTENT_URI_PATTERN_EDIT = 3453452;
    public static final int CONTENT_URI_PATTERN_DELETE = 56453;
    
    public static final String TABLE_NAME = "user";
	public static final String USER_NAME = "user_name";
	public static final String USER_PASSWORD = "user_password";
    
    public static final class UserInfoTableMetaData{
    	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTOHORITY + "/user");
    	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.userinfoprovider.dir";
    	public static final String CONTENT_TYPE_ITEM = "vnd.android.cursor.dir/vnd.userinfoprivoder.dir";
    	
    }
}
