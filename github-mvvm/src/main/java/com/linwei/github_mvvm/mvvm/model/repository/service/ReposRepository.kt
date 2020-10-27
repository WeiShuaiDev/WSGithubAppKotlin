package com.linwei.github_mvvm.mvvm.model.repository.service

import android.app.Application
import android.os.Build
import android.util.Base64
import android.webkit.CookieManager
import android.webkit.WebStorage
import androidx.lifecycle.*
import com.linwei.cams.ext.*
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams.http.config.ApiStateConstant
import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.accessTokenPref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.authIDPref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.authInfoPref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.putAuthInfoPref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.putUserInfoPref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.userBasicCodePref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.userInfoPref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.userNamePref
import com.linwei.github_mvvm.mvvm.model.AppGlobalModel
import com.linwei.github_mvvm.mvvm.model.api.Api
import com.linwei.github_mvvm.mvvm.model.api.service.AuthService
import com.linwei.github_mvvm.mvvm.model.api.service.ReposService
import com.linwei.github_mvvm.mvvm.model.api.service.UserService
import com.linwei.github_mvvm.mvvm.model.bean.*
import com.linwei.github_mvvm.mvvm.model.conversion.UserConversion
import com.linwei.github_mvvm.mvvm.model.repository.db.LocalDatabase
import com.linwei.github_mvvm.mvvm.model.repository.db.dao.ReposDao
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.ReceivedEventEntity
import com.linwei.github_mvvm.utils.GsonUtils
import timber.log.Timber
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/10/27
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
open class ReposRepository @Inject constructor(
        private val application: Application,
        private val appGlobalModel: AppGlobalModel,
        dataRepository: DataMvvmRepository
) : BaseModel(dataRepository) {

    /**
     *  仓库服务接口
     */
    private val reposService: ReposService by lazy {
        dataRepository.obtainRetrofitService(ReposService::class.java)
    }

    /**
     * 仓库服务接口
     */
    private val reposDao: ReposDao by lazy {
        dataRepository.obtainRoomDataBase(LocalDatabase::class.java).reposDao()
    }

    fun requestTrendData(
            owner: LifecycleOwner,
            languageType: String,
            since: String,
            observer: LiveDataCallBack<List<TrendingRepoModel>>
    ): LiveData<List<TrendingRepoModel>> {

        return reposService.getTrendDataAPI(true, Api.API_TOKEN, since, languageType).apply {
            observe(
                    owner,
                    object : LiveDataCallBack<List<TrendingRepoModel>>() {
                        override fun onSuccess(code: String?, data: List<TrendingRepoModel>?) {
                            super.onSuccess(code, data)
                            data?.let {
                                val entity = ReceivedEventEntity(
                                        id = 0,
                                        data = GsonUtils.toJsonString(it)
                                )
                                //userDao.insertReceivedEvent(entity)
                            }
                            observer.onSuccess(code, data)
                        }

                        override fun onFailure(code: String?, message: String?) {
                            super.onFailure(code, message)
                            observer.onFailure(code, message)
                        }
                    })
        }
    }


    fun requestUserRepository100StatusDao(
            owner: LifecycleOwner,
            page: Int,
            sort: String,
            per_page: Int,
            observer: LiveDataCallBack<Page<List<Repository>>>
    ): LiveData<Page<List<Repository>>> {
        val name: String? = appGlobalModel.userObservable.login
        name.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }

        return reposService.getUserRepository100StatusDao(true, name!!, page, sort, per_page).apply {
            observe(
                    owner,
                    object : LiveDataCallBack<Page<List<Repository>>>() {
                        override fun onSuccess(code: String?, data: Page<List<Repository>>?) {
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

    private fun requestGetStarredRepos(
            owner: LifecycleOwner,
            page: Int,
            sort: String,
            per_page: Int,
            observer: LiveDataCallBack<Page<List<Repository>>>
    ): LiveData<Page<List<Repository>>> {
        val name: String? = appGlobalModel.userObservable.login
        name.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }

        return reposService.getStarredRepos(true, name!!, page, sort, per_page).apply {
            observe(
                    owner,
                    object : LiveDataCallBack<Page<List<Repository>>>() {
                        override fun onSuccess(code: String?, data: Page<List<Repository>>?) {
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
}