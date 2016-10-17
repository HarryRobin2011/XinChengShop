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
import com.banksoft.XinChengShop.entity.ShopVO;
import com.banksoft.XinChengShop.entity.TimeSetting;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by harry_robin on 15/12/2.
 */
public class ShopListAdapter extends BaseMyAdapter {

    private ImageLoader mImageLoader;


    public ShopListAdapter(Context context, List dataList) {
        super(context, dataList);
        mImageLoader = ImageLoader.getInstance();
    }

    private class ProductListHolder extends BusinessHolder{
        private TextView name,level,time;
        private ImageView img;
    }

    @Override
    protected View createCellView() {
        return LayoutInflater.from(mContext).inflate(R.layout.shop_list_item_layout,null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        ProductListHolder holder = new ProductListHolder();
        holder.name = (TextView) cellView.findViewById(R.id.name);
        holder.level = (TextView) cellView.findViewById(R.id.level);
        holder.img = (ImageView) cellView.findViewById(R.id.imageView);
        holder.time = (TextView) cellView.findViewById(R.id.time);
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        ShopVO shopVO = (ShopVO) dataList.get(position);
        ProductListHolder holder = (ProductListHolder) cellHolder;
        holder.name.setText(shopVO.getName());
        holder.level.setText("已售："+shopVO.getGoodCount());
        if(shopVO.getShopTimeSetting() != null){
            TimeSetting timeSetting = shopVO.getShopTimeSetting().get(0);
            holder.time.setText("营业时间：" + timeSetting.getStartHour() + ":" + String.format("%02d", timeSetting.getStartMinute())
                    + "-" + timeSetting.getEndHour() + ":" + String.format("%02d", timeSetting.getEndMinute()));
        }else{
            holder.time.setText("营业时间：24小时营业");
        }

        holder.time.setBackgroundResource(R.drawable.edit_text_orange_bg);
        String imageFile;
        if("".equals(shopVO.getLogo()) || shopVO.getLogo() == null){
            imageFile = "";
        }else{
            imageFile = shopVO.getLogo().split("\\|")[0];
        }
        mImageLoader.displayImage(ControlUrl.BASE_URL+imageFile,holder.img, XCApplication.options);
        return cellView;
    }
}
