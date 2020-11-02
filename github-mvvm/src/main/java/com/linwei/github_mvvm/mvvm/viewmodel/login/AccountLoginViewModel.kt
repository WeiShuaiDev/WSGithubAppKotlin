package com.linwei.github_mvvm.mvvm.viewmodel.login

import android.app.Application
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.ext.*
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams.http.model.StatusCode
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.contract.login.AccountLoginContract
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.passwordPref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.userNamePref
import com.linwei.github_mvvm.mvvm.model.bean.User
import com.linwei.github_mvvm.mvvm.model.repository.login.LoginModel
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDevA
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
     * 登录状态
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
            postMessage(obj = R.string.logcat_login_user_name_entry.string())
            return
        }

        if (isEmptyParameter(password)) {
            postMessage(obj = R.string.logcat_login_password_entry.string())
            return
        }

        mLifecycleOwner?.let {
            postUpdateStatus(StatusCode.START)

            model.obtainAccountLogin(
                it,
                username!!,
                password!!,
                object : LiveDataCallBack<User>() {
                    override fun onSuccess(code: String?, data: User?) {
                        super.onSuccess(code, data)
                        postUpdateStatus(StatusCode.END)

                        _loginResult.value = true
                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                        postUpdateStatus(StatusCode.END)
                        postMessage(obj = message)

                        _loginResult.value = false
                    }
                })
        }
    }
}