package com.linwei.github_mvvm.mvvm.model

import com.linwei.github_mvvm.di.scope.GithubScope
import com.linwei.github_mvvm.mvvm.model.ui.UserUIModel
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/9/25
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: App全局数据对象
 *-----------------------------------------------------------------------
 */
@GithubScope
class AppGlobalModel @Inject constructor() {
    val userObservable = UserUIModel()
}