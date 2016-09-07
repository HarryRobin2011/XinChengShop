package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.ReturnGoodRecyclerViewAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.ReturnGoodsDao;
import com.banksoft.XinChengShop.entity.OrderVO;
import com.banksoft.XinChengShop.entity.ReturnMoney;
import com.banksoft.XinChengShop.entity.ReturnProduct;
import com.banksoft.XinChengShop.model.ImageUrlData;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.type.OrderStatus;
import com.banksoft.XinChengShop.type.ReturnType;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.utils.ImageUtil;
import com.banksoft.XinChengShop.utils.PopupWindowUtil;
import com.banksoft.XinChengShop.widget.MyProgressDialog;
import com.banksoft.XinChengShop.widget.alertview.AlertView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Robin on 2016/7/22.
 * 退款退货
 */
public class ReturnGoodsActivity extends XCBaseActivity implements View.OnClickListener, ReturnGoodRecyclerViewAdapter.OnRecylerViewOnItemLinstener {
    private TextView title;
    private ImageView back;
    private RadioGroup returnTypeGroup;
    private EditText returnReasonEdit;
    private EditText returnMoneyEdit;
    // private EditText returnDescribe;
    private LinearLayout imageLayout;
    private Button submitApply;
    private TextView orderMoney;
    private OrderVO orderVO;
    private AlertView mAlertView;
    private ReturnType returnType = ReturnType.RETURN_MONEY;// 默认退款
    private RadioButton returnGoods; // 退货按钮

    private ReturnGoodsDao returnGoodsDao;

    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 0;

    private static final int SELECT_PICTURE = 1;

    private int OPERATION_SELECT_AREA = 2;
    private String localTempImgDir = "xinchengShop/";

    private PopupWindowUtil popupWindowUtil;


    private LinkedList photoList;

    private ImageLoader mImageLoader;

    private RecyclerView mRecyclerView;

    private ReturnGoodRecyclerViewAdapter returnGoodRecyclerViewAdapter;

    private MyProgressDialog progressDialog;


    private String capturePath;// 当前拍照的图片路径

    private LinearLayout expressLayout;
    private EditText expressNoEdit;

    @Override
    protected void initContentView() {
        setContentView(R.layout.return_goods_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
        returnTypeGroup = (RadioGroup) findViewById(R.id.return_group_type);
        returnReasonEdit = (EditText) findViewById(R.id.return_reason);
        returnMoneyEdit = (EditText) findViewById(R.id.money);
        //returnDescribe = (EditText) findViewById(R.id.return_describe);
        submitApply = (Button) findViewById(R.id.submit_apply);
        imageLayout = (LinearLayout) findViewById(R.id.image_layout);
        orderMoney = (TextView) findViewById(R.id.order_money);
        returnGoods = (RadioButton) findViewById(R.id.return_the_goods);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        expressLayout = (LinearLayout) findViewById(R.id.express_layout);
        expressNoEdit = (EditText) findViewById(R.id.express_no);
    }

    @Override
    protected void initData() {
        orderVO = (OrderVO) getIntent().getSerializableExtra(IntentFlag.ORDER_VO);
        title.setText(R.string.return_goods_title);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        orderMoney.setText("最多" + orderVO.getTotalMoney());


        mImageLoader = ImageLoader.getInstance().getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));

        if (OrderStatus.PAY.name().equals(orderVO.getStatus())) {// 已付款未发货
            returnGoods.setVisibility(View.GONE);
        } else if (OrderStatus.DISPATCH.name().equals(orderVO.getStatus())) {// 已发货，未收货
            returnGoods.setVisibility(View.VISIBLE);
        } else if (OrderStatus.SUCCESS.name().equals(orderVO.getStatus())) {//已经收货
            returnGoods.setVisibility(View.VISIBLE);
        }

        if (returnType.equals(ReturnType.RETURN_MONEY)) {
            returnReasonEdit.setHint(R.string.please_select_return_money_reason);
        } else {
            returnReasonEdit.setHint(R.string.please_select_return_good_reason);
        }

