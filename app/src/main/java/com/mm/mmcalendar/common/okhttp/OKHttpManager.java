package com.mm.mmcalendar.common.okhttp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import android.text.TextUtils;
import android.widget.Toast;

import com.mm.mmcalendar.AppExt;
import com.mm.mmcalendar.R;
import com.mm.mmcalendar.common.constant.BmobConst;
import com.mm.mmcalendar.common.log.LogUtil;
import com.mm.mmcalendar.common.utils.ACache;
import com.mm.mmcalendar.common.utils.JsonResult;
import com.mm.mmcalendar.common.utils.SSLUtil;
import com.mm.mmcalendar.common.utils.WifiNetUtils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import javax.net.ssl.SSLSocketFactory;

import okio.Buffer;

/**
 * OKHttp管理类
 * @author xxqj
 * @date 2016-11-3
 */
public class OKHttpManager {

	private static final String TAG = "OKHttpManager";
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private static final String CERT_FILENAME ="bmob.cer";
	private static OKHttpManager instance;
	private OkHttpClient mClient;
	private static final String CERT="-----BEGIN CERTIFICATE-----\n" +
			"MIIGLjCCBRagAwIBAgIDFCb6MA0GCSqGSIb3DQEBCwUAMIGMMQswCQYDVQQGEwJJTDEWMBQGA1UE\n" +
			"ChMNU3RhcnRDb20gTHRkLjErMCkGA1UECxMiU2VjdXJlIERpZ2l0YWwgQ2VydGlmaWNhdGUgU2ln\n" +
			"bmluZzE4MDYGA1UEAxMvU3RhcnRDb20gQ2xhc3MgMSBQcmltYXJ5IEludGVybWVkaWF0ZSBTZXJ2\n" +
			"ZXIgQ0EwHhcNMTQxMTA3MDExNzM0WhcNMTUxMTA4MDIxMDQ2WjBJMQswCQYDVQQGEwJDTjEUMBIG\n" +
			"A1UEAxMLYXBpLmJtb2IuY24xJDAiBgkqhkiG9w0BCQEWFWhlc2hhb3l1ZUBmb3htYWlsLmNvbTCC\n" +
			"ASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBANCEvBFYJmhW+8iixdK0zlzwprsuytUGW5BH\n" +
			"ye9EEkJzGzYfVnEO/v4wC3vEvlWqkwTxY/ydnneH+yo0msAN6IEt6IA+3eO55PAlooAF8b8I2e83\n" +
			"usRTK4YmooZc/2GYNk2WBXvVlMuWABMKJ/oQMXlM46gffd3Z+evbbptZ5vm+QEWjUlw8fsTALakq\n" +
			"JgKsrmGSNBVngx1qnm00DL/3yfR2DZHro4CDzRp4toQV3ofcnt6Nz43Z4YkAXZr5gqxge8BZ2n8P\n" +
			"raQo/5wSfWoPW79Z8lPvZSZv5UIGCUAXdt0qYb3awSDsPSnMrRl03V4XmOK3RDdYDPrWMvii+YrC\n" +
			"/vUCAwEAAaOCAtkwggLVMAkGA1UdEwQCMAAwCwYDVR0PBAQDAgOoMBMGA1UdJQQMMAoGCCsGAQUF\n" +
			"BwMBMB0GA1UdDgQWBBR8ztcEh/lE/9fxcga6p7/b+x+pUTAfBgNVHSMEGDAWgBTrQjTQmLCrn/Qb\n" +
			"awj3zGQu7w4sRTAfBgNVHREEGDAWggthcGkuYm1vYi5jboIHYm1vYi5jbjCCAVYGA1UdIASCAU0w\n" +
			"ggFJMAgGBmeBDAECATCCATsGCysGAQQBgbU3AQIDMIIBKjAuBggrBgEFBQcCARYiaHR0cDovL3d3\n" +
			"dy5zdGFydHNzbC5jb20vcG9saWN5LnBkZjCB9wYIKwYBBQUHAgIwgeowJxYgU3RhcnRDb20gQ2Vy\n" +
			"dGlmaWNhdGlvbiBBdXRob3JpdHkwAwIBARqBvlRoaXMgY2VydGlmaWNhdGUgd2FzIGlzc3VlZCBh\n" +
			"Y2NvcmRpbmcgdG8gdGhlIENsYXNzIDEgVmFsaWRhdGlvbiByZXF1aXJlbWVudHMgb2YgdGhlIFN0\n" +
			"YXJ0Q29tIENBIHBvbGljeSwgcmVsaWFuY2Ugb25seSBmb3IgdGhlIGludGVuZGVkIHB1cnBvc2Ug\n" +
			"aW4gY29tcGxpYW5jZSBvZiB0aGUgcmVseWluZyBwYXJ0eSBvYmxpZ2F0aW9ucy4wNQYDVR0fBC4w\n" +
			"LDAqoCigJoYkaHR0cDovL2NybC5zdGFydHNzbC5jb20vY3J0MS1jcmwuY3JsMIGOBggrBgEFBQcB\n" +
			"AQSBgTB/MDkGCCsGAQUFBzABhi1odHRwOi8vb2NzcC5zdGFydHNzbC5jb20vc3ViL2NsYXNzMS9z\n" +
			"ZXJ2ZXIvY2EwQgYIKwYBBQUHMAKGNmh0dHA6Ly9haWEuc3RhcnRzc2wuY29tL2NlcnRzL3N1Yi5j\n" +
			"bGFzczEuc2VydmVyLmNhLmNydDAjBgNVHRIEHDAahhhodHRwOi8vd3d3LnN0YXJ0c3NsLmNvbS8w\n" +
			"DQYJKoZIhvcNAQELBQADggEBAF/t9Bc14BV0OwXcFf4Bs8y+p1AdbMqualCvLzjS95Z9HbPGcbRl\n" +
			"W76XwaM7iFE1R4mR1lGBQsacbBHOCNeZURYWGAG5c/yqhqCmWCzVJxM88AhCzkEv98uKa3IqE1zY\n" +
			"lOpYn4cMVqpPgg47QXqUfQlRoh21UTTORgiHEUY+JYNIlIXLoHtHVR0886+pIAq5fFrCwMHF45Df\n" +
			"r8tuTASazhYJUlOiGQTVv5p8Kg1wJ0ftMs9xJpThcnpEWrngmnNH/8H05rvJ9dEHkpnAU4mL46Bb\n" +
			"rmQe3oNoGE5EISL9KGVUMeS9wcR2kx+VmGhnAh7kjn5KuEidgfajS3XlcJ5o9t0=\n" +
			"-----END CERTIFICATE-----";


