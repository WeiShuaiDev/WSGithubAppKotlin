package com.linwei.github_mvvm

import com.linwei.cams_mvvm.MvvmApplication
import com.linwei.cams_mvvm.di.component.MvvmComponent
import com.linwei.github_mvvm.di.component.DaggerGithubComponent
import com.linwei.github_mvvm.di.component.GithubComponent

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/24
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: [GithubApplication] 继承基类 [MvvmApplication],初始化 `Dagger`功能。
 *----------------------------------------------------------------------
 */
class GithubApplication : MvvmApplication() {
    private lateinit var mGithubComponent: GithubComponent

    override fun setUpAppChildComponent(mvvmComponent: MvvmComponent?) {
        mvvmComponent?.let {
            this.mGithubComponent = DaggerGithubComponent
                .builder()
                .appComponent(mvvmComponent)
                .build()
            mGithubComponent.inject(this)
        }
    }
}