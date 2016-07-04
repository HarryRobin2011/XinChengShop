package com.banksoft.XinChengShop.utils.xml;

import android.util.Xml;
import com.banksoft.XinChengShop.entity.chinaLocation.City;
import com.banksoft.XinChengShop.entity.chinaLocation.Country;
import com.banksoft.XinChengShop.entity.chinaLocation.County;
import com.banksoft.XinChengShop.entity.chinaLocation.Province;
import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 14-3-25
 * Time: 下午8:46
 * To change this template use File | Settings | File Templates.
 */
public class OperateXml implements PullXml {

    @Override
    public List<Province> parse(InputStream is) throws Exception {
        List<Province> params = null;
        List<City> cityList = null;
        List<County> countyList = null;
        Country country = null;
        Province province = null;
        City city = null;
        County county;
        final String ns = null;
        XmlPullParser parser = Xml.newPullParser(); //由android.util.Xml创建一个XmlPullParser实例
        parser.setInput(is, "UTF-8");               //设置输入流 并指明编码方式

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    params = new ArrayList<Province>();
                    break;
                case XmlPullParser.START_TAG:
                    String tag = parser.getName();
                    if("country".equalsIgnoreCase(tag)){
                          country = new Country();
                          eventType = parser.next();
                    }
                    else if("province".equalsIgnoreCase(tag)){
                          cityList = new ArrayList<City>();
                          province = new Province();
                          province.setName(parser.getAttributeValue(ns, "name"));
                          province.setCode(parser.getAttributeValue(ns, "code"));
                          params.add(province);
                          eventType = parser.next();

                    }else if("city".equalsIgnoreCase(tag)){
                          if(province == null){
                              break;
                          }
                          countyList = new ArrayList<County>();
                          city = new City();
                          city.setName(parser.getAttributeValue(ns,"name"));
                          city.setCode(parser.getAttributeValue(ns,"code"));
                          cityList.add(city);
                          eventType = parser.next();
                    }else if("county".equalsIgnoreCase(tag)){
                          county = new County();
                          county.setName(parser.getAttributeValue(ns, "name"));
                          county.setCode(parser.getAttributeValue(ns, "code"));
                          countyList.add(county);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if("country".equalsIgnoreCase(parser.getName()) && province != null){
                        country.setProvinceList(params);

                    }else if("province".equalsIgnoreCase(parser.getName()) && province != null){
                        province.setCityList(cityList);
                        province = null;
                    }else if("city".equalsIgnoreCase(parser.getName()) && province != null){
                        city.setCountyList(countyList);
                        city = null;
                    }
                    break;
            }
            eventType = parser.next();
        }
        return params;
    }

    @Override
    public List<Province> findProvince() throws Exception {

        return null;
    }

    @Override
    public List<City> findCity(Province province) throws Exception {

        return null;
    }

    @Override
    public List<County> findCountry(City city) throws Exception {

        return null;
    }
}
