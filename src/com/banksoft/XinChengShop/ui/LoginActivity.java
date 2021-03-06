package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.LoginDao;
import com.banksoft.XinChengShop.model.MemberData;
import com.banksoft.XinChengShop.type.MergeType;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.utils.CommonUtil;
import com.banksoft.XinChengShop.utils.JPushUtil;
import com.banksoft.XinChengShop.widget.ClearEditText;
import com.banksoft.XinChengShop.widget.MyProgressDialog;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.Tencent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;
import java.util.Set;


/**
 * Created by harry_robin on 15/11/12.
 */
public class LoginActivity extends XCBaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private TextView title;
    private EditText userNameEdit;
    private ClearEditText passwordEdit;
    private MyProgressDialog myProgressDialog;
    private CheckBox loginSwitchBtn;

    public static Tencent mTencent;
    private UserInfo mInfo;

    //    public static final String QQAppid = "1105213693";
//    public static final String WXAppid = "wxe4d53f58f26cd989";
//    public static final String SinaAppid = "";// 新浪微博
    private Button loginBtn;
    private TextView registerTextView;
    private TextView retrieveTextView;
    private ImageView qqLoginTextView;
    private ImageView weixinLoginTextView;
    private ImageView sinaLoginTextView;
    private LoginDao loginDao;

    private SHARE_MEDIA currentType;


    private UMShareAPI umShareAPI;

    private String TAG = "HarryRobin";

    private static final int MSG_SET_ALIAS = 1001;
    private static final int MSG_SET_TAGS = 1002;

    private final int THIRD_LOGIN = 1000;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
                    break;


                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };


    @Override
    protected void initContentView() {
        setContentView(R.layout.login_activity_layout);
    }

    @Override
    public void initView() {
        title = (TextView) findViewById(R.id.titleText);
        userNameEdit = (EditText) findViewById(R.id.login_input_name);
        passwordEdit = (ClearEditText) findViewById(R.id.login_input_password);
        loginBtn = (Button) findViewById(R.id.login_comfirm_button);
        registerTextView = (TextView) findViewById(R.id.register_link);
        retrieveTextView = (TextView) findViewById(R.id.login_page_find_password);
        qqLoginTextView = (ImageView) findViewById(R.id.qq_login_view);
        weixinLoginTextView = (ImageView) findViewById(R.id.weixin_login_view);
        sinaLoginTextView = (ImageView) findViewById(R.id.sina_login_view);
        loginSwitchBtn = (CheckBox) findViewById(R.id.login_switchBtn);
    }

    @Override
    public void initData() {
        title.setText(R.string.login_title);
        umShareAPI = UMShareAPI.get(getApplicationContext());

    }

    /**
     */
    /**
     * auth callback interface
     **/
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            if (loginDao == null) {
                loginDao = new LoginDao(mContext);
            }
            Log.i("HarryRobin", data.toString());
                 new ThirdLogin(data.get("openid")).execute(loginDao);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            // Toast.makeText( getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), R.string.login_cancel, Toast.LENGTH_SHORT).show();
        }
    };
    /**
     * delauth callback interface
     **/
    private UMAuthListener umdelAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "delete Authorize succeed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "delete Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "delete Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };



    @Override
    protected void initOperate() {
        registerTextView.setOnClickListener(this);
        retrieveTextView.setOnClickListener(this);
        qqLoginTextView.setOnClickListener(this);
        weixinLoginTextView.setOnClickListener(this);
        sinaLoginTextView.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        loginSwitchBtn.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Activity.RESULT_FIRST_USER) {
            if (resultCode == Activity.RESULT_OK) {
                showWarning(R.string.register_success);
//                String accountName = data.getStringExtra(IntentFlag.ACCOUNT);
//                userNameEdit.setText(accountName);
            }
        }else if(requestCode == THIRD_LOGIN){
            setResult(Activity.RESULT_OK);
            finish();
        }
