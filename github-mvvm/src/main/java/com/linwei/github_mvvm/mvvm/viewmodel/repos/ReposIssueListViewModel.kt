package com.linwei.github_mvvm.mvvm.viewmodel.repos

import android.app.Application
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.ext.isEmptyParameter
import com.linwei.cams.ext.string
import com.linwei.cams.ext.yes
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams.http.model.StatusCode
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.contract.repos.ReposIssueListContract
import com.linwei.github_mvvm.mvvm.model.bean.Issue
import com.linwei.github_mvvm.mvvm.model.bean.Page
import com.linwei.github_mvvm.mvvm.model.bean.SearchResult
import com.linwei.github_mvvm.mvvm.model.repository.repos.ReposIssueListModel
import com.linwei.github_mvvm.mvvm.model.ui.FileUIModel
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/2
 * @Contact: linwei9605@gmail.com
 * @Follow: https://github.com/WeiShuaiDevA
 * @Description:
 *-----------------------------------------------------------------------
 */
class ReposIssueListViewModel @Inject constructor(
    val model: ReposIssueListModel,
    application: Application
) : BaseViewModel(model, application), ReposIssueListContract.ViewModel {

    private val _issueData = MutableLiveData<Page<List<Issue>>>()
    val issueData: LiveData<Page<List<Issue>>>
        get() = _issueData

    private val _searchIssueData = MutableLiveData<Page<SearchResult<Issue>>>()
    val searchIssueData: LiveData<Page<SearchResult<Issue>>>
        get() = _searchIssueData

    var userName: String? = ""

    var reposName: String? = ""

    var status: String? = ""

    var query = MutableLiveData<String>()

    override fun loadDataByRefresh() {

    }

    override fun loadDataByLoadMore(page: Int) {

    }

    override fun toReposIssueList(status: String, page: Int) {
        (isEmptyParameter(userName, reposName)).yes {
            postMessage(obj = R.string.unknown_error.string())
            postUpdateStatus(StatusCode.FAILURE)
            return
        }

        mLifecycleOwner?.let {
            model.obtainReposIssueList(
                it,
                userName!!,
                reposName!!,
                status,
                page,
                object : LiveDataCallBack<Page<List<Issue>>>() {
                    override fun onSuccess(code: String?, data: Page<List<Issue>>?) {
                        super.onSuccess(code, data)
                        if (data != null) {
                            postUpdateStatus(StatusCode.SUCCESS)
                        } else {
                            postUpdateStatus(StatusCode.ERROR)
                        }
                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                        postUpdateStatus(StatusCode.FAILURE)
                    }
                })
        }

    }

    override fun toSearchReposIssueList(status: String, query: String, page: Int) {
        (isEmptyParameter(userName, reposName)).yes {
            postMessage(obj = R.string.unknown_error.string())
            postUpdateStatus(StatusCode.FAILURE)
            return
        }

        mLifecycleOwner?.let {
            model.obtainSearchReposIssueList(
                it,
                userName!!,
                reposName!!,
                status,
                query,
                page,
                object : LiveDataCallBack<Page<SearchResult<Issue>>>() {
                    override fun onSuccess(code: String?, data: Page<SearchResult<Issue>>?) {
                        super.onSuccess(code, data)
                        if (data != null) {
                            postUpdateStatus(StatusCode.SUCCESS)
                        } else {
                            postUpdateStatus(StatusCode.ERROR)
                        }
                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                        postUpdateStatus(StatusCode.FAILURE)
                    }
                })
        }
    }
    fun onSearchKeyListener(v: View, keyCode: Int, event: KeyEvent): Boolean {

        return false
    }

    fun onSearchClick(v: View) {

    }
}