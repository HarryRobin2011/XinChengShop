package com.banksoft.XinChengShop.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.LoginDao;
import com.banksoft.XinChengShop.model.base.BaseData;
import com.banksoft.XinChengShop.type.OrderMaster;
import com.banksoft.XinChengShop.type.OrderStatus;
import com.banksoft.XinChengShop.ui.*;
import com.banksoft.XinChengShop.ui.base.XCBaseFragment;
import com.banksoft.XinChengShop.ui.mySelf.MemberInfoActivity;
import com.banksoft.XinChengShop.utils.CommonUtil;
import com.banksoft.XinChengShop.utils.update.UpdateUtil;
import com.banksoft.XinChengShop.widget.MyProgressDialog;
import com.banksoft.XinChengShop.widget.imageView.CustomShapeImageView;
import com.banksoft.XinChengShop.widget.imagezoom.easing.Linear;
import com.google.gson.internal.LinkedTreeMap;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by harry_robin on 15/11/4.
 */
public class XCMyselfFragment extends XCBaseFragment implements View.OnClickListener {
    public CustomShapeImageView login;
    private TextView account, myCollectProduct, myCollectShop, myWallet, blance, integral;
    private LinearLayout myOrder, myComment, myShop, mySendOrder, myIntegralMall, myScoreOrder, checkUpdata, settings, sellerOrder, saleCheck, myProductManager, dispatch_manager;
    private LinearLayout shop_layout;
    private Button rigjtBtn;
    private ImageLoader mImageLoader;
    private XCMainActivity activity;

    private FrameLayout orderCreate, orderPay, orderDispatch, orderSuccess, orderReturn;
    private TextView orderCreateNum, orderPayNum, orderDispatchNum, orderSuccessNum, orderReturnNum, checkAllOrder;

    private LinearLayout searchLayout;

    private ImageView bgImage;

    private TextView title;

    private MyProgressDialog myProgressDialog;

    private LoginDao loginDao;


