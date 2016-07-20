package com.banksoft.XinChengShop.ui;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.SelectShopCategoryDao;
import com.banksoft.XinChengShop.model.base.BaseData;
import com.banksoft.XinChengShop.type.ShopType;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

/**
 * Created by admin on 2016/7/20.
 */
public class SelectShopCategoryActivity extends XCBaseActivity {
    private TextView title;
    private ImageView back;
    private ShopType shopType;
    private ListView mListView;
    private ProgressBar progressBar;
    private SelectShopCategoryDao selectShopCategoryDao;
    private String currentNo;



    @Override
    protected void initContentView() {
        setContentView(R.layout.list_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        back.setVisibility(View.VISIBLE);
        back = (ImageView) findViewById(R.id.title_back_button);
        mListView = (ListView) findViewById(R.id.list_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    @Override
    protected void initData() {
        title.setText(R.string.please_select_category);
        shopType = (ShopType) getIntent().getSerializableExtra(IntentFlag.SHOP_TYPE);
    }

    @Override
    protected void initOperate() {

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

                }else{
                    alert(baseData.getMsg().toString());
                }
            }else{
                alert(R.string.netWork_error);
            }
        }
    }

    private void showListView(BaseData baseData){
        if(shopType.equals(ShopType.SHOP_SALE)){

        }else if(shopType.equals(ShopType.SHOP_SERVER)){

        }else if(shopType.equals(ShopType.SHOP_LOCAL)){

        }
    }
}