        if (photoList == null) {
            photoList = new LinkedList();
            photoList.add(ControlUrl.ANDROID_ASSETS_BASE_PATH + "take_photo.png");
        }
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
            photoList.add(0, ImageUtil.getImageAbsolutePath(this, uri));
            setCaremaImage();
        }
    }

    /**
     * 拍照完显示照片
     */
    private void setCaremaImage() {
        returnGoodRecyclerViewAdapter = (ReturnGoodRecyclerViewAdapter) mRecyclerView.getAdapter();
        returnGoodRecyclerViewAdapter.dataList.add(0, capturePath);
        returnGoodRecyclerViewAdapter.notifyDataSetChanged();

    }

    @Override
    protected void initOperate() {
        GridLayoutManager manager = new GridLayoutManager(mContext, 4);
        mRecyclerView.setLayoutManager(manager);
        ReturnGoodRecyclerViewAdapter adapter = new ReturnGoodRecyclerViewAdapter(mContext, photoList);
        adapter.setCurrentRow(0);
        adapter.setOnRecylerViewOnItemLinstener(this);
        mRecyclerView.setAdapter(adapter);
        returnTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.return_money) {//退款
                    returnType = ReturnType.RETURN_MONEY;// 退款
                    expressLayout.setVisibility(View.GONE);
                } else {
                    returnType = ReturnType.RETURN_GOODS;// 退货
                    expressLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        submitApply.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back_button:
                finish();
                break;
            case R.id.submit_apply:// 提交退货申请
                if (returnType == ReturnType.RETURN_MONEY) {// 已付款未发货
                    ReturnMoney returnMoney = getReturnMoney();
                    if (returnMoney != null) {
                        if (returnGoodsDao == null) {
                            returnGoodsDao = new ReturnGoodsDao(mContext);
                        }
                        new ReturnMoneyTask(returnMoney).execute(returnGoodsDao);
                    }

                } else if (returnType == ReturnType.RETURN_GOODS) {// 已发货，未收货
                    ReturnProduct returnProduct = getReturnProduct();
                    if (returnProduct != null) {
                        if (returnGoodsDao == null) {
                            returnGoodsDao = new ReturnGoodsDao(mContext);
                        }
                        new ReturnGoodsTask(returnProduct).execute(returnGoodsDao);
                    }

                }
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
        }
    }

    private void takePhoto() {
        if (popupWindowUtil == null) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.take_photo_tool_layout, null);
            TextView takePhoto = (TextView) view.findViewById(R.id.take_photo);
            TextView fromPhoto = (TextView) view.findViewById(R.id.from_photo_select);
            TextView cancel = (TextView) view.findViewById(R.id.cancel);
            takePhoto.setOnClickListener(this);
            fromPhoto.setOnClickListener(this);
            cancel.setOnClickListener(this);
            popupWindowUtil = new PopupWindowUtil(this, view, findViewById(R.id.submit_apply));
        }
        popupWindowUtil.showPopupWindow();
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


    private ReturnMoney getReturnMoney() {
        String returnReasonStr = returnReasonEdit.getText().toString();
        String returnMoneyStr = returnMoneyEdit.getText().toString();
        if (returnMoneyStr.isEmpty()) {
            alert(R.string.return_money_no_empty);
            return null;
        } else if (returnReasonStr.isEmpty()) {
            alert(R.string.return_reason_no_empty);
            return null;
        }
        BigDecimal returnMoneyBigDecimal = new BigDecimal(returnMoneyStr);
        BigDecimal orderMoneyBigDecimal = new BigDecimal(String.valueOf(orderVO.getTotalMoney()));
        int result = returnMoneyBigDecimal.compareTo(orderMoneyBigDecimal);

        if (result == 1) {
            alert("最多申请退款" + orderVO.getTotalMoney());
            return null;
        }
        ReturnMoney returnMoney = new ReturnMoney();
        returnMoney.setOrderId(orderVO.getId());
        returnMoney.setMemberReason(returnReasonStr);
        returnMoney.setOrderNo(orderVO.getNo());
        returnMoney.setMemberMoney(Float.valueOf(returnMoneyStr));
        returnMoney.setMemberAccount(member.getMember().getAccount());
        returnMoney.setBackTime(System.currentTimeMillis());
        returnMoney.setMemberId(member.getMember().getId());
        returnMoney.setOrderTotal(orderVO.getTotalMoney());
        return returnMoney;
    }

    private ReturnProduct getReturnProduct() {
        String returnReasonStr = returnReasonEdit.getText().toString();
        String returnMoney = returnMoneyEdit.getText().toString();
        String expressNo = expressNoEdit.getText().toString();
        //   String returnDescribeStr = returnDescribe.getText().toString();
        BigDecimal returnMoneyBigDecimal = new BigDecimal(returnMoney);
        BigDecimal orderMoneyBigDecimal = new BigDecimal(orderVO.getTotalMoney());
        int result = returnMoneyBigDecimal.compareTo(orderMoneyBigDecimal);
        if (returnMoney.isEmpty()) {
            alert(R.string.return_money_no_empty);
            return null;
        } else if (returnReasonStr.isEmpty()) {
            alert(R.string.return_reason_no_empty);
            return null;
        } else if (result == 1) {
            alert("最多申请退款" + orderVO.getTotalMoney());
            return null;
        }

        ReturnProduct returnProduct = new ReturnProduct();
        returnProduct.setOrderId(orderVO.getId());
        returnProduct.setMemberId(member.getMember().getId());
        returnProduct.setOrderTotal(orderVO.getTotalMoney());
        returnProduct.setMemberAccount(member.getMember().getAccount());
        returnProduct.setMemberMoney(Float.valueOf(returnMoney));
        returnProduct.setOrderNo(orderVO.getNo());
        returnProduct.setMemberReason(returnReasonStr);
        returnProduct.setShopId(orderVO.getShopId());
        returnProduct.setShopAccount(orderVO.getShopAccount());
        if (ReturnType.RETURN_MONEY.equals(returnType)) {
            returnProduct.setReturnProduct(false);
        } else {
            if (expressNo == null || expressNo.isEmpty()) {
                alert(R.string.express_no_not_empty);
                return null;
            }
            returnProduct.setNo(expressNo);
            returnProduct.setReturnProduct(true);
        }
        return returnProduct;
    }

    /**
     * @param currentRow
     * @param position
     * @param dataSize
     */
    @Override
    public void onItemLinstener(int currentRow, int position, int dataSize) {
        if (dataSize > 3) {// 跳转图片详情页

        } else {
            if (position == dataSize - 1) {// 拍照
                takePhoto();
            } else {//跳转图片详情

            }
        }
    }

    /**
     * 已付款未发货退款
     */
    private class ReturnMoneyTask extends AsyncTask<ReturnGoodsDao, String, IsFlagData> {
        Object data;

        public ReturnMoneyTask(Object data) {
            this.data = data;
        }

        @Override
        protected IsFlagData doInBackground(ReturnGoodsDao... params) {
            return params[0].returnMoney((ReturnMoney) data);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = new MyProgressDialog(ReturnGoodsActivity.this);
            }
            progressDialog.showDialog(R.string.please_wait);
        }

        @Override
        protected void onPostExecute(IsFlagData isFlagData) {
            super.onPostExecute(isFlagData);
            progressDialog.dismiss();
            if (isFlagData != null) {
                if (isFlagData.isSuccess()) {
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    alert(isFlagData.getMsg().toString());
                }
            } else {
                alert(R.string.net_error);
            }
        }
    }

    /**
     * 退货或者仅退款
     */
    private class ReturnGoodsTask extends AsyncTask<ReturnGoodsDao, String, IsFlagData> {
        ReturnProduct returnProduct;

        public ReturnGoodsTask(ReturnProduct returnProduct) {
            this.returnProduct = returnProduct;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = new MyProgressDialog(ReturnGoodsActivity.this);
            }
            progressDialog.showDialog(R.string.please_wait);
        }

        @Override
        protected IsFlagData doInBackground(ReturnGoodsDao... params) {
            List imageList = null;
            StringBuffer imageBuffer = new StringBuffer();
            if (imageList != null && imageList.size() > 1) {
                for (Object data : imageList) {
                    String path = (String) data;
                    File file = new File(path);
                    String type = path.substring(path.lastIndexOf(".") + 1, path.length());
                    ImageUrlData imageUrl = params[0].submitImage(file, type);
                    if (imageUrl == null) {
                        continue;
                    }
                    imageBuffer.append(imageUrl.getData()).append("|");
                }
            }
            returnProduct.setMemberImage(imageBuffer.toString());
            if (returnType == ReturnType.RETURN_GOODS) {//退货
                return params[0].returnProductMoney(returnProduct);
            } else if (returnType == ReturnType.RETURN_MONEY) {//退款
                return params[0].returnProduct(returnProduct);
            }
            return null;
        }

        @Override
        protected void onPostExecute(IsFlagData isFlagData) {
            super.onPostExecute(isFlagData);
            progressDialog.dismiss();
            if (isFlagData != null) {
                if (isFlagData.isSuccess()) {
                    setResult(Activity.RESULT_OK);
                    finish();
                    alert(R.string.submit_apply_success);
                } else {
                    alert(isFlagData.getMsg().toString());
                }
            } else {
                alert(R.string.net_error);
            }
        }
    }
}
