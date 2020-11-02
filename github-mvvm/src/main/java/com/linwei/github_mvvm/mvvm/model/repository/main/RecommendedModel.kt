package com.linwei.github_mvvm.mvvm.model.repository.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.mvvm.contract.main.RecommendedContract
import com.linwei.github_mvvm.mvvm.model.AppGlobalModel
import com.linwei.github_mvvm.mvvm.model.api.Api
import com.linwei.github_mvvm.mvvm.model.api.service.ReposService
import com.linwei.github_mvvm.mvvm.model.bean.Event
import com.linwei.github_mvvm.mvvm.model.bean.Page
import com.linwei.github_mvvm.mvvm.model.bean.TrendingRepoModel
import com.linwei.github_mvvm.mvvm.model.repository.db.LocalDatabase
import com.linwei.github_mvvm.mvvm.model.repository.db.dao.ReposDao
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.ReceivedEventEntity
import com.linwei.github_mvvm.mvvm.model.repository.service.ReposRepository
import com.linwei.github_mvvm.mvvm.model.repository.service.UserRepository
import com.linwei.github_mvvm.utils.GsonUtils
import timber.log.Timber
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class RecommendedModel @Inject constructor(
        private val reposRepository: ReposRepository,
        dataRepository: DataMvvmRepository
) : BaseModel(dataRepository), RecommendedContract.Model {

    override fun obtainTrend(
            owner: LifecycleOwner,
            languageType: String,
            since: String,
            observer: LiveDataCallBack<List<TrendingRepoModel>>
    ) {
        reposRepository.requestTrend(owner, languageType, since, object : LiveDataCallBack<List<TrendingRepoModel>>() {
            override fun onSuccess(code: String?, data: List<TrendingRepoModel>?) {
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