package com.banksoft.XinChengShop.ui.mySelf;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.MemberInfoDao;
import com.banksoft.XinChengShop.entity.Member;
import com.banksoft.XinChengShop.entity.MemberAddressVO;
import com.banksoft.XinChengShop.entity.MemberInfo;
import com.banksoft.XinChengShop.model.ImageUrlData;
import com.banksoft.XinChengShop.model.MemberData;
import com.banksoft.XinChengShop.model.MemberVOData;
import com.banksoft.XinChengShop.ui.AddressSelectActivity;
import com.banksoft.XinChengShop.ui.AreaListActivity;
import com.banksoft.XinChengShop.ui.ShopListActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.fragment.DateTimeSelectFragment;
import com.banksoft.XinChengShop.ui.fragment.SexSelectFragment;
import com.banksoft.XinChengShop.ui.fragment.TimeSelectFragment;
import com.banksoft.XinChengShop.utils.*;
import com.banksoft.XinChengShop.widget.MyProgressDialog;
import com.banksoft.XinChengShop.widget.imageView.CustomShapeImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sina.weibo.sdk.register.mobile.SelectCountryActivity;

import java.io.File;
import java.util.HashMap;

/**
 * Created by harry_robin on 16/1/22.
 */
public class MemberInfoActivity extends XCBaseActivity implements View.OnClickListener,SexSelectFragment.SelectSexListener{
    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 0;

    private static final int SELECT_PICTURE = 1;

    private int OPERATION_SELECT_AREA = 2;
    private int currentPosition;//点击的 第几个图片
    private EditText account, telPhone_contact;
    private ProgressBar progressBar;
    private TextView title, sex,area;
    private ImageView back;
    private ImageLoader mImageLoader;
    private CustomShapeImageView headImage;
    private PopupWindowUtil popupWindowUtil;
    private PickPhotoUtil pickPhotoUtil;
    private String localTempImgDir = "xinchengShop/";
    private MemberInfoDao memberInfoDao;
    private MyProgressDialog myProgressDialog;

    private LinearLayout areaLayout,sexLayout,addressLayout;

    private HashMap<Integer, String> cameraImage = new HashMap<>();

    private String sexStr = "";
    private Button ok;



    @Override
    protected void initContentView() {
        setContentView(R.layout.member_info_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
        account = (EditText) findViewById(R.id.account);
        telPhone_contact = (EditText) findViewById(R.id.telPhone_contact);
        sex = (TextView) findViewById(R.id.sex);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        headImage = (CustomShapeImageView) findViewById(R.id.head_image);
        areaLayout = (LinearLayout) findViewById(R.id.area_layout);
        sexLayout = (LinearLayout) findViewById(R.id.sex_layout);
        addressLayout = (LinearLayout) findViewById(R.id.address_layout);
        area = (TextView) findViewById(R.id.area);
        ok = (Button) findViewById(R.id.ok);
    }

    @Override
    protected void initData() {
        title.setText(R.string.member_info);
        back.setVisibility(View.VISIBLE);
        mImageLoader = ImageLoader.getInstance().getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        pickPhotoUtil = PickPhotoUtil.getInstance();
        isLogin();
        setInfo();
    }

    @Override
    protected void initOperate() {
        back.setOnClickListener(this);
        headImage.setOnClickListener(this);
        sexLayout.setOnClickListener(this);
        areaLayout.setOnClickListener(this);
        addressLayout.setOnClickListener(this);
        ok.setOnClickListener(this);
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
        }else if(requestCode == OPERATION_SELECT_AREA){// 选择的地理位置
            if (resultCode == RESULT_OK) {
                String areaName = data.getStringExtra(IntentFlag.AREA_NAME);
                area.setText(areaName);
            }
        }
    }

