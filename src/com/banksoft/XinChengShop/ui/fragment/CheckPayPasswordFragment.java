package com.banksoft.XinChengShop.ui.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.CheckPasswordDao;
import com.banksoft.XinChengShop.model.base.BaseData;
import com.banksoft.XinChengShop.ui.OrderListActivity;
import com.banksoft.XinChengShop.ui.PayActivity;
import com.banksoft.XinChengShop.widget.MyProgressDialog;

/**
 * Created by harry_robin on 15/12/12.
 */
public class CheckPayPasswordFragment extends android.support.v4.app.DialogFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private MyProgressDialog progressDialog;
    private EditText payPasswordEdit;
    private CheckBox isShow;
    private TextView forgetPassword;
    private Button ok, cancel;
    private String memberId;
    private String orderIds;
    private  String password;
    private PayActivity payActivity;

    private CheckPasswordDao checkPasswordDao;
    private CheckListener checkListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        memberId = getArguments().getString(IntentFlag.MEMBER_ID);
        orderIds = getArguments().getString(IntentFlag.ORDER_IDS);
        payActivity = (PayActivity) getActivity();
    }

    public void setCheckListener(CheckListener checkListener) {
        this.checkListener = checkListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.check_pay_password_layout, null);
        initView(view);
        initOperation();
        return view;
    }

    private void initView(View view) {
        payPasswordEdit = (EditText) view.findViewById(R.id.login_input_password);
        isShow = (CheckBox) view.findViewById(R.id.login_switchBtn);
        forgetPassword = (TextView) view.findViewById(R.id.forget_pay_password);
        ok = (Button) view.findViewById(R.id.ok);
        cancel = (Button) view.findViewById(R.id.cancel);
    }

    private void initOperation(){
        isShow.setOnCheckedChangeListener(this);
        forgetPassword.setOnClickListener(this);
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            // 显示密码
            payPasswordEdit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        else {
            // 隐藏密码
            payPasswordEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok:
                 password = payPasswordEdit.getText().toString().trim();
                if(password.isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(),R.string.please_input_payPassword,Toast.LENGTH_SHORT).show();
                    return;
                }
                if(checkPasswordDao == null){
                    checkPasswordDao = new CheckPasswordDao(getActivity().getApplicationContext());
                }
                new MyThread().execute(checkPasswordDao);
                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }

    private class MyThread extends AsyncTask<CheckPasswordDao,String,BaseData>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progressDialog == null){
                progressDialog = new MyProgressDialog(getActivity());
            }
            progressDialog.showDialog(R.string.please_wait);
        }

        @Override
        protected BaseData doInBackground(CheckPasswordDao... params) {
            return params[0].balancePay(memberId,orderIds,password);
        }

        @Override
        protected void onPostExecute(BaseData baseData) {
            super.onPostExecute(baseData);
            progressDialog.dismiss();
            if(baseData != null){
                if(baseData.isSuccess()){
                    Intent intent = new Intent(payActivity.getApplicationContext(), OrderListActivity.class);
                    startActivity(intent);
                    checkListener.isSuccess(true);
                }else{
                    payActivity.showWarning(baseData.getMsg().toString());
                }
            }else{
                payActivity.showWarning(R.string.net_error);
            }
        }
    }

    public interface CheckListener{
        public void isSuccess(boolean success);
    }
}
