package com.linwei.github_mvvm.mvvm.model.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.linwei.cams.ext.isNotNullOrEmpty
import com.linwei.cams.ext.no
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.mvvm.contract.main.DynamicContract
import com.linwei.github_mvvm.mvvm.model.AppGlobalModel
import com.linwei.github_mvvm.mvvm.model.api.service.UserService
import com.linwei.github_mvvm.mvvm.model.bean.Event
import com.linwei.github_mvvm.mvvm.model.conversion.EventConversion
import com.linwei.github_mvvm.mvvm.model.data.EventUIModel
import com.linwei.github_mvvm.mvvm.model.db.LocalDatabase
import com.linwei.github_mvvm.mvvm.model.db.dao.UserDao
import com.linwei.github_mvvm.mvvm.model.db.entity.ReceivedEventEntity
import com.linwei.github_mvvm.utils.GsonUtils
import timber.log.Timber
import java.util.ArrayList
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
    dataRepository: DataMvvmRepository,
    private val appGlobalModel: AppGlobalModel
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
        observer: LiveDataCallBack<List<EventUIModel>, List<EventUIModel>>
    ): LiveData<List<Event>> {
        val name: String? = appGlobalModel.userObservable.login
        name.isNotNullOrEmpty().no { return@no }

        return userService.getNewsEvent(true, name!!, page).apply {
            observe(
                owner,
                object : LiveDataCallBack<List<Event>, List<Event>>() {
                    override fun onSuccess(code: String?, data: List<Event>?) {
                        super.onSuccess(code, data)
                        data?.let {
                            //保存接收事件数据到数据库中
                            val entity = ReceivedEventEntity(
                                id = 0,
                                data = GsonUtils.toJsonString(it)
                            )
                            userDao.insertReceivedEvent(entity)

                            val eventUIList = ArrayList<EventUIModel>()
                            it.apply {
                                for (event: Event in it) {
                                    eventUIList.add(EventConversion.eventToEventUIModel(event))
                                }
                            }
                            observer.onSuccess(code, eventUIList)

                            Timber.i(" request Http EventUIModel Data Success~")
                        }
                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                        //获取数据库中接收事件数据
                        queryReceivedEvent(owner, 0, observer)

                        Timber.i(" request Http EventUIModel Data Failed~")
                    }
                })
        }
    }

    override fun queryReceivedEvent(
        owner: LifecycleOwner,
        id: Int,
        observer: LiveDataCallBack<List<EventUIModel>, List<EventUIModel>>
    ): LiveData<ReceivedEventEntity> {
        return userDao.queryReceivedEvent(id).apply {
            observe(owner,
                object :
                    LiveDataCallBack<ReceivedEventEntity, ReceivedEventEntity>() {
                    override fun onSuccess(code: String?, data: ReceivedEventEntity?) {
                        super.onSuccess(code, data)
                        data?.let {
                            val eventUIList = ArrayList<EventUIModel>()
                            GsonUtils.parserJsonToArrayBeans(it.data, Event::class.java).forEach {
                                eventUIList.add(EventConversion.eventToEventUIModel(it))
                            }
                            observer.onSuccess(code, eventUIList)

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