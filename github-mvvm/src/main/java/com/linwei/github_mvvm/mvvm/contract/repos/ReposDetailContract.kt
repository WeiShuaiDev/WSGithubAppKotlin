package com.linwei.github_mvvm.mvvm.contract.repos

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.github_mvvm.mvvm.model.bean.Repository

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/2
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
interface ReposDetailContract {

    interface View

    interface ViewModel {

        /**
         * 获取[reposName]仓库当前状态。
         * @param userName [String] 用户名
         * @param reposName [String] 仓库名
         */
        fun toReposStatus(userName:String?,reposName: String?)

        /**
         * 修改当前 [reposName] 仓库 `Star` 状态
         * @param userName [String] 用户名
         * @param reposName [String] 仓库名
         */
        fun toChangeStarStatus(userName:String?,reposName: String?)

        /**
         * 修改当前 [reposName] 仓库 `Watch` 状态
         * @param userName [String] 用户名
         * @param reposName [String] 仓库名
         */
        fun toChangeWatchStatus(userName:String?,reposName: String?)

        /**
         * 设置当前 [reposName] 仓库 `Fork`
         * @param userName [String] 用户名
         * @param reposName [String] 仓库名
         */
        fun toForkRepository(userName:String?,reposName: String?)

    }

    interface Model {
        /**
         * 检查当前 [reposName] 仓库 `Star` 状态
         * @param owner [LifecycleOwner]
         * @param reposName [String]
         * @param liveData [MutableLiveData]
         */
        fun obtainCheckRepoStarred(
            owner: LifecycleOwner,
            userName:String,
            reposName: String,
            liveData: MutableLiveData<Boolean>
        )

        /**
         * 检查当前 [reposName] 仓库 `Watch`状态
         * @param owner [LifecycleOwner]
         * @param reposName [String]
         * @param liveData [MutableLiveData]
         */
        fun obtainCheckRepoWatched(
            owner: LifecycleOwner,
            userName:String,
            reposName: String,
            liveData: MutableLiveData<Boolean>
        )

        /**
         * 修改当前 [reposName] 仓库 `Star` 状态
         * @param owner [LifecycleOwner]
         * @param reposName [String]
         * @param liveData [MutableLiveData]
         */
        fun obtainChangeStarStatus(
            owner: LifecycleOwner,
            userName:String,
            reposName: String,
            liveData: MutableLiveData<Boolean>
        )

        /**
         * 修改当前 [reposName] 仓库 `Watch` 状态
         * @param owner [LifecycleOwner]
         * @param reposName [String]
         * @param liveData [MutableLiveData]
         */
        fun obtainChangeWatchStatus(
            owner: LifecycleOwner,
            userName:String,
            reposName: String,
            liveData: MutableLiveData<Boolean>
        )

        /**
         * 设置当前 [reposName] 仓库 `Fork`
         * @param owner [LifecycleOwner]
         * @param reposName [String]
         * @param observer [LiveDataCallBack]
         */
        fun obtainForkRepository(
            owner: LifecycleOwner,
            userName:String,
            reposName: String,
            observer: LiveDataCallBack<Repository>?
        )
    }
}