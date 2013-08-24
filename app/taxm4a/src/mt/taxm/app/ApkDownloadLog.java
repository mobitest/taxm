package mt.taxm.app;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ApkDownloadLog implements BaseColumns{
    
    public static final String TABLE_NAME = "apk_download_log";
    
    public static final int LEVEL_ESSENTIAL = 1;
    
    public static final String SAVE_DIRECTORY = "/mobi/";
    
    /**********Table Column Below**************/
    
    public static final String VERSION_CODE = "version_code";
    
    public static final String VERSION_NAME = "version_name";
    
    public static final String VERSION_DATE = "version_date";
    
    public static final String VERSION_URL = "version_url";
    
    public static final String VERSION_LEVEL = "version_level";
    
    public static final String DOWNLOAD_DIRECTORY = "download_directory";
    
    public static final String FILE_NAME = "file_name";
    
    public static final String DOWNLOAD_DATE = "download_date";
    
    public static final String[] ALL = {VERSION_CODE, VERSION_NAME, VERSION_DATE, VERSION_URL,
        VERSION_LEVEL, DOWNLOAD_DIRECTORY, FILE_NAME, DOWNLOAD_DATE};
    public static final String generateFileName(int versionCode){
        return versionCode + "-" + Util.dateToYYYYMMDDHHMMSS(new Date()) + ".apk";
    }
    
    public static List<ContentValues> queryAll(SQLiteDatabase db){
        
        Cursor c = db.query(TABLE_NAME, ALL  , 
                null, null, null, null, null);
        if(!c.moveToFirst()){
            return null;
        }
        final List<ContentValues> list = new ArrayList<ContentValues>();
        do {
            ContentValues values = new ContentValues();
            values.put(VERSION_CODE, c.getString(c.getColumnIndex(VERSION_CODE)));
            values.put(VERSION_NAME,c.getString(c.getColumnIndex(VERSION_NAME)));
            values.put(VERSION_DATE,c.getString(c.getColumnIndex(VERSION_DATE)));
            values.put(VERSION_URL, c.getString(c.getColumnIndex(VERSION_URL)));
            values.put(VERSION_LEVEL, c.getString(c.getColumnIndex(VERSION_LEVEL)));
            values.put(DOWNLOAD_DIRECTORY, c.getString(c.getColumnIndex(DOWNLOAD_DIRECTORY)));
            values.put(FILE_NAME, c.getString(c.getColumnIndex(FILE_NAME)));
            values.put(DOWNLOAD_DATE, c.getString(c.getColumnIndex(DOWNLOAD_DATE)));
            list.add(values);
        } while (c.moveToNext());
        c.close();
        return list;
    }

}