	private OKHttpManager() {
		mClient = new OkHttpClient();
		//SSLSocketFactory sslSocketFactory = SSLUtil.getSSLSocketFactory(getAssets().open(CERT_FILENAME));
		//SSLSocketFactory sslSocketFactory = SSLUtil.getSSLSocketFactory(new Buffer().writeUtf8(CERT).inputStream());
		//mClient.setSslSocketFactory(sslSocketFactory);
	}

	public static OKHttpManager getInstance() {
		if (instance == null) {
			synchronized (OKHttpManager.class) {
				if (instance == null) {
					instance = new OKHttpManager();
				}
			}
		}
		return instance;
	}

	/**
	 * 执行请求
	 * 
	 * @param taskId
	 * @param request
	 * @param watcher
	 */
	private void excuteRequest(OkHttpClient client, final int taskId, Request request, final TaskWatcher watcher,final boolean isReadCache) {
		LogUtil.d(TAG, request.urlString());
		if (watcher != null) {
			watcher.onStart(taskId);
		}

		final String cacheKey=String.valueOf(request.urlString().hashCode());
		if(isReadCache){
			String strCache=ACache.get(AppExt.getApplication()).getAsString(cacheKey);
			if(strCache!=null && !JsonResult.BLANK_RESULT.equalsIgnoreCase(strCache)){
				LogUtil.d(TAG,"readCache"+ strCache);
				if (watcher != null) {
					watcher.sendMessage(watcher.obtainMessage(taskId, 0, 0,strCache));
					return;
				}
			}else{

			}
		}
		//首先检测下网络是否连接,若没有配置存缓存，无网络则直接不处理
		if(!WifiNetUtils.isNetworkConnected(AppExt.getApplication())){
			Toast.makeText(AppExt.getApplication(), R.string.nowifi,Toast.LENGTH_SHORT).show();
			return ;
		}
		Call call = client.newCall(request);
		call.enqueue(new Callback() {

			@Override
			public void onResponse(Response response) throws IOException {
				if (response.isSuccessful()) {
					String resp=response.body().string();
					LogUtil.d(TAG, resp);
					if (watcher != null) {
						watcher.sendMessage(watcher.obtainMessage(taskId, 0, 0,resp));
						if(isReadCache) {
							if(!JsonResult.BLANK_RESULT.equalsIgnoreCase(resp)) {
								ACache.get(AppExt.getApplication()).put(cacheKey, resp);
								LogUtil.d(TAG, "putCache" + resp);
							}
						}
					}
				} else {
					LogUtil.d(TAG, response.code() +":" + response.message());
					if (watcher != null) {
						watcher.sendMessage(watcher.obtainMessage(-taskId, response.code(), 0, response.message()));
					}
				}
			}

			@Override
			public void onFailure(Request request, IOException e) {
				if (watcher != null) {
					watcher.sendMessage(watcher.obtainMessage(-taskId, 0, 0, e.getMessage()));
				}
			}
		});
	}

