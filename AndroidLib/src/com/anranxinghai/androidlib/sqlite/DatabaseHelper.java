package com.anranxinghai.androidlib.sqlite;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.anranxinghai.androidlib.R;
import com.anranxinghai.androidlib.bean.UserInfo;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final int VERSION = 1;

	private static final String DATABASE_NAME = "application.db";
	private static final int DATABASE_VERSION = 1;
	private Dao<UserInfo,Integer> userInfoDao = null;
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION,R.raw.ormlite_config);
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase,
			ConnectionSource connectionSource) {
		// TODO Auto-generated method stub
		try {
			//到处都是反射机制
			TableUtils.createTable(connectionSource, UserInfo.class);
		} catch (SQLException e) {
			// TODO: handle exception
			Log.e(DatabaseHelper.class.getName(), "Unable to create database",
					e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase,
			ConnectionSource connectionSource, int oldVer, int newVer) {
		// TODO Auto-generated method stub
		try {
			TableUtils.dropTable(connectionSource, UserInfo.class, true);
			onCreate(sqLiteDatabase,connectionSource);
		} catch (SQLException e) {
			// TODO: handle exception
			Log.e(DatabaseHelper.class.getName(),
					"Unable to update database from version " + oldVer + " to "
							+ newVer, e);
		}
	}
	
	public Dao<UserInfo, Integer> getUserInfoDao() throws SQLException{
		
		if (userInfoDao == null) {
			userInfoDao = getDao(UserInfo.class);
		}
		return userInfoDao;
		
	}

}
