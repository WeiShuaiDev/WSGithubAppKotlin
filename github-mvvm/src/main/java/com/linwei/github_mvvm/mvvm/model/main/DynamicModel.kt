package com.linwei.github_mvvm.mvvm.model.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.ext.isNotNullOrEmpty
import com.linwei.cams.ext.no
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.mvvm.contract.main.DynamicContract
import com.linwei.github_mvvm.mvvm.model.AppGlobalModel
import com.linwei.github_mvvm.mvvm.model.api.service.UserService
import com.linwei.github_mvvm.mvvm.model.bean.Event
import com.linwei.github_mvvm.mvvm.model.db.LocalDatabase
import com.linwei.github_mvvm.mvvm.model.db.dao.UserDao
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
    val appGlobalModel: AppGlobalModel
) :
    BaseModel(dataRepository), DynamicContract.Model {

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
        liveData: MutableLiveData<Event>
    ) {
        val name: String? = appGlobalModel.userObservable.login
        name.isNotNullOrEmpty().no { return@no }

        userService.getNewsEvent(true, name!!, page).apply {
            observe(
                owner,
                object : LiveDataCallBack<ArrayList<Event>, ArrayList<Event>>() {
                    override fun onSuccess(code: String?, data: ArrayList<Event>?) {
                        super.onSuccess(code, data)
                        data?.let {

                        }
                    }
                })
        }
    }
}