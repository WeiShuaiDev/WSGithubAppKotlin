package com.linwei.github_mvvm.mvvm.model.repository.main

import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.mvvm.contract.main.DynamicContract
import com.linwei.github_mvvm.mvvm.model.bean.Event
import com.linwei.github_mvvm.mvvm.model.bean.Page
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
class DynamicModel @Inject constructor(
        private val userRepository: UserRepository,
        dataRepository: DataMvvmRepository
) : BaseModel(dataRepository), DynamicContract.Model {

    override fun obtainReceivedEvent(owner: LifecycleOwner,
                                     page: Int,
                                     observer: LiveDataCallBack<Page<List<Event>>>
    ) {
        userRepository.requestReceivedEvent(owner, page, object : LiveDataCallBack<Page<List<Event>>>() {
            override fun onSuccess(code: String?, data: Page<List<Event>>?) {
                super.onSuccess(code, data)
                observer.onSuccess(code, data)
            }

            override fun onFailure(code: String?, message: String?) {
                super.onFailure(code, message)
                //userRepository.queryReceivedEvent(owner, 0, observer)
            }
        })
    }
}