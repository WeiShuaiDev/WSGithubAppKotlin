package com.linwei.github_mvvm.mvvm.contract.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.github_mvvm.mvvm.model.bean.*
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.OrgMemberEntity
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.UserEventEntity

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/20
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
interface MineContract {

    interface View

    interface ViewModel {
        /**
         * 分页加载，刷新数据
         */
        fun loadDataByRefresh()

        /**
         * 分页加载,加载更多
         * @param page:Int
         */
        fun loadDataByLoadMore(page: Int)

        /**
         * 用户数据
         */
        fun toPersonInfo()

        /**
         * 用户关注数据
         * @param page:Int
         */
        fun toOrgMembers(page: Int)

        /**
         * 用户产生事件数据
         * @param page:Int
         */
        fun toUserEvents(page: Int)

        /**
         * 用户接收到的事件数据
         */
        fun toNotifyData()
    }

    interface Model {
        /**
         * 网络请求关注数据
         * @param owner [LifecycleOwner]
         * @param page [Int]
         * @param liveData [MutableLiveData]
         */
        fun obtainOrgMembers(
            owner: LifecycleOwner,
            page: Int,
            observer: LiveDataCallBack<Page<List<User>>>
        )

        /**
         * 网络请求产生事件数据
         * @param owner [LifecycleOwner]
         * @param page [Int]
         * @param liveData [MutableLiveData]
         */
        fun obtainUserEvents(
            owner: LifecycleOwner,
            page: Int,
            observer: LiveDataCallBack<Page<List<Event>>>
        )

        /**
         * 网络请求通知数据
         * @param owner [LifecycleOwner]
         * @param all [String]
         * @param participating [Boolean]
         * @param page [Int]
         * @param liveData [MutableLiveData]
         */
        fun obtainNotify(
            owner: LifecycleOwner,
            all: Boolean?,
            participating: Boolean?,
            page: Int,
            observer: LiveDataCallBack<Page<List<Notification>>>
        )
    }
}