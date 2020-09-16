package com.linwei.github_mvvm.mvvm.viewmodel.login

import android.app.Application
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.ext.*
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.contract.login.AccountLoginContract
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.passwordPref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.userNamePref
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
class AccountLoginViewModel @Inject constructor(
    val model: LoginModel,
    application: Application
) : BaseViewModel(model, application), AccountLoginContract.ViewModel {

    /**
     * 用户名
     */
    val userNameField: MutableLiveData<String> = MutableLiveData("")

    /**
     * 密码
     */
    val passwordField: MutableLiveData<String> = MutableLiveData("")

    /**
     * 登录结果
     */
    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean>
        get() = _loginResult


    init {
        userNameField.value = userNamePref
        passwordField.value = passwordPref
    }

    /**
     * 登录按钮点击逻辑处理
     * @param v [View]
     */
    fun onSubmitClick(v: View) {
        toAccountLogin(userNameField.value, passwordField.value)
    }

    override fun toAccountLogin(username: String?, password: String?) {
        if (isEmptyParameter(username)) {
            R.string.logcat_login_user_name_entry.showShort()
            return
        }

        if (isEmptyParameter(password)) {
            R.string.logcat_login_password_entry.showShort()
            return
        }

        mLifecycleOwner?.let {
            model.requestAccountLogin(it, username!!, password!!, _loginResult)
        }
    }
}