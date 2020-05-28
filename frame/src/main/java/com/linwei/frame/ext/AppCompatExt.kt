package com.linwei.frame.ext

import android.app.ActivityManager
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/25
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: 提供AppCompat方法
 *-----------------------------------------------------------------------
 */

/**
 * 获取运算后内存大小
 * {@code maxSize}最大内存大小，{@ multiplierSize}内存运算阈值
 */
fun Context.getCalculateCacheSize(maxSize: Int, multiplierSize: Float): Int {
    val activityManager: ActivityManager =
        getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val targetMemoryCacheSize: Int = (activityManager.memoryClass * multiplierSize * 1024).toInt()
    if (targetMemoryCacheSize > maxSize) {
        return maxSize
    }
    return targetMemoryCacheSize
}

/**
 * 收起软键盘
 */
fun View.hideSoftKeyboard() {
    val imm: InputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * 显示软键盘
 */
fun View.showSoftKeyboard() {
    isFocusable = true
    isFocusableInTouchMode = true
    requestFocus()
    val imm: InputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, 0)
}