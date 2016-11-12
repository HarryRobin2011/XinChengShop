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
import com.banksoft.XinChengShop.type.ReturnProductStatus;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by Robin on 2015/2/2.
 */
public class OrderLisAdapter extends BaseMyAdapter{
    public ImageLoader mImageViewLoader;

    public OrderLisAdapter(Context context, List dataList) {
        super(context, dataList);
        mImageViewLoader = ImageLoader.getInstance();
    }


    private class OrderSaleHolder extends BusinessHolder {
        private TextView shopName;
        private TextView total,status;

        TextView delivery; // 同意退货后发货
        TextView confirm;//确认收货
        TextView cancel;//取消订单
        TextView comments;//评论 如果已评论 就是追加评论
        TextView pay;// 订单创建未付款
        TextView returnMoney;// 退款金额
       // TextView returnGoods;//申请退货
        TextView refund;// 申请退款
         private TextView delete;//删除订单   删除接口没有 改为查看详情
        private LinearLayout productContent;
        private LinearLayout toolLayout;
        private LinearLayout orderItemLayout;

        private OrderVO orderVO;

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
        orderSaleHolder.delivery = (TextView) cellView.findViewById(R.id.delivery);
        orderSaleHolder.confirm = (TextView) cellView.findViewById(R.id.confirm);
        orderSaleHolder.cancel = (TextView) cellView.findViewById(R.id.cancel);
        orderSaleHolder.comments = (TextView) cellView.findViewById(R.id.comments);
        orderSaleHolder.pay = (TextView) cellView.findViewById(R.id.pay);
        orderSaleHolder.returnMoney = (TextView) cellView.findViewById(R.id.return_money);
//        orderSaleHolder.returnGoods = (TextView) cellView.findViewById(R.id.return_the_goods);
       orderSaleHolder.refund = (TextView) cellView.findViewById(R.id.refund);
        orderSaleHolder.delete = (TextView) cellView.findViewById(R.id.delete);
        orderSaleHolder.status = (TextView) cellView.findViewById(R.id.status);
        orderSaleHolder.orderItemLayout = (LinearLayout) cellView.findViewById(R.id.order_list_item);
        return orderSaleHolder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        OrderVO orderVO = (OrderVO) (dataList.get(position));
        OrderSaleHolder holder = (OrderSaleHolder) cellHolder;
        holder.shopName.setText(orderVO.getShopName());
        holder.orderVO = orderVO;
        filterOrder(orderVO.getStatus(),orderVO.getRemarkStatus(), holder);
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

            mImageViewLoader.displayImage(imageUrl, imageView,XCApplication.options);

            name.setText(productVO.getProductName());
           // num.setText(productVO.getNum()+"");
            discountPrice.setText(productVO.getPrice() + "元");
            realPrice.setVisibility(View.GONE);
            saleNum.setVisibility(View.GONE);
            holder.productContent.addView(view);
        }
        holder.total.setText("共"+orderVO.getList().size()+"件商品 "+"合计：" + orderVO.getTotalMoney() +"（含运费￥"+orderVO.getExpressMoney()+"）");

