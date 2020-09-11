package com.linwei.github_mvvm.mvvm.model.login

import android.util.Base64
import androidx.lifecycle.*
import com.linwei.cams.ext.pref
import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.mvvm.contract.login.AccountLoginContract
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.userBasicCodePref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.userNamePref
import com.linwei.github_mvvm.mvvm.model.api.service.AuthService
import com.linwei.github_mvvm.mvvm.model.api.service.UserService
import com.linwei.github_mvvm.mvvm.model.bean.AuthRequestBean
import com.linwei.github_mvvm.mvvm.model.bean.AuthResponseBean
import com.linwei.github_mvvm.mvvm.model.bean.UserInfoBean
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
class AccountLoginModel @Inject constructor(val dataRepository: DataMvvmRepository) :
    BaseModel(dataRepository), AccountLoginContract.Model {


    /**
     *  用户权限校验服务接口
     */
    private val authService: AuthService by lazy {
        dataRepository.obtainRetrofitService(AuthService::class.java)
    }

    /**
     *用户信息操作服务接口
     */
    private val userService: UserService by lazy {
        dataRepository.obtainRetrofitService(UserService::class.java)
    }


    override fun requestAccountLogin(
        username: String,
        password: String
    ): LiveData<AuthResponseBean> {
        clearTokenStorage()

        val type = "$username:$password"

        val userCipherTextInfo: String =
            Base64.encodeToString(type.toByteArray(), Base64.NO_WRAP).replace("\\+", "%2B")

        //保存用户名明文、密文信息
        userNamePref = username
        userBasicCodePref = userCipherTextInfo

        return requestCreateAuthorization()
    }

    override fun requestCreateAuthorization(): LiveData<AuthResponseBean> {
        return authService.createAuthorization(AuthRequestBean.generate())
    }

    override fun requestDeleteAuthorization(id: Int): LiveData<Any> {
        return authService.deleteAuthorization(id)
    }

    override fun requestAuthenticatedUserInfo(): LiveData<UserInfoBean> {
        return userService.fetchAuthenticatedUserInfo()
    }

    override fun clearTokenStorage() {
        userNamePref = ""
        userBasicCodePref = ""
    }
}