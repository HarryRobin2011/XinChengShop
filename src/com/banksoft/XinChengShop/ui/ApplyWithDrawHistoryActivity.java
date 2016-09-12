package com.banksoft.XinChengShop.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.fragment.ApplyWithDrawHistoryFragment;

/**
 * Created by Robin on 2016/9/12.
 * 提现记录列表
 */
public class ApplyWithDrawHistoryActivity extends XCBaseActivity implements View.OnClickListener {
    private TextView title;
    private ImageView back;

    private ApplyWithDrawHistoryFragment applyWithDrawHistoryFragment;

    @Override
    protected void initContentView() {
        setContentView(R.layout.content_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.title);
        back = (ImageView) findViewById(R.id.title_back_button);
    }

    @Override
    protected void initData() {
     title.setText(R.string.with_draw_record);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
    }

    @Override
    protected void initOperate() {

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
