package com.linwei.cams.base.fragment

import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.linwei.cams.R
import com.linwei.cams.base.holder.TopViewHolder
import com.linwei.cams.ext.string
import com.linwei.cams.listener.OnTopBarLeftClickListener
import com.linwei.cams.listener.OnTopBarRightClickListener
import com.linwei.cams.utils.UIUtils

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2019/10/14
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: 增加状态栏基类 [BaseFragmentWithTopAndStatus]
 *-----------------------------------------------------------------------
 */
abstract class BaseFragmentWithTopAndStatus : BaseFragment() {
    lateinit var mTopViewHolder: TopViewHolder

    private var mTopBarLeftClickListener: OnTopBarLeftClickListener? = null
    private var mTopBarRightClickListener: OnTopBarRightClickListener? = null

    override fun withTopBarContainer(): ViewGroup? {
        val topBarView: ViewGroup = View.inflate(mActivity, provideTopBarId(), null) as ViewGroup
        mTopViewHolder = TopViewHolder(topBarView)

        initTopBar()

        return if (useImmersive()) {
            markStatusView(topBarView)
        } else {
            topBarView
        }
    }

    /**
     * `TopBar`初始化处理,并通过 [OnTopLeftClickListener]、[OnTopRightClickListener]
     * 接口回调
     */
    private fun initTopBar() {
        val topBarTitle: Int = fetchTopBarTitle()
        if (topBarTitle > 0) setTopBarTitle(topBarTitle)

        mTopBarLeftClickListener = obtainTopBarLeftListener()
        mTopViewHolder.mTvLeftTitle.setOnClickListener {
            mTopBarLeftClickListener?.onLeftClick()
        }

        mTopViewHolder.mIbLeftImage.setOnClickListener {
            mTopBarLeftClickListener?.onLeftClick()
        }

        mTopBarRightClickListener = obtainTopBarRightListener()
        mTopViewHolder.mIbRightImage.setOnClickListener {
            mTopBarRightClickListener?.onRightClick()
        }
        mTopViewHolder.mTvRightTitle.setOnClickListener {
            mTopBarRightClickListener?.onRightClick()
        }
    }

    /**
     * 对 [view] 增加状态栏功能,并成功返回增加了沉浸式状态栏 [View] 控件
     * [fetchStatusColor] 方法定义状态栏颜色。[fetchStatusBarHeight] 方法定义状态栏高度
     * @param view [View]
     * @return [ViewGroup] 返回已经增加沉浸式状态栏 [ViewGroup]
     */
    private fun markStatusView(view: View?): ViewGroup {
        val linearLayout:LinearLayout =
            View.inflate(mActivity, R.layout.base_content_layout, null) as LinearLayout

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val mStatusFillView = View(mActivity)
            var statusBarHeight: Int = fetchStatusBarHeight()
            if (statusBarHeight <= 0) {
                statusBarHeight = UIUtils.dp2px(mActivity, 25f)
            }

            val params =
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight)
            mStatusFillView.setBackgroundResource(fetchStatusColor())
            mStatusFillView.layoutParams = params
            linearLayout.addView(mStatusFillView)
        }

        if (view != null) {
            val params =
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            view.layoutParams = params
            linearLayout.addView(view)
        }

        return linearLayout
    }

    /**
     * 计算设备沉浸式效果状态栏高度
     * @return [Int] 高度
     */
    private fun fetchStatusBarHeight(): Int {
        return 0
    }

    /**
     * 是否使用沉浸式效果
     * @return [Boolean] `false`:不使用; `true`:使用
     */
    protected abstract fun useImmersive(): Boolean

    /**
     * 沉浸式状态栏颜色
     * @return　[Int]
     */
    protected open fun fetchStatusColor(): Int = R.color.colorPrimary

    /**
     * 导航栏布局`ResId`
     * @return [Int] 布局文件Id
     */
    protected open fun provideTopBarId(): Int = R.layout.include_top_view

    /**
     * 导航栏标题`ResId`
     * @return [Int] 字符串Id
     */
    protected abstract fun fetchTopBarTitle(): Int

    /**
     * 导航栏左侧点击事件
     * @return [OnTopBarLeftClickListener]
     */
    protected open fun obtainTopBarLeftListener(): OnTopBarLeftClickListener? {
        return null
    }

    /**
     * 导航栏右侧点击事件
     * @return [OnTopBarRightClickListener]
     */
    protected open fun obtainTopBarRightListener(): OnTopBarRightClickListener? {
        return null
    }

    /**
     * 设置 `TopBar`  标题文本
     * @param resId 资源id
     */
    protected fun setTopBarTitle(resId: Int) {
        if (resId > 0) {
            setTopBarTitle(resId.string())
        }
    }

    /**
     * 设置 `TopBar` 标题文本
     */
    protected fun setTopBarTitle(title: String) {
        mTopViewHolder.mTvTitle.text = title
    }

    /**
     * 获取 `TopBar` 标题文本
     */
    protected fun getTopBarTvTitle(): TextView? {
        return mTopViewHolder.mTvTitle
    }

    /**
     * 设置 `TopBar` 右边 [Textview] 文本信息
     * @param resId [Int] 显示文本信息，默认显示`刷新`
     */
    protected fun setTopBarRightTitle(resId: Int = R.string.refresh) {
        mTopViewHolder.setRightTitleForId(resId)
    }

    /**
     * 设置 `TopBar` 右边 [ImageButton] 图片
     * @param resId [Int] 显示图片信息，默认显示`刷新图片箭头`
     */
    protected fun setTopBarRightImage(resId: Int = R.drawable.ic_refresh_black_24dp) {
        mTopViewHolder.setRightImageForId(resId)
    }

    /**
     * 设置 `TopBar` 左边 [Textview] 文本信息
     * @param resId [Int] 显示文本信息，默认显示 `退出`
     */
    protected fun setTopBarLeftTitle(resId: Int = R.string.setting) {
        mTopViewHolder.setLeftTitleForId(resId)
    }

    /**
     * 设置 `TopBar` 左边 [ImageButton] 图片
     * @param resId [Int] 显示图片信息，默认显示 `退出图片箭头`
     */
    protected fun setTopBarLeftImage(resId: Int = R.drawable.ic_arrow_back_black_24dp) {
        mTopViewHolder.setLeftImageForId(resId)
    }

    /**
     *  获取 `TopBar` 加载控件
     * @return [ProgressBar]
     */
    protected fun getTopBarLoadingView(): ProgressBar {
        return mTopViewHolder.mPbLoading
    }

    /**
     * 获取 `TopBar` 刷新控件
     * @return [ProgressBar]
     */
    protected fun getTopBarRefreshView(): ProgressBar {
        return mTopViewHolder.mPbRefresh
    }

    override fun onDestroy() {
        super.onDestroy()
        mTopBarRightClickListener = null
        mTopBarLeftClickListener = null
    }
}