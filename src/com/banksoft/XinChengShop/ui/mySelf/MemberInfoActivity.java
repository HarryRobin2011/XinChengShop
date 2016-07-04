package com.banksoft.XinChengShop.ui.mySelf;

import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.utils.TimeUtils;

/**
 * Created by harry_robin on 16/1/22.
 */
public class MemberInfoActivity extends XCBaseActivity implements View.OnClickListener{
    private EditText account, telPhone_contact, member_email, member_name, sex, area, birthday, member_qq;
    private Button cancellation;
    private ProgressBar progressBar;
    private TextView title;
    private ImageView back;

    @Override
    protected void initContentView() {
        setContentView(R.layout.member_info_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
        account = (EditText) findViewById(R.id.account);
        telPhone_contact = (EditText) findViewById(R.id.telPhone_contact);
        member_email = (EditText) findViewById(R.id.member_email);
        member_name = (EditText) findViewById(R.id.member_name);
        sex = (EditText) findViewById(R.id.sex);
        area = (EditText) findViewById(R.id.area);
        birthday = (EditText) findViewById(R.id.birthday);
        member_qq = (EditText) findViewById(R.id.member_qq);
        cancellation = (Button) findViewById(R.id.cancellation);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    protected void initData() {
        title.setText(R.string.member_info);
        back.setVisibility(View.VISIBLE);
        isLogin();
        setInfo();
    }

    @Override
    protected void initOperate() {
        cancellation.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancellation:
                clearLogin();
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.title_back_button:
                finish();
                break;

        }
    }

    private void setInfo(){
        account.setText(getStr(member.getMemberInfo().getAccount()));
        telPhone_contact.setText(getStr(member.getMemberInfo().getTelephone()));
        member_email.setText(getStr(member.getMemberInfo().getEmail()));
        member_name.setText(getStr(member.getMemberInfo().getTrueName()));
        sex.setText(getStr(member.getMemberInfo().getSex()));
        area.setText(member.getMemberInfo().getRegion());
        birthday.setText(TimeUtils.getTimeStr(member.getMemberInfo().getBirthday(), TimeUtils.TimeType.Day));
        member_qq.setText(getStr(member.getMemberInfo().getQq()));

    }

    private String getStr(String str){
      if(str == null){
          return "";
      }else if(str.equals("null")){
        return "";
      }
        return str;
    }



}
