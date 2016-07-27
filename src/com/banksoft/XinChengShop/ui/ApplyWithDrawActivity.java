package com.banksoft.XinChengShop.ui;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.dao.MyBankDao;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

/**
 * Created by Robin on 2016/7/26.
 */
public class ApplyWithDrawActivity extends XCBaseActivity implements View.OnClickListener{
    private ImageView back;
    private TextView title,name,no,account;
    private EditText money;
    private Button ok;
    private MyBankDao myBankDao;
    @Override
    protected void initContentView() {
        setContentView(R.layout.apply_with_draw_layout);
    }

    @Override
    protected void initView() {
        back = (ImageView) findViewById(R.id.title_back_button);
        title = (TextView) findViewById(R.id.titleText);
        name = (TextView) findViewById(R.id.name);
        no = (TextView) findViewById(R.id.no);
        account = (TextView) findViewById(R.id.account);
        money = (EditText) findViewById(R.id.money);
        ok = (Button) findViewById(R.id.ok);
    }

    @Override
    protected void initData() {
        title.setText(R.string.with_draw);
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
