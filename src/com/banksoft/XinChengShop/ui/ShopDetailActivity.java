package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.banksoft.XinChengShop.entity.ShopProductListVO;
import com.banksoft.XinChengShop.entity.ShopProductTypeBO;
import com.banksoft.XinChengShop.entity.ShopVO;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.model.ShopInfoData;
import com.banksoft.XinChengShop.model.ShopProductListData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.utils.CommonUtil;
import com.banksoft.XinChengShop.utils.ShareUmengUtil;
import com.banksoft.XinChengShop.utils.ShareUtil;
import com.banksoft.XinChengShop.widget.MyGridView;
import com.banksoft.XinChengShop.widget.MyProgressDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by admin on 2016/7/2.
 */
public class ShopDetailActivity extends XCBaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
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
    private IsFlagData isCollect;// 是否收藏该店铺
    private MyProgressDialog progressDialog;

    @Override
    protected void initContentView() {
        setContentView(R.layout.shop_detail_layout);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Activity.RESULT_FIRST_USER){
            if(resultCode == Activity.RESULT_OK){
                isCollect();
            }

        }
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

    private void isCollect(){
        if(!isLogin()){
            return;
        }
        new IsCollcetTask().execute(shopInfoDao);
    }

    /**
     * 判断是否收藏
     */
    private class IsCollcetTask extends AsyncTask<ShopInfoDao,String,IsFlagData>{
        @Override
        protected void onPostExecute(IsFlagData isFlagData) {
            super.onPostExecute(isFlagData);
            isCollect = isFlagData;
            if(isFlagData != null){
                if(isFlagData.isSuccess()){
                    collect.setText(R.string.already_collect);
                }else{
                    collect.setText(R.string.collect);
                }
            }else{
                alert(R.string.net_error);
            }
        }

        @Override
        protected IsFlagData doInBackground(ShopInfoDao... params) {
            return params[0].isCollect(shopId,member.getMember().getId());
        }
    }

    /**
     * 收藏店铺
     */
    private class CollectTask extends  AsyncTask<ShopInfoDao,String,IsFlagData>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progressDialog == null){
                progressDialog = new MyProgressDialog(ShopDetailActivity.this);
            }

            progressDialog.showDialog(R.string.please_wait);
        }

        @Override
        protected IsFlagData doInBackground(ShopInfoDao... params) {
            return params[0].collectShop(shopId,member.getMember().getId());
        }

        @Override
        protected void onPostExecute(IsFlagData isFlagData) {
            super.onPostExecute(isFlagData);
            progressDialog.dismiss();
            if(isFlagData != null){

                if(isFlagData.isSuccess()){
                    isCollect.setSuccess(true);
                    alert(R.string.collect_success);
                    collect.setText(R.string.already_collect);
                }else{
                    alert((String) isFlagData.getMsg());
                }
            }else{
                alert(R.string.net_error);
            }
        }
    }
    /**
     * 取消收藏店铺
     */
    private class CancelCollectTask extends  AsyncTask<ShopInfoDao,String,IsFlagData>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progressDialog == null){
                progressDialog = new MyProgressDialog(ShopDetailActivity.this);
            }
            progressDialog.showDialog(R.string.please_wait);
        }

        @Override
        protected IsFlagData doInBackground(ShopInfoDao... params) {
            return params[0].collectDelectShop(shopId,member.getMember().getId());
        }

        @Override
        protected void onPostExecute(IsFlagData isFlagData) {
            super.onPostExecute(isFlagData);
            progressDialog.dismiss();
            if(isFlagData != null){
                if(isFlagData.isSuccess()){
                    isCollect.setSuccess(false);
                    alert(R.string.already_cancel);
                    collect.setText(R.string.collect);
                }else{
                    alert((String) isFlagData.getMsg());
                }
            }else{
                alert(R.string.net_error);
            }
        }
    }



    @Override
    protected void initData() {
        if(shopInfoDao == null){
            shopInfoDao = new ShopInfoDao(mContext);
        }
        share.setVisibility(View.VISIBLE);
        mImageLoadrer = ImageLoader.getInstance();

        shopId = getIntent().getStringExtra(IntentFlag.SHOP_ID);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        title.setText(R.string.shop_info_title);


    }

    @Override
    protected void initOperate() {
        shopLinker.setOnClickListener(this);
        shopIntroduction.setOnClickListener(this);
        shopCategory.setOnClickListener(this);
        share.setOnClickListener(this);
        collect.setOnClickListener(this);
        gridView.setOnItemClickListener(this);
        isCollect();
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
                    try {
                        CommonUtil.openQQ(mContext, shopVO.getQq());
                    } catch (Exception e) {
                        alert(R.string.qq_not_installed);
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.shop_introduction:

                break;
            case R.id.share:
               // ShareUtil.shareMsg(this,getText(R.string.app_name).toString(),shopVO.getName(),shopVO.getDescription(),0);
                ShareUmengUtil shareUmengUtil = new ShareUmengUtil(ShopDetailActivity.this);
                shareUmengUtil.setShareContext(shopVO.getName());
                String imageUrl = "";
                if(shopVO.getLogo() != null && !shopVO.getLogo().isEmpty()){
                    imageUrl = ControlUrl.BASE_URL+shopVO.getLogo().split("\\|")[0];
                }
                shareUmengUtil.setShareImage(imageUrl);
                shareUmengUtil.show();
                break;
            case R.id.collect_btn:
                if(!isLogin()){
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                }else{
                    if(isCollect != null){
                      if(isCollect.isSuccess()){// 取消收藏
                          new CancelCollectTask().execute(shopInfoDao);
                      }else{//收藏
                          new CollectTask().execute(shopInfoDao);
                      }
                    }
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mContext,ShopProductInfoActivity.class);
        intent.putExtra(IntentFlag.SHOP_ID,((ShopProductListVO)shopDetailAdapter.getItem(position)).getShopId());
        intent.putExtra(IntentFlag.PRODUCT_ID,((ShopProductListVO)shopDetailAdapter.getItem(position)).getId());
        startActivity(intent);
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
