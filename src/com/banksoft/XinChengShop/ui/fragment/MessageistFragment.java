package com.banksoft.XinChengShop.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import com.banksoft.XinChengShop.adapter.ArtiCleListAdapter;
import com.banksoft.XinChengShop.adapter.MemberScoreListAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.entity.ArticleListVO;
import com.banksoft.XinChengShop.ui.TeletextActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseListFragment;
import com.banksoft.XinChengShop.utils.JSONHelper;

import java.util.ArrayList;

/**
 * Created by Robin on 2016/4/12.
 */
public class MessageistFragment extends XCBaseListFragment {
    private XCBaseActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (XCBaseActivity) getActivity();
    }

    @Override
    public void request() {
        url = ControlUrl.XC_ARTICLE_LIST_URL;
        params = "";
        jsonType = JSONHelper.XC_ARTICLE_LIST_DATA;
        bailaAdapter = new ArtiCleListAdapter(mContext, new ArrayList());

        xListView.setDividerHeight(1);
        setListDao();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Activity.RESULT_FIRST_USER) {
            switch (resultCode) {
                case Activity.RESULT_OK:  //�����ɹ�
                    request();
                    break;
                case Activity.RESULT_CANCELED: //ȡ�����
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
        return "公告详情";
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mContext, TeletextActivity.class);
        intent.putExtra("describe", ((ArticleListVO) bailaAdapter.getItem(position)).getContent());
        intent.putExtra("title",getTitle());
        startActivity(intent);
    }

    @Override
    public void onAdapterCLick(View view, int position) {

    }
}
