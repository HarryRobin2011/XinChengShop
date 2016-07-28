package com.banksoft.XinChengShop.ui;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.CommonTypeAdapter;
import com.banksoft.XinChengShop.type.CommonType;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.widget.viewpagerindicator.TabPageIndicator;
import com.banksoft.XinChengShop.widget.viewpagerindicator.TitlePageIndicator;

/**
 * Created by harry_robin on 15/11/24.
 */
public class CommentActivity extends XCBaseActivity implements View.OnClickListener{
    private CommonType[] statuses = new CommonType[]{CommonType.assess,CommonType.replySelf};
    private ViewPager mViewPager;
    private TabPageIndicator indicator;
    private CommonTypeAdapter commonTypeAdapter;
    private ImageView back;
    private TextView title;


    @Override
    protected void initContentView() {
        setContentView(R.layout.list_view_pager_layout);
    }

    @Override
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        indicator = (TabPageIndicator) findViewById(R.id.tab_page_indicator);
        back = (ImageView) findViewById(R.id.title_back_button);
       // more = (ImageView) findViewById(R.id.more);
        title = (TextView) findViewById(R.id.titleText);
    }

    @Override
    protected void initData() {
        isLogin();
        title.setText(R.string.my_self_comment);
    }

    @Override
    protected void initOperate() {
        back.setOnClickListener(this);
        back.setVisibility(View.VISIBLE);
      //  more.setOnClickListener(this);
        if (commonTypeAdapter == null) {
            commonTypeAdapter = new CommonTypeAdapter(getSupportFragmentManager(), statuses);
        }
        mViewPager.setAdapter(commonTypeAdapter);
        mViewPager.setCurrentItem(0);
        indicator.setViewPager(mViewPager);
        indicator.setCurrentItem(0);
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
