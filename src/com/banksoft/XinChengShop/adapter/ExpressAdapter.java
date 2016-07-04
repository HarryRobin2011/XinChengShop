package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckedTextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.entity.base.BaseEntity;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by harry_robin on 15/12/4.
 */
public class ExpressAdapter extends BaseMyAdapter{

    public ExpressAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    private class ExpressHolder extends BusinessHolder{
        private CheckedTextView name;
    }

    @Override
    protected View createCellView() {
        return LayoutInflater.from(mContext).inflate(R.layout.express_list_item_layout,null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        ExpressHolder holder = new ExpressHolder();
        holder.name = (CheckedTextView) cellView.findViewById(R.id.name);
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        ExpressHolder holder = (ExpressHolder) cellHolder;
        String name = "";
        String price = "";

        BaseEntity data = (BaseEntity) dataList.get(position);
        if(data == null){
            holder.name.setText(R.string.shop_free_express);

        }else{
            try {
                Field field = data.getClass().getDeclaredField("name");
                Field priceField = data.getClass().getDeclaredField("price");
                field.setAccessible(true);
                priceField.setAccessible(true);

                name = field.get(data).toString();
                price = priceField.get(data).toString();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            holder.name.setText(name+"   "+price+"元");

        }

        return cellView;
    }
}
