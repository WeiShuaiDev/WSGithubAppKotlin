package com.linwei.github_mvvm.mvvm.viewmodel.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.ext.yes
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.mvvm.contract.login.MainContract
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage
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

    /**
     * 退出登录状态
     */
    private val _signOutResult = MutableLiveData<Boolean>()
    val signOutResult: LiveData<Boolean>
        get() = _signOutResult

    override fun toSignOut() {
        UserInfoStorage.isLoginState.yes {
            mLifecycleOwner?.let {
                model.signOut(it, _signOutResult)
            }
        }
    }
}