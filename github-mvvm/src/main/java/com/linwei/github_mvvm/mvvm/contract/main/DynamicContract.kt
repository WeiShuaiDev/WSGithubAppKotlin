package com.linwei.github_mvvm.mvvm.contract.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linwei.github_mvvm.mvvm.model.bean.Event
import com.linwei.github_mvvm.mvvm.model.data.EventUIModel
import com.linwei.github_mvvm.mvvm.model.db.entity.ReceivedEventEntity
import java.util.ArrayList

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
         * 请求网络用户接收到的事件数据
         * @param owner [LifecycleOwner]
         * @param page [Int]
         * @param liveData [MutableLiveData]
         */
        fun httpReceivedEvent(
            owner: LifecycleOwner,
            page: Int
        ): LiveData<List<Event>>

        /**
         * 获取用户接收到的事件数据
         * @param owner [LifecycleOwner]
         * @param page [Int]
         * @param liveData [MutableLiveData]
         */
        fun requestReceivedEvent(
            owner: LifecycleOwner,
            page: Int,
            liveData: MutableLiveData<List<EventUIModel>>
        )

        /**
         * 查询数据库用户接收到的事件数据
         * @param owner [LifecycleOwner]
         * @param id [Int]
         * @param liveData [MutableLiveData]
         */
        fun queryReceivedEvent(
            owner: LifecycleOwner,
            id: Int,
            liveData: MutableLiveData<List<EventUIModel>>
        )
    }
}