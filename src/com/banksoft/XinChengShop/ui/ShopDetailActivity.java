package com.banksoft.XinChengShop.ui;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.ShopDetailAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.config.MapFlag;
import com.banksoft.XinChengShop.dao.ShopInfoDao;
import com.banksoft.XinChengShop.model.ShopInfoData;
import com.banksoft.XinChengShop.model.ShopProductListData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by admin on 2016/7/2.
 */
public class ShopDetailActivity extends XCBaseActivity implements View.OnClickListener {
    private TextView title;
    private ImageView back;
    private TextView shopCategory, shopIntroduction, shopLinker;
    private ShopInfoDao shopInfoDao;
    private ShopDetailAdapter shopDetailAdapter;

    private ProgressBar mProgressBar;

    private String shopId;

    @Override
    protected void initContentView() {
        setContentView(R.layout.shop_detail_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
        shopCategory = (TextView) findViewById(R.id.shop_category);
        shopIntroduction = (TextView) findViewById(R.id.shop_introduction);
        shopLinker = (TextView) findViewById(R.id.shop_linker);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    protected void initData() {
        shopId = getIntent().getStringExtra(IntentFlag.SHOP_ID);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        title.setText(R.string.shop_info_title);
    }

    @Override
    protected void initOperate() {
        if(shopInfoDao == null){
            shopInfoDao = new ShopInfoDao(mContext);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back_button:
                finish();
                break;
        }
    }


    private class MyTask extends AsyncTask<ShopInfoDao, String, HashMap> {
        private HashMap dataMap;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(dataMap == null){
                dataMap = new LinkedHashMap();
            }
        }

        @Override
        protected HashMap doInBackground(ShopInfoDao... params) {
            ShopProductListData shopProductListData = params[0].getShopProductListData(shopId,true);
            ShopInfoData shopInfoData = params[0].getShopInfoData(shopId,true);
            dataMap.put(MapFlag.DATA_0,shopProductListData);
            dataMap.put(MapFlag.DATA_1,shopInfoData);
            return dataMap;
        }

        @Override
        protected void onPostExecute(HashMap dataMap) {
            super.onPostExecute(dataMap);
            ShopProductListData shopProductListData = (ShopProductListData) dataMap.get(MapFlag.DATA_0);
            ShopInfoData shopInfoData = (ShopInfoData) dataMap.get(MapFlag.DATA_1);
            if(dataMap != null){
                if(shopProductListData.isSuccess() && shopInfoData.isSuccess()){

                }else{
                    alert(R.string.net_is_weak);
                }
            }else{
                alert(R.string.net_error);
            }
        }
    }

}
