package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.SelectShopCategoryAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.SelectShopCategoryDao;
import com.banksoft.XinChengShop.entity.ShopLocalType;
import com.banksoft.XinChengShop.entity.ShopServerType;
import com.banksoft.XinChengShop.entity.ShopTypeVO;
import com.banksoft.XinChengShop.model.base.BaseData;
import com.banksoft.XinChengShop.type.ShopType;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2016/7/20.
 */
public class SelectShopCategoryActivity extends XCBaseActivity implements AdapterView.OnItemClickListener,View.OnClickListener{
    private TextView title;
    private ImageView back;
    private ShopType shopType;
    private ListView mListView;
    private ProgressBar progressBar;
    private SelectShopCategoryDao selectShopCategoryDao;
    private String currentNo = "";
    private SelectShopCategoryAdapter selectShopCategoryAdapter;
    private BaseData data;




    @Override
    protected void initContentView() {
        setContentView(R.layout.list_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
        mListView = (ListView) findViewById(R.id.list_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    protected void initData() {
        title.setText(R.string.please_select_category);
        back.setVisibility(View.VISIBLE);
        mListView.setDividerHeight(1);
        shopType = (ShopType) getIntent().getSerializableExtra(IntentFlag.SHOP_TYPE);
    }

    @Override
    protected void initOperate() {
        back.setOnClickListener(this);
        mListView.setOnItemClickListener(this);
       if(selectShopCategoryDao == null){
           selectShopCategoryDao = new SelectShopCategoryDao(mContext);
       }
       new MyTask().execute(selectShopCategoryDao);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        List dataList = (List) data.getData();
        if (shopType.equals(ShopType.SHOP_SERVER)) {
            ShopServerType shopServerType = (ShopServerType) dataList.get(position);
            if(!shopServerType.isLeaf()){
                currentNo = shopServerType.getNo();
                selectShopCategoryAdapter.dataList.clear();
                new MyTask().execute(selectShopCategoryDao);
                return;
            }
        } else if (shopType.equals(ShopType.SHOP_SALE)) {
            ShopTypeVO shopTypeVO = (ShopTypeVO) dataList.get(position);
            if(!shopTypeVO.isLeaf()){
                currentNo = shopTypeVO.getNo();
                selectShopCategoryAdapter.dataList.clear();
                new MyTask().execute(selectShopCategoryDao);
                return;
            }
        } else if (shopType.equals(ShopType.SHOP_LOCAL)) {
            ShopLocalType shopLocalType = (ShopLocalType) dataList.get(position);
            if(!shopLocalType.isLeaf()){
                currentNo = shopLocalType.getNo();
                selectShopCategoryAdapter.dataList.clear();
                new MyTask().execute(selectShopCategoryDao);
                return;
            }
        }
        Intent intent = new Intent();
        intent.putExtra(IntentFlag.DATA, (Serializable) dataList.get(position));
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back_button:
                finish();
                break;
        }
    }

    private class MyTask extends AsyncTask<SelectShopCategoryDao,String,BaseData>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected BaseData doInBackground(SelectShopCategoryDao... params) {
            BaseData data = null;
            if(shopType.equals(ShopType.SHOP_SERVER)){
               data = params[0].getShopServerTypeData(currentNo);
            }else if(shopType.equals(ShopType.SHOP_SALE)){
               data = params[0].getShopTypeVOData(currentNo);
            }else if(shopType.equals(ShopType.SHOP_LOCAL)){
               data = params[0].getShopLocalTypeData(currentNo);
            }
            return data;
        }

        @Override
        protected void onPostExecute(BaseData baseData) {
            super.onPostExecute(baseData);
            progressBar.setVisibility(View.GONE);
            if(baseData != null){
                if(baseData.isSuccess()){
                    data = baseData;
                    showListView(baseData);
                }else{
                    alert(baseData.getMsg().toString());
                }
            }else{
                alert(R.string.netWork_error);
            }
        }
    }

    private void showListView(BaseData baseData){
        if(selectShopCategoryAdapter == null){
            selectShopCategoryAdapter = new SelectShopCategoryAdapter(mContext, (List) baseData.getData());
        }
        selectShopCategoryAdapter.setShopType(shopType);
        mListView.setAdapter(selectShopCategoryAdapter);
    }


}
