package com.banksoft.XinChengShop.ui;

import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.fragment.ScoreOrderListFragment;

/**
 * Created by harry_robin on 16/1/23.
 * 积分订单
 */
public class ScoreOrderListActivity extends XCBaseActivity {
    private TextView title;
    private ScoreOrderListFragment scoreOrderListFragment;
    @Override
    protected void initContentView() {
        setContentView(R.layout.content_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
    }

    @Override
    protected void initData() {
        title.setText(R.string.my_score_order);
    }

    @Override
    protected void initOperate() {
        if(scoreOrderListFragment == null){
            scoreOrderListFragment = new ScoreOrderListFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.content,scoreOrderListFragment).commit();

    }
}
