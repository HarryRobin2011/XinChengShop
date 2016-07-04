package com.banksoft.XinChengShop.ui.fragment;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.model.base.BaseData;
import com.banksoft.XinChengShop.ui.fragment.home.HomeBaseFragment;

import java.util.List;

/**
 * Created by harry_robin on 15/12/14.
 * 首页清仓甩卖
 */
public class HomeClearanceSaleFragment extends HomeBaseFragment {
    private ImageView leftIcon;
    private TextView title;
    private Button rightBtn;
    @Override
    public View initContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.home_cell_item_title_layout,null);
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public View setContentView() {
        return null;
    }

    @Override
    public List setBaseData(BaseData baseData) {
        return null;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initOperation() {

    }

    @Override
    public Fragment getInstance() {
        return null;
    }

    @Override
    public CharSequence getTitle() {
        return null;
    }
}
