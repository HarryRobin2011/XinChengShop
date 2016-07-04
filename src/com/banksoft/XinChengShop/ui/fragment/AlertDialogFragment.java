package com.banksoft.XinChengShop.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.banksoft.XinChengShop.R;

/**
 * Created by harry_robin on 15/12/12.
 */
public class AlertDialogFragment extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.alert_dialog_layout,null);
    }
}
