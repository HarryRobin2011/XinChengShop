package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.entity.ProductTypeVO;
import com.banksoft.XinChengShop.entity.ShopProductTypeBO;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by harry_robin on 15/9/5.
 */
public class CategoryShopGridAdapter extends BaseMyAdapter {
    public CategoryShopGridAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    private class CategoryGridHolder extends BusinessHolder{
        private TextView name;
    }
    @Override
    protected View createCellView() {
        return LayoutInflater.from(mContext).inflate(R.layout.category_grid_item_4_layout,null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        CategoryGridHolder holder = new CategoryGridHolder();
        holder.name = (TextView) cellView.findViewById(R.id.name);
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        CategoryGridHolder holder = (CategoryGridHolder) cellHolder;
        ShopProductTypeBO category = (ShopProductTypeBO) dataList.get(position);
        holder.name.setText(category.getName());
        return cellView;
    }
}
