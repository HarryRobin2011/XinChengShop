package com.banksoft.XinChengShop.ui.fragment.takeout;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.adapter.ShopListAdapter;
import com.banksoft.XinChengShop.adapter.ShopServerGridViewAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.TakeOutMainDao;
import com.banksoft.XinChengShop.entity.ShopVO;
import com.banksoft.XinChengShop.model.ShopServerTypeData;
import com.banksoft.XinChengShop.service.LocationService;
import com.banksoft.XinChengShop.type.ShopType;
import com.banksoft.XinChengShop.ui.LoginActivity;
import com.banksoft.XinChengShop.ui.ShopListActivity;
import com.banksoft.XinChengShop.ui.takeout.TakeOutInfoActivity;
import com.banksoft.XinChengShop.ui.takeout.TakeOutMainActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseListFragment;
import com.banksoft.XinChengShop.utils.JSONHelper;
import com.banksoft.XinChengShop.widget.MyGridView;

import java.util.LinkedList;

/**
 * 美食外卖店铺列表
 * Created by harry_robin on 16/1/21.
 */
public class TakeOutShopListFragment extends XCBaseListFragment implements AdapterView.OnItemClickListener{
    private TakeOutMainActivity takeOutMainActivity;
    private View headView;
    private MyGridView myGridView;
    private ShopServerGridViewAdapter shopServerGridViewAdapter;
    private TakeOutMainDao takeOutMainDao;
    private boolean cacheFlag = true;
    private TextView nearbyName;
    private LocationService locationService;




    @Override
    public void request() {
        takeOutMainActivity = (TakeOutMainActivity) getActivity();
        nearbyName = (TextView) takeOutMainActivity.findViewById(R.id.nearby_name);

        locationService = ((XCApplication) getActivity().getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听

        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        locationService.start();// 定位SDK





        xListView.removeHeaderView(headView);

        if(takeOutMainDao == null){
            takeOutMainDao = new TakeOutMainDao(mContext);
        }
        new ShopTypeServerList().execute(takeOutMainDao);

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(takeOutMainActivity.isLogin()){
            Intent intent = new Intent(mContext, TakeOutInfoActivity.class);
            ShopVO shopVO = (ShopVO) bailaAdapter.getItem(position - 2);
            intent.putExtra(IntentFlag.SHOP_VO,shopVO);
            startActivity(intent);
        }else{
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public Fragment getInstance() {
        return null;
    }

    @Override
    public CharSequence getTitle() {
        return null;
    }


    /**
     * 设置头部分类
     *
     * @param shopServerTypeData
     */
    private void setHeadView(ShopServerTypeData shopServerTypeData) {
        headView = LayoutInflater.from(mContext).inflate(R.layout.shop_list_head_server_layout, null);
        myGridView = (MyGridView) headView.findViewById(R.id.gridView);
        if (shopServerGridViewAdapter == null) {
            shopServerGridViewAdapter = new ShopServerGridViewAdapter(mContext, shopServerTypeData.getData());
        }
        shopServerGridViewAdapter.drables = new Integer[]{R.drawable.android_my_jd_phone_appoint,R.drawable
                .android_my_jd_blank_note,R.drawable
                .android_my_jd_collects,R.drawable
                .android_my_jd_game_center,R.drawable
                .android_my_jd_history,R.drawable
                .android_my_jd_messages};

        myGridView.setAdapter(shopServerGridViewAdapter);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, ShopListActivity.class);
                intent.putExtra(IntentFlag.TITLE,shopServerTypeData.getData().get(position).getName());
                intent.putExtra(IntentFlag.SHOP_TYPE,ShopType.SHOP_SERVER);
                intent.putExtra(IntentFlag.NO,shopServerTypeData.getData().get(position).getNo());
                startActivity(intent);
            }
        });

        xListView.addHeaderView(headView);


    }

    @Override
    public void onAdapterCLick(View view, int position) {

    }

    public interface LocationListener {
        public void location(BDLocation location);
    }

    /**
     * 店铺类型列表
     */
    private class ShopTypeServerList extends AsyncTask<TakeOutMainDao, String, ShopServerTypeData> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ShopServerTypeData doInBackground(TakeOutMainDao... params) {
            return params[0].getShopServerType(cacheFlag);
        }

        @Override
        protected void onPostExecute(ShopServerTypeData shopServerTypeData) {
            super.onPostExecute(shopServerTypeData);
            if (shopServerTypeData != null) {
                if(shopServerTypeData.isSuccess()){
                  setHeadView(shopServerTypeData);
                }else{
                   alert(R.string.netWork_warning);
                }
            } else {
                alert(R.string.netWork_error);
            }

        }
    }

    /**
     * 百度定位
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location != null) {
                locationService.unregisterListener(mListener);
                locationService.stop();
                takeOutMainActivity.location(location);
                url = ControlUrl.XC_POINT_SHOP_LIST_URL;
                params = "lng=" + location.getLongitude() + "&lat=" + location.getLatitude()
                        + "&r=10000000" + "&shopServerType=" + ShopType.SHOP_SERVER;
                bailaAdapter = new ShopListAdapter(mContext, new LinkedList());
                jsonType = JSONHelper.SHOP_LIST_DATA;
                setListDao();
            }
        }
    };

}
