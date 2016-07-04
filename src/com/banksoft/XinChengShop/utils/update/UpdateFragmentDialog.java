package com.banksoft.XinChengShop.utils.update;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.model.UpdateData;

/**
 * Created by Robin on 2015/1/23.
 */
public class UpdateFragmentDialog extends DialogFragment implements View.OnClickListener{
    private TextView content;
    private TextView cancel;
    private TextView ok;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.updata_dialog_layout, container);
        init(view);
        UpdateData updateData = (UpdateData) getArguments().get(IntentFlag.UPDATE_DATA);
        content.setText(updateData.getData().getValue());
        cancel.setOnClickListener(this);
        ok.setOnClickListener(this);
        return view;
    }

    private void init(View cellView) {
        content = (TextView) cellView.findViewById(R.id.content);
        cancel = (TextView) cellView.findViewById(R.id.cancel);
        ok = (TextView) cellView.findViewById(R.id.ok);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok: //确定下载显示下载界面
                dismiss();
                showDownLoadDialog();
                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }

    /**
     *显示下载界面
     */

    private void showDownLoadDialog(){
        DownloadFragmentDialog downloadFragmentDialog = new DownloadFragmentDialog();
        downloadFragmentDialog.show(getActivity().getSupportFragmentManager(),"DownloadFragmentDialog");
    }
}
