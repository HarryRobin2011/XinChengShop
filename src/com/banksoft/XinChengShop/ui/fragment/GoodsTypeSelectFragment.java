package com.banksoft.XinChengShop.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.ui.PublishGoodsActivity;

/**
 * Created by Robin on 2016/4/13.
 */
public class GoodsTypeSelectFragment extends DialogFragment implements AdapterView.OnItemClickListener {
    private TextView title;
    private ListView listView;
    private ProgressBar progressBar;
    private String[] datas = new String[]{"普通商品", "清仓甩卖"};
    private ArrayAdapter goodsAdapters;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.select_goods_ctegory_list_layout, null);
        goodsAdapters = new ArrayAdapter(getActivity().getApplicationContext(), R.layout.select_express_mould_list_item_layout, R.id.name, datas);
        initView(view);
        return view;
    }

    private void initView(View view) {
        title = (TextView) view.findViewById(R.id.titleText);
        listView = (ListView) view.findViewById(R.id.list_view);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        listView.setAdapter(goodsAdapters);
        title.setText(R.string.good_type);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ((PublishGoodsActivity)getActivity()).select(position);
        dismiss();
    }

    public interface GoodsTypeSelectListener{
        public void select(int index);
    }
}
