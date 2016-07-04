package com.banksoft.XinChengShop.utils;


import com.banksoft.XinChengShop.config.ControlUrl;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 13-8-14
 * Time: 下午3:01
 * To change this template use File | Settings | File Templates.
 */
public class LoadSignImage {
    private String recordId;
    public void setId(String id) {
        this.recordId = id;
    }

    public String loadImage(String uploadFileActionUrl, File upload, String type) throws Exception {
        String response;
        String urlP = ControlUrl.BASE_URL+uploadFileActionUrl;
      //  String urlP =ControlUrl.BASE_URL+ControlUrl.SUBMIT_IMAGE_URL;
        String boundary = String.valueOf(System.currentTimeMillis());       //标识代码
        String twoHyphens = "--";                  //分割线 ,连接线
        String lineEnd = "\r\n";                   //换行符，跳转到下一行开头
        URL url = new URL(urlP);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");     //POST 方式提交
        conn.setRequestProperty("Connection", "Keep-Alive");       //保持连接
        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);      //设置数据类型

        //开始发送数
        OutputStream os = conn.getOutputStream();

        String temp;
        byte[] bytes = new byte[2048];
        int len;

        //file
        temp = twoHyphens + boundary + lineEnd;    // -----boundary\r\n 开始标识

        os.write(temp.getBytes());
        temp = "Content-Disposition: form-data; name=\"upload\";filename=\"" + upload.getName() + "\"" + lineEnd;
        os.write(temp.getBytes());           // Content-Disposition: form-data; name="imgFile"; filename="fileName"\r\n 开始标识
        temp = "Content-Type: " + type + lineEnd;
        os.write(temp.getBytes());       // Content-Type : image/png\r\n 标识 该区域内容类型
        os.write(lineEnd.getBytes());     //换行

        // write image start
        FileInputStream fis = new FileInputStream(upload);

        while ((len = fis.read(bytes)) != -1) {
            os.write(bytes, 0, len);
        }
        fis.close();
        // end write image
        os.write(lineEnd.getBytes());    //换行

        temp = twoHyphens + boundary + lineEnd;     // -----boundary\r\n 开始标识
        os.write(temp.getBytes());
        temp = "Content-Disposition: form-data; name=\"recordId\"" + lineEnd;     // Content-Disposition: form-data; name="name"\r\n 开始标识
        os.write(temp.getBytes());
        temp = "Content-Type: text/plain; charset=UTF-8" + lineEnd;     // Content-Type: text/plain; charset=UTF-8\r\n 标识 该区域内容类型
        os.write(temp.getBytes());
        os.write(lineEnd.getBytes());
        //start write text
        os.write(recordId == null ? new byte[0] : recordId.getBytes("UTF-8"));
        // end write text
        os.write(lineEnd.getBytes());   //换行

        temp = twoHyphens + boundary + twoHyphens + lineEnd;     //结束数据标识
        os.write(temp.getBytes());
        os.flush();
        os.close();

        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            is = conn.getInputStream();
            baos = new ByteArrayOutputStream();
            while ((len = is.read(bytes)) != -1) {
                baos.write(bytes, 0, len);
            }
            response = new String(baos.toByteArray(), "UTF-8");
        } finally {
            if (baos != null) baos.close();
            if (is != null) is.close();
        }
        return response;
    }


//    public static void main(String[] args) throws Exception {
//        for(int i = 0; i < 50 ; i++){
//            LoadImage loadImage = new LoadImage();
//            loadImage.loadImage("http://d.sdtyjd.com/bd/upload", new File("D:\\01.jpg"), "jpg");
//        }
//    }
}
