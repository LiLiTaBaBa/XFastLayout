package com.zlj.fastlayout.helper;

import java.math.BigDecimal;

/**
 * Created by zlj on 2021/4/26.
 * @Word：Thought is the foundation of understanding
 * @since 1.0.1
 * UI 数学计算辅助类
 */
public final class MathHelper {
    //除法运算精度
    private static final int DEFAULT_DIV_SCALE = 10;

    /**
     * 根据角度计算角度弧度
     * 转换到{@link java.lang.Math #sin}
     * @param angle
     * @return
     */
    public static float getArcAngle(int angle){
      return  (float) (Math.PI * div(angle, 180.0f));
    }

    /**
     * 精确除法运算
     * @param v1
     * @param v2
     * @return
     */
    private static  float div(float v1, float v2) {
        return div(v1, v2, DEFAULT_DIV_SCALE);
    }

    /**
     * 精确除法运算
     * @param v1
     * @param v2
     * @param scale
     * @return
     */
    private static float div(float v1, float v2, int scale) {
        if (scale < 0)
            throw new IllegalArgumentException("The scale must be a positive integer or zero");

        if (Float.compare(v2, 0.0f) == 0) return 0.0f;
        BigDecimal bgNum1 = new BigDecimal(Float.toString(v1));
        BigDecimal bgNum2 = new BigDecimal(Float.toString(v2));
        return bgNum1.divide(bgNum2, scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 两点间的距离
     * @param sx
     * @param sy
     * @param tx
     * @param ty
     * @return
     */
    public double getDistance(float sx,float sy,float tx,float ty) {
        float nx = Math.abs(tx - sx);
        float ny = Math.abs(ty - sy);
        return Math.hypot(nx, ny);
    }
}
