package com.banksoft.XinChengShop.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import com.banksoft.XinChengShop.adapter.ProductListAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.entity.ShopProductListVO;
import com.banksoft.XinChengShop.ui.ShopProductInfoActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseListFragment;
import com.banksoft.XinChengShop.utils.JSONHelper;

import java.util.ArrayList;

/**
 * Created by harry_robin on 15/11/25.
 */
public class ProductListFragment extends XCBaseListFragment {
    private XCBaseActivity activity;
    private String active;//产品类型0 普通产品 1 清仓甩卖 2团购产品
    private String special;//是否特产
    private String name;//产品名称
    public String shopServerType;// 店铺类型
    public String typeId;// 分类
    public String orderType;//排序方式
    public String orderCol;//排序类型

    public boolean recommend;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (XCBaseActivity) getActivity();
    }

    @Override
    public void request() {
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
        xListView.setDividerHeight(1);
        setListDao();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Activity.RESULT_FIRST_USER){
            switch (resultCode){
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
}
