package com.banksoft.XinChengShop.ui.fragment.localservice;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.ProductListAdapter;
import com.banksoft.XinChengShop.adapter.ShopLocalTypeAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.LocalServiceDao;
import com.banksoft.XinChengShop.entity.ShopLocalType;
import com.banksoft.XinChengShop.entity.ShopProductListVO;
import com.banksoft.XinChengShop.entity.ShopServerType;
import com.banksoft.XinChengShop.model.ShopLocalTypeData;
import com.banksoft.XinChengShop.type.ShopType;
import com.banksoft.XinChengShop.ui.ProductListActivity;
import com.banksoft.XinChengShop.ui.ShopProductInfoActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseListFragment;
import com.banksoft.XinChengShop.utils.JSONHelper;
import com.banksoft.XinChengShop.widget.MyGridView;

import java.util.ArrayList;

/**
 * Created by Robin on 2016/6/13.
 */
public class LocalServiceProductFragment extends XCBaseListFragment{
    private XCBaseActivity activity;
    private String active;//产品类型0 普通产品 1 清仓甩卖 2团购产品
    private String special;//是否特产
    private String name;//产品名称
    public String shopServerType;// 店铺类型
    public String typeId;// 分类
    public String orderType;//排序方式
    public String orderCol;//排序类型

    public boolean recommend;

    private View headView;
    private MyGridView myGridView;
    private LocalServiceDao localServiceDao;
    private Integer[] resIds = new Integer[]{R.drawable.publish_icon_car, R.drawable.publish_icon_pets, R.drawable.publish_icon_piaowu, R.drawable.publish_icon_sale};
    private ShopLocalTypeAdapter shopLocalTypeAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (XCBaseActivity) getActivity();

    }

    @Override
    public void request() {
        headView = LayoutInflater.from(mContext).inflate(R.layout.shop_list_head_server_layout,null);
        myGridView = (MyGridView) headView.findViewById(R.id.gridView);
        myGridView.setOnItemClickListener(this);
        xListView.addHeaderView(headView);

        params = "";
        typeId =  getArguments().getString(IntentFlag.TYPE_ID,"");
        active =  getArguments().getString(IntentFlag.ACTIVE,"");
        special =  getArguments().getString(IntentFlag.SPECIAL,"");
        name =  getArguments().getString(IntentFlag.NAME,"");
        shopServerType =  getArguments().getString(IntentFlag.SHOP_SERVER_TYPE,"");
        recommend = getArguments().getBoolean(IntentFlag.RECOMMEND);


        url = ControlUrl.XC_SHOP_PRODUCT_LIST_URL;

        if(!"".equals(typeId)){
            params += "typeId="+typeId;
        }
        if(!"".equals(shopServerType)){
            params += "&shopServerType="+shopServerType;
        }
        if(!"".equals(name)){
            params += "&name="+name;
        }
        if(!"".equals(active)){
            params += "&active="+active;
        }
        if(!"".equals(special)){
            params += "&special="+special;
        }
        if(!"".equals(orderType)){
            params += "&orderType="+orderType;
        }
        if(!"".equals(orderCol)){
            params += "&orderCol="+orderCol;
        }
        if(recommend){// 是否推荐
            params += "&recommend="+recommend;
        }

        jsonType = JSONHelper.SHOP_LIST_PRODUCT_DATA;
        bailaAdapter = new ProductListAdapter(mContext,new ArrayList());

        if(localServiceDao == null){
            localServiceDao = new LocalServiceDao(mContext);
        }
        new MyTask().execute(localServiceDao);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Activity.RESULT_FIRST_USER) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    request();
                    break;
                case Activity.RESULT_CANCELED:
                    request();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public Fragment getInstance() {
        return null;
    }

    @Override
    public CharSequence getTitle() {
        return null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext,ShopProductInfoActivity.class);
                intent.putExtra(IntentFlag.PRODUCT_ID, ((ShopProductListVO) bailaAdapter.getItem(position - 1)).getId());
                startActivity(intent);
    }

    @Override
    public void onAdapterCLick(View view, int position) {

    }

    private class MyTask extends AsyncTask<LocalServiceDao, String, ShopLocalTypeData> {

        @Override
        protected ShopLocalTypeData doInBackground(LocalServiceDao... params) {
            return params[0].getShopLocalTypeData();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressbar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(ShopLocalTypeData shopLocalTypeData) {
            super.onPostExecute(shopLocalTypeData);
            mProgressbar.setVisibility(View.GONE);
            if (shopLocalTypeData != null) {
                if (shopLocalTypeData.isSuccess()) {
                    showTypeView(shopLocalTypeData);
                } else {
                    alert(R.string.net_is_weak);
                }

            } else {
                alert(R.string.net_error);
            }
        }
    }

    /**
     * 加载显示分类
     */
    private void showTypeView(ShopLocalTypeData shopLocalTypeData) {
        for (int i = 0; i < shopLocalTypeData.getData().size(); i++) {
            shopLocalTypeData.getData().get(i).setImageResID(resIds[i % resIds.length]);
        }
        if (shopLocalTypeAdapter == null) {
            shopLocalTypeAdapter = new ShopLocalTypeAdapter(getActivity(), shopLocalTypeData.getData());
        }
        myGridView.setAdapter(shopLocalTypeAdapter);
        setListDao();
    }
}
