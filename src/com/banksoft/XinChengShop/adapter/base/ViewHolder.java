package com.banksoft.XinChengShop.adapter.base;

import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.RequestManager;

/**
 * Created by Robin on 2016/11/14.
 */
public class ViewHolder {
    private SparseArray<View> mViews;
    private int mLayoutId;
    private View mConvertView;
    public int position;
    private Callback mCaller;

    public interface Callback {
        RequestManager getImgLoader();

        LayoutInflater getInflate();
    }


    public ViewHolder(Callback caller, ViewGroup parent, int layoutId, int position) {
        this.mViews = new SparseArray<View>();
        this.position = position;
        this.mLayoutId = layoutId;
        this.mCaller = caller;
        LayoutInflater inflater = caller.getInflate();
        this.mConvertView = inflater.inflate(layoutId, parent, false);
        this.mConvertView.setTag(this);
    }

    /**
     * 获取一个viewHolder
     *
     * @param caller      Caller
     * @param convertView view
     * @param parent      parent view
     * @param layoutId    布局资源id
     * @param position    索引
     * @return
     */
    public static ViewHolder getViewHolder(Callback caller, View convertView, ViewGroup parent, int layoutId, int position) {
        boolean needCreateView = false;
        ViewHolder vh = null;
        if (convertView == null) {
            needCreateView = true;
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        if (vh != null && (vh.mLayoutId != layoutId)) {
            needCreateView = true;
        }
        if (needCreateView) {
            return new ViewHolder(caller, parent, layoutId, position);
        }
        return (ViewHolder) convertView.getTag();
    }

    public int getPosition() {
        return this.position;
    }

    public int getLayoutId() {
        return mLayoutId;
    }

    // 通过一个viewId来获取一个view
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    // 返回viewHolder的容器类
    public View getConvertView() {
        return this.mConvertView;
    }
}
