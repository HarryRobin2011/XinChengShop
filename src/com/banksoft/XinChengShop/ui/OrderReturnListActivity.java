package com.banksoft.XinChengShop.ui;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.OrderReturnViewPagerAdapter;
import com.banksoft.XinChengShop.type.OrderStatus;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.fragment.OrderReturnListFragment;
import com.banksoft.XinChengShop.widget.viewpagerindicator.TabPageIndicator;

/**
 * Created by Harry on 2016-07-31.
 */
public class OrderReturnListActivity extends XCBaseActivity implements View.OnClickListener {
    private TextView title;
    private ImageView back;
    private TabPageIndicator tabPageIndicator;
    private ViewPager mViewPager;

    private OrderReturnViewPagerAdapter orderReturnViewPagerAdapter;
    private OrderStatus[] orderStatuses = new OrderStatus[]{OrderStatus.REPEALING,OrderStatus.REPEAL_OVER};

    @Override
    protected void initContentView() {
        setContentView(R.layout.order_retuen_list_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
        tabPageIndicator = (TabPageIndicator) findViewById(R.id.tab_page_indicator);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    @Override
    protected void initData() {
        title.setText(R.string.icon_order_after_sale);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
    }

    @Override
    protected void initOperate() {
        if(orderReturnViewPagerAdapter == null){
            orderReturnViewPagerAdapter = new OrderReturnViewPagerAdapter(getSupportFragmentManager(),orderStatuses);
        }
        mViewPager.setAdapter(orderReturnViewPagerAdapter);
        tabPageIndicator.setViewPager(mViewPager);
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
