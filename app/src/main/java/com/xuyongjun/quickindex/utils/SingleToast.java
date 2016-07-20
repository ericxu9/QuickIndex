package com.xuyongjun.quickindex.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * ============================================================
 * 作 者 : XYJ
 * 版 本 ： 1.0
 * 创建日期 ： 2016/7/19 19:52
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class SingleToast {
    private static Toast mToast;

    /**
     * 单例的Toast，只会改变文字，不会重新弹框
     * @param context
     * @param text
     */
    public static void show (Context context,String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context,"",Toast.LENGTH_SHORT);
        }
        mToast.setText(text);
        mToast.show();
    }
}
