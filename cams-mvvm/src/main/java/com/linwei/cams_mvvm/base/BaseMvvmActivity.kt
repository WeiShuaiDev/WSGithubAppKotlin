package com.linwei.cams_mvvm.base

import android.app.Dialog
import android.os.Bundle
import android.os.Message
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.linwei.cams.base.activity.BaseActivity
import com.linwei.cams.di.component.AppComponent
import com.linwei.cams.http.model.StatusCode
import com.linwei.cams.utils.DialogUtils
import com.linwei.cams_mvvm.R
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.cams_mvvm.mvvm.ILoading
import com.linwei.cams_mvvm.mvvm.IView
import com.linwei.cams_mvvm.livedatabus.MessageLiveEvent
import com.linwei.cams_mvvm.livedatabus.StatusLiveEvent
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/15
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:  `MVVM` 架构 `Activity` 核心基类
 *-----------------------------------------------------------------------
 */
abstract class BaseMvvmActivity<VM : BaseViewModel, VDB : ViewDataBinding> : BaseActivity(),
    HasAndroidInjector, ILoading,
    IView<VM> {

    @Inject
    lateinit var mAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    protected var mViewDataBinding: VDB? = null

    protected var mViewModel: VM? = null

    protected var mProgressDialog: Dialog? = null

    override fun setUpOnCreateAndSuperStart(savedInstanceState: Bundle?) {
        super.setUpOnCreateAndSuperStart(savedInstanceState)
        //Dagger.Android Fragment 注入
        AndroidInjection.inject(this)
    }

    override fun setUpOnCreateAndSuperEnd(savedInstanceState: Bundle?) {
        super.setUpOnCreateAndSuperEnd(savedInstanceState)
        initViewModel() //初始化ViewModel
        registerLiveEvent()  //注册事件总线
    }

    override fun setUpActivityComponent(appComponent: AppComponent?) {
    }

    override fun bindingContentView(bundle: Bundle?, contentView: View): View? {
        return if (useDataBinding()) {
            mViewDataBinding = DataBindingUtil.bind(contentView)
            mViewDataBinding?.root
        } else null
    }

    /**
     * 是否使用 `Databinding` 布局
     * @return [Boolean] true:使用；false:不使用
     */
    open fun useDataBinding(): Boolean = true

    /**
     * 是否使用状态页面
     * @return [Boolean] `false`:不使用; `true`:使用
     */
    override fun useStateView(): Boolean = true

    /**
     * `DataBinding` 绑定 [DataBindingComponent]
     */
    //open fun bindingComponent(): DataBindingComponent? =null

    /**
     *  根据 `Class` 类，获取不同的 `ViewModel` 对象
     */
    private fun initViewModel() {
        mViewModel = createViewModel()
        if (mViewModel == null) {
            mViewModel = obtainViewModel(fetchVMClass())
        }

        if (mViewModel != null) {
            lifecycle.addObserver(mViewModel!!)
        }

        bindViewModel()
    }

    /**
     * 注册 `LiveData` 事件，注册两个事件，分别为: [MessageLiveEvent]:消息事件、[StatusLiveEvent]:状态事件
     */
    private fun registerLiveEvent() {
        mViewModel?.fetchStatusLiveEvent()?.observe(this,
            object : StatusLiveEvent.StatusLiveObserver {
                override fun onStatusChanges(status: Int?) {
                    status?.let {
                        if (status > 0) {
                            receiveStatusLiveEvent(it)
                        }
                    }
                }
            })

        mViewModel?.fetchMessageLiveEvent()?.observe(this,
            object : MessageLiveEvent.MessageLiveObserver {
                override fun onNewMessage(message: Message?) {
                    message?.let {
                        receiveMessageLiveEvent(message)
                    }
                }
            })
    }

    /**
     * 接收消息事件总线数据
     * @param msg [Message] 消息
     */
    protected open fun receiveMessageLiveEvent(msg: Message) {
        if (msg.what == 0) {
            mToast?.showShort(msg.obj.toString())
        }
    }

    /**
     * 接收状态事件总线数据
     * @param code [StatusCode]
     */
    protected open fun receiveStatusLiveEvent(code: @StatusCode Int) {
        when (code) {
            StatusCode.START -> {
                showLoading()
            }
            StatusCode.LOADING -> {
                mStateView?.showLoading()
            }
            StatusCode.SUCCESS -> {
                mStateView?.showContent()
            }
            StatusCode.FAILURE -> {
                mStateView?.showRetry()
            }
            StatusCode.ERROR -> {
                mStateView?.showEmpty()
            }
            StatusCode.END -> {
                hideLoading()
            }
            else -> hideLoading()
        }
    }

    /**
     * 获取对应 `ViewModel` 的 Class 类
     * @return Class<VM> [Class]
     */
    @Suppress("UNCHECKED_CAST")
    private fun fetchVMClass(): Class<VM> {
        var cls: Class<*>? = javaClass
        var vmClass: Class<VM>? = null
        while (vmClass == null && cls != null) {
            vmClass = fetchVMClass(cls)
            cls = cls.superclass
        }
        if (vmClass == null) {
            vmClass = BaseViewModel::class.java as Class<VM>
        }
        return vmClass
    }

    /**
     * 获取对应 `ViewModel` 的 Class 类
     * @param cls [Class]
     * @return Class<VM> [Class]
     */
    @Suppress("UNCHECKED_CAST")
    private fun fetchVMClass(cls: Class<*>): Class<VM>? {
        val type: Type? = cls.genericSuperclass

        if (type is ParameterizedType) {
            val types: Array<out Type> = type.actualTypeArguments
            for (t: Type in types) {
                if (t is Class<*>) {
                    if (BaseViewModel::class.java.isAssignableFrom(t)) {
                        return t as Class<VM>
                    }
                } else if (t is ParameterizedType) {
                    val rawType = t.rawType
                    if (rawType is Class<*>) {
                        if (BaseViewModel::class.java.isAssignableFrom(rawType)) {
                            return rawType as Class<VM>
                        }
                    }
                }
            }
        }
        return null
    }

    /**
     * 根据 `ViewModel` 的 [vmClass],获取 `ViewModel` 对象
     * @param vmClass [Class]
     * @return  [ViewModel]
     */
    private fun obtainViewModel(vmClass: Class<VM>): VM? = obtainViewModel(viewModelStore, vmClass)

    /**
     * 根据 `ViewModel` 的 [vmClass],获取 `ViewModel` 对象
     * @param store [ViewModelStore]
     * @param vmClass [Class]
     * @return  [ViewModel]
     */
    private fun obtainViewModel(store: ViewModelStore, vmClass: Class<VM>): VM? =
        createViewModelProvider(store).get(vmClass)

    /**
     * 创建 [ViewModelProvider] 对象
     * @param store [ViewModelStore]
     * @return [ViewModelProvider] 对象
     */
    private fun createViewModelProvider(store: ViewModelStore): ViewModelProvider =
        ViewModelProvider(store, mViewModelFactory)

    /**
     * 获取 [ViewModelProvider.Factory] 对象
     * @return mViewModelFactory [ViewModelProvider.Factory]
     */
    protected fun getViewModelFactory(): ViewModelProvider.Factory = mViewModelFactory

    /**
     * 创建 `ViewModel` 类
     * @return [VM]
     */
    override fun createViewModel(): VM? = null

    /**
     * 获取 `ViewDataBinding` 对象
     * @return mViewDataBinding [VDB]
     */
    protected fun getViewDataBinding(): VDB? = mViewDataBinding

    /**
     *  获取 `ViewModel` 对象
     *  @return mViewModel [VM]
     */
    protected fun getViewModel(): VM? = mViewModel

    /**
     * 显示加载框
     */
    override fun showLoading() {
        if (mProgressDialog?.isShowing == true) {
            mProgressDialog?.hide()
        }
        mProgressDialog = DialogUtils.createCustomDialog(mContext, R.layout.progress_dialog)
        mProgressDialog?.show()
    }

    /**
     * 隐藏加载框
     */
    override fun hideLoading() {
        mProgressDialog?.hide()
    }

    override fun androidInjector(): AndroidInjector<Any> = mAndroidInjector

    override fun onDestroy() {
        super.onDestroy()
        mViewModel?.let {
            lifecycle.removeObserver(mViewModel!!)
            mViewModel = null
        }
        mViewDataBinding?.unbind()

        if (mProgressDialog?.isShowing == true) {
            mProgressDialog?.dismiss()
        }
        mProgressDialog = null
    }
}