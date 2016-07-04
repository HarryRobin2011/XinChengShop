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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robin on 2016/4/10.
 */
public class ProductManagerListAdapter extends BaseMyAdapter {
    private ImageLoader mImageLoader;

    public ProductManagerListAdapter(Context context, List dataList) {
        super(context, dataList);
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
    }

    private class ProductListHolder extends BusinessHolder{
        private TextView name,describe,discountPrice,saleNum;
        private ImageView img;
    }

    @Override
    protected View createCellView() {
        return LayoutInflater.from(mContext).inflate(R.layout.product_list_item_layout,null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        ProductListHolder holder = new ProductListHolder();
        holder.name = (TextView) cellView.findViewById(R.id.name);
        holder.describe = (TextView) cellView.findViewById(R.id.describe);
        holder.img = (ImageView) cellView.findViewById(R.id.imageView);
        holder.discountPrice = (TextView) cellView.findViewById(R.id.discount_price);
        holder.saleNum = (TextView) cellView.findViewById(R.id.sale_num);
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        ShopProductListVO shopProductListVO = (ShopProductListVO) dataList.get(position);
        ProductListHolder holder = (ProductListHolder) cellHolder;
        holder.name.setText(shopProductListVO.getName());
        holder.saleNum.setText("已销售："+String.valueOf(shopProductListVO.getSales()));
        holder.discountPrice.setText(shopProductListVO.getPrice() + "元");
        String imageFile;
        if("".equals(shopProductListVO.getIcon()) || shopProductListVO.getIcon() == null){
            imageFile = "";
        }else{
            imageFile = shopProductListVO.getIcon().split("\\|")[0];
        }
        mImageLoader.displayImage(ControlUrl.BASE_URL+imageFile,holder.img, XCApplication.options);
        return cellView;
    }
}
