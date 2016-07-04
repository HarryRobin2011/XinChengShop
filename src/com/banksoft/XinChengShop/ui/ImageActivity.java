package com.banksoft.XinChengShop.ui;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.adapter.ImageViewPagerAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.widget.imagezoom.ImageViewTouch;
import com.banksoft.XinChengShop.widget.viewpagerindicator.CirclePageIndicator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by harry_robin on 15/9/23.
 */
public class ImageActivity extends XCBaseActivity {
    private ViewPager viewPager;
    private CirclePageIndicator pageIndicator;
    private String[] imageUrls;
    private ImageViewPagerAdapter imageViewPagerAdapter;
    private List<View> viewList = new LinkedList<>();
    private int currentPosition;
    @Override
    protected void initContentView() {
        setContentView(R.layout.product_photo);
    }

    @Override
    protected void initView() {
        viewPager = (ViewPager) findViewById(R.id.fullscreen_viewpager);
        pageIndicator = (CirclePageIndicator) findViewById(R.id.mPageIndicator);
    }

    @Override
    protected void initData() {
        imageUrls = getIntent().getStringArrayExtra(IntentFlag.IMAGE_URL);
        currentPosition = getIntent().getIntExtra(IntentFlag.CURRENT_POSITION,0);
    }

    @Override
    protected void initOperate() {
        ImageLoader mImageLoader;
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        viewList.clear();
        for (String url:imageUrls){
            ImageViewTouch imageViewTouch = (ImageViewTouch) LayoutInflater.from(mContext).inflate(R.layout.image_layout_cell, null);
            mImageLoader.displayImage(ControlUrl.BASE_URL+url,imageViewTouch, XCApplication.options);
            viewList.add(imageViewTouch);
        }
        if(imageViewPagerAdapter == null){
            imageViewPagerAdapter = new ImageViewPagerAdapter(viewList);
        }
        viewPager.setAdapter(imageViewPagerAdapter);
        viewPager.setCurrentItem(currentPosition);
        pageIndicator.setViewPager(viewPager);
        pageIndicator.setCurrentItem(currentPosition);
    }
}
