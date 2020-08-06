package com.linwei.cams_mvvm_template.mvvm.ui.activity

import com.linwei.cams_mvp.base.BaseMvpActivity
import com.linwei.cams_mvp.di.component.MvpActivityComponent
import com.linwei.cams_mvp.di.component.MvpFragmentComponent
import com.linwei.cams_mvp_template.R
import com.linwei.cams_mvp_template.di.component.MainComponent
import com.linwei.cams_mvp_template.mvp.presenter.MainPresenter
import com.linwei.cams_mvvm_template.mvvm.contract.MainContract

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/6
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class MainActivity : BaseMvpActivity<MainPresenter>(), MainContract.View {

    override fun setUpActivityChildComponent(mvpActivityComponent: MvpActivityComponent?) {
//        val mainComponent: DaggerMainComponent =
//            DaggerMainComponent.builder()
//                .appComponent(appComponent) //提供application
//                .build()
    }

    override fun initLayoutView() {
    }

    override fun initLayoutData() {
    }

    override fun initLayoutListener() {
    }

    override fun provideContentViewId(): Int = R.layout.activity_main


}
