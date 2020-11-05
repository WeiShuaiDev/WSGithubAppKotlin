package com.linwei.github_mvvm.mvvm.viewmodel.repos

import android.app.Application
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.ext.isEmptyParameter
import com.linwei.cams.ext.string
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.contract.repos.ReposActionListContract
import com.linwei.github_mvvm.mvvm.model.bean.*
import com.linwei.github_mvvm.mvvm.model.repository.repos.ReposActionModel
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/2
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDevA
 * @Description:
 *-----------------------------------------------------------------------
 */
class ReposActionViewModel @Inject constructor(
    val model: ReposActionModel,
    application: Application
) : BaseViewModel(model, application), ReposActionListContract.ViewModel {

    val pageInfo = MutableLiveData<Repository>()

    private val _repoCommitPage = MutableLiveData<Page<List<RepoCommit>>>()
    val repoCommitPage: LiveData<Page<List<RepoCommit>>>
        get() = _repoCommitPage

    private val _eventPage = MutableLiveData<Page<List<Event>>>()
    val eventPage: LiveData<Page<List<Event>>>
        get() = _eventPage

    override fun toRepoInfo(userName: String?, reposName: String?) {
        if (isEmptyParameter(userName, reposName)) {
            postMessage(obj = R.string.unknown_error.string())
            return
        }

        mLifecycleOwner?.let {
            model.obtainRepoInfo(it, userName!!, reposName!!, pageInfo)
        }
    }

    override fun toRepoCommits(userName: String?, reposName: String?, page: Int) {
        if (isEmptyParameter(userName, reposName)) {
            postMessage(obj = R.string.unknown_error.string())
            return
        }

        mLifecycleOwner?.let {
            model.obtainRepoCommits(
                it,
                userName!!,
                reposName!!,
                page,
                object : LiveDataCallBack<Page<List<RepoCommit>>>() {
                    override fun onSuccess(code: String?, data: Page<List<RepoCommit>>?) {
                        super.onSuccess(code, data)
                        _repoCommitPage.value = data
                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                    }
                })
        }
    }

    override fun toRepoEvent(userName: String?, reposName: String?, page: Int) {
        if (isEmptyParameter(userName, reposName)) {
            postMessage(obj = R.string.unknown_error.string())
            return
        }

        mLifecycleOwner?.let {
            model.obtainRepoEvent(
                it,
                userName!!,
                reposName!!,
                page,
                object : LiveDataCallBack<Page<List<Event>>>() {
                    override fun onSuccess(code: String?, data: Page<List<Event>>?) {
                        super.onSuccess(code, data)
                        _eventPage.value = data
                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                    }
                })
        }
    }

    fun onTabIconClick(v: View) {

    }

}