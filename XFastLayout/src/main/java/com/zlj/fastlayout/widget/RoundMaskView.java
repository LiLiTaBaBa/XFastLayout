package com.zlj.fastlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.zlj.fastlayout.R;

/**
 * Created by zlj on 18-5-13.
 * 防抖音的主界面MaskView
 * @since 1.0.0
 * 这只是一种思路
 * 还有一些是可以扩展的
 */
public class RoundMaskView extends View{
    private Paint mPaint;
    /**圆弧的半径*/
    private float mRadius;
    private int mRoundColor=Color.WHITE;
    //左上角半径
    private float topLeftRadius;
    //右上角半径
    private float topRightRadius;
    //左下角半径
    private float bottomLeftRadius;
    //右下角半径
    private float bottomRightRadius;

    public RoundMaskView(Context context) {
        this(context,null);
    }

    public RoundMaskView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundMaskView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**获取自定义属性*/
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundMaskView);
        mRadius=a.getDimension(R.styleable.RoundMaskView_radius,0);
        topLeftRadius=a.getDimension(R.styleable.RoundMaskView_topLeftRadius,0);
        topRightRadius=a.getDimension(R.styleable.RoundMaskView_topRightRadius,0);
        bottomLeftRadius=a.getDimension(R.styleable.RoundMaskView_bottomLeftRadius,0);
        bottomRightRadius=a.getDimension(R.styleable.RoundMaskView_bottomRightRadius,0);
        mRoundColor=a.getColor(R.styleable.RoundMaskView_roundColor,mRoundColor);
        a.recycle();
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mRoundColor);

    }

    @Override
    protected void onDraw(Canvas canvas) {
       if(mRadius>0){
           int centerX=getWidth()/2;
           int centerY=getHeight()/2;
           RectF rect=new RectF(0,0,centerX*2,centerY*2);
           //保存画布
           int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
           mPaint.setColor(mRoundColor);
           canvas.drawRect(rect,mPaint);
           mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
           mPaint.setColor(mRoundColor);
           canvas.drawRoundRect(rect,mRadius,mRadius,mPaint);
           mPaint.setXfermode(null);
           //回到画布层
           canvas.restoreToCount(sc);
       }else{
           //drawRoundPath(canvas);
           drawTopLeft(canvas);
           drawTopRight(canvas);
           drawBottomLeft(canvas);
           drawBottomRight(canvas);
       }
    }

    /**
     * 绘制左上角
     * @param canvas
     */
    private void drawTopLeft(Canvas canvas) {
        if (topLeftRadius > 0) {
            Path path = new Path();
            path.moveTo(0, topLeftRadius);
            path.lineTo(0, 0);
            path.lineTo(topLeftRadius, 0);
            path.arcTo(new RectF(0, 0, topLeftRadius * 2, topLeftRadius * 2),
                    -90, -90);
            path.close();
            canvas.drawPath(path, mPaint);
        }
    }

    /**
     * 绘制右上角
     * @param canvas
     */
    private void drawTopRight(Canvas canvas) {
        if (topRightRadius > 0) {
            int width = getWidth();
            Path path = new Path();
            path.moveTo(width - topRightRadius, 0);
            path.lineTo(width, 0);
            path.lineTo(width, topRightRadius);
            path.arcTo(new RectF(width - 2 * topRightRadius, 0, width,
                    topRightRadius * 2), 0, -90);
            path.close();
            canvas.drawPath(path, mPaint);
        }
    }

    /**
     * 绘制左下角
     * @param canvas
     */
    private void drawBottomLeft(Canvas canvas) {
        if (bottomLeftRadius > 0) {
            int height = getHeight();
            Path path = new Path();
            path.moveTo(0, height - bottomLeftRadius);
            path.lineTo(0, height);
            path.lineTo(bottomLeftRadius, height);
            path.arcTo(new RectF(0, height - 2 * bottomLeftRadius,
                    bottomLeftRadius * 2, height), 90, 90);
            path.close();
            canvas.drawPath(path, mPaint);
        }
    }

    /**
     * 绘制右下角
     * @param canvas
     */
    private void drawBottomRight(Canvas canvas) {
        if (bottomRightRadius > 0) {
            int height = getHeight();
            int width = getWidth();
            Path path = new Path();
            path.moveTo(width - bottomRightRadius, height);
            path.lineTo(width, height);
            path.lineTo(width, height - bottomRightRadius);
            path.arcTo(new RectF(width - 2 * bottomRightRadius, height - 2
                    * bottomRightRadius, width, height), 0, 90);
            path.close();
            canvas.drawPath(path, mPaint);
        }
    }


    @Deprecated
    private void drawRoundPath(Canvas canvas){
        int centerX=getWidth()/2;
        int centerY=getHeight()/2;
        //绘制四个1/4圆
        Path path1=new Path();
        path1.moveTo(0,topLeftRadius);
        path1.quadTo(0,0,topLeftRadius,0);
        path1.lineTo(0,0);
        path1.close();
        canvas.drawPath(path1,mPaint);

        Path path2=new Path();
        path2.moveTo(centerX*2,topRightRadius);
        path2.quadTo(centerX*2,0,centerX*2-topRightRadius,0);
        path2.lineTo(centerX*2,0);
        path2.close();
        canvas.drawPath(path2,mPaint);

        Path path3=new Path();
        path3.moveTo(centerX*2,centerY*2-bottomRightRadius);
        path3.quadTo(centerX*2,centerY*2,centerX*2-bottomRightRadius,centerY*2);
        path3.lineTo(centerX*2,centerY*2);
        path3.close();
        canvas.drawPath(path3,mPaint);

        Path path4=new Path();
        path4.moveTo(bottomLeftRadius,centerY*2);
        path4.quadTo(0,centerY*2,0,centerY*2-bottomLeftRadius);
        path4.lineTo(0,centerY*2);
        path4.close();
        canvas.drawPath(path4,mPaint);
    }


    /**
     * dip转换PX
     *
     * @param dip
     * @return
     */
    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dip, getResources().getDisplayMetrics());
    }

    /**
     * 设置圆弧的半径
     * @param radius
     */
    public void setRadius(float radius) {
        this.mRadius = radius;
        invalidate();
    }

    /**
     * 设置圆弧的颜色
     * @param mRoundColor
     */
    public void setRoundColor(int mRoundColor) {
        this.mRoundColor = mRoundColor;
        invalidate();
    }
}
