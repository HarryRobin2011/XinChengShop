package com.banksoft.XinChengShop.entity.chinaLocation;

import com.banksoft.XinChengShop.entity.base.BaseEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 14-3-26
 * Time: 下午2:52
 * To change this template use File | Settings | File Templates.
 */
public class Country extends BaseEntity{
    private List<Province> provinceList;

    public List<Province> getProvinceList() {
        return provinceList;
    }

    public void setProvinceList(List<Province> provinceList) {
        this.provinceList = provinceList;
    }
}
