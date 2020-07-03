package com.linwei.cams.base.fragment

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.nukc.stateview.StateView
import com.linwei.cams.R
import com.linwei.cams.manager.HandlerManager
import com.linwei.cams.utils.ToastUtils
import com.linwei.cams.utils.UIUtils

/**
 * @Author: WeiShuai
 * @Time: 2019/10/14
 * @Description: BaseFragment基类
 */
abstract class BaseFragment : LazeLoadFragment() {
    private var mRootView: View? = null
    lateinit var mStateView: StateView  //用于显示加载中、网络异常，空布局、内容布局
    protected lateinit var mActivity: Activity
    protected lateinit var mContext: Context
    private var mToast: ToastUtils? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mRootView == null) {
            val topViewGroup = getTopViewGroup()
            if (provideContentViewId() > 0) {
                if (topViewGroup != null) {
                    //有公共头部
                    mRootView = inflater.inflate(provideContentViewId(), topViewGroup, true)
                } else {
                    mRootView = inflater.inflate(provideContentViewId(), container, false)
                }
            } else {
                mRootView = bindingContentViewId(inflater, container, savedInstanceState, false)
            }
        } else {
            val parent = mRootView?.parent as? ViewGroup
            parent?.removeView(mRootView)
        }
        return mRootView
    }

    open fun getTopViewGroup(): ViewGroup? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (hasStateView()) {
            mStateView = getStateViewRoot()?.let { StateView.inject(it) }!!
            mStateView.setLoadingResource(R.layout.page_loading)
            mStateView.setEmptyResource(R.layout.page_empty)
            mStateView.setRetryResource(R.layout.page_error)
        }
        initToastBuilder()
        init()
        initLayoutView(mRootView)
        initLayoutData()
        initLayoutListener()

        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * 初始化
     */
    open fun init() {}

    /**
     * 初始化View
     * @param rootView
     */
    open fun initLayoutView(rootView: View?) {}

    /**
     * 初始化数据
     */
    open fun initLayoutData() {}

    /**
     * 设置listener的操作
     */
    open fun initLayoutListener() {}

    /**
     * 获取布局Id
     */
    open fun provideContentViewId(): Int = -1

    /**
     * dataBinding布局绑定
     */
    open fun bindingContentViewId(
        inflater: LayoutInflater,
        topViewGroup: ViewGroup?,
        savedInstanceState: Bundle?,
        attachToRoot: Boolean
    ): View? = null

    /**
     * 是否有stateView 默认没有
     */
    open fun hasStateView(): Boolean = false

    /**StateView的null，默认是整个界面，如果需要变换可以重写此方法 */
    open fun getStateViewRoot(): View? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
        mContext = context
    }

    /**
     * 重新加载
     */
    override fun onFragmentVisibleChange(isVisible: Boolean) {
        if (isVisible) {
            reloadData()
        }
    }

    /**onFragmentVisibleChange
     * 重新加载数据
     */
    protected abstract fun reloadData()

    /**
     * 当第一次可见的时候，加载数据
     */
    override fun onFragmentFirstVisible() {
        loadData()
    }

    //加载数据
    open fun loadData() {}


    /**
     * 设置状态栏高度
     */
    protected fun hasTranslucentStatusBar(topView: View) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        val params = topView.layoutParams
        var statusBarHeight: Int = getStatusbarHeight()
        if (statusBarHeight <= 0) {
            statusBarHeight = UIUtils.dp2px(mActivity, 25f)
        }
        params.height = statusBarHeight
        topView.layoutParams = params
    }

    fun getStatusbarHeight(): Int {
        return 0
    }


    open fun useBlackStatusText(): Boolean = false

    /**
     * 是否使用修复resize，默认true
     */
    open fun useResizeFix(): Boolean = true

    /**
     * 显示Toast
     */
    private fun initToastBuilder() {
        mToast = ToastUtils.Builder(mActivity)
            .setBgResource(R.drawable.shape_toast_background)
            .setMessageColor(R.color.colorGlobalBlack)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        synchronized(HandlerManager.getInstance()) {
            HandlerManager.getInstance().removeTask()
        }
    }

    fun postTaskSafely(task: Runnable) {
        HandlerManager.getInstance().postTaskSafely(task)
    }

    fun postTaskDelay(task: Runnable, delayMillis: Int) {
        HandlerManager.getInstance().postTaskDelay(task, delayMillis)
    }

}