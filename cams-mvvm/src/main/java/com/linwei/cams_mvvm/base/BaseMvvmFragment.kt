package com.linwei.cams_mvvm.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.linwei.cams.base.fragment.BaseFragment
import com.linwei.cams.di.component.AppComponent
import com.linwei.cams.http.model.StatusCode
import com.linwei.cams.utils.DialogUtils
import com.linwei.cams_mvvm.R
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.cams_mvvm.mvvm.ILoading
import com.linwei.cams_mvvm.mvvm.IView
import com.linwei.cams_mvvm.livedatabus.MessageLiveEvent
import com.linwei.cams_mvvm.livedatabus.StatusLiveEvent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/21
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:  `MVVM` 架构 `Fragment`基类
 *-----------------------------------------------------------------------
 */
abstract class BaseMvvmFragment<VM : BaseViewModel, VDB : ViewDataBinding> : BaseFragment(),
    HasAndroidInjector, ILoading,
    IView<VM> {

    @Inject
    lateinit var mAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    private var mViewDataBinding: VDB? = null

    private var mViewModel: VM? = null

    private var mProgressDialog: Dialog? = null

    override fun onAttach(context: Context) {
        //Dagger.Android Fragment 注入
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()

    }

    override fun bindingContentView(
        inflater: LayoutInflater,
        contentView: View,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.bind(contentView)
        return mViewDataBinding?.root
    }

    override fun setupFragmentComponent(appComponent: AppComponent?) {
    }


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
        registerLiveEvent()
    }

    /**
     * 注册 `LiveData` 事件，注册两个事件，分别为: [MessageLiveEvent]:消息事件、[StatusLiveEvent]:状态事件
     */
    private fun registerLiveEvent() {
        mViewModel?.fetchStatusLiveEvent()?.observe(this,
            Observer<@StatusCode Int> {
                it?.let {
                    if (it > 0) {
                        receiveStatusLiveEvent(it)
                    }
                }
            })

        mViewModel?.fetchMessageLiveEvent()?.observe(this,
            Observer<Message> {
                it?.let {
                    receiveMessageLiveEvent(it)
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
            StatusCode.LOADING -> showLoading()
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
    }

}