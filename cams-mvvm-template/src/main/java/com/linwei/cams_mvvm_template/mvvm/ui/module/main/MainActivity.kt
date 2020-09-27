package com.linwei.cams_mvvm_template.mvvm.ui.module.main

import android.os.Bundle
import com.linwei.cams_mvvm.base.BaseMvvmActivity
import com.linwei.cams_mvvm_template.R
import com.linwei.cams_mvvm_template.databinding.ActivityMainBinding
import com.linwei.cams_mvvm_template.mvvm.contract.main.MainContract
import com.linwei.cams_mvvm_template.mvvm.viewmodel.main.MainViewModel

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/3
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class MainActivity : BaseMvvmActivity<MainViewModel, ActivityMainBinding>(), MainContract.View {

    override fun provideContentViewId(): Int = R.layout.activity_main

    override fun bindViewModel() {
        mViewDataBinding?.let {
            it.viewModel = mViewModel
            it.lifecycleOwner = this@MainActivity
        }
    }

    override fun initLayoutView(savedInstanceState: Bundle?) {
    }

    override fun initLayoutData() {
    }

    override fun initLayoutListener() {
    }


}
