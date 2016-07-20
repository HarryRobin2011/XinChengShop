package com.banksoft.XinChengShop.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.adapter.CommonAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.entity.ExpressPriceVO;
import com.banksoft.XinChengShop.entity.Shop;
import com.banksoft.XinChengShop.entity.ShopProductTypeBO;
import com.banksoft.XinChengShop.entity.ShopServerType;
import com.banksoft.XinChengShop.model.ViewHolde;
import com.banksoft.XinChengShop.type.ShopType;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.widget.alertview.AlertView;
import com.banksoft.XinChengShop.widget.alertview.OnItemClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Robin on 2016/4/22.
 * 申请开店
 */
public class ApplyOpenShopActivity extends XCBaseActivity implements OnItemClickListener,View.OnClickListener{
    private final String TAG ="ApplyOpenShopActivity";
    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 0;
    private static final int REQUEST_CODE_PICK_IMAGE = 1;
    private static final int SELECT_AREA_LIST = 2;//选择所在区域
    private Context context =null;
    private EditText  shopname=null;
    private EditText  identity=null;
    private EditText  phone=null;
    private EditText  adress =null;
    private EditText  mainwork=null;
    private EditText  shopinfo=null;
    private RadioGroup shop=null;
    private RadioGroup flagship=null;
    private Button selectshoptype =null;
    private Button selectshopcategroy =null;
    private Button selectadress =null;
    private Button selecthouse =null;
    private Button selectstore =null;
    private Button titleRight=null;
    private ImageView one =null;
    private LinearLayout selectshop =null;

    private AlertDialog diglog =null;
    private LinearLayout linearLayout =null;
    private ListView listView =null;
    private String arryshoptype[] =null;
    private String arryshopcategroy[] =null;
    private String arryhouse[] =null;
    private List<Integer> arrystore =new ArrayList<>();
    private AlertView mAlertView;
    private String localTempImgDir = "xinchengShop/";
    private String[] imageNames = new String[]{"one"};
    private HashMap<Integer, String> cameraImage = new HashMap<>();
    private int currentPosition;
    private ImageLoader mImageLoader;
    private ShopProductTypeBO currentShopProductTypeBo;
    private String currentCode;// 选择的地理位置区域

    private ShopType currentType;//店铺类型

    private String areaNo;//所在地区

    private String shopType; // 店铺分类

    private TextView title;
    private ImageView back;

    @Override
    protected void initContentView() {
        setContentView(R.layout.apply_open_layout);
        initView();

    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
        context =getApplicationContext();
        one =(ImageView)findViewById(R.id.one);
        selectshoptype =(Button)findViewById(R.id.btn_selectshoptype);
        selectshopcategroy =(Button)findViewById(R.id.btn_selectshopcategroy);
        selectadress =(Button)findViewById(R.id.btn_selectadress);
        selecthouse =(Button)findViewById(R.id.btn_selecthouse);
        selectstore =(Button)findViewById(R.id.btn_selectstore);
        shopname =(EditText)findViewById(R.id.et_shopname);
        identity =(EditText)findViewById(R.id.et_identity);
        phone =(EditText)findViewById(R.id.et_phone);
        adress =(EditText)findViewById(R.id.et_adress);
        mainwork =(EditText)findViewById(R.id.et_mainwork);
        shopinfo =(EditText)findViewById(R.id.et_shopinfo);
        shop =(RadioGroup)findViewById(R.id.rg_shop);
        selectshop =(LinearLayout)findViewById(R.id.ll_selectshop);
        flagship =(RadioGroup)findViewById(R.id.rg_flagship);
        linearLayout =(LinearLayout)getLayoutInflater().inflate(R.layout.dialog_list,null);
        listView =(ListView)linearLayout.findViewById(R.id.dialog_list);
        titleRight = (Button) findViewById(R.id.titleRightButton);
        diglog =new AlertDialog.Builder(ApplyOpenShopActivity.this).create();
        mAlertView = new AlertView(null, null, "取消", null, new String[]{"拍照", "从相册中选择"}, this, AlertView.Style.ActionSheet, this);
    }

    @Override
    protected void initData() {
        arryshoptype =getResources().getStringArray(R.array.arryshoptype);
        arryhouse =getResources().getStringArray(R.array.arryhouse);
         title.setText(R.string.store_to_apply);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        for(int i=101;i<201;i++){
            arrystore.add(i);
        }
    }

