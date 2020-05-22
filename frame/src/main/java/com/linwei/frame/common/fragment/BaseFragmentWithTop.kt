package com.mimefin.baselib.common.fragment

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.linwei.frame.common.holder.TopViewHolder
import com.linwei.frame.ext.setVisible
import com.linwei.frame.common.fragment.BaseFragment
import com.linwei.frame.listener.OnTopLeftClickListener

/**
 * @Author: WeiShuai
 * @Time: 2019/10/14
 * @Description: BaseFragmentWithTop基类
 */
abstract class BaseFragmentWithTop : BaseFragment() {

    lateinit var mTopBarView: ViewGroup
    lateinit var mTopViewHolder: TopViewHolder

    private var mITopLeftListener: OnTopLeftClickListener? = null//头部左操作监听

    override fun getTopViewGroup(): ViewGroup? {
        initTopBar()
        return mTopBarView
    }

    /**
     * 初始化标题栏
     */
    private fun initTopBar() {
        mTopBarView = View.inflate(mActivity, getTopBarId(), null) as ViewGroup
        mTopViewHolder = TopViewHolder(mTopBarView)

        val titleId = getTitleId()
        if (titleId != 0) {
            setTopBarTitle(titleId)
        }

        //获取头部相关监听
        mITopLeftListener = getTopLeftListener()

        mTopViewHolder.mIvBack.setOnClickListener {
            if (mITopLeftListener != null) {
                mITopLeftListener?.onTopClickListener()
            } else {
                //关闭页面前收起软键盘
                mActivity.finish()
            }
        }
    }

    abstract fun getTopBarId(): Int

    abstract fun getTitleId(): Int

    /**
     * 左侧返回按钮的点击事件
     */
    open fun getTopLeftListener(): OnTopLeftClickListener? {
        return null
    }

    /**
     * 设置返回键显示或隐藏
     *
     * @param visible
     */
    fun setTopBarBackVisible(visible: Boolean) {
        mTopViewHolder.mIvBack.setVisible(visible)
    }

    /**
     * 设置标题文本
     *
     * @param resId 资源id
     */
    fun setTopBarTitle(resId: Int) {
        if (resId > 0) {
            setTopBarTitle(getString(resId))
        }
    }

    /**
     * 设置标题文本
     *
     * @param title 字符
     */
    fun setTopBarTitle(title: String) {
        mTopViewHolder.mTvTitle.text = title
    }


    fun getTopBarTvTitle(): TextView? {
        return mTopViewHolder.mTvTitle
    }
}