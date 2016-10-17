package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.adapter.CommonAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.ApplyOpenShopDao;
import com.banksoft.XinChengShop.entity.*;
import com.banksoft.XinChengShop.model.ImageUrlData;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.model.ViewHolde;
import com.banksoft.XinChengShop.type.ShopType;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.fragment.home.HomeBaseFragment;
import com.banksoft.XinChengShop.utils.Check;
import com.banksoft.XinChengShop.utils.CommonUtil;
import com.banksoft.XinChengShop.utils.ImageUtil;
import com.banksoft.XinChengShop.widget.MyProgressDialog;
import com.banksoft.XinChengShop.widget.alertview.AlertView;
import com.banksoft.XinChengShop.widget.alertview.OnItemClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Robin on 2016/4/22.
 * 申请开店
 */
public class ApplyOpenShopActivity extends XCBaseActivity implements OnItemClickListener{
    private final String TAG = "ApplyOpenShopActivity";
    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 0;
    private static final int REQUEST_CODE_PICK_IMAGE = 1;
    private static final int SELECT_AREA_LIST = 2;//选择所在区域
    private static final int SELECT_SHOP_CATEGORY = 3;//选择店铺分类
    private Context context = null;
    private EditText shopname = null;
    private EditText identity = null;
    private EditText phone = null;
    private EditText adress = null;
    private EditText mainwork = null;
    private EditText shopinfo = null;
    private RadioGroup shop = null;
    private RadioGroup flagship = null;
    private Button selectshoptype = null;
    private Button selectshopcategroy = null;
    private Button selectadress = null;
    private Button selecthouse = null;
    private Button selectstore = null;
    private Button titleRight = null;
    private ImageView one = null;
    private LinearLayout selectshop = null;

    private AlertDialog diglog = null;
    private LinearLayout linearLayout = null;
    private ListView listView = null;
    private String arryshoptype[] = null;
    private ShopType[] shopTypes = new ShopType[]{ShopType.SHOP_SALE, ShopType.SHOP_SERVER, ShopType.SHOP_LOCAL};
    private String arryshopcategroy[] = null;
    private String arryhouse[] = null;
    private List<Integer> arrystore = new ArrayList<>();
    private AlertView mAlertView;
    private String localTempImgDir = "xinchengShop/";
    private HashMap<Integer, String> cameraImage = new HashMap<>();
    private ImageLoader mImageLoader;
    private ShopProductTypeBO currentShopProductTypeBo;
    private String currentCode;// 选择的地理位置区域

    private ShopType currentType;//店铺类型

    private String areaNo;//所在地区

    private String shopCategory; // 店铺分类

    private TextView title;
    private ImageView back;

    private ApplyOpenShopDao applyOpenShopDao;

    private MyProgressDialog myProgressDialog;

    private boolean isXincheng = true;//是否鑫诚内店铺
    private boolean isShip = true;// 是否旗舰店

