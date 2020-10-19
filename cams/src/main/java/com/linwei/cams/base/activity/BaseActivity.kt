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
import com.linwei.cams.config.LibConfig
import com.linwei.cams.ext.px
import com.linwei.cams.http.cache.Cache
import com.linwei.cams.http.cache.CacheType
import com.linwei.cams.listener.OnPermissionListener
import com.linwei.cams.utils.AppLanguageUtils
import com.linwei.cams.utils.ToastUtils
import java.util.*
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2019/10/14
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:普通基类 [BaseActivity]
 *-----------------------------------------------------------------------
 */
abstract class BaseActivity : AppCompatActivity(), IActivity {
    private var currentActivity: Activity? = null
    private var activities: MutableList<Activity> = mutableListOf()  //对所有activity进行管理

    protected lateinit var mContext: Context
    protected var mToast: ToastUtils? = null

    protected var mStateView: StateView? = null
    protected lateinit var mPermissionListener: OnPermissionListener

    @Inject
    lateinit var mCacheFactory: Cache.Factory

    /**
     * `Activity` 模块 [Cache]缓存处理
     * @return [Cache]
     */
    override fun provideCache(): Cache<String, Any> =
        mCacheFactory.build(CacheType.activityCacheType)

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(AppLanguageUtils.attachBaseContext(newBase, LibConfig.LANGUAGE))
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        setUpOnCreateAndSuperStart(savedInstanceState)
        super.onCreate(savedInstanceState)
        setUpOnCreateAndSuperEnd(savedInstanceState)
        mContext = this

        val withTopBarView: View? = withTopBarView()
        val contentView: View = if (withTopBarView != null) {
            if (useImmersive()) {
                markStatusView(withTopBarView)
            } else {
                withTopBarView
            }
        } else {
            val view: View? = View.inflate(this, provideContentViewId(), null)
            if (useImmersive()) {
                markStatusView(view)
            } else {
                view!!
            }
        }

        setContentView(bindingContentView(savedInstanceState, contentView) ?: contentView)

        if (useStateView()) {
            mStateView = obtainStateViewRoot()?.let { StateView.inject(it) }
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

        initLayoutView(savedInstanceState)
        initLayoutData()
        initLayoutListener()
    }

    /**
     * 界面内容布局 [View]
     * @return [View]
     */
    protected open fun bindingContentView(
        bundle: Bundle?,
        contentView: View
    ): View? = null

    /**
     * 对 [view] 增加状态栏功能,并成功返回增加了沉浸式状态栏 [View] 控件
     * [fetchStatusColor] 方法定义状态栏颜色。[fetchStatusBarHeight] 方法定义状态栏高度
     * @param view [View]
     * @return [View] 返回已经增加沉浸式状态栏 [View]
     */
    private fun markStatusView(view: View?): View {
        val linearLayout: LinearLayout =
            View.inflate(this, R.layout.base_content_layout, null) as LinearLayout

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val mStatusFillView = View(this)
            var statusBarHeight: Int = fetchStatusBarHeight()
            if (statusBarHeight <= 0) {
                statusBarHeight = 25f.px.toInt()
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
     * 修改 [View] 状态栏高度 [fetchStatusBarHeight] ,状态栏背景颜色 [fetchStatusColor] 功能
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
        topBarView.setBackgroundResource(fetchStatusColor())
    }

    /**
     * 是否使用沉浸式效果
     * @return [Boolean] `false`:不使用; `true`:使用
     */
    protected open fun useImmersive(): Boolean = false

    /**
     * 沉浸式状态栏颜色
     * @return　[Int]
     */
    protected open fun fetchStatusColor(): Int = R.color.colorPrimary

    /**
     * 是否使用沉浸式黑体文字
     * @return [Boolean] `false`:不使用; `true`:使用
     */
    protected open fun useBlackStatusText(): Boolean = false

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
    protected open fun obtainStateViewRoot(): View? {
        return null
    }

    /**
     * 是否使用状态页面
     * @return [Boolean] `false`:不使用; `true`:使用
     */
    protected open fun useStateView(): Boolean = false

    /**
     * 界面导航栏内容布局 [View]
     * @return [View]
     */
    protected open fun withTopBarView(): View? = null

    /**
     * ing布局绑定
     */
    protected open fun bindingContentViewId(
        savedInstanceState: Bundle?
    ): View? = null

    /**
     * 是否使用手势滑动
     * @return [Boolean] `false`:不使用; `true`:使用
     */
    protected open fun enableSlideClose(): Boolean {
        return false
    }

    /**
     * 默认为左滑，子类可重写返回对应的方向
     * @return [Int]
     */
    protected open fun fetchEdgeDirection(): Int {
        return EDGE_LEFT
    }

    /**
     * 默认为覆盖滑动关闭效果，子类可重写
     * @return [Int]
     */
    protected open fun fetchSlideLayoutType(): Int {
        return LAYOUT_COVER
    }

    /**
     * 执行 [OnCreate] 方法之前配置回调
     * @param savedInstanceState [Bundle]
     */
    protected open fun setUpOnCreateAndSuperStart(savedInstanceState: Bundle?) {}


    /**
     * 执行 [OnCreate] 方法之后配置回调
     * @param savedInstanceState [Bundle]
     */
    protected open fun setUpOnCreateAndSuperEnd(savedInstanceState: Bundle?) {}

    /**
     * 初始化控件
     */
    protected abstract fun initLayoutView(savedInstanceState: Bundle?)

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
    protected abstract fun provideContentViewId(): Int

    /**
     * 初始化 [Toast] 配置
     */
    private fun initToastBuilder() {
        mToast = ToastUtils.Builder(mContext)
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
    protected fun getCurrentActivity(): Activity? {
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
