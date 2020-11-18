package com.linwei.github_mvvm.mvvm.model.repository.service

import android.app.Application
import androidx.lifecycle.*
import com.linwei.cams.ext.*
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams.http.config.ApiStateConstant
import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.ext.compareVersion
import com.linwei.github_mvvm.ext.getVersionName
import com.linwei.github_mvvm.mvvm.model.AppGlobalModel
import com.linwei.github_mvvm.mvvm.model.api.Api
import com.linwei.github_mvvm.mvvm.model.api.service.CommitService
import com.linwei.github_mvvm.mvvm.model.api.service.IssueService
import com.linwei.github_mvvm.mvvm.model.api.service.ReposService
import com.linwei.github_mvvm.mvvm.model.bean.*
import com.linwei.github_mvvm.mvvm.model.repository.db.LocalDatabase
import com.linwei.github_mvvm.mvvm.model.repository.db.dao.ReposDao
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.*
import com.linwei.github_mvvm.utils.GsonUtils
import com.linwei.github_mvvm.utils.HtmlUtils
import okhttp3.ResponseBody
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
     * 仓库服务接口
     */
    private val reposDao: ReposDao by lazy {
        dataRepository.obtainRoomDataBase(LocalDatabase::class.java).reposDao()
    }

    /**
     * 提交服务接口
     */
    private val commitService: CommitService by lazy {
        dataRepository.obtainRetrofitService(CommitService::class.java)
    }

    /**
     *  仓库服务接口
     */
    private val reposService: ReposService by lazy {
        dataRepository.obtainRetrofitService(ReposService::class.java)
    }

    /**
     * 问题服务接口
     */
    private val issueService: IssueService by lazy {
        dataRepository.obtainRetrofitService(IssueService::class.java)
    }

    /**
     * 请求网络获取通过仓库release信息，检查当前App的更新
     * @param owner [LifecycleOwner]
     * @return [LiveDataCallBack]
     */
    fun requestCheckoutUpDate(
        owner: LifecycleOwner,
        observer: LiveDataCallBack<Release>
    ): LiveData<List<Release>> {

        return reposService.getReleasesNotHtml(
            forceNetWork = true,
            owner = "WeiShuaiDev",
            repo = "WSGithubAppKotlin",
            page = 1
        ).apply {
            observe(
                owner,
                object : LiveDataCallBack<List<Release>>() {
                    override fun onSuccess(code: String?, data: List<Release>?) {
                        super.onSuccess(code, data)
                        data?.let {
                            if (it.isNotEmpty()) {
                                val item: Release = it[0]
                                val versionName: String? = item.name
                                versionName?.apply {
                                    val currentName: String = application.getVersionName()
                                    val hadNew: Boolean =
                                        currentName.compareVersion(versionName) != currentName
                                    if (hadNew) {
                                        observer.onSuccess(code, item)
                                    }
                                }
                            }
                        }
                        observer.onSuccess(code, Release())
                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                        observer.onFailure(code, message)
                    }
                })
        }
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
                            //reposDao.insertTrend(entity)
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
     * @param reposName [String]
     * @return [LiveDataCallBack]
     */
    fun requestReposReadme(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        observer: LiveDataCallBack<String>
    ): LiveData<ResponseBody> {

        return reposService.getReadmeHtml(
            forceNetWork = true,
            owner = userName, repo = reposName
        ).apply {
            observe(
                owner,
                object : LiveDataCallBack<ResponseBody>() {
                    override fun onSuccess(code: String?, data: ResponseBody?) {
                        super.onSuccess(code, data)
                        data?.let {
                            val readme: String = it.string()

                            val entity = RepositoryDetailReadmeEntity(
                                data = HtmlUtils.generateHtml(application, readme),
                                fullName = "$userName/$reposName",
                                branch = "master"
                            )
                            //reposDao.insertRepositoryDetailReadme(entity)
                            observer.onSuccess(code, readme)
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
            observer.onFailure(
                ApiStateConstant.REQUEST_FAILURE,
                R.string.unknown_error.string()
            )
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
            observer.onFailure(
                ApiStateConstant.REQUEST_FAILURE,
                R.string.unknown_error.string()
            )
            return@no
        }

        return reposService.getRepoFilesDetail(
            owner = userName!!,
            repo = reposName,
            path = path
        )
            .apply {
                observe(
                    owner,
                    object : LiveDataCallBack<String>() {
                        override fun onSuccess(code: String?, data: String?) {
                            super.onSuccess(code, data)
                            data?.let {
                                observer.onSuccess(
                                    code,
                                    HtmlUtils.resolveHtmlFile(application, it)
                                )
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
     * 请求网络获取仓库详情
     * @param owner [LifecycleOwner]
     * @param userName [String]
     * @param reposName [String]
     * @return [LiveDataCallBack]
     */
    fun requestRepoInfo(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        observer: LiveDataCallBack<Repository>
    ): LiveData<Repository> {

        return reposService.getRepoInfo(
            forceNetWork = true,
            owner = userName,
            repo = reposName
        )
            .apply {
                observe(
                    owner,
                    object : LiveDataCallBack<Repository>() {
                        override fun onSuccess(code: String?, data: Repository?) {
                            super.onSuccess(code, data)
                            data?.let {
                                val entity = RepositoryDetailEntity(
                                    data = GsonUtils.toJsonString(it),
                                    fullName = "$userName/$reposName",
                                    branch = "master"
                                )
                                //reposDao.insertRepositoryDetail(entity)
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
     * 查询数据库获取仓库详情
     * @param owner [LifecycleOwner]
     * @param userName [String]
     * @param reposName [String]
     * @return [LiveDataCallBack]
     */
    fun queryRepoInfo(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        observer: LiveDataCallBack<Repository>
    ): LiveData<RepositoryDetailEntity> {

        return reposDao.queryRepositoryDetail(
            fullName = "$userName/$reposName", branch = "master"
        ).apply {
            observe(owner,
                object :
                    LiveDataCallBack<RepositoryDetailEntity>() {
                    override fun onSuccess(code: String?, data: RepositoryDetailEntity?) {
                        super.onSuccess(code, data)
                        data?.let {
                            observer.onSuccess(
                                code, GsonUtils.parserJsonToBean(
                                    it.data,
                                    Repository::class.java
                                )
                            )
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
     * 请求网络获取仓库活跃事件
     * @param owner [LifecycleOwner]
     * @param userName [String]
     * @param reposName [String]
     * @param page [Int]
     * @return [LiveDataCallBack]
     */
    fun requestRepoEvent(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        page: Int,
        observer: LiveDataCallBack<Page<List<Event>>>
    ): LiveData<Page<List<Event>>> {

        return reposService.getRepoEvent(
            forceNetWork = true,
            owner = userName,
            repo = reposName,
            page = page
        )
            .apply {
                observe(
                    owner,
                    object : LiveDataCallBack<Page<List<Event>>>() {
                        override fun onSuccess(code: String?, data: Page<List<Event>>?) {
                            super.onSuccess(code, data)
                            data?.let {
                                if (page == 1) {
                                    val entity = RepositoryEventEntity(
                                        fullName = "$userName/$reposName",
                                        data = GsonUtils.toJsonString(it)
                                    )
                                    //reposDao.insertRepositoryEvent(entity)
                                }
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
     * 查询数据库获取仓库活跃事件
     * @param owner [LifecycleOwner]
     * @param userName [String]
     * @param reposName [String]
     * @return [LiveDataCallBack]
     */
    fun queryRepoEvent(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        observer: LiveDataCallBack<Page<List<Event>>>
    ): LiveData<RepositoryEventEntity> {

        return reposDao.queryRepositoryEvent(
            fullName = "$userName/$reposName"
        ).apply {
            observe(owner,
                object :
                    LiveDataCallBack<RepositoryEventEntity>() {
                    override fun onSuccess(code: String?, data: RepositoryEventEntity?) {
                        super.onSuccess(code, data)
                        data?.let {
                            val page = Page<List<Event>>()
                            page.result =
                                GsonUtils.parserJsonToArrayBeans(it.data, Event::class.java)
                            observer.onSuccess(code, page)
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
     * 请求网络保存仓库提交数据
     * @param owner [LifecycleOwner]
     * @param userName [String]
     * @param reposName [String]
     * @param page [Int]
     * @return [LiveDataCallBack]
     */
    fun requestRepoCommits(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        page: Int,
        observer: LiveDataCallBack<Page<List<RepoCommit>>>
    ): LiveData<Page<List<RepoCommit>>> {

        return commitService.getRepoCommits(
            forceNetWork = true,
            owner = userName,
            repo = reposName,
            page = page
        )
            .apply {
                observe(
                    owner,
                    object : LiveDataCallBack<Page<List<RepoCommit>>>() {
                        override fun onSuccess(code: String?, data: Page<List<RepoCommit>>?) {
                            super.onSuccess(code, data)
                            data?.let {
                                if (page == 1) {
                                    val entity = RepositoryCommitsEntity(
                                        fullName = "$userName/$reposName",
                                        data = GsonUtils.toJsonString(it)
                                    )
                                    //reposDao.insertRepositoryCommits(entity)
                                }
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
     * 查询数据库获取保存仓库提交数据
     * @param owner [LifecycleOwner]
     * @param reposName [String]
     * @return [LiveDataCallBack]
     */
    fun queryRepoCommits(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        observer: LiveDataCallBack<Page<List<RepoCommit>>>
    ): LiveData<RepositoryCommitsEntity> {

        return reposDao.queryRepositoryCommits(
            fullName = "$userName/$reposName"
        ).apply {
            observe(owner,
                object :
                    LiveDataCallBack<RepositoryCommitsEntity>() {
                    override fun onSuccess(code: String?, data: RepositoryCommitsEntity?) {
                        super.onSuccess(code, data)
                        data?.let {
                            val page = Page<List<RepoCommit>>()
                            page.result =
                                GsonUtils.parserJsonToArrayBeans(
                                    it.data,
                                    RepoCommit::class.java
                                )
                            observer.onSuccess(code, page)
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
     * 请求网络仓库文件数据
     * @param owner [LifecycleOwner]
     * @param userName [String]
     * @param reposName [String]
     * @param path [String]
     * @return [LiveDataCallBack]
     */
    fun requestFiles(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        path: String,
        observer: LiveDataCallBack<List<FileModel>>
    ): LiveData<List<FileModel>> {

        return reposService.getRepoFiles(
            owner = userName,
            repo = reposName,
            path = path
        )
            .apply {
                observe(
                    owner,
                    object : LiveDataCallBack<List<FileModel>>() {
                        override fun onSuccess(code: String?, data: List<FileModel>?) {
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
     * 请求网络检查仓库是否Star
     * @param owner [LifecycleOwner]
     * @param userName [String]
     * @param reposName [String]
     * @return [LiveDataCallBack]
     */
    fun requestCheckRepoStarred(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        observer: LiveDataCallBack<ResponseBody>
    ): LiveData<ResponseBody> {

        return reposService.checkRepoStarred(
            owner = userName,
            repo = reposName
        ).apply {
            observe(
                owner,
                object : LiveDataCallBack<ResponseBody>() {
                    override fun onSuccess(code: String?, data: ResponseBody?) {
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
     * 请求网络检查仓库是否Watched
     * @param owner [LifecycleOwner]
     * @param userName [String]
     * @param reposName [String]
     * @return [LiveDataCallBack]
     */
    fun requestCheckRepoWatched(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        observer: LiveDataCallBack<ResponseBody>
    ): LiveData<ResponseBody> {

        return reposService.checkRepoWatched(
            owner = userName,
            repo = reposName
        ).apply {
            observe(
                owner,
                object : LiveDataCallBack<ResponseBody>() {
                    override fun onSuccess(code: String?, data: ResponseBody?) {
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
     * 请求网络改变当前用户对仓库的Star状态
     * @param owner [LifecycleOwner]
     * @param userName [String]
     * @param reposName [String]
     * @param status [MutableLiveData]
     * @return [LiveDataCallBack]
     */
    fun requestChangeStarStatus(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        status: MutableLiveData<Boolean>
    ): LiveData<ResponseBody> {

        val starred: Boolean? = status.value
        return if (starred == true) {
            reposService.unstarRepo(
                owner = userName,
                repo = reposName
            )
        } else {
            reposService.starRepo(
                owner = userName,
                repo = reposName
            )
        }.apply {
            observe(
                owner,
                object : LiveDataCallBack<ResponseBody>() {
                    override fun onSuccess(code: String?, data: ResponseBody?) {
                        super.onSuccess(code, data)
                        status.value = status.value?.not()
                    }
                })
        }
    }

    /**
     * 请求网络改变当前用户对仓库的订阅状态
     * @param owner [LifecycleOwner]
     * @param userName [String]
     * @param reposName [String]
     * @param watch [MutableLiveData]
     * @return [LiveDataCallBack]
     */
    fun requestChangeWatchStatus(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        watch: MutableLiveData<Boolean>
    ): LiveData<ResponseBody> {

        val watched: Boolean? = watch.value
        return if (watched == true) {
            reposService.unwatchRepo(
                owner = userName,
                repo = reposName
            )
        } else {
            reposService.watchRepo(
                owner = userName,
                repo = reposName
            )
        }.apply {
            observe(
                owner,
                object : LiveDataCallBack<ResponseBody>() {
                    override fun onSuccess(code: String?, data: ResponseBody?) {
                        super.onSuccess(code, data)
                        watch.value = watch.value?.not()
                    }
                })
        }
    }

    /**
     * 请求网络Fork当前仓库
     * @param owner [LifecycleOwner]
     * @param userName [String]
     * @param reposName [String]
     * @return [LiveDataCallBack]
     */
    fun requestForkRepository(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        observer: LiveDataCallBack<Repository>
    ): LiveData<Repository> {
        return reposService.createFork(
            owner = userName,
            repo = reposName
        ).apply {
            observe(
                owner,
                object : LiveDataCallBack<Repository>() {
                    override fun onSuccess(code: String?, data: Repository?) {
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
     * 请求网络保存仓库Issue列表数据
     * @param owner [LifecycleOwner]
     * @param userName [String]
     * @param reposName [String]
     * @param status [String]
     * @param page [Int]
     * @return [LiveDataCallBack]
     */
    fun requestReposIssueList(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        status: String,
        page: Int,
        observer: LiveDataCallBack<Page<List<Issue>>>
    ): LiveData<Page<List<Issue>>> {

        return issueService.getRepoIssues(
            forceNetWork = true,
            owner = userName,
            repo = reposName,
            page = page,
            state = status
        )
            .apply {
                observe(
                    owner,
                    object : LiveDataCallBack<Page<List<Issue>>>() {
                        override fun onSuccess(code: String?, data: Page<List<Issue>>?) {
                            super.onSuccess(code, data)
                            data?.let {
                                if (page == 1) {
                                    val entity = RepositoryIssueEntity(
                                        fullName = "$userName/$reposName",
                                        state = status,
                                        data = GsonUtils.toJsonString(it)
                                    )
                                    //reposDao.insertRepositoryIssue(entity)
                                }
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
     * 查询数据库获取仓库Issue列表数据
     * @param owner [LifecycleOwner]
     * @param reposName [String]
     * @return [LiveDataCallBack]
     */
    fun queryReposIssueList(
        owner: LifecycleOwner,
        reposName: String,
        status: String,
        observer: LiveDataCallBack<Page<List<Issue>>>
    ): LiveData<RepositoryIssueEntity> {

        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(
                ApiStateConstant.REQUEST_FAILURE,
                R.string.unknown_error.string()
            )
            return@no
        }

        return reposDao.queryRepositoryIssue(
            fullName = "$userName/$reposName",
            state = status
        ).apply {
            observe(owner,
                object :
                    LiveDataCallBack<RepositoryIssueEntity>() {
                    override fun onSuccess(code: String?, data: RepositoryIssueEntity?) {
                        super.onSuccess(code, data)
                        data?.let {
                            val page = Page<List<Issue>>()
                            page.result =
                                GsonUtils.parserJsonToArrayBeans(it.data, Issue::class.java)
                            observer.onSuccess(code, page)
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
     * 请求网络获取仓库分支Fork数据
     * @param owner [LifecycleOwner]
     * @param reposName [String]
     * @param page [Int]
     * @return [LiveDataCallBack]
     */
    fun requestReposFork(
        owner: LifecycleOwner,
        reposName: String,
        page: Int,
        observer: LiveDataCallBack<Page<List<Repository>>>
    ): LiveData<Page<List<Repository>>> {

        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(
                ApiStateConstant.REQUEST_FAILURE,
                R.string.unknown_error.string()
            )
            return@no
        }

        return reposService.getForks(
            forceNetWork = true,
            owner = userName!!,
            repo = reposName,
            page = page
        )
            .apply {
                observe(
                    owner,
                    object : LiveDataCallBack<Page<List<Repository>>>() {
                        override fun onSuccess(code: String?, data: Page<List<Repository>>?) {
                            super.onSuccess(code, data)
                            data?.let {
                                if (page == 1) {
                                    val entity = RepositoryForkEntity(
                                        fullName = "$userName/$reposName",
                                        data = GsonUtils.toJsonString(it)
                                    )
                                    //reposDao.insertRepositoryFork(entity)
                                }
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
     * 查询数据库获取仓库分支Fork数据
     * @param owner [LifecycleOwner]
     * @param reposName [String]
     * @param page [Int]
     * @return [LiveDataCallBack]
     */
    fun queryReposFork(
        owner: LifecycleOwner,
        reposName: String,
        observer: LiveDataCallBack<Page<List<Repository>>>
    ): LiveData<RepositoryForkEntity> {

        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(
                ApiStateConstant.REQUEST_FAILURE,
                R.string.unknown_error.string()
            )
            return@no
        }

        return reposDao.queryRepositoryFork(
            fullName = "$userName/$reposName"
        ).apply {
            observe(owner,
                object :
                    LiveDataCallBack<RepositoryForkEntity>() {
                    override fun onSuccess(code: String?, data: RepositoryForkEntity?) {
                        super.onSuccess(code, data)
                        data?.let {
                            val page = Page<List<Repository>>()
                            page.result =
                                GsonUtils.parserJsonToArrayBeans(
                                    it.data,
                                    Repository::class.java
                                )
                            observer.onSuccess(code, page)
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
     * 请求网络获取用户的仓库数据
     * @param owner [LifecycleOwner]
     * @param page [Int]
     * @param sort [String]
     * @return [LiveDataCallBack]
     */
    fun requestUserPublicRepos(
        owner: LifecycleOwner,
        page: Int,
        sort: String,
        observer: LiveDataCallBack<Page<List<Repository>>>
    ): LiveData<Page<List<Repository>>> {

        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(
                ApiStateConstant.REQUEST_FAILURE,
                R.string.unknown_error.string()
            )
            return@no
        }

        return reposService.getUserPublicRepos(
            forceNetWork = true,
            user = userName!!,
            page = page,
            sort = sort
        )
            .apply {
                observe(
                    owner,
                    object : LiveDataCallBack<Page<List<Repository>>>() {
                        override fun onSuccess(code: String?, data: Page<List<Repository>>?) {
                            super.onSuccess(code, data)
                            data?.let {
                                if (page == 1) {
                                    val entity = UserReposEntity(
                                        userName = "$userName",
                                        sort = sort,
                                        data = GsonUtils.toJsonString(it)
                                    )
                                    //reposDao.insertUserRepos(entity)
                                }
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
     * 查询数据库获取用户的仓库数据
     * @param owner [LifecycleOwner]
     * @param sort [String]
     * @return [LiveDataCallBack]
     */
    fun queryUserPublicRepos(
        owner: LifecycleOwner,
        sort: String,
        observer: LiveDataCallBack<Page<List<Repository>>>
    ): LiveData<UserReposEntity> {

        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(
                ApiStateConstant.REQUEST_FAILURE,
                R.string.unknown_error.string()
            )
            return@no
        }

        return reposDao.queryUserRepos(
            userName = "$userName", sort = sort
        ).apply {
            observe(owner,
                object :
                    LiveDataCallBack<UserReposEntity>() {
                    override fun onSuccess(code: String?, data: UserReposEntity?) {
                        super.onSuccess(code, data)
                        data?.let {
                            val page = Page<List<Repository>>()
                            page.result =
                                GsonUtils.parserJsonToArrayBeans(
                                    it.data,
                                    Repository::class.java
                                )
                            observer.onSuccess(code, page)
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
     * 请求网络获取用户star的仓库数据
     * @param owner [LifecycleOwner]
     * @param page [Int]
     * @return [LiveDataCallBack]
     */
    private fun requestStarredRepos(
        owner: LifecycleOwner,
        page: Int,
        observer: LiveDataCallBack<Page<List<Repository>>>
    ): LiveData<Page<List<Repository>>> {
        val nameName: String? = appGlobalModel.userObservable.login
        nameName.isNotNullOrEmpty().no {
            observer.onFailure(
                ApiStateConstant.REQUEST_FAILURE,
                R.string.unknown_error.string()
            )
            return@no
        }

        return reposService.getStarredRepos(
            forceNetWork = true,
            user = nameName!!,
            page = page
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

    /**
     * 查询数据库获取用户的仓库数据
     * @param owner [LifecycleOwner]
     * @return [LiveDataCallBack]
     */
    fun queryStarredRepos(
        owner: LifecycleOwner,
        observer: LiveDataCallBack<Page<List<Repository>>>
    ): LiveData<UserStaredEntity> {

        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(
                ApiStateConstant.REQUEST_FAILURE,
                R.string.unknown_error.string()
            )
            return@no
        }

        return reposDao.queryUserStared(
            userName = "$userName"
        ).apply {
            observe(owner,
                object :
                    LiveDataCallBack<UserStaredEntity>() {
                    override fun onSuccess(code: String?, data: UserStaredEntity?) {
                        super.onSuccess(code, data)
                        data?.let {
                            val page = Page<List<Repository>>()
                            page.result =
                                GsonUtils.parserJsonToArrayBeans(
                                    it.data,
                                    Repository::class.java
                                )
                            observer.onSuccess(code, page)
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
     * 请求网络获取提交详情
     * @param owner [LifecycleOwner]
     * @param reposName [String]
     * @param sha [String]
     * @return [LiveDataCallBack]
     */
    private fun requestCommitInfo(
        owner: LifecycleOwner,
        reposName: String,
        sha: String,
        observer: LiveDataCallBack<RepoCommitExt>
    ): LiveData<RepoCommitExt> {
        val nameName: String? = appGlobalModel.userObservable.login
        nameName.isNotNullOrEmpty().no {
            observer.onFailure(
                ApiStateConstant.REQUEST_FAILURE,
                R.string.unknown_error.string()
            )
            return@no
        }

        return commitService.getCommitInfo(
            forceNetWork = true,
            owner = nameName!!,
            repo = reposName,
            sha = sha
        ).apply {
            observe(
                owner,
                object : LiveDataCallBack<RepoCommitExt>() {
                    override fun onSuccess(code: String?, data: RepoCommitExt?) {
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
     * @return [LiveDataCallBack]
     */
    fun requestUserRepository100StatusDao(
        owner: LifecycleOwner,
        page: Int,
        observer: LiveDataCallBack<Page<List<Repository>>>
    ): LiveData<Page<List<Repository>>> {
        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(
                ApiStateConstant.REQUEST_FAILURE,
                R.string.unknown_error.string()
            )
            return@no
        }

        return reposService.getUserRepository100StatusDao(
            forceNetWork = true,
            user = userName!!,
            page = page
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