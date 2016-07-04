package com.banksoft.XinChengShop.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.adapter.ProductStandardGridAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.config.MapFlag;
import com.banksoft.XinChengShop.config.SharedPreTag;
import com.banksoft.XinChengShop.dao.ShopProductInfoDao;
import com.banksoft.XinChengShop.entity.*;
import com.banksoft.XinChengShop.http.HttpUtils;
import com.banksoft.XinChengShop.model.*;
import com.banksoft.XinChengShop.model.base.BaseData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.utils.CommonUtil;
import com.banksoft.XinChengShop.utils.JSONHelper;
import com.banksoft.XinChengShop.utils.PopupWindowUtil;
import com.banksoft.XinChengShop.widget.MyGridView;
import com.banksoft.XinChengShop.widget.MyProgressDialog;
import com.banksoft.XinChengShop.widget.MyWebView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by harry_robin on 15/11/17.
 */
public class ShopProductInfoActivity extends XCBaseActivity implements View.OnClickListener {
    private MyWebView myWebView;
    private ProgressBar mProgressLoadingBar;
    private ProgressBar mProgressBar;
    private String CACHE_DIRFILE = "/WEBCACHE";
    private String cachePath;
    //  private ShopProductListVO shopProductListVO;
    private String productID;
    private String shopID;
    private ShopProductInfoDao shopProductInfoDao;
    private ShopProductInfoData shopProductInfoData;
    private LinearLayout shop;
    private RelativeLayout shopCart;
    private ImageView collectImage;

    private TextView buy, shopNUm;
    private ImageView back;
    private TextView addShopCart;
    private TextView title;
    private SharedPreferences shopCartSP;
    private LinkedHashMap<String, ShopCartProductData> linkedHashMap;
    private ProductStandard productStandard;
    private ProductVO productVO;
    private HashMap dataInfoMap;

    private FrameLayout content;

    private ShopCartProductData shopCartProductData;// 店铺订单对象

    private MyProgressDialog progressDialog;

    private int productNum = 1;

    private PopupWindowUtil popupWindowUtil;

    private TextView nullMessage;
    private LinearLayout toolLayout;
    private FrameLayout nullPagerLayout;
    private ShopVO shopVO;

    private ShopInfoEntity shopInfoEntity;


    @Override
    protected void initContentView() {
        setContentView(R.layout.xc_shop_product_info_layout);
    }

