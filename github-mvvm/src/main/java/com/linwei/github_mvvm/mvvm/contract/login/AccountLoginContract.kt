package com.linwei.github_mvvm.mvvm.contract.login

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.github_mvvm.mvvm.model.bean.AuthResponse
import com.linwei.github_mvvm.mvvm.model.bean.User

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

    interface View

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
        fun obtainAccountLogin(
                owner: LifecycleOwner,
                username: String,
                password: String,
                observer: LiveDataCallBack<User>
        )
    }
}