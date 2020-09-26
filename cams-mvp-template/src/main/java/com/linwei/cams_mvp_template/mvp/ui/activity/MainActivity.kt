package com.linwei.cams_mvp_template.mvp.ui.activity

import android.os.Bundle
import com.linwei.cams_mvp.base.BaseMvpActivity
import com.linwei.cams_mvp.di.component.MvpActivityComponent
import com.linwei.cams_mvp_template.R
import com.linwei.cams_mvp_template.di.component.DaggerMainActivityComponent
import com.linwei.cams_mvp_template.di.component.MainActivityComponent
import com.linwei.cams_mvp_template.mvp.presenter.MainPresenter
import com.linwei.cams_mvp_template.mvp.contract.MainContract

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

    override fun setUpActivityChildComponent(mvpActivityComponent: MvpActivityComponent) {
        val mainActivityComponent: MainActivityComponent =
            DaggerMainActivityComponent.builder()
                .component(mvpActivityComponent) //提供application
                .build()
        mainActivityComponent.inject(this)
    }

    override fun initLayoutView(savedInstanceState: Bundle?) {
    }

    override fun initLayoutData() {
    }

    override fun initLayoutListener() {
    }

    override fun provideContentViewId(): Int = R.layout.activity_main


}
