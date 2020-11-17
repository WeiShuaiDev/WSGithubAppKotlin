package com.linwei.github_mvvm.mvvm.model.repository.service

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.linwei.cams.ext.isNotNullOrEmpty
import com.linwei.cams.ext.no
import com.linwei.cams.ext.string
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams.http.config.ApiStateConstant
import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.model.AppGlobalModel
import com.linwei.github_mvvm.mvvm.model.api.Api
import com.linwei.github_mvvm.mvvm.model.api.service.IssueService
import com.linwei.github_mvvm.mvvm.model.bean.*
import com.linwei.github_mvvm.mvvm.model.repository.db.LocalDatabase
import com.linwei.github_mvvm.mvvm.model.repository.db.dao.IssueDao
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.IssueCommentEntity
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.IssueDetailEntity
import com.linwei.github_mvvm.utils.GsonUtils
import okhttp3.ResponseBody
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/10/28
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
open class IssueRepository @Inject constructor(
    private val appGlobalModel: AppGlobalModel,
    dataRepository: DataMvvmRepository
) : BaseModel(dataRepository) {

    /**
     *  问题服务接口
     */
    private val issueService: IssueService by lazy {
        dataRepository.obtainRetrofitService(IssueService::class.java)
    }

    /**
     * 问题服务接口
     */
    private val issueDao: IssueDao by lazy {
        dataRepository.obtainRoomDataBase(LocalDatabase::class.java).issueDao()
    }

    /**
     * 请求网络获取Issue信息
     * @param owner [LifecycleOwner]
     * @param reposName [String] 仓库名
     * @param number [Int]
     * @return  [LiveDataCallBack]
     */
    fun requestIssueInfo(
        owner: LifecycleOwner,
        reposName: String,
        number: Int, observer: LiveDataCallBack<Issue>
    ): LiveData<Issue> {

        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }
        return issueService.getIssueInfo(
            forceNetWork = true,
            owner = userName!!,
            repo = reposName,
            issueNumber = number
        ).apply {
            observe(
                owner,
                object : LiveDataCallBack<Issue>() {
                    override fun onSuccess(code: String?, data: Issue?) {
                        super.onSuccess(code, data)
                        data?.let {
                            val entity = IssueDetailEntity(
                                number = number.string(),
                                fullName = "$userName/$reposName",
                                data = GsonUtils.toJsonString(it)
                            )
                            //issueDao.insertIssueDetail(entity)
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
     * 查询数据库获取Issue信息
     * @param owner [LifecycleOwner]
     * @param reposName [String] 仓库名
     * @param number [Int]
     * @return  [LiveDataCallBack]
     */
    fun queryIssueInfo(
        owner: LifecycleOwner,
        reposName: String,
        number: Int, observer: LiveDataCallBack<Issue>
    ): LiveData<IssueDetailEntity> {

        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }

        return issueDao.queryIssueDetail(
            fullName = "$userName/$reposName",
            number = number.string()
        ).apply {
            observe(owner,
                object :
                    LiveDataCallBack<IssueDetailEntity>() {
                    override fun onSuccess(code: String?, data: IssueDetailEntity?) {
                        super.onSuccess(code, data)
                        data?.let {
                            val issue: Issue =
                                GsonUtils.parserJsonToBean(it.data, Issue::class.java)
                            observer.onSuccess(code, issue)
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
     * 请求网络获取Issue评论信息
     * @param owner [LifecycleOwner]
     * @param reposName [String] 仓库名
     * @param number [Int]
     * @param page [Int]
     * @param per_page [Int]
     * @return  [LiveDataCallBack]
     */
    fun requestIssueComments(
        owner: LifecycleOwner,
        reposName: String,
        number: Int,
        page: Int,
        per_page: Int = Api.PAGE_SIZE,
        observer: LiveDataCallBack<Page<List<IssueEvent>>>
    ): LiveData<Page<List<IssueEvent>>> {

        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }
        return issueService.getIssueComments(
            forceNetWork = true, owner = userName!!, repo = reposName,
            issueNumber = number, page = page, per_page = per_page
        )
            .apply {
                observe(
                    owner,
                    object : LiveDataCallBack<Page<List<IssueEvent>>>() {
                        override fun onSuccess(code: String?, data: Page<List<IssueEvent>>?) {
                            super.onSuccess(code, data)
                            data?.let {
                                if (page == 1) {
                                    val entity = IssueCommentEntity(
                                        number = number.string(),
                                        fullName = "$userName/$reposName",
                                        data = GsonUtils.toJsonString(it),
                                        commentId = "-1"
                                    )
                                    //issueDao.insertIssueComment(entity)
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
     * 查询数据库获取Issue评论信息
     * @param owner [LifecycleOwner]
     * @param reposName [String] 仓库名
     * @param number [Int]
     * @return  [LiveDataCallBack]
     */
    fun queryIssueComments(
        owner: LifecycleOwner,
        reposName: String,
        number: Int, observer: LiveDataCallBack<Page<List<IssueEvent>>>
    ): LiveData<IssueCommentEntity> {

        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }

        return issueDao.queryIssueComment(
            fullName = "$userName/$reposName",
            number = number.string()
        ).apply {
            observe(owner,
                object :
                    LiveDataCallBack<IssueCommentEntity>() {
                    override fun onSuccess(code: String?, data: IssueCommentEntity?) {
                        super.onSuccess(code, data)
                        data?.let {
                            val page = Page<List<IssueEvent>>()
                            page.result =
                                GsonUtils.parserJsonToArrayBeans(it.data, IssueEvent::class.java)
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
     * 请求网络提交Issue编辑信息
     * @param owner [LifecycleOwner]
     * @param reposName [String] 仓库名
     * @param number [Int]
     * @param issue [Issue]
     * @return  [LiveDataCallBack]
     */
    fun requestEditIssue(
        owner: LifecycleOwner,
        reposName: String,
        number: Int,
        issue: Issue,
        observer: LiveDataCallBack<Issue>
    ): LiveData<Issue> {

        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }
        return issueService.editIssue(
            owner = userName!!,
            repo = reposName,
            issueNumber = number,
            body = issue
        )
            .apply {
                observe(
                    owner,
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

    /**
     * 请求网络创建Issue信息
     * @param owner [LifecycleOwner]
     * @param userName [String] 用户名
     * @param reposName [String] 仓库名
     * @param issue [Issue]
     * @return  [LiveDataCallBack]
     */
    fun requestCreateIssue(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        issue: Issue,
        observer: LiveDataCallBack<Issue>
    ): LiveData<Issue> {

        return issueService.createIssue(owner = userName, repo = reposName, body = issue)
            .apply {
                observe(
                    owner,
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

    /**
     * 请求网络提交Issue评论信息
     * @param owner [LifecycleOwner]
     * @param reposName [String] 仓库名
     * @param number [Int]
     * @param commentRequestModel [CommentRequestModel]
     * @return  [LiveDataCallBack]
     */
    fun requestCommentIssue(
        owner: LifecycleOwner,
        reposName: String,
        number: Int,
        commentRequestModel: CommentRequestModel,
        observer: LiveDataCallBack<IssueEvent>
    ): LiveData<IssueEvent> {

        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }
        return issueService.addComment(
            owner = userName!!,
            repo = reposName,
            issueNumber = number,
            body = commentRequestModel
        ).apply {
            observe(
                owner,
                object : LiveDataCallBack<IssueEvent>() {
                    override fun onSuccess(code: String?, data: IssueEvent?) {
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
     * 请求网络锁定/解锁 issue
     * @param owner [LifecycleOwner]
     * @param reposName [String] 仓库名
     * @param number [Int]
     * @param lock [Boolean]
     * @return  [LiveDataCallBack]
     */
    fun requestLockIssue(
        owner: LifecycleOwner,
        reposName: String,
        number: Int,
        lock: Boolean,
        observer: LiveDataCallBack<Boolean>
    ): LiveData<ResponseBody> {

        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }

        return if (lock) {
            issueService.lockIssue(owner = userName!!, repo = reposName, issueNumber = number)
        } else {
            issueService.unLockIssue(owner = userName!!, repo = reposName, issueNumber = number)
        }.apply {
            observe(
                owner,
                object : LiveDataCallBack<ResponseBody>() {
                    override fun onSuccess(code: String?, data: ResponseBody?) {
                        super.onSuccess(code, data)
                        observer.onSuccess(code, true)
                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                        observer.onFailure(code, message)
                    }
                })
        }
    }

    /**
     * 请求网络提交Issue编辑评论信息
     * @param owner [LifecycleOwner]
     * @param reposName [String] 仓库名
     * @param commentId [String]
     * @param commentRequestModel [CommentRequestModel]
     * @return  [LiveDataCallBack]
     */
    fun requestEditComment(
        owner: LifecycleOwner,
        reposName: String,
        commentId: String,
        commentRequestModel: CommentRequestModel,
        observer: LiveDataCallBack<IssueEvent>
    ): LiveData<IssueEvent> {

        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }
        return issueService.editComment(
            owner = userName!!,
            repo = reposName,
            commentId = commentId,
            body = commentRequestModel
        )
            .apply {
                observe(
                    owner,
                    object : LiveDataCallBack<IssueEvent>() {
                        override fun onSuccess(code: String?, data: IssueEvent?) {
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
     * 请求网络删除Issue评论信息
     * @param owner [LifecycleOwner]
     * @param reposName [String] 仓库名
     * @param commentId [String]
     * @return  [LiveDataCallBack]
     */
    fun requestDeleteComment(
        owner: LifecycleOwner,
        reposName: String,
        commentId: String,
        observer: LiveDataCallBack<String>
    ): LiveData<ResponseBody> {

        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }
        return issueService.deleteComment(
            owner = userName!!,
            repo = reposName,
            commentId = commentId
        )
            .apply {
                observe(
                    owner,
                    object : LiveDataCallBack<ResponseBody>() {
                        override fun onSuccess(code: String?, data: ResponseBody?) {
                            super.onSuccess(code, data)
                            observer.onSuccess(code, commentId)
                        }

                        override fun onFailure(code: String?, message: String?) {
                            super.onFailure(code, message)
                            if (code == "400") {
                                observer.onSuccess(code, commentId)
                            } else {
                                observer.onFailure(code, message)
                            }
                        }
                    })
            }
    }

}