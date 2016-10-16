package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.AreaListAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.SelectGoodsCategoryDao;
import com.banksoft.XinChengShop.entity.ProductTypeVO;
import com.banksoft.XinChengShop.model.ProductTypeData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

/**
 * Created by Robin on 2016/4/12.
 */
public class SelectGoodsCategoryActivity extends XCBaseActivity implements AdapterView.OnItemClickListener {
    private TextView title;
    private ProgressBar mProgressBar;
    private ListView listView;
    private SelectGoodsCategoryDao selectGoodsCategoryDao;
    private AreaListAdapter goodsCategoryAdapter;
    private int currentGrade = 1;//当前分类等级
    private String currentNo = "";//当前分类编号

    public SelectGoodsCategoryActivity() {
        super();
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.select_goods_ctegory_list_full_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.list_view);
    }

    @Override
    protected void initData() {
        title.setText(R.string.please_select_categort);
        if (selectGoodsCategoryDao == null) {
            selectGoodsCategoryDao = new SelectGoodsCategoryDao(getApplicationContext());
        }
        listView.setOnItemClickListener(this);
        new MyThread().execute(selectGoodsCategoryDao);
    }

    @Override
    protected void initOperate() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /**
     * 获取店铺商品分类
     */
    private class MyThread extends AsyncTask<SelectGoodsCategoryDao, String, ProductTypeData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ProductTypeData doInBackground(SelectGoodsCategoryDao... params) {
            return params[0].getProductTypeBOData(currentGrade, currentNo);
        }

        @Override
        protected void onPostExecute(ProductTypeData productTypeBOData) {
            super.onPostExecute(productTypeBOData);
            mProgressBar.setVisibility(View.GONE);
            if (productTypeBOData != null) {
                if (productTypeBOData.isSuccess()) {
                    if (goodsCategoryAdapter == null) {
                        goodsCategoryAdapter = new AreaListAdapter(getApplicationContext(), productTypeBOData.getData().getList());
                    }
                    goodsCategoryAdapter.dataList = productTypeBOData.getData().getList();
                    listView.setAdapter(goodsCategoryAdapter);
                    goodsCategoryAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), getText(R.string.net_is_weak), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), getText(R.string.netWork_error), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ProductTypeVO productTypeVO = (ProductTypeVO) goodsCategoryAdapter.getItem(position);
        if (productTypeVO.isLeaf()) {// 最终叶子节点
            Intent intent = new Intent();
            intent.putExtra(IntentFlag.DATA, productTypeVO);
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else {
            currentGrade = productTypeVO.getGrade()+1;
            currentNo = productTypeVO.getNo();
            goodsCategoryAdapter.dataList.clear();
            new MyThread().execute(selectGoodsCategoryDao);
        }
    }


}
