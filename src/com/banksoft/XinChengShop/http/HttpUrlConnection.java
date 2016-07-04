package com.banksoft.XinChengShop.http;


import com.banksoft.XinChengShop.config.ControlUrl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 14-8-31
 * Time: 下午4:03
 * To change this template use File | Settings | File Templates.
 */
public class HttpUrlConnection {
    private final String LOG_TAG = this.getClass().getSimpleName();

    public static byte[] postFromWebByHttpUrlConnection(String postUrl,byte[] params) throws IOException {
        HttpURLConnection connection = null;
        ByteArrayOutputStream bos = null;
        URL url = new URL(ControlUrl.BASE_URL+postUrl);
        connection = (HttpURLConnection) url.openConnection();//打开链接
        connection.setConnectTimeout(20 * 1000);
        connection.setReadTimeout(20 * 1000);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestMethod("POST");
        connection.connect();

        OutputStream os = connection.getOutputStream();
        if(os!= null){
          os.write(params);
          os.close();
        }
        InputStream is = connection.getInputStream();
        bos = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len;
        while((len = is.read(data)) != -1){
          bos.write(data,0,len);
          bos.close();
        }
        return bos.toByteArray();
    }

    public static byte[] postServer(String postUrl) throws IOException{
        URL url = new URL(postUrl);
        InputStream is = null;
        ByteArrayOutputStream os = null;
        try {
            is = url.openStream();
            os = new ByteArrayOutputStream();
            int len = 0;
            byte[] buff = new byte[1024 * 8];
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
            }
            return os.toByteArray();
        } finally {
            if(os != null) os.close();
            if(is != null) is.close();
        }
    }
}
