package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.adapter.PublishCommentAdapter;
import com.banksoft.XinChengShop.adapter.PublishCommentRecyclerViewAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.PublishCommentDao;
import com.banksoft.XinChengShop.entity.*;
import com.banksoft.XinChengShop.model.ImageUrlData;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.utils.Check;
import com.banksoft.XinChengShop.utils.CommonUtil;
import com.banksoft.XinChengShop.utils.ImageUtil;
import com.banksoft.XinChengShop.utils.PopupWindowUtil;
import com.banksoft.XinChengShop.widget.MyGridView;
import com.banksoft.XinChengShop.widget.MyProgressDialog;
import com.banksoft.XinChengShop.widget.alertview.AlertView;
import com.banksoft.XinChengShop.widget.alertview.OnItemClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by admin on 2016/7/11.
 */
public class PublishCommentActivity extends XCBaseActivity implements View.OnClickListener, OnItemClickListener, AdapterView.OnItemClickListener,PublishCommentRecyclerViewAdapter.OnRecylerViewOnItemLinstener {
    private ListView mListView;
    private TextView title;
    private ImageView back;
    private PublishCommentDao publishCommentDao;
    private MyProgressDialog progressDialog;
    private CheckBox checkBox;
    private boolean isAnonymous;
    private Button publish;

    private LinearLayout content;

    private ImageLoader mImageLoader;


    private OrderVO currentOrderVo;

    private View footView;

    private AlertView alertView;
    private String localTempImgDir = "xinchengShop/";
    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 0;

    private static final int SELECT_PICTURE = 1;

    private RatingBar describeRating;
    private RatingBar expressService;
    private RatingBar serviceAttitude;


    private HashMap<String,LinkedList> photoMap;

    private int currentRow;// 拍照当前行数

    private String capturePath;// 当前拍照的图片路径

    @Override
    protected void initContentView() {
        setContentView(R.layout.publish_comment_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
        publish = (Button) findViewById(R.id.publish);
        checkBox = (CheckBox) findViewById(R.id.anonymous);
        content = (LinearLayout) findViewById(R.id.content);
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        describeRating = (RatingBar) findViewById(R.id.describe_ratingBar);
        expressService = (RatingBar) findViewById(R.id.express_service);
        serviceAttitude = (RatingBar) findViewById(R.id.service_attitude);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
            if (resultCode == Activity.RESULT_CANCELED) {
                return;
            }
             setCaremaImage();
        } else if (requestCode == SELECT_PICTURE) {
            if (data == null) {
                return;
            }
            //选择图片
            Uri uri = data.getData();
            //      cameraImage.put(currentPosition, ImageUtil.getImageAbsolutePath(this,uri));
                capturePath = ImageUtil.getImageAbsolutePath(this,uri);
                setCaremaImage();
        }
    }

