package com.linwei.github_mvvm.mvvm.contract.repos

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.http.callback.LiveDataCallBack

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/4
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
interface ReposReadmeContract {

    interface View

    interface ViewModel {
        /**
         * 获取当前 [reposName] 仓库的 `Readme`地址
         */
        fun toReposReadme()
    }

    interface Model {

        /**
         * 获取当前 [reposName] 仓库的 `Readme`地址
         * @param owner [LifecycleOwner]
         * @param userName [String]
         * @param reposName [String]
         * @param liveData [MutableLiveData]
         */
        fun obtainReposReadme(
            owner: LifecycleOwner,
            userName: String,
            reposName: String,
            observer: LiveDataCallBack<String>
        )
    }
}