package com.banksoft.XinChengShop.ui.base;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.config.Config;
import com.banksoft.XinChengShop.dao.ListDao;
import com.banksoft.XinChengShop.entity.ListEntity;
import com.banksoft.XinChengShop.model.base.BaseData;
import com.banksoft.XinChengShop.widget.XListView;

import java.lang.reflect.Type;

/**
 * Created by Robin on 2015/1/11.
 */
public abstract class XCBaseListFragment extends XCBaseFragment implements XListView.IXListViewListener, AdapterView.OnItemClickListener,
        SearchView.OnQueryTextListener,BaseMyAdapter.OnAdapterClickListener {
    public XListView xListView;
    public ProgressBar mProgressbar;
    public SearchView mSearchView;
    public TextView mSearchTextView;
    public boolean cacheFlag = false;
    public View nullPager;
    public String url;
    public String params = "";
    public Type jsonType;

    private boolean firstStart = true;

    public BaseMyAdapter bailaAdapter;

    private TextView footText;


    private ListDao listDao;


    public void setListDao() {
        xListView.setAdapter(bailaAdapter);
        if (listDao == null) {
            listDao = new ListDao(mContext);
        }
        listDao.index = 1;
        listDao.size = Config.DEFAULT_SIZE;
        listDao.url = url;
        listDao.params = params;
        listDao.type = jsonType;
        bailaAdapter.setOnAdapterClickListener(this);
        new MyThread().execute(listDao);
    }

    public abstract void request();

    public void setDefaultXlistView() {
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);
        xListView.setRefreshTime();
        xListView.setXListViewListener(this);
        xListView.setTextFilterEnabled(true);
        xListView.setHeaderDividersEnabled(false);

        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(false);
    }


    @Override
    public View initContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.x_list_layout, null);
    }

    @Override
    public void initView(View view) {
        xListView = (XListView) view.findViewById(R.id.xListView);
        mProgressbar = (ProgressBar) view.findViewById(R.id.progressBar);
        mSearchView = (SearchView) view.findViewById(R.id.search);
        footText = (TextView) view.findViewById(R.id.footerView);
        nullPager = view.findViewById(R.id.null_pager);
        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        mSearchTextView = (TextView) mSearchView.findViewById(id);
        mSearchTextView.setTextColor(getResources().getColor(android.R.color.black));

        setDefaultXlistView();
        request();
    }

    @Override
    public void onRefresh() {
        cacheFlag = false;
        firstStart = true;
        bailaAdapter.dataList.clear();
        try {
            xListView.removeAllViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
        request();
    }

    @Override
    public void onLoadMore() {
        listDao.index += 1;
        listDao.params = params;
        new MyThread().execute(listDao);
    }

    @Override
    public void onLoad() {
        xListView.stopLoadMore();
        xListView.stopRefresh();
        xListView.setRefreshTime();
    }

    private class MyThread extends AsyncTask<ListDao, String, BaseData<ListEntity>> {
        @Override
        protected void onPreExecute() {
            if (firstStart) {
                mProgressbar.setVisibility(View.VISIBLE);
                firstStart = false;
            }

        }

        @Override
        protected BaseData<ListEntity> doInBackground(ListDao... params) {
            return params[0].getBaseListData(cacheFlag);
        }

        @Override
        protected void onPostExecute(BaseData<ListEntity> baseListData) {
            super.onPostExecute(baseListData);
            mProgressbar.setVisibility(View.GONE);
            onLoad();
            if (baseListData != null) {
                if (baseListData.isSuccess()) {
                    if (baseListData.getData().getList().size() == 0) {
                        nullPager.setVisibility(View.VISIBLE);
                        xListView.mFooterView.mHintView.setText(R.string.no_data);
                        xListView.mFooterView.setClickable(false);
                        return;
                    } else if (baseListData.getData().getList().size() < Config.DEFAULT_SIZE) {
                        noMore();
                    }
                    bailaAdapter.dataList.addAll(baseListData.getData().getList());
                    bailaAdapter.notifyDataSetChanged();
                } else {
                    alert(R.string.netWork_warning);
                }
            } else {
                alert(R.string.netWork_error);
            }
        }
    }

    public void noMore() {
        xListView.setPullLoadEnable(false);
        xListView.setRefreshTime();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initOperation() {
        xListView.setOnItemClickListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            xListView.clearTextFilter();
        } else {
            xListView.setFilterText(newText);
        }
        return true;
    }

}
