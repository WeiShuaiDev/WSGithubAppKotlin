package com.linwei.github_mvvm.mvvm.contract.login

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.github_mvvm.mvvm.model.bean.AccessToken
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
interface OAuthLoginContract {

    interface View {

    }

    interface ViewModel {
        /**
         *  `OAuth` 登录方式处理
         *  @param code [String]
         */
        fun toOAuthLogin(code: String?)
    }

    interface Model {
        /**
         * `OAuth` 登录网络请求
         * @param owner [LifecycleOwner]
         * @param code [String] 校验码
         * @param liveData [MutableLiveData]
         */
        fun obtainOAuthLogin(
                owner: LifecycleOwner,
                code: String,
                observer: LiveDataCallBack<User>
        )
    }

}