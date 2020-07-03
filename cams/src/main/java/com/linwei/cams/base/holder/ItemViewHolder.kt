package com.linwei.cams.base.holder

import android.view.View
import android.widget.*
import com.linwei.cams.R

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/3
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: ViewHolder管理
 *-----------------------------------------------------------------------
 */
open class ItemViewHolder(val itemView: View) {
    private var mMapViews: MutableMap<Int, View> = mutableMapOf()

    /**
     * 根据 `ResId` 获取 [View]
     * @param viewId [Int]
     * @return [View]
     */
    fun getView(viewId: Int): View {
        return findViewById(viewId)
    }

    /**
     * 根据 `ResId` 获取 [TextView]
     * @param viewId [Int]
     * @return [TextView]
     */
    fun getTextView(viewId: Int): TextView {
        return findViewById(viewId)
    }

    /**
     * 根据 `ResId` 获取 [Button]
     * @param viewId [Int]
     * @return [Button]
     */
    fun getButton(viewId: Int): Button {
        return findViewById(viewId)
    }

    /**
     * 根据 `ResId` 获取 [ImageView]
     * @param viewId [Int]
     * @return [ImageView]
     */
    fun getImageView(viewId: Int): ImageView {
        return findViewById(viewId)
    }

    /**
     * 根据 `ResId` 获取 [ImageButton]
     * @param viewId [Int]
     * @return [ImageButton]
     */
    fun getImageButton(viewId: Int): ImageButton {
        return findViewById(viewId)
    }

    /**
     * 根据 `ResId` 获取 [EditText]
     * @param viewId [Int]
     * @return [EditText]
     */
    fun getEditText(viewId: Int): EditText {
        return findViewById(viewId)
    }

    /**
     * 获取控件
     */
    @Suppress("UNCHECKED_CAST")
    protected fun <T : View> findViewById(viewId: Int): T {
        var view: View? = mMapViews[viewId]
        if (view == null) {
            view = itemView.findViewById(viewId)
            mMapViews[viewId] = view
        }
        return view as T
    }
}