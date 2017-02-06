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
import com.banksoft.XinChengShop.entity.ProductTypeVO;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by harry_robin on 15/9/5.
 */
public class CategoryGridAdapter extends BaseMyAdapter {
    private ImageLoader mImageLoader;
    public CategoryGridAdapter(Context context, List dataList) {
        super(context, dataList);
        mImageLoader = ImageLoader.getInstance();
    }

    private class CategoryGridHolder extends BusinessHolder{
        private TextView name;
        private ImageView imageView;
    }
    @Override
    protected View createCellView() {
        return LayoutInflater.from(mContext).inflate(R.layout.category_grid_item_2_layout,null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        CategoryGridHolder holder = new CategoryGridHolder();
        holder.name = (TextView) cellView.findViewById(R.id.name);
        holder.imageView = (ImageView) cellView.findViewById(R.id.image);
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        CategoryGridHolder holder = (CategoryGridHolder) cellHolder;
        ProductTypeVO category = (ProductTypeVO) dataList.get(position);
        holder.name.setText(category.getName());
        mImageLoader.displayImage(ControlUrl.BASE_URL.toString()+category.getIcon(),holder.imageView, XCApplication.options);
        return cellView;
    }
}
