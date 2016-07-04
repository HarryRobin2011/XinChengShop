package com.banksoft.XinChengShop.entity;

import com.banksoft.XinChengShop.entity.base.BaseEntity;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-19
 * Time: 上午11:29
 * To change this template use File | Settings | File Templates.
 */
public class ProductVO extends BaseEntity{
    private String id;
    private String siteId;
    private String typeNo;
    private String typeName;
    private String name;
    private String no;
    private float price;
    private int stockNum;
    private String icon;
    private String description;
    private boolean status;
    private String shopId;
    private String shopName;
    private boolean auditStatus;
    private boolean violation;
    private boolean hot;
    private boolean fashion;
    private String shopNo;
    private boolean freight;
    private String expressModelId;
	private String brand;
	private String typeId;
	private String areaNo;
	private String validity;

	private String seoKeyword;
	private String seoDescription;
	private boolean recommend;
    private String shopProTypeId;
	private float score;
    private int sales;
    private int collections;
    private int views;
    private String inventoryChange;//库存变更模式，生成订单后或买家付款后
    private String flvUrl;  //产品视频

    private String images;

    private int assesses;//商品被评价次数

    private boolean special; //是否特色产品
    private String active; //活动产品 0 无活动， 1 清仓产品  2 团购产品
    private int virtualSale; //虚拟销量
    private int groupSale; //团购销量
    private int virtualGroup; //虚拟团购销量
    private int clearSale; //清仓销量
    private int virtualClear; //虚拟清仓销量
    private String salePrice; //市场价

    private String shopImg; //店铺图片
    private float matchScore; //与实物描述
    private float serviceScore;//服务态度评分
    private float dispatchScore; //发货速度评分
    private String shopServerType; //店铺类型

    public String getShopServerType() {
        return shopServerType;
    }

    public void setShopServerType(String shopServerType) {
        this.shopServerType = shopServerType;
    }

    public String getShopImg() {
        return shopImg;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg;
    }

    public float getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(float matchScore) {
        this.matchScore = matchScore;
    }

    public float getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(float serviceScore) {
        this.serviceScore = serviceScore;
    }

    public float getDispatchScore() {
        return dispatchScore;
    }

    public void setDispatchScore(float dispatchScore) {
        this.dispatchScore = dispatchScore;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public boolean isSpecial() {
        return special;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public int getVirtualSale() {
        return virtualSale;
    }

    public void setVirtualSale(int virtualSale) {
        this.virtualSale = virtualSale;
    }

    public int getGroupSale() {
        return groupSale;
    }

    public void setGroupSale(int groupSale) {
        this.groupSale = groupSale;
    }

    public int getVirtualGroup() {
        return virtualGroup;
    }

    public void setVirtualGroup(int virtualGroup) {
        this.virtualGroup = virtualGroup;
    }

    public int getClearSale() {
        return clearSale;
    }

    public void setClearSale(int clearSale) {
        this.clearSale = clearSale;
    }

    public int getVirtualClear() {
        return virtualClear;
    }

    public void setVirtualClear(int virtualClear) {
        this.virtualClear = virtualClear;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getAssesses() {
        return assesses;
    }

    public void setAssesses(int assesses) {
        this.assesses = assesses;
    }

    public String getFlvUrl() {
        return flvUrl;
    }

    public void setFlvUrl(String flvUrl) {
        this.flvUrl = flvUrl;
    }

    public String getInventoryChange() {
        return inventoryChange;
    }

    public void setInventoryChange(String inventoryChange) {
        this.inventoryChange = inventoryChange;
    }

    public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getShopProTypeId() {
        return shopProTypeId;
    }

    public void setShopProTypeId(String shopProTypeId) {
        this.shopProTypeId = shopProTypeId;
    }

    public String getSeoKeyword() {
		return seoKeyword;
	}

	public void setSeoKeyword(String seoKeyword) {
		this.seoKeyword = seoKeyword;
	}

	public String getSeoDescription() {
		return seoDescription;
	}

	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	public boolean isRecommend() {
		return recommend;
	}

	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public boolean isFreight() {
        return freight;
    }

    public void setFreight(boolean freight) {
        this.freight = freight;
    }

    public String getExpressModelId() {
        return expressModelId;
    }

    public void setExpressModelId(String expressModelId) {
        this.expressModelId = expressModelId;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getCollections() {
        return collections;
    }

    public void setCollections(int collections) {
        this.collections = collections;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public boolean isHot() {
        return hot;
    }

    public void setHot(boolean hot) {
        this.hot = hot;
    }

    public boolean isFashion() {
        return fashion;
    }

    public void setFashion(boolean fashion) {
        this.fashion = fashion;
    }

    public boolean isAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(boolean auditStatus) {
        this.auditStatus = auditStatus;
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

    public String getTypeNo() {
        return typeNo;
    }

    public void setTypeNo(String typeNo) {
        this.typeNo = typeNo;
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

    public int getStockNum() {
        return stockNum;
    }

    public void setStockNum(int stockNum) {
        this.stockNum = stockNum;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getAreaNo() {
		return areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

    public boolean isViolation() {
        return violation;
    }

    public void setViolation(boolean violation) {
        this.violation = violation;
    }
}
