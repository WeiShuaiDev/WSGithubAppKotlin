package com.linwei.frame.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.util.*

/**
 * @Author: WeiShuai
 * @Time: 2019/10/14
 * @Description:UI工具类
 */
object UIUtils {
    /**
     * DP-PX
     */
    fun dp2px(context: Context, dpValue: Float): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * PX-DP
     */
    fun px2dp(context: Context, pxValue: Float): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * SP-PX
     */
    fun sp2px(context: Context, spValue: Float): Int {
        val scale: Float = context.resources.displayMetrics.scaledDensity
        return (spValue * scale + 0.5f).toInt()
    }

    /**
     * PX-SP
     */
    fun px2sp(context: Context, pxValue: Float): Int {
        val scale: Float = context.resources.displayMetrics.scaledDensity
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 判断字符串：Null\null\""
     */
    fun isNotNullOrEmpty(value: String?): Boolean {
        if (!value.isNullOrEmpty()) {
            if (value.toUpperCase(Locale.getDefault()) == "NULL") {
                return true
            }
        }
        return false
    }

}