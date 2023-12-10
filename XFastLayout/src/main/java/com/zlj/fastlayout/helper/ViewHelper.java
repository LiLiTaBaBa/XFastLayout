package com.zlj.fastlayout.helper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import com.google.android.material.tabs.TabLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewParent;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by zlj on 2021/4/25.
 * @Word：Thought is the foundation of understanding
 * @since 1.0.0
 * View设置图片宽高等辅助类，后续版本功能不仅仅在此
 */
public final class ViewHelper {

    /**
     * 设置TextView右边的图片
     * @param textView
     * @param drawableId
     * @param drawablePadding
     */
    public static void setRightDrawable(TextView textView, int drawableId,int drawablePadding) {
       setRightDrawable(textView,drawableId,drawablePadding,0,0);
    }

    /**
     * 设置TextView右边的图片
     * @param textView
     * @param drawableId
     * @param drawablePadding
     * @param w
     * @param h
     */
    public static void setRightDrawable(TextView textView, int drawableId,int drawablePadding,int w,int h) {
        textView.setCompoundDrawables(null, null, getDrawable(textView,drawableId,drawablePadding,w,h), null);
    }

    /**
     * 设置TextView右边的图片
     * @param textView
     * @param drawableId
     * @param drawablePadding
     */
    public static void setTopDrawable(TextView textView, int drawableId,int drawablePadding) {
        setTopDrawable(textView,drawableId,drawablePadding,0,0);
    }

    /**
     * 设置TextView右边的图片
     * @param textView
     * @param drawableId
     * @param drawablePadding
     * @param w
     * @param h
     */
    public static void setTopDrawable(TextView textView, int drawableId,int drawablePadding,int w,int h) {
        textView.setCompoundDrawables(null, getDrawable(textView,drawableId,drawablePadding,w,h), null, null);
    }


    /**
     * 设置TextView右边的图片
     * @param textView
     * @param drawableId
     * @param drawablePadding
     */
    public static void setLeftDrawable(TextView textView, int drawableId,int drawablePadding) {
        setLeftDrawable(textView,drawableId,drawablePadding,0,0);
    }

    /**
     * 设置TextView右边的图片
     * @param textView
     * @param drawableId
     * @param drawablePadding
     * @param w
     * @param h
     */
    public static void setLeftDrawable(TextView textView, int drawableId,int drawablePadding,int w,int h) {
        textView.setCompoundDrawables(getDrawable(textView,drawableId,drawablePadding,w,h), null, null, null);
    }


    /**
     * 设置TextView右边的图片
     * @param textView
     * @param drawableId
     * @param drawablePadding
     */
    public static void setBottomDrawable(TextView textView, int drawableId,int drawablePadding) {
        setBottomDrawable(textView,drawableId,drawablePadding,0,0);
    }

    /**
     * 设置TextView右边的图片
     * @param textView
     * @param drawableId
     * @param drawablePadding
     * @param w
     * @param h
     */
    public static void setBottomDrawable(TextView textView, int drawableId,int drawablePadding,int w,int h) {
        textView.setCompoundDrawables(null, null, null, getDrawable(textView,drawableId,drawablePadding,w,h));
    }

    /**
     * 根据位置获取合适Drawable
     * @param textView
     * @param drawableId
     * @param drawablePadding
     * @param w
     * @param h
     * @return
     */
    private static Drawable getDrawable(TextView textView,int drawableId,int drawablePadding,int w,int h) {
        Resources res = textView.getResources();
        Drawable drawable = res.getDrawable(drawableId);
        textView.setCompoundDrawablePadding(drawablePadding);
        // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
        drawable.setBounds(0, 0, w==0?drawable.getMinimumWidth():w , h==0?drawable.getMinimumHeight():h);
        return drawable;
    }

    /**
     *  下划线
     * @param textView
     */
    public static void drawUnderline(TextView textView) {
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    /**
     * 中划线
     * @param textView
     */
    public static void drawStrikethrough(TextView textView) {
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    /**
     * 设置ImageView的滤镜颜色
     * @param imageView
     * @param color
     */
    public static void setColorFilter(ImageView imageView,int color){
        imageView.setColorFilter(color);
    }

    /**
     * 输入框文本变化监听
     * @param editText
     * @param imgClear
     */
    public static void setTextClearWatcher(final EditText editText, final ImageView imgClear) {
        imgClear.setVisibility(View.GONE);
        imgClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                editText.setText("");
            }
        });
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                if (!"".equals(arg0.toString())) {
                    imgClear.setVisibility(View.VISIBLE);
                } else {
                    imgClear.setVisibility(View.GONE);
                }

            }
        });
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!TextUtils.isEmpty(editText.getEditableText().toString())) {
                    imgClear.setVisibility(View.VISIBLE);
                } else {
                    imgClear.setVisibility(View.GONE);
                }
            }
        });
    }


    /**
     * findSuitableParent
     * @param view
     * @return
     */
    public static ViewGroup findSuitableParent(View view) {
        ViewGroup fallback = null;
        do {
            if (view instanceof FrameLayout) {
                if (view.getId() == Window.ID_ANDROID_CONTENT) {
                    return (ViewGroup) view;
                } else {
                    fallback = (ViewGroup) view;
                }
            }
            if (view != null) {
                final ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
        } while (view != null);
        return fallback;
    }

    /**
     *动态修改tabLayout的宽度 通过反射
     * @param tabLayout
     * @param context
     * @param marginStart
     * @param marginEnd
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static void setTabLayoutIndicatorWidth(TabLayout tabLayout, Context context, float marginStart, float marginEnd)
            throws NoSuchFieldException, IllegalAccessException {
        Class<?> tablayout = tabLayout.getClass();
        Field tabStrip = tablayout.getDeclaredField("mTabStrip");
        tabStrip.setAccessible(true);
        LinearLayout ll_tab = (LinearLayout) tabStrip.get(tablayout);
        for (int i = 0; i < ll_tab.getChildCount(); i++) {
            View child = ll_tab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                params.setMarginStart(DensityUtil.dip2px(context, marginStart));
                params.setMarginEnd(DensityUtil.dip2px(context, marginEnd));
            }
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    /**
     * 手指是否在View内
     * @param view
     * @param event
     * @return
     */
    public static boolean fingerIsOnView(View view, MotionEvent event){
        if(event.getX()<=view.getLeft())return false;
        if(event.getX()>=view.getRight())return false;
        if(event.getY()<=view.getTop())return false;
        if(event.getY()>=view.getBottom())return false;
        return true;
    }

    /**
     * 裁剪原型View
     * @param view
     */
    public static void clipCircle(View view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setOval(0,0,view.getWidth(), view.getHeight());
                }
            };
            view.setOutlineProvider(viewOutlineProvider);
            view.setClipToOutline(true);
        }
    }

    /**
     * 裁剪圆角矩形View
     * @param view
     */
    public static void clipRoundRect(View view, final float radius){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0,0,view.getWidth(), view.getHeight(),radius);
                }
            };
            view.setOutlineProvider(viewOutlineProvider);
            view.setClipToOutline(true);
        }
    }

}
