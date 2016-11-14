package com.banksoft.XinChengShop.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.ShopCartAdapter;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.config.SharedPreTag;
import com.banksoft.XinChengShop.entity.ProductCart;
import com.banksoft.XinChengShop.model.ShopCartProductData;
import com.banksoft.XinChengShop.type.ShopType;
import com.banksoft.XinChengShop.ui.SubmitSaleOrderActivity;
import com.banksoft.XinChengShop.ui.XCMainActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseFragment;
import com.banksoft.XinChengShop.utils.JSONHelper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Created by harry_robin on 15/11/4.
 */
public class XCShopCartFragment extends XCBaseFragment implements View.OnClickListener,BaseMyAdapter.OnAdapterClickListener{
    private ListView listView;
    private TextView price;
    private Button ok;
    private ShopCartAdapter shopCartAdapter;
    private SharedPreferences shopCartSP;
    private LinkedHashMap shopCartDataMap;
    private LinkedList shopCartDataList;
    private FrameLayout nullPager;
    private LinearLayout operation_layout;

    private LinearLayout searchLayout;
    private TextView title;
    private ImageView bgImage;

    @Override
    public View initContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.xc_shop_cart_layout, null);
    }

    @Override
    public void initView(View view) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        searchLayout = (LinearLayout) view.findViewById(R.id.search_layout);
        operation_layout = (LinearLayout) view.findViewById(R.id.operation_layout);
        nullPager = (FrameLayout) view.findViewById(R.id.null_pager);
        searchLayout.setVisibility(View.GONE);
        listView = (ListView) view.findViewById(R.id.list_view);
        price = (TextView) view.findViewById(R.id.price);
        ok = (Button) view.findViewById(R.id.confirm);

        searchLayout = (LinearLayout) view.findViewById(R.id.search_layout);

        title = (TextView) view.findViewById(R.id.titleText);

        bgImage = (ImageView) view.findViewById(R.id.titleBg);
    }


    @Override
    public void initData() {
        title.setText(R.string.shop_cart);
        title.setTextColor(getResources().getColor(R.color.text_black));
        searchLayout.setVisibility(View.GONE);
        bgImage.setBackgroundResource(R.color.white);
        setShopCartDataList();
    }

    private void setShopCartDataList(){
        shopCartSP = getActivity().getSharedPreferences(SharedPreTag.SHOP_CART_PRODUCT, Context.MODE_PRIVATE);
        shopCartDataMap = getShopProductData();
        shopCartDataList = new LinkedList();
        for (int position = 0; position < shopCartDataMap.keySet().size(); position++) {
            shopCartDataList.add(shopCartDataMap.get(shopCartDataMap.keySet().toArray()[position]));
        }
        if (shopCartDataList.size() == 0) {
            nullPager.setVisibility(View.VISIBLE);
            operation_layout.setVisibility(View.GONE);

        }
        if (shopCartAdapter == null) {
            shopCartAdapter = new ShopCartAdapter(this, shopCartDataList);
        }
        shopCartAdapter.setOnAdapterClickListener(this);
        setTotalMoney(shopCartDataList);
        listView.setAdapter(shopCartAdapter);
    }

    @Override
    public void initOperation() {
        ok.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Activity.RESULT_FIRST_USER){
           if(resultCode == Activity.RESULT_OK){
               setShopCartDataList();
           }
        }
    }

    @Override
    public Fragment getInstance() {
        return null;
    }

    @Override
    public CharSequence getTitle() {
        return getText(R.string.shop_cart);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                HashMap<String, ShopCartProductData> dataLinkedHashMap = getSelectShop();
                if (dataLinkedHashMap.keySet().size() == 0) {
                    alert(R.string.please_select_product);
                    return;
                }
                Intent submitIntent = new Intent(mContext, SubmitSaleOrderActivity.class);
                submitIntent.putExtra(IntentFlag.SHOP_TYPE, ShopType.SHOP_SALE.name());
                submitIntent.putExtra(IntentFlag.SHOP_CART_PRODUCT_DATA, dataLinkedHashMap);
                startActivity(submitIntent);
                break;

        }
    }

    /**
     * 获取购物车数据
     *
     * @return
     */
    private LinkedHashMap getShopProductData() {
        String dataJson = shopCartSP.getString(SharedPreTag.SHOP_CART_PRODUCT, "");
        LinkedHashMap linkedHashMap;
        float totalMoney = 0f;
        if ("".equals(dataJson)) {//购物车无商品
            linkedHashMap = new LinkedHashMap<>();
        } else {//购物车有商品
            linkedHashMap = JSONHelper.fromJSONObject(dataJson, JSONHelper.SHOP_CART_PRODUCT_DATA);
        }
        return linkedHashMap;
    }

    public void setTotalMoney(LinkedList<ShopCartProductData> dataList) {
        String totalMoney = "0";
        for (ShopCartProductData productData : dataList) {
            for (int position = 0; position < productData.getProductVOHashMap().keySet().size(); position++) {
                ProductCart productCart = productData.getProductVOHashMap().get(productData.getProductVOHashMap().keySet().toArray()[position]);
                if (productCart.isSelect()) {
                    BigDecimal total = new BigDecimal(totalMoney);
                    BigDecimal price = new BigDecimal("" + productCart.getSalePrice());
                    BigDecimal num = new BigDecimal("" + productCart.getNum());
                    BigDecimal singleMoney = price.multiply(num);
                    totalMoney = total.add(singleMoney).toString();
                }
            }
        }
        price.setText(totalMoney + "元");

    }



    /**
     * 获取购物车选中数据
     */
    private HashMap<String, ShopCartProductData> getSelectShop() {
        HashMap<String, ShopCartProductData> shopCartProductDataHashMap = new HashMap<>();
        LinkedList<ShopCartProductData> shopCartProductListData = (LinkedList<ShopCartProductData>) shopCartAdapter.dataList;
        for (ShopCartProductData productData : shopCartProductListData) {
            HashMap<String, ProductCart> productCartHashMap = new HashMap<>();
            for (Object key : productData.getProductVOHashMap().keySet().toArray()) {
                ProductCart productCart = productData.getProductVOHashMap().get(key);
                if (productCart.isSelect()) {
                    productCartHashMap.put(key.toString(), productCart);
                }
            }
            if (productCartHashMap.keySet().size() != 0) {
                ShopCartProductData shopCartProductData = new ShopCartProductData();
                shopCartProductData.setProductVOHashMap(productCartHashMap);
                shopCartProductData.setExpressPriceVO(productData.getExpressPriceVO());
                shopCartProductData.setAreaNo(productData.getAreaNo());
                shopCartProductData.setRemark(productData.getRemark());
                shopCartProductData.setShopId(productData.getShopId());
                shopCartProductData.setShopName(productData.getShopName());
                shopCartProductData.setShopType(productData.getShopType());
                shopCartProductDataHashMap.put(productData.getShopId(), shopCartProductData);
            }
        }
        return shopCartProductDataHashMap;
    }

    @Override
    public void onAdapterCLick(View view, int position) {
       switch (view.getId()){
           case R.id.delete:
              shopCartAdapter.remove(position);
               shopCartDataMap.remove(shopCartDataMap.keySet().toArray()[position]);
               shopCartSP.edit().putString(SharedPreTag.SHOP_CART_PRODUCT,JSONHelper.toJSONString(shopCartDataMap)).commit();
               break;
           case R.id.num:

               break;
       }
    }
}
