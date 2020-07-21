package com.linwei.cams_aac.base

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.linwei.cams.base.activity.BaseActivity
import com.linwei.cams.di.component.AppComponent
import com.linwei.cams_aac.aac.IView
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
 * @Description:  `AAC` 架构 `Activity` 基类
 *-----------------------------------------------------------------------
 *
 */
abstract class BaseAacActivity<VM : BaseViewModel, VDB : ViewDataBinding> : BaseActivity(),
    HasAndroidInjector, ILoading,
    IView<VM> {

    @Inject
    lateinit var mAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    private var mViewDataBinding: VDB? = null

    private var mViewModel: VM? = null


    override fun setUpActivityComponent(appComponent: AppComponent?) {

    }

    override fun onCreateExpandConfigBefore(
        savedInstanceState: Bundle?
    ) {
        super.onCreateExpandConfigBefore(savedInstanceState)

        AndroidInjection.inject(this)
    }

    override fun bindingContentView(contentView1: Bundle?, contentView: View): View? {
        mViewDataBinding = DataBindingUtil.bind(contentView)
        return mViewDataBinding?.root
    }

    override fun initLayoutView() {
        initViewModel()
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
    }

    /**
     * 获取对应 `ViewModel` 的 Class 类
     * @return Class<VM> [Class]
     */
    @Suppress("UNCHECKED_CAST")
    private fun fetchVMClass(): Class<VM>? {
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
            val types = type.actualTypeArguments
            for (t in types) {
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
    private fun obtainViewModel(vmClass: Class<VM>?): VM? {
        return null
    }

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