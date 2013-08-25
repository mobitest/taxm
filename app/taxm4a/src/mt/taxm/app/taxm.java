/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package mt.taxm.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.widget.TextView;

import org.apache.cordova.*;

public class taxm extends DroidGap
{
    public static String serverUrl ="http://192.168.1.8:8080/";
    private mt.taxm.app.DatabaseHelper mDbHelper;
	private ProgressDialog mLoadingDialog;

	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        mDbHelper = new DatabaseHelper( taxm.this );
        serverUrl = ServerConfig.SelServer(mDbHelper);
        CookieManager.setAcceptFileSchemeCookies(true);
        super.onCreate(savedInstanceState);
        // Set by <content src="index.html" /> in config.xml
        super.loadUrl(Config.getStartUrl());
        //super.loadUrl("file:///android_asset/www/index.html")
        
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(
                    "mt.taxm.app", 0);
            String vname =  packageInfo.versionName;
            int vcode =  packageInfo.versionCode;
            
            Log.d(TAG, "versionName=" + vname+ ";versionCode="+ vcode);
            
            new GetNewVersion(taxm.this, mDbHelper, true, mLoadingDialog).execute();
        } catch (NameNotFoundException e1) {
            e1.printStackTrace();
        }
     }
    
    /**
     * 该方法仅用于外部调用
     * 用于启动在线登录子系统
     */
    public void onGetVersionEnd(){
    }
    
    private void showLoadingDailog(String msg) {
        mLoadingDialog = new ProgressDialog(taxm.this);
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

