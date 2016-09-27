package com.banksoft.XinChengShop.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.fragment.MemberScoreListFragment;
import com.banksoft.XinChengShop.widget.viewpagerindicator.TabPageIndicator;

/**
 * Created by Robin on 2016/4/9.
 */
public class ScoreListActivity extends XCBaseActivity implements View.OnClickListener {
    private MemberScoreListFragment memberScoreListFragment;
    private ImageView back;
    private TextView title;

    @Override
    protected void initContentView() {
        setContentView(R.layout.score_content_layout);
    }

    @Override
    protected void initView() {
        back = (ImageView) findViewById(R.id.title_back_button);
        back.setVisibility(View.VISIBLE);
        back.setBackgroundResource(R.drawable.tb_icon_actionbar_back);
        back.setOnClickListener(this);
        title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        title.setText(R.string.user_score);
    }

    @Override
    protected void initData() {
        if(memberScoreListFragment == null){
            memberScoreListFragment = new MemberScoreListFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.content,memberScoreListFragment).commit();
    }

    @Override
    protected void initOperate() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back_button:
                finish();
                break;
        }
    }
}
