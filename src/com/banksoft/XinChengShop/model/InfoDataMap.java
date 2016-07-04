package com.banksoft.XinChengShop.model;

import java.util.HashMap;
import java.util.List;

/**17
 * Created by Robin on 2016/5/17.
 */
public class InfoDataMap {
    private HashMap<String,List<HashMap<String,String>>> 头像信息;
    private HashMap<String,List<HashMap<String,String>>> 贫困户信息;
    private HashMap<String,List<HashMap<String,String>>> 家庭成员;
    private HashMap<String,List<HashMap<String,String>>> 帮扶责任人;
    private HashMap<String,List<HashMap<String,String>>> 帮扶单位;
    private String[] 相册视频;

    public HashMap<String, List<HashMap<String, String>>> get头像信息() {
        return 头像信息;
    }

    public void set头像信息(HashMap<String, List<HashMap<String, String>>> 头像信息) {
        this.头像信息 = 头像信息;
    }

    public HashMap<String, List<HashMap<String, String>>> get贫困户信息() {
        return 贫困户信息;
    }

    public void set贫困户信息(HashMap<String, List<HashMap<String, String>>> 贫困户信息) {
        this.贫困户信息 = 贫困户信息;
    }

    public HashMap<String, List<HashMap<String, String>>> get家庭成员() {
        return 家庭成员;
    }

    public void set家庭成员(HashMap<String, List<HashMap<String, String>>> 家庭成员) {
        this.家庭成员 = 家庭成员;
    }

    public HashMap<String, List<HashMap<String, String>>> get帮扶责任人() {
        return 帮扶责任人;
    }

    public void set帮扶责任人(HashMap<String, List<HashMap<String, String>>> 帮扶责任人) {
        this.帮扶责任人 = 帮扶责任人;
    }

    public HashMap<String, List<HashMap<String, String>>> get帮扶单位() {
        return 帮扶单位;
    }

    public void set帮扶单位(HashMap<String, List<HashMap<String, String>>> 帮扶单位) {
        this.帮扶单位 = 帮扶单位;
    }

    public String[] get相册视频() {
        return 相册视频;
    }

    public void set相册视频(String[] 相册视频) {
        this.相册视频 = 相册视频;
    }
}
