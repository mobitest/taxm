package mt.taxm.app;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpResponseException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import mt.taxm.app.*;


public class GetNewVersion extends AsyncTask<Void, Void, Object> {
    private static final String TAG = "GetNewVersion";

	private static final String VERSION_URL = "services/api/appversion/anymore/";

    private Context mContext;

    private ProgressDialog mLoadingDialog;

    private String newVersionName;

    private int currVersionCode;

    private int newVersionCode;

    private String newVersionInfo;

    private String newVersionRemark;

    private String newVersionDate;

    private String newVersionUrl;

    private int newVersionNecessary;

    private Boolean isContinue = true;

    private Boolean isAuto = false;

    private SharedPreferences settingPrefs;
    String SHOW_UPDATE_DIALOG = "show_update_dialog";
    
    /**
     * network type
     * 仅在自动更新时使用，用于指示当前网络类型
     */
    private int networkType = -1;

	private DatabaseHelper mDbHelper;
    
    private static HttpRequest connect;

    public GetNewVersion() {
        if (connect == null) {
            connect = HttpRequest.getInstance();
        }
    }
    public static final class Version{
        public static final String VERSION_NAME = "title";
        /**
         * 详细描述
         */
        public static final String VERSION_REMARK = "remark";
        public static final String IS_NECESSARY = "mustUpdate";
        /**
         * 简要描述
         */
        public static final String VERSION_INFO = "releaseNotes";
        public static final String VERSION_DATE = "releaseDate";
        public static final String VERSION_URL = "url";
        public static final String VERSION_CODE = "num";
        public static final int NECESSARY = 1;
        
    }
 
