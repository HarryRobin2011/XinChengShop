package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.entity.ScoreOrder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by harry_robin on 16/1/23.
 */
public class ScoreOrderListAdapter extends BaseMyAdapter {
    public ImageLoader mImageViewLoader;

    public ScoreOrderListAdapter(Context context, List dataList) {
        super(context, dataList);
        mImageViewLoader = ImageLoader.getInstance();
        mImageViewLoader.init(ImageLoaderConfiguration.createDefault(mContext));
    }


    private class OrderSaleHolder extends BusinessHolder {
        private TextView shopName;
        private TextView total;

        TextView confirm;//确认收货

        private LinearLayout productContent;
        private LinearLayout toolLayout;

    }

    @Override
    protected View createCellView() {
        return LayoutInflater.from(mContext).inflate(R.layout.order_list_item_layout, null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        OrderSaleHolder orderSaleHolder = new OrderSaleHolder();
        orderSaleHolder.shopName = (TextView) cellView.findViewById(R.id.shop_name);
        orderSaleHolder.total = (TextView) cellView.findViewById(R.id.total);
        orderSaleHolder.productContent = (LinearLayout) cellView.findViewById(R.id.content);
        orderSaleHolder.toolLayout = (LinearLayout) cellView.findViewById(R.id.layout_tool);
        orderSaleHolder.confirm = (TextView) cellView.findViewById(R.id.confirm);
        return orderSaleHolder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        ScoreOrder scoreOrder = (ScoreOrder) (dataList.get(position));
        OrderSaleHolder holder = (OrderSaleHolder) cellHolder;
        holder.shopName.setText(scoreOrder.getNo());
        holder.productContent.removeAllViews();
        holder.toolLayout.setVisibility(View.GONE);

        View view = LayoutInflater.from(mContext).inflate(R.layout.product_list_item_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        TextView name = (TextView) view.findViewById(R.id.name);
        //TextView num = (TextView) view.findViewById(R.id.describe);
        TextView discountPrice = (TextView) view.findViewById(R.id.discount_price);
        TextView realPrice = (TextView) view.findViewById(R.id.real_price);
        TextView saleNum = (TextView) view.findViewById(R.id.sale_num);
        String imageUrl = "";
        if (!"".equals(scoreOrder.getImageFile()) && scoreOrder.getImageFile() != null) {
            imageUrl = ControlUrl.BASE_URL + scoreOrder.getImageFile().split("\\|")[0];
        }

        mImageViewLoader.displayImage(imageUrl, imageView, XCApplication.options);

        name.setText(scoreOrder.getProductName());
        // num.setText(productVO.getNum()+"");
        discountPrice.setText(scoreOrder.getProductScore() + "分");
        realPrice.setVisibility(View.GONE);
        saleNum.setVisibility(View.GONE);
        holder.productContent.addView(view);
        holder.total.setText("合计：" + scoreOrder.getScore() + "分");
        return cellView;
    }

}
