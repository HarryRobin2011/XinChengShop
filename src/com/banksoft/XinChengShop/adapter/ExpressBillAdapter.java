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
import com.banksoft.XinChengShop.type.DispatchStatus;
import com.banksoft.XinChengShop.ui.ExpressBillListActivity;
import com.banksoft.XinChengShop.utils.TimeUtils;
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
    }


    private class OrderSaleHolder extends BusinessHolder {
        private TextView shopName;
        private TextView total;
        private TextView initAddress,destination,telphone,orderStatus,commission;
        private TextView time;

        TextView myDispatch,goDispatch; // 我去送货

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
        orderSaleHolder.initAddress = (TextView) cellView.findViewById(R.id.init_address);
        orderSaleHolder.destination = (TextView) cellView.findViewById(R.id.destination);
        orderSaleHolder.telphone = (TextView) cellView.findViewById(R.id.telPhone);
        orderSaleHolder.goDispatch = (TextView) cellView.findViewById(R.id.go_dispatch);
        orderSaleHolder.orderStatus = (TextView) cellView.findViewById(R.id.order_status);
        orderSaleHolder.commission = (TextView) cellView.findViewById(R.id.commission);
        orderSaleHolder.time = (TextView) cellView.findViewById(R.id.time);
        return orderSaleHolder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        OrderVO orderVO = (OrderVO) (dataList.get(position));
        ExpressBillAdapter.OrderSaleHolder holder = (ExpressBillAdapter.OrderSaleHolder) cellHolder;
        holder.shopName.setText(orderVO.getShopName());
        holder.orderVO = orderVO;
        holder.productContent.removeAllViews();
        holder.orderStatus.setText(DispatchStatus.valueOf(orderVO.getDispatchStatus()).getName());

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
            discountPrice.setText(productVO.getPrice() + "元  X  "+productVO.getNum());
            realPrice.setVisibility(View.GONE);
            saleNum.setVisibility(View.GONE);
            holder.productContent.addView(view);
        }
        holder.total.setText("共"+orderVO.getList().size()+"件商品 "+"合计：" + orderVO.getTotalMoney() +"（含运费￥"+orderVO.getExpressMoney()+"）");
       if(orderVO.getTelephone() != null){
           String[] tels = orderVO.getTelephone().split("/");
           if(tels.length >0){
               holder.telphone.setText("联系电话："+tels[0]);
           }
        }

        holder.initAddress.setText("初始地："+orderVO.getShopAddress());
        holder.destination.setText("目的地："+orderVO.getAddress());
        holder.orderItemLayout.setOnClickListener(this);
        holder.myDispatch.setOnClickListener(this);
        holder.myDispatch.setTag(position);
        holder.orderItemLayout.setTag(position);
        holder.commission.setText("佣金："+orderVO.getDispatchPrice()+"元");
        holder.time.setText("下单时间："+TimeUtils.getTimeStr(orderVO.getCreateTime(),TimeUtils.TimeType.MINUTE));
        if(DispatchStatus.CREATE.name().equals(orderVO.getDispatchStatus())){
            holder.toolLayout.setVisibility(View.VISIBLE);
            holder.goDispatch.setVisibility(View.GONE);
            holder.myDispatch.setVisibility(View.VISIBLE);
        }else if(DispatchStatus.DISPATCH.name().equals(orderVO.getDispatchStatus())){
            holder.toolLayout.setVisibility(View.VISIBLE);
            holder.goDispatch.setVisibility(View.VISIBLE);
            holder.myDispatch.setVisibility(View.GONE);
        }else{
            holder.toolLayout.setVisibility(View.GONE);
        }

        return cellView;
    }


}
