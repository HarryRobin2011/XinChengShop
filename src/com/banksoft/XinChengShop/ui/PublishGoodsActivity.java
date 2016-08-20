package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.PublishGoodsDao;
import com.banksoft.XinChengShop.entity.ExpressModelBO;
import com.banksoft.XinChengShop.entity.ExpressPriceVO;
import com.banksoft.XinChengShop.entity.ProductBO;
import com.banksoft.XinChengShop.entity.ProductTypeVO;
import com.banksoft.XinChengShop.model.ImageUrlData;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.fragment.FreightSelectMouldFragment;
import com.banksoft.XinChengShop.ui.fragment.GoodsTypeSelectFragment;
import com.banksoft.XinChengShop.utils.ImageUtil;
import com.banksoft.XinChengShop.widget.MyProgressDialog;
import com.banksoft.XinChengShop.widget.alertview.AlertView;
import com.banksoft.XinChengShop.widget.alertview.OnItemClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Robin on 2016/4/10.
 */
public class PublishGoodsActivity extends XCBaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, OnItemClickListener
        , GoodsTypeSelectFragment.GoodsTypeSelectListener, FreightSelectMouldFragment.FreightSelectListener {
    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 0;
    private static final int REQUEST_CODE_PICK_IMAGE = 1;//相册选择
    private static final int SELECT_AREA_LIST = 2;//选择所在区域
    private static final int SELECT_PRODUCT_CATEGORY = 3;//请选择商品分类


    private ImageView imageOne, imageTwo, imageThree, imageFour, back;
    private TextView title;
    private Button goodCategory, freightMould, selectSite, goodType, publish;
    private RadioGroup shopFreight, specialtyGoodsRadiogroup, recommendGoodsRadiogroup, inventoryGoodsRadiogroup;
    private boolean shopFreightFlag, specialtyGoodsRadiogroupFlag, recommendGoodsRadiogroupFlag, inventoryGoodsRadiogroupFlag;
    private EditText goodName, goodPrice, goodMarkPrice, goodStock;
    private AlertView mAlertView;
    private HashMap<Integer, String> cameraImage = new HashMap<>();
    private int currentPosition;

    private String localTempImgDir = "xinchengShop/";

    private ImageLoader mImageLoader;

    private ProductTypeVO currentShopProductTypeBo;
    private String currentCode;// 选择的地理位置区域
    private ExpressModelBO currentExpressPriceBO;

    private String goodsType;

    private PublishGoodsDao publishGoodsDao;

    private MyProgressDialog mProgressDialog;

    private Button rightBtn;

    private boolean isFreight;//
    private boolean isSpecialty;//
    private boolean isRecommend;//
    private String isinventor;//


    @Override
    protected void initContentView() {
        setContentView(R.layout.publish_goods_layout);
    }

    @Override
    protected void initOperate() {
        imageOne.setOnClickListener(this);
        imageTwo.setOnClickListener(this);
        imageThree.setOnClickListener(this);
        imageFour.setOnClickListener(this);
        back.setOnClickListener(this);
        goodCategory.setOnClickListener(this);
        freightMould.setOnClickListener(this);
        selectSite.setOnClickListener(this);
        goodType.setOnClickListener(this);
        publish.setOnClickListener(this);

        rightBtn.setOnClickListener(this);

        shopFreight.setOnCheckedChangeListener(this);
        rightBtn.setVisibility(View.VISIBLE);
        rightBtn.setText(R.string.send_supply);

        specialtyGoodsRadiogroup.setOnCheckedChangeListener(this);
        recommendGoodsRadiogroup.setOnCheckedChangeListener(this);
        inventoryGoodsRadiogroup.setOnCheckedChangeListener(this);

    }

    @Override
    protected void initView() {
        imageOne = (ImageView) findViewById(R.id.one);
        imageTwo = (ImageView) findViewById(R.id.two);
        imageThree = (ImageView) findViewById(R.id.three);
        imageFour = (ImageView) findViewById(R.id.four);

        back = (ImageView) findViewById(R.id.title_back_button);
        title = (TextView) findViewById(R.id.titleText);
        goodCategory = (Button) findViewById(R.id.good_category);
        freightMould = (Button) findViewById(R.id.select_freight_mould);
        selectSite = (Button) findViewById(R.id.select_site);
        goodType = (Button) findViewById(R.id.good_type);
        rightBtn = (Button) findViewById(R.id.titleRightButton);

        shopFreight = (RadioGroup) findViewById(R.id.good_freight_group);
        specialtyGoodsRadiogroup = (RadioGroup) findViewById(R.id.specialty_goods_radiogroup);
        recommendGoodsRadiogroup = (RadioGroup) findViewById(R.id.recommend_goods_radiogroup);
        inventoryGoodsRadiogroup = (RadioGroup) findViewById(R.id.Inventory_goods_radiogroup);

        goodName = (EditText) findViewById(R.id.good_name);
        goodPrice = (EditText) findViewById(R.id.good_price);
        goodMarkPrice = (EditText) findViewById(R.id.good_mark_price);
        goodStock = (EditText) findViewById(R.id.goods_stock);
        publish = (Button) findViewById(R.id.titleRightButton);
    }

    @Override
    protected void initData() {
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        selectSite.setOnClickListener(this);
        title.setText(R.string.goods_publish);
        mAlertView = new AlertView(null, null, "取消", null, new String[]{"拍照", "从相册中选择"}, this, AlertView.Style.ActionSheet, this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            if(data == null){
                return;
            }

            Uri uri = data.getData();
            String url = ImageUtil.getImageAbsolutePath(this,uri);
            cameraImage.put(currentPosition,url);
            setCaremaImage();
            //to do find the path of pic

        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
            setCaremaImage();
        } else if (SELECT_AREA_LIST == requestCode) {// 选择所在区域
            if (data != null) {
                String name = data.getStringExtra(IntentFlag.AREA_NAME);
                String code = data.getStringExtra(IntentFlag.CODE);
                currentCode = code;
                selectSite.setText(name);
            }

        }else if(SELECT_PRODUCT_CATEGORY == requestCode){// 选择商品分类
            Object o = null;
            if(data == null){
                return;
            }
            o = data.getSerializableExtra(IntentFlag.DATA);
            this.currentShopProductTypeBo = (ProductTypeVO) o;
            goodCategory.setText(currentShopProductTypeBo.getName());
        }
    }

    /**
     * 拍照完显示照片
     */
    private void setCaremaImage() {
        switch (currentPosition) {
            case 0:
                mImageLoader.displayImage("file://" + cameraImage.get(currentPosition), imageOne, XCApplication.options);
                break;
            case 1:
                mImageLoader.displayImage("file://" + cameraImage.get(currentPosition), imageTwo, XCApplication.options);
                break;
            case 2:
                mImageLoader.displayImage("file://" + cameraImage.get(currentPosition), imageThree, XCApplication.options);
                break;
            case 3:
                mImageLoader.displayImage("file://" + cameraImage.get(currentPosition), imageFour, XCApplication.options);
                break;
        }
    }

    ;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.one:
                currentPosition = 0;
                mAlertView.show();
                break;
            case R.id.two:
                currentPosition = 1;
                mAlertView.show();
                break;
            case R.id.three:
                currentPosition = 2;
                mAlertView.show();
                break;
            case R.id.four:
                currentPosition = 3;
                mAlertView.show();
                break;
            case R.id.good_category:
                 Intent goodCategoryIntent = new Intent(mContext,SelectGoodsCategoryActivity.class);
                 startActivityForResult(goodCategoryIntent,SELECT_PRODUCT_CATEGORY);
                break;
            case R.id.select_site:// 选择所在区域
                Intent intent = new Intent(mContext, AreaListActivity.class);
                startActivityForResult(intent, SELECT_AREA_LIST);
                break;
            case R.id.select_freight_mould:// 选择云费模板
                FreightSelectMouldFragment freightSelectMouldFragment = new FreightSelectMouldFragment();
                Bundle freightBundle = new Bundle();
                freightBundle.putString(IntentFlag.SHOP_ID,member.getShop().getId());
                freightSelectMouldFragment.setArguments(freightBundle);
                freightSelectMouldFragment.show(getSupportFragmentManager(), "freightSelectMouldFragment");
                break;
            case R.id.good_type://选择产品类型
                GoodsTypeSelectFragment goodsTypeSelectFragment = new GoodsTypeSelectFragment();
                goodsTypeSelectFragment.show(getSupportFragmentManager(), "");
                break;
            case R.id.titleRightButton://发布
               ProductBO productBO = getProductBO();
                if(productBO != null){
                    if(publishGoodsDao == null){
                        publishGoodsDao = new PublishGoodsDao(mContext);
                    }
                    new MyTask(productBO).execute(publishGoodsDao);
                }
                break;

        }
    }

    /**
     * 获取发布商品对象
     *
     * @return
     */
    private ProductBO getProductBO() {
        ProductBO productBO = new ProductBO();
        String name = goodName.getText().toString().trim();
        String price = goodPrice.getText().toString().trim();
        String markPrice = goodMarkPrice.getText().toString().trim();
        String stock = goodStock.getText().toString().trim();

        if(name.isEmpty()){//商品名称
            alert(R.string.product_name_empty);
            return null;
        }else if(price.isEmpty()){
            alert(R.string.product_price_no_zero);
            return null;
        }else if(markPrice.isEmpty()){
            alert(R.string.product_mark_price_empty);
            return null;
        }else if(stock.isEmpty()){
            alert(R.string.product_stock_no_empty);
            return null;
        }
//        else if(currentExpressPriceBO == null){
//            alert(R.string.please_select_express_model);
//            return null;
//        }

        productBO.setName(name);
        productBO.setPrice(Float.valueOf(price));
        productBO.setSalePrice(markPrice);
        productBO.setStockNum(Integer.valueOf(stock));

        productBO.setFreight(isFreight);
        productBO.setInventoryChange(isinventor);
        productBO.setSpecial(isSpecialty);
        productBO.setRecommend(isRecommend);

        productBO.setTypeNo(currentShopProductTypeBo.getNo());
        productBO.setAreaNo(currentCode);
        productBO.setShopId(member.getShop().getId());
        if(currentExpressPriceBO != null){
            productBO.setExpressModelId(currentExpressPriceBO.getId());
        }

        productBO.setActive(goodsType);
        return productBO;
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()) {
            case R.id.good_freight_group://运费
                if (group.getCheckedRadioButtonId() == R.id.shop_freight) {
                    isFreight = true;
                } else if (group.getCheckedRadioButtonId() == R.id.member_freight) {
                    isFreight = false;
                }
                break;
            case R.id.recommend_goods_radiogroup://商品推荐
                if (group.getCheckedRadioButtonId() == R.id.recommend_yes) {
                    isRecommend = true;
                } else if (group.getCheckedRadioButtonId() == R.id.recommend_no) {
                    isRecommend = false;
                }
                break;
            case R.id.specialty_goods_radiogroup://特色产品
                if (group.getCheckedRadioButtonId() == R.id.specialty_yes) {//
                    isSpecialty = true;
                } else if (group.getCheckedRadioButtonId() == R.id.specialty_no) {
                    isSpecialty = false;
                }
                break;
            case R.id.Inventory_goods_radiogroup://库存变更膜ORDER_START  生成订单后     PAY_START  买家付款后
                if (group.getCheckedRadioButtonId() == R.id.Inventory_yes) {
                    isinventor = "ORDER_START";
                } else if (group.getCheckedRadioButtonId() == R.id.Inventory_no) {
                    isinventor = "PAY_START";
                }
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
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "选择图片"), REQUEST_CODE_PICK_IMAGE);
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
            cameraImage.put(currentPosition,capturePath);
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(capturePath)));
            getImageByCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(getImageByCamera, REQUEST_CODE_CAPTURE_CAMEIA);
        } else {
            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 选择产品类型
     *
     * @param index
     * 0 普通商品
     * 1 清仓商品
     */
    @Override
    public void select(int index) {
        goodsType = "" + index;
        switch (index){
            case 0:
                goodType.setText("普通商品");
                break;
            case 1:
                goodType.setText("清仓商品");
                break;
        }
}


    /**
     * 选择物流模板
     * @param
     */
    @Override
    public void select(ExpressModelBO expressModelBO) {
        this.currentExpressPriceBO = expressModelBO;
        freightMould.setText(currentExpressPriceBO.getName());
    }


/**
 * 商品发布
 */
private class MyTask extends AsyncTask<PublishGoodsDao, String, IsFlagData> {
    ProductBO productBO = null;
    public MyTask(ProductBO productBO){
        this.productBO = productBO;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new MyProgressDialog(PublishGoodsActivity.this);
        mProgressDialog.showDialog("请稍后...");
    }

    @Override
    protected IsFlagData doInBackground(PublishGoodsDao... params) {

        HashMap<Integer, String> dataMap = ImageUtil.compressImage(mContext, cameraImage);
        if (dataMap == null) {
            return null;
        }
        for (int position = 0; position < dataMap.keySet().size(); position++) {
            String imagePath = dataMap.get(dataMap.keySet().toArray()[position]);
            String imageType = imagePath.substring(imagePath.lastIndexOf(".") + 1, imagePath.length());
            File file = new File(imagePath);
            ImageUrlData imageUrlData = params[0].submitImage(file, imageType);
            if (imageUrlData != null) {
                dataMap.put((Integer) dataMap.keySet().toArray()[position], imageUrlData.getData());
            } else {
                return null;
            }
        }
        StringBuffer imageBuffer = new StringBuffer();
        for (Integer index : dataMap.keySet()) {
           imageBuffer.append(dataMap.get(index)).append("|");
        }
        productBO.setIcon(imageBuffer.toString());
        return params[0].submitGoods(productBO);
    }

    @Override
    protected void onPostExecute(IsFlagData baseData) {
        super.onPostExecute(baseData);
        mProgressDialog.dismiss();
        if (baseData != null) {
            if (baseData.isSuccess()) {
                alert(R.string.add_success);
                setResult(Activity.RESULT_OK);
                finish();
            } else {
                alert(baseData.getMsg().toString());
            }
        } else {
            alert(R.string.net_error);
        }
    }
}

    @Override
    public void onBackPressed() {
        if(mAlertView.isShowing()){
             mAlertView.dismiss();
        }else{
            super.onBackPressed();
        }
    }
}
