package com.banksoft.XinChengShop.ui;

import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.type.MergeType;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

/**
 * Created by Robin on 2016/4/3.
 * 关联注册页
 */
public class AssociatedActivity extends XCBaseActivity implements View.OnClickListener{
    private ImageView imageView;

    private TextView tag;

    private TextView account;

    private Button register;// 快速创建

    private Button assisted;//关联账号

    private MergeType currentMergeType;


    @Override
    protected void initContentView() {
        setContentView(R.layout.associted_layout);
    }

    @Override
    protected void initView() {
        imageView = (ImageView) findViewById(R.id.image);
        tag = (TextView) findViewById(R.id.tag);
        account = (TextView) findViewById(R.id.account);
        register = (Button) findViewById(R.id.register);
        assisted = (Button) findViewById(R.id.associted_account);
    }

    @Override
    protected void initData() {
        currentMergeType = (MergeType) getIntent().getSerializableExtra(IntentFlag.MERGE_TYPE);
    }

    @Override
    protected void initOperate() {
        if (currentMergeType.QQ.equals(currentMergeType)) {
            tag.setText(R.string.dear_qq_account);
        } else if (currentMergeType.WEIXIN.equals(currentMergeType)) {
            tag.setText(R.string.dear_weixin_account);
        } else if (currentMergeType.WEIBO.equals(currentMergeType)) {
            tag.setText(R.string.dear_weibo_account);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }
}
