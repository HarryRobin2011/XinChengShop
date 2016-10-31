package com.banksoft.XinChengShop.ui.fragment.takeout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.TakeMenuCategoryListAdapter;
import com.banksoft.XinChengShop.adapter.TakeOutMenuProductAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.config.MapFlag;
import com.banksoft.XinChengShop.dao.TakeOutMenuDao;
import com.banksoft.XinChengShop.entity.ShopProductListVO;
import com.banksoft.XinChengShop.entity.ShopProductTypeBO;
import com.banksoft.XinChengShop.model.ShopProductListData;
import com.banksoft.XinChengShop.model.ShopProductTypeBOData;
import com.banksoft.XinChengShop.ui.takeout.TakeMenuSubmitOrderAcvitity;
import com.banksoft.XinChengShop.ui.takeout.TakeOutInfoActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseFragment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by harry_robin on 16/3/21.
 */
public class TakeOutMenuFragment extends XCBaseFragment implements AdapterView.OnItemClickListener,
        TakeOutMenuProductAdapter.NumMenuListener, View.OnClickListener {
    private ListView listView;
    private ListView menuListView;
    private TakeMenuCategoryListAdapter categoryListAdapter;
    private TakeOutMenuProductAdapter takeOutMenuProductAdapter;
    private TakeOutMenuDao takeOutMenuDao;
    private ProgressBar progressBar;
    private MyParentAsync myParentAsync;
    private TakeOutInfoActivity activity;
    private String orderMoney;

    public HashMap<String, HashMap<String, Object>> cartMap;

    private TakeOutMenuProductFragment takeOutMenuProductFragment;


    private TextView cartMoney;
    private Button submitOrder;

    private FrameLayout nullPage;

    private HashMap viewHashMap; //


    // 当前菜单
    private TakeOutMenuProductFragment currentMenuFragment;

    public String shopId;


    @Override
    public View initContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.take_out_menu_layout, null);
    }

    @Override
    public void initView(View view) {
        activity = (TakeOutInfoActivity) getActivity();
        listView = (ListView) view.findViewById(R.id.list_view);
        menuListView = (ListView) view.findViewById(R.id.menu_list_view);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        cartMoney = (TextView) view.findViewById(R.id.cart_price);
        submitOrder = (Button) view.findViewById(R.id.submit_order);

        nullPage = (FrameLayout) view.findViewById(R.id.null_pager);
    }

    @Override
    public void initData() {
        cartMap = new HashMap<>();//初始化订餐购物车
    }

    @Override
    public void initOperation() {
        activity.bgImage.setBackgroundResource(R.color.white);
        activity.searchLayout.setVisibility(View.GONE);
        activity.title.setTextColor(getResources().getColor(R.color.text_black));


        listView.setOnItemClickListener(this);
        submitOrder.setOnClickListener(this);

        if (takeOutMenuDao == null) {
            takeOutMenuDao = new TakeOutMenuDao(mContext);
        }
        myParentAsync = new MyParentAsync();
        myParentAsync.execute(takeOutMenuDao);
        addAsync(myParentAsync);
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
        switch (parent.getId()) {
            case R.id.list_view:
                categoryListAdapter.setSelected(position);
                setChildView(position, categoryListAdapter.getItem(position));
                break;

        }
    }

    @Override
    public void setDataNum(HashMap<String, HashMap<String, Object>> dataMap) {
        this.cartMap = dataMap;
        BigDecimal totalDecimal = new BigDecimal(0);
        BigDecimal freeShippingDecimal = new BigDecimal(activity.shopVO.getDispatchPrice());
        for (String key : dataMap.keySet()) {
            HashMap<String, Object> map = dataMap.get(key);
            int num = (int) map.get(MapFlag.NUM);

            ShopProductListVO shopProductListVO = (ShopProductListVO) map.get(MapFlag.DATA_0);
            BigDecimal numDecimal = new BigDecimal(num);
            BigDecimal salePriceDecimal = new BigDecimal(shopProductListVO.getSalePrice());
            BigDecimal orderDecimal = numDecimal.multiply(salePriceDecimal);
            totalDecimal = totalDecimal.add(orderDecimal);
        }
        switch (totalDecimal.compareTo(freeShippingDecimal)) {
            case -1:
                submitOrder.setBackgroundColor(getResources().getColor(R.color.green_light));
                submitOrder.setText("还差" + freeShippingDecimal.subtract(freeShippingDecimal).toString());
                break;
            case 0:
                submitOrder.setBackgroundResource(R.drawable.click_red_button);
                submitOrder.setText(R.string.settlement);
                break;
            case 1:
                submitOrder.setBackgroundResource(R.drawable.click_red_button);
                submitOrder.setText(R.string.settlement);
                break;
        }
        if(totalDecimal.compareTo(new BigDecimal(0))==0){
            cartMoney.setText("");
        }else{
            cartMoney.setText(totalDecimal.toString() + "元");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_order:
                if(activity.member.getShop() != null && activity.member.getShop().getId().equals(shopId)){
                alert(R.string.no_buy_my_self_shop_product);
                return;
                }
                if(cartMap.keySet().size() > 0){
                    Intent intent = new Intent(mContext, TakeMenuSubmitOrderAcvitity.class);
                    intent.putExtra(IntentFlag.DATA_MAP,cartMap);
                    startActivity(intent);
                }else{
                    alert(R.string.cart_no_empty);
                }

                break;
        }
    }


    private class MyParentAsync extends AsyncTask<TakeOutMenuDao, String, ShopProductTypeBOData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ShopProductTypeBOData doInBackground(TakeOutMenuDao... params) {
            return params[0].getShopProductTypeBOData(activity.shopVO.getId(), "", true);
        }

        @Override
        protected void onPostExecute(ShopProductTypeBOData shopProductTypeBOData) {
            super.onPostExecute(shopProductTypeBOData);
            if (shopProductTypeBOData != null) {
                if (shopProductTypeBOData.isSuccess()) {
                    if(shopProductTypeBOData.getData() == null || shopProductTypeBOData.getData().size() == 0){
                        return;
                    }
                    if (categoryListAdapter == null) {
                        categoryListAdapter = new TakeMenuCategoryListAdapter(mContext, shopProductTypeBOData.getData());
                    }
                    listView.setAdapter(categoryListAdapter);
                    setChildView(0, categoryListAdapter.dataList.get(0));
                }
            } else {
                alert(R.string.net_error);
            }
        }
    }

    /**
     * 设置分类下菜单
     *
     * @param o
     * @param position // 当前分类position
     */
    private void setChildView(int position, Object o) {
        TakeOutMenuProductFragment takeOutMenuProductFragment = null;

        if (viewHashMap == null) {
            viewHashMap = new HashMap();
        }

        takeOutMenuProductFragment = (TakeOutMenuProductFragment) viewHashMap.get(position);
        if (o == null) {//全部分类
            return;
        }
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if (takeOutMenuProductFragment == null) {
            takeOutMenuProductFragment = new TakeOutMenuProductFragment();
            takeOutMenuProductFragment.currentShopTypeBO = (ShopProductTypeBO) o;
            takeOutMenuProductFragment.shopId = shopId;
            takeOutMenuProductFragment.takeOutMenuFragment = this;
            viewHashMap.put(position,takeOutMenuProductFragment);
        }


        if (currentMenuFragment != null) {
            if (!takeOutMenuProductFragment.isAdded()) {
                fragmentTransaction.hide(currentMenuFragment).add(R.id.content,takeOutMenuProductFragment,takeOutMenuProductFragment.getClass().getSimpleName()).commit();
            } else {
                fragmentTransaction.hide(currentMenuFragment).show(takeOutMenuProductFragment).commit();
            }
        } else {
            fragmentTransaction.add(R.id.content, takeOutMenuProductFragment).commit();
        }
        this.currentMenuFragment = takeOutMenuProductFragment;
    }


}
