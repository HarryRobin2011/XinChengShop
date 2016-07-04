package com.banksoft.XinChengShop.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.TimePicker;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.ui.base.XCBaseFragment;
import com.banksoft.XinChengShop.ui.takeout.TakeMenuSubmitOrderAcvitity;
import com.banksoft.XinChengShop.utils.TimeUtils;

/**
 * Created by admin on 2016/7/4.
 */
public class TimeSelectFragment extends DialogFragment implements View.OnClickListener{
    private TextView title;
    private TimePicker timePicker;
    private TextView ok;
    private long currentTime;
    private TakeMenuSubmitOrderAcvitity takeMenuSubmitOrderAcvitity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view =  LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.time_select_dialog_layout,null);
        initView(view);
        initData();
        initOperation();
        return view;
    }

    private void initView(View view){
        title = (TextView) view.findViewById(R.id.titleText);
        timePicker = (TimePicker) view.findViewById(R.id.time_pickker);
        ok = (TextView) view.findViewById(R.id.ok);
    }

    private void initData(){
        title.setText(R.string.please_select_take_time);
        timePicker.setIs24HourView(true);
        takeMenuSubmitOrderAcvitity = (TakeMenuSubmitOrderAcvitity) getActivity();
    }

    private void initOperation(){
        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ok:
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();
                takeMenuSubmitOrderAcvitity.OnSelectTime(hour+":"+minute+":00");
                dismiss();
                break;
        }
    }

    public interface OnSelectTimeListener{
        public void OnSelectTime(String currentTime);
    }
}
