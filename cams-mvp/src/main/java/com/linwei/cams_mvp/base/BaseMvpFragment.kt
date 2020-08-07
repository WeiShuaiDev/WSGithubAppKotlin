package com.linwei.cams_mvp.base

import android.app.Dialog
import com.linwei.cams.base.fragment.BaseFragment
import com.linwei.cams.di.component.AppComponent
import com.linwei.cams.ext.showShort
import com.linwei.cams.utils.DialogUtils
import com.linwei.cams_mvp.R
import com.linwei.cams_mvp.di.component.DaggerMvpFragmentComponent
import com.linwei.cams_mvp.di.component.MvpFragmentComponent
import com.linwei.cams_mvp.lifecycle.FragmentRxLifecycle
import com.linwei.cams_mvp.mvp.BasePresenter
import com.linwei.cams_mvp.mvp.IModel
import com.linwei.cams_mvp.mvp.IPresenter
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
abstract class BaseMvpFragment<T : IPresenter> : BaseFragment(),IView,
    FragmentRxLifecycle {

    private var mLifecycleSubject: BehaviorSubject<FragmentEvent> = BehaviorSubject.create()

    @Inject
    lateinit var mPresenter: T

    private var mProgressDialog: Dialog? = null

    override fun setupFragmentComponent(appComponent: AppComponent?) {
        val mvpFragmentComponent: MvpFragmentComponent = DaggerMvpFragmentComponent.builder()
            .appComponent(appComponent) //提供application
            .build()

        setUpFragmentChildComponent(mvpFragmentComponent)
    }

    override fun provideLifecycleSubject(): Subject<FragmentEvent>? = mLifecycleSubject

    /**
     * 提供给 {@link Activity}实现类，进行{@code appComponent}依赖
     * @param mvpFragmentComponent [MvpFragmentComponent]
     */
    abstract fun setUpFragmentChildComponent(mvpFragmentComponent: MvpFragmentComponent?)


    override fun showLoading(message: Int) {
        if (mProgressDialog?.isShowing == true) {
            mProgressDialog?.hide()
        }
        mProgressDialog = DialogUtils.createCustomDialog(mContext, R.layout.progress_dialog)
        mProgressDialog?.show()
    }


    override fun hideLoading() {
        mProgressDialog?.hide()
    }

    override fun showMessage(message: Int) {
        message.showShort()
    }

    override fun showMessage(message: String) {
        message.showShort()
    }

    override fun onDestroy() {
        super.onDestroy()

        mPresenter.onDestroy()

        if (mProgressDialog?.isShowing == true) {
            mProgressDialog?.dismiss()
        }
        mProgressDialog = null
    }


}

