
package mt.taxm.app;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class HttpHelper {

    private static final String TAG = "HttpHelper";

    public static final String HTTP_PACK = "mobi";

    private static final String CODE = "code";
    
    /**
     * HttpEntity -> JSON Object
     * @param entity
     * @return
     * @throws JSONException 
     * @throws IOException 
     */
    public static JSONObject formatJSON(HttpEntity entity) throws IOException{
        if(entity==null){
            throw new IOException("");
        }
        JSONObject json = null;
        try {
            String jsonStr = "";
            //add by xiacp 2011.9.22 解压压缩数据
            if (entity.getContentEncoding() != null && 
                    entity.getContentEncoding().getValue().equalsIgnoreCase("gzip")) {
                
                Log.d(TAG, "ContentEncoding:" + entity.getContentEncoding().getValue());
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(new GZIPInputStream(entity.getContent()))); 
                StringBuffer buffer = new StringBuffer(); 
                String line = ""; 
                while ((line = in.readLine()) != null){ 
                  buffer.append(line); 
                }
                in.close();
                jsonStr = buffer.toString();
            } else {
                jsonStr = EntityUtils.toString(entity);
            }
//            String jsonStr = EntityUtils.toString(entity);
            json = new JSONObject(jsonStr);
        } catch (ParseException e) {
            Log.w(TAG, e.getMessage(), e);
        } catch (JSONException e) {
        	throw new IOException("");
        }
//        Log.d(TAG, "Received JSON: "+ (json== null? "null" : json.toString()));
//        Log.d(TAG, "------------------------  finish  ----------------------");
        return json;
    }

    private static void checkSessionException(JSONObject json) throws SessionException {
    	if(json == null){
    		return;
    	}
        try {
            if(json.has(CODE)){
                String code = json.getString(CODE);
                Log.d(TAG,"Session Code:"+code);
                if(code.equals("404")){
                    throw new SessionException("Session Time Out.");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }   
    }

    /**
     * 拆包
     * 
     * @param entity
     * @return
     * @throws JSONException
     * @throws IOException
     * @throws SessionException
     */
    public static JSONObject packHttpEntity(HttpEntity entity) throws IOException, SessionException{
        JSONObject json = null;
        try {
            //{"mobi":[{"CAT_ID":"1040","CAT_NAME":"产品发展概况c","KPI_ID":null,"KPI_NAME":null}]} 
            json = formatJSON(entity); 
            checkSessionException(json);
            if(json != null){
            	return json.getJSONObject(HTTP_PACK);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 拆包
     * 
     * @param entity
     * @return
     * @throws JSONException
     * @throws IOException
     * @throws SessionException
     */
    public static JSONArray packHttpEntityList(HttpEntity entity) throws IOException, SessionException {
        JSONObject json = null;
        try {
            json = formatJSON(entity);
            checkSessionException(json);
            return json.getJSONArray(HTTP_PACK);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }   

    public static String formatMapToString(Map<String, String> map) {
        if (map == null) {
            return "N/A";
        }
        StringBuffer buffer = new StringBuffer();
        for (String key : map.keySet()) {
            buffer.append("{" + key + "=" + map.get(key) + "},");
        }
        return buffer.toString();
    }

    public static String formatListToString(List<Pair> list) {
        if (list == null) {
            return "N/A";
        }
        StringBuffer buffer = new StringBuffer();
        for (Pair p : list) {
            buffer.append("{" + p.key + "=" + p.value + "},");
        }
        return buffer.toString();
    }

}
