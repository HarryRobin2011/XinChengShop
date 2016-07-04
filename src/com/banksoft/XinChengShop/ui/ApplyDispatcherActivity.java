package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.ApplyDispatchDao;
import com.banksoft.XinChengShop.entity.DispatchMember;
import com.banksoft.XinChengShop.model.ImageUrlData;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.utils.Check;
import com.banksoft.XinChengShop.utils.CommonUtil;
import com.banksoft.XinChengShop.utils.ImageUtil;
import com.banksoft.XinChengShop.widget.MyProgressDialog;
import com.banksoft.XinChengShop.widget.alertview.AlertView;
import com.banksoft.XinChengShop.widget.alertview.OnItemClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.util.HashMap;

/**
 * Created by harry_robin on 16/1/25.
 */
public class ApplyDispatcherActivity extends XCBaseActivity implements View.OnClickListener, OnItemClickListener {
    private ImageView headImage, positive, opposite, back;
    private EditText identityCard;
    private EditText realName;
    private EditText telPhone;
    private TextView title;
    private Button apply;
    private ApplyDispatchDao applyDispatchDao;
    private MyProgressDialog myProgressDialog;
    private AlertView alertView;
    private String localTempImgDir = "xinchengShop/";
    private HashMap<Integer, String> cameraImage = new HashMap<>();
    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 0;

    private static final int SELECT_PICTURE = 1;
    private int currentPosition;//点击的 第几个图片
    private ImageLoader mImageLoader;//

    @Override
    protected void initContentView() {
        setContentView(R.layout.apply_dispatch_layout);
    }

    @Override
    protected void initView() {
        headImage = (ImageView) findViewById(R.id.head_image);
        positive = (ImageView) findViewById(R.id.positive);
        opposite = (ImageView) findViewById(R.id.opposite);
        identityCard = (EditText) findViewById(R.id.identity_card);
        realName = (EditText) findViewById(R.id.real_name);
        telPhone = (EditText) findViewById(R.id.telPhone);
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
        apply = (Button) findViewById(R.id.titleRightButton);
    }

    @Override
    protected void initData() {
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        apply.setVisibility(View.VISIBLE);
        apply.setOnClickListener(this);
        apply.setText(R.string.apply);
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        title.setText(R.string.apply_dispatch);
    }

