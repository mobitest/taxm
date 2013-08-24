
package mt.taxm.app;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.io.StringBufferInputStream;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Util {
    public static final String TAG = "Util";
    
    public static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public static SimpleDateFormat SDF_1 = new SimpleDateFormat("M月d日 HH:mm");

    public static SimpleDateFormat SDF_YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");

    public static SimpleDateFormat SDF_YYYYMMDDHHMMSSL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static SimpleDateFormat SDF_ONLY_DATE = new SimpleDateFormat("yyyy-MM-dd");

    public static SimpleDateFormat SDF_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");

    public static String SCREEN_SHOTS_LOCATION = "/sdcard/MoBI/cache/";
    
    public static final String SNAP_NAVIGATOR_LOCATION = "/sdcard/MoBI/snap/";
    
    public static final String SNAP_NAVIGATOR_FILE_SUFFIX = ".nvg";

    public static Date stringToDate(String str) {
        try {
            return SDF.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Date parseStrDate(String format, String date){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static String formatDateToStr(String format, Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
    
    /**
     * 
     * @param format
     * @param date
     * @return [year, month(0-11), day]
     */
    public static int[] parseStrDateArr(String format, String date){
        Calendar c = Calendar.getInstance();
        c.setTime(parseStrDate(format, date));
        int[] darr = new int[3];
        darr[0] = c.get(Calendar.YEAR);
        darr[1] = c.get(Calendar.MONTH);
        darr[2] = c.get(Calendar.DAY_OF_MONTH);
        return darr;
    }
    
    /**
     * 
     * @param format
     * @param year
     * @param month 0-11
     * @param day
     * @return
     */
    public static String getStrDateByFormat(String format, int year, int month, int day){
        return new SimpleDateFormat(format).format(new GregorianCalendar(year, month, day).getTime());
    }

    /**
     * return data like format: yyyyMMdd
     * 
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getStrDate(int year, int month, int day) {
        StringBuilder sb = new StringBuilder();
        sb.append(year).append(month > 9 ? month : "0" + month).append(day > 9 ? day : "0" + day);
        return sb.toString();
    }

    /**
     * return data like format: yyyyMMdd
     * 
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getStrDate(int year, int month, int day, String separator) {
        StringBuilder sb = new StringBuilder();
        sb.append(year).append(separator).append(month > 9 ? month : "0" + month)
                .append(separator).append(day > 9 ? day : "0" + day);
        return sb.toString();
    }

    public static String dateToString(Date date) {
        return SDF.format(date);
    }
    
    public static String dateToString(long date) {
        return SDF.format(new Date(date));
    }
    
    public static String dateToStr(long date) {
        return SDF_1.format(new Date(date));
    }
    
    

    public static Date stringToDateOnly(String str) {
        try {
            return SDF_ONLY_DATE.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String dateOnlyToString(Date date) {
        return SDF_ONLY_DATE.format(date);
    }

    public static String dateToYYYYMMDDHHMMSS(Date date) {
        return SDF_YYYYMMDDHHMMSS.format(date);
    }

    public static String strDateToYYYYMMDDHHMMSSL(String date) {
        try {
            return SDF_YYYYMMDDHHMMSSL.format(SDF_YYYYMMDDHHMMSS.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String dateToYYYYMMDDHHMMSSL(Date date) {
        return SDF_YYYYMMDDHHMMSSL.format(date);
    }

    public static String dateToYYYYMMDD(Date date) {
        return SDF_YYYYMMDD.format(date);
    }

    
    /**
     * 判断参数中的年月日 是否超过改变后的年月日。
     * 
     * @param year
     * @param month between 0 to 11
     * @param day
     * @param field 改变的字段(YEAR, MONTH or DAY_OF_MONTH)
     * @param offset 改变的值
     * @return 0：相等，1：超出给定的日期，-1：小于给定的日期
     */
    public static int isDateOverBound(int year, int month, int day, int field, int offset) {
        // 对比的对象日期（当前天）
        Calendar maxData = Calendar.getInstance();

        //设定日期
        Calendar compareDate = Calendar.getInstance();
        compareDate.set(year, month, day);
        compareDate.add(field, offset);
        
        int y = compareDate.get(Calendar.YEAR);
        int m = compareDate.get(Calendar.MONTH);
        int d = compareDate.get(Calendar.DAY_OF_MONTH);
        
        return isDateOverBound(y, m, d, maxData);
    }
    
    /**
     * 判断参数中的年月日 是否超过给定的日期
     * @param year 指定年
     * @param month 指定月
     * @param day 指定日
     * @param maxDate 给定的日期
     * @return 0：相等，1：超出给定的日期，-1：小于给定的日期
     */
    public static int isDateOverBound(int year, int month, int day, Calendar maxDate) {
        if(maxDate == null){
            return 1;
        }
        // 指定日期
        Calendar compareDate = Calendar.getInstance();
        compareDate.set(year, month, day);
        
        // 判断相等
        int y = compareDate.get(Calendar.YEAR);
        int m = compareDate.get(Calendar.MONTH);
        int d = compareDate.get(Calendar.DAY_OF_MONTH);
        if (maxDate.get(Calendar.DAY_OF_MONTH) == d && maxDate.get(Calendar.MONTH) == m
                && maxDate.get(Calendar.YEAR) == y) {
            return 0;
        }
        
        // 判断大于
        if (compareDate.compareTo(maxDate) == 1) {
            return 1;
        }
        
        return -1;
    }
    
    public static Date stringToDate(String str, String format) {
        try {
            return new SimpleDateFormat(format).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String dateToString(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }
    
    public static CharSequence dateFormat(String format){
        return DateFormat.format(format, System.currentTimeMillis());
    }

    public static Integer getVersionCode(Context cxt) {
        try {
            return cxt.getPackageManager().getPackageInfo(cxt.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static Date setCalendar(Date currentDay, int field, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDay);
        calendar.add(field, value);
        return calendar.getTime();
    }

    public static Date getPrevDay(Date currentDay) {
        return setCalendar(currentDay, Calendar.DATE, -1);
    }

    public static Date getNextDay(Date currentDay) {
        return setCalendar(currentDay, Calendar.DATE, 1);
    }

    public static Uri takeScreenShot(View view) throws Exception {
        return takeScreenShot(view, "temp");
    }

    public static String formatStrArrayToStr(String[] strArr) {
        if (strArr == null) {
            throw new IllegalArgumentException(TAG + "   String[] from do not allowede to be null");
        }
        final StringBuilder sb = new StringBuilder();
        final String spaceMark = ",";
        final int len = strArr.length;
        if (len == 0) {
            throw new IllegalArgumentException(TAG + "    String[] from do not allowede to be null");
        }
        for (int i = 0; i < len; i++) {
            sb.append(strArr[i]).append(spaceMark);
        }
        return sb.toString();
    }

    public static String formatIntArrayToStr(int[] intArr) {
        if (intArr == null) {
            throw new IllegalArgumentException(TAG + "    int[]to do not allowede to be null");
        }
        final StringBuilder sb = new StringBuilder();
        final String spaceMark = ",";
        final int len = intArr.length;
        if (len == 0) {
            throw new IllegalArgumentException(TAG + "    int[]to do not allowede to be null");
        }
        for (int i = 0; i < len; i++) {
            sb.append(intArr[i]).append(spaceMark);
        }
        return sb.toString();
    }

    public static String[] getStrArr(String comStr) {
        if (comStr == null) {
            throw new IllegalArgumentException(TAG + "    comStr do not allowede to be null");
        }
        final String spaceMark = ",";
        return comStr.split(spaceMark);
    }

    public static int[] getIntArr(String comStr) {
        final String[] strs = getStrArr(comStr);
        final int len = strs.length;
        final int[] intArr = new int[len];
        for (int i = 0; i < len; i++) {
            intArr[i] = Integer.parseInt(strs[i]);
        }
        return intArr;
    }

    public static Uri takeScreenShot(View view, String name) throws Exception {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b = view.getDrawingCache();
        FileOutputStream fos = null;
        try {
            File sddir = new File(SCREEN_SHOTS_LOCATION + "image/");
            if (!sddir.exists()) {
                sddir.mkdirs();
            }
            fos = new FileOutputStream(SCREEN_SHOTS_LOCATION + "image/" + name + ".png");
            if (fos != null) {
                b.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.close();
            }
            return Uri.parse("file://" + SCREEN_SHOTS_LOCATION + "image/" + name + ".png");
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 将对象转化为字符串
     * @param obj
     * @return
     */
    public static String objectToString(Serializable obj){
        String s = null;
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
            objOut.writeObject(obj);
            s = byteOut.toString();
            byteOut.close();
            objOut.close();
        } catch (IOException e) {
            Log.w(TAG, e);
            e.printStackTrace();
        }
        return s;
    }
    
    /**
     * 将对象转化为byte[]
     * @param obj
     * @return
     */
    public static byte[] objectToBytes(Serializable obj){
        byte[] b = null;
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
            objOut.writeObject(obj);
            b = byteOut.toByteArray();
            byteOut.close();
            objOut.close();
        } catch (IOException e) {
            Log.w(TAG, e);
            e.printStackTrace();
        }
        return b;
    }
    
    public static Object objStringToObject(String ObjStr){
        Object obj = null;
        StringBufferInputStream buffInput = new StringBufferInputStream(ObjStr);
        try {
            ObjectInputStream objInput = new ObjectInputStream(buffInput);
            obj = objInput.readObject();
            objInput.close();
            buffInput.close();
        } catch (StreamCorruptedException e) {
            Log.w(TAG, e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.w(TAG, e);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Log.w(TAG, e);
            e.printStackTrace();
        }
        return obj;
    }
    
    public static Object bytesToObject(byte[] bytes){
        Object obj = null;
        ByteArrayInputStream buffInput = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream objInput = new ObjectInputStream(buffInput);
            obj = objInput.readObject();
            objInput.close();
            buffInput.close();
        } catch (StreamCorruptedException e) {
            Log.w(TAG, e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.w(TAG, e);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Log.w(TAG, e);
            e.printStackTrace();
        }
        return obj;
    }
    
    public static Object readObjFromAndroidFile(String fileName) {
        ObjectInputStream ois = null;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                return null;
            }
            ois = new ObjectInputStream(new FileInputStream(file));
            return ois.readObject();
        } catch (Exception e) {
            Log.w("Read object failed", e);
            return null;
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    Log.w("close server config loading err" , e);
                }
            }
        }
    }

    public static void writeObjectToFile(Object obj, String path,
            String fileName){
        if (obj == null) {
            return;
        }
        ObjectOutputStream oos = null;
        try {
            File f = new File(path);
            if (!f.exists()) {
                if (!f.mkdirs()) {
                    Log.w("Util", "create file : " + path  + " failed");
                    return;
                }
            }
            try {
                oos = new ObjectOutputStream(new FileOutputStream(path + fileName));
                oos.writeObject(obj);
            } catch (FileNotFoundException e) {
                Log.w(TAG, e);
                return;
            } catch (IOException e) {
                Log.w(TAG, e);
                return;
            }
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w(TAG, e);
                }
            }
        }
    }
    
    
    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
    
}
