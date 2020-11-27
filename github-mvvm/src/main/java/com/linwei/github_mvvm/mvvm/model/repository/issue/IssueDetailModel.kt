package com.linwei.github_mvvm.mvvm.model.repository.issue

import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.mvvm.contract.issue.IssueDetailContract
import com.linwei.github_mvvm.mvvm.model.bean.CommentRequestModel
import com.linwei.github_mvvm.mvvm.model.bean.Issue
import com.linwei.github_mvvm.mvvm.model.repository.service.UserRepository
import com.linwei.github_mvvm.mvvm.model.ui.IssueUIModel
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/2
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class IssueDetailModel @Inject constructor(
    dataRepository: DataMvvmRepository,
    private val userRepository: UserRepository
) : BaseModel(dataRepository), IssueDetailContract.Model {
    override fun obtainIssueComments(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        issueNumber: Int,
        page: Int,
        observer: LiveDataCallBack<IssueUIModel>
    ) {
        TODO("Not yet implemented")
    }

    override fun obtainIssueInfo(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        issueNumber: Int,
        observer: LiveDataCallBack<IssueUIModel>
    ) {
        TODO("Not yet implemented")
    }

    override fun obtainEditIssue(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        issue: Issue,
        issueNumber: Int,
        observer: LiveDataCallBack<IssueUIModel>
    ) {
        TODO("Not yet implemented")
    }

    override fun obtainEditComment(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        commentId: String,
        commentRequestModel: CommentRequestModel,
        observer: LiveDataCallBack<IssueUIModel>
    ) {
        TODO("Not yet implemented")
    }

    override fun obtainDeleteComment(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        commentId: String,
        observer: LiveDataCallBack<String>
    ) {
        TODO("Not yet implemented")
    }


}