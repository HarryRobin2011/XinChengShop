package com.banksoft.XinChengShop.utils.update;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.UpdateDao;
import com.banksoft.XinChengShop.http.HttpUtils;
import com.banksoft.XinChengShop.model.UpdateData;

/**
 * Created by Robin on 2015/1/23.
 */
public class UpdateUtil {
    private final String TAG = "UpdateUtil";

    private Context mContext;
    private FragmentActivity mActivity;
    private UpdateDao updateDao;
    private boolean animation;//是否显示进度条
    private ProgressDialog progressDialog;


    public UpdateUtil(FragmentActivity activity,boolean animation){
        this.mContext = activity.getApplicationContext();
        this.mActivity = activity;
        this.animation = animation;
    }

    /**
     * 判断是否更新
     */
    public void isUpdate(){
       if(HttpUtils.isNetworkAvailable(mContext)){
          if(updateDao == null){
             updateDao = new UpdateDao(mContext);
          }
          new UpdateVersion().execute(updateDao);
       }else{
           Toast.makeText(mContext,mContext.getText(R.string.netWork_error),Toast.LENGTH_SHORT).show();
       }
    }

    /**
     * 获取软件版本号
     * @return
     */
    private int getVersionCode(){
        int versionCode = 0;
        try {
            versionCode = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 显示提示更新
     */
    private void showUpdateDialog(UpdateData updateData){
       UpdateFragmentDialog updateFragmentDialog = new UpdateFragmentDialog();
       Bundle bundle = new Bundle();
       bundle.putSerializable(IntentFlag.UPDATE_DATA,updateData);
       updateFragmentDialog.setArguments(bundle);
       updateFragmentDialog.show(mActivity.getSupportFragmentManager(),"UpdateFragmentDialog");
    }



    private class UpdateVersion extends AsyncTask<UpdateDao,String,UpdateData>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(animation){   //是否需要显示进度条
                progressDialog = ProgressDialog.show(mActivity,mContext.getText(R.string.prompt),mContext.getText(R.string.please_wait));
            }
        }

        @Override
        protected UpdateData doInBackground(UpdateDao... params) {
            return params[0].getUpdateData();
        }

        @Override
        protected void onPostExecute(UpdateData updateData) {
            super.onPostExecute(updateData);
            if(animation){
              progressDialog.dismiss();
            }
            if(updateData != null){
               if(updateData.isSuccess()){
                  if (updateData.getData().getVersionCode() > getVersionCode()){//大于更新
                      if(updateData.getData().isForce()){  //更新
                          showUpdateDialog(updateData);//显示更新界面
                         // ((WelcomeActivity)mActivity).loaded();
                      }else{  //暂停维护
                           Toast.makeText(mContext,mContext.getText(R.string.upgrade_maintenance),Toast.LENGTH_LONG).show();
                      }

                  }else{
                      if(animation){
                        Toast.makeText(mContext,mContext.getText(R.string.already_best_new_version),Toast.LENGTH_SHORT).show();
                      }
                  }
               }else{
                   Log.i(TAG,"update is fail");
               }
            }else{
                Log.i(TAG,"update is null");
            }
        }
    }
}
