
package mt.taxm.app;

import mt.taxm.app.R;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DATABASE_NAME = "mt.db";
    
    public static final int DELETE_NONE_ROWS = 0;
    
    public static String defaultServerUrl;
    
    private String defaultServerName;
    
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, context.getResources().getInteger(R.integer.db_version));
        defaultServerUrl = context.getString(R.string.default_server_url).toString();
        defaultServerName = context.getString(R.string.default_server_name).toString();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {       
        final String createApkLogSql = "CREATE VIRTUAL TABLE " + ApkDownloadLog.TABLE_NAME
                + " USING fts3(" + ApkDownloadLog.VERSION_CODE + ","
                + ApkDownloadLog.VERSION_NAME + "," 
                + ApkDownloadLog.VERSION_DATE + ","
                + ApkDownloadLog.VERSION_URL + "," 
                + ApkDownloadLog.VERSION_LEVEL + ","
                + ApkDownloadLog.DOWNLOAD_DIRECTORY + "," 
                + ApkDownloadLog.FILE_NAME + ","
                + ApkDownloadLog.DOWNLOAD_DATE + ");";
        Log.d( TAG, createApkLogSql );
        db.execSQL(createApkLogSql);
        
        final String createServerSql = "CREATE TABLE IF NOT EXISTS " + ServerConfig.TABLE_NAME
               + "(" + ServerConfig.SERVERROW_ID + " INTEGER PRIMARY KEY ,"
               + ServerConfig.SERVERROW_NAME + " varchar(20)," 
               + ServerConfig.SERVERROW_URL  + " varchar(50),"
               + ServerConfig.SERVERROW_USER +" varchar(20),"
               + ServerConfig.SERVERROW_PASSWORD + " varchar(20),"
               + ServerConfig.SERVERROW_ISELECTED + " integer"+");";
        Log.d(TAG, createServerSql);
        db.execSQL(createServerSql);
        
        final String insertServer = "INSERT INTO " + ServerConfig.TABLE_NAME
                + "(" + ServerConfig.SERVERROW_NAME + "," 
                +   ServerConfig.SERVERROW_URL + ","
                + ServerConfig.SERVERROW_ISELECTED + ") VALUES ("
                + "'" + defaultServerName + "',"
                + "'" + defaultServerUrl + "'," + ServerConfig.SELECTED + ");";
        Log.d(TAG, insertServer);
        db.execSQL(insertServer);

    }
    

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        int upgradeVersion = oldVersion;
            //删除老版中的数据表
            db.execSQL("DROP TABLE IF EXISTS " + ApkDownloadLog.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + ServerConfig.TABLE_NAME);
            onCreate(db);
            //重新插入数据
//            dataInsert(db,Snap.TABLE_NAME,snapList);
//            dataInsert(db,Bookmark.TABLE_NAME,bookmarkList);
//            dataInsert(db,ApkDownloadLog.TABLE_NAME,logList);
//            dataInsert(db,ServerConfig.TABLE_NAME,serverList);
    }
    
    private void dataInsert(SQLiteDatabase db , String name, List<ContentValues> list){
        if(list == null){
            return;
        }
        for(int i = 0; i < list.size(); i++){
            db.insert(name, null, list.get(i));
        }
    }
    /**
     * 注：该方法仅用于外部调用
     */
    public void truncateTables(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + ApkDownloadLog.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ServerConfig.TABLE_NAME);
        onCreate(db);
    }
    
    

    public long insert(String table, ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(table, null, values);
        Log.d(TAG, "insert into " + table + ", at position " + id + " where content values is "
                + values);
        return id;
    }
    
    public long update(String table, ContentValues values, String whereClause, String[] whereArgs){
    	SQLiteDatabase db = getWritableDatabase();
        long id = db.update(table, values, whereClause, whereArgs);
		return id;
    	
    }
    
    
    /**
     * 
     * @param tableName
     * @param projectionMap
     * @param projections
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param sortOrder
     * @return
     */
    public Cursor query(String tableName, HashMap<String, String> projectionMap,
            String[] columns, String selection, String[] selectionArgs, String groupBy,
            String having, String sortOrder) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(tableName);
        builder.setProjectionMap(projectionMap);
        Log.d(TAG, builder.buildQuery(columns, selection, selectionArgs, groupBy, having, sortOrder, null));
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(tableName, columns, selection, selectionArgs, groupBy, having, sortOrder);
//        Cursor c = builder.query(getReadableDatabase(), projections, selection, selectionArgs,
//                groupBy, having, sortOrder);
        if (c == null) {
            return null;
        } else if (!c.moveToFirst()) {
            c.close();
            return null;
        }
        return c;
    }

    public int delete(String table, String whereClause, String[] args) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(table, whereClause, args);
    }

    public void deleteAll(String table) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(table, null, null);
    }

    public Cursor queryAll(String table) {
        SQLiteDatabase db = getReadableDatabase();
        final String sql = "SELECT rowid,* FROM " + table;
        Log.d(TAG, sql);
        Cursor c = db.rawQuery(sql, null);
        if (c == null) {
            return null;
        }
        if (!c.moveToFirst()) {
            c.close();
            return null;
        }
        return c;
    }

    public Cursor queryById(String table, String rowId) {
        SQLiteDatabase db = getReadableDatabase();
        final String sql = "SELECT rowid,* FROM " + table + " where rowid = " + rowId;
        Log.d(TAG, sql);
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public Cursor queryApkbyCode(int versionCode) {
        SQLiteDatabase db = getReadableDatabase();
        final String sql = "SELECT rowid, * FROM " + ApkDownloadLog.TABLE_NAME + " where "
                + ApkDownloadLog.VERSION_CODE + "=" + versionCode;
        Log.d(TAG, sql);
        Cursor c = db.rawQuery(sql, null);
        if (c == null) {
            return null;
        }
        if (!c.moveToFirst()) {
            c.close();
            return null;
        }
        return c;
    }
    
    public Cursor rawQuery(String sql, String[] args){
    	if(sql != null && !sql.equals("")){
    		Log.d(TAG, sql);
    		SQLiteDatabase db = getWritableDatabase();
    		return db.rawQuery(sql, args);
    	}
    	
    	return null;
    }
    
    public void execSQL(String sql){
    	if(sql != null && !sql.equals("")){
    		Log.d(TAG, sql);
    		SQLiteDatabase db = getWritableDatabase();
    		db.execSQL(sql);
    	}
    }

}
