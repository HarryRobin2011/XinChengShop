package com.banksoft.XinChengShop.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.ExpressBillAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.ExpressDao;
import com.banksoft.XinChengShop.dao.TextDao;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.ui.ExpressBillListActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseListFragment;
import com.banksoft.XinChengShop.utils.JSONHelper;

import java.util.ArrayList;

/**
 * Created by Robin on 2016/4/23.
 */
public class ExpressBillListFragment extends XCBaseListFragment{
    private ExpressBillListActivity.OperaType currentType;
    private XCBaseActivity activity;
    private TextDao textDao;
    private ExpressDao expressDao;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (XCBaseActivity) getActivity();
    }

    @Override
    public void request() {
        currentType = (ExpressBillListActivity.OperaType) getArguments().getSerializable(IntentFlag.TYPE);
        if(ExpressBillListActivity.OperaType.MY_DISPATH_ORDER.equals(currentType)){//�ҵ����Ͷ���
            url = ControlUrl.EXPRESS_BILL_ORDER_LIST_URL;
            params = "memberId="+activity.member.getMember().getId();
        }else{
            url = ControlUrl.DISPATCH_ORDER_LIST;
            params = "memberId="+activity.member.getMember().getId();
        }

        jsonType = JSONHelper.ORDER_LIST_DATA;
        bailaAdapter = new ExpressBillAdapter(mContext,new ArrayList());
        ((ExpressBillAdapter)bailaAdapter).setCurrentType(currentType);
        xListView.setDividerHeight(10);
        setListDao();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Activity.RESULT_FIRST_USER){
            switch (resultCode){
                case Activity.RESULT_OK:  //???????
                    request();
                    break;
                case Activity.RESULT_CANCELED: //??????
                    request();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public Fragment getInstance() {
        return null;
    }

    @Override
    public CharSequence getTitle() {
        return null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onAdapterCLick(View view, int position) {
       switch (view.getId()){
           case R.id.my_dispatch:
               if(expressDao == null){
                   expressDao = new ExpressDao(mContext);
               }
               new MyDispatch().execute(expressDao);
               break;
       }
    }

    private class MyDispatch extends AsyncTask<ExpressDao,String,IsFlagData>{
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progressDialog == null){
                progressDialog = ProgressDialog.show(getActivity(),"",getText(R.string.please_wait));
            }
        }

        @Override
        protected IsFlagData doInBackground(ExpressDao... params) {

            return null;
        }

        @Override
        protected void onPostExecute(IsFlagData isFlagData) {
            super.onPostExecute(isFlagData);
            if(isFlagData != null){
                if(isFlagData.isSuccess()){

                }else{
                    alert(isFlagData.getMsg().toString());
                }
            }else{
                alert(R.string.net_error);
            }
        }
    }



}
