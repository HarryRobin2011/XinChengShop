package com.banksoft.XinChengShop.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.fragment.ShopProductAssessListFragment;

/**
 * Created by Robin on 2016/4/24.
 */
public class ShopProductAssessListActivity extends XCBaseActivity implements View.OnClickListener {
    private TextView title;
    private ImageView back;
    private String productId;
    private ShopProductAssessListFragment shopProductAssessListFragment;

    @Override
    protected void initContentView() {
        setContentView(R.layout.content_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);

    }

    @Override
    protected void initData() {
        title.setText(R.string.product_assess_title);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        productId = getIntent().getStringExtra(IntentFlag.PRODUCT_ID);
    }

    @Override
    protected void initOperate() {
        if (shopProductAssessListFragment == null) {
            shopProductAssessListFragment = new ShopProductAssessListFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(IntentFlag.PRODUCT_ID,productId);
        shopProductAssessListFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, shopProductAssessListFragment).commit();
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
