package com.banksoft.XinChengShop.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 14-6-13
 * Time: 上午7:51
 * To change this template use File | Settings | File Templates.
 */
public class MyProgressDialog {
    private  ProgressDialog progressDialog;
    private Context mContext;

    public MyProgressDialog(Context mContext){
       this.mContext = mContext;
    }

    public void showDialog(String msg){
        if(progressDialog == null){
           progressDialog = new ProgressDialog(mContext);
        }
        progressDialog.show();
        View cellView = LayoutInflater.from(mContext).inflate(R.layout.my_progress_dialog_layout,null);
        TextView textMsg = (TextView) cellView.findViewById(R.id.msg);
        textMsg.setText(msg);
        progressDialog.setContentView(cellView);
    }

    public void showDialog(int resId){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(mContext);
        }
        progressDialog.show();
        View cellView = LayoutInflater.from(mContext).inflate(R.layout.my_progress_dialog_layout,null);
        TextView textMsg = (TextView) cellView.findViewById(R.id.msg);
        textMsg.setText(mContext.getText(resId));
        progressDialog.setContentView(cellView);
    }


    public void dismiss() {
        if(progressDialog != null){
           progressDialog.dismiss();
        }
    }
}
