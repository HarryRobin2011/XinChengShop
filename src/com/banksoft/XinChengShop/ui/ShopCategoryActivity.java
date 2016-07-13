package com.banksoft.XinChengShop.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.CategoryShopGridAdapter;
import com.banksoft.XinChengShop.adapter.CategoryShopListAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.XCShopCategoryDao;
import com.banksoft.XinChengShop.entity.ProductTypeVO;
import com.banksoft.XinChengShop.entity.ShopProductTypeBO;
import com.banksoft.XinChengShop.model.ShopProductTypeBOData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

/**
 * Created by harry_robin on 15/11/24.
 */
public class ShopCategoryActivity extends XCBaseActivity implements AdapterView.OnItemClickListener{
    private ListView listView;
    private GridView gridView;
    private CategoryShopListAdapter categoryListAdapter;
    private CategoryShopGridAdapter categoryGridAdapter;
    private XCShopCategoryDao categoryDao;
    private ProgressBar progressBar;
    private MyParentAsync myParentAsync;
    private MyChildAsync myChildAsync;
    private XCMainActivity mainActivity;

    private ImageView bgImage;

    private TextView title;
    private LinearLayout searchLayout;

    private String shopId;
    private TextView alertText;
    @Override
    protected void initContentView() {
        setContentView(R.layout.category_layout);
    }

    @Override
    protected void initView() {
        bgImage = (ImageView) findViewById(R.id.titleBg);

        title = (TextView) findViewById(R.id.titleText);

        searchLayout = (LinearLayout) findViewById(R.id.search_layout);

        listView = (ListView) findViewById(R.id.list_view);
        gridView = (GridView) findViewById(R.id.gridView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        alertText = (TextView) findViewById(R.id.alertText);
    }

    @Override
    protected void initData() {
      shopId = getIntent().getStringExtra(IntentFlag.SHOP_ID);
    }

    @Override
    protected void initOperate() {
        bgImage.setBackgroundResource(R.color.white);
        searchLayout.setVisibility(View.GONE);
        title.setTextColor(getResources().getColor(R.color.text_black));
        title.setText(R.string.category_title);


        listView.setOnItemClickListener(this);
        gridView.setOnItemClickListener(this);
        if (categoryDao == null) {
            categoryDao = new XCShopCategoryDao(mContext);
        }
        myParentAsync = new MyParentAsync();
        myParentAsync.execute(categoryDao);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.list_view:
                categoryListAdapter.setSelected(position);
                setChildView((ShopProductTypeBO) categoryListAdapter.getItem(position));
                break;
            case R.id.gridView:
                Intent intent = new Intent(mContext, ProductListActivity.class);
                intent.putExtra(IntentFlag.TYPE_ID, ((ProductTypeVO) categoryGridAdapter.getItem(position)).getId());
                intent.putExtra(IntentFlag.TITLE, ((ProductTypeVO) categoryGridAdapter.getItem(position)).getName());
                startActivity(intent);
                break;
        }
    }


    private class MyParentAsync extends AsyncTask<XCShopCategoryDao, String,ShopProductTypeBOData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ShopProductTypeBOData doInBackground(XCShopCategoryDao... params) {
            return params[0].getProductTypeData(shopId,1,true);
        }

        @Override
        protected void onPostExecute(ShopProductTypeBOData productTypeData) {
            super.onPostExecute(productTypeData);
            progressBar.setVisibility(View.GONE);
            if (productTypeData != null) {
                if (productTypeData.isSuccess()) {
                    if(productTypeData.getData().size() > 0){
                        if (categoryListAdapter == null) {
                            categoryListAdapter = new CategoryShopListAdapter(mContext, productTypeData.getData());
                        }
                        listView.setAdapter(categoryListAdapter);
                        setChildView((ShopProductTypeBO) categoryListAdapter.getItem(0));
                        alertText.setVisibility(View.GONE);
                    }else{
                        alertText.setVisibility(View.VISIBLE);
                    }

                }
            } else {
                alert(R.string.net_error);
            }
        }
    }
   

    private class MyChildAsync extends AsyncTask<XCShopCategoryDao, String, ShopProductTypeBOData> {
        private ShopProductTypeBO productTypeVO;

        public MyChildAsync(ShopProductTypeBO productTypeVO){
            this.productTypeVO = productTypeVO;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
        }

        @Override
        protected ShopProductTypeBOData doInBackground(XCShopCategoryDao... params) {
            return params[0].getProductTypeData(shopId,2,true);
        }

        @Override
        protected void onPostExecute(ShopProductTypeBOData productTypeData) {
            super.onPostExecute(productTypeData);
            progressBar.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
            if (productTypeData != null) {
                if (productTypeData.isSuccess()) {
                    if (categoryGridAdapter == null) {
                        categoryGridAdapter = new CategoryShopGridAdapter(mContext,productTypeData.getData());
                        gridView.setAdapter(categoryGridAdapter);
                    } else {
                        categoryGridAdapter.setDataList( productTypeData.getData());
                        categoryGridAdapter.notifyDataSetChanged();
                    }

                } else {
                    alert(R.string.netWork_warning);
                }
            } else {
                alert(R.string.net_error);
            }
        }
    }

    private void setChildView(ShopProductTypeBO shopProductTypeBO) {
        if (categoryDao == null) {
            return;
        }
        categoryDao.id = shopProductTypeBO.getId();
        myChildAsync = new MyChildAsync(shopProductTypeBO);
        myChildAsync.execute(categoryDao);
    }
}
