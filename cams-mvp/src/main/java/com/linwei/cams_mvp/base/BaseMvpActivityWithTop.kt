package com.linwei.cams_mvp.base

import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import com.linwei.cams.R
import com.linwei.cams.base.holder.TopViewHolder
import com.linwei.cams.ext.hideSoftKeyboard
import com.linwei.cams.ext.string
import com.linwei.cams.listener.OnTopBarLeftClickListener
import com.linwei.cams.listener.OnTopBarRightClickListener
import com.linwei.cams_mvp.mvp.BasePresenter
import com.linwei.cams_mvp.mvp.IModel
import com.linwei.cams_mvp.mvp.IView

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/7
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: `MVP`架构 `Activity` 导航栏基类
 *-----------------------------------------------------------------------
 */
abstract class BaseMvpActivityWithTop<T : BasePresenter<IModel, IView>> : BaseMvpActivity<T>() {

    lateinit var mTopViewHolder: TopViewHolder

    private var mTopBarLeftClickListener: OnTopBarLeftClickListener? = null
    private var mTopBarRightClickListener: OnTopBarRightClickListener? = null

    override fun withTopBarView(): View {
        val view: View = View.inflate(this, provideContentViewId(), null)
        return addTopBarView(view)
    }

    /**
     * `TopBar` 配置处理，同时返回控件
     * @param view [View]
     */
    private fun addTopBarView(view: View): View {
        val topBarView: View = View.inflate(this, provideTopBarId(), null) as ViewGroup
        mTopViewHolder = TopViewHolder(topBarView)
        mTopViewHolder.mIncludeContent.addView(view)//添加内容区域的视图

        initTopBarEvent()

        return topBarView
    }

    /**
     * `TopBar` 增加事件处理,并通过 [OnTopLeftClickListener]、[OnTopRightClickListener]
     * 接口回调
     */
    private fun initTopBarEvent() {
        mTopBarLeftClickListener = obtainTopBarLeftListener()
        mTopViewHolder.mTvLeftTitle.setOnClickListener {
            if (mTopBarLeftClickListener != null) {
                mTopBarLeftClickListener?.onLeftClick()
            } else {
                //关闭页面前收起软键盘
                window.decorView.hideSoftKeyboard()
                finish()
            }
        }

        mTopViewHolder.mIbLeftImage.setOnClickListener {
            if (mTopBarLeftClickListener != null) {
                mTopBarLeftClickListener?.onLeftClick()
            } else {
                //关闭页面前收起软键盘
                window.decorView.hideSoftKeyboard()
                finish()
            }
        }

        // 获取头部右标题点击监听
        mTopBarRightClickListener = obtainTopBarRightListener()
        mTopViewHolder.mIbRightImage.setOnClickListener {
            mTopBarRightClickListener?.onRightClick()
        }
        mTopViewHolder.mTvRightTitle.setOnClickListener {
            mTopBarRightClickListener?.onRightClick()
        }
    }

    /**
     * 导航栏布局`ResId`
     * @return [Int] 布局文件Id
     */
    open fun provideTopBarId(): Int = R.layout.include_top_view

    /**
     * 导航栏左侧点击事件
     * @return [OnTopBarLeftClickListener]
     */
    open fun obtainTopBarLeftListener(): OnTopBarLeftClickListener? {
        return null
    }

    /**
     * 导航栏右侧点击事件
     * @return [OnTopBarRightClickListener]
     */
    open fun obtainTopBarRightListener(): OnTopBarRightClickListener? {
        return null
    }

    /**
     * 设置 `TopBar`  标题文本
     * @param resId 资源id
     */
    fun setTopBarTitle(resId: Int) {
        if (resId > 0) {
            setTopBarTitle(resId.string())
        }
    }

    /**
     * 设置 `TopBar` 标题文本
     */
    fun setTopBarTitle(title: String) {
        mTopViewHolder.mTvTitle.text = title
    }

    /**
     * 获取 `TopBar` 标题文本
     */
    fun getTopBarTvTitle(): TextView? {
        return mTopViewHolder.mTvTitle
    }


    /**
     * 设置 `TopBar` 右边 [Textview] 文本信息
     * @param resId [Int] 显示文本信息，默认显示`刷新`
     */
    fun setTopBarRightTitle(resId: Int = R.string.refresh) {
        mTopViewHolder.setRightTitleForId(resId)
    }

    /**
     * 设置 `TopBar` 右边 [ImageButton] 图片
     * @param resId [Int] 显示图片信息，默认显示`刷新图片箭头`
     */
    fun setTopBarRightImage(resId: Int = R.drawable.ic_refresh_black_24dp) {
        mTopViewHolder.setRightImageForId(resId)
    }


    /**
     * 设置 `TopBar` 左边 [Textview] 文本信息
     * @param resId [Int] 显示文本信息，默认显示 `退出`
     */
    fun setTopBarLeftTitle(resId: Int = R.string.setting) {
        mTopViewHolder.setLeftTitleForId(resId)
    }

    /**
     * 设置 `TopBar` 左边 [ImageButton] 图片
     * @param resId [Int] 显示图片信息，默认显示 `退出图片箭头`
     */
    fun setTopBarLeftImage(resId: Int = R.drawable.ic_arrow_back_black_24dp) {
        mTopViewHolder.setLeftImageForId(resId)
    }

    /**
     *  获取 `TopBar` 加载控件
     * @return [ProgressBar]
     */
    fun getTopBarLoadingView(): ProgressBar {
        return mTopViewHolder.mPbLoading
    }

    /**
     * 获取 `TopBar` 刷新控件
     * @return [ProgressBar]
     */
    fun getTopBarRefreshView(): ProgressBar {
        return mTopViewHolder.mPbRefresh
    }

    override fun onDestroy() {
        super.onDestroy()
        mTopBarRightClickListener = null
        mTopBarLeftClickListener = null
    }

}