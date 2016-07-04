package com.banksoft.XinChengShop.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.fragment.XCShopCartFragment;

/**
 * Created by harry_robin on 15/11/26.
 */
public class ShopCartProductActivity extends XCBaseActivity implements View.OnClickListener{
    private XCShopCartFragment xcShopCartFragment;
    private ImageView bacck;
    private TextView title;
    private RelativeLayout titleLayout;

    @Override
    protected void initContentView() {
        setContentView(R.layout.content_layout);
    }

    @Override
    protected void initView() {
        bacck = (ImageView) findViewById(R.id.title_back_button);
        title = (TextView) findViewById(R.id.titleText);
        titleLayout = (RelativeLayout) findViewById(R.id.title_layout);
    }

    @Override
    protected void initData() {
        bacck.setVisibility(View.VISIBLE);
        bacck.setOnClickListener(this);
        title.setText(R.string.shop_cart);
        titleLayout.setVisibility(View.GONE);
    }

    @Override
    protected void initOperate() {
        if (xcShopCartFragment == null) {
            xcShopCartFragment = new XCShopCartFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.content, xcShopCartFragment).commit();
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
