package com.banksoft.XinChengShop.ui.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.CheckPasswordDao;
import com.banksoft.XinChengShop.entity.BalanceRecord;
import com.banksoft.XinChengShop.entity.MemberVO;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.model.MemberVOData;
import com.banksoft.XinChengShop.model.base.BaseData;
import com.banksoft.XinChengShop.ui.OrderListActivity;
import com.banksoft.XinChengShop.ui.PayActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseFragment;
import com.banksoft.XinChengShop.widget.MyProgressDialog;

/**
 * Created by Robin on 2016/9/12.
 */
public class CheckPay2PasswordFragment extends android.support.v4.app.DialogFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private MyProgressDialog progressDialog;
    private EditText payPasswordEdit;
    private CheckBox isShow;
    private TextView forgetPassword;
    private Button ok, cancel;
    private String memberId;
    private String password;
    //  private PayActivity payActivity;

    private CheckPasswordDao checkPasswordDao;
    private CheckPay2PasswordFragment.CheckListener checkListener;

    private BalanceRecord balanceRecord;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        memberId = getArguments().getString(IntentFlag.MEMBER_ID);
    }

    public void setCheckListener(CheckPay2PasswordFragment.CheckListener checkListener) {
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

    private void initOperation() {
        balanceRecord = (BalanceRecord) getArguments().get(IntentFlag.DATA);
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
        } else {
            // 隐藏密码
            payPasswordEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok:
                password = payPasswordEdit.getText().toString().trim();
                if (password.isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.please_input_payPassword, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (checkPasswordDao == null) {
                    checkPasswordDao = new CheckPasswordDao(getActivity().getApplicationContext());
                }
                new CheckPay2PasswordFragment.MyThread().execute(checkPasswordDao);
                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }

    private class MyThread extends AsyncTask<CheckPasswordDao, String, IsFlagData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = new MyProgressDialog(getActivity());
            }
            progressDialog.showDialog(R.string.please_wait);
        }

        @Override
        protected IsFlagData doInBackground(CheckPasswordDao... params) {
            return params[0].applyWithDraw(balanceRecord.getMemberId(), balanceRecord, password);
        }

        @Override
        protected void onPostExecute(IsFlagData baseData) {
            super.onPostExecute(baseData);
            progressDialog.dismiss();
            if (baseData != null) {
                if (baseData.isSuccess()) {
                    checkListener.isSuccess(true, null);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), baseData.getMsg().toString(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity().getApplicationContext(), R.string.net_error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public interface CheckListener {
        public void isSuccess(boolean success, MemberVO memberVO);
    }
}