    @Override
    public View initContentView() {
        activity = (XCMainActivity) getActivity();
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        return LayoutInflater.from(mContext).inflate(R.layout.xc_myself_layout, null);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Activity.RESULT_FIRST_USER) {
            if (resultCode == Activity.RESULT_OK) {
                if(activity.isLogin()){

                    if (loginDao == null) {
                        loginDao = new LoginDao(mContext);
                    }
                    new MyOrderStatusTask().execute(loginDao);
                }
                setMySelf();
            }
        }
    }

    private void setMySelf() {
        if (activity.isLogin()) {
            account.setText(activity.member.getMember().getAccount());
            blance.setText(activity.member.getMember().getBalance() + "");
            integral.setText(activity.member.getMember().getMemberPoint() + "");
            String imageUrl = activity.member.getMember().getImageFile();

            if (imageUrl == null || imageUrl.equals("")) {
                login.setImageResource(R.drawable.default_head_image);
            } else {
                mImageLoader.displayImage(ControlUrl.BASE_URL + activity.member.getMember().getImageFile().split("\\|")[0], login, XCApplication.options);
            }
            if (activity.member.getShop() != null && activity.member.getShop().isAuditStatus()) {// 开店了
                if (activity.member.getShop().isStatus()) {//店铺开启了

                } else {//店铺未开启

                }
                myShop.setVisibility(View.GONE);
                shop_layout.setVisibility(View.VISIBLE);//开店功能
            } else {//没有开店了
                myShop.setVisibility(View.VISIBLE);
                shop_layout.setVisibility(View.GONE);
            }


        } else {
            account.setText("");
            blance.setText("" + 0f);
            integral.setText("" + 0f);
            orderCreateNum.setVisibility(View.GONE);
            orderPayNum.setVisibility(View.GONE);
            orderDispatchNum.setVisibility(View.GONE);
            orderSuccessNum.setVisibility(View.GONE);
            // mImageLoader.displayImage(ControlUrl.BASE_URL+activity.member.getMember().getImageFile(),login, XCApplication.options);
            login.setImageResource(R.drawable.contact_avator);
            shop_layout.setVisibility(View.GONE);

        }

    }

    @Override
    public void initView(View view) {
        login = (CustomShapeImageView) view.findViewById(R.id.login);
        account = (TextView) view.findViewById(R.id.account);
        myCollectProduct = (TextView) view.findViewById(R.id.my_collect_product);
        myCollectShop = (TextView) view.findViewById(R.id.my_collect_shop);
        myScoreOrder = (LinearLayout) view.findViewById(R.id.my_score_order);
        myOrder = (LinearLayout) view.findViewById(R.id.my_order);
        myWallet = (TextView) view.findViewById(R.id.my_wallet);
        myComment = (LinearLayout) view.findViewById(R.id.my_comment);
        myShop = (LinearLayout) view.findViewById(R.id.my_shop);
        mySendOrder = (LinearLayout) view.findViewById(R.id.my_send_order);
        myIntegralMall = (LinearLayout) view.findViewById(R.id.my_integral_mall);
        blance = (TextView) view.findViewById(R.id.balance);
        integral = (TextView) view.findViewById(R.id.integral);
        searchLayout = (LinearLayout) getActivity().findViewById(R.id.search_layout);
        rigjtBtn = (Button) getActivity().findViewById(R.id.titleRightButton);
        checkUpdata = (LinearLayout) view.findViewById(R.id.check_update);
        settings = (LinearLayout) view.findViewById(R.id.setting);
        shop_layout = (LinearLayout) view.findViewById(R.id.shop_layout);
        sellerOrder = (LinearLayout) view.findViewById(R.id.seller_order_layout);
        saleCheck = (LinearLayout) view.findViewById(R.id.consumption_layout);
        dispatch_manager = (LinearLayout) view.findViewById(R.id.dispatch_manager);

        bgImage = (ImageView) view.findViewById(R.id.titleBg);

        title = (TextView) view.findViewById(R.id.titleText);

        searchLayout = (LinearLayout) view.findViewById(R.id.search_layout);

        myProductManager = (LinearLayout) view.findViewById(R.id.my_product_manager);

        orderCreate = (FrameLayout) view.findViewById(R.id.order_create);
        orderCreateNum = (TextView) view.findViewById(R.id.order_create_num);
        orderPay = (FrameLayout) view.findViewById(R.id.order_pay);
        orderPayNum = (TextView) view.findViewById(R.id.order_pay_num);
        orderDispatch = (FrameLayout) view.findViewById(R.id.order_dispatch);
        orderDispatchNum = (TextView) view.findViewById(R.id.order_dispatch_num);
        orderSuccess = (FrameLayout) view.findViewById(R.id.order_success);
        orderSuccessNum = (TextView) view.findViewById(R.id.order_success_num);
        orderReturn = (FrameLayout) view.findViewById(R.id.order_return);
        orderReturnNum = (TextView) view.findViewById(R.id.order_return_num);
    }

    @Override
    public void initData() {
        title.setText(R.string.my_self);
        title.setTextColor(getResources().getColor(R.color.white));
//.leftBtn.setVisibility(View.GONE);
        searchLayout.setVisibility(View.GONE);
        bgImage.setBackgroundResource(R.color.bg_red);
        if(activity.isLogin()){
            setMySelf();
            if (loginDao == null) {
                loginDao = new LoginDao(mContext);
            }
            new MyOrderStatusTask().execute(loginDao);
        }
    }

    @Override
    public void initOperation() {
        login.setOnClickListener(this);
        myCollectProduct.setOnClickListener(this);
        myCollectShop.setOnClickListener(this);
        myOrder.setOnClickListener(this);
        myWallet.setOnClickListener(this);
        myComment.setOnClickListener(this);
        myShop.setOnClickListener(this);
        mySendOrder.setOnClickListener(this);
        myIntegralMall.setOnClickListener(this);
        blance.setOnClickListener(this);
        integral.setOnClickListener(this);
        myScoreOrder.setOnClickListener(this);
        checkUpdata.setOnClickListener(this);
        settings.setOnClickListener(this);
        sellerOrder.setOnClickListener(this);
        saleCheck.setOnClickListener(this);
        myProductManager.setOnClickListener(this);
        dispatch_manager.setOnClickListener(this);

        orderCreate.setOnClickListener(this);
        orderPay.setOnClickListener(this);
        orderDispatch.setOnClickListener(this);
        orderSuccess.setOnClickListener(this);
        orderReturn.setOnClickListener(this);
    }

    @Override
    public Fragment getInstance() {
        return null;
    }

    @Override
    public CharSequence getTitle() {
        return getText(R.string.my_self);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                if (activity.isLogin()) {
                    //跳转详情页
                    Intent infoIntent = new Intent(mContext, MemberInfoActivity.class);
                    startActivityForResult(infoIntent, Activity.RESULT_FIRST_USER);
                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                }

                break;
            case R.id.my_collect_product:
                if (activity.isLogin()) {
                    Intent intent = new Intent(mContext, CollectActivity.class);
                    intent.putExtra(IntentFlag.INDEX, 0);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                }

                break;
            case R.id.my_collect_shop:
                if (activity.isLogin()) {
                    Intent intent = new Intent(mContext, CollectActivity.class);
                    intent.putExtra(IntentFlag.INDEX, 1);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                }
                break;
            case R.id.my_order:
                if (activity.isLogin()) {
                    Intent intent = new Intent(mContext, OrderListActivity.class);
                    intent.putExtra(IntentFlag.Order_MASTER, OrderMaster.SALE.name());
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                }
                break;
            case R.id.my_wallet:
                if (activity.isLogin()) {
                    //跳转详情页
                    Intent intent = new Intent(mContext, WalletManagerActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                }
                break;
            case R.id.my_comment:
                if (activity.isLogin()) {
                    //跳转评论
                    Intent commentIntent = new Intent(mContext, CommentActivity.class);
                    startActivity(commentIntent);
                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                }
                break;
            case R.id.my_shop:
                if (activity.isLogin()) {//申请店铺
                    Intent intent = new Intent(mContext, ApplyOpenShopActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                }
                break;
            case R.id.my_send_order:
                if (activity.isLogin()) {
                    //跳转详情页
                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                }
                break;
            case R.id.balance:
                if (activity.isLogin()) {
                    //跳转详情页
                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                }
                break;
            case R.id.integral:
                if (activity.isLogin()) {
                    //跳转详情页
                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                }
                break;
            case R.id.my_integral_mall:

                break;
            case R.id.my_score_order://我的积分订单
                if (activity.isLogin()) {
                    Intent intent = new Intent(mContext, ScoreOrderListActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                }
                break;

            case R.id.check_update:
                UpdateUtil updateUtil = new UpdateUtil(getActivity(), true);
                updateUtil.isUpdate();
                break;

            case R.id.seller_order_layout:// 卖家订单
                if (activity.isLogin()) {
                    Intent sellerIntent = new Intent(mContext, OrderListActivity.class);
                    sellerIntent.putExtra(IntentFlag.Order_MASTER, OrderMaster.SELLER.name());
                    startActivityForResult(sellerIntent, Activity.RESULT_FIRST_USER);
                } else {
                    Intent loginIntennt = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(loginIntennt, Activity.RESULT_FIRST_USER);
                }

                break;
            case R.id.consumption_layout://消费验证
                if (activity.isLogin()) {
                    Intent checkInten = new Intent(mContext, CheckCodeActivity.class);
                    startActivity(checkInten);
                } else {
                    Intent loginIntennt = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(loginIntennt, Activity.RESULT_FIRST_USER);
                }
                break;
            case R.id.earnings_information://盈利分析
                if (activity.isLogin()) {
                    Intent earningsIntent = new Intent(mContext, ProfitActivity.class);
                    startActivity(earningsIntent);
                } else {
                    Intent loginIntennt = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(loginIntennt, Activity.RESULT_FIRST_USER);
                }

                break;
            case R.id.about:
                Intent intent = new Intent(mContext, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.my_product_manager://产品管理
                if (activity.isLogin()) {
                    Intent productIntent = new Intent(mContext, ProductManagerListActivity.class);
                    startActivity(productIntent);
                } else {
                    Intent loginIntennt = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(loginIntennt, Activity.RESULT_FIRST_USER);
                }

                break;
            case R.id.dispatch_manager://抢单管理
                if (activity.isLogin()) {
                    Intent dispatchIntent = new Intent(mContext, DispatchOrderManagerActivity.class);
                    startActivity(dispatchIntent);
                } else {
                    Intent loginIntennt = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(loginIntennt, Activity.RESULT_FIRST_USER);
                }
                break;
            case R.id.setting:
                if (!activity.isLogin()) {
                    Intent loginIntent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(loginIntent, Activity.RESULT_FIRST_USER);
                } else {
                    Intent settingIntent = new Intent(mContext, SettingActivity.class);
                    startActivityForResult(settingIntent, Activity.RESULT_FIRST_USER);
                }
                break;
            case R.id.order_create:
                if (!activity.isLogin()) {
                    Intent loginIntent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(loginIntent, Activity.RESULT_FIRST_USER);
                    return;
                }
                Intent orderCreateIntent = new Intent(mContext, OrderListActivity.class);
                orderCreateIntent.putExtra(IntentFlag.Order_MASTER, OrderStatus.CREATE.name());
                startActivity(orderCreateIntent);
                break;
            case R.id.order_pay:
                if (!activity.isLogin()) {
                    Intent loginIntent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(loginIntent, Activity.RESULT_FIRST_USER);
                    return;
                }
                Intent orderPayIntent = new Intent(mContext, OrderListActivity.class);
                orderPayIntent.putExtra(IntentFlag.Order_MASTER, OrderStatus.PAY.name());
                startActivity(orderPayIntent);
                break;
            case R.id.order_dispatch:
                if (!activity.isLogin()) {
                    Intent loginIntent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(loginIntent, Activity.RESULT_FIRST_USER);
                    return;
                }
                Intent orderDispatchIntent = new Intent(mContext, OrderListActivity.class);
                orderDispatchIntent.putExtra(IntentFlag.Order_MASTER, OrderStatus.DISPATCH.name());
                startActivity(orderDispatchIntent);
                break;
            case R.id.order_success:
                if (!activity.isLogin()) {
                    Intent loginIntent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(loginIntent, Activity.RESULT_FIRST_USER);
                    return;
                }
                Intent orderSuccessIntent = new Intent(mContext, OrderListActivity.class);
                orderSuccessIntent.putExtra(IntentFlag.Order_MASTER, OrderStatus.SUCCESS.name());
                startActivity(orderSuccessIntent);
                break;
            case R.id.order_return:
                if (!activity.isLogin()) {
                    Intent loginIntent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(loginIntent, Activity.RESULT_FIRST_USER);
                    return;
                }
                Intent orderReturnIntent = new Intent(mContext, OrderReturnListActivity.class);
                startActivity(orderReturnIntent);
                break;


        }
    }

    private class MyOrderStatusTask extends AsyncTask<LoginDao, String, BaseData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (myProgressDialog == null) {
                myProgressDialog = new MyProgressDialog(getActivity());
            }
            myProgressDialog.showDialog(R.string.please_wait);
        }

        @Override
        protected BaseData doInBackground(LoginDao... params) {
            return params[0].getOrderStatus(activity.member.getMember().getId());
        }

        @Override
        protected void onPostExecute(BaseData baseData) {
            super.onPostExecute(baseData);
            myProgressDialog.dismiss();
            if (baseData != null) {
                if (baseData.isSuccess()) {
                    showOrderStatus(baseData);
                } else {
                    activity.alert(baseData.getMsg().toString());
                }
            } else {
                alert(R.string.net_error);
            }
        }
    }

    private void showOrderStatus(BaseData data) {
        LinkedTreeMap dataMap = (LinkedTreeMap) data.getData();
        for (Object key : dataMap.keySet().toArray()) {
            if (key.equals(OrderStatus.CREATE.name())) {
                int createNum = CommonUtil.stringToInt(dataMap.get(OrderStatus.CREATE.name()).toString());
                if (createNum != 0d) {
                    orderCreateNum.setVisibility(View.VISIBLE);
                    orderCreateNum.setText(String.valueOf(createNum));
                }
            } else if (key.equals(OrderStatus.PAY.name())) {
                int payNum = CommonUtil.stringToInt(dataMap.get(OrderStatus.PAY.name()).toString());
                if (payNum != 0d) {
                    orderPayNum.setVisibility(View.VISIBLE);
                    orderPayNum.setText(String.valueOf(payNum));
                }
            } else if (key.equals(OrderStatus.DISPATCH.name())) {
                int dispathNum = CommonUtil.stringToInt(dataMap.get(OrderStatus.DISPATCH.name()).toString());
                if (dispathNum != 0d) {
                    orderDispatchNum.setVisibility(View.VISIBLE);
                    orderDispatchNum.setText(String.valueOf(dispathNum));
                }
            } else if (key.equals(OrderStatus.SUCCESS.name())) {
                int successNum = CommonUtil.stringToInt(dataMap.get(OrderStatus.SUCCESS.name()).toString());
                if (successNum != 0d) {
                    orderSuccessNum.setVisibility(View.VISIBLE);
                    orderSuccessNum.setText(String.valueOf(successNum));
                }
            }
        }
    }
}
