package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.entity.Advertisement;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.HashMap;
import java.util.List;

/**
 * Created by harry_robin on 15/11/14.
 */
public class BannerViewPagerAdapter extends PagerAdapter {
    public List dataList;
    private Context mContext;
    private ImageLoader mImageLoader;
    private HashMap<Integer,View> viewHashMap;

    public BannerViewPagerAdapter(Context context,List dataList){
        this.dataList = dataList;
        this.mContext = context;
        mImageLoader = ImageLoader.getInstance();
        viewHashMap = new HashMap<>();
    };

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //container.removeView(container.getChildAt(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = (ImageView) container.getChildAt(position);
        if(imageView == null){
            imageView = (ImageView) LayoutInflater.from(mContext).inflate(R.layout.banner_image_cell, null);
            Advertisement advertisement = (Advertisement) dataList.get(position);
            mImageLoader.displayImage(ControlUrl.BASE_URL + advertisement.getImageUrl(), imageView, XCApplication.options);
            container.addView(imageView);
        }
        return imageView;
    }


    @Override
    public int getCount() {
        return dataList == null? 0:dataList.size() - 1;
    }




    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }




}
