package com.banksoft.XinChengShop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.OrderViewAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.type.OrderStatus;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.widget.viewpagerindicator.TabPageIndicator;
import com.banksoft.XinChengShop.widget.viewpagerindicator.TitlePageIndicator;

/**
 * Created by harry_robin on 15/11/23.
 */
public class OrderListActivity extends XCBaseActivity implements View.OnClickListener{

    private OrderStatus[] statuses = new OrderStatus[]{OrderStatus.CREATE,OrderStatus.PAY,OrderStatus.DISPATCH,OrderStatus.SUCCESS};
    private ViewPager mViewPager;
    private TabPageIndicator indicator;
    private ImageView back,bgImage;
    private OrderViewAdapter orderViewAdapter;
    private String orderMaster;
    private TextView title;


    @Override
    protected void initContentView() {
        setContentView(R.layout.list_view_pager_layout);
    }

    @Override
    protected void initView() {
        orderMaster = getIntent().getStringExtra(IntentFlag.Order_MASTER);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        indicator = (TabPageIndicator) findViewById(R.id.tab_page_indicator);
        back = (ImageView) findViewById(R.id.title_back_button);
        title = (TextView) findViewById(R.id.titleText);
        bgImage = (ImageView) findViewById(R.id.titleBg);
    }

    @Override
    protected void initData() {
        isLogin();
        back.setImageResource(R.drawable.tb_icon_actionbar_back);
        back.setVisibility(View.VISIBLE);
        bgImage.setImageResource(R.color.white);
        title.setTextColor(getResources().getColor(R.color.text_black_light));
        title.setText(R.string.my_order);
    }

    @Override
    protected void initOperate() {
        back.setOnClickListener(this);
        if(orderViewAdapter == null){
            orderViewAdapter = new OrderViewAdapter(getSupportFragmentManager(),statuses);
        }
        orderViewAdapter.orderMaster = orderMaster;
        mViewPager.setAdapter(orderViewAdapter);
        indicator.setViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back_button:
                finish();
                break;
            case R.id.more:

                break;
        }
    }
}
