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

    protected var mViewBinding: VDB? = null

    protected var mViewModel: VM? = null


    override fun setUpActivityComponent(appComponent: AppComponent?) {

    }

    override fun onCreateExpandConfigBefore(
        savedInstanceState: Bundle?
    ) {
        super.onCreateExpandConfigBefore(savedInstanceState)

        AndroidInjection.inject(this)
    }

    override fun bindingContentView(contentView1: Bundle?, contentView: View): View? {
        mViewBinding = DataBindingUtil.bind<VDB>(contentView)
        return null
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
            mViewModel = obtainViewModel(getVMClass())
        }

    }

    /**
     * 获取对应 `ViewModel` 的 Class 类
     * @return Class<VM> [Class]
     */
    private fun getVMClass(): Class<VM>? {
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

    override fun androidInjector(): AndroidInjector<Any> = mAndroidInjector

}