    @Override
    protected void initContentView() {
        setContentView(R.layout.apply_open_layout);
        initView();
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
        context = getApplicationContext();
        one = (ImageView) findViewById(R.id.one);
        selectshoptype = (Button) findViewById(R.id.btn_selectshoptype);
        selectshopcategroy = (Button) findViewById(R.id.btn_selectshopcategroy);
        selectadress = (Button) findViewById(R.id.btn_selectadress);
        selecthouse = (Button) findViewById(R.id.btn_selecthouse);
        selectstore = (Button) findViewById(R.id.btn_selectstore);
        shopname = (EditText) findViewById(R.id.et_shopname);
        identity = (EditText) findViewById(R.id.et_identity);
        phone = (EditText) findViewById(R.id.et_phone);
        adress = (EditText) findViewById(R.id.et_adress);
        mainwork = (EditText) findViewById(R.id.et_mainwork);
        shopinfo = (EditText) findViewById(R.id.et_shopinfo);
        shop = (RadioGroup) findViewById(R.id.rg_shop);
        selectshop = (LinearLayout) findViewById(R.id.ll_selectshop);
        flagship = (RadioGroup) findViewById(R.id.rg_flagship);
        linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_list, null);
        listView = (ListView) linearLayout.findViewById(R.id.dialog_list);
        titleRight = (Button) findViewById(R.id.titleRightButton);
        diglog = new AlertDialog.Builder(ApplyOpenShopActivity.this).create();
        mAlertView = new AlertView(null, null, "取消", null, new String[]{"拍照", "从相册中选择"}, this, AlertView.Style.ActionSheet, this);
    }

    @Override
    protected void initData() {
        mImageLoader = ImageLoader.getInstance();
        arryshoptype = getResources().getStringArray(R.array.arryshoptype);
        arryhouse = getResources().getStringArray(R.array.arryhouse);
        title.setText(R.string.store_to_apply);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(onClickListener);
        for (int i = 101; i < 201; i++) {
            arrystore.add(i);
        }
    }

    @Override
    protected void initOperate() {
        selectshoptype.setOnClickListener(onClickListener);
        selectshopcategroy.setOnClickListener(onClickListener);
        selecthouse.setOnClickListener(onClickListener);
        selectstore.setOnClickListener(onClickListener);
        selectadress.setOnClickListener(onClickListener);

        titleRight.setVisibility(View.VISIBLE);
        titleRight.setText(R.string.ok);
        titleRight.setOnClickListener(onClickListener);
        one.setOnClickListener(onClickListener);
        shop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_shopyes) {//是
                    isXincheng = true;
                } else {//否
                    isXincheng = false;
                }
            }
        });
        /**
         * 是否
         */
        flagship.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_flagshipyes) {//是
                    isShip = true;
                } else {//否
                    isShip = false;
                }
            }
        });
        //选择框
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Button btn = (Button) listView.getTag();
                if (btn == selectshoptype) {
                    btn.setText(arryshoptype[i]);
                    currentType = shopTypes[i];
                } else if (btn == selectshopcategroy) {
                    btn.setText(arryshopcategroy[i]);
                } else if (btn == selecthouse) {
                    btn.setText(arryhouse[i]);
                } else if (btn == selectstore) {
                    btn.setText(String.valueOf(arrystore.get(i)));
                }
                diglog.dismiss();
            }
        });
    }


    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.title_back_button:
                    finish();
                    break;
                case R.id.btn_selectshoptype://选择店铺类型
                    listView.setTag(selectshoptype);
                    listView.setAdapter(new CommonAdapter<String>(context, Arrays.asList(arryshoptype), R.layout.dialog_onetext_adapter) {
                        @Override
                        public void convert(ViewHolde viewHolde, String item) {
                            viewHolde.setText(R.id.tv_str, item);
                        }
                    });

                    diglog.show();
                    diglog.setContentView(linearLayout);
                    break;
                case R.id.btn_selectshopcategroy://选择商品分类
                    if (currentType == null) {
                        alert(R.string.please_select_shop_type);
                        return;
                    }
                    Intent selectIntent = new Intent(mContext, SelectShopCategoryActivity.class);
                    selectIntent.putExtra(IntentFlag.SHOP_TYPE, currentType);
                    startActivityForResult(selectIntent, SELECT_SHOP_CATEGORY);
                    //查询
                    break;
                case R.id.btn_selectadress://选择地址
                    Intent intent = new Intent(mContext, AreaListActivity.class);
                    startActivityForResult(intent, SELECT_AREA_LIST);
                    break;
                case R.id.btn_selecthouse://选择区号
                    listView.setTag(selecthouse);
                    listView.setAdapter(new CommonAdapter<String>(context, Arrays.asList(arryhouse), R.layout.dialog_onetext_adapter) {
                        @Override
                        public void convert(ViewHolde viewHolde, String item) {
                            viewHolde.setText(R.id.tv_str, item);
                        }
                    });
                    diglog.show();
                    diglog.setContentView(linearLayout);
                    break;
                case R.id.btn_selectstore://选择门牌号
                    listView.setTag(selectstore);
                    listView.setAdapter(new CommonAdapter<Integer>(context, arrystore, R.layout.dialog_onetext_adapter) {
                        @Override
                        public void convert(ViewHolde viewHolde, Integer item) {
                            viewHolde.setText(R.id.tv_str, item + "");
                        }
                    });
                    diglog.show();
                    diglog.setContentView(linearLayout);
                    break;
                case R.id.one://选择商店照片
                    mAlertView.show();
                    break;
                case R.id.titleRightButton:// 确定
                    Shop shop = getSubmitShop();
                    if (shop != null) {
                        if (applyOpenShopDao == null) {
                            applyOpenShopDao = new ApplyOpenShopDao(mContext);
                        }
                        new MyTask(shop).execute(applyOpenShopDao);
                    }
                    break;
            }
        }
    };

    /**
     * 订单提交
     *
     * @return
     */
    private Shop getSubmitShop() {
        String shopNameStr = shopname.getText().toString();
        String identityStr = identity.getText().toString();
        String telPhoneStr = phone.getText().toString();
        String addressStr = adress.getText().toString();
        String mainWork = mainwork.getText().toString();
        String shopInfo = shopinfo.getText().toString();
        String houseStr = selecthouse.getText().toString();
        String storeStr = selectstore.getText().toString();
        if (shopNameStr.isEmpty()) {
            alert(R.string.shop_name_no_empty);
            return null;
        } else if (identityStr.isEmpty()) {
            alert(R.string.identity_card_no_empty);
            return null;
        } else if (telPhoneStr.isEmpty()) {
            alert(R.string.telephone_no_empty);
            return null;
        } else if (addressStr.isEmpty()) {
            alert(R.string.address_no_empty);
            return null;
        } else if (mainWork.isEmpty()) {
            alert(R.string.main_work_no_empty);
            return null;
        } else if (shopInfo.isEmpty()) {
            alert(R.string.shop_info_no_empty);
            return null;
        } else if (houseStr.isEmpty()) {
            alert(R.string.house_no_empty);
            return null;
        } else if (storeStr.isEmpty()) {
            alert(R.string.store_no_empty);
            return null;
        } else if (shopCategory.isEmpty()) {
            alert("请选择店铺分类");
            return null;
        } else if (currentType == null) {
            alert("请选择店铺类型");
            return null;
        } else if(currentCode == null||currentCode.isEmpty()){
            alert("请选择所在地区");
            return null;
        }else if(!Check.isCellphone(telPhoneStr)){
            alert(R.string.telphone_format_error);
            return null;
        }
        Shop shopaa = new Shop();
        shopaa.setName(shopNameStr);
        shopaa.setAddress(addressStr);
        shopaa.setIdCard(identityStr);
        shopaa.setXinchengPoint(houseStr + "|" + storeStr);
        shopaa.setTelephone(telPhoneStr);
        shopaa.setDescription(shopInfo);
        shopaa.setBusiness(mainWork);
        shopaa.setShopType(shopCategory);
        shopaa.setShopServerType(currentType.name());
        shopaa.setDistrict(currentCode);
        shopaa.setXincheng(isXincheng);
        shopaa.setShip(isShip);
        return shopaa;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            if (data == null) {
                return;
            }
            //选择图片
            Uri uri = data.getData();
            cameraImage.put(0, ImageUtil.getImageAbsolutePath(this, uri));
            setCaremaImage();

        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
            setCaremaImage();
        } else if (SELECT_AREA_LIST == requestCode) {// 选择所在区域
            if (data != null) {
                String name = data.getStringExtra(IntentFlag.AREA_NAME);
                String code = data.getStringExtra(IntentFlag.CODE);
                currentCode = code;
                selectadress.setText(name);
            }

        } else if (SELECT_SHOP_CATEGORY == requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                Object o = data.getSerializableExtra(IntentFlag.DATA);
                if (currentType.equals(ShopType.SHOP_SERVER)) {
                    ShopServerType shopServerType = (ShopServerType) o;
                    shopCategory = shopServerType.getNo();
                    selectshopcategroy.setText(shopServerType.getName());
                } else if (currentType.equals(ShopType.SHOP_SALE)) {
                    ShopTypeVO shopTypeVO = (ShopTypeVO) o;
                    shopCategory = shopTypeVO.getNo();
                    selectshopcategroy.setText(shopTypeVO.getName());
                } else if (currentType.equals(ShopType.SHOP_LOCAL)) {
                    ShopLocalType shopLocalType = (ShopLocalType) o;
                    shopCategory = shopLocalType.getNo();
                    selectshopcategroy.setText(shopLocalType.getName());
                }
            }
        }
    }

    public RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i) {
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
        mImageLoader.displayImage("file://" + cameraImage.get(0), one, XCApplication.options);
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
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "选择图片"), REQUEST_CODE_PICK_IMAGE);
                break;
            case 2:
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
            String capturePath = out_file_path + System.currentTimeMillis() + ".jpg";
            cameraImage.put(0, capturePath);
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(capturePath)));
            getImageByCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(getImageByCamera, REQUEST_CODE_CAPTURE_CAMEIA);
        } else {
            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }


    private class MyTask extends AsyncTask<ApplyOpenShopDao, String, IsFlagData> {
        private Shop shop;

        public MyTask(Shop shop) {
            this.shop = shop;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            myProgressDialog = new MyProgressDialog(ApplyOpenShopActivity.this);
            myProgressDialog.showDialog(R.string.please_wait);

        }

        @Override
        protected IsFlagData doInBackground(ApplyOpenShopDao... params) {
            HashMap<Integer,String> dataMap = ImageUtil.compressImage(getApplicationContext(),cameraImage);
            for(Integer position:dataMap.keySet()){
                String path = dataMap.get(position);
                File file = new File(path);
                String type = path.substring(path.lastIndexOf(".")+1,path.length());
                ImageUrlData imageUrl = params[0].submitImage(file, type);
                if(imageUrl == null){
                    continue;
                }
                shop.setImageFile(imageUrl.getData());
            }
            return params[0].applyOpenShop(member.getMember().getId(), shop);
        }

        @Override
        protected void onPostExecute(IsFlagData isFlagData) {
            super.onPostExecute(isFlagData);
            if (isFlagData != null) {
                if (isFlagData.isSuccess()) {
                    alert(R.string.apple_open_shop_success);
                    finish();
                } else {
                    alert(isFlagData.getMsg().toString());
                }
            } else {
                alert(R.string.net_error);
            }
        }
    }


}
