package com.linwei.github_mvvm.mvvm.contract.login

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.github_mvvm.mvvm.model.bean.AuthResponse
import com.linwei.github_mvvm.mvvm.model.bean.User
import com.linwei.github_mvvm.mvvm.model.data.EventUIModel

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/20
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
interface AccountLoginContract {

    interface View {

    }

    interface ViewModel {
        /**
         * 账号登录逻辑处理
         * @param username [String] 用户名
         * @param password [String] 密码
         */
        fun toAccountLogin(username: String?, password: String?)

    }

    interface Model {

        /**
         * 请求账号密码登录网络
         * @param owner [LifecycleOwner]
         * @param username [String] 用户名
         * @param password [String] 密码
         * @param liveData [MutableLiveData]
         */
        fun requestAccountLogin(
            owner: LifecycleOwner,
            username: String,
            password: String,
            observer: LiveDataCallBack<Boolean>
        )

        /**
         * 请求创建该账号认证 `Token` 令牌
         * @param owner [LifecycleOwner]
         * @return LiveData [AuthResponseBean]
         */
        fun requestCreateAuthorization(owner: LifecycleOwner): LiveData<AuthResponse>

        /**
         * 请求删除该账号认证 `Token` 令牌
         * @param owner [LifecycleOwner]
         * @param id [Int]
         * @return  LiveData [Any]
         */
        fun requestDeleteAuthorization(owner: LifecycleOwner, id: Int): LiveData<Any>

        /**
         * 请求用户信息
         * @param owner [LifecycleOwner]
         * @return LiveData [UserInfoBean]
         */
        fun requestAuthenticatedUserInfo(
            owner: LifecycleOwner
        ): LiveData<User>

        /**
         * 退出登录
         */
        fun signOut()

        /**
         * 清除所有的 `Token` 令牌
         */
        fun clearTokenStorage()

        /**
         * 清除 `Cookies`
         */
        fun clearCookies()

    }

}