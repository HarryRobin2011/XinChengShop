package com.banksoft.XinChengShop.dao.base;

import android.content.Context;

import java.lang.reflect.Type;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 14-6-12
 * Time: 下午6:14
 * To change this template use File | Settings | File Templates.
 */
public interface HttpRequest {
    public Object postHttpRequest(Context mContext, String url, String params, Type jsonType, boolean cacheFlag);

    public Object postHttpUrl(String url, Type jsonType);
}
