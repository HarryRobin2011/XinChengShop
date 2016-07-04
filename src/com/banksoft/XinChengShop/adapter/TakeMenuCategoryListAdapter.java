package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckedTextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.entity.ShopProductTypeBO;

import java.util.List;

/**
 * Created by harry_robin on 16/3/21.
 */
public class TakeMenuCategoryListAdapter extends BaseMyAdapter {

    public int selected;
    public View view;

    public TakeMenuCategoryListAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    public class MyCategoryListHolder extends BaseMyAdapter.BusinessHolder {
        private CheckedTextView name;
    }

    @Override
    protected View createCellView() {
        view = LayoutInflater.from(mContext).inflate(R.layout.category_list_item_layout, null);
        return view;
    }

    @Override
    public BaseMyAdapter.BusinessHolder createCellHolder(View cellView) {
        MyCategoryListHolder holder = new MyCategoryListHolder();

        holder.name = (CheckedTextView) cellView.findViewById(R.id.name);
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BaseMyAdapter.BusinessHolder cellHolder) {
        ShopProductTypeBO shopProductTypeBO;
        MyCategoryListHolder holder = (MyCategoryListHolder) cellHolder;
        shopProductTypeBO = (ShopProductTypeBO) dataList.get(position);
        holder.name.setText(shopProductTypeBO.getName());
        return cellView;
    }

    public void setSelected(int position) {
        for (int i = 0; i < dataMap.size(); i++) {
            CheckedTextView name = (CheckedTextView) dataMap.get(i).findViewById(R.id.name);
            name.setBackgroundColor(android.R.color.transparent);
        }
        this.selected = position;
        CheckedTextView name = (CheckedTextView) dataMap.get(position).findViewById(R.id.name);
        name.setBackgroundColor(mContext.getResources().getColor(R.color.white));
    }
}
