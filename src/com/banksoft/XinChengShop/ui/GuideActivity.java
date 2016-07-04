package com.banksoft.XinChengShop.ui;

import android.support.v4.view.ViewPager;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.GuidePageAdapyer;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.widget.viewpagerindicator.CirclePageIndicator;

/**
 * Created by harry_robin on 15/9/8.
 */
public class GuideActivity extends XCBaseActivity{
    private Integer[] images = new Integer[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
    private ViewPager viewPager;
    private CirclePageIndicator indicator;
    private GuidePageAdapyer guidePageAdapyer;
    private TextView startNow;
    @Override
    protected void initContentView() {
        setContentView(R.layout.guide_layout);
    }

    @Override
    protected void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        startNow = (TextView) findViewById(R.id.start);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initOperate() {
     if(guidePageAdapyer == null){
         guidePageAdapyer = new GuidePageAdapyer(this,images);
     }
        viewPager.setAdapter(guidePageAdapyer);
        indicator.setViewPager(viewPager);
    }


}
