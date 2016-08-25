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
import com.banksoft.XinChengShop.entity.OrderProductVO;
import com.banksoft.XinChengShop.entity.OrderVO;
import com.banksoft.XinChengShop.type.OrderStatus;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by Robin on 2016/8/12.
 */
public class ExpressBillAdapter extends BaseMyAdapter {
    public ImageLoader mImageViewLoader;

    public ExpressBillAdapter(Context context, List dataList) {
        super(context, dataList);
        mImageViewLoader = ImageLoader.getInstance();
        mImageViewLoader.init(ImageLoaderConfiguration.createDefault(mContext));
    }


    private class OrderSaleHolder extends BusinessHolder {
        private TextView shopName;
        private TextView total;

        TextView myDispatch; // 我去送货

        private LinearLayout productContent;
        private LinearLayout toolLayout;
        private LinearLayout orderItemLayout;

        private OrderVO orderVO;

    }

    @Override
    protected View createCellView() {
        return LayoutInflater.from(mContext).inflate(R.layout.express_bill_list_item_layout, null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        ExpressBillAdapter.OrderSaleHolder orderSaleHolder = new ExpressBillAdapter.OrderSaleHolder();
        orderSaleHolder.shopName = (TextView) cellView.findViewById(R.id.shop_name);
        orderSaleHolder.total = (TextView) cellView.findViewById(R.id.total);
        orderSaleHolder.productContent = (LinearLayout) cellView.findViewById(R.id.content);
        orderSaleHolder.toolLayout = (LinearLayout) cellView.findViewById(R.id.layout_tool);
        orderSaleHolder.myDispatch = (TextView) cellView.findViewById(R.id.my_dispatch);
        orderSaleHolder.orderItemLayout = (LinearLayout) cellView.findViewById(R.id.order_list_item);
        return orderSaleHolder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        OrderVO orderVO = (OrderVO) (dataList.get(position));
        ExpressBillAdapter.OrderSaleHolder holder = (ExpressBillAdapter.OrderSaleHolder) cellHolder;
        holder.shopName.setText(orderVO.getShopName());
        holder.orderVO = orderVO;
        holder.productContent.removeAllViews();

        for (OrderProductVO productVO : orderVO.getList()) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.product_list_order_item_layout, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            TextView name = (TextView) view.findViewById(R.id.name);
            //TextView num = (TextView) view.findViewById(R.id.describe);
            TextView discountPrice = (TextView) view.findViewById(R.id.discount_price);
            TextView realPrice = (TextView) view.findViewById(R.id.real_price);
            TextView saleNum = (TextView) view.findViewById(R.id.sale_num);
            String imageUrl = "";
            if(!"".equals(productVO.getImageFile()) && productVO.getImageFile() != null){
                imageUrl = ControlUrl.BASE_URL + productVO.getImageFile().split("\\|")[0];
            }

            mImageViewLoader.displayImage(imageUrl, imageView, XCApplication.options);

            name.setText(productVO.getProductName());
            // num.setText(productVO.getNum()+"");
            discountPrice.setText(productVO.getPrice() + "元");
            realPrice.setVisibility(View.GONE);
            saleNum.setVisibility(View.GONE);
            holder.productContent.addView(view);
        }
        holder.total.setText("共"+orderVO.getList().size()+"件商品 "+"合计：" + orderVO.getTotalMoney() +"（含运费￥"+orderVO.getExpressMoney()+"）");

        holder.orderItemLayout.setOnClickListener(this);
        holder.orderItemLayout.setTag(position);
        return cellView;
    }


}
