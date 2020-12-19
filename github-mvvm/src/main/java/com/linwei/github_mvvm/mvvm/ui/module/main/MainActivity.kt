package com.linwei.github_mvvm.mvvm.ui.module.main

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.linwei.cams.ext.showShort
import com.linwei.cams_mvvm.base.BaseMvvmActivity
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.contract.login.MainContract
import com.linwei.github_mvvm.mvvm.model.AppGlobalModel
import com.linwei.github_mvvm.mvvm.ui.adapter.FragmentPagerViewAdapter
import com.linwei.github_mvvm.mvvm.ui.view.ExpandNavigationTabBar
import com.linwei.github_mvvm.mvvm.viewmodel.main.MainViewModel
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import devlight.io.library.ntb.NavigationTabBar
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: 首页
 *-----------------------------------------------------------------------
 */
class MainActivity : BaseMvvmActivity<MainViewModel, ViewDataBinding>(),
    Toolbar.OnMenuItemClickListener, MainContract.View {

    private var mPreTime: Long = 0

    private var mDrawer: Drawer? = null

    /**
     * 全局数据
     */
    @Inject
    lateinit var globalModel: AppGlobalModel

    /**
     * fragment列表
     */
    @Inject
    lateinit var mainFragmentList: MutableList<Fragment>

    /**
     * tab列表
     */
    @Inject
    lateinit var mainTabModel: MutableList<NavigationTabBar.Model>

    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity<MainActivity>()
        }
    }

    override fun useDataBinding(): Boolean = false

    override fun provideContentViewId(): Int = R.layout.activity_main

    override fun initLayoutView(savedInstanceState: Bundle?) {
        initToolbar()
        initViewPager()

        initMaterialDrawer(savedInstanceState)
    }

    override fun initLayoutData() {

    }

    override fun initLayoutListener() {

    }

    override fun bindViewModel() {
        mViewModel?.mLifecycleOwner = this
    }

    /**
     * 初始化 `Toolbar`
     */
    private fun initToolbar() {
        setSupportActionBar(activity_main_toolbar)
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowTitleEnabled(false)
        }
        activity_main_toolbar.setTitle(R.string.app_name)
        activity_main_toolbar.setOnMenuItemClickListener(this)
    }

    /**
     * 初始化 `ViewPager`
     */
    private fun initViewPager() {
        activity_main_viewpager.adapter = FragmentPagerViewAdapter(mainFragmentList, supportFragmentManager)
        activity_main_tab_bar.models = mainTabModel
        activity_main_tab_bar.setViewPager(activity_main_viewpager, 0)
        activity_main_viewpager.offscreenPageLimit = mainFragmentList.size

        activity_main_tab_bar.doubleTouchListener =
            object : ExpandNavigationTabBar.TabDoubleClickListener {
                override fun onDoubleClick(position: Int) {
                }
            }
    }

    /**
     * 初始化 `MaterialDrawer` 控件。
     * @param savedInstanceState [Bundle]
     */
    private fun initMaterialDrawer(savedInstanceState: Bundle?) {
        mDrawer = DrawerBuilder()
            .withActivity(this)
            .withToolbar(activity_main_toolbar)
            .withSelectedItem(-1)
            .addDrawerItems(
                PrimaryDrawerItem().withName(R.string.main_feedback)
                    .withTextColorRes(R.color.colorPrimary)
                    .withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                        override fun onItemClick(
                            view: View?,
                            position: Int,
                            drawerItem: IDrawerItem<*>
                        ): Boolean {
                            unSelect(drawerItem)
                            return true
                        }
                    })
            )
            .addDrawerItems(
                PrimaryDrawerItem().withName(R.string.main_person)
                    .withTextColorRes(R.color.colorPrimary)
                    .withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                        override fun onItemClick(
                            view: View?,
                            position: Int,
                            drawerItem: IDrawerItem<*>
                        ): Boolean {
                            unSelect(drawerItem)
                            return true
                        }
                    })
            )
            .addDrawerItems(
                PrimaryDrawerItem().withName(R.string.main_update)
                    .withTextColorRes(R.color.colorPrimary)
                    .withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                        override fun onItemClick(
                            view: View?,
                            position: Int,
                            drawerItem: IDrawerItem<*>
                        ): Boolean {
                            unSelect(drawerItem)
                            return true
                        }
                    })
            )
            .addDrawerItems(
                PrimaryDrawerItem().withName(R.string.main_about)
                    .withTextColorRes(R.color.colorPrimary)
                    .withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                        override fun onItemClick(
                            view: View?,
                            position: Int,
                            drawerItem: IDrawerItem<*>
                        ): Boolean {
                            unSelect(drawerItem)
                            return true
                        }
                    })
            )
            .addDrawerItems(
                PrimaryDrawerItem().withName(R.string.main_login_out)
                    .withTextColorRes(R.color.colorDrawerLightText)
                    .withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                        override fun onItemClick(
                            view: View?,
                            position: Int,
                            drawerItem: IDrawerItem<*>
                        ): Boolean {
                            mViewModel?.toSignOut()
                            unSelect(drawerItem)
                            return true
                        }
                    })
            )
            .withAccountHeader(
                AccountHeaderBuilder()
                    .withActivity(this)
                    .addProfiles(
                        ProfileDrawerItem().withName(globalModel.userObservable.login)
                            .withSelected(false)
                            .withTextColorRes(R.color.colorGlobalWhite)
                            .withIcon(
                                Uri.parse(globalModel.userObservable.avatarUrl)
                            )
                            .withEmail(globalModel.userObservable.email ?: "")
                    )
                    .withHeaderBackground(R.color.colorPrimary)
                    .withTextColorRes(R.color.colorGlobalTextWhite)
                    .withSelectionListEnabled(false)
                    .withSavedInstance(savedInstanceState)
                    .withTranslucentStatusBar(true)
                    .build()
            ).build()
    }

    /**
     * `MaterialDrawer` 控件修改当前 `Item` 状态。
     * @param drawerItem [IDrawerItem]
     */
    private fun unSelect(drawerItem: IDrawerItem<*>) {
        drawerItem.isSelected = false
        mDrawer?.adapter?.notifyAdapterDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return true
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_search -> {

            }
        }
        return true
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
