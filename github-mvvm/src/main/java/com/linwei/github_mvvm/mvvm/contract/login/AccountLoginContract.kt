package com.linwei.github_mvvm.mvvm.contract.login

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.linwei.github_mvvm.mvvm.model.bean.AuthResponseBean
import com.linwei.github_mvvm.mvvm.model.bean.UserInfoBean
import io.reactivex.Observable

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
         * 账号密码登录网络请求
         * @param username [String] 用户名
         * @param password [String] 密码
         */
        fun requestAccountLogin(
            owner: LifecycleOwner,
            username: String,
            password: String
        )

        /**
         * 请求创建该账号认证 `Token` 令牌
         * @return LiveData [AuthResponseBean]
         */
        fun requestCreateAuthorization(owner: LifecycleOwner): LiveData<AuthResponseBean>

        /**
         * 请求删除该账号认证 `Token` 令牌
         * @param id [Int]
         * @return  LiveData [Any]
         */
        fun requestDeleteAuthorization(owner: LifecycleOwner, id: Int): LiveData<Any>

        /**
         * 请求用户信息
         * @return LiveData [UserInfoBean]
         */
        fun requestAuthenticatedUserInfo(owner: LifecycleOwner): LiveData<UserInfoBean>

        /**
         * 清除所有的 `Token` 令牌
         */
        fun clearTokenStorage()

    }

}