    @Override
    protected void initView() {
        nullMessage = (TextView) findViewById(R.id.null_text_message);
        myWebView = (MyWebView) findViewById(R.id.myWebView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressLoadingBar = (ProgressBar) findViewById(R.id.pb);
        shop = (LinearLayout) findViewById(R.id.shop);
        buy = (TextView) findViewById(R.id.buy);
        addShopCart = (TextView) findViewById(R.id.add_shop_cart);
        shopCartSP = getSharedPreferences(SharedPreTag.SHOP_CART_PRODUCT, Context.MODE_PRIVATE);
        // sideBottomPanel = (SlideBottomPanel) findViewById(R.id.side_buttom_panel);
        content = (FrameLayout) findViewById(R.id.content);
        collectImage = (ImageView) findViewById(R.id.collect_btn);
        title = (TextView) findViewById(R.id.titleText);
        shopCart = (RelativeLayout) findViewById(R.id.shopping_car_layout);
        shopNUm = (TextView) findViewById(R.id.num);
        nullPagerLayout = (FrameLayout) findViewById(R.id.null_pager);
        toolLayout = (LinearLayout) findViewById(R.id.tool_layout);
        back = (ImageView) findViewById(R.id.title_back_button);
    }

    @SuppressLint("JavascriptInterface")
    @Override
    protected void initData() {
        // shopProductListVO = (ShopProductListVO) getIntent().getSerializableExtra(IntentFlag.DATA);
        title.setText(R.string.product_info_title);
        productID = getIntent().getStringExtra(IntentFlag.PRODUCT_ID);
        shopID = getIntent().getStringExtra(IntentFlag.SHOP_ID);
        cachePath = getFilesDir().getAbsolutePath() + CACHE_DIRFILE;
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        setmWebViewSettings();
        myWebView.addJavascriptInterface(new JavascriptInterface(), "android");

    }

    @Override
    protected void initOperate() {
        if (shopProductInfoDao == null) {
            shopProductInfoDao = new ShopProductInfoDao(mContext);
        }
        new MyThread().execute(shopProductInfoDao);

        myWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mProgressLoadingBar.setVisibility(View.VISIBLE);
                mProgressLoadingBar.setProgress(newProgress);
                if (newProgress == 100) {
                    mProgressLoadingBar.setVisibility(View.GONE);
                    myWebView.loadUrl("javascript:getDetailData(" + JSONHelper.toJSONString(shopProductInfoData.getData()) + ")");
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        buy.setOnClickListener(this);
        shop.setOnClickListener(this);
        collectImage.setOnClickListener(this);
        shopCart.setOnClickListener(this);
        addShopCart.setOnClickListener(this);
    }

    private class ShopInfoEntity {
        private ShopVO shopVO;
        private ProductVO productVO;

        public ShopVO getShopVO() {
            return shopVO;
        }

        public void setShopVO(ShopVO shopVO) {
            this.shopVO = shopVO;
        }

        public ProductVO getProductVO() {
            return productVO;
        }

        public void setProductVO(ProductVO productVO) {
            this.productVO = productVO;
        }
    }

    private void setmWebViewSettings() {
        WebSettings webSettings = myWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webSettings.setBlockNetworkImage(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSupportZoom(false);
        webSettings.setAllowFileAccess(true);
        if (HttpUtils.isNetworkAvailable(getApplicationContext())) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDatabasePath(cachePath);
        webSettings.setAppCachePath(cachePath);
        webSettings.setAppCacheEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buy://立即购买
                if (productVO == null) {
                    return;
                }
                if (isLogin()) {
                    showPopwindow(R.id.buy);
                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.add_shop_cart:
                if (isLogin()) {
                    showPopwindow(R.id.add_shop_cart);
                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.collect_btn://收藏/取消收藏商品
                if (!isLogin()) {// 没有登录先登录
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                } else {
                    if ((Boolean) collectImage.getTag()) {// 已收藏取消收藏
                        new CancelCollectProductThread().execute(shopProductInfoDao);
                    } else {//没有收藏，收藏操作
                        new CollectProductThread().execute(shopProductInfoDao);
                    }
                }

                break;
            case R.id.shop:
                Intent shopIntent = new Intent(mContext, ShopInfoActivity.class);
                shopIntent.putExtra(IntentFlag.SHOP_ID, productVO.getShopId());
                startActivity(shopIntent);
                break;
            case R.id.shopping_car_layout:
                Intent intent = new Intent(mContext, ShopCartProductActivity.class);
                startActivity(intent);
                break;
            case R.id.title_back_button:
                finish();
                break;
        }
    }


    /**
     * Js 调用
     */
    public class JavascriptInterface {


        /**
         * 跳转评论列表
         */
        @android.webkit.JavascriptInterface
        public void startCommentActivity() {
            Intent intent = new Intent(mContext, ShopProductAssessListActivity.class);
            intent.putExtra(IntentFlag.PRODUCT_ID, productVO.getShopId());
            startActivity(intent);
        }

        /**
         * 跳转店铺
         */
        @android.webkit.JavascriptInterface
        public void startShopInfoActivity() {
            Intent intent = new Intent(mContext, ShopInfoActivity.class);
            intent.putExtra(IntentFlag.SHOP_ID, productVO.getShopId());
            startActivity(intent);
        }

        /**
         * 跳转图片页面
         *
         * @param currentPosition 传点击图片的当前角标  从0开始
         */
        @android.webkit.JavascriptInterface
        public void startImageActivity(int currentPosition) {
            Intent imageIntent = new Intent(mContext, ImageActivity.class);
            String[] images = productVO.getIcon().split("\\|");
            imageIntent.putExtra(IntentFlag.IMAGE_URL, images);
            imageIntent.putExtra(IntentFlag.CURRENT_POSITION, currentPosition);
            startActivity(imageIntent);
        }

        /**
         * 拨打电话
         */
        public void callPhone() {
            CommonUtil.callPhone(mContext, shopVO.getTelephone());

        }

        public void openQQ() {
            CommonUtil.openQQ(mContext, shopVO.getQq());

        }
    }


    /**
     * 产品详情
     */
    private class MyThread extends AsyncTask<ShopProductInfoDao, String, HashMap<String, BaseData>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected HashMap doInBackground(ShopProductInfoDao... params) {
            HashMap dataMap = new HashMap();
            ShopProductInfoData productInfoData = params[0].getShopProductInfo(productID, true);
            if (productInfoData != null) {
                ShopInfoData shopInfoData = params[0].getShopInfoData(productInfoData.getData().getShopId(), true);
                shopVO = shopInfoData.getData();
            }

            ProductStandardData productStandardData = params[0].getProductStandardData(productID, true);
            IsFlagData isCollectData;
            if (isLogin()) {
                isCollectData = (IsFlagData) params[0].isCheckCollect(member.getMember().getId(), productID);
                dataMap.put(MapFlag.DATA_2, isCollectData);
            }

            dataMap.put(MapFlag.DATA_0, productInfoData);
            dataMap.put(MapFlag.DATA_1, productStandardData);
            return dataMap;
        }

        @Override
        protected void onPostExecute(HashMap dataMap) {
            super.onPostExecute(dataMap);
            mProgressBar.setVisibility(View.GONE);
            if (dataMap != null) {
                dataInfoMap = dataMap;
                shopProductInfoData = (ShopProductInfoData) dataMap.get(MapFlag.DATA_0);
                ProductStandardData productStandardData = (ProductStandardData) dataMap.get(MapFlag.DATA_1);
                productStandard = productStandardData.getData();
                if (shopProductInfoData != null && productStandardData != null) {
                    if(shopInfoEntity == null){
                        shopInfoEntity = new ShopInfoEntity();
                    }
                    shopInfoEntity.setProductVO(productVO);
                    shopInfoEntity.setShopVO(shopVO);

                    if (shopProductInfoData.isSuccess() && productStandardData.isSuccess()) {
                        productVO = shopProductInfoData.getData();
                        productStandard = productStandardData.getData();
                        myWebView.loadUrl("file:///android_asset/goods/detail.html");
                        if (isLogin()) {
                            IsFlagData collectData = (IsFlagData) dataMap.get(MapFlag.DATA_2);
                            if (Boolean.parseBoolean(collectData.getData().toString())) {
                                collectImage.setImageResource(R.drawable.c_shop_level1);
                            } else {
                                collectImage.setImageResource(R.drawable.c_shop_level0);
                            }
                            collectImage.setTag(Boolean.parseBoolean(collectData.getData().toString()));

                        }
                    } else {
                        showWarning(R.string.netWork_warning);
                    }
                } else {
                    showNullPager();
                }

            } else {
                showWarning(R.string.net_error);
            }
        }
    }

    /**
     * 收藏商品
     */
    private class CollectProductThread extends AsyncTask<ShopProductInfoDao, String, BaseData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = new MyProgressDialog(ShopProductInfoActivity.this);
            }
            progressDialog.showDialog(getText(R.string.please_wait).toString());
        }

        @Override
        protected BaseData doInBackground(ShopProductInfoDao... params) {
            return params[0].collectProduct(member.getMember().getId(), productVO.getId());
        }

        @Override
        protected void onPostExecute(BaseData baseData) {
            super.onPostExecute(baseData);
            progressDialog.dismiss();
            if (baseData != null) {
                if (baseData.isSuccess()) {
                    collectImage.setTag(true);
                    collectImage.setImageResource(R.drawable.c_shop_level1);//收藏商品
                    showWarning(R.string.collect_success);
                } else {
                    showWarning(R.string.netWork_warning);
                }
            } else {
                showWarning(R.string.net_error);
            }
        }
    }

