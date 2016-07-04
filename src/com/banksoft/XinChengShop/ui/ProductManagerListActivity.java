package com.banksoft.XinChengShop.ui;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.ViewPagerAdapter;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.fragment.ProductManagerListFragment;
import com.banksoft.XinChengShop.widget.viewpagerindicator.TabPageIndicator;

import java.util.LinkedList;

/**
 * Created by Robin on 2016/4/9.
 */
public class ProductManagerListActivity extends XCBaseActivity implements View.OnClickListener{
    private TextView title;
    private Button submitGood;
    private ImageView back;
    private EditText searchEdit;
    private TabPageIndicator tabPageIndicator;
    private ViewPager mViewPager;
    private ProductManagerListFragment productManagerPutawayListFragment;
    private ProductManagerListFragment productManagerTakeoffListFragment;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void initContentView() {
        setContentView(R.layout.product_manager_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
        submitGood = (Button) findViewById(R.id.titleRightButton);
        searchEdit = (EditText) findViewById(R.id.search_edit);
        tabPageIndicator = (TabPageIndicator) findViewById(R.id.tab_page_indicator);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    @Override
    protected void initData() {
        title.setOnClickListener(this);
        back.setOnClickListener(this);
        submitGood.setOnClickListener(this);
        submitGood.setText(R.string.goods_publish);

        back.setVisibility(View.VISIBLE);
        submitGood.setVisibility(View.VISIBLE);

    }

    @Override
    protected void initOperate() {
       back.setVisibility(View.VISIBLE);
       title.setText(R.string.goods_manager_title);
        LinkedList dataList = new LinkedList();
        if(productManagerPutawayListFragment == null){
            productManagerPutawayListFragment = new ProductManagerListFragment();
            productManagerPutawayListFragment.currentType = ProductManagerListFragment.Type.PUTAWAY;
        }
        if(productManagerTakeoffListFragment == null){
            productManagerTakeoffListFragment = new ProductManagerListFragment();
            productManagerTakeoffListFragment.currentType = ProductManagerListFragment.Type.TAKEOFF;
        }
        dataList.add(productManagerPutawayListFragment);
        dataList.add(productManagerTakeoffListFragment);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),dataList);
        mViewPager.setAdapter(viewPagerAdapter);
        tabPageIndicator.setViewPager(mViewPager);
        tabPageIndicator.setCurrentItem(0);

        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    Log.i("HarryRobin","IME_ACTION_SEARCH");
                    String name = searchEdit.getText().toString().trim();
                    if(name.equals("")){
                        alert(R.string.goods_search_name_no_empty);
                        return false;
                    }
                    if(mViewPager.getCurrentItem() == 0){// 出售中
                        productManagerPutawayListFragment.goodName = name;
                        productManagerPutawayListFragment.onRefresh();
                    }else if(mViewPager.getCurrentItem() == 1){//仓库中
                        productManagerTakeoffListFragment.goodName = name;
                        productManagerTakeoffListFragment.onRefresh();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back_button:
                finish();
                break;
            case R.id.titleRightButton:
                Intent intent = new Intent(mContext,PublishGoodsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
