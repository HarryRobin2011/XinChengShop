package com.banksoft.XinChengShop.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.SubmitOrderDao;
import com.banksoft.XinChengShop.entity.ProductCart;
import com.banksoft.XinChengShop.model.ShopCartProductData;
import com.banksoft.XinChengShop.ui.SubmitSaleOrderActivity;
import com.banksoft.XinChengShop.utils.StringNumber;
import com.banksoft.XinChengShop.widget.ClearEditText;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by harry_robin on 15/11/17.
 */
public class SubmitSaleOrderAdapter extends BaseMyAdapter {
    private ImageLoader mImageLoader;
    private SubmitOrderDao submitOrderDao;
    private SubmitSaleOrderActivity activity;

    public SubmitSaleOrderAdapter(Activity activity, List dataList) {
        super(activity.getApplicationContext(), dataList);
        this.activity = (SubmitSaleOrderActivity) activity;
        mImageLoader = ImageLoader.getInstance();
    }

    private class SubmitOrderHolder extends BusinessHolder {
        private TextView shopName;
        private TextView distributionText,num,total;
        private LinearLayout distributionLayout, content;
        private ClearEditText leave_message;
    }

    @Override
    protected View createCellView() {
        return LayoutInflater.from(mContext).inflate(R.layout.submit_order_item_layout, null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        SubmitOrderHolder holder = new SubmitOrderHolder();
        holder.shopName = (TextView) cellView.findViewById(R.id.shop_name);
        holder.distributionText = (TextView) cellView.findViewById(R.id.distribution_text);
        holder.distributionLayout = (LinearLayout) cellView.findViewById(R.id.distribution_layout);
        holder.leave_message = (ClearEditText) cellView.findViewById(R.id.leave_message);
        holder.content = (LinearLayout) cellView.findViewById(R.id.content);
        holder.num = (TextView) cellView.findViewById(R.id.num);
        holder.total = (TextView) cellView.findViewById(R.id.total);
        return holder;
    }

    @Override
    protected View buildData(final int position, View cellView, BusinessHolder cellHolder) {
        String totalMoney = "";
        SubmitOrderHolder holder = (SubmitOrderHolder) cellHolder;
        StringBuffer describeBuffer = new StringBuffer();
        final ShopCartProductData shopCartProductData = (ShopCartProductData) dataList.get(position);
        holder.shopName.setText(shopCartProductData.getShopName());
        holder.content.removeAllViews();
        for (String ids : shopCartProductData.getProductVOHashMap().keySet()) {
            ProductCart productCart = shopCartProductData.getProductVOHashMap().get(ids);
            View productView = LayoutInflater.from(mContext).inflate(R.layout.submit_order_product_item_layout, null);
            TextView name = (TextView) productView.findViewById(R.id.name);
            TextView describe = (TextView) productView.findViewById(R.id.describe);
            TextView price = (TextView) productView.findViewById(R.id.price);
            ImageView imageView = (ImageView) productView.findViewById(R.id.image);
            name.setText(productCart.getProductVO().getName());
            String imageUrl = "";
            if (!"".equals(productCart.getProductVO().getIcon())) {
                imageUrl = ControlUrl.BASE_URL + productCart.getProductVO().getIcon().split("\\|")[0];
            }
            mImageLoader.displayImage(imageUrl, imageView, XCApplication.options);

            if (productCart.getIdAndValues() != null) {
                for (Integer i : productCart.getIdAndValues().keySet()) {
                    describeBuffer.append(productCart.getIdAndValues().get(i).getValue()).append(" ");
                }
            }

            describeBuffer.append(" X ").append(productCart.getNum() + "件");
            describe.setText(describeBuffer.toString());
            holder.content.addView(productView);
            price.setText(productCart.getSalePrice() + "元");
            totalMoney = StringNumber.add(totalMoney,StringNumber.mul(String.valueOf(productCart.getSalePrice()),String.valueOf(productCart.getNum())));
        }

        if(shopCartProductData.getExpressPriceVO() != null){
            totalMoney = StringNumber.add(totalMoney,""+shopCartProductData.getExpressPriceVO().getPrice());
            holder.distributionText.setText(shopCartProductData.getExpressPriceVO().getName()+"  "+shopCartProductData.getExpressPriceVO().getPrice()+"元");
        }else{
            if(shopCartProductData.isFree()){//免运费
                holder.distributionText.setText(R.string.shop_free_express);
            }else{
                holder.distributionText.setText(R.string.please_select_express_model_title);
            }

        }
        holder.total.setText("¥"+totalMoney+"元");
        holder.num.setText("共"+shopCartProductData.getProductVOHashMap().size()+"件商品"+"     "+"合计：");


        holder.distributionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.selectExpressModel(position);
            }
        });
        return cellView;
    }



    public void updateIndexCell(Object o,int position,ListView listView){
        if(o == null || position == -1 || listView == null){
            throw new NullPointerException("data is null");
        }
        dataList.remove(position);
        dataList.add(position,o);
        int start = listView.getFirstVisiblePosition();
        View view = listView.getChildAt(position - start);
        getView(position, view, listView);
    }

}
