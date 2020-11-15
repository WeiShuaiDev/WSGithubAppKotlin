package com.linwei.github_mvvm.di.module.logic
import android.app.Application
import androidx.core.content.ContextCompat
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.factory.IconFontFactory
import com.mikepenz.iconics.IconicsColor
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.utils.sizeDp
import dagger.Module
import dagger.Provides
import devlight.io.library.ntb.NavigationTabBar

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/6
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description: `Repos` 模块 Module
 *-----------------------------------------------------------------------
 */
@Module
class ReposIssueListModule {

    @Provides
    fun providerReposIssueListTabModel(application: Application): List<NavigationTabBar.Model> {
        return listOf(
            NavigationTabBar.Model.Builder(
                IconicsDrawable(application)
                    .icon(IconFontFactory.Icon.APP_REPOS_ITEM_ALL)
                    .color(IconicsColor.colorInt(R.color.colorSubText))
                    .sizeDp(14),
                ContextCompat.getColor(application, R.color.colorPrimaryText))
                .title(application.getString(R.string.issue_all_text))
                .build(),
            NavigationTabBar.Model.Builder(
                IconicsDrawable(application)
                    .icon(IconFontFactory.Icon.APP_REPOS_ITEM_OPEN)
                    .color(IconicsColor.colorInt(R.color.colorSubText))
                    .sizeDp(14),
                ContextCompat.getColor(application, R.color.colorPrimaryText))
                .title(application.getString(R.string.issue_open_text))
                .build(),
            NavigationTabBar.Model.Builder(
                IconicsDrawable(application)
                    .icon(IconFontFactory.Icon.APP_REPOS_ITEM_CLOSE)
                    .color(IconicsColor.colorInt(R.color.colorSubText))
                    .sizeDp(14),
                ContextCompat.getColor(application, R.color.colorPrimaryText))
                .title(application.getString(R.string.issue_close_text))
                .build()
        )

    }

    @Provides
    fun providerStatusList(): List<String> = arrayListOf("all", "open", "closed")
}