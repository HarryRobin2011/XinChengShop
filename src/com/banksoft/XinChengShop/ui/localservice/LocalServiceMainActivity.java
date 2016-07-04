package com.banksoft.XinChengShop.ui.localservice;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.type.ShopType;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.fragment.localservice.LocalServiceProductFragment;

/**
 * Created by admin on 2016/6/21.
 */
public class LocalServiceMainActivity extends XCBaseActivity implements View.OnClickListener {
    private LocalServiceProductFragment localServiceProductFragment;
    private TextView title;
    private ImageView back;

    @Override
    protected void initContentView() {
        setContentView(R.layout.local_service_main_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
    }

    @Override
    protected void initData() {
        title.setText(R.string.local_service_title);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
    }

    @Override
    protected void initOperate() {
        if (localServiceProductFragment == null) {
            localServiceProductFragment = new LocalServiceProductFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.SHOP_SERVER_TYPE, ShopType.SHOP_LOCAL.toString());
        localServiceProductFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, localServiceProductFragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back_button:
                finish();
                break;
        }
    }


}
