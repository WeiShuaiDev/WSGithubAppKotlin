package com.linwei.cams_mvp.base

import com.linwei.cams.base.fragment.BaseFragment
import com.linwei.cams.di.component.AppComponent
import com.linwei.cams_mvp.di.component.BaseMvpFragmentComponent
import com.linwei.cams_mvp.di.component.DaggerBaseMvpFragmentComponent
import com.linwei.cams_mvp.lifecycle.FragmentRxLifecycle
import com.linwei.cams_mvp.mvp.BasePresenter
import com.linwei.cams_mvp.mvp.IModel
import com.linwei.cams_mvp.mvp.IView
import com.trello.rxlifecycle4.android.FragmentEvent
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/8
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: `MVP` 架构 `Fragment`基类
 *-----------------------------------------------------------------------
 */
abstract class BaseMvpFragment<T : BasePresenter<IModel, IView>> : BaseFragment(),
    FragmentRxLifecycle {

    private var mLifecycleSubject: BehaviorSubject<FragmentEvent> = BehaviorSubject.create()

    @Inject
    lateinit var mPresenter: T

    override fun setupFragmentComponent(appComponent: AppComponent?) {
        val mvpFragmentComponent: BaseMvpFragmentComponent = DaggerBaseMvpFragmentComponent.builder()
            .appComponent(appComponent) //提供application
            .build()

        setUpFragmentChildComponent(mvpFragmentComponent)
    }

    override fun provideLifecycleSubject(): Subject<FragmentEvent>? = mLifecycleSubject

    /**
     * 提供给 {@link Activity}实现类，进行{@code appComponent}依赖
     * @param mvpFragmentComponent [BaseMvpFragmentComponent]
     */
    abstract fun setUpFragmentChildComponent(mvpFragmentComponent: BaseMvpFragmentComponent?)

    override fun onDestroy() {
        super.onDestroy()

        mPresenter.onDestroy()
    }


}

