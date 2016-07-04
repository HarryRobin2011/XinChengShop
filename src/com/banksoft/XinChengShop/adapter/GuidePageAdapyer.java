package com.banksoft.XinChengShop.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.SharedPreTag;
import com.banksoft.XinChengShop.ui.XCMainActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

/**
 * Created by harry_robin on 15/9/8.
 */
public class GuidePageAdapyer extends PagerAdapter {
    public Integer[] imageUrls;
    public int currentPosition;
    public boolean isEdit = false;
    private Activity activity;

    private int mChildCount = 0;

    public GuidePageAdapyer(Activity activity, Integer[] imageUrls){
        this.activity = activity;
        this.imageUrls = imageUrls;
    }
    @Override
    public int getCount() {
        return imageUrls == null? 0:imageUrls.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.guide_item_layout,null);
        final ImageView imageView = (ImageView) view.findViewById(R.id.image);
        final TextView start = (TextView) view.findViewById(R.id.start);
        imageView.setBackgroundResource(imageUrls[position]);
        if(position == 2){
            start.setVisibility(View.VISIBLE);
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((XCBaseActivity)activity).preferences.edit().putBoolean(SharedPreTag.IS_GUDIE,true).commit();
                    Intent intent = new Intent(activity.getApplicationContext(), XCMainActivity.class);
                    activity.startActivity(intent);
                }
            });
        }else{
            start.setVisibility(View.GONE);
        }
        container.addView(view);
        return view;
    }





    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object)   {
        if ( mChildCount > 0) {
            mChildCount --;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }
}
