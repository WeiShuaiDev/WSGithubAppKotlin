package com.linwei.github_mvvm.mvvm.model.repository.repos

import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.mvvm.contract.repos.ReposIssueListContract
import com.linwei.github_mvvm.mvvm.model.bean.Issue
import com.linwei.github_mvvm.mvvm.model.bean.Page
import com.linwei.github_mvvm.mvvm.model.bean.SearchResult
import com.linwei.github_mvvm.mvvm.model.repository.service.IssueRepository
import com.linwei.github_mvvm.mvvm.model.repository.service.ReposRepository
import com.linwei.github_mvvm.mvvm.model.repository.service.SearchRepository
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
class ReposIssueListModel @Inject constructor(
    dataRepository: DataMvvmRepository,
    private val reposRepository: ReposRepository,
    private val searchRepository: SearchRepository,
    private val issueRepository: IssueRepository
) : BaseModel(dataRepository), ReposIssueListContract.Model {

    override fun obtainReposIssueList(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        status: String,
        page: Int,
        observer: LiveDataCallBack<Page<List<Issue>>>
    ) {
        reposRepository.requestReposIssueList(
            owner,
            userName,
            reposName,
            status, page,
            object : LiveDataCallBack<Page<List<Issue>>>() {
                override fun onSuccess(code: String?, data: Page<List<Issue>>?) {
                    super.onSuccess(code, data)
                    observer.onSuccess(code, data)
                }

                override fun onFailure(code: String?, message: String?) {
                    super.onFailure(code, message)
                    observer.onFailure(code, message)
                }
            })
    }

    override fun obtainSearchReposIssueList(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        status: String,
        query: String,
        page: Int,
        observer: LiveDataCallBack<Page<SearchResult<Issue>>>
    ) {
        searchRepository.requestSearchIssues(
            owner,
            userName,
            reposName,
            status, query, page,
            object : LiveDataCallBack<Page<SearchResult<Issue>>>() {
                override fun onSuccess(code: String?, data: Page<SearchResult<Issue>>?) {
                    super.onSuccess(code, data)
                    observer.onSuccess(code, data)
                }

                override fun onFailure(code: String?, message: String?) {
                    super.onFailure(code, message)
                    observer.onFailure(code, message)
                }
            })
    }

    override fun obtainCreateIssue(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        issue: Issue,
        observer: LiveDataCallBack<Issue>
    ) {
        issueRepository.requestCreateIssue(
            owner,
            userName,
            reposName,
            issue,
            object : LiveDataCallBack<Issue>() {
                override fun onSuccess(code: String?, data: Issue?) {
                    super.onSuccess(code, data)
                    observer.onSuccess(code, data)
                }

                override fun onFailure(code: String?, message: String?) {
                    super.onFailure(code, message)
                    observer.onFailure(code, message)
                }
            })
    }
}