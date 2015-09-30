package com.anranxinghai.applicationa.providers;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;

import com.anranxinghai.application.aidl.UserInfo;
import com.anranxinghai.applicationa.constant.Constants;
import com.anranxinghai.applicationa.constant.UserInfoProviderMetaDada;
import com.anranxinghai.applicationa.db.DatabaseHelper;
import com.tojc.ormlite.android.OrmLiteSimpleContentProvider;
import com.tojc.ormlite.android.framework.MatcherController;
import com.tojc.ormlite.android.framework.MatcherPattern;
import com.tojc.ormlite.android.framework.MimeTypeVnd.SubType;
import com.tojc.ormlite.android.framework.OperationParameters.InsertParameters;

public class UserInfoContentProvider extends
		OrmLiteSimpleContentProvider<DatabaseHelper> {

	public static UriMatcher uriMarcher;
	

	@Override
	protected Class<DatabaseHelper> getHelperClass() {
		// TODO Auto-generated method stub
		return DatabaseHelper.class;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		setMatcherController(new MatcherController()
				.add(UserInfo.class, SubType.DIRECTORY, "",
						UserInfoProviderMetaDada.CONTENT_URI_PATTERN_QUERY)
				/*.add(UserInfo.class, SubType.ITEM, "#",
						UserInfoProviderMetaDada.CONTENT_URI_PATTERN_EDIT)
				.add(UserInfo.class, SubType.ITEM, "#",
						UserInfoProviderMetaDada.CONTENT_URI_PATTERN_DELETE)*/);
		return true;
	}

	//同样不需要实现，默认实现已经很好了。
	/*@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		// TODO Auto-generated method stub
		DatabaseHelper databaseHelper = getHelper();
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		db.beginTransaction();
		try {
			for (int i = 0; i < values.length; i++) {
				db.insert("user", null, values[i]);
			}
		} catch (SQLiteException e) {
			// TODO: handle exception
		}
		return super.bulkInsert(uri, values);
	}*/

	

	



	//这种方式并没有提高效率，基本上只是实现了默认实现
	/*@Override
	public Uri onInsert(DatabaseHelper helper, SQLiteDatabase db,
			MatcherPattern target, InsertParameters parameter) {
		// TODO Auto-generated method stub
		db.beginTransaction();
		Uri uri = null;
		try {
			db.insert(target.getTableInfo().getName(), null, parameter.getValues());
			
			uri = parameter.getUri();
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			db.endTransaction();
		}
		
		return uri;
	}*/
	
	
	

}
