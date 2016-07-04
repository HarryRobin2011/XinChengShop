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
import com.banksoft.XinChengShop.entity.ProductCollectionVO;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by harry_robin on 16/1/23.
 */
public class CollectProductAdapter extends BaseMyAdapter {
    private ImageLoader mImageLoader;


    public CollectProductAdapter(Context context, List dataList) {
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
        return LayoutInflater.from(mContext).inflate(R.layout.collect_product_item_layout, null);
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
        ProductCollectionVO productCollectionVO = (ProductCollectionVO) dataList.get(position);
        ProductListHolder holder = (ProductListHolder) cellHolder;
        holder.name.setText(productCollectionVO.getProductName());
        holder.saleNum.setText("已销售："+String.valueOf(productCollectionVO.getSales()));
        holder.discountPrice.setText(productCollectionVO.getProductPrice() + "元");
        String imageFile;
        if("".equals(productCollectionVO.getProductImage()) || productCollectionVO == null){
            imageFile = "";
        }else{
            imageFile = productCollectionVO.getProductImage().split("\\|")[0];
        }
        mImageLoader.displayImage(ControlUrl.BASE_URL+imageFile,holder.img, XCApplication.options);
        return cellView;
    }
}
