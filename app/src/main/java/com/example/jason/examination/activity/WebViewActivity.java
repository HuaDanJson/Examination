package com.example.jason.examination.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.jason.examination.R;
import com.example.jason.examination.base.BaseActivity;
import com.example.jason.examination.constants.ConstKey;

//加载WebView的共用Activity
public class WebViewActivity extends BaseActivity {

    private WebView wvWebViewActivity;

    private String title;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initVariables();
        initWebView();
    }

    public void initVariables() {
        title = getIntent().getStringExtra(ConstKey.INTENT_KEY_TO_WEB_VIEW_ACTIVITY_TITLE);
        url = getIntent().getStringExtra(ConstKey.INTENT_KEY_TO_WEB_VIEW_ACTIVITY_URL);
    }

    public void initWebView() {
        setTitle(title);
        wvWebViewActivity = (WebView) findViewById(R.id.wvWebViewActivity);
        wvWebViewActivity.loadUrl(url);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        wvWebViewActivity.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        //启用支持javascript
        WebSettings settings = wvWebViewActivity.getSettings();
        settings.setJavaScriptEnabled(true);
        //使用缓存
        wvWebViewActivity.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }
}
