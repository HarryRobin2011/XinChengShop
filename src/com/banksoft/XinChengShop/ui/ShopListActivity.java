package com.banksoft.XinChengShop.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.fragment.ShopListFragment;

/**
 * Created by harry_robin on 15/12/2.
 */
public class ShopListActivity extends XCBaseActivity implements View.OnClickListener{
    private ShopListFragment shopListFragment;
    private TextView title;
    private ImageView back;
    private String titleStr;
    private String shopType;
    private String no;
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
        titleStr = getIntent().getStringExtra(IntentFlag.TITLE);
        shopType = getIntent().getStringExtra(IntentFlag.SHOP_TYPE);
        no = getIntent().getStringExtra(IntentFlag.NO);
        title.setText(titleStr);
    }

    @Override
    protected void initOperate() {
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        if(shopListFragment == null){
            shopListFragment = new ShopListFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.SHOP_SERVER_TYPE,shopType);
        bundle.putSerializable(IntentFlag.NO,no);
        shopListFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.content,shopListFragment).commit();
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
