package com.linwei.github_mvvm.mvvm.viewmodel.login

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.ext.*
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.contract.login.AccountLoginContract
import com.linwei.github_mvvm.mvvm.model.login.AccountLoginModel
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
    val model: AccountLoginModel,
    application: Application
) : BaseViewModel(model, application), AccountLoginContract.ViewModel {

    private var prefUserName: String by pref("")

    private var prefPassword: String by pref("")

    /**
     * 用户名
     */
    val username = MutableLiveData<String>()

    /**
     * 密码
     */
    val password = MutableLiveData<String>()

    init {
        username.value = prefUserName
        password.value = prefPassword
    }

    /**
     * 登录按钮点击逻辑处理
     * @param v [View]
     */
    fun onSubmitClick(v: View) {

        toAccountLogin(username.value, password.value)
    }

    override fun toAccountLogin(username: String?, password: String?) {

        if (username.isNotNullOrEmpty()) {
            R.string.logcat_login_user_name_entry.showShort()
        }

        if (password.isNotNullOrEmpty()) {
            R.string.logcat_login_password_entry.showShort()
        }

        model.requestAccountLogin(username!!, password!!)

    }

}