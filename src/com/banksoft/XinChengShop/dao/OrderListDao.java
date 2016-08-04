package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.entity.OrderAssessBO;
import com.banksoft.XinChengShop.entity.ProductAssessBO;
import com.banksoft.XinChengShop.entity.ReturnProduct;
import com.banksoft.XinChengShop.model.ExpressCompanyCellListData;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.type.OrderStatus;
import com.banksoft.XinChengShop.type.OrderType;
import com.banksoft.XinChengShop.utils.JSONHelper;

import java.util.List;

/**
 * Created by admin on 2016/7/8.
 */
public class OrderListDao extends BaseDao {
    public OrderListDao(Context mContext) {
        super(mContext);
    }

    /**
     * 未付款取消订单
     *
     * @param orderId
     * @return
     */
    public IsFlagData cancelOrder(String orderId) {
        String url = ControlUrl.XC_ORDER_CANCEL_URL;
        String params = "orderId=" + orderId;
        IsFlagData data = (IsFlagData) postHttpRequest(mContext, url, params, JSONHelper.IS_FLAG_DATA, false);
        return data;
    }

    public IsFlagData deleteOrder(String orderId){
        String url = ControlUrl.XC_ORDER_DELETE_URL;
        String  params= "orderId="+orderId;
        IsFlagData data = (IsFlagData) postHttpRequest(mContext,url,params,JSONHelper.IS_FLAG_DATA,false);
        return data;
    }

    /**
     *退款
     * @param returnProduct
     * @return
     */
    public IsFlagData returnMoney(ReturnProduct returnProduct,OrderStatus status) {
        String url = "";
        if(OrderStatus.PAY.equals(status)){//未发货退款
            url= ControlUrl.XC_ORDER_UN_DISPATCH_RETURN_MONEY_URL;
        }else{//已发货退款
            url= ControlUrl.XC_ORDER_DISPATCH_RETURN_MONEY_URL;
        }
        String params = getParams(returnProduct, "data");
        IsFlagData data = (IsFlagData) postHttpRequest(mContext, url, params, JSONHelper.IS_FLAG_DATA, false);
        return data;
    }

    /**
     * 确认收货
     * @param orderId
     * @return
     */
    public IsFlagData confirmGoods(String orderId){
        String url = ControlUrl.XC_ORDER_CONFIRM_GOODS_URL;
        String params = "orderId=" + orderId;
        IsFlagData data = (IsFlagData) postHttpRequest(mContext, url, params, JSONHelper.IS_FLAG_DATA, false);
        return data;
    }

    /**
     * 退货
     * @param returnProduct
     * @return
     */
    public IsFlagData returnGoods(ReturnProduct returnProduct){
        String url = ControlUrl.XC_ORDER_RETURN_GOODS_URL;
        String params = getParams(returnProduct,"data");
        IsFlagData data = (IsFlagData) postHttpRequest(mContext, url, params, JSONHelper.IS_FLAG_DATA, false);
        return data;
    }

    /**
     *买家退货 发货
     * @param expressCompany 物流公司KEY
     * @param company // 选其它时手动填写的物流公司
     * @param expressNo//运单编号
     * @param orderId//订单ID
     * @return
     */
    public IsFlagData buyerDispatch(String expressCompany,String company,String expressNo,String orderId){
        String url = ControlUrl.XC_ORDER_BUYER_DISPATCH_URL;
        String params = "expressCompany="+expressCompany+"&company="+company+"&expressNo="+expressNo+"&orderId="+orderId;
        IsFlagData data = (IsFlagData) postHttpRequest(mContext, url, params, JSONHelper.IS_FLAG_DATA, false);
        return data;
    }

    /**
     * 订单评论
     * @param memberId
     * @param productData
     * @param orderData
     * @return
     */
    public IsFlagData commentOrder(String memberId,List<ProductAssessBO> productData, OrderAssessBO orderData){
        String url = ControlUrl.XC_ORDER_COMMENT_URL;
        String params = "memberId="+memberId+"&"+getListParams(productData,"list")+"&"+getParams(productData,"orderData");
        IsFlagData data = (IsFlagData) postHttpRequest(mContext, url, params, JSONHelper.IS_FLAG_DATA, false);
        return data;
    }

    /**
     * 获取物流公司列表
     * @return
     */
    public ExpressCompanyCellListData getExpressCompanyListData(){
        String url = ControlUrl.XC_EXPRESS_COMPANY_LIST_URL;
        String params = "";
        ExpressCompanyCellListData data = (ExpressCompanyCellListData) postHttpRequest(mContext, url, params, JSONHelper.EXPRESS_COMPANY_CELL_LIST_DATA, false);
        return data;
    }
}
