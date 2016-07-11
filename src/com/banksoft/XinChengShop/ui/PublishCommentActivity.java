package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.PublishCommentAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.PublishCommentDao;
import com.banksoft.XinChengShop.entity.OrderAssessBO;
import com.banksoft.XinChengShop.entity.OrderVO;
import com.banksoft.XinChengShop.entity.ReturnProduct;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.widget.MyProgressDialog;

import java.util.List;

/**
 * Created by admin on 2016/7/11.
 */
public class PublishCommentActivity extends XCBaseActivity implements View.OnClickListener{
    private ListView mListView;
    private ProgressBar mProgressBar;
    private TextView title;
    private ImageView back;
    private PublishCommentDao publishCommentDao;
    private MyProgressDialog progressDialog;


    private PublishCommentAdapter publishCommentAdapter;
    private OrderVO currentOrderVo;

    private View footView;

    @Override
    protected void initContentView() {
        setContentView(R.layout.list_layout);
    }

    @Override
    protected void initView() {
        mListView = (ListView) findViewById(R.id.list_view);
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    protected void initData() {
        currentOrderVo = (OrderVO) getIntent().getExtras().getSerializable(IntentFlag.ORDER_VO);
        footView = LayoutInflater.from(mContext).inflate(R.layout.publish_comment_foot_layout,null);
        title.setText(R.string.publish_comment_title);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void initOperate() {
        if (publishCommentAdapter == null) {
            publishCommentAdapter = new PublishCommentAdapter(mContext, currentOrderVo.getList());
        }
        mListView.setDividerHeight(20);
        mListView.setAdapter(publishCommentAdapter);
        mListView.addFooterView(footView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back_button:
                finish();
                break;
        }
    }

    private class MyTask extends AsyncTask<PublishCommentDao, String, IsFlagData> {
        List<ReturnProduct> returnProducts;
        OrderAssessBO orderAssessBO;

        @Override
        protected void onPostExecute(IsFlagData isFlagData) {
            super.onPostExecute(isFlagData);
            progressDialog.dismiss();
            if (isFlagData != null) {
                if (isFlagData.isSuccess()) {
                    alert(R.string.comment_success);
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    alert(isFlagData.getMsg().toString());
                }
            } else {
                alert(R.string.net_error);
            }
        }

        @Override
        protected IsFlagData doInBackground(PublishCommentDao... params) {
            return params[0].submitComment(currentOrderVo.getId(), returnProducts, orderAssessBO);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = new MyProgressDialog(PublishCommentActivity.this);
            }
            progressDialog.showDialog(R.string.please_wait);
        }
    }
}
