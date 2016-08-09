package com.banksoft.XinChengShop.ui;

import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.adapter.ImageViewPagerAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.ScoreDao;
import com.banksoft.XinChengShop.entity.ScoreProduct;
import com.banksoft.XinChengShop.http.HttpUtils;
import com.banksoft.XinChengShop.model.ScoreProductData;
import com.banksoft.XinChengShop.model.ScoreProductListData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.utils.TimeUtils;
import com.banksoft.XinChengShop.widget.MyProgressDialog;
import com.banksoft.XinChengShop.widget.MyWebView;
import com.banksoft.XinChengShop.widget.viewpagerindicator.TabPageIndicator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by harry_robin on 16/1/23.
 */
public class ScoreProductInfoActivity extends XCBaseActivity implements View.OnClickListener{
    private TextView title;
    private ImageView back;
    private TextView name, num, time, score, price;
    private MyWebView myWebView;
    private MyProgressDialog myProgressDialog;
    private ScoreProduct scoreProduct;
    private ScoreDao scoreDao;
    private String CACHE_DIRFILE = "/WEBCACHE";
    private String cachePath;
    private ViewPager mViewPager;
    private TabPageIndicator mIndicator;

    private ImageViewPagerAdapter imageViewPagerAdapter;
    private LinkedList imageView = new LinkedList();
    private ImageLoader mImageLoader;

    @Override
    protected void initContentView() {
        setContentView(R.layout.score_product_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
        name = (TextView) findViewById(R.id.name);
        num = (TextView) findViewById(R.id.num);
        time = (TextView) findViewById(R.id.time);
        score = (TextView) findViewById(R.id.score);
        price = (TextView) findViewById(R.id.good_mark_price);
        myWebView = (MyWebView) findViewById(R.id.webView);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mIndicator = (TabPageIndicator) findViewById(R.id.tab_page_indicator);
    }

    @Override
    protected void initData() {
        title.setText(R.string.score_change);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        scoreProduct = (ScoreProduct) getIntent().getSerializableExtra(IntentFlag.DATA);
        cachePath = getFilesDir().getAbsolutePath() +getClass().getSimpleName()+ File.separator+ CACHE_DIRFILE;
        setmWebViewSettings();
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
    }

    @Override
    protected void initOperate() {
        if (scoreDao == null) {
            scoreDao = new ScoreDao(mContext);
        }
        new MyTask().execute(scoreDao);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back_button:
                finish();
                break;
        }
    }

    /**
     *
     */
    private class MyTask extends AsyncTask<ScoreDao, String, ScoreProductData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (myProgressDialog == null) {
                myProgressDialog = new MyProgressDialog(ScoreProductInfoActivity.this);
            }
            myProgressDialog.showDialog(R.string.please_wait);
        }

        @Override
        protected ScoreProductData doInBackground(ScoreDao... params) {
            return params[0].getScoreProductData(scoreProduct.getId());
        }

        @Override
        protected void onPostExecute(ScoreProductData scoreProductData) {
            super.onPostExecute(scoreProductData);
            myProgressDialog.dismiss();
            if (scoreProductData != null) {
                if (scoreProductData.isSuccess()) {
                    setScoreView(scoreProductData.getData());
                } else {
                    alert(scoreProductData.getMsg().toString());
                }
            } else {
                alert(R.string.net_error);
            }
        }
    }

    private void setmWebViewSettings() {
        WebSettings webSettings = myWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

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
        webSettings.setDatabasePath(cachePath);
        webSettings.setAppCachePath(cachePath);
        webSettings.setAppCacheEnabled(true);
    }

    private void setScoreView(ScoreProduct scoreProduct) {
        name.setText(scoreProduct.getName());
        score.setText(String.valueOf(scoreProduct.getScore()));
        price.setText(String.valueOf(scoreProduct.getPrice()));
        String[] images = new String[0];
        if(scoreProduct.getImageFile() != null&&scoreProduct.getImageFile().isEmpty()){
            images = scoreProduct.getImageFile().split("\\|");
        }
        for (String image:images){
            ImageView view = (ImageView) LayoutInflater.from(mContext).inflate(R.layout.banner_image_cell,null);
            mImageLoader.displayImage(ControlUrl.BASE_URL+image,view, XCApplication.options);
            imageView.add(view);
        }
        if(imageViewPagerAdapter == null){
            imageViewPagerAdapter = new ImageViewPagerAdapter(imageView);
        }
        mViewPager.setAdapter(imageViewPagerAdapter);
        mIndicator.setViewPager(mViewPager);
        if (scoreProduct.isAgreeNumber()) {
            num.setVisibility(View.VISIBLE);
            num.setText("每个ID限制购买" + scoreProduct.getNum());
        } else {
            num.setVisibility(View.GONE);
        }
        if (scoreProduct.isAgreeBuyTime()) {
            time.setVisibility(View.VISIBLE);
            time.setText("购买时间：" + TimeUtils.getTimeStr(scoreProduct.getStartTime(), TimeUtils.TimeType.MINUTE) + TimeUtils.getTimeStr(scoreProduct.getEndTime(), TimeUtils.TimeType.MINUTE));
        } else {
            time.setVisibility(View.GONE);
        }

    }
}
