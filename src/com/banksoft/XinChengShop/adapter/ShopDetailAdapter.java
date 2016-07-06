package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.entity.ShopProductListVO;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by admin on 2016/7/2.
 */
public class ShopDetailAdapter extends BaseMyAdapter {
    private ImageLoader imageLoader;

    public ShopDetailAdapter(Context context, List dataList) {
        super(context, dataList);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
    }

    private class ShopDetailHolder extends BusinessHolder {
        private ImageView imageView;
        private TextView name, price, num;
    }

    @Override
    protected View createCellView() {
        return mInflater.inflate(R.layout.shop_detail_item_layout, null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        ShopDetailHolder holder = new ShopDetailHolder();
        holder.imageView = (ImageView) cellView.findViewById(R.id.image);
        holder.name = (TextView) cellView.findViewById(R.id.name);
        holder.price = (TextView) cellView.findViewById(R.id.price);
        holder.num = (TextView) cellView.findViewById(R.id.num);
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        ShopProductListVO shopProductListVO = (ShopProductListVO) dataList.get(position);
        ShopDetailHolder holder = (ShopDetailHolder) cellHolder;
        String icon = shopProductListVO.getIcon();
        if("".equals(icon)||icon == null){
            icon = "";
        }else{
            icon = shopProductListVO.getIcon().split("\\|")[0];
        }
        imageLoader.displayImage(ControlUrl.BASE_URL+icon,holder.imageView, XCApplication.options);
        holder.name.setText(shopProductListVO.getName());
        holder.price.setText("￥"+shopProductListVO.getPrice());
        holder.num.setText("已售："+shopProductListVO.getSales());
        return cellView;
    }
}
