package com.banksoft.XinChengShop.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.SexSelectAdapter;
import com.banksoft.XinChengShop.ui.mySelf.MemberInfoActivity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Robin on 2016/8/22.
 */
public class SexSelectFragment extends DialogFragment implements AdapterView.OnItemClickListener{
    private ListView listView;
    private TextView titleText;
    private ProgressBar mProgressBar;

    private SexSelectAdapter sexSelectAdapter;
    private List dataList;
    private String[] sexs = new String[]{"男","女"};
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.list_layout,null);
        listView = (ListView) view.findViewById(R.id.list_view);
        titleText = (TextView) view.findViewById(R.id.titleText);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        titleText.setText(R.string.please_select_sex);
        dataList = new LinkedList();
        dataList.clear();
        for (String sex:sexs){
            dataList.add(sex);
        }

        if(sexSelectAdapter == null){
            sexSelectAdapter = new SexSelectAdapter(getActivity().getApplicationContext(),dataList);
        }
        listView.setAdapter(sexSelectAdapter);
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ((MemberInfoActivity)getActivity()).OnSelectSex(position);
        dismiss();
    }

    public interface SelectSexListener{
        public void OnSelectSex(int position);
    }
}
