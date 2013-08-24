package mt.taxm.app;

import java.util.ArrayList;
import java.util.List;

import mt.taxm.app.R;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class ServerConfig implements BaseColumns{
    
    public static final String TABLE_NAME = "ServerURL";
    
    public static final String SERVERROW_ID = "_ID";
    
    /**
     * 服务器名称
     */
    public static final String SERVERROW_NAME = "ServerName";
    /**
     * 服务器URL
     */
    public static final String SERVERROW_URL = "ServerUrl";
    
    /**
     * 用户名， 加密
     */
    public static final String SERVERROW_USER = "ServerUser";
    /**
     * 密码，加密
     */
    public static final String SERVERROW_PASSWORD = "ServerPassword";
    
    /**
     * 判断服务器是否选中
     */
    public static final String SERVERROW_ISELECTED = "ServerisSelected"; 
    
    /**
     * 表示此服务器为被选中
     */
    public static final String NOSELECTED = "0";
    
    /**
     * 表示此服务器被选中
     */
    public static final String SELECTED = "1";
    
    public static final String[] PROJECTIONS_ALL = {SERVERROW_ID,SERVERROW_NAME,SERVERROW_URL,SERVERROW_USER,SERVERROW_PASSWORD,SERVERROW_ISELECTED};
    
    public static final String[] PROJECTIONS_EXCEPT_ISELECTED= {SERVERROW_ID, SERVERROW_NAME,SERVERROW_URL,SERVERROW_USER,SERVERROW_PASSWORD};
    /**
     * 选择登录服务器
     * @return
     */
    public static String SelServer(DatabaseHelper mDbHelper){
        Cursor c = mDbHelper.query(ServerConfig.TABLE_NAME, null, ServerConfig.PROJECTIONS_ALL, null,
        		null, null, null, null);
        c.moveToFirst();
        String mSelServer = null;
        do{
        	int m = c.getInt(c.getColumnIndex(SERVERROW_ISELECTED));
        	if(m == 1){
        		mSelServer = c.getString(c.getColumnIndex(SERVERROW_URL));
        	}
        }while(c.moveToNext());
        c.close();
        mDbHelper.close();
		return mSelServer;
    }

    public static List<ContentValues> queryAll(SQLiteDatabase db){
        
        Cursor c = db.query(TABLE_NAME, PROJECTIONS_ALL  , 
                null, null, null, null, null);
        if(!c.moveToFirst()){
            return null;
        }
        final List<ContentValues> list = new ArrayList<ContentValues>();
        do {
            ContentValues values = new ContentValues();
            if(c.getString(c.getColumnIndex(SERVERROW_URL))
                    .equals(DatabaseHelper.defaultServerUrl)){
                continue;
            }
            values.put(SERVERROW_NAME, c.getString(c.getColumnIndex(SERVERROW_NAME)));
            values.put(SERVERROW_URL,c.getString(c.getColumnIndex(SERVERROW_URL)));
            values.put(SERVERROW_USER,c.getString(c.getColumnIndex(SERVERROW_USER)));
            values.put(SERVERROW_PASSWORD, c.getString(c.getColumnIndex(SERVERROW_PASSWORD)));
            values.put(SERVERROW_ISELECTED, c.getString(c.getColumnIndex(SERVERROW_ISELECTED)));
            list.add(values);
        } while (c.moveToNext());
        c.close();
        return list;
    }
}