    /**
     * 拍照完显示照片
     */
    private void setCaremaImage() {
        mImageLoader.displayImage("file://" + cameraImage.get(currentPosition), headImage, XCApplication.options);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancellation:
                clearLogin();
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.title_back_button:
                finish();
                break;
            case R.id.head_image:
                showTakePhotoToolView();
                break;
            case R.id.take_photo:
                String state = Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    getImageFromCamera();
                } else {
                    Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
                }
                popupWindowUtil.dismiss();
                break;
            case R.id.from_photo_select:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "选择图片"), SELECT_PICTURE);
                popupWindowUtil.dismiss();
                break;
            case R.id.cancel:
                popupWindowUtil.dismiss();
                break;
            case R.id.head_image_layout:
                showTakePhotoToolView();
                break;
            case R.id.area_layout:
                Intent areaIntent = new Intent(mContext, AreaListActivity.class);
                startActivityForResult(areaIntent,OPERATION_SELECT_AREA);
                break;
            case R.id.sex_layout:
                SexSelectFragment sexSelectFragment = new SexSelectFragment();
                sexSelectFragment.show(getSupportFragmentManager(),"SexSelectFragment");
                break;
            case R.id.account:
                alert(R.string.account_no_updata);
                break;
            case R.id.address_layout:
                Intent addressIntent = new Intent(mContext,AddressSelectActivity.class);
                startActivity(addressIntent);
                break;
            case R.id.ok:
                MemberInfo info = getMemberInfo();
                if(info != null){
                  if(memberInfoDao == null){
                      memberInfoDao = new MemberInfoDao(mContext);
                  }
                  new MyTask(info).execute(memberInfoDao);
                }
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

    private void showTakePhotoToolView(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.take_photo_tool_layout,null);
        TextView one = (TextView) view.findViewById(R.id.take_photo);
        TextView two = (TextView) view.findViewById(R.id.from_photo_select);
        TextView three = (TextView) view.findViewById(R.id.cancel);
        popupWindowUtil = new PopupWindowUtil(MemberInfoActivity.this,view,findViewById(R.id.head_image));
        popupWindowUtil.backgroundAlpha(0.5f);
        popupWindowUtil.showPopupWindow();
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
    }

    private void setInfo(){
        account.setText(getStr(member.getMemberInfo().getAccount()));
        telPhone_contact.setText(getStr(member.getMemberInfo().getTelephone()));
        if("WOMAN".equals(member.getMember().getSex())){
            sexStr = "女";
        }else{
            sexStr = "男";
        }
        sex.setText(sexStr);
        String headImageUrl = member.getMember().getImageFile();
        if(headImageUrl == null || headImageUrl.isEmpty()){
           headImageUrl = "";
        }else{
           headImageUrl = headImageUrl.split("\\|")[0];
        }
        mImageLoader.displayImage(ControlUrl.BASE_URL+headImageUrl,headImage, XCApplication.options);
    }

    private String getStr(String str){
      if(str == null){
          return "";
      }else if(str.equals("null")){
        return "";
      }
        return str;
    }

    private MemberInfo getMemberInfo(){
        String accountStr = account.getText().toString();
        String telPhoneStr = telPhone_contact.getText().toString();

        if(accountStr.isEmpty()){
            alert(R.string.account_not_empty);
            return null;

        }else if(telPhoneStr.isEmpty()){
            alert(R.string.telephone_no_empty);
            return null;
        }else if(sexStr == null || sexStr.isEmpty()){
            alert(R.string.sex_is_empty);
            return null;
        }
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setId(member.getMember().getId());
        memberInfo.setAccount(accountStr);
        memberInfo.setTelephone(telPhoneStr);


        memberInfo.setSex(sexStr);
        return memberInfo;
    }



    @Override
    public void OnSelectSex(int position) {
        switch (position){
            case 0://男
                this.sexStr = "MAN";
                sex.setText("男");
                break;
            case 1://女
                this.sexStr = "WOMAN";
                sex.setText("女");
                break;
        }
    }

    private class MyTask extends AsyncTask<MemberInfoDao,String,MemberVOData>{
        private MemberInfo memberInfo;

        public MyTask(MemberInfo memberInfo) {
            this.memberInfo = memberInfo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            myProgressDialog = new MyProgressDialog(MemberInfoActivity.this);
            myProgressDialog.showDialog(R.string.please_wait);
        }

        @Override
        protected MemberVOData doInBackground(MemberInfoDao... params) {
            HashMap<Integer,String> dataMap = ImageUtil.compressImage(getApplicationContext(),cameraImage);
            for(Integer position:dataMap.keySet()){
                String path = dataMap.get(position);
                File file = new File(path);
                String type = path.substring(path.lastIndexOf(".")+1,path.length());
                ImageUrlData imageUrl = params[0].submitImage(file, type);
                if(imageUrl == null){
                    continue;
                }
                memberInfo.setImageFile(imageUrl.getData());
            }
            return params[0].updataMemberInfo(memberInfo);
        }

        @Override
        protected void onPostExecute(MemberVOData memberData) {
            super.onPostExecute(memberData);
            myProgressDialog.dismiss();
            if(memberData != null){
                if(memberData.isSuccess()){
                    MemberData data = new MemberData();
                    Member member1 = new Member();
                    member1.setMember(memberData.getData());
                    member1.setAccountInfo(member.getAccountInfo());
                    member1.setMemberInfo(member.getMemberInfo());
                    member1.setShop(member.getShop());
                    data.setData(member1);
                    data.setSuccess(true);

                     alert(R.string.update_success);
                    saveLogin(data.getData());
                     setResult(Activity.RESULT_OK);
                    finish();
                }else{
                    alert(memberData.getMsg().toString());
                }
            }else{
                alert(R.string.net_error);
            }
        }
    }





}
