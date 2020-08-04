package com.linwei.wsgithubappkotlin.mvvm.ui.activity
import com.linwei.cams_mvvm.base.BaseMvvmActivity
import com.linwei.wsgithubappkotlin.R
import com.linwei.wsgithubappkotlin.databinding.ActivityMainBinding
import com.linwei.wsgithubappkotlin.mvvm.viewmodel.MainViewModel

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/3
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class MainActivity : BaseMvvmActivity<MainViewModel, ActivityMainBinding>() {

    override fun provideContentViewId(): Int= R.layout.activity_main

    override fun initLayoutView() {
    }

    override fun initLayoutData() {
    }

    override fun initLayoutListener() {
    }



}
