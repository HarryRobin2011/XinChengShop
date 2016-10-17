package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.type.MergeType;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by Robin on 2016/4/3.
 * 关联注册页
 */
public class AssociatedActivity extends XCBaseActivity implements View.OnClickListener {
    private ImageView imageView;

    private TextView tag;

    private TextView account;

    private Button register;// 快速创建

    private Button assisted;//关联账号

    private SHARE_MEDIA currentMergeType;
    private MergeType mergeType;

    private String openid;
    private ImageLoader mImageLoader;

    private UMShareAPI umShareAPI;
    private TextView title;


    @Override
    protected void initContentView() {
        setContentView(R.layout.associted_layout);
    }

    @Override
    protected void initView() {
        imageView = (ImageView) findViewById(R.id.image);
        tag = (TextView) findViewById(R.id.tag);
        account = (TextView) findViewById(R.id.name);
        register = (Button) findViewById(R.id.register);
        assisted = (Button) findViewById(R.id.associted_account);
        title = (TextView) findViewById(R.id.titleText);
    }

    @Override
    protected void initData() {
        currentMergeType = (SHARE_MEDIA) getIntent().getSerializableExtra(IntentFlag.SHARE_MEDIA);
        mImageLoader = ImageLoader.getInstance();

        umShareAPI = UMShareAPI.get(this);

        umShareAPI.getPlatformInfo(this,currentMergeType,umInfoListener);
        title.setText(R.string.combined_login);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Activity.RESULT_FIRST_USER){
            if(resultCode == Activity.RESULT_OK){
                setResult(Activity.RESULT_OK);
                finish();
            }
        }
    }

    @Override
    protected void initOperate() {
        if (SHARE_MEDIA.QQ.equals(currentMergeType)) {
            tag.setText(R.string.dear_qq_account);
            mergeType = MergeType.QQ;
        } else if (SHARE_MEDIA.WEIXIN.equals(currentMergeType)) {
            tag.setText(R.string.dear_weixin_account);
            mergeType = MergeType.WEIXIN;
        } else if (SHARE_MEDIA.SINA.equals(currentMergeType)) {
            tag.setText(R.string.dear_weibo_account);
            mergeType =MergeType.WEIBO;
        }
        register.setOnClickListener(this);
        assisted.setOnClickListener(this);
    }

    /**
     * info callback interface
     **/
    private UMAuthListener umInfoListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Log.d("auth callbacl", "getting data" + data.toString());
            String name = null;
            String headImage = null;
            if (data != null) {
                openid = data.get("openid");
                if (SHARE_MEDIA.QQ.equals(platform)) {//QQ登陆
                     name = data.get("screen_name");
                     headImage = data.get("profile_image_url");
                } else if (SHARE_MEDIA.WEIXIN.equals(platform)) {//微信登陆
                    name = data.get("nickname");
                    headImage = data.get("headimgurl");
                } else if (SHARE_MEDIA.SINA.equals(platform)) {// 新浪登陆
                    name = data.get("screen_name");
                    headImage = data.get("profile_image_url");
                }
            }
            account.setText(name);
            mImageLoader.displayImage(headImage,imageView, XCApplication.options);

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "get fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "get cancel", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                Intent regIntent = new Intent(mContext,RegisterActivity.class);
                regIntent.putExtra(IntentFlag.TYPE,mergeType);
                regIntent.putExtra(IntentFlag.OPEN_ID,openid);
                startActivityForResult(regIntent, Activity.RESULT_FIRST_USER);

                break;
            case R.id.associted_account:
                Intent intent = new Intent(mContext,BindingLoginActivity.class);
                intent.putExtra(IntentFlag.TYPE,mergeType);
                intent.putExtra(IntentFlag.OPEN_ID,openid);
                startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                break;
        }
    }
}
