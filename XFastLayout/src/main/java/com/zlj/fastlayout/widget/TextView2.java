package com.zlj.fastlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;
import com.zlj.fastlayout.R;

/**
 * Created by zlj on 2021/2/21.
 * @Word：Thought is the foundation of understanding
 * @since 1.0.0
 * 增强功能 可以在布局文件中设置left、top、right、bottom中drawable的宽高
 */
public class TextView2 extends TextView {
    private int leftDrawableWidth;
    private int leftDrawableHeight;
    private int rightDrawableWidth;
    private int rightDrawableHeight;
    private int topDrawableWidth;
    private int topDrawableHeight;
    private int bottomDrawableWidth;
    private int bottomDrawableHeight;

    public TextView2(Context context) {
        this(context,null);
    }

    public TextView2(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public TextView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TextView2);
        topDrawableWidth= (int) typedArray.getDimension(R.styleable.TextView2_drawableTopWidth,0);
        topDrawableHeight= (int) typedArray.getDimension(R.styleable.TextView2_drawableTopHeight,0);
        leftDrawableWidth= (int) typedArray.getDimension(R.styleable.TextView2_drawableLeftWidth,0);
        leftDrawableHeight= (int) typedArray.getDimension(R.styleable.TextView2_drawableLeftHeight,0);
        rightDrawableWidth= (int) typedArray.getDimension(R.styleable.TextView2_drawableRightWidth,0);
        rightDrawableHeight= (int) typedArray.getDimension(R.styleable.TextView2_drawableRightHeight,0);
        bottomDrawableWidth= (int) typedArray.getDimension(R.styleable.TextView2_drawableBottomWidth,0);
        bottomDrawableHeight= (int) typedArray.getDimension(R.styleable.TextView2_drawableBottomHeight,0);
        typedArray.recycle();
        Drawable[] drawables=getCompoundDrawables();
        this.setCompoundDrawablesWithIntrinsicBounds(drawables[0],drawables[1],drawables[2],drawables[3]);
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds( Drawable left, Drawable top,  Drawable right,  Drawable bottom) {
        if (left != null) {
            left.setBounds(0, 0, leftDrawableWidth==0?left.getIntrinsicWidth():leftDrawableWidth,
                    leftDrawableHeight==0?left.getIntrinsicHeight():leftDrawableHeight);
        }
        if (right != null) {
            right.setBounds(0, 0, rightDrawableWidth==0?right.getIntrinsicWidth():rightDrawableWidth,
                    rightDrawableHeight==0?right.getIntrinsicHeight():rightDrawableHeight);
        }
        if (top != null) {
            top.setBounds(0, 0, topDrawableWidth==0?top.getIntrinsicWidth():topDrawableWidth,
                    topDrawableHeight==0?top.getIntrinsicHeight():topDrawableHeight);
        }
        if (bottom != null) {
            bottom.setBounds(0, 0, bottomDrawableWidth==0?bottom.getIntrinsicWidth():bottomDrawableWidth,
                    bottomDrawableHeight==0?bottom.getIntrinsicHeight():bottomDrawableHeight);
        }
        setCompoundDrawables(left, top, right, bottom);
    }

}
