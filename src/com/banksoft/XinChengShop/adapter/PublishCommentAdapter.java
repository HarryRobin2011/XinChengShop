package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.entity.OrderProductVO;
import com.banksoft.XinChengShop.entity.OrderVO;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by admin on 2016/7/11.
 */
public class PublishCommentAdapter extends BaseMyAdapter{
    private ImageLoader mImageLoader;

    public PublishCommentAdapter(Context context, List dataList) {
        super(context, dataList);
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
    }

    @Override
    protected View createCellView() {
        return mInflater.inflate(R.layout.publish_comment_item_layout,null);
    }

    private class PublishCommentHolder extends BusinessHolder{
        private ImageView imageView;
        private EditText editText;
        private RadioGroup radioGroup;
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        PublishCommentHolder holder = new PublishCommentHolder();
        holder.imageView = (ImageView) cellView.findViewById(R.id.image);
        holder.editText = (EditText) cellView.findViewById(R.id.data_edit_text);
        holder.radioGroup = (RadioGroup) cellView.findViewById(R.id.radio_group);
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        PublishCommentHolder holder = (PublishCommentHolder) cellHolder;
        OrderProductVO orderProductVO = (OrderProductVO) dataList.get(position);
        String imageUrl = orderProductVO.getImageFile();
        if(imageUrl == null || imageUrl.isEmpty()){
            imageUrl = "";
        }else{
            imageUrl = imageUrl.split("\\|")[0];
        }
        mImageLoader.displayImage(imageUrl,holder.imageView, XCApplication.options);
        return cellView;
    }
}
