package com.linwei.github_mvvm.mvvm.model.repository.repos

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.mvvm.contract.repos.ReposDetailContract
import com.linwei.github_mvvm.mvvm.model.bean.Repository
import com.linwei.github_mvvm.mvvm.model.repository.service.ReposRepository
import okhttp3.ResponseBody
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
class ReposDetailModel @Inject constructor(
    dataRepository: DataMvvmRepository,
    private val reposRepository: ReposRepository
) : BaseModel(dataRepository), ReposDetailContract.Model {

    override fun obtainCheckRepoStarred(
        owner: LifecycleOwner,
        reposName: String,
        liveData: MutableLiveData<Boolean>
    ) {
        reposRepository.requestCheckRepoStarred(
            owner,
            reposName,
            object : LiveDataCallBack<ResponseBody>() {
                override fun onSuccess(code: String?, data: ResponseBody?) {
                    super.onSuccess(code, data)
                    liveData.value = true
                }

                override fun onFailure(code: String?, message: String?) {
                    super.onFailure(code, message)
                    liveData.value=false
                }
            })
    }

    override fun obtainCheckRepoWatched(
        owner: LifecycleOwner,
        reposName: String,
        liveData: MutableLiveData<Boolean>
    ) {
        reposRepository.requestCheckRepoWatched(
            owner,
            reposName,
            object : LiveDataCallBack<ResponseBody>() {
                override fun onSuccess(code: String?, data: ResponseBody?) {
                    super.onSuccess(code, data)
                    liveData.value=true
                }

                override fun onFailure(code: String?, message: String?) {
                    super.onFailure(code, message)
                    liveData.value=false
                }
            })
    }

    override fun obtainChangeStarStatus(
        owner: LifecycleOwner,
        reposName: String,
        liveData: MutableLiveData<Boolean>
    ) {
        reposRepository.requestChangeStarStatus(owner, reposName, liveData)
    }

    override fun obtainChangeWatchStatus(
        owner: LifecycleOwner,
        reposName: String,
        liveData: MutableLiveData<Boolean>
    ) {
        reposRepository.requestChangeWatchStatus(owner, reposName, liveData)
    }

    override fun obtainForkRepository(
        owner: LifecycleOwner,
        reposName: String,
        observer: LiveDataCallBack<Repository>?
    ) {
        reposRepository.requestForkRepository(
            owner,
            reposName,
            object : LiveDataCallBack<Repository>() {
                override fun onSuccess(code: String?, data: Repository?) {
                    super.onSuccess(code, data)
                    observer?.onSuccess(code, data)
                }

                override fun onFailure(code: String?, message: String?) {
                    super.onFailure(code, message)
                    observer?.onFailure(code, message)
                }
            })
    }
}