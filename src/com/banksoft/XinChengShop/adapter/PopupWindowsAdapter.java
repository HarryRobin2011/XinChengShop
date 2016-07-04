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
 * Date: 14-7-18
 * Time: 下午2:52
 * To change this template use File | Settings | File Templates.
 */
public class PopupWindowsAdapter extends BaseMyAdapter {
    private int history = -1;
    private PopupType popupType;

    public PopupWindowsAdapter(Context mContext, List dataList) {
        super(mContext, dataList);
    }

    private class PopupWindowsCellHolder extends BusinessHolder{
        private LinearLayout layout;
        private TextView name;
        private TextView count;
        private TextView arrow;
    }


    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        String itemString = (String) dataList.get(position);
        final PopupWindowsCellHolder holder = (PopupWindowsCellHolder) cellHolder;
        holder.name.setText(itemString);
        if(PopupType.GROUP.equals(popupType)){
            holder.layout.setBackgroundResource(R.color.white);
        }else{
            holder.layout.setBackgroundResource(R.color.text_bg_color);
        }

        return cellView;
    }




    @Override
    public View createCellView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popup_windows_item_layout,null);
        return view;
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        PopupWindowsCellHolder holder = new PopupWindowsCellHolder();
        holder.layout = (LinearLayout) cellView.findViewById(R.id.layout);
        holder.name = (TextView) cellView.findViewById(R.id.name);
        holder.arrow = (TextView) cellView.findViewById(R.id.arrow);
        return holder;
    }


    public void setPopupType(PopupType popupType) {
        this.popupType = popupType;
    }

    public enum PopupType{
        GROUP,
        CHILD
    }
}
