package com.linwei.cams_mvvm

import com.linwei.cams.base.BaseApplication
import com.linwei.cams.ext.obtainAppComponent
import com.linwei.cams_mvvm.di.component.DaggerMvvmComponent
import com.linwei.cams_mvvm.di.component.MvvmComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/24
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: [MvvmApplication] 继承基类 [BaseApplication],初始化 `Dagger`功能。
 *----------------------------------------------------------------------
 */
abstract class MvvmApplication : BaseApplication(), HasAndroidInjector {

    @Inject
    lateinit var mAndroidInjector: DispatchingAndroidInjector<Any>

    private lateinit var mMvvmComponent: MvvmComponent

    override fun onCreate() {
        super.onCreate()

        this.mMvvmComponent = DaggerMvvmComponent
            .builder()
            .appComponent(obtainAppComponent())
            .build()
        mMvvmComponent.inject(this)

        setUpAppChildComponent(mMvvmComponent)
    }

    /**
     * 提供给 {@link Application}实现类，进行{@code mvvmComponent}依赖
     * @param mvvmComponent [MvvmComponent]
     */
    abstract fun setUpAppChildComponent(mvvmComponent: MvvmComponent?)

    override fun androidInjector(): AndroidInjector<Any> = mAndroidInjector
}