package com.banksoft.XinChengShop.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.ui.fragment.takeout.BaseFragmentInterface;
import com.banksoft.XinChengShop.utils.AnimationUtil;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 14-8-31
 * Time: 下午4:37
 * To change this template use File | Settings | File Templates.
 */
public abstract class XCBaseFragment extends Fragment implements BaseFragmentInterface{
    protected XCBaseActivity mActivity;
    public Context mContext;
    protected boolean animation = false;
    public View contentView;
    public Bundle savedState;
    public boolean cacheFlag = true;
    private LinkedList<AsyncTask> asyncTaskLinkedList;
    private String bundleKey;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (XCBaseActivity) getActivity();
        mContext = getActivity().getApplicationContext();
        if (asyncTaskLinkedList == null) {
            asyncTaskLinkedList = new LinkedList<>();
        } else {
            asyncTaskLinkedList.clear();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = initContentView();
        initView(contentView);
        initData();
        return contentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initOperation();
    }

    public abstract View initContentView();

    public abstract void initView(View view);

    public abstract void initData();

    public abstract void initOperation();

    public abstract Fragment getInstance();


    public abstract CharSequence getTitle();

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (!animation) {
            AnimationUtil.setAnimationOfRight(mActivity);
        }
    }

    /**
     * 刷新，重新请求数据
     */
    public void refreshMethod() {
        cacheFlag = false;
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (!animation) {
            AnimationUtil.setAnimationOfRight(mActivity);
        }
    }

    public void alert(int resId) {
        Toast.makeText(mContext, resId, Toast.LENGTH_SHORT).show();
    }

    public void addAsync(AsyncTask asyncTask) {
        asyncTaskLinkedList.add(asyncTask);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (asyncTaskLinkedList != null && asyncTaskLinkedList.size() > 0) {
            for (AsyncTask asyncTask : asyncTaskLinkedList) {
                if (asyncTask.getStatus() != AsyncTask.Status.FINISHED) {
                    asyncTask.cancel(true);
                }
            }
            asyncTaskLinkedList.clear();
        }
    }

}