//        else if (resultCode == SINA_LOGIN) {// 新浪微博登陆回掉
//            // SSO 授权回调
//            // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
//            if (sinaUtil.mSsoHandler != null) {
//                sinaUtil.mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
//            }
//        } else if (requestCode == Constants.REQUEST_LOGIN ||
//                requestCode == Constants.REQUEST_APPBAR) {
//            Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
//        }
        umShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        SHARE_MEDIA platform = null;
        switch (v.getId()) {
            case R.id.login_comfirm_button:
                String account = userNameEdit.getText().toString().trim();
                String password = passwordEdit.getText().toString().trim();
                if ("".equals(account)) {
                    showWarning(R.string.account_not_empty);
                } else if ("".equals(password)) {
                    showWarning(R.string.password_not_empty);
                }
                if (loginDao == null) {
                    loginDao = new LoginDao(mContext);
                }
                loginDao.account = account;
                loginDao.password = password;

                if (myProgressDialog == null) {
                    myProgressDialog = new MyProgressDialog(this);
                }

                myProgressDialog.showDialog(getText(R.string.please_wait).toString());
                new MyThread().execute(loginDao);
                break;
            case R.id.register_link:
                startActivityForResult(new Intent(mContext, RegisterActivity.class), Activity.RESULT_FIRST_USER);
                break;
            case R.id.login_page_find_password://忘记密码
                Intent foundPassword = new Intent(mContext, FoundPasswordActivity.class);
                startActivity(foundPassword);
                break;
            case R.id.qq_login_view://QQ登陆
                platform = SHARE_MEDIA.QQ;
                this.currentType = platform;
                umShareAPI.doOauthVerify(this, platform, umAuthListener);
                break;
            case R.id.weixin_login_view://微信登陆
                platform = SHARE_MEDIA.WEIXIN;
                this.currentType = platform;
                umShareAPI.doOauthVerify(this, platform, umAuthListener);
                break;
            case R.id.sina_login_view:// 新浪登陆
                platform = SHARE_MEDIA.SINA;
                this.currentType = platform;
                umShareAPI.doOauthVerify(this, platform, umAuthListener);
                break;
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.login_switchBtn:
                if (isChecked) {
                    passwordEdit.setInputType(EditorInfo.TYPE_TEXT_VARIATION_NORMAL);
                } else {
                    passwordEdit.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
                }

                break;
        }
    }

    private class MyThread extends AsyncTask<LoginDao, String, MemberData> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (myProgressDialog == null) {
                myProgressDialog = new MyProgressDialog(mContext);
            }
            myProgressDialog.showDialog(R.string.please_wait);
        }

        @Override
        protected MemberData doInBackground(LoginDao... params) {
            MemberData data = params[0].login();
            return data;
        }

        @Override
        protected void onPostExecute(MemberData memberData) {
            super.onPostExecute(memberData);
            myProgressDialog.dismiss();
            if (memberData != null) {
                if (memberData.isSuccess()) {
                    mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, memberData.getData().getMember().getId()));
                    saveLogin(memberData.getData());
                    setResult(RESULT_OK);//返回登陆成功状态
                    CommonUtil.operationKeyboard(getApplicationContext());
                    finish();
                } else {
                    showWarning(memberData.getMsg().toString());

                }
            } else {
                showWarning(R.string.net_error);
            }
        }
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    if (JPushUtil.isConnected(getApplicationContext())) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    } else {
                        Log.i(TAG, "No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
        }

    };


    private class ThirdLogin extends AsyncTask<LoginDao, String, MemberData> {
        String openID = "";

        public ThirdLogin(String openId) {
            this.openID = openId;
        }

        @Override
        protected MemberData doInBackground(LoginDao... params) {
            return params[0].thirdLogin(currentType, openID);
        }

        @Override
        protected void onPostExecute(MemberData memberData) {
            super.onPostExecute(memberData);
            if (memberData != null) {
                if (memberData.isSuccess()) {
                    saveLogin(memberData.getData());
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    if (memberData.getCode() == 9) { //未绑定账号
                        Intent intent = new Intent(mContext, AssociatedActivity.class);
                        intent.putExtra(IntentFlag.SHARE_MEDIA,currentType);
                        intent.putExtra(IntentFlag.OPEN_ID,openID);
                        startActivityForResult(intent,THIRD_LOGIN);
                    } else {
                        alert((String) memberData.msg);
                    }

                }
            } else {

            }
        }
    }


}
