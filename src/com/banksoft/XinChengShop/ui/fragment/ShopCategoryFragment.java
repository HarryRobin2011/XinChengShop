package com.banksoft.XinChengShop.ui.fragment;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.ShopCategoryAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.ShopCategoryDao;
import com.banksoft.XinChengShop.model.base.BaseData;
import com.banksoft.XinChengShop.type.ShopType;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseFragment;

import java.util.List;

/**
 * Created by harry_robin on 15/12/2.
 */
public class ShopCategoryFragment extends XCBaseFragment implements AdapterView.OnItemClickListener{
    private RelativeLayout titleLayout;
    private ListView listView;
    private ProgressBar mProgressBar;
    private ShopCategoryAdapter shopCategoryAdapter;
    private ShopCategoryDao shopCategoryDao;
    private ShopType shopType;
    private String no;
    private XCBaseActivity activity;
    private LinearLayout searchLayout;
    private Button rigjtBtn;

    @Override
    public View initContentView() {
        activity = (XCBaseActivity) getActivity();
        return LayoutInflater.from(mContext).inflate(R.layout.list_layout,null);
    }

    @Override
    public void initView(View view) {
        titleLayout = (RelativeLayout) view.findViewById(R.id.title_layout);
        listView = (ListView) view.findViewById(R.id.list_view);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        searchLayout = (LinearLayout) view.findViewById(R.id.search_layout);
        rigjtBtn = (Button) view.findViewById(R.id.titleRightButton);
    }

    @Override
    public void initData() {
        searchLayout.setVisibility(View.GONE);
        rigjtBtn.setVisibility(View.GONE);
        shopType = (ShopType) getArguments().get(IntentFlag.SHOP_TYPE);
        no = (String) getArguments().get(IntentFlag.NO);
    }

    @Override
    public void initOperation() {

    }

    @Override
    public Fragment getInstance() {
        return null;
    }

    @Override
    public CharSequence getTitle() {
        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private class MyThread extends AsyncTask<ShopCategoryDao,String,BaseData>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected BaseData doInBackground(ShopCategoryDao... params) {
            BaseData data = params[0].getCategoryData(shopType,no,true);
            return data;
        }

        @Override
        protected void onPostExecute(BaseData baseData) {
            super.onPostExecute(baseData);
            mProgressBar.setVisibility(View.GONE);
            if(baseData != null){
                if(baseData.isSuccess()){
                    if(shopCategoryAdapter == null){
                        shopCategoryAdapter = new ShopCategoryAdapter(mContext,(List)baseData.getData());
                    }
                    listView.setAdapter(shopCategoryAdapter);
                }else{
                    activity.showWarning(R.string.netWork_warning);
                }

            }else{
                activity.showWarning(R.string.net_error);
            }
        }
    }
}
