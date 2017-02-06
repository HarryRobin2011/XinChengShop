package com.banksoft.XinChengShop.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.MyBankDao;
import com.banksoft.XinChengShop.model.MemberVOData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

/**
 * Created by Robin on 2016/4/8.
 */
public class WalletManagerActivity extends XCBaseActivity implements View.OnClickListener {
    private LinearLayout memberBalanceLayout, memberScoreLayout, myBank, applyWithDraw,applyWithDrawHistory;
    private TextView title, moneyBalance, scoreBalance;
    private ImageView back;
    private MyBankDao myBankDao;
    private ProgressDialog progressDialog;

    @Override
    protected void initContentView() {
        setContentView(R.layout.waller_manager_layout);
    }

    @Override
    protected void initView() {
        memberBalanceLayout = (LinearLayout) findViewById(R.id.my_balance);
        memberScoreLayout = (LinearLayout) findViewById(R.id.user_score);
        title = (TextView) findViewById(R.id.titleText);
        moneyBalance = (TextView) findViewById(R.id.money_balance);
        scoreBalance = (TextView) findViewById(R.id.score_balance);
        back = (ImageView) findViewById(R.id.title_back_button);
        myBank = (LinearLayout) findViewById(R.id.my_bank);
        applyWithDraw = (LinearLayout) findViewById(R.id.apply_with_draw);
        applyWithDrawHistory = (LinearLayout) findViewById(R.id.apply_with_draw_histroy);
    }

    @Override
    protected void initData() {
        title.setText(R.string.my_wallet);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        moneyBalance.setText(member.getMember().getBalance() + "元");
        scoreBalance.setText(member.getMember().getMemberPoint() + "分");
    }

    @Override
    protected void initOperate() {
        back.setOnClickListener(this);
        memberBalanceLayout.setOnClickListener(this);
        memberScoreLayout.setOnClickListener(this);
        myBank.setOnClickListener(this);
        applyWithDraw.setOnClickListener(this);
        applyWithDrawHistory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back_button:
                finish();
                break;
            case R.id.my_balance:
                Intent balanceIntent = new Intent(mContext, MoneyBalanceActivity.class);
                startActivity(balanceIntent);
                break;
            case R.id.user_score:
                Intent scoreIntent = new Intent(mContext, ScoreListActivity.class);
                startActivity(scoreIntent);
                break;
            case R.id.my_bank:
                Intent intent = new Intent(mContext, MyBankListActivity.class);
                startActivity(intent);
                break;
            case R.id.apply_with_draw:
                if (myBankDao == null) {
                    myBankDao = new MyBankDao(mContext);
                }
                new MyTask().execute(myBankDao);
                break;
            case R.id.apply_with_draw_histroy:
                Intent applyIntent = new Intent(mContext,MoneyBalanceActivity.class);
                startActivity(applyIntent);
                break;
        }
    }

    private class MyTask extends AsyncTask<MyBankDao, String, MemberVOData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(WalletManagerActivity.this, getText(R.string.alert), getText(R.string.please_wait));
            }
        }

        @Override
        protected MemberVOData doInBackground(MyBankDao... params) {
            return params[0].getMemberVOData(member.getMember().getId());
        }

        @Override
        protected void onPostExecute(MemberVOData memberVOData) {
            super.onPostExecute(memberVOData);
            progressDialog.dismiss();
            if (memberVOData != null) {
                if (memberVOData.isSuccess()) {
                    Intent intent = new Intent(mContext, ApplyWithDrawActivity.class);
                    intent.putExtra(IntentFlag.MEMBER, memberVOData.getData());
                    startActivity(intent);
                } else {
                    alert(memberVOData.getMsg().toString());
                }
            } else {
                alert(R.string.net_error);
            }
        }
    }
}
