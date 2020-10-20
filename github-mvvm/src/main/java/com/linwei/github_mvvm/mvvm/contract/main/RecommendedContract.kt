package com.linwei.github_mvvm.mvvm.contract.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.github_mvvm.mvvm.model.bean.Event
import com.linwei.github_mvvm.mvvm.model.bean.Page
import com.linwei.github_mvvm.mvvm.model.bean.TrendingRepoModel

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
interface RecommendedContract {

    interface View

    interface ViewModel {

        /**
         * 获取趋势数据
         */
        fun toTrendData()

    }

    interface Model {

        /**
         * 请求趋势数据
         * @param owner [LifecycleOwner]
         * @param languageType [String]
         * @param since [String]
         * @param liveData [MutableLiveData]
         */
        fun requestTrendData(
            owner: LifecycleOwner,
            languageType: String,
            since: String,
            observer: LiveDataCallBack<List<TrendingRepoModel>>
        ): LiveData<List<TrendingRepoModel>>
    }

}