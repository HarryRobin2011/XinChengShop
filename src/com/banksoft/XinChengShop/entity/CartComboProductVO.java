/**
 * @(#) CartComboProductVO    14-3-27 上午10:43
 *
 * 版权所有 (c) 北京银软网络技术有限公司
 * 北京市海淀区上地国际创业园西区1号
 * 保留所有权利
 */
package com.banksoft.XinChengShop.entity;


import java.util.List;

/**
 * 类的功能，目的，描述等写在此处
 *
 * @author 张战军
 * @version 1.0
 */
public class CartComboProductVO {
    private String comboProductId;
    private List<ShoppingCartVO> shoppingCartVOList;
    private List<OrderProductBO> orderProductBOList;//
    private List<OrderProductVO> orderProductVOList;
    private float comboPrice;   //组合销售价格
    private String title;//组合销售活动名称
    private String description;//组合销售活动描述
    private long createTime; // 创建时间
    private String icon;//组合商品图片
    private String assume;//seller ：卖家承担运费  buyer：买家承担运费
    private float freight;//运费 如果是买家承担运费则进行手动填写，如果是卖家承担运费则不用填写。
    private float costPrice; //组合商品原价
    private float totalPrice;// 购买组总价格
    private int countComboProduct;//购买组数量

    public List<OrderProductVO> getOrderProductVOList() {
        return orderProductVOList;
    }

    public void setOrderProductVOList(List<OrderProductVO> orderProductVOList) {
        this.orderProductVOList = orderProductVOList;
    }

    public List<OrderProductBO> getOrderProductBOList() {
        return orderProductBOList;
    }

    public void setOrderProductBOList(List<OrderProductBO> orderProductBOList) {
        this.orderProductBOList = orderProductBOList;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getCountComboProduct() {
        return countComboProduct;
    }

    public void setCountComboProduct(int countComboProduct) {
        this.countComboProduct = countComboProduct;
    }

    public String getComboProductId() {
        return comboProductId;
    }

    public void setComboProductId(String comboProductId) {
        this.comboProductId = comboProductId;
    }

    public List<ShoppingCartVO> getShoppingCartVOList() {
        return shoppingCartVOList;
    }

    public void setShoppingCartVOList(List<ShoppingCartVO> shoppingCartVOList) {
        this.shoppingCartVOList = shoppingCartVOList;
    }

    public float getComboPrice() {
        return comboPrice;
    }

    public void setComboPrice(float comboPrice) {
        this.comboPrice = comboPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAssume() {
        return assume;
    }

    public void setAssume(String assume) {
        this.assume = assume;
    }

    public float getFreight() {
        return freight;
    }

    public void setFreight(float freight) {
        this.freight = freight;
    }

    public float getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(float costPrice) {
        this.costPrice = costPrice;
    }
}