        holder.cancel.setOnClickListener(this);
        holder.cancel.setTag(position);
        holder.pay.setOnClickListener(this);
        holder.pay.setTag(position);
        holder.comments.setOnClickListener(this);
        holder.comments.setTag(position);
        holder.orderItemLayout.setOnClickListener(this);
        holder.orderItemLayout.setTag(position);
        holder.refund.setOnClickListener(this);
        holder.refund.setTag(position);
        return cellView;
    }



    /**
     * ALL("所有订单"),
     * CREATE("等待卖家确认"),
     * WAIT ("等待付款"),
     * PAY("，等待发货"),
     * DISPATCH("等待买家确认"),
     * OVER("交易完成");
     *
     * @param orderStatus
     * @return
     */
    private OrderStatus filterOrder(String orderStatus,String returnStatus,OrderSaleHolder orderSaleHolder) {
            if (OrderStatus.CREATE.name().equals(orderStatus)) {// 待付款
                orderSaleHolder.delivery.setVisibility(View.GONE);
                orderSaleHolder.confirm.setVisibility(View.GONE);
                orderSaleHolder.cancel.setVisibility(View.VISIBLE);
                orderSaleHolder.comments.setVisibility(View.GONE);
                orderSaleHolder.pay.setVisibility(View.VISIBLE);
//            orderSaleHolder.returnGoods.setVisibility(View.GONE);
                orderSaleHolder.refund.setVisibility(View.GONE);
                orderSaleHolder.delete.setVisibility(View.GONE);
                orderSaleHolder.returnMoney.setVisibility(View.GONE);
                orderSaleHolder.toolLayout.setVisibility(View.VISIBLE);
                return OrderStatus.CREATE;
            } else if (OrderStatus.PAY.name().equals(orderStatus)) {//待发货
                orderSaleHolder.delivery.setVisibility(View.GONE);
                orderSaleHolder.confirm.setVisibility(View.GONE);
                orderSaleHolder.cancel.setVisibility(View.GONE);
                orderSaleHolder.comments.setVisibility(View.GONE);
//            orderSaleHolder.returnGoods.setVisibility(View.GONE);
                orderSaleHolder.refund.setVisibility(View.VISIBLE);
                orderSaleHolder.pay.setVisibility(View.GONE);
                orderSaleHolder.delete.setVisibility(View.GONE);
                orderSaleHolder.returnMoney.setVisibility(View.GONE);
                orderSaleHolder.toolLayout.setVisibility(View.VISIBLE);
                checkReturnOrderStatus(returnStatus,orderSaleHolder);
                return OrderStatus.PAY;
            } else if (OrderStatus.DISPATCH.name().equals(orderStatus)) {//待确认收货
                orderSaleHolder.delivery.setVisibility(View.GONE);
                orderSaleHolder.confirm.setVisibility(View.VISIBLE);
                orderSaleHolder.refund.setVisibility(View.GONE);
//            orderSaleHolder.returnGoods.setVisibility(View.VISIBLE);
                orderSaleHolder.cancel.setVisibility(View.GONE);
                orderSaleHolder.comments.setVisibility(View.GONE);
                orderSaleHolder.pay.setVisibility(View.GONE);
                orderSaleHolder.delete.setVisibility(View.GONE);
                orderSaleHolder.returnMoney.setVisibility(View.GONE);
                orderSaleHolder.toolLayout.setVisibility(View.VISIBLE);
                checkReturnOrderStatus(returnStatus,orderSaleHolder);
                return OrderStatus.DISPATCH;
            } else if (OrderStatus.SUCCESS.name().equals(orderStatus)) {//待评价
                orderSaleHolder.delivery.setVisibility(View.GONE);
                orderSaleHolder.confirm.setVisibility(View.GONE);
                orderSaleHolder.cancel.setVisibility(View.GONE);
                orderSaleHolder.comments.setVisibility(View.VISIBLE);
//            orderSaleHolder.returnGoods.setVisibility(View.GONE);
                orderSaleHolder.refund.setVisibility(View.GONE);
                orderSaleHolder.pay.setVisibility(View.GONE);
                orderSaleHolder.delete.setVisibility(View.GONE);
                orderSaleHolder.returnMoney.setVisibility(View.GONE);
                orderSaleHolder.toolLayout.setVisibility(View.VISIBLE);
                return OrderStatus.SUCCESS;
            } else if (OrderStatus.OVER.name().equals(orderStatus)) {
                orderSaleHolder.delivery.setVisibility(View.GONE);
                orderSaleHolder.confirm.setVisibility(View.GONE);
                orderSaleHolder.cancel.setVisibility(View.GONE);
                orderSaleHolder.comments.setVisibility(View.GONE);
                orderSaleHolder.pay.setVisibility(View.GONE);
//            orderSaleHolder.returnGoods.setVisibility(View.GONE);
                orderSaleHolder.refund.setVisibility(View.GONE);
                orderSaleHolder.delete.setVisibility(View.GONE);
                orderSaleHolder.returnMoney.setVisibility(View.GONE);
                orderSaleHolder.status.setText(OrderStatus.valueOf(orderStatus).getName());
                orderSaleHolder.toolLayout.setVisibility(View.VISIBLE);
                return OrderStatus.OVER;
            }else if(OrderStatus.REPEALING.name().equals(orderStatus)){//订单退货中
                orderSaleHolder.returnMoney.setVisibility(View.VISIBLE);
                orderSaleHolder.returnMoney.setText(String.valueOf(orderSaleHolder.orderVO.getTotalMoney()));
                orderSaleHolder.toolLayout.setVisibility(View.VISIBLE);
                orderSaleHolder.delete.setVisibility(View.GONE);
                return OrderStatus.OVER;
            }else if(OrderStatus.REPEAL_OVER.name().equals(orderStatus)){//订单退货完成
                orderSaleHolder.returnMoney.setVisibility(View.VISIBLE);
                orderSaleHolder.toolLayout.setVisibility(View.VISIBLE);
                orderSaleHolder.returnMoney.setText(String.valueOf(orderSaleHolder.orderVO.getTotalMoney()));
                orderSaleHolder.delete.setVisibility(View.GONE);
                return OrderStatus.REPEAL_OVER;
            }
        return null;
    }

    private OrderStatus checkReturnOrderStatus(String returnStatus,OrderSaleHolder orderSaleHolder){
        if(ReturnProductStatus.APPLY_MONEY.name().equals(returnStatus)){
            orderSaleHolder.delivery.setVisibility(View.GONE);
            orderSaleHolder.confirm.setVisibility(View.GONE);
            orderSaleHolder.cancel.setVisibility(View.GONE);
            orderSaleHolder.comments.setVisibility(View.GONE);
            orderSaleHolder.pay.setVisibility(View.GONE);
//            orderSaleHolder.returnGoods.setVisibility(View.GONE);
            orderSaleHolder.refund.setVisibility(View.GONE);
            orderSaleHolder.delete.setVisibility(View.GONE);
            orderSaleHolder.returnMoney.setVisibility(View.GONE);
            orderSaleHolder.status.setText(R.string.apply_return_ing);
            orderSaleHolder.toolLayout.setVisibility(View.VISIBLE);
            return OrderStatus.OVER;
        }else if(ReturnProductStatus.APPLY_PART_MONEY.name().equals(returnStatus)){
            orderSaleHolder.delivery.setVisibility(View.GONE);
            orderSaleHolder.confirm.setVisibility(View.GONE);
            orderSaleHolder.cancel.setVisibility(View.GONE);
            orderSaleHolder.comments.setVisibility(View.GONE);
            orderSaleHolder.pay.setVisibility(View.GONE);
//            orderSaleHolder.returnGoods.setVisibility(View.GONE);
            orderSaleHolder.refund.setVisibility(View.GONE);
            orderSaleHolder.delete.setVisibility(View.GONE);
            orderSaleHolder.returnMoney.setVisibility(View.GONE);
            orderSaleHolder.status.setText(R.string.apply_return_ing);
            orderSaleHolder.toolLayout.setVisibility(View.VISIBLE);
            return OrderStatus.OVER;
        }else if(ReturnProductStatus.APPLY_PRODUCT.name().equals(returnStatus)){
            orderSaleHolder.delivery.setVisibility(View.GONE);
            orderSaleHolder.confirm.setVisibility(View.GONE);
            orderSaleHolder.cancel.setVisibility(View.GONE);
            orderSaleHolder.comments.setVisibility(View.GONE);
            orderSaleHolder.pay.setVisibility(View.GONE);
//            orderSaleHolder.returnGoods.setVisibility(View.GONE);
            orderSaleHolder.refund.setVisibility(View.GONE);
            orderSaleHolder.delete.setVisibility(View.GONE);
            orderSaleHolder.returnMoney.setVisibility(View.GONE);
            orderSaleHolder.status.setText(R.string.apply_return_ing);
            orderSaleHolder.toolLayout.setVisibility(View.VISIBLE);
            return OrderStatus.OVER;
        }else if(ReturnProductStatus.SHOP_NOT_AGREE_PRODUCT.name().equals(returnStatus)){
            orderSaleHolder.delivery.setVisibility(View.GONE);
            orderSaleHolder.confirm.setVisibility(View.GONE);
            orderSaleHolder.cancel.setVisibility(View.GONE);
            orderSaleHolder.comments.setVisibility(View.GONE);
            orderSaleHolder.pay.setVisibility(View.GONE);
//            orderSaleHolder.returnGoods.setVisibility(View.GONE);
            orderSaleHolder.refund.setVisibility(View.GONE);
            orderSaleHolder.delete.setVisibility(View.GONE);
            orderSaleHolder.returnMoney.setVisibility(View.GONE);
            orderSaleHolder.status.setText(R.string.apply_return_ing);
            orderSaleHolder.toolLayout.setVisibility(View.VISIBLE);
            return OrderStatus.OVER;

        }else if(ReturnProductStatus.SHOP_NOT_PART_MONEY.name().equals(returnStatus)){
            orderSaleHolder.delivery.setVisibility(View.GONE);
            orderSaleHolder.confirm.setVisibility(View.GONE);
            orderSaleHolder.cancel.setVisibility(View.GONE);
            orderSaleHolder.comments.setVisibility(View.GONE);
            orderSaleHolder.pay.setVisibility(View.GONE);
//            orderSaleHolder.returnGoods.setVisibility(View.GONE);
            orderSaleHolder.refund.setVisibility(View.GONE);
            orderSaleHolder.delete.setVisibility(View.GONE);
            orderSaleHolder.returnMoney.setVisibility(View.GONE);
            orderSaleHolder.status.setText(R.string.apply_return_ing);
            orderSaleHolder.toolLayout.setVisibility(View.VISIBLE);
            return OrderStatus.OVER;
        }else if(ReturnProductStatus.SHOP_NOT_AGREE_PRODUCT.name().equals(returnStatus)){
            orderSaleHolder.delivery.setVisibility(View.GONE);
            orderSaleHolder.confirm.setVisibility(View.GONE);
            orderSaleHolder.cancel.setVisibility(View.GONE);
            orderSaleHolder.comments.setVisibility(View.GONE);
            orderSaleHolder.pay.setVisibility(View.GONE);
//            orderSaleHolder.returnGoods.setVisibility(View.GONE);
            orderSaleHolder.refund.setVisibility(View.GONE);
            orderSaleHolder.delete.setVisibility(View.GONE);
            orderSaleHolder.returnMoney.setVisibility(View.GONE);
            orderSaleHolder.status.setText(R.string.apply_return_ing);
            orderSaleHolder.toolLayout.setVisibility(View.VISIBLE);
            return OrderStatus.OVER;
        }else if(ReturnProductStatus.SHOP_NOT_AGREE_MONEY.name().equals(returnStatus)){
            orderSaleHolder.delivery.setVisibility(View.GONE);
            orderSaleHolder.confirm.setVisibility(View.GONE);
            orderSaleHolder.cancel.setVisibility(View.GONE);
            orderSaleHolder.comments.setVisibility(View.GONE);
            orderSaleHolder.pay.setVisibility(View.GONE);
//            orderSaleHolder.returnGoods.setVisibility(View.GONE);
            orderSaleHolder.refund.setVisibility(View.GONE);
            orderSaleHolder.delete.setVisibility(View.GONE);
            orderSaleHolder.returnMoney.setVisibility(View.GONE);
            orderSaleHolder.status.setText(R.string.no_agree_return);
            orderSaleHolder.toolLayout.setVisibility(View.VISIBLE);
            return OrderStatus.OVER;
        }else if(ReturnProductStatus.AGREE_DISPATCH.name().equals(returnStatus)){
            orderSaleHolder.delivery.setVisibility(View.GONE);
            orderSaleHolder.confirm.setVisibility(View.GONE);
            orderSaleHolder.cancel.setVisibility(View.GONE);
            orderSaleHolder.comments.setVisibility(View.GONE);
            orderSaleHolder.pay.setVisibility(View.GONE);
//            orderSaleHolder.returnGoods.setVisibility(View.GONE);
            orderSaleHolder.refund.setVisibility(View.GONE);
            orderSaleHolder.delete.setVisibility(View.GONE);
            orderSaleHolder.returnMoney.setVisibility(View.GONE);
            orderSaleHolder.status.setText(R.string.agree_dispatch);
            orderSaleHolder.toolLayout.setVisibility(View.VISIBLE);
            return OrderStatus.OVER;
        }else if(ReturnProductStatus.AGREE_NO_DISPATCH.name().equals(returnStatus)){
            orderSaleHolder.delivery.setVisibility(View.GONE);
            orderSaleHolder.confirm.setVisibility(View.GONE);
            orderSaleHolder.cancel.setVisibility(View.GONE);
            orderSaleHolder.comments.setVisibility(View.GONE);
            orderSaleHolder.pay.setVisibility(View.GONE);
//            orderSaleHolder.returnGoods.setVisibility(View.GONE);
            orderSaleHolder.refund.setVisibility(View.GONE);
            orderSaleHolder.delete.setVisibility(View.GONE);
            orderSaleHolder.returnMoney.setVisibility(View.GONE);
            orderSaleHolder.status.setText(R.string.agree_no_dispatch);
            orderSaleHolder.toolLayout.setVisibility(View.VISIBLE);
            return OrderStatus.OVER;
        }else if(ReturnProductStatus.AGREE_OVER.name().equals(returnStatus)){
            orderSaleHolder.delivery.setVisibility(View.GONE);
            orderSaleHolder.confirm.setVisibility(View.GONE);
            orderSaleHolder.cancel.setVisibility(View.GONE);
            orderSaleHolder.comments.setVisibility(View.GONE);
            orderSaleHolder.pay.setVisibility(View.GONE);
//            orderSaleHolder.returnGoods.setVisibility(View.GONE);
            orderSaleHolder.refund.setVisibility(View.GONE);
            orderSaleHolder.delete.setVisibility(View.GONE);
            orderSaleHolder.returnMoney.setVisibility(View.GONE);
            orderSaleHolder.status.setText(R.string.return_good_yes);
            orderSaleHolder.toolLayout.setVisibility(View.VISIBLE);
            return OrderStatus.OVER;
        }
        return OrderStatus.OVER;
    }
}
