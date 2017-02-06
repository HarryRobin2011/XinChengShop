package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.WelcomeDao;
import com.banksoft.XinChengShop.entity.Advertisement;
import com.banksoft.XinChengShop.model.AdvertisementData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

import java.util.Random;

/**
 * Created by Robin on 2014/11/9.
 */
public class WelcomeActivity extends XCBaseActivity implements View.OnClickListener {
    private WelcomeDao welcomeDao;
    private RelativeLayout advertisementLayout;
    private ImageView advertisement;
    private TextView time;
    private String baseDes = "跳过";
    private int timeLong = 5;
    private MyTask myTask;
    private boolean isOut = false;
//    private ImageView imageView;
//    private TextView describe;
//    private LinearLayout welcomeLayout;
    // private TextView describe;

    public final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    loaded();
                    break;
                case 1:// 弹出广告页
                    if (msg != null && Integer.parseInt(msg.obj.toString()) >= 0) {
                        Message msg1 = mHandler.obtainMessage();
                        msg1.obj = Integer.parseInt(msg.obj.toString()) - 1;
                        msg1.what = 1;
                        time.setText("跳过（" + msg.obj.toString() + "s)");
                        mHandler.sendMessageDelayed(msg1, 1000);
                    } else {
                        if (!myTask.isCancelled() && isOut == false) {
                            startMain();
                        }
                    }
                    break;
                case 2:
                    startMain();
                    break;
            }
        }
    };


    @Override
    protected void initContentView() {
        setContentView(R.layout.welcome_layout);
        advertisementLayout = (RelativeLayout) findViewById(R.id.advertisement_layout);
        time = (TextView) findViewById(R.id.time);
        advertisement = (ImageView) findViewById(R.id.advertisement);
        MobclickAgent.updateOnlineConfig(getApplicationContext());
        MobclickAgent.setDebugMode(true);


    }

    @Override
    protected void initView() {
        //  imageView = (ImageView) findViewById(R.id.image);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Activity.RESULT_FIRST_USER){
            if(resultCode == Activity.RESULT_OK){
                advertisementLayout.setVisibility(View.GONE);
                mHandler.sendEmptyMessageDelayed(2,1000);
            }
        }
    }

    @Override
    protected void initOperate() {
        mHandler.sendEmptyMessageDelayed(0, 2000);
        advertisement.setOnClickListener(this);
        time.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.time:
               myTask.cancel(true);
                startMain();
                break;
            case R.id.advertisement:
                this.isOut = true;
                Advertisement data = (Advertisement) v.getTag();
                Intent advertise = new Intent(mContext, WebViewLoadActivity.class);
                advertise.putExtra(IntentFlag.URL, data.getUrl());
                animation = false;
                startActivityForResult(advertise, Activity.RESULT_FIRST_USER);
                break;
        }
    }

    private class MyTask extends AsyncTask<WelcomeDao, String, AdvertisementData> {

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected AdvertisementData doInBackground(WelcomeDao... params) {
            return params[0].getAdvertisementData(true);
        }

        @Override
        protected void onPostExecute(AdvertisementData advertisementData) {
            super.onPostExecute(advertisementData);
            if (advertisementData != null && advertisementData.getData() != null) {
                Random random = new Random();
                int position = random.nextInt(advertisementData.getData().size());
                Glide.with(mContext)
                        .load(ControlUrl.BASE_URL + advertisementData.getData().get(position).getImageUrl()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        advertisementLayout.setVisibility(View.VISIBLE);
                        advertisement.setTag(advertisementData.getData().get(position));
                        advertisement.setOnClickListener(WelcomeActivity.this);
                        Message msg = mHandler.obtainMessage();
                        msg.what = 1;
                        msg.obj = timeLong;
                        mHandler.sendMessage(msg);
                        advertisement.setImageBitmap(bitmap);
                    }
                });
            } else {
                startMain();
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void loaded() {
        if (!isGuide()) {
            Intent intent = new Intent(getApplicationContext(), GuideActivity.class);
            startActivity(intent);
            finish();
        } else {
            if (welcomeDao == null) {
                welcomeDao = new WelcomeDao(this);
            }
            myTask = new MyTask();
            myTask.execute(welcomeDao);
        }
    }

    private void startMain() {
        Intent intent = new Intent(getApplicationContext(), XCMainActivity.class);
        animation = false;
        startActivity(intent);
        finish();
    }
}
