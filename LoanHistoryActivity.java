package com.eastedge.microfinance.activitys;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.eastedge.microfinance.R;
import com.eastedge.microfinance.constant.Constants;
import com.eastedge.microfinance.engine.AppManager;
import com.eastedge.microfinance.engine.MicroFinaceApp;
import com.eastedge.microfinance.net.HttpConfig;
import com.eastedge.microfinance.utils.FileUtils;
import com.eastedge.microfinance.utils.LogUtils;
import com.eastedge.microfinance.utils.MyPreferences;
import com.eastedge.microfinance.utils.WebkitCookieManagerProxy;


@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("SetJavaScriptEnabled")
public class LoanHistoryActivity extends BaseActivity {

	private WebView webView;
	private Intent intent;
	private String cusID;
	private String title;
	private Button back_Btn;

	@Override
	protected void setConentView() {
		super.setConentView();
		setContentView(R.layout.activity_loan_history);
	}

	@Override
	protected void init() {
		super.init();
		TextView tv_title = (TextView) findViewById(R.id.tv_webview_title);
		getParam();
		// 标题
		tv_title.setText(title);

		back_Btn = (Button) findViewById(R.id.loan_history_back_but);

		webView = (WebView) findViewById(R.id.load_history_webview);
		WebSettings mWebSettings = webView.getSettings();
		// 使用javascript方法
		mWebSettings.setJavaScriptEnabled(true);
		 webView.loadUrl("file:///android_asset/loanHistory.html");
	}

	public void showImg(String url){ 
		System.out.println("test:"+url);    
		webView.loadUrl("file:///android_asset/"+url); 
		//页面缩放
//		WebSettings settings = webView.getSettings();
//		settings.setBuiltInZoomControls(true);
//		settings.setSupportZoom(true);  
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eastedge.microfinance.activitys.BaseActivity#processLogic()
	 */
	@Override
	protected void processLogic() {
		super.processLogic();
		back_Btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clearCache();
				finish();
			}

		});
	}

	/**
	 * 清除页面缓存
	 */
	private void clearCache() {
		webView.clearView();
		webView.clearCache(true);
		webView.clearFormData();
		webView.clearHistory();
	}

	/**
	 * 
	 */
	private void getParam() {
		intent = getIntent();
		title = intent.getStringExtra(Constants.INTENT_KEY_TITLE_TEXT);
		cusID = intent.getStringExtra(Constants.INTENT_KEY_CUSTOMER_ID);

		String param = String.format(
				"var Params = {\"cid\":\"%s\",\"host\":\"%s\"}", cusID,
				HttpConfig.HOST);
		FileUtils.writeUpdate("/Zhnx", "getParam.js", param.getBytes());
		LogUtils.logi("参数为：" + param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			clearCache();
			break;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onRestart() {
		if (AppManager.getAppManager().isOnStop()) {
			if (MicroFinaceApp.isStatus) {
				MyPreferences mysp = MyPreferences.getPrefer(this, "myconfig");
				String status = mysp.getString("isStartlock", "");
				Boolean b = false;
				if (status.equals("on")) {
					b = true;
				} else if (status.equals("off")) {
					b = false;
				}
				if (b) {
					startActivity(new Intent(this, DeblockActivity.class));
				}
			}
		}
		super.onRestart();
	}
}

