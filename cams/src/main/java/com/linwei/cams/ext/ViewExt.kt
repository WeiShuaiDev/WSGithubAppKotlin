package com.linwei.cams.ext

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.linwei.cams.utils.UIUtils

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/2
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: 提供 View 扩展方法
 *-----------------------------------------------------------------------
 */

/**
 * 扩展点击事件
 */
fun View.onClick(listener: View.OnClickListener): View {
    setOnClickListener(listener)
    return this
}

/**
 * 扩展点击事件，参数为方法
 */
fun View.onClick(method: () -> Unit): View {
    setOnClickListener { method() }
    return this
}

/**
 * 扩展视图可见性
 */
fun View.setVisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

/**
 * 获取url对应的域名
 */
fun String.getDomain(): String {
    var j = 0
    var startIndex = 0
    var endIndex = this.length - 1
    for (i in this.indices) {
        if (this[i] == '/') {
            j++
            if (j == 2)
                startIndex = i
            else if (j == 3)
                endIndex = i
        }

    }
    return this.substring(startIndex + 1, endIndex)
}


/**
 * 设置字体颜色
 */
fun TextView.setTextColorSpan(text: String, textColor: Int, startIndex: Int, endIndex: Int) {
    val span = SpannableString(text)
    span.setSpan(
        ForegroundColorSpan(ContextCompat.getColor(context!!, textColor)),
        startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    setText(span)
    movementMethod = LinkMovementMethod.getInstance()
}

/**
 *  设置字体大小
 */
fun TextView.setTextSizeSpan(text: String, textSize: Float, startIndex: Int, endIndex: Int) {
    val span = SpannableString(text)
    span.setSpan(
        AbsoluteSizeSpan(UIUtils.sp2px(context, textSize)),
        startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    setText(span)
    movementMethod = LinkMovementMethod.getInstance()
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


