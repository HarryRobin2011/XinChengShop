package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.entity.IdAndValue;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by harry_robin on 15/11/20.
 */
public class ProductStandardGridAdapter extends BaseMyAdapter {
    private ImageLoader mImageLoader;
    private boolean isImage;
    public View lastView;

    /**
     * @param context
     * @param dataList
     * @param isImage  是否图片尺码
     */
    public ProductStandardGridAdapter(Context context, List dataList, boolean isImage) {
        super(context, dataList);
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        this.isImage = isImage;
    }

    private class ProductStandardHolder extends BusinessHolder {
        private TextView name;
        private ImageView imageView;
        private FrameLayout contentLayout;
    }

    @Override
    protected View createCellView() {
        return LayoutInflater.from(mContext).inflate(R.layout.product_standard_grid_item_layout, null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        ProductStandardHolder holder = new ProductStandardHolder();
        holder.name = (TextView) cellView.findViewById(R.id.name);
        holder.imageView = (ImageView) cellView.findViewById(R.id.image);
        holder.contentLayout = (FrameLayout) cellView.findViewById(R.id.content_layout);
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        final ProductStandardHolder holder = (ProductStandardHolder) cellHolder;
        IdAndValue idAndValue = (IdAndValue) dataList.get(position);
        if (isImage) {
            holder.imageView.setVisibility(View.VISIBLE);
            holder.name.setVisibility(View.GONE);
            mImageLoader.displayImage(ControlUrl.BASE_URL + idAndValue.getImage(), holder.imageView, XCApplication.options);
        }else{
            holder.imageView.setVisibility(View.GONE);
            holder.name.setVisibility(View.VISIBLE);
            holder.name.setText(idAndValue.getValue());
        }
        holder.name.setText(idAndValue.getValue());
//        holder.contentLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (lastView != null) {
//                    lastView.setSelected(false);
//                }
//                if (v.isSelected()) {
//                    holder.contentLayout.setSelected(false);
//                } else {
//                    holder.contentLayout.setSelected(true);
//                    ofsizeView.setText(ofsizeView.getText().toString()+"  "+);
//                }
//                lastView = v;
//            }
//        });
        return cellView;
    }
}
