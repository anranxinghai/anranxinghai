package com.anranxinghai.applicationb.constant;

import android.content.ContentResolver;
import android.net.Uri;

public class Constants {
	public static final String TABLE_NAME = "user";
	public static final String USERINFOR_TRANSPORT = "com.anranxinghai.application.USERINFOR_TRANSPORT";
	public static final String AUTOHORITY = "com.anranxinghai.applicationa.providers.userinfoprovider";
	public static final Uri CONTENT_URI = Uri.parse(ContentResolver.SCHEME_CONTENT + AUTOHORITY);
	
	public static final int CONTENT_URI_PATTERN_QUERY = 1;
    public static final int CONTENT_URI_PATTERN_EDIT = 2;
    public static final int CONTENT_URI_PATTERN_DELETE = 3;
}
