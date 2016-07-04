package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.DispatchDao;
import com.banksoft.XinChengShop.model.DispatchMemberData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

/**
 * Created by Robin on 2016/4/23.
 */
public class DispatchOrderManagerActivity extends XCBaseActivity implements View.OnClickListener{
    private LinearLayout myDispatchOrder, newDispatchOrder,toolLayout;
    private Button applyDispatch;
    private TextView title;
    private ImageView back;
    private ProgressBar mProgressBar;
    private DispatchDao dispatchDao;
    private int APPLY_DISPATCHER = 0;// 申请派单员

    @Override
    protected void initContentView() {
        setContentView(R.layout.dispatch_order_manager_layout);
    }

    @Override
    protected void initView() {
        myDispatchOrder = (LinearLayout) findViewById(R.id.my_dispatch_order);
        newDispatchOrder = (LinearLayout) findViewById(R.id.new_dispatch_order);
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
        applyDispatch = (Button) findViewById(R.id.apply_dispatch);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        toolLayout = (LinearLayout) findViewById(R.id.tool_layout);
    }

    @Override
    protected void initData() {
        title.setText(R.string.dispatch_manager_title);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        applyDispatch.setOnClickListener(this);
        myDispatchOrder.setOnClickListener(this);
        newDispatchOrder.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APPLY_DISPATCHER){

        }
    }

    @Override
    protected void initOperate() {
        if(dispatchDao == null){
           dispatchDao = new DispatchDao(mContext);
        }
        new MyThread().execute(dispatchDao);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_dispatch_order:// 我的派送订单
                Intent intent = new Intent(mContext,ExpressBillListActivity.class);
                intent.putExtra(IntentFlag.TYPE,ExpressBillListActivity.OperaType.MY_DISPATH_ORDER);
                startActivity(intent);
                break;
            case R.id.new_dispatch_order://新的派送订单
                Intent newIntent = new Intent(mContext,ExpressBillListActivity.class);
                newIntent.putExtra(IntentFlag.TYPE,ExpressBillListActivity.OperaType.NEW_DISPATCH_ORDER);
                startActivity(newIntent);
                break;
            case R.id.title_back_button://
                finish();
                break;
            case R.id.apply_dispatch:// 申请派单员
                Intent dispatchIntent = new Intent(mContext,ApplyDispatcherActivity.class);
                startActivity(dispatchIntent);
                break;
        }
    }

    private class MyThread extends AsyncTask<DispatchDao,String,DispatchMemberData>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected DispatchMemberData doInBackground(DispatchDao... params) {
            return params[0].getDispatchMemberData(member.getMember().getId(),true);
        }

        @Override
        protected void onPostExecute(DispatchMemberData dispatchMemberData) {
            super.onPostExecute(dispatchMemberData);
            mProgressBar.setVisibility(View.GONE);
            if(dispatchMemberData.isSuccess()){// 是派单员
                 toolLayout.setVisibility(View.VISIBLE);
                 applyDispatch.setVisibility(View.GONE);
            }else{
                toolLayout.setVisibility(View.GONE);
                applyDispatch.setVisibility(View.VISIBLE);
            }
        }
    }
}
