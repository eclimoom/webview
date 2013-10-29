package com.eastedge.microfinance.test;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.eastedge.microfinance.activitys.LoanHistoryActivity;

public class WebAppInterface {
	 Context mContext;
	  /** Instantiate the interface and set the context */
	    public WebAppInterface(Context c) {
	        mContext = c;
	    }

	    /** Show a toast from the web page */
	    @JavascriptInterface
	    public void showToast(String toast) {  	 
	        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
	    } 
	    
	    @JavascriptInterface   /* 新版本修妖加入@JavascriptInterface*/
	    public void showImg(String url) {
	    	((LoanHistoryActivity) mContext).showImg(url);  
	    	///WebView view = mContext.findViewById(R.id.load_history_webview);
	    	//WebView view = findViewById(R.id.load_history_webview).loadUrl("file:///android_asset/imageList.html");
	    }
}
