package com.banksoft.XinChengShop.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

/**
 * Created by harry_robin on 16/2/21.
 */
public class AdvertisingActivity extends XCBaseActivity {
    private TextView time;
    private ImageView advertImage;
    private Bitmap advertBitmap;
    private int startTime = 3;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(startTime>1){
               startTime --;
               mHandler.sendEmptyMessageDelayed(0,1000);
            }else{
                Intent intent = new Intent(mContext,XCMainActivity.class);
                startActivity(intent);
            }
        }
    };
    @Override
    protected void initContentView() {
        setContentView(R.layout.advertising_layout);
    }

    @Override
    protected void initView() {
      time = (TextView) findViewById(R.id.time);
      advertImage = (ImageView) findViewById(R.id.image);
    }

    @Override
    protected void initData() {
      advertBitmap = getIntent().getParcelableExtra(IntentFlag.DATA);
    }

    @Override
    protected void initOperate() {
      advertImage.setImageBitmap(advertBitmap);
        mHandler.sendEmptyMessageDelayed(0,1000);// 开始倒计时

    }
}
