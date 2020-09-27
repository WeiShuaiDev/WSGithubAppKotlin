package com.linwei.cams_mvvm_template
import android.os.Bundle
import com.linwei.cams.base.activity.BaseActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/3
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class SplashActivity : BaseActivity(), HasAndroidInjector {

    @Inject
    lateinit var mAndroidInjector: DispatchingAndroidInjector<Any>

    override fun setUpOnCreateAndSuperStart(savedInstanceState: Bundle?) {
        super.setUpOnCreateAndSuperStart(savedInstanceState)
        AndroidInjection.inject(this)
    }

    override fun provideContentViewId(): Int = R.layout.activity_splash

    override fun initLayoutView(savedInstanceState: Bundle?) {
    }

    override fun initLayoutData() {
    }

    override fun initLayoutListener() {
    }

    override fun androidInjector(): AndroidInjector<Any> = mAndroidInjector
}