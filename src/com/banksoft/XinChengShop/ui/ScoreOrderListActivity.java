package com.banksoft.XinChengShop.ui;

import android.view.View;
import android.widget.ImageView;
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
    private ImageView back;
    @Override
    protected void initContentView() {
        setContentView(R.layout.content_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
    }

    @Override
    protected void initData() {
        title.setText(R.string.my_score_order);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initOperate() {
        if(scoreOrderListFragment == null){
            scoreOrderListFragment = new ScoreOrderListFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.content,scoreOrderListFragment).commit();

    }
}
