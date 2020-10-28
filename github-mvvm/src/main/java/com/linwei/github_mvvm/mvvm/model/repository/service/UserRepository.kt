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
import com.linwei.github_mvvm.mvvm.model.api.service.AuthService
import com.linwei.github_mvvm.mvvm.model.api.service.NotificationService
import com.linwei.github_mvvm.mvvm.model.api.service.UserService
import com.linwei.github_mvvm.mvvm.model.bean.*
import com.linwei.github_mvvm.mvvm.model.conversion.UserConversion
import com.linwei.github_mvvm.mvvm.model.repository.db.LocalDatabase
import com.linwei.github_mvvm.mvvm.model.repository.db.dao.UserDao
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.OrgMemberEntity
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.ReceivedEventEntity
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.UserEventEntity
import com.linwei.github_mvvm.utils.GsonUtils
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
open class UserRepository @Inject constructor(
        private val application: Application,
        private val appGlobalModel: AppGlobalModel,
        dataRepository: DataMvvmRepository
) : BaseModel(dataRepository) {

    /**
     *  用户权限校验服务接口
     */
    private val authService: AuthService by lazy {
        dataRepository.obtainRetrofitService(AuthService::class.java)
    }

    /**
     *用户信息操作服务接口
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

    /**
     * 通知数据库接口
     */
    private val notificationService: NotificationService by lazy {
        dataRepository.obtainRetrofitService(NotificationService::class.java)
    }

    fun requestAccountLogin(
            owner: LifecycleOwner, username: String,
            password: String, observer: LiveDataCallBack<AuthResponse>) {

        clearTokenStorage()

        val type = "$username:$password"

        val userCipherTextInfo: String =
                Base64.encodeToString(type.toByteArray(), Base64.NO_WRAP)
                        .replace("\\+", "%2B")

        //保存用户名明文、密文信息
        userNamePref = username
        userBasicCodePref = userCipherTextInfo

        requestCreateAuthorization(owner, observer)
    }

    fun requestOAuthLogin(
            owner: LifecycleOwner, code: String, observer: LiveDataCallBack<AccessToken>) {

        clearTokenStorage()

        requestCreateCodeAuthorization(owner, code, observer)
    }

    private fun requestCreateCodeAuthorization(
            owner: LifecycleOwner,
            code: String,
            observer: LiveDataCallBack<AccessToken>
    ): LiveData<AccessToken> {
        return authService.createCodeAuthorization(
                AuthRequest.clientId,
                AuthRequest.clientSecret,
                code
        ).apply {
            observe(
                    owner,
                    object : LiveDataCallBack<AccessToken>() {
                        override fun onSuccess(code: String?, data: AccessToken?) {
                            super.onSuccess(code, data)
                            data?.let {
                                data.accessToken.isNotNullOrEmpty().yes {
                                    accessTokenPref = data.accessToken!!

                                    observer.onSuccess(code, data)
                                }.otherwise {
                                    //删除用户令牌信息
                                    clearTokenStorage()
                                    observer.onFailure(code, R.string.unknown_error.string())
                                }
                            }
                        }

                        override fun onFailure(code: String?, message: String?) {
                            super.onFailure(code, message)
                            observer.onFailure(code, message)
                        }
                    })
        }
    }

    private fun requestCreateAuthorization(owner: LifecycleOwner, observer: LiveDataCallBack<AuthResponse>): LiveData<AuthResponse> {
        return authService.createAuthorization(AuthRequest.generate()).apply {
            observe(
                    owner,
                    object : LiveDataCallBack<AuthResponse>() {
                        override fun onSuccess(code: String?, data: AuthResponse?) {
                            super.onSuccess(code, data)
                            data?.let {
                                data.token.isNotNullOrEmpty().yes {
                                    accessTokenPref = data.token!!
                                    authIDPref = data.id.toString()

                                    //保存 `AuthResponseBean` 认证信息
                                    putAuthInfoPref(data)

                                    observer.onSuccess(code, data)
                                }.otherwise {
                                    //删除用户令牌信息
                                    requestDeleteAuthorization(owner, data.id)

                                    observer.onFailure(code, R.string.unknown_error.string())
                                }
                            }
                        }

                        override fun onFailure(code: String?, message: String?) {
                            super.onFailure(code, message)
                            observer.onFailure(code, message)
                        }
                    })
        }
    }

    private fun requestDeleteAuthorization(owner: LifecycleOwner, id: Int): LiveData<Any> {
        return authService.deleteAuthorization(id).apply {
            observe(owner, object : LiveDataCallBack<Any>() {
                override fun onSuccess(code: String?, data: Any?) {
                    super.onSuccess(code, data)
                    accessTokenPref = ""
                    authIDPref = ""
                }
            })
        }
    }

    fun requestAuthenticatedUserInfo(owner: LifecycleOwner, name: String?, observer: LiveDataCallBack<User>): LiveData<User> {
        return name.isNotNullOrEmpty().yes {
            requestUser(owner, name!!, observer)
        }.otherwise {
            requestPersonInfo(owner, observer)
        }
    }

    private fun requestPersonInfo(owner: LifecycleOwner, observer: LiveDataCallBack<User>): LiveData<User> {
        return userService.getPersonInfo(true).apply {
            observe(owner, object : LiveDataCallBack<User>() {
                override fun onSuccess(code: String?, data: User?) {
                    super.onSuccess(code, data)
                    data?.let {
                        //`User`本地数据克隆到 `ModelUIModel` 内存数据
                        UserConversion.cloneDataFromUser(
                                application,
                                it,
                                appGlobalModel.userObservable
                        )

                        //保存 `UserInfoBean` 用户数据
                        putUserInfoPref(data)
                    }
                    observer.onSuccess(code, data)
                }

                override fun onFailure(code: String?, message: String?) {
                    super.onFailure(code, message)
                    clearTokenStorage()

                    observer.onFailure(code, message)
                }
            })
        }
    }

    private fun requestUser(owner: LifecycleOwner, name: String, observer: LiveDataCallBack<User>): LiveData<User> {
        return userService.getUser(true, name).apply {
            observe(owner, object : LiveDataCallBack<User>() {
                override fun onSuccess(code: String?, data: User?) {
                    super.onSuccess(code, data)
                    data?.let {
                        //`User`本地数据克隆到 `ModelUIModel` 内存数据
                        UserConversion.cloneDataFromUser(
                                application,
                                it,
                                appGlobalModel.userObservable
                        )

                        //保存 `UserInfoBean` 用户数据
                        putUserInfoPref(data)
                    }
                    observer.onSuccess(code, data)
                }

                override fun onFailure(code: String?, message: String?) {
                    super.onFailure(code, message)
                    clearTokenStorage()
                    observer.onFailure(code, message)
                }
            })
        }
    }

    fun requestReceivedEvent(
            owner: LifecycleOwner,
            page: Int,
            observer: LiveDataCallBack<Page<List<Event>>>
    ): LiveData<Page<List<Event>>> {
        val name: String? = appGlobalModel.userObservable.login
        name.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }

        return userService.getNewsEvent(true, name!!, page).apply {
            observe(
                    owner,
                    object : LiveDataCallBack<Page<List<Event>>>() {
                        override fun onSuccess(code: String?, data: Page<List<Event>>?) {
                            super.onSuccess(code, data)
                            data?.let {
                                if (page == 1) {
                                    val entity = ReceivedEventEntity(
                                            id = 0,
                                            data = GsonUtils.toJsonString(it)
                                    )
                                    //userDao.insertReceivedEvent(entity)
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

    fun queryReceivedEvent(
            owner: LifecycleOwner,
            id: Int,
            observer: LiveDataCallBack<Page<List<Event>>>
    ): LiveData<ReceivedEventEntity> {
        return userDao.queryReceivedEvent(id).apply {
            observe(owner,
                    object :
                            LiveDataCallBack<ReceivedEventEntity>() {
                        override fun onSuccess(code: String?, data: ReceivedEventEntity?) {
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

    fun requestOrgMembers(
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

    fun queryOrgMembers(
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
                            }
                        }

                        override fun onFailure(code: String?, message: String?) {
                            super.onFailure(code, message)
                            observer.onFailure(code, message)
                        }
                    })
        }
    }

    fun requestUserEvents(
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
                            }
                            observer.onSuccess(code, data)
                        }

                        override fun onFailure(code: String?, message: String?) {
                            super.onFailure(code, message)
                            observer.onFailure(code,message)
                        }
                    })
        }
    }

     fun queryUserEvents(
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
                            }
                        }

                        override fun onFailure(code: String?, message: String?) {
                            super.onFailure(code, message)
                            observer.onFailure(code, message)
                        }
                    })
        }
    }

    fun requestNotify(
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
                        observer.onSuccess(code, data)
                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                        observer.onFailure(code, message)
                    }
                })
        }
    }


    fun signOut() {
        clearTokenStorage()
        clearCookies()
    }

    private fun clearTokenStorage() {
        userInfoPref = ""
        authInfoPref = ""

        userBasicCodePref = ""
        accessTokenPref = ""
        authIDPref = ""
    }

    private fun clearCookies() {
        val cookieManager: CookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeAllCookies(null)
        }
        WebStorage.getInstance().deleteAllData()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().flush()
        }
    }
}