    public GetNewVersion(Context context, DatabaseHelper mDbHelper,
            Boolean isAuto,ProgressDialog mLoadingDialog) {
        this.mContext = context;
        this.mDbHelper = mDbHelper;
        this.isAuto = isAuto;
        this.mLoadingDialog = mLoadingDialog;
        settingPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    @Override
    protected void onPreExecute() {
        if(mLoadingDialog == null){
            showLoadingDailog("检查版本中,取消请按退回键...");
        }else{
//            mLoadingDialog.setMessage("检查版本中,请稍后...");
            mLoadingDialog.setMessage("正在联网，请稍候...");
        }
        
        try {
            PackageInfo packageInfo = mContext.getPackageManager()
                    .getPackageInfo(mContext.getPackageName(), 0);
            currVersionCode = packageInfo.versionCode;
            // String currentVersionName = packageInfo.versionName;
        } catch (NameNotFoundException e) {
            Log.w(TAG, e);
            isContinue = false;
        }
    }

    @Override
    protected Object doInBackground(Void... params) {
        if (isContinue) {
            try {
                return getLastVersion(currVersionCode);
            } catch (DisconnectException e) {
                Log.w(TAG, e);
                return e;
            } catch(HttpResponseException e){
            	Log.w(TAG,e);
            	return e;
            } catch (IOException e) {
                Log.w(TAG, e);
                return e;
            } catch (SessionException e) {
                Log.w(TAG, e);
                return e;
            }
        } else {
            return isContinue;
        }
    }
    
    private JSONObject getLastVersion(int currentCode) throws DisconnectException, HttpResponseException, IOException, SessionException
    {
    	Map<String, String> post = new HashMap<String, String>();
    	//   	post.put("code", currentCode + "");
    	//   	post.put("version_type", "android");
    	if (connect == null) {
    		connect = HttpRequest.getInstance();
    	}

    	String url = taxm.serverUrl + VERSION_URL + currentCode + ".json";
    	JSONObject json = HttpHelper. formatJSON(connect.doGet(url,true));
    	//   	JSONObject json = HttpHelper.packHttpEntity(connect.doPost(VERSION_URL + currentCode + ".json", post,null));
    	Log.d(TAG, json == null ? " null " : json.toString());
    	return json;
   }

    @Override
    protected void onPostExecute(Object result) {
        try {
            if (result instanceof JSONObject) {
//                if (isAuto) {
//                    if(NetworkType.ID.CT_WAP_ID == networkType){
//                        //使用 CTWAP， 建议使用CTNET
//                        ActivityHelper.simpleToast(mContext, "正在使用CTWAP，建议使用CTNET");
//                    }
//                }
                JSONObject jo = (JSONObject) result;
                newVersionCode = jo.getInt(Version.VERSION_CODE);
                newVersionName = jo.getString(Version.VERSION_NAME);
                newVersionInfo = jo.getString(Version.VERSION_INFO);
                newVersionRemark = jo.getString(Version.VERSION_REMARK);
                newVersionDate = jo.getString(Version.VERSION_DATE);
                newVersionUrl = jo.getString(Version.VERSION_URL);
                newVersionNecessary = jo.getInt(Version.IS_NECESSARY);
//                Log.d("download", newVersionUrl);
                if (currVersionCode < newVersionCode) {
                    dismissLoadingDialog();
                    if (newVersionNecessary == Version.NECESSARY) {
                        showVersionDialog(newVersionNecessary);
//                        preInstallNewVersion();
                    } else {
                        if (isAuto
                                && !settingPrefs.getBoolean(
                                        SHOW_UPDATE_DIALOG, true)) {
//                            ((taxm) mContext).onGetVersionEnd();
                        } else {
                            // 发现新版本
                            showVersionDialog(newVersionNecessary);
                        }
                    }
                } else {// 无新版本时提示用户
                    if (isAuto) {
//                        ((LoginActivity) mContext).onGetVersionEnd();
                    	dismissLoadingDialog();
                    }else{
                        dismissLoadingDialog();
                        Toast.makeText(mContext, "已是最新版本，不需要更新！",
                                Toast.LENGTH_LONG).show();
                    }
                }
            } else if(result instanceof Integer){
                //使用 CM 移动网络， 受限制
            	 dismissLoadingDialog();
            }else{
                dismissLoadingDialog();
                ActivityHelper.exceptionHandler(mContext, result);
            }
        } catch (JSONException e) {
            Log.w(TAG, e);
            dismissLoadingDialog();
            Toast.makeText(mContext,
                    mContext.getString(R.string.toast_check_update_failed),
                    Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 更新时，根据用户配置（提醒安装），提示用户安装更新。
     * 若为强制更新，且用户选择不更新则直接退出客户端
     * 
     * @param necessary 是否强制更新，1:强制更新，其他为可选更新。
     */
    private void showVersionDialog(int necessary) {
        //是否强制更新
        final boolean isNecessary = (necessary == Version.NECESSARY);
        String NegativeStr = "取消";
        
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        // builder.setView(layout);
        if(isNecessary){
            builder.setTitle("应用强制更新");
            NegativeStr = "退出";
            builder.setCancelable(false);
        }else {
            builder.setTitle("i应用可选更新");
        }
//        builder.setIcon(R.drawable.alert_dialog_icon);
        final View view = LayoutInflater.from(mContext).inflate(
                R.layout.alert_dialog_show_version, null);
        ((TextView) view.findViewById(R.id.newVersionName))
                .setText(newVersionName == null
                        || newVersionName.equals("null") ? "" : newVersionName);
        ((TextView) view.findViewById(R.id.newVersionInfo))
                .setText(newVersionInfo == null
                        || newVersionInfo.equals("null") ? "" : newVersionInfo);
        ((TextView) view.findViewById(R.id.newVersionRemark))
                .setText(newVersionRemark == null
                        || newVersionRemark.equals("null") ? ""
                        : newVersionRemark);
        ((TextView) view.findViewById(R.id.newVersionDate))
                .setText(newVersionDate == null ? "" : newVersionDate);
        if (!isAuto) {
            ((CheckBox) view.findViewById(R.id.show_update_again))
                    .setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.check_update_again))
                    .setVisibility(View.GONE);
        }else{
            ((CheckBox)view.findViewById(R.id.show_update_again))
                  .setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {
                Editor edit = settingPrefs.edit();
                if (isChecked) {
                    edit.putBoolean(SHOW_UPDATE_DIALOG, false);
                } else {
                    edit.putBoolean(SHOW_UPDATE_DIALOG, true);
                }
                edit.commit();
            }
          });
        }
        builder.setView(view);
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                preInstallNewVersion();
            }
        });
        builder.setNegativeButton(NegativeStr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isAuto) {
                    if(isNecessary){
                        //取消强制更新，直接退出界面
                        ((taxm) mContext).finish();
                    }else {
//                        ((taxm) mContext).onGetVersionEnd();
                    }
                }
            }

        });
        AlertDialog alert = builder.create();
        alert.setOwnerActivity((Activity) mContext);
        alert.show();
    }

    private void preInstallNewVersion() {
        showLoadingDailog("开始更新版本...");
        Cursor c = mDbHelper.queryApkbyCode(newVersionCode);
        if (c == null) {
            downloadNewVersion();
        } else {
            final String fileUrl = c.getString(c
                    .getColumnIndex(ApkDownloadLog.DOWNLOAD_DIRECTORY))
                    + c.getString(c
                            .getColumnIndex(ApkDownloadLog.FILE_NAME));
            c.close();
            File apkFile = new File(fileUrl);
            if (apkFile.exists()) {
                dismissLoadingDialog();
                installApkFromFIle(apkFile);
            } else {
                downloadNewVersion();
            }
        }
    }

    private void downloadNewVersion() {
//        if (newVersionUrl.startsWith("/")) {
            newVersionUrl = taxm.serverUrl + newVersionUrl;
//        } else {
//            newVersionUrl = taxm.serverUrl + "/" + newVersionUrl;
//        }
        new DownloadFile().execute();
    }

    private void installApkFromFIle(File f) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(f),
                "application/vnd.android.package-archive");
        ((Activity) mContext).finish();
        mContext.startActivity(intent);
    }

    private class DownloadFile extends AsyncTask<Void, Void, Object> {

        private String directory;

        private String fileName;

        @Override
        protected void onPreExecute() {
            mLoadingDialog.setMessage("正在下载更新，请稍候...");
        }

        @Override
        protected Object doInBackground(Void... params) {
            directory = Environment.getExternalStorageDirectory().getPath()
                    + ApkDownloadLog.SAVE_DIRECTORY;
//            Log.d(TAG, "download file directoryis  ; " + directory);
            fileName = ApkDownloadLog.generateFileName(newVersionCode);
            File drifile = new File(directory);
            if (!drifile.exists()) {
                if (!drifile.mkdir()) {
                    Log.w(TAG, "创建文件到SD卡失败");
                    return null;
                }
            }
            File apkFile = new File(drifile, fileName);
            int count;
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
            	Log.d(TAG, newVersionUrl);
                inputStream = new BufferedInputStream(HttpRequest.getInstance()
                        .doDownload(newVersionUrl));
                outputStream = new BufferedOutputStream(new FileOutputStream(
                        apkFile));
                byte data[] = new byte[8 * 1024];
                long total = 0;
                while ((count = inputStream.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // publishProgress((int)(total * 100 / lenghtOfFile));
                    outputStream.write(data, 0, count);
                }

                outputStream.flush();
            } catch (MalformedURLException e) {
                Log.w(TAG, e);
                dismissLoadingDialog();
                return null;
            } catch (IOException e) {
                Log.w(TAG, e);
                if (apkFile != null) {
                    apkFile.delete();
                    apkFile = null;
                }
                return e;
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        Log.w(TAG, e);
                        return null;
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        Log.w(TAG, e);
                        return null;
                    }
                }
            }
            return apkFile;
        }

        // @Override
        // public void onProgressUpdate(Integer... args) {
        // mLoadingDialog.setProgress(args[0]);
        // }

        @Override
        protected void onPostExecute(Object result) {
            if (result == null) {
                dismissLoadingDialog();
                Toast.makeText(mContext, "在SD卡创建系统临时文件失败！", Toast.LENGTH_LONG)
                        .show();
                return;
            }
            if (result instanceof File) {
                File apkFile = (File) result;
                final ContentValues cv = new ContentValues();
                cv.put(ApkDownloadLog.VERSION_CODE, newVersionCode);
                cv.put(ApkDownloadLog.VERSION_NAME, newVersionName);
                cv.put(ApkDownloadLog.VERSION_DATE, newVersionDate);
                cv.put(ApkDownloadLog.VERSION_URL, newVersionUrl);
                cv.put(ApkDownloadLog.VERSION_LEVEL,
                        ApkDownloadLog.LEVEL_ESSENTIAL);
                cv.put(ApkDownloadLog.DOWNLOAD_DIRECTORY, directory);
                cv.put(ApkDownloadLog.FILE_NAME, fileName);
                cv.put(ApkDownloadLog.DOWNLOAD_DATE,
                        System.currentTimeMillis());
                mDbHelper.insert(ApkDownloadLog.TABLE_NAME, cv);
                dismissLoadingDialog();
                installApkFromFIle(apkFile);
            } else if (result instanceof IOException) {
                dismissLoadingDialog();
                ActivityHelper.exceptionHandler(mContext, result);
            }
        }
    }

    private void showLoadingDailog(String msg) {
        mLoadingDialog = new ProgressDialog(mContext);
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.setMessage(msg);
        mLoadingDialog.show();
    }

    private void dismissLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }
}
