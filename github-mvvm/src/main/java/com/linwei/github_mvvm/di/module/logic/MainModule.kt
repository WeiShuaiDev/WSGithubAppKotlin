package com.linwei.github_mvvm.di.module.logic

import android.app.Application
import android.graphics.Color
import androidx.fragment.app.Fragment
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.factory.IconFontFactory
import com.linwei.github_mvvm.mvvm.ui.module.main.DynamicFragment
import com.linwei.github_mvvm.mvvm.ui.module.main.MineFragment
import com.linwei.github_mvvm.mvvm.ui.module.main.RecommendedFragment
import com.mikepenz.iconics.IconicsColor
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.utils.sizeDp
import dagger.Module
import dagger.Provides
import devlight.io.library.ntb.NavigationTabBar

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/9/25
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description: `Main` 模块 Module
 *-----------------------------------------------------------------------
 */
@Module
class MainModule {

    /**
     * `ViewPager` 加载 `Fragment` List集合
     * @return [List]
     */
    @Provides
    fun providerMainFragmentList(): List<Fragment> {
        return listOf(DynamicFragment(), RecommendedFragment(), MineFragment())
    }

    /**
     * 底部 `Tab` List集合
     * @param application [Application]
     * @return [List]
     */
    @Provides
    fun providerMainTabModelList(application: Application): List<NavigationTabBar.Model> {
        return listOf(
            NavigationTabBar.Model.Builder(
                IconicsDrawable(application)
                    .icon(IconFontFactory.Icon.APP_MAIN_DT)
                    .color(IconicsColor.colorInt(R.color.colorNavigationActiveTab))
                    .sizeDp(20),
                Color.parseColor("#00000000")
            )
                .title(application.getString(R.string.tab_dynamic))
                .build(),
            NavigationTabBar.Model.Builder(
                IconicsDrawable(application)
                    .icon(IconFontFactory.Icon.APP_MAIN_QS)
                    .color(IconicsColor.colorInt(R.color.colorNavigationActiveTab))
                    .sizeDp(20),
                Color.parseColor("#00000000")
            )
                .title(application.getString(R.string.tab_recommended))
                .build(),
            NavigationTabBar.Model.Builder(
                IconicsDrawable(application)
                    .icon(IconFontFactory.Icon.APP_MAIN_MY)
                    .color(IconicsColor.colorInt(R.color.colorNavigationActiveTab))
                    .sizeDp(20),
                Color.parseColor("#00000000")
            )
                .title(application.getString(R.string.tab_mine))
                .build()
        )

    }
}
