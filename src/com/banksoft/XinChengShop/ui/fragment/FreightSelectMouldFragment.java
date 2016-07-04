package com.banksoft.XinChengShop.ui.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.FreightMouldAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.FreightSelectDao;
import com.banksoft.XinChengShop.entity.ExpressModelBO;
import com.banksoft.XinChengShop.entity.ExpressPriceVO;
import com.banksoft.XinChengShop.model.ExpressModelBOData;
import com.banksoft.XinChengShop.model.ExpressModelData;
import com.banksoft.XinChengShop.ui.PublishGoodsActivity;

import java.io.Serializable;

/**
 * Created by Robin on 2016/4/13.
 * 运费模板选择
 */
public class FreightSelectMouldFragment extends DialogFragment implements AdapterView.OnItemClickListener{
    private TextView title;
    private ListView listView;
    private ProgressBar progressBar;
    private FreightSelectDao freightSelectDao;
    private FreightMouldAdapter freightMouldAdapter;
    private String shopId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.select_goods_ctegory_list_layout, null);
        initView(view);
        if(freightSelectDao == null){
            freightSelectDao = new FreightSelectDao(getActivity().getApplicationContext());
        }
        new MyThread().execute(freightSelectDao);
        return view;
    }

    private void initView(View view) {
        title = (TextView) view.findViewById(R.id.titleText);
        listView = (ListView) view.findViewById(R.id.list_view);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        shopId = (String) getArguments().get(IntentFlag.SHOP_ID);
        listView.setOnItemClickListener(this);
        title.setText(R.string.please_select_freight_mould);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ((PublishGoodsActivity)getActivity()).select((ExpressModelBO) freightMouldAdapter.getItem(position));
        dismiss();
    }

    /**
     * 获取运费模板
     */
    private class MyThread extends AsyncTask<FreightSelectDao,String,ExpressModelBOData>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ExpressModelBOData doInBackground(FreightSelectDao... params) {
            return params[0].getExpressModelBOData(shopId);
        }

        @Override
        protected void onPostExecute(ExpressModelBOData expressModelBOData) {
            super.onPostExecute(expressModelBOData);
            if(expressModelBOData != null){
                if(expressModelBOData.isSuccess()){
                    if(freightMouldAdapter == null){
                        freightMouldAdapter = new FreightMouldAdapter(getActivity().getApplicationContext(),expressModelBOData.getData());
                    }
                    listView.setAdapter(freightMouldAdapter);
                }else{
                    Toast.makeText(getActivity().getApplicationContext(),getText(R.string.net_is_weak),Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getActivity().getApplicationContext(),getText(R.string.net_error),Toast.LENGTH_SHORT).show();
            }
        }
    }



    public interface FreightSelectListener{
        public void select(ExpressModelBO expressModelBO);
    }



}
