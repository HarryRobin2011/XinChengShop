package com.banksoft.XinChengShop.ui;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.adapter.ShopDetailAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.config.MapFlag;
import com.banksoft.XinChengShop.dao.ShopInfoDao;
import com.banksoft.XinChengShop.entity.ShopProductTypeBO;
import com.banksoft.XinChengShop.entity.ShopVO;
import com.banksoft.XinChengShop.model.ShopInfoData;
import com.banksoft.XinChengShop.model.ShopProductListData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.utils.CommonUtil;
import com.banksoft.XinChengShop.utils.ShareUtil;
import com.banksoft.XinChengShop.widget.MyGridView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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

    private MyGridView gridView;

    private ImageView headImage;
    private ImageView logoImage;
    private TextView headShopName;
    private Button collect;

    private ImageView share;


    private ImageLoader mImageLoadrer;

    private ShopVO shopVO;

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
        gridView = (MyGridView) findViewById(R.id.gridView);
        share = (ImageView) findViewById(R.id.share);

        headImage = (ImageView) findViewById(R.id.image);
        logoImage = (ImageView) findViewById(R.id.logo);
        headShopName = (TextView) findViewById(R.id.name);
        collect = (Button) findViewById(R.id.collect_btn);
    }

    @Override
    protected void initData() {
        share.setVisibility(View.VISIBLE);
        mImageLoadrer = ImageLoader.getInstance();
        mImageLoadrer.init(ImageLoaderConfiguration.createDefault(mContext));

        shopId = getIntent().getStringExtra(IntentFlag.SHOP_ID);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        title.setText(R.string.shop_info_title);
        shopLinker.setOnClickListener(this);
        shopIntroduction.setOnClickListener(this);
        shopCategory.setOnClickListener(this);
        share.setOnClickListener(this);

    }

    @Override
    protected void initOperate() {
        if(shopInfoDao == null){
            shopInfoDao = new ShopInfoDao(mContext);
        }
        new MyTask().execute(shopInfoDao);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back_button:
                finish();
                break;
            case R.id.shop_category:
                Intent categoryIntent = new Intent(mContext,ShopCategoryActivity.class);
                categoryIntent.putExtra(IntentFlag.SHOP_ID,shopId);
                startActivity(categoryIntent);
                break;
            case R.id.shop_linker:
                if(shopVO != null){
                    CommonUtil.openQQ(mContext, shopVO.getQq());
                }
                break;
            case R.id.shop_introduction:

                break;
            case R.id.share:
                ShareUtil.shareMsg(this,getText(R.string.app_name).toString(),shopVO.getName(),shopVO.getDescription(),0);
                break;
        }
    }


    private class MyTask extends AsyncTask<ShopInfoDao, String, HashMap> {
        private HashMap dataMap;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
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
            mProgressBar.setVisibility(View.GONE);
            ShopProductListData shopProductListData = (ShopProductListData) dataMap.get(MapFlag.DATA_0);
            ShopInfoData shopInfoData = (ShopInfoData) dataMap.get(MapFlag.DATA_1);
            if(dataMap != null){
                if(shopProductListData.isSuccess() && shopInfoData.isSuccess()){
                    shopVO = shopInfoData.getData();
                    showHeadView(shopVO);
                    showProductView(shopProductListData);
                }else{
                    alert(R.string.net_is_weak);
                }
            }else{
                alert(R.string.net_error);
            }
        }
    }

    private void showProductView(ShopProductListData shopProductListData){
        if(shopDetailAdapter == null){
            shopDetailAdapter = new ShopDetailAdapter(mContext,shopProductListData.getData().getList());
        }
        gridView.setAdapter(shopDetailAdapter);
    }

    private void showHeadView(ShopVO shopVO){
        mImageLoadrer.displayImage(ControlUrl.BASE_URL+shopVO.getBanner(),headImage,XCApplication.options);
        mImageLoadrer.displayImage(ControlUrl.BASE_URL+shopVO.getLogo(),logoImage, XCApplication.options);
        headShopName.setText(shopVO.getName());
        collect.setOnClickListener(this);
    }

}
