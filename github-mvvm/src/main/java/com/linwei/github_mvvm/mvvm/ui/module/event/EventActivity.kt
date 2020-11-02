package com.linwei.github_mvvm.mvvm.ui.module.event

import android.content.Context
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.linwei.cams_mvvm.base.BaseMvvmActivity
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.ui.module.login.UserActivity
import dagger.android.AndroidInjection
import org.jetbrains.anko.startActivity

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/2
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class EventActivity : BaseMvvmActivity<BaseViewModel, ViewDataBinding>() {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity<EventActivity>()
        }
    }

    override fun useDataBinding(): Boolean = false

    override fun setUpOnCreateAndSuperStart(savedInstanceState: Bundle?) {
        super.setUpOnCreateAndSuperStart(savedInstanceState)
        AndroidInjection.inject(this)
    }

    override fun provideContentViewId(): Int = R.layout.activity_event

    override fun initLayoutView(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun initLayoutData() {
        TODO("Not yet implemented")
    }

    override fun initLayoutListener() {
        TODO("Not yet implemented")
    }

    override fun bindViewModel() {
        TODO("Not yet implemented")
    }

}