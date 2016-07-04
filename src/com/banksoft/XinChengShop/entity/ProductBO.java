package com.banksoft.XinChengShop.entity;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-19
 * Time: 上午10:40
 * To change this template use File | Settings | File Templates.
 */
public class ProductBO {
    private String id;
    private String siteId;//1
    private String shopId;//1
    private String typeNo;//产品分类1
    private String name;//mingzi1
    private String no;//产品编号1
    private float price;//1
    private int stockNum;//1
    private String icon;//多个“|”1
    private String description;//描述1
    private boolean status;//上下架 操作表示
    private boolean auditStatus;//审核状态
    private boolean violation;      //违规下架
    private boolean hot;//是否热卖
    private boolean fashion;
    private int sales;
    private int collections;
    private int views;

	private String areaNo;//地区编号
	private String typeId;//
	private String brand;//
	private String seoKeyword;//
	private String seoDescription;//
	private boolean recommend;//1
	private String validity;//

	private boolean freight;//1
	private String expressModelId;//1

	private float score;
    private String signProductId;
    private String inventoryChange;//库存变更模式，生成订单后或买家付款后1
    private String flvUrl;      //产品视频说明

    private boolean special; //是否特色产品
    private String active; //活动产品 0 无活动， 1 清仓产品  2 团购产品
    private int virtualSale; //虚拟销量
    private int groupSale; //团购销量
    private int virtualGroup; //虚拟团购销量
    private int clearSale; //清仓销量
    private int virtualClear; //虚拟清仓销量
    private String salePrice; //市场价1

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

    public String getSignProductId() {
        return signProductId;
    }

    public void setSignProductId(String signProductId) {
        this.signProductId = signProductId;
    }

    public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	private String shopProTypeId;

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

	public String getAreaNo() {
		return areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

    public boolean isViolation() {
        return violation;
    }

    public void setViolation(boolean violation) {
        this.violation = violation;
    }
}
