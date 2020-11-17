package com.linwei.github_mvvm.mvvm.contract.repos

import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.github_mvvm.mvvm.model.bean.Issue
import com.linwei.github_mvvm.mvvm.model.bean.Page
import com.linwei.github_mvvm.mvvm.model.bean.SearchResult

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/9
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
interface ReposIssueListContract {

    interface View

    interface ViewModel {

        /**
         * 分页加载,加载更多。
         * @param page:Int
         */
        fun loadData(isLoad: Boolean, page: Int)

        /**
         * 获取当前 [reposName] 仓库的 `Issue` 列表信息。
         * @param status [String]
         * @param page [Int]
         */
        fun toReposIssueList(
            status: String,
            page: Int
        )

        /**
         * 获取当前 [reposName] 仓库的 `Issue` 列表信息。
         * @param status [String]
         * @param query [String]
         * @param page [Int]
         */
        fun toSearchReposIssueList(
            status: String,
            query: String,
            page: Int
        )

        /**
         * 当前 [reposName] 仓库提交 `Issue` 信息。
         * @param issue [Issue]
         */
        fun toCreateIssue(issue: Issue)
    }

    interface Model {

        /**
         * 获取当前 [reposName] 仓库的 `Issue` 列表信息。
         * @param owner [LifecycleOwner]
         * @param userName [String]
         * @param reposName [String]
         * @param status [String]
         * @param page [Int]
         * @param observer [LiveDataCallBack]
         */
        fun obtainReposIssueList(
            owner: LifecycleOwner,
            userName: String,
            reposName: String,
            status: String,
            page: Int,
            observer: LiveDataCallBack<Page<List<Issue>>>
        )

        /**
         * 获取当前 [reposName] 仓库的 `Issue` 列表信息。
         * @param owner [LifecycleOwner]
         * @param userName [String]
         * @param reposName [String]
         * @param status [String]
         * @param query [String]
         * @param page [Int]
         * @param observer [LiveDataCallBack]
         */
        fun obtainSearchReposIssueList(
            owner: LifecycleOwner,
            userName: String,
            reposName: String,
            status: String,
            query: String,
            page: Int,
            observer: LiveDataCallBack<Page<SearchResult<Issue>>>
        )

        /**
         * 当前 [reposName] 仓库提交 `Issue` 信息。
         * @param owner [LifecycleOwner]
         * @param userName [String]
         * @param reposName [String]
         * @param issue [Issue]
         * @param observer [LiveDataCallBack]
         */
        fun obtainCreateIssue(
            owner: LifecycleOwner,
            userName: String,
            reposName: String,
            issue: Issue,
            observer: LiveDataCallBack<Issue>
        )
    }
}