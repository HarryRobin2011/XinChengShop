package com.banksoft.XinChengShop.widget;

import android.app.Activity;
import android.graphics.Point;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.PopupFilterByWindowsAdapter;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 14-8-5
 * Time: 上午5:22
 * To change this template use File | Settings | File Templates.
 */
public class MyPopupWindow {
    public static int selectPosition;

//    public static void showPopupWindows(final Activity mActivity, List dataList, View anchor, final Handler mHandler) {
//        final int[] history = {-1};
//        Point point = new Point();
//        mActivity.getWindowManager().getDefaultDisplay().getSize(point);
//        final View windowOne = mActivity.getLayoutInflater().inflate(R.layout.popup_windows_layout, null);
//        final PopupWindow popupWindow = new PopupWindow(windowOne, point.x, point.y / 2, true);
//        popupWindow.setOutsideTouchable(true);
//
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
//        ListView groupListView = (ListView) windowOne.findViewById(R.id.mListViewGroup);
//        final PopupWindowsAdapter adapter = new PopupWindowsAdapter(mActivity.getApplicationContext(), dataList);
//        adapter.setPopupType(PopupWindowsAdapter.PopupType.GROUP);
//
////        groupListView.addHeaderView(headView);
//        groupListView.setAdapter(adapter);
//        groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout);
//                if (history[0] != -1) {
//                    View historyView = adapter.viewHashMap.get(history[0]);
//                    LinearLayout historyLayout = (LinearLayout) historyView.findViewById(R.id.layout);
//                    historyLayout.setBackgroundResource(R.color.white);
//                }
//                layout.setBackgroundResource(R.color.text_bg_color);
//                history[0] = position;
//
//                LocalProductTypeApp currentProductTypeApp = (LocalProductTypeApp) adapter.getItem(position);
//                if (!currentProductTypeApp.isLeaf()) {
//                    showChildView(windowOne, currentProductTypeApp.getList());
//                } else {
//                    mHandler.sendMessage(mHandler.obtainMessage(0, currentProductTypeApp));
//                    popupWindow.dismiss();
//                }
//
//            }
//
//            //
//            private void showChildView(View parent, List dataList) {
//                ListView childListView = (ListView) parent.findViewById(R.id.mListViewChild);
//                FrameLayout childLayout = (FrameLayout) windowOne.findViewById(R.id.childLayout);
//                childLayout.setBackgroundResource(R.color.text_bg_color);
//                final PopupWindowsAdapter childAdapter = new PopupWindowsAdapter(mActivity.getApplicationContext(), dataList);
//                childAdapter.setPopupType(PopupWindowsAdapter.PopupType.CHILD);
//                childListView.setAdapter(childAdapter);
//                childListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        LocalProductTypeApp localProductTypeApp = (LocalProductTypeApp) childAdapter.getItem(position);
//                        mHandler.sendMessage(mHandler.obtainMessage(0, localProductTypeApp));
//                        popupWindow.dismiss();
//                    }
//                });
//            }
//        });
//        popupWindow.showAsDropDown(anchor, 5, 1);
//        popupWindow.update();
//    }


    public static void showOrderPopupWindows(final Activity mActivity, List dataList, View anchor) {
        final int[] history = {-1};
        Point point = new Point();
        mActivity.getWindowManager().getDefaultDisplay().getSize(point);
        final View windowOne = mActivity.getLayoutInflater().inflate(R.layout.popup_by_windows_layout, null);
        final PopupWindow popupWindow = new PopupWindow(windowOne, point.x, point.y, true);
        popupWindow.setOutsideTouchable(true);

        popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);

        PoponDismissListener poponDismissListener = new PoponDismissListener(mActivity,1f);
        popupWindow.setOnDismissListener(poponDismissListener);


        ListView groupListView = (ListView) windowOne.findViewById(R.id.mListViewGroup);
        final PopupFilterByWindowsAdapter adapter = new PopupFilterByWindowsAdapter(mActivity.getApplicationContext(), dataList);
        adapter.selectPosition = selectPosition;
        groupListView.setAdapter(adapter);
        groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((ItemPopuwindows)mActivity).itemOnClickLinster(position);
                popupWindow.dismiss();
            }
        });

        popupWindow.showAsDropDown(anchor, 5, 1);
        popupWindow.update();
    }

    public interface ItemPopuwindows{
        public void itemOnClickLinster(int position);
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public static void backgroundAlpha(Activity activity, float bgAlpha)
    {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     * @author cg
     *
     */
    private static class PoponDismissListener implements PopupWindow.OnDismissListener{
        private Activity activity;
        private float alpha;
        public PoponDismissListener(Activity activity,float alpha){
            this.activity = activity;
            this.alpha = alpha;
        }
        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(activity,alpha);
        }

    }
}