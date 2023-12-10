package com.zlj.fastlayout.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;
import com.zlj.fastlayout.R;

/**
 * Created by zlj on 2021/2/18 0018
 * @Word：Thought is the foundation of understanding
 *  带有分割线的GridLayout
 */
public class GridLayout2 extends GridLayout {
    //画笔
    private Paint mPaint;
    private boolean verticalDivider;
    private boolean horizontalDivider;
    //分割线的颜色
    private int mDividerColor=Color.parseColor("#dddddd");
    //分割线的宽度
    private int mDividerWidth=1;
    public GridLayout2(Context context) {
        this(context,null);
    }

    public GridLayout2(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GridLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GridLayout2);
        mDividerWidth=a.getDimensionPixelSize(R.styleable.GridLayout2_dividerWidth,mDividerWidth);
        mDividerColor=a.getColor(R.styleable.GridLayout2_dividerColor,mDividerColor);
        a.recycle();
        mPaint=new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mDividerWidth);
        mPaint.setColor(mDividerColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        //绘制分割线
        for (int i = 0; i < getChildCount(); i++) {
            View childView=getChildAt(i);
            if (childView.getVisibility() == View.GONE) continue;
            LayoutParams lp= (LayoutParams) childView.getLayoutParams();
            if(childView.getBottom()!=getMeasuredHeight()){
                int startY= (int) (childView.getBottom()+mPaint.getStrokeWidth());
                canvas.drawLine(childView.getLeft(),startY,childView.getRight(),startY,mPaint);
            }
            if(childView.getLeft()!=0){
                int startX= (int) (childView.getLeft()-mPaint.getStrokeWidth());
                canvas.drawLine(startX,childView.getTop(),startX,childView.getBottom(),mPaint);
            }
            //最准确的做法是获取每个cell的宽高

        }

    }


    /**
     * 绘制垂直方向的分割线
     * @param canvas
     */
    private void drawVerticalLine(Canvas canvas) {
        int bottom=-1;
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() == View.GONE) continue;
            //绘制垂直分割线
            if(childView.getBottom()!=bottom){
                int startY= (int) (childView.getBottom()+mPaint.getStrokeWidth());
                //最后一行      可以根据属性配置以及可选择的选择绘制那些分割线
                if(childView.getBottom()!=getMeasuredHeight()){
                    //绘制一条整线水平方向
                    canvas.drawLine(0,startY,getMeasuredWidth(),startY,mPaint);
                }
                bottom=childView.getBottom();
            }
        }

    }

    /**
     * 绘制水平方向的分割线
     * @param canvas
     */
    private void drawHorizontalLine(Canvas canvas) {
        int left=-1;
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() == View.GONE) continue;
            //绘制水平分割线
            if(childView.getLeft()!=left){
                int startX= (int) (childView.getLeft()-mPaint.getStrokeWidth());
                //第一列
                if(childView.getLeft()!=0){
                    //绘制一条整线垂直方向
                    canvas.drawLine(startX,0,startX,getMeasuredHeight(),mPaint);
                }
                left=childView.getLeft();
            }
        }
    }
}
