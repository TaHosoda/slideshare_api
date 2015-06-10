package com.example.hsd.slideshare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by hsd on 2015/06/06.
 */
public class ViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        Log.i("ViewActivity", "url=" + url);

        WebView myWebView = (WebView)findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl(url);
    }
}
