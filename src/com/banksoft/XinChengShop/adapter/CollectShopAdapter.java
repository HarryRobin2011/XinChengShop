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
import com.banksoft.XinChengShop.entity.ShopCollectionVO;
import com.banksoft.XinChengShop.entity.ShopVO;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by harry_robin on 15/11/23.
 */
public class CollectShopAdapter extends BaseMyAdapter {
    private ImageLoader mImageLoader;
    public CollectShopAdapter(Context context, List dataList) {
        super(context, dataList);
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
    }

    private class CollectShopHolder extends BusinessHolder{
        private TextView shopName;
        private TextView level;
        private ImageView imageView;
    }



    @Override
    protected View createCellView() {
        return LayoutInflater.from(mContext).inflate(R.layout.shop_list_item_layout,null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        CollectShopHolder holder = new CollectShopHolder();
        holder.shopName = (TextView) cellView.findViewById(R.id.name);
        holder.level = (TextView) cellView.findViewById(R.id.level);
        holder.imageView = (ImageView) cellView.findViewById(R.id.imageView);
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        CollectShopHolder holder = (CollectShopHolder) cellHolder;
        ShopCollectionVO shopVO = (ShopCollectionVO) dataList.get(position);
        holder.shopName.setText(shopVO.getShopName());
        mImageLoader.displayImage(ControlUrl.BASE_URL+shopVO.getShopLogo(),holder.imageView, XCApplication.options);
        return cellView;
    }
}
