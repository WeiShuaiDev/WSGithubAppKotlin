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
import com.linwei.github_mvvm.mvvm.model.api.service.ReposService
import com.linwei.github_mvvm.mvvm.model.api.service.UserService
import com.linwei.github_mvvm.mvvm.model.bean.*
import com.linwei.github_mvvm.mvvm.model.conversion.UserConversion
import com.linwei.github_mvvm.mvvm.model.repository.db.LocalDatabase
import com.linwei.github_mvvm.mvvm.model.repository.db.dao.UserDao
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.*
import com.linwei.github_mvvm.utils.GsonUtils
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
open class UserRepository @Inject constructor(
    private val application: Application,
    private val appGlobalModel: AppGlobalModel,
    dataRepository: DataMvvmRepository
) : BaseModel(dataRepository) {

    /**
     * 用户权限校验服务接口
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

    /**
     * 仓库服务接口
     */
    private val reposService: ReposService by lazy {
        dataRepository.obtainRetrofitService(ReposService::class.java)
    }

    /**
     * 请求网络进行账号密码登录
     * @param owner [LifecycleOwner]
     * @param username [String] 用户名
     * @param password [Int]  密码
     * @return  [LiveDataCallBack]
     */
    fun requestAccountLogin(
        owner: LifecycleOwner, username: String,
        password: String, observer: LiveDataCallBack<AuthResponse>
    ) {

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

    /**
     * 请求网络进行OAuth登录
     * @param owner [LifecycleOwner]
     * @param code [String]
     * @return  [LiveDataCallBack]
     */
    fun requestOAuthLogin(
        owner: LifecycleOwner, code: String, observer: LiveDataCallBack<AccessToken>
    ) {
        clearTokenStorage()

        requestCreateCodeAuthorization(owner, code, observer)
    }

    private fun requestCreateCodeAuthorization(
        owner: LifecycleOwner,
        code: String,
        observer: LiveDataCallBack<AccessToken>
    ): LiveData<AccessToken> {
        return authService.createCodeAuthorization(
            client_id = AuthRequest.clientId,
            client_secret = AuthRequest.clientSecret,
            code = code
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

    /**
     * 请求网络获取账号密码Token令牌
     * @param owner [LifecycleOwner]
     * @return  [LiveDataCallBack]
     */
    private fun requestCreateAuthorization(
        owner: LifecycleOwner,
        observer: LiveDataCallBack<AuthResponse>
    ): LiveData<AuthResponse> {
        return authService.createAuthorization(authRequestModel = AuthRequest.generate()).apply {
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

    /**
     * 请求网络删除 Token令牌
     * @param owner [LifecycleOwner]
     * @param id [Int]
     * @return  [LiveDataCallBack]
     */
    private fun requestDeleteAuthorization(owner: LifecycleOwner, id: Int): LiveData<Any> {
        return authService.deleteAuthorization(id = id).apply {
            observe(owner, object : LiveDataCallBack<Any>() {
                override fun onSuccess(code: String?, data: Any?) {
                    super.onSuccess(code, data)
                    accessTokenPref = ""
                    authIDPref = ""
                }
            })
        }
    }

    /**
     * 请求网络获取第三方用户数据或者当前用户数据
     * @param owner [LifecycleOwner]
     * @param name [String]
     * @return [LiveDataCallBack]
     */
    fun requestAuthenticatedUserInfo(
        owner: LifecycleOwner,
        name: String?,
        observer: LiveDataCallBack<User>
    ): LiveData<User> {
        return name.isNotNullOrEmpty().yes {
            requestUser(owner = owner, name = name!!, observer = observer)
        }.otherwise {
            requestPersonInfo(owner = owner, observer = observer)
        }
    }

    /**
     * 请求网络获取第三方用户数据
     * @param owner [LifecycleOwner]
     * @return [LiveDataCallBack]
     */
    private fun requestPersonInfo(
        owner: LifecycleOwner,
        observer: LiveDataCallBack<User>
    ): LiveData<User> {
        return userService.getPersonInfo(forceNetWork = true).apply {
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

    /**
     * 请求网络获取当前用户数据
     * @param owner [LifecycleOwner]
     * @param name [String]
     * @return [LiveDataCallBack]
     */
    private fun requestUser(
        owner: LifecycleOwner,
        name: String,
        observer: LiveDataCallBack<User>
    ): LiveData<User> {
        return userService.getUser(forceNetWork = true, user = name).apply {
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

    /**
     * 请求网络修改个人信息
     * @param owner [LifecycleOwner]
     * @param requestModel [UserInfoRequestModel]
     * @return [LiveDataCallBack]
     */
    private fun requestChangeUserInfo(
        owner: LifecycleOwner,
        requestModel: UserInfoRequestModel,
        observer: LiveDataCallBack<User>
    ): LiveData<User> {
        return userService.saveUserInfo(body = requestModel).apply {
            observe(owner, object : LiveDataCallBack<User>() {
                override fun onSuccess(code: String?, data: User?) {
                    super.onSuccess(code, data)
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

    /**
     * 请求网络检查是否关注
     * @param owner [LifecycleOwner]
     * @return [LiveDataCallBack]
     */
    private fun requestCheckFocus(
        owner: LifecycleOwner,
        observer: LiveDataCallBack<Boolean>
    ): LiveData<ResponseBody> {

        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }

        return userService.checkFollowing(user = userName!!).apply {
            observe(owner, object : LiveDataCallBack<ResponseBody>() {
                override fun onSuccess(code: String?, data: ResponseBody?) {
                    super.onSuccess(code, data)
                    if (code == "404") {
                        observer.onSuccess(code, false)
                    } else {
                        observer.onSuccess(code, true)
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
     * 请求网络执行关注操作
     * @param owner [LifecycleOwner]
     * @param name [String]
     * @param focus [Boolean]
     * @return [LiveDataCallBack]
     */
    private fun requestDoFocus(
        owner: LifecycleOwner,
        user: String,
        focus: Boolean,
        observer: LiveDataCallBack<Boolean>
    ): LiveData<ResponseBody> {

        val userName: String? = appGlobalModel.userObservable.login
        (userName == null || userName == user).no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }

        return if (focus) {
            userService.unfollowUser(user = user)
        } else {
            userService.followUser(user = user)
        }.apply {
            observe(owner, object : LiveDataCallBack<ResponseBody>() {
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
     * 请求网络获取用户产生的行为事件数据
     * @param owner [LifecycleOwner]
     * @param page [Int]
     * @return [LiveDataCallBack]
     */
    fun requestUserEvents(
        owner: LifecycleOwner,
        page: Int,
        observer: LiveDataCallBack<Page<List<Event>>>
    ): LiveData<Page<List<Event>>> {

        val userName: String? = appGlobalModel.userObservable.login

        return userService.getUserEvents(forceNetWork = true, user = userName ?: "", page = page)
            .apply {
                observe(
                    owner,
                    object : LiveDataCallBack<Page<List<Event>>>() {
                        override fun onSuccess(code: String?, data: Page<List<Event>>?) {
                            super.onSuccess(code, data)
                            data?.let {
                                if (page == 1) {
                                    val entity = UserEventEntity(
                                        userName = userName,
                                        data = GsonUtils.toJsonString(it)
                                    )
                                    //userDao.insertUserEvent(entity)
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
     * 查询数据库获取用户产生的行为事件数据
     * @param owner [LifecycleOwner]
     * @param name [Int]
     * @return [LiveDataCallBack]
     */
    fun queryUserEvents(
        owner: LifecycleOwner,
        name: String,
        observer: LiveDataCallBack<Page<List<Event>>>
    ): LiveData<UserEventEntity> {
        return userDao.queryUserEvent(userName = name).apply {
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


    /**
     * 请求网络获取用户产生的行为事件数据
     * @param owner [LifecycleOwner]
     * @param page [Int]
     * @return [LiveDataCallBack]
     */
    fun requestOrgMembers(
        owner: LifecycleOwner,
        page: Int,
        observer: LiveDataCallBack<Page<List<User>>>
    ): LiveData<Page<List<User>>> {
        val org: String? = appGlobalModel.userObservable.login

        return userService.getOrgMembers(forceNetWork = true, org = org ?: "", page = page).apply {
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

    /**
     * 查询数据库获取用户产生的行为事件数据
     * @param owner [LifecycleOwner]
     * @param org [String]
     * @return [LiveDataCallBack]
     */
    fun queryOrgMembers(
        owner: LifecycleOwner,
        org: String,
        observer: LiveDataCallBack<Page<List<User>>>
    ): LiveData<OrgMemberEntity> {
        return userDao.queryOrgMember(org = org).apply {
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

    /**
     * 请求网络获取用户接收到的事件
     * @param owner [LifecycleOwner]
     * @param page [Int]
     * @return [LiveDataCallBack]
     */
    fun requestReceivedEvent(
        owner: LifecycleOwner,
        page: Int,
        observer: LiveDataCallBack<Page<List<Event>>>
    ): LiveData<Page<List<Event>>> {
        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }

        return userService.getNewsEvent(forceNetWork = true, user = userName!!, page = page).apply {
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

    /**
     * 查询数据库获取用户接收到的事件
     * @param owner [LifecycleOwner]
     * @param page [Int]
     * @return [LiveDataCallBack]
     */
    fun queryReceivedEvent(
        owner: LifecycleOwner,
        id: Int,
        observer: LiveDataCallBack<Page<List<Event>>>
    ): LiveData<ReceivedEventEntity> {
        return userDao.queryReceivedEvent(id = id).apply {
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

    /**
     * 请求网络获取用户粉丝列表数据
     * @param owner [LifecycleOwner]
     * @param page [Int]
     * @return [LiveDataCallBack]
     */
    fun requestUserFollower(
        owner: LifecycleOwner,
        page: Int,
        observer: LiveDataCallBack<Page<List<User>>>
    ): LiveData<Page<List<User>>> {
        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }

        return userService.getFollowers(forceNetWork = true, user = userName!!, page = page).apply {
            observe(
                owner,
                object : LiveDataCallBack<Page<List<User>>>() {
                    override fun onSuccess(code: String?, data: Page<List<User>>?) {
                        super.onSuccess(code, data)
                        data?.let {
                            if (page == 1) {
                                val entity = UserFollowerEntity(
                                    userName = userName,
                                    data = GsonUtils.toJsonString(it)
                                )
                                userDao.insertUserFollower(entity)
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
     * 查询数据库获取用户粉丝列表数据
     * @param owner [LifecycleOwner]
     * @return [LiveDataCallBack]
     */
    fun queryUserFollower(
        owner: LifecycleOwner,
        observer: LiveDataCallBack<Page<List<User>>>
    ): LiveData<UserFollowerEntity> {
        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }

        return userDao.queryUserFollower(userName = userName!!).apply {
            observe(owner,
                object :
                    LiveDataCallBack<UserFollowerEntity>() {
                    override fun onSuccess(code: String?, data: UserFollowerEntity?) {
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

    /**
     * 请求网络获取用户关注列表数据
     * @param owner [LifecycleOwner]
     * @param page [Int]
     * @return [LiveDataCallBack]
     */
    fun requestUserFollowed(
        owner: LifecycleOwner,
        page: Int,
        observer: LiveDataCallBack<Page<List<User>>>
    ): LiveData<Page<List<User>>> {
        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }

        return userService.getFollowing(forceNetWork = true, user = userName!!, page = page).apply {
            observe(
                owner,
                object : LiveDataCallBack<Page<List<User>>>() {
                    override fun onSuccess(code: String?, data: Page<List<User>>?) {
                        super.onSuccess(code, data)
                        data?.let {
                            if (page == 1) {
                                val entity = UserFollowedEntity(
                                    userName = userName,
                                    data = GsonUtils.toJsonString(it)
                                )
                                userDao.insertUserFollowed(entity)
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
     * 查询数据库获取用户关注列表数据
     * @param owner [LifecycleOwner]
     * @return [LiveDataCallBack]
     */
    fun queryUserFollowed(
        owner: LifecycleOwner,
        observer: LiveDataCallBack<Page<List<User>>>
    ): LiveData<UserFollowedEntity> {
        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }

        return userDao.queryUserFollowed(userName = userName!!).apply {
            observe(owner,
                object :
                    LiveDataCallBack<UserFollowedEntity>() {
                    override fun onSuccess(code: String?, data: UserFollowedEntity?) {
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

    /**
     * 请求网络获取仓库的star用户列表
     * @param owner [LifecycleOwner]
     * @param reposName [String]
     * @param page [Int]
     * @return [LiveDataCallBack]
     */
    fun requestStargazers(
        owner: LifecycleOwner,
        reposName: String,
        page: Int,
        observer: LiveDataCallBack<Page<List<User>>>
    ): LiveData<Page<List<User>>> {
        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }

        return reposService.getStargazers(
            forceNetWork = true,
            page = page,
            owner = userName!!,
            repo = reposName
        )
            .apply {
                observe(
                    owner,
                    object : LiveDataCallBack<Page<List<User>>>() {
                        override fun onSuccess(code: String?, data: Page<List<User>>?) {
                            super.onSuccess(code, data)
                            data?.let {
                                if (page == 1) {
                                    val entity = RepositoryStarEntity(
                                        fullName = "$userName/$reposName",
                                        data = GsonUtils.toJsonString(it)
                                    )
                                    userDao.insertRepositoryStar(entity)
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
     * 查询数据库获取仓库的star用户列表
     * @param owner [LifecycleOwner]
     * @param reposName [String]
     * @return [LiveDataCallBack]
     */
    fun queryStargazers(
        owner: LifecycleOwner,
        reposName: String,
        observer: LiveDataCallBack<Page<List<User>>>
    ): LiveData<RepositoryStarEntity> {
        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }

        return userDao.queryRepositoryStar(fullName = "$userName/$reposName").apply {
            observe(owner,
                object :
                    LiveDataCallBack<RepositoryStarEntity>() {
                    override fun onSuccess(code: String?, data: RepositoryStarEntity?) {
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


    /**
     * 请求网络获取仓库的watch用户列表
     * @param owner [LifecycleOwner]
     * @param reposName [String]
     * @param page [Int]
     * @return [LiveDataCallBack]
     */
    fun requestWatchers(
        owner: LifecycleOwner,
        reposName: String,
        page: Int,
        observer: LiveDataCallBack<Page<List<User>>>
    ): LiveData<Page<List<User>>> {
        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }

        return reposService.getWatchers(
            forceNetWork = true,
            page = page,
            owner = userName!!,
            repo = reposName
        )
            .apply {
                observe(
                    owner,
                    object : LiveDataCallBack<Page<List<User>>>() {
                        override fun onSuccess(code: String?, data: Page<List<User>>?) {
                            super.onSuccess(code, data)
                            data?.let {
                                if (page == 1) {
                                    val entity = RepositoryWatcherEntity(
                                        fullName = "$userName/$reposName",
                                        data = GsonUtils.toJsonString(it)
                                    )
                                    userDao.insertRepositoryWatcher(entity)
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
     * 查询数据库获取仓库的watch用户列表
     * @param owner [LifecycleOwner]
     * @param reposName [String]
     * @return [LiveDataCallBack]
     */
    fun queryWatchers(
        owner: LifecycleOwner,
        reposName: String,
        observer: LiveDataCallBack<Page<List<User>>>
    ): LiveData<RepositoryWatcherEntity> {
        val userName: String? = appGlobalModel.userObservable.login
        userName.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no
        }

        return userDao.queryRepositoryWatcher(fullName = "$userName/$reposName").apply {
            observe(owner,
                object :
                    LiveDataCallBack<RepositoryWatcherEntity>() {
                    override fun onSuccess(code: String?, data: RepositoryWatcherEntity?) {
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

    /**
     * @param owner [LifecycleOwner]
     * @param all [Boolean]
     * @param participating [Boolean]
     * @param page [Int]
     * @return [LiveDataCallBack]
     */
    fun requestNotify(
        owner: LifecycleOwner,
        all: Boolean?,
        participating: Boolean?,
        page: Int,
        observer: LiveDataCallBack<Page<List<Notification>>>
    ): LiveData<Page<List<Notification>>> {

        return if (all == null || participating == null) {
            notificationService.getNotificationUnRead(forceNetWork = true, page = page)
        } else {
            notificationService.getNotification(
                forceNetWork = true,
                all = all,
                participating = participating,
                page = page
            )
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

    /**
     * 退出登录
     */
    fun signOut() {
        clearTokenStorage()
        clearCookies()
    }

    /**
     * 清除所有 `Token` 数据，用户信息数据
     */
    private fun clearTokenStorage() {
        userInfoPref = ""
        authInfoPref = ""

        userBasicCodePref = ""
        accessTokenPref = ""
        authIDPref = ""
    }

    /**
     * 清除所有 `Cookies` 数据
     */
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