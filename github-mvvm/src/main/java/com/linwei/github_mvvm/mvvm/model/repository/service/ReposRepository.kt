package com.linwei.github_mvvm.mvvm.model.repository.service

import android.app.Application
import androidx.lifecycle.*
import com.linwei.cams.ext.*
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams.http.config.ApiStateConstant
import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.model.AppGlobalModel
import com.linwei.github_mvvm.mvvm.model.api.Api
import com.linwei.github_mvvm.mvvm.model.api.service.ReposService
import com.linwei.github_mvvm.mvvm.model.bean.*
import com.linwei.github_mvvm.mvvm.model.repository.db.LocalDatabase
import com.linwei.github_mvvm.mvvm.model.repository.db.dao.ReposDao
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.IssueDetailEntity
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.ReceivedEventEntity
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.RepositoryDetailReadmeEntity
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.TrendEntity
import com.linwei.github_mvvm.utils.GsonUtils
import com.linwei.github_mvvm.utils.HtmlUtils
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

    /**
     * 请求网络获取Trend信息
     * @param owner [LifecycleOwner]
     * @param languageType [String] 语言
     * @param since [String] 时间（今天/本周/本月）
     * @return [LiveDataCallBack]
     */
    fun requestTrend(
        owner: LifecycleOwner,
        languageType: String,
        since: String,
        observer: LiveDataCallBack<List<TrendingRepoModel>>
    ): LiveData<List<TrendingRepoModel>> {

        return reposService.getTrendDataAPI(
            forceNetWork = true,
            apiToken = Api.API_TOKEN,
            since = since,
            languageType = languageType
        ).apply {
            observe(
                owner,
                object : LiveDataCallBack<List<TrendingRepoModel>>() {
                    override fun onSuccess(code: String?, data: List<TrendingRepoModel>?) {
                        super.onSuccess(code, data)
                        data?.let {
                            val entity = TrendEntity(
                                data = GsonUtils.toJsonString(it),
                                languageType = languageType,
                                since = since
                            )
                            reposDao.insertTrend(entity)
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

    /**
     * 查询数据库获取Trend信息
     * @param owner [LifecycleOwner]
     * @param languageType [String] 语言
     * @param since [String] 时间（今天/本周/本月）
     * @return  [LiveDataCallBack]
     */
    fun queryTrend(
        owner: LifecycleOwner,
        languageType: String,
        since: String, observer: LiveDataCallBack<List<TrendingRepoModel>>
    ): LiveData<TrendEntity> {

        return reposDao.queryTrend(
            languageType = languageType, since = since
        ).apply {
            observe(owner,
                object :
                    LiveDataCallBack<TrendEntity>() {
                    override fun onSuccess(code: String?, data: TrendEntity?) {
                        super.onSuccess(code, data)
                        data?.let {
                            val list: List<TrendingRepoModel> =
                                GsonUtils.parserJsonToArrayBeans(
                                    it.data,
                                    TrendingRepoModel::class.java
                                )
                            observer.onSuccess(code, list)
                        }

                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                        observer.onFailure(code, message)
                    }
                })
        }
    }

    /**
     * 请求网络获取Readme数据
     * @param owner [LifecycleOwner]
     * @param languageType [String] 语言
     * @param since [String] 时间（今天/本周/本月）
     * @return [LiveDataCallBack]
     */
    fun requestReposReadme(
        owner: LifecycleOwner,
        reposName: String,
        observer: LiveDataCallBack<String>
    ): LiveData<String> {

        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }

        return reposService.getReadmeHtml(
            forceNetWork = true,
            owner = userName!!, repo = reposName
        )
            .apply {
                observe(
                    owner,
                    object : LiveDataCallBack<String>() {
                        override fun onSuccess(code: String?, data: String?) {
                            super.onSuccess(code, data)
                            data?.let {
                                val entity = RepositoryDetailReadmeEntity(
                                    data = HtmlUtils.generateHtml(application, it),
                                    fullName = "$userName/$reposName",
                                    branch = "master"
                                )
                                reposDao.insertRepositoryDetailReadme(entity)
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

    /**
     * 查询数据库获取Readmde信息
     * @param owner [LifecycleOwner]
     * @param reposName [String]
     * @return  [LiveDataCallBack]
     */
    fun queryReposReadme(
        owner: LifecycleOwner,
        reposName: String,
        observer: LiveDataCallBack<String>
    ): LiveData<RepositoryDetailReadmeEntity> {

        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }

        return reposDao.queryRepositoryDetailReadme(
            fullName = "$userName/$reposName", branch = "master"
        ).apply {
            observe(owner,
                object :
                    LiveDataCallBack<RepositoryDetailReadmeEntity>() {
                    override fun onSuccess(code: String?, data: RepositoryDetailReadmeEntity?) {
                        super.onSuccess(code, data)
                        data?.let {
                            observer.onSuccess(code, it.data)
                        }
                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                        observer.onFailure(code, message)
                    }
                })
        }
    }

    /**
     * 请求网络获取文件详情
     * @param owner [LifecycleOwner]
     * @param reposName [String]
     * @param path [String]
     * @return [LiveDataCallBack]
     */
    fun requestRepoFilesDetail(
        owner: LifecycleOwner,
        reposName: String,
        path: String,
        observer: LiveDataCallBack<String>
    ): LiveData<String> {

        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }

        return reposService.getRepoFilesDetail(owner = userName!!, repo = reposName, path = path)
            .apply {
                observe(
                    owner,
                    object : LiveDataCallBack<String>() {
                        override fun onSuccess(code: String?, data: String?) {
                            super.onSuccess(code, data)
                            data?.let {
                                observer.onSuccess(code, HtmlUtils.resolveHtmlFile(application, it))
                            }
                        }

                        override fun onFailure(code: String?, message: String?) {
                            super.onFailure(code, message)
                            observer.onFailure(code, message)
                        }
                    })
            }
    }


    /**
     * 仓库详情
     * @param owner [LifecycleOwner]
     * @param reposName [String]
     * @return [LiveDataCallBack]
     */
    fun requestRepoInfo(
        owner: LifecycleOwner,
        reposName: String,
        observer: LiveDataCallBack<Repository>
    ): LiveData<Repository> {

        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }

        return reposService.getRepoInfo(forceNetWork = true, owner = userName!!, repo = reposName)
            .apply {
                observe(
                    owner,
                    object : LiveDataCallBack<Repository>() {
                        override fun onSuccess(code: String?, data: Repository?) {
                            super.onSuccess(code, data)
                            data?.let {

                            }
                        }

                        override fun onFailure(code: String?, message: String?) {
                            super.onFailure(code, message)
                            observer.onFailure(code, message)
                        }
                    })
            }
    }


    /**
     * @param owner [LifecycleOwner]
     * @param page [Int]
     * @param sort [String]
     * @param per_page [Int]
     * @return [LiveDataCallBack]
     */
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

        return reposService.getUserRepository100StatusDao(
            forceNetWork = true,
            user = name!!,
            page = page,
            sort = sort,
            per_page = per_page
        )
            .apply {
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

    /**
     * @param owner [LifecycleOwner]
     * @param page [Int]
     * @param sort [String]
     * @param per_page [Int]
     * @return [LiveDataCallBack]
     */
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

        return reposService.getStarredRepos(
            forceNetWork = true,
            user = name!!,
            page = page,
            sort = sort,
            per_page = per_page
        ).apply {
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