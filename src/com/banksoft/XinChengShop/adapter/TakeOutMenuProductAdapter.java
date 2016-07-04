package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.config.MapFlag;
import com.banksoft.XinChengShop.config.TagFlag;
import com.banksoft.XinChengShop.entity.ShopProductListVO;
import com.banksoft.XinChengShop.ui.fragment.takeout.TakeOutMenuFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by harry_robin on 16/3/21.
 */
public class TakeOutMenuProductAdapter extends BaseMyAdapter implements View.OnClickListener {
    private ImageLoader mImageLoader;
    private HashMap<String, Object> dataMap;
    private TakeOutMenuFragment fragment;

    public TakeOutMenuProductAdapter(Fragment fragment, Context context, List dataList) {
        super(context, dataList);
        this.fragment = (TakeOutMenuFragment) fragment;
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cart_single_product_num_reduce://减
                HashMap<String, Object> dataMap = (HashMap<String, Object>) dataList.get((Integer) v.getTag(R.string.key2));
                TakeOutMenuProductHolder takeOutMenuProductHolder = (TakeOutMenuProductHolder) v.getTag(R.string.key1);
                ShopProductListVO shopProductListVO = (ShopProductListVO) dataMap.get(MapFlag.DATA_0);
                int num = (int) dataMap.get(MapFlag.NUM);
                if (num > 1) {
                    num -= 1;
                } else {
                    num = 0;
                    takeOutMenuProductHolder.reduce.setVisibility(View.GONE);
                    takeOutMenuProductHolder.productNum.setVisibility(View.GONE);

                }
                dataMap.put(MapFlag.NUM,num);
                takeOutMenuProductHolder.productNum.setText(String.valueOf(num));
                if (fragment.cartMap == null) {
                    return;
                }

                HashMap<String, Object> productMap = fragment.cartMap.get(shopProductListVO.getId());
                if (productMap != null) {//购物车有了
                    if (num == 0) {// 产品数量变为0
                        fragment.cartMap.remove(shopProductListVO.getId());
                        return;
                    }
                    productMap.put(MapFlag.DATA_0, shopProductListVO);
                    productMap.put(MapFlag.NUM, num);
                    fragment.cartMap.put(shopProductListVO.getId(), productMap);
                }
                fragment.setDataNum(fragment.cartMap);
                break;
            case R.id.cart_single_product_num_add://加
                HashMap<String, Object> dataMapAdd = (HashMap<String, Object>) dataList.get((Integer) v.getTag(R.string.key2));
                TakeOutMenuProductHolder takeOutMenuProductHolderAdd = (TakeOutMenuProductHolder) v.getTag(R.string.key1);

                ShopProductListVO shopProductListVOAdd = (ShopProductListVO) dataMapAdd.get(MapFlag.DATA_0);
                int numAdd = (int) dataMapAdd.get(MapFlag.NUM);
                numAdd += 1;
                dataMapAdd.put(MapFlag.NUM, numAdd);
                takeOutMenuProductHolderAdd.reduce.setVisibility(View.VISIBLE);
                takeOutMenuProductHolderAdd.productNum.setVisibility(View.VISIBLE);
                takeOutMenuProductHolderAdd.productNum.setText(String.valueOf(numAdd));

                if (fragment.cartMap == null) {
                    return;
                }
                HashMap<String, Object> productMapAdd = fragment.cartMap.get(shopProductListVOAdd.getId());
                if (productMapAdd == null) {//购物车没有
                    HashMap<String, Object> cartProductMap = new HashMap<>();
                    cartProductMap.put(MapFlag.DATA_0, shopProductListVOAdd);
                    cartProductMap.put(MapFlag.NUM, numAdd);
                    fragment.cartMap.put(shopProductListVOAdd.getId(), cartProductMap);
                } else {//购物车有了
                    productMapAdd.put(MapFlag.DATA_0, shopProductListVOAdd);
                    productMapAdd.put(MapFlag.NUM, numAdd);
                    fragment.cartMap.put(shopProductListVOAdd.getId(), productMapAdd);
                }
                fragment.setDataNum(fragment.cartMap);

                break;
        }
    }


    private class TakeOutMenuProductHolder extends BusinessHolder {
        private ImageView mImageView;
        private TextView name, saleNum, price, productNum;
        private ImageButton reduce, add;
    }

    @Override
    protected View createCellView() {
        return LayoutInflater.from(mContext).inflate(R.layout.take_out_menu_product_item_layout, null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        TakeOutMenuProductHolder takeOutMenuProductHolder = new TakeOutMenuProductHolder();
        takeOutMenuProductHolder.mImageView = (ImageView) cellView.findViewById(R.id.image);
        takeOutMenuProductHolder.price = (TextView) cellView.findViewById(R.id.price);
        takeOutMenuProductHolder.saleNum = (TextView) cellView.findViewById(R.id.sale_num);
        takeOutMenuProductHolder.name = (TextView) cellView.findViewById(R.id.name);
        takeOutMenuProductHolder.reduce = (ImageButton) cellView.findViewById(R.id.cart_single_product_num_reduce);
        takeOutMenuProductHolder.add = (ImageButton) cellView.findViewById(R.id.cart_single_product_num_add);
        takeOutMenuProductHolder.productNum = (TextView) cellView.findViewById(R.id.cart_single_product_et_num);

        return takeOutMenuProductHolder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        HashMap<String, Object> dataMap = (HashMap<String, Object>) dataList.get(position);
        ShopProductListVO shopProductListVO = (ShopProductListVO) dataMap.get(MapFlag.DATA_0);
        int num = (int) dataMap.get(MapFlag.NUM);
        TakeOutMenuProductHolder takeOutMenuProductHolder = (TakeOutMenuProductHolder) cellHolder;
        takeOutMenuProductHolder.name.setText(shopProductListVO.getName());
        String imageUrl = "";
        if (!"".equals(shopProductListVO.getIcon()) && shopProductListVO.getIcon() != null) {
            imageUrl = shopProductListVO.getIcon().split("\\|")[0];
        }
        mImageLoader.displayImage(ControlUrl.BASE_URL + imageUrl, takeOutMenuProductHolder.mImageView, XCApplication.options);
        takeOutMenuProductHolder.saleNum.setText("已售：" + shopProductListVO.getSales());
        takeOutMenuProductHolder.price.setText(shopProductListVO.getSalePrice() + "元/份");


        if (num > 0) {
            takeOutMenuProductHolder.productNum.setText(dataMap.get(MapFlag.NUM).toString());
        }


        takeOutMenuProductHolder.reduce.setTag(R.string.key1, takeOutMenuProductHolder);
        takeOutMenuProductHolder.reduce.setTag(R.string.key2, position);
        takeOutMenuProductHolder.reduce.setOnClickListener(this);

        takeOutMenuProductHolder.add.setTag(R.string.key1, takeOutMenuProductHolder);
        takeOutMenuProductHolder.add.setTag(R.string.key2, position);
        takeOutMenuProductHolder.add.setOnClickListener(this);

        return cellView;
    }

    public interface NumMenuListener {
        void setDataNum(HashMap<String, HashMap<String, Object>> dataMap);
    }

    @Override
    public void setDataList(List dataList) {
        super.setDataList(dataList);
    }
}
