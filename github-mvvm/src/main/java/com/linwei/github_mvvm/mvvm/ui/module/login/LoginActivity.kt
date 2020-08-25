package com.linwei.github_mvvm.mvvm.ui.module.login

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.linwei.cams.base.activity.BaseActivity
import com.linwei.cams.di.component.AppComponent
import com.linwei.cams_mvvm.base.BaseMvvmActivity
import com.linwei.cams_mvvm.base.BaseMvvmFragment
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.R
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class LoginActivity : BaseActivity(), HasAndroidInjector {

    @Inject
    lateinit var mAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        //Dagger.Android Fragment 注入
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun provideContentViewId(): Int = R.layout.activity_login



    override fun initLayoutView() {

    }

    override fun initLayoutData() {
    }

    override fun initLayoutListener() {
    }

    override fun androidInjector(): AndroidInjector<Any> = mAndroidInjector

}