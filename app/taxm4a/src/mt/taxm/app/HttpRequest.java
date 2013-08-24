package mt.taxm.app;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class HttpRequest {

    private static final String TAG = "HttpRequest";

    private static HttpRequest _instance;

    private static DefaultHttpClient httpClient;

//    private HttpPost httpPost = null;
//    
//    private HttpGet httpGet = null;

    // private List<NameValuePair> postParams;

    private static int timeoutConnection = 5000;

    private static int timeoutSocket = 30000;
    
    public static final String STATUS_CODE = "status_code:";
    
    private HttpRequest() {
        getHttpClient();
    }

    /**
     * 返回HttpRequest对象
     * 
     * @return
     */
    public static HttpRequest getInstance() {
        if (_instance == null) {
            _instance = new HttpRequest();
        }
        return _instance;
    }

    /**
     * 发出HTTP POST请求
     * 
     * @param url 请求的URL路径
     * @param params Body参数
     * @param headers Header参数
     * @return HttpEntity
     * @throws ClientProtocolException 协议出错
     * @throws IOException 连接出错
     * @throws DisconnectException 
     */
    public HttpEntity doPost(String url, Map<String, String> params, Map<String, String> headers)
            throws IOException, DisconnectException {
        try {
            HttpPost requestPost = buildPostRequest(url, params, headers);
            HttpResponse response = httpClient.execute(requestPost);
//            }
            
            if (checkHttpStatus(response)) {
                return response.getEntity();
            } else {
                throw new HttpResponseException(response.getStatusLine().getStatusCode(), url);
//                throw new IOException(STATUS_CODE + response.getStatusLine().getStatusCode());
            }
        } catch (SocketTimeoutException e) {
            Log.d(TAG, "Using DisconnectException to proxy SocketTimeoutException.");
            throw new DisconnectException(e);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw new IOException();
//            throw new RuntimeException(e);
        }
    }

    public HttpEntity doPost(String url, List<Pair> params, Map<String, String> headers)
            throws DisconnectException, IOException {
        try {
            HttpPost requestPost = buildPostRequest(url, params, headers);
//            requestPost.addHeader("Accept-Encoding", "gzip");
//            requestPost.addHeader("User-Agent", "Mozila/4.0(compatible;MSIE5.01;Window NT5.0)");
//            HttpResponse response = httpClient.execute(requestPost);
//            HttpResponse response;
//            String proxy = Proxy.getDefaultHost();
//            int port = Proxy.getDefaultPort();
//            if(!MobiApp.isWIFIAvailable() && CTWAP_PROXY.equals(proxy)){
//                HttpHost host = new HttpHost(proxy, port);
//                httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, host);
//                Log.d(TAG, "******use proxy:" + host.toString());
//                response = httpClient.execute(requestPost);
//            }else {
//                if(httpClient.getParams().getParameter(ConnRoutePNames.DEFAULT_PROXY) != null){
//                    httpClient.getParams().removeParameter (ConnRoutePNames.DEFAULT_PROXY);
//                }
            HttpResponse response = httpClient.execute(requestPost);
//            }
            
            if (checkHttpStatus(response)) {
                return response.getEntity();
            } else {
                throw new HttpResponseException(response.getStatusLine().getStatusCode(), url);
//                throw new IOException(STATUS_CODE + response.getStatusLine().getStatusCode());
            }
        }catch (ClientProtocolException e) {
            e.printStackTrace();
            throw new IOException();
//            throw new RuntimeException(e);
        }
    }

    public HttpEntity doGet(String url,boolean useEntity) throws ClientProtocolException, IOException{
        HttpGet requestGet = new HttpGet(url);
        HttpResponse response = httpClient.execute(requestGet);
        
        HttpEntity entity = response.getEntity();
        
        if(!checkHttpStatus(response) || entity == null){
            throw new HttpResponseException(response.getStatusLine().getStatusCode(), url);
        }
        return entity;
    }
    public String doGet(String url) throws ClientProtocolException, IOException{
        HttpGet requestGet = new HttpGet(url);
        String ret = "";
        HttpResponse response = httpClient.execute(requestGet);
//        }
        
        HttpEntity entity = response.getEntity();
        
        if(!checkHttpStatus(response) || entity == null){
            return ret;
        }
        
        //add by xiacp 2011.9.22 解压压缩数据
        if (entity.getContentEncoding() != null && 
                entity.getContentEncoding().getValue().contains("gzip")) {
            
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(new GZIPInputStream(entity.getContent()))); 
            StringBuffer buffer = new StringBuffer(); 
            String line = ""; 
            while ((line = in.readLine()) != null){ 
              buffer.append(line); 
            }
            in.close();
            return buffer.toString();
        } else {
            return EntityUtils.toString(response.getEntity());
        }
    }
    
    /**
     * add by xiacp 2011.7.27
     * 用于下载
     * @throws ClientProtocolException, IOException
     * 
     * @return InputStrem
     */
    public InputStream doDownload(String url) throws ClientProtocolException, IOException{
        HttpGet requestGet = new HttpGet(url);
        
        
//        requestGet.addHeader("Accept-Encoding", "gzip");
//        final HttpResponse response = httpClient.execute(requestGet);
//        HttpResponse response;
//        String proxy = Proxy.getDefaultHost();
//        int port = Proxy.getDefaultPort();
//        if(!MobiApp.isWIFIAvailable() && CTWAP_PROXY.equals(proxy)){
//            HttpHost host = new HttpHost(proxy, port);
//            httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, host);
//            Log.d(TAG, "******use proxy:" + host.toString());
//            response = httpClient.execute(requestGet);
//        }else {
//            if(httpClient.getParams().getParameter(ConnRoutePNames.DEFAULT_PROXY) != null){
//                httpClient.getParams().removeParameter (ConnRoutePNames.DEFAULT_PROXY);
//            }
        HttpResponse response = httpClient.execute(requestGet);
//        }
        
        HttpEntity entity = response.getEntity();
        if(checkHttpStatus(response) && null != entity){
            Header contentEncoding = response.getFirstHeader("Content-Encoding");
            if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
                return new GZIPInputStream(entity.getContent());
            }
        	return entity.getContent();
        }
		return null;
    }

    /**
     * 关闭连接
     */
    public void closed() {
        httpClient = null;
        _instance = null;
    }

    /**
     * 解析HTTP服务器响应信息，判断请求是否成功,
     * 
     * @return true:成功 ,false:失败
     */
    private boolean checkHttpStatus(HttpResponse resp) {
        int status = resp.getStatusLine().getStatusCode();
//        Log.d(TAG, "Http Response Status: " + status);
        if (status == HttpStatus.SC_OK) {
            return true;
        }
        Log.w(TAG, "====request failed ， http response status is: " + status);
        return false;
    }

    /**
     * 创建Http POST Request对象
     * 
     * @param params Body参数
     * @param headers Head参数
     * @return HttpRequest对象
     * @throws UnsupportedEncodingException 参数编码不正确
     */
    private HttpPost buildPostRequest(String url, Map<String, String> params,
            Map<String, String> headers) {
        // TODO 未完成
//        Log.d(TAG, "-----------------------Send Http Req----------------------");
//        Log.d(TAG, MobiApp.serverUrl + url);
//        Log.d(TAG, "POST Body:" + HttpHelper.formatMapToString(params));
        HttpPost post = new HttpPost(taxm.serverUrl + url); 
        List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        if (params != null) {
            for (String key : params.keySet()) {
                String value = params.get(key);
                postParams.add(new BasicNameValuePair(key, value));
            }
        }
        try {
            post.setEntity(new UrlEncodedFormEntity(postParams, "utf-8"));
            return post;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    private HttpPost buildPostRequest(String url, List<Pair> params, Map<String, String> headers) {
        // TODO 未完成
//    	Log.d(TAG, "-----------------------Send Http Req----------------------");
//        Log.d(TAG, MobiApp.serverUrl + url);
//        Log.d(TAG, "POST Body:" + HttpHelper.formatListToString(params));
        HttpPost post = new HttpPost(taxm.serverUrl + url);
        List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        if (params != null) {
            for (Pair p : params) {
                postParams.add(new BasicNameValuePair(p.key, p.value));
            }
        }
        try {
            post.setEntity(new UrlEncodedFormEntity(postParams, "utf-8"));
            return post;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
    
//    private HttpUriRequest addHttpHeader(HttpUriRequest req){
//        if(req == null){
//            return req; 
//        }
//        req.setHeader("Accept-Encoding", "GZIP");
//        
//        return req;
//    }


    /**
     * 创建HttpClient,重写SSL模块
     * 
     * @return HttpClient
     */
    private static DefaultHttpClient getHttpClient() {
        HttpParams myParams = new BasicHttpParams();

        HttpProtocolParams.setVersion(myParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(myParams, "utf-8");
        HttpConnectionParams.setConnectionTimeout(myParams, timeoutConnection);
        HttpConnectionParams.setSoTimeout(myParams, timeoutSocket);

        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        registry.register(new Scheme("https", new EasySSLSocketFactory(), 443));

        httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(myParams, registry), myParams);
        
        //增加GZIP请求
        httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
            @Override
            public void process(org.apache.http.HttpRequest request,
                    HttpContext context) throws HttpException, IOException {
                if (!request.containsHeader("Accept-Encoding")) {
                    request.addHeader("Accept-Encoding", "gzip");
                }  
            }
        });
        return httpClient;
    }
}