    @Override
    protected void initOperate() {
        alertView = new AlertView(null, null, "取消", null, new String[]{"拍照", "从相册中选择"}, this, AlertView.Style.ActionSheet, this);
        headImage.setOnClickListener(this);
        positive.setOnClickListener(this);
        opposite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back_button:
                break;

            case R.id.positive:
                currentPosition = 1;
                if (alertView.isShowing()) {
                    alertView.dismiss();
                } else {
                    alertView.show();
                }
                break;
            case R.id.opposite:
                currentPosition = 2;

                alertView.show();
                break;
            case R.id.head_image:
                currentPosition = 0;

                alertView.show();
                break;
            case R.id.titleRightButton:
                DispatchMember member = getDispatchMember();
                if (member != null) {
                    if (applyDispatchDao == null) {
                        applyDispatchDao = new ApplyDispatchDao(mContext);
                    }
                    new MyThread(member).execute(applyDispatchDao);
                }
                break;
        }
    }

    private DispatchMember getDispatchMember() {
        DispatchMember dispatchMember = new DispatchMember();
        String idCard = identityCard.getText().toString();//身份证号
        String telPhoneStr = telPhone.getText().toString();// 电话
        String realNameStr = realName.getText().toString();// 真实姓名

        if (idCard.isEmpty()) {
            alert(R.string.identity_card_no_empty);
            return null;
        } else if (telPhoneStr.isEmpty()) {
            alert(R.string.telphone_no_empty);
            return null;
        } else if (realNameStr.isEmpty()) {
            alert(R.string.real_name_no_empty);
            return null;
        }else if(!Check.isCellphone(telPhoneStr)){
            alert(R.string.telphone_format_error);
        }

        dispatchMember.setSiteId(member.getMember().getSiteId());
        dispatchMember.setName(realNameStr);
        dispatchMember.setIdCard(idCard);
        dispatchMember.setTelephone(telPhoneStr);
        dispatchMember.setReason(realNameStr);
        return dispatchMember;
    }

    @Override
    public void onItemClick(Object o, int position) {
        switch (position) {
            case 0:
                alertView.dismiss();
                String state = Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    getImageFromCamera();
                } else {
                    Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
                }
                break;
            case 1:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "选择图片"), SELECT_PICTURE);
                break;
            case 2:
                break;
        }
    }

    protected void getImageFromCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
            String out_file_path = Environment.getExternalStorageDirectory() + "/" + localTempImgDir;
            File dir = new File(out_file_path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String capturePath = out_file_path + System.currentTimeMillis() + ".jpg";
            cameraImage.put(currentPosition, capturePath);
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(capturePath)));
            getImageByCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(getImageByCamera, REQUEST_CODE_CAPTURE_CAMEIA);
        } else {
            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
            if(resultCode == Activity.RESULT_CANCELED){
             return;
            }
            setCaremaImage();
        }else if(requestCode == SELECT_PICTURE){
            if(data == null){
                return;
            }
            //选择图片
            Uri uri = data.getData();
            cameraImage.put(currentPosition, ImageUtil.getImageAbsolutePath(this,uri));
            setCaremaImage();
        }
    }

    /**
     * 拍照完显示照片
     */
    private void setCaremaImage() {
        switch (currentPosition) {
            case 0:
                mImageLoader.displayImage("file://" + cameraImage.get(currentPosition), headImage, XCApplication.options);
                break;
            case 1:
                mImageLoader.displayImage("file://" + cameraImage.get(currentPosition), positive, XCApplication.options);
                break;
            case 2:
                mImageLoader.displayImage("file://" + cameraImage.get(currentPosition), opposite, XCApplication.options);
                break;
        }
    }

    private class MyThread extends AsyncTask<ApplyDispatchDao, String, IsFlagData> {
        private DispatchMember dispatchMember;

        public MyThread(DispatchMember dispatchMember) {
            this.dispatchMember = dispatchMember;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            myProgressDialog = new MyProgressDialog(ApplyDispatcherActivity.this);
            myProgressDialog.showDialog(R.string.please_wait);
        }

        @Override
        protected void onPostExecute(IsFlagData isFlagData) {
            super.onPostExecute(isFlagData);
            myProgressDialog.dismiss();
            if(isFlagData != null){
                if(isFlagData.isSuccess()){
                    alert(R.string.apply_success);
                  Intent intent = new Intent(mContext,XCMainActivity.class);
                  startActivity(intent);
                }else{
                    alert(R.string.netWork_warning);
                }
            }else{
                alert(R.string.net_error);
            }
        }

        @Override
        protected IsFlagData doInBackground(ApplyDispatchDao... params) {
            HashMap<Integer,String> dataMap = ImageUtil.compressImage(getApplicationContext(),cameraImage);
            for(Integer position:dataMap.keySet()){
                String path = dataMap.get(position);
                File file = new File(path);
                String type = path.substring(path.lastIndexOf(".")+1,path.length());
                ImageUrlData imageUrl = params[0].submitImage(file, type);
                if(imageUrl == null){
                    continue;
                }
                if(position == 0){
                    dispatchMember.setImgFile(imageUrl.getData());
                }else if(position == 1){
                    dispatchMember.setCardImg(imageUrl.getData());
                }else if(position == 2){
                    dispatchMember.setCardImg2(imageUrl.getData());
                }
            }
            return params[0].applyDispatch(member.getMember().getId(),dispatchMember);
        }
    }

    @Override
    public void onBackPressed() {
        if (alertView.isShowing()) {
            alertView.dismiss();
        } else {
            super.onBackPressed();
        }

    }
}
