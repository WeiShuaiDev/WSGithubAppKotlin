package com.linwei.cams.base.activity

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.anzewei.parallaxbacklayout.ParallaxHelper
import com.github.anzewei.parallaxbacklayout.ViewDragHelper.EDGE_LEFT
import com.github.anzewei.parallaxbacklayout.widget.ParallaxBackLayout
import com.github.anzewei.parallaxbacklayout.widget.ParallaxBackLayout.EDGE_MODE_DEFAULT
import com.github.anzewei.parallaxbacklayout.widget.ParallaxBackLayout.LAYOUT_COVER
import com.github.nukc.stateview.StateView
import com.linwei.cams.R
import com.linwei.cams.manager.HandlerManager
import com.linwei.cams.config.LibConfig
import com.linwei.cams.ext.obtainAppComponent
import com.linwei.cams.http.cache.Cache
import com.linwei.cams.http.cache.CacheType
import com.linwei.cams.listener.OnPermissionListener
import com.linwei.cams.utils.AppLanguageUtils
import com.linwei.cams.utils.ToastUtils
import com.linwei.cams.utils.UIUtils
import java.util.*
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2019/10/14
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: [Activity] 基类
 *-----------------------------------------------------------------------
 */
abstract class BaseActivity : AppCompatActivity(), IActivity {
    private var currentActivity: Activity? = null// 对所有activity进行管理
    private var activities: MutableList<Activity> = mutableListOf()

    protected lateinit var mContext: Context
    private var mToast: ToastUtils? = null

    lateinit var mStateView: StateView
    lateinit var mPermissionListener: OnPermissionListener

    @Inject
    lateinit var mCacheFactory: Cache.Factory


    override fun provideCache(): Cache<String, Any> =
        mCacheFactory.build(CacheType.activityCacheType)


    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(AppLanguageUtils.attachBaseContext(newBase, LibConfig.LANGUAGE))
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        val withTopContentView: View? = withTopContentView()
        val contentView: View = if (withTopContentView != null) {
            if (useImmersive()) {
                markStatusView(withTopContentView)
            } else {
                withTopContentView
            }
        } else {
            val view: View? = View.inflate(this, provideContentViewId(), null)
            if (useImmersive()) {
                markStatusView(view!!)
            } else {
                view!!
            }
        }

        setContentView(contentView)

        if (hasStateView()) {
            mStateView = obtainStateViewRoot()?.let { StateView.inject(it) }!!
            mStateView.setLoadingResource(R.layout.page_loading)
            mStateView.setEmptyResource(R.layout.page_empty)
            mStateView.setRetryResource(R.layout.page_error)
        }

        if (enableSlideClose()) {
            val parallaxBackLayout: ParallaxBackLayout =
                ParallaxHelper.getParallaxBackLayout(this, true)
            parallaxBackLayout.setEdgeMode(EDGE_MODE_DEFAULT)//边缘滑动
            parallaxBackLayout.edgeFlag = fetchEdgeDirection()
            parallaxBackLayout.setLayoutType(fetchSlideLayoutType(), null)

            parallaxBackLayout.setSlideCallback(object : ParallaxBackLayout.ParallaxSlideCallback {
                override fun onStateChanged(state: Int) {

                }

                override fun onPositionChanged(percent: Float) {

                }
            })
        }

