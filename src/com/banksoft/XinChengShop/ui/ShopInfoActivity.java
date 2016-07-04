package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.config.MapFlag;
import com.banksoft.XinChengShop.dao.ShopInfoDao;
import com.banksoft.XinChengShop.entity.ShopVO;
import com.banksoft.XinChengShop.http.HttpUtils;
import com.banksoft.XinChengShop.model.ShopInfoData;
import com.banksoft.XinChengShop.model.ShopProductListData;
import com.banksoft.XinChengShop.model.base.BaseData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.utils.CommonUtil;
import com.banksoft.XinChengShop.utils.JSONHelper;

import java.util.HashMap;

/**
 * Created by harry_robin on 15/11/19.
 */
public class ShopInfoActivity extends XCBaseActivity implements View.OnClickListener{
    private String CACHE_DIRFILE = "/WEBCACHE";
    private WebView mWebView;
    private TextView title;
    private ProgressBar mProgressBar;
    private ShopInfoDao shopInfoDao;
    private HashMap dataMap;

    private ShopVO shopVO;

    private String shopId;
    private TextView shopCategory,shopIntroduction,shopContact;
    private ImageView back;

    private TextView nullMessage;
    private LinearLayout toolLayout;
    private FrameLayout nullPagerLayout;

    @Override
    protected void initContentView() {
        setContentView(R.layout.xc_shop_info_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        mWebView = (WebView) findViewById(R.id.mWebView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        shopCategory = (TextView) findViewById(R.id.category);
        shopIntroduction = (TextView) findViewById(R.id.introduction);
        shopContact = (TextView) findViewById(R.id.contact);
        nullPagerLayout = (FrameLayout) findViewById(R.id.null_pager);
        toolLayout = (LinearLayout) findViewById(R.id.tool_layout);
        back = (ImageView) findViewById(R.id.title_back_button);
    }

    @Override
    protected void initData() {
        title.setText(R.string.shop_details);
        shopId = getIntent().getStringExtra(IntentFlag.SHOP_ID);
        setmWebViewSettings();
        mWebView.addJavascriptInterface(new JavascriptInterface(), "android");
    }

    @Override
    protected void initOperate() {
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                    mWebView.loadUrl("javascript:getShopData(" + JSONHelper.toJSONString(dataMap) + ")");
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        if (shopInfoDao == null) {
            shopInfoDao = new ShopInfoDao(mContext);
        }
        new MyThread().execute(shopInfoDao);
        shopCategory.setOnClickListener(this);
        shopIntroduction.setOnClickListener(this);
        shopContact.setOnClickListener(this);
    }


    private void setmWebViewSettings() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBlockNetworkImage(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSupportZoom(false);
        webSettings.setAllowFileAccess(true);
        if (HttpUtils.isNetworkAvailable(getApplicationContext())) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDatabasePath(CACHE_DIRFILE);
        webSettings.setAppCachePath(CACHE_DIRFILE);
        webSettings.setAppCacheEnabled(true);

    }


    /**
     * Js 调用
     */
    public class JavascriptInterface {
        /**
         * 跳转店铺
         */
        @android.webkit.JavascriptInterface
        public void startProductActivity(String productID) {
            Intent intent = new Intent(mContext, ShopProductInfoActivity.class);
            intent.putExtra(IntentFlag.PRODUCT_ID,productID);
            startActivity(intent);
        }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.category:
                Intent intent = new Intent(mContext,ShopCategoryActivity.class);
                startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                break;
            case R.id.introduction:
                Intent infoIntent = new Intent(mContext,ShopIntroductionActivity.class);
                startActivity(infoIntent);
                break;
            case R.id.contact://调用打电话
                if(shopVO != null){
                    CommonUtil.callPhone(mContext,shopVO.getTelephone());
                }
                break;
            case R.id.title_back_button:
                finish();
                break;
        }
    }

    private class MyThread extends AsyncTask<ShopInfoDao, String, HashMap<String, BaseData>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected HashMap<String, BaseData> doInBackground(ShopInfoDao... params) {
            ShopInfoData shopInfoData = params[0].getShopInfoData(shopId, true);
            ShopProductListData shopProductListData = params[0].getShopProductListData(shopId, true);
            HashMap dataMap = new HashMap<>();
            dataMap.put(MapFlag.DATA_0, shopInfoData);
            dataMap.put(MapFlag.DATA_1, shopProductListData);
            return dataMap;
        }

        @Override
        protected void onPostExecute(HashMap<String, BaseData> stringBaseDataHashMap) {
            super.onPostExecute(stringBaseDataHashMap);
            mProgressBar.setVisibility(View.GONE);
            if (stringBaseDataHashMap != null) {
                ShopInfoData shopInfoData = (ShopInfoData) stringBaseDataHashMap.get(MapFlag.DATA_0);
                ShopProductListData productListData = (ShopProductListData) stringBaseDataHashMap.get(MapFlag.DATA_1);
                if (productListData != null && shopInfoData != null) {
                    shopVO = shopInfoData.getData();
                    dataMap = stringBaseDataHashMap;
                    mWebView.loadUrl("file:///android_asset/goods/shop.html");
                } else {
                    showWarning(R.string.net_error);
                }
            }
        }
    }
}
