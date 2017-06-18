package com.mm.mmcalendar.common;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings.RenderPriority;
import com.tencent.smtt.export.external.interfaces.QuotaUpdater;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class BaseH5Activity extends BaseActivity {

	protected WebView myWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		myWebView = new WebView(this);
		initCfgWebView();


	}

	private void initCfgWebView() {

		//性能设置开启
		// 在条件满足时开启硬件加速
		try
		{
		if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11)
		{
		getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
		android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
		}
		}
		catch (Exception e)
		{
		e.printStackTrace();
		}
		myWebView.getSettings().setJavaScriptEnabled(true);
		myWebView.getSettings().setRenderPriority(RenderPriority.HIGH);
		//把图片加载放在最后来加载渲染 测试启用后显示不了图片
		//myWebView.getSettings().setBlockNetworkImage(true);
		 // 设置可以使用localStorage
		myWebView.getSettings().setDomStorageEnabled(true);
		 // 应用可以有数据库
		myWebView.getSettings().setDatabaseEnabled(true);
		String databasePath = this.getApplicationContext()
				.getDir("database", Context.MODE_PRIVATE).getPath();
		myWebView.getSettings().setDatabasePath(databasePath);
		
		// 应用可以有缓存
		myWebView.getSettings().setAppCacheEnabled(true);            
        String appCaceDir =this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        myWebView.getSettings().setAppCachePath(appCaceDir);
		
		myWebView.setWebChromeClient(new WebChromeClient() {


			public void onExceededDatabaseQuota(String url,
					String databaseIdentifier, long quota,
					long estimatedDatabaseSize, long totalQuota,
					QuotaUpdater quotaUpdater) {
				quotaUpdater.updateQuota(5 * 1024 * 1024);
			}

		});

		// Android中点击一个链接，默认是调用应用程序来启动，因此WebView需要代为处理这个动作 通过WebViewClient
		myWebView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
			}

		});
		//webview没有提供文件下载功能，但可以使用安装好的浏览器下载，但前提需启用下载
		/*myWebView.setDownloadListener(new DownloadListener(){

			@Override
			public void onDownloadStart(String url, String userAgent, String contentDisposition,
					String mimetype, long contentLength) {
				
				Uri uri=Uri.parse(url);
				startActivity(new Intent(Intent.ACTION_VIEW,uri));
			}
			
		});*/
		
		//自定义js alert方法
		myWebView.setWebChromeClient(new WebChromeClient(){

			@Override  
		    public boolean onJsAlert(WebView view, String url, String message,  
		    		com.tencent.smtt.export.external.interfaces.JsResult result) {  
		          
				final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
				 
                builder.setTitle("提示信息")
                .setMessage(message)
                .setPositiveButton("确定", null)
                .setCancelable(false)
                .create()
                .show();
                result.confirm();
                return true;
		    }
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
			myWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
