package com.linwei.github_mvvm.mvvm.model.repository.login
import androidx.lifecycle.*
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.mvvm.contract.login.AccountLoginContract
import com.linwei.github_mvvm.mvvm.contract.login.OAuthLoginContract
import com.linwei.github_mvvm.mvvm.model.bean.*
import com.linwei.github_mvvm.mvvm.model.repository.service.UserRepository
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/20
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class LoginModel @Inject constructor(
        dataRepository: DataMvvmRepository,
        private val userRepository: UserRepository
) : BaseModel(dataRepository), AccountLoginContract.Model, OAuthLoginContract.Model {

    override fun obtainAccountLogin(
            owner: LifecycleOwner,
            username: String,
            password: String,
            observer: LiveDataCallBack<User>
    ) {
        userRepository.requestAccountLogin(owner, username, password, object : LiveDataCallBack<AuthResponse>() {
            override fun onSuccess(code: String?, data: AuthResponse?) {
                super.onSuccess(code, data)
                userRepository.requestAuthenticatedUserInfo(owner, "", observer)
            }

            override fun onFailure(code: String?, message: String?) {
                super.onFailure(code, message)
                observer.onFailure(code, message)
            }
        })
    }

    override fun obtainOAuthLogin(
            owner: LifecycleOwner,
            code: String,
            observer: LiveDataCallBack<User>
    ) {
        userRepository.requestOAuthLogin(owner, code, object : LiveDataCallBack<AccessToken>() {
            override fun onSuccess(code: String?, data: AccessToken?) {
                super.onSuccess(code, data)
                userRepository.requestAuthenticatedUserInfo(owner, "", observer)
            }

            override fun onFailure(code: String?, message: String?) {
                super.onFailure(code, message)
                observer.onFailure(code, message)
            }
        })
    }
}