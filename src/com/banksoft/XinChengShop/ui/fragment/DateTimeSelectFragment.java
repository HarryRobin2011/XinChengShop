package com.banksoft.XinChengShop.ui.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import com.banksoft.XinChengShop.R;

/**
 * Created by Robin on 2016/8/23.
 */
public class DateTimeSelectFragment extends DialogFragment implements View.OnClickListener{

    private TextView title;
    private DatePicker datePicker;
    private TextView ok;
    private long currentTime;
    private DateTimeSelectFragment.OnSelectTimeListener onSelectTimeListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view =  LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.data_time_select_dialog_layout,null);
        initView(view);
        initData();
        initOperation();
        return view;
    }

    public void setOnSelectTimeListener(DateTimeSelectFragment.OnSelectTimeListener onSelectTimeListener) {
        this.onSelectTimeListener = onSelectTimeListener;
    }

    private void initView(View view){
        title = (TextView) view.findViewById(R.id.titleText);
        datePicker = (DatePicker) view.findViewById(R.id.time_pickker);
        ok = (TextView) view.findViewById(R.id.ok);
    }

    private void initData(){
        title.setText(R.string.please_select_take_time);
    }

    private void initOperation(){
        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ok:
                int year = datePicker.getYear();
                int month =datePicker.getMonth();
                int day = datePicker.getDayOfMonth();

                onSelectTimeListener.OnSelectTime(year+"-"+month+"-"+day);
                dismiss();
                break;
        }
    }


    public interface OnSelectTimeListener{
        public void OnSelectTime(String currentTime);
    }
}
