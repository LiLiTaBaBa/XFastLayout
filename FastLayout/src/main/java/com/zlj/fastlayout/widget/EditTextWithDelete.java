package com.zlj.fastlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.zlj.fastlayout.R;


/**
 * Created by ZhangLiJun on 2018/6/12 0012.
 * @email：282384507@qq.com
 * @Word：Thought is the foundation of understanding
 * @since 1.0.0
 * 带有删除按钮的EditText
 */
public class EditTextWithDelete extends EditText {

    private static final int DRAWABLE_LEFT = 0;
    private static final int DRAWABLE_TOP = 1;
    private static final int DRAWABLE_RIGHT = 2;
    private static final int DRAWABLE_BOTTOM = 3;
    private Drawable mClearDrawable;


    public EditTextWithDelete(Context context) {
        super(context);
        init(context,null);
    }

    public EditTextWithDelete(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public EditTextWithDelete(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EditTextWithDelete);
        mClearDrawable=a.getDrawable(R.styleable.EditTextWithDelete_clearDrawable);
        a.recycle();

        if(mClearDrawable==null){
            mClearDrawable = getResources().getDrawable(R.drawable.login_delete);
        }
    }


    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setClearIconVisible(hasFocus() && length() > 0);
        if(onTextChangeListener!=null){
            onTextChangeListener.onTextChanged(text,start,lengthBefore,lengthAfter);
        }
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setClearIconVisible(focused && length() > 0);
        if(onTextChangeListener!=null){
            onTextChangeListener.onTextChanged(null,0,0,0);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                Drawable drawable = getCompoundDrawables()[DRAWABLE_RIGHT];
                if (drawable != null && event.getX() <= (getWidth() - getPaddingRight()) && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())) {
                    setText("");
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }


    public void setClearIconVisible(boolean visible) {
        setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[DRAWABLE_LEFT], getCompoundDrawables()[DRAWABLE_TOP]
                , visible ? mClearDrawable : null, getCompoundDrawables()[DRAWABLE_BOTTOM]);
    }

    public interface OnTextChangeListener{
         void onTextChanged(CharSequence s, int start, int before, int count);
    }

    private  OnTextChangeListener onTextChangeListener;

    public void setOnTextChangeListener(OnTextChangeListener onTextChangeListener){
        this.onTextChangeListener=onTextChangeListener;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mClearDrawable=null;
        onTextChangeListener=null;
    }
}