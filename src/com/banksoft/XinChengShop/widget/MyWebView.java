package com.banksoft.XinChengShop.widget;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * author: Robin
 * createTime: 2015/5/16.
 * desc: �Զ���webView Ƕ��Scrollview
 * version:
 */
public class MyWebView extends WebView {

    private Context context;
    private boolean isScroll = true;// webview

    PointF downP = new PointF();
    PointF curP = new PointF();

    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //webview������������ɻ���
                isScroll = true ;
                curP.x = event.getX();
                curP.y = event.getY();
                //֪ͨ���ؼ����ڽ��е��Ǳ��ؼ��Ĳ�������Ҫ���ҵĲ������и���
                getParent().requestDisallowInterceptTouchEvent(true);
                break;

            case MotionEvent.ACTION_MOVE:
                float lastY = event.getY(event.getPointerCount() - 1);
                if (isBottom())//�������ײ���������Ϊ���ܹ���
                    isScroll = false;
                // �������ײ�������ʼ���Ϲ�������ôwebview���Թ���
                if (isBottom() && (curP.y - lastY < 0))
                    isScroll = true;
                if(isTop())//�������������ٻ�
                    isScroll = false ;
                if(isTop() && (curP.y - lastY >0))//���������������»������Ի���
                    isScroll = true ;
                getParent().requestDisallowInterceptTouchEvent(isScroll);
                break;
            case MotionEvent.ACTION_UP:
                isScroll = false ;
                break;
        }

        return super.onTouchEvent(event);
    }

    /**
     * �ж��Ƿ�WebView��ײ�
     */
    private boolean isBottom() {
        // WebView���ܸ߶�
        float contentHeight = getContentHeight() * getScale();
        // WebView���ָ߶�
        float currentHeight = getHeight() + getScrollY();
        // ֮��Ĳ��С��2����Ϊ�������ײ�
        return contentHeight - currentHeight < 1;
    }

    private boolean isTop(){
        //��ScrollYΪ0�ǵ��ﶥ��
        return getScrollY() == 0 ;
    }
}
