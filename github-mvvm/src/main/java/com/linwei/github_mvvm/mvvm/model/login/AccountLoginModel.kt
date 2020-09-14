package com.linwei.github_mvvm.mvvm.model.login

import android.util.Base64
import androidx.lifecycle.*
import com.linwei.cams.ext.no
import com.linwei.cams.ext.otherwise
import com.linwei.cams.ext.string
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.mvvm.contract.login.AccountLoginContract
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.accessTokenPref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.authIDPref
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

    /**
     * 用户信息
     */
    private val _userInfoBean = MutableLiveData<UserInfoBean>()
    val userInfoBean: LiveData<UserInfoBean>
        get() = _userInfoBean


    override fun requestAccountLogin(
        owner: LifecycleOwner,
        username: String,
        password: String
    ) {
        clearTokenStorage()

        val type = "$username:$password"

        val userCipherTextInfo: String =
            "Basic " + Base64.encodeToString(type.toByteArray(), Base64.NO_WRAP)
                .replace("\\+", "%2B")

        //保存用户名明文、密文信息
        userNamePref = username
        userBasicCodePref = userCipherTextInfo

        requestCreateAuthorization(owner)
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
                                authIDPref = data.id.string()

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
                        _userInfoBean.value = data
                    }
                }
            })
        }
    }

    override fun clearTokenStorage() {
        userNamePref = ""
        userBasicCodePref = ""
    }
}