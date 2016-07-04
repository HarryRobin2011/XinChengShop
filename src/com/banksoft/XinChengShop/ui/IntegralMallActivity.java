package com.banksoft.XinChengShop.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.fragment.IntegralMallListFragment;
import com.banksoft.XinChengShop.ui.fragment.ProductListFragment;

/**
 * Created by harry_robin on 15/11/25.
 */
public class IntegralMallActivity extends XCBaseActivity {
    private TextView title;
    private String titleName;
    private LinearLayout searchLayout;
    private IntegralMallListFragment integralMallListFragment;

    @Override
    protected void initContentView() {
        setContentView(R.layout.content_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        searchLayout = (LinearLayout) findViewById(R.id.search_layout);
    }

    @Override
    protected void initData() {

        titleName = getIntent().getStringExtra(IntentFlag.TITLE);
        title.setText(titleName);
        searchLayout.setVisibility(View.GONE);
    }

    @Override
    protected void initOperate() {
        if (integralMallListFragment == null) {
            integralMallListFragment = new IntegralMallListFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.content, integralMallListFragment).commit();
    }
}
