package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.entity.ShopServerType;

import java.util.List;

/**
 * Created by harry_robin on 16/3/17.
 */
public class ShopServerGridViewAdapter extends BaseMyAdapter {
    public Integer[] drables;
    public ShopServerGridViewAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    @Override
    protected View createCellView() {
        return LayoutInflater.from(mContext).inflate(R.layout.category_grid_item_3_layout,null);
    }

    private class ShopServerGridViewHolder extends BusinessHolder
    {
        private TextView name;
        private ImageView imageView;

    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        ShopServerGridViewHolder holder = new ShopServerGridViewHolder();
        holder.name = (TextView) cellView.findViewById(R.id.name);
        holder.imageView = (ImageView) cellView.findViewById(R.id.image);
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        ShopServerGridViewHolder holder = (ShopServerGridViewHolder) cellHolder;
        ShopServerType shopServerType = (ShopServerType) dataList.get(position);
        holder.name.setText(shopServerType.getName());
        int resId = drables[position];
        holder.imageView.setImageResource(resId);
        return cellView;
    }
}
