package com.banksoft.XinChengShop.utils.xml;


import com.banksoft.XinChengShop.entity.chinaLocation.City;
import com.banksoft.XinChengShop.entity.chinaLocation.County;
import com.banksoft.XinChengShop.entity.chinaLocation.Province;

import java.io.InputStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 14-3-25
 * Time: 下午8:41
 * To change this template use File | Settings | File Templates.
 */
public interface PullXml {
    /**
     * 解析输入流 得到Model对象集合
     * @param is
     * @return
     * @throws Exception
     */
    public List<Province> parse(InputStream is) throws Exception;
    public List<Province> findProvince() throws Exception;       //未实现
    public List<City> findCity(Province province) throws Exception; //未实现
    public List<County> findCountry(City city) throws Exception;  //未实现
}
