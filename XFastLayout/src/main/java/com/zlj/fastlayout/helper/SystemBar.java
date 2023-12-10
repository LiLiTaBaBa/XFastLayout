package com.zlj.fastlayout.helper;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import androidx.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.zlj.fastlayout.helper.eye.Eyes;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by zlj on 2021/4/25.
 * @Word：Thought is the foundation of understanding
 * @since 1.0.0
 * 系统栏(状态栏、导航栏、标题栏、以及屏幕显示参数等等) 操作辅助类
 *  {@link  android.view.View #setSystemUiVisibility(int)}
 *  SYSTEM_UI_FLAG_LOW_PROFILE 低调模式，隐藏不重要的状态栏图标
 *  SYSTEM_UI_FLAG_HIDE_NAVIGATION 隐藏导航栏，点击屏幕任意区域，导航栏将重新出现
 *  SYSTEM_UI_FLAG_FULLSCREEN 隐藏状态栏，从状态栏位置下拉，状态栏将重新出现
 *  SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION    将布局内容拓展到导航栏和状态栏的后面
 *  SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  将布局内容拓展到状态栏的后面
 *  SYSTEM_UI_FLAG_LAYOUT_STABLE
 *            稳定布局，需要配合SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION和
 *            SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN使用，同时设置布局的android:fitsSystemWindows属性。
 *  SYSTEM_UI_FLAG_IMMERSIVE  配合SYSTEM_UI_FLAG_HIDE_NAVIGATION和SYSTEM_UI_FLAG_FULLSCREEN使用
 *  SYSTEM_UI_FLAG_IMMERSIVE_STICKY
 *
 * 就是需要做到兼容、适配大部分机型   达到一个稳定的状态
 * 明天把这些方法测试一下
 */
public final class SystemBar {


    /**
     * 设置横屏
     * @param activity The activity.
     */
    public static void setLandscape(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 设置竖屏
     * @param activity The activity.
     */
    public static void setPortrait(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 是否全屏
     * @return {@code true}: yes<br>{@code false}: no
     * @param activity
     */
    public static boolean isFullScreen(Activity activity) {
        return (activity.getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN)
                == WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }

    /**
     * 是否横屏
     * @return {@code true}: yes<br>{@code false}: no
     * @param context
     */
    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 是否竖屏
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * Return the rotation of screen.
     * @param activity The activity.
     * @return the rotation of screen
     */
    public static int getScreenRotation(@NonNull final Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
            default:
                return 0;
        }
    }

    /**
     * 获取状态栏的高度
     * @param context
     * @return
     */

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取ActionBar的高度
     * @param context
     * @return
     */
    public static float getActionBarHeight(Context context){
        TypedArray actionbarSizeTypedArray = context.obtainStyledAttributes(new int[] { android.R.attr.actionBarSize });
        float h = actionbarSizeTypedArray.getDimension(0, 0);
        actionbarSizeTypedArray.recycle();
        return h;
    }

    /**
     * 获得屏幕宽
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获得屏幕高
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;

    }

    /**
     * 获取底部系统导航栏的高度
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        if (!checkDeviceHasNavigationBar(context)) return 0;
        if(context instanceof Activity){
            if(!isNavigationBarShow((Activity) context)){
                return 0;
            }
        }
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * 判断是否显示了导航栏
     * (说明这里的context 一定要是activity的context 否则类型转换失败)
     *
     * @param activity
     * @return
     */
    public static boolean isNavigationBarShow(Activity activity) {
        if (null == activity) {
            return false;
        }
        /**
         * 获取应用区域高度
         */
        Rect outRect1 = new Rect();
        try {
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return false;
        }
        int activityHeight = outRect1.height();
        /**
         * 获取状态栏高度
         */
        int statusBarHeight = getStatusBarHeight(activity);
        /**
         * 屏幕物理高度 减去 状态栏高度
         */
        int remainHeight = getScreenSize(activity,true)[1] - statusBarHeight;
        /**
         * 剩余高度跟应用区域高度相等 说明导航栏没有显示 否则相反
         */
        if (activityHeight == remainHeight) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * 获取屏幕的实际的宽高
     * @param context
     * @param useDeviceSize
     * @return
     */
    public static int[] getScreenSize(Context context, boolean useDeviceSize) {

        int[] size = new int[2];

        WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        // since SDK_INT = 1;
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;

        if (!useDeviceSize) {
            size[0] = widthPixels;
            size[1] = heightPixels - getStatusBarHeight(context);

            return size;
        }

        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
            try {
                widthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(d);
                heightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(d);
            } catch (Exception ignored) {
            }
        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 17)
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(d, realSize);
                widthPixels = realSize.x;
                heightPixels = realSize.y;
            } catch (Exception ignored) {
            }
        size[0] = widthPixels;
        size[1] = heightPixels;
        return size;
    }

    /**
     * 获取是否存在NavigationBar
     * @param context
     * @return
     */
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;

    }

    /**
     * 设置屏幕全屏
     * @param activity The activity.
     */
    public static void setFullScreen(@NonNull final Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 设置状态栏的颜色 并且把状态栏的位置兼容不遮挡
     * @param activity
     * @param color
     */
    public static void setStatusBarColor(Activity activity,int color) {
        //如果系统在4.4以上的话使用如下方法实现沉浸式状态栏  可以动态设置状态栏的颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 获取状态栏高度
            int statusBarHeight = getStatusBarHeight(activity);
            View rectView = new View(activity);
            // 绘制一个和状态栏一样高的矩形，并添加到视图中
            LinearLayout.LayoutParams params
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            rectView.setLayoutParams(params);
            //设置状态栏颜色（该颜色根据你的App主题自行更改）
            rectView.setBackgroundColor(color);
            // 添加矩形View到布局中
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(rectView);
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT)).getChildAt(0);
            if(rootView!=null){
                rootView.setFitsSystemWindows(true);
                rootView.setClipToPadding(true);
            }
        }
    }

    /**
     * 设置导航栏的背景色
     * @param activity
     * @param color
     */
    public static void setNavigationBarColor(Activity activity,int color) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                //activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                activity.getWindow().setNavigationBarColor(color);
            }
    }

    /**
     * 设置状态栏是否为亮色模式 状态栏的文字可以设置成黑色
     * @param activity
     * @param color
     */
    public static void setStatusBarLightMode(Activity activity, int color) {
        Eyes.setStatusBarLightMode(activity,color);
    }

    /**
     * 修改NavigationBar按键颜色 两色可选【黑，白】
     * @param activity
     * @param light
     */
    public static void setLightNavigationBar (Activity activity,boolean light) {
        int vis = activity.getWindow().getDecorView().getSystemUiVisibility();
        if (light) {
            // 黑色
            vis |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
        } else {
            //白色
            vis &= ~ View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(vis);
    }

    /**
     * 隐藏状态栏
     * @param activity
     */
    public static void hideStatusBar(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN) ;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
//        activity.getWindow().getDecorView().setSystemUiVisibility(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 显示状态栏
     * @param activity
     */
    public static void showStatusBar(Activity activity){
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }

    /**
     * 隐藏导航栏
     * @param activity
     */
    public static void hideNavigationBar(Activity activity){
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) { // lower api
            View v = activity.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.  | View.SYSTEM_UI_FLAG_FULLSCREEN
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY ;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    /**
     * 显示导航栏
     * @param activity
     */
    public static void showNavigationBar(Activity activity){
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 开启沉浸模式  全屏状态
     * @param activity
     */
    public static void openImmersionStyle(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                //适配刘海屏
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.P){
                    Window window = activity.getWindow();
                    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    WindowManager.LayoutParams lp = window.getAttributes();
                    lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
                    window.setAttributes(lp);
                }
        }
    }

    /**
     * 关闭沉浸模式 非全屏
     * @param activity
     */
    public static void closeImmersionStyle(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.P){
                Window window = activity.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT;
                window.setAttributes(lp);
            }
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            );
        }
    }

    /**
     * 设置导航栏以及状态栏透明
     * @param activity
     */
    public static void setNavigationBarStatusBarTranslucent(Activity activity){
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = activity.getActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
    }

    //------------------------对刘海屏的手机适配包括 小米、VIVO、OPPO、华为----------------------------------------------------------------------

    private static final int VIVO_NOTCH = 0x00000020;//是否有刘海
    private static final int VIVO_FILLET = 0x00000008;//是否有圆角

    /**
     * 判断是否是刘海屏
     * @return
     */
    public static boolean isNotchScreen(Activity activity){
        if (isNotchAtXiaoMi(activity) || isNotchAtHuawei(activity.getApplication()) || isNotchAtOPPO(activity.getApplication())
                || isNotchAtVivo(activity.getApplication()) || isAndroidP(activity) != null){
            return true;
        }

        return false;
    }

    /**
     * 小米刘海屏判断
     * @param activity
     * @return
     */
    private static boolean isNotchAtXiaoMi(Activity activity) {
        int result = 0;
        if (isXiaomi()){
            try {
                ClassLoader classLoader = activity.getClassLoader();
                @SuppressWarnings("rawtypes")
                Class SystemProperties = classLoader.loadClass("android.os.SystemProperties");
                //参数类型
                @SuppressWarnings("rawtypes")
                Class[] paramTypes = new Class[2];
                paramTypes[0] = String.class;
                paramTypes[1] = int.class;
                Method getInt = SystemProperties.getMethod("getInt", paramTypes);
                //参数
                Object[] params = new Object[2];
                params[0] = new String("ro.miui.notch");
                params[1] = new Integer(0);
                result = (Integer) getInt.invoke(SystemProperties, params);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return result==1;
    }

    /**
     * Android P 刘海屏判断
     * @param activity
     * @return
     */
    private static DisplayCutout isAndroidP(Activity activity){
        View decorView = activity.getWindow().getDecorView();
        if (decorView != null && android.os.Build.VERSION.SDK_INT >= 28){
            WindowInsets windowInsets = decorView.getRootWindowInsets();
            if (windowInsets != null)
                return windowInsets.getDisplayCutout();
        }
        return null;
    }

    /**
     * VIVO刘海屏判断
     * @return
     */
    public static boolean isNotchAtVivo(Application application) {
        boolean ret = false;
        try {
            ClassLoader classLoader = application.getClassLoader();
            Class FtFeature = classLoader.loadClass("android.util.FtFeature");
            Method method = FtFeature.getMethod("isFeatureSupport", int.class);
            ret = (boolean) method.invoke(FtFeature, VIVO_NOTCH);
        } catch (ClassNotFoundException e) {
            Log.e("Notch","hasNotchAtVivo ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("Notch", "hasNotchAtVivo NoSuchMethodException");
        } catch (Exception e) {
            Log.e("Notch", "hasNotchAtVivo Exception");
        } finally {
            return ret;
        }
    }

    /**
     * 华为刘海屏判断
     * @return
     */
    private static boolean isNotchAtHuawei(Application application) {
        boolean ret = false;
        try {
            ClassLoader classLoader =application.getClassLoader();
            Class HwNotchSizeUtil = classLoader.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            ret = (boolean) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            Log.e("Notch","hasNotchAtHuawei ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("Notch","hasNotchAtHuawei NoSuchMethodException");
        } catch (Exception e) {
            Log.e("Notch","hasNotchAtHuawei Exception");
        } finally {
            return ret;
        }
    }

    /**
     * OPPO刘海屏判断
     * @return
     */
    public static boolean isNotchAtOPPO(Application application) {
        return  application.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }

    /**
     * 判断是否为小米设备商
     * @return
     */
    private static boolean isXiaomi() {
        return "Xiaomi".equals(Build.MANUFACTURER);
    }

}
