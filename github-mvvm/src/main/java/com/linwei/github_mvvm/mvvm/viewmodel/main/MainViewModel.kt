package com.linwei.github_mvvm.mvvm.viewmodel.main

import android.app.Application
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.ext.jumpUserActivity
import com.linwei.github_mvvm.mvvm.contract.login.MainContract
import com.linwei.github_mvvm.mvvm.model.login.LoginModel
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class MainViewModel @Inject constructor(
    val model: LoginModel,
    application: Application
) : BaseViewModel(model, application), MainContract.ViewModel {

    override fun toSignOut() {
        //删除用户信息，退出登录
        model.signOut()

        //跳转到登录页面
        jumpUserActivity()
    }
}