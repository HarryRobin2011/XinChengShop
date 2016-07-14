package com.banksoft.XinChengShop.ui.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.baidu.location.BDLocation;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.adapter.BannerViewPagerAdapter;
import com.banksoft.XinChengShop.adapter.HomeDataListAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.config.MapFlag;
import com.banksoft.XinChengShop.dao.HomeDao;
import com.banksoft.XinChengShop.entity.Advertisement;
import com.banksoft.XinChengShop.model.AdvertisementData;
import com.banksoft.XinChengShop.model.ProductTypeData;
import com.banksoft.XinChengShop.model.ShopProductListData;
import com.banksoft.XinChengShop.model.base.BaseData;
import com.banksoft.XinChengShop.ui.*;
import com.banksoft.XinChengShop.ui.base.XCBaseFragment;
import com.banksoft.XinChengShop.ui.localservice.LocalServiceMainActivity;
import com.banksoft.XinChengShop.ui.takeout.TakeOutMainActivity;
import com.banksoft.XinChengShop.utils.LocationUtils;
import com.banksoft.XinChengShop.utils.update.UpdateUtil;
import com.banksoft.XinChengShop.widget.ClearEditText;
import com.banksoft.XinChengShop.widget.XListView;
import com.banksoft.XinChengShop.widget.imagezoom.easing.Linear;
import com.banksoft.XinChengShop.widget.rollviewpager.RollPagerView;
import com.banksoft.XinChengShop.widget.rollviewpager.hintview.ColorPointHintView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by harry_robin on 15/11/4.
 */
public class XCHomeFragment extends XCBaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener, LocationUtils.LocationListener, XListView.IXListViewListener {
    private LinearLayout xcTakeout, xcBbs, xcQingcang, xcLocationService, xcTechan, xcxcIntegralMall,
            contentAnnounce, shoppingLayout, mySelfLayout;
    private BannerViewPagerAdapter bannerViewPagerAdapter;


    private HomeDao homeDao;

    private final int BANNER = 0;
    private final int LOCATION = 1;
    private final int BANNER_TIME = 3000;//广告切换时间

    private RollPagerView rollPagerView;

    private XCMainActivity activity;
    private ProgressBar progressBar;


    private List<Advertisement> bannersList;// banner 数量

    private boolean cacheFlag = true;

    private ClearEditText searchText;

    private String lng = "119.431221", lat = "35.991195";//纬度
    private String radius = 1000000 + "";

    private XListView mListView;


    private ImageView salesPromotionImage;

    private ImageLoader mImageLoader;

    private XCMainActivity mainActivity;

    private LinearLayout searchLayout;

    private ImageView bgImage;

    private TextView title;


    private View bannerView;

    private LinkedList dataList;// home 数据list

    private LinearLayout mesageLayout;

    private HomeDataListAdapter homeDataListAdapter;



    final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case BANNER:

