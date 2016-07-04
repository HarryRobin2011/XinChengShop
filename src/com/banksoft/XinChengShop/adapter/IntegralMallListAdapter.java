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
import com.banksoft.XinChengShop.entity.ScoreProduct;
import com.banksoft.XinChengShop.entity.ShopProductListVO;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by harry_robin on 15/11/25.
 */
public class IntegralMallListAdapter extends BaseMyAdapter {
    private ImageLoader mImageLoader;


    public IntegralMallListAdapter(Context context, List dataList) {
        super(context, dataList);
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
    }

    private class ProductListHolder extends BusinessHolder{
        private TextView name,describe,discountPrice,realPrice,saleNum;
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
        holder.realPrice = (TextView) cellView.findViewById(R.id.real_price);
        holder.saleNum = (TextView) cellView.findViewById(R.id.sale_num);
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        ScoreProduct scoreProduct = (ScoreProduct) dataList.get(position);
        ProductListHolder holder = (ProductListHolder) cellHolder;
        holder.name.setText(scoreProduct.getName());
        holder.saleNum.setText("已兑换："+String.valueOf(scoreProduct.getSales()));
        holder.discountPrice.setText(scoreProduct.getScore()+"积分");
        holder.realPrice.setText(scoreProduct.getPrice() + "元");

        String imageFile;
        if("".equals(scoreProduct.getImageFile()) || scoreProduct == null){
            imageFile = "";
        }else{
            imageFile = scoreProduct.getImageFile().split("\\|")[0];
        }
        mImageLoader.displayImage(ControlUrl.BASE_URL+imageFile,holder.img, XCApplication.options);
        return cellView;
    }
}
