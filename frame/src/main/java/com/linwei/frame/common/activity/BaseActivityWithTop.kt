package com.linwei.frame.common.activity

import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.linwei.frame.common.holder.TopViewHolder
import com.linwei.frame.ext.hideSoftKeyboard
import com.linwei.frame.ext.setVisible
import com.linwei.frame.listener.OnTopLeftClickListener
import com.linwei.frame.listener.OnTopRightClickListener
import com.linwei.frame.utils.UIUtils
import kotlinx.android.synthetic.main.include_top_view.view.*

/**
 * @Author: WS
 * @Time: 2019/10/14
 * @Description: BaseActivityWithTop基类
 */
abstract class BaseActivityWithTop : BaseActivity() {

    lateinit var mTopBarView: ViewGroup
    lateinit var mTopViewHolder: TopViewHolder

    private var mITopLeftListener: OnTopLeftClickListener? = null//头部左操作监听
    private var mTopRightListener: OnTopRightClickListener? = null//头部右标题点击监听

    override fun withTopContentView(): View {
        initTopBar()
        val mainView = View.inflate(this, provideContentViewId(), null)
        mTopBarView.mIncludeContent.addView(mainView)//添加内容区域的视图
        return mTopBarView
    }

    /**
     * 初始化TopBar
     */
    private fun initTopBar() {
        mTopBarView = View.inflate(this, getTopBarId(), null) as ViewGroup
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
                window.decorView.hideSoftKeyboard()
                finish()
            }
        }

        // 获取头部右标题点击监听
        mTopRightListener = getTopRightListener()
        mTopViewHolder.mRightTitle.setOnClickListener {
            if (mTopRightListener != null) {
                mTopRightListener?.onTopRightClickListener()
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

    open fun getTopRightListener(): OnTopRightClickListener? {
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
     * @param resId 资源id
     */
    fun setTopBarTitle(resId: Int) {
        if (resId > 0) {
            setTopBarTitle(getString(resId))
        }
    }

    /**
     * 设置标题文本
     */
    fun setTopBarTitle(titleTxt: String?) {
        var title = titleTxt
        if (TextUtils.isEmpty(title)) {
            title = ""
        }
        mTopViewHolder.mTvTitle.text = title
    }

    /**
     * 获取标题文本
     */
    fun getTopBarTvTitle(): TextView? {
        return mTopViewHolder.mTvTitle
    }


    /**
     * 设置右标题
     * @param resId 资源id
     */
    fun setTopBarRightTitle(resId: Int) {
        if (resId > 0) {
            setTopBarRightTitle(getString(resId))
        }
    }

    /**
     * 设置右标题
     * @param Stringw 文本
     */
    fun setTopBarRightTitle(rightTitle: String?) {
        var title = rightTitle
        if (title!!.isEmpty()) {
            title = ""
        }
        mTopViewHolder.mRightTitle.text = title
    }

    /**
     * 获取右标题
     */
    fun getTopBarRightTitle(): String {
        return mTopViewHolder.mRightTitle.text.toString()
    }

    /**
     * 设置右标题visible
     */
    fun setTopBarRightTitleVisible(visible: Boolean) {
        mTopViewHolder.mRightTitle.setVisible(visible)
    }
}