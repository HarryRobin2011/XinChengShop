package com.banksoft.XinChengShop.ui.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.widget.Toast;
import com.banksoft.XinChengShop.config.SharedPreTag;
import com.banksoft.XinChengShop.entity.Member;
import com.banksoft.XinChengShop.model.MemberData;
import com.banksoft.XinChengShop.utils.AnimationUtil;
import com.banksoft.XinChengShop.utils.JSONHelper;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by harry_robin on 15/11/4.
 */
public abstract class XCBaseActivity extends FragmentActivity {
    public Context mContext;

    public SharedPreferences preferences;

    protected boolean animation = true;
    public Member member;
//    public String accountId = "";
//    public String companyId;
//    public String account;
//    public String password;
//    public String accountIcon;
    public String areaName, areaID;


    protected boolean canClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        preferences = getSharedPreferences(SharedPreTag.BS_QQERSHOU, MODE_PRIVATE);
        isLogin();
        isLocation();
        initContentView();
        initView();
        initData();
        initOperate();
    }

    protected abstract void initContentView();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initOperate();


    @Override
    protected void onResume() {
        super.onResume();
        this.canClick = true;
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    @Override
    public void startActivity(Intent intent) {

        super.startActivity(intent);
        if (animation) {
            AnimationUtil.setAnimationOfRight(this);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (animation) {
            AnimationUtil.setAnimationOfRight(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        if (animation) {
            AnimationUtil.setAnimationOfLeft(this);
        }
    }

    public void defaultFinish() {
        super.finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void showWarning(int resId) {
        Toast toast = Toast.makeText(mContext, resId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 80);
        toast.show();
    }

    public void showWarning(String showText) {
        Toast toast = Toast.makeText(mContext, showText, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 80);
        toast.show();
    }


    public boolean isLogin() {
        String memberJson = preferences.getString(SharedPreTag.MEMBER, "");

        if (memberJson.equals("") || memberJson.equals("")) {
            return false;
        } else {
            member =  JSONHelper.fromJSONObject(memberJson,JSONHelper.MEMBER);
            return true;
        }
    }

    public boolean isGuide() {
        boolean isGuide = preferences.getBoolean(SharedPreTag.IS_GUDIE, false);
        return isGuide;
    }

    public void saveLogin(Member member) {
        preferences.edit().putString(SharedPreTag.MEMBER, JSONHelper.toJSONString(member)).commit();
    }



    public void saveArea(String areaID, String areaName) {
        preferences.edit().putString(SharedPreTag.AREA_ID, areaID).commit();
        preferences.edit().putString(SharedPreTag.AREA_NAME, areaName).commit();
    }

    public void isLocation() {
        areaName = preferences.getString(SharedPreTag.AREA_NAME, "");
        areaID = preferences.getString(SharedPreTag.AREA_ID, "");
    }

    public void clearLogin() {
        preferences.edit().putString(SharedPreTag.MEMBER, "").commit();

    }

    public void alert(int resId){
        Toast.makeText(mContext,resId,Toast.LENGTH_SHORT).show();
    }

    public void alert(String resId){
        Toast.makeText(mContext,resId,Toast.LENGTH_SHORT).show();
    }
}