        initToastBuilder()
        initLayoutView()
        initLayoutData()
        initLayoutListener()
    }

    /**
     * 对 [view] 增加状态栏功能,并成功返回增加了沉浸式状态栏 [View] 控件
     * [fetchStatusColor] 方法定义状态栏颜色。[fetchStatusBarHeight] 方法定义状态栏高度
     * @param view [View]
     * @return [View] 返回已经增加沉浸式状态栏 [View]
     */
    private fun markStatusView(view: View): View {
        val linearLayout: LinearLayout =
            View.inflate(this, R.layout.base_content_layout, null) as LinearLayout

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val mStatusFillView = View(this)
            var statusBarHeight: Int = fetchStatusBarHeight()
            if (statusBarHeight <= 0) {
                statusBarHeight = UIUtils.dp2px(this, 25f)
            }

            val params =
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight)
            mStatusFillView.setBackgroundResource(fetchStatusColor())
            mStatusFillView.layoutParams = params
            linearLayout.addView(mStatusFillView)
        }

        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        view.layoutParams = params
        linearLayout.addView(view)

        return linearLayout
    }


    /**
     * 修改 [View] 状态栏高度 [fetchStatusBarHeight],状态栏背景颜色 [fetchStatusColor] 功能
     * [fetchStatusColor] 方法定义状态栏颜色。[fetchStatusBarHeight] 方法定义状态栏高度
     * @param view [View]
     */
    fun hasTranslucentStatusBar(topView: View) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        val params: ViewGroup.LayoutParams = topView.layoutParams
        var statusBarHeight: Int = fetchStatusBarHeight()
        if (statusBarHeight <= 0) {
            statusBarHeight = UIUtils.dp2px(this, 25f)
        }
        params.height = statusBarHeight
        topView.layoutParams = params
        topView.setBackgroundResource(fetchStatusColor())
    }

    /**
     * 是否使用沉浸式效果
     * @return [Boolean] `false`:不使用; `true`:使用
     */
    open fun useImmersive(): Boolean = false

    /**
     * 沉浸式状态栏颜色
     * @return　[Int]
     */
    open fun fetchStatusColor(): Int = R.color.colorPrimary

    /**
     * 是否使用沉浸式黑体文字
     * @return [Boolean] `false`:不使用; `true`:使用
     */
    open fun useBlackStatusText(): Boolean = false

    /**
     * 计算设备沉浸式效果状态栏高度
     * @return [Int] 高度
     */
    private fun fetchStatusBarHeight(): Int {
        return 0
    }

    /**
     * 获取状态页面绑定顶层 [View]
     * @return [View]
     */
    open fun obtainStateViewRoot(): View? {
        return null
    }

    /**
     * 是否使用状态页面
     * @return [Boolean] `false`:不使用; `true`:使用
     */
    open fun hasStateView(): Boolean = false


    /**
     * 界面内容布局 [View]
     * @return [View]
     */
    open fun withTopContentView(): View? = null

    /**
     * dataBinding布局绑定
     */
    open fun bindingContentViewId(
        savedInstanceState: Bundle?
    ): View? = null

    /**
     * 是否使用手势滑动
     * @return [Boolean] `false`:不使用; `true`:使用
     */
    open fun enableSlideClose(): Boolean {
        return false
    }

    /**
     * 默认为左滑，子类可重写返回对应的方向
     * @return [Int]
     */
    open fun fetchEdgeDirection(): Int {
        return EDGE_LEFT
    }

    /**
     * 默认为覆盖滑动关闭效果，子类可重写
     * @return [Int]
     */
    open fun fetchSlideLayoutType(): Int {
        return LAYOUT_COVER
    }

    /**
     * 初始化控件
     */
    abstract fun initLayoutView();

    /**
     * 初始化数据
     */
    abstract fun initLayoutData();

    /**
     * 初始化事件
     */
    abstract fun initLayoutListener();

    /**
     * 界面内容布局 `ResId`
     * @return [Int] 布局文件Id
     */
    abstract fun provideContentViewId(): Int

    /**
     * 初始化 [Toast] 配置
     */
    private fun initToastBuilder() {
        mToast = ToastUtils.Builder(mContext)
            .setBgResource(R.drawable.shape_toast_background)
            .setMessageColor(R.color.colorGlobalBlack)
            .build()
    }

    override fun onResume() {
        super.onResume()
        currentActivity = this
    }

    override fun onPause() {
        super.onPause()
        currentActivity = null
    }

    override fun onDestroy() {
        super.onDestroy()
        HandlerManager.getInstance().removeTask()
    }

    fun postTaskSafely(task: Runnable) {
        HandlerManager.getInstance().postTaskSafely(task)
    }

    fun postTaskDelay(task: Runnable, delayMillis: Int) {
        HandlerManager.getInstance().postTaskDelay(task, delayMillis)
    }

    /**
     * 增加 [Activity] 任务栈中 [activity] 实例
     * @param activity [Activity]
     */
    override fun addStackSingleActivity(activity: Activity?) {
        //初始化的时候将其添加到集合中
        synchronized(activities) {
            if (activity != null) {
                activities.add(activity)
            }
        }
    }

    /**
     * 销毁 [Activity] 任务栈中 [activity] 实例
     * @param activity [Activity]
     */
    override fun removeStackSingleActivity(activity: Activity?) {
        //销毁的时候从集合中移除
        synchronized(activities) {
            activities.remove(activity)
        }
    }

    /**
     * 清除 [Activity] 任务栈中所有数据
     */
    fun exitApp() {
        val iterator: MutableListIterator<Activity> = activities.listIterator()

        while (iterator.hasNext()) {
            val next: Activity = iterator.next()
            next.finish()
        }
    }

    /**
     * Activity任务栈，返回顶层 [Activity]
     * @return [Activity] 顶层 `Activity`
     */
    fun getCurrentActivity(): Activity? {
        return currentActivity
    }

    /**
     * 校验 [permissions] 敏感权限是否全部申请通过
     * @param permissions [Array] 校验权限数据
     * @return [Boolean] true:校验通过;false:校验失败
     */
    fun checkRuntimePermission(permissions: Array<String>): Boolean {
        for (permission: String in permissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    /**
     * 请求 [permissions] 所有敏感权限，请求结构通过 [permissionListener] 回调
     * @param permissions [Array] 校验权限数据
     * @param permissionListener [OnPermissionListener] 校验结果回调
     */
    fun requestRuntimePermission(
        permissions: Array<String>,
        permissionListener: OnPermissionListener
    ) {
        this.mPermissionListener = permissionListener
        val permissionList = ArrayList<String>()
        for (permission: String in permissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionList.add(permission)
            }
        }

        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toTypedArray(), 1)
        } else {
            permissionListener.onGranted()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> if (grantResults.isNotEmpty()) {
                val deniedPermissions = ArrayList<String>()
                for (i: Int in grantResults.indices) {
                    val grantResult: Int = grantResults[i]
                    val permission: String = permissions[i]
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        deniedPermissions.add(permission)
                    }
                }
                if (deniedPermissions.isEmpty()) {
                    mPermissionListener.onGranted()
                } else {
                    mPermissionListener.onDenied(deniedPermissions)
                }
            }
        }
    }

}
