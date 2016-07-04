package com.banksoft.XinChengShop.ui.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.CategoryGridAdapter;
import com.banksoft.XinChengShop.adapter.CategoryListAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.XCCategoryDao;
import com.banksoft.XinChengShop.entity.ProductTypeVO;
import com.banksoft.XinChengShop.model.ProductTypeData;
import com.banksoft.XinChengShop.ui.ProductListActivity;
import com.banksoft.XinChengShop.ui.XCMainActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseFragment;

/**
 * Created by harry_robin on 15/11/4.
 */
public class XCCategoryFragment extends XCBaseFragment implements AdapterView.OnItemClickListener{
    private ListView listView;
    private GridView gridView;
    private CategoryListAdapter categoryListAdapter;
    private CategoryGridAdapter categoryGridAdapter;
    private XCCategoryDao categoryDao;
    private ProgressBar progressBar;
    private MyParentAsync myParentAsync;
    private  MyChildAsync myChildAsync;
    private XCMainActivity mainActivity;

    private ImageView bgImage;

    private TextView title;
    private LinearLayout searchLayout;


    @Override
    public View initContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.category_layout, null);
    }

    @Override
    public void initView(View view) {
        mainActivity = (XCMainActivity) getActivity();

//        mainActivity.title.setText(R.string.category_title);
//        mainActivity.title.setTextColor(getResources().getColor(R.color.text_black));
//        mainActivity.leftBtn.setVisibility(View.GONE);
//        mainActivity.searchLayout.setVisibility(View.GONE);
//        mainActivity.backGround.setBackgroundResource(R.color.white);

        bgImage = (ImageView) view.findViewById(R.id.titleBg);

        title = (TextView) view.findViewById(R.id.titleText);

        searchLayout = (LinearLayout) view.findViewById(R.id.search_layout);

        listView = (ListView) view.findViewById(R.id.list_view);
        gridView = (GridView) view.findViewById(R.id.gridView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initOperation() {
        bgImage.setBackgroundResource(R.color.white);
        searchLayout.setVisibility(View.GONE);
        title.setTextColor(getResources().getColor(R.color.text_black));
        title.setText(R.string.category_title);


        listView.setOnItemClickListener(this);
        gridView.setOnItemClickListener(this);
        if (categoryDao == null) {
            categoryDao = new XCCategoryDao(mContext);
        }
        myParentAsync = new MyParentAsync();
        myParentAsync.execute(categoryDao);
        addAsync(myParentAsync);
    }

    @Override
    public Fragment getInstance() {
        return null;
    }

    @Override
    public CharSequence getTitle() {
        return getText(R.string.category_title);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.list_view:
                categoryListAdapter.setSelected(position);
                setChildView((ProductTypeVO) categoryListAdapter.getItem(position));
                break;
            case R.id.gridView:
                Intent intent = new Intent(mContext, ProductListActivity.class);
                intent.putExtra(IntentFlag.TYPE_ID, ((ProductTypeVO) categoryGridAdapter.getItem(position)).getId());
                intent.putExtra(IntentFlag.TITLE, ((ProductTypeVO) categoryGridAdapter.getItem(position)).getName());
                startActivity(intent);
                break;
        }
    }

    private class MyParentAsync extends AsyncTask<XCCategoryDao, String,ProductTypeData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ProductTypeData doInBackground(XCCategoryDao... params) {
            return params[0].getProductTypeData(1,"",true);
        }

        @Override
        protected void onPostExecute(ProductTypeData productTypeData) {
            super.onPostExecute(productTypeData);
            if (productTypeData != null) {
                if (productTypeData.isSuccess()) {
                    if (categoryListAdapter == null) {
                        categoryListAdapter = new CategoryListAdapter(mContext, productTypeData.getData().getList());
                    }
                    listView.setAdapter(categoryListAdapter);
                    setChildView((ProductTypeVO) categoryListAdapter.getItem(0));
                }
            } else {
                alert(R.string.net_error);
            }
        }
    }

    private void setChildView(ProductTypeVO productTypeVO) {
        if (productTypeVO == null) {
            return;
        }
        categoryDao.id = productTypeVO.getId();
        myChildAsync = new MyChildAsync(productTypeVO);
        myChildAsync.execute(categoryDao);
        addAsync(myChildAsync);
    }

    private class MyChildAsync extends AsyncTask<XCCategoryDao, String, ProductTypeData> {
        private ProductTypeVO productTypeVO;

        public MyChildAsync(ProductTypeVO productTypeVO){
         this.productTypeVO = productTypeVO;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
        }

        @Override
        protected ProductTypeData doInBackground(XCCategoryDao... params) {
            return params[0].getProductTypeData(2,productTypeVO.getNo(),true);
        }

        @Override
        protected void onPostExecute(ProductTypeData productTypeData) {
            super.onPostExecute(productTypeData);
            progressBar.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
            if (productTypeData != null) {
                if (productTypeData.isSuccess()) {
                    if (categoryGridAdapter == null) {
                        categoryGridAdapter = new CategoryGridAdapter(mContext,productTypeData.getData().getList());
                        gridView.setAdapter(categoryGridAdapter);
                    } else {
                        categoryGridAdapter.setDataList( productTypeData.getData().getList());
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
}
