package com.banksoft.XinChengShop.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.SearchAutoCompleteAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.widget.ClearEditText;
import com.banksoft.XinChengShop.widget.XListView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Robin on 2016/3/31.
 */
public class SearchAutoCompleteActivity extends XCBaseActivity implements AdapterView.OnItemClickListener{
    private ListView listView;
    private ClearEditText searchEdit;
    private Button searchbtn;
    private SearchAutoCompleteAdapter searchAutoCompleteAdapter;
    private List dataList;
    private View headView;
    private TextView headTitle;
    private ImageView back;

    @Override
    protected void initContentView() {
        setContentView(R.layout.search_auto_complete_layout);

        // 初始化
        searchEdit = (ClearEditText) findViewById(R.id.search_edit);
        searchbtn = (Button) findViewById(R.id.search);
        listView = (ListView) findViewById(R.id.list_view);
        back = (ImageView) findViewById(R.id.title_back_button);

        // 获取搜索记录文件内容
        SharedPreferences sp = getSharedPreferences("search_history", 0);
        String history = sp.getString("history", "");

        // 用逗号分割内容返回数组
        String[] history_arr = history.split(",");


        // 保留前50条数据
        if (history_arr.length > 0) {
            dataList = new LinkedList();
            for (String his : history_arr) {
                dataList.add(his);
            }
            searchAutoCompleteAdapter = new SearchAutoCompleteAdapter(mContext, dataList);
        }
        headView = LayoutInflater.from(mContext).inflate(R.layout.search_auto_complete__item_layout,null);
        headTitle = (TextView) headView.findViewById(R.id.name);
        headTitle.setText(R.string.histroy_record);
        headTitle.setTextColor(getResources().getColor(R.color.light_gray));
        listView.addHeaderView(headView);
        listView.setOnItemClickListener(this);
        // 设置适配器
        listView.setAdapter(searchAutoCompleteAdapter);

        back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
            }
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initOperate() {
        // 设置监听事件，点击搜索写入搜索词
        searchbtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String searchName = searchEdit.getText().toString().trim();
                if(searchName.equals("")){
                    alert(R.string.search_name_no_empty);
                }else{
                    Intent intent = new Intent(mContext,ProductListActivity.class);
                    intent.putExtra(IntentFlag.NAME,searchName);
                    startActivity(intent);
                }
                save();
            }

        });
    }


    public void save() {
        // 获取搜索框信息
        String text = searchEdit.getText().toString();
        SharedPreferences mysp = getSharedPreferences("search_history", 0);
        String old_text = mysp.getString("history", "");

        // 利用StringBuilder.append新增内容，逗号便于读取内容时用逗号拆分开
        StringBuilder builder = new StringBuilder(old_text);
        builder.append(text + ",");

        // 判断搜索内容是否已经存在于历史文件，已存在则不重复添加
        if (!old_text.contains(text + ",")) {
            SharedPreferences.Editor myeditor = mysp.edit();
            myeditor.putString("history", builder.toString());
            myeditor.commit();
        } else {
            // Toast.makeText(this, text + "已存在", Toast.LENGTH_SHORT).show();
        }

    }

    //清除搜索记录
    public void cleanHistory(View v) {
        SharedPreferences sp = getSharedPreferences("search_history", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
        Toast.makeText(this, "清除成功", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String searchName = (String) searchAutoCompleteAdapter.getItem(position-1);
        Intent intent = new Intent(mContext,ProductListActivity.class);
        intent.putExtra(IntentFlag.NAME,searchName);
        startActivity(intent);
    }
}
