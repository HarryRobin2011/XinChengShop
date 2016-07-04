package com.banksoft.XinChengShop.widget.webImageView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;

import java.util.HashMap;

/**
 * Created by Robin on 2014/11/10.
 */
public class VerticalTableView extends ViewGroup {
    private int STARTX = 0;
    private int STARTY = 0;
    private int BORDER = 2;

    private int mCol;//列数
    private int mRow;//行数
    private HashMap<String,Object> dataMap;

    public VerticalTableView(Context context, HashMap<String, Object> dataMap) {
        super(context);
        this.dataMap = dataMap;
        addOtherView(context);
    }

    public VerticalTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalTableView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void addOtherView(Context mContext){
        String value;
        mCol = 2;
        mRow = dataMap.keySet().size();
        for(int i = 0; i < mRow; i++){
            for (int j = 0;j < mCol ; j++){
                TextView textView = new TextView(mContext);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(R.color.text_gray);
                if(j % 2 == 0){
                    textView.setBackgroundColor(getResources().getColor(R.color.tab_background_color));
                    textView.setText(dataMap.keySet().toArray()[i].toString());
                }else{
                    textView.setBackgroundResource(R.color.white);
                    value = dataMap.get(dataMap.keySet().toArray()[i].toString()).toString();
                    textView.setText(value);
                }
                this.addView(textView);
            }
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(BORDER);
        paint.setColor(R.color.text_gray);

        canvas.drawRect(STARTX,STARTY,STARTX + getWidth(),STARTY + getHeight(),paint);
        for (int i = 0; i<mRow;i++){                                           //行分割线
            canvas.drawLine(STARTX,(i*getHeight()/mRow),getWidth(),(i*getHeight()/mRow),paint);
        }

        for (int j = 0;j<mCol;j++){     //列分割线
            canvas.drawLine((getWidth()/mCol)*j,STARTY,(getWidth()/mCol)*j,getHeight(),paint);
        }

        super.dispatchDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int x = STARTX + BORDER;
        int y = STARTY + BORDER;
        int index = 0;
        mCol = 2;
        mRow = dataMap.keySet().size();
        for(int i=0;i<getChildCount();i++){
            View child = getChildAt(i);

            child.layout(x,y,(x+getWidth()/mCol-BORDER*2),y+getHeight()/mRow - BORDER*2);
            if(index >= mCol - 1){ //
                index = 0;
                x = STARTX+BORDER;
                y +=getHeight()/mRow;
            }else{
                index++;
                x += getWidth()/mCol;
            }
        }
    }


    public int getRow() {
        return mRow;
    }

    public void setRow(int row) {
        mRow = row;
    }

    public int getCol() {
        return mCol;
    }

    public void setCol(int col) {
        mCol = col;
    }
}