    private void setCaremaImage() {
        View view = content.getChildAt(currentRow);//点击拍照的行View
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        PublishCommentRecyclerViewAdapter adapter = (PublishCommentRecyclerViewAdapter) recyclerView.getAdapter();
        adapter.dataList.add(0,capturePath);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void initData() {
        alertView = new AlertView(null, null, "取消", null, new String[]{"拍照", "从相册中选择"}, this, AlertView.Style.ActionSheet, this);
        currentOrderVo = (OrderVO) getIntent().getExtras().getSerializable(IntentFlag.ORDER_VO);
        footView = LayoutInflater.from(mContext).inflate(R.layout.publish_comment_foot_layout, null);
        title.setText(R.string.publish_comment_title);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isAnonymous = isChecked;
            }
        });
        publish.setOnClickListener(this);
    }

    @Override
    protected void initOperate() {
        setProductViews();
    }

    private void setProductViews() {
        if(photoMap == null){
            photoMap = new HashMap<>();
        }
        for (int i = 0; i < currentOrderVo.getList().size();i++) {
            OrderProductVO orderProductVO = currentOrderVo.getList().get(i);
            LinkedList photoList = new LinkedList();
            photoList.add(ControlUrl.ANDROID_ASSETS_BASE_PATH+"take_photo.png");
            photoMap.put(orderProductVO.getProductId(),photoList);
            ProductAssessBO productAssessBO = new ProductAssessBO();
            productAssessBO.setOrderId(orderProductVO.getOrderId());
            productAssessBO.setProductId(orderProductVO.getProductId());
            productAssessBO.setMemberId(member.getMember().getId());
            productAssessBO.setShopId(currentOrderVo.getShopId());
            productAssessBO.setSiteId(currentOrderVo.getSiteId());

            View view = LayoutInflater.from(mContext).inflate(R.layout.publish_comment_item_layout, null);
            view.setTag(productAssessBO);


            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            EditText editText = (EditText) view.findViewById(R.id.data_edit_text);
            RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);


            GridLayoutManager manager = new GridLayoutManager(mContext,4);
            recyclerView.setLayoutManager(manager);
            PublishCommentRecyclerViewAdapter adapter = new PublishCommentRecyclerViewAdapter(mContext,photoMap.get(orderProductVO.getProductId()));
            adapter.setCurrentRow(i);
            adapter.setOnRecylerViewOnItemLinstener(this);
            recyclerView.setAdapter(adapter);

            String imageUrl = orderProductVO.getImageFile();
            if (imageUrl == null || imageUrl.isEmpty()) {
                imageUrl = "";
            } else {
                imageUrl = imageUrl.split("\\|")[0];
            }
            mImageLoader.displayImage(ControlUrl.BASE_URL + imageUrl, imageView, XCApplication.options);

            content.addView(view);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back_button:
                finish();
                break;
            case R.id.publish:
                LinkedList productAssessBoLIST = (LinkedList) getProductAssessBOList();
                OrderAssessBO orderAssessBO = getOrderAssessBO();
                if(productAssessBoLIST == null || orderAssessBO == null){
                    alert(R.string.pleaset_shop_scode);
                    return;
                }
                if(publishCommentDao == null){
                    publishCommentDao = new PublishCommentDao(mContext);
                }
                new MyTask(productAssessBoLIST,orderAssessBO).execute(publishCommentDao);
                break;
        }
    }

    /**
     * 获取评价
     *
     * @return
     */
    private List<ProductAssessBO> getProductAssessBOList() {
        LinkedList productAssessBOList = new LinkedList();

        for (int i = 0; i < content.getChildCount(); i++) {
            final int[] score = new int[1];
            score[0] = 1;//默认好评
            int c = 0;
            View itemView = content.getChildAt(i);
            EditText editText = (EditText) itemView.findViewById(R.id.data_edit_text);
            RadioGroup radioGroup = (RadioGroup) itemView.findViewById(R.id.radio_group);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.comment_good:
                            score[0] = 1;
                            break;
                        case R.id.comment_middle:
                            score[0] = 0;
                            break;
                        case R.id.comment_bad:
                            score[0] = -1;
                            break;
                    }
                }
            });
            ProductAssessBO productAssessBO = (ProductAssessBO) itemView.getTag();
            productAssessBO.setContent(editText.getText().toString());
            productAssessBO.setCreateTime(System.currentTimeMillis());
            productAssessBO.setScore(score[0]);
            productAssessBOList.add(productAssessBO);
        }
        return productAssessBOList;
    }

    /**
     * 获取店铺评论
     *
     * @return
     */
    private OrderAssessBO getOrderAssessBO() {
        OrderAssessBO orderAssessBO = new OrderAssessBO();
        orderAssessBO.setCreateTime(System.currentTimeMillis());
        orderAssessBO.setContent("");
        orderAssessBO.setSiteId(currentOrderVo.getSiteId());
        orderAssessBO.setShopId(currentOrderVo.getShopId());
        orderAssessBO.setOrderId(currentOrderVo.getId());
        if(serviceAttitude.getRating() == 0f || describeRating.getRating() == 0f || expressService.getRating() == 0f){
          return null;
        }
        orderAssessBO.setServiceScore(Math.round(serviceAttitude.getRating()));
        orderAssessBO.setDispatchScore(Math.round(expressService.getRating()));
        orderAssessBO.setMatchScore(Math.round(describeRating.getRating()));
        orderAssessBO.setMemberId(member.getMember().getId());
        return orderAssessBO;
    }


    @Override
    public void onItemClick(Object o, int position) {
        switch (position) {
            case 0:
                String state = Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                       getImageFromCamera();
                } else {
                    Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
                }
                alertView.dismiss();
                break;
            case 1:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "选择图片"), SELECT_PICTURE);
                alertView.dismiss();
                break;
            case 2:
                alertView.dismiss();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /**
     * 晒图点击
     * @param currentRow
     * @param position
     */
    @Override
    public void onItemLinstener(int currentRow, int position,int dataSize) {
        this.currentRow = currentRow;
        if(dataSize >4){// 跳转图片详情页

        }else{
            if(position == dataSize - 1){// 拍照
                if(alertView.isShowing()){
                    alertView.dismiss();
                }else{
                    alertView.show();
                }

            }else{//跳转图片详情

            }
        }
    }

    private class MyTask extends AsyncTask<PublishCommentDao, String, IsFlagData> {
        List<ProductAssessBO> productAssessBOList;
        OrderAssessBO orderAssessBO;

        public MyTask(List<ProductAssessBO> productAssessBOList, OrderAssessBO orderAssessBO) {
            this.productAssessBOList = productAssessBOList;
            this.orderAssessBO = orderAssessBO;
        }

        @Override
        protected void onPostExecute(IsFlagData isFlagData) {
            super.onPostExecute(isFlagData);
            progressDialog.dismiss();
            if (isFlagData != null) {
                if (isFlagData.isSuccess()) {
                    alert(R.string.comment_success);
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    alert(isFlagData.getMsg().toString());
                }
            } else {
                alert(R.string.net_error);
            }
        }

        @Override
        protected IsFlagData doInBackground(PublishCommentDao... params) {
            for(ProductAssessBO productAssessBO:productAssessBOList){
                List imageList = photoMap.get(productAssessBO.getProductId());
                StringBuffer imageBuffer = new StringBuffer();
                if(imageList != null && imageList.size() > 1){
                    for(Object data:imageList){
                        String path = (String) data;
                        File file = new File(path);
                        String type = path.substring(path.lastIndexOf(".")+1,path.length());
                        ImageUrlData imageUrl = params[0].submitImage(file, type);
                        if(imageUrl == null){
                            continue;
                        }
                        imageBuffer.append(imageUrl.getData()).append("|");
                    }
                }
                productAssessBO.setImages(imageBuffer.toString());
            }
            return params[0].submitComment(member.getMember().getId(), productAssessBOList, orderAssessBO);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = new MyProgressDialog(PublishCommentActivity.this);
            }
            progressDialog.showDialog(R.string.please_wait);
        }
    }

    /**
     * 拍照
     */
    protected void getImageFromCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
            String out_file_path = Environment.getExternalStorageDirectory() + "/" + localTempImgDir;
            File dir = new File(out_file_path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            capturePath = out_file_path + System.currentTimeMillis() + ".jpg";
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(capturePath)));
            getImageByCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(getImageByCamera, REQUEST_CODE_CAPTURE_CAMEIA);
        } else {
            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }

    private void showTakePhotoView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.take_photo_tool_layout, null);
        TextView describe = (TextView) view.findViewById(R.id.take_photo_num);
        PopupWindowUtil popupWindowUtil = new PopupWindowUtil(PublishCommentActivity.this, view, findViewById(R.id.publish));
    }
}
