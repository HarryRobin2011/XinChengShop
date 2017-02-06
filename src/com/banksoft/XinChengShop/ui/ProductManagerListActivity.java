package com.banksoft.XinChengShop.ui;

import android.app.Activity;
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
public class ProductManagerListActivity extends XCBaseActivity implements View.OnClickListener,TextView.OnEditorActionListener{
    private TextView title;
    private Button submitGood;
    private ImageView back;
    private EditText searchEdit;
    private TabPageIndicator tabPageIndicator;
    private ViewPager mViewPager;
    private ProductManagerListFragment productManagerPutawayListFragment;
    private ProductManagerListFragment productManagerTakeoffListFragment;
    private ViewPagerAdapter viewPagerAdapter;
    private final int OPERATION_PUBLISH_CODE = 0 ;

    @Override
    protected void initContentView() {
        setContentView(R.layout.product_manager_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
        submitGood = (Button) findViewById(R.id.titleRightButton);
        searchEdit = (EditText) findViewById(R.id.search_type);
        tabPageIndicator = (TabPageIndicator) findViewById(R.id.tab_page_indicator);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == OPERATION_PUBLISH_CODE){
            if(resultCode == Activity.RESULT_OK){
                productManagerTakeoffListFragment.onRefresh();
            }
        }
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
        searchEdit.setOnEditorActionListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back_button:
                finish();
                break;
            case R.id.titleRightButton:
                Intent intent = new Intent(mContext,PublishGoodsActivity.class);
                startActivityForResult(intent,OPERATION_PUBLISH_CODE);
                break;
        }
    }

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
}
