package com.zlj.fastlayout.helper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import androidx.core.view.ViewCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.reflect.Field;
import me.drakeet.support.toast.ToastCompat;

/**
 * Created by zlj on 2021/4/25.
 * @Word：Thought is the foundation of understanding
 * @since 1.0.0
 * 吐司弹出辅助类
 */
public final class ToastHelper {

    /**
     * toast
     * @param context
     * @param str
     */
    public static void toast(Context context, CharSequence str) {
        toast(context, str,false);
    }

    /**
     * toast
     * @param context
     * @param id
     */
    public static void toast(Context context, int id) {
        toast(context, context.getString(id),false);
    }

    /**
     * toast
     * @param context
     * @param str
     */
    public static void toast(Context context, String str) {
        toast(context, str,false);
    }

    /**
     * 单例显示Toast，不改变Toast的样式
     * @param context
     * @param text
     * @param isCenter
     */
    public static void toast(Context context, CharSequence text,boolean isCenter) {
        if (isFastDoubleClick()) return;
        ToastCompat toast=ToastCompat.makeText(context, text, Toast.LENGTH_SHORT);
        if(isCenter){
            toast.setGravity(Gravity.CENTER,0,0);
        }
        toast.show();
    }

    /**
     * 防止连续点击跳转两个页面
     */
    private static long lastClickTime;
    private static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    //-------------------------------------------------------start--------------------------------------------------------------
    private Builder builder;
    public ToastHelper(Builder builder) {
        this.builder=builder;
    }

    /**
     * 显示Toast弹出
     */
    public void show(){
        if (isFastDoubleClick()) return;
        ToastCompat toast=ToastCompat.makeText(builder.mContext, builder.charSequence, Toast.LENGTH_SHORT);
        if(builder.customView==null&&builder.isCustom){
            FrameLayout view =new FrameLayout(builder.mContext);
            TextView tv_toast1 = new TextView(builder.mContext);
            FrameLayout.LayoutParams lpp=new FrameLayout.LayoutParams(builder.width,builder.height);
            tv_toast1.setTag("textView");
            tv_toast1.setPadding(dipToPx(20),dipToPx(10),dipToPx(20),dipToPx(10));
            lpp.gravity=builder.textGravity;
            view.addView(tv_toast1,lpp);
            toast.setView(view);
            if(builder.backgroundDrawable!=null){
                ViewCompat.setBackground(tv_toast1,builder.backgroundDrawable);
            }
            tv_toast1.setTextSize(TypedValue.COMPLEX_UNIT_SP,builder.textSize);
            tv_toast1.setTextColor(builder.textColor);
            ViewGroup.LayoutParams lp=new ViewGroup.LayoutParams(builder.width,builder.height);
            view.setLayoutParams(lp);
            tv_toast1.setGravity(Gravity.CENTER);
            tv_toast1.setText(builder.charSequence);
        }else if(builder.isCustom){
            toast.setView(builder.customView);
            TextView tv_toast1 = builder.customView.findViewWithTag("textView");
            ViewGroup.LayoutParams lp=new ViewGroup.LayoutParams(builder.width,builder.height);
            builder.customView.setLayoutParams(lp);
            tv_toast1.setText(builder.charSequence);
        }
        makeToastSelfViewAnim(toast.getBaseToast(),builder.animationStyle);
        if(builder.gravity!=-200){
            toast.setGravity(builder.gravity|Gravity.FILL_HORIZONTAL,builder.xOffset,builder.yOffset);
        }else{
            if(builder.isCustomSize){
                toast.setGravity(Gravity.FILL_HORIZONTAL|Gravity.BOTTOM,0,
                       dipToPx(80));
            }
        }
        toast.show();
    }

    private int dipToPx(int size) {
      return   (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,size,
                builder.mContext.getResources().getDisplayMetrics());
    }

    /**创建Builder对象*/
    public static Builder create(Context context){
        return new ToastHelper.Builder(context);
    }

    public static class Builder{
        /**设置View的对齐方式*/
        private int gravity=-200;
        /**TextView的对齐方式*/
        private int textGravity=Gravity.CENTER;
        /**自定义的View*/
        private View customView;
        /**X方向偏移的具体*/
        private int xOffset=0;
        /**Y方向偏移的距离*/
        private int yOffset=0;
        /**自定义的View*/
        private boolean  isCustom;
        /**需要显示的文字*/
        private CharSequence charSequence;
        /**文字的颜色*/
        private int textColor= Color.parseColor("#333333");
        /**需要显示的文字大小*/
        private int textSize=14;
        /**toast的背景*/
        private Drawable backgroundDrawable;
        /**显示宽度*/
        private int width=ViewGroup.LayoutParams.WRAP_CONTENT;
        /**显示高度*/
        private int height=ViewGroup.LayoutParams.WRAP_CONTENT;
        /**动画设置*/
        private  int animationStyle=android.R.style.Animation_Toast;
        private boolean isCustomSize=false;
        private Context mContext;
        public Builder(Context context) {
            this.mContext=context;
        }

        public Builder setGravity( int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setView(View customView) {
            this.customView = customView;
            this.isCustom=true;
            return this;
        }

        public Builder setText(CharSequence charSequence) {
            this.charSequence = charSequence;
            return this;
        }

        public Builder setCustom(boolean custom) {
            this.isCustom = custom;
            return this;
        }

        public Builder setOffsetX(int xOffset) {
            this.xOffset = xOffset;
            return this;
        }
        public Builder setOffsetY(int yOffset) {
            this.yOffset = yOffset;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setCustomSize(boolean isCustomSize) {
            this.isCustomSize=isCustomSize;
            return this;
        }

        public Builder setAnimationStyle(int animationStyle) {
            this.animationStyle = animationStyle;
            return this;
        }

        public Builder setBackgroundDrawable(Drawable backgroundDrawable) {
            this.backgroundDrawable = backgroundDrawable;
            return this;
        }

        public Builder setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public Builder setTextGravity(int textGravity) {
            this.textGravity = textGravity;
            return this;
        }

        public ToastHelper builder(){
            return new ToastHelper(this);
        }
    }


    /**
     * 此方法在Android P上面失效
     * 设置自定义View和Animation
     * @param animationStyle   动画资源id
     */
    private void makeToastSelfViewAnim(Toast toast,int animationStyle){
        try {
            Field mTNField = toast.getClass().getDeclaredField("mTN");
            mTNField.setAccessible(true);
            Object mTNObject = mTNField.get(toast);
            Class tnClass = mTNObject.getClass();
            Field paramsField = tnClass.getDeclaredField("mParams");
            /**由于WindowManager.LayoutParams mParams的权限是private*/
            paramsField.setAccessible(true);
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) paramsField.get(mTNObject);
            layoutParams.windowAnimations = animationStyle;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------end---------------------------------------------------------------
}
