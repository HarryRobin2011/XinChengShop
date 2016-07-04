package com.banksoft.XinChengShop.entity;

import com.banksoft.XinChengShop.entity.base.BaseEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘志斌
 * Date: 13-1-3
 * Time: 下午1:11
 * To change this template use File | Settings | File Templates.
 *
 */
public class OrderVO extends BaseEntity{
    private String id;
    private String siteId;      //
    private String shopId;
    private String shopNo;
    private String shopName;
    private String shopTelephone;
    private String shopAccount;
    private String shopAddress;
    private String shopDistrict;
    private String shopScore;
    private String no;
    private String memberId;
    private String memberName;      //会员名称
    private String memberAccount;       //会员帐号
    private int memberScore;         //会员等级
    private String status;          //订单主状态
    private float totalMoney;       //订单总金额
    private long createTime;         //订单创建时间
    private long payTime;           //订单支付时间
    private long dispatchTime;          //订单发货时间
    private long successTime;           //订单收货时间
    private long overTime;      //订单关闭时间
    private String userName;        //收货人名称
    private String address;         //收货人地址
    private String postcode;        //收货人邮编
    private String telephone;       //收货人电话
    private String areaNo;          //收货人所在地区编码
    private String remark;          //收货人留言
    private float expressMoney;     //邮费金额
    private String province;        //收货人所在省份（汉字）
    private String county;      //收货人所在城市（汉字）
    private String city;            //收货人所在县区（汉字）
    private String expressType;     //收货人快递类型
	private String remarkStatus;        //订单备注状态
    private int productCount;       //订单总商品数量
    private String assessStatus;
    private String payType;    //订单支付类型
    private String returnExpressNo;
    private String returnExpressCompany;
    private boolean memberAssess;
    private boolean shopAssess;
    private String softShopNo;
    private String expressNo;
    private String expressCompany;
    private float cashCouponMoney;   //现金券价值
    private float changePrice;          //手动修改订单价格所变动的值（订单计算得出的总金额 - 变动后金额）
    private long timeLimit;  //下一状态处理期限

    private boolean express; //是否送货， true 送货  false 不送货，到店消费
    private String orderType; //订单类型
    private String dispatchStatus; //派送员状态，
    private String dispatchId; //派送员状态，
    private String dispatchName; //派送员姓名
    private String dispatchNo; //派单员编号
    private String dispatchTelephone; //派送员电话
    private long orderTime;        //预定时间
    private String tableRemark; //桌号信息
    private String orderCode; //到店消费的券密码


    private List<OrderProductVO> list;      //订单包含普通商品
    private List<CartComboProductVO> groupList;//订单包含组合商品

    public String getDispatchNo() {
        return dispatchNo;
    }

    public void setDispatchNo(String dispatchNo) {
        this.dispatchNo = dispatchNo;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public String getTableRemark() {
        return tableRemark;
    }

    public void setTableRemark(String tableRemark) {
        this.tableRemark = tableRemark;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(String dispatchId) {
        this.dispatchId = dispatchId;
    }

    public String getDispatchName() {
        return dispatchName;
    }

    public void setDispatchName(String dispatchName) {
        this.dispatchName = dispatchName;
    }

    public String getDispatchTelephone() {
        return dispatchTelephone;
    }

    public void setDispatchTelephone(String dispatchTelephone) {
        this.dispatchTelephone = dispatchTelephone;
    }

    public boolean isExpress() {
        return express;
    }

    public void setExpress(boolean express) {
        this.express = express;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getDispatchStatus() {
        return dispatchStatus;
    }

    public void setDispatchStatus(String dispatchStatus) {
        this.dispatchStatus = dispatchStatus;
    }

    public long getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(long timeLimit) {
        this.timeLimit = timeLimit;
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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopTelephone() {
        return shopTelephone;
    }

    public void setShopTelephone(String shopTelephone) {
        this.shopTelephone = shopTelephone;
    }

    public String getShopAccount() {
        return shopAccount;
    }

    public void setShopAccount(String shopAccount) {
        this.shopAccount = shopAccount;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopDistrict() {
        return shopDistrict;
    }

    public void setShopDistrict(String shopDistrict) {
        this.shopDistrict = shopDistrict;
    }

    public String getShopScore() {
        return shopScore;
    }

    public void setShopScore(String shopScore) {
        this.shopScore = shopScore;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(String memberAccount) {
        this.memberAccount = memberAccount;
    }

    public int getMemberScore() {
        return memberScore;
    }

    public void setMemberScore(int memberScore) {
        this.memberScore = memberScore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getPayTime() {
        return payTime;
    }

    public void setPayTime(long payTime) {
        this.payTime = payTime;
    }

    public long getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(long dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    public long getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(long successTime) {
        this.successTime = successTime;
    }

    public long getOverTime() {
        return overTime;
    }

    public void setOverTime(long overTime) {
        this.overTime = overTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(String areaNo) {
        this.areaNo = areaNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public float getExpressMoney() {
        return expressMoney;
    }

    public void setExpressMoney(float expressMoney) {
        this.expressMoney = expressMoney;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getExpressType() {
        return expressType;
    }

    public void setExpressType(String expressType) {
        this.expressType = expressType;
    }

    public String getRemarkStatus() {
        return remarkStatus;
    }

    public void setRemarkStatus(String remarkStatus) {
        this.remarkStatus = remarkStatus;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public String getAssessStatus() {
        return assessStatus;
    }

    public void setAssessStatus(String assessStatus) {
        this.assessStatus = assessStatus;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getReturnExpressNo() {
        return returnExpressNo;
    }

    public void setReturnExpressNo(String returnExpressNo) {
        this.returnExpressNo = returnExpressNo;
    }

    public String getReturnExpressCompany() {
        return returnExpressCompany;
    }

    public void setReturnExpressCompany(String returnExpressCompany) {
        this.returnExpressCompany = returnExpressCompany;
    }

    public boolean isMemberAssess() {
        return memberAssess;
    }

    public void setMemberAssess(boolean memberAssess) {
        this.memberAssess = memberAssess;
    }

    public boolean isShopAssess() {
        return shopAssess;
    }

    public void setShopAssess(boolean shopAssess) {
        this.shopAssess = shopAssess;
    }

    public String getSoftShopNo() {
        return softShopNo;
    }

    public void setSoftShopNo(String softShopNo) {
        this.softShopNo = softShopNo;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public float getCashCouponMoney() {
        return cashCouponMoney;
    }

    public void setCashCouponMoney(float cashCouponMoney) {
        this.cashCouponMoney = cashCouponMoney;
    }

    public float getChangePrice() {
        return changePrice;
    }

    public void setChangePrice(float changePrice) {
        this.changePrice = changePrice;
    }

    public List<OrderProductVO> getList() {
        return list;
    }

    public void setList(List<OrderProductVO> list) {
        this.list = list;
    }

    public List<CartComboProductVO> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<CartComboProductVO> groupList) {
        this.groupList = groupList;
    }
}
