package com.linwei.github_mvvm.mvvm.contract.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.github_mvvm.mvvm.model.bean.Notification
import com.linwei.github_mvvm.mvvm.model.bean.Page
import com.linwei.github_mvvm.mvvm.model.bean.TrendingRepoModel

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
         * 用户接收到的事件数据
         * @param page [Int] 页码
         */
        fun toNotifyData()
    }

    interface Model {
        /**
         * 请求通知数据
         * @param owner [LifecycleOwner]
         * @param languageType [String]
         * @param since [String]
         * @param liveData [MutableLiveData]
         */
        fun requestNotify(
            owner: LifecycleOwner,
            all: Boolean?,
            participating: Boolean?,
            page: Int,
            observer: LiveDataCallBack<Page<List<Notification>>>
        ): LiveData<Page<List<Notification>>>
    }

}