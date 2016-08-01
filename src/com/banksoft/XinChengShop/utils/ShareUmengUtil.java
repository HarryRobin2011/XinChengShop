package com.banksoft.XinChengShop.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.entity.ShareToolEntity;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.BitmapUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Robin on 2016/7/19.
 * Share Tool
 */
public class ShareUmengUtil {
    private Context mContext;
    private Activity mActivity;
    private LinkedList tools;
    private String[] shareNames = new String[]{"QQ空间","QQ好友","微信朋友圈","微信好友"};
    private Integer[] shareIds = new Integer[]{R.drawable.ic_qq_space,R.drawable.ic_share_qq_bg,R.drawable.ic_remcommend_forword_friend_circle,R.drawable.ic_share_wx_bg};
    private String shareContext;
    private String shareImage;



    public ShareUmengUtil(Activity activity){
        this.mActivity = activity;
        this.mContext = activity.getApplicationContext();
    }

    public void setShareContext(String shareContext) {
        this.shareContext = shareContext;
    }

    public void setShareImage(String shareImage) {
        this.shareImage = shareImage;
    }

    public void show(){
        if(tools == null){
            tools = new LinkedList();
        }
        tools.clear();

        for (int i = 0;i<shareNames.length;i++){
            ShareToolEntity shareToolEntity = new ShareToolEntity();
            shareToolEntity.setName(shareNames[i]);
            shareToolEntity.setResId(shareIds[i]);
            tools.add(shareToolEntity);
        }
        shareFromToBottom();
    }


    private void shareFromToBottom() {
        View shareView = LayoutInflater.from(mContext).inflate(R.layout.share_from_to_bottom_layout, null);
        GridView gridView = (GridView) shareView.findViewById(R.id.my_gridview);
        TextView cancel = (TextView) shareView.findViewById(R.id.cancel);
        ShareToolsAdapter shareToolsAdapter = new ShareToolsAdapter();
         gridView.setAdapter(shareToolsAdapter);
        final PopupWindowUtil popupWindowUtil = new PopupWindowUtil((XCBaseActivity) mActivity, shareView, mActivity.findViewById(R.id.titleText));
        popupWindowUtil.backgroundAlpha(0.5f);
        popupWindowUtil.showPopupWindow();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShareAction shareAction = new ShareAction(mActivity);
                SHARE_MEDIA share_media = null;
                UMImage umImage = null;
                switch (position){
                    case 0:
                      share_media = SHARE_MEDIA.QZONE;
                        break;
                    case 1:
                        share_media = SHARE_MEDIA.QQ;
                        break;
                    case 2:
                        share_media = SHARE_MEDIA.WEIXIN;
                        break;
                    case 3:
                        share_media = SHARE_MEDIA.WEIXIN_CIRCLE;
                        break;
                }
                shareAction.withText(shareContext);
                if(shareImage != null && !shareImage.isEmpty()){
                    umImage = new UMImage(mContext, shareImage);
                    shareAction.withMedia(umImage);
                }
                shareAction.withTargetUrl(ControlUrl.WEI_XIN_DOWNLOAD_URL);
                shareAction.setPlatform(share_media).setCallback(umShareListener).share();
                popupWindowUtil.dismiss();;
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 popupWindowUtil.dismiss();
            }
        });

    }


    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            if(platform.name().equals("WEIXIN_FAVORITE")){
            }else{
                Toast.makeText(mContext, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(mContext,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){

            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(mContext,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    private class ShareToolsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return tools ==null? 0:tools.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.content_share_grid_item_layout, null);
            ShareToolEntity shareTool = (ShareToolEntity) tools.get(position);
            TextView name = (TextView) convertView.findViewById(R.id.name);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
            name.setText(shareTool.getName());
            imageView.setBackgroundResource(shareTool.getResId());
            return convertView;
        }
    }

}