	private void excuteRequest(OkHttpClient client, final int taskId, Request request, final TaskWatcher watcher) {
		LogUtil.d(TAG, request.urlString());
		if (watcher != null) {
			watcher.onStart(taskId);
		}
		Call call = client.newCall(request);
		call.enqueue(new Callback() {

			@Override
			public void onResponse(Response response) throws IOException {
				if (response.isSuccessful()) {
					String resp=response.body().string();
					LogUtil.d(TAG, resp);
					if (watcher != null) {
						watcher.sendMessage(watcher.obtainMessage(taskId, 0, 0,resp));
					}
				} else {
					LogUtil.d(TAG, response.code() +":" + response.message());
					if (watcher != null) {
						watcher.sendMessage(watcher.obtainMessage(-taskId, response.code(), 0, response.message()));
					}
				}
			}

			@Override
			public void onFailure(Request request, IOException e) {
				if (watcher != null) {
					watcher.sendMessage(watcher.obtainMessage(-taskId, 0, 0, e.getMessage()));
				}
			}
		});
	}

	/**
	 * 拼接url
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	private String spellURL(String url, Map<String, String> params) {
		if (params != null && !params.isEmpty()) {
			StringBuffer sb = new StringBuffer(url).append("?");
			for (Entry<String, String> entry : params.entrySet()) {
				sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
			url = sb.deleteCharAt(sb.length() - 1).toString();
		}
		return url;
	}

	/**
	 * 构建Post请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	private Request buildRequest(String url, HashMap<String, String> params) {
		FormEncodingBuilder builder = new FormEncodingBuilder();
		for (Entry<String, String> param : params.entrySet()) {
			builder.add(param.getKey(), param.getValue());
		}
		return new Request.Builder().url(url).addHeader("Content-Type","application/json")
				.addHeader("X-Bmob-Application-Id", BmobConst.APPLICATION_ID)
				.addHeader("X-Bmob-REST-API-Key",BmobConst.API_KEY)
				.post(builder.build()).build();
	}

	private Request buildRequest(String url,  RequestBody body) {
		return new Request.Builder().url(url).addHeader("Content-Type","application/json")
				.addHeader("X-Bmob-Application-Id", BmobConst.APPLICATION_ID)
				.addHeader("X-Bmob-REST-API-Key",BmobConst.API_KEY)
				.post(body).build();
	}

	/**
	 * 构建Get请求
	 * 
	 * @param url
	 * @return
	 */
	private Request buildRequest(String url) {
		//return new Request.Builder().url(url).build();
		return new Request.Builder().url(url).addHeader("Content-Type","application/json")
				.addHeader("X-Bmob-Application-Id", BmobConst.APPLICATION_ID)
				.addHeader("X-Bmob-REST-API-Key",BmobConst.API_KEY).build();
	}

