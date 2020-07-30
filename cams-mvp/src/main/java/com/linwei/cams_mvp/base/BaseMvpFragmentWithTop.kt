package com.linwei.cams_mvp.base

import android.app.Dialog
import com.linwei.cams.di.component.AppComponent
import com.linwei.cams.ext.showShort
import com.linwei.cams.utils.DialogUtils
import com.linwei.cams_mvp.R
import com.linwei.cams_mvp.di.component.BaseMvpFragmentComponent
import com.linwei.cams_mvp.di.component.DaggerBaseMvpFragmentComponent
import com.linwei.cams_mvp.lifecycle.FragmentRxLifecycle
import com.linwei.cams_mvp.mvp.BasePresenter
import com.linwei.cams_mvp.mvp.IModel
import com.linwei.cams_mvp.mvp.IView
import com.mimefin.baselib.common.fragment.BaseFragmentWithTop
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
abstract class BaseMvpFragmentWithTop<T : BasePresenter<IModel, IView>> : BaseFragmentWithTop(),IView,
    FragmentRxLifecycle {

    private var mLifecycleSubject: BehaviorSubject<FragmentEvent> = BehaviorSubject.create()

    @Inject
    lateinit var mPresenter: T

    private var mProgressDialog: Dialog? = null

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

