package com.banksoft.XinChengShop.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.WithDrawTariffAdapter;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

/**
 * Created by Robin on 2016/7/28.
 */
public class WithDrawTariffActivity extends XCBaseActivity implements View.OnClickListener{
    private ImageView back;
    private ListView listView;
    private TextView title;
    private WithDrawTariffAdapter withDrawTariffAdapter;

    @Override
    protected void initContentView() {
        setContentView(R.layout.with_draw_tariff_layout);
    }

    @Override
    protected void initView() {
        back = (ImageView) findViewById(R.id.title_back_button);
        listView = (ListView) findViewById(R.id.list_view);
        title = (TextView) findViewById(R.id.titleText);
    }

    @Override
    protected void initData() {
        title.setText(R.string.with_draw_tariff);
        back.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initOperate() {
       back.setOnClickListener(this);
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
