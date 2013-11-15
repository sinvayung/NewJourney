package com.newjourney.android.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.HttpAuthHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.newjourney.android.BaseActivity;

public class WebViewActivity extends BaseActivity {

	private final static int REQ_FILE_CHOOSE = 1;

	private CustomWebChromeClient chromeClient = new CustomWebChromeClient();
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		webView = new WebView(this);
		setContentView(webView);
		
		String URL = "http://www.qq.com";

		//webView.addJavascriptInterface(new JSInvokeClass(handler), "android");
		// 修改
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		//支持放大缩小
		webSettings.setBuiltInZoomControls(true);
		webView.setInitialScale(100);

		webView.setWebViewClient(webViewClient);
		webView.setWebChromeClient(chromeClient);
		webView.loadUrl(URL);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
			webView.goBack();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == REQ_FILE_CHOOSE && resultCode == RESULT_OK) {
			if (intent != null) {
				Uri result = intent.getData();
				chromeClient.updateChooseResult(result);
			}
		}
	}

	public class CustomWebChromeClient extends WebChromeClient {

		private ValueCallback<Uri> chooseCallback;

		/**
		 * For Android 4.1.1+
		 * 
		 * @param uploadMsg
		 * @param acceptType
		 * @param capture
		 */
		public void openFileChooser(ValueCallback<Uri> callback, String acceptType, String capture) {
			openFileChooser(callback);
		}

		/**
		 * For Android 3.0+
		 * 
		 * @param uploadMsg
		 * @param acceptType
		 */
		public void openFileChooser(ValueCallback<Uri> callback, String acceptType) {
			openFileChooser(callback);
		}

		/**
		 * For Android 2.x
		 * 
		 * @param uploadMsg
		 */
		public void openFileChooser(ValueCallback<Uri> callback) {
			this.chooseCallback = callback;
			// 打开图片选择器
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.addCategory(Intent.CATEGORY_OPENABLE);
			intent.setType("image/*");
			startActivityForResult(Intent.createChooser(intent, "File Chooser"), REQ_FILE_CHOOSE);
		}

		/**
		 * 选定哪个文件等待上传 <br />
		 * 在Activity的onActivityResult()方法中调用
		 * 
		 * @param result
		 */
		public void updateChooseResult(Uri result) {
			chooseCallback.onReceiveValue(result);
			chooseCallback = null;
		}
	}

	WebViewClient webViewClient = new WebViewClient() {

		@Override
		public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
			// TODO 页面加载完成时要做的操作，如：显示进度条
			super.onPageStarted(view, url, favicon);
		};

		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO 页面加载完成时要做的操作，如：隐藏进度条
			super.onPageFinished(view, url);
		}

		@Override
		public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
			handler.proceed("test", "5179");
		}

	};

}
