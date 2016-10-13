package com.banksoft.XinChengShop.ui.takeout;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.TakeOutInfoAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.entity.ShopVO;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.fragment.takeout.TakeOutMenuFragment;
import com.banksoft.XinChengShop.ui.fragment.takeout.TakeOutShopInfoFragment;
import com.banksoft.XinChengShop.widget.viewpagerindicator.TabPageIndicator;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by harry_robin on 16/3/21.
 */
public class TakeOutInfoActivity extends XCBaseActivity implements View.OnClickListener{
    private ViewPager viewPager;
    private TabPageIndicator tabPageIndicator;
    private TakeOutInfoAdapter takeOutInfoAdapter;
    public ShopVO shopVO;
    private List fragmentList;


    public ImageView bgImage;
    public TextView title;
    public ImageView back;
    public LinearLayout searchLayout;

    private String[] titles = new String[]{"菜单","店铺"};

    @Override
    protected void initContentView() {
        setContentView(R.layout.take_out_info_layout);
    }

    @Override
    protected void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabPageIndicator = (TabPageIndicator) findViewById(R.id.tab_page_indicator);
        bgImage = (ImageView) findViewById(R.id.titleBg);
        title = (TextView) findViewById(R.id.titleText);
        searchLayout = (LinearLayout) findViewById(R.id.search_layout);
        back = (ImageView) findViewById(R.id.title_back_button);
    }

    @Override
    protected void initData() {
        back.setVisibility(View.VISIBLE);
        back.setImageResource(R.drawable.ic_action_previous_item);
        back.setOnClickListener(this);
        shopVO = (ShopVO) getIntent().getExtras().get(IntentFlag.SHOP_VO);
        title.setText(shopVO.getName());
        if (fragmentList == null) {
            fragmentList = new LinkedList();
        }
        TakeOutMenuFragment takeOutMenuFragment = new TakeOutMenuFragment();
        takeOutMenuFragment.shopId = shopVO.getId();
        TakeOutShopInfoFragment takeOutShopInfoFragment = new TakeOutShopInfoFragment();
        fragmentList.add(takeOutMenuFragment);
        fragmentList.add(takeOutShopInfoFragment);
        if (takeOutInfoAdapter == null) {
            takeOutInfoAdapter = new TakeOutInfoAdapter(getSupportFragmentManager(), fragmentList, titles);
        }
        viewPager.setAdapter(takeOutInfoAdapter);
        tabPageIndicator.setViewPager(viewPager);
    }

    @Override
    protected void initOperate() {
        viewPager.setOffscreenPageLimit(2);
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
