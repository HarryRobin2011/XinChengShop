package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.entity.ShopLocalType;
import com.banksoft.XinChengShop.entity.ShopServerType;
import com.banksoft.XinChengShop.entity.ShopTypeVO;
import com.banksoft.XinChengShop.type.ShopType;

import java.util.List;

/**
 * Created by admin on 2016/7/21.
 */
public class SelectShopCategoryAdapter extends BaseMyAdapter {
    private ShopType shopType;


    public SelectShopCategoryAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    @Override
    protected View createCellView() {
        return mInflater.inflate(R.layout.select_shop_category_list_item_layout, null);
    }

    private class SelectShopCategoryHolder extends BusinessHolder {
        private TextView nameText;
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        SelectShopCategoryHolder holder = new SelectShopCategoryHolder();
        holder.nameText = (TextView) cellView.findViewById(R.id.name);
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        SelectShopCategoryHolder holder = (SelectShopCategoryHolder) cellHolder;
        if (shopType.equals(ShopType.SHOP_SERVER)) {
            ShopServerType shopServerType = (ShopServerType) dataList.get(position);
            holder.nameText.setText(shopServerType.getName());
        } else if (shopType.equals(ShopType.SHOP_SALE)) {
            ShopTypeVO shopTypeVO = (ShopTypeVO) dataList.get(position);
            holder.nameText.setText(shopTypeVO.getName());
        } else if (shopType.equals(ShopType.SHOP_LOCAL)) {
            ShopLocalType shopLocalType = (ShopLocalType) dataList.get(position);
            holder.nameText.setText(shopLocalType.getName());
        }
        return cellView;
    }

    public void setShopType(ShopType shopType) {
        this.shopType = shopType;
    }
}
