package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.MyBankDao;
import com.banksoft.XinChengShop.entity.BalanceRecord;
import com.banksoft.XinChengShop.entity.Bank;
import com.banksoft.XinChengShop.entity.MemberRateVO;
import com.banksoft.XinChengShop.entity.MemberVO;
import com.banksoft.XinChengShop.model.BankListData;
import com.banksoft.XinChengShop.model.MemberData;
import com.banksoft.XinChengShop.model.MemberVOData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.fragment.CheckPay2PasswordFragment;
import com.banksoft.XinChengShop.ui.fragment.CheckPayPasswordFragment;
import com.banksoft.XinChengShop.utils.CommonUtil;
import com.banksoft.XinChengShop.utils.PopupWindowUtil;
import com.banksoft.XinChengShop.widget.MyProgressDialog;

import java.math.BigDecimal;

/**
 * Created by Robin on 2016/7/26.
 */
public class ApplyWithDrawActivity extends XCBaseActivity implements View.OnClickListener,CheckPay2PasswordFragment.CheckListener{
    private ImageView back;
    private TextView title, name, no, account;
    private EditText money;
    private Button ok;
    private MyBankDao myBankDao;
    private Button rightButton;
    private Bank currentBank;
    private MyProgressDialog progressDialog;
    private LinearLayout contentLayout;
    private LinearLayout selectCardLayout;
    private LinearLayout addLayout;
    private MemberVO memberVO;
    private ProgressBar progressBar;
     private TextView rates;
    private final int APPLY_WITH_DRAW_RATES = 100;
    private MemberRateVO memberRateVo;

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
        rightButton = (Button) findViewById(R.id.titleRightButton);

        contentLayout = (LinearLayout) findViewById(R.id.content_layout);
        addLayout = (LinearLayout) findViewById(R.id.add_layout);
        selectCardLayout = (LinearLayout) findViewById(R.id.select_bank_card);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        rates = (TextView) findViewById(R.id.rates);
    }

    @Override
    protected void initData() {
        memberVO = (MemberVO) getIntent().getSerializableExtra(IntentFlag.MEMBER);
        title.setText(R.string.with_draw);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        rightButton.setText(R.string.with_draw_tariff);
        rightButton.setVisibility(View.GONE);
        rightButton.setOnClickListener(this);
        money.setHint("最多可申请提现"+memberVO.getBalance()+"元");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Activity.RESULT_FIRST_USER) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    currentBank = (Bank) data.getSerializableExtra(IntentFlag.DATA);
                    showBankView(currentBank);
                }
            }
        }else if(requestCode == APPLY_WITH_DRAW_RATES){//选择提现费率
            if(data != null){
                memberRateVo = (MemberRateVO) data.getSerializableExtra(IntentFlag.DATA);
                rates.setText(memberRateVo.getRate()+"%");
            }
        }
    }

    @Override
    protected void initOperate() {
        addLayout.setOnClickListener(this);
        rightButton.setOnClickListener(this);
        selectCardLayout.setOnClickListener(this);
        rates.setOnClickListener(this);
        ok.setOnClickListener(this);
        if (myBankDao == null) {
            myBankDao = new MyBankDao(mContext);
        }
        new MyBankList().execute(myBankDao);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back_button:
                finish();
                break;
            case R.id.titleRightButton:

                break;
            case R.id.add_layout:
                Intent bankListIntent = new Intent(mContext, MyBankListActivity.class);
                bankListIntent.putExtra(IntentFlag.IS_SELECT, true);
                startActivityForResult(bankListIntent, Activity.RESULT_FIRST_USER);
                break;
            case R.id.ok:
                BalanceRecord record = getBalanceRecord();
                if (record != null) {
                   showPayPasswordView(record);
                }
                break;
            case R.id.select_bank_card:
                Intent bankListIntent2 = new Intent(mContext, MyBankListActivity.class);
                bankListIntent2.putExtra(IntentFlag.IS_SELECT, true);
                startActivityForResult(bankListIntent2, Activity.RESULT_FIRST_USER);
                break;
            case R.id.rates:
                Intent intent = new Intent(mContext,WithDrawTariffActivity.class);
                startActivityForResult(intent,APPLY_WITH_DRAW_RATES);
                break;

        }
    }

    private void showPayPasswordView(BalanceRecord balanceRecord) {
        CheckPay2PasswordFragment checkPayPasswordFragment = new CheckPay2PasswordFragment();
        checkPayPasswordFragment.setCheckListener(this);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.DATA,balanceRecord);
        checkPayPasswordFragment.setArguments(bundle);
        checkPayPasswordFragment.show(getSupportFragmentManager(),"CheckPay2PasswordFragment");
    }

    private BalanceRecord getBalanceRecord() {
        if (currentBank == null) {
            alert(R.string.the_bank_no_empty);
            return null;
        }
        String moneyStr = money.getText().toString();

        if (moneyStr.isEmpty()) {
            alert(R.string.money_no_empty);
            return null;
        } else if (moneyStr.equals("0")) {
            alert(R.string.money_no_zero);
            return null;
        }else if(memberRateVo == null){
            alert(R.string.pleaet_select_rates);
            return null;
        }
        BigDecimal moneyDecimal = new BigDecimal(moneyStr);
        BigDecimal balanceDecimal = new BigDecimal(member.getMember().getBalance());

        if (moneyDecimal.compareTo(balanceDecimal) == 1) {
            alert(R.string.with_draw_money_greater_than_balance);
            return null;
        }
        BalanceRecord balanceRecord = new BalanceRecord();
        balanceRecord.setCreateTime(System.currentTimeMillis());
        balanceRecord.setMemberAccount(currentBank.getAccount());
        balanceRecord.setWithdrawAccount(currentBank.getNo());
        balanceRecord.setWithdrawName(currentBank.getName());
        balanceRecord.setWithdrawBank(currentBank.getBankName());
        balanceRecord.setWithdrawSubBank(currentBank.getAddress());
        balanceRecord.setMemberId(member.getMember().getId());
        balanceRecord.setMoney(Float.valueOf(moneyStr));
        balanceRecord.setWithdrawRate(memberRateVo.getRate());
        balanceRecord.setWithdrawRateName(memberRateVo.getName());
        return balanceRecord;
    }
    /**
     *     private String memberId;     会员id
     private float money;      //提现金额
     private String remark;
     private String withdrawAccount;    //提现帐号(银行卡号)
     private String memberAccount;   //会员帐号
     private String withdrawName;        //开户人姓名
     private float withdrawRate;     //提现费率
     private String withdrawRateName;     //提现限制
     private String withdrawBank;        //所属银行
     private String withdrawSubBank;    //所属分行

     */

    /**
     * 支付是否成功
     * @param success
     * @param memberVO
     */
    @Override
    public void isSuccess(boolean success, MemberVO memberVO) {
          if(success){
              alert(R.string.apply_with_draw_success);
//              member.setMember(memberVO);
//              saveLogin(member);
              finish();
          }else{

          }
    }
