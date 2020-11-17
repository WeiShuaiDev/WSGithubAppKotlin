package com.linwei.cams.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import com.linwei.cams.R
import com.linwei.cams.base.holder.ItemViewHolder
import com.linwei.cams.ext.getScreenWidthPixels

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/2
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: [DialogUtils]提示框工具类
 * ---------------------------------------------------------------------
 */
object DialogUtils {

    private const val DEFAULT_WIDTH_RATIO: Float = 0.85f

    /**
     * 创建一个自定义的View
     */
    fun createCustomDialog(
        context: Context, @LayoutRes layout: Int, @StyleRes theme: Int = R.style.dialog_style,
        listener: OnViewCreatedListener? = null
    ): Dialog {
        val view: View = LayoutInflater.from(context).inflate(layout, null)
        val dialog = CustomDialog(context, view, theme)
        dialog.setCancelable(true)
        listener?.onCreatedView(dialog, ItemViewHolder(view))
        setDialogWindow(dialog.window)
        return dialog
    }

    /**
     * 创建一个自定义的透明View
     * @param context [Context]
     * @param layout [Int]
     * @param listener [OnViewCreatedListener]
     * @return [Dialog] 显示框
     */
    fun createCustomTransparentDialog(
        context: Context, @LayoutRes layout: Int, @StyleRes theme: Int = R.style.dialog_style,
        listener: OnViewCreatedListener?
    ): Dialog {
        val dialog: Dialog = createCustomDialog(context, layout, theme, listener)
        //修改透明样式
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }

    /**
     * 关闭 [Dialog] 显示框
     * @param dialog [Dialog]
     */
    fun dismissDialog(dialog: Dialog?) {
        if (dialog != null) {
            val context:Context = (dialog.context as ContextWrapper).baseContext
            if (context is Activity) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    if (!context.isFinishing && !context.isDestroyed)
                        dialog.dismiss()
                } else
                    dialog.dismiss()
            } else
                dialog.dismiss()
        }
    }

    /**
     * 修改 [Window] 属性值
     * @param window [Window]
     * @param widthRatio [Float] 长度缩放比
     */
    private fun setDialogWindow(window: Window?, widthRatio: Float = DEFAULT_WIDTH_RATIO) {
        val attr: WindowManager.LayoutParams? = window?.attributes
        attr?.width = (getScreenWidthPixels() * widthRatio).toInt()
        window?.attributes = attr

    }

    class CustomDialog(context: Context, var view: View, @StyleRes theme: Int) :
        Dialog(context, theme) {

        override fun show() {
            super.show()
            setContentView(view)
        }
    }

    interface OnViewCreatedListener {
        fun onCreatedView(dialog: Dialog, viewHolder: ItemViewHolder)
    }
}