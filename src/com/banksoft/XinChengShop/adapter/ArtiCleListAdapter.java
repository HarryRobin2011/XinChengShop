package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.entity.ArticleListVO;
import com.banksoft.XinChengShop.utils.TimeUtils;

import java.util.ArrayList;

/**
 * Created by Robin on 2016/4/12.
 */
public class ArtiCleListAdapter extends BaseMyAdapter {
    public ArtiCleListAdapter(Context mContext, ArrayList arrayList) {
        super(mContext, arrayList);
    }

    @Override
    protected View createCellView() {
        return LayoutInflater.from(mContext).inflate(R.layout.aricle_list_item_layout, null);
    }

    private class ArticleListHolder extends BusinessHolder {
        private TextView title;
        private TextView time;
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        ArticleListHolder articleListHolder = new ArticleListHolder();
        articleListHolder.title = (TextView) cellView.findViewById(R.id.title);
        articleListHolder.time = (TextView) cellView.findViewById(R.id.time);
        return articleListHolder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        ArticleListHolder holder = (ArticleListHolder) cellHolder;
        ArticleListVO articleListVO = (ArticleListVO) dataList.get(position);
        holder.title.setText(articleListVO.getTitle());
        holder.time.setText(TimeUtils.getTimeStr(articleListVO.getModifyTime(), TimeUtils.TimeType.MINUTE));
        return cellView;
    }
}
