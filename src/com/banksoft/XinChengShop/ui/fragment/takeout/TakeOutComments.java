package com.banksoft.XinChengShop.ui.fragment.takeout;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.ui.base.XCBaseFragment;

/**
 * 美食外卖评论列表
 * Created by harry_robin on 16/3/21.
 */
public class TakeOutComments extends XCBaseFragment{
    private FrameLayout frameLayout;
    @Override
    public View initContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.null_pager,null);
    }

    @Override
    public void initView(View view) {
        frameLayout = (FrameLayout) view.findViewById(R.id.null_pager);
    }

    @Override
    public void initData() {
        frameLayout.setVisibility(View.VISIBLE);
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
