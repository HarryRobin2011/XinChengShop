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
import com.banksoft.XinChengShop.entity.ShopProductListVO;
import com.banksoft.XinChengShop.utils.StringNumber;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by harry_robin on 15/11/14.
 */
public class ShopProductGridViewAdapter extends BaseMyAdapter {
    private ImageLoader mImageLoader;

    public ShopProductGridViewAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    private class ShopProductHolder extends BusinessHolder{
        private ImageView imageView;
        private TextView describe;
        private TextView price;
        private TextView num;
    }



    @Override
    protected View createCellView() {
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        return LayoutInflater.from(mContext).inflate(R.layout.shop_product_grid_item,null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        ShopProductHolder holder = new ShopProductHolder();
        holder.imageView = (ImageView) cellView.findViewById(R.id.image);
        holder.describe = (TextView) cellView.findViewById(R.id.describe);
        holder.num = (TextView) cellView.findViewById(R.id.num);
        holder.price = (TextView) cellView.findViewById(R.id.price);
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        ShopProductHolder holder = (ShopProductHolder) cellHolder;
        ShopProductListVO shopProductListVO = (ShopProductListVO) dataList.get(position);
        String icon = shopProductListVO.getIcon();
        String images = null;
        if(icon != null && !icon.equals("")){
            images = icon.split("\\|")[0];
        }
        mImageLoader.displayImage(ControlUrl.BASE_URL+images, holder.imageView, XCApplication.options);
        holder.describe.setText(shopProductListVO.getName());
        holder.price.setText(Float.valueOf(shopProductListVO.getPrice())+"元");
        holder.num.setText(""+ StringNumber.add(""+shopProductListVO.getSales(),""+shopProductListVO.getVirtualSale()));
        return cellView;
    }
}
