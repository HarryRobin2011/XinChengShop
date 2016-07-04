package com.banksoft.XinChengShop.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

/**
 * Created by harry_robin on 15/12/8.
 */
public class PopupWindowUtil {
    private View view;
    private View parentView;
    private Activity activity;
    private PopupWindow window;


    public PopupWindowUtil(XCBaseActivity activity, View view, View parentView) {
        this.view = view;
        this.parentView = parentView;
        this.activity = activity;
    }

    public void showPopupWindow() {
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);


        // 实例化一个ColorDrawable颜色为半透明
        // ColorDrawable dw = new ColorDrawable(0xb0000000);
        ColorDrawable dw = new ColorDrawable(00000000);
        window.setBackgroundDrawable(dw);


        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(parentView,
                Gravity.BOTTOM, 0, 0);
        window.setOnDismissListener(new PopwindowDismissListener());
    }

    public void dismiss() {
        if(window.isShowing()){
            window.dismiss();
        }

    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    public class PopwindowDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }

    }
}