    @Override
    protected void initOperate() {
        selectshoptype.setOnClickListener(onClickListener);
        selectshopcategroy.setOnClickListener(onClickListener);
        selecthouse.setOnClickListener(onClickListener);
        selectstore.setOnClickListener(onClickListener);
        titleRight.setVisibility(View.VISIBLE);
        titleRight.setText(R.string.ok);
        titleRight.setOnClickListener(this);
        //选择框
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Button btn =(Button)listView.getTag();
                if(btn == selectshoptype){
                    btn.setText(arryshoptype[i]);
                }else if(btn == selectshopcategroy){
                    btn.setText(arryshopcategroy[i]);
                }else  if(btn == selecthouse){
                    btn.setText(arryhouse[i]);
                }else if(btn == selectstore){
                    btn.setText(String.valueOf(arrystore.get(i)));
                }
                diglog.dismiss();
            }
        });
    }
    public View.OnClickListener  onClickListener =new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_selectshoptype://选择店铺类型
                    listView.setTag(selectshoptype);
                    listView.setAdapter(new CommonAdapter<String>(context, Arrays.asList(arryshoptype),R.layout.dialog_onetext_adapter) {
                        @Override
                        public void convert(ViewHolde viewHolde, String item) {
                            viewHolde.setText(R.id.tv_str,item);
                            if(item.equals(ShopType.SHOP_LOCAL.getName())){
                               currentType = ShopType.SHOP_LOCAL;
                            }else if(item.equals(ShopType.SHOP_SERVER.getName())){
                                currentType = ShopType.SHOP_SERVER;
                            }else if(item.equals(ShopType.SHOP_SALE.getName())){
                                currentType = ShopType.SHOP_SALE;
                            }
                        }
                    });
                    break;
                case R.id.btn_selectshopcategroy://选择商品分类
                    Intent intent = new Intent(mContext,SelectShopCategoryActivity.class);
                    startActivity(intent);
                    //查询
                    break;
                case R.id.btn_selectadress://选择地址
                    Intent intent = new Intent(mContext, AreaListActivity.class);
                    startActivityForResult(intent, SELECT_AREA_LIST);
                    break;
                case R.id.btn_selecthouse://选择区号
                    listView.setTag(selecthouse);
                    listView.setAdapter(new CommonAdapter<String>(context, Arrays.asList(arryhouse),R.layout.dialog_onetext_adapter) {
                        @Override
                        public void convert(ViewHolde viewHolde, String item) {
                            viewHolde.setText(R.id.tv_str,item);
                        }
                    });
                    break;
                case R.id.btn_selectstore://选择门牌号
                    listView.setTag(selectstore);
                    listView.setAdapter(new CommonAdapter<Integer>(context, arrystore,R.layout.dialog_onetext_adapter) {
                        @Override
                        public void convert(ViewHolde viewHolde, Integer item) {
                            viewHolde.setText(R.id.tv_str,item+"");
                        }
                    });
                    break;
                case R.id.one://选择商店照片
                    mAlertView.show();
                    break;
                case R.id.titleRightButton:// 确定
                   if(){

                   }
                    break;
            }
            diglog.show();
            diglog.setContentView(linearLayout);
        }
    };

    /**
     * 订单提交
     * @return
     */
    private Shop getSubmitShop(){
        String shopNameStr = shopname.getText().toString();
        String identityStr = identity.getText().toString();
        String telPhoneStr = phone.getText().toString();
        String addressStr = adress.getText().toString();
        String mainWork = mainwork.getText().toString();
        String shopInfo = shopinfo.getText().toString();
        String houseStr = selecthouse.getText().toString();
        String storeStr = selectstore.getText().toString();
        if(shopNameStr.isEmpty()){
            alert(R.string.shop_name_no_empty);
            return null;
        }else if(identityStr.isEmpty()){
            alert(R.string.identity_card_no_empty);
            return null;
        }else if(telPhoneStr.isEmpty()){
             alert(R.string.telephone_no_empty);
            return null;
        }else if(addressStr.isEmpty()){
             alert(R.string.address_no_empty);
            return null;
        }else if(mainWork.isEmpty()){
             alert(R.string.main_work_no_empty);
            return null;
        }else if(shopInfo.isEmpty()){
             alert(R.string.shop_info_no_empty);
            return null;
        }else if(houseStr.isEmpty()){
             alert(R.string.house_no_empty);
            return null;
        }else if(storeStr.isEmpty()){
             alert(R.string.store_no_empty);
            return null;
        }
        Shop shopaa = new Shop();
        shopaa.setName(shopNameStr);
        shopaa.setAddress(addressStr);
        shopaa.setIdCard(identityStr);
        shopaa.setXinchengPoint(houseStr+"|"+storeStr);
        shopaa.setTelephone(telPhoneStr);
        shopaa.setDescription(shopInfo);
        shopaa.setBusiness(mainWork);
        shop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_shopyes){//是
                    shopaa.setXincheng(true);
                }else{//否
                    shopaa.setXincheng(false);
                }
            }
        });
        /**
         * 是否
         */
        flagship.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_flagshipyes){//是
                    shopaa.setShip(true);
                }else{//否
                    shopaa.setShip(false);
                }
            }
        });
      return shopaa;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            Uri uri = data.getData();
            //to do find the path of pic

        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
            cameraImage.put(currentPosition, Environment.getExternalStorageDirectory()
                    + "/" + localTempImgDir + "/" + imageNames[currentPosition] + ".jpg");
            setCaremaImage();
        } else if (SELECT_AREA_LIST == requestCode) {// 选择所在区域
            if (data != null) {
                String name = data.getStringExtra(IntentFlag.AREA_NAME);
                String code = data.getStringExtra(IntentFlag.CODE);
                currentCode = code;
                selectadress.setText(name);
            }

        }
    }
    public RadioGroup.OnCheckedChangeListener onCheckedChangeListener =new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i){
                case R.id.rb_shopyes://广场商店
                    selectshop.setVisibility(View.VISIBLE);
                    break;
                case R.id.rb_shopno:
                    selectshop.setVisibility(View.GONE);
                    break;
                case R.id.rb_flagshipyes://旗舰店
                    break;
                case R.id.rb_flagshipno:
                    break;
                default:
                    radioGroup.check(i);
                    break;
            }
        }
    };
    /**
     * 拍照完显示照片
     */
    private void setCaremaImage() {
        switch (currentPosition) {
            case 0:
                mImageLoader.displayImage("file://" + cameraImage.get(currentPosition), one, XCApplication.options);
                break;
        }
    }
    @Override
    public void onItemClick(Object o, int position) {
        switch (position) {
            case 0:
                mAlertView.dismiss();
                String state = Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    getImageFromCamera();
                } else {
                    Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
                }
                break;
            case 1:
                mAlertView.dismiss();
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
            String capturePath = out_file_path + imageNames[currentPosition] + ".jpg";
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(capturePath)));
            getImageByCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(getImageByCamera, REQUEST_CODE_CAPTURE_CAMEIA);
        } else {
            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back_button:
                finish();
                break;
        }
    }
}
