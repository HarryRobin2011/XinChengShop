package com.banksoft.XinChengShop.entity;

import com.banksoft.XinChengShop.entity.base.BaseEntity;

/**
 * Created with IntelliJ IDEA.
 * User: 刘志斌
 * Date: 13-10-23
 * Time: 下午3:08
 * To change this template use File | Settings | File Templates.
 * 积分换购的商品
 */
public class ScoreProduct extends BaseEntity{
    private String id;
    private String siteId;
    private String name;   //商品名称
    private String no;          //商品编号
    private float price;        //商品原价
    private int score;          //消耗积分
    private int stock;          //库存
    private String title;       //商品标签
    private boolean agreeNumber;        //是否限制购买
    private int num;         //限制购买数量
    private boolean agreeBuyTime;       //是否限制换购时间
    private long startTime;         //换购开始时间
    private long endTime;           //换购结束时间
    private boolean status;         //是否上架
    private boolean recommend;      //是否推荐
    private String seoKey;          //seo标题
    private String seoContent;      //seo内容
    private int sort;           //排序
    private String description;         //商品描述
    private String imageFile;           //商品图片
    private int sales;          //已兑换数量
    private int views;          //已浏览数量
    private long createTime;    //创建时间

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAgreeNumber() {
        return agreeNumber;
    }

    public void setAgreeNumber(boolean agreeNumber) {
        this.agreeNumber = agreeNumber;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isAgreeBuyTime() {
        return agreeBuyTime;
    }

    public void setAgreeBuyTime(boolean agreeBuyTime) {
        this.agreeBuyTime = agreeBuyTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public String getSeoKey() {
        return seoKey;
    }

    public void setSeoKey(String seoKey) {
        this.seoKey = seoKey;
    }

    public String getSeoContent() {
        return seoContent;
    }

    public void setSeoContent(String seoContent) {
        this.seoContent = seoContent;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
