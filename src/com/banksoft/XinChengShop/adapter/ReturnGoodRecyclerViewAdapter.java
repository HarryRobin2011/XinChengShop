package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by Robin on 2016/7/25.
 */
public class ReturnGoodRecyclerViewAdapter extends RecyclerView.Adapter<ReturnGoodRecyclerViewAdapter.ReturnGoodRecyclerViewHolder> implements View.OnClickListener,View.OnLongClickListener{

    public List dataList;
    private Context mContext;
    private boolean isEdit = false;
    private ImageLoader mImageLoader;
    private int currentRow;

    private OnRecylerViewOnItemLinstener onRecylerViewOnItemLinstener;

    public ReturnGoodRecyclerViewAdapter(Context mContext, List dataList) {
        this.dataList = dataList;
        this.mContext = mContext;
        this.mImageLoader = ImageLoader.getInstance();
        this.mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
    }

    public void setOnRecylerViewOnItemLinstener(OnRecylerViewOnItemLinstener onRecylerViewOnItemLinstener) {
        this.onRecylerViewOnItemLinstener = onRecylerViewOnItemLinstener;
    }

    @Override
    public ReturnGoodRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.photo_grid_item_layout,null);
        ReturnGoodRecyclerViewHolder serviceGridViewHolder = new ReturnGoodRecyclerViewHolder(view);
        serviceGridViewHolder.contentView = view.findViewById(R.id.content_layout);
        serviceGridViewHolder.mImageView = (ImageView) view.findViewById(R.id.image_view);
        serviceGridViewHolder.close = (ImageView) view.findViewById(R.id.close);
        return serviceGridViewHolder;
    }

    @Override
    public void onBindViewHolder(ReturnGoodRecyclerViewHolder holder, int position) {
        String imagePath = (String) dataList.get(position);
        if(position == dataList.size() - 1){//最后一项
          holder.mImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
          holder.mImageView.setImageResource(R.drawable.take_photo);
        }else{
            holder.mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageLoader.displayImage("file://"+imagePath,holder.mImageView, XCApplication.options);
        }

        if(isEdit){
            if(dataList.size() <= 3){
                if(position == dataList.size() - 1){
                   holder.close.setVisibility(View.GONE);
                }else {
                    holder.close.setVisibility(View.VISIBLE);
                }
            }else{
                holder.close.setVisibility(View.VISIBLE);
            }
        }else{
            holder.close.setVisibility(View.GONE);
        }
        holder.close.setTag(position);
        holder.close.setOnClickListener(this);
        holder.contentView.setTag(position);
        holder.contentView.setOnLongClickListener(this);
        holder.contentView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        int count = dataList == null?0:dataList.size();
        if(count > 3){ // 最多张图片
           count = 3;
        }
        return count;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.close:
                deleteItem((Integer) v.getTag());
                break;
            case R.id.content_layout:
                onRecylerViewOnItemLinstener.onItemLinstener(currentRow, (Integer) v.getTag(),dataList.size());
                break;
        }
    }

    /**
     * 删除某个item
     */
    public boolean deleteItem(int position) {
        if(position<0 || position >= dataList.size()){
            return false;
        }
        notifyItemRemoved(position);
        dataList.remove(position);
        notifyItemRangeChanged(position, dataList.size()-position);
        return true;
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()){
            case R.id.content_layout:
                if(isEdit){
                    isEdit = false;
                }else {
                    isEdit = true;
                }
                notifyDataSetChanged();
                break;
        }
        return true;
    }

    public class ReturnGoodRecyclerViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;
        private ImageView close;
        private View contentView;
        public ReturnGoodRecyclerViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnRecylerViewOnItemLinstener{
        public void onItemLinstener(int currentRow, int position, int dataSize);
    }
}
