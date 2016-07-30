package com.banksoft.XinChengShop.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.type.OrderMaster;
import com.banksoft.XinChengShop.ui.*;
import com.banksoft.XinChengShop.ui.base.XCBaseFragment;
import com.banksoft.XinChengShop.ui.mySelf.MemberInfoActivity;
import com.banksoft.XinChengShop.utils.update.UpdateUtil;
import com.banksoft.XinChengShop.widget.imageView.CustomShapeImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by harry_robin on 15/11/4.
 */
public class XCMyselfFragment extends XCBaseFragment implements View.OnClickListener {
    public CustomShapeImageView login;
    private TextView account, myCollectProduct, myCollectShop,myWallet,blance,integral;
    private LinearLayout myOrder, myComment, myShop, mySendOrder, myIntegralMall,myScoreOrder,checkUpdata,settings,sellerOrder,saleCheck,myProductManager,dispatch_manager;
    private LinearLayout shop_layout;
    private Button rigjtBtn;
    private ImageLoader mImageLoader;
    private XCMainActivity activity;

    private LinearLayout searchLayout;

    private ImageView bgImage;

    private TextView title;



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
        if(requestCode == Activity.RESULT_FIRST_USER){
            if(resultCode == Activity.RESULT_OK){
                activity.isLogin();
                setMySelf();
            }
        }
    }

    private void setMySelf(){
        if(activity.isLogin()){
            account.setText(activity.member.getMember().getAccount());
            blance.setText(activity.member.getMember().getBalance()+"");
            integral.setText(activity.member.getMember().getMemberPoint() + "");
           String imageUrl = activity.member.getMember().getImageFile();

            if(imageUrl == null || imageUrl.equals("")){
                login.setImageResource(R.drawable.default_head_image);
            }else{
                mImageLoader.displayImage(ControlUrl.BASE_URL+activity.member.getMember().getImageFile().split("\\|")[0],login, XCApplication.options);
            }
            if(activity.member.getShop() != null && activity.member.getShop().isAuditStatus()){// 开店了
                if(activity.member.getShop().isStatus()){//店铺开启了

                }else{//店铺未开启

                }
                myShop.setVisibility(View.GONE);
                shop_layout.setVisibility(View.VISIBLE);//开店功能
            }else{//没有开店了
                myShop.setVisibility(View.VISIBLE);
                shop_layout.setVisibility(View.GONE);
            }


        }else{
            account.setText("");
            blance.setText(""+0f);
            integral.setText("" + 0f);
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
    }

    @Override
    public void initData() {
         title.setText(R.string.my_self);
         title.setTextColor(getResources().getColor(R.color.white));
//.leftBtn.setVisibility(View.GONE);
         searchLayout.setVisibility(View.GONE);
         bgImage.setBackgroundResource(R.color.bg_red);
         setMySelf();
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
                if(activity.isLogin()){
                    //跳转详情页
                    Intent infoIntent = new Intent(mContext, MemberInfoActivity.class);
                    startActivityForResult(infoIntent, Activity.RESULT_FIRST_USER);
                }else{
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                }

                break;
            case R.id.my_collect_product:
                if(activity.isLogin()){
                    Intent intent = new Intent(mContext, CollectActivity.class);
                    intent.putExtra(IntentFlag.INDEX,0);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                }else{
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                }

                break;
            case R.id.my_collect_shop:
                if(activity.isLogin()){
                    Intent intent = new Intent(mContext, CollectActivity.class);
                    intent.putExtra(IntentFlag.INDEX,1);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                }else{
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                }
                break;
            case R.id.my_order:
                if(activity.isLogin()){
                    Intent intent = new Intent(mContext, OrderListActivity.class);
                    intent.putExtra(IntentFlag.Order_MASTER, OrderMaster.SALE.name());
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                }else{
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                }
                break;
            case R.id.my_wallet:
                if(activity.isLogin()){
                    //跳转详情页
                    Intent intent = new Intent(mContext, WalletManagerActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                }
                break;
            case R.id.my_comment:
                if(activity.isLogin()){
                    //跳转评论
                    Intent commentIntent = new Intent(mContext,CommentActivity.class);
                    startActivity(commentIntent);
                }else{
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                }
                break;
            case R.id.my_shop:
                if(activity.isLogin()){//申请店铺
                    Intent intent = new Intent(mContext,ApplyOpenShopActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                }
                break;
            case R.id.my_send_order:
                if(activity.isLogin()){
                    //跳转详情页
                }else{
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                }
                break;
            case R.id.balance:
                if(activity.isLogin()){
                    //跳转详情页
                }else{
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                }
                break;
            case R.id.integral:
                if(activity.isLogin()){
                    //跳转详情页
                }else{
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                }
                break;
            case R.id.my_integral_mall:

                break;
            case R.id.my_score_order://我的积分订单
                if(activity.isLogin()){
                    Intent intent = new Intent(mContext,ScoreOrderListActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                }
                break;

            case R.id.check_update:
                UpdateUtil updateUtil = new UpdateUtil(getActivity(),true);
                updateUtil.isUpdate();
                break;

            case R.id.seller_order_layout:// 卖家订单
                if(activity.isLogin()){
                    Intent sellerIntent = new Intent(mContext, OrderListActivity.class);
                    sellerIntent.putExtra(IntentFlag.Order_MASTER, OrderMaster.SELLER.name());
                    startActivityForResult(sellerIntent, Activity.RESULT_FIRST_USER);
                }else{
                    Intent loginIntennt = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(loginIntennt, Activity.RESULT_FIRST_USER);
                }

                break;
            case R.id.consumption_layout://消费验证
                if(activity.isLogin()){
                    Intent checkInten = new Intent(mContext,CheckCodeActivity.class);
                    startActivity(checkInten);
                }else{
                    Intent loginIntennt = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(loginIntennt, Activity.RESULT_FIRST_USER);
                }
                break;
            case R.id.earnings_information://盈利分析
                if(activity.isLogin()){
                    Intent earningsIntent = new Intent(mContext,ProfitActivity.class);
                    startActivity(earningsIntent);
                }else{
                    Intent loginIntennt = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(loginIntennt, Activity.RESULT_FIRST_USER);
                }

                break;
            case R.id.about:
                Intent intent = new Intent(mContext, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.my_product_manager://产品管理
                if(activity.isLogin()){
                    Intent productIntent = new Intent(mContext,ProductManagerListActivity.class);
                    startActivity(productIntent);
                }else{
                    Intent loginIntennt = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(loginIntennt, Activity.RESULT_FIRST_USER);
                }

                break;
            case R.id.dispatch_manager://抢单管理
                if(activity.isLogin()){
                    Intent dispatchIntent = new Intent(mContext,DispatchOrderManagerActivity.class);
                    startActivity(dispatchIntent);
                }else{
                    Intent loginIntennt = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(loginIntennt, Activity.RESULT_FIRST_USER);
                }
                break;
            case R.id.setting:
                if(!activity.isLogin()){
                    Intent loginIntent = new Intent(mContext,LoginActivity.class);
                    startActivityForResult(loginIntent,Activity.RESULT_FIRST_USER);
                }else{
                    Intent settingIntent = new Intent(mContext,SettingActivity.class);
                    startActivityForResult(settingIntent,Activity.RESULT_FIRST_USER);
                }
                break;


        }
    }

}
