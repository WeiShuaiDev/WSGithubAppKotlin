package com.linwei.frame.common.activity

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
import com.linwei.frame.R
import com.linwei.frame.common.HandlerMessage
import com.linwei.frame.config.LibConfig
import com.linwei.frame.listener.OnPermissionListener
import com.linwei.frame.utils.AndroidBug5497Workaround
import com.linwei.frame.utils.AppLanguageUtils
import com.linwei.frame.utils.ToastUtils
import com.linwei.frame.utils.UIUtils
import com.linwei.frame.utils.statusbar.Eyes
import com.qmuiteam.qmui.util.QMUIDeviceHelper
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import java.util.*

/**
 * @Author: WeiShuai
 * @Time: 2019/10/14
 * @Description: Activity基类
 */
abstract class BaseActivity : AppCompatActivity() {
    private var currentActivity: Activity? = null// 对所有activity进行管理
    private var activities: MutableList<Activity> = mutableListOf()
    protected lateinit var mContext: Context
    lateinit var mStateView: StateView
    private var mTipDialog: QMUITipDialog? = null
    protected var mToast: ToastUtils? = null
    lateinit var mPermissionListener: OnPermissionListener

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(AppLanguageUtils.attachBaseContext(newBase, LibConfig.LANGUAGE))
    }


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        //初始化的时候将其添加到集合中
        synchronized(activities) {
            activities.add(this)
        }

        val withTopContentView = withTopContentView()
        val contentView: View
        if (withTopContentView != null) {
            contentView = if (useImmersive()) {
                addStatusView(withTopContentView)
            } else {
                dealImmersive()  //隐藏状态栏
                withTopContentView
            }
        } else {
            val view = View.inflate(this, provideContentViewId(), null)
            contentView = if (useImmersive()) {
                addStatusView(view!!)
            } else {
                dealImmersive()
                view!!
            }

        }

        setContentView(contentView)

        if (hasStateView()) {
            mStateView = getStateViewRoot()?.let { StateView.inject(it) }!!
            mStateView.setLoadingResource(R.layout.page_loading)
            mStateView.setEmptyResource(R.layout.page_empty)
            mStateView.setRetryResource(R.layout.page_error)
        }

        if (enableSlideClose()) {
            val layout = ParallaxHelper.getParallaxBackLayout(this, true)
            layout.setEdgeMode(EDGE_MODE_DEFAULT)//边缘滑动
            layout.edgeFlag = getEdgeDirection()
            layout.setLayoutType(getSlideLayoutType(), null)

            layout.setSlideCallback(object : ParallaxBackLayout.ParallaxSlideCallback {
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
     * 是否使用沉浸式，默认是
     */
    open fun useImmersive() = false

    private fun addStatusView(view: View): View {
        val linearLayout = View.inflate(this, R.layout.base_content_layout, null) as LinearLayout

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val mStatusFillView = View(this)
            var statusBarHeight = QMUIStatusBarHelper.getStatusbarHeight(this)
            if (statusBarHeight <= 0) {
                statusBarHeight = UIUtils.dp2px(this, 25f)
            }

            val params =
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight)
            mStatusFillView.setBackgroundResource(getStatusColor())
            mStatusFillView.layoutParams = params
            linearLayout.addView(mStatusFillView)
        }

        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        view.layoutParams = params
        linearLayout.addView(view)

        dealImmersive()
        return linearLayout
    }

    /**
     * 处理沉浸式
     */
    private fun dealImmersive() {
        if (!(QMUIDeviceHelper.isMeizu() || QMUIDeviceHelper.isMIUI()) && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //如果不是小米或不是魅族，并且是5.0以下
            Eyes.translucentStatusBar(this)
        } else {
            QMUIStatusBarHelper.translucent(this)
        }

        if (useBlackStatusText()) {
            QMUIStatusBarHelper.setStatusBarLightMode(this)
        } else {
            QMUIStatusBarHelper.setStatusBarDarkMode(this)
        }

        if (useResizeFix()) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                //如果大于4.4，则添加处理因沉浸式导致的ajustResize失效的问题
                AndroidBug5497Workaround.assistActivity(findViewById<View>(android.R.id.content))
            }
        }
    }

    /**
     * 是否沉浸式
     */
    fun hasTranslucentStatusBar(topView: View) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        val params = topView.layoutParams
        var statusBarHeight = QMUIStatusBarHelper.getStatusbarHeight(this)
        if (statusBarHeight <= 0) {
            statusBarHeight = UIUtils.dp2px(this, 25f)
        }
        params.height = statusBarHeight
        topView.layoutParams = params
        topView.setBackgroundResource(getStatusColor())
    }

    /**
     * 是否使用修复resize，默认true
     */
    open fun useResizeFix(): Boolean = true

    open fun getStatusColor(): Int = R.color.colorPrimary

    open fun useBlackStatusText(): Boolean = false

    open fun getStateViewRoot(): View? {
        return null
    }

    open fun hasStateView(): Boolean = false

    open fun withTopContentView(): View? = null

    /**
     * dataBinding布局绑定
     */
    open fun bindingContentViewId(
        savedInstanceState: Bundle?
    ): View? = null

    /**
     * 默认开启滑动关闭
     * @return
     */
    open fun enableSlideClose(): Boolean {
        return false
    }

    /**
     * 默认为左滑，子类可重写返回对应的方向
     * @return
     */
    open fun getEdgeDirection(): Int {
        return EDGE_LEFT
    }

    /**
     * 默认为覆盖滑动关闭效果，子类可重写
     * @return
     */
    open fun getSlideLayoutType(): Int {
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
     * 得到当前界面的布局文件
     */
    open fun provideContentViewId(): Int {
        return -1
    }

    fun showLoadingDialog(tipId: Int) {
        showTipDialog(QMUITipDialog.Builder.ICON_TYPE_LOADING, getString(tipId))
    }

    /**
     * 显示提示弹窗
     */
    protected fun showTipDialog(iconType: Int, tipWord: String) {
        if (isFinishing) {
            return
        }
        if (mTipDialog != null && mTipDialog?.isShowing!!) {
            mTipDialog?.dismiss()
        }

        mTipDialog = QMUITipDialog.Builder(this)
            .setTipWord(tipWord)
            .setIconType(iconType)
            .create()

        mTipDialog?.setCancelable(false)
        mTipDialog?.show()
    }

    /**
     * 显示Toast
     */
    private fun initToastBuilder() {
        mToast = ToastUtils.Builder(mContext)
            .setBgResource(R.drawable.shape_toast_background)
            .setMessageColor(R.color.colorGlobalBlack)
            .build()
    }

    protected fun dismissTipDialog() {
        if (!isFinishing && mTipDialog != null && mTipDialog!!.isShowing) {
            mTipDialog?.dismiss()
        }
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

        //销毁的时候从集合中移除
        synchronized(activities) {
            activities.remove(this)
            HandlerMessage.instance.removeTask()
        }
    }

    /**
     * 退出应用的方法
     */
    fun exitApp() {
        val iterator = activities.listIterator()

        while (iterator.hasNext()) {
            val next = iterator.next()
            next.finish()
        }
    }

    fun getCurrentActivity(): Activity? {
        return currentActivity
    }

    /**
     *验证权限
     */
    fun checkRuntimePermission(permissions: Array<String>): Boolean {
        for (permission in permissions) {
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
     * 申请运行时权限
     */
    fun requestRuntimePermission(
        permissions: Array<String>,
        permissionListener: OnPermissionListener
    ) {
        this.mPermissionListener = permissionListener
        val permissionList = ArrayList<String>()
        for (permission in permissions) {
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
                for (i in grantResults.indices) {
                    val grantResult = grantResults[i]
                    val permission = permissions[i]
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

    fun postTaskSafely(task: Runnable) {
        HandlerMessage.instance.postTaskSafely(task)
    }

    fun postTaskDelay(task: Runnable, delayMillis: Int) {
        HandlerMessage.instance.postTaskDelay(task, delayMillis)
    }
}
