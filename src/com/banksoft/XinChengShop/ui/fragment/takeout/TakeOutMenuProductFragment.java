package com.banksoft.XinChengShop.ui.fragment.takeout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.TakeOutMenuProductAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.config.MapFlag;
import com.banksoft.XinChengShop.dao.TakeOutMenuDao;
import com.banksoft.XinChengShop.entity.ShopProductListVO;
import com.banksoft.XinChengShop.entity.ShopProductTypeBO;
import com.banksoft.XinChengShop.model.ShopProductListData;
import com.banksoft.XinChengShop.ui.base.XCBaseFragment;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Robin on 2016/5/26.
 */
public class TakeOutMenuProductFragment extends XCBaseFragment {
    private LinearLayout progress;
    private TextView alertText;
    private ListView menuListView;
    public ShopProductTypeBO currentShopTypeBO;
    private TakeOutMenuProductAdapter takeOutMenuProductAdapter;
    LinkedList dataList;
    private TakeOutMenuDao takeOutMenuDao;

    public String shopId;

    public TakeOutMenuFragment takeOutMenuFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View initContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.list_content, null);
    }

    @Override
    public void initView(View view) {
        progress = (LinearLayout) view.findViewById(R.id.progressContainer);
        menuListView = (ListView) view.findViewById(android.R.id.list);
        alertText = (TextView) view.findViewById(R.id.internalEmpty);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initOperation() {
        if(takeOutMenuDao == null){
            takeOutMenuDao = new TakeOutMenuDao(mContext);
        }

        if(dataList == null){//û������ ��������
            new MyChildAsync(currentShopTypeBO).execute(takeOutMenuDao);
        }else{// ������ ���� ֱ�Ӽ���
            setMenuListView();
         }

    }

    @Override
    public Fragment getInstance() {
        return null;
    }


    @Override
    public CharSequence getTitle() {
        return null;
    }

    private class MyChildAsync extends AsyncTask<TakeOutMenuDao, String, ShopProductListData> {
        private ShopProductTypeBO shopProductTypeBO;

        public MyChildAsync(ShopProductTypeBO shopProductTypeBO) {
            this.shopProductTypeBO = shopProductTypeBO;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
            menuListView.setVisibility(View.GONE);
        }

        @Override
        protected ShopProductListData doInBackground(TakeOutMenuDao... params) {
            return params[0].getShopProductListData(shopId, shopProductTypeBO.getNo(), true);
        }

        @Override
        protected void onPostExecute(ShopProductListData shopProductListData) {
            super.onPostExecute(shopProductListData);
            progress.setVisibility(View.GONE);
            menuListView.setVisibility(View.VISIBLE);
            showMenuView(shopProductListData);
        }
    }

    private void showMenuView(ShopProductListData shopProductListData) {
        if (shopProductListData != null) {
            if (shopProductListData.isSuccess()) {
                if (shopProductListData.getData().getList() == null || shopProductListData.getData().getList().size() == 0) {
                    alertText.setText(R.string.no_goods);
                    return;
                }
                dataList = new LinkedList();
                alertText.setVisibility(View.GONE);
                for (ShopProductListVO productListVO : shopProductListData.getData().getList()) {
                    HashMap<String, Object> dataMap = new HashMap<>();
                    dataMap.put(MapFlag.NUM, 0);
                    dataMap.put(MapFlag.DATA_0, productListVO);
                    dataList.add(dataMap);
                }
                // ����ListView
                setMenuListView();
            } else {
                alert(R.string.netWork_warning);
            }
        } else {
            alert(R.string.net_error);
        }
    }

    private void setMenuListView(){
        if(takeOutMenuProductAdapter == null){
            takeOutMenuProductAdapter = new TakeOutMenuProductAdapter(takeOutMenuFragment, mContext,
                    dataList);
        }
        takeOutMenuProductAdapter.dataList = dataList;
        menuListView.setAdapter(takeOutMenuProductAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
       // saveStateToArguments();
    }



    private void restoreStateFromArguments() {
        Bundle b = getArguments();
        savedState = b.getBundle(this.getClass().getSimpleName());
    }


}
