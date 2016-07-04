package com.banksoft.XinChengShop.ui.fragment.home;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.dao.HomeDao;
import com.banksoft.XinChengShop.model.base.BaseData;
import com.banksoft.XinChengShop.ui.base.XCBaseFragment;

import java.util.List;

/**
 * Created by harry_robin on 15/12/14.
 */
public abstract class HomeBaseFragment extends XCBaseFragment {
    private ProgressBar mProgressBar;


    @Override
    public View initContentView() {
        View view = setContentView();
        return view;
    }

    @Override
    public void initView(View view) {
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    }


    public abstract View setContentView();

    public class MyThread extends AsyncTask<HomeDao, String, BaseData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected BaseData doInBackground(HomeDao... params) {
            return null;
        }

        @Override
        protected void onPostExecute(BaseData baseData) {
            super.onPostExecute(baseData);
            mProgressBar.setVisibility(View.GONE);
            if (baseData != null) {
                setBaseData(baseData);
            } else {
                alert(R.string.net_error);
            }
        }
    }

    /**
     * 设置子类数据
     */
    public abstract List setBaseData(BaseData baseData);
}
