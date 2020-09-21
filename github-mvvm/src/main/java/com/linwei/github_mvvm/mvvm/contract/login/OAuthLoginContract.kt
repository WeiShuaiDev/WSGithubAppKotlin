package com.linwei.github_mvvm.mvvm.contract.login

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linwei.github_mvvm.mvvm.model.bean.AccessToken

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
        fun requestOAuthLogin(
            owner: LifecycleOwner,
            code: String,
            liveData: MutableLiveData<Boolean>
        )

        /**
         * 请求创建该账号标识码 `Code`
         * @param owner [LifecycleOwner]
         * @return LiveData [AuthResponseBean]
         */
        fun requestCreateCodeAuthorization(
            owner: LifecycleOwner,
            code: String): LiveData<AccessToken>
    }

}