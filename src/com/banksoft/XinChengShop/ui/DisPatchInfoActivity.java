package com.banksoft.XinChengShop.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.entity.OrderProductVO;
import com.banksoft.XinChengShop.entity.OrderVO;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

/**
 * Created by Robin on 2016/10/18.
 */
public class DisPatchInfoActivity extends XCBaseActivity implements View.OnClickListener{
    private TextView commission;//佣金
    private TextView memberShopName, telPhoneShop, addressShop, memberName, telPhone, address;
    private LinearLayout productLayout;
    private OrderVO currentOrderVo;
    private TextView title, back;

    @Override
    protected void initContentView() {
        setContentView(R.layout.dispatch_info_layout);
    }

    @Override
    protected void initView() {
        commission = (TextView) findViewById(R.id.commission);
        memberShopName = (TextView) findViewById(R.id.member_shop_name);
        telPhoneShop = (TextView) findViewById(R.id.telPhone_shop);
        addressShop = (TextView) findViewById(R.id.address_shop);
        memberName = (TextView) findViewById(R.id.member_name);
        telPhone = (TextView) findViewById(R.id.telPhone);
        address = (TextView) findViewById(R.id.address);
        productLayout = (LinearLayout) findViewById(R.id.content_layout);
        title = (TextView) findViewById(R.id.titleText);
        back = (TextView) findViewById(R.id.title_back_button);
    }

    @Override
    protected void initData() {
        currentOrderVo = (OrderVO) getIntent().getSerializableExtra(IntentFlag.DATA);
        back.setVisibility(View.VISIBLE);
        title.setText(R.string.order_info_title);
    }

    @Override
    protected void initOperate() {
        back.setOnClickListener(this);
        memberShopName.setText(currentOrderVo.getShopAccount());
        if(currentOrderVo.getShopTelephone() != null){
            if(currentOrderVo.getShopTelephone().split("/").length > 0){
                telPhoneShop.setText(currentOrderVo.getShopTelephone().split("/")[0]);
            }else{
                telPhoneShop.setText("");
            }
        }
        addressShop.setText(currentOrderVo.getShopAddress());
        memberName.setText(currentOrderVo.getUserName());
        telPhone.setText(currentOrderVo.getTelephone());
        address.setText(currentOrderVo.getProvince()+" "+currentOrderVo.getCity()+" "+currentOrderVo.getCounty()+currentOrderVo.getAddress());
        setProductView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back_button:
                finish();
                break;
        }
    }

    private void setProductView(){
        productLayout.removeAllViews();
        for (OrderProductVO productVO:currentOrderVo.getList()){
            View view = LayoutInflater.from(mContext).inflate(R.layout.product_list_order_item_layout,null);
            TextView name = (TextView) view.findViewById(R.id.product_name);
            TextView price = (TextView) view.findViewById(R.id.real_price);
            TextView num = (TextView) view.findViewById(R.id.sale_num);

            name.setText(productVO.getProductName());
            price.setText(productVO.getPrice()+"元");
            num.setText(""+productVO.getNum());
            productLayout.addView(view);
        }
    }
}
