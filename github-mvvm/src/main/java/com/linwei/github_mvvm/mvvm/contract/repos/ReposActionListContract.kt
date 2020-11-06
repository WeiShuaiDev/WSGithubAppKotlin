package com.linwei.github_mvvm.mvvm.contract.repos

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.github_mvvm.mvvm.model.bean.Event
import com.linwei.github_mvvm.mvvm.model.bean.Page
import com.linwei.github_mvvm.mvvm.model.bean.RepoCommit
import com.linwei.github_mvvm.mvvm.model.bean.Repository

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/5
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
interface ReposActionListContract {

    interface View

    interface ViewModel {

        /**
         * 分页加载，刷新数据
         */
        fun loadDataByRefresh(userName: String?, reposName: String?)

        /**
         * 分页加载,加载更多
         * @param page:Int
         */
        fun loadDataByLoadMore(userName: String?, reposName: String?, page: Int)


        /**
         * 获取仓库 [reposName] 详情信息
         * @param userName [String] 用户名
         * @param reposName [String] 仓库名
         */
        fun toRepoInfo(userName: String?, reposName: String?)

        /**
         * 获取仓库 [reposName] `Commits` 提交信息
         * @param userName [String]
         * @param reposName [String]
         */
        fun toRepoCommits(userName: String?, reposName: String?, page: Int)

        /**
         * 获取仓库 [reposName] `Event` 信息
         * @param userName [String]
         * @param reposName [String]
         */
        fun toRepoEvent(userName: String?, reposName: String?, page: Int)

    }

    interface Model {

        /**
         * 获取仓库 [reposName] 详情信息
         * @param owner [LifecycleOwner]
         * @param userName [String]
         * @param reposName [String]
         * @param observer [MutableList]
         */
        fun obtainRepoInfo(
            owner: LifecycleOwner,
            userName: String,
            reposName: String,
            liveData: MutableLiveData<Repository>
        )

        /**
         * 获取仓库 [reposName] `Commits` 提交信息
         * @param owner [LifecycleOwner]
         * @param userName [String]
         * @param reposName [String]
         * @param page [Int]
         * @param observer [LiveDataCallBack]
         *
         */
        fun obtainRepoCommits(
            owner: LifecycleOwner,
            userName: String,
            reposName: String,
            page: Int,
            observer: LiveDataCallBack<Page<List<RepoCommit>>>
        )

        /**
         * 获取仓库 [reposName] `Event` 信息
         * @param owner [LifecycleOwner]
         * @param userName [String]
         * @param reposName [String]
         * @param page [Int]
         * @param observer [LiveDataCallBack]
         */
        fun obtainRepoEvent(
            owner: LifecycleOwner,
            userName: String,
            reposName: String,
            page: Int,
            observer: LiveDataCallBack<Page<List<Event>>>
        )
    }
}