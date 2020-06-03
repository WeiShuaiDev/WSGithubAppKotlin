package com.linwei.frame.base.holder

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.linwei.frame.R

/**
 * @Author: WeiShuai
 * @Time: 2019/10/14
 * @Description: ViewHolder管理
 */
class TopViewHolder(view: View) {
    private var mMapViews: MutableMap<Int, View> = mutableMapOf()
    private var mItemView: View = view
    var mIvBack: ImageButton
    var mTvTitle: TextView
    var mRightTitle: TextView
    var mIbChoose: ImageButton
    var mLine: View

    init {
        mIvBack = findViewById(R.id.mIbBack)
        mTvTitle = findViewById(R.id.mTvTitle)
        mRightTitle = findViewById(R.id.mTvRightTitle)
        mIbChoose = findViewById(R.id.mIbChoose)
        mLine = findViewById(R.id.mVLine)
    }

    /**
     * 获取控件
     */
    private fun <T : View> findViewById(viewId: Int): T {
        var view: View? = mMapViews[viewId]
        if (view == null) {
            view = mItemView.findViewById(viewId)
            mMapViews[viewId] = view
        }
        return view as T
    }
}