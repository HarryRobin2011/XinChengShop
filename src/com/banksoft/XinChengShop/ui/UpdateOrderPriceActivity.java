package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.UpdateDao;
import com.banksoft.XinChengShop.dao.UpdateOrderDao;
import com.banksoft.XinChengShop.entity.OrderVO;
import com.banksoft.XinChengShop.model.IsFlagData;
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
    private UpdateOrderDao updateOrderDao;
    private ProgressDialog progressDialog;

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
                      alert(R.string.total_no_empty);
                    return;
                } else if ("".equals(expressMoney)) {
                    alert(R.string.express_money_no_empty);
                    return;
                }
                if(updateOrderDao == null){
                    updateOrderDao = new UpdateOrderDao(mContext);
                }
                new MyTask(total,expressMoney).execute(updateOrderDao);
                break;
        }
    }

    private class MyTask extends AsyncTask<UpdateOrderDao,String,IsFlagData>{
        private String total;
        private String expressMoney;

        public MyTask(String total, String expressMoney) {
            this.total = total;
            this.expressMoney = expressMoney;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progressDialog == null){
                progressDialog = ProgressDialog.show(UpdateOrderPriceActivity.this,getText(R.string.alert),getText(R.string.please_wait));
            }
        }

        @Override
        protected IsFlagData doInBackground(UpdateOrderDao... params) {
            return params[0].UpdatePrice(currentOrderVo.getId(),total,expressMoney);
        }

        @Override
        protected void onPostExecute(IsFlagData isFlagData) {
            super.onPostExecute(isFlagData);
            if(isFlagData != null){
                if(isFlagData.isSuccess()){
                    alert(R.string.update_order_success);
                    setResult(Activity.RESULT_OK);
                    finish();
                }else{
                    alert(isFlagData.getMsg().toString());
                }
            }else{
                alert(R.string.net_error);
            }
        }
    }
}
