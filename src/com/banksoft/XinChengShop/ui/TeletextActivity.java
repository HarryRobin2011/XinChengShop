package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 14-6-30
 * Time: 下午3:37
 * To change this template use File | Settings | File Templates.
 * 产品图文界面
 */
public class TeletextActivity extends Activity {
    private WebView webView;
    private String goodsDescribe;
    private String title;
    private TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teletext_layout);
        goodsDescribe = (String) getIntent().getExtras().get("describe");
        title = (String) getIntent().getExtras().get("title");
        init();
        setWebView();
        titleText.setText(title);
        webView.loadDataWithBaseURL(null,goodsDescribe,"text/html","utf-8",null);
    }

    private void init() {
        webView = (WebView) findViewById(R.id.mWebView);
        titleText = (TextView) findViewById(R.id.titleText);
    }

    private void setWebView(){
        webView.setWebViewClient(new WebViewClient() { // 通过webView打开链接，不调用系统浏览器

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.setInitialScale(25);
        WebSettings infoWebSettings = webView.getSettings();
        infoWebSettings.setJavaScriptEnabled(true);
        infoWebSettings.setBuiltInZoomControls(true);
        infoWebSettings.setSupportZoom(true);
        infoWebSettings.setBlockNetworkImage(false);

        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
