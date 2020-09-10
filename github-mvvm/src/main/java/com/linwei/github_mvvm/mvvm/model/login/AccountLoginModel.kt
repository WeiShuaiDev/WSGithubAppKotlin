package com.linwei.github_mvvm.mvvm.model.login

import android.util.Base64
import androidx.lifecycle.*
import com.linwei.cams.ext.pref
import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.mvvm.contract.login.AccountLoginContract
import com.linwei.github_mvvm.mvvm.model.api.service.UserService
import com.linwei.github_mvvm.mvvm.model.bean.AccessToken
import com.linwei.github_mvvm.mvvm.model.bean.LoginRequestModel
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
     * 用户名
     */
    private var userNameStorage: String by pref("")

    /**
     * 密码
     */
    private var passwordStorage: String by pref("")

    /**
     * 访问令牌
     */
    private var accessTokenStorage: String by pref("")

    /**
     * 用户密文信息
     */
    private var userBasicCodeStorage: String by pref("")

    /**
     * 用户信息
     */
    private var userInfoStorage: String by pref("")

    /**
     *  用户网络服务接口
     */
    private val userService: UserService by lazy {
        dataRepository.obtainRetrofitService(UserService::class.java)
    }


    override fun requestAccountLogin(
        username: String,
        password: String
    ) {
        //清除 `Token` 信息
        clearTokenStorage()

        val type = "$username:$password"

        val userCipherTextInfo: String =
            Base64.encodeToString(type.toByteArray(), Base64.NO_WRAP).replace("\\+", "%2B")

        userNameStorage = username
        userBasicCodeStorage = userCipherTextInfo

    }


    override fun requestTokenObservable(): LiveData<AccessToken> {
        return dataRepository.obtainRetrofitService(UserService::class.java)
            .authorizations(LoginRequestModel.generate())
//            System.out.println("+++${it.string()}")
//            var accessToken = ""
//            if (ApiStateConstant.REQUEST_SUCCESS == it.code) {
//                accessToken = it.result?.access_token ?: ""
//                accessTokenStorage = accessToken
//            } else {
//                clearTokenStorage()
//            }
//            accessToken
//        }
    }


    /**
     * 清除 `Token` 缓存信息
     */
    private fun clearTokenStorage() {
        accessTokenStorage = ""
        userBasicCodeStorage = ""
    }

    override fun requestCodeTokenObservable(code: String): LiveData<String> {
        return userService.authorizationsCode(
            LoginRequestModel.clientId,
            LoginRequestModel.clientSecret,
            code
        ).map {
            var userBasicCode = ""
            if (it.isSuccessful) {
                userBasicCode = it.body()?.access_token ?: ""
                userBasicCodeStorage = userBasicCode
            } else {
                clearTokenStorage()
            }
            userBasicCode
        }
    }
}