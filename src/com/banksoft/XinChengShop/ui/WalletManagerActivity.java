package com.banksoft.XinChengShop.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

/**
 * Created by Robin on 2016/4/8.
 */
public class WalletManagerActivity extends XCBaseActivity implements View.OnClickListener{
    private LinearLayout memberBalanceLayout, memberScoreLayout,myBank,applyWithDraw;
    private TextView title, moneyBalance, scoreBalance;
    private ImageView back;

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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back_button:
               finish();
                break;
            case R.id.my_balance:
                Intent balanceIntent = new Intent(mContext,MoneyBalanceActivity.class);
                startActivity(balanceIntent);
                break;
            case R.id.user_score:
                Intent scoreIntent = new Intent(mContext,ScoreListActivity.class);
                startActivity(scoreIntent);
                break;
            case R.id.my_bank:
                Intent intent = new Intent(mContext,MyBankListActivity.class);
                startActivity(intent);
                break;
            case R.id.apply_with_draw:
                Intent withDraw = new Intent(mContext,ApplyWithDrawActivity.class);
                startActivity(withDraw);
                break;
        }
    }
}
