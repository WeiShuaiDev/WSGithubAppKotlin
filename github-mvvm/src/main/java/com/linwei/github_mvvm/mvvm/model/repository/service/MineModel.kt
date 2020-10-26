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
import com.linwei.github_mvvm.mvvm.contract.main.MineContract
import com.linwei.github_mvvm.mvvm.model.AppGlobalModel
import com.linwei.github_mvvm.mvvm.model.api.service.NotificationService
import com.linwei.github_mvvm.mvvm.model.api.service.UserService
import com.linwei.github_mvvm.mvvm.model.bean.Event
import com.linwei.github_mvvm.mvvm.model.bean.Notification
import com.linwei.github_mvvm.mvvm.model.bean.Page
import com.linwei.github_mvvm.mvvm.model.bean.User
import com.linwei.github_mvvm.mvvm.model.repository.db.LocalDatabase
import com.linwei.github_mvvm.mvvm.model.repository.db.dao.UserDao
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.OrgMemberEntity
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.ReceivedEventEntity
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.UserEventEntity
import com.linwei.github_mvvm.utils.GsonUtils
import timber.log.Timber
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/20
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class MineModel @Inject constructor(
    private val appGlobalModel: AppGlobalModel,
    dataRepository: DataMvvmRepository
) :
    BaseModel(dataRepository), MineContract.Model {

    /**
     *  通知服务接口
     */
    private val notificationService: NotificationService by lazy {
        dataRepository.obtainRetrofitService(NotificationService::class.java)
    }

    /**
     * 用户服务接口
     */
    private val userService: UserService by lazy {
        dataRepository.obtainRetrofitService(UserService::class.java)
    }

    /**
     * 用户数据库接口
     */
    private val userDao: UserDao by lazy {
        dataRepository.obtainRoomDataBase(LocalDatabase::class.java).userDao()
    }

    override fun requestOrgMembers(
        owner: LifecycleOwner,
        page: Int,
        observer: LiveDataCallBack<Page<List<User>>>
    ): LiveData<Page<List<User>>> {
        val org: String? = appGlobalModel.userObservable.login

        return userService.getOrgMembers(true, org ?: "", page).apply {
            observe(
                owner,
                object : LiveDataCallBack<Page<List<User>>>() {
                    override fun onSuccess(code: String?, data: Page<List<User>>?) {
                        super.onSuccess(code, data)
                        data?.let {
                            if (page == 1) {
                                val entity = OrgMemberEntity(
                                    org = org,
                                    data = GsonUtils.toJsonString(it)
                                )
                                //userDao.insertOrgMember(entity)
                            }

                            observer.onSuccess(code, data)

                            Timber.i(" request Http=\"orgs/{org}/members\"  Data Success~")
                        }
                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                        //queryOrgMembers(owner, org?:"", observer)
                        Timber.i(" request Http=\"orgs/{org}/members\" Data Failed~")
                    }
                })
        }
    }


    override fun queryOrgMembers(
        owner: LifecycleOwner,
        org: String,
        observer: LiveDataCallBack<Page<List<User>>>
    ): LiveData<OrgMemberEntity> {
        return userDao.queryOrgMember(org).apply {
            observe(owner,
                object :
                    LiveDataCallBack<OrgMemberEntity>() {
                    override fun onSuccess(code: String?, data: OrgMemberEntity?) {
                        super.onSuccess(code, data)
                        data?.let {
                            val page = Page<List<User>>()
                            page.result =
                                GsonUtils.parserJsonToArrayBeans(it.data, User::class.java)

                            observer.onSuccess(code, page)

                            Timber.i(" request DB=\"orgs/{org}/members\" Data Success~")
                        }
                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                        observer.onFailure(code, message)

                        Timber.i(" request DB=\"orgs/{org}/members\" Data failed~")
                    }
                })
        }
    }

    override fun requestUserEvents(
        owner: LifecycleOwner,
        page: Int,
        observer: LiveDataCallBack<Page<List<Event>>>
    ): LiveData<Page<List<Event>>> {
        val user: String? = appGlobalModel.userObservable.login

        return userService.getUserEvents(true, user ?: "", page).apply {
            observe(
                owner,
                object : LiveDataCallBack<Page<List<Event>>>() {
                    override fun onSuccess(code: String?, data: Page<List<Event>>?) {
                        super.onSuccess(code, data)
                        data?.let {
                            if (page == 1) {
                                val entity = UserEventEntity(
                                    userName = user,
                                    data = GsonUtils.toJsonString(it)
                                )
                                //userDao.insertUserEvent(entity)
                            }

                            observer.onSuccess(code, data)

                            Timber.i(" request Http=\"users/{user}/events\" Data Success~")
                        }
                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                        //queryUserEvents(owner, user?:"", observer)
                        Timber.i(" request Http=\"users/{user}/events\" Data Failed~")
                    }
                })
        }
    }

    override fun queryUserEvents(
        owner: LifecycleOwner,
        name: String,
        observer: LiveDataCallBack<Page<List<Event>>>
    ): LiveData<UserEventEntity> {
        return userDao.queryUserEvent(name).apply {
            observe(owner,
                object :
                    LiveDataCallBack<UserEventEntity>() {
                    override fun onSuccess(code: String?, data: UserEventEntity?) {
                        super.onSuccess(code, data)
                        data?.let {
                            val page = Page<List<Event>>()
                            page.result =
                                GsonUtils.parserJsonToArrayBeans(it.data, Event::class.java)

                            observer.onSuccess(code, page)

                            Timber.i(" request DB=\"users/{user}/events\" Data Success~")
                        }
                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                        observer.onFailure(code, message)

                        Timber.i(" request DB=\"users/{user}/events\" Data failed~")
                    }
                })
        }
    }

    override fun requestNotify(
        owner: LifecycleOwner,
        all: Boolean?,
        participating: Boolean?,
        page: Int,
        observer: LiveDataCallBack<Page<List<Notification>>>
    ): LiveData<Page<List<Notification>>> {

        return if (all == null || participating == null) {
            notificationService.getNotificationUnRead(true, page)
        } else {
            notificationService.getNotification(true, all, participating, page)
        }.apply {
            observe(
                owner,
                object : LiveDataCallBack<Page<List<Notification>>>() {
                    override fun onSuccess(code: String?, data: Page<List<Notification>>?) {
                        super.onSuccess(code, data)
                        data?.let {
                            observer.onSuccess(code, data)
                            Timber.i(" request Http=\"notifications\" Data success~")
                        }
                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                        observer.onFailure(code, message)
                        Timber.i(" request Http=\"notifications\" Data Failed~")
                    }
                })
        }
    }
}