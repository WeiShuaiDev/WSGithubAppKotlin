package com.linwei.cams.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.linwei.cams.R

/**
 * @Author: WeiShuai
 * @Time: 2019/10/14
 * @Description: BaseDialogFragment基类
 */
abstract class BaseDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.dialog_style)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getContentLayoutId(), container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.setCanceledOnTouchOutside(cancelable())
        convertView(view)
    }

    override fun onStart() {
        super.onStart()

        val window = dialog?.window
        val windowParams = window!!.attributes
        windowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        windowParams.height = getHeight()
        windowParams.windowAnimations = getAnimationStyle()
        window.attributes = windowParams
    }

    open fun getAnimationStyle(): Int = R.style.top_dialog_animation

    abstract fun getContentLayoutId(): Int

    abstract fun convertView(view: View)

    open fun cancelable(): Boolean = true

    open fun getHeight(): Int = WindowManager.LayoutParams.WRAP_CONTENT

    var mClickCompleteListener: OnClickCompleteListener? = null

    fun setOnClickCompleteListener(listener: OnClickCompleteListener) {
        mClickCompleteListener = listener
    }

    interface OnClickCompleteListener {
        fun onComplete()
    }
}