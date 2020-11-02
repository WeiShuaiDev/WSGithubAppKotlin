package com.linwei.github_mvvm.mvvm.model.repository.main

import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.mvvm.contract.main.MineContract
import com.linwei.github_mvvm.mvvm.model.bean.*
import com.linwei.github_mvvm.mvvm.model.repository.service.SearchRepository
import com.linwei.github_mvvm.mvvm.model.repository.service.UserRepository
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
    private val userRepository: UserRepository,
    dataRepository: DataMvvmRepository
) : BaseModel(dataRepository), MineContract.Model {

    override fun obtainOrgMembers(
            owner: LifecycleOwner,
            page: Int, observer:
            LiveDataCallBack<Page<List<User>>>
    ) {
        userRepository.requestOrgMembers(owner, page, object : LiveDataCallBack<Page<List<User>>>() {
            override fun onSuccess(code: String?, data: Page<List<User>>?) {
                super.onSuccess(code, data)
                observer.onSuccess(code, data)
            }

            override fun onFailure(code: String?, message: String?) {
                super.onFailure(code, message)
                //userRepository.queryOrgMembers(owner, "0", observer)
            }
        })
    }

    override fun obtainUserEvents(
            owner: LifecycleOwner,
            page: Int,
            observer: LiveDataCallBack<Page<List<Event>>>
    ) {
        userRepository.requestUserEvents(owner, page, object : LiveDataCallBack<Page<List<Event>>>() {
            override fun onSuccess(code: String?, data: Page<List<Event>>?) {
                super.onSuccess(code, data)
                observer.onSuccess(code, data)
            }

            override fun onFailure(code: String?, message: String?) {
                super.onFailure(code, message)
                //userRepository.queryUserEvents(owner, "0", observer)
            }
        })
    }

    override fun obtainNotify(
            owner: LifecycleOwner,
            all: Boolean?,
            participating: Boolean?,
            page: Int,
            observer: LiveDataCallBack<Page<List<Notification>>>
    ) {
        userRepository.requestNotify(owner, all, participating, page, object : LiveDataCallBack<Page<List<Notification>>>() {
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