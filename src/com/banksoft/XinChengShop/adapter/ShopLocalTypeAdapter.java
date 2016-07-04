package com.banksoft.XinChengShop.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.entity.ShopLocalType;
import com.banksoft.XinChengShop.type.ShopType;
import com.banksoft.XinChengShop.ui.ProductListActivity;
import com.banksoft.XinChengShop.ui.ShopListActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by admin on 2016/6/24.
 */
public class ShopLocalTypeAdapter extends BaseMyAdapter{
    private ImageLoader mImageLoader;
    private Activity activity;

    public ShopLocalTypeAdapter(Activity activity, List dataList) {
        super(activity.getApplicationContext(), dataList);
        this.activity = activity;
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
    }

    private class ShopLocalTypeHolder extends BusinessHolder{
        private TextView name;
        private ImageView imageView;
    }

    @Override
    protected View createCellView() {
        return mInflater.inflate(R.layout.category_grid_item_3_layout,null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        ShopLocalTypeHolder shopLocalTypeHolder = new ShopLocalTypeHolder();
        shopLocalTypeHolder.name = (TextView) cellView.findViewById(R.id.name);
        shopLocalTypeHolder.imageView = (ImageView) cellView.findViewById(R.id.image);
        return shopLocalTypeHolder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        ShopLocalType shopLocalType = (ShopLocalType) dataList.get(position);
        ShopLocalTypeHolder holder = (ShopLocalTypeHolder) cellHolder;
        holder.name.setText(shopLocalType.getName());
        holder.imageView.setBackgroundResource(shopLocalType.getImageResID());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShopListActivity.class);
                intent.putExtra(IntentFlag.NO,shopLocalType.getId());
                intent.putExtra(IntentFlag.SHOP_SERVER_TYPE, ShopType.SHOP_LOCAL.toString());
                intent.putExtra(IntentFlag.TITLE,shopLocalType.getName());
                activity.startActivity(intent);
            }
        });
        return cellView;
    }
}
