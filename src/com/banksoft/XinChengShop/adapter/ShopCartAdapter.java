package com.banksoft.XinChengShop.adapter;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.entity.ProductCart;
import com.banksoft.XinChengShop.model.ShopCartProductData;
import com.banksoft.XinChengShop.ui.fragment.XCShopCartFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by harry_robin on 15/11/28.
 */
public class ShopCartAdapter extends BaseMyAdapter{
    private XCShopCartFragment xcShopCartFragment;
    public ShopCartAdapter(Fragment fragment, List dataList) {
        super(fragment.getActivity().getApplicationContext(), dataList);
        xcShopCartFragment = (XCShopCartFragment) fragment;
    }
    private ImageLoader mImageLoader;

    @Override
    protected View createCellView() {
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        return LinearLayout.inflate(mContext,R.layout.sale_cart_list_item_layout,null);
    }

    private class ShopCartHolder extends BusinessHolder
    {
        private CheckBox allSelect;
        private TextView shopName;
        private LinearLayout content;
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        ShopCartHolder shopCartHolder = new ShopCartHolder();
        shopCartHolder.allSelect = (CheckBox) cellView.findViewById(R.id.check_box);
        shopCartHolder.shopName = (TextView) cellView.findViewById(R.id.shop_name);
        shopCartHolder.content = (LinearLayout) cellView.findViewById(R.id.content);
        return shopCartHolder;
    }

    @Override
    protected View buildData(final int position, View cellView, BusinessHolder cellHolder) {
        boolean allSelect = true;
        boolean isFree = true;//是否免运费
        ShopCartHolder holder = (ShopCartHolder) cellHolder;

        final ShopCartProductData shopCartProductData = (ShopCartProductData) dataList.get(position);
        holder.shopName.setText(shopCartProductData.getShopName());
        holder.content.removeAllViews();
        holder.allSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateItem(position, shopCartProductData, isChecked, buttonView.getId());
            }
        });
        for (String productIds:shopCartProductData.getProductVOHashMap().keySet()){
            final ProductCart productCart = shopCartProductData.getProductVOHashMap().get(productIds);
            if(!productCart.isSelect()){
              allSelect = false;
            }

            if(!productCart.getProductVO().isFreight()){
               isFree = false;
            }
            shopCartProductData.setIsFree(isFree);

            final int[] count = {productCart.getNum()};
            View view = LinearLayout.inflate(mContext, R.layout.sale_product_cart_list_item_layout, null);
            final CheckBox checkBox = (CheckBox) view.findViewById(R.id.check_child_box);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            TextView productName = (TextView) view.findViewById(R.id.name);
            TextView priceText = (TextView) view.findViewById(R.id.real_price);
            ImageButton cartReduce = (ImageButton) view.findViewById(R.id.cart_single_product_num_reduce);
            ImageButton cartAdd = (ImageButton) view.findViewById(R.id.cart_single_product_num_add);
            final TextView num = (TextView) view.findViewById(R.id.cart_single_product_et_num);
            String imageUrl = "";
            if(!"".equals(productCart.getProductVO().getIcon()) && productCart.getProductVO().getIcon() != null){
               imageUrl = ControlUrl.BASE_URL + productCart.getProductVO().getIcon().split("\\|")[0];
            }else{
               imageUrl = "";

            }
            mImageLoader.displayImage(imageUrl,imageView, XCApplication.options);
            productName.setText(productCart.getProductVO().getName());
            num.setText(productCart.getNum()+"");
            priceText.setText(productCart.getSalePrice()+"元");
            holder.content.addView(view);
            checkBox.setChecked(productCart.isSelect());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    productCart.setIsSelect(isChecked);
                    updateItem(position,shopCartProductData, isChecked, buttonView.getId());
                    xcShopCartFragment.setTotalMoney((LinkedList<ShopCartProductData>) dataList);
                }
            });
            cartReduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (count[0] > 1) {
                        count[0] = count[0] - 1;
                        num.setText(count[0] + "");
                        productCart.setNum(count[0]);
                        xcShopCartFragment.setTotalMoney((LinkedList<ShopCartProductData>) dataList);
                    }

                }
            });
            cartAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     if(count[0] < productCart.getProductVO().getStockNum()){

                       count[0] = count[0] + 1;
                         num.setText(count[0] + "");
                         productCart.setNum(count[0]);
                         xcShopCartFragment.setTotalMoney((LinkedList<ShopCartProductData>) dataList);
                     }
                }
            });
        }
        holder.allSelect.setChecked(allSelect);
        return cellView;
    }

    private void updateItem(int index,ShopCartProductData productData,boolean isSelect,int resID){
        View parentView = dataMap.get(index);

        LinearLayout content = (LinearLayout) parentView.findViewById(R.id.content);
        CheckBox checkBox = (CheckBox) parentView.findViewById(R.id.check_box);
        switch (resID){
            case R.id.check_child_box://选择全部产品
                boolean isAll = true;
                for (int position = 0;position < productData.getProductVOHashMap().keySet().size();position++){
                    ProductCart productCart = productData.getProductVOHashMap().get(productData.getProductVOHashMap().keySet().toArray()[position]);
                    if(!productCart.isSelect()){
                       isAll = false;
                    }
                }
                checkBox.setChecked(isAll);
                break;
            case R.id.check_box:// 选择产品
                for (int position = 0;position < productData.getProductVOHashMap().keySet().size();position++){
                    ProductCart productCart = productData.getProductVOHashMap().get(productData.getProductVOHashMap().keySet().toArray()[position]);
                    productCart.setIsSelect(isSelect);
                 //   CheckBox checkChildBox = (CheckBox) content.getChildAt(position).findViewById(R.id.check_child_box);
                   // checkChildBox.setChecked(isSelect);
                }
                break;

        }


        xcShopCartFragment.setTotalMoney((LinkedList<ShopCartProductData>) dataList);

    }
}
