package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.dao.MyBankDao;
import com.banksoft.XinChengShop.entity.Bank;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.widget.MyProgressDialog;

/**
 * Created by Robin on 2016/7/26.
 */
public class MyBankAddActivity extends XCBaseActivity implements View.OnClickListener {
    private TextView title;
    private Button save;
    private ImageView back;
    private EditText name, bankName, cardNo, openCardAddress;
    private MyBankDao myBankDao;
    private MyProgressDialog progressDialog;

    @Override
    protected void initContentView() {
        setContentView(R.layout.my_bank_add_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        save = (Button) findViewById(R.id.titleRightButton);
        back = (ImageView) findViewById(R.id.title_back_button);
        name = (EditText) findViewById(R.id.name);
        bankName = (EditText) findViewById(R.id.bank_name);
        cardNo = (EditText) findViewById(R.id.card_no);
        openCardAddress = (EditText) findViewById(R.id.open_card_address);
    }

    @Override
    protected void initData() {
        back.setVisibility(View.VISIBLE);
        save.setVisibility(View.VISIBLE);
        title.setText(R.string.my_bank_add_title);
        save.setText(R.string.save);
    }

    @Override
    protected void initOperate() {
         save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titleRightButton:
                Bank bank = getBank();
                if (bank != null) {
                    if (myBankDao == null) {
                        myBankDao = new MyBankDao(mContext);
                    }
                    new MyTask(bank).execute(myBankDao);
                }
                break;
        }
    }

    /**
     * 保存银行卡
     */
    private class MyTask extends AsyncTask<MyBankDao, String, IsFlagData> {
        Bank bank;

        public MyTask(Bank bank) {
            this.bank = bank;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new MyProgressDialog(MyBankAddActivity.this);
            progressDialog.showDialog(R.string.please_wait);
        }
        @Override
        protected IsFlagData doInBackground(MyBankDao... params) {
            return params[0].saveBankData(bank);
        }
        @Override
        protected void onPostExecute(IsFlagData isFlagData) {
            super.onPostExecute(isFlagData);
            if(isFlagData != null){
               if(isFlagData.isSuccess()){
                   alert(R.string.bank_save_success);
                   setResult(Activity.RESULT_OK);
                   finish();
               }else{
                  alert(isFlagData.getMsg().toString());
               }
            }else {
                alert(R.string.net_error);
            }
        }
    }

    private Bank getBank() {
        String nameStr = name.getText().toString();
        String bankNameStr = bankName.getText().toString();
        String cardNoStr = cardNo.getText().toString();
        String openCardAddressStr = openCardAddress.getText().toString();
        if (nameStr.isEmpty()) {
            alert(R.string.card_holder_no_empty);
            return null;
        } else if (bankNameStr.isEmpty()) {
            alert(R.string.bank_name_no_empty);
            return null;
        } else if (cardNoStr.isEmpty()) {
            alert(R.string.card_no_no_empty);
            return null;
        } else if (openCardAddressStr.isEmpty()) {
            alert(R.string.open_card_address_no_empty);
            return null;
        }
        Bank bank = new Bank();
        bank.setAccount(member.getMemberInfo().getId());
        bank.setBankName(bankNameStr);
        bank.setName(nameStr);
        bank.setNo(cardNoStr);
        return bank;
    }
}
