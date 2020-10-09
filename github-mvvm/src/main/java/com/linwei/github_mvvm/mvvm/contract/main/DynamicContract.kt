package com.linwei.github_mvvm.mvvm.contract.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linwei.github_mvvm.mvvm.model.bean.Event
import com.linwei.github_mvvm.mvvm.model.data.EventUIModel

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/20
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
interface DynamicContract {

    interface View {

    }

    interface ViewModel {
        /**
         * 用户接收到的事件数据
         * @param page [Int] 页码
         */
        fun toReceivedEvent(page: Int)

    }

    interface Model {
        /**
         * 请求用户接收到的事件数据
         * @param owner [LifecycleOwner]
         * @param page [Int]
         * @param liveData [MutableLiveData]
         */
        fun requestReceivedEvent(
            owner: LifecycleOwner,
            page: Int,
            liveData: MutableLiveData<Event>
        )
    }

}