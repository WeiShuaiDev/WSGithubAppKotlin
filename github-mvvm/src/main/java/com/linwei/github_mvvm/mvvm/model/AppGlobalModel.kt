package com.linwei.github_mvvm.mvvm.model

import com.linwei.github_mvvm.mvvm.model.data.UserUIModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/9/25
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: App全局数据对象
 *-----------------------------------------------------------------------
 */
@Singleton
class AppGlobalModel @Inject constructor() {
    val userObservable = UserUIModel()
}