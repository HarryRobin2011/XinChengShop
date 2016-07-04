package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.entity.base.BaseEntity;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by harry_robin on 15/12/10.
 * 选择地理区域
 */
public class AreaListAdapter extends BaseMyAdapter {
    public AreaListAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    private class AreaListHolder extends BusinessHolder{
        private TextView name;
    }


    @Override
    protected View createCellView() {
        return LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1,null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        AreaListHolder holder = new AreaListHolder();
        holder.name = (TextView) cellView.findViewById(android.R.id.text1);
        holder.name.setBackgroundResource(R.drawable.button_white_bg);
        holder.name.setTextColor(mContext.getResources().getColor(R.color.text_black_light));
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        AreaListHolder holder = (AreaListHolder) cellHolder;
        BaseEntity baseEntity = (BaseEntity) dataList.get(position);
        try {
            Field nameFiled = baseEntity.getClass().getDeclaredField("name");
            nameFiled.setAccessible(true);
            holder.name.setText(nameFiled.get(baseEntity).toString());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
