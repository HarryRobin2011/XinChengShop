/**
 * @(#) AlipayInfoBO..java     2012/11/08 15:15
 *
 * 版权所有 (c) 北京银软网络技术有限公司
 * 北京市海淀区上地国际创业园西区1号
 * 保留所有权利
 */
package com.banksoft.XinChengShop.entity;

/**
 * 类的功能，目的，描述等写在此处
 *店铺评论
 * @author 刘志斌
 * @version 1.0
 */

public class OrderAssessBO {
	private String id;
	private String siteId;
	private String orderId;      //订单id
	private String shopId; //店铺id
	private String memberId; //评论会员id
	private String memberType;
	private long createTime;
//	private int score;
	private String content;
	private boolean status;
    private float matchScore;    //宝贝与描述相符度  1-5分
    private float serviceScore; //卖家服务态度 1-5分
    private float dispatchScore; //卖家发货速度 1-5分

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

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
