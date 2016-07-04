package com.banksoft.XinChengShop.entity;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by harry_robin on 15/11/19.
 */
public class ProductCart implements Serializable {
    private ProductVO productVO;
    private int num;
    private float total;
    private HashMap<Integer,IdAndValue> idAndValues;
    private float salePrice;
    private String standardIDs;
    private String standardNames;
    private String idAndValueNames;
    private String idAndValueIDs;
    private boolean isSelect;

    public ProductVO getProductVO() {
        return productVO;
    }

    public void setProductVO(ProductVO productVO) {
        this.productVO = productVO;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public HashMap<Integer, IdAndValue> getIdAndValues() {
        return idAndValues;
    }

    public void setIdAndValues(HashMap<Integer, IdAndValue> idAndValues) {
        this.idAndValues = idAndValues;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }

    public String getStandardIDs() {
        return standardIDs;
    }

    public void setStandardIDs(String standardIDs) {
        this.standardIDs = standardIDs;
    }

    public String getIdAndValueIDs() {
        return idAndValueIDs;
    }

    public void setIdAndValueIDs(String idAndValueIDs) {
        this.idAndValueIDs = idAndValueIDs;
    }

    public String getStandardNames() {
        return standardNames;
    }

    public void setStandardNames(String standardNames) {
        this.standardNames = standardNames;
    }

    public String getIdAndValueNames() {
        return idAndValueNames;
    }

    public void setIdAndValueNames(String idAndValueNames) {
        this.idAndValueNames = idAndValueNames;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }
}