                    break;
                case LOCATION:
                    LocationUtils locationUtils = new LocationUtils(XCHomeFragment.this);
                    locationUtils.startLocation();
                    break;
            }

        }
    };


    @Override
    public View initContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.home_fragment_layout, null);
    }

    @Override
    public void initView(View view) {

        bannerView = LayoutInflater.from(mContext).inflate(R.layout.home_banner_layout, null);

        rollPagerView = (RollPagerView) bannerView.findViewById(R.id.rollPagerView);

        xcTakeout = (LinearLayout) bannerView.findViewById(R.id.xc_takeout);
        xcBbs = (LinearLayout) bannerView.findViewById(R.id.xc_bbs_layout);
        xcLocationService = (LinearLayout) bannerView.findViewById(R.id.xc_location_service);
        xcQingcang = (LinearLayout) bannerView.findViewById(R.id.xc_qing_cang);
        xcTechan = (LinearLayout) bannerView.findViewById(R.id.xc_techan);
        xcxcIntegralMall = (LinearLayout) bannerView.findViewById(R.id.xc_integral_mall);
        contentAnnounce = (LinearLayout) bannerView.findViewById(R.id.content_announce);
        shoppingLayout = (LinearLayout) bannerView.findViewById(R.id.xc_shop_ing);
        mySelfLayout = (LinearLayout) bannerView.findViewById(R.id.my_self);

        salesPromotionImage = (ImageView) bannerView.findViewById(R.id.sales_promotion_image);

        searchLayout = (LinearLayout) view.findViewById(R.id.search_layout);

        bgImage = (ImageView) view.findViewById(R.id.titleBg);

        title = (TextView) view.findViewById(R.id.titleText);


        searchText = (ClearEditText) view.findViewById(R.id.search_edit);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        mListView = (XListView) view.findViewById(R.id.list_view);
        mesageLayout = (LinearLayout) view.findViewById(R.id.message_layout);
     //   leftBtn = (Button) view.findViewById(R.id.titleLeftButton);

       new UpdateUtil(getActivity(),false).isUpdate();
    }


    @Override
    public void initData() {
        mainActivity = (XCMainActivity) getActivity();
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        // messageLayout.setVisibility(View.VISIBLE);

        mListView.addHeaderView(bannerView);
        mListView.setAdapter(null);

     ///  leftBtn.setVisibility(View.VISIBLE);
      // leftBtn.setOnClickListener(this);
    }

    @Override
    public void initOperation() {
        bgImage.setBackgroundResource(R.color.bg_red);
        searchLayout.setVisibility(View.VISIBLE);
        xcTakeout.setOnClickListener(this);
        xcBbs.setOnClickListener(this);
        xcLocationService.setOnClickListener(this);
        xcQingcang.setOnClickListener(this);
        xcTechan.setOnClickListener(this);
        xcxcIntegralMall.setOnClickListener(this);
        shoppingLayout.setOnClickListener(this);
        mySelfLayout.setOnClickListener(this);

        mListView.setPullLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setPullRefreshEnable(false);

        searchText.setOnClickListener(this);
        mesageLayout.setOnClickListener(this);

        HomeDao homeDao = new HomeDao(mContext);
        new HomeThread().execute(homeDao);
    }

    /**
     * 定位成功回调
     *
     * @param location
     */
    @Override
    public void setLocation(BDLocation location) {
        if (location != null) {
            this.lng = String.valueOf(location.getLongitude());
            this.lat = String.valueOf(location.getLatitude());
        }
    }

    /**
     * xListView 刷新方法
     */
    @Override
    public void onRefresh() {
        cacheFlag = false;
        bannerViewPagerAdapter.dataList.clear();
        homeDataListAdapter.dataList.clear();
        if(homeDao == null){
           homeDao = new HomeDao(mContext);
        }
        new HomeThread().execute(homeDao);
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onLoad() {
        mListView.stopLoadMore();
        mListView.stopRefresh();
        mListView.setRefreshTime();
    }


    /**
     * 请求首页数据
     */
    private class HomeThread extends AsyncTask<HomeDao, String, HashMap<String, BaseData>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected HashMap<String, BaseData> doInBackground(HomeDao... params) {
            AdvertisementData advertisementData = params[0].getAdvertisementData(true);
            ProductTypeData productTypeData = params[0].getProductTypeListData(Integer.MAX_VALUE, 0, cacheFlag);
            ShopProductListData shopListData = params[0].getShopProductListData(cacheFlag, 0, 20, 1);
            HashMap<String, BaseData> dataHashMap = new HashMap<>();
            dataHashMap.put(MapFlag.DATA_0, advertisementData);
            dataHashMap.put(MapFlag.DATA_1, productTypeData);
            dataHashMap.put(MapFlag.DATA_2,shopListData);
            return dataHashMap;
        }

        @Override
        protected void onPostExecute(HashMap<String, BaseData> dataHashMap) {
            super.onPostExecute(dataHashMap);
            progressBar.setVisibility(View.GONE);
            setHomeFragment(dataHashMap);

        }
    }

    private void setHomeFragment(HashMap<String, BaseData> dataHashMap) {
        AdvertisementData advertisementData = (AdvertisementData) dataHashMap.get(MapFlag.DATA_0);
        ProductTypeData productTypeData = (ProductTypeData) dataHashMap.get(MapFlag.DATA_1);
        ShopProductListData shopProductListData = (ShopProductListData) dataHashMap.get(MapFlag.DATA_2);
        if (advertisementData != null) {
            bannersList = advertisementData.getData();
            bannerViewPagerAdapter = new BannerViewPagerAdapter(mContext, bannersList);
            rollPagerView.setAdapter(bannerViewPagerAdapter);
            rollPagerView.setHintView(new ColorPointHintView(mContext, getResources().getColor(R.color.bg_red), getResources().getColor(R.color.white)));
            String imageUrl = ControlUrl.BASE_URL + bannersList.get(bannersList.size() - 1).getImageUrl();
            mImageLoader.displayImage(imageUrl, salesPromotionImage, XCApplication.options);

            if(dataList == null){
               dataList = new LinkedList();
            }
            dataList.add(0,productTypeData);
            dataList.add(1,shopProductListData);

            if(homeDataListAdapter == null){
                homeDataListAdapter = new HomeDataListAdapter(mContext,dataList);
            }
            mListView.setAdapter(homeDataListAdapter);

        }
    }

    @Override
    public Fragment getInstance() {
        return null;
    }

    @Override
    public CharSequence getTitle() {
        return getText(R.string.home_page);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.xc_takeout://美食外卖
                Intent takeoutIntent = new Intent(mContext, TakeOutMainActivity.class);
                startActivity(takeoutIntent);
                break;
            case R.id.xc_bbs_layout://鑫诚论坛



                break;
            case R.id.xc_location_service://本地服务
                Intent localServiceIntent = new Intent(mContext, LocalServiceMainActivity.class);
                startActivity(localServiceIntent);
                break;
            case R.id.xc_qing_cang://清仓甩卖
                Intent qingcangIntent = new Intent(mContext, ProductListActivity.class);
                qingcangIntent.putExtra(IntentFlag.ACTIVE, "1");
                qingcangIntent.putExtra(IntentFlag.TITLE, "");
                startActivity(qingcangIntent);
                break;
            case R.id.xc_techan://本地特产
                Intent techantIntent = new Intent(mContext, ProductListActivity.class);
                techantIntent.putExtra(IntentFlag.SPECIAL, true);
                techantIntent.putExtra(IntentFlag.TITLE, "");
                startActivity(techantIntent);
                break;
            case R.id.xc_integral_mall://积分商城
                Intent jifenIntent = new Intent(mContext, IntegralMallActivity.class);
                jifenIntent.putExtra(IntentFlag.TITLE, "");
                startActivity(jifenIntent);
                break;

            case R.id.xc_shop_ing:
                Intent shopingIntent = new Intent(mContext, ProductListActivity.class);
                shopingIntent.putExtra(IntentFlag.ACTIVE, "0");
                shopingIntent.putExtra(IntentFlag.TITLE, "");
                startActivity(shopingIntent);
                break;
            case R.id.my_self:
                mainActivity.tabFragment.onTabSelected(3);
                break;
            case R.id.search_edit:
                Intent intent = new Intent(mContext,SearchAutoCompleteActivity.class);
                animation = true;
                startActivity(intent);
                break;
            case R.id.titleLeftButton:// 扫描二维码
                Intent intent1 = new Intent(mContext,MipcaActivityCapture.class);
                startActivity(intent1);
                break;
            case R.id.message_layout:
                Intent intent2 = new Intent(mContext,MessageListActivity.class);
                startActivity(intent2);
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }



}
