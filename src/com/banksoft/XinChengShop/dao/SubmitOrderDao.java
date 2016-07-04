package com.banksoft.XinChengShop.dao;

import android.content.Context;
import android.util.Log;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.entity.ProductCart;
import com.banksoft.XinChengShop.entity.ShoppingCartVO;
import com.banksoft.XinChengShop.entity.SubmitOrder;
import com.banksoft.XinChengShop.model.ExpressModelData;
import com.banksoft.XinChengShop.model.MemberAddressVOData;
import com.banksoft.XinChengShop.model.OrderVOListData;
import com.banksoft.XinChengShop.model.ShopCartProductData;
import com.banksoft.XinChengShop.type.ShopType;
import com.banksoft.XinChengShop.utils.JSONHelper;
import com.banksoft.XinChengShop.utils.StringUtil;

import java.util.LinkedList;

/**
 * Created by harry_robin on 15/11/28.
 */
public class SubmitOrderDao extends BaseDao {

    public SubmitOrderDao(Context mContext) {
        super(mContext);
    }

    public MemberAddressVOData getMemberAddressData(String memberId, boolean cacheFlag) {
        String url = ControlUrl.SHIPPING_LIST_URL;
        String params = "memberId=" + memberId;
        MemberAddressVOData data = (MemberAddressVOData) postHttpRequest(mContext, url, params, JSONHelper.MEMBER_ADDRESS_DATA, cacheFlag);
        return data;
    }

    /**
     * 创建订单
     *
     * @return
     */
    public OrderVOListData submitOrderVOListData(SubmitOrder submitOrder) {
        String url = ControlUrl.SUBMIT_ORDER_URL;
        String params = getSubmitOrderParams(submitOrder);
        OrderVOListData data = (OrderVOListData) postHttpRequest(mContext, url, params, JSONHelper.ORDER_VO_LIST_DATA, false);
        return data;
    }

    private String getSubmitOrderParams(SubmitOrder submitOrder) {
        StringBuffer paramsBuffer = new StringBuffer(getListParams(submitOrder.getList(),"list"));
        paramsBuffer.append("&memberId=").append(StringUtil.checkEmpty(submitOrder.getMemberId()));
        paramsBuffer.append("&shopId=").append(StringUtil.checkEmpty(submitOrder.getShopId()));
        paramsBuffer.append("&expressPrice=").append(StringUtil.checkEmpty(submitOrder.getExpressPrice()));
        paramsBuffer.append("&expressType=").append(StringUtil.checkEmpty(submitOrder.getExpressType()));
        paramsBuffer.append("&addressId=").append(StringUtil.checkEmpty(submitOrder.getAddressId()));
        paramsBuffer.append("&remark=").append(StringUtil.checkEmpty(submitOrder.getRemark()));
        paramsBuffer.append("&orderType=").append(StringUtil.checkEmpty(submitOrder.getOrderType()));
        if (ShopType.SHOP_SALE.name().equals(StringUtil.checkEmpty(submitOrder.getShopType()))) {//商城类订单
            //
        } else if (ShopType.SHOP_SERVER.name().equals(StringUtil.checkEmpty(submitOrder.getShopType()))){//美食外卖，服务类店铺
            paramsBuffer.append("&express=").append(submitOrder.getExpress());
            paramsBuffer.append("&telephone=").append(submitOrder.getTelephone());
            paramsBuffer.append("&orderTime=").append(submitOrder.getOrderTime());
            paramsBuffer.append("&orderTable＝").append(submitOrder.getOrderTable());
        }
        return paramsBuffer.toString();
    }

    /**
     * 运费模版
     *
     * @param shopCartProductData
     * @return
     */
    public ExpressModelData getExpressModelData(ShopCartProductData shopCartProductData, String areaNo) {
        LinkedList dataList = new LinkedList();
        for (String key : shopCartProductData.getProductVOHashMap().keySet()) {
            ProductCart productCart = shopCartProductData.getProductVOHashMap().get(key);
            ShoppingCartVO shoppingCartVO = new ShoppingCartVO();
            shoppingCartVO.setShopNo(productCart.getProductVO().getShopNo());
            shoppingCartVO.setStandardIds(productCart.getStandardIDs());
            shoppingCartVO.setStandardValues(productCart.getStandardNames());
            shoppingCartVO.setStandardKeys(productCart.getIdAndValueIDs());
            shoppingCartVO.setStandardNames(productCart.getIdAndValueNames());
            shoppingCartVO.setShopId(shopCartProductData.getShopId());
            shoppingCartVO.setActive(productCart.getProductVO().getActive());
            shoppingCartVO.setProductId(productCart.getProductVO().getId());
            shoppingCartVO.setProductName(productCart.getProductVO().getName());
            shoppingCartVO.setImageFile(productCart.getProductVO().getIcon());
            shoppingCartVO.setPrice(Float.parseFloat(productCart.getProductVO().getSalePrice()));
            shoppingCartVO.setGoodsNum(productCart.getNum());
            shoppingCartVO.setTotal(productCart.getTotal());
            dataList.add(shoppingCartVO);
        }
        String url = ControlUrl.EXPRESS_MODEL_FIND_URL;
        String params = getListParams(dataList,"dataList") + "&areaNo=" + areaNo + "&shopId=" + shopCartProductData.getShopId();
        Log.i("HarryRobin", params);
        ExpressModelData data = (ExpressModelData) postHttpRequest(mContext, url, params, JSONHelper.EXPRESS_MODEL_DATA, false);
        return data;
    }
}
