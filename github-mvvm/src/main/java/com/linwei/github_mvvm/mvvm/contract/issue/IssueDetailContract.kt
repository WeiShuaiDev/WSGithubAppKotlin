package com.linwei.github_mvvm.mvvm.contract.issue

import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.github_mvvm.mvvm.model.bean.*
import com.linwei.github_mvvm.mvvm.model.ui.IssueUIModel

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/18
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
interface IssueDetailContract {

    interface View {

    }

    interface ViewModel {

    }

    interface Model {
        /**
         * 获取仓库 [reposName] 评论`Issue` 信息。
         * @param owner [LifecycleOwner]
         * @param userName [String]
         * @param reposName [String]
         * @param issueNumber [Int]
         * @param content [String]
         * @param observer [LiveDataCallBack]
         */
        fun obtainIssueComments(
            owner: LifecycleOwner,
            userName: String,
            reposName: String,
            issueNumber: Int,
            page: Int,
            observer: LiveDataCallBack<IssueUIModel>
        )

        /**
         * 获取仓库 [reposName] 的 `Issue` 信息
         * @param owner [LifecycleOwner]
         * @param userName [String]
         * @param reposName [String]
         * @param issueNumber [Int]
         * @param observer [LiveDataCallBack]
         */
        fun obtainIssueInfo(
            owner: LifecycleOwner,
            userName: String,
            reposName: String,
            issueNumber: Int,
            observer: LiveDataCallBack<IssueUIModel>
        )

        /**
         * 仓库 [reposName] 编辑 `Issue` 信息
         * @param owner [LifecycleOwner]
         * @param userName [String]
         * @param reposName [String]
         * @param issue [Issue]
         * @param issueNumber [Int]
         * @param observer [LiveDataCallBack]
         */
        fun obtainEditIssue(
            owner: LifecycleOwner,
            userName: String,
            reposName: String,
            issue: Issue,
            issueNumber: Int,
            observer: LiveDataCallBack<IssueUIModel>
        )

        /**
         * 仓库 [reposName] 编辑 `Commits` 信息
         * @param owner [LifecycleOwner]
         * @param userName [String]
         * @param reposName [String]
         * @param commentId [String]
         * @param commentRequestModel [CommentRequestModel]
         * @param observer [LiveDataCallBack]
         */
        fun obtainEditComment(
            owner: LifecycleOwner,
            userName: String,
            reposName: String,
            commentId: String,
            commentRequestModel: CommentRequestModel,
            observer: LiveDataCallBack<IssueUIModel>
        )

        /**
         * 仓库 [reposName] 删除 `Commits` 信息
         * @param owner [LifecycleOwner]
         * @param userName [String]
         * @param reposName [String]
         * @param commentId [String]
         * @param observer [LiveDataCallBack]
         *
         */
        fun obtainDeleteComment(
            owner: LifecycleOwner,
            userName: String,
            reposName: String,
            commentId: String,
            observer: LiveDataCallBack<String>
        )
    }
}