	/**
	 * get 请求
	 * 
	 * @param taskId
	 * @param url
	 * @param watcher
	 */
	public void doGetRequest(int taskId, String url, TaskWatcher watcher) {
		doGetRequest(taskId, url, null, watcher,false);
	}

	public void doGetRequest(int taskId, String url, TaskWatcher watcher,boolean isReadCache) {
		doGetRequest(taskId, url, null, watcher,isReadCache);
	}


	/**
	 * get 请求 带参数
	 * 
	 * @param taskId
	 * @param url
	 * @param params
	 * @param watcher
	 */
	public void doGetRequest(int taskId, String url, HashMap<String, String> params, final TaskWatcher watcher) {
		if (TextUtils.isEmpty(url)) {
			return;
		}
		url = spellURL(url, params);
		excuteRequest(mClient, taskId, buildRequest(url), watcher);
	}

	public void doGetRequest(int taskId, String url, HashMap<String, String> params, final TaskWatcher watcher,boolean isReadCache) {
		if (TextUtils.isEmpty(url)) {
			return;
		}
		url = spellURL(url, params);

		excuteRequest(mClient, taskId, buildRequest(url), watcher,isReadCache);
	}

	/**
	 * post 请求
	 * 
	 * @param url
	 * @param params
	 * @param watcher
	 */
	public void doPostRequest(int taskId, String url, HashMap<String, String> params, final TaskWatcher watcher) {
		if (TextUtils.isEmpty(url)) {
			return;
		}
		if (params == null || params.isEmpty()) {
			return;
		}
		excuteRequest(mClient, taskId, buildRequest(url, params), watcher);
	}

	/**
	 * post 请求
	 *
	 * @param url
	 * @param body
	 * @param watcher
	 */
	public void doPostRequest(int taskId, String url,  RequestBody body, final TaskWatcher watcher) {
		if (TextUtils.isEmpty(url)) {
			return;
		}
		if (body == null) {
			return;
		}
		excuteRequest(mClient, taskId, buildRequest(url, body), watcher);
	}

	/**
	 * 下载文件
	 */
	public void doDownloadFile(int taskId, String url, final TaskWatcher watcher, final OKHttpProgressListener listener) {
		OkHttpClient client = addProgressResponseListener(mClient, listener);
		excuteRequest(client, taskId, buildRequest(url), watcher);
	}

	/**
	 * 上传文件
	 */
	public void doUploadFile(int taskId, String url, HashMap<String, String> params, final TaskWatcher watcher) {
	}

	private static OkHttpClient addProgressResponseListener(OkHttpClient client, final OKHttpProgressListener listener) {
		// 克隆 增加拦截器
		OkHttpClient clone = client.clone();
		clone.networkInterceptors().add(new Interceptor() {
			@Override
			public Response intercept(Chain chain) throws IOException {
				// 拦截包装响应体并返回
				Response response = chain.proceed(chain.request());
				return response.newBuilder().body(new OKHttpProgressResponseBody(response.body(), listener)).build();
			}
		});
		return clone;
	}

	/**
	 * 包装请求体用于上传文件的回调
	 * 
	 * @param requestBody
	 *            请求体RequestBody
	 * @param listener
	 *            进度回调接口
	 * @return 包装后的进度回调请求体
	 */
	private static OKHttpProgressRequestBody addProgressRequestListener(RequestBody requestBody,
			OKHttpProgressListener listener) {
		return new OKHttpProgressRequestBody(requestBody, listener);
	}

}
