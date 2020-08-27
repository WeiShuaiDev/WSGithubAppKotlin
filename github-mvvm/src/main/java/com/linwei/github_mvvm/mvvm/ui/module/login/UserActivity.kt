package com.linwei.github_mvvm.mvvm.ui.module.login

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.linwei.cams_mvvm.base.BaseMvvmActivity
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.R
import dagger.android.AndroidInjection

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class UserActivity :  BaseMvvmActivity<BaseViewModel, ViewDataBinding>() {

    override fun useDataBinding(): Boolean = false

    override fun setUpOnCreateAndSuperStart(savedInstanceState: Bundle?) {
        super.setUpOnCreateAndSuperStart(savedInstanceState)
        AndroidInjection.inject(this)
    }

    override fun provideContentViewId(): Int = R.layout.activity_user


    override fun initLayoutView() {
    }

    override fun initLayoutData() {
    }

    override fun initLayoutListener() {
    }

    override fun bindViewModel() {

    }


}