package com.linwei.cams.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import com.linwei.cams.base.holder.ItemViewHolder

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

    /**
     * 创建一个自定义的View
     */
    fun createCustomDialog(context: Context, @LayoutRes layout: Int, listener: OnViewCreatedListener): Dialog {
        val view:View = LayoutInflater.from(context).inflate(layout, null)
        val dialog = CustomDialog(context, view)
        dialog.setCancelable(false)
        listener.onCreatedView(dialog, ItemViewHolder(view))
        return dialog
    }

    /**
     * 创建一个自定义的透明View
     */
    fun createCustomTransparentDialog(
        context: Context, @LayoutRes layout: Int,
        listener: OnViewCreatedListener
    ): Dialog {
        val view:View = LayoutInflater.from(context).inflate(layout, null)
        val dialog = CustomDialog(context, view)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        listener.onCreatedView(dialog, ItemViewHolder(view))
        return dialog
    }


    interface OnViewCreatedListener {
        fun onCreatedView(dialog: Dialog, viewHolder: ItemViewHolder)
    }

    fun dismiss(dialog: Dialog?) {
        if (dialog != null) {
            val context = (dialog.context as ContextWrapper).baseContext
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

    class CustomDialog(@NonNull context: Context, internal var view: View) : Dialog(context) {

        override fun show() {
            super.show()
            setContentView(view)
        }
    }
}