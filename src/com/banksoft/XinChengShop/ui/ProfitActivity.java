package com.banksoft.XinChengShop.ui;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

/**
 * Created by harry_robin on 16/1/25.
 */
public class ProfitActivity extends XCBaseActivity {
    private FrameLayout framentLayout;
    private TextView title;
    @Override
    protected void initContentView() {
        setContentView(R.layout.profit_layout);
    }

    @Override
    protected void initView() {
        framentLayout = (FrameLayout) findViewById(R.id.null_pager);
        title = (TextView) findViewById(R.id.titleText);
    }

    @Override
    protected void initData() {
        title.setText(R.string.earnings_information);
       framentLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initOperate() {

    }
}
