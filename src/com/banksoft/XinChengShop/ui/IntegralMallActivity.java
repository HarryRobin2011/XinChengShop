package com.banksoft.XinChengShop.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.fragment.IntegralMallListFragment;

/**
 * Created by harry_robin on 15/11/25.
 */
public class IntegralMallActivity extends XCBaseActivity implements View.OnClickListener {
    private TextView title;
    private String titleName;
    private ImageView back;
    private LinearLayout searchLayout;
    private IntegralMallListFragment integralMallListFragment;

    @Override
    protected void initContentView() {
        setContentView(R.layout.content_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
        searchLayout = (LinearLayout) findViewById(R.id.search_layout);
    }

    @Override
    protected void initData() {
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        title.setText(R.string.integral_mall);
        searchLayout.setVisibility(View.GONE);
    }

    @Override
    protected void initOperate() {
        if (integralMallListFragment == null) {
            integralMallListFragment = new IntegralMallListFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.content, integralMallListFragment).commit();
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
