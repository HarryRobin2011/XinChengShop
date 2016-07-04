/**
 * @(#) LoginInfoColumn.java 2014/10/09 14:23
 *
 * 版权所有 (c) 北京银软网络技术有限公司
 * 北京市海淀区上地国际创业园西区1号
 * 保留所有权利
 */
package com.banksoft.XinChengShop.db;

import java.util.HashMap;
import java.util.Map;

/**
 * 类的功能，目的，描述等写在此处
 *
 * @author Robin
 * @version 1.0
 */
public class LoginInfoColumn extends DataBaseColumn {

    public static final String TABLE_NAME = "bs_login_info";
    public static final String F_ID = "f_id";
    public static final String F_ACCOUNT = "f_account";
    public static final String F_PASSWORD = "f_password";
    public static final String F_LOGIN_TIME = "f_password";

    private Map<String, String> columnMap;

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Map<String, String> getColumnMap() {
        if (columnMap == null) {
            columnMap = new HashMap<String, String>();
        }
        columnMap.put(F_ID, "integer primary key autoincrement");
        columnMap.put(F_ACCOUNT, "text");
        columnMap.put(F_PASSWORD, "text");
        columnMap.put(F_LOGIN_TIME, "NUMBER");
        return columnMap;
    }
}