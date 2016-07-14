package com.banksoft.XinChengShop.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.entity.OrderProductVO;
import com.banksoft.XinChengShop.entity.OrderVO;
import com.banksoft.XinChengShop.utils.PickPhotoUtil;
import com.banksoft.XinChengShop.utils.UUIDFactory;
import com.banksoft.XinChengShop.widget.MyGridView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sina.weibo.sdk.utils.ImageUtils;

import java.util.List;

/**
 * Created by admin on 2016/7/11.
 */
public class PublishCommentAdapter extends BaseMyAdapter{
    private ImageLoader mImageLoader;
    private Activity activity;

    public PublishCommentAdapter(Activity activity, List dataList) {
        super(activity.getApplicationContext(), dataList);
        mImageLoader = ImageLoader.getInstance();
        this.activity = activity;
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
        private MyGridView photoGridView;
        private ImageView takePhoto;
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        PublishCommentHolder holder = new PublishCommentHolder();
        holder.imageView = (ImageView) cellView.findViewById(R.id.image);
        holder.editText = (EditText) cellView.findViewById(R.id.data_edit_text);
        holder.radioGroup = (RadioGroup) cellView.findViewById(R.id.radio_group);
        holder.photoGridView = (MyGridView) cellView.findViewById(R.id.gridView);
        holder.takePhoto = (ImageView) cellView.findViewById(R.id.take_photo);
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
        mImageLoader.displayImage(ControlUrl.BASE_URL+imageUrl,holder.imageView, XCApplication.options);
        return cellView;
    }

    private void takePhoto(){
        PickPhotoUtil pickPhotoUtil = PickPhotoUtil.getInstance();
      //  pickPhotoUtil.takePhoto(activity, UUIDFactory.random(),)
    }
}