    /**
     * 删除收藏
     */
    private class CancelCollectProductThread extends AsyncTask<ShopProductInfoDao, String, BaseData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = new MyProgressDialog(ShopProductInfoActivity.this);
            }
            progressDialog.showDialog(getText(R.string.please_wait).toString());
        }

        @Override
        protected BaseData doInBackground(ShopProductInfoDao... params) {
            return params[0].cancelCollectProduct(member.getMember().getId(), productVO.getId());
        }

        @Override
        protected void onPostExecute(BaseData baseData) {
            super.onPostExecute(baseData);
            progressDialog.dismiss();
            if (baseData != null) {
                if (baseData.isSuccess()) {
                    collectImage.setTag(false);
                    collectImage.setImageResource(R.drawable.c_shop_level0);
                    showWarning(R.string.cancel_product_success);
                } else {
                    showWarning(R.string.netWork_warning);
                }
            } else {
                showWarning(R.string.net_error);
            }
        }
    }

    /**
     * 显示popupWindow
     */
    private void showPopwindow(final int resId) {
        final boolean[] isOk = {false};// 颜色尺码是否选择完

        final HashMap<String, HashMap<String, String>> priceAndStockMap = new HashMap<>();


        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.of_sizes_layout, null);

        popupWindowUtil = new PopupWindowUtil(this, view, findViewById(R.id.buy));
        popupWindowUtil.backgroundAlpha(0.5f);
        popupWindowUtil.showPopupWindow();

        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        final TextView price = (TextView) view.findViewById(R.id.price);
        TextView inventory = (TextView) view.findViewById(R.id.inventory);
        final TextView ofSizes = (TextView) view.findViewById(R.id.of_sizes);
        ImageView close = (ImageView) view.findViewById(R.id.close);
        ImageButton minusBtn = (ImageButton) view.findViewById(R.id.imgBtn_minus);
        ImageButton plusBtn = (ImageButton) view.findViewById(R.id.imgBtn_plus);
        final Button okBtn = (Button) view.findViewById(R.id.ok);
        final TextView num = (TextView) view.findViewById(R.id.num);

        LinearLayout contentView = (LinearLayout) view.findViewById(R.id.content);
        ImageLoader mImageLoader = ImageLoader.getInstance();
        String imageUrl = "";
        if (productVO.getIcon() != null) {
            imageUrl = ControlUrl.BASE_URL + productVO.getIcon().split("\\|")[0];
        } else {
            imageUrl = "";
        }

        mImageLoader.displayImage(imageUrl, imageView, XCApplication.options);

        inventory.setText(String.valueOf(productVO.getStockNum()) + "件");
        price.setText(String.valueOf(productVO.getPrice()));
        boolean isImage;
        if (productStandard.getStandardsList() != null) {
            price.setText(String.valueOf(productStandard.getMinPrice()) + "~" + productStandard.getMaxPrice() + "元");
            for (int posiion = 0; posiion < productStandard.getPriceStock().size(); posiion++) {
                PriceAndStock priceAndStock = productStandard.getPriceStock().get(posiion);
                HashMap<String, String> map = new HashMap<>();
                map.put(MapFlag.PRICE, String.valueOf(priceAndStock.getPrice()));
                map.put(MapFlag.STOCK, String.valueOf(priceAndStock.getStock()));
                priceAndStockMap.put(priceAndStock.getIds(), map);
            }
            contentView.removeAllViews();
            final HashMap<Integer, IdAndValue> selectMap = new HashMap<>();
            for (int positon = 0; positon < productStandard.getStandardsList().size(); positon++) {
                final Standard standard = productStandard.getStandardsList().get(positon);
                View standardCell = LayoutInflater.from(mContext).inflate(R.layout.product_standard_item_layout, null);
                TextView name = (TextView) standardCell.findViewById(R.id.standard_name);

                final MyGridView gridView = (MyGridView) standardCell.findViewById(R.id.gridView);
                name.setText(standard.getName());
                if ("image".equals(standard.getType())) {
                    isImage = true;
                } else {
                    isImage = false;
                }
                final ProductStandardGridAdapter standardGridAdapter = new ProductStandardGridAdapter(mContext, standard.getList(), isImage);
                gridView.setAdapter(standardGridAdapter);
                contentView.addView(standardCell);
                gridView.setTag(positon);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        View contentLayout = view.findViewById(R.id.content_layout);
                        if (standardGridAdapter.lastView != null) {
                            standardGridAdapter.lastView.setSelected(false);
                        }

                        StringBuffer idBuffer = new StringBuffer();
                        StringBuffer ofSizeBuffer = new StringBuffer();

                        if (contentLayout.isSelected()) {
                            contentLayout.setSelected(false);
                            selectMap.remove(gridView.getTag());

                        } else {
                            contentLayout.setSelected(true);
                            selectMap.put((Integer) gridView.getTag(), ((IdAndValue) standardGridAdapter.getItem(position)));
                        }

                        /**
                         * 尺码选完计算库存价格
                         */
                        if (selectMap.keySet().size() == productStandard.getStandardsList().size()) {
                            isOk[0] = true;

                            for (int i = 0; i < selectMap.keySet().size(); i++) {
                                idBuffer.append(selectMap.get(i).getId()).append("|");
                                ofSizeBuffer.append(selectMap.get(i).getValue()).append(" ");
                            }
                            String ids = idBuffer.substring(0, idBuffer.length() - 1);// 去掉最后一个“｜”
                            HashMap<String, String> dataMap = priceAndStockMap.get(ids);
                            price.setText(dataMap.get(MapFlag.PRICE));
                            ofSizeBuffer.append(dataMap.get(MapFlag.STOCK) + "件");
                            okBtn.setTag(R.id.select_map, selectMap);
                            okBtn.setTag(R.id.price, dataMap.get(MapFlag.PRICE));
                        }
                        ofSizes.setText(ofSizeBuffer.toString());
                        standardGridAdapter.lastView = contentLayout;
                    }
                });
            }
        } else {
            isOk[0] = true;
        }


        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(num.getText().toString()) > 1) {
                    num.setText("" + (Integer.parseInt(num.getText().toString()) - 1));
                }
            }
        });
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num.setText("" + (Integer.parseInt(num.getText().toString()) + 1));
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowUtil.dismiss();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (resId) {
                    case R.id.buy:
                        if (isOk[0]) {// 可以购买
                            Float price = 0f;
                            if (productStandard.getStandardsList() == null) {//没有规格尺码
                                price = productVO.getPrice();
                            } else {
                                price = (Float.parseFloat(okBtn.getTag(R.id.price).toString()));
                            }
                            toOrderSubmit(Integer.parseInt(num.getText().toString()), price, (HashMap<Integer, IdAndValue>) okBtn.getTag(R.id.select_map));
                            popupWindowUtil.dismiss();
                        } else {
                            showWarning(R.string.please_select_standard);
                        }

                        break;
                    case R.id.add_shop_cart:
                        if (isOk[0]) {// 可以购买
                            Float price = 0f;
                            if (productStandard.getStandardsList() == null) {//没有规格尺码
                                price = productVO.getPrice();
                            } else {
                                price = (Float.parseFloat(okBtn.getTag(R.id.price).toString()));
                            }
                            popupWindowUtil.dismiss();
                            addShopProductCart(Integer.parseInt(num.getText().toString()), price, (HashMap<Integer, IdAndValue>) okBtn.getTag(R.id.select_map), productVO);
                        } else {
                            showWarning(R.string.please_select_standard);
                        }
                        break;
                }
            }
        });

    }

    /**
     * 直接购买
     */
    private void toOrderSubmit(int num, float salePrice, HashMap<Integer, IdAndValue> standardMap) {
        ShopCartProductData shopCartProductData = new ShopCartProductData();
        ProductCart productCart = new ProductCart();
        productCart.setNum(num);
        productCart.setSalePrice(salePrice);
        productCart.setTotal(num * salePrice);
        productCart.setProductVO(productVO);
        StringBuffer productCartIds = new StringBuffer(productVO.getId() + "|");
        if (productStandard != null && productStandard.getStandardsList() != null) {
            StringBuffer standardIds = new StringBuffer();
            StringBuffer standardNames = new StringBuffer();
            for (Standard standard : productStandard.getStandardsList()) {
                standardIds.append(standard.getId()).append("|");
                standardNames.append(standard.getName()).append("|");
            }
            productCart.setStandardIDs(standardIds.toString());
            productCart.setStandardNames(standardNames.toString());

            if (standardMap != null) {
                StringBuffer idAndValuesIDS = new StringBuffer();
                StringBuffer idAndNames = new StringBuffer();
                for (int i = 0; i < standardMap.keySet().size(); i++) {
                    idAndValuesIDS.append(standardMap.get(i).getId()).append("|");
                    idAndNames.append(standardMap.get(i).getValue()).append("|");
                    productCartIds.append(standardMap.get(i).getId()).append("|");
                }
                productCart.setIdAndValues(standardMap);
                productCart.setIdAndValueIDs(idAndValuesIDS.toString());
                productCart.setIdAndValueNames(idAndNames.toString());
            }
        }


        HashMap<String, ProductCart> productCartHashMap = new HashMap<>();

        productCartHashMap.put(productCartIds.toString(), productCart);


        shopCartProductData.setShopId(productVO.getShopId());
        shopCartProductData.setShopName(productVO.getShopName());
        shopCartProductData.setProductVOHashMap(productCartHashMap);
        shopCartProductData.setTotal(num * salePrice);
        shopCartProductData.setIsFree(productVO.isFreight());
        LinkedHashMap<String, ShopCartProductData> shopCartProductDataLinkedHashMap = new LinkedHashMap<>();
        shopCartProductDataLinkedHashMap.put(productVO.getShopId(), shopCartProductData);

        Intent intent = new Intent(mContext, SubmitSaleOrderActivity.class);
        intent.putExtra(IntentFlag.SHOP_TYPE, productVO.getShopServerType());
        intent.putExtra(IntentFlag.SHOP_CART_PRODUCT_DATA, shopCartProductDataLinkedHashMap);
        startActivity(intent);
    }

    /**
     * 保存购物车数据
     */
    private void addShopProductCart(int num, float salePrice, HashMap<Integer, IdAndValue> standardMap, ProductVO productVO) {
        String dataJson = shopCartSP.getString(SharedPreTag.SHOP_CART_PRODUCT, "");
        float totalMoney = 0f;
        if ("".equals(dataJson)) {//购物车无商品
            linkedHashMap = new LinkedHashMap<>();
        } else {//购物车有商品
            linkedHashMap = JSONHelper.fromJSONObject(dataJson, JSONHelper.SHOP_CART_PRODUCT_DATA);
        }
        ShopCartProductData shopCartProductData = linkedHashMap.get(productVO.getShopId());
        if (shopCartProductData != null) {//已有该店铺的产品
            StringBuffer productCartIds = new StringBuffer(productVO.getId() + "|");

            if (standardMap != null) {// 存在颜色尺码规格
                for (Integer position : standardMap.keySet()) {
                    productCartIds.append(standardMap.get(position).getId()).append("|");
                }
            }


            ProductCart cartProduct = shopCartProductData.getProductVOHashMap().get(productCartIds.toString());

            if (cartProduct != null) {// 购物车已存在有商品 修改产品数量和总价后 重新保存
                cartProduct.setNum(cartProduct.getNum() + num);
                cartProduct.setTotal(cartProduct.getNum() * Float.parseFloat(cartProduct.getProductVO().getSalePrice()));
                shopCartProductData.getProductVOHashMap().put(cartProduct.getProductVO().getId() + "|", cartProduct);
            } else {// 没有该商品
                cartProduct = new ProductCart();
                cartProduct.setIdAndValues(standardMap);
                cartProduct.setNum(num);
                cartProduct.setProductVO(productVO);
                cartProduct.setTotal(num * salePrice);
                cartProduct.setSalePrice(salePrice);
                cartProduct.setIsSelect(true);
                shopCartProductData.getProductVOHashMap().put(productCartIds.toString(), cartProduct);
            }

        } else {// 没有该店铺的商品
            shopCartProductData = new ShopCartProductData();
            shopCartProductData.setShopName(productVO.getShopName());
            shopCartProductData.setShopId(productVO.getShopId());
            LinkedHashMap<String, ProductCart> productMap = new LinkedHashMap<>();

            ProductCart productCart = new ProductCart();
            productCart.setProductVO(productVO);
            productCart.setNum(num);
            productCart.setSalePrice(salePrice);
            productCart.setTotal(num * salePrice);
            productCart.setIsSelect(true);
            productCart.setIdAndValues(standardMap);

            StringBuffer productCartIds = new StringBuffer(productVO.getId() + "|");

            if (standardMap != null) {//存在颜色尺码
                for (Integer position : standardMap.keySet()) {
                    productCartIds.append(standardMap.get(position).getId()).append("|");
                }
            }

            productMap.put(productCartIds.toString(), productCart);
            shopCartProductData.setProductVOHashMap(productMap);

        }

//        for(String key:shopCartProductData.getProductVOHashMap().keySet()){
//            ProductCart productCart = shopCartProductData.getProductVOHashMap().get(key);
//            totalMoney.
//        }

        linkedHashMap.put(shopCartProductData.getShopId(), shopCartProductData);
        shopCartSP.edit().putString(SharedPreTag.SHOP_CART_PRODUCT, JSONHelper.toJSONString(linkedHashMap)).commit();
        shopNUm.setText("" + linkedHashMap.keySet().size());
        showWarning(R.string.add_shop_cart_success);
    }

    /**
     * 商品过期
     */
    private void showNullPager() {
        nullMessage = (TextView) findViewById(R.id.null_text_message);
        nullMessage.setText(R.string.product_already_bargain);
        nullPagerLayout.setVisibility(View.VISIBLE);
        toolLayout.setVisibility(View.GONE);
    }


}
