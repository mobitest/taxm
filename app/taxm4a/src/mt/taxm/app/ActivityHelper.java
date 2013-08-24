
package mt.taxm.app;

import org.apache.http.client.HttpResponseException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.util.Calendar;

public class ActivityHelper {


    private static final int LENGTH_SHORT = 800;
private static ConnectivityManager connectivityManager;

    public static void simpleToast(Context context, int message) {
        Toast.makeText(context.getApplicationContext(), message, LENGTH_SHORT).show();
    }

    public static void simpleToast(Context context, String message) {
        Toast.makeText(context.getApplicationContext(), message, LENGTH_SHORT).show();
    }
    

    public static ProgressDialog showProgressDialog(Context context, int message) {
        return ProgressDialog.show(context, null, context.getResources().getString(message));
    }

    public static boolean isNetAvailable(){
        if(connectivityManager == null){
            return false;
        }
        NetworkInfo netWorkInfo = connectivityManager.getActiveNetworkInfo();
        if(netWorkInfo == null){
            return false;
        }
        
        return netWorkInfo.isConnected();
    }
    /**
     * 异常处理提示
     * 
     * @param context 上下文
     * @param e 异常对象
     */
    public static void exceptionHandler(final Context context, Object e){
        if(e instanceof IOException){
            if(e instanceof HttpResponseException){
                if(((HttpResponseException)e).getStatusCode() >= 500){
                    simpleToast(context, R.string.toast_server_exc);
                }else {
                	HttpResponseException ee = (HttpResponseException)e;
                    simpleToast(context, R.string.toast_server_status_exc ) ;
                    simpleToast(context, ee.getStatusCode() + ":"+ ee.getMessage()); 
                    return;
                }
            }else if(e instanceof ConnectException){
                simpleToast(context, R.string.toast_io_exc);
            }else{
                simpleToast(context, R.string.toast_io_exc);
            }

            if(!isNetAvailable()){
                //add by xiacp 2011.10.28
                //增加网络设置跳转
//                simpleToast(context, R.string.toast_network_exc);
                AlertDialog.Builder builder = (Builder) new AlertDialog.Builder(context).
                        setMessage(R.string.toast_network_exc).
                        setPositiveButton("网络设置", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                            }
                        }).setNegativeButton("取消", null);
                builder.create().show();
                return ;
            }
            
        }else if(e instanceof DisconnectException){
            simpleToast(context, R.string.toast_network_exc);
        }else if(e instanceof SessionException){
//            reLogin(context, R.string.toast_session_exc);
        }else{
            simpleToast(context, R.string.toast_io_exc);
        }
    }

}
