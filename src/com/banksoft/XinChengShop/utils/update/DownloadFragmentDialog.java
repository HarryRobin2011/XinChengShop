package com.banksoft.XinChengShop.utils.update;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.ControlUrl;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.NumberFormat;

/**
 * Created by Robin on 2015/1/23.
 */
public class DownloadFragmentDialog extends DialogFragment implements View.OnClickListener {
    private ProgressBar mProgressBar;
    private TextView cancel;
    private TextView size;
    private TextView runing;
    private Context mContext;

    private DownloadThread asyncTask;
    private int progress;
    private String apkPath;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
        apkPath = Environment.getExternalStorageDirectory() + File.separator + getSaveFileName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);//点击屏幕消失
        setCancelable(false);
        View view = LayoutInflater.from(mContext).inflate(R.layout.down_load_layout, null);
        init(view);
        cancel.setOnClickListener(this);

        asyncTask = new DownloadThread();
        asyncTask.execute();
        return view;
    }

    private void init(View cellView) {
        cancel = (TextView) cellView.findViewById(R.id.cancel);
        size = (TextView) cellView.findViewById(R.id.update_current_size);
        runing = (TextView) cellView.findViewById(R.id.update_current_runing_size);
        mProgressBar = (ProgressBar) cellView.findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                asyncTask.cancel(true);
                dismiss();
                break;
        }
    }

    /**
     * 下载异步线程
     */
    private class DownloadThread extends AsyncTask<String,Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            runing.setText(R.string.runing_connection);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            File pathFile = new File(apkPath);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            String fileName = getSaveFileName() + ".apk";
            File file = new File(apkPath + File.separator + fileName);
            if (file.exists()) {
                file.delete();
            }
            URL url = null;
            try {
                url = new URL(ControlUrl.NEW_VERSION_DOWNLOAD_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection .setRequestProperty("Accept-Encoding", "identity");
                connection.setRequestMethod("GET");
                int length = connection.getContentLength();
                InputStream is = null;
                FileOutputStream fos = null;
                int current = 0;
                int len;
                try {
                    is = connection.getInputStream();
                    fos = new FileOutputStream(file);
                    byte[] buffer = new byte[1024 * 8];
                    while ((len = is.read(buffer)) != -1) {
                        if (asyncTask.isCancelled()) {//取消下载
                            break;
                        }
                        fos.write(buffer, 0, len);
                        current += len;
                        publishProgress(current, length);
                    }
                } finally {
                    if (fos != null) {
                        fos.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                }
                return true;//下载完成
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        // 0表示当前字节，1表示总字节
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            progress = (int) ((Float.valueOf(values[0])/ Float.valueOf(values[1])) * 100);
            mProgressBar.setProgress(progress);
            runing.setText(byteToMB(values[0])+"MB");
            //显示文件大小
            size.setText("/"+byteToMB(values[1])+"MB");
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean.booleanValue()){//下载完成
                dismiss();
               installApk();//安装APK文件
            }
        }

        //取消下载
        @Override
        protected void onCancelled(Boolean aBoolean) {
            super.onCancelled(aBoolean);
        }
    }

    /**
     * 1MB等于1024kb 1kb等于1024字节
     * @return
     */
    private String byteToMB(int length){
       NumberFormat numberFormat = NumberFormat.getInstance();
       numberFormat.setMaximumFractionDigits(2);
       return numberFormat.format(Float.valueOf(length)/1024f/1024f);
    }

    /**
     * 获取保存APK文件名
     */
    public String getSaveFileName(){
        String pageName = mContext.getPackageName();
        String[] array = pageName.split("\\.");
        String saveName = array[array.length - 1]; //取道最后一个
        return saveName;
    }

    /**
     * 安装APK文件
     */
    private void installApk() {
        File apkfile = new File(apkPath+File.separator+getSaveFileName()+".apk");
        if (!apkfile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(i);
    }


}
