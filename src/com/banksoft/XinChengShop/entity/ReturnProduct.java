/**
 * @(#) AlipayInfo..java     2012/11/08 15:15
 *
 * 版权所有 (c) 北京银软网络技术有限公司
 * 北京市海淀区上地国际创业园西区1号
 * 保留所有权利
 */
package com.banksoft.XinChengShop.entity;

/**
 * 退货记录
 *
 * @author 刘志斌
 * @version 1.0
 */

public class ReturnProduct {
    private String id;
    private String siteId;
    private String orderId;     //申请订单
    private String orderNo;     //订单编号
    private String status;          //申请状态（申请中，同意，不同意）
    private String no;          //退货单号
    private float orderTotal;   //订单总金额

    private String memberId;    //申请人
    private String memberAccount;       //申请人帐号
    private String memberImage;     //申请人证据
    private String returnType;      //退货类型
    private float memberMoney;      //退货金额
    private boolean returnProduct;      //是否需要退货
    private String memberReason;    //申请原因

	private String shopId;      //被申请人
    private String shopAccount;     //被申请人帐号
    private String shopName;            //商铺名称
	private long createTime;        //申请时间
    private long backTime;          //被申请人处理时间
	private String shopReason;      //不同意原因
	private String shopImage;       //被提供人证据

    private long dispatchTime;      // 买家退回时间
    private long overTime;          //商家收货时间

    private boolean admin;          //管理员是否介入
    private String adminRemark;  //管理员备注

    public String getAdminRemark() {
        return adminRemark;
    }

    public void setAdminRemark(String adminRemark) {
        this.adminRemark = adminRemark;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMemberReason() {
		return memberReason;
	}

	public void setMemberReason(String memberReason) {
		this.memberReason = memberReason;
	}

	public String getShopReason() {
		return shopReason;
	}

	public void setShopReason(String shopReason) {
		this.shopReason = shopReason;
	}

	public String getShopImage() {
		return shopImage;
	}

	public void setShopImage(String shopImage) {
		this.shopImage = shopImage;
	}

	public String getMemberImage() {
		return memberImage;
	}

	public void setMemberImage(String memberImage) {
		this.memberImage = memberImage;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public float getMemberMoney() {
		return memberMoney;
	}

	public void setMemberMoney(float memberMoney) {
		this.memberMoney = memberMoney;
	}

    public String getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(String memberAccount) {
        this.memberAccount = memberAccount;
    }

    public String getShopAccount() {
        return shopAccount;
    }

    public void setShopAccount(String shopAccount) {
        this.shopAccount = shopAccount;
    }

    public boolean isReturnProduct() {
        return returnProduct;
    }

    public void setReturnProduct(boolean returnProduct) {
        this.returnProduct = returnProduct;
    }

    public long getBackTime() {
        return backTime;
    }

    public void setBackTime(long backTime) {
        this.backTime = backTime;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public float getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(float orderTotal) {
        this.orderTotal = orderTotal;
    }

    public long getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(long dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    public long getOverTime() {
        return overTime;
    }

    public void setOverTime(long overTime) {
        this.overTime = overTime;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
