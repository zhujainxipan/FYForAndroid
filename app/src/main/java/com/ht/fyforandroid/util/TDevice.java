package com.ht.fyforandroid.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.ht.fyforandroid.BaseApplication;
import com.ht.fyforandroid.R;

import java.io.File;
import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.List;
import java.util.UUID;

/**
 * Created by niehongtao on 16/5/21.
 */
public class TDevice {

    // 手机网络类型
    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_CMWAP = 0x02;
    public static final int NETTYPE_CMNET = 0x03;

    public static boolean GTE_HC;
    public static boolean GTE_ICS;
    public static boolean PRE_HC;
    private static Boolean _hasBigScreen = null;
    private static Boolean _hasCamera = null;
    private static Boolean _isTablet = null;
    private static Integer _loadFactor = null;

    private static int _pageSize = -1;
    public static float displayDensity = 0.0F;

    static {
        GTE_ICS = Build.VERSION.SDK_INT >= 14;
        GTE_HC = Build.VERSION.SDK_INT >= 11;
        PRE_HC = Build.VERSION.SDK_INT < 11;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }



    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
     * @return
     */
    public static float getDensity() {
        if (displayDensity == 0.0)
            displayDensity = getDisplayMetrics().density;
        return displayDensity;
    }

    public static DisplayMetrics getDisplayMetrics() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((WindowManager) BaseApplication.getContext().getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(
                displaymetrics);
        return displaymetrics;
    }

    /**
     * 获取屏幕的高度
     * @return
     */
    public static float getScreenHeight() {
        return getDisplayMetrics().heightPixels;
    }


    /**
     * 获取屏幕宽度
     * @return
     */
    public static float getScreenWidth() {
        return getDisplayMetrics().widthPixels;
    }

    /**
     * 获取当前activity真实的宽高
     * @param activity
     * @return
     */
    public static int[] getRealScreenSize(Activity activity) {
        int[] size = new int[2];
        int screenWidth = 0, screenHeight = 0;
        WindowManager w = activity.getWindowManager();
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        // since SDK_INT = 1;
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
            try {
                screenWidth = (Integer) Display.class.getMethod("getRawWidth")
                        .invoke(d);
                screenHeight = (Integer) Display.class
                        .getMethod("getRawHeight").invoke(d);
            } catch (Exception ignored) {
            }
        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 17)
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(d,
                        realSize);
                screenWidth = realSize.x;
                screenHeight = realSize.y;
            } catch (Exception ignored) {
            }
        size[0] = screenWidth;
        size[1] = screenHeight;
        return size;
    }

    public static int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            return BaseApplication.getContext().getResources()
                    .getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

