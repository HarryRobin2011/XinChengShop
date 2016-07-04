package com.banksoft.XinChengShop.entity;

import com.banksoft.XinChengShop.entity.base.BaseEntity;

import java.util.List;

/**
 * Created by harry_robin on 15/12/5.
 */
public class ExpressModel extends BaseEntity{
    private boolean expressStatus;
    private List<ExpressPriceVO> list;

    public boolean isExpressStatus() {
        return expressStatus;
    }

    public void setExpressStatus(boolean expressStatus) {
        this.expressStatus = expressStatus;
    }

    public List<ExpressPriceVO> getList() {
        return list;
    }

    public void setList(List<ExpressPriceVO> list) {
        this.list = list;
    }
}
