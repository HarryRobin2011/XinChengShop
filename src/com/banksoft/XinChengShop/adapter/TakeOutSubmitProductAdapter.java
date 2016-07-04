package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.config.MapFlag;
import com.banksoft.XinChengShop.entity.ProductVO;
import com.banksoft.XinChengShop.entity.ShopProductListVO;

import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2016/7/4.
 */
public class TakeOutSubmitProductAdapter extends BaseMyAdapter{
    public TakeOutSubmitProductAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    @Override
    protected View createCellView() {
        return mInflater.inflate(R.layout.take_out_submit_order_product_item_layout,null);
    }

    private class TakeOutSubmitProductHolder extends BusinessHolder{
        private TextView name,price,num;
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        TakeOutSubmitProductHolder holder = new TakeOutSubmitProductHolder();
        holder.name = (TextView) cellView.findViewById(R.id.name);
        holder.price = (TextView) cellView.findViewById(R.id.price);
        holder.num  = (TextView) cellView.findViewById(R.id.num);
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        HashMap<String,Object> dataMap = (HashMap<String, Object>) dataList.get(position);
        TakeOutSubmitProductHolder holder = (TakeOutSubmitProductHolder) cellHolder;
        ShopProductListVO productVO = (ShopProductListVO) dataMap.get(MapFlag.DATA_0);
        int productNum = (int) dataMap.get(MapFlag.NUM);
        holder.name.setText(productVO.getName());
        holder.num.setText("X"+productNum);
        holder.price.setText(productVO.getPrice()+"元");
        return cellView;
    }
}
