package com.linwei.github_mvvm.mvvm.ui.module.login

import android.content.Context
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.linwei.cams_mvvm.base.BaseMvvmActivity
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.ui.module.main.MainActivity
import dagger.android.AndroidInjection
import org.jetbrains.anko.startActivity

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: `Activity` 登录模块,提供两种登录方式，普通登录 `AccountLoginFragment`,
 *              OAuth登录 `OAuthLoginFragment`
 *-----------------------------------------------------------------------
 */
class UserActivity : BaseMvvmActivity<BaseViewModel, ViewDataBinding>() {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity<UserActivity>()
        }
    }

    override fun useDataBinding(): Boolean = false

    override fun setUpOnCreateAndSuperStart(savedInstanceState: Bundle?) {
        super.setUpOnCreateAndSuperStart(savedInstanceState)
        AndroidInjection.inject(this)
    }

    override fun provideContentViewId(): Int = R.layout.activity_user


    override fun initLayoutView(savedInstanceState: Bundle?) {
    }

    override fun initLayoutData() {
    }

    override fun initLayoutListener() {
    }

    override fun bindViewModel() {

    }

    override fun onBackPressed() {
        val fragment: Fragment? = supportFragmentManager.primaryNavigationFragment
        if (fragment is NavHostFragment) {
            if (fragment.navController.currentDestination?.id == R.id.accountLoginFragment) {
                super.onBackPressed()
            } else if (fragment.navController.currentDestination?.id == R.id.oAuthLoginFragment) {
                fragment.navController.navigate(
                    R.id.accountLoginFragment,
                    null, NavOptions.Builder().setPopUpTo(
                        fragment.navController.graph.id,
                        true
                    ).build()
                )
            }
        }
    }

    /**
     * 销毁 `Activity`,资源回收
     */
    fun activityFinish() {
        this.finish()
    }

}