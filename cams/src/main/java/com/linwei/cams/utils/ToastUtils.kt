package com.linwei.cams.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import java.lang.ref.WeakReference

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2019/10/18
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: Toast工具类
 *-----------------------------------------------------------------------
 */
const val DEFAULT_COLOR = 0x12000000

class ToastUtils private constructor(context: Context) {

    private var mContext: Context = context

    private var backgroundColor = DEFAULT_COLOR
    private var messageColor = DEFAULT_COLOR
    private var mToast: Toast? = null
    private var bgResource = -1
    private var sViewWeakReference: WeakReference<View>? = null
    private var sHandler = Handler(Looper.getMainLooper())

    private var gravity = -1
    private var xOffset = -1
    private var yOffset = -1

    /**
     * 建造者模式,设置Toast的各种属性
     */
    class Builder(context: Context) {

        private var caidaoToast = ToastUtils(context)

        /**
         * 设置位置
         */
        fun setGravity(gravity: Int, xOffset: Int, yOffset: Int): Builder {
            caidaoToast.gravity = gravity
            caidaoToast.xOffset = xOffset
            caidaoToast.yOffset = yOffset
            return this
        }

        /**
         * 设置View
         */
        fun setView(@LayoutRes layoutId: Int): Builder {
            val inflate = caidaoToast.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            caidaoToast.sViewWeakReference = WeakReference(inflate.inflate(layoutId, null))
            return this
        }

        /**
         * 设置View
         */
        fun setView(view: View): Builder {
            caidaoToast.sViewWeakReference = WeakReference(view)
            return this
        }

        /**
         * 设置背景颜色
         */
        fun setBackgroundColor(@ColorInt backgroundColor: Int): Builder {
            caidaoToast.backgroundColor = backgroundColor
            return this
        }

        /**
         * 设置背景资源
         */
        fun setBgResource(@DrawableRes bgResource: Int): Builder {
            caidaoToast.bgResource = bgResource
            return this
        }

        /**
         * 设置消息字体颜色
         */
        fun setMessageColor(messageColor: Int): Builder {
            caidaoToast.messageColor = messageColor
            return this
        }

        /**
         * 建造者
         */
        fun build(): ToastUtils {
            return caidaoToast
        }
    }

    /**
     * 安全地显示短时吐司
     *
     * @param text 文本
     */
    fun showShortSafe(text: CharSequence) {
        sHandler.post { showToast(text, Toast.LENGTH_SHORT) }
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     */
    fun showShortSafe(@StringRes resId: Int) {
        sHandler.post { show(resId, Toast.LENGTH_SHORT) }
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    fun showShortSafe(@StringRes resId: Int,  args: Array<out String>) {
        sHandler.post { show(resId, Toast.LENGTH_SHORT, args) }
    }

    /**
     * 安全地显示短时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    fun showShortSafe(format: String,  args: Array<out String>) {
        sHandler.post { show(format, Toast.LENGTH_SHORT, args) }
    }

    /**
     * 安全地显示长时吐司
     *
     * @param text 文本
     */
    fun showLongSafe(text: CharSequence) {
        sHandler.post { showToast(text, Toast.LENGTH_LONG) }
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源Id
     */
    fun showLongSafe(@StringRes resId: Int) {
        sHandler.post { show(resId, Toast.LENGTH_LONG) }
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    fun showLongSafe(@StringRes resId: Int,  args: Array<out String>) {
        sHandler.post { show(resId, Toast.LENGTH_LONG, args) }
    }

    /**
     * 安全地显示长时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    fun showLongSafe(format: String, args: Array<out String>) {
        sHandler.post { show(format, Toast.LENGTH_LONG, args) }
    }

    /**
     * 显示短时吐司
     *
     * @param text 文本
     */
    fun showShort(text: CharSequence) {
        showToast(text, Toast.LENGTH_SHORT)
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     */
    fun showShort(@StringRes resId: Int) {
        show(resId, Toast.LENGTH_SHORT)
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    fun showShort(@StringRes resId: Int,  args:  Array<out String>) {
        show(resId, Toast.LENGTH_SHORT, args)
    }

    /**
     * 显示短时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    fun showShort(format: String,  args: Array<out String>) {
        show(format, Toast.LENGTH_SHORT, args)
    }

    /**
     * 显示长时吐司
     *
     * @param text 文本
     */
    fun showLong(text: CharSequence) {
        showToast(text, Toast.LENGTH_LONG)
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     */
    fun showLong(@StringRes resId: Int) {
        show(resId, Toast.LENGTH_LONG)
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    fun showLong(@StringRes resId: Int,  args: Array<out String>) {
        show(resId, Toast.LENGTH_LONG, args)
    }

    /**
     * 显示长时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    fun showLong(format: String,  args:Array<out String>) {
        show(format, Toast.LENGTH_LONG, args)
    }


    /**
     * 显示吐司
     *
     * @param resId    资源Id
     * @param duration 显示时长
     * @param args     参数
     */
    private fun show(format: String, duration: Int) {
        showToast(format, duration)
    }


    /**
     * 显示吐司
     *
     * @param resId    资源Id
     * @param duration 显示时长
     * @param args     参数
     */
    private fun show(@StringRes resId: Int, duration: Int) {
        show(mContext.getString(resId), duration)
    }


    /**
     * 显示吐司
     *
     * @param resId    资源Id
     * @param duration 显示时长
     * @param args     参数
     */
    private fun show(@StringRes resId: Int, duration: Int, args: Array<out String>) {
        show(String.format(mContext.getString(resId), args), duration)
    }

    /**
     * 显示吐司
     *
     * @param format   格式
     * @param duration 显示时长
     * @param args     参数
     */
    private fun show(format: String, duration: Int, args: Array<out String>) {
        showToast(String.format(format, *args), duration)
    }

    /**
     * 显示吐司
     *
     * @param text     文本
     * @param duration 显示时长
     */
    @SuppressLint("ShowToast")
    private fun showToast(text: CharSequence, duration: Int) {
        cancel()
        if (text.isBlank()) return

        var isCustom = false
        val customView = sViewWeakReference?.get()
        if (null != customView) {
            mToast = Toast(mContext)
            mToast?.view = customView
            mToast?.duration = duration
            isCustom = true
        }

        if (!isCustom) {
            mToast = if (messageColor != DEFAULT_COLOR) {
                val spannableString = SpannableString(text)
                val colorSpan = ForegroundColorSpan(messageColor)
                spannableString.setSpan(colorSpan, 0, spannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                Toast.makeText(mContext, spannableString, duration)
            } else {
                Toast.makeText(mContext, text, duration)
            }
        }

        val toastView = mToast?.view
        if (bgResource != -1) {
            toastView?.setBackgroundResource(bgResource)
        } else if (backgroundColor != DEFAULT_COLOR) {
            toastView?.setBackgroundColor(backgroundColor)
        }
        if (-1 != gravity && -1 != xOffset && -1 != yOffset) {
            mToast?.setGravity(gravity, xOffset, yOffset)
        }
        mToast?.show()
    }

    /**
     * 取消吐司显示
     */
    private fun cancel() {
        mToast?.cancel()
        mToast = null
    }

}