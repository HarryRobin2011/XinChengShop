package com.banksoft.XinChengShop.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.MoneyBalanceDetailAdapter;
import com.banksoft.XinChengShop.adapter.ProductManagerListAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.ProductManagerDao;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseListFragment;
import com.banksoft.XinChengShop.utils.JSONHelper;

import java.util.ArrayList;

/**
 * Created by Robin on 2016/4/10.
 */
public class ProductManagerListFragment extends XCBaseListFragment {
    private XCBaseActivity activity;
    public Type currentType;
    public String goodName;
    private ProductManagerDao productManagerDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (XCBaseActivity) getActivity();
    }

    @Override
    public void request() {
        url = ControlUrl.XC_SHOP_GOODS_LIST_URL;
        jsonType = JSONHelper.SHOP_LIST_PRODUCT_DATA;
        bailaAdapter = new ProductManagerListAdapter(this,mContext, new ArrayList(),currentType);
        boolean status = false;
        if (Type.PUTAWAY.equals(currentType)) {// 出售中
            status = true;

        } else if (Type.TAKEOFF.equals(currentType)) {// 仓库中
            status = false;
        }

        params = "shopId=" + activity.member.getShop().getId() + "&status=" + status;

        if (!"".equals(goodName) && goodName != null) {// 搜索产品名称不为空
            params += "&name=" + goodName;
        }

        xListView.setDividerHeight(1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Activity.RESULT_FIRST_USER) {
            switch (resultCode) {
                case Activity.RESULT_OK:  //�����ɹ�
                    request();
                    break;
                case Activity.RESULT_CANCELED: //ȡ�����
                    request();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public Fragment getInstance() {
        return null;
    }

    @Override
    public CharSequence getTitle() {

        if (Type.PUTAWAY.equals(currentType)) {// 出售中
            return "出售中";

        } else if (Type.TAKEOFF.equals(currentType)) {// 仓库中
            return "仓库中";
        }
        return "";
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public static enum Type {
        PUTAWAY,//出售中
        TAKEOFF;//仓库中

    }

    /**
     *
     */
    public class TakeOperationTask extends AsyncTask<ProductManagerDao, String, IsFlagData> {
        private String ids;

        public TakeOperationTask(String ids) {
            this.ids = ids;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected IsFlagData doInBackground(ProductManagerDao... params) {
            if (Type.TAKEOFF.equals(currentType)) {
                return params[0].productTakeOn(ids);
            } else {
                return params[0].productTakeOff(ids);
            }
        }

        @Override
        protected void onPostExecute(IsFlagData isFlagData) {
            super.onPostExecute(isFlagData);
            if(isFlagData != null){
                if(isFlagData.isSuccess()){
                    alert(R.string.operation_success);
                   onRefresh();
                }else{
                    alert(R.string.net_is_weak);
                }
            }else{
                alert(R.string.net_error);
            }
        }
    }

    /**
     * 上下架操作
     *
     * @param ids
     */
    public void takeOperation(String ids) {
        if (productManagerDao == null) {
            productManagerDao = new ProductManagerDao(mContext);
        }
        new TakeOperationTask(ids).execute(productManagerDao);
    }

}
