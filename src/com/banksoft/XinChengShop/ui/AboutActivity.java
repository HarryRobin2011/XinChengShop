package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;

/**
 * Created by harry_robin on 16/1/25.
 */
public class AboutActivity extends Activity implements View.OnClickListener {

    private TextView titleText;
    private TextView product_version;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        initView();
        initData();

    }

    private void initView() {
        titleText = (TextView) findViewById(R.id.titleText);
        product_version = (TextView) findViewById(R.id.product_version);
        back = (ImageView) findViewById(R.id.title_back_button);
    }

    private void initData() {
        titleText.setText(R.string.personal_about);
//        back.setVisibility(View.VISIBLE);
//        back.setOnClickListener(this);
        product_version.setText("版本号："+ getVersionName());
    }

    /**
     * 获取软件版本号
     * @return
     */
    private String getVersionName(){
        String versionName = null;
        try {
            versionName = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back_button:
                finish();
                break;
        }
    }
}
