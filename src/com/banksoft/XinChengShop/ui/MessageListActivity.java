package com.banksoft.XinChengShop.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.fragment.MessageistFragment;

/**
 * Created by Robin on 2016/4/12.
 */
public class MessageListActivity extends XCBaseActivity implements View.OnClickListener{
    private MessageistFragment messageistFragment;
    private TextView title;
    private ImageView back;


    @Override
    protected void initContentView() {
        setContentView(R.layout.content_layout);
    }

    @Override
    protected void initView() {
       title = (TextView) findViewById(R.id.titleText);
       back = (ImageView) findViewById(R.id.title_back_button);
    }

    @Override
    protected void initData() {
       title.setText(R.string.notice);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
    }

    @Override
    protected void initOperate() {
        if(messageistFragment == null){
            messageistFragment = new MessageistFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.content,messageistFragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back_button:
                finish();
                break;
        }
    }
}
