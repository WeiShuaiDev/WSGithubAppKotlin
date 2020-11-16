package com.linwei.cams.base.fragment

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.github.nukc.stateview.StateView
import com.linwei.cams.R
import com.linwei.cams.ext.px
import com.linwei.cams.http.cache.Cache
import com.linwei.cams.http.cache.CacheType
import com.linwei.cams.utils.ToastUtils
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2019/10/14
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: 普通基类 [BaseFragment]
 *-----------------------------------------------------------------------
 */
abstract class BaseFragment : LazeLoadFragment(),
    IFragment {
    protected lateinit var mActivity: Activity
    protected lateinit var mContext: Context

    protected var mStateView: StateView? = null  //用于显示加载中、网络异常，空布局、内容布局
    private var mRootView: View? = null
    protected var mToast: ToastUtils? = null

    @Inject
    lateinit var mCacheFactory: Cache.Factory

    /**
     * `Fragment` 模块 [Cache]缓存处理
     * @return [Cache]
     */
    override fun provideCache(): Cache<String, Any> =
        mCacheFactory.build(CacheType.fragmentCacheType)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mRootView == null) {
            val withTopContainer: ViewGroup? = withTopBarContainer()
            if (provideContentViewId() > 0) {
                if (withTopContainer != null) {
                    //有公共头部
                    mRootView = inflater.inflate(provideContentViewId(), withTopContainer, true)
                } else {
                    mRootView = inflater.inflate(provideContentViewId(), container, false)
                }

                if (mRootView != null)
                    mRootView =
                        bindingContentView(inflater, mRootView!!, container, savedInstanceState)
                            ?: mRootView
            }
        } else {
            val parent: ViewGroup? = mRootView?.parent as? ViewGroup
            parent?.removeView(mRootView)
        }
        return mRootView
    }

    /**
     * 界面导航栏内容布局 [ViewGroup]
     * @return [ViewGroup]
     */
    protected open fun withTopBarContainer(): ViewGroup? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (useStateView()) {
            mStateView = obtainStateViewRoot()?.let { StateView.inject(it) }
        }
        initToastBuilder()
        initLayoutView(mRootView)
        initLayoutData()
        initLayoutListener()
    }

    /**
     * 初始化View
     * @param rootView
     */
    protected abstract fun initLayoutView(rootView: View?)

    /**
     * 初始化数据
     */
    protected abstract fun initLayoutData()

    /**
     * 初始化事件
     */
    protected abstract fun initLayoutListener()

    /**
     * 界面内容布局 `ResId`
     * @return [Int] 布局文件Id
     */
    protected open fun provideContentViewId(): Int = -1

    /**
     * 界面内容布局 [View]
     * @return [View]
     */
    protected open fun bindingContentView(
        inflater: LayoutInflater,
        contentView: View,
        parentView: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = null

    /**
     * 是否使用状态页面
     * @return [Boolean] `false`:不使用; `true`:使用
     */
    protected open fun useStateView(): Boolean = false


    /**
     * 获取状态页面绑定顶层 [View]
     * @return [View]
     */
    protected open fun obtainStateViewRoot(): View? = null


    /**
     * [Fragment] 界面状态发生改变，会触发该回调
     * @param isVisible [Boolean] true:可见；false:不可见
     */
    override fun onFragmentVisibleChange(isVisible: Boolean) {
        if (isVisible) {
            reloadData()
        }
    }

    /**
     * 当第一次可见的时候，会触发该回调
     */
    override fun onFragmentFirstVisible() {
        loadData()
    }

    /**
     * [LazeLoadFragment] 重新加载数据
     */
    protected abstract fun reloadData()


    /**
     * [LazeLoadFragment]  首次加载数据
     */
    protected abstract fun loadData()


    /**
     * 修改 [View] 状态栏高度 [fetchStatusBarHeight],状态栏背景颜色 [fetchStatusColor] 功能
     * [fetchStatusColor] 方法定义状态栏颜色。[fetchStatusBarHeight] 方法定义状态栏高度
     * @param view [View]
     */
    protected fun hasTranslucentStatusBar(topBarView: View) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        val params: ViewGroup.LayoutParams = topBarView.layoutParams
        var statusBarHeight: Int = fetchStatusBarHeight()
        if (statusBarHeight <= 0) {
            statusBarHeight = 25f.px.toInt()
        }
        params.height = statusBarHeight
        topBarView.layoutParams = params
    }

    /**
     * 计算设备沉浸式效果状态栏高度
     * @return [Int] 高度
     */
    private fun fetchStatusBarHeight(): Int {
        return 0
    }

    /**
     * 初始化 [Toast] 配置
     */
    private fun initToastBuilder() {
        mToast = ToastUtils.Builder(mActivity)
            .setMessageColor(R.color.colorGlobalBlack)
            .build()
    }

}