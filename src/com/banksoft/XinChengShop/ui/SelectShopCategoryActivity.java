package com.banksoft.XinChengShop.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.type.ShopType;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.fragment.SelectShopCategoryFragment;

/**
 * Created by admin on 2016/7/20.
 */
public class SelectShopCategoryActivity extends XCBaseActivity {
    private TextView title;
    private ImageView back;
    private ShopType shopType;
    private SelectShopCategoryFragment selectShopCategoryFragment;


    @Override
    protected void initContentView() {
        setContentView(R.layout.content_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        back.setVisibility(View.VISIBLE);
        back = (ImageView) findViewById(R.id.title_back_button);
    }

    @Override
    protected void initData() {
       title.setText(R.string.please_select_category);
    }

    @Override
    protected void initOperate() {
       if(selectShopCategoryFragment == null){
           selectShopCategoryFragment = new SelectShopCategoryFragment();
       }
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.SHOP_TYPE,shopType);
        selectShopCategoryFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.content,selectShopCategoryFragment);
    }
}
