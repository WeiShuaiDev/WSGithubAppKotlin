package com.linwei.github_mvvm.mvvm.model.login

import android.util.Base64
import androidx.lifecycle.*
import com.linwei.cams.ext.isEmptyParameter
import com.linwei.cams.ext.no
import com.linwei.cams.ext.otherwise
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.mvvm.contract.login.AccountLoginContract
import com.linwei.github_mvvm.mvvm.contract.login.OAuthLoginContract
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.accessTokenPref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.authIDPref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.authInfoPref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.putAuthInfoPref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.putUserInfoPref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.userBasicCodePref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.userInfoPref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.userNamePref
import com.linwei.github_mvvm.mvvm.model.api.service.AuthService
import com.linwei.github_mvvm.mvvm.model.api.service.UserService
import com.linwei.github_mvvm.mvvm.model.bean.AccessTokenBean
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
class LoginModel @Inject constructor(val dataRepository: DataMvvmRepository) :
    BaseModel(dataRepository), AccountLoginContract.Model, OAuthLoginContract.Model {

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

    private var mLoginResult = MutableLiveData<Boolean>()

    override fun requestAccountLogin(
        owner: LifecycleOwner,
        username: String,
        password: String,
        liveData: MutableLiveData<Boolean>
    ) {
        mLoginResult = liveData

        clearTokenStorage()

        val type = "$username:$password"

        val userCipherTextInfo: String =
            Base64.encodeToString(type.toByteArray(), Base64.NO_WRAP)
                .replace("\\+", "%2B")

        //保存用户名明文、密文信息
        userNamePref = username
        userBasicCodePref = userCipherTextInfo

        requestCreateAuthorization(owner)
    }

    override fun requestOAuthLogin(
        owner: LifecycleOwner,
        code: String,
        liveData: MutableLiveData<Boolean>
    ) {
        mLoginResult = liveData

        clearTokenStorage()

        requestCreateCodeAuthorization(owner, code)
    }

    override fun requestCreateCodeAuthorization(
        owner: LifecycleOwner,
        code: String
    ): LiveData<AccessTokenBean> {
        return authService.createCodeAuthorization(
            AuthRequestBean.clientId,
            AuthRequestBean.clientSecret,
            code
        ).apply {
            observe(
                owner,
                object : LiveDataCallBack<AccessTokenBean, AccessTokenBean>() {
                    override fun onSuccess(code: String?, data: AccessTokenBean?) {
                        super.onSuccess(code, data)
                        data?.let {
                            data.access_token?.let {
                                isEmptyParameter(it).no {
                                    accessTokenPref = it
                                    //获取 `UserInfo`信息数据
                                    requestAuthenticatedUserInfo(owner)
                                }.otherwise {
                                    //删除用户令牌信息
                                    clearTokenStorage()
                                }
                            }
                        }
                    }
                })
        }
    }


    override fun requestCreateAuthorization(owner: LifecycleOwner): LiveData<AuthResponseBean> {
        return authService.createAuthorization(AuthRequestBean.generate()).apply {
            observe(
                owner,
                object : LiveDataCallBack<AuthResponseBean, AuthResponseBean>() {
                    override fun onSuccess(code: String?, data: AuthResponseBean?) {
                        super.onSuccess(code, data)
                        data?.let {
                            data.token.isEmpty().no {
                                accessTokenPref = data.token
                                authIDPref = data.id.toString()

                                //保存 `AuthResponseBean` 认证信息
                                putAuthInfoPref(data)

                                //获取 `UserInfo`信息数据
                                requestAuthenticatedUserInfo(owner)
                            }.otherwise {
                                //删除用户令牌信息
                                requestDeleteAuthorization(owner, data.id)
                            }
                        }
                    }
                })
        }
    }


    override fun requestDeleteAuthorization(owner: LifecycleOwner, id: Int): LiveData<Any> {
        return authService.deleteAuthorization(id).apply {
            observe(owner, object : LiveDataCallBack<Any, Any>() {
                override fun onSuccess(code: String?, data: Any?) {
                    super.onSuccess(code, data)
                    data?.let {
                        accessTokenPref = ""
                        authIDPref = ""
                    }
                }
            })
        }
    }

    override fun requestAuthenticatedUserInfo(owner: LifecycleOwner): LiveData<UserInfoBean> {
        return userService.fetchAuthenticatedUserInfo().apply {
            observe(owner, object : LiveDataCallBack<UserInfoBean, UserInfoBean>() {
                override fun onSuccess(code: String?, data: UserInfoBean?) {
                    super.onSuccess(code, data)
                    data?.let {
                        //保存 `UserInfoBean` 用户数据
                        putUserInfoPref(data)

                        mLoginResult.value = true
                    }
                }

                override fun onFailure(code: String?, message: String?) {
                    super.onFailure(code, message)
                    clearTokenStorage()

                    mLoginResult.value = false
                }
            })
        }
    }

    override fun clearTokenStorage() {
        userInfoPref = ""
        authInfoPref = ""
    }
}