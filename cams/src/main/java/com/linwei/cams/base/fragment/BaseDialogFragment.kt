package com.linwei.cams.base.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.linwei.cams.R
import com.linwei.cams.base.holder.ItemViewHolder

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2019/10/14
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: 普通基类 [DialogFragment]
 *-----------------------------------------------------------------------
 */
abstract class BaseDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.dialog_style)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(provideContentLayoutId(), container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.setCanceledOnTouchOutside(cancelable())
        convertView(ItemViewHolder(view))
    }

    override fun onStart() {
        super.onStart()

        val window: Window? = dialog?.window
        val windowParams: WindowManager.LayoutParams = window!!.attributes
        windowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        windowParams.height = fetchDialogHeight()
        windowParams.windowAnimations = fetchDialogAnimStyle()
        window.attributes = windowParams
    }

    /**
     * [Dialog] 布局ResId
     * @return [Int]
     */
    protected abstract fun provideContentLayoutId(): Int

    /**
     *  [Dialog] 显示框通过 [ItemViewHolder] 对象，操作内部 `childView`
     * @param [view]
     */
    protected abstract fun convertView(view: ItemViewHolder)

    /**
     * [Dialog] 样式
     * @return [Int]
     */
    protected open fun fetchDialogAnimStyle(): Int = R.style.dialog_animation

    /**
     * [Dialog] 手势外边框关闭显示
     * @return [Boolean]
     */
    protected open fun cancelable(): Boolean = true

    /**
     * [Dialog] 高度,默认值参数 `WRAP_CONTENT`
     * @return [Int] 整数高度值
     */
    protected open fun fetchDialogHeight(): Int = WindowManager.LayoutParams.WRAP_CONTENT

}