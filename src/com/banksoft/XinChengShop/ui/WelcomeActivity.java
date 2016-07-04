package com.banksoft.XinChengShop.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Robin on 2014/11/9.
 */
public class WelcomeActivity extends XCBaseActivity {
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
            }
        }
    };


    @Override
    protected void initContentView() {
        setContentView(R.layout.welcome_layout);
//        describe = (TextView) findViewById(R.id.describe);
//        welcomeLayout = (LinearLayout) findViewById(R.id.welcomeLayout);
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
    protected void initOperate() {
//        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
//        alphaAnimation.setDuration(2000);
//    //    imageView.setAnimation(alphaAnimation);
//        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                 mHandler.sendEmptyMessage(0);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });

        mHandler.sendEmptyMessageDelayed(0,2000);
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
        if(!isGuide()){
            Intent intent = new Intent(getApplicationContext(), GuideActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(getApplicationContext(), XCMainActivity.class);
            startActivity(intent);
        }
    }
}
