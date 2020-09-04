package com.linwei.github_mvvm.mvvm.ui.module.main

import android.content.Context
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.linwei.cams.base.activity.BaseActivity
import com.linwei.cams.ext.showShort
import com.linwei.cams_mvvm.base.BaseMvvmActivity
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.contract.main.RecommendedContract
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
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
class MainActivity : BaseMvvmActivity<BaseViewModel, ViewDataBinding>() {
    private var mPreTime: Long = 0

    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity<MainActivity>()
        }
    }

    override fun useDataBinding(): Boolean = false

    override fun provideContentViewId(): Int = R.layout.activity_main

    override fun initLayoutView() {
        initBottomNavigationView()
    }

    /**
     * 初始化NavigaionView
     */
    private fun initBottomNavigationView() {
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.mNavHostFragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        mBnvNavigation.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.recommendedFragment -> {

                }
                R.id.dynamicFragment -> {

                }
                R.id.mineFragment -> {

                }
            }
        }
    }

    override fun initLayoutData() {
    }

    override fun initLayoutListener() {
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - mPreTime < 1000) {
            this.finish()
            super.onBackPressed()
        } else {
            R.string.exit_app_tip.showShort()
            mPreTime = System.currentTimeMillis()
        }
    }

    override fun bindViewModel() {
    }

}
