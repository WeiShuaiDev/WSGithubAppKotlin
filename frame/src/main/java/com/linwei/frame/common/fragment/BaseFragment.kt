package com.linwei.frame.common.fragment

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.nukc.stateview.StateView
import com.linwei.frame.R
import com.linwei.frame.common.HandlerMessage
import com.linwei.frame.utils.AndroidBug5497Workaround
import com.linwei.frame.utils.ToastUtils
import com.linwei.frame.utils.UIUtils
import com.linwei.frame.utils.statusbar.Eyes
import com.qmuiteam.qmui.util.QMUIDeviceHelper
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog

/**
 * @Author: WS
 * @Time: 2019/10/14
 * @Description: BaseFragment基类
 */
abstract class BaseFragment : LazeLoadFragment() {
    private var mRootView: View? = null
    lateinit var mStateView: StateView  //用于显示加载中、网络异常，空布局、内容布局
    protected lateinit var mActivity: Activity
    protected lateinit var mContext: Context
    private var mTipDialog: QMUITipDialog? = null
    private var mToast: ToastUtils? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
     * 处理沉浸式
     */
    protected fun dealImmersive() {
        if (!(QMUIDeviceHelper.isMeizu() || QMUIDeviceHelper.isMIUI()) && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //如果不是小米或不是魅族，并且是5.0以下
            Eyes.translucentStatusBar(mActivity)
        } else {
            QMUIStatusBarHelper.translucent(mActivity)
        }

        if (useBlackStatusText()) {
            QMUIStatusBarHelper.setStatusBarLightMode(mActivity)
        } else {
            QMUIStatusBarHelper.setStatusBarDarkMode(mActivity)
        }

        if (useResizeFix()) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                //如果大于4.4，则添加处理因沉浸式导致的ajustResize失效的问题
                AndroidBug5497Workaround.assistActivity(mActivity.findViewById<View>(android.R.id.content))
            }
        }
    }

    /**
     * 设置状态栏高度
     */
    protected fun hasTranslucentStatusBar(topView: View) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        val params = topView.layoutParams
        var statusBarHeight = QMUIStatusBarHelper.getStatusbarHeight(mActivity)
        if (statusBarHeight <= 0) {
            statusBarHeight = UIUtils.dp2px(mActivity, 25f)
        }
        params.height = statusBarHeight
        topView.layoutParams = params
    }

    open fun useBlackStatusText(): Boolean = false

    /**
     * 是否使用修复resize，默认true
     */
    open fun useResizeFix(): Boolean = true

    /**
     * 显示加载框
     */
    fun showLoadingDialog(tipId: Int) {
        showTipDialog(QMUITipDialog.Builder.ICON_TYPE_LOADING, getString(tipId))
    }

    /**
     * 显示提示弹窗
     */
    private fun showTipDialog(iconType: Int, tipWord: String) {
        if (mActivity.isFinishing) {
            return
        }
        if (mTipDialog != null && mTipDialog?.isShowing!!) {
            mTipDialog?.dismiss()
        }
        mTipDialog = QMUITipDialog.Builder(mActivity)
            .setTipWord(tipWord)
            .setIconType(iconType)
            .create()

        mTipDialog?.setCancelable(true)
        mTipDialog?.show()
    }

    /**
     * 显示Toast
     */
    private fun initToastBuilder() {
        mToast = ToastUtils.Builder(mActivity)
            .setBgResource(R.drawable.shape_toast_background)
            .setMessageColor(R.color.colorGlobalBlack)
            .build()
    }

    protected fun dismissTipDialog() {
        if (!mActivity.isFinishing && mTipDialog != null && mTipDialog!!.isShowing) {
            mTipDialog?.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        synchronized(HandlerMessage.instance) {
            HandlerMessage.instance.removeTask()
        }
    }

    fun postTaskSafely(task: Runnable) {
        HandlerMessage.instance.postTaskSafely(task)
    }

    fun postTaskDelay(task: Runnable, delayMillis: Int) {
        HandlerMessage.instance.postTaskDelay(task, delayMillis)
    }

}