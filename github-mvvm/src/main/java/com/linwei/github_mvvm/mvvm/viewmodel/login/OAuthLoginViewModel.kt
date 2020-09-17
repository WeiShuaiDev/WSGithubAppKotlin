package com.linwei.github_mvvm.mvvm.viewmodel.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.ext.isEmptyParameter
import com.linwei.cams.ext.showShort
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.contract.login.OAuthLoginContract
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
class OAuthLoginViewModel @Inject constructor(
    val model: LoginModel,
    application: Application
) : BaseViewModel(model, application), OAuthLoginContract.ViewModel {

    /**
     * 登录结果
     */
    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean>
        get() = _loginResult

    override fun toOAuthLogin(code: String?) {
        if (isEmptyParameter(code)) {
            R.string.logcat_login_oauth_code.showShort()
            return
        }

        mLifecycleOwner?.let {
            model.requestOAuthLogin(it, code!!, _loginResult)
        }
    }
}