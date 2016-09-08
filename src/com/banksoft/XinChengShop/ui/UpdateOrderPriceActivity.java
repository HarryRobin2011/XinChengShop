package com.banksoft.XinChengShop.ui;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.entity.OrderVO;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

/**
 * Created by Robin on 2016/9/7.
 */
public class UpdateOrderPriceActivity extends XCBaseActivity implements View.OnClickListener {
    private ImageView back;
    private TextView title;
    private EditText totalEdit;
    private EditText expressEdit;
    private Button ok;
    private OrderVO currentOrderVo;

    @Override
    protected void initContentView() {
        setContentView(R.layout.update_order_layout);
    }

    @Override
    protected void initView() {
        back = (ImageView) findViewById(R.id.title_back_button);
        title = (TextView) findViewById(R.id.titleText);
        totalEdit = (EditText) findViewById(R.id.total);
        expressEdit = (EditText) findViewById(R.id.express_money);
        ok = (Button) findViewById(R.id.ok);
    }

    @Override
    protected void initData() {
        currentOrderVo = (OrderVO) getIntent().getSerializableExtra(IntentFlag.ORDER_VO);
        back.setVisibility(View.VISIBLE);
        title.setText(R.string.updata_order_price);
        totalEdit.setText(String.valueOf(currentOrderVo.getTotalMoney()));
        expressEdit.setText(String.valueOf(currentOrderVo.getExpressMoney()));
    }

    @Override
    protected void initOperate() {
        back.setOnClickListener(this);
        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back_button:
                finish();
                break;
            case R.id.ok:
                String total = totalEdit.getText().toString();
                String expressMoney = expressEdit.getText().toString();
                if ("".equals(total)) {

                } else if ("".equals(expressMoney)) {

                }
                break;
        }
    }
}
