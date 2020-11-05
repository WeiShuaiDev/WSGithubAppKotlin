package com.linwei.github_mvvm.mvvm.model.repository.repos

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.mvvm.contract.repos.ReposActionListContract
import com.linwei.github_mvvm.mvvm.model.bean.Event
import com.linwei.github_mvvm.mvvm.model.bean.Page
import com.linwei.github_mvvm.mvvm.model.bean.RepoCommit
import com.linwei.github_mvvm.mvvm.model.bean.Repository
import com.linwei.github_mvvm.mvvm.model.repository.service.ReposRepository
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/5
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class ReposActionModel @Inject constructor(
    dataRepository: DataMvvmRepository,
    private val reposRepository: ReposRepository
) : BaseModel(dataRepository), ReposActionListContract.Model {

    override fun obtainRepoInfo(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        liveData: MutableLiveData<Repository>
    ) {
        reposRepository.requestRepoInfo(
            owner,
            userName,
            reposName,
            object : LiveDataCallBack<Repository>() {
                override fun onSuccess(code: String?, data: Repository?) {
                    super.onSuccess(code, data)
                    liveData.value = data
                }
            })
    }

    override fun obtainRepoCommits(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        page: Int,
        observer: LiveDataCallBack<Page<List<RepoCommit>>>
    ) {
        reposRepository.requestRepoCommits(
            owner,
            userName,
            reposName,
            page,
            object : LiveDataCallBack<Page<List<RepoCommit>>>() {
                override fun onSuccess(code: String?, data: Page<List<RepoCommit>>?) {
                    super.onSuccess(code, data)
                    observer.onSuccess(code, data)
                }

                override fun onFailure(code: String?, message: String?) {
                    super.onFailure(code, message)
                    observer.onFailure(code, message)
                }
            })
    }

    override fun obtainRepoEvent(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        page: Int,
        observer: LiveDataCallBack<Page<List<Event>>>
    ) {
        reposRepository.requestRepoEvent(
            owner,
            userName,
            reposName,
            page,
            object : LiveDataCallBack<Page<List<Event>>>() {
                override fun onSuccess(code: String?, data: Page<List<Event>>?) {
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