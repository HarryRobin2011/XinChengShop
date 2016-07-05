package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckedTextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.entity.ProductTypeVO;
import com.banksoft.XinChengShop.entity.ShopProductTypeBO;

import java.util.List;

/**
 * Created by harry_robin on 15/9/5.
 */
public class CategoryShopListAdapter extends BaseMyAdapter{
    public int selected;
    public View view;

    public CategoryShopListAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    public class MyCategoryListHolder extends BusinessHolder{
        private CheckedTextView name;
    }

    @Override
    protected View createCellView() {
        view = LayoutInflater.from(mContext).inflate(R.layout.category_list_item_layout,null);
        return view;
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        MyCategoryListHolder holder = new MyCategoryListHolder();

        holder.name = (CheckedTextView) cellView.findViewById(R.id.name);
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        ShopProductTypeBO category = (ShopProductTypeBO) dataList.get(position);
        MyCategoryListHolder holder = (MyCategoryListHolder) cellHolder;
        holder.name.setText(category.getName());
        if(position == 0){
            holder.name.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
        return cellView;
    }

    public void setSelected(int position){
        for(int i = 0;i<dataMap.size();i++){
            CheckedTextView name = (CheckedTextView) dataMap.get(i).findViewById(R.id.name);
            name.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }
        this.selected = position;
        CheckedTextView name = (CheckedTextView) dataMap.get(position).findViewById(R.id.name);
        name.setBackgroundColor(mContext.getResources().getColor(R.color.white));
    }
}
