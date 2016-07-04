package com.banksoft.XinChengShop.entity;

import com.banksoft.XinChengShop.entity.base.BaseEntity;

/**
 * 本地服务类店铺分类
 * Created by Administrator on 15-9-18.
 */
public class ShopLocalType extends BaseEntity{

    private String id;
    private String siteId;
    private String name;
    private String no;
    private int sort;       //排序字段
    private boolean root;       //是否根节点
    private int grade;      //等级，1， 2， 3
    private boolean leaf;       //是否叶子节点
    private String parentId;        //父类id
    private boolean status;         //是否启用
    private String description;     //描述
    private int imageResID; // 图片显示

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageResID() {
        return imageResID;
    }

    public void setImageResID(int imageResID) {
        this.imageResID = imageResID;
    }
}
