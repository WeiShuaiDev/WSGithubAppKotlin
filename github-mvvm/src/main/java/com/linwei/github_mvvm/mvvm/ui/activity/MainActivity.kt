package com.linwei.github_mvvm.mvvm.ui.activity

import com.linwei.cams_mvvm.base.BaseMvvmActivity
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.databinding.ActivityMainBinding
import com.linwei.github_mvvm.mvvm.contract.MainContract
import com.linwei.github_mvvm.mvvm.viewmodel.MainViewModel

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class MainActivity : BaseMvvmActivity<MainViewModel, ActivityMainBinding>(), MainContract.View {

    override fun provideContentViewId(): Int = R.layout.activity_main

    override fun initLayoutView() {
    }

    override fun initLayoutData() {
    }

    override fun initLayoutListener() {
    }
}
