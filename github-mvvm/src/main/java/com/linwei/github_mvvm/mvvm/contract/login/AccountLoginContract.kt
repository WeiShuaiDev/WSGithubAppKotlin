package com.linwei.github_mvvm.mvvm.contract.login

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
         * 账号登录网络请求
         * @param username [String] 用户名
         * @param password [String] 密码
         */
        fun requestAccountLogin(username: String, password: String)

        /**
         * @return [Observable]
         */
        fun requestTokenObservable(): Observable<String>

        /**
         * @return [Observable]
         */
        fun requestCodeTokenObservable(code: String): Observable<String>

    }

}