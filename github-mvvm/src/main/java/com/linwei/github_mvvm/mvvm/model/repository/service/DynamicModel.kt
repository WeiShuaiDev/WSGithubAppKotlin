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
import com.linwei.github_mvvm.mvvm.contract.main.DynamicContract
import com.linwei.github_mvvm.mvvm.model.AppGlobalModel
import com.linwei.github_mvvm.mvvm.model.api.service.UserService
import com.linwei.github_mvvm.mvvm.model.bean.Event
import com.linwei.github_mvvm.mvvm.model.bean.Page
import com.linwei.github_mvvm.mvvm.model.repository.db.LocalDatabase
import com.linwei.github_mvvm.mvvm.model.repository.db.dao.UserDao
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.ReceivedEventEntity
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
class DynamicModel @Inject constructor(
    private val appGlobalModel: AppGlobalModel,
    dataRepository: DataMvvmRepository
) : BaseModel(dataRepository), DynamicContract.Model {

    /**
     *  用户服务接口
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

    override fun requestReceivedEvent(
        owner: LifecycleOwner,
        page: Int,
        observer: LiveDataCallBack<Page<List<Event>>>
    ): LiveData<Page<List<Event>>> {
        val name: String? = appGlobalModel.userObservable.login
        name.isNotNullOrEmpty().no {
            observer.onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
            return@no }

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

                            observer.onSuccess(code, data)

                            Timber.i(" request Http EventUIModel Data Success~")
                        }
                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                        //queryReceivedEvent(owner, 0, observer)
                        Timber.i(" request Http EventUIModel Data Failed~")
                    }
                })
        }
    }

    override fun queryReceivedEvent(
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

                            Timber.i(" request DB EventUIModel Data Success~")
                        }
                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                        observer.onFailure(code, message)

                        Timber.i(" request DB EventUIModel Data failed~")
                    }
                })
        }
    }
}