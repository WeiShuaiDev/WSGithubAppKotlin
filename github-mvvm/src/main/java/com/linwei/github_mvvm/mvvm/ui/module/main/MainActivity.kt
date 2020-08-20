package com.linwei.github_mvvm.mvvm.ui.module.main

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.linwei.cams.ext.showShort
import com.linwei.cams_mvvm.base.BaseMvvmActivity
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.databinding.ActivityMainBinding
import com.linwei.github_mvvm.mvvm.contract.main.RecommendedContract
import com.linwei.github_mvvm.mvvm.viewmodel.main.DynamicViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class MainActivity : BaseMvvmActivity<DynamicViewModel, ActivityMainBinding>(), RecommendedContract.View {
    private var mPreTime: Long = 0

    override fun provideContentViewId(): Int = R.layout.activity_main

    override fun bindViewModel() {
        mViewDataBinding?.let {
            it.viewModel = mViewModel
            it.lifecycleOwner = this@MainActivity
        }
    }

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
}
