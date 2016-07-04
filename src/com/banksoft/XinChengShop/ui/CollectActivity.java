package com.banksoft.XinChengShop.ui;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.CollectViewPagerAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.fragment.ProductCollectListFragment;
import com.banksoft.XinChengShop.ui.fragment.ShopCollectListFragment;
import com.banksoft.XinChengShop.widget.viewpagerindicator.TabPageIndicator;

import java.util.LinkedList;

/**
 * Created by harry_robin on 15/11/23.
 */
public class CollectActivity extends XCBaseActivity implements View.OnClickListener{
    private ProductCollectListFragment productCollectListFragment;// 收藏产品力列表
    private ShopCollectListFragment shopCollectListFragment;//收藏店铺列表
    private TabPageIndicator tabPageIndicator;
    private ViewPager mViewPager;
    private int currentIndex;

    private CollectViewPagerAdapter collectViewPagerAdapter;

    private LinkedList dataList;

    private ImageView back,more;

    @Override
    protected void initContentView() {
        setContentView(R.layout.collect_content_layout);
    }

    @Override
    protected void initView() {
        tabPageIndicator = (TabPageIndicator) findViewById(R.id.tab_page_indicator);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        back = (ImageView) findViewById(R.id.title_back_button);
        more = (ImageView) findViewById(R.id.more);
    }

    @Override
    protected void initData() {
        currentIndex = getIntent().getIntExtra(IntentFlag.INDEX,0);
        dataList = new LinkedList();
    }

    @Override
    protected void initOperate() {
        dataList.clear();
        if (productCollectListFragment == null) {
            productCollectListFragment = new ProductCollectListFragment();
        }
        if(shopCollectListFragment == null){
            shopCollectListFragment = new ShopCollectListFragment();
        }
        dataList.add(productCollectListFragment);
        dataList.add(shopCollectListFragment);
        if(collectViewPagerAdapter == null){
            collectViewPagerAdapter = new CollectViewPagerAdapter(getSupportFragmentManager(),dataList);
        }
        mViewPager.setAdapter(collectViewPagerAdapter);
        tabPageIndicator.setViewPager(mViewPager);
        tabPageIndicator.setCurrentItem(currentIndex);
        back.setOnClickListener(this);
        more.setOnClickListener(this);
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
