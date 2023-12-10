package com.zlj.fastlayout.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by zlj on 2021/4/24 0024
 * @Word：Thought is the foundation of understanding
 * @since 1.0.0
 * 支持四个方向圆角的ImageView
 */
public class RoundedImageView extends com.makeramen.roundedimageview.RoundedImageView {
    public RoundedImageView(Context context) {
        super(context);
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
