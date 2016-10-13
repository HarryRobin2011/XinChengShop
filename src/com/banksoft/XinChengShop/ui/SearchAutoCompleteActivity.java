package com.banksoft.XinChengShop.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.SearchAutoCompleteAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.type.ShopType;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.utils.CommonUtil;
import com.banksoft.XinChengShop.utils.PopupWindowUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Robin on 2016/3/31.
 */
public class SearchAutoCompleteActivity extends XCBaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView listView;
    private EditText searchEdit;
    private TextView searchbtn;
    private SearchAutoCompleteAdapter searchAutoCompleteAdapter;
    private List dataList;
    private View headView;
    private View footerView;
    private TextView headTitle;
    private TextView footerTitle;
    private ImageView back;
    private Button searchType;
    private SearchType selectType = SearchType.PRODUCT;// 搜索类型
    private PopupWindowUtil popupWindowUtil;
    private boolean editFlag = false;

    @Override
    protected void initContentView() {
        setContentView(R.layout.search_auto_complete_layout);
        //    mHandler.sendEmptyMessageDelayed(0,500);
    }

    /**
     * 显示ListView
     */
    private void showListView() {
        // 获取搜索记录文件内容
        SharedPreferences sp = getSharedPreferences("search_history", 0);
        String history = sp.getString("history", "|");

        // 用逗号分割内容返回数组
        String[] history_arr = history.split("\\|");


        // 保留前50条数据
        if (history_arr.length > 0) {
            dataList = new LinkedList();
            for (String his : history_arr) {
                dataList.add(his);
            }
            searchAutoCompleteAdapter = new SearchAutoCompleteAdapter(mContext, dataList);


            listView.addHeaderView(headView);
            listView.addFooterView(footerView);
            listView.setOnItemClickListener(this);
            // 设置适配器
            listView.setAdapter(searchAutoCompleteAdapter);
        }
    }

    @Override
    protected void initView() {
        searchEdit = (EditText) findViewById(R.id.search_edit);
        searchbtn = (TextView) findViewById(R.id.search);
        listView = (ListView) findViewById(R.id.list_view);
        back = (ImageView) findViewById(R.id.title_back_button);
        searchType = (Button) findViewById(R.id.search_type);

        headView = LayoutInflater.from(mContext).inflate(R.layout.search_auto_complete__item_layout, null);
        headTitle = (TextView) headView.findViewById(R.id.name);
        headTitle.setText(R.string.histroy_record);
        headTitle.setTextColor(getResources().getColor(R.color.text_black));

        footerView = LayoutInflater.from(mContext).inflate(R.layout.search_auto_complete__item_foot_layout, null);
        footerTitle = (TextView) footerView.findViewById(R.id.name);
        footerTitle.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.animation = false;
        showListView();
    }

    @Override
    protected void initOperate() {

        // 设置监听事件，点击搜索写入搜索词
        searchbtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editFlag){
                    CommonUtil.operationKeyboard(getApplicationContext());
                }
                finish();
            }

        });
        searchEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFlag = true;
            }
        });
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // TODO Auto-generated method stub
                    String searchName = searchEdit.getText().toString().trim();
                    if (searchName.equals("")) {
                        alert(R.string.search_name_no_empty);
                    } else {
                        if (SearchType.PRODUCT == selectType) {
                            Intent intent = new Intent(mContext, ProductListActivity.class);
                            intent.putExtra(IntentFlag.NAME, searchName);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(mContext, ShopListActivity.class);
                            intent.putExtra(IntentFlag.NAME,searchName);
                            intent.putExtra(IntentFlag.TITLE,"店铺");
                            intent.putExtra(IntentFlag.SHOP_TYPE, ShopType.SHOP_SALE.toString());
                            startActivity(intent);
                        }

                    }
                    save();
                    return true;
                }
                return false;
            }
        });
        searchType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindowUtil != null && popupWindowUtil.isShowing()) {
                    popupWindowUtil.dismiss();
                } else {
                    showPopuMenu();
                }
            }
        });
    }

    private void showPopuMenu() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.search_auto_type_layout, null);
        RadioGroup group = (RadioGroup) view.findViewById(R.id.search_type);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.product:
                        popupWindowUtil.dismiss();
                        searchEdit.setHint(R.string.please_input_product_name);
                        selectType = SearchType.PRODUCT;
                        break;
                    case R.id.shop:
                        popupWindowUtil.dismiss();
                        searchEdit.setHint(R.string.please_input_shop_name);
                        selectType = SearchType.SHOP;
                        break;
                }
                searchType.setText(selectType.name);
            }
        });
        popupWindowUtil = new PopupWindowUtil(SearchAutoCompleteActivity.this, view, searchType);
        popupWindowUtil.showPopuWindowMenuDropDown();
    }


    public void save() {
        // 获取搜索框信息
        String text = searchEdit.getText().toString();
        SharedPreferences mysp = getSharedPreferences("search_history", 0);
        String old_text = mysp.getString("history", "");

        // 利用StringBuilder.append新增内容，逗号便于读取内容时用逗号拆分开
        StringBuilder builder = new StringBuilder(old_text);
        builder.insert(0, text + "|");

        // 判断搜索内容是否已经存在于历史文件，已存在则不重复添加
        if (!old_text.contains(text + "|")) {
            SharedPreferences.Editor myeditor = mysp.edit();
            myeditor.putString("history", builder.toString());
            myeditor.commit();
        } else {
            // Toast.makeText(this, text + "已存在", Toast.LENGTH_SHORT).show();
        }

    }

    //清除搜索记录
    public void cleanHistory() {
        SharedPreferences sp = getSharedPreferences("search_history", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
        Toast.makeText(this, "清除成功", Toast.LENGTH_SHORT).show();
        listView.removeHeaderView(headView);
        listView.removeFooterView(footerView);
        searchAutoCompleteAdapter.dataList.clear();
        searchAutoCompleteAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String searchName = (String) searchAutoCompleteAdapter.getItem(position - 1);
        if (SearchType.PRODUCT == selectType) {
            Intent intent = new Intent(mContext, ProductListActivity.class);
            intent.putExtra(IntentFlag.NAME, searchName);
            startActivity(intent);
        } else {
            Intent intent = new Intent(mContext, ShopListActivity.class);
            intent.putExtra(IntentFlag.NAME,searchName);
            intent.putExtra(IntentFlag.TITLE,"店铺");
            intent.putExtra(IntentFlag.SHOP_TYPE, ShopType.SHOP_SALE.toString());
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.name:
                cleanHistory();
                break;
            case R.id.cancel:
                finish();
                break;
        }
    }

    private enum SearchType {
        PRODUCT("商品"),
        SHOP("店铺");
        private String name;

        private SearchType(String name) {
            this.name = name;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (popupWindowUtil != null && popupWindowUtil.isShowing()) {
            popupWindowUtil.dismiss();
        }
        if(editFlag){
           CommonUtil.operationKeyboard(getApplicationContext());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //  CommonUtil.operationKeyboard(getApplicationContext());
    }
}
