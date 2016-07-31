package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.graphics.Paint;
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

import java.util.List;

/**
 * Created by harry_robin on 16/3/2.
 */
public class ClearanceAdapter extends BaseMyAdapter {
    private ImageLoader mImageLoader;
    public ClearanceAdapter(Context context, List dataList) {
        super(context, dataList);
        mImageLoader = ImageLoader.getInstance();
    }

    @Override
    protected View createCellView() {
        return LayoutInflater.from(mContext).inflate(R.layout.clean_sale_item_layout,null);
    }

    private class ClearanceHolder extends BusinessHolder{
        private ImageView imageView;
        private TextView describe;
        private TextView price;
        private TextView realPrice;
        private TextView num;
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        ClearanceHolder holder = new ClearanceHolder();
        holder.imageView = (ImageView) cellView.findViewById(R.id.image);
        holder.describe = (TextView) cellView.findViewById(R.id.describe);
        holder.price = (TextView) cellView.findViewById(R.id.price);
        holder.realPrice = (TextView) cellView.findViewById(R.id.real_price);
        holder.num = (TextView) cellView.findViewById(R.id.num);
        holder.realPrice.setTextColor(mContext.getResources().getColor(R.color.light_gray));
        holder.realPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG |Paint.ANTI_ALIAS_FLAG);//设置中划线并清晰
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        ClearanceHolder holder = (ClearanceHolder) cellHolder;
        ShopProductListVO shopProductListVO = (ShopProductListVO) dataList.get(position);
        String imageUrl = "";
        if(!"".equals(shopProductListVO.getIcon()) || shopProductListVO.getIcon() != null){
          imageUrl = ControlUrl.BASE_URL + shopProductListVO.getIcon().split("\\|")[0];
        }
        holder.describe.setText(shopProductListVO.getName());
        holder.price.setText(" ¥ "+shopProductListVO.getPrice());
        holder.realPrice.setText(" ¥ "+shopProductListVO.getSalePrice());
        holder.num.setText("已售："+(shopProductListVO.getGroupSale() + shopProductListVO.getVirtualGroup()));
        mImageLoader.displayImage(imageUrl,holder.imageView, XCApplication.options);
        return cellView;
    }
}
