package com.banksoft.XinChengShop.entity;

import com.banksoft.XinChengShop.entity.base.BaseEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-28
 * Time: 上午8:38
 * To change this template use File | Settings | File Templates.
 */
public class ShopProductListVO extends BaseEntity{
    private String id;
	private String siteId;      //所属商城
	private String typeId;      //商品分类
	private String price;       //商品单价
	private String stockNum;        //网上库存
	private String createTime;      //创建时间
	private String modifyTime;      //修改时间
	private String description;     //商品描述
	private String auditStatus;     //是否审核通过
	private int collections;        //被收藏次数
    private int sales;          //被购买次数
	private int views;          //被浏览次数
	private String brand;       //所属品牌
	private String validity;    //有效期（暂时没用）
	private String areaNo;      //所在地区（六位地区编码）
	private String freight;     //是否免运费
	private String expressModelId;    //所选物流模版

    private String no;  //商品编号
    private String name;   //商品名称
    private boolean status;  //是否上架
    private String typeNo;  //所属分类编号
    private String typeName;  //所属分类名称
    private String icon;  //商品图片（多个，用|分割）
    private String shopId;  //商铺id
    private String shopName;   //商铺名称
    private String shopNO;  //商铺编号
    private boolean hot;  //是否热卖
    private boolean fashion;  //是否推荐
    private String seoKeyword;
    private int assesses;  //该商品被评价数量
    private float goodCount;  //评分
    private int shopScore;  //商铺评分
    private float shopMatchScore;  //商品服务等级
    private List<String> typeNames;  //当前商品的所属分类寻根列表
    private String signProductId;   //软件对应商品id
    private boolean recommend; //
    private String jstAccount;      //商铺的及时通帐号
    private String qq; //店铺的QQ
    private int hasStandard;//是否存在颜色尺码

    private String shopTypeName; //店铺自身分类名称
    private String shopProTypeId; //店铺自身分类id

    private boolean special; //是否特色产品
    private String active; //活动产品 0 无活动， 1 清仓产品  2 团购产品
    private int virtualSale; //虚拟销量
    private int groupSale; //团购销量
    private int virtualGroup; //虚拟团购销量
    private int clearSale; //清仓销量
    private int virtualClear; //虚拟清仓销量
    private String salePrice; //市场价

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getSeoKeyword() {
        return seoKeyword;
    }

    public void setSeoKeyword(String seoKeyword) {
        this.seoKeyword = seoKeyword;
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

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getShopProTypeId() {
        return shopProTypeId;
    }

    public void setShopProTypeId(String shopProTypeId) {
        this.shopProTypeId = shopProTypeId;
    }

    public String getShopTypeName() {
        return shopTypeName;
    }

    public void setShopTypeName(String shopTypeName) {
        this.shopTypeName = shopTypeName;
    }

    public String getJstAccount() {
        return jstAccount;
    }

    public void setJstAccount(String jstAccount) {
        this.jstAccount = jstAccount;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public String getSignProductId() {
        return signProductId;
    }

    public void setSignProductId(String signProductId) {
        this.signProductId = signProductId;
    }

    public int getAssesses() {
        return assesses;
    }

    public void setAssesses(int assesses) {
        this.assesses = assesses;
    }

    public float getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(float goodCount) {
        this.goodCount = goodCount;
    }

    public int getShopScore() {
        return shopScore;
    }

    public void setShopScore(int shopScore) {
        this.shopScore = shopScore;
    }

    public float getShopMatchScore() {
        return shopMatchScore;
    }

    public void setShopMatchScore(float shopMatchScore) {
        this.shopMatchScore = shopMatchScore;
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

    public String getShopNO() {
        return shopNO;
    }

    public void setShopNO(String shopNO) {
        this.shopNO = shopNO;
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

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTypeNo() {
        return typeNo;
    }

    public void setTypeNo(String typeNo) {
        this.typeNo = typeNo;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getStockNum() {
		return stockNum;
	}

	public void setStockNum(String stockNum) {
		this.stockNum = stockNum;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

    public int getCollections() {
        return collections;
    }

    public void setCollections(int collections) {
        this.collections = collections;
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

    public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getAreaNo() {
		return areaNo;
	}


	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

	public String getExpressModelId() {
		return expressModelId;
	}

	public void setExpressModelId(String expressModelId) {
		this.expressModelId = expressModelId;
	}

    public List<String> getTypeNames() {
        return typeNames;
    }

    public void setTypeNames(List<String> typeNames) {
        this.typeNames = typeNames;
    }

    public int getHasStandard() {
        return hasStandard;
    }

    public void setHasStandard(int hasStandard) {
        this.hasStandard = hasStandard;
    }
}
