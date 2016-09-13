package com.banksoft.XinChengShop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.fragment.ProductListFragment;
import com.banksoft.XinChengShop.widget.ClearEditText;
import com.banksoft.XinChengShop.widget.MyPopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harry_robin on 15/11/25.
 */
public class ProductListActivity extends XCBaseActivity implements View.OnClickListener,MyPopupWindow.ItemPopuwindows{
    private ClearEditText searchEdit;
    private String titleName;
    private ProductListFragment productListFragment;

    private String active;//产品类型0 普通产品 1 清仓甩卖 2团购产品
    private String special;//是否特产
    private String name;//产品名称
    private String shopServerType;// 店铺类型
    private String typeId;// 分类
    private boolean recommend;// 是否推荐
    private static final String[] titles_arr = {"综合排序","人气最高","销量优先"};
    private static final String[] child_titles_arr = {"综合排序","价格从高到低","价格从低到高","信用排序"};

    private ListView filterListView;
    private MyPopupWindow myPopupWindow;
    private TextView comprehensive,sentiment,sales_preferred;
    private int selectPosition = 0;
    private ImageView comprehensiveImage;
    private ImageView back;


    @Override
    protected void initContentView() {
        setContentView(R.layout.product_content_layout);
    }

    @Override
    protected void initView() {
        comprehensive = (TextView) findViewById(R.id.comprehensive);// 综合
        sentiment = (TextView) findViewById(R.id.sentiment);//人气
        sales_preferred = (TextView) findViewById(R.id.sales_preferred);//销量
        comprehensiveImage = (ImageView) findViewById(R.id.comprehensive_image);
        searchEdit = (ClearEditText) findViewById(R.id.search_edit);

        back = (ImageView) findViewById(R.id.title_back_button);
    }

    /**
     * Recommend  是否推荐
     * typeId: 分类id
     Name:商品名称模糊检索
     shopId:店铺id
     Special:是否特产
     Active:产品类型 0 普通产品 1 清仓甩卖 2团购产品
     shopServerType:店铺类型SHOP_SERVER, //美食外卖类SHOP_LOCAL, //本地服务类SHOP_SALE;  //商城类
     */
    @Override
    protected void initData() {
        typeId = getIntent().getExtras().getString(IntentFlag.TYPE_ID, "");//分类
        active = getIntent().getExtras().getString(IntentFlag.ACTIVE, "");
        special = getIntent().getExtras().getString(IntentFlag.SPECIAL, "");
        name = getIntent().getExtras().getString(IntentFlag.NAME, "");
        shopServerType = getIntent().getExtras().getString(IntentFlag.SHOP_SERVER_TYPE, "");
        titleName = getIntent().getStringExtra(IntentFlag.TITLE);
        recommend = getIntent().getBooleanExtra(IntentFlag.RECOMMEND,true);

        searchEdit.setText(titleName);
        searchEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,SearchAutoCompleteActivity.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static List createSimpleListView(String[] dataArrays) {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < dataArrays.length; i++) {
            data.add( dataArrays[i]);
        }
        return data;
    }

    @Override
    protected void initOperate() {
        if (productListFragment == null) {
            productListFragment = new ProductListFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(IntentFlag.TYPE_ID, typeId);
        bundle.putString(IntentFlag.ACTIVE, active);
        bundle.putString(IntentFlag.SPECIAL, special);
        bundle.putString(IntentFlag.NAME, name);
        bundle.putString(IntentFlag.SHOP_SERVER_TYPE, shopServerType);
        bundle.putBoolean(IntentFlag.RECOMMEND,recommend); // 是否推荐
        productListFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, productListFragment).commit();
        comprehensive.setOnClickListener(this);
        sentiment.setOnClickListener(this);
        sales_preferred.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comprehensive:
                myPopupWindow = new MyPopupWindow();
                myPopupWindow.selectPosition = selectPosition;
                MyPopupWindow.showOrderPopupWindows(this,createSimpleListView(child_titles_arr),comprehensive);
                comprehensiveImage.setBackgroundResource(R.drawable.ic_arrow_up);
                comprehensive.setTextColor(mContext.getResources().getColor(R.color.red_normal));
                sentiment.setTextColor(mContext.getResources().getColor(R.color.text_black_light));
                sales_preferred.setTextColor(mContext.getResources().getColor(R.color.text_black_light));
                break;
            case R.id.sentiment://人气
                comprehensiveImage.setBackgroundResource(R.drawable.ic_arrow_up);
                comprehensive.setTextColor(mContext.getResources().getColor(R.color.text_black_light));
                sentiment.setTextColor(mContext.getResources().getColor(R.color.red_normal));
                sales_preferred.setTextColor(mContext.getResources().getColor(R.color.text_black_light));
                productListFragment.orderCol = "saleOrder";
                productListFragment.orderType = "asc";
                productListFragment.onRefresh();
                break;
            case R.id.sales_preferred://销量
                comprehensiveImage.setBackgroundResource(R.drawable.ic_arrow_up);
                comprehensive.setTextColor(mContext.getResources().getColor(R.color.text_black_light));
                sentiment.setTextColor(mContext.getResources().getColor(R.color.text_black_light));
                sales_preferred.setTextColor(mContext.getResources().getColor(R.color.red_normal));
                productListFragment.orderCol = "collectionOrder";
                productListFragment.orderType = "asc";
                productListFragment.onRefresh();
                break;
        }
    }

    /**
     *
     * @param position
     */
    @Override
    public void itemOnClickLinster(int position) {
        comprehensive.setText(child_titles_arr[position]);
        selectPosition = position;
        comprehensiveImage.setBackgroundResource(R.drawable.ic_arrow_down);
        switch (position){
            case 0://综合排序
                productListFragment.orderCol = "";
                break;
            case 1://价格从低到高
                productListFragment.orderCol = "priceOrder";
                productListFragment.orderType = "desc";
                break;
            case 2://价格从高到低
                productListFragment.orderCol = "priceOrder";
                productListFragment.orderType = "asc";
                break;
            case 3://信用排序
                productListFragment.orderCol = "scoreOrder";
                productListFragment.orderType = "asc";
                break;
        }
        productListFragment.onRefresh();
    }


}
