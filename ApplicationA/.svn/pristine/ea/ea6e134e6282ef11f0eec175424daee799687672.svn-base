package com.anranxinghai.applicationa.db;



import com.anranxinghai.application.aidl.UserInfo;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    private static final Class<?>[] classes = new Class[] { UserInfo.class };

    public static void main(String[] args) throws Exception {

        writeConfigFile("ormlite_config.txt", classes);

    }

}
