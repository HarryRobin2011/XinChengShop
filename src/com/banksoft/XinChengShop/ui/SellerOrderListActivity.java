package com.banksoft.XinChengShop.ui;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.SellerOrderViewPagerAdapter;
import com.banksoft.XinChengShop.type.OrderStatus;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.widget.viewpagerindicator.TabPageIndicator;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Robin on 2016/8/5.
 */
public class SellerOrderListActivity extends XCBaseActivity implements View.OnClickListener {
    private TextView title;
    private ImageView back;
    private TabPageIndicator indicator;
    private ViewPager viewPager;
    private OrderStatus[] orderStatuses = new OrderStatus[]{OrderStatus.ALL, OrderStatus.PAY, OrderStatus.DISPATCH};
    private SellerOrderViewPagerAdapter sellerOrderViewPagerAdapter;
    private List dataList;

    @Override
    protected void initContentView() {
        setContentView(R.layout.seller_order_list_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
        indicator = (TabPageIndicator) findViewById(R.id.tab_page_indicator);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    @Override
    protected void initData() {
        title.setText(R.string.seller_order);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        if(dataList == null){
            dataList = new LinkedList();
        }
        for(OrderStatus orderStatus:orderStatuses){
            dataList.add(orderStatus);
        }

    }

    @Override
    protected void initOperate() {
        if (sellerOrderViewPagerAdapter == null) {
            sellerOrderViewPagerAdapter = new SellerOrderViewPagerAdapter(getSupportFragmentManager(), dataList);
        }
        viewPager.setAdapter(sellerOrderViewPagerAdapter);
        indicator.setViewPager(viewPager);
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
