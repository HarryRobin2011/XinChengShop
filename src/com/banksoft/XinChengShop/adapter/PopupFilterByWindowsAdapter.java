package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 14-8-15
 * Time: 下午3:13
 * To change this template use File | Settings | File Templates.
 */
public class PopupFilterByWindowsAdapter extends BaseMyAdapter {
    public int selectPosition = 0;

    public PopupFilterByWindowsAdapter(Context mContext, List dataList) {
        super(mContext, dataList);
    }

    private class PopupOrderByWindowsCellHolder extends BusinessHolder{
        LinearLayout layout;
        TextView name;
        TextView state;
    }



    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        String data = dataList.get(position).toString();
        PopupOrderByWindowsCellHolder holder = (PopupOrderByWindowsCellHolder) cellHolder;
        holder.layout.setBackgroundResource(R.color.white);

        if(position == selectPosition){
            holder.name.setTextColor(mContext.getResources().getColor(R.color.red_normal));
            holder.state.setTextColor(mContext.getResources().getColor(R.color.red_normal));
            holder.state.setText(R.string.check_the_number);
        }else{
            holder.state.setText("");
        }
        holder.name.setText(data);
        return cellView;
    }

    @Override
    public View createCellView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popup_windows_item_layout,null);
        return view;
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        PopupOrderByWindowsCellHolder holder = new PopupOrderByWindowsCellHolder();
        holder.layout = (LinearLayout) cellView.findViewById(R.id.layout);
        holder.name = (TextView) cellView.findViewById(R.id.name);
        holder.state = (TextView) cellView.findViewById(R.id.state);
        return holder;
    }
}
