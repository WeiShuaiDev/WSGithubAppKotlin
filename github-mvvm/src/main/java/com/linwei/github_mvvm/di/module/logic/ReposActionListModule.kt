package com.linwei.github_mvvm.di.module.logic

import android.app.Application
import android.graphics.Color
import com.linwei.github_mvvm.R
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
class ReposActionListModule {

    /**
     * @param application [Application]
     */
    @Provides
    fun providerActionTabModel(application: Application): List<NavigationTabBar.Model> {
        return listOf(
            NavigationTabBar.Model.Builder(
                null,
                Color.parseColor("#00000000")
            ).title(application.getString(R.string.repos_activity))
                .build(),
            NavigationTabBar.Model.Builder(
                null,
                Color.parseColor("#00000000")
            ).title(application.getString(R.string.repos_push))
                .build()
        )
    }
}