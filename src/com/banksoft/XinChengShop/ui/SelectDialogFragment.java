package com.banksoft.XinChengShop.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.dao.SelectDialogDao;
import com.banksoft.XinChengShop.model.base.BaseData;
import com.banksoft.XinChengShop.widget.alertview.OnItemClickListener;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by admin on 2016/7/20.
 */
public class SelectDialogFragment extends DialogFragment implements OnItemClickListener {
    private TextView title;
    private ListView listView;
    private ProgressBar progressBar;
    private String url;
    private String requestParams;
    private BaseMyAdapter baseMyAdapter;
    private LinkedList dataList;
    private SelectItemLister selectItemLister;
    private Type type;
    private String titleStr;
    private SelectDialogDao selectDialogDao;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.list_layout, null);
        initView(view);
        initData();
        initOperation();
        return view;
    }

    private void initView(View view) {
        title = (TextView) view.findViewById(R.id.titleText);
        listView = (ListView) view.findViewById(R.id.list_view);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    }

    private void initData(){
        title.setText(titleStr);

    }

    private void initOperation(){
        if(dataList == null){//加载已有数据

        }else{//请求网咯

        }

    }

    private class MyTask extends AsyncTask<SelectDialogDao,String,BaseData>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected BaseData doInBackground(SelectDialogDao... params) {
            return params[0].getSelectData(url,requestParams,type);
        }

        @Override
        protected void onPostExecute(BaseData baseData) {
            super.onPostExecute(baseData);
            if(baseData!= null){
                if(baseData.isSuccess()){
                    baseMyAdapter.dataList = (List) baseData.getData();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(),baseData.getMsg().toString(),Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getActivity().getApplicationContext(),getText(R.string.net_error),Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemClick(Object o, int position) {
        selectItemLister.select(position);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public void setBaseMyAdapter(BaseMyAdapter baseMyAdapter) {
        this.baseMyAdapter = baseMyAdapter;
    }

    public void setSelectItemLister(SelectItemLister selectItemLister) {
        this.selectItemLister = selectItemLister;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public void setDataList(LinkedList dataList) {
        this.dataList = dataList;
    }

    private interface SelectItemLister {
        public void select(int position);
    }

    ;


}
