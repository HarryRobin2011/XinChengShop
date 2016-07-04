/**
 * @(#) ShopCollectionVO..java     2012/11/08 15:15
 *
 * 版权所有 (c) 北京银软网络技术有限公司
 * 北京市海淀区上地国际创业园西区1号
 * 保留所有权利
 */
package com.banksoft.XinChengShop.entity;

import com.banksoft.XinChengShop.entity.base.BaseEntity;

/**
 * 类的功能，目的，描述等写在此处
 *
 * @author 刘志斌
 * @version 1.0
 */

public class ShopCollectionVO extends BaseEntity{
    private String id;
    private String shopId;
    private String shopName;
    private String shopNo;
    private String shopLogo;
    private String areaNo;
    private int goodsNum;
    private long createTime;
    private int collections;
    private String shopAccount;
    private String shopServerType;  //店铺类型
    private int serverGoodsNum;  //服务类店铺产品
    private String area;  //地区名称

    private String memberImage;//收藏认得头像
    private String memberName; //收藏人账号
    private String memberId; //会员id

    public String getMemberImage() {
        return memberImage;
    }

    public void setMemberImage(String memberImage) {
        this.memberImage = memberImage;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getServerGoodsNum() {
        return serverGoodsNum;
    }

    public void setServerGoodsNum(int serverGoodsNum) {
        this.serverGoodsNum = serverGoodsNum;
    }

    public String getShopServerType() {
        return shopServerType;
    }

    public void setShopServerType(String shopServerType) {
        this.shopServerType = shopServerType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public String getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(String areaNo) {
        this.areaNo = areaNo;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getCollections() {
        return collections;
    }

    public void setCollections(int collections) {
        this.collections = collections;
    }

    public String getShopAccount() {
        return shopAccount;
    }

    public void setShopAccount(String shopAccount) {
        this.shopAccount = shopAccount;
    }
}
