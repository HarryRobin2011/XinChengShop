package com.banksoft.XinChengShop.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.fragment.ExpressBillListFragment;

/**
 * Created by Robin on 2016/4/23.
 */
public class ExpressBillListActivity extends XCBaseActivity implements View.OnClickListener {
    private TextView title;
    private ImageView back;
    private OperaType currentOperaType;
    private ExpressBillListFragment expressBillListFragment;

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
        currentOperaType = (OperaType) getIntent().getSerializableExtra(IntentFlag.TYPE);
        title.setText(currentOperaType.getName());
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
    }

    @Override
    protected void initOperate() {
        ExpressBillListFragment expressBillListFragment = new ExpressBillListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.TYPE,currentOperaType);
        expressBillListFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.content,expressBillListFragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back_button:
                finish();
                break;
        }
    }

    public static enum OperaType {
        MY_DISPATH_ORDER("我派送的订单"),
        NEW_DISPATCH_ORDER("新的派送订单");

        private String name;

        OperaType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
