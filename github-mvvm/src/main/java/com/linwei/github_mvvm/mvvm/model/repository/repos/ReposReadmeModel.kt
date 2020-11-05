package com.linwei.github_mvvm.mvvm.model.repository.repos

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.mvvm.contract.repos.ReposReadmeContract
import com.linwei.github_mvvm.mvvm.model.bean.TrendingRepoModel
import com.linwei.github_mvvm.mvvm.model.repository.service.ReposRepository
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
class ReposReadmeModel @Inject constructor(
    dataRepository: DataMvvmRepository,
    private val reposRepository: ReposRepository
) : BaseModel(dataRepository), ReposReadmeContract.Model {

    override fun obtainReposReadme(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        observer: LiveDataCallBack<String>
    ) {
        reposRepository.requestReposReadme(
            owner,
            userName,
            reposName,
            object : LiveDataCallBack<String>() {
                override fun onSuccess(code: String?, data: String?) {
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