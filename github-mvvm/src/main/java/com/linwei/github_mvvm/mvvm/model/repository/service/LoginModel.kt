package com.linwei.github_mvvm.mvvm.model.repository.service

import android.app.Application
import android.os.Build
import android.util.Base64
import android.webkit.CookieManager
import android.webkit.WebStorage
import androidx.lifecycle.*
import com.linwei.cams.ext.*
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
import com.linwei.github_mvvm.mvvm.model.AppGlobalModel
import com.linwei.github_mvvm.mvvm.model.api.service.AuthService
import com.linwei.github_mvvm.mvvm.model.api.service.UserService
import com.linwei.github_mvvm.mvvm.model.bean.*
import com.linwei.github_mvvm.mvvm.model.conversion.UserConversion
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
    private val application: Application,
    private val appGlobalModel: AppGlobalModel,
    dataRepository: DataMvvmRepository
) : BaseModel(dataRepository), AccountLoginContract.Model, OAuthLoginContract.Model {

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

    private var mResultCallBack: LiveDataCallBack<Boolean>? = null

    override fun requestAccountLogin(
        owner: LifecycleOwner,
        username: String,
        password: String,
        observer: LiveDataCallBack<Boolean>
    ) {
        mResultCallBack = observer

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
        observer: LiveDataCallBack<Boolean>
    ) {
        mResultCallBack = observer

        clearTokenStorage()

        requestCreateCodeAuthorization(owner, code)
    }

    override fun requestCreateCodeAuthorization(
        owner: LifecycleOwner,
        code: String
    ): LiveData<AccessToken> {
        return authService.createCodeAuthorization(
            AuthRequest.clientId,
            AuthRequest.clientSecret,
            code
        ).apply {
            observe(
                owner,
                object : LiveDataCallBack<AccessToken>() {
                    override fun onSuccess(code: String?, data: AccessToken?) {
                        super.onSuccess(code, data)
                        data?.let {
                            data.accessToken.isNotNullOrEmpty().yes {
                                accessTokenPref = data.accessToken!!

                                //获取 `UserInfo`信息数据
                                requestAuthenticatedUserInfo(owner)
                            }.otherwise {
                                //删除用户令牌信息
                                clearTokenStorage()
                            }
                        }
                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                        mResultCallBack?.onFailure(code, message)
                    }
                })
        }
    }


    override fun requestCreateAuthorization(owner: LifecycleOwner): LiveData<AuthResponse> {
        return authService.createAuthorization(AuthRequest.generate()).apply {
            observe(
                owner,
                object : LiveDataCallBack<AuthResponse>() {
                    override fun onSuccess(code: String?, data: AuthResponse?) {
                        super.onSuccess(code, data)
                        data?.let {
                            data.token.isNotNullOrEmpty().yes {
                                accessTokenPref = data.token!!
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

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                        mResultCallBack?.onFailure(code, message)
                    }
                })
        }
    }

    override fun requestDeleteAuthorization(owner: LifecycleOwner, id: Int): LiveData<Any> {
        return authService.deleteAuthorization(id).apply {
            observe(owner, object : LiveDataCallBack<Any>() {
                override fun onSuccess(code: String?, data: Any?) {
                    super.onSuccess(code, data)
                    accessTokenPref = ""
                    authIDPref = ""
                }

                override fun onFailure(code: String?, message: String?) {
                    super.onFailure(code, message)
                    mResultCallBack?.onFailure(code, message)
                }
            })
        }
    }

    override fun requestAuthenticatedUserInfo(owner: LifecycleOwner): LiveData<User> {
        return userService.getAuthenticatedUserInfo(true).apply {
            observe(owner, object : LiveDataCallBack<User>() {
                override fun onSuccess(code: String?, data: User?) {
                    super.onSuccess(code, data)
                    data?.let {
                        //`User`本地数据克隆到 `ModelUIModel` 内存数据
                        UserConversion.cloneDataFromUser(
                            application,
                            it,
                            appGlobalModel.userObservable
                        )

                        //保存 `UserInfoBean` 用户数据
                        putUserInfoPref(data)
                        mResultCallBack?.onSuccess(code, true)
                    }
                }

                override fun onFailure(code: String?, message: String?) {
                    super.onFailure(code, message)
                    clearTokenStorage()
                    mResultCallBack?.onFailure(code, message)
                }
            })
        }
    }

    override fun signOut() {
        clearTokenStorage()
        clearCookies()
    }

    override fun clearTokenStorage() {
        userInfoPref = ""
        authInfoPref = ""

        userBasicCodePref = ""
        accessTokenPref = ""
        authIDPref = ""
    }

    override fun clearCookies() {
        val cookieManager: CookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeAllCookies(null)
        }
        WebStorage.getInstance().deleteAllData()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().flush()
        }
    }
}