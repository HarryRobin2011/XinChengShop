package com.banksoft.XinChengShop.ui.takeout;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.fragment.takeout.TakeOutShopListFragment;
import com.banksoft.XinChengShop.ui.fragment.takeout.TakeOutTabbarFragment;

import java.util.List;

/**
 * Created by harry_robin on 16/3/17.
 */
public class TakeOutMainActivity extends XCBaseActivity implements TakeOutShopListFragment.LocationListener, TakeOutTabbarFragment.OnTabSelectListener {
    public LinearLayout nearbyLayout;
    public TextView nearbyName;
    public ImageView back;
    public ImageView bgImage;
    public TextView title;
    public TakeOutTabbarFragment takeOutTabbarFragment;
    public String shopId;

    @Override
    protected void initContentView() {
        setContentView(R.layout.take_out_main_layout);
    }

    @Override
    protected void initView() {
        nearbyName = (TextView) findViewById(R.id.nearby_name);
        nearbyLayout = (LinearLayout) findViewById(R.id.nearby_layout);
        back = (ImageView) findViewById(R.id.title_back_button);
        bgImage = (ImageView) findViewById(R.id.titleBg);
        title = (TextView) findViewById(R.id.titleText);
        takeOutTabbarFragment = TakeOutTabbarFragment.newInstance(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.take_tab_fragment, takeOutTabbarFragment).commit();
    }

    @Override
    protected void initData() {
        nearbyLayout.setVisibility(View.VISIBLE);
        bgImage.setBackgroundColor(getResources().getColor(R.color.white));
        back.setImageResource(R.drawable.wopc_arrow);
        back.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initOperate() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMyFragment();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setMyFragment();
    }

    private void setMyFragment() {
        if (takeOutTabbarFragment != null && takeOutTabbarFragment.currentFragment != null) {
            if (takeOutTabbarFragment.currentFragment.getClass().equals(TakeOutShopListFragment.class)) {
                finish();
            } else {
                takeOutTabbarFragment.onTabSelected(0);
            }
        }

    }

    /**
     * 定位成功
     *
     * @param location
     */
    @Override
    public void location(BDLocation location) {
        List<Poi> poiList = location.getPoiList();
        if (poiList.size() != 0) {
            nearbyName.setText(poiList.get(0).getName());
        }

    }

    @Override
    public void onTabSelect(int position) {
        switch (position) {
            case 0:
                nearbyLayout.setVisibility(View.VISIBLE);
                nearbyName.setVisibility(View.VISIBLE);
                title.setVisibility(View.GONE);
                break;
            case 1:
                nearbyLayout.setVisibility(View.GONE);
                nearbyName.setVisibility(View.GONE);
                title.setVisibility(View.VISIBLE);
                title.setText(R.string.order);
                break;
            case 2:
                nearbyLayout.setVisibility(View.GONE);
                nearbyName.setVisibility(View.GONE);
                title.setVisibility(View.VISIBLE);
                title.setText(R.string.my_self);
                break;

        }
    }
}
