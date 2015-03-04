package com.channelsoft.android.aboutus.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.channelsoft.android.aboutus.db.QNSQLiteHelper;

public class DBOperationUtils
{
    private Context        context = null;
    private SQLiteDatabase db;
    private QNSQLiteHelper helper;

    public DBOperationUtils(Context context)
    {
        this.context = context;
    }

    /**
     * 打开数据连接
     * 
     * @return
     * @throws SQLException
     */
    public DBOperationUtils open() throws SQLException
    {
        helper = new QNSQLiteHelper(context);
        db = helper.getWritableDatabase(); // 如果数据库不存在就建立一个，反之存在则根据版本更新
        return this;
    }

    /**
     * 关闭数据连接
     */
    public void close()
    {
        helper.close();
    }

    /**
     * 查询数据库操作
     * @param sql
     * @param selectionArgs
     * @return
     * @throws Exception
     */
    public Cursor query(String sql, String[] selectionArgs) throws Exception
    {

        if (db.isOpen())
        {

            Cursor cursor = db.rawQuery(sql, selectionArgs);

            if (cursor != null)
            {

                cursor.moveToFirst();

            }

            return cursor;
        }
        return null;

    }

}
