package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.entity.ProductAssessDTO;
import com.banksoft.XinChengShop.entity.ProductAssessFrontDTO;
import com.banksoft.XinChengShop.utils.TimeUtils;

import java.util.ArrayList;

/**
 * Created by harry_robin on 16/1/24.
 */
public class MyCommonListAdapter extends BaseMyAdapter {
    public MyCommonListAdapter(Context mContext, ArrayList arrayList) {
        super(mContext,arrayList);
    }

    private class CommonListHolder extends BusinessHolder{
        private ImageView mImageView;
        private TextView common;
        private TextView productName;
        private TextView content;
        private TextView time;
        private LinearLayout imageLayout;
    }

    @Override
    protected View createCellView() {
        return LinearLayout.inflate(mContext, R.layout.common_list_item_layout,null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        CommonListHolder commonListHolder = new CommonListHolder();
        commonListHolder.common = (TextView) cellView.findViewById(R.id.common);
        commonListHolder.productName = (TextView) cellView.findViewById(R.id.product_name);
        commonListHolder.content = (TextView) cellView.findViewById(R.id.content);
        commonListHolder.time = (TextView) cellView.findViewById(R.id.time);
        commonListHolder.imageLayout = (LinearLayout) cellView.findViewById(R.id.image_layout);
        return commonListHolder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        CommonListHolder commonListHolder = (CommonListHolder) cellHolder;
        ProductAssessDTO assessDTO = (ProductAssessDTO) dataList.get(position);
        commonListHolder.productName.setText(assessDTO.getProductName());
        commonListHolder.content.setText(assessDTO.getContent());
        commonListHolder.time.setText(TimeUtils.getTimeStr(assessDTO.getCreateTime(), TimeUtils.TimeType.SECOND));
        String images = assessDTO.getImages();
        if(images != null && "".equals(images)){
            for(String image:images.split("\\|")){
                View itemView  = mInflater.inflate(R.layout.image_layout_cell,null);
            }
        }

        switch(Integer.valueOf(assessDTO.getScore())){
            case 1:
                commonListHolder.common.setText(R.string.high_praise);
                commonListHolder.common.setTextColor(mContext.getResources().getColor(R.color.red_normal));
                break;
            case 0:
                commonListHolder.common.setText(R.string.middle_praise);
                commonListHolder.common.setTextColor(mContext.getResources().getColor(R.color.orange_500));
                break;
            case -1:
                commonListHolder.common.setText(R.string.poor_praise);
                commonListHolder.common.setTextColor(mContext.getResources().getColor(R.color.shop_green_press));
                break;
        }
        return null;
    }
}
