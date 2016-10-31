package com.banksoft.XinChengShop.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.ImageView;
import com.banksoft.XinChengShop.R;
import com.bumptech.glide.Glide;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Robin on 2015/2/5.
 */
public class ImageUtil {

    /**
     * 获取本机所有图片
     */
    public static HashMap<String, LinkedList<String>> findImage(Context mContext) {
        HashMap<String, LinkedList<String>> dataMap = new HashMap<String, LinkedList<String>>();
        LinkedList<String> mAllImgs = new LinkedList<String>();
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = mContext
                .getContentResolver();

        // 只查询jpeg和png的图片

        Cursor mCursor = mContentResolver.query(mImageUri, null,
                MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png"},
                MediaStore.Images.Media.DATE_MODIFIED);

        while (mCursor.moveToNext()) {
            // 获取图片的路径
            String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
            // 获取该图片的父路径名
            File pa_file = new File(path).getParentFile();
            String parentName = pa_file.getAbsolutePath();
            if (mAllImgs.size() < 281) {
                mAllImgs.add(path);
            }
            dataMap.put("all", mAllImgs);
            // 根据父路径名将图片放入到mGruopMap中
            if (!dataMap.containsKey(parentName)) {
                LinkedList<String> chileList = new LinkedList<String>();
                chileList.add(path);
                dataMap.put(parentName, chileList);
            } else {
                dataMap.get(parentName).add(path);
            }
        }
        mCursor.close();
        return dataMap;
    }

    public static HashMap<Integer, String> compressImage(Context context, HashMap<Integer, String> imageMap) {
        HashMap<Integer, String> tagHashMap = new HashMap<Integer, String>();
        String imageTempName = "tempImage";
        String path = context.getPackageName();
        String sdPath = context.getExternalCacheDir().getAbsolutePath() + File.separator + path + File.separator;
        FileOutputStream outputStream = null;
        File file = new File(sdPath, imageTempName);
        if (!file.exists()) {
            file.mkdirs();
        }

        for (int i = 0; i < imageMap.keySet().size(); i++) {
            try {
                String imagePath = imageMap.get(imageMap.keySet().toArray()[i]).toString();
                String imageType = imagePath.substring(imagePath.lastIndexOf(".") + 1, imagePath.length());
                String imageName = imagePath.substring(imagePath.lastIndexOf("/") + 1, imagePath.length());

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                Bitmap image = BitmapFactory.decodeFile(imagePath, options);
                File outFile = new File(file.getAbsolutePath() + File.separator, imageName);
                outputStream = new FileOutputStream(outFile);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int quality = 100;
                Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
                if ("jpg".equals(imageType)) {
                    format = Bitmap.CompressFormat.JPEG;
                } else if ("png".equals(imageType)) {
                    format = Bitmap.CompressFormat.PNG;
                }
                image.compress(format,quality,baos);
                while (baos.toByteArray().length/1024 > 150 && quality > 30){// 图片大于100K
                    baos.reset();
                    if(quality > 30){
                        quality -=4;
                    }
                    image.compress(format,quality,baos);
                }
                outputStream.write(baos.toByteArray());
                tagHashMap.put(i, outFile.getAbsolutePath());
                if (image != null && !image.isRecycled()) {
                    image.recycle();
                }
                outputStream.flush();
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (OutOfMemoryError e){
                return null;
            }

        }
        return tagHashMap;
    }

    public static void cropImageUri(Activity activity, Uri uri, int outputX, int outputY, int requestCode){
        int endIndex = uri.getPath().lastIndexOf("/");
        String tempPath = uri.getPath().substring(0,endIndex+1)+"temp";
        String tempFileName = uri.getPath().substring(endIndex + 1, uri.getPath().length());
        File tempFile = new File(tempPath);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(tempPath+File.separator+tempFileName)));
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 获取图片绝对路径
     * @param context
     * @param imageUri
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getImageAbsolutePath(Activity context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[] { split[1] };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = { column };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * 加载图片
     * @param context
     * @param imageUrl
     * @param imageView
     */
    public static void loadImage(Context context, String imageUrl, ImageView imageView){
        Glide.with(context).load(imageUrl).placeholder(R.drawable.list_thumbnail_loading_ss).centerCrop().crossFade().into(imageView);
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