//
//    private class MyTask extends AsyncTask<MyBankDao, String, MemberVOData> {
//        private BalanceRecord record;
//        private String password;
//
//        public MyTask(BalanceRecord record,String password) {
//            this.record = record;
//            this.password = password;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressDialog = new MyProgressDialog(ApplyWithDrawActivity.this);
//            progressDialog.showDialog(R.string.please_wait);
//        }
//
//        @Override
//        protected MemberVOData doInBackground(MyBankDao... params) {
//            return params[0].applyWithDraw(member.getMember().getId(),record,password);
//        }
//
//        @Override
//        protected void onPostExecute(MemberVOData memberVOData) {
//            super.onPostExecute(memberVOData);
//            progressDialog.dismiss();
//            if (memberVOData != null) {
//                if (memberVOData.isSuccess()) {
//                    alert(R.string.apply_with_draw_success);
//                    finish();
//                } else {
//                    alert(memberVOData.getMsg().toString());
//                }
//            } else {
//                alert(R.string.net_error);
//            }
//        }
//    }

    private class MyBankList extends AsyncTask<MyBankDao, String, BankListData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected BankListData doInBackground(MyBankDao... params) {
            return params[0].getBankListData(member.getMember().getId());
        }

        @Override
        protected void onPostExecute(BankListData bankListData) {
            super.onPostExecute(bankListData);
            contentLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            if (bankListData != null) {
                if (bankListData.isSuccess()) {
                    if (bankListData.getData().size() != 0) {
                        selectCardLayout.setVisibility(View.VISIBLE);
                        addLayout.setVisibility(View.GONE);
                        currentBank = bankListData.getData().get(0);
                        showBankView(currentBank);
                    } else {
                        selectCardLayout.setVisibility(View.GONE);
                        addLayout.setVisibility(View.VISIBLE);
                    }
                } else {
                    alert(bankListData.getMsg().toString());
                }
            } else {
                alert(R.string.net_error);
            }
        }
    }

    private void showBankView(Bank bank) {
        name.setText(bank.getBankName());
        no.setText(bank.getNo());
        account.setText(bank.getName());
    }
}
