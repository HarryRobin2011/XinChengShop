package com.banksoft.XinChengShop.ui;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.CollectViewPagerAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.fragment.MoneyBalanceDetailFragment;
import com.banksoft.XinChengShop.ui.fragment.ProductCollectListFragment;
import com.banksoft.XinChengShop.ui.fragment.ShopCollectListFragment;
import com.banksoft.XinChengShop.widget.viewpagerindicator.TabPageIndicator;

import java.util.LinkedList;

/**
 * Created by Robin on 2016/4/8.
 * 账号金额明细
 */
public class MoneyBalanceActivity extends XCBaseActivity implements View.OnClickListener{
    private MoneyBalanceDetailFragment rechargeDetailFragment;
    private MoneyBalanceDetailFragment cashOutDetailFragment;
    private TabPageIndicator tabPageIndicator;
    private ViewPager mViewPager;
    private int currentIndex;

    private CollectViewPagerAdapter collectViewPagerAdapter;

    private LinkedList dataList;

    private ImageView back,more;

    @Override
    protected void initContentView() {
        setContentView(R.layout.money_detail_content_layout);
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
        if (rechargeDetailFragment == null) {
            rechargeDetailFragment = new MoneyBalanceDetailFragment();
            rechargeDetailFragment.currentType = MoneyBalanceDetailFragment.Type.RECHARGE;
        }
        if(cashOutDetailFragment == null){
            cashOutDetailFragment = new MoneyBalanceDetailFragment();
            cashOutDetailFragment.currentType = MoneyBalanceDetailFragment.Type.CASHOUT;
        }
        dataList.add(rechargeDetailFragment);
        dataList.add(cashOutDetailFragment);

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
