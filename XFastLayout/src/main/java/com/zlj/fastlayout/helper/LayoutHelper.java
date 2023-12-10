package com.zlj.fastlayout.helper;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zlj on 2021/4/25.
 * @Word：Thought is the foundation of understanding
 * @since 1.0.0
 * 设置布局参数的辅助类
 */
public final class LayoutHelper {

    /**
     * 检查View是否为NULL 为NULL直接报错
     * @param view
     */
    private static void checkViewNoNull(View view){
        if(view==null){
            throw new IllegalArgumentException("view must be not null");
        }
    }

    /**
     * 布局参数
     * 设置宽度
     * @param view
     * @param width
     */
    public static void setWidth(View view, int width) {
        checkViewNoNull(view);
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if(lp !=null){
            lp.width = width;
            view.setLayoutParams(lp);
        }
    }

    /**
     * 布局参数
     * 设置高度
     * @param view
     * @param height
     */
    public static void setHeight(View view, int height) {
        checkViewNoNull(view);
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if(lp !=null){
            lp.width = height;
            view.setLayoutParams(lp);
        }
    }


    /**
     * 设置底部的margin
     * @param view
     * @param bottomMargin
     */
    public static void setBottomMargin(View view, int bottomMargin) {
        checkViewNoNull(view);
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            lp.bottomMargin = bottomMargin;
            view.setLayoutParams(lp);
        }
    }

    /**
     * 设置左边的margin
     * @param view
     * @param leftMargin
     */
    public static void setLeftMargin(View view, int leftMargin) {
        checkViewNoNull(view);
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            lp.leftMargin = leftMargin;
            view.setLayoutParams(lp);
        }
    }

    /**
     * 设置右边的margin
     * @param view
     * @param rightMargin
     */
    public static void setRightMargin(View view, int rightMargin) {
        checkViewNoNull(view);
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            lp.rightMargin = rightMargin;
            view.setLayoutParams(lp);
        }
    }


    /**
     * 设置顶部的margin
     * @param view
     * @param topMargin
     */
    public static void setTopMargin(View view, int topMargin) {
        checkViewNoNull(view);
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            lp.topMargin = topMargin;
            view.setLayoutParams(lp);
        }
    }

    /**
     * 设置View布局参数
     * @param view
     * @param width
     * @param height
     * @param top
     * @param bottom
     * @param left
     * @param right
     */
    public void setViewLayoutParams(View view,int width,int height,int top,int bottom,int left,int right){
        checkViewNoNull(view);
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            lp.width = width;
            lp.height = height;
            lp.leftMargin = left;
            lp.rightMargin = right;
            lp.topMargin = top;
            lp.bottomMargin = bottom;
            view.setLayoutParams(lp);
        }
    }


}