//    public static String getUdid() {
//        String udid = BaseApplication.getPreferences().getString("udid", "");
//        if (udid.length() == 0) {
//            SharedPreferences.Editor editor = BaseApplication.getPreferences()
//                    .edit();
//            udid = String.format("%s", UUID.randomUUID());
//            editor.putString("udid", udid);
//            editor.commit();
//        }
//        return udid;
//    }

    public static boolean hasBigScreen() {
        boolean flag = true;
        if (_hasBigScreen == null) {
            boolean flag1;
            if ((0xf & BaseApplication.getContext().getResources()
                    .getConfiguration().screenLayout) >= 3)
                flag1 = flag;
            else
                flag1 = false;
            Boolean boolean1 = Boolean.valueOf(flag1);
            _hasBigScreen = boolean1;
            if (!boolean1.booleanValue()) {
                if (getDensity() <= 1.5F)
                    flag = false;
                _hasBigScreen = Boolean.valueOf(flag);
            }
        }
        return _hasBigScreen.booleanValue();
    }

    public static final boolean hasCamera() {
        if (_hasCamera == null) {
            PackageManager pckMgr = BaseApplication.getContext()
                    .getPackageManager();
            boolean flag = pckMgr
                    .hasSystemFeature("android.hardware.camera.front");
            boolean flag1 = pckMgr.hasSystemFeature("android.hardware.camera");
            boolean flag2;
            if (flag || flag1)
                flag2 = true;
            else
                flag2 = false;
            _hasCamera = Boolean.valueOf(flag2);
        }
        return _hasCamera.booleanValue();
    }

    public static boolean hasHardwareMenuKey(Context context) {
        boolean flag = false;
        if (PRE_HC)
            flag = true;
        else if (GTE_ICS) {
            flag = ViewConfiguration.get(context).hasPermanentMenuKey();
        } else
            flag = false;
        return flag;
    }

    public static boolean hasInternet() {
        boolean flag;
        if (((ConnectivityManager) BaseApplication.getContext().getSystemService("connectivity")).getActiveNetworkInfo() != null)
            flag = true;
        else
            flag = false;
        return flag;
    }


    public static void hideAnimatedView(View view) {
        if (PRE_HC && view != null)
            view.setPadding(view.getWidth(), 0, 0, 0);
    }


    public static void hideSoftKeyboard(View view) {
        if (view == null)
            return;
        ((InputMethodManager) BaseApplication.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                view.getWindowToken(), 0);
    }

    public static boolean isLandscape() {
        boolean flag;
        if (BaseApplication.getContext().getResources().getConfiguration().orientation == 2)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public static boolean isPortrait() {
        boolean flag = true;
        if (BaseApplication.getContext().getResources().getConfiguration().orientation != 1)
            flag = false;
        return flag;
    }

    public static boolean isTablet() {
        if (_isTablet == null) {
            boolean flag;
            if ((0xf & BaseApplication.getContext().getResources()
                    .getConfiguration().screenLayout) >= 3)
                flag = true;
            else
                flag = false;
            _isTablet = Boolean.valueOf(flag);
        }
        return _isTablet.booleanValue();
    }

    public static float pixelsToDp(float f) {
        return f / (getDisplayMetrics().densityDpi / 160F);
    }

    public static void showAnimatedView(View view) {
        if (PRE_HC && view != null)
            view.setPadding(0, 0, 0, 0);
    }

    public static void showSoftKeyboard(Dialog dialog) {
        dialog.getWindow().setSoftInputMode(4);
    }

    public static void showSoftKeyboard(View view) {
        ((InputMethodManager) BaseApplication.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE)).showSoftInput(view,
                InputMethodManager.SHOW_FORCED);
    }

    public static void toogleSoftKeyboard(View view) {
        ((InputMethodManager) BaseApplication.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE)).toggleSoftInput(0,
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static boolean isSdcardReady() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    public static String getCurCountryLan() {
        return BaseApplication.getContext().getResources().getConfiguration().locale
                .getLanguage()
                + "-"
                + BaseApplication.getContext().getResources().getConfiguration().locale
                .getCountry();
    }

    public static boolean isZhCN() {
        String lang = BaseApplication.getContext().getResources()
                .getConfiguration().locale.getCountry();
        if (lang.equalsIgnoreCase("CN")) {
            return true;
        }
        return false;
    }

    public static String percent(double p1, double p2) {
        String str;
        double p3 = p1 / p2;
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(2);
        str = nf.format(p3);
        return str;
    }

    public static String percent2(double p1, double p2) {
        String str;
        double p3 = p1 / p2;
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(0);
        str = nf.format(p3);
        return str;
    }


    public static void setFullScreen(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow()
                .getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(params);
        activity.getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public static void cancelFullScreen(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow()
                .getAttributes();
        params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().setAttributes(params);
        activity.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public static PackageInfo getPackageInfo(String pckName) {
        try {
            return BaseApplication.getContext().getPackageManager()
                    .getPackageInfo(pckName, 0);
        } catch (PackageManager.NameNotFoundException e) {
        }
        return null;
    }

    public static int getVersionCode() {
        int versionCode = 0;
        try {
            versionCode = BaseApplication
                    .getContext()
                    .getPackageManager()
                    .getPackageInfo(BaseApplication.getContext().getPackageName(),
                            0).versionCode;
        } catch (PackageManager.NameNotFoundException ex) {
            versionCode = 0;
        }
        return versionCode;
    }

    public static int getVersionCode(String packageName) {
        int versionCode = 0;
        try {
            versionCode = BaseApplication.getContext().getPackageManager()
                    .getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException ex) {
            versionCode = 0;
        }
        return versionCode;
    }

    public static String getVersionName() {
        String name = "";
        try {
            name = BaseApplication
                    .getContext()
                    .getPackageManager()
                    .getPackageInfo(BaseApplication.getContext().getPackageName(),
                            0).versionName;
        } catch (PackageManager.NameNotFoundException ex) {
            name = "";
        }
        return name;
    }

    public static boolean isScreenOn() {
        PowerManager pm = (PowerManager) BaseApplication.getContext()
                .getSystemService(Context.POWER_SERVICE);
        return pm.isScreenOn();
    }

    public static void installAPK(Context context, File file) {
        if (file == null || !file.exists())
            return;
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }


    public static void openDial(Context context, String number) {
        Uri uri = Uri.parse("tel:" + number);
        Intent it = new Intent(Intent.ACTION_DIAL, uri);
        context.startActivity(it);
    }

    public static void openSMS(Context context, String smsBody, String tel) {
        Uri uri = Uri.parse("smsto:" + tel);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", smsBody);
        context.startActivity(it);
    }

    public static void openDail(Context context) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void openSendMsg(Context context) {
        Uri uri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void openCamera(Context context) {
        Intent intent = new Intent(); // 调用照相机
        intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
        intent.setFlags(0x34c40000);
        context.startActivity(intent);
    }

    public static String getIMEI() {
        TelephonyManager tel = (TelephonyManager) BaseApplication.getContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tel.getDeviceId();
    }

    public static String getPhoneType() {
        return android.os.Build.MODEL;
    }

    public static void openApp(Context context, String packageName) {
        Intent mainIntent = BaseApplication.getContext().getPackageManager()
                .getLaunchIntentForPackage(packageName);
        if (mainIntent == null) {
            mainIntent = new Intent(packageName);
        } else {
        }
        context.startActivity(mainIntent);
    }

    public static boolean isWifiOpen() {
        boolean isWifiConnect = false;
        ConnectivityManager cm = (ConnectivityManager) BaseApplication
                .getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        // check the networkInfos numbers
        NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
        for (int i = 0; i < networkInfos.length; i++) {
            if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
                if (networkInfos[i].getType() == ConnectivityManager.TYPE_MOBILE) {
                    isWifiConnect = false;
                }
                if (networkInfos[i].getType() == ConnectivityManager.TYPE_WIFI) {
                    isWifiConnect = true;
                }
            }
        }
        return isWifiConnect;
    }



    public static int getStatuBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 38;// 默认为38，貌似大部分是这样的
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = BaseApplication.getContext().getResources()
                    .getDimensionPixelSize(x);

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }

//    public static int getActionBarHeight(Context context) {
//        int actionBarHeight = 0;
//        TypedValue tv = new TypedValue();
//        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize,
//                tv, true))
//            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
//                    context.getResources().getDisplayMetrics());
//
//        if (actionBarHeight == 0
//                && context.getTheme().resolveAttribute(R.attr.actionBarSize,
//                tv, true)) {
//            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
//                    context.getResources().getDisplayMetrics());
//        }
//
//        return actionBarHeight;
//    }

    public static boolean hasStatusBar(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        if ((attrs.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取当前网络类型
     *
     * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
     */
    public static int getNetworkType() {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (!StringUtils.isEmpty(extraInfo)) {
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }
}
