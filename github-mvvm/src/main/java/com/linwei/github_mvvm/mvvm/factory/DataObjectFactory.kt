package com.linwei.github_mvvm.mvvm.factory

import com.linwei.cams.ext.fromBean
import com.linwei.cams.ext.isEmptyParameter
import com.linwei.cams.ext.pref
import com.linwei.cams.ext.toBean
import com.linwei.github_mvvm.mvvm.model.bean.AuthResponse
import com.linwei.github_mvvm.mvvm.model.bean.User

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/9/11
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
object UserInfoStorage {
    /**
     * 用户名
     */
    var userNamePref: String by pref("")

    /**
     * 密码
     */
    var passwordPref: String by pref("")

    /**
     * 用户密文信息
     */
    var userBasicCodePref: String by pref("")

    /**
     * 访问令牌 `Token`
     */
    var accessTokenPref: String by pref("")

    /**
     * 访问令牌 `Id`
     */
    var authIDPref: String by pref("")

    /**
     * 用户信息
     */
    var userInfoPref: String by pref("")

    /**
     * 认证信息
     */
    var authInfoPref: String by pref("")

    /**
     * 保存用户信息
     * @param userInfoBean [UserInfoBean] 用户实体类
     */
    fun putUserInfoPref(userInfoBean: User?) {
        userInfoBean?.let {
            val fromBean: String? = it.fromBean()
            if (!isEmptyParameter(fromBean)) {
                userInfoPref = fromBean ?: ""
            }
        }
    }

    /**
     * 保存认证信息
     * @param authResponseBean [AuthResponseBean] 认证实体类
     */
    fun putAuthInfoPref(authResponseBean: AuthResponse?) {
        authResponseBean?.let {
            val fromBean: String? = it.fromBean()
            if (!isEmptyParameter(fromBean)) {
                authInfoPref = fromBean ?: ""
            }
        }
    }

    /**
     * 获取用户信息
     * @return userInfoBean [UserInfoBean] 用户实体类
     */
    fun getUserInfoPref(): User? {
        if (isEmptyParameter(userInfoPref)) return null
        val toBean: Any? = userInfoPref.toBean()
        toBean?.let {
            return it as User
        }
        return null
    }

    /**
     * 获取认证信息
     * @return authResponseBean [AuthResponseBean] 认证实体类
     */
    fun getAuthInfoPref(): AuthResponse? {
        if (isEmptyParameter(authInfoPref)) return null
        val toBean: Any? = authInfoPref.toBean()
        toBean?.let {
            return it as AuthResponse
        }
        return null
    }

    /**
     * 判断当前用户状态
     */
    val isLoginState: Boolean
        get() {
            return !isEmptyParameter(accessTokenPref)
        }

}


